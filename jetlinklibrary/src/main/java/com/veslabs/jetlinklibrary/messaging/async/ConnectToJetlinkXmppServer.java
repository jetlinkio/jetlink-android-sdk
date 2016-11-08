package com.veslabs.jetlinklibrary.messaging.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.veslabs.jetlinklibrary.messaging.AsyncResponse;

import org.jivesoftware.smack.AndroidConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackAndroid;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.provider.PrivacyProvider;
import org.jivesoftware.smack.provider.ProviderManager;
import org.jivesoftware.smackx.GroupChatInvitation;
import org.jivesoftware.smackx.PrivateDataManager;
import org.jivesoftware.smackx.bytestreams.socks5.provider.BytestreamsProvider;
import org.jivesoftware.smackx.packet.ChatStateExtension;
import org.jivesoftware.smackx.packet.LastActivity;
import org.jivesoftware.smackx.packet.OfflineMessageInfo;
import org.jivesoftware.smackx.packet.OfflineMessageRequest;
import org.jivesoftware.smackx.packet.SharedGroupsInfo;
import org.jivesoftware.smackx.provider.AdHocCommandDataProvider;
import org.jivesoftware.smackx.provider.DataFormProvider;
import org.jivesoftware.smackx.provider.DelayInformationProvider;
import org.jivesoftware.smackx.provider.DiscoverInfoProvider;
import org.jivesoftware.smackx.provider.DiscoverItemsProvider;
import org.jivesoftware.smackx.provider.MUCAdminProvider;
import org.jivesoftware.smackx.provider.MUCOwnerProvider;
import org.jivesoftware.smackx.provider.MUCUserProvider;
import org.jivesoftware.smackx.provider.MessageEventProvider;
import org.jivesoftware.smackx.provider.MultipleAddressesProvider;
import org.jivesoftware.smackx.provider.RosterExchangeProvider;
import org.jivesoftware.smackx.provider.StreamInitiationProvider;
import org.jivesoftware.smackx.provider.VCardProvider;
import org.jivesoftware.smackx.provider.XHTMLExtensionProvider;
import org.jivesoftware.smackx.pubsub.packet.PubSubNamespace;
import org.jivesoftware.smackx.pubsub.provider.SubscriptionProvider;
import org.jivesoftware.smackx.search.UserSearch;


/**
 * Created by Burhan Aras on 10/26/2016.
 */
public class ConnectToJetlinkXmppServer extends AsyncTask<Void, Void, Void>{
    private static final String TAG = ConnectToJetlinkXmppServer.class.getSimpleName();
    private Context context;
    private String serverUrl;
    private AsyncResponse asyncResponse;
    private XMPPConnection connection;

    public ConnectToJetlinkXmppServer(Context context,String server, AsyncResponse asyncResponse) {
        this.context = context;
        this.serverUrl = server;
        this.asyncResponse = asyncResponse;
    }


    @Override
    protected Void doInBackground(Void... voids) {
        Log.d(TAG, "doInBackground() serverUrl:" + serverUrl + "]");
        try {
            SmackAndroid.init(context);
            configure(ProviderManager.getInstance());

            ConnectionConfiguration config = new AndroidConnectionConfiguration(serverUrl, 5222);
            config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
            config.setReconnectionAllowed(false);
            config.setSASLAuthenticationEnabled(false);
            connection = new XMPPConnection(config);
            connection.connect();

        } catch (XMPPException e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());

        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (connection != null && connection.isConnected()) {
            asyncResponse.onSuccess(connection);
        }else{
            asyncResponse.onFailure("Connection failed.");
        }
    }

