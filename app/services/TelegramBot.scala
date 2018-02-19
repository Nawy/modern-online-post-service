package services

import javax.inject.Singleton

import org.telegram.telegrambots.api.methods.send.SendMessage
import org.telegram.telegrambots.api.objects.Update
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.exceptions.TelegramApiException
import play.api.Logger

@Singleton
class BotService {


}

class TelegramBot() extends TelegramLongPollingBot {
  override def getBotToken: String = "527873963:AAGuHqSC8IQmwEELUVeRptdOLy4qFqUjTg0"

  override def getBotUsername: String = "email_post_it_bot"

  override def onUpdateReceived(update: Update): Unit = {
    if (!update.hasMessage || !update.getMessage.hasText) return
    val message: SendMessage = new SendMessage().setChatId(update.getMessage.getChatId).setText(update.getMessage.getText)
    try {
      execute(message)
    } catch {
      case exception: TelegramApiException => Logger.error("can't send bot message!", exception)
      case _ => Logger.error("unknown error happen!")
    }
  }
}
