package controllers

import javax.inject._

import model._
import play.api.libs.json._
import play.api.mvc._
import reactivemongo.play.json.BSONFormats._
import services.MessageService

@Singleton
class MessageController @Inject()(cc: ControllerComponents, messageService: MessageService) extends AbstractController(cc) {

  import myutils.JsonParsers._
  implicit val createMessageDtoFormatter: Format[CreateMessageDto] = Json.format[CreateMessageDto]

  def send(): Action[JsValue] = Action(parse.json) { request =>
    request.body.validate[CreateMessageDto].asOpt
      .map(messageDto => {
        val message = messageDto.makeMessage
        val ownerEmail = message.senderEmail
        val chatId = message.recipientEmail

        val userMessages: UserMessages = messageService.sendMessage(message, ownerEmail, chatId)

        Ok(Json.obj(
          "messagesId" -> userMessages._id,
          "chatId" -> chatId,
          "messageId" -> message._id
        ))
      })
      .getOrElse(BadRequest)
  }

  def get(email: String, opponent: String) = Action {

    messageService.getChat(email, opponent)
      .filter(_.isEmpty)
      .map(chat => Ok(Json.toJson(chat)))
      .getOrElse(NotFound("[]"))
  }

  case class CreateMessageDto(text: String, senderEmail: String, recipientEmail: String) {
    def makeMessage = Message(text = text, senderEmail = senderEmail, recipientEmail = recipientEmail)
  }
}
