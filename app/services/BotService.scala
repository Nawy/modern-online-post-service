package services

import javax.inject.Singleton

import info.mukel.telegrambot4s.api._
import info.mukel.telegrambot4s.api.declarative.{Commands, InlineQueries}
import info.mukel.telegrambot4s.methods.ParseMode

import scala.collection.mutable.ListBuffer


class EmailBot(val token: String) extends TelegramBot with Polling with InlineQueries with Commands {

  val CHECK_MAIL_DEFAULT_VALUE = 5
  val messages = new ListBuffer[String]()
  val commandsInfo: String = "You can use some of these commands:\n\n" +
    "/help - commands list\n" +
    "/send (text) - example: /send hello kate!\n" +
    "/check - check recent emails\n" +
    "/amount - find the total number of messages\n"

  onMessage { implicit msg =>
    msg.text.get match {
      case "/help" => reply(text = commandsInfo)
      case "/start" => reply(
        text = "<b align=\"center\">Bot for email!</b>\n" +
          "<pre> We create bot that able to send and receive some emails from your friends </pre>\n\n" + commandsInfo,
        parseMode = Some(ParseMode.HTML)
      )
      case message if message.startsWith("/send") =>
        getCommandValue(message) match {
          case null => reply("ERROR!!! no message!")
          case s if s.trim.length == 0 => reply("ERROR!!! no message!")
          case s if s.trim.length > 0 => messages += s.trim; reply("mail sent")
        }
      case message if message.startsWith("/check") =>
        if (messages.isEmpty) reply("no messages")
        val amount: Int = getCheckMailMessagesNumber(getCommandValue(message))
        messages.takeRight(amount).reverse.zipWithIndex.foreach { case (storedMessage, index) => reply(index + 1 + ") " + storedMessage) }
      case "/amount" => reply(messages.length.toString)
      case _ => reply("unknown command, try /help")
    }
  }

  def getCheckMailMessagesNumber(s: String): Int = {
    try {
      s.toInt
    } catch {
      case _: Exception => CHECK_MAIL_DEFAULT_VALUE
    }
  }

  // get value after command like: /command VALUE
  def getCommandValue(message: String): String = {
    val splittedMessage: Array[String] = message.split(" ", 2)
    if (splittedMessage.length < 2) null else splittedMessage(1)
  }
}

@Singleton
class BotService {
  new EmailBot("527873963:AAGuHqSC8IQmwEELUVeRptdOLy4qFqUjTg0").run()
}