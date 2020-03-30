package com.example.cloudapp.model;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.example.cloudapp.R;


public class AWSS3Connection {


    private static AWSS3Connection connection;
    private AmazonS3Client client;
    private BasicAWSCredentials credentials;

    private AWSS3Connection(){

    }

    public static getAWSS3Connection(){
        if(connection==null){
            connection=new AWSS3Connection();
        }
        return connection;
    }




}
