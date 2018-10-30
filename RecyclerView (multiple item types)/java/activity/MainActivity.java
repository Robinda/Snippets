/*
 * Dans cet exemple, on crée une liste contenant 2 types d'objets (Vehicle et Shop).
 * Suivant le type d'objet, l'adapter affiche une liste avec des layouts différents (row_vehicle ou
 * row_shop) et leurs widgets associés
 */
package com.example.robin.demoapp.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.robin.demoapp.R;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.rv_list);
        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // Affichage sous forme de liste
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        // Set layout manager to RecyclerView
        recyclerView.setLayoutManager(layoutManager);

        // Populate data
        ArrayList<Object> myList = createFakeData();

        // Define an adapter
        RVACustomList mAdapter = new RVACustomList(getApplicationContext(), myList);

        // Set adapter to RecyclerView
        recyclerView.setAdapter(mAdapter);
    }

    /**
     * Création d'une liste contenant des objets génériques
     * @return ArrayList<Object> Liste d'objets
     */
    private ArrayList<Object> createFakeData() {
        ArrayList<Object> arrayList = new ArrayList<>();

        arrayList.add(new Vehicle(generateRandomId(), "Ducati", "HyperMotard"));
        arrayList.add(new Vehicle(generateRandomId(),"Volkswagen", "Polo"));
        arrayList.add(new Shop("Cloz' Shop", "91, cours Franklin Roosevelt", "Clothing"));
        arrayList.add(new Shop("Garage Descamps", "60, avenue Rémy Descamps", "Garage / Réparation"));
        arrayList.add(new Vehicle(generateRandomId(),"Husqvarna", "701"));
        arrayList.add(new Shop("Sushi Shop", "25, avenue Étienne Lecomte", "Restaurant"));
        arrayList.add(new Shop("Epicerie de nuit", "3, chemin de Lebreton", "Epicerie"));
        arrayList.add(new Shop("Normand Service", "96, Place Julien Normand", "Service / Vente"));
        arrayList.add(new Vehicle(generateRandomId(),"KTM", "Duke 390"));
        arrayList.add(new Shop("Assurances Mercier", "57, boulevard de Mercier", "Service / Assurance"));
        arrayList.add(new Vehicle(generateRandomId(),"Range Rover", "Evoke"));
        arrayList.add(new Shop("100% Cyclisme", "125, rue William Hebert", "Service / Réparation cycles"));
        arrayList.add(new Vehicle(generateRandomId(),"Audi", "A7"));
        arrayList.add(new Vehicle(generateRandomId(),"Peugeot", "308"));
        arrayList.add(new Shop("Boulangerie", "545, place Samson", "Boulangerie"));
        arrayList.add(new Vehicle(generateRandomId(),"Seat", "Leon"));
        arrayList.add(new Vehicle(generateRandomId(),"Porsche", "Macan"));
        arrayList.add(new Vehicle(generateRandomId(),"Renault", "Megane"));
        arrayList.add(new Shop("Coiffure Création", "80, avenue de Leblanc", "Service / Coiffure"));
        arrayList.add(new Shop("Cabinet Docteur Lagarde", "37, impasse Céline Lagarde", "Service / Médecine générale"));

        return arrayList;
    }

    /**
     * Génération aléatoire d'un Id
     * @return String ID
     */
    private String generateRandomId() {
        int min = 1000;
        int max = 9999;
        Random random = new Random();
        int randomNumber = random.nextInt(max - min + 1) + min;

        return String.valueOf(randomNumber);
    }
}
