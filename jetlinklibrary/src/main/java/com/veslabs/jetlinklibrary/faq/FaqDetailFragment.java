package com.veslabs.jetlinklibrary.faq;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.veslabs.jetlinklibrary.R;
import com.veslabs.jetlinklibrary.faq.dummy.DummyContent;
import com.veslabs.jetlinklibrary.network.FAQ;

/**
 * A fragment representing a single Faq detail screen.
 * This fragment is either contained in a {@link FaqListActivity}
 * in two-pane mode (on tablets) or a {@link FaqDetailActivity}
 * on handsets.
 */
public class FaqDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_FAQ = "faq";


    /**
     * The dummy content this fragment is presenting.
     */
    private FAQ  faq;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FaqDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_FAQ)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            faq = (FAQ) getArguments().getSerializable(ARG_FAQ);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(getString(R.string.faq));
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.faq_details, container, false);

        // Show the dummy content as text in a TextView.
        if (faq != null) {
            ((TextView) rootView.findViewById(R.id.faq_detail)).setText(faq.getQuestion());
            ((TextView) rootView.findViewById(R.id.faq_answer)).setText(faq.getAnswer());
        }

        return rootView;
    }
}
