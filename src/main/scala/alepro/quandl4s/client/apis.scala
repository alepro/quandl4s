package alepro.quandl4s.client

import alepro.quandl4s.csv.CSVParser
import cats.effect.IO

import java.time.LocalDate

object apis {

  /*
   *  InvestorSentimentData.
   *  https://www.quandl.com/api/v3/datasets/AAII/AAII_SENTIMENT.csv?api_key=your_quandl_key
   */

  // Model
  case class InvestorSentimentData(date: LocalDate,
                                   bullish: Option[Double],
                                   neutral: Option[Double],
                                   bearish: Option[Double],
                                   total: Option[Double],
                                   bullish8WeekMovAvg: Option[Double],
                                   bullBearSpread: Option[Double],
                                   bullishAverage: Option[Double],
                                   bullishAveragePlusStDev: Option[Double],
                                   bullishAverageMinusStDev: Option[Double],
                                   sp500WeeklyHigh: Option[Double],
                                   sp500WeeklyLow: Option[Double],
                                   sp500WeeklyClose: Option[Double])

  // Base request
  def investorSentimentRequest(implicit apiKey: ApiKey): DatasetRequest =
    DatasetRequest("AAII/AAII_SENTIMENT", apiKey.key)

  // Basic way to build request and get results as InvestorSentimentData list.
  def getInvestorSentimentDataset(f: DatasetRequest => DatasetRequest = identity)
                                 (implicit apiKey: ApiKey): IO[Either[String, List[InvestorSentimentData]]] = {
    implicit val parser = CSVParser[InvestorSentimentData]
    f(investorSentimentRequest).mapTo[InvestorSentimentData]
  }


  /*
   *  United States Crash Confidence Index Data - Institutional
   *  https://www.quandl.com/api/v3/datasets/YALE/US_CONF_INDEX_CRASH_INST.csv?api_key=your_quandl_key
   */

  // Model
  case class USCrashConfidenceIndex(date: LocalDate,
                                    value: Double,
                                    stdError: Double)
  // Base request
  def usCrashConfidenceIndexInstitutionalRequest(implicit apiKey: ApiKey): DatasetRequest =
    DatasetRequest("YALE/US_CONF_INDEX_CRASH_INST", apiKey.key)

  // Basic way to build request and get results as USCrashConfidenceIndex list.
  def getUSCrashConfidenceIndexInstitutional(f: DatasetRequest => DatasetRequest = identity)
                                            (implicit apiKey: ApiKey): IO[Either[String, List[USCrashConfidenceIndex]]] = {
    implicit val parser = CSVParser[USCrashConfidenceIndex]
    f(usCrashConfidenceIndexInstitutionalRequest).mapTo[USCrashConfidenceIndex]
  }


  /*
   *  United States Crash Confidence Index Data - Individual
   *  https://www.quandl.com/api/v3/datasets/YALE/US_CONF_INDEX_CRASH_INDIV.csv?api_key=your_quandl_key
   */

  // Base request
  def usCrashConfidenceIndexIndividualRequest(implicit apiKey: ApiKey): DatasetRequest =
    DatasetRequest("YALE/US_CONF_INDEX_CRASH_INDIV", apiKey.key)

  // Basic way to build request and get results as USCrashConfidenceIndex list.
  def getUSCrashConfidenceIndexIndividual(f: DatasetRequest => DatasetRequest = identity)
                                         (implicit apiKey: ApiKey): IO[Either[String, List[USCrashConfidenceIndex]]] = {
    implicit val parser = CSVParser[USCrashConfidenceIndex]
    f(usCrashConfidenceIndexIndividualRequest).mapTo[USCrashConfidenceIndex]
  }


  /*
  *  United States One Year Index Data - Institutional
  *  https://www.quandl.com/api/v3/datasets/YALE/US_CONF_INDEX_1YR_INST.csv?api_key=your_quandl_key
  */

  // Model
  case class USOneYearIndex(date: LocalDate,
                            value: Double,
                            stdError: Double)
  // Base request
  def usOneYearIndexInstitutionalRequest(implicit apiKey: ApiKey): DatasetRequest =
    DatasetRequest("YALE/US_CONF_INDEX_1YR_INST", apiKey.key)

