package com.prueba.ejerciciodia19;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class EditarDatos extends AppCompatActivity {

    Button btnGuardarEditar;

    EditText titulo;
    EditText autor;
    EditText editorial;
    EditText anio;

    Bundle datos;
    String notas;


ImageView objetImageView;
private static final int PICK_IMAGE_REQUEST = 100;
private Uri imageFilePath;
private Bitmap imageToStore;

    public EditarDatos() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_datos);
        btnGuardarEditar = (Button) findViewById(R.id.btnGuardarEditar);

        titulo = (EditText) findViewById(R.id.editTextTituloEditar);
        autor = (EditText) findViewById(R.id.editTextAutorEditar);
        editorial = (EditText) findViewById(R.id.editTextEditorialEditar);
        anio = (EditText) findViewById(R.id.editTextAnioEditar);
objetImageView = (ImageView) findViewById(R.id.imageEditar);


        /*Intent intent = getIntent();
        if(intent.hasExtra(Intent.EXTRA_TEXT)) {
              notas = (String ) intent.getSerializableExtra(Intent.EXTRA_TEXT);
            nombre.setText(notas);
        }*/






        Intent recibir = getIntent();
        String idRecibido = recibir.getStringExtra("id");
        String  tituloRecibido = recibir.getStringExtra("titulo");
        String autorRecibido = recibir.getStringExtra("autor");
        String editorialRecibido = recibir.getStringExtra("editorial");
        String anioRecibido = recibir.getStringExtra("anio");





        titulo.setText(tituloRecibido);
        autor.setText(autorRecibido);
        editorial.setText(editorialRecibido);
        anio.setText( anioRecibido);



        ConexionSQLiteHelper admin = new ConexionSQLiteHelper(getApplicationContext(),
                "Libro", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        Cursor fila = bd.rawQuery(
                //  "select Imagen from Perfil", null);
                "select Imagen from Libro where Id=" + idRecibido, null);


        if (fila.moveToNext()) {



            try{
                byte[] imagen = fila.getBlob(0);
                Bitmap bitmap = BitmapFactory.decodeByteArray(imagen, 0 ,imagen.length);
                objetImageView.setImageBitmap(bitmap);}
            catch (Exception e) {

            }
        }


        btnGuardarEditar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){





                    ConexionSQLiteHelper admin = new ConexionSQLiteHelper(EditarDatos.this,
                            "Libro", null, 1);
                    SQLiteDatabase bd = admin.getWritableDatabase();

                    ContentValues registro = new ContentValues();


                    registro.put("Titulo", titulo.getText().toString());
                    registro.put("Autor", autor.getText().toString());
                    registro.put("Editorial", editorial.getText().toString());
                registro.put("Anio", anio.getText().toString());

                if (imageToStore != null) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream(20480);
                    Bitmap bitmap = null;
                    imageToStore.compress(Bitmap.CompressFormat.PNG, 0, baos);
                    byte[] blob = baos.toByteArray();

                    registro.put("imagen", blob);
                }

                    System.out.println("VALORRRRRRR" + notas);
                    //System.out.println("VALUESSSS" + " " + String.valueOf(nombre.getText()) + " " + String.valueOf(telefono.getText()) + " " + String.valueOf(correo.getText()));

                    int cant = bd.update("Libro", registro, "Id=" + idRecibido, null);
                    bd.close();


                    if (cant == 1) {
                        Toast.makeText(EditarDatos.this, "se modificaron los datos", Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        Toast.makeText(EditarDatos.this, "no existe un artículo con el código ingresado",
                                Toast.LENGTH_SHORT).show();
                    }


                    Intent intent = new Intent(EditarDatos.this, MainActivity.class);
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