package org.invendiv.user.presentation.routes

import auth.domain.JwtProvider.Companion.authJWT
import com.vladsch.flexmark.html.HtmlRenderer
import com.vladsch.flexmark.parser.Parser
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.invendiv.user.domain.model.NewUser
import org.invendiv.user.domain.useCase.AddUserUseCase
import org.invendiv.user.domain.useCase.FetchUsersUseCase
import org.invendiv.user.jobs.UserActionJobHandler
import org.invendiv.utils.extensions.ResultWrapper
import org.invendiv.utils.extensions.generateHtml
import org.koin.java.KoinJavaComponent.inject

import java.io.File

fun Route.userRoutes() {
    // Injected
    val addUserUseCase: AddUserUseCase by inject(AddUserUseCase::class.java)
    val fetchUsersUseCase: FetchUsersUseCase by inject(FetchUsersUseCase::class.java)
    val userActionJobHandler: UserActionJobHandler by inject(UserActionJobHandler::class.java)

    get("/") {
        // Simple approach
        val readmeFile = File("README.md")
        if (readmeFile.exists()) {
            val markdown = readmeFile.readText()
            val parser = Parser.builder().build()
            val document = parser.parse(markdown)
            val html = HtmlRenderer.builder().build().render(document)

            call.respondText(html, ContentType.Text.Html)
        } else {
            call.respondText("README.md file not found", ContentType.Text.Plain, HttpStatusCode.NotFound)
        }

        // Ktx Result approach
    /*    val result = File("README.md").generateHtml()
        if (result.isSuccess) {
           call.respondText(result.getOrNull() ?: "Error generating HTML", ContentType.Text.Html)
       } else {
           call.respondText(result.exceptionOrNull()?.message ?: "Error processing file", ContentType.Text.Plain, HttpStatusCode.NotFound)
       }
*/

        // Advanced approach
     /*   when(val result = generateHtml(File("README.md"))) {
            is ResultWrapper.Success -> {
                call.respondText(result.value, ContentType.Text.Html)
            }

            is ResultWrapper.Error -> {
                call.respondText(result.exception.cause.toString(), ContentType.Text.Plain, HttpStatusCode.NotFound)
            }
        }*/
    }

    // Protected routes that require authentication
    authenticate(authJWT) {
        post("/api/add-user") {
            val newUser = call.receive<NewUser>()
            val createdUser = addUserUseCase.execute(newUser)
            createdUser?.let {
                // Action related job (dependant)
                userActionJobHandler.startLogUserActionJob(it.id, "User added")
                call.respond(HttpStatusCode.Created, it)
                return@post
            }
            call.respond(HttpStatusCode.BadRequest, "Bad request 400")
        }

        get("/api/get-users") {
            val users = fetchUsersUseCase.execute()
            call.respond(users)
        }
    }
}