package alepro.quandl4s

import cats.implicits._
import shapeless.{::, Generic, HList, HNil, Lazy}

import java.time.LocalDate

package object csv {

  trait CSVParser[T] {
    def from(s: String): Either[String, T]
  }

  object CSVParser {
    def apply[T](implicit parser: Lazy[CSVParser[T]]): CSVParser[T] = parser.value

    class CSVParseException(msg: String) extends RuntimeException(msg)

    // implicit for Product ADT (case class).
    implicit def fromProduct[T, R](implicit g: Generic.Aux[T, R], parser: CSVParser[R]): CSVParser[T] =
      (s: String) => parser.from(s).map(g.from)

    // implicit for List[ADT]
    implicit def fromProductList[T, R](implicit parser: CSVParser[T]): CSVParser[List[T]] =
      (s: String) => s
        .split("\\n")
        .toList
        .filter(_.nonEmpty)
        .foldRight(List.empty[Either[String, T]])((s, agr) => parser.from(s) :: agr)
        .sequence

    // implicits for Generic: HList = {HNil, H::T}
    // CSVParser implicit for HNil
    implicit def fromHNil: CSVParser[HNil] =
      (s: String) => if (s.isEmpty) Right(HNil) else Left(s"Cannot parse $s to HNil")

    // CSVParser implicit for H :: T
    implicit def fromHList[H, T <: HList](implicit ph: Lazy[CSVParser[H]],
                                               pt: Lazy[CSVParser[T]]): CSVParser[H :: T] =
      (s: String) => s.span(_ != ',') match {
        case (before, after) =>
          for {
            h <- ph.value.from(before)
            t <- pt.value.from(after.tail)
          } yield h :: t
        case _ => Left(s"Failed to parse $s to H :: T")
      }

    // CSVParser implicit for Option[H] :: T
   implicit def fromHListOpt[H, T<:HList](implicit ph: Lazy[CSVParser[H]],
                                          pt: Lazy[CSVParser[T]]): CSVParser[Option[H] :: T] =
     (s: String) => {
       s.span(_ != ',') match {
         case (before, after) =>
           val optH = ph.value.from(before).toOption
           pt.value.from(after.tail).map(optH :: _)
         case _ => Left(s"Failed to parse $s to Option[H] :: T")
       }
     }

    // implicits for standard types: String, Boolean, Int, Double
    implicit def fromString: CSVParser[String] = (s: String) => Right(s)
    implicit def fromBoolean: CSVParser[Boolean] = (s: String) => Either.catchNonFatal(s.toBoolean).leftMap(_.getMessage)
    implicit def fromInt: CSVParser[Int] = (s: String) => Either.catchNonFatal(s.toInt).leftMap(_.getMessage)
    implicit def fromDouble: CSVParser[Double] = (s: String) => Either.catchNonFatal(s.toDouble).leftMap(_.getMessage)
    implicit def fromLocalDate: CSVParser[LocalDate] = (s: String) => Either.catchNonFatal(LocalDate.parse(s)).leftMap(_.getMessage)
  }

}
