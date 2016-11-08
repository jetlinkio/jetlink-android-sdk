package com.veslabs.jetlinklibrary.network;

import java.io.Serializable;

/**
 * Created by Burhan Aras on 11/7/2016.
 */
public class FAQCategory implements Serializable {
    private  String FAQCategoryId;
    private  String CompanyId;
    private  String Category;
    private  String Status;
    private  String CreateDate;
    private  String LastUpdate;
    private  String LastUpdaterUserId;

    public FAQCategory() {
    }

    public String getFAQCategoryId() {
        return FAQCategoryId;
    }

    public void setFAQCategoryId(String FAQCategoryId) {
        this.FAQCategoryId = FAQCategoryId;
    }

    public String getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(String companyId) {
        CompanyId = companyId;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
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
