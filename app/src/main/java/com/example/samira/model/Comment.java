package com.example.samira.model;

public class Comment {
    private String comment;
    private String publisher;
    private String commentid;
    private String date;

    public Comment(String comment, String publisher, String commentid, String date) {
        this.comment = comment;
        this.publisher = publisher;
        this.commentid = commentid;
        this.date = date;
    }

    public Comment() {
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getCommentid() {
        return commentid;
    }

    public void setCommentid(String commentid) {
        this.commentid = commentid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
