package com.redapps.phonepolice.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.redapps.phonepolice.databinding.IntruderItemBinding;
import com.redapps.phonepolice.models.IntruderModels;
import com.redapps.phonepolice.ui.showIntruderScreen.ShowFullImageScreen;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class IntruderAdapter extends RecyclerView.Adapter<IntruderAdapter.ViewHolder> {
    private final WeakReference<Activity> activityWeakReference;
    ArrayList<IntruderModels> models;

    public IntruderAdapter(ArrayList<IntruderModels> arrayList, Activity activity) {
        this.models = arrayList;
        this.activityWeakReference = new WeakReference<>(activity);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(IntruderItemBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final IntruderModels intruderModels = this.models.get(i);
        Picasso.get().load(Uri.fromFile(intruderModels.getFile())).resize(100, 100).into(viewHolder.binding.image);
        Date date = new Date(intruderModels.getFile().lastModified());
        if (intruderModels.isIsSelected()) {
            viewHolder.binding.colorview.setVisibility(View.VISIBLE);
            viewHolder.binding.tickimg.setVisibility(View.VISIBLE);
        } else {
            viewHolder.binding.colorview.setVisibility(View.GONE);
            viewHolder.binding.tickimg.setVisibility(View.GONE);
        }
        viewHolder.binding.date.setText(new SimpleDateFormat("MM/dd/yyyy").format(date));
        viewHolder.binding.time.setText(new SimpleDateFormat("HH:mm:aa").format(date));
        viewHolder.binding.intuderMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                IntruderAdapter.this.IntruderAdapter(intruderModels, view);
            }
        });
    }

    public void IntruderAdapter(IntruderModels intruderModels, View view) {
        Intent intent = new Intent(this.activityWeakReference.get(), ShowFullImageScreen.class);
        intent.putExtra("obj", intruderModels);
        this.activityWeakReference.get().startActivity(intent);
    }

    public ArrayList<IntruderModels> getSelected() {
        ArrayList<IntruderModels> arrayList = new ArrayList<>();
        for (int i = 0; i < this.models.size(); i++) {
            if (this.models.get(i).isIsSelected()) {
                arrayList.add(this.models.get(i));
            }
        }
        return arrayList;
    }

    @Override
    public int getItemCount() {
        return this.models.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        IntruderItemBinding binding;

        public ViewHolder(IntruderItemBinding intruderItemBinding) {
            super(intruderItemBinding.getRoot());
            this.binding = intruderItemBinding;
        }
    }
}
