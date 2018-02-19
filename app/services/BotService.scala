package services

import javax.inject.Singleton

import info.mukel.telegrambot4s.api._
import info.mukel.telegrambot4s.api.declarative.{Commands, InlineQueries}


class EmailBot() extends TelegramBot with Polling with InlineQueries with Commands {
  override def token = "527873963:AAGuHqSC8IQmwEELUVeRptdOLy4qFqUjTg0"

  onMessage (implicit msg => reply("hello world"))
}

@Singleton
class BotService {
  new EmailBot().run()
}