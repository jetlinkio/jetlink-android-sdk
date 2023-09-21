## Jetlink Android - How to use Jetlink messaging Android in iOS applicaitons
[![Website](https://static.wixstatic.com/media/5750ed_9f0be19719cc4fdb89b40bdf78e22584~mv2.png/v1/fill/w_203,h_137,al_c,q_85,usm_0.66_1.00_0.01,enc_auto/Jetlink%20New%20Logo.png)](https://jetlink.io)
### [Official Website](https://jetlink.io/)

Meaningful experiences + high ROI delivered with the most sophisticated Conversational AI
----------------------------------------------------------------------------------------

## HOW TO USE JETLINK

### 1. Place any suitable icon on you application.
It can be used in Menu, Header Bar or anywhere that you want your users can reach chatbot interface.

### 2. Open Jetlink Webview URL after clicking chatbot icon

You can find Jetlink Webview URL on your Jetlink Dashboard. It should be in Settings -> Android

Jetlink WebView URL can be as following

```
https://public.jetlink.io/Home/MobilSDK?appId=<YOUR-APP-ID>&appKey=<YOUR-APP-KEY>
	&mobileSDKType=android&mobileDeviceName=<DEVICE-NAME>&mobileDeviceOS=<DEVICE-OS-VERSION>
```

### 4. Initialize Jetlink UI with User's Login Information

You can also open Jetlink Webview URL with your user's login information such as name, surname, email, phone number, and userId

```
https://public.jetlink.io/Home/MobilSDK?appId=<YOUR-APP-ID>&appKey=<YOUR-APP-KEY>
	&mobileSDKType=android&mobileDeviceName=<DEVICE-NAME>&mobileDeviceOS=<DEVICE-OS-VERSION>
	&username=<USER-FIRST-NAME>&userSurname=<USER-SURNAME>
	&userEmail=<USER-EMAIL>&userPhone=<USER-PHONE-NUMBER>
	&userSourceUserId=<USER-UNIQUE-ID-IN-YOUR-SYSTEM>
```

You can send any user sensitive information via Jetlink WebView URL.
Name, surname, email, phone and your user unique ID... You can send all or some of these due to which information is stored on your system. Jetlink UI will open with these information and remember the user by these values another time they will chat again.  

That's all.. You can start messaging on your on mobile applicaion.
