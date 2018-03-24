package services

import model.{Message, UserMessages, UserMessagesDAO, UserMessagesQueryObject}

class MessageService {

  def sendMessage(message: Message, ownerEmail: String, chatId: String): UserMessages = {
    val userMessages: UserMessages = UserMessagesDAO
      .findOne(UserMessagesQueryObject(ownerEmail = Some(ownerEmail)))
      .getOrElse(UserMessages(ownerEmail = ownerEmail))

    val messages: List[Message] = userMessages.messages.getOrElse(ownerEmail, List())
    userMessages.messages = userMessages.messages + (chatId -> (message :: messages)) //add to messages one message and put it in map
    UserMessagesDAO.save(userMessages)
    userMessages
  }

  def getChat(email: String, opponent: String): Option[List[Message]] = {
    UserMessagesDAO.findOne(UserMessagesQueryObject(ownerEmail = Some(email)))
      .map(userMessages => userMessages.messages.get(opponent))
      .getOrElse(None)
  }

}
