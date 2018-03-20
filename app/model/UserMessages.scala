package model

import com.mongodb.casbah.Imports.ObjectId
import config.MongoConfig
import salat.dao.SalatDAO
import config.SalatContext._
import scala.collection.mutable.ArrayBuffer

case class UserMessages(_id: String = new ObjectId().toHexString,
                        messages: Map[String, ArrayBuffer[Message]] = Map.empty,
                        spam: Map[String, ArrayBuffer[Message]] = Map.empty)


object UserMessagesDAO extends SalatDAO[UserMessages, String](
  MongoConfig.mongoConnection("messages")
)



