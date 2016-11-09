

## JetLink Android SDK
[![Twitter](https://jetlink.io/Assets/images/apple-touch-icon-precomposed.png)](https://twitter.com/jetlink_io)
### [Official Website](https://jetlink.io/)

###Instant customer service and commerce with messaging

##### Jetlink is the easiest way to connect with your customers for selling and support

##### Quick Steps

App Module gradle file **(app/build.gradle)**
```
dependencies {
    compile 'com.veslabs.jetlink:jetlinklibrary:1.0.0'
}
```

That's All! JetLink is read to go! Now, ou have a messaging module in your app.

Congratulations!!!



----------------------------------------------------------------------------------------


## HOW TO USE JETLINK

### 1. Add dependencies to our app module's Gradle file. (project/app/build.gradle)

```
dependencies {
    compile 'com.veslabs.jetlink:jetlinklibrary:1.0.0'
}
```




### 2. Initialize JetLink

Add the following to your app's launcher activity’s onCreate() method. Please ensure JetLink.init() is invoked before you use any feature of JetLink SDK. 
Don't forget to replace the <YOUR-APP-ID> and <YOUR-APP-KEY> in the following code snippet with the actual app ID and app key.   

```
	JetlinkConfig jetlinkConfig = new JetlinkConfig("android-developer-8fe2a96b-581c53a1", "c636d12159eb47e5aa6972cc3417249a");
    jetlinkConfig.setJetLinkUIProperties(uiProperties);
    JetLinkApp.getInstance(getApplicationContext()).init(jetlinkConfig);
```


### 3. Initialize User

You can send basic user information at the beginning to give you more context on the user when your support agents are messaging back and forth with them.         
```
	JetLinkUser user = new JetLinkUser();
	user.setEmail("sena.yener@veslabs.com");
	user.setName("Sena");
	user.setSurname("Yener");
	JetLinkApp.getInstance(getApplicationContext()).setUser(user);
```


### 4. Call the Chat Activity

Just call JetLinkChatActivity and it will open a ready-to-use chat screen;

```
Intent intent = new Intent(MainActivity.this, JetLinkChatActivity.class);
startActivity(intent);
```