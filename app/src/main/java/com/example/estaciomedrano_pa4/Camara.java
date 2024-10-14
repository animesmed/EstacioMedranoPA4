package com.example.estaciomedrano_pa4;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.Manifest;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Camara extends AppCompatActivity {
    ImageView imgFotoTomada;
    ImageButton imgbtnTomarFoto;
    public static final int REQUEST_PERMISSION_CAMERA=101;
     public Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camara);
        imgbtnTomarFoto = findViewById(R.id.imgbtnTomarFoto);
        imgFotoTomada = findViewById(R.id.imgFotoTomada);
        uri = null;
        imgbtnTomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (ActivityCompat.checkSelfPermission(Camara.this, Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_DENIED){
                        abrirCamara();
                    }
                    else {
                        ActivityCompat.requestPermissions(Camara.this,
                                new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSION_CAMERA);
                    }
                }
                else {
                    abrirCamara();
                }
            }
        });
    }

    private void abrirCamara() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"Titulo");
        values.put(MediaStore.Images.Media.DESCRIPTION,"PruebadeCamara");
        values.put(MediaStore.Images.Media.AUTHOR,"yms");
        uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,uri );
        cameraUrl.launch(intent);
    }
    private ActivityResultLauncher<Intent> cameraUrl
            = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    if (o.getResultCode()== Activity.RESULT_OK){
                        Toast.makeText(Camara.this,"Ruta"+
                                uri.toString(),Toast.LENGTH_SHORT).show();
                        imgFotoTomada.setImageURI(uri);
                    }
                    else {
                        Toast.makeText(Camara.this,"La acción se cancelo por el usuario",Toast.LENGTH_SHORT).show();
                    }
                }
            }); {
    }
}