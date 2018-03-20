package config

import play.Play
import salat.Context

object SalatContext {

    implicit val ctx = new Context {
      val name = "Custom_Classloader"
    }
    ctx.registerClassLoader(Play.application.classloader())

}
