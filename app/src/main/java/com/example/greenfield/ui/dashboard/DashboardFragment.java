package com.example.greenfield.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.greenfield.Programacion2Fragment;
import com.example.greenfield.ProgramacionFragment;
import com.example.greenfield.R;
import com.example.greenfield.databinding.FragmentDashboardBinding;
import com.example.greenfield.ui.notifications.NotificationsFragment;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;
    private Button buttonContinuar;

    ListView listViewData;
    ArrayAdapter<String> adapter;
    String[] data = {"Papel / Cartones", "Botellas de Plastico / Plasticos", "Pilas / Desechos Electronicos",
    "Botellas de Vidrio / Vasos"};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        listViewData = (ListView) root.findViewById(R.id.listView_data);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_multiple_choice, data);
        listViewData.setAdapter(adapter);
        setHasOptionsMenu(true);

        /*buttonContinuar = root.findViewById(R.id.btnContinuar);
        buttonContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               NotificationsFragment programacionFragment = new NotificationsFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_dashboard,  programacionFragment, "findThisFragment");
                fragmentTransaction.commit();
                NotificationsFragment fragment = new NotificationsFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.nav_host_fragment_activity_dashboard, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });*/

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.item_done){
            String itemsSelect = "elect items";
            for(int i=0; i<listViewData.getCount(); i++){
                if(listViewData.isItemChecked(i)){
                    itemsSelect += listViewData.getItemAtPosition(i);
                }
            }
           // Toast.makeText(itemsSelect, this, Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}