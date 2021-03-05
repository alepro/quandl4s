package alepro.quandl4s.client

import alepro.quandl4s.client.apis.InvestorSentimentData
import alepro.quandl4s.csv.CSVParser
import cats.effect.{IO, Resource}
import cats.implicits.catsSyntaxOptionId
import fs2.Stream
import fs2.text.utf8Encode
import org.http4s.client.Client
import org.http4s.{Response, Status}
import org.scalatest.{Matchers, WordSpec}

import java.time.LocalDate

class QuandlClientTest extends WordSpec with Matchers {

  "QuandlClient.InvestorSentimentData" should {
    "parse sentiments for 2021-02-04 and 1987-06-26" in {
      val data =
        """
          |2021-02-04, 0.373529, 0.270588, 0.355882, 0.9999990000000001, 0.429851875, 0.017647000000000024, 0.37956390217391417, 0.48009723468341037, 0.27903056966441797, 3847.51, 3694.12, 3830.17
          |1987-06-26,,,,,,,0.37956390217391417,0.48009723468341037,0.27903056966441797,,,
          |""".stripMargin
      val resp = Response[IO](Status.Ok, body = Stream(data).through(utf8Encode))
      implicit val testClient = Resource.pure[IO, Client[IO]](Client[IO](_ => Resource.pure[IO, Response[IO]](resp)))
      implicit val parser = CSVParser[InvestorSentimentData]
      val request = DatasetRequest("A/B", "key")
      quandlRequest(request).unsafeRunSync() shouldBe Right(List(
        InvestorSentimentData(date = LocalDate.of(2021, 2, 4),
          bullish = 0.373529.some,
          neutral = 0.270588.some,
          bearish = 0.355882.some,
          total = 0.9999990000000001.some,
          bullish8WeekMovAvg = 0.429851875.some,
          bullBearSpread = 0.017647000000000024.some,
          bullishAverage = 0.37956390217391417.some,
          bullishAveragePlusStDev = 0.48009723468341037.some,
          bullishAverageMinusStDev = 0.27903056966441797.some,
          sp500WeeklyHigh = 3847.51.some,
          sp500WeeklyLow = 3694.12.some,
          sp500WeeklyClose = 3830.17.some),
        InvestorSentimentData(date = LocalDate.of(1987, 6, 26),
          bullish = None,
          neutral = None,
          bearish = None,
          total = None,
          bullish8WeekMovAvg = None,
          bullBearSpread = None,
          bullishAverage = 0.37956390217391417.some,
          bullishAveragePlusStDev = 0.48009723468341037.some,
          bullishAverageMinusStDev = 0.27903056966441797.some,
          sp500WeeklyHigh = None,
          sp500WeeklyLow = None,
          sp500WeeklyClose = None)
      ))
    }
  }

}
