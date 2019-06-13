package com.alzzz.dilogindemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.alzzz.dilogindemo.action.LoginService;
import com.alzzz.loginsdk.CommonLoginActivity;
import com.alzzz.loginsdk.InvokeLoginActivity;
import com.alzzz.loginsdk.SLLoginActivity;
import com.alzzz.loginsdk.register.LoginRegister;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoginRegister.bind(LoginService.class);
    }

    public void jumpIntoDILogin(View view) {
    }

    public void jumpIntoSLLogin(View view) {
        Intent intent = new Intent();
        intent.setClass(this, SLLoginActivity.class);
        startActivity(intent);
    }

    public void jumpIntoInvokeLogin(View view) {
        Intent intent = new Intent();
        intent.setClass(this, InvokeLoginActivity.class);
        startActivity(intent);
    }

    public void jumpIntoCommonLogin(View view) {
        Intent intent = new Intent();
        intent.setClass(this, CommonLoginActivity.class);
        startActivity(intent);
    }
}
