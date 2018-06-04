package org.muks.bitcoining.utilities

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.databind.{JsonNode, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule



object JsonUtils {
  val mapper: ObjectMapper = new ObjectMapper()


  def convertToJson(strContent:String): JsonNode = {
    mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true)
    mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
    //mapper.configure(JsonParser.Feature.ALLOW_MISSING_VALUES, true)
    mapper.configure(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS, true)

    val rootRead: JsonNode = mapper.readTree(strContent)
    rootRead
  }


  /**
    * By example
    */
  def createJsonNode(): Unit = {
    // needed for list, map and array
    mapper.registerModule(DefaultScalaModule)

    val root: ObjectNode = mapper.createObjectNode()

    // simple value
    root.put("simple", "value")
  }


  def beautify(jsonNode: JsonNode): String = {
    mapper.writerWithDefaultPrettyPrinter.writeValueAsString(jsonNode)
  }


}