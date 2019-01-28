package io.github.andrewgroe.uniteus.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;
import io.github.andrewgroe.uniteus.representatives.RepresentativesActivity;
import io.github.andrewgroe.uniteus.representatives.data.RepresentativeRepository;
import io.github.andrewgroe.uniteus.representatives.data.local.RepresentativeDatabase;
import io.github.andrewgroe.uniteus.representatives.data.local.RepresentativesDao;
import io.github.andrewgroe.uniteus.representatives.data.local.UserAddressDao;

@Singleton
@Component(modules = {AppModule.class, UtilsModule.class})
public interface AppComponent {
    void inject(RepresentativesActivity representativesActivity);

    RepresentativesDao representativeDao();

    UserAddressDao userAddressDao();

    RepresentativeDatabase representativeDatabase();

    RepresentativeRepository representativeRepository();

    Application application();
}
