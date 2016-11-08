package com.veslabs.jetlinklibrary.config;

import android.graphics.Color;

/**
 * Created by Burhan Aras on 11/7/2016.
 */
public class JetLinkUIProperties {
    private int backgroundColor;
    private int backgroundImage;
    private int incomingMessageBackgroundColor;
    private int incomingMessageTextColor;
    private int outgoingMessageBackgroundColor;
    private int outgoingMessageTextColor;
    private int toolbarColor;
    private int companyBrandLogo;

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(int backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public int getIncomingMessageBackgroundColor() {
        return incomingMessageBackgroundColor;
    }

    public void setIncomingMessageBackgroundColor(int incomingMessageBackgroundColor) {
        this.incomingMessageBackgroundColor = incomingMessageBackgroundColor;
    }

    public int getIncomingMessageTextColor() {
        return incomingMessageTextColor;
    }

    public void setIncomingMessageTextColor(int incomingMessageTextColor) {
        this.incomingMessageTextColor = incomingMessageTextColor;
    }

    public int getOutgoingMessageBackgroundColor() {
        return outgoingMessageBackgroundColor;
    }

    public void setOutgoingMessageBackgroundColor(int outgoingMessageBackgroundColor) {
        this.outgoingMessageBackgroundColor = outgoingMessageBackgroundColor;
    }

    public int getOutgoingMessageTextColor() {
        return outgoingMessageTextColor;
    }

    public void setOutgoingMessageTextColor(int outgoingMessageTextColor) {
        this.outgoingMessageTextColor = outgoingMessageTextColor;
    }

    public int getToolbarColor() {
        return toolbarColor;
    }

    public void setToolbarColor(int toolbarColor) {
        this.toolbarColor = toolbarColor;
    }

    public int getCompanyBrandLogo() {
        return companyBrandLogo;
    }

    public void setCompanyBrandLogo(int companyBrandLogo) {
        this.companyBrandLogo = companyBrandLogo;
    }

    @Override
    public String toString() {
        return "JetLinkUIProperties{" +
                "backgroundColor=" + backgroundColor +
                ", backgroundImage=" + backgroundImage +
                ", incomingMessageBackgroundColor=" + incomingMessageBackgroundColor +
                ", incomingMessageTextColor=" + incomingMessageTextColor +
                ", outgoingMessageBackgroundColor=" + outgoingMessageBackgroundColor +
                ", outgoingMessageTextColor=" + outgoingMessageTextColor +
                ", toolbarColor=" + toolbarColor +
                ", companyBrandLogo=" + companyBrandLogo +
                '}';
    }
}
