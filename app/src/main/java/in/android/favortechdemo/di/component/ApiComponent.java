package in.android.favortechdemo.di.component;

import javax.inject.Singleton;

import dagger.Component;
import in.android.favortechdemo.MainActivity;
import in.android.favortechdemo.di.module.ContextModule;
import in.android.favortechdemo.di.module.DBModule;
import in.android.favortechdemo.di.module.IlmaAppModule;
import in.android.favortechdemo.di.module.RetrofitApiModule;
import in.android.favortechdemo.fragments.MainFeedFragment;
import in.android.favortechdemo.fragments.SignInFragment;
import in.android.favortechdemo.fragments.SignUpFragment;
import retrofit2.Retrofit;

@Singleton
@Component(modules = {RetrofitApiModule.class, IlmaAppModule.class, ContextModule.class, DBModule.class})
public interface ApiComponent {

    Retrofit getRetrofit();

    void inject(MainActivity activity);

    void inject(SignUpFragment activity);

    void inject(MainFeedFragment fragment);

    void inject(SignInFragment fragment);
}
