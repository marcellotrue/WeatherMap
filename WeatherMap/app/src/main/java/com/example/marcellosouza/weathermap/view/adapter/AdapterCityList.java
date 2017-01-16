package com.example.marcellosouza.weathermap.view.adapter;

import android.graphics.Movie;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import com.example.marcellosouza.weathermap.R;
import com.example.marcellosouza.weathermap.model.City;

import java.util.List;

/**
 * Created by Marcello Souza on 1/14/2017.
 */

public class AdapterCityList extends RecyclerView.Adapter<AdapterCityList.ViewHolder>{

    private List<City> cityList;

    public AdapterCityList(List<City> cityList) {
        this.cityList = cityList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView city;

        public ViewHolder(View view){
            super(view);
            city = (TextView) view.findViewById(R.id.city);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        City city = cityList.get(position);
        holder.city.setText(city.getName());
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }



}
