package controllers

import javax.inject._

import model.UserInfo
import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.mvc._
import services.UserService
import utils.StringUtils

@Singleton
class UserController @Inject()(cc: ControllerComponents, userService: UserService) extends AbstractController(cc) {

  implicit val createUserDtoReads: Reads[CreateUserDto] = (
    (JsPath \ "email").read[String] and
      (JsPath \ "password").read[String]
    ) (CreateUserDto.apply _)

  implicit val userDtoReads: Format[UserDto] = (
    (JsPath \ "id").formatNullable[Long] and
      (JsPath \ "email").format[String] and
      (JsPath \ "password").format[String] and
      (JsPath \ "phoneNumber").formatNullable[String] and
      (JsPath \ "fullName").formatNullable[String]
    ) (UserDto.apply, unlift(UserDto.unapply))

  def insert() = Action(parse.json) { implicit request =>

    request.body.validate[CreateUserDto].fold(
      errors => BadRequest(Json.obj("status" -> "can't parse object", "message" -> JsError.toJson(errors))),
      createUserDto => {
        if (StringUtils.isBlank(createUserDto.email) || StringUtils.isBlank(createUserDto.password)) BadRequest("email or password can't be blank")
        val id = userService.insert(createUserDto.toUserInfo)
        Ok(Json.obj("id" -> id))
      }
    )
  }

  def update() = Action(parse.json) { implicit request =>
    request.body.validate[UserDto].fold(
      errors => BadRequest(Json.obj("status" -> "can't parse object", "message" -> JsError.toJson(errors))),
      userDto => {
        userService.update(userDto.toUserInfo)
        Ok(Json.obj("status" -> "OK"))
      }
    )
  }

  def get(id: Long) = Action { implicit request =>
    val user: UserInfo = userService.get(id)
    Ok(Json.toJson(UserDto.toUserDto(user)))
  }

}

case class CreateUserDto(var email: String, var password: String) {
  def toUserInfo: UserInfo = UserInfo(None, email, password, null, null)
}

case class UserDto(
                    var id: Option[Long],
                    var email: String,
                    var password: String,
                    var phoneNumber: Option[String],
                    var fullName: Option[String]
                  ) {
  def toUserInfo: UserInfo = UserInfo(id, email, password, phoneNumber.orNull, fullName.orNull)
}

object UserDto {
  def toUserDto(userInfo: UserInfo): UserDto =
    UserDto(
      userInfo.id,
      userInfo.email,
      userInfo.password,
      Option(userInfo.phoneNumber),
      Option(userInfo.fullName)
    )
}
