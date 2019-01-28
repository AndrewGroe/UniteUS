package io.github.andrewgroe.uniteus.di;

import android.app.Application;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import io.github.andrewgroe.uniteus.representatives.ViewModelFactory;
import io.github.andrewgroe.uniteus.representatives.data.RepresentativeRepository;
import io.github.andrewgroe.uniteus.representatives.data.local.RepresentativeDatabase;
import io.github.andrewgroe.uniteus.representatives.data.local.RepresentativesDao;
import io.github.andrewgroe.uniteus.representatives.data.local.UserAddressDao;
import io.github.andrewgroe.uniteus.representatives.data.remote.CivicAPIService;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/*
Provides dependencies for networking and db
*/

@Module
public class UtilsModule {
    private RepresentativeDatabase representativeDatabase;

    // Gson
    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder builder = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return builder.setLenient().create();
    }

    // Retrofit
    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson) {

        return new Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    // Retrofit Service
    @Provides
    @Singleton
    CivicAPIService getCivicAPIService(Retrofit retrofit) {
        return retrofit.create(CivicAPIService.class);
    }


    // Room db (Representatives)
    @Singleton
    @Provides
    RepresentativeDatabase getRepresentativeDatabase(Application application) {
        representativeDatabase = Room.databaseBuilder(application, RepresentativeDatabase.class, "reps_db").build();
        return representativeDatabase;
    }

    @Singleton
    @Provides
    RepresentativesDao getRepresentativesDao(RepresentativeDatabase representativeDatabase) {
        return representativeDatabase.representativesDao();
    }

    @Singleton
    @Provides
    UserAddressDao getUserAddressDao(RepresentativeDatabase representativeDatabase) {
        return representativeDatabase.userAddressDao();
    }


    // Repository
    @Provides
    @Singleton
    RepresentativeRepository getRepresentativeRepository(CivicAPIService civicAPIService, RepresentativesDao representativesDao, UserAddressDao userAddressDao) {
        return new RepresentativeRepository(civicAPIService, representativesDao, userAddressDao);
    }

    // ViewModel Factory
    @Provides
    @Singleton
    ViewModelProvider.Factory getViewModelFactory(RepresentativeRepository myRepository) {
        return new ViewModelFactory(myRepository);
    }
}
