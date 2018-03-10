package services

import javax.inject._

import model.Message

@Singleton
class MessageService {

  def send(senderId: Long, dialog: String) = {}

  def get(receiverId: Long, messageId: Long, dialog: String): Message = null

  def getRecent(receiverId: Long, amount: Int, dialog: String): List[Message] = Nil

}
