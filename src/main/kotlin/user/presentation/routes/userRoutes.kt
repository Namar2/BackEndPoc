package org.invendiv.presentation.user.routes

import com.vladsch.flexmark.html.HtmlRenderer
import com.vladsch.flexmark.parser.Parser
import org.invendiv.user.useCase.AddUserUseCase
import org.invendiv.user.useCase.FetchUsersUseCase
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.invendiv.user.jobs.UserActionJobHandler
import org.invendiv.user.domain.model.NewUser
import org.invendiv.auth.presentation.AuthModule
import java.io.File

val userActionJobHandler = UserActionJobHandler()

fun Route.userRoutes(
    addUserUseCase: AddUserUseCase,
    fetchUsersUseCase: FetchUsersUseCase
) {

    // Publicly accessible root route to display README.md content
    get("/") {
        val readmeFile = File("README.md")
        if (readmeFile.exists()) {
            val markdown = readmeFile.readText()

            // Convert Markdown to HTML
            val parser = Parser.builder().build()
            val document = parser.parse(markdown)
            val html = HtmlRenderer.builder().build().render(document)

            // Respond with HTML content
            call.respondText(html, ContentType.Text.Html)
        } else {
            call.respondText("README.md file not found", ContentType.Text.Plain, HttpStatusCode.NotFound)
        }
    }

    // Protected routes that require authentication
    authenticate(AuthModule.jwtConfig) {
        post("/api/add-user") {
            val newUser = call.receive<NewUser>()
            val createdUser = addUserUseCase.execute(newUser)
            createdUser?.let {
                // Trigger the background job to log this action
                userActionJobHandler.startLogUserActionJob(it.id, "User added")

                // Respond with 201 Created and return the created user core.data
                call.respond(HttpStatusCode.Created, it)
                return@post
            }

            // If user creation failed, respond with 400 Bad Request
            call.respond(HttpStatusCode.BadRequest, "Bad request 400")
        }

        get("/api/get-users") {
            val users = fetchUsersUseCase.execute()
            call.respond(users)
        }
    }
}