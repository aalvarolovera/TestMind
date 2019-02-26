package com.testmind.al.testmind;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
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

                //Añadimos la información del Bundle al intent
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_preguntas, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_acercaDe:
                Log.i("ActionBar", "Acerca de");
                startActivity(new Intent(getApplicationContext(),AcercaDeActivity.class));
                return true;
                /*
            case R.id.action_preguntas:
                Log.i("ActionBar", "Preguntas");
                startActivity(new Intent(getApplicationContext(),PreguntasActivity.class));
                return true;
                */
            case R.id.action_exportar:
                Log.i("ActionBar", "Exportar Todas las preguntas");

                try {

                        mandarXmlEmail();


                    // crearArchivoXML();
                } catch (IllegalArgumentException | IllegalStateException  ex){
                   // System.out.println(ex);
                    Toast.makeText(PreguntasActivity.this, " ERROR", Toast.LENGTH_SHORT).show();
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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
    public File crearArchivoXML() {

        // Creo el directoio para guardar el fichero
        File dir = new File("/sdcard" + "/Xml/");

        // si el direcctorio no existe, lo creo
        if (!dir.exists()) {
          //  System.out.println("creando directorio: " + "MiDirectorio");
            dir.mkdir();
        }

        // Creamos el fichero en su ubicación completa
        File file = new File(dir, "BBDDXml.xml");

        File fileXml = new File(Constantes.RutaXml);

        try {
           // FileOutputStream fos = openFileOutput(fileXml.getName(), MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(openFileOutput(fileXml.getName(), MODE_PRIVATE));
            osw.write(createXMLString(),0,createXMLString().length());
            fileXml.createNewFile();
            osw.flush();
            osw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileXml;
    }

    @SuppressWarnings("null")
    public String createXMLString() throws IllegalArgumentException, IllegalStateException, IOException
    {
        XmlSerializer xmlSerializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();
        String stringXML;
        xmlSerializer.setOutput(writer);

        //Start Document
        xmlSerializer.startDocument("UTF-8", true);
        xmlSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        //Open Tag <file>
        xmlSerializer.startTag("", "quiz");



            ArrayList<Pregunta> preguntas =  Repositorio.getRepositorio().getPreguntasBBDD(myContext);

            for(int i=0; i < preguntas.size();i++){

                Pregunta p=preguntas.get(i);

                xmlSerializer.startTag("", "question");
                xmlSerializer.attribute("", "type", "category");
                xmlSerializer.startTag("", "category");
                xmlSerializer.startTag("", "text");
                xmlSerializer.text(p.getCategoria());
                xmlSerializer.endTag("", "text");
                xmlSerializer.endTag("", "category");
                xmlSerializer.endTag("", "question");

                xmlSerializer.startTag("", "question");
                xmlSerializer.attribute("", "type", "multichoice");
                xmlSerializer.startTag("", "name");
                xmlSerializer.startTag("", "text");
                xmlSerializer.text(p.getEnunciado());
                xmlSerializer.endTag("", "text");
                xmlSerializer.endTag("", "name");

                xmlSerializer.startTag("", "questiontext");
                xmlSerializer.attribute("", "format", "html");
                xmlSerializer.startTag("", "text");
                xmlSerializer.text("<![CDATA[ <p>"+p.getEnunciado()+"</p>]]>");
                xmlSerializer.endTag("", "text");
                xmlSerializer.startTag("", "file");
                xmlSerializer.attribute("", "encoding", "base64");
                xmlSerializer.text(p.getImagen());
                xmlSerializer.endTag("", "file");
                xmlSerializer.endTag("", "questiontext");

                xmlSerializer.startTag("", "answernumbering");
                xmlSerializer.text("abc");
                xmlSerializer.endTag("", "answernumbering");

                xmlSerializer.startTag("", "answer");
                xmlSerializer.attribute("", "fraction", "100");
                xmlSerializer.attribute("", "format", "html");
                xmlSerializer.startTag("", "text");
                xmlSerializer.text(p.getPreguntaCorrecta());
                xmlSerializer.endTag("", "text");
                xmlSerializer.endTag("", "answer");

                xmlSerializer.startTag("", "answer");
                xmlSerializer.attribute("", "fraction", "0");
                xmlSerializer.attribute("", "format", "html");
                xmlSerializer.startTag("", "text");
                xmlSerializer.text(p.getPreguntaIncorrecta1());
                xmlSerializer.endTag("", "text");
                xmlSerializer.endTag("", "answer");

                xmlSerializer.startTag("", "answer");
                xmlSerializer.attribute("", "fraction", "0");
                xmlSerializer.attribute("", "format", "html");
                xmlSerializer.startTag("", "text");
                xmlSerializer.text(p.getPreguntaIncorrecta2());
                xmlSerializer.endTag("", "text");
                xmlSerializer.endTag("", "answer");

                xmlSerializer.startTag("", "answer");
                xmlSerializer.attribute("", "fraction", "0");
                xmlSerializer.attribute("", "format", "html");
                xmlSerializer.startTag("", "text");
                xmlSerializer.text(p.getPreguntaIncorrecta3());
                xmlSerializer.endTag("", "text");
                xmlSerializer.endTag("", "answer");

                xmlSerializer.endTag("","question");
            }

        //end tag <file>
        xmlSerializer.endTag("", "quiz");
        xmlSerializer.endDocument();

   //     Uri path = Uri.parse("file://"+writer.toString());
        stringXML=writer.toString();

        return stringXML;
    }
    public boolean mandarXmlEmail() {

        // Creo el directoio para guardar el fichero
       // File dir = new File("/sdcard" + "/Xml/");
        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Xml/");
        // si el direcctorio no existe, lo creo
        if (!dir.exists()) {
            dir.mkdir();
            System.out.println("creando directorio: " + "MiDirectorio");
        }
        // Creamos el fichero en su ubicación completa
        File fileXml = new File(dir,"BDxml.xml");

        try {

            FileOutputStream fileos= getApplicationContext().openFileOutput(fileXml.getName(), Context.MODE_PRIVATE);

            OutputStreamWriter osw = new OutputStreamWriter(openFileOutput(fileXml.getName(), MODE_PRIVATE));
          //  osw.write(createXMLString());
          //  fileXml.createNewFile();
            fileos.write(createXMLString().getBytes());
            fileos.close();
            osw.flush();
            osw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        StrictMode.VmPolicy.Builder builder= new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        //String filename="contacts_sid.vcf";
      //  File filelocation = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), fileXml.getName());
        Uri path = Uri.fromFile( new File (Environment.getExternalStorageDirectory().getAbsolutePath() + "/Xml/",fileXml.getName()));

        Intent i = new Intent(Intent.ACTION_SEND);

        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"destinatario@mail.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
        i.putExtra(Intent.EXTRA_STREAM, path);
        startActivity(Intent.createChooser(i , "Send email..."));

        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(PreguntasActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }

        return false;
    }

}
