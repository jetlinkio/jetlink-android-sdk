## JetLink Android SDK
[![Website](https://app.jetlink.io/Assets/img/jetlink-logo-medium.png)](https://jetlink.io)
### [Official Website](https://jetlink.io/)

Messaging platform for easy commerce and better support.

##### Quick Steps

App Module gradle file **(app/build.gradle)**
```
dependencies {
      implementation('org.bitbucket.veslabs:jetlink-android-sdk-v2:2.0.9') {
        transitive = false
    }
}
```

That's All! JetLink is ready to go! Now, you have a messaging module in your app.

Congratulations!!!



----------------------------------------------------------------------------------------


## HOW TO USE JETLINK

### 1. Add dependencies to our app module's Gradle file. (project/app/build.gradle)

```
dependencies {
        implementation('org.bitbucket.veslabs:jetlink-android-sdk-v2:2.0.9') {
        transitive = false
    }

}
```


And your minSdkVersion must at least be 16.


### 2. Initialize JetLink

Add the following to your app's launcher activity’s onCreate() method or Application class' onCreate(). 
Don't forget to replace the <YOUR-APP-ID> and <YOUR-APP-KEY> in the following code snippet with the actual app ID and app key.   

```
	final JetlinkConfig jetlinkConfig = new JetlinkConfig("<YOUR-APP-ID>", "<YOUR-APP-KEY>");
	JetlinkApp.getInstance(getApplicationContext()).init(jetlinkConfig);

```


### 3. Initialize User

You can send basic user information at the beginning to give you more context on the user when your support agents are messaging back and forth with them.
**(Email, Phone,)** and **(SourceUserId)** fields are mandatory.       

```	
		final JetlinkUser user = new JetlinkUser();
		user.setEmail("test@test.com");
		user.setGenderString("male");
		user.setName("Test User");
		user.setSurname("Surname");
		user.setPhone("021384217319");
		user.setSourceUserId("08265");
		JetlinkApp.getInstance(getApplicationContext()).createUser(user);
		
```

### 4. Update User
     

```	
		final JetlinkUser user = new JetlinkUser();
		user.setEmail("test@test.com");
		user.setGenderString("male");
		user.setName("Test User");
		user.setSurname("Surname");
		user.setPhone("021384217319");
		JetlinkApp.getInstance(getApplicationContext()).updateUser(user);
		
```

### 5. Update User Avatar
     

```	
	final File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "avatar.jpg");
	JetlinkApp.getInstance(getApplicationContext()).updateUserAvatar(file.getPath());
		
```



### 4. Call the Chat Screen

Just call activateChatPanel and it will open a ready-to-use chat screen. Chat screen uses your colorPrimary, colorPrimaryDark, colorAccent

```
JetlinkApp.activateChatPanel();
```

### 5. Offline Messages (Push Notifications)

You need to integrate Firebase Cloud Messaging (FCM) into your app in order to get messages while your app is killed. 
Because this mechanism uses push messages to trigger Jetlink and Jetlink works with Firebase Cloud Messaging System of Google.

[For Firebase Cloud Messaging integration please click.](https://github.com/jetlinkio/jetlink-android-sdk/wiki/Notification-Message-Integration)

## VIDEO TUTORIAL
Altough we have clearly explained everthing, a video tutorial may still be helpful.

[![IMAGE ALT TEXT HERE](https://github.com/jetlinkio/jetlink-android-sdk/blob/master/app/src/main/res/mipmap-hdpi/jetlink_Youtube_Video2.png)](https://www.youtube.com/watch?v=LmoE6XGCl0c)
