package com.testmind.al.testmind;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class Repositorio {
    private static Repositorio miRepositorio;
    private ArrayList<Pregunta> todasPreguntas;
    private static SQLiteDatabase db;
    private static TestMindSQLite tmSQLite;
    private  String ruta="/sdcard/BaseDatos/"+ "Preguntas" +".db";
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

    public void abrirBDEscritura(Context contexto){
        tmSQLite=new TestMindSQLite(contexto,ruta,null,1);
        db=tmSQLite.getWritableDatabase();
    }

    public void cerrarBBDD(){
        db.close();
    }

    public ArrayList<Pregunta> getTodasPreguntas() {
        return todasPreguntas;
    }

    public boolean insertPregunta(Context contexto,Pregunta p){
        boolean exito=false;

        tmSQLite=new TestMindSQLite(contexto,ruta,null,1);
        db=tmSQLite.getWritableDatabase();

        if(db!=null) {

            db.execSQL("INSERT INTO Preguntas (enunciado,categoria,respuestaCorrecta,respuestaIncorrecta1,respuestaIncorrecta2,respuestaIncorrecta3) VALUES ('" + p.getEnunciado()
                    + "','" + p.getCategoria() + "','" + p.getPreguntaCorrecta() + "','" + p.getPreguntaIncorrecta1() + "','" + p.getPreguntaIncorrecta2() + "','" + p.getPreguntaIncorrecta3() + "') ");
        exito=true;
        }
        return exito;
    }

    public boolean updatePregunta(Pregunta pregunta){
        boolean exito=false;
        //Establecemos los campos-valores a actualizar
        ContentValues valores = new ContentValues();

        valores.put("id",pregunta.getId());
        valores.put("enunciado",pregunta.getEnunciado());
        valores.put("categoria",pregunta.getCategoria());
        valores.put("respuestaCorrecta",pregunta.getPreguntaCorrecta());
        valores.put("respuestaIncorrecta1",pregunta.getPreguntaIncorrecta1());
        valores.put("respuestaIncorrecta2",pregunta.getPreguntaIncorrecta2());
        valores.put("respuestaIncorrecta3",pregunta.getPreguntaIncorrecta3());
        //Actualizamos el registro en la base de datos
        if(db.update("Preguntas", valores, "id="+pregunta.getId(), null)!=-1){
            exito=true;
        }
        return exito;
    }
    public boolean updatePregunta(int id, String enunciado, String categoria, String respuestaCorrecta,
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

    public boolean selectPregunta(){

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
                int id= c.getInt(c.getColumnIndex("id"));
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

    public boolean createPregunta(Pregunta pregunta){
        return Repositorio.getRepositorio().getTodasPreguntas().add(pregunta);
}
    //public void cargarTodasPreguntas(){ }

}
