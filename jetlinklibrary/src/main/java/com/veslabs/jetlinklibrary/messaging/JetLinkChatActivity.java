package com.veslabs.jetlinklibrary.messaging;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.veslabs.jetlinklibrary.JetLinkApp;
import com.veslabs.jetlinklibrary.config.JetLinkUIProperties;
import com.veslabs.jetlinklibrary.R;
import com.veslabs.jetlinklibrary.db.ChatMessagesDB;
import com.veslabs.jetlinklibrary.network.JetLinkInternalUser;
import com.veslabs.jetlinklibrary.util.NotificationUtil;
import com.veslabs.jetlinklibrary.messaging.async.SendJetlinkMessageAsyncTask;
import com.veslabs.jetlinklibrary.messaging.async.XmppConnectFacade;
import com.veslabs.jetlinklibrary.messaging.service.XMPPService;
import com.veslabs.jetlinklibrary.network.Message;
import com.veslabs.jetlinklibrary.network.ServiceResult;

public class JetLinkChatActivity extends AppCompatActivity {
    private static final String TAG = JetLinkChatActivity.class.getSimpleName();
    private RecyclerView rvMessages;
    private ChatAdapter chatAdapter;

    private TextView tvNullText;
    private TextView tvMessage;
    private Button btnSend;
    private BroadcastReceiver jetLinkIncomingMessageReceiver;

    private ChatMessagesDB chatMessagesDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chatMessagesDB = new ChatMessagesDB(JetLinkChatActivity.this);

        rvMessages = (RecyclerView) findViewById(R.id.rv_messages);
        rvMessages.setLayoutManager(new LinearLayoutManager(JetLinkChatActivity.this));
        rvMessages.setHasFixedSize(true);
        chatAdapter = new ChatAdapter(JetLinkChatActivity.this, chatMessagesDB.getAllMessages());
        rvMessages.setAdapter(chatAdapter);
        rvMessages.smoothScrollToPosition(chatAdapter.getItemCount());

        tvNullText = (TextView) findViewById(R.id.tv_null_text);
        tvMessage = (TextView) findViewById(R.id.tv_send_message);
        btnSend = (Button) findViewById(R.id.btn_send_message);
        btnSend.setOnClickListener(new SendButtonOnClickListener());
        ifThereIsNoMessageThenRemoveNullText();
        configureUI();

