package services

import javax.inject.Inject

import model.{Message, UserMessages}
import repository.MessageRepository

class MessageService @Inject()(messageRepository: MessageRepository) {

  def sendMessage(message: Message, ownerEmail: String, chatId: String): UserMessages = {
    val userMessages: UserMessages = messageRepository.get(ownerEmail).getOrElse(UserMessages(ownerEmail = ownerEmail))

    val messages: List[Message] = userMessages.messages.getOrElse(ownerEmail, List())
    userMessages.messages = userMessages.messages + (chatId -> (message :: messages)) //add to messages one message and put it in map
    messageRepository.save(userMessages)
    userMessages
  }

  def getChat(email: String, opponent: String): Option[List[Message]] = {
     messageRepository.get(email)
       .map(userMessages => userMessages.messages.get(opponent))
       .getOrElse(None)
  }

}
