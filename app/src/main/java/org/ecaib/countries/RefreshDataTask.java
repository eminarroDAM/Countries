package org.ecaib.countries;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

public class RefreshDataTask extends AsyncTask<Void, Void, Void> {
    @Override
    protected Void doInBackground(Void... voids) {
        CountriesApiClient api = new CountriesApiClient();

        ArrayList<Country> countries = api.getCountries();

        Log.d(null, countries.toString());
        return null;
    }
}
