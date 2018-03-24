package utils

import java.time.LocalDateTime

import play.api.libs.json.{Format, JsResult, JsValue, Json}

object JsonParsers {

  val LocalDateTimeFormatter: Format[LocalDateTime] = new Format[LocalDateTime] {
    override def reads(json: JsValue): JsResult[LocalDateTime] = json.validate[String].map(LocalDateTime.parse)

    override def writes(o: LocalDateTime): JsValue = Json.toJson(o.toString)
  }
}
