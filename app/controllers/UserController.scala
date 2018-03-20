package controllers

import javax.inject._

import model.{User, UserDAO}
import org.bson.types.ObjectId
import play.api.libs.json._
import play.api.mvc._

@Singleton
class UserController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  implicit val createUserDtoReads: Format[CreateUserDto] = Json.format[CreateUserDto]
  implicit val userDtoReads: Format[User] = Json.format[User]

  def insert() = Action(parse.json) { request =>
    request.body.validate[CreateUserDto].asOpt
      .map(userDto => Ok(UserDAO.insert(userDto.toUser)))
      .getOrElse(BadRequest)
  }

  def save() = Action(parse.json) { request =>
    request.body.validate[User].asOpt
      .map(user => {
        UserDAO.save(user)
        Ok(user._id)
      })
      .getOrElse(BadRequest)
  }

  def get(id: String) = Action {
    UserDAO.findOneById(new ObjectId(id))
      .map(user => Ok(Json.toJson(user)))
      .getOrElse(BadRequest)
  }

  def delete(id: String) = Action {
    UserDAO.removeById(new ObjectId(id))
    Ok
  }
}

case class CreateUserDto(var email: String, var password: String) {
  def toUser: User = User(email = email, password = password)
}
