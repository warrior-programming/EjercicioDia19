package com.prueba.ejerciciodia19;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CustomAdapter extends BaseAdapter{


    final Context context;
    final List<Objeto> lista;
    Button btnBorrar;
    Button btnSeleccion;


    public CustomAdapter(Context context, List<Objeto> lista) {
        this.context = context;
        this.lista = lista;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        ImageView ImageViewObjeto;
        TextView TextViewNombre;


        Objeto c=lista.get(i);

        if (view==null){

           view = LayoutInflater.from(context).inflate(R.layout.listview_objetos,null);

           ImageViewObjeto = view.findViewById(R.id.imageViewObjeto);
            TextViewNombre = view.findViewById(R.id.textViewNombre);
            btnBorrar = view.findViewById(R.id.btnBorrarListView);
            btnSeleccion = view.findViewById(R.id.buttonSeleccion);


            ConexionSQLiteHelper admin = new ConexionSQLiteHelper(context.getApplicationContext(),
                    "Libro", null, 1);
            SQLiteDatabase bd = admin.getWritableDatabase();

            Cursor fila = bd.rawQuery(
                  //  "select Imagen from Perfil", null);
            "select Imagen from Libro where Id=" + c.id, null);


           if (fila.moveToNext()) {



try{
                    byte[] imagen = fila.getBlob(0);
                Bitmap bitmap = BitmapFactory.decodeByteArray(imagen, 0 ,imagen.length);
                ImageViewObjeto.setImageBitmap(bitmap);}
catch (Exception e) {

}
     }
            bd.close();










                TextViewNombre.setText("Titulo: " + c.titulo + " " + "Autor: " + c.autor + " " + "Editorial: " + c.editorial + " " + "Año: " + c.anio  );


//btnEditar.setTag(i);
            btnSeleccion.setOnClickListener(v -> {



                int numEnteroId = c.id;
                String numCadenaId= String.valueOf(numEnteroId);

                int numEnteroAnio = c.anio;
                String numCadenaAnio= String.valueOf(numEnteroAnio);
                Intent intent = new Intent(context, EditarDatos.class);


                intent.putExtra("id", numCadenaId);
                intent.putExtra("titulo", c.titulo);
                intent.putExtra("autor", c.autor);
                intent.putExtra("editorial", c.editorial);
                intent.putExtra("anio",numCadenaAnio);


                context.startActivity(intent);


            });

            btnBorrar.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){

                    /*
                    int numEntero = c.id;
                    String numCadena= String.valueOf(numEntero);


                    Intent intent = new Intent(context, EditarDatos.class);
                    intent.putExtra("id", c.id);
                    intent.putExtra("nombre", c.nombre);
                    intent.putExtra("telefono", c.telefono);
                    intent.putExtra("correo", c.correo);


                    context.startActivity(intent);

*/



                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:

                                    ConexionSQLiteHelper admin = new ConexionSQLiteHelper(v.getContext(),
                                            "Libro", null, 1);
                                    SQLiteDatabase bd = admin.getWritableDatabase();

                                    int cant = bd.delete("Libro", "Id=" + c.id, null);
                                    bd.close();

                                    if (cant == 1)
                                        Toast.makeText(v.getContext(), "Se borró el artículo con dicho código",
                                                Toast.LENGTH_SHORT).show();
                                    else {
                                        Toast.makeText(v.getContext(), "No existe un artículo con dicho código",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                    Intent intent = new Intent(context, MainActivity.class);
                                    context.startActivity(intent);


                                   // break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    //break;
                            }
                        }
                    };



                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Desea eliminar el libro?").setPositiveButton("Si", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();







                }
            });


            return view;
        }



        return view;
    }


}
