package com.example.samira.model;

import com.google.firebase.database.ServerValue;

import java.util.Map;

import java.util.HashMap;
public class Post {


    private String postid;
    private String postimage;
    private String description;
    private String publisher;
    public Post(String postid, String postimage, String description, String publisher ) {
        this.postid = postid;
        this.postimage = postimage;
        this.description = description;
        this.publisher = publisher;// Initialiser la map de likes

    }

    // make sure to have an empty constructor inside ur model class
    public Post() {
    }


    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public void setPostimage(String postimage) {
        this.postimage = postimage;
    }
    public String getPostimage() {
        return postimage;
    }

    public String getDescription() {
        return description;
    }

    public String getPublisher() {
        return publisher;
    }



    public void setDescription(String description) {
        this.description = description;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

}