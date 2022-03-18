package org.ecaib.countries;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class CountriesApiClient {

    //String BASE_URL = "https://countriesnow.space/api/v0.1/countries/";
    String BASE_URL = "https://restcountries.com/v2/all";

    public ArrayList<Country> getCountries() {

        Uri builtUri = Uri.parse(BASE_URL)
                .buildUpon()
                .build();

        String url = builtUri.toString();
        ArrayList<Country> response = doCall(url);

        return response;
    }

/*
    public String getFlagsUrls(){

        Uri builtUri = Uri.parse(BASE_URL)
                .buildUpon()
                .appendPath("flag")
                .appendPath("images")
                .build();

        String url = builtUri.toString();
        String response = doCallFlags(url);
        return response;

    }

 */



    private ArrayList<Country> doCall(String url) {
        try{
            String JsonResponse = HttpUtils.get(url);

            return parseJson(JsonResponse);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

/*
    private String doCallFlags(String url) {
        try{

            String response = HttpUtils.get(url);
            return  response;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

 */



    private ArrayList<Country> parseJson(String jsonResponse) {
        ArrayList<Country> countries = new ArrayList<>();
        try {
            //JSONObject countriesResponse = new JSONObject(jsonResponse);
            JSONArray array =  new JSONArray(jsonResponse);

            /*
            // Getting all flags image urls into a JsonArray
            JSONObject jsonFlagResponse = new JSONObject(getFlagsUrls());
            JSONArray flagsArray = jsonFlagResponse.getJSONArray("data");

             */



            // Here we build up Country objects one by one
            for (int i = 0; i < array.length(); i++) {
                Country country = new Country();

                JSONObject object = array.getJSONObject(i);

                if(object.has("name")){
                    country.setName(object.getString("name"));
                }
                if(object.has("alpha2Code")){
                    country.setAlpha2Code(object.getString("alpha2Code"));
                }
                if(object.has("alpha3Code")){
                    country.setAlpha3Code(object.getString("alpha3Code"));
                }
                if(object.has("capital")){
                    country.setCapital(object.getString("capital"));
                }
                if(object.has("subregion")){
                    country.setSubregion(object.getString("subregion"));
                }
                if(object.has("region")){
                    country.setRegion(object.getString("region"));
                }
                if(object.has("population")){
                    country.setPopulation(object.getInt("population"));
                }
                if(object.has("latlang")){
                    // latlang is an array, so we have to get inside to get the two integers
                    JSONArray latlang = object.getJSONArray("latlang");
                    country.setLatitud(latlang.getInt(0));
                    country.setLongitud(latlang.getInt(1));
                }
                if(object.has("demonym")){
                    country.setDemonym(object.getString("demonym"));
                }
                if(object.has("area")){
                    country.setArea(object.getInt("area"));
                }


                if(object.has("flags")){
                    // flags is an array, so we have to get inside to get the png
                    JSONObject flags = object.getJSONObject("flags");
                    country.setFlagUrl(flags.getString("png"));
                }








                /*
                // We search the flag in the flags array
                for (int j = 0; i< flagsArray.length() -2; j++) {
                    JSONObject flagObject = flagsArray.getJSONObject(j);
                    //Log.e("flagObject", flagObject.toString());

                    // If we find the country name on the list, add its flagUrl to the Country object
                    if(flagObject.getString("name").equalsIgnoreCase(country.getName())){
                        country.setFlagUrl(flagObject.getString("flag"));
                        Log.e("FLAGURL", flagObject.getString("flag"));
                        break;
                    } else{
                        Log.e("ELSE", "Comparando " + flagObject.getString("name") + " con " + country.getName());
                    }
                }

                 */



                countries.add(country);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return countries;
    }

}
