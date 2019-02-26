package com.testmind.al.testmind;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import static android.util.Base64.DEFAULT;


public class NuevaPreguntaActivity extends AppCompatActivity {

    final private int CODE_WRITE_EXTERNAL_STORAGE_PERMISSION = 123;
    private Context myContext;
    private ConstraintLayout constraintLayoutNuevaPreguntaActivity;
    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    private Button anadirCategoria;
    private String TAG = "DemoAndroidImages";
    final String pathFotos = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/demoAndroidImages/";
    private Uri uri;
    String imagenParaView;
    String imagen="";
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

        //Inicio de la gestion de imagenes

        Button buttonCamera = findViewById(R.id.buttonCamera);
        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            buttonCamera.setEnabled(false);
        } else {
            buttonCamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
                        takePicture();
                    }
                }
            });
        }

        Button buttonGallery = (Button) findViewById(R.id.buttonGallery);
        buttonGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPicture();
            }
        });

        //Final de la gestion  de Imagenes!!!!!!!!!

        //Configuración de la Edición de Pregunta

        //Recuperamos la información pasada en el intent
        final Bundle bundle = this.getIntent().getExtras();

        //Construimos el mensaje a mostrar
       // En el caso de que no se haya pulsado un
        if(bundle!=null) {
            int id = bundle.getInt("id");

            Pregunta p = Repositorio.getRepositorio().getPreguntaXid(id, myContext);


            EditText enun = (EditText) findViewById(R.id.enunciadoPregunta);
            Spinner cate = (Spinner) findViewById(R.id.categoriaPregunta);
            EditText preCor = (EditText) findViewById(R.id.respuestaCorrecta);
            EditText preIn1 = (EditText) findViewById(R.id.respuestaIncorrecta1);
            EditText preIn2 = (EditText) findViewById(R.id.respuestaIncorrecta2);
            EditText preIn3 = (EditText) findViewById(R.id.respuestaIncorrecta3);
            ImageView imagenView=(ImageView) findViewById(R.id.imageView);
            Button guardar = (Button) findViewById(R.id.buttonGuardar);
            Button borrar = (Button) findViewById(R.id.borrar);
            borrar.setEnabled(true);
            enun.setText(p.getEnunciado());
            cate.setSelection(Repositorio.getRepositorio().getCategoriasBBDD(myContext).indexOf(p.getCategoria()));
            preCor.setText(p.getPreguntaCorrecta());
            preIn1.setText(p.getPreguntaIncorrecta1());
            preIn2.setText(p.getPreguntaIncorrecta2());
            preIn3.setText(p.getPreguntaIncorrecta3());

            if(!p.getImagen().isEmpty()) {
                String imagenParaView=p.getImagen();
                byte[] decodedString = Base64.decode(imagenParaView, DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                //image.setImageBitmap(decodedByte);
                imagenView.setImageBitmap(decodedByte);
            }

            borrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = bundle.getInt("id");
                    Repositorio.getRepositorio().deletePregunta( Repositorio.getRepositorio().getPreguntaXid(id, myContext),myContext);

                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            startActivity(new Intent(getApplicationContext(), PreguntasActivity.class));
                            finish();
                            //borrar.setActivated(false);
                        }
                    }, 1);

                }});




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
                ImageView imagenView=(ImageView) findViewById(R.id.imageView);
                final Button guardar = (Button)findViewById(R.id.buttonGuardar);

                //Ocultar Teclado
                ocultarTeclado(enun);
                ocultarTeclado(preCor);
                ocultarTeclado(preIn1);
                ocultarTeclado(preIn2);
                ocultarTeclado(preIn3);
                ocultarTeclado(guardar);

                //Que comprobar campos solo devuelva un true o false, que solo le entre el constrein layout global, y si

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
                    //String imagen= imagen.ge
                    //Comprueba si se ha pasado seleccionado una pregunta en la actividad anterior
                    if(bundle==null) {
                        Button borrar = (Button) findViewById(R.id.borrar);
                        borrar.setEnabled(false);

                        Pregunta p = new Pregunta(enunciado, categoria, preguntaCorrecta, preguntaIn1, preguntaIn2, preguntaIn3,imagen);

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
                       // pregutaId.setImagen();
                        if(!pregutaId.getImagen().isEmpty()) {
                           String imagenParaView=pregutaId.getImagen();
                            byte[] decodedString = Base64.decode(imagenParaView, DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            //image.setImageBitmap(decodedByte);
                            imagenView.setImageBitmap(decodedByte);
                        }
                       // Bitmap selectedImage =  BitmapFactory.decodeFile(pregutaId.getImagen());
                       /*
                        Bitmap selectedImage =  BitmapFactory.decodeFile(filePath);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        byte[] byteArray = stream.toByteArray();
                        String strBase64=Base64.encodeToString(byteArray, 0);
                         */

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


        perdirPermisos();


    }
public void perdirPermisos() {
    int WriteExternalStoragePermission = ContextCompat.checkSelfPermission(myContext, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    MyLog.d("MainActivity", "WRITE_EXTERNAL_STORAGE Permission: " + WriteExternalStoragePermission);
    int CameraPermission = ContextCompat.checkSelfPermission(myContext, Manifest.permission.CAMERA);

    if (WriteExternalStoragePermission != PackageManager.PERMISSION_GRANTED
            || CameraPermission != PackageManager.PERMISSION_GRANTED) {
        // Permiso denegado
        // A partir de Marshmallow (6.0) se pide aceptar o rechazar el permiso en tiempo de ejecución
        // En las versiones anteriores no es posible hacerlo
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

            ActivityCompat.requestPermissions(NuevaPreguntaActivity.this, new String[] {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE }, Constantes.REQUEST_CAPTURE_IMAGE);

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
}
    //INICIO IMAGENES

    private void takePicture() {
        try {
            // Se crea el directorio para las fotografías
            File dirFotos = new File(pathFotos);
            dirFotos.mkdirs();

            // Se crea el archivo para almacenar la fotografía
            File fileFoto = File.createTempFile(getFileCode(),".jpg", dirFotos);

            // Se crea el objeto Uri a partir del archivo
            // A partir de la API 24 se debe utilizar FileProvider para proteger
            // con permisos los archivos creados
            // Con estas funciones podemos evitarlo
            // https://stackoverflow.com/questions/42251634/android-os-fileuriexposedexception-file-jpg-exposed-beyond-app-through-clipdata
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            uri = Uri.fromFile(fileFoto);
            MyLog.d(TAG, uri.getPath().toString());

            // Se crea la comunicación con la cámara
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Se le indica dónde almacenar la fotografía
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            // Se lanza la cámara y se espera su resultado
            startActivityForResult(cameraIntent, Constantes.REQUEST_CAPTURE_IMAGE);

        } catch (IOException ex) {

            MyLog.d(TAG, "Error: " + ex);
           // CoordinatorLayout coordinatorLayout = findViewById(R.id.coordinatorLayout);
            constraintLayoutNuevaPreguntaActivity = findViewById(R.id.constraintLayoutNuevaPreguntaActivity);
            Snackbar snackbar = Snackbar
                    .make(constraintLayoutNuevaPreguntaActivity, getResources().getString(R.string.error_files), Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    private void selectPicture(){
        // Se le pide al sistema una imagen del dispositivo
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(intent, getResources().getString(R.string.choose_picture)),
                Constantes.REQUEST_SELECT_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case (Constantes.REQUEST_CAPTURE_IMAGE):
                if(resultCode == Activity.RESULT_OK){
                    // Se carga la imagen desde un objeto URI al imageView
                    ImageView imageView = findViewById(R.id.imageView);
                    imageView.setImageURI(uri);

                    // Se le envía un broadcast a la Galería para que se actualice
                    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    mediaScanIntent.setData(uri);
                    sendBroadcast(mediaScanIntent);

                    /*
                    Intento de transformar a base64 igual que en la seleccion de imagen
                     */

                    // Se transformam los bytes de la imagen a un Bitmap
                    Bitmap bmp = BitmapFactory.decodeFile(uri.getPath());
                    //Prueba para redimensionar
                    Bitmap resized =Bitmap.createScaledBitmap(bmp,200,200,true);
                    ByteArrayOutputStream baos=new ByteArrayOutputStream();
                    resized.compress(Bitmap.CompressFormat.JPEG,100,baos);//bmisthebitmapobject
                    byte[]b=baos.toByteArray();
                    //y lo guardo en string de pregunta
                    this.imagen=Base64.encodeToString(b,DEFAULT);


                    ///
                   // ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    //bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    //byte[] byteArray = stream.toByteArray();
                    //String strBase64=Base64.encodeToString(byteArray, 0);
                    //this.imagen=strBase64;

                } else if (resultCode == Activity.RESULT_CANCELED) {
                    // Se borra el archivo temporal
                    File file = new File(uri.getPath());
                    file.delete();
                }
                break;

            case (Constantes.REQUEST_SELECT_IMAGE):
                if (resultCode == Activity.RESULT_OK) {
                    // Se carga la imagen desde un objeto Bitmap
                    Uri selectedImage = data.getData();
                    String selectedPath = selectedImage.getPath();

                    if (selectedPath != null) {
                        // Se leen los bytes de la imagen
                        InputStream imageStream = null;
                        try {
                            imageStream = getContentResolver().openInputStream(selectedImage);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        // Se transformam los bytes de la imagen a un Bitmap
                        Bitmap bmp = BitmapFactory.decodeStream(imageStream);
                        //ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        //bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        //byte[] byteArray = stream.toByteArray();
                        //String strBase64=Base64.encodeToString(byteArray, 0);

                        Bitmap resized =bmp.createScaledBitmap(bmp,200,200,true);
                        ByteArrayOutputStream baos=new ByteArrayOutputStream();
                        resized.compress(Bitmap.CompressFormat.JPEG,100,baos);//bmisthebitmapobject
                        byte[]b=baos.toByteArray();
                        //y lo guardo en string de pregunta
                        this.imagen=Base64.encodeToString(b,DEFAULT);
                        /*
                         //Prueba para redimensionar
                    Bitmap resized =Bitmap.createScaledBitmap(bmp,500,500,true);
                    ByteArrayOutputStream baos=new ByteArrayOutputStream();
                    resized.compress(Bitmap.CompressFormat.JPEG,100,baos);//bmisthebitmapobject
                    byte[]b=baos.toByteArray();
                    //y lo guardo en string de pregunta
                    this.imagen=Base64.encodeToString(b,Base64.DEFAULT);
                         */

                        //this.imagen=strBase64;
                        /*
                        Bitmap selectedImage =  BitmapFactory.decodeFile(filePath);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        byte[] byteArray = stream.toByteArray();
                        String strBase64=Base64.encodeToString(byteArray, 0);
                        */


                        // Se carga el Bitmap en el ImageView
                        ImageView imageView = findViewById(R.id.imageView);
                        imageView.setImageBitmap(bmp);
                    }
                }
                break;
        }
    }

    private String getFileCode()
    {
        // Se crea un código a partir de la fecha y hora
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss", java.util.Locale.getDefault());
        String date = dateFormat.format(new Date());
        // Se devuelve el código
        return "pic_" + date;
    }

    //FINAL IMAGENES
    /**
     *
     * Comprueba que los EditText y el Spinner tienen contenido, devolviendo true
     * en caso afirmativo o false en negativo.
     *
     * @param constraintLayoutNuevaPreguntaActivity
     * @param enun
     * @param cate
     * @param preCor
     * @param preInc1
     * @param preInc2
     * @param preInc3
     * @return boolean
     */
    public boolean comprobarCamposPre(ConstraintLayout constraintLayoutNuevaPreguntaActivity, EditText enun,Spinner cate, EditText preCor, EditText preInc1,
                                   EditText preInc2, EditText preInc3){
            boolean lleno=false;

            if(!enun.getText().toString().isEmpty()&&
                !preCor.getText().toString().isEmpty()&&
                !preInc1.getText().toString().isEmpty()&&
                !preInc2.getText().toString().isEmpty()&&
                !preInc3.getText().toString().isEmpty()&&
                    cate.getSelectedItem()!=null&&
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

            case Constantes.REQUEST_CAPTURE_IMAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permiso aceptado
                    Snackbar.make(constraintLayoutNuevaPreguntaActivity, getResources().getString(R.string.camera_permission_accepted), Snackbar.LENGTH_LONG)
                            .show();
                } else {
                    // Permiso rechazado
                    Snackbar.make(constraintLayoutNuevaPreguntaActivity, getResources().getString(R.string.write_permission_not_accepted), Snackbar.LENGTH_LONG)
                            .show();
                }
                break;
            case Constantes.REQUEST_SELECT_IMAGE:
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
