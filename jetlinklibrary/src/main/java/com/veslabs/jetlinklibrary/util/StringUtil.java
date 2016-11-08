package com.veslabs.jetlinklibrary.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.veslabs.jetlinklibrary.network.XmppMessage;

/**
 * Created by Burhan Aras on 10/27/2016.
 */
public class StringUtil {
    private static final String TAG = StringUtil.class.getSimpleName();

    public static String trimDomain(String str) {
        if (str != null && str.length() > 0 && str.contains("@")) {
            int indexOfATchar = str.indexOf('@');
            str = str.subSequence(0, indexOfATchar).toString();
            return str;
        }
        return "";
    }

    public static String parseMessageBody(String messageBody) {

        try {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            XmppMessage message = gson.fromJson(messageBody, XmppMessage.class);
            message.getFromUserId();
            return message.getMessageText();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
