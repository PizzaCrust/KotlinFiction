package online.pizzacrust.kotlinfiction

interface StoryArchives {

    fun query(queryTerm: String): List<Story>

    fun story(id: String): Story?

}