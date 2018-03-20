package controllers

import javax.inject._

import model.{MessageType, _}
import play.api.libs.json.{Format, Json}
import play.api.mvc._

import scala.collection.mutable.ArrayBuffer

@Singleton
class MessageController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  implicit val createMessageDtoFormatter: Format[CreateMessageDto] = Json.format[CreateMessageDto]


  def insert() = Action(parse.json) { request =>
    request.body.validate[CreateMessageDto].asOpt
      .map(messageDto => {
        val message = messageDto.toMessage
        val userMessages = UserMessages(messages = Map("test" -> ArrayBuffer(message)))
        UserMessagesDAO.insert(userMessages)
        Ok(userMessages._id)
      })
      .getOrElse(BadRequest)
  }

  /*
    def save() = Action(parse.json) { request =>
      request.body.validate[User].asOpt
        .map(user => {
          UserDAO.save(user)
          Ok(Json.toJson(user._id))
        })
        .getOrElse(BadRequest)
    }
  */

/*  def get(id: String) = Action {
    UserMessagesDAO.findOneById(id)
      .map(userMessages => Ok(Json.toJson(userMessages)))
      .getOrElse(NotFound)
  }

  def delete(id: String) = Action {
    UserMessagesDAO.removeById(id)
    Ok
  }*/

  case class CreateMessageDto(messageType: String, text: String) {
    def toMessage = Message(messageType = messageType, text = text)
  }
}
