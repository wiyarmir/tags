package es.guillermoorellana.tags.data

import io.reactivex.Single

class TagsRepository(
        private val tagsService: TagsService
) {
    fun getTags(): Single<List<DataTag>> = tagsService.getTags()
}
