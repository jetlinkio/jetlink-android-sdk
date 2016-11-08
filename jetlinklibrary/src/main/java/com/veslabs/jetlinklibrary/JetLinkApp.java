package com.veslabs.jetlinklibrary;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.veslabs.jetlinklibrary.config.JetLinkUser;
import com.veslabs.jetlinklibrary.config.JetlinkConfig;
import com.veslabs.jetlinklibrary.messaging.AsyncResponse;
import com.veslabs.jetlinklibrary.messaging.async.RetrieveCompanyIdForThisAppAsyncTask;
import com.veslabs.jetlinklibrary.messaging.async.XmppConnectFacade;
import com.veslabs.jetlinklibrary.messaging.service.XMPPService;
import com.veslabs.jetlinklibrary.network.JetLinkInternalUser;
import com.veslabs.jetlinklibrary.network.Message;
import com.veslabs.jetlinklibrary.util.NotificationUtil;

/**
 * Created by Burhan Aras on 11/4/2016.
 */
public class JetLinkApp extends Application {
    private static final String TAG = JetLinkApp.class.getSimpleName();
    private static JetLinkApp INSTANCE = new JetLinkApp();
    private static Context applicationContext;
    private JetlinkConfig jetlinkConfig;
    private boolean chatActivityActive;
    private JetLinkInternalUser internalUser;
    private JetLinkUser user;
    private String companyId = null;
    private JetLinkBroadcastReceiver jetLinkIncomingMessageReceiver;

    public JetLinkApp() {
    }

    public static JetLinkApp getInstance(Context _applicationContext) {
        if (applicationContext == null) {
            JetLinkApp.applicationContext = _applicationContext;
        }
        return INSTANCE;
    }

    public void init(JetlinkConfig jetlinkConfig) {
        Log.d(TAG, "init() called with: jetlinkConfig = [" + jetlinkConfig.toString() + "]");
        this.jetlinkConfig = jetlinkConfig;
        String packageName = applicationContext.getPackageName();
        new RetrieveCompanyIdForThisAppAsyncTask(jetlinkConfig.getAppId(), jetlinkConfig.getAppToken(), packageName, new AsyncResponse() {
            @Override
            public void onSuccess(Object response) {
                companyId = (String) response;
                XmppConnectFacade xmppConnectFacade = new XmppConnectFacade(applicationContext);
                xmppConnectFacade.doInBackground();

                jetLinkIncomingMessageReceiver = new JetLinkBroadcastReceiver();
                LocalBroadcastManager.getInstance(applicationContext).registerReceiver(jetLinkIncomingMessageReceiver, new IntentFilter(XMPPService.KEY_JETLINK_BROADCASTS));
            }

            @Override
            public void onFailure(String message) {
                //redirect to error page
                Log.e(TAG, message);
            }
        }).execute();

    }

    private class JetLinkBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive() called with: context = [" + context + "], intent = [" + intent + "]");
            Message msg = intent.getParcelableExtra(XMPPService.KEY_CHAT_MESSAGE);
            Log.d(TAG, "onReceive: Message received : " + msg.toString());
            if (!isChatActivityActive()) {
                NotificationUtil.showNotification(applicationContext, msg);
            }
        }
    }

    public void setChatActivityActive(boolean chatActivityActive) {
        this.chatActivityActive = chatActivityActive;
    }

    public boolean isChatActivityActive() {
        return chatActivityActive;
    }

    public JetLinkUser getUser() {
        return user;
    }

    public void setUser(JetLinkUser user) {
        this.user = user;
        this.internalUser = new JetLinkInternalUser(user);
    }

    public JetLinkInternalUser getInternalUser() {
        return internalUser;
    }

    public void setInternalUser(JetLinkInternalUser internalUser) {
        this.internalUser = internalUser;
        if (internalUser != null && companyId != null) {
            internalUser.setCompanyId(companyId);
        }
    }

    public String getCompanyId() {
        return companyId;
    }

    public JetlinkConfig getJetlinkConfig() {
        return jetlinkConfig;
    }

    public void setJetlinkConfig(JetlinkConfig jetlinkConfig) {
        this.jetlinkConfig = jetlinkConfig;
    }
}
