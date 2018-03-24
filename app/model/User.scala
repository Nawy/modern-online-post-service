package model

import reactivemongo.bson.BSONObjectID


case class User(_id: BSONObjectID = BSONObjectID.generate,
                email: String,
                password: String,
                phoneNumber: Option[String] = None,
                fullName: Option[String] = None)


