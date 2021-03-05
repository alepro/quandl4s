package alepro.quandl4s.client

import alepro.quandl4s.csv.CSVParser
import cats.effect.{IO, Resource}
import cats.implicits._
import org.http4s.Request
import org.http4s.client.Client
import shapeless.Poly1

/**
 * Basically function QuandlRequest => IO[Either[String, List[A]]]
 * Now only DatasetRequest is supported.
 * DatatableRequest TBD.
 */
object quandlRequest extends Poly1 {
  implicit def baseCase[A, R <: QuandlRequest](implicit parser: CSVParser[A],
                                               clientResource: Resource[IO, Client[IO]]):
  Case.Aux[R, IO[Either[String, List[A]]]] = at { request =>
    clientResource.use { c =>
      for {
        req <- request.toUri.map { u => Request[IO](uri = u) }
        items = c
          .stream(req)
          .flatMap(_.body.chunkAll)
          .map(b => new String(b.toArray).split("\n").tail.toList)
          .map(_.parTraverse(parser.from))
        rez <- items.compile.lastOrError
      } yield rez
    }
  }
}
