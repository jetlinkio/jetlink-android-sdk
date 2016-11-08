package com.veslabs.jetlinklibrary.messaging.async;

import android.util.Log;

import com.veslabs.jetlinklibrary.messaging.AsyncResponse;
import com.veslabs.jetlinklibrary.network.ServiceResult;

/**
 * Created by Burhan Aras on 10/25/2016.
 */
public class RetrieveCompanyIdForThisAppAsyncTask extends JetlinkBaseAsyncTask {
    private static final String TAG = RetrieveCompanyIdForThisAppAsyncTask.class.getSimpleName();
    private final String appId;
    private final String appToken;
    private final String packageName;
    private AsyncResponse asyncResponse;
    private ServiceResult serviceResult;

    public RetrieveCompanyIdForThisAppAsyncTask(String appId, String appToken, String packageName, AsyncResponse asyncResponse) {

        this.appId = appId;
        this.appToken = appToken;
        this.packageName = packageName;
        this.asyncResponse = asyncResponse;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Log.d(TAG, "RetrieveCompanyIdForThisAppAsyncTask() called with: appId = [" + appId + "], appToken = [" + appToken + "], packageName = [" + packageName + "]");
        if (appId != null && !appId.equals("") && appToken != null && !appToken.equals("") && packageName != null && !packageName.equals("")) {
            serviceResult = webService.getCompanyByAppInfo(appId, appToken, packageName);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (serviceResult == null) {
            Log.e(TAG, "Null returned from network.");
            asyncResponse.onFailure("Null returned from network.");
        } else {
            Log.d(TAG, serviceResult.toString());
            if (serviceResult.getCode().equals(ServiceResult.BASARILI)) {
                asyncResponse.onSuccess(serviceResult.getMessage());
            } else {
                asyncResponse.onFailure(serviceResult.toString());
            }
        }
    }
}
