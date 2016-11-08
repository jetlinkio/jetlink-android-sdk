package com.veslabs.jetlinklibrary.network;


import java.util.List;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by Burhan Aras on 10/25/2016.
 */

public interface IApiMethods {

    @GET("/General/GetSystemConfigurations")
    List<JetLinkConfiguration> getSystemConfigurations();

    @POST("/Messaging/CreateChatUser")
    ServiceResult createChatUser(@Body JetLinkInternalUser user);

    @POST("/Messaging/UpdateChatUser")
    ServiceResult updateChatUser(@Body JetLinkInternalUser user);

    @GET("/Messaging/GetChatUserByEmail")
    JetLinkInternalUser getChatUserByEmail(@Query("email") String email);

    @GET("/Messaging/GetChatUserByPhone")
    JetLinkInternalUser getChatUserByPhone(@Query("phone") String phone);

    @GET("/Messaging/SendMessage")
    ServiceResult sendMessage(@Query("fromUserId") String fromUserId
            , @Query("toCompanyId") String toCompanyId
            , @Query("messageContent") String messageContent
            , @Query("isViaIOS") String isViaIOS);

    @GET("/Company/GetCompanyByAppInfo")
    ServiceResult getCompanyByAppInfo(@Query("developerAppId") String developerAppId, @Query("developerAppToken") String developerAppToken, @Query("applicationPackageName") String applicationPackageName);

    @GET("/FAQ/GetFAQCategories")
    List<FAQCategory> getFAQCategories(@Query("companyId") String companyId);

    @GET("/FAQ/GetFAQListByCategory")
    List<FAQ> getFAQListByCategory(@Query("faqCategoryId") String faqCategoryId, @Query("companyId") String companyId);

}
