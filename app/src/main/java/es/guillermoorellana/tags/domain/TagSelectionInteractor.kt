package es.guillermoorellana.tags.domain

import es.guillermoorellana.tags.data.SelectionRepository

class TagSelectionInteractor(
        private val selectionRepository: SelectionRepository
) {

    fun toggleTag(id: Int) =
            if (selectionRepository.get().contains(id)) removeTag(id) else addTag(id)

    private fun addTag(id: Int) {
        selectionRepository.get()
                .plus(id)
                .let { selectionRepository.store(it) }
    }

    fun removeTag(id: Int) {
        selectionRepository.get()
                .minus(id)
                .let { selectionRepository.store(it) }
    }
}