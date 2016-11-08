package com.veslabs.jetlinklibrary.faq;

import android.util.Log;

import com.veslabs.jetlinklibrary.messaging.AsyncResponse;
import com.veslabs.jetlinklibrary.messaging.async.JetlinkBaseAsyncTask;
import com.veslabs.jetlinklibrary.network.FAQCategory;

import java.util.List;

/**
 * Created by Burhan Aras on 11/7/2016.
 */
public class RetrieveFaqCategoriesAsyncTask extends JetlinkBaseAsyncTask {
    private static final String TAG = RetrieveFaqCategoriesAsyncTask.class.getSimpleName();
    private final String companyId;
    private final AsyncResponse asyncResponse;
    private List<FAQCategory> faqCategories;

    public RetrieveFaqCategoriesAsyncTask(String companyId, AsyncResponse asyncResponse) {
        this.companyId = companyId;
        this.asyncResponse = asyncResponse;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        if (companyId != null) {
            try {
                faqCategories = webService.getFAQCategories(companyId);
            } catch (Exception e) {
                Log.e(TAG, "doInBackground: ", e);
            }
        }

        return super.doInBackground(voids);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (faqCategories != null) {
            asyncResponse.onSuccess(faqCategories);
        } else {
            asyncResponse.onFailure("ERROR");
        }
    }
}
