package model

import java.time.LocalDateTime

import com.mongodb.casbah.Imports.ObjectId

case class Message(_id: String = new ObjectId().toHexString,
                   messageType: MessageType.Value,
                   text: String,
                   date: LocalDateTime = LocalDateTime.now)