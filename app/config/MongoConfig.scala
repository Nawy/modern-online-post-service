package config

import com.mongodb.casbah.{MongoConnection, MongoDB}
import com.typesafe.config.{Config, ConfigFactory}

object MongoConfig {
  private val config: Config = ConfigFactory.load("my")
  private val host: String = config.getString("mongo.host")
  private val port: Int = config.getInt("mongo.port")
  private val database: String = config.getString("mongo.database")

  val mongoConnection: MongoDB = MongoConnection(host, port)(database)
}
