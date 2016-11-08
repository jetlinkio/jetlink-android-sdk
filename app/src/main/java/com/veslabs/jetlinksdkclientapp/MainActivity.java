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

    private Button btnJetlink;
    private Button btnFaq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JetlinkConfig jetlinkConfig = new JetlinkConfig("android-developer-8fe2a96b-581c53a1", "c636d12159eb47e5aa6972cc3417249a");
        JetLinkUIProperties uiProperties = new JetLinkUIProperties();
        uiProperties.setBackgroundColor(Color.MAGENTA);
        jetlinkConfig.setJetLinkUIProperties(uiProperties);
        JetLinkApp.getInstance(getApplicationContext()).init(jetlinkConfig);

        JetLinkUser user = new JetLinkUser();
        user.setEmail("sena.yener@veslabs.com");
        user.setName("Sena");
        user.setSurname("Yener");
        JetLinkApp.getInstance(getApplicationContext()).setUser(user);

        btnJetlink = (Button) findViewById(R.id.btnJetlink);
        btnJetlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, JetLinkChatActivity.class);
                startActivity(intent);
            }
        });

        btnFaq = (Button) findViewById(R.id.btnFaq);
        btnFaq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FaqActivity.class);
                startActivity(intent);
            }
        });
    }
}
