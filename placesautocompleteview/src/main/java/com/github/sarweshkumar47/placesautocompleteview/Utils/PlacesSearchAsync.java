package com.github.sarweshkumar47.placesautocompleteview.Utils;

import android.os.AsyncTask;
import android.util.Log;

import com.github.sarweshkumar47.placesautocompleteview.Models.AllPlacesDetails;
import com.github.sarweshkumar47.placesautocompleteview.Models.PlacesCustomInfo;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

import static com.github.sarweshkumar47.placesautocompleteview.Utils.Constants.BUILD_DEBUG;

public class PlacesSearchAsync {

    private String TAG = "PlacesSearchAsync";
    private DownloadPlaces mDownloadPlaces = null;
    private String mGeocodingUrl = null;

    public PlacesSearchAsync(String url) {
        if(BUILD_DEBUG) Log.d(TAG, "PlacesAutoCompleteView, PlacesSearchAsync AsyncTask constructor");
        mGeocodingUrl = url;
    }

    public ArrayList<PlacesCustomInfo> check() {

        if(mDownloadPlaces == null) {
            //Log.d(TAG, "PlacesAutoCompleteView, PlacesSearchAsync AsyncTask is null");
            // --- create a new task --
            mDownloadPlaces = new DownloadPlaces();
            try {
                return mDownloadPlaces.execute().get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        else if(mDownloadPlaces.getStatus() == AsyncTask.Status.FINISHED){
            //Log.d(TAG, "PlacesAutoCompleteView, PlacesSearchAsync AsyncTask is finished");
            // --- the task finished, so start another one --
            mDownloadPlaces = new DownloadPlaces();
            try {
                return mDownloadPlaces.execute().get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        else if(mDownloadPlaces.getStatus() == AsyncTask.Status.RUNNING ||
                mDownloadPlaces.getStatus() == AsyncTask.Status.PENDING) {
            //Log.d(TAG, "PlacesAutoCompleteView, PlacesSearchAsync AsyncTask is running | pending");
            // --- the task is running, call pause function --
            mDownloadPlaces.cancel(true);
            mDownloadPlaces = new DownloadPlaces();
            try {
                return mDownloadPlaces.execute().get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return  null;
    }


    private class DownloadPlaces extends AsyncTask<Void, Void, ArrayList<PlacesCustomInfo>> {

        @Override
        protected ArrayList<PlacesCustomInfo> doInBackground(Void... params) {

            try {
                if (isCancelled()) {
                    //Log.e(TAG, "PlacesAutoCompleteView, PlacesSearchAsync Asynctask isCancelled 1");
                    return null;
                }
                String Geo_URL = mGeocodingUrl.replaceAll(" ", "%20");
                if(BUILD_DEBUG) Log.d(TAG, "PlacesAutoCompleteView, PlacesSearchAsync Asynctask url: \n"+Geo_URL);
                URL url = new URL(Geo_URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                //connection.setConnectTimeout(3000);
                if (isCancelled()) {
                    //Log.e(TAG, "PlacesAutoCompleteView, PlacesSearchAsync Asynctask isCancelled 2");
                    return null;
                }
                InputStream in = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine()) != null){
                    sb.append(line).append("\n");
                }
                if (isCancelled()) {
                    //Log.e(TAG, "PlacesAutoCompleteView, PlacesSearchAsync Asynctask isCancelled 3");
                    return null;
                }
                //parse JSON and store it in the list
                String jsonString =  sb.toString();
                if(BUILD_DEBUG) Log.d(TAG, "PlacesAutoCompleteView, PlacesSearchAsync Asynctask doInBackground jsonString" + jsonString);
                ArrayList<PlacesCustomInfo> arr = null;
                try {
                    JSONArray jsonArray = new JSONArray(jsonString);
                    /*for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObj = jsonArray.getJSONObject(i);
                        //System.out.println("TestAutoComplete: "+ jsonObj);
                    }*/
                    arr = convert_json_to_array_or_list(jsonArray);
                } catch (JSONException e) {
                    if(BUILD_DEBUG) Log.d(TAG, "PlacesAutoCompleteView, PlacesSearchAsync Asynctask doInBackground JSONException");
                }
                if(BUILD_DEBUG) Log.d(TAG, "PlacesAutoCompleteView, PlacesSearchAsync Asynctask doInBackground over");
                return arr;
            } catch (IOException e) {
                if(BUILD_DEBUG) Log.e(TAG, "PlacesAutoCompleteView, PlacesSearchAsync Asynctask IOException");
            } catch (Exception e) {
                if(BUILD_DEBUG) Log.e(TAG, "PlacesAutoCompleteView, PlacesSearchAsync Asynctask Exception");
            }
            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            if(BUILD_DEBUG) Log.i(TAG, "PlacesAutoCompleteView, PlacesSearchAsync Asynctask onCancelled");
        }
    }

    private ArrayList<PlacesCustomInfo> convert_json_to_array_or_list(JSONArray ja) {

        ArrayList<PlacesCustomInfo> listOfFilteredPlaces = new ArrayList<>();

        JsonElement mJsonElement = new JsonParser().parse(ja.toString());
        JsonArray mJsonArray = mJsonElement.getAsJsonArray();
        Iterator iterator = mJsonArray.iterator();
        AllPlacesDetails allPlacesDetails = null;
        while (iterator.hasNext()) {
            JsonElement mJsonElement2 = (JsonElement) iterator.next();
            Gson gson = new Gson();
            allPlacesDetails = gson.fromJson(mJsonElement2, AllPlacesDetails.class);
            PlacesCustomInfo placesCustomInfo = new PlacesCustomInfo();
            placesCustomInfo.setPlaceName(allPlacesDetails.getDisplayName());
            placesCustomInfo.setLatitude(Double.valueOf(allPlacesDetails.getLat()));
            placesCustomInfo.setLongitude(Double.valueOf(allPlacesDetails.getLon()));
            int i = 0; ArrayList<Double> bb = new ArrayList<>();
            while (i < allPlacesDetails.getGeoLocation().size()) {
                    bb.add(i, Double.valueOf(allPlacesDetails.getGeoLocation().get(i)));
                    i++;
            }
            placesCustomInfo.setGeoBoundingBox(bb);
            listOfFilteredPlaces.add(placesCustomInfo);
        }
        return listOfFilteredPlaces;
    }
}
