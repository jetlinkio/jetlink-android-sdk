package com.veslabs.jetlinklibrary.messaging;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.veslabs.jetlinklibrary.R;
import com.veslabs.jetlinklibrary.db.ChatMessagesDB;
import com.veslabs.jetlinklibrary.network.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Burhan Aras on 10/25/2016.
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private static final String TAG = ChatAdapter.class.getSimpleName();
    private static final int INCOMING_MESSAGE = 0;
    private static final int OUTGOING_MESSAGE = 1;

    private List<Message> messages = new ArrayList<>();
    private Context context;

    public ChatAdapter(Context context, List<Message> previousMessages) {
        this.context = context;
        this.messages.addAll(previousMessages);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root;
        if (viewType == INCOMING_MESSAGE) {
            root = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_incoming, null);
        } else {
            root = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_outgoing, null);
        }
        ViewHolder viewHolder = new ViewHolder(root);
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        if (messages.get(position).isIncoming()) {
            return INCOMING_MESSAGE;
        } else {
            return OUTGOING_MESSAGE;
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
            holder.tvMessage.setText(messages.get(position).getText());
            holder.tvDate.setText(DateUtil.convertDateToVisibleStringOnChatScreen(context, messages.get(position).getDate()));

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void addMessage(Message message) {
        if (message != null) {
            this.messages.add(message);
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView ivProfile;
        public TextView tvMessage;
        public TextView tvDate;
        public Button btnYes;
        public Button btnNo;

        public ViewHolder(View itemView) {
            super(itemView);
            ivProfile = (ImageView) itemView.findViewById(R.id.iv_profile);
            tvMessage = (TextView) itemView.findViewById(R.id.tv_message_text);
            tvDate = (TextView) itemView.findViewById(R.id.tv_mesage_date);
            btnYes = (Button) itemView.findViewById(R.id.chatbot_btn_yes);
            btnNo = (Button) itemView.findViewById(R.id.chatbot_btn_no);

            if (btnYes != null && btnNo != null) {
                btnYes.setOnClickListener(this);
                btnNo.setOnClickListener(this);
            }

        }

        @Override
        public void onClick(View view) {

        }
    }

    private void insertToDB(String text) {
        Message msg = new Message();
        msg.setText(text);
        msg.setIncoming(false);
        msg.setMessageType(Message.MESSAGE_TYPE_TEXT);
        addMessage(msg);
        ChatMessagesDB chatMessagesDB = new ChatMessagesDB(context);
        chatMessagesDB.insertChatMessage(msg);
        notifyDataSetChanged();
    }
}
