package com.example.samira;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    private Button button1, button2, button3, button4;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
       /* view.findViewById(R.id.my_button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SuivFragment fragmentB = new SuivFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragmentB)
                        .addToBackStack(null)
                        .commit();
            }
        });*/

        // Initialize buttons
        button1 = view.findViewById(R.id.my_button1);
        button2 = view.findViewById(R.id.my_button2);
        button3 = view.findViewById(R.id.my_button3);
        button4 = view.findViewById(R.id.my_button4);

        // Set click listeners for buttons
       button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start Activity1 when button1 is clicked
                Intent intent = new Intent(getActivity(),SuivActivity.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start Activity2 when button2 is clicked
                Intent intent = new Intent(getActivity(), MoodActivity.class);
                startActivity(intent);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start Activity3 when button3 is clicked
                Intent intent = new Intent(getActivity(), TreatmentActivity.class);
                startActivity(intent);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start Activity4 when button4 is clicked
                Intent intent = new Intent(getActivity(), RegimeActivity2.class);
                startActivity(intent);
            }
        });


        // Accéder à l'activité parente
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        // Changer la couleur de la barre d'état
        if (activity != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.setStatusBarColor(ContextCompat.getColor(activity, R.color.gris));
            }
        }


        return view;
    }
}
