package org.invendiv.utils.extensions

import com.vladsch.flexmark.html.HtmlRenderer
import com.vladsch.flexmark.parser.Parser
import io.ktor.server.plugins.*
import java.io.File


fun generateHtml(file: File): Result<String> {
    return try {
        val markdown = file.readText()
        val parser = Parser.builder().build()
        val document = parser.parse(markdown)
        val html = HtmlRenderer.builder().build().render(document)
        Result.success(html)
    } catch (e: NotFoundException) {
        Result.failure(e)
    }
}