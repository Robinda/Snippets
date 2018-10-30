package com.example.robin.demoapp;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements DialogYesNoFragment.DialogListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnOpenDialogBox = findViewById(R.id.btn_open_dialog_box);
        Context context = getApplicationContext();

        btnOpenDialogBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();

                // Get image resource Id (use "drawable" for drawables, "string" for strings, etc.)
                int iconResourceId = context.getResources().getIdentifier("ic_dialog_box_icon", "drawable", context.getPackageName());

                DialogYesNoFragment dialogBox = DialogYesNoFragment.newInstance(
                        iconResourceId,
                        "Confirmation du vol",
                        "Etes-vous sûr de vouloir confirmer le Vol 714 pour Sydney ?",
                        "Confirmer",
                        "Annuler",
                        false);

                dialogBox.show(fm, "tag_dialog_box");
                dialogBox.setDialogListener(this);
            }
        });
    }

    @Override
    public void onConfirm(int result) {
        if(result == DialogYesNoFragment.DIALOG_RESULT_POSITIVE) {
            Toast.makeText(getApplicationContext(), "Clické oui", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "Clické non", Toast.LENGTH_SHORT).show();
        }
    }
}