package com.alzzz.loginsdk;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alzzz.loginsdk.common.CommonLoginController;
import com.alzzz.loginsdk.common.ILoginController;

/**
 * @Description LoginActivity
 * @Date 2019-06-10
 * @Author sz
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText accountEt;
    EditText passwordEt;
    Button loginBtn;
    ILoginController loginController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        accountEt = findViewById(R.id.et_account);
        passwordEt = findViewById(R.id.et_password);
        loginBtn = findViewById(R.id.btn_login);
        loginBtn.setOnClickListener(this);
        initController();
    }

    private void initController() {
        loginController = new CommonLoginController(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_login) {
            loginController.login(accountEt.getText().toString(),
                    passwordEt.getText().toString());
        }
    }
    
    public void onLoginSuccessed(){
        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
    }

    public void onLoginFailed(){
        Toast.makeText(this, "登录失败", Toast.LENGTH_SHORT).show();
    }
}
