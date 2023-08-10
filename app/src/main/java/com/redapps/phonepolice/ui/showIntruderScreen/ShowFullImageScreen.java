package com.redapps.phonepolice.ui.showIntruderScreen;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.redapps.phonepolice.R;
import com.redapps.phonepolice.databinding.ActivityShowFullImageScreenBinding;
import com.redapps.phonepolice.models.IntruderModels;
import com.squareup.picasso.Picasso;


public class ShowFullImageScreen extends AppCompatActivity {
    ActivityShowFullImageScreenBinding binding;
    IntruderModels models;


    @Override

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActivityShowFullImageScreenBinding inflate = ActivityShowFullImageScreenBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());
        this.models = (IntruderModels) getIntent().getSerializableExtra("obj");
        Picasso.get().load(Uri.fromFile(this.models.getFile())).into(this.binding.intruderimage);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(Integer.MIN_VALUE);
            window.clearFlags(67108864);
            window.setStatusBarColor(Color.parseColor("#7e8ee7"));
        }
        this.binding.delbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                ShowFullImageScreen.this.ShowFullImageScreen(view);
            }
        });
        this.binding.sharebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                ShowFullImageScreen.this.ShowFullImageScreen2(view);
            }
        });
        this.binding.backicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                ShowFullImageScreen.this.ShowFullImageScreen3(view);
            }
        });
    }

    public void ShowFullImageScreen(View view) {
        final Dialog dialog = new Dialog(this);
        View inflate = getLayoutInflater().inflate(R.layout.confrom_del_dialog, (ViewGroup) null);
        dialog.setContentView(inflate);
        Window window = dialog.getWindow();
        window.setAttributes(window.getAttributes());
        dialog.getWindow().setLayout(-2, -2);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        ((Button) inflate.findViewById(R.id.cancl_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view2) {
                dialog.dismiss();
            }
        });
        ((Button) inflate.findViewById(R.id.cnfrm_del_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view2) {
                ShowFullImageScreen.this.ShowFullImageScreen1(dialog, view2);
            }
        });
    }

    public void ShowFullImageScreen1(Dialog dialog, View view) {
        dialog.dismiss();
        delPics();
    }

    public void ShowFullImageScreen2(View view) {
        if (this.models.getFile().exists()) {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("image/*");
            if (Build.VERSION.SDK_INT >= 23) {
                Uri uriForFile = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".fileprovider", this.models.getFile());
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.putExtra("android.intent.extra.STREAM", uriForFile);
            } else {
                intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(this.models.getFile()));
            }
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(Intent.createChooser(intent, "Share via"));
            }
        }
    }

    public void ShowFullImageScreen3(View view) {
        onBackPressed();
    }

    private void delPics() {
        if (this.models.getFile().exists() && this.models.getFile().delete()) {
            Toast.makeText(this, getString(R.string.imagedeleted), Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
    }
}
