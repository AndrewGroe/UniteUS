package io.github.andrewgroe.uniteus.representatives;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.github.andrewgroe.uniteus.representatives.data.RepresentativeRepository;
import io.github.andrewgroe.uniteus.representatives.data.local.RepresentativeEntity;

public class RepresentativesViewModel extends ViewModel {

    private static final String TAG = "ViewModel";
    private final MutableLiveData<List<RepresentativeEntity>> repsResponseLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> hasAddressResponseLiveData = new MutableLiveData<>();
    private RepresentativeRepository representativeRepository;

    RepresentativesViewModel(RepresentativeRepository representativeRepository) {
        this.representativeRepository = representativeRepository;
    }

    MutableLiveData<List<RepresentativeEntity>> repsResponse() {
        return repsResponseLiveData;
    }

    MutableLiveData<Boolean> hasAddressResponse() {
        return hasAddressResponseLiveData;
    }

    // Check if repo has user's address stored
    void getHasAddressFromRepo() {
        representativeRepository.fetchAddress();
        representativeRepository.hasAddress().observeForever(this::consumeHasAddressResponse);
    }

    // Called On Successful Observation of address
    private void consumeHasAddressResponse(Boolean hasAddress) {
        hasAddressResponseLiveData.setValue(hasAddress);
    }

    // Trigger Fetch In Repository & Observe LiveData
    void getRepsFromRepo() {
        representativeRepository.fetchReps();
        representativeRepository.repsResponse().observeForever(this::consumeRepsResponse);
    }

    // Called On Successful Observation of reps
    private void consumeRepsResponse(List<RepresentativeEntity> representativeEntities) {
        repsResponseLiveData.setValue(representativeEntities);
    }

    // Stop Observing
    @Override
    protected void onCleared() {
        representativeRepository.repsResponse().removeObserver(this::consumeRepsResponse);
        representativeRepository.hasAddress().removeObserver(this::consumeHasAddressResponse);
    }

    public void storeAddressInRepo(String addressText, String cityText, String stateText) {
        representativeRepository.storeAddress(addressText, cityText, stateText);
    }
}
