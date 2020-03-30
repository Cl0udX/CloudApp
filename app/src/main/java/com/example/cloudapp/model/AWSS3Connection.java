package com.example.cloudapp.model;

import android.content.Context;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.example.cloudapp.R;


public class AWSS3Connection {


    private static AWSS3Connection connection;
    private AmazonS3Client client;
    private BasicAWSCredentials credentials;


    private AWSS3Connection(String key, String secret, String endpoint){

        credentials = new BasicAWSCredentials(key, secret);
        client = new AmazonS3Client(credentials);
        client.setRegion(Region.getRegion(Regions.AP_NORTHEAST_1));


    }

    public static AWSS3Connection getAWSS3Connection(String key, String secret, String endpoint){
        if(connection==null){
            connection=new AWSS3Connection(key,secret,endpoint);
        }
        return connection;
    }

    public AmazonS3Client getClient(){
        return client;
    }


}
