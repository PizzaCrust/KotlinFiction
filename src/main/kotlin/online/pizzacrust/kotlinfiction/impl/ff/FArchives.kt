package online.pizzacrust.kotlinfiction.impl.ff

import online.pizzacrust.kotlinfiction.Story
import online.pizzacrust.kotlinfiction.StoryArchives
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.net.URLEncoder

class FArchives: StoryArchives {
    override fun query(queryTerm: String, maxPages: Int): List<Story> {
        return recursiveQuery(1, queryTerm, maxPages)
    }

    private fun parseEntriesInPage(document: Document): MutableList<Story> {
        val stories = mutableListOf<Story>()
        for (element in document.getElementsByClass("z-list")) {
            try {
                stories.add(FStory(element.getElementsByClass("stitle").first().attr("href").split
                ("/")[2]))
            } catch (e: Exception) {
                println("Failed to get one of the entries!")
                continue
            }
        }
        return stories
    }

    private fun hasNextPage(document: Document): Boolean {
        for (element in document.getElementsByTag("a")) {
            if (element.text().startsWith("Next")) {
                return true
            }
        }
        return false
    }

    private fun recursiveQuery(startingPage: Int, query: String, maxPage: Int): List<Story> {
        val url = "https://www.fanfiction.net/search/?ready=1&keywords=${URLEncoder
                .encode(query)}&type=story&ppage=$startingPage"
        val doc = Jsoup.connect(url).ignoreContentType(true).get()
        val stories = parseEntriesInPage(doc)
        if (hasNextPage(doc) && startingPage != maxPage) {
            stories.addAll(recursiveQuery(startingPage + 1, query, maxPage))
        }
        return stories
    }

    override fun story(id: String): Story = FStory(id)
}

fun main(vararg strings: String) {
    println(FArchives().query("Restart with a Bang").size)
    println(FArchives().query("Test Test", 2).size)
}