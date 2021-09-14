package com.liot.routes

import com.liot.data.checkIfUserExists
import com.liot.data.collections.User
import com.liot.data.registerUser
import com.liot.data.requests.AccountRequest
import com.liot.data.responses.SimpleResponse
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.registerRoute() {
    route("/register") {
        post {
            val request = try {
                call.receive<AccountRequest>()
            } catch (e: ContentTransformationException) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            val userExists = checkIfUserExists(request.email)
            if (!userExists) {
                if (registerUser(User(request.email, request.password))) {
                    call.respond(HttpStatusCode.OK, SimpleResponse(true, "Successfully created account!"))
                } else {
                    call.respond(HttpStatusCode.OK, SimpleResponse(false, "An unknown error occurred"))
                }
            } else {
                call.respond(HttpStatusCode.OK, SimpleResponse(false, "Already exists a user with that email"))
            }
        }
    }
}