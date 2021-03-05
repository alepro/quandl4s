package alepro.quandl4s

import alepro.quandl4s.client.QuandlCollapse.QuandlCollapse
import alepro.quandl4s.client.QuandlOrder.QuandlOrder
import alepro.quandl4s.client.QuandlTransform.QuandlTransform
import alepro.quandl4s.client.quandlRequest.baseCase
import alepro.quandl4s.csv.CSVParser
import cats.effect.{ContextShift, IO, Resource, Timer}
import cats.implicits._
import org.http4s.client.Client
import org.http4s.client.blaze.BlazeClientBuilder
import org.http4s.{QueryParamEncoder, Uri}

import java.time.LocalDate
import java.time.format.DateTimeFormatter.ISO_LOCAL_DATE
import scala.concurrent.ExecutionContext.global

package object client {

  sealed case class ApiKey(key: String)

  object QuandlOrder extends Enumeration {
    type  QuandlOrder = Value
    val ASC, DESC = Value
  }

  object QuandlCollapse extends Enumeration {
    type QuandlCollapse = Value
    val NONE, DAILY, WEEKLY, MONTHLY, QUARTERLY, ANNUAL = Value
  }

  object QuandlTransform extends Enumeration {
    type QuandlTransform = Value
    val NONE, DIFF, RDIFF, RDIFF_FROM, CUMUL, NORMALIZE = Value
  }

  sealed trait QuandlRequest {
    def toUri: IO[Uri]
  }

  case class DatasetRequest(code: String,
                            apiKey: String,
                            limit: Option[Int] = None,
                            columnIndex: Option[Int] = None,
                            startDate: Option[LocalDate] = None,
                            endDate: Option[LocalDate] = None,
                            order: Option[QuandlOrder] = None,
                            collapse: Option[QuandlCollapse] = None,
                            transform: Option[QuandlTransform] = None,
                            baseUrl: String = "https://www.quandl.com/api/v3/datasets") extends QuandlRequest {

    def withBaseUrl(url: String): DatasetRequest =
      copy(baseUrl = url)

    def withCode(c: String): DatasetRequest =
      copy(code = c)

    def withApiKey(key: String): DatasetRequest =
      copy(apiKey = key)

    /**
     * limit=n to get the first n rows of the dataset.
     * limit=1 to get just the latest row.
     */
    def withLimit(newLimit: Int): DatasetRequest =
      copy(limit = newLimit.some)

    def latest: DatasetRequest =
      withLimit(1)

    /**
     * Request a specific column.
     * Column 0 is the date column and is always returned.
     * Data begins at column 1.
     */
    def withColumnIndex(index: Int): DatasetRequest =
      copy(columnIndex = index.some)

    def withStartDate(date: LocalDate): DatasetRequest =
      copy(startDate = date.some)

    def withEndDate(date: LocalDate): DatasetRequest =
      copy(endDate = date.some)

    def withOrder(ord: QuandlOrder): DatasetRequest =
      copy(order = ord.some)

    def withCollapse(c: QuandlCollapse): DatasetRequest =
      copy(collapse = c.some)

    def withTransform(t: QuandlTransform): DatasetRequest =
      copy(transform = t.some)

    override def toUri: IO[Uri] =
      Uri
        .fromString(baseUrl)
        .fold(IO.raiseError, IO.pure)
        .map(_.addPath(code + ".csv"))
        .map(_.withQueryParam("api_key", apiKey))
        .map(addParam("limit", limit))
        .map(addParam("column_index", columnIndex))
        .map(addParam("start_date", startDate.map(_.format(ISO_LOCAL_DATE))))
        .map(addParam("end_date", endDate.map(_.format(ISO_LOCAL_DATE))))
        .map(addParam("order", order.map(_.toString.toLowerCase)))
        .map(addParam("collapse", collapse.map(_.toString.toLowerCase)))
        .map(addParam("transform", transform.map(_.toString.toLowerCase)))

    private def addParam[A: QueryParamEncoder](key: String, maybeVal: Option[A]): Uri => Uri = u =>
      maybeVal match {
        case Some(v) => u.withQueryParam(key, v)
        case None => u
      }
  }

  object Implicits {
    implicit val cs: ContextShift[IO] = IO.contextShift(global)
    implicit val timer: Timer[IO] = IO.timer(global)
    implicit val clientResource: Resource[IO, Client[IO]] = BlazeClientBuilder[IO](global).resource
  }

  implicit class QuandlRequestOps[A <: QuandlRequest](a: A) {
    import Implicits._
    def mapTo[B](implicit apiKey: ApiKey, parser: CSVParser[B]): IO[Either[String, List[B]]] =
      quandlRequest(a)(baseCase)
  }

}
