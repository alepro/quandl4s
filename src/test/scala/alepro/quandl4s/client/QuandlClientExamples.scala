package alepro.quandl4s.client

import alepro.quandl4s.client.apis.VixIndex._
import alepro.quandl4s.client.apis._
import alepro.quandl4s.csv.CSVParser
import cats.effect.unsafe.implicits.global
import org.scalatest.{Matchers, WordSpec}

import java.time.LocalDate

class QuandlClientExamples extends WordSpec with Matchers {

  private implicit val apiKey: ApiKey = ApiKey("_your_quandl_key_")

  "QuandlClient.InvestorSentimentData" ignore {
    "get all data from AAII/AAII_SENTIMENT (InvestorSentimentData)" in {
      getInvestorSentimentDataset().unsafeRunSync().foreach(_ foreach println)
    }
    "get latest record from AAII/AAII_SENTIMENT (InvestorSentimentData)" in {
      getInvestorSentimentDataset(_.latest).unsafeRunSync().foreach(_ foreach println)
    }
    "get BULLISH field of latest record from AAII/AAII_SENTIMENT (InvestorSentimentData)" in {
      type Bullish = (LocalDate, Double)
      implicit val parser = CSVParser[Bullish]
      investorSentimentRequest
        .latest
        .withColumnIndex(1)
        .mapTo[Bullish]
        .unsafeRunSync()
        .foreach(_ foreach println)
    }
    "get BEARISH field of latest record from AAII/AAII_SENTIMENT (InvestorSentimentData" in {
      type Bearish = (LocalDate, Double)
      implicit val parser = CSVParser[Bearish]
      investorSentimentRequest
        .latest
        .withColumnIndex(3)
        .mapTo[Bearish]
        .unsafeRunSync()
        .foreach(_ foreach println)
    }
  }

  "QuandlClient.USCrashConfidenceIndexInstitutional" ignore {
    "get all data from YALE/US_CONF_INDEX_CRASH_INST" in {
      getUSCrashConfidenceIndexInstitutional().unsafeRunSync().foreach(_ foreach println)
    }
    "get latest record from YALE/US_CONF_INDEX_CRASH_INST" in {
      getUSCrashConfidenceIndexInstitutional(_.latest).unsafeRunSync().foreach(_ foreach println)
    }
  }

  "QuandlClient.USCrashConfidenceIndexIndividual" ignore {
    "get all data from YALE/US_CONF_INDEX_CRASH_INDIV" in {
      getUSCrashConfidenceIndexIndividual().unsafeRunSync().foreach(_ foreach println)
    }
    "get latest record from YALE/US_CONF_INDEX_CRASH_INDIV" in {
      getUSCrashConfidenceIndexIndividual(_.latest).unsafeRunSync().foreach(_ foreach println)
    }
  }

  "QuandlClient.USOneYearIndexInstitutional" ignore {
    "get all data from YALE/US_CONF_INDEX_1YR_INST" in {
      getUSOneYearIndexInstitutional().unsafeRunSync().foreach(_ foreach println)
    }
    "get latest record from YALE/US_CONF_INDEX_1YR_INST" in {
      getUSOneYearIndexInstitutional(_.latest).unsafeRunSync().foreach(_ foreach println)
    }
  }

  "QuandlClient.USOneYearIndexIndividual" ignore {
    "get all data from YALE/US_CONF_INDEX_1YR_INDIV" in {
      getUSOneYearIndexIndividual().unsafeRunSync().foreach(_ foreach println)
    }
    "get latest record from YALE/US_CONF_INDEX_1YR_INDIV" in {
      getUSOneYearIndexIndividual(_.latest).unsafeRunSync().foreach(_ foreach println)
    }
  }

  "QuandlClient.USValuationIndexInstitutional" ignore {
    "get all data from YALE/US_CONF_INDEX_VAL_INST" in {
      getUSValuationIndexInstitutional().unsafeRunSync().foreach(_ foreach println)
    }
    "get latest record from YALE/US_CONF_INDEX_VAL_INST" in {
      getUSValuationIndexInstitutional(_.latest).unsafeRunSync().foreach(_ foreach println)
    }
  }

