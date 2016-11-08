package com.veslabs.jetlinklibrary.profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.veslabs.jetlinklibrary.JetLinkApp;
import com.veslabs.jetlinklibrary.R;
import com.veslabs.jetlinklibrary.messaging.AsyncResponse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = ProfileActivity.class.getSimpleName();
    private static final int REQUEST_CODE_PICK_FROM_CAMERA = 100;
    private static final int REQUEST_CODE_PICK_FROM_FILE = 101;
    private ImageView ivEditProfilePicture;
    private ImageView ivProfilePicture;
    private File imageFile;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ivProfilePicture = (ImageView) findViewById(R.id.iv_profile);
        ivEditProfilePicture = (ImageView) findViewById(R.id.iv_edit_profile_picture);
        ivEditProfilePicture.setOnClickListener(new EditProfileImageOnClickListener());


    }

    private class EditProfileImageOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (isConnectedToInternet()) {
                chooseFromGallery();
            }

        }
    }

//    public static Uri resourceToUri(Context context, int resID) {
//        return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
//                context.getResources().getResourcePackageName(resID) + '/' +
//                context.getResources().getResourceTypeName(resID) + '/' +
//                context.getResources().getResourceEntryName(resID));
//    }
//
//    public Uri bitmapToUri(Context inContext, Bitmap inImage) {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
//        return Uri.parse(path);
//    }

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String imageFileName = "jetsu_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        imageFile = new File(Environment.getExternalStorageDirectory(), imageFileName);
        Log.d(TAG, "imageFile is " + imageFile.getAbsolutePath());
        imageUri = Uri.fromFile(imageFile);
        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, REQUEST_CODE_PICK_FROM_CAMERA);
    }

    private void chooseFromGallery() {
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_PICK_FROM_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;

        switch (requestCode) {
            case REQUEST_CODE_PICK_FROM_FILE:
                imageUri = data.getData();
                Bitmap bitmap;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    ivProfilePicture.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                upload(imageUri);

                break;

//            case REQUEST_CODE_PICK_FROM_CAMERA:
//                Bitmap bitmap;
//                try {
//                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
////                    bitmap = crupAndScale(bitmap, 300); // if you mind scaling
//                    ivProfilePicture.setImageBitmap(bitmap);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                upload(imageUri);
//                break;
        }
    }

    private void upload(Uri uri) {
        new UpdateUserProfilePhotoAsyncTask(ProfileActivity.this, ((JetLinkApp)getApplication()).getInternalUser().getUserId(), uri,
                new AsyncResponse() {
                    @Override
                    public void onSuccess(Object response) {
                        Log.d(TAG, "onSuccess: DONE.");
                    }

                    @Override
                    public void onFailure(String message) {
                        Log.e(TAG, "onFailure: " + message);
                    }
                }).execute();
    }
//
//    public static  Bitmap crupAndScale (Bitmap source,int scale){
//        int factor = source.getHeight() <= source.getWidth() ? source.getHeight(): source.getWidth();
//        int longer = source.getHeight() >= source.getWidth() ? source.getHeight(): source.getWidth();
//        int x = source.getHeight() >= source.getWidth() ?0:(longer-factor)/2;
//        int y = source.getHeight() <= source.getWidth() ?0:(longer-factor)/2;
//        source = Bitmap.createBitmap(source, x, y, factor, factor);
//        source = Bitmap.createScaledBitmap(source, scale, scale, false);
//        return source;
//    }

    public boolean isConnectedToInternet()
    {
        String command = "ping -c 1 google.com";
        try {
            return (Runtime.getRuntime().exec (command).waitFor() == 0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
