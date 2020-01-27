package in.android.favortechdemo;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import in.android.favortechdemo.di.component.ApiComponent;
import in.android.favortechdemo.di.component.DaggerApiComponent;
import in.android.favortechdemo.di.module.ContextModule;
import in.android.favortechdemo.di.module.IlmaAppModule;
import in.android.favortechdemo.di.module.RetrofitApiModule;
import in.android.favortechdemo.utils.Constants;

public class MyApplication extends Application {

    private RefWatcher refWatcher;
    private ApiComponent apiComponent;


    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        refWatcher = LeakCanary.install(this);
        apiComponent = DaggerApiComponent.builder().contextModule(new ContextModule(this))
                .ilmaAppModule(new IlmaAppModule(this))
                .retrofitApiModule(new RetrofitApiModule(Constants.BASEURL))
                .build();
    }

    public ApiComponent getApiComponent() {
        return apiComponent;
    }
}