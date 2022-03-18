package org.ecaib.countries.ui.main;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.ecaib.countries.AppDatabase;
import org.ecaib.countries.Country;
import org.ecaib.countries.CountryDao;
import org.ecaib.countries.CountriesApiClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainViewModel extends AndroidViewModel {
    private final Application app;
    private final AppDatabase appDatabase;
    private final CountryDao countryDao;
    private LiveData<List<Country>> movies;

    public MainViewModel(Application application) {
        super(application);

        this.app = application;
        this.appDatabase = AppDatabase.getDatabase(
                this.getApplication());
        this.countryDao = appDatabase.getCountryDao();
    }

    public LiveData<List<Country>> getCountries() {
        return countryDao.getCountries();
    }

    public LiveData<List<Country>> getCountriesRegion(List<String> region) {
        Log.e("VMREG", region.toString());
        return countryDao.getCountriesRegion(region);
    }

    public void reload() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            CountriesApiClient api = new CountriesApiClient();
            ArrayList<Country> countries = api.getCountries();
            countryDao.deleteCountries();
            countryDao.addCountries(countries);

        });
    }
}