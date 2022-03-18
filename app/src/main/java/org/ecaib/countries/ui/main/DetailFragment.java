package org.ecaib.countries.ui.main;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import org.ecaib.countries.Country;
import org.ecaib.countries.SharedViewModel;
import org.ecaib.countries.databinding.MainFragment2Binding;

public class DetailFragment extends Fragment {

    private DetailViewModel mViewModel;
    private MainFragment2Binding binding;
    /*private TextView nameDetail;
    private TextView rarityDetail;
    private TextView manaCostDetail;
    private TextView textDetail;
    private ImageView imageDetail;*/

    public static DetailFragment newInstance() {
        return new DetailFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = MainFragment2Binding.inflate(inflater);
        View view = binding.getRoot();

        /*nameDetail = view.findViewById(R.id.txtNameDetail);
        rarityDetail = view.findViewById(R.id.txtRarityDetail);
        manaCostDetail = view.findViewById(R.id.txtManaCostDetail);
        textDetail = view.findViewById(R.id.txtTextDetail);
        imageDetail = view.findViewById(R.id.imgDetail);*/


        Intent intent = getActivity().getIntent();

        if(intent != null) {
            Country country = (Country)intent.getSerializableExtra("card");

            if (country != null){
                showData(country);
            }
        }

        SharedViewModel sharedViewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
        sharedViewModel.getSelected().observe(getViewLifecycleOwner(), this::showData);

        return view;
    }

    private void showData(Country country) {
        binding.txtNameDetail.setText(country.getName());
        binding.txtRarityDetail.setText(country.getCapital());
        binding.txtManaCostDetail.setText(String.valueOf(country.getPopulation()));
        binding.txtTypeDetail.setText(country.getSubregion());
        //binding.txtTextDetail.setText(country.getId());


        Glide.with(getContext()
        ).load(country.getFlagUrl()
        ).into(binding.imgDetail);





    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DetailViewModel.class);
        // TODO: Use the ViewModel
    }

}