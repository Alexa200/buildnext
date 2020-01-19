package com.classroom.app1.UI.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.classroom.app1.Model.Store;
import com.classroom.app1.R;
import com.classroom.app1.UI.ClickListeners.RecyclerViewClickListenerStore;

import java.util.ArrayList;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.ViewHolder> {

    private ArrayList<Store> stores;
    private Context context;
    private RecyclerViewClickListenerStore mClickListener;

    public StoreAdapter(ArrayList<Store> stores, Context context, RecyclerViewClickListenerStore mClickListener) {
        this.stores = stores;
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

    public ArrayList<Store> getItems() {
        return stores;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.bindModel(stores.get(position));
    }

    @Override
    public int getItemCount() {
        return stores.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Store mStore;
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
        void bindModel(Store stores) {

            this.mStore = stores;
            builderName.setText(mStore.getName());
            builderAddress.setText(mStore.getAddress());
            builderDescription.setText(mStore.getDescription());
            builderPhone.setText(mStore.getPhone());
            
        }

        @Override
        public void onClick(View view) {
            mClickListener.onClick(view, mStore);
        }
    }
}
