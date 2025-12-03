package com.example.samira.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samira.AddMedicineActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.samira.R;
import com.example.samira.model.MedicineLibrary;

public class MedAdapter extends FirebaseRecyclerAdapter<MedicineLibrary, MedAdapter.MedViewHolder> {
    String TAG ="";
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MedAdapter(@NonNull  FirebaseRecyclerOptions<MedicineLibrary> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull  MedAdapter.MedViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull MedicineLibrary model) {
        holder.medicName.setText(model.getName());
        holder.medicDesc.setText(model.getDescription());
        holder.medicDose.setText(model.getDose());
        holder.editMedicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddMedicineActivity.class);
                intent.putExtra("medicineID", getRef(position).getKey()); // Passer l'identifiant de la médication
                v.getContext().startActivity(intent);
            }
        });

        holder.deleteMedicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = getRef(position).getKey();
                Log.d(TAG, "Delete Position: "+key);

                DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("Medicine");
                dbref.child(key).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(v.getContext(), "Item Deleted", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

    }

    @NonNull

    @Override
    public MedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listmedicineitem, parent, false);
        return new MedAdapter.MedViewHolder(view);
    }

    public class MedViewHolder extends RecyclerView.ViewHolder {

        TextView medicName, medicDesc, medicDose;
        ImageView  editMedicBtn, deleteMedicBtn;
        public MedViewHolder(@NonNull View itemView) {
            super(itemView);

            medicName      = itemView.findViewById(R.id.textNameMed);
            medicDesc      = itemView.findViewById(R.id.textDescriptionMed);
            medicDose      = itemView.findViewById(R.id.textDoseMed);
            editMedicBtn   = itemView.findViewById(R.id.editBtnItemMed);
            deleteMedicBtn = itemView.findViewById(R.id.deleteBtnItemMed);
        }
    }
}