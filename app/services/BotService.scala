package services

import javax.inject.Singleton

import info.mukel.telegrambot4s.api._
import info.mukel.telegrambot4s.api.declarative.{Callbacks, Commands, InlineQueries}
import info.mukel.telegrambot4s.models.{InlineKeyboardButton, InlineKeyboardMarkup}

import scala.collection.mutable.ListBuffer


class EmailBot(val token: String) extends TelegramBot with Polling with InlineQueries with Commands with Callbacks {

  val messages = new ListBuffer[String]()

  val keyboard = InlineKeyboardMarkup.singleRow(
    List(
      InlineKeyboardButton.callbackData("Test1", "help"),
      InlineKeyboardButton.callbackData("Test2", "Test2_Worked"),
      InlineKeyboardButton.callbackData("Test3", "Test3_Worked")
    )
  )

  onCallbackQuery(implicit callbackQuery => ackCallback(Some("button pushed")))

  onMessage { implicit msg =>
    msg.text.get match {
      case "/help" => reply("/start, /send_mail, /check_mail, /mails_amount, /btn")
      case "/start" => reply("/start, /send_mail, /check_mail, /mails_amount, /btn")
      case message if message.startsWith("/send_mail") =>
        val splittedMessage: Array[String] = message.split(" ", 2)
        if (splittedMessage.length < 2) reply("no message")
        messages += splittedMessage(1)
        reply("mail send")
      case "/check_mail" =>
        if (messages.isEmpty) reply("no messages")

        messages.foreach(reply(_))
      case "/mails_amount" => reply(messages.length.toString)
      case "/btn" => reply(text = "Email keyboard", replyMarkup = Some(keyboard))
      case _ => reply("unknown command, try /help or /start")
    }
  }
}

@Singleton
class BotService {
  new EmailBot("527873963:AAGuHqSC8IQmwEELUVeRptdOLy4qFqUjTg0").run()
}