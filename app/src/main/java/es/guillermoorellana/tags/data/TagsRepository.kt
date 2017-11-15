package es.guillermoorellana.tags.data

import io.reactivex.Flowable

class TagsRepository(
        private val tagsService: TagsService
) {
    fun getTags(): Flowable<List<DataTag>> = tagsService.getTags().toFlowable()
}
