package com.testmind.al.testmind;

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

public class ResumeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar = (Toolbar) findViewById(R.id.toolbarSobreNosotros);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Asignar la acción necesaria. En este caso "volver atrás"
                    onBackPressed();
                }
            });
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
    public void onBackPressed() {
        // Asignar la acción necesaria. En este caso terminar la actividad
        finish();
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

        MyLog.d("ResumeActivity","Finalizado OnResume");
    }

    @Override
    protected void onDestroy() {
        MyLog.d("ResumeActivity","Iniciando OnDestroy");
        super.onDestroy();

        MyLog.d("ResumeActivity","Finalizado OnDestroy");
    }
}
