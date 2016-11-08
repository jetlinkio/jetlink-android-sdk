package com.veslabs.jetlinklibrary.messaging.async;

import android.util.Log;

import com.veslabs.jetlinklibrary.messaging.AsyncResponse;
import com.veslabs.jetlinklibrary.network.JetLinkInternalUser;
import com.veslabs.jetlinklibrary.network.ServiceResult;

/**
 * Created by Burhan Aras on 10/25/2016.
 */
public class RegisterUserToJetlinkServerAsyncTask extends JetlinkBaseAsyncTask {
    private static final String TAG = RegisterUserToJetlinkServerAsyncTask.class.getSimpleName();
    private JetLinkInternalUser user;
    private AsyncResponse asyncResponse;
    private ServiceResult serviceResult;

    public RegisterUserToJetlinkServerAsyncTask(JetLinkInternalUser user, AsyncResponse asyncResponse) {
        this.user = user;
        this.asyncResponse = asyncResponse;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        if (user != null) {
            Log.d(TAG, "doInBackground: "+user.toString());
            try{
                serviceResult = webService.createChatUser(user);
            }catch (Exception e){
                Log.e(TAG,e.getMessage());
                e.printStackTrace();
            }

        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (serviceResult == null) {
            asyncResponse.onFailure("Null returned from network.");
        } else if (serviceResult.getCode().equals("0")) {
            Log.d(TAG, "createChatUser succesfull.");
            asyncResponse.onSuccess(serviceResult.getData());
        } else {
            Log.e(TAG, serviceResult.toString());
            asyncResponse.onFailure(serviceResult.toString());
        }
    }
}
