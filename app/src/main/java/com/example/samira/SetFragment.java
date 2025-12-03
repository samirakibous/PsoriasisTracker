package com.example.samira;

import android.app.NotificationManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;

public class SetFragment extends Fragment {
    private FirebaseAuth auth;
    private SwitchCompat notificationSwitch;
    private static final String shareURL = "https://play.googlr.com//store/apps/details?id=";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_set, container, false);

        // Find the first button by its ID
        ImageButton aboutButton = view.findViewById(R.id.aboutBtn);
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load AboutFragment when the button is clicked
                About1Fragment about1Fragment = new About1Fragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, about1Fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        SwitchCompat darkModeSwitch = view.findViewById(R.id.switch2);
        darkModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Set the phone to dark mode
                    int nightModeFlags = view.getContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                    if (nightModeFlags != Configuration.UI_MODE_NIGHT_YES) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        // Optional: Restart the activity for changes to take effect immediately
                    }
                } else {
                    // Set the phone to light mode
                    int nightModeFlags = view.getContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                    if (nightModeFlags != Configuration.UI_MODE_NIGHT_NO) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        // Optional: Restart the activity for changes to take effect immediately
                    }
                }
            }
        });

        // Find the second button by its ID
        ImageButton saveButton = view.findViewById(R.id.btn_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load SavedPostsFragment when the button is clicked
                AboutFragment aboutFragment = new AboutFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, aboutFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        ImageButton profileButton = view.findViewById(R.id.profile_btn);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load SavedPostsFragment when the button is clicked
                AccountFragment accountFragment = new AccountFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, accountFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        ImageButton logoutBtn = view.findViewById(R.id.logoutBtn);
        auth = FirebaseAuth.getInstance();

        logoutBtn.setOnClickListener(v -> showLogoutConfirmationDialog());
        ImageButton shareBtn = view.findViewById(R.id.shareBtn);
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareApp();
            }
        });
        notificationSwitch = view.findViewById(R.id.notificationSwitch);

        // Initialize switch state
        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(getContext().NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            boolean areNotificationsEnabled = notificationManager.areNotificationsEnabled();
            notificationSwitch.setChecked(areNotificationsEnabled);
        }

        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Enable notifications
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                    intent.putExtra(Settings.EXTRA_APP_PACKAGE, getActivity().getPackageName());
                    startActivity(intent);
                } else {
                    // Disable notifications
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(getContext().NOTIFICATION_SERVICE);
                        notificationManager.deleteNotificationChannel("your_channel_id");
                    } else {
                        // For older versions, guide the user to the app settings
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.addCategory(Intent.CATEGORY_DEFAULT);
                        intent.setData(Uri.parse("package:" + getActivity().getPackageName()));
                        startActivity(intent);
                    }
                }
            }
        });

        return view;
    }
    private void shareApp() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Share App");
        intent.putExtra(Intent.EXTRA_TEXT, "This is an awesome app for your Android mobile, Check it out: " + shareURL + getActivity().getPackageName());
        startActivity(Intent.createChooser(intent, "Share App Via..."));
    }
    private void showLogoutConfirmationDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Confirmation de déconnexion")
                .setMessage("Êtes-vous sûr de vouloir vous déconnecter?")
                .setPositiveButton("Oui", (dialog, which) -> logout())
                .setNegativeButton("Non", null)
                .show();
    }

    private void logout() {
        auth.signOut();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        requireActivity().finish();
    }
}
