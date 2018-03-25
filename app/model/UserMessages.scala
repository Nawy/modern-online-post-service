package model

import reactivemongo.bson.BSONObjectID


case class Message(_id: BSONObjectID = BSONObjectID.generate,
                   senderEmail: String,
                   recipientEmail: String,
                   text: String/*, date: LocalDateTime = LocalDateTime.now*/)

case class UserMessages(_id: BSONObjectID = BSONObjectID.generate,
                        ownerEmail: String,
                        var messages: Map[String, List[Message]] = Map.empty,
                        var spam: Map[String, List[Message]] = Map.empty)



