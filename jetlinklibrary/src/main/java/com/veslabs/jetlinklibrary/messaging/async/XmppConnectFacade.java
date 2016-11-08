package com.veslabs.jetlinklibrary.messaging.async;

import android.content.Context;
import android.util.Log;

import com.veslabs.jetlinklibrary.JetLinkApp;
import com.veslabs.jetlinklibrary.messaging.AsyncResponse;
import com.veslabs.jetlinklibrary.messaging.service.XMPPService;
import com.veslabs.jetlinklibrary.network.JetLinkConfiguration;
import com.veslabs.jetlinklibrary.network.JetLinkInternalUser;
import com.veslabs.jetlinklibrary.network.ServiceResult;
import com.veslabs.jetlinklibrary.util.StringUtil;

import java.util.List;

/**
 * Created by Burhan Aras on 11/1/2016.
 */
public class XmppConnectFacade {
    private static final String TAG = XmppConnectFacade.class.getSimpleName();
    private String chatServerDomain;
    private Context context;

    public XmppConnectFacade(Context context) {
        this.context = context;
    }

    public void doInBackground() {
        new RetrieveJetlinkConfigurationsAsyncTask(new AsyncResponse() {
            @Override
            public void onSuccess(Object response) {
                List<JetLinkConfiguration> configurations = (List<JetLinkConfiguration>) response;
                for (JetLinkConfiguration configuration : configurations) {
                    if (configuration.getKey().equals("ChatServerDomain")) {
                        chatServerDomain = configuration.getValue();
                        if (chatServerDomain != null && chatServerDomain.length() > 0 && chatServerDomain.charAt(0) == '@') {
                            chatServerDomain = chatServerDomain.substring(1);
                            downloadUserInfoFromJetlinkServer();
                        }
                        Log.d(TAG, "chatServerDomain is " + chatServerDomain);
                        break;
                    }
                }

            }

            @Override
            public void onFailure(String message) {

            }
        }).execute();
    }

    private void downloadUserInfoFromJetlinkServer() {
        final JetLinkInternalUser user = JetLinkApp.getInstance(context).getInternalUser();
        if (user == null) {
            Log.e(TAG, "JetLinkInternalUser is null");
            return;
        }
        new RetrieveJetlinkUserInfoAsyncTask(user.getEmail(), user.getPhone(), new AsyncResponse() {
            @Override
            public void onSuccess(Object response) {
                ((JetLinkApp) context.getApplicationContext()).setInternalUser((JetLinkInternalUser) response);
                ((JetLinkApp) context.getApplicationContext()).getInternalUser().setChatUserId(StringUtil.trimDomain(((JetLinkInternalUser) response).getChatUserId()));
                addListenerForIncomingMessagesFromJetlinkServer();
            }

            @Override
            public void onFailure(String message) {
                register();
            }
        }).execute();
    }


    private void register() {
        final JetLinkInternalUser user = ((JetLinkApp) context.getApplicationContext()).getInternalUser();

        new RegisterUserToJetlinkServerAsyncTask(user, new AsyncResponse() {
            @Override
            public void onSuccess(Object response) {
                ServiceResult.UserData userData = (ServiceResult.UserData) response;
                user.setUserId(userData.getUserId());
                user.setChatUserId(StringUtil.trimDomain(userData.getChatUsername()));
                user.setChatUserPassword(userData.getChatPassword());

                downloadUserInfoFromJetlinkServer();
            }

            @Override
            public void onFailure(String message) {
                Log.e(TAG, message);
            }
        }).execute();
    }

    private void addListenerForIncomingMessagesFromJetlinkServer() {
        final JetLinkInternalUser user = ((JetLinkApp) context.getApplicationContext()).getInternalUser();
        context.startService(XMPPService.newIntent(context, user, chatServerDomain));
    }

}
