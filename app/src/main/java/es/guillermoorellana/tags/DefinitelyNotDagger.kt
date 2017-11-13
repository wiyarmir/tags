package es.guillermoorellana.tags

import com.google.gson.Gson
import es.guillermoorellana.tags.data.TagsRepository
import es.guillermoorellana.tags.data.TagsService
import es.guillermoorellana.tags.domain.StreamStateInteractor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object DefinitelyNotDagger {

    private const val baseUrl = "https://gist.githubusercontent.com/jgritman/" +
            "7f2e89d1937ba9d9fc678f4c9844cbf1/raw/729eecaacbe749fbeeb891cc430d55235aa8036a/"

    private val gson: Gson by lazy { Gson() }
    private val TAGS_SERVICE: TagsService by lazy {
        Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(TagsService::class.java)
    }
    private val TAGS_REPOSITORY: TagsRepository by lazy {
        TagsRepository(TAGS_SERVICE)
    }

    val streamStateInteractor = StreamStateInteractor(TAGS_REPOSITORY)
}