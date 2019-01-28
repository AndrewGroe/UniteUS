package io.github.andrewgroe.uniteus.representatives.data;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import io.github.andrewgroe.uniteus.representatives.data.local.RepresentativeEntity;
import io.github.andrewgroe.uniteus.representatives.data.local.RepresentativesDao;
import io.github.andrewgroe.uniteus.representatives.data.local.UserAddressDao;
import io.github.andrewgroe.uniteus.representatives.data.local.UserAddressEntity;
import io.github.andrewgroe.uniteus.representatives.data.remote.CivicAPIService;
import io.github.andrewgroe.uniteus.representatives.data.remote.model.Official;
import io.github.andrewgroe.uniteus.representatives.data.remote.model.Representatives;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class RepresentativeRepository {

    private static final String TAG = "RepresentativeRepo";
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<List<RepresentativeEntity>> repsResponseLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> hasAddressLiveData = new MutableLiveData<>();
    private final String key = "YOUR_API_KEY";
    private CivicAPIService civicAPIService;
    private RepresentativesDao representativesDao;
    private UserAddressDao userAddressDao;
    private String address = "";

    public RepresentativeRepository(CivicAPIService civicAPIService, RepresentativesDao representativesDao, UserAddressDao userAddressDao) {
        this.civicAPIService = civicAPIService;
        this.representativesDao = representativesDao;
        this.userAddressDao = userAddressDao;
    }

    // Confirms presence of user's address in db
    public void fetchAddress() {
        disposables.add(userAddressDao.getCount()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Integer>() {
                    @Override
                    public void onSuccess(Integer numberOfRows) {
                        if (numberOfRows > 0) {
                            fetchAddressFromDB();
                        } else repoHasAddress(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Error " + e);
                    }
                }));
    }

    // Store address in db
    public void storeAddress(String address, String city, String state) {
        // Create New Entity
        UserAddressEntity userAddress = new UserAddressEntity();
        userAddress.setAddress(address);
        userAddress.setCity(city);
        userAddress.setState(state);

        // Store in DB
        Completable.fromAction(() -> userAddressDao
                .insertUserAddress(userAddress))
                .subscribeOn(Schedulers.io())
                .subscribe();
        {
            fetchAddressFromDB();
        }
    }

    private void repoHasAddress(Boolean hasAddress) {
        hasAddressLiveData.setValue(hasAddress);
    }

    // ViewModel Observes This
    public MutableLiveData<Boolean> hasAddress() {
        return hasAddressLiveData;
    }

    // Fetch Address From Local Data Source
    private void fetchAddressFromDB() {
        disposables.add(userAddressDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<UserAddressEntity>() {
                    @Override
                    public void onSuccess(UserAddressEntity userAddressEntity) {
                        repoHasAddress(true);
                        address = userAddressEntity.toString();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Error " + e);
                    }
                }));
    }


    // ViewModel Triggers This
    public void fetchReps() {
        // Gets Number Of Rows In Table
        disposables.add(representativesDao.getCount()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribeWith(new DisposableSingleObserver<Integer>() {
                                   @Override
                                   public void onSuccess(Integer numberOfRows) {
                                       if (numberOfRows > 0) {
                                           fetchRepsFromDB();
                                       } else fetchRepsFromAPI();
                                   }

                                   @Override
                                   public void onError(Throwable e) {
                                       Log.e(TAG, "Error " + e);
                                   }
                               }
                ));
    }

    // ViewModel Observes This
    public MutableLiveData<List<RepresentativeEntity>> repsResponse() {
        return repsResponseLiveData;
    }

    // Fetch Reps From Remote Data Source
    private void fetchRepsFromAPI() {
        // Requests Data From Remote API
        disposables.add(civicAPIService.getReps(address, key)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribeWith(new DisposableSingleObserver<Representatives>() {
                                   @Override
                                   public void onSuccess(Representatives representatives) {

                                       // Store In DB
                                       storeReps(representatives);


                                   }

                                   @Override
                                   public void onError(Throwable e) {
                                       Log.e(TAG, "Error " + e);
                                   }
                               }


                ));
    }

    // Fetch Reps From Local Data Source
    private void fetchRepsFromDB() {
        // Requests Data From Local DB
        disposables.add(representativesDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<RepresentativeEntity>>() {

                    @Override
                    public void onSuccess(List<RepresentativeEntity> representativeEntities) {
                        // Sets Response To LiveData For Observation
                        repsResponseLiveData.setValue(representativeEntities);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Error " + e);
                    }
                }));

    }

    // Store Reps In Local DB
    private void storeReps(Representatives representatives) {
        List<RepresentativeEntity> repList = new ArrayList<>();
        // Convert From Object<Representatives> -> Object<List<RepresentativeEntity>>
        // For DB Storage
        for (Official official : representatives.getOfficials()
        ) {
            RepresentativeEntity representative = new RepresentativeEntity();
            representative.setName(official.getName());
            representative.setParty(official.getParty());
            representative.setPhotoURL(official.getPhotoUrl());
            repList.add(representative);
        }
        // Insert Into DB
        Completable.fromAction(() -> representativesDao
                .insertReps(repList))
                .subscribeOn(Schedulers.trampoline())
                .subscribe();
        {
            fetchRepsFromDB();
        }

    }

}
