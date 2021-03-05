package alepro.quandl4s.client

import alepro.quandl4s.client.QuandlCollapse.WEEKLY
import alepro.quandl4s.client.QuandlOrder.ASC
import org.scalatest.{Matchers, WordSpec}

import java.time.LocalDate

class DatasetRequestTest extends WordSpec with Matchers {

  private val req = DatasetRequest(code = "A", apiKey = "apiKey")

  "DatasetRequest" should {
    "create basic dataset request" in {
      req.toUri.unsafeRunSync().toString() shouldBe
        "https://www.quandl.com/api/v3/datasets/A.csv?api_key=apiKey"
    }
    "dataset request with changes" in {
      req.withBaseUrl("https://www.quandl.com/api/v4/datasets")
        .withCode("B")
        .withApiKey("apiKey2")
        .withLimit(30)
        .withColumnIndex(1)
        .withStartDate(LocalDate.of(2021, 1, 1))
        .withEndDate(LocalDate.of(2021, 1, 31))
        .withOrder(ASC)
        .withCollapse(WEEKLY)
        .withTransform(QuandlTransform.RDIFF_FROM)
        .toUri.unsafeRunSync().toString() shouldBe
        "https://www.quandl.com/api/v4/datasets/B.csv?api_key=apiKey2&limit=30" +
          "&column_index=1&start_date=2021-01-01&end_date=2021-01-31&order=asc&collapse=weekly&transform=rdiff_from"
    }
    "dataset request for latest record" in {
      req.latest.toUri.unsafeRunSync().toString() shouldBe
        "https://www.quandl.com/api/v3/datasets/A.csv?api_key=apiKey&limit=1"
    }
  }

}
