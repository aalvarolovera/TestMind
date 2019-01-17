package com.testmind.al.testmind;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class LogoActivity extends AppCompatActivity {

    final private int CODE_WRITE_EXTERNAL_STORAGE_PERMISSION = 123;
    private ConstraintLayout constraintLayoutLogoActivity;
    private Context myContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        MyLog.d("LogoActivity", "Iniciando OnCreate");

        myContext=this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), ResumeActivity.class));
/*
                int WriteExternalStoragePermission = ContextCompat.checkSelfPermission(myContext, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                MyLog.d("MainActivity", "WRITE_EXTERNAL_STORAGE Permission: " + WriteExternalStoragePermission);

                if (WriteExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
                    // Permiso denegado
                    // A partir de Marshmallow (6.0) se pide aceptar o rechazar el permiso en tiempo de ejecución
                    // En las versiones anteriores no es posible hacerlo
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        ActivityCompat.requestPermissions(LogoActivity.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, CODE_WRITE_EXTERNAL_STORAGE_PERMISSION);
                        // Una vez que se pide aceptar o rechazar el permiso se ejecuta el método "onRequestPermissionsResult" para manejar la respuesta
                        // Si el usuario marca "No preguntar más" no se volverá a mostrar este diálogo

                    } else {
                        Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.write_permission_denied), Snackbar.LENGTH_LONG)
                                .show();
                    }
                } else {
                    // Permiso aceptado
                    Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.write_permission_granted), Snackbar.LENGTH_LONG)
                            .show();
                }
*/
         finish(); }
        }, 3000);

        MyLog.d("LogoActivity", "Finalizado OnCreate");
    }

    @Override
    protected void onStart() {
        MyLog.d("LogoActivity","Iniciando OnStart");
        super.onStart();

        MyLog.d("LogoActivity","Finalizado OnStart");
    }

    @Override
    protected void onStop() {
        MyLog.d("LogoActivity","Iniciando OnStop");
        super.onStop();

        MyLog.d("LogoActivity","Finalizado OnStop");
    }

    @Override
    protected void onPause() {
        MyLog.d("LogoActivity","Iniciando OnPause");
        super.onPause();

        MyLog.d("LogoActivity","Finalizado OnPause");
    }

    @Override
    protected void onRestart() {
        MyLog.d("LogoActivity","Iniciando OnRestart");
        super.onRestart();

        MyLog.d("LogoActivity","Finalizado OnRestar");
    }

    @Override
    protected void onResume() {
        MyLog.d("LogoActivity","Iniciando OnResume");
        super.onResume();

        MyLog.d("LogoActivity","Finalizado OnResume");
    }

    @Override
    protected void onDestroy() {
        MyLog.d("LogoActivity","Iniciando OnDestroy");
        super.onDestroy();

        MyLog.d("LogoActivity","Finalizado OnDestroy");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case CODE_WRITE_EXTERNAL_STORAGE_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permiso aceptado
                    Snackbar.make(constraintLayoutLogoActivity, getResources().getString(R.string.write_permission_accepted), Snackbar.LENGTH_LONG)
                            .show();
                } else {
                    // Permiso rechazado
                    Snackbar.make(constraintLayoutLogoActivity, getResources().getString(R.string.write_permission_not_accepted), Snackbar.LENGTH_LONG)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
