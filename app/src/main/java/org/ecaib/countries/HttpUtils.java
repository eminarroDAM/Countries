package org.ecaib.countries;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HttpUtils {
    public static String get(String dataUrl) throws IOException {

        URL url = new URL(dataUrl);
        String response = null;

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try{
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            response = readStream(in);
        } finally {
            urlConnection.disconnect();
        }
        return response;
    }



    public static String post(String dataUrl) throws IOException, JSONException {



        // CON OKHTTP (FLAG INDIVIDUAL)
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = new FormBody.Builder()
                .add("iso2", "NG")
                .build();

        Request request = new Request.Builder()
                .url(dataUrl)
                .post(body)
                .build();



        try (Response response = client.newCall(request).execute()) {
            Log.e("jasñkjb", response.peekBody(2048).string());
            return response.peekBody(2048).string();
        } catch (Exception e){
            Log.e("ERRFLAG", e.toString());
            return null;
        }




        /*
        //CON HTTPSURLCONNECTION
        URL url = new URL ("https://countriesnow.space/api/v0.1/countries/flag/images");
        String response = null;
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        //con.setRequestProperty("Content-Type", "application/json; utf-8");
        //con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);

        try(OutputStream os = con.getOutputStream()) {
            byte[] input = bodyContent.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        try{
            InputStream in = new BufferedInputStream(con.getInputStream());
            response = readStream(in);
        } finally {
            con.disconnect();
        }

         */
    }


    private static String readStream(InputStream in) throws IOException {
        InputStreamReader is = new InputStreamReader(in);
        BufferedReader rd = new BufferedReader(is);
        String line;
        StringBuilder response = new StringBuilder();
        while ((line = rd.readLine()) != null) {
            response.append(line);
            response.append('\r');
        }
        rd.close();
        return response.toString();

    }
}
