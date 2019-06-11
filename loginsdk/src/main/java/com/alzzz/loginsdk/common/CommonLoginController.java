package com.alzzz.loginsdk.common;

import com.alzzz.loginsdk.LoginActivity;

/**
 * @Description CommonLoginController
 * @Date 2019-06-11
 * @Author sz
 */
public class CommonLoginController implements ILoginController {
    LoginActivity activity;

    public CommonLoginController(LoginActivity activity) {
        this.activity = activity;
    }

    @Override
    public void login(String account, String password) {
        if ("111".equals(account) && "222".equals(password)){
            activity.onLoginSuccessed();
        } else {
            activity.onLoginFailed();
        }
    }
}
