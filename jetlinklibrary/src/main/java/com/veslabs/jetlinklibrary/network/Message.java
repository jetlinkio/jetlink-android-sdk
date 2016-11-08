package com.veslabs.jetlinklibrary.network;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Burhan Aras on 10/25/2016.
 */
public class Message  implements Parcelable {
    public static final int MESSAGE_TYPE_TEXT = 0;

    private String id;
    private String senderId;
    private String senderName;
    private String senderPhoto;
    private String text;
    private boolean isSeen;
    private boolean isIncoming;
    private long date;
    private int messageType;

    public Message() {
        date = System.currentTimeMillis();
    }

    protected Message(Parcel in) {
        id = in.readString();
        senderId = in.readString();
        senderName = in.readString();
        senderPhoto = in.readString();
        text = in.readString();
        isSeen = in.readByte() != 0;
        isIncoming = in.readByte() != 0;
        date = in.readLong();
        messageType = in.readInt();
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderPhoto() {
        return senderPhoto;
    }

    public void setSenderPhoto(String senderPhoto) {
        this.senderPhoto = senderPhoto;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public void setSeen(boolean seen) {
        isSeen = seen;
    }

    public boolean isIncoming() {
        return isIncoming;
    }

    public void setIncoming(boolean incoming) {
        isIncoming = incoming;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(senderId);
        parcel.writeString(senderName);
        parcel.writeString(senderPhoto);
        parcel.writeString(text);
        parcel.writeByte((byte) (isSeen ? 1 : 0));
        parcel.writeByte((byte) (isIncoming ? 1 : 0));
        parcel.writeLong(date);
        parcel.writeInt(messageType);
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", senderId='" + senderId + '\'' +
                ", senderName='" + senderName + '\'' +
                ", senderPhoto='" + senderPhoto + '\'' +
                ", text='" + text + '\'' +
                ", isSeen=" + isSeen +
                ", isIncoming=" + isIncoming +
                ", date=" + date +
                ", messageType=" + messageType +
                '}';
    }
}
