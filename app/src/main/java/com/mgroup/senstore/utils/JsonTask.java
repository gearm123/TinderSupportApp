package com.mgroup.senstore.utils;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import org.json.JSONObject;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;
import java.io.InputStreamReader;

import org.json.JSONArray;
import info.guardianproject.netcipher.NetCipher;

import javax.net.ssl.HttpsURLConnection;

import static android.content.Context.TELEPHONY_SERVICE;

public class JsonTask extends AsyncTask<Void, Void, JSONObject> {

    private String responseObj = "";
    private AsyncResponse delegate = null;
    private Context context;

    public JsonTask(Context context)
    {
        this.context=context;
    }

    @Override
    protected JSONObject doInBackground(Void... params) {
        getJsonObject();
        try {
            return new JSONObject(responseObj);
        }
        catch(Exception e)
        {
            Log.v("MGCarAppStore", "cannot get json");
            return null;
        }
    }

    @Override
    protected void onPostExecute(JSONObject response) {
        JSONArray array = null;

        if (response != null) {
            Log.v("MGCarAppStore", "received json object successfully");

            try {
                array = response.getJSONArray("endpoint_response_items_array");
            } catch (Exception e) {
                Log.v("MGCarAppStore", "exception parsing json");
            }

            delegate.processFinish(array);

        }
    }


    private void getJsonObject() {
        try {
            Log.v("MGCarAppStore", "not sending http request");
            String url = "http://mgactivity.com/cup/getSensors.php";
            HttpsURLConnection client = NetCipher.getHttpsURLConnection(url);
            client.setRequestMethod("POST");
            client.setDoOutput(true);
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("model", Build.MODEL)
                    .appendQueryParameter("name",Build.PRODUCT)
                    .appendQueryParameter("imei",getImei());
            String query = builder.build().getEncodedQuery();
            OutputStream os = client.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();
            int responseCode = client.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {

                Log.v("MGCarAppStore", "could not receive json url from server");
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    client.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            responseObj=response.toString();
            in.close();

        } catch (Exception e) {
            Log.v("MGCarAppStore","exception in receiving json URL " +e);

        }
    }

    private String getImei()
    {
        String imei=null;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        if (telephonyManager != null) {
            try {
                imei = telephonyManager.getDeviceId();
                Log.v("MGCarAppStore","device imei is "+ imei);
            }

            catch(Exception e)
            {
                Log.v("MGCarAppStore","could not read imei");
            }
        }

        return imei;

    }


    public void setResonse(AsyncResponse res)
    {
        delegate=res;
    }


}

