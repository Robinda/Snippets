/**
Source : http://www.vogella.com/tutorials/AndroidRecyclerView/article.html#exercise-using-recyclerview-in-a-new-android-application
*/

package com.example.robin.demoapp.snippets;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private GridLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // Si on souhaite une liste simple, utiliser un linear layout manager
        //layoutManager = new LinearLayoutManager(this);

        // Si on souhaite une grille, utiliser un grid layout manager
        int nbColonnes = 2;
        layoutManager = new GridLayoutManager(this, nbColonnes);

        recyclerView.setLayoutManager(layoutManager);

        // populate data
        List<String> input = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            input.add("Test" + i);
        }

        // define an adapter
        mAdapter = new MyRecyclerViewAdapter(input);

        recyclerView.setAdapter(mAdapter);
    }
}