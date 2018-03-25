package repository

import javax.inject.Inject

import model.{Message, UserMessages}
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.{BSONDocument, BSONObjectID, _}
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

class MessageRepository @Inject()(reactiveMongoApi: ReactiveMongoApi) {

  import myutils.JsonParsers._
  implicit def messageWriter: BSONDocumentWriter[Message] = Macros.writer[Message]
  implicit def messageReader: BSONDocumentReader[Message] = Macros.reader[Message]
  implicit def userMessagesWriter: BSONDocumentWriter[UserMessages] = Macros.writer[UserMessages]
  implicit def userMessagesReader: BSONDocumentReader[UserMessages] = Macros.reader[UserMessages]

  private def collection: Future[BSONCollection] = reactiveMongoApi.database.map(_.collection[BSONCollection]("user_messages"))

  def get(id: BSONObjectID): UserMessages =
    Await.result(
      collection.flatMap(_.find(BSONDocument("_id" -> id)).requireOne[UserMessages]),
      1 minute
    )

  def get(email: String): Option[UserMessages] =
    Await.result(
      collection.flatMap(_.find(BSONDocument("ownerEmail" -> email)).one[UserMessages]),
      1 minute
    )

  def save(userMessages: UserMessages): UserMessages = {
    val future = collection.flatMap(
      _.findAndUpdate(
        selector = document("id" -> userMessages._id),
        update = userMessages,
        fetchNewObject = true,
        upsert = true
      )
    ).map(_.result[UserMessages])
    Await.result(future, 1 minute).get
  }

  def remove(id: BSONObjectID): WriteResult =
    Await.result(
      collection.flatMap(_.remove(BSONDocument("_id" -> id))),
      1 minute
    )

}
