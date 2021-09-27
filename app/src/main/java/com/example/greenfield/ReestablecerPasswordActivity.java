package com.example.greenfield;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.greenfield.model.nthc_usuario;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ReestablecerPasswordActivity extends AppCompatActivity {
    private int id;
    String Nombre, ApellidoMaterno, ApellidoPaterno, DNI, Correo, Telefono, Direccion, Usuario, Clave;
    String FotoPerfil;

    private String phone = "";

    @Override
    protected void onStart() {
        super.onStart();
        listar();
        //select id from tUser telefono = telefono
        phone = getIntent().getStringExtra("phone");
    }


    public void listar() {
        ReestablecerPasswordActivity.TareaWSListar tarea = new ReestablecerPasswordActivity.TareaWSListar();
        tarea.execute();
    }

    //Tarea Asíncrona para llamar al WS de listado en segundo plano
    public class TareaWSListar extends AsyncTask<String, Integer, Boolean> {

        public List<nthc_usuario> listaCategoria;

        protected Boolean doInBackground(String... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;
            boolean resul = true;
            String urlApiREST = "http://proyectoupc.6te.net/listar_user.php?Telefono=" + phone;
            try {
                URL url = new URL(urlApiREST);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                String finalJson = buffer.toString();
                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("lista");
                listaCategoria = new ArrayList<nthc_usuario>();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    id = finalObject.getInt("id");
                    Nombre = finalObject.getString("Nombre");
                    ApellidoMaterno = finalObject.getString("ApellidoMaterno");
                    ApellidoPaterno = finalObject.getString("ApellidoPaterno");
                    DNI = finalObject.getString("DNI");
                    Correo = finalObject.getString("Correo");
                    Telefono = finalObject.getString("Telefono");
                    Direccion = finalObject.getString("Direccion");
                    Usuario = finalObject.getString("Usuario");
                    Clave = finalObject.getString("Clave");
                    FotoPerfil = finalObject.getString("FotoPerfil");

                    listaCategoria.add(new nthc_usuario(id,
                            Nombre, ApellidoMaterno, ApellidoPaterno, DNI,
                            Correo, Telefono, Direccion, Usuario, Clave, FotoPerfil));
                }
                dataList(id, Clave, Telefono);
            } catch (Exception ex) {
                Log.e("ServicioRest", "Error!", ex);
                resul = false;
            }
            return resul;


        }


        protected void onPostExecute(Boolean result) {
            if (result) {
            }
        }


    }

    private void dataList(Integer id, String clave, String telefono) {
        Log.d("ServicioRest", "id: " + id.toString());
        Log.d("ServicioRest", "clave: " + clave);
        Log.d("ServicioRest", "telefono: " + telefono);
    }

    EditText newPasswordTextInputEditText;
    EditText confirmPasswordTextInputEditText;
    Button continueButton;
    Boolean dataCorrecta = false;
    ProgressBar progress;
    ConstraintLayout parentlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reestablecer_password);
        progress = findViewById(R.id.progress);
        continueButton = findViewById(R.id.continueButton);
        newPasswordTextInputEditText = findViewById(R.id.newPasswordTextInputEditText);
        confirmPasswordTextInputEditText = findViewById(R.id.confirmPasswordTextInputEditText);
        parentlayout = findViewById(R.id.parentlayout);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newPasswordTextInputEditText.getText().toString().isEmpty() && confirmPasswordTextInputEditText.getText().toString().isEmpty()) {
                    Toast.makeText(ReestablecerPasswordActivity.this, "Los datos estan vacíos!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        confirmPasswordTextInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (newPasswordTextInputEditText.getText().toString().equals(confirmPasswordTextInputEditText.getText().toString())) {
                    Log.d("ServicioRest", "CORRECTO: ");
                    dataCorrecta = true;
                    if (dataCorrecta) {
                        continueButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                progress.setVisibility(View.VISIBLE);
                                Thread tr2 = new Thread() {
                                    @Override
                                    public void run() {
                                        super.run();
                                        Actualizar(confirmPasswordTextInputEditText.getText().toString());
                                        Snackbar.make(parentlayout, "Actualización exitosa!", Snackbar.LENGTH_LONG)
                                                .setActionTextColor(getResources().getColor(android.R.color.holo_green_dark))
                                                .show();
                                        Intent intent = new Intent(ReestablecerPasswordActivity.this, DashboardActivity.class);
                                        startActivity(intent);
                                    }
                                };
                                tr2.start();
                            }
                        });
                    } else {
                        Toast.makeText(ReestablecerPasswordActivity.this, "Los datos ingresados no son correctos!", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    confirmPasswordTextInputEditText.setError("Password no coincide");
                    Log.d("ServicioRest", "INCORRECTO: ");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    public void Actualizar(String Clave) {
        String urlParameters = "Clave=" + Clave;
        HttpURLConnection conection = null;

        try {
            URL url = new URL("http://proyectoupc.6te.net/update_password.php?id=" + id);
            conection = (HttpURLConnection) url.openConnection();
            //estableciendo el metodo
            conection.setRequestMethod("POST");
            //longitud de datos que se envian
            conection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
            //comando para la salida de datos
            conection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(conection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.close();
            InputStream is = conection.getInputStream();
            progress.setVisibility(View.GONE);

        } catch (Exception ex) {
        }


    }


}