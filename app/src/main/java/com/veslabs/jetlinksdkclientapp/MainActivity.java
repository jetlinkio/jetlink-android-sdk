package com.veslabs.jetlinksdkclientapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.veslabs.jetlinklibrary.JetLinkApp;
import com.veslabs.jetlinklibrary.config.JetLinkUIProperties;
import com.veslabs.jetlinklibrary.config.JetLinkUser;
import com.veslabs.jetlinklibrary.config.JetlinkConfig;
import com.veslabs.jetlinklibrary.faq.FaqActivity;
import com.veslabs.jetlinklibrary.messaging.JetLinkChatActivity;


public class MainActivity extends AppCompatActivity {

    private Button btnMessaging;
    private Button btnFaq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JetlinkConfig jetlinkConfig = new JetlinkConfig("<YOUR-APP-ID>", "<YOUR-APP-KEY>");
        JetLinkUIProperties uiProperties = new JetLinkUIProperties();
        jetlinkConfig.setJetLinkUIProperties(uiProperties);


        JetLinkUser user = new JetLinkUser();
        user.setEmail("salman.khan@jetlink.com");
        user.setName("Salman");
        user.setSurname("Khan");
        JetLinkApp.getInstance(getApplicationContext()).setUser(user);
        JetLinkApp.getInstance(getApplicationContext()).init(jetlinkConfig);


        btnMessaging= (Button) findViewById(R.id.btnJetLink);
        btnMessaging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, JetLinkChatActivity.class);
                startActivity(intent);

            }
        });

        btnFaq= (Button) findViewById(R.id.btnFaq);
        btnFaq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FaqActivity.class);
                startActivity(intent);

            }
        });

    }
}
