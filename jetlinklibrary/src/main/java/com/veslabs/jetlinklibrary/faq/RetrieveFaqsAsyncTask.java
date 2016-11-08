package com.veslabs.jetlinklibrary.faq;

import android.util.Log;

import com.veslabs.jetlinklibrary.messaging.AsyncResponse;
import com.veslabs.jetlinklibrary.messaging.async.JetlinkBaseAsyncTask;
import com.veslabs.jetlinklibrary.network.FAQ;
import com.veslabs.jetlinklibrary.network.FAQCategory;

import java.util.List;

/**
 * Created by Burhan Aras on 11/7/2016.
 */
public class RetrieveFaqsAsyncTask extends JetlinkBaseAsyncTask {
    private static final String TAG = RetrieveFaqsAsyncTask.class.getSimpleName();
    private final String companyId;
    private String categoryId;
    private final AsyncResponse asyncResponse;
    private List<FAQ> faqs;

    public RetrieveFaqsAsyncTask(String companyId,String categoryId, AsyncResponse asyncResponse) {
        this.companyId = companyId;
        this.categoryId = categoryId;
        this.asyncResponse = asyncResponse;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        if (companyId != null) {
            try {
                faqs = webService.getFAQListByCategory(categoryId, companyId);
            } catch (Exception e) {
                Log.e(TAG, "doInBackground: ", e);
            }
        }

        return super.doInBackground(voids);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (faqs != null) {
            asyncResponse.onSuccess(faqs);
        } else {
            asyncResponse.onFailure("ERROR");
        }
    }
}
