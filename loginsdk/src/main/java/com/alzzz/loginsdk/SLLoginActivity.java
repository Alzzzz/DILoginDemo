package com.alzzz.loginsdk;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alzzz.loginsdk.common.ILoginController;
import com.alzzz.loginsdk.register.LoginRegister;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @Description LoginActivity
 * @Date 2019-06-10
 * @Author sz
 */
public class SLLoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = SLLoginActivity.class.getSimpleName();
    EditText accountEt;
    EditText passwordEt;
    Button loginBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login_sl);
        accountEt = findViewById(R.id.et_account);
        passwordEt = findViewById(R.id.et_password);
        loginBtn = findViewById(R.id.btn_login);
        loginBtn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_login) {
            ILoginController iLoginController = LoginRegister.executeTargetAction(this, "doLogin");
            if (iLoginController == null){
                Log.d(TAG, "外部没有注册相关处理逻辑");
                return;
            }
            if (iLoginController.login(accountEt.getText().toString(),
                    passwordEt.getText().toString())){
                onLoginSuccessed();
            } else {
                onLoginFailed();
            }
        }
    }
    
    public void onLoginSuccessed(){
        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
    }

    public void onLoginFailed(){
        Toast.makeText(this, "登录失败", Toast.LENGTH_SHORT).show();
    }
}
