package org.ecaib.countries;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import org.ecaib.countries.databinding.LvCardsRowBinding;

import java.util.List;

public class CountriesAdapter extends ArrayAdapter<Country> {
    private LvCardsRowBinding binding;

    public CountriesAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<Country> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Country country = getItem(position);

        if(convertView == null){
            binding = LvCardsRowBinding.inflate(
                    LayoutInflater.from(getContext()),
                    parent,
                    false
            );
        } else {
            binding = LvCardsRowBinding.bind(convertView);
        }


        binding.txtTitleRow.setText(country.getName());
        binding.txtRarity.setText(country.getCapital());
        binding.txtType.setText(country.getRegion());


        Glide.with(getContext()
        ).load(country.getFlagUrl()
        ).into(binding.cardImageRow);





        return binding.getRoot();
    }
}