  "QuandlClient.USValuationIndexIndividual" ignore {
    "get all data from YALE/US_CONF_INDEX_VAL_INDIV" in {
      getUSValuationIndexIndividual().unsafeRunSync().foreach(_ foreach println)
    }
    "get latest record from YALE/US_CONF_INDEX_VAL_INDIV" in {
      getUSValuationIndexIndividual(_.latest).unsafeRunSync().foreach(_ foreach println)
    }
  }

  "QuandlClient.TreasuryYields" ignore {
    "get all data from USTREASURY/YIELD" in {
      getTreasuryYields().unsafeRunSync().foreach(_ foreach println)
    }
    "get latest record from USTREASURY/YIELD" in {
      getTreasuryYields(_.latest).unsafeRunSync().foreach(_ foreach println)
    }
  }

  "QuandlClient TreasuryYields Spread" ignore {
    "calculate latest 10Y2Y spread" in {
      getTreasury10Y2YSpread(_.latest).unsafeRunSync().foreach(_ foreach println)
    }
    "calculate 30 days 10Y2Y spread" in {
      getTreasury10Y2YSpread(_.withLimit(30)).unsafeRunSync().foreach(_ foreach println)
    }
  }

  "QuandlClient VIX futures" ignore {
    "get latest VX1 future value" in {
      getVixDataset(VX1)(_.latest).unsafeRunSync().foreach(_ foreach println)
    }
    "get latest VX2 future value" in {
      getVixDataset(VX2)(_.latest).unsafeRunSync().foreach(_ foreach println)
    }
    "get latest VX3 future value" in {
      getVixDataset(VX3)(_.latest).unsafeRunSync().foreach(_ foreach println)
    }
    "get latest VX4 future value" in {
      getVixDataset(VX4)(_.latest).unsafeRunSync().foreach(_ foreach println)
    }
    "get latest VX5 future value" in {
      getVixDataset(VX5)(_.latest).unsafeRunSync().foreach(_ foreach println)
    }
    "get latest VX6 future value" in {
      getVixDataset(VX6)(_.latest).unsafeRunSync().foreach(_ foreach println)
    }
    "get latest VX7 future value" in {
      getVixDataset(VX7)(_.latest).unsafeRunSync().foreach(_ foreach println)
    }
    "get latest VX8 future value" in {
      getVixDataset(VX8)(_.latest).unsafeRunSync().foreach(_ foreach println)
    }
    "get latest VX9 future value" in {
      getVixDataset(VX9)(_.latest).unsafeRunSync().foreach(_ foreach println)
    }
  }

  "QuandlClient.ConsumerPriceIndex" ignore {
    "get all data from RATEINF/CPI_USA (Consumer Price Index)" in {
      getCpiUSA().unsafeRunSync().foreach(_ foreach println)
    }
    "get latest record from RATEINF/CPI_USA (Consumer Price Index)" in {
      getCpiUSA(_.latest).unsafeRunSync().foreach(_ foreach println)
    }
  }

  "QuandlClient.InflationYOY" ignore {
    "get all data from RATEINF/INFLATION_USA (Inflation YOY)" in {
      getInflationYoyUSA().unsafeRunSync().foreach(_ foreach println)
    }
    "get latest record from RATEINF/INFLATION_USA (Inflation YOY)" in {
      getInflationYoyUSA(_.latest).unsafeRunSync().foreach(_ foreach println)
    }
  }

  "QuandlClient.ExpectedInflation" ignore {
    "get all data from FRBC/EXIN (Expected Inflation)" in {
      getExpectedInflation().unsafeRunSync().foreach(_ foreach println)
    }
    "get latest record from FRBC/EXIN (Expected Inflation)" in {
      getExpectedInflation(_.latest).unsafeRunSync().foreach(_ foreach println)
    }
  }

}
