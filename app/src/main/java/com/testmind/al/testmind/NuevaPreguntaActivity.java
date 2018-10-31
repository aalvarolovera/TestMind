package com.testmind.al.testmind;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class NuevaPreguntaActivity extends AppCompatActivity {

    final private int CODE_WRITE_EXTERNAL_STORAGE_PERMISSION = 123;
    private Context myContext;
    private ConstraintLayout constraintLayoutNuevaPreguntaActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_pregunta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Almacenamos el contexto de la actividad para utilizar en las clases internas
        myContext = this;
        // Recuperamos el Layout donde mostrar el Snackbar con las notificaciones
        constraintLayoutNuevaPreguntaActivity = findViewById(R.id.constraintLayoutNuevaPreguntaActivity);

        Spinner spinner = (Spinner) findViewById(R.id.categoriaPregunta);
        String[] letra = {"Programación","Cosas"};
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, letra));


         //Me tengo que declarar el boton
       // comprobarCamposPre(enun,preCor,preIn1,preIn2,preIn3);
        //Tienen que estar en el onclick no aqui


        Button guardar = (Button) findViewById(R.id.buttonGuardar);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText enun =  (EditText) findViewById(R.id.enunciadoPregunta);
                EditText preCor =  (EditText) findViewById(R.id.respuestaCorrecta);
                EditText preIn1 =  (EditText) findViewById(R.id.respuestaIncorrecta1);
                EditText preIn2 =  (EditText) findViewById(R.id.respuestaIncorrecta2);
                EditText preIn3 =  (EditText) findViewById(R.id.respuestaIncorrecta3);
                Button guardar = (Button)findViewById(R.id.buttonGuardar);
                comprobarCamposPre(enun, preCor, preIn1,
                        preIn2,  preIn3, guardar);
            }
        });

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
        }else if(!enun.getText().toString().isEmpty()&&
                !preCor.getText().toString().isEmpty()&&
                !preInc1.getText().toString().isEmpty()&&
                !preInc2.getText().toString().isEmpty()&&
                !preInc3.getText().toString().isEmpty()){
                guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //Meter metodos para pedir permisos de escritura


                        Snackbar.make(view, "Guardado", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    }
                });
        }
    }



    public void comprobarPermisoEscritura(View view) {
        int WriteExternalStoragePermission = ContextCompat.checkSelfPermission(myContext, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        MyLog.d("MainActivity", "WRITE_EXTERNAL_STORAGE Permission: " + WriteExternalStoragePermission);

        if (WriteExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
            // Permiso denegado
            // A partir de Marshmallow (6.0) se pide aceptar o rechazar el permiso en tiempo de ejecución
            // En las versiones anteriores no es posible hacerlo
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                ActivityCompat.requestPermissions(NuevaPreguntaActivity.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, CODE_WRITE_EXTERNAL_STORAGE_PERMISSION);
                // Una vez que se pide aceptar o rechazar el permiso se ejecuta el método "onRequestPermissionsResult" para manejar la respuesta
                // Si el usuario marca "No preguntar más" no se volverá a mostrar este diálogo
            } else {
                Snackbar.make(view, getResources().getString(R.string.write_permission_denied), Snackbar.LENGTH_LONG)
                        .show();
            }
        } else {
            // Permiso aceptado
            Snackbar.make(view, getResources().getString(R.string.write_permission_granted), Snackbar.LENGTH_LONG)
                    .show();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case CODE_WRITE_EXTERNAL_STORAGE_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permiso aceptado
                    Snackbar.make(constraintLayoutNuevaPreguntaActivity, getResources().getString(R.string.write_permission_accepted), Snackbar.LENGTH_LONG)
                            .show();
                } else {
                    // Permiso rechazado
                    Snackbar.make(constraintLayoutNuevaPreguntaActivity, getResources().getString(R.string.write_permission_not_accepted), Snackbar.LENGTH_LONG)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}
