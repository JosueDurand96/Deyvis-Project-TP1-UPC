package com.example.greenfield.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.greenfield.R;
import com.example.greenfield.databinding.FragmentNotificationsBinding;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private FragmentNotificationsBinding binding;

    ListView listViewData;
    Spinner spinnerDia, spinnerHora;
    ArrayAdapter<String> adapter;
    String[] data = {"Papel / Cartones", "Botellas de Plastico / Plasticos", "Pilas / Desechos Electronicos",
            "Botellas de Vidrio / Vasos"};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        listViewData = (ListView) root.findViewById(R.id.listView_data);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, data);
        listViewData.setAdapter(adapter);
        spinnerDia = (Spinner) root.findViewById(R.id.spinnerDia);
        String[] dias = {"Lunes","Martes","Miercoles","Jueves","Viernes","Sabado", "Domingo"};
        spinnerDia.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, dias));
        spinnerHora = (Spinner) root.findViewById(R.id.spinnerHora);
        String[] horas = {"8:00 am - 11:00 am", "11:00 am - 2:00 pm", "2:00 pm - 5:00 pm"};
        spinnerHora.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, horas));
        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}