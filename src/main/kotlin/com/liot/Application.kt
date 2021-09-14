package com.liot

import com.liot.data.collections.User
import com.liot.data.registerUser
import io.ktor.application.*
import com.liot.plugins.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.routing.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    install(DefaultHeaders)
    install(CallLogging)
    install(Routing)
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }

    CoroutineScope(Dispatchers.IO).launch {
        registerUser(
            User("abc@abc.com", "123456")
        )
    }

    configureRouting()
}
