package in.android.favortechdemo.remote;


import in.android.favortechdemo.entities.FeedResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RestApiService {
//https://api.myjson.com/bins/1dwvxe
    @GET("1dwvxe")
    Call<FeedResponse> getAllTeachers();

    @GET
    Call<ResponseBody> fetchCaptcha(@Url String url);


}
