package com.veslabs.jetlinklibrary.faq;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.veslabs.jetlinklibrary.JetLinkApp;
import com.veslabs.jetlinklibrary.R;
import com.veslabs.jetlinklibrary.messaging.AsyncResponse;
import com.veslabs.jetlinklibrary.network.FAQCategory;

import java.util.ArrayList;
import java.util.List;

public class FaqActivity extends AppCompatActivity {
    private static final String TAG = FaqActivity.class.getSimpleName();
    private List<FAQCategory> faqCategories;
    private RecyclerView rvFaqCategires;
    private FaqCategoriesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        rvFaqCategires = (RecyclerView) findViewById(R.id.rv_faq_categories);
        rvFaqCategires.setLayoutManager(new LinearLayoutManager(FaqActivity.this));

        String companyId = JetLinkApp.getInstance(FaqActivity.this).getCompanyId();
        new RetrieveFaqCategoriesAsyncTask(companyId, new AsyncResponse() {
            @Override
            public void onSuccess(Object response) {
                faqCategories = (List<FAQCategory>) response;
                if (faqCategories != null) {
                    Log.d(TAG, faqCategories.size() + " categories downloaded from network.");
                    adapter = new FaqCategoriesAdapter(FaqActivity.this,faqCategories);
                    rvFaqCategires.setAdapter(adapter);
                } else {
                    Log.d(TAG, "faqCategories is null");
                }
            }

            @Override
            public void onFailure(String message) {
                Log.e(TAG, message);
            }
        }).execute();
    }
}
