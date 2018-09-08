package online.pizzacrust.kotlinfiction

interface Story {

    val name: String

    val id: String

    fun chapter(info: ChapterInfo): String

    val chapters: List<ChapterInfo>

    val author: String

}