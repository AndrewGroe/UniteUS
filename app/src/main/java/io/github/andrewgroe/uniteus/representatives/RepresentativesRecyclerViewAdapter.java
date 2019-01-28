package io.github.andrewgroe.uniteus.representatives;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import io.github.andrewgroe.uniteus.R;
import io.github.andrewgroe.uniteus.representatives.data.local.RepresentativeEntity;

public class RepresentativesRecyclerViewAdapter extends RecyclerView.Adapter<RepresentativesRecyclerViewAdapter.ViewHolder> {

    private List<RepresentativeEntity> reps;
    private LayoutInflater inflater;

    public RepresentativesRecyclerViewAdapter(Context context, List<RepresentativeEntity> reps) {
        this.inflater = LayoutInflater.from(context);
        this.reps = reps;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.representatives_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // Set representative name
        String repName = reps.get(position).getName();
        holder.repName.setText(repName);

        String repParty;
        if (reps.get(position).getParty() != null) {
            // Set representative party
            repParty = reps.get(position).getParty();
            // Normalize party response
            if (repParty.contains("Democrat")) {
                repParty = "Democrat";
            } else if (repParty.contains("Republican")) {
                repParty = "Republican";
            }
            holder.repParty.setText(repParty);
        } else {
            repParty = "Unknown";
            holder.repParty.setText(repParty);
        }
        // Check if image url is provided

        String repImageURL = reps.get(position).getPhotoURL();
        holder.progressBar.setVisibility(View.VISIBLE);
        Glide.with(holder.itemView.getContext())
                .load(repImageURL)
                .apply(RequestOptions.circleCropTransform()
                        .fallback(R.drawable.ic_person_black))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Log.e("adapter", ", " + e.getMessage());
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        Log.d("adapter", "success");
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.repImage);
    }


    @Override
    public int getItemCount() {
        return reps.size();
    }

    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {
        super.onViewRecycled(holder);
        Glide.with(holder.itemView.getContext()).clear(holder.repImage);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView repName;
        TextView repParty;
        ImageView repImage;
        ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            repName = itemView.findViewById(R.id.rep_name);
            repParty = itemView.findViewById(R.id.rep_party);
            repImage = itemView.findViewById(R.id.profile_image);
            progressBar = itemView.findViewById(R.id.progress);
        }
    }
}
