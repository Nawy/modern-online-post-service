package controllers

import javax.inject._

import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.mvc._

@Singleton
class UserController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  implicit val locationReads: Reads[CreateUserDto] = (
    (JsPath \ "email").read[String] and
      (JsPath \ "password").read[String]
    ) (CreateUserDto.apply _)


  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def insert(): Action[JsValue] = Action(parse.json) { implicit request =>
    request.body.validate[CreateUserDto].fold(
      errors => BadRequest(Json.obj("status" -> "can't parse object", "message" -> JsError.toJson(errors))),
      createUserDto => Ok(Json.obj("status" -> "OK", "message" -> ("Place '" + createUserDto.email + "' saved.")))
    )
  }

  def update() = Action(parse.json) { implicit request =>
    /*val body: String = request.body
    val userDto: UserDto = Json.
    if (userDto.email == null || userDto.email.isEmpty) BadRequest("user Email must not be blank")
    if (userDto.password == null || userDto.password.isEmpty) BadRequest("user Email must not be blank")*/
    Ok
  }


  def get(): UserDto = {}

}

case class CreateUserDto(var email: String, var password: String)

case class UserDto(
                    var id: Long,
                    var email: String,
                    var password: String,
                    var phoneNumber: String,
                    var fullName: String
                  )
