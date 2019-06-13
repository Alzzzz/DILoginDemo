package com.alzzz.loginsdk.common;

import com.alzzz.loginsdk.CommonLoginActivity;

/**
 * @Description CommonLoginController
 * @Date 2019-06-11
 * @Author sz
 */
public class CommonLoginController implements ILoginController {
    CommonLoginActivity activity;

    public CommonLoginController(CommonLoginActivity activity) {
        this.activity = activity;
    }

    @Override
    public boolean login(String account, String password) {
        if ("111".equals(account) && "222".equals(password)){
            activity.onLoginSuccessed();
        } else {
            activity.onLoginFailed();
        }
        return false;
    }
}
