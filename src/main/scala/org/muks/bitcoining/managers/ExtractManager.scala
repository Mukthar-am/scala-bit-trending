package org.muks.bitcoining.managers

import java.io.{File, FileInputStream}
import java.util.Properties

import org.apache.log4j.Logger

import scala.collection.JavaConverters._

object ExtractManager {
  val logger = Logger.getLogger(ExtractManager.getClass.getName)

  def main(args: Array[String]) {
    if (args.length == 0)
      println("Please provide the trends.properties file path.")

    val filename = args(0) // props file name

    val prop = ExtractManager.readProperties(filename)
    prop.entrySet().asScala.foreach {
      (entry) => {
        //sys.props += ((entry.getKey.asInstanceOf[String], entry.getValue.asInstanceOf[String]))
        val str = String.format("%s=%s", entry.getKey.asInstanceOf[String], entry.getValue.asInstanceOf[String])
        logger.debug(str)
      }
    }


//    //var dateFormat = "yyyy-MM-dd"
    //var headersListing = prop.getProperty("coinbase.api.header").asInstanceOf[List[String]] //.toSeq //
    var headersListing = prop.getProperty("coinbase.api.header").asInstanceOf[String].split(",").toList


    val coinbaseExtractor = new CoinbaseExtractor()
    coinbaseExtractor
      .setCoinbaseApiUrl(prop.getProperty("coinbase.api.spot"))
      .setDateFormat(prop.getProperty("coinbase.api.date.format"))
      .day(prop.getProperty("coinbase.api.date.backport").toInt)
      .setRequestHeaders(headersListing)
      .extract()
  }


  /**
    * Read propertis file and transform into props object
    *
    * @param filename - file name
    * @return - Properties object
    */
  def readProperties(filename: String): Properties = {
    val prop = new Properties()
    val propsFile = new File(filename)
    try {
      prop.load(new FileInputStream(propsFile))
    } catch {
      case e:
        Exception => logger.error(e)
    }

    prop
  }
}
