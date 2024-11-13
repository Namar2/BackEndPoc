package org.invendiv.presentation.user.routes

import com.vladsch.flexmark.html.HtmlRenderer
import com.vladsch.flexmark.parser.Parser
import org.invendiv.domain.useCase.user.AddUserUseCase
import org.invendiv.domain.useCase.user.FetchUsersUseCase
import io.ktor.http.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.invendiv.jobs.user.UserActionJobHandler
import org.invendiv.domain.model.user.NewUser
import java.io.File

val userActionJobHandler = UserActionJobHandler()

fun Route.userRoutes(
    addUserUseCase: AddUserUseCase,
    fetchUsersUseCase: FetchUsersUseCase
) {

    staticResources("/", "static")

    // Root route to display README.md content
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

    post("/api/add-user") {
        val newUser = call.receive<NewUser>()
        val createdUser = addUserUseCase.execute(newUser)
        createdUser?.let {
            // Trigger the background job to log this action
            userActionJobHandler.startLogUserActionJob(it.id, "User added")

            // Respond with 201 Created and return the created user data
            call.respond(HttpStatusCode.Created, it)
            return@post
        }

        // If user creation failed, respond with 400 Bad Request
        call.respond(HttpStatusCode.BadRequest, "Bad request 400")
    }

    get("api/get-users") {
        val users = fetchUsersUseCase.execute()
        call.respond(users)
    }
}