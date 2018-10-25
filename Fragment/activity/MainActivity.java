package com.example.robin.demoapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements MyFragment.FragmentListener {

    private TextView tvMessageTyped;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Button btnOpenFragment = findViewById(R.id.btn_open_fragment);
        tvMessageTyped = findViewById(R.id.tv_value_message_typed);

        btnOpenFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFragment();
            }
        });
    }

    /**
     * Création d'un fragment dynamiquement (avec arguments) et ouverture
     */
    private void createFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        // Création d'un nouveau fragment
        //MyFragment fragment = new MyFragment();                               // Sans arguments
        MyFragment fragment = MyFragment.newInstance("Hello fragment!");        // Avec arguments

        ft.add(R.id.fragment_container, fragment, "MY_FRAGMENT_TAG");
        ft.addToBackStack(null);

        ft.commit();
    }

    /**
     * Callback du fragment
     * @param message Message à afficher dans l'activité
     */
    @Override
    public void answerFromFragment(String message) {
        tvMessageTyped.setText(message);
    }
}
