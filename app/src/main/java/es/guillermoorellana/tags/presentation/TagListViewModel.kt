package es.guillermoorellana.tags.presentation

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import es.guillermoorellana.tags.DefinitelyNotDagger
import es.guillermoorellana.tags.domain.TagSelectionInteractor
import es.guillermoorellana.tags.domain.TagStreamInteractor
import es.guillermoorellana.tags.domain.Tags
import io.reactivex.disposables.CompositeDisposable

class TagListViewModel(
        private val tags: TagStreamInteractor = DefinitelyNotDagger.tagsInteractor(),
        private val selection: TagSelectionInteractor = DefinitelyNotDagger.selectionInteractor()
) : ViewModel() {

    val data: MutableLiveData<Tags> = MutableLiveData()
    private val disposables: CompositeDisposable = CompositeDisposable()

    init {
        disposables.add(bindInteractor(tags))
    }

    private fun bindInteractor(interactor: TagStreamInteractor) =
            interactor.states()
                    .subscribe { data.postValue(it) }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    fun tapTag(id: Int) = selection.toggleTag(id)
}