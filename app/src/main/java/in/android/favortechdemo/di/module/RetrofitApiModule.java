package in.android.favortechdemo.di.module;


import android.app.Application;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
public class RetrofitApiModule {


    private String mBaseUrl;

    public RetrofitApiModule(String mBaseUrl) {
        this.mBaseUrl = mBaseUrl;
    }

    @Singleton
    @Provides
    public Cache provideCache(Application application) {
        int cacheSize = 10 * 1024 * 1024;
        return new Cache(application.getCacheDir(), cacheSize);
    }


    @Singleton
    @Provides
    public OkHttpClient provideOkHttpClient(Cache cache) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.cache(cache);
        return builder.build();
    }

    @Singleton
    @Provides
    public Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setLenient();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }


    @Singleton
    @Provides
    public Converter.Factory provideConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Singleton
    @Provides
    public Retrofit provideRetrofit(Converter.Factory factory, OkHttpClient okHttpClient) {
        return new Retrofit.Builder().baseUrl(mBaseUrl).addConverterFactory(factory).client(okHttpClient).build();
    }


}
