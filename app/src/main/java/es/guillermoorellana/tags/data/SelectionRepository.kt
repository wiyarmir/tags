package es.guillermoorellana.tags.data

import io.reactivex.Flowable
import io.reactivex.processors.BehaviorProcessor

class SelectionRepository {

    private val selectionProcessor: BehaviorProcessor<Set<Int>> = BehaviorProcessor.createDefault(emptySet())

    val selectionStream: Flowable<Set<Int>>
        get() = selectionProcessor

    fun store(value: Set<Int>) =
            selectionProcessor.onNext(value)

    fun get(): Set<Int> = selectionProcessor.value
}
