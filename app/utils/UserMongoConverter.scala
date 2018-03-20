package utils

import com.mongodb.DBObject
import com.mongodb.casbah.Imports.{MongoDBList, ObjectId}
import com.mongodb.casbah.commons.MongoDBObject
import model.User
import utils.UserMongoProperties._
import com.mongodb.casbah.Imports._

object UserMongoConverter {

  def convertToMongoObject(userInfo: User): DBObject = {
    val builder = MongoDBObject.newBuilder
    builder += ID -> userInfo.id
    builder += EMAIL -> userInfo.email
    builder += PASSWORD -> userInfo.password
    builder += PHONE_NUMBER -> userInfo.phoneNumber
    builder += FULL_NAME -> userInfo.fullName
    builder += MESSAGES -> MongoDBList
    builder += SPAM -> MongoDBList
    builder.result()
  }

  def convertFromMongoObject(db: DBObject): User = {
    val name: String = db.getAsOrElse[String](NAME, mongoFail)
    val age: Int = db.getAsOrElse[Int](AGE, mongoFail)
    val knownLanguages = db.getAs[MongoDBList](KNOWN_LANGUAGES) match {
      case Some(languages) => languages collect {
        case s: String => s
      }
      case None => mongoFail
    }

    val socialId = db.getAs[Long](SOCIAL_ID)
    val address = AddressMongoConverter.convertFromMongoObject(db.getAsOrElse[DBObject](ADDRESS, mongoFail))

    Person(
      _id = db.getAsOrElse[ObjectId](ID, mongoFail),
      age = age,
      name = name,
      address = address,
      knownLanguages = knownLanguages,
      socialId = socialId
    )
  }
}
