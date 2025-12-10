package com.example.golift.saved;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.golift.R;
import com.example.golift.model.Gym;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class SavedGymsAdapter extends RecyclerView.Adapter<SavedGymsAdapter.GymViewHolder> {

    private List<Gym> gymList;
    private OnGymActionListener listener;

    public interface OnGymActionListener {
        void onRemoveGym(Gym gym, int position);
    }

    public SavedGymsAdapter(OnGymActionListener listener) {
        this.gymList = new ArrayList<>();
        this.listener = listener;
    }

    public void setGymList(List<Gym> gyms) {
        this.gymList = gyms;
        notifyDataSetChanged();
    }

    public void removeGym(int position) {
        if (position >= 0 && position < gymList.size()) {
            gymList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, gymList.size());
        }
    }

    @NonNull
    @Override
    public GymViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_saved_gym, parent, false);
        return new GymViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GymViewHolder holder, int position) {
        Gym gym = gymList.get(position);
        holder.bind(gym);
    }

    @Override
    public int getItemCount() {
        return gymList.size();
    }

    class GymViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvDistance;
        TextView tvDescription;
        MaterialButton btnRemove;

        public GymViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvGymName);
            tvDistance = itemView.findViewById(R.id.tvGymDistance);
            tvDescription = itemView.findViewById(R.id.tvGymDescription);
            btnRemove = itemView.findViewById(R.id.btnRemoveGym);
        }

        public void bind(Gym gym) {
            tvName.setText(gym.getName());
            tvDistance.setText(gym.getDistance() + " km away");
            tvDescription.setText(gym.getDescription());

            btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onRemoveGym(gym, position);
                    }
                }
            });
        }
    }
}