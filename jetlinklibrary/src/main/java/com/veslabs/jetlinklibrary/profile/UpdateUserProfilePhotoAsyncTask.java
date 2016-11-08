package com.veslabs.jetlinklibrary.profile;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.veslabs.jetlinklibrary.messaging.AsyncResponse;
import com.veslabs.jetlinklibrary.messaging.async.JetlinkBaseAsyncTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * Created by Burhan Aras on 6/29/2016.
 */
public class UpdateUserProfilePhotoAsyncTask extends JetlinkBaseAsyncTask {
    private static final String TAG = UpdateUserProfilePhotoAsyncTask.class.getSimpleName();
    private Context context;
    private final String userProfileId;
    private final Uri imageUri;
    private AsyncResponse asyncResponse;
    private Boolean success = true;

    public UpdateUserProfilePhotoAsyncTask(Context context, String userProfileId, Uri imageUri, AsyncResponse asyncResponse) {
        this.context = context;
        this.userProfileId = userProfileId;
        this.imageUri = imageUri;
        this.asyncResponse = asyncResponse;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Log.d(TAG, userProfileId);
        try {
            uploadProfilePhoto();
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
            success = false;
        }
        return super.doInBackground(voids);
    }


    public void uploadProfilePhoto() {
        Log.d(TAG, "uploadProfilePhoto()");
        OkHttpClient client = new OkHttpClient();

        byte[] data = new byte[0];
        try {
            data = readBytes(imageUri);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }

        if (data == null) {
            Log.d(TAG, "data is null.");
            return;
        }
        RequestBody requestBody1 = RequestBody.create(MediaType.parse("image/jpeg"), data);
        Log.d(TAG, "requestBody: " + requestBody1.toString());
        RequestBody requestBody2 = new MultipartBuilder()
                .type(MultipartBuilder.FORM)
                .addFormDataPart("photo", "t.jpg", requestBody1)
                .build();

        Request request = new Request.Builder()
                .url("https://mobileapi.jetlink.io/v1/Messaging/UploadUserAvatar?userId=" + userProfileId)
                .post(requestBody2)
                .addHeader("content-type", "multipart/form-data; boundary=---011000010111000001101001")
//                .addHeader("authorization", "Basic ZXhwby1tb2JpbGUtYW5kcm9pZDowMDM5MmY4OTVhNTk5MjViYjhlMDQxNDUwZWVhNDQxMw==")
                .addHeader("authorization", "Basic amV0bGluay1hbmRyb2lkLXNkazo4ODg1MzM5Y2UyMDg0MGRjODZhMmRkZTZkNGU2MTIyNjU=")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "e52b19be-33fb-f5fb-250a-08e66f65de3b")
                .build();


        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
                Log.e(TAG, e.getMessage());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                Log.d(TAG, response.toString());

            }
        });


    }


    public byte[] readBytes(Uri uri) throws IOException {
        Log.d(TAG, "readBytes()" + uri.toString());
        // this dynamically extends to take the bytes you read
        InputStream inputStream = context.getContentResolver().openInputStream(uri);
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

        // this is storage overwritten on each iteration with bytes
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        // we need to know how may bytes were read to write them to the byteBuffer
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }

        Log.d(TAG, "returning bytes: " + byteBuffer.size());
        // and then we can return your byte array.
        return byteBuffer.toByteArray();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (success) {
            asyncResponse.onSuccess("Done.");
        }else{
            asyncResponse.onFailure("Failed.");
        }
    }
}
