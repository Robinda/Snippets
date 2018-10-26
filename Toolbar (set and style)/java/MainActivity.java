package com.example.robin.demoapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Bind the activity layout
        setContentView(R.layout.activity_my_favorites);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = findViewById(R.id.toolbar);

        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        if (ab != null) {
            ab.setDisplayShowTitleEnabled(false); 	// Remove default title text
            ab.setDisplayHomeAsUpEnabled(true); 	// active la flèche de retour vers l'activité précédente

            // Get access to the custom title view
            TextView tvToolbarTitle = toolbar.findViewById(R.id.toolbar_title);
            TextView tvToolbarNotificationsCount = toolbar.findViewById(R.id.toolbar_count_notif);

            // Set data to views
            tvToolbarTitle.setText(R.string.label_my_title);
            tvToolbarNotificationsCount.setText(getNotificationsCount());
        }

        // To be continued...
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Retourne le nombre de notifications non lues
     * return String Nombre de notifications
     */
    private String getNotificationsCount() {
        return "99+";
    }
}