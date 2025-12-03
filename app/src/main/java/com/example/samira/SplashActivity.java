package com.example.samira;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class SplashActivity extends AppCompatActivity {
    VideoView videoView;
    MediaPlayer MediaPlayer;
    private static final int SPLASH_TIME_OUT = 5000; // Durée de l'écran de démarrage en millisecondes

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        videoView = findViewById(R.id.splash);
        videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.splashv); // Remplacer R.raw.splash_video par le nom de votre fichier vidéo dans le dossier /res/raw/

        videoView.setOnCompletionListener(mp -> {
            // Après la fin de la lecture de la vidéo, démarrer l'activité principale de l'application
            Intent mainIntent = new Intent(SplashActivity.this,LoginActivity.class);
            startActivity(mainIntent);
            finish(); // Fermer l'écran de démarrage pour éviter qu'il ne soit rappelé si l'utilisateur appuie sur le bouton de retour
        });

        videoView.start(); // Démarrer la lecture de la vidéo

        // Utiliser un Handler pour définir un délai avant de démarrer l'activité principale de l'application
        new Handler().postDelayed(() -> {
            Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(mainIntent);
            finish(); // Fermer l'écran de démarrage pour éviter qu'il ne soit rappelé si l'utilisateur appuie sur le bouton de retour
        }, SPLASH_TIME_OUT);
    };
}