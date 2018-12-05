package com.testmind.al.testmind;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class NuevaPreguntaActivity extends AppCompatActivity {

    final private int CODE_WRITE_EXTERNAL_STORAGE_PERMISSION = 123;
    private Context myContext;
    private ConstraintLayout constraintLayoutNuevaPreguntaActivity;
    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    private Button anadirCategoria;

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


        //String[] letra = {"Programación","Cosas"};
        //spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, letra));



        // Definición de la lista de opciones
        ArrayList<String> categoria = Repositorio.getRepositorio().getCategoriasBBDD(myContext); //new ArrayList<String>();
        //items.add("Opción 1");


        // Definición del Adaptador que contiene la lista de opciones
        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, categoria);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        // Definición del Spinner
        spinner = (Spinner) findViewById(R.id.categoriaPregunta);
        spinner.setAdapter(adapter);

        // Definición de la acción del botón
        anadirCategoria = (Button) findViewById(R.id.anadirCategoria);
        anadirCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Recuperación de la vista del AlertDialog a partir del layout de la Actividad
                LayoutInflater layoutActivity = LayoutInflater.from(myContext);
                View viewAlertDialog = layoutActivity.inflate(R.layout.alert_dialog, null);

                // Definición del AlertDialog
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(myContext);

                // Asignación del AlertDialog a su vista
                alertDialog.setView(viewAlertDialog);

                // Recuperación del EditText del AlertDialog
                final EditText dialogInput = (EditText) viewAlertDialog.findViewById(R.id.dialogInput);

                // Configuración del AlertDialog
                alertDialog
                        .setCancelable(false)
                        // Botón Añadir
                        .setPositiveButton(getResources().getString(R.string.add),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        adapter.add(dialogInput.getText().toString());
                                        spinner.setSelection(adapter.getPosition(dialogInput.getText().toString()));
                                    }
                                })
                        // Botón Cancelar
                        .setNegativeButton(getResources().getString(R.string.cancel),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                })
                        .create()
                        .show();
            }
        });


        //Configuración de la Edición de Pregunta

        //Recuperamos la información pasada en el intent
        final Bundle bundle = this.getIntent().getExtras();

        //Construimos el mensaje a mostrar
       // txtSaludo.setText(bundle.getInt("id"));
        if(bundle!=null) {
            int id = bundle.getInt("id");

            Pregunta p = Repositorio.getRepositorio().getPreguntaXid(id, myContext);


            EditText enun = (EditText) findViewById(R.id.enunciadoPregunta);
            Spinner cate = (Spinner) findViewById(R.id.categoriaPregunta);
            EditText preCor = (EditText) findViewById(R.id.respuestaCorrecta);
            EditText preIn1 = (EditText) findViewById(R.id.respuestaIncorrecta1);
            EditText preIn2 = (EditText) findViewById(R.id.respuestaIncorrecta2);
            EditText preIn3 = (EditText) findViewById(R.id.respuestaIncorrecta3);
            Button guardar = (Button) findViewById(R.id.buttonGuardar);

            enun.setText(p.getEnunciado());
            cate.setSelection(Repositorio.getRepositorio().getCategoriasBBDD(myContext).indexOf(p.getCategoria()));
            preCor.setText(p.getPreguntaCorrecta());
            preIn1.setText(p.getPreguntaIncorrecta1());
            preIn2.setText(p.getPreguntaIncorrecta2());
            preIn3.setText(p.getPreguntaIncorrecta3());
        }


        final Button guardar = (Button) findViewById(R.id.buttonGuardar);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


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
                        Snackbar.make(constraintLayoutNuevaPreguntaActivity, getResources().getString(R.string.write_permission_denied), Snackbar.LENGTH_LONG)
                                .show();
                    }
                } else {
                    // Permiso aceptado
                    Snackbar.make(constraintLayoutNuevaPreguntaActivity, getResources().getString(R.string.write_permission_granted), Snackbar.LENGTH_LONG)
                            .show();
                }


                EditText enun =  (EditText) findViewById(R.id.enunciadoPregunta);
                Spinner cate = (Spinner)findViewById(R.id.categoriaPregunta);
                EditText preCor =  (EditText) findViewById(R.id.respuestaCorrecta);
                EditText preIn1 =  (EditText) findViewById(R.id.respuestaIncorrecta1);
                EditText preIn2 =  (EditText) findViewById(R.id.respuestaIncorrecta2);
                EditText preIn3 =  (EditText) findViewById(R.id.respuestaIncorrecta3);
                final Button guardar = (Button)findViewById(R.id.buttonGuardar);

                //Ocultar Teclado
                ocultarTeclado(enun);
                ocultarTeclado(preCor);
                ocultarTeclado(preIn1);
                ocultarTeclado(preIn2);
                ocultarTeclado(preIn3);
                ocultarTeclado(guardar);
               // comprobarCamposPre(constraintLayoutNuevaPreguntaActivity, enun, preCor, preIn1,
                      //  preIn2,  preIn3);
                //Que comprobar campos solo devuelva un true o false, que solo le entre el constrein layout global, y si
                //falta alguno mostrar el snack bar generico pero que no lo haga el metodo comprobar campos

                if(comprobarCamposPre(constraintLayoutNuevaPreguntaActivity, enun,cate, preCor, preIn1,
                        preIn2,  preIn3)){
                    Snackbar.make(view, "Guardado", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                   String enunciado= enun.getText().toString().trim();
                   String preguntaCorrecta=preCor.getText().toString().trim();
                   String categoria=cate.getSelectedItem().toString().trim();
                   String preguntaIn1=preIn1.getText().toString().trim();
                   String preguntaIn2=preIn2.getText().toString().trim();
                   String preguntaIn3=preIn3.getText().toString().trim();
                    //Bundle bd=new Bundle();

                    if(bundle==null) {

                        Pregunta p = new Pregunta(enunciado, categoria, preguntaCorrecta, preguntaIn1, preguntaIn2, preguntaIn3);

                        Repositorio.getRepositorio().insertPregunta(myContext, p);

                        Repositorio.getRepositorio().cerrarBBDD();

                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                startActivity(new Intent(getApplicationContext(), PreguntasActivity.class));
                                finish();
                                guardar.setActivated(false);
                            }
                        }, 1);

                    }else if(bundle!=null) {
                        Pregunta pregutaId = Repositorio.getRepositorio().getPreguntaXid(bundle.getInt("id"), myContext);


                        pregutaId.setEnunciado(enun.getText().toString().trim());
                        pregutaId.setCategoria(cate.getSelectedItem().toString().trim());
                        pregutaId.setPreguntaCorrecta(preCor.getText().toString().trim());
                        pregutaId.setPreguntaIncorrecta1(preIn1.getText().toString().trim());
                        pregutaId.setPreguntaIncorrecta2(preIn2.getText().toString().trim());
                        pregutaId.setPreguntaIncorrecta3(preIn3.getText().toString().trim());


                        Repositorio.getRepositorio().updatePregunta(pregutaId, myContext);
                        Repositorio.getRepositorio().cerrarBBDD();

                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                startActivity(new Intent(getApplicationContext(), PreguntasActivity.class));
                                finish();
                                guardar.setActivated(false);
                            }
                        }, 1);

                    }


                }else{
                    Snackbar.make(view, "Tienes que rellenar todos los campos", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
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

    public boolean comprobarCamposPre(ConstraintLayout constraintLayoutNuevaPreguntaActivity, EditText enun,Spinner cate, EditText preCor, EditText preInc1,
                                   EditText preInc2, EditText preInc3){
            boolean lleno=false;

            if(!enun.getText().toString().isEmpty()&&
                !preCor.getText().toString().isEmpty()&&
                !preInc1.getText().toString().isEmpty()&&
                !preInc2.getText().toString().isEmpty()&&
                !preInc3.getText().toString().isEmpty()&&
                 !cate.getSelectedItem().toString().isEmpty()){

            lleno=true;
        }
        return lleno;
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

    public void ocultarTeclado(View et){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
    }
    @Override
    protected void onStart() {
        MyLog.d("NuevaPreguntaActivity","Iniciando OnStart");
        super.onStart();

        MyLog.d("NuevaPreguntaActivity","Finalizado OnStart");
    }

    @Override
    protected void onStop() {
        MyLog.d("NuevaPreguntaActivity","Iniciando OnStop");
        super.onStop();

        MyLog.d("NuevaPreguntaActivity","Finalizado OnStop");
    }

    @Override
    protected void onPause() {
        MyLog.d("NuevaPreguntaActivity","Iniciando OnPause");
        super.onPause();

        MyLog.d("NuevaPreguntaActivity","Finalizado OnPause");
    }

    @Override
    protected void onRestart() {
        MyLog.d("NuevaPreguntaActivity","Iniciando OnRestart");
        super.onRestart();

        MyLog.d("NuevaPreguntaActivity","Finalizado OnRestar");
    }

    @Override
    protected void onResume() {
        MyLog.d("NuevaPreguntaActivity","Iniciando OnResume");
        super.onResume();

        MyLog.d("NuevaPreguntaActivity","Finalizado OnResume");
    }

    @Override
    protected void onDestroy() {
        MyLog.d("NuevaPreguntaActivity","Iniciando OnDestroy");
        super.onDestroy();

        MyLog.d("NuevaPreguntaActivity","Finalizado OnDestroy");
    }
}
