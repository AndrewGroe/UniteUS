package io.github.andrewgroe.uniteus.representatives;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import org.parceler.Parcels;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import io.github.andrewgroe.uniteus.R;
import io.github.andrewgroe.uniteus.representatives.data.local.RepresentativeEntity;

public class RepresentativeDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "RepDetailActivity";
    RepresentativeEntity representativeEntity;
    ImageView imageView;
    ProgressBar progressBar;
    TextView repName;
    TextView repParty;
    TextView repEmail;
    TextView repPhone;
    TextView repURL;
    RelativeLayout phoneLayout;
    RelativeLayout emailLayout;
    RelativeLayout urlLayout;
    RelativeLayout upperLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_representative_detail);

        representativeEntity = Parcels.unwrap(getIntent().getParcelableExtra("EXTRA_REPS"));
        initUI();
    }

    private void initUI() {
        repName = findViewById(R.id.rep_name);
        repParty = findViewById(R.id.rep_party);
        repEmail = findViewById(R.id.rep_email);
        repPhone = findViewById(R.id.rep_phone);
        repURL = findViewById(R.id.rep_url);
        progressBar = findViewById(R.id.progress);
        imageView = findViewById(R.id.profile_image);

        phoneLayout = findViewById(R.id.layout_phone);
        phoneLayout.setOnClickListener(this);
        emailLayout = findViewById(R.id.layout_email);
        emailLayout.setOnClickListener(this);
        urlLayout = findViewById(R.id.layout_url);
        urlLayout.setOnClickListener(this);

        upperLayout = findViewById(R.id.upper_layout);

        repName.setText(representativeEntity.name);
        repParty.setText(representativeEntity.party);
        repPhone.setText(representativeEntity.phone);
        repURL.setText("Visit Site");

        fetchImage(imageView);
        checkEmailIsPresent();
        checkPartyForBGColor();
    }

    private void checkPartyForBGColor() {
        if (representativeEntity.party != null) {
            if (representativeEntity.party.contains("Republican")) {
                upperLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.colorRepublicanRed));
            }
            if (representativeEntity.party.contains("Democrat")) {
                upperLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.colorDemocratBlue));
            }
        } else
            upperLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.colorUnknownGrey));
    }

    private void checkEmailIsPresent() {
        if (representativeEntity.email != null) {
            emailLayout.setVisibility(View.VISIBLE);
            repEmail.setText(representativeEntity.email);
        }
    }

    private void fetchImage(ImageView imageView) {
        Glide.with(this)
                .load(representativeEntity.photoURL)
                .apply(RequestOptions.circleCropTransform()
                        .fallback(R.drawable.ic_person_black))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Log.e(TAG, ", " + e.getMessage());
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        Log.d(TAG, "success");
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(imageView);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_phone:
                Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", representativeEntity.phone, null));
                if (phoneIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(phoneIntent);
                }

                break;
            case R.id.layout_email:
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:" + representativeEntity.email));

                if (emailIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(emailIntent);
                }

                break;
            case R.id.layout_url:
                Intent urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(representativeEntity.url));
                if (urlIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(urlIntent);
                }
                break;
        }
    }

}
