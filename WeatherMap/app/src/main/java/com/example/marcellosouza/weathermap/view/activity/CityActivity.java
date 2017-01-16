package com.example.marcellosouza.weathermap.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.marcellosouza.weathermap.R;
import com.example.marcellosouza.weathermap.control.Session;
import com.example.marcellosouza.weathermap.model.City;
import com.example.marcellosouza.weathermap.util.Constants;

import org.parceler.Parcels;

public class CityActivity extends AppCompatActivity {

    TextView cityName, temMaximum, temMinimum, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        City city = Session.selectedCity;

        cityName = (TextView) findViewById(R.id.textCity);
        temMaximum = (TextView) findViewById(R.id.resultMaxi);
        temMinimum = (TextView) findViewById(R.id.resultMin);
        description = (TextView) findViewById(R.id.textDescicao);

        if (city != null) {

            int max,min;
            max = (int) convertFahrenheitToCelcius(city.getMeasure().getTempMaximun());
            min = (int) convertFahrenheitToCelcius(city.getMeasure().getTempMinimum());

            temMaximum.setText(""+ max);
            temMinimum.setText(""+ min);
            cityName.setText(city.getName());
            description.setText(city.getMeasure().getWeatherDescription());
        }

    }


    private double convertFahrenheitToCelcius(double fahrenheit) {
        return (((fahrenheit - 32) * 5) / 9);
    }


}
