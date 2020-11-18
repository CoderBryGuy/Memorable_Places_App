package com.example.memorable_places_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView myListView;
    static ArrayList<String> places = new ArrayList<>();
    static ArrayList<LatLng> locations = new ArrayList<>();
    static ArrayAdapter<String> arrayAdapter;
    SharedPreferences sharedPreferences;



    private void hideActionBar() {
        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.hide();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = this.getSharedPreferences("com.example.memorable_places_app", Context.MODE_PRIVATE);

        ArrayList<String> latitudes = new ArrayList<>();
        ArrayList<String> longitudes = new ArrayList<>();

        places.clear();
        latitudes.clear();
        longitudes.clear();
        locations.clear();

        try{
            places = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("places", ObjectSerializer.serialize(new ArrayList<String>())));
            longitudes = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("lats", ObjectSerializer.serialize(new ArrayList<String>())));
            latitudes = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("long", ObjectSerializer.serialize(new ArrayList<String>())));

        }catch (Exception e){
            e.printStackTrace();
        }

        if(places.size() > 0 && latitudes.size() > 0 && longitudes.size() > 0){
            if(places.size() == latitudes.size() && places.size() == longitudes.size()){
                for(int i = 0; i < latitudes.size(); i++){
                    locations.add(new LatLng(Double.parseDouble(latitudes.get(i)), Double.parseDouble(longitudes.get(i))));
                }
            }else{
                places.add("Add a new places...");
                locations.add(new LatLng(0,0));
            }
        }

        hideActionBar();



        myListView = (ListView) findViewById(R.id.myListView);







        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, places);

        myListView.setAdapter(arrayAdapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("placeNumber", i);

                startActivity(intent);
            }
        });
    }


}