  // Basic way to build request and get results as USOneYearIndex list.
  def getUSOneYearIndexInstitutional(f: DatasetRequest => DatasetRequest = identity)
                                            (implicit apiKey: ApiKey): IO[Either[String, List[USOneYearIndex]]] = {
    implicit val parser = CSVParser[USOneYearIndex]
    f(usOneYearIndexInstitutionalRequest).mapTo[USOneYearIndex]
  }


  /*
  *  United States One Year Index Data - Individual
  *  https://www.quandl.com/api/v3/datasets/YALE/US_CONF_INDEX_1YR_INDIV.csv?api_key=your_quandl_key
  */

  // Base request
  def usOneYearIndexIndividualRequest(implicit apiKey: ApiKey): DatasetRequest =
    DatasetRequest("YALE/US_CONF_INDEX_1YR_INDIV", apiKey.key)

  // Basic way to build request and get results as USOneYearIndex list.
  def getUSOneYearIndexIndividual(f: DatasetRequest => DatasetRequest = identity)
                                    (implicit apiKey: ApiKey): IO[Either[String, List[USOneYearIndex]]] = {
    implicit val parser = CSVParser[USOneYearIndex]
    f(usOneYearIndexIndividualRequest).mapTo[USOneYearIndex]
  }

  /*
  *  United States Valuation Index Data - Institutional
  *  https://www.quandl.com/api/v3/datasets/YALE/US_CONF_INDEX_VAL_INST.csv?api_key=your_quandl_key
  */

  // Model
  case class USValuationIndex(date: LocalDate,
                              value: Double,
                              stdError: Double)
  // Base request
  def usValuationIndexInstitutionalRequest(implicit apiKey: ApiKey): DatasetRequest =
    DatasetRequest("YALE/US_CONF_INDEX_VAL_INST", apiKey.key)

  // Basic way to build request and get results as USValuationIndex list.
  def getUSValuationIndexInstitutional(f: DatasetRequest => DatasetRequest = identity)
                                    (implicit apiKey: ApiKey): IO[Either[String, List[USValuationIndex]]] = {
    implicit val parser = CSVParser[USValuationIndex]
    f(usValuationIndexInstitutionalRequest).mapTo[USValuationIndex]
  }

  /*
  * United States Valuation Index Data - Individual
  * https://www.quandl.com/api/v3/datasets/YALE/US_CONF_INDEX_VAL_INDIV.csv?api_key=your_quandl_key
  */

  // Base request
  def usValuationIndexIndividualRequest(implicit apiKey: ApiKey): DatasetRequest =
    DatasetRequest("YALE/US_CONF_INDEX_VAL_INDIV", apiKey.key)

  // Basic way to build request and get results as USValuationIndex list.
  def getUSValuationIndexIndividual(f: DatasetRequest => DatasetRequest = identity)
                                      (implicit apiKey: ApiKey): IO[Either[String, List[USValuationIndex]]] = {
    implicit val parser = CSVParser[USValuationIndex]
    f(usValuationIndexIndividualRequest).mapTo[USValuationIndex]
  }

  /*
  * Treasury Yield Curve Rates
  * https://www.quandl.com/api/v3/datasets/USTREASURY/YIELD.csv
  */

  // Model
  case class TreasuryYields(date: LocalDate,
                            m1: Option[Double],
                            m2: Option[Double],
                            m3: Option[Double],
                            m6: Option[Double],
                            y1: Option[Double],
                            y2: Option[Double],
                            y3: Option[Double],
                            y5: Option[Double],
                            y7: Option[Double],
                            y10: Option[Double],
                            y20: Option[Double],
                            y30: Option[Double])

  // Base request
  def treasuryYieldsRequest(implicit apiKey: ApiKey): DatasetRequest =
    DatasetRequest("USTREASURY/YIELD", apiKey.key)

  // Basic way to build request and get results as TreasuryYields list.
  def getTreasuryYields(f: DatasetRequest => DatasetRequest = identity)
                                   (implicit apiKey: ApiKey): IO[Either[String, List[TreasuryYields]]] = {
    implicit val parser = CSVParser[TreasuryYields]
    f(treasuryYieldsRequest).mapTo[TreasuryYields]
  }

  def getTreasury10Y2YSpread(f: DatasetRequest => DatasetRequest = identity)
                            (implicit apiKey: ApiKey): IO[Either[String, List[(LocalDate, Option[Double])]]] = {
    getTreasuryYields(f).map(_.map(_.map { e =>
      val spread = for {
        y10 <- e.y10
        y2 <- e.y2
      } yield y10 - y2
      (e.date, spread)
    }))

  }

}
