package in.android.favortechdemo.viewmodels;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.io.FileOutputStream;
import java.io.IOException;

import in.android.favortechdemo.db.ImageDBDAO;
import in.android.favortechdemo.entities.FeedResponse;
import in.android.favortechdemo.entities.ImageEntity;
import in.android.favortechdemo.remote.RestApiService;
import in.android.favortechdemo.utils.Logs;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainFeedFragmentViewModel extends AndroidViewModel {

    private String TAG = MainFeedFragmentViewModel.class.getSimpleName();
    private MutableLiveData<FeedResponse> mutableLiveData;
    private MutableLiveData<ImageEntity> mutableImage;
    private Application application;
    private ImageDBDAO imageDBDAO;

    public MainFeedFragmentViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
    }


    public MutableLiveData<FeedResponse> getMainFeeds(Retrofit retrofit, ImageDBDAO imageDBDAO) {
        this.imageDBDAO = imageDBDAO;
        if (mutableLiveData == null) {
            mutableLiveData = new MutableLiveData<>();
            RestApiService restApiService = retrofit.create(RestApiService.class);

            Call<FeedResponse> call = restApiService.getAllTeachers();
            call.enqueue(new Callback<FeedResponse>() {
                @Override
                public void onResponse(Call<FeedResponse> call, Response<FeedResponse> response) {
                    if (response.body() != null) {
                        Logs.v(TAG, response.body().getMessage());
                        FeedResponse feedResponse = response.body();
                        mutableLiveData.setValue(feedResponse);
                    }
                }

                @Override
                public void onFailure(Call<FeedResponse> call, Throwable t) {
                    Logs.v(TAG, t.getMessage());
                }
            });
        }
        return mutableLiveData;
    }

    public MutableLiveData<ImageEntity> downloadImage(Retrofit retrofit, String url, int pos) {

        mutableImage = new MutableLiveData<>();
        ImageEntity imageEntity = imageDBDAO.getImageUrl(url);

        if (imageEntity == null) {
            RestApiService restApiService = retrofit.create(RestApiService.class);
            Call<ResponseBody> call = restApiService.fetchCaptcha(url);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                            String imageName = url.substring(url.lastIndexOf("/") + 1);
                            saveFile(application.getApplicationContext(), bmp, url, imageName, pos);
                        } else {
                            // TODO
                        }
                    } else {
                        // TODO
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    // TODO
                }
            });
        } else {
            Logs.v(TAG, "In DB" + imageEntity.picName);
            mutableImage.postValue(imageEntity);
        }
        return mutableImage;
    }

    private void saveFile(Context context, Bitmap b, String url, String picName, int pos) {
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(picName, Context.MODE_PRIVATE);
            b.compress(Bitmap.CompressFormat.PNG, 100, fos);
            ImageEntity imageEntity = new ImageEntity();
            imageEntity.picName = picName;
            imageEntity.url = url;
            imageDBDAO.insert(imageEntity);
            mutableImage.postValue(imageEntity);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
