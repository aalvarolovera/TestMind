package com.testmind.al.testmind;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NuevaPreguntaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_pregunta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        EditText enun =  (EditText) findViewById(R.id.enunciadoPregunta);
        EditText preCor =  (EditText) findViewById(R.id.respuestaCorrecta);
        EditText preIn1 =  (EditText) findViewById(R.id.respuestaIncorrecta1);
        EditText preIn2 =  (EditText) findViewById(R.id.respuestaIncorrecta2);
        EditText preIn3 =  (EditText) findViewById(R.id.respuestaIncorrecta3);
        Button guardar = (Button)findViewById(R.id.buttonGuardar);
         //Me tengo que declarar el boton
       // comprobarCamposPre(enun,preCor,preIn1,preIn2,preIn3);
        comprobarCamposPre(enun, preCor, preIn1,
                 preIn2,  preIn3, guardar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void comprobarCamposPre(EditText enun, EditText preCor, EditText preInc1,
                                   EditText preInc2, EditText preInc3, Button guardar){
        if(enun.getText().toString().isEmpty()){
            guardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Tienes que rellenar el enunciado", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }else if(preCor.getText().toString().isEmpty()){
            guardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Tienes que rellenar la pregunta correcta", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }else if(preInc1.getText().toString().isEmpty()){
            guardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Tienes que rellenar la pregunta incorrecta 1", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }else if(preInc2.getText().toString().isEmpty()){
            guardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Tienes que rellenar la pregunta incorrecta 2", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }else if(preInc3.getText().toString().isEmpty()) {
            guardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Tienes que rellenar la pregunta incorrecta 3", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }else if(enun.getText().toString().isEmpty()||preCor.getText().toString().isEmpty()||
                preInc1.getText().toString().isEmpty()||preInc2.getText().toString().isEmpty()||
                preInc3.getText().toString().isEmpty()){
                guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(view, "Tienes varios campos sin rellenar", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    }
                });
        }
    }

}
