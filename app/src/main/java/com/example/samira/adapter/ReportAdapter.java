package com.example.samira.adapter;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samira.R;
import com.example.samira.model.Report;

import java.util.List;

// ReportAdapter.java
public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {

    private Context mContext;
    private List<Report> mReports;

    public ReportAdapter(Context context, List<Report> reports) {
        mContext = context;
        mReports = reports;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_report, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Report report = mReports.get(position);
        holder.postTitle.setText(report.getPostTitle());
        holder.reporterEmail.setText(report.getReporterEmail());
        holder.timestamp.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", report.getTimestamp()));
    }

    @Override
    public int getItemCount() {
        return mReports.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView postTitle, reporterEmail, timestamp;

        public ViewHolder(View itemView) {
            super(itemView);
            postTitle = itemView.findViewById(R.id.text_post_title);
            reporterEmail = itemView.findViewById(R.id.text_reporter_email);
            timestamp = itemView.findViewById(R.id.text_report_timestamp);
        }
    }
}
