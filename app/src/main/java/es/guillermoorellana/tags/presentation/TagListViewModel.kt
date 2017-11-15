package es.guillermoorellana.tags.presentation

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import es.guillermoorellana.tags.DefinitelyNotDagger
import es.guillermoorellana.tags.domain.TagSelectionInteractor
import es.guillermoorellana.tags.domain.TagStreamInteractor
import es.guillermoorellana.tags.domain.model.Tags
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

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
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { data.postValue(it) }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    fun tapTag(id: Int) = selection.toggleTag(id)

    fun removeTag(id: Int) = selection.deselectTag(id)
}