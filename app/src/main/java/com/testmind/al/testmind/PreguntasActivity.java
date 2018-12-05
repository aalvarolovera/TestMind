package com.testmind.al.testmind;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class PreguntasActivity extends AppCompatActivity {
    private ArrayList<Pregunta> preguntas;
    private Context myContext = this;
    final private int CODE_WRITE_EXTERNAL_STORAGE_PERMISSION = 123;
    private ConstraintLayout constraintLayoutPreguntasActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyLog.d("PreguntasActivity", "Iniciando OnCreate");

        setContentView(R.layout.activity_preguntas);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),NuevaPreguntaActivity.class));
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                 //       .setAction("Action", null).show();
            }
        });


/*

        int WriteExternalStoragePermission = ContextCompat.checkSelfPermission(myContext, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        MyLog.d("MainActivity", "WRITE_EXTERNAL_STORAGE Permission: " + WriteExternalStoragePermission);

        if (WriteExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
            // Permiso denegado
            // A partir de Marshmallow (6.0) se pide aceptar o rechazar el permiso en tiempo de ejecución
            // En las versiones anteriores no es posible hacerlo
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                ActivityCompat.requestPermissions(PreguntaActivity.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, CODE_WRITE_EXTERNAL_STORAGE_PERMISSION);
                // Una vez que se pide aceptar o rechazar el permiso se ejecuta el método "onRequestPermissionsResult" para manejar la respuesta
                // Si el usuario marca "No preguntar más" no se volverá a mostrar este diálogo
            } else {
                Snackbar.make(constraintLayoutPreguntasActivity, getResources().getString(R.string.write_permission_denied), Snackbar.LENGTH_LONG)
                        .show();
            }
        } else {
            // Permiso aceptado
            Snackbar.make(constraintLayoutPreguntasActivity, getResources().getString(R.string.write_permission_granted), Snackbar.LENGTH_LONG)
                    .show();
        }


       */

        MyLog.d("PreguntasActivity", "Finalizado OnCreate");
    }



    @Override
    protected void onStart() {
        MyLog.d("PreguntasActivity","Iniciando OnStart");
        super.onStart();

        MyLog.d("PreguntasActivity","Finalizado OnStart");
    }

    @Override
    protected void onStop() {
        MyLog.d("PreguntasActivity","Iniciando OnStop");
        super.onStop();

        MyLog.d("PreguntasActivity","Finalizado OnStop");
    }

    @Override
    protected void onPause() {
        MyLog.d("PreguntasActivity","Iniciando OnPause");
        super.onPause();

        MyLog.d("PreguntasActivity","Finalizado OnPause");
    }

    @Override
    protected void onRestart() {
        MyLog.d("PreguntasActivity","Iniciando OnRestart");
        super.onRestart();

        MyLog.d("PreguntasActivity","Finalizado OnRestar");
    }

    @Override
    protected void onResume() {
        MyLog.d("PreguntasActivity","Iniciando OnResume");
        super.onResume();

        TextView tvNoPregunta = findViewById(R.id.textViewNoPreguntas);
        //preguntas = new ArrayList<>();
        //preguntas.add(new Pregunta("¿Qué miras?", "uno","Un payaso","Nada","...","....."));
        //preguntas.add(new Pregunta("¿Qué miras?", "dos","Un payaso","Nada","...","....."));
        //preguntas.add(new Pregunta("¿Qué miras?", "3","Un payaso","Nada","...","....."));

        preguntas=Repositorio.getRepositorio().getPreguntasBBDD(myContext);

        if(preguntas.isEmpty()){
            tvNoPregunta.setVisibility(View.VISIBLE);
        }


        // Inicializa el RecyclerView
        final RecyclerView recyclerView = findViewById(R.id.recyclerView);

        // Crea el Adaptador con los datos de la lista anterior
        PreguntaAdapter adaptador = new PreguntaAdapter(preguntas);

        // Asocia el elemento de la lista con una acción al ser pulsado
        adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción al pulsar el elemento
                int position = recyclerView.getChildAdapterPosition(v);
                Toast.makeText(PreguntasActivity.this, "Id: " + preguntas.get(position).getId() + " Enunciado: " + preguntas.get(position).getEnunciado() + " Categria: " + preguntas.get(position).getCategoria(), Toast.LENGTH_SHORT)
                        .show();

                //Creamos el Intent
                Intent intent =
                        new Intent(PreguntasActivity.this, NuevaPreguntaActivity.class);

                //Creamos la información a pasar entre actividades
                Bundle b = new Bundle();
                b.putInt("id", preguntas.get(position).getId());

                //Añadimos la información al intent
                intent.putExtras(b);

                //Iniciamos la nueva actividad
                startActivity(intent);

            }
        });

        // Asocia el Adaptador al RecyclerView
        recyclerView.setAdapter(adaptador);

        // Muestra el RecyclerView en vertical
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MyLog.d("PreguntasActivity","Finalizado OnResume");
    }

    @Override
    protected void onDestroy() {
        MyLog.d("PreguntasActivity","Iniciando OnDestroy");
        super.onDestroy();

        MyLog.d("PreguntasActivity","Finalizado OnDestroy");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case CODE_WRITE_EXTERNAL_STORAGE_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permiso aceptado
                    Snackbar.make(constraintLayoutPreguntasActivity, getResources().getString(R.string.write_permission_accepted), Snackbar.LENGTH_LONG)
                            .show();
                } else {
                    // Permiso rechazado
                    Snackbar.make(constraintLayoutPreguntasActivity, getResources().getString(R.string.write_permission_not_accepted), Snackbar.LENGTH_LONG)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
