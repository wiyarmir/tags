package es.guillermoorellana.tags.data

import io.reactivex.Single
import retrofit2.http.GET

interface TagsService {
    @GET("tags.json")
    fun getTags(): Single<List<Tag>>
}
