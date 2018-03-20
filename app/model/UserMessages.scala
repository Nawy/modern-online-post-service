package model

import com.mongodb.casbah.Imports.ObjectId
import config.MongoConfig
import salat.dao.SalatDAO

import scala.collection.mutable.ArrayBuffer

case class UserMessages(var _id: ObjectId = new ObjectId,
                        var messages: Map[String, ArrayBuffer[Message]],
                        var spam: Map[String, ArrayBuffer[Message]])


object UserMessagesDAO extends SalatDAO[UserMessages, ObjectId](
  MongoConfig.mongoConnection("messages")
)



