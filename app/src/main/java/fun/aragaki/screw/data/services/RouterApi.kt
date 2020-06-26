package `fun`.aragaki.screw.data.services

import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RouterApi {
    @GET("/login.cgi")
    suspend fun login(
        @Query("Action") Action: String? = null,
        @Query("username") username: String? = null,
        @Query("realm") realm: String? = null,
        @Query("nonce") nonce: String? = null,
        @Query("response") response: String? = null,
        @Query("qop") qop: String? = null,
        @Query("cnonce") cnonce: String? = null,
        @Query("temp") temp: String? = null,
        @Query("client") client: String? = null
    ): Response<ResponseBody>

    @GET("/xml_action.cgi")
    suspend fun get(
        @Query("method") method: String? = "get",
        @Query("module") module: String? = null,
        @Query("file") file: String? = null,
        @Query("Action") Action: String? = null,
        @Query("Id") Id: String? = null
    ): Response<ResponseBody>

    @POST("/xml_action.cgi")
    suspend fun set(
        @Query("method") method: String? = "set",
        @Query("module") module: String? = null,
        @Query("file") file: String? = null,
        @Query("Action") Action: String? = null,
        @Query("Id") Id: String? = null,
        @Body body: RequestBody? = null
    ): Response<ResponseBody>

    @GET("/upload/Mifi_device.cfg")
    suspend fun dumpConfig(): Response<ResponseBody>

    companion object {
        operator fun invoke(
            client: OkHttpClient, baseUrl: String
        ): RouterApi {
            return Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .build().create(RouterApi::class.java)
        }
    }
}