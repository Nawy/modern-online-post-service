name := "PostService"
 
version := "1.0" 
      
lazy val `postservice` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"
      
scalaVersion := "2.12.2"

libraryDependencies ++= Seq(ehcache, specs2 % Test, guice)
libraryDependencies += "info.mukel" %% "telegrambot4s" % "3.0.14"
libraryDependencies += "org.reactivemongo" %% "play2-reactivemongo" % "0.13.0-play26"

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

      