
package com.example.samira;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {

   FirebaseAuth auth; // Instance de FirebaseAuth pour gérer l'authentification Firebase
    DatabaseReference reference ;
    ProgressDialog pd;
    EditText username,fullname, email,password; // Champs de nom et d'adresse e-mail et de mot de passe pour l'inscription
    Button register; // Bouton d'inscription
     TextView txt_login; // Texte pour rediriger vers l'activité de connexion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getWindow().setStatusBarColor(ContextCompat.getColor(SignupActivity.this, R.color.gris));// Définit le layout pour cette activité
        auth = FirebaseAuth.getInstance(); // Initialise l'instance de FirebaseAuth
        // Liaison des éléments de l'interface utilisateur avec les variables membres
        username = findViewById(R.id.username);
        fullname = findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        txt_login = findViewById(R.id.txt_login);

// Configuration de l'écouteur pour le texte de redirection vers l'activité de connexion
        txt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class)); // Redirige vers l'activité de connexion
            }
        });
        // Configuration de l'écouteur pour le bouton d'inscription
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd = new ProgressDialog(SignupActivity.this);
                pd.setMessage("please wait");
                pd.show();


                String str_username = username.getText().toString();// Récupère le nom entrée par l'utilisateur
                String str_fullname = fullname.getText().toString(); // Récupère l'adresse e-mail entrée par l'utilisateur
                String str_email = email.getText().toString(); // Récupère le mot de passe entré par l'utilisateur
                String str_password = password.getText().toString();
                // Vérifie si le nom est vide
                if (TextUtils.isEmpty(str_username) || TextUtils.isEmpty(str_fullname) ||
                        TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_password)) {
                    Toast.makeText(SignupActivity.this, "All fieled required ! ", Toast.LENGTH_SHORT).show();
                } else if (str_password.length() < 6) {
                    Toast.makeText(SignupActivity.this, "password must have 6 charachters ! ", Toast.LENGTH_SHORT).show();

                } else {
                Register(str_username,str_fullname,str_email,str_password);
                }
            }
        });
    }

        private void Register(final String username , final String fullname , String email ,String password){
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser firebaseUser =auth.getCurrentUser();
                            String userid =firebaseUser.getUid();
                            reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);
                            HashMap<String, Object> hashMap=new HashMap<>();
                            hashMap.put("id",userid);
                            hashMap.put("username",username.toLowerCase());
                            hashMap.put("fullname",fullname);
                            hashMap.put("bio","");
                            hashMap.put("imageurl","https://firebasestorage.googleapis.com/v0/b/samira-cb354.appspot.com/o/user%20(1).png?alt=media&token=7ed118d3-4600-453a-9fd1-22c5edbb0693");

                             reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                 @Override
                                 public void onComplete(@NonNull Task<Void> task) {
                                     if(task.isSuccessful()){
                                         pd.dismiss();
                                         Intent intent =new Intent(SignupActivity.this,MainActivity.class);
                                         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                           startActivity(intent);
                                 }
                             }

                            });

                        }else{
                            pd.dismiss();
                            Toast.makeText(SignupActivity.this,"you can't register with this email or password!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}


