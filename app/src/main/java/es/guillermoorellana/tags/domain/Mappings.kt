package es.guillermoorellana.tags.domain

import es.guillermoorellana.tags.data.DataTag
import es.guillermoorellana.tags.domain.model.SelectedTag
import es.guillermoorellana.tags.domain.model.Tag
import es.guillermoorellana.tags.domain.model.Tags

fun tags(tags: List<DataTag>, selection: Set<Int>): Tags =
        Tags(
                tags = tags
                        .map { Tag(it.id, it.tag, it.id in selection) },
                selectedTags = tags
                        .filter { it.id in selection }
                        .sortedBy { it.tag }
                        .map { SelectedTag(it.id, it.tag, it.color) }
        )
