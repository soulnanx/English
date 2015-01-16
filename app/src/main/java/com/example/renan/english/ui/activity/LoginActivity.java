package com.example.renan.english.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.renan.english.R;
import com.example.renan.english.app.App;
import com.example.renan.english.entity.Note;
import com.example.renan.english.entity.Phrase;
import com.example.renan.english.entity.User;
import com.example.renan.english.util.NavigationUtil;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends Activity {

    private UIHelper ui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        NavigationUtil.navigate(LoginActivity.this, DrawerLayoutMain.class);
        finish();

//        if (ParseUser.getCurrentUser() != null) {
//        } else {
//            ui = new UIHelper();
//            setEvents();
//        }
    }

    private void setEvents() {
        ui.btnLogin.setOnClickListener(onClickBtnLogin());
    }

    private View.OnClickListener onClickBtnLogin() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ui.progressFB.setVisibility(ProgressBar.VISIBLE);
                ParseFacebookUtils.logIn(LoginActivity.this, callBackLoginFacebook());
            }
        };
    }

    private LogInCallback callBackLoginFacebook() {
        return  new LogInCallback() {

            @Override
            public void done(ParseUser user, ParseException err) {
                ui.progressFB.setVisibility(ProgressBar.GONE);

                if (user == null) {
                    Toast.makeText(LoginActivity.this, "nao logou", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (user.isNew()) {
                    Toast.makeText(LoginActivity.this, "logou primeira vez", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "logou de novo", Toast.LENGTH_SHORT).show();
                }

                NavigationUtil.navigate(LoginActivity.this, DrawerLayoutMain.class);
                makeMeRequest(ParseFacebookUtils.getSession());
                finish();
            }

        };
    }

    private void makeMeRequest(final Session session) {
        Request request = Request.newMeRequest(session,
                new Request.GraphUserCallback() {
                    @Override
                    public void onCompleted(GraphUser graphUser, Response response) {
                        // If the response is successful
                        if (session == Session.getActiveSession()) {
                            if (graphUser != null) {
                                User user = (User) ParseUser.getCurrentUser();
                                user.setEmail(null != graphUser.getUsername() ? graphUser.getUsername() : "");
                                user.setName(null != graphUser.getName() ? graphUser.getName() : "");

                                user.saveEventually();
                            }
                        }
                    }
                });
        request.executeAsync();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
    }

    private class UIHelper {
        View btnLogin;
        ProgressBar progressFB;

        public UIHelper (){
            this.btnLogin = findViewById(R.id.btn_login);
            this.progressFB = (ProgressBar) findViewById(R.id.progressFB);
        }

    }

}
