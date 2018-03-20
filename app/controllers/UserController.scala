package controllers

import javax.inject._

import model.{User, UserDAO}
import play.api.libs.json._
import play.api.mvc._

@Singleton
class UserController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  implicit val createUserDtoReads: Format[CreateUserDto] = Json.format[CreateUserDto]
  implicit val userDtoRFormat: Format[User] = Json.format[User]

  def insert() = Action(parse.json) { request =>
    request.body.validate[CreateUserDto].asOpt
      .map(userDto => {
        val user = userDto.toUser
        UserDAO.insert(user)
        Ok(user._id)
      })
      .getOrElse(BadRequest)
  }

  def save() = Action(parse.json) { request =>
    request.body.validate[User].asOpt
      .map(user => {
        UserDAO.save(user)
        Ok(Json.toJson(user._id))
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
