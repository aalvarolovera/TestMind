package com.testmind.al.testmind;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class AcercaDeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca_de);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Asignar la acción necesaria. En este caso "volver atrás"
                    onBackPressed();
                    onNavigateUp();
                }
            });
        } else {
            MyLog.d("AcercaDe", "Error al cargar toolbar");
        }

    }
    @Override
    public boolean onNavigateUp() {
        // Asignar la acción necesaria. En este caso terminar la actividad
        finish();
        return true;
    }
    @Override
    public void onBackPressed() {
        // Asignar la acción necesaria. En este caso terminar la actividad
        finish();
    }

    @Override
    protected void onStart() {
        MyLog.d("AcercaDeActivity","Iniciando OnStart");
        super.onStart();

        MyLog.d("AcercaDeActivity","Finalizado OnStart");
    }

    @Override
    protected void onStop() {
        MyLog.d("AcercaDeActivity","Iniciando OnStop");
        super.onStop();

        MyLog.d("AcercaDeActivity","Finalizado OnStop");
    }

    @Override
    protected void onPause() {
        MyLog.d("AcercaDeActivity","Iniciando OnPause");
        super.onPause();

        MyLog.d("AcercaDeActivity","Finalizado OnPause");
    }

    @Override
    protected void onRestart() {
        MyLog.d("AcercaDeActivity","Iniciando OnRestart");
        super.onRestart();

        MyLog.d("AcercaDeActivity","Finalizado OnRestar");
    }

    @Override
    protected void onResume() {
        MyLog.d("AcercaDeActivity","Iniciando OnResume");
        super.onResume();

        MyLog.d("AcercaDeActivity","Finalizado OnResume");
    }

    @Override
    protected void onDestroy() {
        MyLog.d("AcercaDeActivity","Iniciando OnDestroy");
        super.onDestroy();

        MyLog.d("AcercaDeActivity","Finalizado OnDestroy");
    }
}
