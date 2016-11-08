package com.veslabs.jetlinklibrary.faq;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.veslabs.jetlinklibrary.JetLinkApp;
import com.veslabs.jetlinklibrary.R;

import com.veslabs.jetlinklibrary.faq.dummy.DummyContent;
import com.veslabs.jetlinklibrary.messaging.AsyncResponse;
import com.veslabs.jetlinklibrary.network.FAQ;
import com.veslabs.jetlinklibrary.network.FAQCategory;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.app.NavUtils.navigateUpFromSameTask;

/**
 * An activity representing a list of Faqs. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link FaqDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class FaqListActivity extends AppCompatActivity {

    private static final String KEY_FAQ_CATEGORY = "KEY_FAQ_CATEGORY";
    private static final String TAG = FaqListActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private List<FAQ> faqs=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getString(R.string.faq));

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        recyclerView = (RecyclerView) findViewById(R.id.faq_list);
        assert recyclerView != null;
        setupRecyclerView(recyclerView);

        if (findViewById(R.id.faq_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        FAQCategory faqCategory = (FAQCategory) getIntent().getSerializableExtra(KEY_FAQ_CATEGORY);
        if (faqCategory != null && !faqCategory.getCategory().equals("")) {
            String companyId = JetLinkApp.getInstance(FaqListActivity.this).getCompanyId();
            new RetrieveFaqsAsyncTask(companyId, faqCategory.getFAQCategoryId(), new AsyncResponse() {
                @Override
                public void onSuccess(Object response) {
                    faqs = (List<FAQ>) response;
                    Log.d(TAG, faqs!= null ? faqs.size()+" faqs downloaded. ":"Faqs is NULL for this category");
                    setupRecyclerView(recyclerView);
                }

                @Override
                public void onFailure(String message) {
                    Log.e(TAG, message);
                }
            }).execute();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static Intent newIntent(Context context, FAQCategory faqCategory) {
        Intent intent = new Intent(context, FaqListActivity.class);
        intent.putExtra(KEY_FAQ_CATEGORY, faqCategory);
        return intent;
    }


    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(faqs));
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private  List<FAQ> mValues=new ArrayList<>();

        public SimpleItemRecyclerViewAdapter(List<FAQ> items) {
            if (items != null) {
                mValues = items;
            }

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.faq_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.mContentView.setText(mValues.get(position).getQuestion());
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putSerializable(FaqDetailFragment.ARG_FAQ,  mValues.get(position));
                        FaqDetailFragment fragment = new FaqDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.faq_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, FaqDetailActivity.class);
                        intent.putExtra(FaqDetailFragment.ARG_FAQ, mValues.get(position));

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mContentView;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
}
