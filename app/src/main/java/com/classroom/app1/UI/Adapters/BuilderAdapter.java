package com.classroom.app1.UI.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.classroom.app1.Model.Builder;
import com.classroom.app1.R;
import com.classroom.app1.UI.ClickListeners.RecyclerViewClickListenerBuilder;

import java.util.ArrayList;

public class BuilderAdapter extends RecyclerView.Adapter<BuilderAdapter.ViewHolder> {

    private ArrayList<Builder> builders;
    private Context context;
    private RecyclerViewClickListenerBuilder mClickListener;

    public BuilderAdapter(ArrayList<Builder> builders, Context context, RecyclerViewClickListenerBuilder mClickListener) {
        this.builders = builders;
        this.context = context;
        this.mClickListener = mClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.builder_card_list,
                viewGroup, false);
        return new ViewHolder(v);
    }

    public ArrayList<Builder> getItems() {
        return builders;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.bindModel(builders.get(position));
    }

    @Override
    public int getItemCount() {
        return builders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Builder mBuilder;
        TextView builderName;
        TextView builderAddress;
        TextView builderDescription;
        TextView builderPhone;


        ViewHolder(View v) {
            super(v);
            builderName = itemView.findViewById(R.id.builder_name);
            builderAddress = itemView.findViewById(R.id.builder_address);
            builderDescription = itemView.findViewById(R.id.builder_description);
            builderPhone = itemView.findViewById(R.id.builder_phone);

            v.setOnClickListener(this);
        }

        @SuppressLint("SetTextI18n")
        void bindModel(Builder builders) {

            this.mBuilder = builders;
            builderName.setText(mBuilder.getName());
            builderAddress.setText(mBuilder.getAddress());
            builderDescription.setText(mBuilder.getDescription());
            builderPhone.setText(mBuilder.getPhone());

        }

        @Override
        public void onClick(View view) {
            mClickListener.onClick(view, mBuilder);
        }
    }
}
