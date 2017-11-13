package es.guillermoorellana.tags.domain

import es.guillermoorellana.tags.data.DataTag
import es.guillermoorellana.tags.data.SelectionRepository
import es.guillermoorellana.tags.data.TagsRepository
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class SelectableTagsInteractor(
        private val tagsRepository: TagsRepository,
        private val selectionRepository: SelectionRepository
) {
    fun getState(): Flowable<Tags> =
            Flowable.combineLatest(
                    tagsRepository.getTags().toFlowable(),
                    selectionRepository.selectionStream,
                    BiFunction { tags: List<DataTag>, selection: Set<Int> ->
                        Tags(
                                tags = tags.toDomainTag(),
                                selectedTags = tags
                                        .filter { it.id in selection }
                                        .toDomainSelectedTag()
                        )
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())

    fun toggleTag(id: Int): Unit = TODO()
}

private fun List<DataTag>.toDomainSelectedTag(): List<SelectedTag> =
        map { SelectedTag(it.id, it.tag, it.color) }

private fun List<DataTag>.toDomainTag(): List<Tag> =
        map { Tag(it.id, it.tag, false) }