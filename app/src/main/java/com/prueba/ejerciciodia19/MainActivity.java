package com.prueba.ejerciciodia19;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //ArrayList<String>
    ListView ListViewObjeto;
    List<Objeto> lista;

    Integer id;
    String titulo;
    String autor;
    String editorial;
    int anio;
    Bitmap imagen;
    Button btnEditar;
    Button btnInsertar;
    Button btnSeleccion;
    Button btnBusqueda;
    ArrayList<String> listaBusqueda = new ArrayList<>();
    TextView txtViewListaBusqueda;

    EditText editTextBusquedaLibro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListViewObjeto = findViewById(R.id.listViewObjeto);
        btnInsertar = findViewById(R.id.btnNuevoPerfil);
        btnBusqueda = findViewById(R.id.btnBusqueda);

   editTextBusquedaLibro = findViewById(R.id.editTextBusquedaLibro);




   CustomAdapter adapter = new CustomAdapter(MainActivity.this, GetData());

        ListViewObjeto.setAdapter(adapter);




      /*  ListViewObjeto.setOnItemClickListener((parent, view, i, id) -> {
            Objeto c=lista.get(i);
            Toast.makeText(getBaseContext(),c.nombre,Toast.LENGTH_SHORT).show();
        });
*/
       /*ListViewObjeto.setOnItemClickListener((parent, view, i, id) -> {
            Objeto c=lista.get(i);


           int numEntero = c.id;
           String numCadena= String.valueOf(numEntero);


           Intent intent = new Intent(MainActivity.this, EditarDatos.class);
           intent.putExtra("id", c.id);
           intent.putExtra("nombre", c.nombre);
           intent.putExtra("telefono", c.telefono);
           intent.putExtra("correo", c.correo);
           startActivity(intent);

            Toast.makeText(getBaseContext(),c.nombre,Toast.LENGTH_SHORT).show();

        });*/




        btnInsertar.setOnClickListener(view -> {

            Intent intent = new Intent(MainActivity.this, InsertarDatos.class);
            //intent.putExtras("datos", lista.get(i));
            startActivity(intent);


        });


        btnBusqueda.setOnClickListener(view -> {
            CustomAdapter adapter2 = new CustomAdapter(MainActivity.this, GetSearch());

            ListViewObjeto.setAdapter(adapter2);
        });


    }

    public List<Objeto> GetSearch(){
        lista = new ArrayList<>();




        ConexionSQLiteHelper admin = new ConexionSQLiteHelper(this,

                "Libro", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();


        Cursor fila = bd.rawQuery(



                "select * from Libro " +
                        "where " +
                        "Titulo LIKE " + "'%" + editTextBusquedaLibro.getText() +  "%'" +
                        "or Autor LIKE " + "'%" + editTextBusquedaLibro.getText() + "%'"+
                        "or Editorial LIKE " + "'%" + editTextBusquedaLibro.getText() + "%'"+
                        "or Anio LIKE " + "'%" + editTextBusquedaLibro.getText() + "%'",
                null);


        Toast.makeText(this, "LIBROOO" + editTextBusquedaLibro.getText(), Toast.LENGTH_SHORT).show();


            while (fila.moveToNext()) {


                id = fila.getInt(0);
                titulo = fila.getString(1);
                autor =  fila.getString(2);
                editorial = fila.getString(3);
                anio = fila.getInt(4);



                   lista.add(new Objeto(id,imagen, titulo,autor,editorial, anio));


                   //Id    , Titulo ,Autor , Editorial , Anio , Imagen


                System.out.println("MYITEMS" + id + titulo + autor + editorial + anio);
            }



           /* Toast.makeText(this, "No existe un artículo con dicha descripción",
                    Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(MainActivity.this, MainActivity.class);

            startActivity(intent);*/

        bd.close();

        return lista;

    }

    public List<Objeto> GetData() {
        lista = new ArrayList<>();


        ConexionSQLiteHelper admin = new ConexionSQLiteHelper(MainActivity.this,
                "Libro", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        Cursor fila = bd.rawQuery(
                "select * from Libro" , null);

        while (fila.moveToNext()) {

            id = fila.getInt(0);
            titulo = fila.getString(1);
            autor =  fila.getString(2);
            editorial = fila.getString(3);
            anio = fila.getInt(4);
//id, Titulo,Autor,Editorial,Anio, Imagen


//int id, Bitmap imagen, String titulo, String autor, String editorial, int anio
            lista.add(new Objeto(id, imagen, titulo,autor,editorial, anio));
            System.out.println("HOLAAAA" + lista);
        }
        bd.close();





        return lista;
    }




}