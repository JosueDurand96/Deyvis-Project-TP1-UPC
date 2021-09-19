package com.example.greenfield.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.greenfield.R;
import com.example.greenfield.databinding.FragmentHomeBinding;
import com.example.greenfield.service.ClienteListviewAdapter;
import com.example.greenfield.service.Puntaje;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private Integer disponible,id;
    private Integer ganado;
    Integer canjeado;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        listar();

        return root;
    }

    public void listar() {
        HomeFragment.TareaWSListar tarea = new HomeFragment.TareaWSListar();
        tarea.execute();
    }

    public ListView lstCliente;

    //Tarea Asíncrona para llamar al WS de listado en segundo plano
    public class TareaWSListar extends AsyncTask<String, Integer, Boolean> {

        public List<Puntaje> listaCategoria;

        @SuppressLint("SetTextI18n")
        protected Boolean doInBackground(String... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;
            boolean resul = true;
            String urlApiREST = "http://proyectotp.6te.net/listar_puntaje.php?Id=" + "1";
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
                JSONArray parentArray = parentObject.getJSONArray("list");
                listaCategoria = new ArrayList<Puntaje>();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    id = finalObject.getInt("Id");
                    disponible = finalObject.getInt("disponible");
                    ganado = finalObject.getInt("ganado");
                    canjeado = finalObject.getInt("canjeado");

                    binding.txtGanados.setText(ganado.toString());
                    binding.txtCanjeados.setText(canjeado.toString());
                    binding.txtDisponibles.setText(disponible.toString());

                    listaCategoria.add(new Puntaje(id, disponible, ganado, canjeado));
                }

            } catch (Exception ex) {
                Log.e("ServicioRest", "Error!", ex);
                resul = false;
            }
            return resul;


        }


        protected void onPostExecute(Boolean result) {

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}