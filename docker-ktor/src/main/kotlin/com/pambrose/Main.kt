package com.pambrose

import com.github.pambrose.common.util.getBanner
import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.http.ContentType.Text.Plain
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.install
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import io.ktor.server.html.respondHtml
import io.ktor.server.http.content.staticResources
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.plugins.compression.Compression
import io.ktor.server.plugins.compression.deflate
import io.ktor.server.plugins.compression.gzip
import io.ktor.server.plugins.compression.minimumSize
import io.ktor.server.plugins.defaultheaders.DefaultHeaders
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.request.path
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.html.body
import kotlinx.html.h1
import kotlinx.html.head
import kotlinx.html.p
import kotlinx.html.title
import org.slf4j.event.Level

object Main {
  private val logger = KotlinLogging.logger {}

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

        staticResources("/static", "public")
      }
    }.start(wait = true)
  }
}
