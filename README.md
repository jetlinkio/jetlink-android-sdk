

## JetLink Android SDK
[![Website](https://app.jetlink.io/Assets/custom/img/jetlink_logo.png)](https://jetlink.io)
### [Official Website](https://jetlink.io/)

###Instant customer service and commerce with messaging

##### Jetlink is the easiest way to connect with your customers for selling and support

##### Quick Steps

App Module gradle file **(app/build.gradle)**
```
dependencies {
      compile('com.veslabs.jetlink:jetlinklibrary:1.0.39@aar') {
        transitive = true
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
        compile('com.veslabs.jetlink:jetlinklibrary:1.0.39@aar') {
        transitive = true
    }

}
```


And your minSdkVersion must at least be 18.


### 2. Initialize JetLink

Add the following to your app's launcher activity’s onCreate() method. Please ensure JetLink.init() is invoked before you use any feature of JetLink SDK. 
Don't forget to replace the <YOUR-APP-ID> and <YOUR-APP-KEY> in the following code snippet with the actual app ID and app key.   

```
	JetlinkConfig jetlinkConfig = new JetlinkConfig("<YOUR-APP-ID>", "<YOUR-APP-KEY>");


```


### 3. Initialize User

You can send basic user information at the beginning to give you more context on the user when your support agents are messaging back and forth with them.         
```
	JetLinkUser user = new JetLinkUser();
	user.setEmail("salman.khan@veslabs.com");
	user.setName("Salman");
	user.setSurname("Khan");
	JetLinkApp.getInstance(getApplicationContext()).setUser(user);
	JetLinkApp.getInstance(getApplicationContext()).init(jetlinkConfig);
```


### 4. Call the Chat Activity

Just call JetLinkChatActivity and it will open a ready-to-use chat screen;

```
Intent intent = new Intent(MainActivity.this, JetLinkChatActivity.class);
startActivity(intent);
```
