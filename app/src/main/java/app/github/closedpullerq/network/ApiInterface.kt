package app.github.closedpullerq.network

import app.github.closedpullerq.ui.pulls.model.PullsDataClass
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("pulls")
    suspend fun getPullList(@Query("state") state : String): Response<PullsDataClass>
}