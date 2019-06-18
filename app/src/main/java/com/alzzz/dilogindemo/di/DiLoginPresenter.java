package com.alzzz.dilogindemo.di;

import android.content.Context;

import com.alzzz.dilogindemo.action.SLLoginController;
import com.alzzz.loginsdk.annotation.Action;
import com.alzzz.loginsdk.annotation.Inject;
import com.alzzz.loginsdk.common.ILoginController;

/**
 * @Description DiLoginPresenter
 * @Date 2019-06-18
 * @Author sz
 */
public class DiLoginPresenter {
    private Context mContext;
    @Inject
    public DiLoginPresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Action(action = "doLogin")
    public ILoginController outterDoLogin(){
        return new SLLoginController(mContext);
    }
}
