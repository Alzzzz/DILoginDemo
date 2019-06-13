package com.alzzz.dilogindemo.action;

import android.content.Context;
import android.widget.Toast;

import com.alzzz.loginsdk.annotation.Action;
import com.alzzz.loginsdk.annotation.LoginController;
import com.alzzz.loginsdk.common.ILoginController;

/**
 * @Description LoginService
 * @Date 2019-06-13
 * @Author sz
 */
@LoginController
public class LoginService {
    private Context mContext;
    public LoginService(Context mContext) {
        this.mContext = mContext;
    }

    @Action(action = "doLogin")
    public ILoginController outterDoLogin(){
        return new SLLoginController(mContext);
    }
}
