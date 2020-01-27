package in.android.favortechdemo.di.module;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class IlmaAppModule {

    private Application application;

    public IlmaAppModule(Application application) {
        this.application = application;
    }

    @Singleton
    @Provides
    public Application provideApplication() {
        return application;
    }
}
