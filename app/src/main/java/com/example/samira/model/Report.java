package com.example.samira.model;

// Report.java
// Report.java
public class Report {
    private String reportId;
    private String postId;
    private String userId;
    private String postTitle; // Example field to store post title
    private String reporterEmail; // Example field to store reporter's email
    private long timestamp;

    public Report() {
        // Default constructor required for calls to DataSnapshot.getValue(Report.class)
    }

    public Report(String reportId, String postId, String userId, String postTitle, String reporterEmail, long timestamp) {
        this.reportId = reportId;
        this.postId = postId;
        this.userId = userId;
        this.postTitle = postTitle;
        this.reporterEmail = reporterEmail;
        this.timestamp = timestamp;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getReporterEmail() {
        return reporterEmail;
    }

    public void setReporterEmail(String reporterEmail) {
        this.reporterEmail = reporterEmail;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    // Getters and setters
    // ...
}
