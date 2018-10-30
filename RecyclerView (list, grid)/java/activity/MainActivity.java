/*
 Source : http://www.vogella.com/tutorials/AndroidRecyclerView/article.html#exercise-using-recyclerview-in-a-new-android-application
 */
package com.example.robin.demoapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.robin.demoapp.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.rv_list);
        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // Si on souhaite une liste simple, utiliser un LinearLayoutManager
        //LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        // Si on souhaite une grille, utiliser un GridLayoutManager
        int nbColonnes = 2;
        GridLayoutManager layoutManager = new GridLayoutManager(this, nbColonnes);

        // Set LayoutManager to RecyclerView
        recyclerView.setLayoutManager(layoutManager);

        // Define an adapter
        RVAVehicles mAdapter = new RVAVehicles(getApplicationContext(), createFakeData());

        // Set adapter to RecyclerView
        recyclerView.setAdapter(mAdapter);
    }

    /**
     * Création d'une liste contenant des objets de type "Vehicle"
     * @return ArrayList<Vehicle> Liste
     */
    private ArrayList<Vehicle> createFakeData() {
        ArrayList<Vehicle> arrayList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            arrayList.add(new Vehicle(String.valueOf(i), "Brand_" + i, "Model_" + i));
        }

        return arrayList;
    }
}