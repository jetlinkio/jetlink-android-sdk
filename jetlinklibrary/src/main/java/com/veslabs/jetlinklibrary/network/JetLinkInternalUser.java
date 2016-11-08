package com.veslabs.jetlinklibrary.network;

import android.os.Parcel;
import android.os.Parcelable;

import com.veslabs.jetlinklibrary.config.JetLinkUser;

/**
 * Created by Burhan Aras on 10/25/2016.
 */
public class JetLinkInternalUser implements Parcelable {
    private static final String USERSOURCE_ANDROID = "4";
    private String UserId;
    private String CompanyId;
    private String UserSource = USERSOURCE_ANDROID;
    private String SourceUserId;
    private String ChatUserId;
    private String ChatUserPassword;
    private String Name;
    private String Surname;
    private String Email;
    private String Password;
    private String Phone;
    private String AvatarUrl;
    private String LastSeen;
    private String LastMessage;

    public JetLinkInternalUser() {
    }

    public JetLinkInternalUser(Parcel in) {
        UserId = in.readString();
        CompanyId = in.readString();
        UserSource = in.readString();
        SourceUserId = in.readString();
        ChatUserId = in.readString();
        ChatUserPassword = in.readString();
        Name = in.readString();
        Surname = in.readString();
        Email = in.readString();
        Password = in.readString();
        Phone = in.readString();
        AvatarUrl = in.readString();
        LastSeen = in.readString();
        LastMessage = in.readString();
    }

    public static final Creator<JetLinkInternalUser> CREATOR = new Creator<JetLinkInternalUser>() {
        @Override
        public JetLinkInternalUser createFromParcel(Parcel in) {
            return new JetLinkInternalUser(in);
        }

        @Override
        public JetLinkInternalUser[] newArray(int size) {
            return new JetLinkInternalUser[size];
        }
    };

    public JetLinkInternalUser(JetLinkUser user) {
        Name = user.getName();
        Surname = user.getSurname();
        Email = user.getEmail();
        Phone = user.getPhone();
        AvatarUrl = user.getAvatarUrl();
        UserSource = USERSOURCE_ANDROID;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(String companyId) {
        CompanyId = companyId;
    }

    public String getUserSource() {
        return UserSource;
    }

    public void setUserSource(String userSource) {
        UserSource = userSource;
    }

    public String getSourceUserId() {
        return SourceUserId;
    }

    public void setSourceUserId(String sourceUserId) {
        SourceUserId = sourceUserId;
    }

    public String getChatUserId() {
        return ChatUserId;
    }

    public void setChatUserId(String chatUserId) {
        ChatUserId = chatUserId;
    }

    public String getChatUserPassword() {
        return ChatUserPassword;
    }

    public void setChatUserPassword(String chatUserPassword) {
        ChatUserPassword = chatUserPassword;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getAvatarUrl() {
        return AvatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        AvatarUrl = avatarUrl;
    }

    public String getLastSeen() {
        return LastSeen;
    }

    public void setLastSeen(String lastSeen) {
        LastSeen = lastSeen;
    }

    public String getLastMessage() {
        return LastMessage;
    }

    public void setLastMessage(String lastMessage) {
        LastMessage = lastMessage;
    }

    @Override
    public String toString() {
        return "JetLinkInternalUser{" +
                "UserId='" + UserId + '\'' +
                ", CompanyId='" + CompanyId + '\'' +
                ", UserSource='" + UserSource + '\'' +
                ", SourceUserId='" + SourceUserId + '\'' +
                ", ChatUserId='" + ChatUserId + '\'' +
                ", ChatUserPassword='" + ChatUserPassword + '\'' +
                ", Name='" + Name + '\'' +
                ", Surname='" + Surname + '\'' +
                ", Email='" + Email + '\'' +
                ", Password='" + Password + '\'' +
                ", Phone='" + Phone + '\'' +
                ", AvatarUrl='" + AvatarUrl + '\'' +
                ", LastSeen='" + LastSeen + '\'' +
                ", LastMessage='" + LastMessage + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(UserId);
        parcel.writeString(CompanyId);
        parcel.writeString(UserSource);
        parcel.writeString(SourceUserId);
        parcel.writeString(ChatUserId);
        parcel.writeString(ChatUserPassword);
        parcel.writeString(Name);
        parcel.writeString(Surname);
        parcel.writeString(Email);
        parcel.writeString(Password);
        parcel.writeString(Phone);
        parcel.writeString(AvatarUrl);
        parcel.writeString(LastSeen);
        parcel.writeString(LastMessage);
    }
}
