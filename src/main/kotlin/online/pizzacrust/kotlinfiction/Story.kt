package online.pizzacrust.kotlinfiction

interface Story {

    val name: String

    val id: String

    fun chapter(info: ChapterInfo): String

    val chapters: List<ChapterInfo>

    val author: String

}

open class BasicStory(override val name: String,
                      override val id: String,
                      val chapterBlock: (ChapterInfo) -> String,
                      override val chapters: List<ChapterInfo>,
                      override val author: String): Story {
    override fun chapter(info: ChapterInfo): String {
        return chapterBlock(info)
    }
}