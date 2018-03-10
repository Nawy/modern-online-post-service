package model

import scala.collection.mutable.ArrayBuffer

case class User(
                 var id: Long,
                 var email: String,
                 var password: String,
                 var phoneNumber: String,
                 var fullName: String,
                 var messages: Map[String, ArrayBuffer[Message]],
                 var spam: Map[String, ArrayBuffer[Message]]
               ) {

  def toUserInfo: UserInfo = UserInfo(Some(id), email, password, phoneNumber, fullName)

}
