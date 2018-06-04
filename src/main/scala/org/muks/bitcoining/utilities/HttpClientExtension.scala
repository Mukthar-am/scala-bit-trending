package org.muks.bitcoining.utilities

import java.nio.charset.StandardCharsets

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.{JsonNode, ObjectMapper}
import com.google.gson.Gson
import jdk.nashorn.internal.parser.JSONParser
import org.apache.commons.io.IOUtils
import org.apache.http.client.methods.{CloseableHttpResponse, HttpGet}
import org.apache.http.impl.client.{BasicResponseHandler, DefaultHttpClient}
import org.apache.http.params.HttpConnectionParams
import org.apache.http.protocol.HttpContext
import org.apache.http.{HttpRequest, HttpRequestInterceptor}
import org.apache.log4j.Logger

import scala.collection.mutable.HashMap





/**
  * HTTP request handling
  */

class HttpClientExtension {
  //val logger = Logger.getRootLogger
  //val logger = Logger.getLogger(HttpClientExtension.getClass.getName)
  val logger = Logger.getLogger(classOf[HttpClientExtension])
  private val defaultConnTimeout = 10
  private val defaultSocketTimeout = 10


  private def buildHttpClient(connectionTimeout: Int, socketTimeout: Int):
  DefaultHttpClient = {
    val httpClient = new DefaultHttpClient
    val httpParams = httpClient.getParams
    HttpConnectionParams.setConnectionTimeout(httpParams, connectionTimeout)
    HttpConnectionParams.setSoTimeout(httpParams, socketTimeout)
    httpClient.setParams(httpParams)

    httpClient    // this is the return statement in scala
  }

  private def buildHttpClient():
  DefaultHttpClient = {
    val httpClient = new DefaultHttpClient
    val httpParams = httpClient.getParams
    HttpConnectionParams.setConnectionTimeout(httpParams, defaultConnTimeout)
    HttpConnectionParams.setSoTimeout(httpParams, defaultSocketTimeout)
    httpClient.setParams(httpParams)

    httpClient    // this is the return statement in scala
  }


  def simpleRequest(url: String): String = {
    val client = buildHttpClient()
    simpleRequest(client, url)
  }

  def simpleRequest(client: DefaultHttpClient, url: String): String = {
    try {
      val get = new HttpGet(url)

      logger.info("executing request " + get.getURI);

      val responseHandler = new BasicResponseHandler
      client.execute(get, responseHandler)
    } finally {
      client.getConnectionManager.shutdown()
    }
  }

  def requestWithHeaders(url: String, headers: HashMap[String, String]): CloseableHttpResponse = {
    val client = new DefaultHttpClient

    var response: CloseableHttpResponse = null

    try {
      client.addRequestInterceptor(new HttpRequestInterceptor {
        def process(request: HttpRequest, context: HttpContext) {
          for ((k, v) <- headers) {
            request.addHeader(k, v)
          }
        }
      })


      //simpleRequest(client, url)

      val getRequest = new HttpGet(url)


      // send the post request
      response = client.execute(getRequest)

      logger.debug("=== Response headers ===")
      response.getAllHeaders.foreach(arg => logger.debug("\n" + arg))
      logger.info("Status: " + response.getStatusLine.toString)


      val entity = response.getEntity()
      var content = ""
      if (entity != null) {
        val inputStream = entity.getContent()
        content = io.Source.fromInputStream(inputStream).getLines.mkString
        inputStream.close
      }
      client.getConnectionManager().shutdown()


      val jNode = JsonUtils.convertToJson(content)
      println("Beautified JsonNode:\n" + JsonUtils.beautify(jNode))
      logger.info("Beautified JsonNode:\n" + JsonUtils.beautify(jNode))

    }

    response

  }


}


object MainObject {
  val logger = Logger.getLogger(MainObject.getClass.getName)

  import java.io.{File, FileInputStream}
  import java.util.Properties
  import scala.collection.JavaConverters._

  def main(args: Array[String]) {
//    val response = HttpClientExtension.requestWithHeaders("http://tools.ietf.org/rfc/rfc3629.txt", Map("SUBJECT_DN" -> "TEST_DN"))
//    logger.info(response)
//    println(response)

    val prop = new Properties()
    val propsFile = new File("/Users/mukthara/Data/git/personal/bitcoin-trending/configs/trends.properties")
    if (propsFile.exists()) {
      prop.load(new FileInputStream(propsFile))
    } else {
      //fillFromEnv(prop)
      println("Cannot load file:")
    }
    prop.entrySet().asScala.foreach {
      (entry) => {
        //sys.props += ((entry.getKey.asInstanceOf[String], entry.getValue.asInstanceOf[String]))
        val str = String.format("%s=%s", entry.getKey.asInstanceOf[String], entry.getValue.asInstanceOf[String])
        println(str)
      }
    }


  }
}
