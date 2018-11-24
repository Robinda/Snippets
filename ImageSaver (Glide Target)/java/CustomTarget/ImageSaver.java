package com.example.robin.demoapp.test;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.robin.demoapp.App;
import com.example.robin.demoapp.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class ImageSaver implements Target<Drawable> {

    private static final String TAG = ImageSaver.class.getSimpleName();

    private final static int DEFAULT_COMPRESS_QUALITY = 100;
    private final static String DEFAULT_STORAGE_DIRECTORY_PATH =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    + "/" + App.get().getString(R.string.app_name);
    private final static String DEFAULT_IMAGE_FILE_NAME = "Image";
    private final static Bitmap.CompressFormat DEFAULT_IMAGE_FORMAT = Bitmap.CompressFormat.JPEG;
    private final static String DEFAULT_IMAGE_FILE_EXTENSION = ".jpg";

    private Context context;
    private ImageSaverListener mImageSaverListener;
    private String storageDirectoryPath, imageFileName, imageExtension;
    private int width, height, quality;
    private Bitmap.CompressFormat imageFormat;

    /**
     * Interface de communication entre ImageSaver et l'activité parente
     */
    public interface ImageSaverListener {
        void imageSaverResult(String imagePath);
        void imageSaverCatchException(Exception e);
    }

    /**
     * Initialisation du listener
     * @param listener Listener
     */
    public void setImageSaverListener(ImageSaverListener listener) {
        this.mImageSaverListener = listener;
    }

    public ImageSaver() {
        this(DEFAULT_STORAGE_DIRECTORY_PATH,
                DEFAULT_IMAGE_FILE_NAME,
                -1,
                -1,
                Bitmap.CompressFormat.JPEG,
                DEFAULT_IMAGE_FILE_EXTENSION,
                DEFAULT_COMPRESS_QUALITY);
    }

    public ImageSaver(String storageDirectoryPath, String imageFileName) {
        this(storageDirectoryPath,
                imageFileName,
                -1,
                -1,
                Bitmap.CompressFormat.JPEG,
                DEFAULT_IMAGE_FILE_EXTENSION,
                DEFAULT_COMPRESS_QUALITY);

    }

    public ImageSaver(String storageDirectoryPath, String imageFileName, int width,
                      int height) {
        this(storageDirectoryPath,
                imageFileName,
                width,
                height,
                DEFAULT_IMAGE_FORMAT,
                DEFAULT_IMAGE_FILE_EXTENSION,
                DEFAULT_COMPRESS_QUALITY);
    }

    /**
     * Constructeur
     * @param storageDirectoryPath Chemin du répertoire de stockage
     * @param imageFileName Nom de l'image (sans l'extension)
     * @param width Largeur souhaitée (ratio conservé avec la hateur)
     * @param height Hateur souhaitée (ratio conservé avec la largeur)
     * @param imageFormat Format de l'image (JPEG par défaut)
     * @param imageExtension Nom de l'extension de l'image (".jpg")
     * @param quality Taux de compression (0-100)
     */
    public ImageSaver(String storageDirectoryPath, String imageFileName, int width, int height,
                      Bitmap.CompressFormat imageFormat, String imageExtension, int quality) {
        this.context = App.get().getApplicationContext();
        this.storageDirectoryPath = storageDirectoryPath;
        this.imageFileName = imageFileName;
        this.width = width;
        this.height = height;
        this.imageFormat = imageFormat;
        this.imageExtension = imageExtension;
        this.quality = quality;
    }

    @Override
    public void onLoadStarted(@Nullable Drawable placeholder) {

    }

    @Override
    public void onLoadFailed(@Nullable Drawable errorDrawable) {

    }

    @Override
    public void onResourceReady(@NonNull Drawable drawable, @Nullable Transition<? super Drawable> transition) {
        String savedImagePath = saveImageOnDevice(drawable);

        // Notification à l'appelant : l'image à bien été sauvegardée
        if (mImageSaverListener != null) {
            mImageSaverListener.imageSaverResult(savedImagePath);
        }
    }

    @Override
    public void onLoadCleared(@Nullable Drawable placeholder) {

    }

    @Override
    public void getSize(@NonNull SizeReadyCallback cb) {
        if(width >= 0 && height >= 0) {
            cb.onSizeReady(width, height);  // Glide applique une des 2 tailles (la plus grande en conservant le ratio)
        }
        else {
            cb.onSizeReady(SIZE_ORIGINAL, SIZE_ORIGINAL);
        }
    }

    @Override
    public void removeCallback(@NonNull SizeReadyCallback cb) {

    }

    @Override
    public void setRequest(@Nullable Request request) {

    }

    @Nullable
    @Override
    public Request getRequest() {
        return null;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }

    /**
     * Enregistre une image sur le device à partir d'un drawable
     * @param drawable Drawable source
     * @return Chemin de l'image enregistrée
     */
    private String saveImageOnDevice(Drawable drawable) {

        Bitmap image = getBitmapFromDrawable(drawable);

        String savedImagePath = "";
        File storageDir = new File(storageDirectoryPath);

        boolean success = true;

        if (!storageDir.exists()) {
            success = storageDir.mkdirs();
        }

        if (success) {
            File imageFile = new File(storageDir, imageFileName + imageExtension);
            savedImagePath = imageFile.getAbsolutePath();

            try {
                OutputStream outputStream = new FileOutputStream(imageFile);
                image.compress(imageFormat, quality, outputStream);
                outputStream.flush();
                outputStream.close();
            }
            catch (Exception e) {
                e.printStackTrace();
                mImageSaverListener.imageSaverCatchException(e);
            }

            // Add the image to the system gallery
            addImageToSystemGallery(savedImagePath);
        }
        else {
            Log.e(TAG, "Erreur : le dossier de destination n'existe pas et n'a pas pu être créé ! " +
                    "Les autorisations d'écriture et/ou lecture sont peut être manquantes.");
        }

        return savedImagePath;
    }

    /**
     * Récupère le Bitmap depuis un Drawable
     * @param drawable Drawable source
     * @return Bitmap
     */
    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        Bitmap bitmap;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        }
        else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    /**
     * Add the image to the system gallery
     * @param imagePath Chemin de l'image
     */
    private void addImageToSystemGallery(String imagePath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);

        File file = new File(imagePath);
        Uri contentUri = Uri.fromFile(file);
        mediaScanIntent.setData(contentUri);

        context.sendBroadcast(mediaScanIntent);
    }
}
