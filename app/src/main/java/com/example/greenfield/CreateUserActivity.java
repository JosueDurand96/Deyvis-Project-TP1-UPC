package com.example.greenfield;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class CreateUserActivity extends AppCompatActivity {
    public static final int REQUEST_CODE = 1;
    public ImageView imagePhoto;
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissions = new ArrayList<>();
    private static final int ALL_PERMISSIONS_RESULT = 1011;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear__usuario);
        imagePhoto = findViewById(R.id.imagen);
        final Button boton_registrar = findViewById(R.id.crear);
        final EditText texto_nombre = findViewById(R.id.nombre);
        final EditText texto_ape_materno = findViewById(R.id.apellido);
        final EditText texto_dni = findViewById(R.id.dni);
        final EditText texto_correo = findViewById(R.id.txtOtraPersonaCorreo);
        final EditText texto_telefono = findViewById(R.id.telefono);
        final EditText texto_direccion = findViewById(R.id.direccion);
        final EditText texto_usuario = findViewById(R.id.usuario);
        final EditText claveEditText = findViewById(R.id.claveEditText);


        imagePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(CreateUserActivity.this,
                            Manifest.permission.CAMERA)
                            == PackageManager.PERMISSION_GRANTED) {
                        openCamera();
                    }
                } else {
                    openCamera();
                }
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(CreateUserActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
            }
        }
        permissionsToRequest = permissionsToRequest(permissions);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0) {
                requestPermissions(permissionsToRequest.
                        toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
            }
        }


        boton_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = texto_nombre.getText().toString();
                String apellidomaterno = texto_ape_materno.getText().toString();
                String dni = texto_dni.getText().toString();
                String correo = texto_correo.getText().toString();
                String telefono = texto_telefono.getText().toString();
                String dirrecion = texto_direccion.getText().toString();
                String usuario = texto_usuario.getText().toString();
                String clave = claveEditText.getText().toString();

                final byte[] imagen1 = imageViewToByte(imagePhoto);
                final Bitmap bitmap1 = BitmapFactory.decodeByteArray(imagen1, 0, imagen1.length);
                String image = getStringImagen(bitmap1);
                new DescargarImagen(CreateUserActivity.this).execute(nombre, apellidomaterno, dni, correo, telefono, dirrecion, usuario,image,clave);

                Intent intent = new Intent(CreateUserActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private ArrayList<String> permissionsToRequest(ArrayList<String> wantedPermissions) {
        ArrayList<String> result = new ArrayList<>();

        for (String perm : wantedPermissions) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
        }

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void openCamera() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePicture.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePicture, REQUEST_CODE);
        }
    }



    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        /*Photo 1*/
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE && data != null) {
            Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
            imagePhoto.setImageBitmap(imageBitmap);
        }

    }

    public static class DescargarImagen extends AsyncTask<String, Void, String> {
        private WeakReference<Context> context;

        public DescargarImagen(Context context) {
            this.context = new WeakReference<>(context);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)


        @Override
        protected String doInBackground(String... params) {
            String registrar_url = "http://proyectotp.6te.net/Registro.php";
            String resultado = null;
            try {
                URL url = new URL(registrar_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));

                String nombre = params[0];
                String apellidomaterno = params[1];
                String dni = params[2];
                String correo = params[3];
                String telefono = params[4];
                String dirrecion = params[5];
                String usuario = params[6];
                String image = params[7];
                String clave = params[8];



                String data = URLEncoder.encode("nombre", "UTF-8") + "=" + URLEncoder.encode(nombre, "UTF-8")
                        + "&" + URLEncoder.encode("apellidomaterno", "UTF-8") + "=" + URLEncoder.encode(apellidomaterno, "UTF-8")
                        + "&" + URLEncoder.encode("dni", "UTF-8") + "=" + URLEncoder.encode(dni, "UTF-8")
                        + "&" + URLEncoder.encode("correo", "UTF-8") + "=" + URLEncoder.encode(correo, "UTF-8")
                        + "&" + URLEncoder.encode("telefono", "UTF-8") + "=" + URLEncoder.encode(telefono, "UTF-8")
                        + "&" + URLEncoder.encode("dirrecion", "UTF-8") + "=" + URLEncoder.encode(dirrecion, "UTF-8")
                        + "&" + URLEncoder.encode("FotoPerfil","UTF-8") + "=" + URLEncoder.encode(image, "UTF-8")
                        + "&" + URLEncoder.encode("clave","UTF-8") + "=" + URLEncoder.encode(clave, "UTF-8")
                        + "&" + URLEncoder.encode("usuario", "UTF-8") + "=" + URLEncoder.encode(usuario, "UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                StringBuilder stringBuilder = new StringBuilder();

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                resultado = stringBuilder.toString();
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                resultado = "Formato incorrecto";
            } catch (IOException e) {
                e.printStackTrace();
                resultado = "Error";
            }
            return resultado;
        }

        protected void onPostExecute(String resultado) {
            Toast.makeText(context.get(), resultado, Toast.LENGTH_LONG).show();
        }
    }

    public String getStringImagen(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    //CONVERTIDOR
    public static byte[] imageViewToByte(ImageView image) {

        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }



}