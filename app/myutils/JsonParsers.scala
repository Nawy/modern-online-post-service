package myutils

import java.time.LocalDateTime

import model.{Message, User, UserMessages}
import play.api.libs.json.{Format, JsResult, JsValue, Json}


object JsonParsers {
  import reactivemongo.play.json.BSONFormats._

  implicit val LocalDateTimeFormatter: Format[LocalDateTime] = new Format[LocalDateTime] {
    override def reads(json: JsValue): JsResult[LocalDateTime] = json.validate[String].map(LocalDateTime.parse)
    override def writes(o: LocalDateTime): JsValue = Json.toJson(o.toString)
  }

  implicit val userDtoRFormat: Format[User] = Json.format[User]
  implicit val messageFormatter: Format[Message] = Json.format[Message]
  implicit val userMessagesFormatter: Format[UserMessages] = Json.format[UserMessages]
}
