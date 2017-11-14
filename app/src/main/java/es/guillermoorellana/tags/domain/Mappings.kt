package es.guillermoorellana.tags.domain

import es.guillermoorellana.tags.data.DataTag

fun tags(tags: List<DataTag>, selection: Set<Int>): Tags {
    return Tags(
            tags = tags.toDomainTag(),
            selectedTags = tags
                    .filter { it.id in selection }
                    .toDomainSelectedTag()
    )
}

private fun List<DataTag>.toDomainSelectedTag(): List<SelectedTag> =
        map { SelectedTag(it.id, it.tag, it.color) }

private fun List<DataTag>.toDomainTag(): List<Tag> =
        map { Tag(it.id, it.tag, false) }