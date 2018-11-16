package com.testmind.al.testmind;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class Repositorio {
    private static Repositorio miRepositorio;
    private ArrayList<Pregunta> todasPreguntas;
//REPASAR LO DEL ID
    public static Repositorio getRepositorio() {
        if (miRepositorio == null) {
            miRepositorio = new Repositorio();
        }
        return miRepositorio;
    }
    private Repositorio() {
        todasPreguntas = new ArrayList<>();
    }

    public ArrayList<Pregunta> getTodasPreguntas() {
        return todasPreguntas;
    }

    public boolean insertPregunta(SQLiteDatabase db, int id, String enunciado, String categoria, String respuestaCorrecta,
                                  String respuestaIncorrecta1, String respuestaIncorrecta2, String respuestaIncorrecta3){
        boolean exito=false;
        //Creamos el registro a insertar como objeto ContentValues
        ContentValues nuevoRegistro = new ContentValues();
        nuevoRegistro.put("id",id);
        nuevoRegistro.put("enunciado",enunciado);
        nuevoRegistro.put("categoria",categoria);
        nuevoRegistro.put("respuestaCorrecta",respuestaCorrecta);
        nuevoRegistro.put("respuestaIncorrecta1",respuestaIncorrecta1);
        nuevoRegistro.put("respuestaIncorrecta2",respuestaIncorrecta2);
        nuevoRegistro.put("respuestaIncorrecta3",respuestaIncorrecta3);
        //Insertamos el registro en la base de datos
        if(db.insert("Preguntas", null, nuevoRegistro)!=-1){
            exito=true;
        }
        return exito;
    }

    public boolean updatePregunta(SQLiteDatabase db,int id, String enunciado, String categoria, String respuestaCorrecta,
                                  String respuestaIncorrecta1, String respuestaIncorrecta2, String respuestaIncorrecta3){
        boolean exito=false;
        //Establecemos los campos-valores a actualizar
        ContentValues valores = new ContentValues();
        valores.put("id",id);
        valores.put("enunciado",enunciado);
        valores.put("categoria",categoria);
        valores.put("respuestaCorrecta",respuestaCorrecta);
        valores.put("respuestaIncorrecta1",respuestaIncorrecta1);
        valores.put("respuestaIncorrecta2",respuestaIncorrecta2);
        valores.put("respuestaIncorrecta3",respuestaIncorrecta3);
        //Actualizamos el registro en la base de datos
       if(db.update("Preguntas", valores, "id="+id, null)!=-1){
          exito=true;
       }
        return exito;
    }

    public boolean deletePregunta(SQLiteDatabase db,int id, String enunciado, String categoria, String respuestaCorrecta,
                                  String respuestaIncorrecta1, String respuestaIncorrecta2, String respuestaIncorrecta3){
        return false;
    }

    public boolean selectPregunta(SQLiteDatabase db){

        boolean exito=false;
        String[] campos = new String[] {"id", "enunciado","categoria","respuestaCorrecta","respuestaIncorrecta1","respuestaIncorrecta2",
        "respuestaIncorrecta3"};
        String[] args = new String[] {"pre1"};

        Cursor c = db.query("Preguntas", campos, "id=?", args, null, null, null);
        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            exito=true;
            //Recorremos el cursor hasta que no haya más registros
            do {
                //int id= c.getInt(c.getColumnIndex("id"));
                String enunciado = c.getString(c.getColumnIndex("enunciado"));
                String categoria= c.getString(c.getColumnIndex("categoria"));
                String respuestaCorrecta = c.getString(c.getColumnIndex("respuestaCorrecta"));
                String respuestaIncorrecta1= c.getString(c.getColumnIndex("respuestaIncorrecta1"));
                String respuestaIncorrecta2 = c.getString(c.getColumnIndex("respuestaIncorrecta2"));
                String respuestaIncorrecta3= c.getString(c.getColumnIndex("respuestaIncorrecta3"));

                Pregunta pregunta= new Pregunta(enunciado,categoria,respuestaCorrecta,respuestaIncorrecta1,respuestaIncorrecta2,respuestaIncorrecta3);
                getRepositorio().getTodasPreguntas().add(pregunta);
            } while(c.moveToNext());
        }
        return exito;
    }

    //public void cargarTodasPreguntas(){ }

}
