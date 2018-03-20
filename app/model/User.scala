package model

import com.mongodb.casbah.Imports.ObjectId
import config.MongoConfig
import salat.dao.SalatDAO

case class User(_id: ObjectId = new ObjectId,
                email: String,
                password: String,
                phoneNumber: String = null,
                fullName: String = null)

object UserDAO extends SalatDAO[User, ObjectId](
  MongoConfig.mongoConnection("user")
)