package com.veslabs.jetlinklibrary.messaging.async;

import android.os.AsyncTask;
import android.util.Base64;

import com.veslabs.jetlinklibrary.network.IApiMethods;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

import static com.veslabs.jetlinklibrary.messaging.JetlinkConstants.FIZMON_MOBILE_API_URL;
import static com.veslabs.jetlinklibrary.messaging.JetlinkConstants.PASSWORD;
import static com.veslabs.jetlinklibrary.messaging.JetlinkConstants.USERNAME;

/**
 * Created by Burhan Aras on 10/25/2016.
 */
public class JetlinkBaseAsyncTask extends AsyncTask<Void, Void, Void> {
    private static final String TAG = JetlinkBaseAsyncTask.class.getSimpleName();

    protected RestAdapter restAdapter;
    protected IApiMethods webService;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // set endpoint url and use OkHTTP as HTTP client
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(FIZMON_MOBILE_API_URL);

        String username = USERNAME;
        String password = PASSWORD;
        if (username != null && password != null) {
            // concatenate username and password with colon for authentication
            final String credentials = username + ":" + password;

            builder.setRequestInterceptor(new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {
                    // create Base64 encodet string
                    String string = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                    request.addHeader("Accept", "application/json");
                    request.addHeader("Authorization", string);
                }
            });
        }

        restAdapter = builder.build();
        webService = restAdapter.create(IApiMethods.class);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        return null;
    }

}
