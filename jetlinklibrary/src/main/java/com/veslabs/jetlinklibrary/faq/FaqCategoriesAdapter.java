package com.veslabs.jetlinklibrary.faq;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.veslabs.jetlinklibrary.R;
import com.veslabs.jetlinklibrary.network.FAQCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Burhan Aras on 11/7/2016.
 */
public class FaqCategoriesAdapter extends RecyclerView.Adapter<FaqCategoriesAdapter.ViewHolder> {
    private static final String TAG = FaqCategoriesAdapter.class.getSimpleName();
    private List<FAQCategory> faqCategories = new ArrayList<>();
    private Context context;

    public FaqCategoriesAdapter(Context context, List<FAQCategory> faqCategories) {
        this.context = context;
        if (faqCategories != null) {
            this.faqCategories = faqCategories;
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.faq_category, parent, false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvTitle.setText(faqCategories.get(position).getCategory());
    }

    @Override
    public int getItemCount() {
        return faqCategories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //goes to details
            if (context != null) {
                context.startActivity(FaqListActivity.newIntent(context, faqCategories.get(getAdapterPosition())));
            }
        }
    }
}
