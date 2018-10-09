package com.testmind.al.testmind;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class LogoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        MyLog.d("LogoActivity","Iniciando OnCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);


        MyLog.d("LogoActivity","Finalizado OnCreate");
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
}
