package alepro.quandl4s.csv

import cats.syntax.all._
import org.scalacheck.Arbitrary
import org.scalacheck.Gen._
import org.scalatest.{Matchers, WordSpec}

class CSVParserTest extends WordSpec with Matchers {

  case class Person(name: String, surname: String, age: Option[Int], isAgent: Option[Boolean])
  val johnSmithCsv = "John,Smith,38,"
  val jamesBondCsv = "James,Bond,,true"
  val unknownCsv = "U,N,hundred,0"
  val johnSmith: Person = Person("John", "Smith", 38.some, none)
  val jamesBond: Person = Person("James", "Bond", none, true.some)
  val unknownPerson: Person = Person("U", "N", none, none)


  "CSVParser" should {
    s"parse $johnSmithCsv into Person product" in {
      CSVParser[Person].from(johnSmithCsv) shouldBe Right(johnSmith)
    }
    s"parse $jamesBondCsv into Person product" in {
      CSVParser[Person].from(jamesBondCsv) shouldBe Right(jamesBond)
    }
    s"parse $unknownCsv into Person product" in {
      CSVParser[Person].from(unknownCsv) shouldBe Right(unknownPerson)
    }
    s"parse multiline csv list into list of Persons" in {
      val csv = s"$johnSmithCsv\n$jamesBondCsv\n$unknownCsv"
      CSVParser[List[Person]].from(csv) shouldBe Right(List(johnSmith, jamesBond, unknownPerson))
    }
    "parse empty csv to list" in {
      CSVParser[List[Person]].from("") shouldBe Right(Nil)
    }
    "skip empty rows when parsing multiple values" in {
      val csv = s"$johnSmithCsv\n\n\n$jamesBondCsv\n"
      CSVParser[List[Person]].from(csv) shouldBe Right(List(johnSmith, jamesBond))
    }

    "parse arbitrary csv into Person" in {
      for (_ <- 1 to 1000) {
        for {
          name <- identifier.sample
          surname <- identifier.sample
          age = Arbitrary.arbOption[Int].arbitrary.sample.flatten.getOrElse("")
          agent =Arbitrary.arbOption[Boolean].arbitrary.sample.flatten.getOrElse("")
          csv = s"$name,$surname,$age,$agent"
        } yield {
          println(s"parsing\n$csv")
          val person = CSVParser[Person].from(csv)
          person should be a Symbol("right")
          println(s"parsed\n$person\n")
        }
      }
    }
  }

}
