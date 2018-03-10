package controllers

import javax.inject._

import play.api.mvc._
import services.MessageService


@Singleton
class MessageController @Inject()(cc: ControllerComponents, messageService: MessageService) extends AbstractController(cc) {

/*  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }*/

}
