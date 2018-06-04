package org.muks.bitcoining.utilities

import java.text.SimpleDateFormat
import java.util.Calendar

object CalendarCustomUtils {

  /**
    * Static date extractor method
    * @param dateFormat
    * @return
    */
  def getDate(dateFormat: String): String = {
    val format = new SimpleDateFormat(dateFormat)

    val calendarInstance = Calendar.getInstance()
    calendarInstance.add(Calendar.DATE, 0)

    format.format(calendarInstance.getTime()) // return formatted string
  }

  /**
    *
    * @param dateFormat
    * @param daysBackporting
    * @return
    */
  def getDate(dateFormat: String, daysBackporting: Int): String = {
    val format = new SimpleDateFormat(dateFormat)

    val calendarInstance = Calendar.getInstance()
    calendarInstance.add(Calendar.DATE, daysBackporting)

    format.format(calendarInstance.getTime()) // return formatted string
  }

}