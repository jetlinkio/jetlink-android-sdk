package com.veslabs.jetlinklibrary.messaging.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.veslabs.jetlinklibrary.db.ChatMessagesDB;
import com.veslabs.jetlinklibrary.messaging.AsyncResponse;
import com.veslabs.jetlinklibrary.messaging.async.ConnectToJetlinkXmppServer;
import com.veslabs.jetlinklibrary.network.JetLinkInternalUser;
import com.veslabs.jetlinklibrary.util.StringUtil;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smackx.ChatState;
import org.jivesoftware.smackx.ChatStateListener;
import org.jivesoftware.smackx.ping.PingFailedListener;
import org.jivesoftware.smackx.ping.PingManager;

public class XMPPService extends Service implements Handler.Callback,
        ChatManagerListener, MessageListener, PacketListener, ChatStateListener,
        ConnectionListener {
    private static final String TAG = XMPPService.class.getSimpleName();
    public static final String EXTRA_USER = "EXTRA_USER";
    public static final String EXTRA_SERVER_URL = "EXTRA_SERVER_URL";
    public static final String KEY_CHAT_MESSAGE = "KEY_CHAT_MESSAGE";
    public static final String KEY_JETLINK_BROADCASTS = "KEY_JETLINK_BROADCASTS";
    private IBinder mBinder = new MyBinder();
    private JetLinkInternalUser user;

    private XMPPConnection xmppConnection = null;
    private String serverUrl;
    private PingManager mPingManager;
    private static boolean isAuthenticated = false;

    public XMPPService() {

    }

    public static boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            user = (JetLinkInternalUser) intent.getExtras().get(EXTRA_USER);
            serverUrl = intent.getExtras().getString(EXTRA_SERVER_URL);
            if (user != null) {
                connectToJetLinkServer();
            } else {
                Log.e(TAG, "user is null");
            }
        }
        return START_STICKY;
    }

    private void connectToJetLinkServer() {
        if (xmppConnection == null || !xmppConnection.isConnected()) {
            new ConnectToJetlinkXmppServer(XMPPService.this, serverUrl, new AsyncResponse() {
                @Override
                public void onSuccess(Object response) {
                    xmppConnection = (XMPPConnection) response;
                    xmppConnection.addConnectionListener(XMPPService.this);
                    Log.d(TAG, "Connected to JetlinkXmppServer");
                    try {
                        Log.d(TAG, "login() with "+user.getChatUserId()+" "+ user.getChatUserPassword());
                        xmppConnection.login(user.getChatUserId(), user.getChatUserPassword());
                        Log.d(TAG, xmppConnection.isAuthenticated() ? "authenticated" : "not authenticated");
                        if (xmppConnection.isAuthenticated()) {
                            isAuthenticated=true;
                            mPingManager = PingManager.getInstanceFor(xmppConnection);
                            mPingManager.setPingIntervall(5);
                            mPingManager
                                    .registerPingFailedListener(new PingFailedListener() {
                                        @Override
                                        public void pingFailed() {
                                            // LogUtils.printLogD("Ping failed");
                                        }
                                    });

                            xmppConnection.getChatManager().addChatListener(XMPPService.this);

                        }

                    } catch (XMPPException e) {
                        e.printStackTrace();
                        Log.e(TAG,e.getMessage());
                    }
                }

                @Override
                public void onFailure(String message) {
                    Log.e(TAG, message);
                }
            }).execute();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind() called with: intent = [" + intent + "]");
        return mBinder;
    }

    @Override
    public boolean handleMessage(Message message) {
        Log.d(TAG, "handleMessage() called with: message = [" + message + "]");
        return false;
    }

    @Override
    public void chatCreated(Chat chat, boolean b) {
        Log.d(TAG, "chatCreated() called with: chat = [" + chat.toString() + "], b = [" + b + "]");
        chat.addMessageListener(XMPPService.this);
    }

    @Override
    public void connectionClosed() {
        Log.d(TAG, "connectionClosed() called");
        isAuthenticated=false;
    }

    @Override
    public void connectionClosedOnError(Exception e) {
        Log.d(TAG, "connectionClosedOnError() called with: e = [" + e + "]");
        isAuthenticated=false;
        connectToJetLinkServer();
    }

    @Override
    public void reconnectingIn(int i) {
        Log.d(TAG, "reconnectingIn() called with: i = [" + i + "]");
    }

    @Override
    public void reconnectionSuccessful() {
        Log.d(TAG, "reconnectionSuccessful() called");
    }

    @Override
    public void reconnectionFailed(Exception e) {
        Log.d(TAG, "reconnectionFailed() called with: e = [" + e + "]");
    }

    @Override
    public void processPacket(Packet packet) {
        Log.d(TAG, "processPacket() called with: packet = [" + packet + "]");
    }

    @Override
    public void stateChanged(Chat chat, ChatState chatState) {
        Log.d(TAG, "stateChanged() called with: chat = [" + chat + "], chatState = [" + chatState + "]");
    }

    @Override
    public void processMessage(Chat chat, org.jivesoftware.smack.packet.Message message) {
        Log.d(TAG, "processMessage() called with: chat = [" + chat.toString() + "], message = [" + message.toXML() + "]");
        if (message != null) {
            if (message.toXML().contains(ChatState.composing.toString())) {
                Log.d(TAG, "processMessage: Composing....");
            } else if (message.toXML().contains(ChatState.paused.toString())) {
                Log.d(TAG, "processMessage: Paused");
            }else{
                String messageText = StringUtil.parseMessageBody(message.getBody());
                com.veslabs.jetlinklibrary.network.Message msg = new com.veslabs.jetlinklibrary.network.Message();
                msg.setText(messageText);
                msg.setIncoming(true);
                sendBroadcastForIncomingMessage(msg);

                ChatMessagesDB chatMessagesDB=new ChatMessagesDB(XMPPService.this);
                chatMessagesDB.insertChatMessage(msg);
            }
        }
    }

    private void sendBroadcastForIncomingMessage(com.veslabs.jetlinklibrary.network.Message message) {
        Log.d(TAG, "sendBroadcastForIncomingMessage() called with: message = [" + message + "]");
        Intent broadcastIntent=new Intent(KEY_JETLINK_BROADCASTS);
        broadcastIntent.putExtra(KEY_CHAT_MESSAGE,message);
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
    }

    public static Intent newIntent(Context callerActivity, JetLinkInternalUser user, String serverUrl) {
        Intent intent = new Intent(callerActivity, XMPPService.class);
        intent.putExtra(EXTRA_USER, user);
        intent.putExtra(EXTRA_SERVER_URL, serverUrl);
        return intent;
    }

    public class MyBinder extends Binder {
        XMPPService getService() {
            return XMPPService.this;
        }
    }
}
