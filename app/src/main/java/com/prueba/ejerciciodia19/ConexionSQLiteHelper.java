package com.prueba.ejerciciodia19;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class ConexionSQLiteHelper extends SQLiteOpenHelper {


    public ConexionSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL("create table Libro (Id INTEGER PRIMARY KEY AUTOINCREMENT, Titulo TEXT,Autor TEXT, Editorial TEXT, Anio INTEGER, Imagen BLOB)");
        db.execSQL("INSERT INTO Libro (Titulo,Autor,Editorial,Anio, Imagen)" +
                "VALUES " +
                "('100 Anios de Soledad','Gabriel García','EDICIONES LA CUEVA',1967,null)," +
                "('Don Quijote de la Mancha','Miguel de Cervantes','Editorial Juventud',1605,null), " +
                "('La Divina Comedia','Dante Alighieri','Porrúa ', 1307,null)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAntigua, int versionNueva) {
        db.execSQL("DROP TABLE IF EXISTS Libro");



        onCreate(db);
    }

}