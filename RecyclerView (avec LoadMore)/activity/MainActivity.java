/**
RecyclerView dynamically load more items when scroll to end with bottom ProgressBar:
http://www.androidlearningtutorials.com/blog_details.php?article_id=12
http://www.devexchanges.info/2017/02/android-recyclerview-dynamically-load.html
*/

package com.example.robin.demoapp;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Context mContext;

    private ArrayList<HashMap<String,String>> getDatalist;
    private RecyclerView mrecyclerView;
    private RecyclerViewAdapter mAdapter;
    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();
        random = new Random();
        getDatalist = new ArrayList<>();
        
        for (int i = 0 ; i < 20; i++) {
            HashMap<String,String> map = new HashMap<>();
            map.put("KEY_EMAIL","android" + i + "@gmail.com");
            map.put("KEY_PHONE",phoneNumberGenerating());
            getDatalist.add(map);
        }

        mrecyclerView = findViewById(R.id.mRecyclerview);
        mrecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));

        mAdapter = new RecyclerViewAdapter(this, mContext, getDatalist, mrecyclerView);
        mrecyclerView.setAdapter(mAdapter);

        // set RecyclerView on item click listner
        mAdapter.setOnItemListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(HashMap<String, String> item) {
                String mEmail = "";
                String mPhone = "";
                try {
                    mEmail = item.get("KEY_EMAIL");
                    mPhone = item.get("KEY_PHONE");
                }
                catch (Exception ev) {
                    System.out.print(ev.getMessage());
                }
                Toast.makeText(mContext, "Clicked row: \nEmail: " + mEmail + ", Phone: " + mPhone, Toast.LENGTH_LONG).show();
            }
        });


        // Set load more listener for the RecyclerView adapter
        mAdapter.setOnLoadMoreListener(new RecyclerViewAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (getDatalist.size() <= 40) {
                    // On ajoute "null" à la liste de manière à ce que la méthode getItemViewType de l'adapter renvoie
                    // un type égal à "VIEW_TYPE_LOADING", et qu'ainsi le onBindViewHolder affiche la ProgressBar
                    getDatalist.add(null);
                    // Puis on notifie l'adapter de l'insertion, en vue d'afficher la ProgressBar
                    mAdapter.notifyItemInserted(getDatalist.size() - 1);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // On supprime le dernier élement de la liste (un "null") qui servait à afficher la ProgressBar
                            getDatalist.remove(getDatalist.size() - 1);
                            // On notifie l'adapter de la suppression, pour ne plus afficher la ProgressBar
                            mAdapter.notifyItemRemoved(getDatalist.size());

                            // On génère de nouvelles données
                            int index = getDatalist.size();
                            int end = index + 20;
                            for (int i = index; i < end; i++) {
                                HashMap<String,String> map = new HashMap<>();
                                map.put("KEY_EMAIL","android" + i + "@gmail.com");
                                map.put("KEY_PHONE",phoneNumberGenerating());
                                getDatalist.add(map);
                            }

                            // On notifie l'adapter du changement de données
                            mAdapter.notifyDataSetChanged();

                            // On met le flag de loading à false
                            mAdapter.setLoaded();
                        }
                    }, 5000);
                }
                else {
                    Toast.makeText(mContext, "Loading data completed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String phoneNumberGenerating() {
        int low = 100000000;
        int high = 999999999;
        int randomNumber = random.nextInt(high - low) + low;

        return "0" + randomNumber;
    }
}
