package com.testmind.al.testmind;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class ResumeActivity extends AppCompatActivity {
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume);

        context=this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar = (Toolbar) findViewById(R.id.toolbarSobreNosotros);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.app_name));

        } else {
            Log.d("SobreNosotros", "Error al cargar toolbar");
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Por implementar en futuras versiones", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    public void clockwise(View view){
        ImageView image = (ImageView)findViewById(R.id.imageView3);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.clockwise);
        image.startAnimation(animation);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_resume, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_acercaDe:
                Log.i("ActionBar", "Acerca de");
                startActivity(new Intent(getApplicationContext(),AcercaDeActivity.class));
                return true;
            case R.id.action_preguntas:
                Log.i("ActionBar", "Preguntas");
                startActivity(new Intent(getApplicationContext(),PreguntasActivity.class));
                return true;
/*
            case R.id.action_buscar:
                Log.i("ActionBar", "Buscar!");;
                return true;
*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        MyLog.d("ResumeActivity","Iniciando OnStart");
        super.onStart();

        MyLog.d("ResumeActivity","Finalizado OnStart");
    }

    @Override
    protected void onStop() {
        MyLog.d("ResumeActivity","Iniciando OnStop");
        super.onStop();

        MyLog.d("ResumeActivity","Finalizado OnStop");
    }

    @Override
    protected void onPause() {
        MyLog.d("ResumeActivity","Iniciando OnPause");
        super.onPause();

        MyLog.d("ResumeActivity","Finalizado OnPause");
    }

    @Override
    protected void onRestart() {
        MyLog.d("ResumeActivity","Iniciando OnRestart");
        super.onRestart();

        MyLog.d("ResumeActivity","Finalizado OnRestar");
    }

    @Override
    protected void onResume() {
        MyLog.d("ResumeActivity","Iniciando OnResume");
        super.onResume();

        TextView numCategorias= (TextView) findViewById(R.id.num_categorias_disponibles);
        TextView numPreguntas= (TextView) findViewById(R.id.num_preguntas_disponibles);
        if(Repositorio.getRepositorio().getCategoriasBBDD(context) != null) {
            numCategorias.setText(Integer.toString(Repositorio.getRepositorio().getCategoriasBBDD(context).size()));
        }else{
            numCategorias.setText(0);
        }

        if(Repositorio.getRepositorio().getPreguntasBBDD(context) != null) {
            numPreguntas.setText(Integer.toString(Repositorio.getRepositorio().getPreguntasBBDD(context).size()));
        }else{
            numPreguntas.setText(0);
        }
        ImageView image = (ImageView)findViewById(R.id.imageView3);
        clockwise(image);
        MyLog.d("ResumeActivity","Finalizado OnResume");
    }

    @Override
    protected void onDestroy() {
        MyLog.d("ResumeActivity","Iniciando OnDestroy");
        super.onDestroy();

        MyLog.d("ResumeActivity","Finalizado OnDestroy");
    }
}
