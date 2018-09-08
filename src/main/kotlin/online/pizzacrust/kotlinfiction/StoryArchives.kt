package online.pizzacrust.kotlinfiction

interface StoryArchives {

    fun query(queryTerm: String, maxPages: Int = 5): List<Story>

    fun story(id: String): Story

}