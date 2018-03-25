package controllers

import javax.inject._

import model.User
import play.api.libs.json._
import play.api.mvc._
import reactivemongo.bson.BSONObjectID
import reactivemongo.play.json.BSONFormats._
import repository.UserRepository

@Singleton
class UserController @Inject()(cc: ControllerComponents, userRepository: UserRepository) extends AbstractController(cc) {

  import myutils.JsonParsers._

  implicit val createUserDtoReads: Format[CreateUserDto] = Json.format[CreateUserDto]

  def save() = Action(parse.json) { request =>
    request.body.validate[User].asOpt
      .map(user => {
        userRepository.save(user)
        Ok(Json.obj("id" -> user._id))
      })
      .getOrElse(BadRequest)
  }

  def get(id: String) = Action {
    userRepository.get(BSONObjectID.parse(id).get)
      .map(user => Ok(Json.toJson(user)))
      .getOrElse(NotFound)
  }

  def delete(id: String) = Action {
    userRepository.remove(BSONObjectID.parse(id).get)
    Ok
  }
}

case class CreateUserDto(email: String, password: String) {
  def toUser: User = User(email = email, password = password)
}
