package com.alzzz.dilogindemo.action;

import android.content.Context;
import android.widget.Toast;

import com.alzzz.loginsdk.common.ILoginController;

public class SLLoginController implements ILoginController {
    private Context mContext;

    public SLLoginController(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public boolean login(String account, String password) {
        if ("555".equalsIgnoreCase(account) && "666".equalsIgnoreCase(password)){
            return true;
        }
        return false;
    }
}
