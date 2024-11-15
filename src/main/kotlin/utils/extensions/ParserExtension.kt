package org.invendiv.utils.extensions

import com.vladsch.flexmark.html.HtmlRenderer
import com.vladsch.flexmark.parser.Parser
import io.ktor.server.plugins.*
import java.io.File


fun generateHtml(file: File): ResultWrapper<String> {
    return try {
        val markdown = file.readText()
        val parser = Parser.builder().build()
        val document = parser.parse(markdown)
        val html = HtmlRenderer.builder().build().render(document)
        ResultWrapper.Success(html)
    } catch (e: NotFoundException) {
        ResultWrapper.Error(e)
    }
}


fun File.generateHtml(): Result<String> {
    return try {
        val markdown = this.readText()
        val parser = Parser.builder().build()
        val document = parser.parse(markdown)
        val html = HtmlRenderer.builder().build().render(document)
        Result.success(html)
    } catch (e: NotFoundException) {
        Result.failure(e)
    }
}



sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class Error(val exception: Throwable) : ResultWrapper<Nothing>()
}
