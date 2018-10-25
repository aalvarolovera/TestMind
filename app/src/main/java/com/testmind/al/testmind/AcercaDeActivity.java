package com.testmind.al.testmind;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
                    // Asignar la acción necesaria. En este caso "volver atrás"
                    onBackPressed();
                }
            });
        } else {
            MyLog.d("AcercaDe", "Error al cargar toolbar");
        }

    }
    @Override
    public boolean onNavigateUp() {
        // Asignar la acción necesaria. En este caso terminar la actividad
        return true;
    }
    @Override
    public void onBackPressed() {
        // Asignar la acción necesaria. En este caso terminar la actividad
        finish();
    }
}
