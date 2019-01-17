package com.testmind.al.testmind;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import static com.testmind.al.testmind.Constantes.*;

public class Repositorio {
    private static Repositorio miRepositorio;
    private ArrayList<Pregunta> todasPreguntas;
    private static SQLiteDatabase db;
    private static TestMindSQLite tmSQLite;
    //private  String ruta="/sdcard/BaseDatos/"+ "Preguntas" +".db";
   // private  String ruta= RUTA + NOMBREBBDD +".db";
    private  String ruta= NOMBREBBDD;
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
            db.execSQL("INSERT INTO Preguntas (enunciado,categoria,respuestaCorrecta,respuestaIncorrecta1,respuestaIncorrecta2,respuestaIncorrecta3, imagen) " +
                    "VALUES ('" + p.getEnunciado()
                    + "','" + p.getCategoria() + "','" + p.getPreguntaCorrecta() + "','"
                    + p.getPreguntaIncorrecta1() + "','" + p.getPreguntaIncorrecta2() + "','"
                    + p.getPreguntaIncorrecta3() + "','" + p.getImagen()
                    + "') ");
            exito=true;
        }
        db.close();
        return exito;

    }

    /**
     * Recupera un ArrayList<Pregunta> con todas las preguntas de la BBDD
     * @param contexto
     * @return
     */
    public ArrayList<Pregunta> getPreguntasBBDD(Context contexto){
        tmSQLite=new TestMindSQLite(contexto,ruta,null,1);
        db=tmSQLite.getReadableDatabase();
        ArrayList<Pregunta> itemsPregunta=null;
        if(db!=null){

            itemsPregunta=new ArrayList<Pregunta>();

            String[] campos = new String[] {"rowId", "enunciado","categoria","respuestaCorrecta","respuestaIncorrecta1","respuestaIncorrecta2",
                    "respuestaIncorrecta3","imagen"};

            Cursor c = db.query("Preguntas", campos, null, null, null, null, null);
            //Nos aseguramos de que existe al menos un registro
            if (c.moveToFirst()) {

                //Recorremos el cursor hasta que no haya más registros
                do {
                    //int id= c.getInt(c.getColumnIndex("id"));
                    int id= c.getInt(0);
                    String enunciado = c.getString(c.getColumnIndex("enunciado"));
                    String categoria= c.getString(c.getColumnIndex("categoria"));
                    String respuestaCorrecta = c.getString(c.getColumnIndex("respuestaCorrecta"));
                    String respuestaIncorrecta1= c.getString(c.getColumnIndex("respuestaIncorrecta1"));
                    String respuestaIncorrecta2 = c.getString(c.getColumnIndex("respuestaIncorrecta2"));
                    String respuestaIncorrecta3= c.getString(c.getColumnIndex("respuestaIncorrecta3"));
                    String imagen = c.getString(c.getColumnIndex("imagen"));

                    Pregunta pregunta= new Pregunta(id,enunciado,categoria,respuestaCorrecta,respuestaIncorrecta1,respuestaIncorrecta2,respuestaIncorrecta3,imagen);
                    itemsPregunta.add(pregunta);
                } while(c.moveToNext());
            }
            db.close();
           return itemsPregunta;
        }
        db.close();
        return itemsPregunta;
    }

    /**
     * Recupera una Pregunta de la BBDD con un Id igual al introducido
     * @param id
     * @param contexto
     * @return Pregunta
     */
    public Pregunta getPreguntaXid(int id,Context contexto){
        tmSQLite=new TestMindSQLite(contexto,ruta,null,1);
        db=tmSQLite.getReadableDatabase();
        Pregunta pregunta=null;
        if(db!=null){

            String[] campos = new String[] {"enunciado","categoria","respuestaCorrecta","respuestaIncorrecta1","respuestaIncorrecta2",
                    "respuestaIncorrecta3","imagen"};
            String[] args=new String[]{String.valueOf(id)};

            Cursor c = db.query( "Preguntas", campos, "rowId=?", args, null, null, null);

            //Nos aseguramos de que existe al menos un registro
            if (c.moveToFirst()) {

                //Recorremos el cursor hasta que no haya más registros
                do {
                    //int id= c.getInt(c.getColumnIndex("id"));

                        String enunciado = c.getString(c.getColumnIndex("enunciado"));
                        String categoria = c.getString(c.getColumnIndex("categoria"));
                        String respuestaCorrecta = c.getString(c.getColumnIndex("respuestaCorrecta"));
                        String respuestaIncorrecta1 = c.getString(c.getColumnIndex("respuestaIncorrecta1"));
                        String respuestaIncorrecta2 = c.getString(c.getColumnIndex("respuestaIncorrecta2"));
                        String respuestaIncorrecta3 = c.getString(c.getColumnIndex("respuestaIncorrecta3"));
                        String imagen= c.getString(c.getColumnIndex("imagen"));

                        pregunta = new Pregunta(id, enunciado, categoria, respuestaCorrecta, respuestaIncorrecta1, respuestaIncorrecta2, respuestaIncorrecta3,imagen);
                        // Repositorio.getRepositorio().getTodasPreguntas().add(pregunta);
                        db.close();
                        return pregunta;
                } while(c.moveToNext());

            }
            db.close();
            return pregunta;

        }
        db.close();
        return pregunta;
    }

    /**
     * Recupera de la BBDD las categorias distintas de todas las Preguntas
     * @param contexto
     * @return ArrayList<String>
     */
    public ArrayList<String> getCategoriasBBDD(Context contexto){

        tmSQLite=new TestMindSQLite(contexto,ruta,null,1);
        db=tmSQLite.getReadableDatabase();
        ArrayList<String> itemsCategoria=null;

        if(db!=null){
            itemsCategoria=new ArrayList<String>();
            String[] campos = new String[] {"categoria"};

            Cursor c = db.query(true, "Preguntas",campos, null, null, null, null, null, null);
            //Nos aseguramos de que existe al menos un registro
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    String categoria= c.getString(c.getColumnIndex("categoria"));
                    itemsCategoria.add(categoria);
                } while(c.moveToNext());
            }
            db.close();
            return itemsCategoria;
        }
        db.close();
        return itemsCategoria;

    }

    /**
     * Hace un Update de Pregunta cogiendo como clave su Id
     * @param pregunta
     * @param contexto
     * @return
     */
    public boolean updatePregunta(Pregunta pregunta, Context contexto){
        boolean exito=false;
        tmSQLite=new TestMindSQLite(contexto,ruta,null,1);
        db=tmSQLite.getWritableDatabase();
        //Establecemos los campos-valores a actualizar
        ContentValues valores = new ContentValues();
        String[] args=new String[]{String.valueOf(pregunta.getId())};
        //valores.put("id",pregunta.getId());
        valores.put("enunciado",pregunta.getEnunciado());
        valores.put("categoria",pregunta.getCategoria());
        valores.put("respuestaCorrecta",pregunta.getPreguntaCorrecta());
        valores.put("respuestaIncorrecta1",pregunta.getPreguntaIncorrecta1());
        valores.put("respuestaIncorrecta2",pregunta.getPreguntaIncorrecta2());
        valores.put("respuestaIncorrecta3",pregunta.getPreguntaIncorrecta3());
        valores.put("imagen",pregunta.getImagen());
        //Actualizamos el registro en la base de datos
        if(db.update("Preguntas", valores, "rowId=?",args)!=-1){
            exito=true;
        }
        db.close();
        return exito;
    }

    public boolean deletePregunta(Pregunta pregunta, Context contexto){
        tmSQLite=new TestMindSQLite(contexto,ruta,null,1);
        db=tmSQLite.getWritableDatabase();
        boolean exito=false;
        String[] args=new String[]{String.valueOf(pregunta.getId())};

        if(db.delete("Preguntas",  "rowId=?",args)!=-1){
            exito=true;
        }
        db.close();
        return exito;
    }

    /**
     * Guarda Pregunta en el ArrayList de Preguntas del Repositorio
     * @param pregunta
     * @return boolean
     */
    public boolean createPregunta(Pregunta pregunta){
        return Repositorio.getRepositorio().getTodasPreguntas().add(pregunta);
}


}
