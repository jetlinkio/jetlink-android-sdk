package com.veslabs.jetlinklibrary.messaging.async;

import android.util.Log;

import com.veslabs.jetlinklibrary.messaging.AsyncResponse;
import com.veslabs.jetlinklibrary.network.JetLinkInternalUser;

/**
 * Created by Burhan Aras on 10/25/2016.
 */
public class RetrieveJetlinkUserInfoAsyncTask extends JetlinkBaseAsyncTask {
    private static final String TAG = RetrieveJetlinkUserInfoAsyncTask.class.getSimpleName();
    private JetLinkInternalUser user;
    private final String email;
    private final String phone;
    private AsyncResponse asyncResponse;

    public RetrieveJetlinkUserInfoAsyncTask(String email, String phone, AsyncResponse asyncResponse) {
        this.email = email;
        this.phone = phone;
        this.asyncResponse = asyncResponse;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Log.d(TAG, "email:"+email+" phone:"+phone);
        if (email != null || !email.equals("")) {
            user = webService.getChatUserByEmail(email);
        } else if (phone != null || !phone.equals("")) {
            user = webService.getChatUserByPhone(phone);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (user == null) {
            Log.e(TAG, "Null returned from network.");
            asyncResponse.onFailure("Null returned from network.");
        } else {
            Log.d(TAG, user.toString());
            asyncResponse.onSuccess(user);
        }
    }
}
