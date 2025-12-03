package com.example.samira;
import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samira.adapter.MedAdapter;
import com.example.samira.model.MedicineLibrary;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import androidx.appcompat.widget.Toolbar;
import java.util.ArrayList;
import java.util.List;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class TreatmentActivity  extends AppCompatActivity{
    Button startButton,stopButton;
    RecyclerView thishomerecycler;
    MedAdapter myadapter;
    DatabaseReference mBase;
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    String UserID;
    Query query;
    FloatingActionButton fab;

    CalendarView calendar;
    MedicineLibrary medicineLibrary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment2);


        medicineLibrary = new MedicineLibrary();

        calendar = findViewById(R.id.calendarView);
        thishomerecycler = findViewById(R.id.homeRecycler);
        calendar.getDate();


        View backBtn = findViewById(R.id.back_btn2);
        backBtn.setOnClickListener((v)-> onBackPressed() );


        fab = findViewById(R.id.addMedicine);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TreatmentActivity.this, AddMedicineActivity.class);
                startActivity(intent);
            }
        });
        UserID = fAuth.getCurrentUser().getUid();
        Log.d(TAG, "UserID: "+UserID);

        try{
            thishomerecycler.setLayoutManager(new LinearLayoutManager(this));

            mBase = FirebaseDatabase.getInstance().getReference("Medicine");
            query = mBase.orderByChild("medicineID").equalTo(UserID);
            Log.d(TAG, "mBase: "+mBase);

            FirebaseRecyclerOptions<MedicineLibrary> options = new FirebaseRecyclerOptions.Builder<MedicineLibrary>()
                    .setQuery(query,MedicineLibrary.class)
                    .build();
            myadapter = new MedAdapter(options);
            thishomerecycler.setAdapter(myadapter);

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"no data",Toast.LENGTH_SHORT).show();
        }





    }

    @Override
    protected void onStart() {
        super.onStart();
        myadapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        myadapter.stopListening();
    }



    private void stopMyService() {
        Intent serviceIntent = new Intent(this, MyService.class);
        stopService(serviceIntent);
    }

    private void startMyService() {
        Intent serviceIntent = new Intent(this, MyService.class);
        serviceIntent.putExtra("inputExtra", "Running by bimal");
        ContextCompat.startForegroundService(this, serviceIntent);
    }


}


       /* implements View.OnClickListener {

    private FloatingActionButton addTaskButton;
    private DatabaseReference tasksRef;
    private ValueEventListener tasksValueEventListener;
    private List<Task> taskList;
    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    FirebaseAuth firebaseAuth;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment2);

        mAuth = FirebaseAuth.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        String userId = firebaseAuth.getCurrentUser().getUid();

        // Create a reference to the tasks node in the Firebase database
        tasksRef = FirebaseDatabase.getInstance().getReference("users")
                .child(userId)
                .child("tasks");

        addTaskButton = findViewById(R.id.addTaskButton);
        addTaskButton.setOnClickListener(this);

        recyclerView = findViewById(R.id.recyclerView);
        taskList = new ArrayList<>();
        taskAdapter = new TaskAdapter(taskList, tasksRef, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(taskAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        tasksValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                taskList.clear();
                for (DataSnapshot taskSnapshot : dataSnapshot.getChildren()) {
                    Task task = taskSnapshot.getValue(Task.class);
                    task.setKey(taskSnapshot.getKey());
                    taskList.add(task);
                }
                taskAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        };

        tasksRef.addValueEventListener(tasksValueEventListener);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (tasksValueEventListener != null) {
            tasksRef.removeEventListener(tasksValueEventListener);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.addTaskButton) {
            // Créer un nouveau conteneur de fragment
            FrameLayout fragmentContainer = new FrameLayout(this);
            fragmentContainer.setId(View.generateViewId());

            // Ajouter le conteneur de fragment à la mise en page de l'activité
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            setContentView(fragmentContainer, layoutParams);

            // Ajouter le fragment au conteneur de fragment
            AddTaskFragment addTaskFragment = new AddTaskFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(fragmentContainer.getId(), addTaskFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }*/
