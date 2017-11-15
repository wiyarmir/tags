package es.guillermoorellana.tags.domain.model

data class Tags(
        val tags: List<Tag>,
        val selectedTags: List<SelectedTag> = emptyList()
)
