package es.guillermoorellana.tags.domain

import com.nhaarman.mockito_kotlin.argForWhich
import com.nhaarman.mockito_kotlin.mock
import es.guillermoorellana.tags.data.SelectionRepository
import org.amshove.kluent.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.StrictStubs::class)
class TagSelectionInteractorTest {
    private val tagId = 1

    @Test
    fun `when toggling a selected tag it is unselected`() {
        val repo: SelectionRepository = mock()
        When calling repo.get() `it returns` setOf(tagId)

        val interactor = TagSelectionInteractor(repo)
        interactor.toggleTag(tagId)

        Verify on repo that repo.store(argForWhich { !contains(tagId) }) was called
    }

    @Test
    fun `when toggling a not selected tag it is selected`() {
        val repo: SelectionRepository = mock()
        When calling repo.get() `it returns` setOf()

        val interactor = TagSelectionInteractor(repo)
        interactor.toggleTag(tagId)

        Verify on repo that repo.store(argForWhich { contains(tagId) }) was called
    }

    @Test
    fun `when deselecting a selected tag it is unselected`() {
        val repo: SelectionRepository = mock()
        When calling repo.get() `it returns` setOf(tagId)

        val interactor = TagSelectionInteractor(repo)
        interactor.deselectTag(tagId)

        Verify on repo that repo.store(argForWhich { !contains(tagId) }) was called
    }

    @Test
    fun `when deselecting a not selected tag it is unselected`() {
        val repo: SelectionRepository = mock()
        When calling repo.get() `it returns` setOf()

        val interactor = TagSelectionInteractor(repo)
        interactor.deselectTag(tagId)

        Verify on repo that repo.store(argForWhich { !contains(tagId) }) was called
    }
}
