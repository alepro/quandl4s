# quandl4s
Scala client for Quandl.com APIs


## How to use ?
There are several already defined APIs in `alepro.quandl4s.client.apis` such as

[AAII Investor Sentiment Data](https://www.quandl.com/data/AAII/AAII_SENTIMENT-AAII-Investor-Sentiment-Data)

[United States Crash Confidence Index Data - Institutional](https://www.quandl.com/data/YALE/US_CONF_INDEX_CRASH_INST-Stock-Market-Confidence-Indices-United-States-Crash-Confidence-Index-Data-Institutional)

[United States Crash Confidence Index Data - Individual](https://www.quandl.com/data/YALE/US_CONF_INDEX_CRASH_INDIV-Stock-Market-Confidence-Indices-United-States-Crash-Confidence-Index-Data-Individual)

[United States One Year Index Data - Institutional](https://www.quandl.com/data/YALE/US_CONF_INDEX_1YR_INST-Stock-Market-Confidence-Indices-United-States-One-Year-Index-Data-Institutional)

[United States One Year Index Data - Individual](https://www.quandl.com/data/YALE/US_CONF_INDEX_1YR_INDIV-Stock-Market-Confidence-Indices-United-States-One-Year-Index-Data-Individual)

[United States Valuation Index Data - Institutional](https://www.quandl.com/data/YALE/US_CONF_INDEX_VAL_INST-Stock-Market-Confidence-Indices-United-States-Valuation-Index-Data-Institutional)

[United States Valuation Index Data - Individual](https://www.quandl.com/data/YALE/US_CONF_INDEX_VAL_INDIV-Stock-Market-Confidence-Indices-United-States-Valuation-Index-Data-Individual)

[Treasury Yield Curve Rates](https://www.quandl.com/data/USTREASURY-US-Treasury)

For example, you can get all items from `Treasury Yield Curve Rates` 
```scala
import alepro.quandl4s.client.apis._

val yields: IO[Either[String, List[TreasuryYields]]] = getTreasuryYields()
```
To get latest treasury yields: 
```scala
val latestYields = getTreasuryYields(_.latest)
```

More examples there `alepro.quandl4s.client.QuandlClientExamples`

### How to call arbitrary dataset ?
To get data from arbitrary dataset, follow the steps:
* Define data model as case class or tuple;
* Provide `CSVParser[_]` instance for you model (should be derived automatically for case classes and typles)
* Build `DatasetRequest` (at least specify quandl code and API key)

The `QuandlClientExamples` has an example for getting Bearish sentiment values from `AAII Investor Sentiment Data`  

```scala
type Bearish = (LocalDate, Double)
implicit val parser = CSVParser[Bearish]
DatasetRequest("AAII/AAII_SENTIMENT", apiKey.key)
  .latest
  .withColumnIndex(3)
  .mapTo[Bearish]
  .unsafeRunSync()
  .foreach(_ foreach println)
```





