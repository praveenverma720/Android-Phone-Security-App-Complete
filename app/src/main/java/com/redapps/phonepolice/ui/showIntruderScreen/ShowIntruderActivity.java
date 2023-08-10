package com.redapps.phonepolice.ui.showIntruderScreen;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.redapps.phonepolice.adapter.IntruderAdapter;
import com.redapps.phonepolice.databinding.ActivityShowIntruderBinding;
import com.redapps.phonepolice.models.IntruderModels;

import java.io.File;
import java.util.ArrayList;


public class ShowIntruderActivity extends AppCompatActivity {
    IntruderAdapter adapter;
    ArrayList<IntruderModels> allIntruderList;
    ActivityShowIntruderBinding binding;
    File dir;


    @Override

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActivityShowIntruderBinding inflate = ActivityShowIntruderBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());
        this.dir = new File(getExternalFilesDir(null) + "/Anti Theft");
        ArrayList<IntruderModels> arrayList = new ArrayList<>();
        this.allIntruderList = arrayList;
        this.adapter = new IntruderAdapter(arrayList, this);
        this.binding.intruderList.setLayoutManager(new GridLayoutManager(this, 2));
        this.binding.intruderList.setAdapter(this.adapter);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(Integer.MIN_VALUE);
            window.clearFlags(67108864);
            window.setStatusBarColor(Color.parseColor("#7e8ee7"));
        }
        this.binding.backicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                ShowIntruderActivity.this.ShowIntruderActivity(view);
            }
        });
    }

    public void ShowIntruderActivity(View view) {
        onBackPressed();
    }


    public void getFile(File file) {
        this.allIntruderList.clear();
        try {
            File[] listFiles = file.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                for (File file2 : listFiles) {
                    if (file2.isDirectory()) {
                        getFile(file2);
                    } else if (file2.exists() && (file2.getName().endsWith(".jpg") || file2.getName().endsWith(".png") || file2.getName().endsWith(".jpeg"))) {
                        this.allIntruderList.add(new IntruderModels(file2, false));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.adapter.notifyDataSetChanged();
    }


    @Override
    public void onResume() {
        super.onResume();
        File file = new File(getExternalFilesDir(null) + "/Anti Theft");
        this.dir = file;
        getFile(file);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override

    public void onDestroy() {
        super.onDestroy();
    }
}
