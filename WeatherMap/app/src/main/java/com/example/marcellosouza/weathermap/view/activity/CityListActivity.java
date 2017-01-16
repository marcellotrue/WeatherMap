package com.example.marcellosouza.weathermap.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.marcellosouza.weathermap.R;
import com.example.marcellosouza.weathermap.control.Session;
import com.example.marcellosouza.weathermap.model.City;
import com.example.marcellosouza.weathermap.model.Measure;
import com.example.marcellosouza.weathermap.util.Constants;
import com.example.marcellosouza.weathermap.util.RecyclerItemClickListener;
import com.example.marcellosouza.weathermap.view.adapter.AdapterCityList;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import static org.parceler.Parcels.*;

public class CityListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterCityList mAdapterCityList;
    private ProgressDialog pd;
    private GoogleApiClient client;
    private  List<City> cityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);

        recyclerView = (RecyclerView) findViewById(R.id.RecycleViewCits);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                        if (cityList != null) {

                            City city = cityList.get(position);

                            Session.selectedCity = city;

                            Intent intent = new Intent(CityListActivity.this, CityActivity.class);

                            startActivity(intent);


                        }


                    }
                })
        );

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        Double lat = getIntent().getDoubleExtra(Constants.BUNDLE_LAT, 0);
        Double lon = getIntent().getDoubleExtra(Constants.BUNDLE_LONG, 0);

        new JsonTask().execute("http://api.openweathermap.org/data/2.5/find?lat="+lat+"&lon="+lon+"&cnt=15&APPID=18bdb59d98b800d64ba40fdc6fb4e06b");
    }

    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("CityList Page")
                .setUrl(Uri.parse("http://api.openweathermap.org/data/2.5/find?lat={LAT}&lon={LON}&cnt=15&APPID=<APP_ID>"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }


    public class JsonTask extends AsyncTask<String, String, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(CityListActivity.this);
            pd.setMessage("Please Wait");
            pd.setCancelable(false);
            pd.show();

        }

        @Override
        protected JSONObject doInBackground(String... params) {

            URLConnection urlConnection = null;
            BufferedReader bufferedReader = null;

            try {
                URL url = new URL(params[0]);
                urlConnection = url.openConnection();
                bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                StringBuffer stringBuffer = new StringBuffer();
                String line;
                while ((line = bufferedReader.readLine()) !=null)
                {
                    stringBuffer.append(line);
                }

                return new JSONObject(stringBuffer.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    if (bufferedReader != null)
                    {
                        bufferedReader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            return null;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            pd.cancel();

            if(jsonObject == null)
            {


            } else {
                cityList = new ArrayList<>();

                try {
                    Log.e("App","Success: " + jsonObject.getString("message"));

                    JSONArray jsonArray  = jsonObject.getJSONArray("list");

                    for(int n =0; n < jsonArray.length(); n++) {

                        JSONObject jsonObjectItemList = jsonArray.getJSONObject(n);

                        City city = new City();
                        Measure measure = new Measure();

                        city.setId(jsonObjectItemList.getInt("id"));
                        city.setName(jsonObjectItemList.getString("name"));
                        city.setLatitude(jsonObjectItemList.getJSONObject("coord").getDouble("lat"));
                        city.setLongetude(jsonObjectItemList.getJSONObject("coord").getDouble("lon"));

                        measure.setTemperature(jsonObjectItemList.getJSONObject("main").getDouble("temp"));
                        measure.setTempMinimum(jsonObjectItemList.getJSONObject("main").getDouble("temp_min"));
                        measure.setTempMaximun(jsonObjectItemList.getJSONObject("main").getDouble("temp_max"));
                        measure.setPreasurre(jsonObjectItemList.getJSONObject("main").getDouble("pressure"));
                        measure.setSeaLevel(jsonObjectItemList.getJSONObject("main").getDouble("sea_level"));
                        measure.setGrndLevel(jsonObjectItemList.getJSONObject("main").getDouble("grnd_level"));
                        measure.setHumidity(jsonObjectItemList.getJSONObject("main").getInt("humidity"));
                        measure.setData(jsonObjectItemList.getLong("dt"));
                        measure.setWindSpeed(jsonObjectItemList.getJSONObject("wind").getDouble("speed"));
                        measure.setWinddeg(jsonObjectItemList.getJSONObject("wind").getDouble("deg"));
                        measure.setCountry(jsonObjectItemList.getJSONObject("sys").getString("country"));
                        measure.setCloudsAll(jsonObjectItemList.getJSONObject("clouds").getInt("all"));
                        measure.setWeatherId(jsonObjectItemList.getJSONArray("weather").getJSONObject(0).getInt("id"));
                        measure.setWeatherMain(jsonObjectItemList.getJSONArray("weather").getJSONObject(0).getString("main"));
                        measure.setWeatherDescription(jsonObjectItemList.getJSONArray("weather").getJSONObject(0).getString("description"));
                        measure.setWeatherIcon(jsonObjectItemList.getJSONArray("weather").getJSONObject(0).getString("icon"));

                        city.setMeasure(measure);


                        cityList.add(city);

                    }

                     mAdapterCityList = new AdapterCityList(cityList);
                     RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                     recyclerView.setLayoutManager(mLayoutManager);
                     recyclerView.setItemAnimator(new DefaultItemAnimator());
                     recyclerView.setAdapter(mAdapterCityList);

                }
                catch (JSONException exception){
                    Log.e("App","Failure",exception);
                }

            }
        }
    }


}
