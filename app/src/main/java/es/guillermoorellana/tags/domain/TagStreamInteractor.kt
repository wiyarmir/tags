package es.guillermoorellana.tags.domain

import es.guillermoorellana.tags.data.SelectionRepository
import es.guillermoorellana.tags.data.TagsRepository
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class TagStreamInteractor(
        private val tagsRepository: TagsRepository,
        private val selectionRepository: SelectionRepository
) {
    fun states(): Flowable<Tags> =
            Flowable.combineLatest(
                    tagsRepository.getTags().toFlowable(), selectionRepository.selectionStream,
                    BiFunction(::tags))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
}
