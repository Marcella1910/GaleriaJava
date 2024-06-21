package gaurink.marcella.galeria;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;

public class PhotoActivity extends AppCompatActivity {

    String photoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_photo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.tbPhoto);
        setSupportActionBar(toolbar);
        //pega a foto do startPhotoActivity
        Intent i = getIntent();
        photoPath = i.getStringExtra("photo_path");

        //carrega a ft no bitmap
        Bitmap bitmap = Util.getBitmap(photoPath);
        //setar no imagemView
        ImageView imPhoto = findViewById(R.id.imPhoto);
        imPhoto.setImageBitmap(bitmap);
        ActionBar actionBar = getSupportActionBar();
        //habilita o botao de voltar
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    //cria um inflador de menu -> cria as opcoes de menu definidas no arquivo de menu antigo e adiciona no atual
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.photo_activity_tb, menu);
        return true;
    }

    //abrir para tirar foto quando clica no botão camera
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.opShare) {
            sharePhoto();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void sharePhoto() {
        //Codigo para compartilhar a foto
        Uri photoUri = FileProvider.getUriForFile(PhotoActivity.this, "gaurink.marcella.galeria.fileprovider", new File(photoPath));
        Intent i = new Intent(Intent.ACTION_SEND);
        i.putExtra(Intent.EXTRA_STREAM, photoUri);
        i.setType("image/jpeg");
        startActivity(i);
    }
}