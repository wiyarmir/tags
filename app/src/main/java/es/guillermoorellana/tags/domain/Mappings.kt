package es.guillermoorellana.tags.domain

import es.guillermoorellana.tags.data.DataTag

fun tags(tags: List<DataTag>, selection: Set<Int>): Tags =
        Tags(
                tags = tags
                        .map { Tag(it.id, it.tag, it.id in selection) },
                selectedTags = tags
                        .filter { it.id in selection }
                        .map { SelectedTag(it.id, it.tag, it.color) }
        )
