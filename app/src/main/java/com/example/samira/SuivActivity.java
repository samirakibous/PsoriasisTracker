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

public class SuivActivity extends AppCompatActivity {

    private SeekBar seekBar, seekBar2, seekBarSurface, seekBarItches;
    private TextView tvSymptomLevel, tvSymptomLevel1, tvSymptomSurface, tvSymptomItches;
    private BarChart barChart, barChart2, barChartSurface, barChartItches;
    private int symptomLevel = 0;
    private int symptomLevel2 = 0;
    private int symptomLevelSurface = 0;
    private int symptomLevelItches = 0;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference dbRef, dbRef2, dbRefSurface, dbRefItches;
    private List<BarEntry> barEntries = new ArrayList<>();
    private List<String> xLabels = new ArrayList<>();
    private List<BarEntry> barEntries2 = new ArrayList<>();
    private List<String> xLabels2 = new ArrayList<>();
    private List<BarEntry> barEntriesSurface = new ArrayList<>();
    private List<String> xLabelsSurface = new ArrayList<>();
    private List<BarEntry> barEntriesItches = new ArrayList<>();
    private List<String> xLabelsItches = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suiv);
        View backBtn = findViewById(R.id.back_btn3);
        backBtn.setOnClickListener((v) -> onBackPressed());
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        dbRef = FirebaseDatabase.getInstance().getReference("SymptomEntries").child(user.getUid());
            dbRef2 = FirebaseDatabase.getInstance().getReference("SymptomEntries2").child(user.getUid());
        dbRefSurface = FirebaseDatabase.getInstance().getReference("SymptomEntriesSurface").child(user.getUid());
        dbRefItches = FirebaseDatabase.getInstance().getReference("SymptomEntriesItches").child(user.getUid());

        seekBar = findViewById(R.id.seekBar);
        tvSymptomLevel = findViewById(R.id.tv_symptom_level);
        barChart = findViewById(R.id.barChart);
        Button btnSubmit = findViewById(R.id.btn_submit);

        seekBar2 = findViewById(R.id.seekBar2);
        tvSymptomLevel1 = findViewById(R.id.tv_symptom_level1);
        barChart2 = findViewById(R.id.barChart2);
        Button btnSubmit2 = findViewById(R.id.btn_submit2);

        seekBarSurface = findViewById(R.id.seekBarSurface);
        tvSymptomSurface = findViewById(R.id.tv_symptom_surface);
        barChartSurface = findViewById(R.id.barChartSurface);
        Button btnSubmitSurface = findViewById(R.id.btn_surface);

        seekBarItches = findViewById(R.id.seekBarItches);
        tvSymptomItches = findViewById(R.id.tv_symptom_itches);
        barChartItches = findViewById(R.id.barChartItches);
        Button btnSubmitItches = findViewById(R.id.btn_itches);

        setupSeekBar(seekBar, tvSymptomLevel);
        setupSeekBar(seekBar2, tvSymptomLevel1);
        setupSeekBar(seekBarSurface, tvSymptomSurface);
        setupSeekBar(seekBarItches, tvSymptomItches);

        btnSubmit.setOnClickListener(v -> submitSymptomEntry("Symptom", symptomLevel, dbRef, this::loadData));
        btnSubmit2.setOnClickListener(v -> submitSymptomEntry("Symptom2", symptomLevel2, dbRef2, this::loadData2));
        btnSubmitSurface.setOnClickListener(v -> submitSymptomEntry("Surface", symptomLevelSurface, dbRefSurface, this::loadDataSurface));
        btnSubmitItches.setOnClickListener(v -> submitSymptomEntry("Itches", symptomLevelItches, dbRefItches, this::loadDataItches));

        loadData();
        loadData2();
        loadDataSurface();
        loadDataItches();
    }

    private void setupSeekBar(SeekBar seekBar, TextView textView) {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (seekBar == SuivActivity.this.seekBar) {
                    symptomLevel = progress;
                } else if (seekBar == SuivActivity.this.seekBar2) {
                    symptomLevel2 = progress;
                } else if (seekBar == SuivActivity.this.seekBarSurface) {
                    symptomLevelSurface = progress;
                } else if (seekBar == SuivActivity.this.seekBarItches) {
                    symptomLevelItches = progress;
                }
                String severityLevel = getSeverityLevel(progress);
                textView.setText(" Niveau: " + progress + " (" + severityLevel + ")");
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
                        Toast.makeText(SuivActivity.this, "Entrée de symptôme enregistrée", Toast.LENGTH_SHORT).show();
                        loadData.run(); // Recharger les données pour rafraîchir le graphique
                    } else {
                        Toast.makeText(SuivActivity.this, "Échec de l'enregistrement de l'entrée de symptôme", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadData() {
        loadData(dbRef, barEntries, xLabels, this::setupBarChart);
    }

    private void loadData2() {
        loadData(dbRef2, barEntries2, xLabels2, this::setupBarChart2);
    }

    private void loadDataSurface() {
        loadData(dbRefSurface, barEntriesSurface, xLabelsSurface, this::setupBarChartSurface);
    }

    private void loadDataItches() {
        loadData(dbRefItches, barEntriesItches, xLabelsItches, this::setupBarChartItches);
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
                Log.e("SuivActivity", "Échec du chargement des données", databaseError.toException());
            }
        });
    }

    private void setupBarChart() {
        setupBarChart(barChart, barEntries, xLabels);
    }

    private void setupBarChart2() {
        setupBarChart(barChart2, barEntries2, xLabels2);
    }

    private void setupBarChartSurface() {
        setupBarChart(barChartSurface, barEntriesSurface, xLabelsSurface);
    }

    private void setupBarChartItches() {
        setupBarChart(barChartItches, barEntriesItches, xLabelsItches);
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
