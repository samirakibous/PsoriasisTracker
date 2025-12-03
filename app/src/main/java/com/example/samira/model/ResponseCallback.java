package com.example.samira.model;

public interface ResponseCallback {
    void onResponse(String response);
    void  onError(Throwable t);
}
