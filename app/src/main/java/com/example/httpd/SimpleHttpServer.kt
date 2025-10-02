package com.example.httpd

import fi.iki.elonen.NanoHTTPD
import java.io.IOException

class SimpleHttpServer(private val port: Int = 3456) : NanoHTTPD(port) {

    @Throws(IOException::class)
    override fun start() {
        super.start(SOCKET_READ_TIMEOUT, false)
    }

    override fun serve(session: IHTTPSession): Response {
        val uri = session.uri ?: "/"
        return when (uri) {
            "/", "/index", "/hello" -> {
                newFixedLengthResponse(
                    Response.Status.OK,
                    "text/plain",
                    "Hello from NanoHTTPD on port $port\nRequest: ${session.method} ${session.uri}"
                )
            }
            "/json" -> {
                val json = "{"message":"Hello from NanoHTTPD","port":$port}"
                newFixedLengthResponse(Response.Status.OK, "application/json", json)
            }
            else -> {
                newFixedLengthResponse(Response.Status.NOT_FOUND, "text/plain", "Not found: $uri")
            }
        }
    }
}