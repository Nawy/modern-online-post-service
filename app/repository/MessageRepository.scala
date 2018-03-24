package repository

import javax.inject.Inject

import model.UserMessages
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson.{BSONDocument, BSONObjectID}

import scala.concurrent.Future

class MessageRepository @Inject()(reactiveMongoApi: ReactiveMongoApi) {

  private def collection: Future[BSONCollection] = reactiveMongoApi.database.map(_.collection[BSONCollection]("user_messages"))


  def get(id: BSONObjectID): Future[Option[UserMessages]] = {
    collection.andThen{case _ => }
    collection.flatMap(_.find(BSONDocument("id" -> id)).one[UserMessages])
  }

}