    private void configure(ProviderManager pm) {
        // Private Data Storage
        pm.addIQProvider("query", serverUrl + ":iq:private",
                new PrivateDataManager.PrivateDataIQProvider());

        // Time
        try {
            pm.addIQProvider("query", serverUrl + ":iq:time",
                    Class.forName("org.jivesoftware.smackx.packet.Time"));
        } catch (ClassNotFoundException e) {
            Log.w("TestClient",
                    "Can't load class for org.jivesoftware.smackx.packet.Time");
        }

        // Roster Exchange
        pm.addExtensionProvider("x", serverUrl + ":x:roster",
                new RosterExchangeProvider());

        // Message Events
        pm.addExtensionProvider("x", serverUrl + ":x:event",
                new MessageEventProvider());

        // Chat State
        pm.addExtensionProvider("active", "http://" + serverUrl
                + "/protocol/chatstates", new ChatStateExtension.Provider());
        pm.addExtensionProvider("composing", "http://" + serverUrl
                + "/protocol/chatstates", new ChatStateExtension.Provider());
        pm.addExtensionProvider("paused", "http://" + serverUrl
                + "/protocol/chatstates", new ChatStateExtension.Provider());
        pm.addExtensionProvider("inactive", "http://" + serverUrl
                + "/protocol/chatstates", new ChatStateExtension.Provider());
        pm.addExtensionProvider("gone", "http://" + serverUrl
                + "/protocol/chatstates", new ChatStateExtension.Provider());

        // XHTML
        pm.addExtensionProvider("html", "http://" + serverUrl
                + "/protocol/xhtml-im", new XHTMLExtensionProvider());

        // Group Chat Invitations
        pm.addExtensionProvider("x", serverUrl + ":x:conference",
                new GroupChatInvitation.Provider());

        // Service Discovery # Items
        pm.addIQProvider("query", "http://" + serverUrl
                + "/protocol/disco#items", new DiscoverItemsProvider());

        // Service Discovery # Info
        pm.addIQProvider("query", "http://" + serverUrl
                + "/protocol/disco#info", new DiscoverInfoProvider());

        // Data Forms
        pm.addExtensionProvider("x", serverUrl + ":x:data",
                new DataFormProvider());

        // MUC User
        pm.addExtensionProvider("x", "http://" + serverUrl
                + "/protocol/muc#user", new MUCUserProvider());

        // MUC Admin
        pm.addIQProvider("query", "http://" + serverUrl
                + "/protocol/muc#admin", new MUCAdminProvider());

        // MUC Owner
        pm.addIQProvider("query", "http://" + serverUrl
                + "/protocol/muc#owner", new MUCOwnerProvider());

        // Delayed Delivery
        pm.addExtensionProvider("x", serverUrl + ":x:delay",
                new DelayInformationProvider());

        // Version
        try {
            pm.addIQProvider("query", serverUrl + ":iq:version",
                    Class.forName("org.jivesoftware.smackx.packet.Version"));
        } catch (ClassNotFoundException e) {
            // Not sure what's happening here.
        }

        // VCard
        pm.addIQProvider("vCard", "vcard-temp", new VCardProvider());

        // Offline Message Requests
        pm.addIQProvider("offline", "http://" + serverUrl
                + "/protocol/offline", new OfflineMessageRequest.Provider());

        // Offline Message Indicator
        pm.addExtensionProvider("offline", "http://" + serverUrl
                + "/protocol/offline", new OfflineMessageInfo.Provider());

        // Last Activity
        pm.addIQProvider("query", serverUrl + ":iq:last",
                new LastActivity.Provider());

        // User Search
        pm.addIQProvider("query", serverUrl + ":iq:search",
                new UserSearch.Provider());

        // SharedGroupsInfo
        pm.addIQProvider("sharedgroup",
                "http://www.jivesoftware.org/protocol/sharedgroup",
                new SharedGroupsInfo.Provider());

        // JEP-33: Extended Stanza Addressing
        pm.addExtensionProvider("addresses", "http://" + serverUrl
                + "/protocol/address", new MultipleAddressesProvider());

        // FileTransfer
        pm.addIQProvider("si", "http://" + serverUrl + "/protocol/si",
                new StreamInitiationProvider());

        pm.addIQProvider("query", "http://" + serverUrl
                + "/protocol/bytestreams", new BytestreamsProvider());

        // Privacy
        pm.addIQProvider("query", serverUrl + ":iq:privacy",
                new PrivacyProvider());
        pm.addIQProvider("command", "http://" + serverUrl
                + "/protocol/commands", new AdHocCommandDataProvider());
        pm.addExtensionProvider("malformed-action", "http://"
                        + serverUrl + "/protocol/commands",
                new AdHocCommandDataProvider.MalformedActionError());
        pm.addExtensionProvider("bad-locale", "http://" + serverUrl
                        + "/protocol/commands",
                new AdHocCommandDataProvider.BadLocaleError());
        pm.addExtensionProvider("bad-payload", "http://" + serverUrl
                        + "/protocol/commands",
                new AdHocCommandDataProvider.BadPayloadError());
        pm.addExtensionProvider("bad-sessionid", "http://" + serverUrl
                        + "/protocol/commands",
                new AdHocCommandDataProvider.BadSessionIDError());
        pm.addExtensionProvider("session-expired", "http://" + serverUrl
                        + "/protocol/commands",
                new AdHocCommandDataProvider.SessionExpiredError());

        // ******************************Registratiion for
        // PUBSUB******************
        pm.addIQProvider("pubsub", "http://jabber.org/protocol/pubsub",
                new org.jivesoftware.smackx.pubsub.provider.PubSubProvider());

        pm.addExtensionProvider("subscription",
                PubSubNamespace.BASIC.getXmlns(), new SubscriptionProvider());

        pm.addExtensionProvider(
                "create",
                "http://jabber.org/protocol/pubsub",
                new org.jivesoftware.smackx.pubsub.provider.SimpleNodeProvider());

        pm.addExtensionProvider("items", "http://jabber.org/protocol/pubsub",
                new org.jivesoftware.smackx.pubsub.provider.ItemsProvider());

        pm.addExtensionProvider("item", "http://jabber.org/protocol/pubsub",
                new org.jivesoftware.smackx.pubsub.provider.ItemProvider());

        pm.addExtensionProvider("item", "",
                new org.jivesoftware.smackx.pubsub.provider.ItemProvider());

        pm.addExtensionProvider(
                "subscriptions",
                "http://jabber.org/protocol/pubsub",
                new org.jivesoftware.smackx.pubsub.provider.SubscriptionsProvider());

        pm.addExtensionProvider(
                "subscriptions",
                "http://jabber.org/protocol/pubsub#owner",
                new org.jivesoftware.smackx.pubsub.provider.SubscriptionsProvider());

        pm.addExtensionProvider(
                "affiliations",
                "http://jabber.org/protocol/pubsub",
                new org.jivesoftware.smackx.pubsub.provider.AffiliationsProvider());

        pm.addExtensionProvider(
                "affiliation",
                "http://jabber.org/protocol/pubsub",
                new org.jivesoftware.smackx.pubsub.provider.AffiliationProvider());

        pm.addExtensionProvider("options", "http://jabber.org/protocol/pubsub",
                new org.jivesoftware.smackx.pubsub.provider.FormNodeProvider());

        pm.addIQProvider("pubsub", "http://jabber.org/protocol/pubsub#owner",
                new org.jivesoftware.smackx.pubsub.provider.PubSubProvider());

        pm.addExtensionProvider("configure",
                "http://jabber.org/protocol/pubsub#owner",
                new org.jivesoftware.smackx.pubsub.provider.FormNodeProvider());

        pm.addExtensionProvider("default",
                "http://jabber.org/protocol/pubsub#owner",
                new org.jivesoftware.smackx.pubsub.provider.FormNodeProvider());

        pm.addExtensionProvider("event",
                "http://jabber.org/protocol/pubsub#event",
                new org.jivesoftware.smackx.pubsub.provider.EventProvider());

        pm.addExtensionProvider(
                "configuration",
                "http://jabber.org/protocol/pubsub#event",
                new org.jivesoftware.smackx.pubsub.provider.ConfigEventProvider());

        pm.addExtensionProvider(
                "delete",
                "http://jabber.org/protocol/pubsub#event",
                new org.jivesoftware.smackx.pubsub.provider.SimpleNodeProvider());

        pm.addExtensionProvider("options",
                "http://jabber.org/protocol/pubsub#event",
                new org.jivesoftware.smackx.pubsub.provider.FormNodeProvider());

        pm.addExtensionProvider("items",
                "http://jabber.org/protocol/pubsub#event",
                new org.jivesoftware.smackx.pubsub.provider.ItemsProvider());

        pm.addExtensionProvider("item",
                "http://jabber.org/protocol/pubsub#event",
                new org.jivesoftware.smackx.pubsub.provider.ItemProvider());

        pm.addExtensionProvider("headers", "http://jabber.org/protocol/shim",
                new org.jivesoftware.smackx.provider.HeaderProvider());

        pm.addExtensionProvider("header", "http://jabber.org/protocol/shim",
                new org.jivesoftware.smackx.provider.HeadersProvider());

        pm.addExtensionProvider(
                "retract",
                "http://jabber.org/protocol/pubsub#event",
                new org.jivesoftware.smackx.pubsub.provider.RetractEventProvider());

        pm.addExtensionProvider(
                "purge",
                "http://jabber.org/protocol/pubsub#event",
                new org.jivesoftware.smackx.pubsub.provider.SimpleNodeProvider());

        pm.addExtensionProvider("x", "jabber:x:data",
                new org.jivesoftware.smackx.provider.DataFormProvider());
    }
}
