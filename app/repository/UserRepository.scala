package repository

import javax.inject.Inject

import model.User
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter, BSONObjectID, Macros, document}
import reactivemongo.play.json.collection.JSONCollection
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

class UserRepository @Inject()(reactiveMongoApi: ReactiveMongoApi) {

  import myutils.JsonParsers._
  implicit def messageWriter: BSONDocumentWriter[User] = Macros.writer[User]
  implicit def messageReader: BSONDocumentReader[User] = Macros.reader[User]

  private def collection: Future[JSONCollection] = reactiveMongoApi.database.map(_.collection[JSONCollection]("user_messages"))

  def remove(id: BSONObjectID): WriteResult =
    Await.result(
      collection.flatMap(_.remove(Map("_id" -> id))),
      1 minute
    )

  def get(id: BSONObjectID): Option[User] =
    Await.result(
      collection.flatMap(_.find(Map("_id" -> id)).one[User]),
      1 minute
    )

  def save(user: User): User = {
    val future = collection.flatMap(
      _.findAndUpdate(
        selector = document("id" -> user._id),
        update = user,
        fetchNewObject = true,
        upsert = true
      )
    ).map(_.result[User])
    Await.result(future, 1 minute).get
  }

}
