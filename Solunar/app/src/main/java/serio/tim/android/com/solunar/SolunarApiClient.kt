package serio.tim.android.com.solunar

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface SolunarApiClient {
    @GET("/solunar/{location}")
    fun getSolunar(@Path("location") location: String): Call<SolunarData>
}