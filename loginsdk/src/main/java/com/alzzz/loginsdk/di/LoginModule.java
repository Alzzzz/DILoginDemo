package com.alzzz.loginsdk.di;

import android.content.Context;

import com.alzzz.loginsdk.annotation.Module;
import com.alzzz.loginsdk.annotation.Provider;

/**
 * @Description LoginModule
 * @Date 2019-06-18
 * @Author sz
 */
@Module
public class LoginModule {
    private Context context;

    public LoginModule(Context context) {
        this.context = context;
    }

    @Provider
    public Context getContext(){
        return context;
    }
}
