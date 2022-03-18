package org.ecaib.countries;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<Country> selected = new MutableLiveData<Country>();

    public void select(Country country) {
        selected.setValue(country);
    }

    public LiveData<Country> getSelected() {
        return selected;
    }

}
