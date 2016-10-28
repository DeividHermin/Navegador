package com.example.deivi.navegador3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Vector;


public class EjemploBD extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "nombreDeTuBaseDatos.db";

    public static final String TABLA_NOMBRES = "nombres";
    public static final String COLUMNA_ID = "_id";
    public static final String COLUMNA_NOMBRE = "nombre";
    private static final String SQL_CREAR = "create table "
            + TABLA_NOMBRES + "(" + COLUMNA_ID
            + " integer primary key autoincrement, " + COLUMNA_NOMBRE
            + " text not null);";

    public EjemploBD(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREAR);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
    }

    public long agregar(String nombre){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMNA_NOMBRE, nombre);

        long newRowId;
        newRowId = db.insert(TABLA_NOMBRES, null,values);


        //newRowId = db.insert("Kyoda");
        db.close();
        return newRowId;
    }

    public String obtener(int id){

        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {COLUMNA_ID, COLUMNA_NOMBRE};

        Cursor cursor = db.query(TABLA_NOMBRES, projection, " _id = ?", new String[] { String.valueOf(id) },
                        null,
                        null,
                        null,
                        null);


        if (cursor != null)
            cursor.moveToFirst();

        //System.out.println("El nombre es " +  cursor.getString(1) );
        db.close();
        return cursor.getString(1);

    }

    public boolean eliminar(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.delete(TABLA_NOMBRES,
                    " _id = ?",
                    new String[] { String.valueOf (id ) });
            db.close();
            return true;

        }catch(Exception ex){
            return false;
        }
    }

    public Vector obtenerSugerencias(){
        Vector v = new Vector();
        SQLiteDatabase db = this.getReadableDatabase();
        String campos[]={COLUMNA_ID, COLUMNA_NOMBRE};
        Cursor c;
        if(db != null){

            c = db.query(TABLA_NOMBRES, campos, null, null, null, null, null, null);

            if(c != null){
                c.moveToFirst();
                do{
                    v.add(c.getString(1));
                }while(c.moveToNext());
            }
        }
        return v;
    }

}
