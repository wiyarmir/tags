package es.guillermoorellana.tags.domain

import es.guillermoorellana.tags.data.SelectionRepository
import es.guillermoorellana.tags.data.TagsRepository
import es.guillermoorellana.tags.domain.model.SelectedTag
import es.guillermoorellana.tags.domain.model.Tag
import es.guillermoorellana.tags.domain.model.Tags
import io.reactivex.Flowable
import io.reactivex.functions.BiFunction

class TagStreamInteractor(
        private val tagsRepository: TagsRepository,
        private val selectionRepository: SelectionRepository
) {
    fun states(): Flowable<Tags> =
            Flowable.combineLatest(
                    tagsRepository.getTags(), selectionRepository.selectionStream,
                    BiFunction { tags, selection ->
                        Tags(
                                tags = tags
                                        .map { Tag(it.id, it.tag, it.id in selection) },
                                selectedTags = tags
                                        .filter { it.id in selection }
                                        .sortedBy { it.tag }
                                        .map { SelectedTag(it.id, it.tag, it.color) }
                        )
                    })
}
