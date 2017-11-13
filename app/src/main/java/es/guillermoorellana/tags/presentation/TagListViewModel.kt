package es.guillermoorellana.tags.presentation

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import es.guillermoorellana.tags.DefinitelyNotDagger
import es.guillermoorellana.tags.data.Tag
import es.guillermoorellana.tags.domain.StreamStateInteractor
import io.reactivex.disposables.CompositeDisposable

class TagListViewModel(
        interactor: StreamStateInteractor = DefinitelyNotDagger.streamStateInteractor
) : ViewModel() {

    val data: MutableLiveData<List<Tag>> = MutableLiveData()
    private val disposables: CompositeDisposable = CompositeDisposable()

    init {
        disposables.add(bindInteractor(interactor))
    }

    private fun bindInteractor(interactor: StreamStateInteractor) =
            interactor.getState()
                    .subscribe { tag: List<Tag> ->
                        data.postValue(tag)
                    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}