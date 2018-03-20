package model

import com.mongodb.casbah.Imports.ObjectId
import config.MongoConfig
import config.SalatContext._
import salat.annotations.raw.Salat
import salat.dao.SalatDAO

case class User(_id: String = new ObjectId().toHexString,
                email: String,
                password: String,
                phoneNumber: String = null,
                fullName: String = null)

@Salat
object UserDAO extends SalatDAO[User, String](
  MongoConfig.mongoConnection("user")
)

