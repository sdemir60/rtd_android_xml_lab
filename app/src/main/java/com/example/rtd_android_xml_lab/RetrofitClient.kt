import android.content.Context
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    private const val CACHE_SIZE = 10 * 1024 * 1024 // 10 MB

    private var lastRequestTime = 0L
    private var isFirstRequest = true

    private lateinit var retrofit: Retrofit
    private lateinit var apiInstance: JSONPlaceholderApiInterface

    fun initialize(context: Context) {

        val cacheDir = File(context.cacheDir, "http-cache")
        val cache = Cache(cacheDir, CACHE_SIZE.toLong())

        val okHttpClient = OkHttpClient.Builder()
            .cache(cache)
            .addNetworkInterceptor(provideCacheInterceptor())
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiInstance = retrofit.create(JSONPlaceholderApiInterface::class.java)
    }

    private fun provideCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            val response = chain.proceed(chain.request())

            val cacheControl = CacheControl.Builder()
                .maxAge(1, TimeUnit.MINUTES)
                .build()

            response.newBuilder()
                .header("Cache-Control", cacheControl.toString())
                .build()
        }
    }

    fun isDataFromCache(): Boolean {

        val currentTime = System.currentTimeMillis()

        if (isFirstRequest) {
            isFirstRequest = false
            lastRequestTime = currentTime
            return false
        }

        val isFromCache = currentTime - lastRequestTime < TimeUnit.MINUTES.toMillis(1)
        lastRequestTime = currentTime

        return isFromCache
    }

    val api: JSONPlaceholderApiInterface
        get() = apiInstance
}