package com.example.cloudapp.model;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.example.cloudapp.R;

import java.io.File;


public class AWSS3Connection {


    private static AWSS3Connection connection;
    private AmazonS3Client s3;
    private Context context;
    private CognitoCachingCredentialsProvider credentialsProvider;


    private AWSS3Connection(Context context) {

        this.context = context;
        credentialsProvider = new CognitoCachingCredentialsProvider(
                this.context,
                this.context.getString(R.string.pool), // Identity pool ID
                Regions.US_EAST_1 // Region
        );

        s3 = new AmazonS3Client(credentialsProvider);


    }

    public static AWSS3Connection getAWSS3Connection(Context context) {
        if (connection == null) {
            connection = new AWSS3Connection(context);
        }
        return connection;
    }

    public AmazonS3Client getClient() {
        return s3;
    }

    public void upload(String name, File file, AsyncCallback<String> callback) {

        TransferUtility transferUtility = new TransferUtility(s3, this.context);

        Log.i("INFO", "CREDENTIALS: " + credentialsProvider.getCredentials());


        Log.i("INFO", "showPhotos: Before");


        String bucket = this.context.getString(R.string.bucket);

        Log.i("INFO", "bucket: " + bucket);

        final TransferObserver observer = transferUtility.upload(
                bucket,
                name,
                file,
                CannedAccessControlList.PublicRead
        );

        Log.i("INFO", "showPhotos: After");

        observer.setTransferListener(new TransferListener() {
            @Override
            public void onStateChanged(int id, TransferState state) {
                if (state.equals(TransferState.COMPLETED)) {
                    Log.i("INFO", "onStateChanged: COMPLETE");
                    callback.callback("200");
                } else if (state.equals(TransferState.FAILED)) {
                    Log.i("INFO", "onStateChanged: FAILED");
                } else {
                    Log.i("INFO", "onStateChanged: state: " + state);
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                Log.i("INFO", "onProgressChanged: id-" + id + " bytes-" + bytesCurrent + " bytesTotal: " + bytesTotal);
            }

            @Override
            public void onError(int id, Exception ex) {
                Log.i("ERROR", "onError: " + ex.getMessage());
                callback.error(ex);
            }
        });

    }

    public void download(String name, AsyncCallback<File> callback){
        TransferUtility transferUtility = new TransferUtility(s3, this.context);

        Log.i("INFO", "CREDENTIALS: " + credentialsProvider.getCredentials());

        Log.i("INFO", "showPhotos: Before");


        String bucket = this.context.getString(R.string.bucket);

        Log.i("INFO", "bucket: " + bucket);

        File downloaded = new File("cloudxApp/jsons/"+name);


        final TransferObserver observer =  transferUtility.download(bucket, name, downloaded);

        Log.i("INFO", "showPhotos: After");

        observer.setTransferListener(new TransferListener() {
            @Override
            public void onStateChanged(int id, TransferState state) {
                if (state.equals(TransferState.COMPLETED)) {
                    Log.i("INFO", "onStateChanged: COMPLETE");
                    callback.callback(downloaded);
                } else if (state.equals(TransferState.FAILED)) {
                    Log.i("INFO", "onStateChanged: FAILED");
                    callback.callback(null);
                } else {
                    Log.i("INFO", "onStateChanged: state: " + state);
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                Log.i("INFO", "onProgressChanged: id-" + id + " bytes-" + bytesCurrent + " bytesTotal: " + bytesTotal);
            }

            @Override
            public void onError(int id, Exception ex) {
                Log.i("ERROR", "onError: " + ex.getMessage());
                callback.error(ex);
            }
        });
    }


}
