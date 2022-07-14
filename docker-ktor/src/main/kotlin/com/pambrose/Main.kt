package com.pambrose

import com.github.pambrose.common.util.getBanner
import io.ktor.http.*
import io.ktor.http.ContentType.Text.Plain
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.html.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.html.*
import mu.KLogging
import org.slf4j.event.Level

object Main : KLogging() {
  @JvmStatic
  fun main(args: Array<String>) {
    logger.apply {
      info { getBanner("banners/ktor-demo.banner", this) }
    }

    embeddedServer(CIO, port = System.getenv("PORT")?.toInt() ?: 8080) {
      install(CallLogging) { level = Level.INFO }
      install(DefaultHeaders)
      install(StatusPages) {
        status(HttpStatusCode.NotFound) { call, status ->
          val msg = "Page not found: ${call.request.path()}"
          call.respondText(text = msg, status = status)
          logger.info { msg }
        }
      }

      install(Compression) {
        gzip { priority = 1.0 }
        deflate { priority = 10.0; minimumSize(1024) }
      }

      routing {
        get("/") {
          call.respondHtml() {
            head {
              title { +"docker-ktor" }
            }
            body {
              h1 { +"docker-ktor" }
              p { +"Hello World!" }
            }
          }
        }

        get("ping") { call.respondText("pong", Plain) }

        static("/") {
          staticBasePackage = "public"
          resources(".")
        }
      }
    }.start(wait = true)
  }
}