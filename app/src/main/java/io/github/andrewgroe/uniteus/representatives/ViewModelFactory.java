package io.github.andrewgroe.uniteus.representatives;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import io.github.andrewgroe.uniteus.representatives.data.RepresentativeRepository;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private RepresentativeRepository repository;

    @Inject
    public ViewModelFactory(RepresentativeRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RepresentativesViewModel.class)) {
            return (T) new RepresentativesViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown class name");
    }
}
