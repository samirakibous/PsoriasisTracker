package com.example.samira;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MoodActivity extends AppCompatActivity {

    private SeekBar seekBarStresse, seekBarMalheureu, seekBarHeureu, seekBarEnergetic;
    private TextView tvSymptomStresse, tvSymptomMalheureux, tvSymptomHeureux, tvSymptomEnergitic;
    private BarChart barChartStresse, barChartMalheureux, barChartHeureu, barChartEnergetic;
    private int symptomLevelStresse = 0;
    private int symptomLevelMalheureu = 0;
    private int symptomLevelHeureu = 0;
    private int symptomLevelEnergetic = 0;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference dbRefStresse, dbRefMalheureux, dbRefHeureu, dbRefEnergetic;
    private List<BarEntry> barEntriesStresse = new ArrayList<>();
    private List<String> xLabelsStresse = new ArrayList<>();
    private List<BarEntry> barEntriesMalheureux = new ArrayList<>();
    private List<String> xLabelsMalheureux = new ArrayList<>();
    private List<BarEntry> barEntriesHeureu = new ArrayList<>();
    private List<String> xLabelsHeureu = new ArrayList<>();
    private List<BarEntry> barEntriesEnergetic = new ArrayList<>();
    private List<String> xLabelsEnergetic = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood);
        View backBtn = findViewById(R.id.back_btn3);
        backBtn.setOnClickListener((v)-> onBackPressed() );
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        dbRefStresse = FirebaseDatabase.getInstance().getReference("SymptomEntriesStresse").child(user.getUid());
        dbRefMalheureux = FirebaseDatabase.getInstance().getReference("SymptomEntriesMalheureux").child(user.getUid());
        dbRefHeureu = FirebaseDatabase.getInstance().getReference("SymptomEntriesHeureu").child(user.getUid());
        dbRefEnergetic = FirebaseDatabase.getInstance().getReference("SymptomEntriesEnergetic").child(user.getUid());

        seekBarStresse = findViewById(R.id.seekBarStresse);
        tvSymptomStresse = findViewById(R.id.tv_symptom);
        barChartStresse = findViewById(R.id.barChartStresse);
        Button btnStresse = findViewById(R.id.btn_stresse);

        seekBarMalheureu = findViewById(R.id.seekBarMalheureu);
        tvSymptomMalheureux = findViewById(R.id.tv_symptom_malheureux);
        barChartMalheureux = findViewById(R.id.barChartMalheureux);
        Button btnMalheureux = findViewById(R.id.btn_Malheureux);

        seekBarHeureu = findViewById(R.id.seekBarHeureu);
        tvSymptomHeureux = findViewById(R.id.tv_symptom_content);
        barChartHeureu = findViewById(R.id.barChartHeureu);
        Button btnHeureux = findViewById(R.id.btn_Heureux);

        seekBarEnergetic = findViewById(R.id.seekBarEnergetic);
        tvSymptomEnergitic = findViewById(R.id.tv_symptom_energitic);
        barChartEnergetic = findViewById(R.id.barChartEnergetic);
        Button btnEnergetic = findViewById(R.id.btn_Energetic);

        setupSeekBar(seekBarStresse, tvSymptomStresse);
        setupSeekBar(seekBarMalheureu, tvSymptomMalheureux);
        setupSeekBar(seekBarHeureu, tvSymptomHeureux);
        setupSeekBar(seekBarEnergetic, tvSymptomEnergitic);

        btnStresse.setOnClickListener(v -> submitSymptomEntry("Stresse", symptomLevelStresse, dbRefStresse, this::loadDataStresse));
        btnMalheureux.setOnClickListener(v -> submitSymptomEntry("Malheureux", symptomLevelMalheureu, dbRefMalheureux, this::loadDataMalheureux));
        btnHeureux.setOnClickListener(v -> submitSymptomEntry("Heureu", symptomLevelHeureu, dbRefHeureu, this::loadDataHeureu));
        btnEnergetic.setOnClickListener(v -> submitSymptomEntry("Energetic", symptomLevelEnergetic, dbRefEnergetic, this::loadDataEnergetic));

        loadDataStresse();
        loadDataMalheureux();
        loadDataHeureu();
        loadDataEnergetic();
    }

    private void setupSeekBar(SeekBar seekBar, TextView textView) {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (seekBar == seekBarStresse) {
                    symptomLevelStresse = progress;
                } else if (seekBar == seekBarMalheureu) {
                    symptomLevelMalheureu = progress;
                } else if (seekBar == seekBarHeureu) {
                    symptomLevelHeureu = progress;
                } else if (seekBar == seekBarEnergetic) {
                    symptomLevelEnergetic = progress;
                }
                String severityLevel = getSeverityLevel(progress);
                textView.setText("Symptom Level: " + progress + " (" + severityLevel + ")");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Do nothing
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Do nothing
            }
        });
    }

    private String getSeverityLevel(int progress) {
        switch (progress) {
            case 0:
                return "Aucun";
            case 1:
                return "Léger";
            case 2:
                return "Modéré-Léger";
            case 3:
                return "Modéré";
            case 4:
                return "Modéré-Sévère";
            case 5:
                return "Sévère";
            default:
                return "Inconnu";
        }
    }

    private void submitSymptomEntry(String type, int level, DatabaseReference dbRef, Runnable loadData) {
        String date = new SimpleDateFormat("dd-MM-yy", Locale.getDefault()).format(new Date());
        Map<String, Object> symptomEntry = new HashMap<>();
        symptomEntry.put("date", date);
        symptomEntry.put("level", level);

        dbRef.child(date).setValue(symptomEntry)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(MoodActivity.this, "Entrée de symptôme enregistrée", Toast.LENGTH_SHORT).show();
                        loadData.run(); // Recharger les données pour rafraîchir le graphique
                    } else {
                        Toast.makeText(MoodActivity.this, "Échec de l'enregistrement de l'entrée de symptôme", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadDataStresse() {
        loadData(dbRefStresse, barEntriesStresse, xLabelsStresse, this::setupBarChartStresse);
    }

    private void loadDataMalheureux() {
        loadData(dbRefMalheureux, barEntriesMalheureux, xLabelsMalheureux, this::setupBarChartMalheureux);
    }

    private void loadDataHeureu() {
        loadData(dbRefHeureu, barEntriesHeureu, xLabelsHeureu, this::setupBarChartHeureu);
    }

    private void loadDataEnergetic() {
        loadData(dbRefEnergetic, barEntriesEnergetic, xLabelsEnergetic, this::setupBarChartEnergetic);
    }

    private void loadData(DatabaseReference dbRef, List<BarEntry> barEntries, List<String> xLabels, Runnable setupBarChart) {
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                barEntries.clear();
                xLabels.clear();
                int index = 0;

                Map<String, Integer> symptomData = new HashMap<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String date = snapshot.child("date").getValue(String.class);
                    Integer level = snapshot.child("level").getValue(Integer.class);
                    if (date != null && level != null) {
                        symptomData.put(date, level);
                    }
                }

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy", Locale.getDefault());
                for (int i = 29; i >= 0; i--) {
                    calendar.add(Calendar.DAY_OF_YEAR, -i);
                    String date = sdf.format(calendar.getTime());
                    xLabels.add(date);

                    Integer level = symptomData.get(date);
                    if (level == null) {
                        level = 0;
                    }
                    barEntries.add(new BarEntry(index, level));
                    index++;
                    calendar.add(Calendar.DAY_OF_YEAR, i);
                }

                setupBarChart.run();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("MoodActivity", "Échec du chargement des données", databaseError.toException());
            }
        });
    }

    private void setupBarChartStresse() {
        setupBarChart(barChartStresse, barEntriesStresse, xLabelsStresse);
    }

    private void setupBarChartMalheureux() {
        setupBarChart(barChartMalheureux, barEntriesMalheureux, xLabelsMalheureux);
    }

    private void setupBarChartHeureu() {
        setupBarChart(barChartHeureu, barEntriesHeureu, xLabelsHeureu);
    }

    private void setupBarChartEnergetic() {
        setupBarChart(barChartEnergetic, barEntriesEnergetic, xLabelsEnergetic);
    }
    private static final String[] SEVERITY_LEVELS = {"Aucun", "Léger", "Modéré-Léger", "Modéré", "Modéré-Sévère", "Sévère"};

    private void setupBarChart(BarChart barChart, List<BarEntry> barEntries, List<String> xLabels) {
        BarDataSet barDataSet = new BarDataSet(barEntries, "Niveaux de symptômes");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);
        barDataSet.setValueFormatter(new IntegerValueFormatter()); // Utilise le ValueFormatter personnalisé pour les valeurs des barres

        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xLabels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);

        YAxis yAxis = barChart.getAxisLeft(); // Récupère l'axe Y gauche
        yAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                int intValue = (int) value;
                if (intValue >= 0 && intValue < SEVERITY_LEVELS.length) {
                    return SEVERITY_LEVELS[intValue];
                }
                return "";
            }
        });

        barChart.getAxisRight().setEnabled(false); // Désactive l'axe Y droit

        barChart.setVisibleXRangeMaximum(7); // Afficher 7 jours à la fois
        barChart.moveViewToX(barEntries.size() - 7); // Commencer la vue aux 7 jours les plus récents

        barChart.getDescription().setEnabled(false);
        barChart.getLegend().setEnabled(false);
        barChart.invalidate(); // Rafraîchir le graphique
    }
}
