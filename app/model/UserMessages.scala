package model

import org.bson.types.ObjectId
import reactivemongo.bson.BSONObjectID


case class Message(_id: BSONObjectID = BSONObjectID.generate,
                   senderEmail: String,
                   recipientEmail: String,
                   text: String/*, date: LocalDateTime = LocalDateTime.now*/)

case class UserMessages(_id: String = new ObjectId().toHexString,
                        ownerEmail: String,
                        var messages: Map[String, List[Message]] = Map.empty,
                        var spam: Map[String, List[Message]] = Map.empty)

case class UserMessagesQueryObject(_id: Option[ObjectId] = None,
                                   ownerEmail: Option[String] = None)



