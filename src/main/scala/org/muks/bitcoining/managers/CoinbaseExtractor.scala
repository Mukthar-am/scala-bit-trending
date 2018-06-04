package org.muks.bitcoining.managers

import org.apache.log4j.Logger
import org.muks.bitcoining.utilities.{CalendarCustomUtils, HttpClientExtension}

import scala.collection.mutable


class CoinbaseExtractor {
  private val logger = Logger.getLogger(classOf[CoinbaseExtractor])
  private var COINBASE_API_URL: String = ""
  private var DAY: Int = 0
  private var DATE_FORMAT = ""
  var HEADERS = mutable.HashMap[String,String]()

  def setCoinbaseApiUrl(url: String): CoinbaseExtractor = {
    this.COINBASE_API_URL = url
    this
  }

  def day(daysBackported: Int): CoinbaseExtractor = {
    this.DAY = daysBackported
    this
  }

  def setDateFormat(dateFormat:String): CoinbaseExtractor = {
    this.DATE_FORMAT = dateFormat
    this
  }


  def setRequestHeaders(headers:List[String]): CoinbaseExtractor = {

    for (header <- headers) {

      header match {

        case "CB-VERSION" =>
          HEADERS.put(header, CalendarCustomUtils.getDate(this.DATE_FORMAT, this.DAY))

        case "Content-Type" =>
          HEADERS.put("Content-Type", "application/json")

        case _ =>
          logger.info("Invalid header key.")
      }

      logger.info("Headers listing: " + this.HEADERS.toString())
    }

    this
  }



  def extract(): CoinbaseExtractor = {
    logger.info("Extracting BitCoin spot price")
    val date = CalendarCustomUtils.getDate(this.DATE_FORMAT, this.DAY)
    logger.info("Date: " + date + ", Day as: " + this.DAY + ", With coinbase Url: " + this.COINBASE_API_URL)


    val response = new HttpClientExtension().requestWithHeaders(this.COINBASE_API_URL, this.HEADERS)
    logger.info("Response: \n" + response )


    this
  }
}
