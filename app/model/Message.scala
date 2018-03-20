package model

import com.mongodb.casbah.Imports.ObjectId

case class Message(_id: String = new ObjectId().toHexString,
                   messageType: String,
                   text: String /*, date: LocalDateTime = LocalDateTime.now*/
                  )