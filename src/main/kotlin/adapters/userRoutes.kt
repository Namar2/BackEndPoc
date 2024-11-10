package adapters

import application.useCase.AddUserUseCase
import application.useCase.FetchUsersUseCase
import com.vladsch.flexmark.html.HtmlRenderer
import com.vladsch.flexmark.parser.Parser
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.invendiv.domain.model.NewUser
import java.io.File

fun Route.userRoutes(
    addUserUseCase: AddUserUseCase,
    fetchUsersUseCase: FetchUsersUseCase
) {

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

    post("/addUser") {
        val newUser = call.receive<NewUser>()
        val createdUser = addUserUseCase.execute(newUser)
        call.respond(HttpStatusCode.Created, createdUser)
    }

    get("/getUsers") {
        val users = fetchUsersUseCase.execute()
        call.respond(users)
    }
}