package model

case class UserInfo(
                     var id: Option[Long],
                     var email: String,
                     var password: String,
                     var phoneNumber: String,
                     var fullName: String
                   ) {

}
