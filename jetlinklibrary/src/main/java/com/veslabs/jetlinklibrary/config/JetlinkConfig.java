package com.veslabs.jetlinklibrary.config;

/**
 * Created by Burhan Aras on 11/4/2016.
 */
public class JetlinkConfig {
    private static final String TAG = JetlinkConfig.class.getSimpleName();
    private  String appId;
    private  String appToken;
    private  String packageName;
    private JetLinkUIProperties jetLinkUIProperties;

    public JetlinkConfig(String appId, String appToken) {

        this.appId = appId;
        this.appToken = appToken;
        packageName = "";
    }

    public String getAppId() {
        return appId;
    }

    public String getAppToken() {
        return appToken;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setAppToken(String appToken) {
        this.appToken = appToken;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public JetLinkUIProperties getJetLinkUIProperties() {
        return jetLinkUIProperties;
    }

    public void setJetLinkUIProperties(JetLinkUIProperties jetLinkUIProperties) {
        this.jetLinkUIProperties = jetLinkUIProperties;
    }


    @Override
    public String toString() {
        return "JetlinkConfig{" +
                "appId='" + appId + '\'' +
                ", appToken='" + appToken + '\'' +
                ", packageName='" + packageName + '\'' +
                ", jetLinkUIProperties=" + jetLinkUIProperties +
                '}';
    }
}
