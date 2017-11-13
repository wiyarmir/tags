package es.guillermoorellana.tags.domain

import es.guillermoorellana.tags.data.Tag
import es.guillermoorellana.tags.data.TagsRepository
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class StreamStateInteractor(
        private val tagsRepository: TagsRepository
) {
    fun getState(): Flowable<List<Tag>> = tagsRepository.getTags()
            .toFlowable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}