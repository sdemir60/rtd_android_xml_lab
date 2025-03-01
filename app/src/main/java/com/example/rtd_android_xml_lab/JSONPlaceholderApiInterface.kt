import com.example.rtd_android_xml_lab.JSONPlaceholderPostModel
import retrofit2.http.GET

interface JSONPlaceholderApiInterface {
    @GET("posts")
    suspend fun getPosts(): List<JSONPlaceholderPostModel>
}