        jetLinkIncomingMessageReceiver = new JetLinkBroadcastReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(jetLinkIncomingMessageReceiver, new IntentFilter(XMPPService.KEY_JETLINK_BROADCASTS));

    }

    private void configureUI() {
        JetLinkUIProperties uiProperties = JetLinkApp.getInstance(getApplicationContext()).getJetlinkConfig().getJetLinkUIProperties();
        if (uiProperties != null) {
            if (uiProperties.getBackgroundColor() != 0) {
                ((RelativeLayout) findViewById(R.id.activity_chat)).setBackgroundColor(uiProperties.getBackgroundColor());
            }
            if (uiProperties.getBackgroundImage() != 0) {
                ((RelativeLayout) findViewById(R.id.activity_chat)).setBackgroundResource(uiProperties.getBackgroundImage());
            }
            RelativeLayout rlIncomingMessage = (RelativeLayout) findViewById(R.id.rl_incoming_message);
            RelativeLayout rlOutgoingMessage = (RelativeLayout) findViewById(R.id.rl_outgoing_message);

            if (uiProperties.getIncomingMessageBackgroundColor() != 0) {
                ((LinearLayout) rlIncomingMessage.findViewById(R.id.ll_message)).setBackgroundColor(uiProperties.getBackgroundColor());
            }
            if (uiProperties.getOutgoingMessageBackgroundColor() != 0) {
                ((LinearLayout) rlOutgoingMessage.findViewById(R.id.ll_message)).setBackgroundColor(uiProperties.getBackgroundColor());
            }
            if (uiProperties.getIncomingMessageTextColor() != 0) {
                ((TextView) rlIncomingMessage.findViewById(R.id.tv_message_text)).setTextColor(uiProperties.getIncomingMessageTextColor());
            }
            if (uiProperties.getOutgoingMessageTextColor() != 0) {
                ((TextView) rlOutgoingMessage.findViewById(R.id.tv_message_text)).setTextColor(uiProperties.getOutgoingMessageTextColor());
            }
            if (uiProperties.getCompanyBrandLogo() != 0) {
                ((ImageView) findViewById(R.id.brand_logo)).setImageResource(uiProperties.getCompanyBrandLogo());
            }
            if (uiProperties.getToolbarColor() != 0) {
                ((RelativeLayout) findViewById(R.id.rl_toolbar)).setBackgroundResource(uiProperties.getToolbarColor());
            }
        }
    }

    private void ifThereIsNoMessageThenRemoveNullText() {
        if (chatAdapter.getItemCount() == 0) {
            tvNullText.setVisibility(View.VISIBLE);
        } else {
            tvNullText.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        NotificationUtil.calcelNotifications(JetLinkChatActivity.this);
        JetLinkApp.getInstance(JetLinkChatActivity.this).setChatActivityActive(true);
        final JetLinkInternalUser user = ((JetLinkApp) getApplication()).getInternalUser();
        if (user != null && user.getCompanyId() != null && !user.getCompanyId().equals("")) {
            btnSend.setEnabled(true);
            tvMessage.setEnabled(true);
        } else {
            btnSend.setEnabled(false);
            tvMessage.setEnabled(false);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        JetLinkApp.getInstance(JetLinkChatActivity.this).setChatActivityActive(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JetLinkApp.getInstance(JetLinkChatActivity.this).setChatActivityActive(false);
    }

    public static final int MENU_DELETE_ALL = Menu.FIRST;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(Menu.NONE, MENU_DELETE_ALL, Menu.NONE, "Mesajları Sil")
                .setIcon(android.R.drawable.ic_menu_delete)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return true;

//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.chat_menu, menu);
//        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.menu_delete:
//                ChatMessagesDB db = new ChatMessagesDB(JetLinkChatActivity.this);
//                db.deleteAll();
//                chatAdapter.notifyDataSetChanged();
//                break;
////            case MENU_DELETE_ALL:
////                ChatMessagesDB db = new ChatMessagesDB(JetLinkChatActivity.this);
////                db.deleteAll();
////                chatAdapter.notifyDataSetChanged();
////                break;
//        }
        return super.onOptionsItemSelected(item);
    }

    private class SendButtonOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            final String text = tvMessage.getText().toString();
            final JetLinkInternalUser user = ((JetLinkApp) getApplication()).getInternalUser();

            if (text != null && !text.equals("")) {
                if (user != null && user.getUserId() != null && !user.getUserId().equals("")) {

                    new SendJetlinkMessageAsyncTask(user.getUserId(), JetLinkApp.getInstance(JetLinkChatActivity.this).getCompanyId(), text, new AsyncResponse() {
                        @Override
                        public void onSuccess(Object response) {
                            Log.d(TAG, "message delivered." + ((ServiceResult) response).toString());
                            Message message = new Message();
                            message.setText(text);
                            message.setIncoming(false);
                            chatAdapter.addMessage(message);
                            rvMessages.smoothScrollToPosition(chatAdapter.getItemCount());
                            playNewMessageSound(false);
                            chatMessagesDB.insertChatMessage(message);
                            ifThereIsNoMessageThenRemoveNullText();
                        }

                        @Override
                        public void onFailure(String message) {
                            Log.e(TAG, message);
                        }
                    }).execute();


                } else {
                    Log.e(TAG, "JetLinkInternalUser must be authenticated. UserId musn't be null.");
                    XmppConnectFacade xmppConnectFacade = new XmppConnectFacade(JetLinkChatActivity.this);
                    xmppConnectFacade.doInBackground();
                }

                tvMessage.setText("");

            }
        }
    }

    private class JetLinkBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive() called with: context = [" + context + "], intent = [" + intent + "]");
            Message msg = intent.getParcelableExtra(XMPPService.KEY_CHAT_MESSAGE);
            Log.d(TAG, "onReceive: Message received : " + msg.toString());
            chatAdapter.addMessage(msg);
            rvMessages.smoothScrollToPosition(chatAdapter.getItemCount());
            playNewMessageSound(true);
            ifThereIsNoMessageThenRemoveNullText();

        }
    }

    private void playNewMessageSound(boolean incomingMessage) {
        if (incomingMessage) {
            SoundPoolPlayer soundPoolPlayer = new SoundPoolPlayer(this, R.raw.incoming);
        } else {
            SoundPoolPlayer soundPoolPlayer = new SoundPoolPlayer(this, R.raw.send_message);
        }
        //it will automatically play sound. no need to do more.
    }
}
