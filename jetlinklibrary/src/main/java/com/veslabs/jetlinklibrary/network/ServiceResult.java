package com.veslabs.jetlinklibrary.network;

/**
 * Created by Burhan Aras on 10/25/2016.
 */
public class ServiceResult {

    public static final String VERI_GONDERILEMEDI = "100";
    public static final String VERI_BULUNAMADI = "200";
    public static final String BASARILI = "0";
    public static final String HATA_OLUSTU = "300";

    private String Code;
    private String Message;
    private UserData Data;

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public UserData getData() {
        return Data;
    }

    public void setData(UserData data) {
        Data = data;
    }

    @Override
    public String toString() {
        return "ServiceResult{" +
                "Code='" + Code + '\'' +
                ", Message='" + Message + '\'' +
                ", Data='" + ((Data != null) ? Data.toString():"")
                + '\'' +
                '}';
    }

    public class UserData {
        /*"UserId":"581065197037e22f04ab102d","ChatUsername":"android-580ef106bad1510ccf899b6e-9adee3-burhan.aras-@chat-server.jetlink.io","ChatPassword":"jtlnk_user!123"}*/
        private String UserId;
        private String ChatUsername;
        private String ChatPassword;

        public String getUserId() {
            return UserId;
        }

        public void setUserId(String userId) {
            UserId = userId;
        }

        public String getChatUsername() {
            return ChatUsername;
        }

        public void setChatUsername(String chatUsername) {
            ChatUsername = chatUsername;
        }

        public String getChatPassword() {
            return ChatPassword;
        }

        public void setChatPassword(String chatPassword) {
            ChatPassword = chatPassword;
        }

        @Override
        public String toString() {
            return "UserData{" +
                    "UserId='" + UserId + '\'' +
                    ", ChatUsername='" + ChatUsername + '\'' +
                    ", ChatPassword='" + ChatPassword + '\'' +
                    '}';
        }
    }
}
