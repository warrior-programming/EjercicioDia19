package com.prueba.ejerciciodia19;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;

public class InsertarDatos extends AppCompatActivity{




        Button btnAgregar;

    EditText titulo;
    EditText autor;
    EditText editorial;
    EditText anio;


    byte[] imagen;
    ImageView objetImageView;
    private static final int PICK_IMAGE_REQUEST = 100;
    private Uri imageFilePath;
    private Bitmap imageToStore;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_insertar_datos);



            btnAgregar = (Button) findViewById(R.id.btnGuardarInsertar);


                titulo = (EditText) findViewById(R.id.editTextTituloInsertar);
                autor = (EditText) findViewById(R.id.editTextAutorInsertar);
                editorial = (EditText) findViewById(R.id.editTextEditorialInsertar);
                anio = (EditText) findViewById(R.id.editTextAnioInsertar);


            objetImageView = (ImageView) findViewById(R.id.imageView2);



            btnAgregar.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {




    ConexionSQLiteHelper admin = new ConexionSQLiteHelper(InsertarDatos.this,
            "Libro", null, 1);
    SQLiteDatabase bd = admin.getWritableDatabase();

    ContentValues registro = new ContentValues();

    registro.put("Titulo", String.valueOf(titulo.getText()));
    registro.put("Autor", String.valueOf(autor.getText()));
    registro.put("Editorial", String.valueOf(editorial.getText()));
   registro.put("Anio", String.valueOf(anio.getText()));



    if(imageToStore != null) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream(20480);
        imageToStore.compress(Bitmap.CompressFormat.PNG, 0, baos);
        byte[] blob = baos.toByteArray();

        registro.put("imagen", blob);
    }

    bd.insert("Libro", null, registro);

    bd.close();
    Toast.makeText(InsertarDatos.this, "Se cargaron los datos de: " + titulo.getText() + " " + autor.getText() + " " + editorial.getText(),
            Toast.LENGTH_SHORT).show();

    System.out.println(

            "Se cargaron los datos del artículo" + titulo.getText() + " " + autor.getText() + " " + editorial.getText()

    );

    Intent intent = new Intent(InsertarDatos.this, MainActivity.class);
    startActivity(intent);




                }


            });




        }


    public void chooseImage(View objectView){
        try{
            Intent objectIntent=new  Intent();
            objectIntent.setType("image/*");
            objectIntent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(objectIntent,PICK_IMAGE_REQUEST);



        }catch (Exception e){

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try{
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null){
                imageFilePath=data.getData();
                imageToStore= MediaStore.Images.Media.getBitmap(getContentResolver(),imageFilePath);
                objetImageView.setImageBitmap(imageToStore);


            }
        }catch (Exception e){

        }




    }

    }

