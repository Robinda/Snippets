/*
Sources : 
https://bumptech.github.io/glide/
https://futurestud.io/tutorials/glide-callbacks-simpletarget-and-viewtarget-for-custom-view-classes
*/
package com.example.robin.demoapp.test;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.robin.demoapp.R;
import com.example.robin.demoapp.helper.GlideApp;

public class MainActivity extends AppCompatActivity implements ImageSaver.ImageSaverListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String IMAGE_URL = "https://picsum.photos/1440/900/?random";

    private ImageSaver target;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();
        ImageView mImageView = findViewById(R.id.iv_image);
        Button btnSaveImage = findViewById(R.id.btn_save);

        //TODO Gérér la permission "storage" avant de lancer la sauvegarde !

        String folderPathname = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                + "/AppName";
        String wallpaperName = "Image";

		/*
		Technically, Java/Android would allow you to declare the target anonymously in the .into() method. 
		However, this significantly increases the chance that the Android garbage collector removes the anonymous target object before Glide was 
		done with the image request. Eventually, this would lead to a situation, where the image is loaded, but the callback can never be called. 
		So please make sure you declare your callback objects as field objects.
		Note : if you use a RequestListener, it works the both ways (as a field objecr or directly into the .listener Glide's method), 
		*/
        target = new ImageSaver(folderPathname, wallpaperName);
        target.setImageSaverListener(this);

        // Au clic, l'image sera sauvegardée avec la Target Glide
        btnSaveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlideApp.with(mContext)
                        .load(IMAGE_URL)
                        .into(target);
            }
        });

        // Aperçu de l'image dans l'imageView (n'intervient pas dans la sauvegarde)
        GlideApp.with(mContext)
                .load(IMAGE_URL)
                .into(mImageView);
    }

    @Override
    public void imageSaverResult(String imagePath) {
        Toast.makeText(mContext, "Image sauvegardée avec succès (" + imagePath + ")", Toast.LENGTH_LONG).show();
    }

    @Override
    public void imageSaverCatchException(Exception e) {
        Log.e(TAG, "Erreur : impossible de sauvegarde l'image !");
        e.printStackTrace();
    }
}
