package es.guillermoorellana.tags.domain

data class Tags(
        val tags: List<Tag>,
        val selectedTags: List<SelectedTag> = emptyList()
)

data class Tag(val id: Int, val name: String, val selected: Boolean)
data class SelectedTag(val id: Int, val name: String, val color: String)
