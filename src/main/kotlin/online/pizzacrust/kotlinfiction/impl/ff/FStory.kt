package online.pizzacrust.kotlinfiction.impl.ff

import online.pizzacrust.kotlinfiction.BasicStory
import online.pizzacrust.kotlinfiction.ChapterInfo
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

internal fun getStoryPage(id: String, page: Int): Document {
    val link = "https://www.fanfiction.net/s/$id/$page/"
    return Jsoup.connect(link).ignoreContentType(true).get()
}

internal fun getStoryChapters(id: String): List<ChapterInfo> {
    val page = getStoryPage(id, 1)
    val chapterSelect = page.body().getElementById("chap_select")
    val chapters = mutableListOf<ChapterInfo>()
    if (chapterSelect == null) return chapters
    for (element in chapterSelect.getElementsByTag("option")) {
        val value = element.attr("value")
        val name = element.text()
        chapters.add(ChapterInfo(name, value, value.toInt() - 1))
    }
    return chapters
}

class FStory(override val id: String): BasicStory(
        getStoryPage(id, 1).body().getElementsByClass("xcontrast_txt")[3].text(),
        id, {
            val root = getStoryPage(id, it.index + 1)
            val builder = StringBuilder()
            for (element in root.getElementsByTag("p")) {
                builder.append(element.text()).append("\n")
            }
    builder.toString()
        }, getStoryChapters(id), getStoryPage(id, 1).getElementsByClass("xcontrast_txt")[5].text())

fun main(vararg args: String) {
    val story = FStory("12659220")
    println(story.name)
    println(story.author)
    println(story.chapters.size)
    println(story.chapter(story.chapters[0]))
}