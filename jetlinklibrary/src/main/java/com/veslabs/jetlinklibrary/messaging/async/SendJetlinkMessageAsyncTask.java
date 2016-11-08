package com.veslabs.jetlinklibrary.messaging.async;

import android.util.Log;

import com.veslabs.jetlinklibrary.messaging.AsyncResponse;
import com.veslabs.jetlinklibrary.network.ServiceResult;

/**ch
 * Created by Burhan Aras on 10/25/2016.
 */
public class SendJetlinkMessageAsyncTask extends JetlinkBaseAsyncTask {
    private static final String TAG = SendJetlinkMessageAsyncTask.class.getSimpleName();
    private final String fromUserId;
    private final String toCompanyId;
    private final String messageContent;
    private AsyncResponse asyncResponse;
    private ServiceResult serviceResult;

    public SendJetlinkMessageAsyncTask(String fromUserId, String toCompanyId, String messageContent, AsyncResponse asyncResponse) {
        this.fromUserId = fromUserId;
        this.toCompanyId = toCompanyId;
        this.messageContent = messageContent;
        this.asyncResponse = asyncResponse;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Log.d(TAG, "fromUserId:" + fromUserId + " toCompanyId:" + toCompanyId + " messageContent:" + messageContent);
        try {
            if (fromUserId != null || !fromUserId.equals("") && toCompanyId != null || !toCompanyId.equals("") && messageContent != null || !messageContent.equals("")) {
                serviceResult = webService.sendMessage(fromUserId, toCompanyId, messageContent, "false");
            }
        } catch (Exception e) {
            Log.e(TAG, "doInBackground: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (serviceResult == null) {
            Log.e(TAG, "serviceResult is null");
            asyncResponse.onFailure("serviceResult is null");
        } else {
            Log.d(TAG, serviceResult.toString());
            asyncResponse.onSuccess(serviceResult);
        }
    }
}
