package org.ecaib.countries.ui.main;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.ecaib.countries.Country;
import org.ecaib.countries.CountriesAdapter;
import org.ecaib.countries.DetailActivity;
import org.ecaib.countries.MainActivity;
import org.ecaib.countries.R;
import org.ecaib.countries.SettingsActivity;
import org.ecaib.countries.SharedViewModel;
import org.ecaib.countries.databinding.MainFragmentBinding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;
    private ArrayList<Country> items;
    private CountriesAdapter adapter;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        MainFragmentBinding binding = MainFragmentBinding.inflate(inflater);
        View view = binding.getRoot();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        Set<String> region = preferences.getStringSet("region", new HashSet<String>());
        List<String> regionsList = new ArrayList<>(region);


        items = new ArrayList<>();

        adapter = new CountriesAdapter(
                getContext(),
                R.layout.lv_countries_row,
                R.id.txtTitleRow,
                items
        );

        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        // Si las preferencias no tienen region seleccionada, hacemos un get de todos los paises
        if(region.isEmpty()){
            mViewModel.getCountries().observe(getViewLifecycleOwner(), countries -> {
                adapter.clear();
                adapter.addAll(countries);
            });
        } else {
            mViewModel.getCountriesRegion(regionsList).observe(getViewLifecycleOwner(), countries -> {
                adapter.clear();
                adapter.addAll(countries);
            });
        }



        SharedViewModel sharedViewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);




        binding.lvCountries.setAdapter(adapter);

        binding.lvCountries.setOnItemClickListener((parent, view1, position, id) -> {
            Country country = adapter.getItem(position);

            if(!esTablet()) {
                Intent i = new Intent(getContext(), DetailActivity.class);
                i.putExtra("country", country);

                startActivity(i);
            } else {
                sharedViewModel.select(country);
            }

        });




        return view;
    }

    void refresh() {
        mViewModel.reload();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.refresh) {
            refresh();
        }

        if (id == R.id.settings) {
            Intent i = new Intent(getContext(), SettingsActivity.class);
            startActivity(i);
        }

        if (id == R.id.home) {
            Intent i = new Intent(getContext(), MainActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        Set<String> region = preferences.getStringSet("region", new HashSet<String>());
        List<String> regionsList = new ArrayList<>(region);


        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        if(region.isEmpty()){
            mViewModel.getCountries().removeObservers(getViewLifecycleOwner());


            mViewModel.getCountries().observe(getViewLifecycleOwner(), countries -> {
                adapter.clear();
                adapter.addAll(countries);
            });
        } else {
            mViewModel.getCountriesRegion(regionsList).removeObservers(getViewLifecycleOwner());


            mViewModel.getCountriesRegion(regionsList).observe(getViewLifecycleOwner(), countries -> {
                adapter.clear();
                adapter.addAll(countries);
            });
        }


        refresh();


    }

    boolean esTablet() {
        return getResources().getBoolean(R.bool.tablet);
    }
}