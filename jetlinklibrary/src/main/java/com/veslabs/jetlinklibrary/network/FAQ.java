package com.veslabs.jetlinklibrary.network;

import java.io.Serializable;

/**
 * Created by Burhan Aras on 11/7/2016.
 */
public class FAQ implements Serializable{
    private String FAQId;
    private String CompanyId;
    private String FAQCategoryId;
    private String Question;
    private String Answer;
    private String Status;
    private String CreateDate;
    private String LastUpdate;
    private String LastUpdaterUserId;

    public FAQ() {
    }

    public String getFAQId() {
        return FAQId;
    }

    public void setFAQId(String FAQId) {
        this.FAQId = FAQId;
    }

    public String getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(String companyId) {
        CompanyId = companyId;
    }

    public String getFAQCategoryId() {
        return FAQCategoryId;
    }

    public void setFAQCategoryId(String FAQCategoryId) {
        this.FAQCategoryId = FAQCategoryId;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getLastUpdate() {
        return LastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        LastUpdate = lastUpdate;
    }

    public String getLastUpdaterUserId() {
        return LastUpdaterUserId;
    }

    public void setLastUpdaterUserId(String lastUpdaterUserId) {
        LastUpdaterUserId = lastUpdaterUserId;
    }
}
