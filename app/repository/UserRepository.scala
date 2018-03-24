package repository

import javax.inject.Inject

import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.bson.{BSONDocument, BSONObjectID}
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.Future

class UserRepository @Inject()(reactiveMongoApi: ReactiveMongoApi) {

  private def collection: Future[JSONCollection] = reactiveMongoApi.database.map(_.collection[JSONCollection]("user_messages"))

  def remove(id:BSONObjectID) = collection.map(_.remove(BSONDocument("id" -> id)))

}
