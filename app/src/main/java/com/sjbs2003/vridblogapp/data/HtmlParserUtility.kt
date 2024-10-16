package com.sjbs2003.vridblogapp.data

import org.jsoup.Jsoup
import org.jsoup.nodes.Element

object HtmlParserUtil {
    fun parseHtml(html: String): String {
        val document = Jsoup.parse(html)
        return parseElement(document.body())
    }

    private fun parseElement(element: Element): String {
        val sb = StringBuilder()
        for (node in element.childNodes()) {
            when {
                node is Element && node.tagName() == "p" -> {
                    sb.append(node.text()).append("\n\n")
                }
                node is Element && node.tagName() == "h2" -> {
                    sb.append(node.text().uppercase()).append("\n\n")
                }
                node is Element && node.tagName() == "ul" -> {
                    for (li in node.select("li")) {
                        sb.append("• ").append(li.text()).append("\n")
                    }
                    sb.append("\n")
                }
                node is Element -> sb.append(parseElement(node))
            }
        }
        return sb.toString().trim()
    }
}