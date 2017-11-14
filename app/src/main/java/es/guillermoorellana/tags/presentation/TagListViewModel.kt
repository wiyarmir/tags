package es.guillermoorellana.tags.presentation

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import es.guillermoorellana.tags.DefinitelyNotDagger
import es.guillermoorellana.tags.domain.SelectableTagsInteractor
import es.guillermoorellana.tags.domain.Tags
import io.reactivex.disposables.CompositeDisposable

class TagListViewModel(
        private val interactor: SelectableTagsInteractor = DefinitelyNotDagger.streamStateInteractor()
) : ViewModel() {

    val data: MutableLiveData<Tags> = MutableLiveData()
    private val disposables: CompositeDisposable = CompositeDisposable()

    init {
        disposables.add(bindInteractor(interactor))
    }

    private fun bindInteractor(interactor: SelectableTagsInteractor) =
            interactor.states()
                    .subscribe { data.postValue(it) }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    fun tapTag(id: Int) = interactor.toggleTag(id)
}