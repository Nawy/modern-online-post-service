package controllers

import java.time.LocalDateTime
import javax.inject._

import model.User
import play.api.libs.json._
import play.api.mvc._
import utils.JsonParsers
import reactivemongo.play.json.BSONFormats._

@Singleton
class UserController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  implicit val localDateTimeFormatter: Format[LocalDateTime] = JsonParsers.LocalDateTimeFormatter
  implicit val createUserDtoReads: Format[CreateUserDto] = Json.format[CreateUserDto]
  implicit val userDtoRFormat: Format[User] = Json.format[User]

  def insert() = Action(parse.json) { request =>
    request.body.validate[CreateUserDto].asOpt
      .map(userDto => {
        val user = userDto.toUser
        UserDAO.insert(user)
        Ok(Json.obj("id" -> user._id))
      })
      .getOrElse(BadRequest)
  }

  def save() = Action(parse.json) { request =>
    request.body.validate[User].asOpt
      .map(user => {
        UserDAO.save(user)
        Ok(Json.obj("id" -> user._id))
      })
      .getOrElse(BadRequest)
  }

  def get(id: String) = Action {
    UserDAO.findOneById(id)
      .map(user => Ok(Json.toJson(user)))
      .getOrElse(NotFound)
  }

  def delete(id: String) = Action {
    UserDAO.removeById(id)
    Ok
  }
}

case class CreateUserDto(email: String, password: String) {
  def toUser: User = User(email = email, password = password)
}
