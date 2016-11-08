package com.veslabs.jetlinklibrary.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Burhan Aras on 10/27/2016.
 */
public class XmppMessage {
    @SerializedName("MessageId")
    @Expose
    private String messageId;
    @SerializedName("ConversationId")
    @Expose
    private String conversationId;
    @SerializedName("FromUserId")
    @Expose
    private String fromUserId;
    @SerializedName("ToUserId")
    @Expose
    private String toUserId;
    @SerializedName("MessageText")
    @Expose
    private String messageText;
    @SerializedName("MessageType")
    @Expose
    private String messageType;
    @SerializedName("Payload")
    @Expose
    private String payload;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
