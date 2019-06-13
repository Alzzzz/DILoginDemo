package com.alzzz.dilogindemo.impl;

import android.content.Context;
import android.widget.Toast;

import com.alzzz.loginsdk.common.ILoginController;

/**
 * @Description InvokeLoginController
 * @Date 2019-06-13
 * @Author sz
 */
public class InvokeLoginController implements ILoginController {
    private Context context;

    public InvokeLoginController(Context context) {
        this.context = context;
    }

    @Override
    public boolean login(String account, String password) {
        if ("333".equalsIgnoreCase(account) 
                && "444".equalsIgnoreCase(password)){
            return true;
        }
        return false;
    }
}
