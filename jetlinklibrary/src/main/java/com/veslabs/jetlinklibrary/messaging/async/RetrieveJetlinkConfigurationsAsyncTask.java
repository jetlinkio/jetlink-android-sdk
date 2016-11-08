package com.veslabs.jetlinklibrary.messaging.async;

import android.util.Log;

import com.veslabs.jetlinklibrary.messaging.AsyncResponse;
import com.veslabs.jetlinklibrary.network.JetLinkConfiguration;

import java.util.List;

/**
 * Created by Burhan Aras on 10/25/2016.
 */
public class RetrieveJetlinkConfigurationsAsyncTask extends JetlinkBaseAsyncTask {
    private static final String TAG = RetrieveJetlinkConfigurationsAsyncTask.class.getSimpleName();
    private List<JetLinkConfiguration> systemConfigurations;
    private AsyncResponse asyncResponse;

    public RetrieveJetlinkConfigurationsAsyncTask(AsyncResponse asyncResponse) {
        this.asyncResponse = asyncResponse;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            systemConfigurations = webService.getSystemConfigurations();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (systemConfigurations == null) {
            Log.e(TAG, "systemConfigurations is null");
            asyncResponse.onFailure("systemConfigurations is null");
        } else {
            Log.d(TAG, systemConfigurations.toString());
            asyncResponse.onSuccess(systemConfigurations);
        }
    }
}
