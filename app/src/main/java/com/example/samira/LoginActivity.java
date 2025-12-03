package com.example.samira;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    EditText email, password; // Champs d'e-mail et de mot de passe pour la connexion
    TextView txt_signup, forgotpassword; // Texte pour rediriger vers l'activité d'inscription
    Button login; // Bouton de connexion
    FirebaseAuth auth; // Instance de FirebaseAuth pour gérer l'authentification Firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // Définit le layout pour cette activité
        getWindow().setStatusBarColor(ContextCompat.getColor(LoginActivity.this, R.color.gris));

        // Liaison des éléments de l'interface utilisateur avec les variables membres
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        txt_signup = findViewById(R.id.txt_signup);
        forgotpassword = findViewById(R.id.forgot_password);
        auth = FirebaseAuth.getInstance(); // Initialise l'instance de FirebaseAuth

        // Configuration de l'écouteur pour le texte de redirection vers l'activité d'inscription
        txt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class)); // Redirige vers l'activité d'inscription
            }
        });

        // Configuration de l'écouteur pour le texte de redirection vers l'activité de réinitialisation de mot de passe
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotpassActivity.class);
                startActivity(intent);
            }
        });

        // Configuration de l'écouteur pour le bouton de connexion
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog pd = new ProgressDialog(LoginActivity.this);
                pd.setMessage("please wait . . .");
                pd.show();
                String str_email = email.getText().toString(); // Récupère l'e-mail entré par l'utilisateur
                String str_password = password.getText().toString(); // Récupère le mot de passe entré par l'utilisateur

                if (TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_password)) {
                    pd.dismiss();
                    Toast.makeText(LoginActivity.this, "all fields are required", Toast.LENGTH_SHORT).show();
                } else {
                    auth.signInWithEmailAndPassword(str_email, str_password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        if (str_email.equals("siham@gmail.com")) {
                                            // Si l'utilisateur est l'administrateur, redirige vers l'activité admin
                                            pd.dismiss();
                                            startActivity(new Intent(LoginActivity.this, AdminActivity.class));
                                            finish();
                                        } else {
                                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users")
                                                    .child(auth.getCurrentUser().getUid());

                                            reference.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    pd.dismiss();
                                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    startActivity(intent);
                                                    finish();
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {
                                                    pd.dismiss();
                                                }
                                            });
                                        }
                                    } else {
                                        pd.dismiss();
                                        Toast.makeText(LoginActivity.this, "Authentication failed!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}
