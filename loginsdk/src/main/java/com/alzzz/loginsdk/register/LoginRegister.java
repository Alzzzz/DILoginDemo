package com.alzzz.loginsdk.register;

import android.content.Context;

import com.alzzz.loginsdk.annotation.Action;
import com.alzzz.loginsdk.annotation.LoginController;
import com.alzzz.loginsdk.common.ILoginController;

import java.lang.ref.SoftReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description LoginRegister
 * @Date 2019-06-13
 * @Author sz
 */
public class LoginRegister {
    private static Class targetController;
    private static Map<String, SoftReference<Method>> sCachedAction = new HashMap<>();

    public static void bind(@LoginController Class clazz) {
        targetController = clazz;
    }

    public static void executeTargetAction(String action) {
        try {
            //根据action找到对应的方法
            Class clazz = targetController;
            if (clazz == null) {
                return;
            }

            Constructor<ILoginController> constructor =
                    targetController.getConstructor(null);
            Object obj = constructor.newInstance();

            Method actionMethod = getCachedMethod(action, clazz);
            if (actionMethod != null) {
                actionMethod.setAccessible(true);
                actionMethod.invoke(obj);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    private static Method getCachedMethod(String action, Class clz) {
        Method actionMethod = null;
        if (sCachedAction != null) {
            SoftReference<Method> reference = sCachedAction.get(action);
            actionMethod = reference != null ? reference.get() : null;
        }
        if (actionMethod == null) {
            for (Method method : clz.getDeclaredMethods()) {
                Action annotation = method.getAnnotation(Action.class);
                if (annotation == null) {
                    continue;
                }
                String methodAction = annotation.action();
                if (action.equalsIgnoreCase(methodAction)) {
                    if (sCachedAction == null) {
                        sCachedAction = new HashMap<>();
                    }
                    sCachedAction.put(action, new SoftReference<Method>(method));
                    actionMethod = method;
                    break;
                }
            }
        }
        return actionMethod;
    }
}
