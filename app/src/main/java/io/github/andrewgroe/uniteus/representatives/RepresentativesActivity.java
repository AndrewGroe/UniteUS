package io.github.andrewgroe.uniteus.representatives;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.marcoscg.dialogsheet.DialogSheet;

import java.util.List;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.github.andrewgroe.uniteus.MyApplication;
import io.github.andrewgroe.uniteus.R;
import io.github.andrewgroe.uniteus.representatives.data.local.RepresentativeEntity;

public class RepresentativesActivity extends AppCompatActivity {
    private static final String TAG = "RepresentativesActivity";

    @Inject
    ViewModelFactory viewModelFactory;
    RepresentativesViewModel viewModel;
    RepresentativesRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init ProgressBar
        progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);

        // Init RecyclerView
        recyclerView = findViewById(R.id.reps_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Init ViewModel
        ((MyApplication) getApplication()).getAppComponent().inject(this);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(RepresentativesViewModel.class);

        // Check if Address has been saved
        viewModel.hasAddressResponse().observe(this, this::consumeAddressResponse);
        viewModel.getHasAddressFromRepo();

    }

    // Called when ViewModel receives address response from repo
    private void consumeAddressResponse(Boolean hasAddress) {
        // If Address is false, prompt user
        if (!hasAddress) {
            initAddressDialogue();
        } else {
            // Load response from db
            viewModel.repsResponse().observe(this, this::consumeRepsResponse);
            viewModel.getRepsFromRepo();
        }
    }

    // Show dialog for user to enter address
    private void initAddressDialogue() {
        DialogSheet dialogSheet = new DialogSheet(this);
        dialogSheet.setView(R.layout.dialogue_address);
        View inflatedView = dialogSheet.getInflatedView();

        EditText address = inflatedView.findViewById(R.id.address_edit_text);
        EditText city = inflatedView.findViewById(R.id.city_edit_text);
        Spinner state = inflatedView.findViewById(R.id.state_spinner);

        dialogSheet.setTitle("Enter Address")
                .setPositiveButton(android.R.string.ok, v -> {
                    String addressText = address.getText().toString();
                    String cityText = city.getText().toString();
                    String stateText = state.getSelectedItem().toString();
                    viewModel.storeAddressInRepo(addressText, cityText, stateText);
                    progressBar.setVisibility(View.VISIBLE);
                })
                .setNegativeButton(android.R.string.cancel, v -> {
                    finish();
                })
                .show();
        progressBar.setVisibility(View.GONE);
    }

    // Called when ViewModel receives representatives response from repo
    private void consumeRepsResponse(List<RepresentativeEntity> representatives) {

        Log.d(TAG, "got response from ViewModel " + representatives.size());
        // Pass reps to RecyclerView
        adapter = new RepresentativesRecyclerViewAdapter(this, representatives);
        recyclerView.setAdapter(adapter);
        progressBar.setVisibility(View.GONE);
    }

}
