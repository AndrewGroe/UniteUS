package io.github.andrewgroe.uniteus.representatives.data.remote;

import io.github.andrewgroe.uniteus.representatives.data.remote.model.Representatives;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CivicAPIService {

    @GET("civicinfo/v2/representatives")
    Single<Representatives> getReps(@Query("address") String address,
                                    @Query("key") String key);
}