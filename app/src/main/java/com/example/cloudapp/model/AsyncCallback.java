package com.example.cloudapp.model;

public interface AsyncCallback<T> {

    public void callback(T data);
    public void error(Exception e);


}
