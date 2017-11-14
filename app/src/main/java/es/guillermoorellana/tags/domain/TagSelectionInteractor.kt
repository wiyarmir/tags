package es.guillermoorellana.tags.domain

import es.guillermoorellana.tags.data.SelectionRepository

class TagSelectionInteractor(
        private val selectionRepository: SelectionRepository
) {

    fun toggleTag(id: Int) {
        selectionRepository.get()
                .let { if (it.contains(id)) it.minus(id) else it.plus(id) }
                .let { selectionRepository.store(it) }
    }
}