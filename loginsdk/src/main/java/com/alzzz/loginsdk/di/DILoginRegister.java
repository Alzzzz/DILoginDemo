package com.alzzz.loginsdk.di;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.alzzz.loginsdk.annotation.Action;
import com.alzzz.loginsdk.annotation.Inject;
import com.alzzz.loginsdk.annotation.LoginController;
import com.alzzz.loginsdk.annotation.Module;
import com.alzzz.loginsdk.annotation.Provider;
import com.alzzz.loginsdk.common.ILoginController;

import java.lang.annotation.Annotation;
import java.lang.ref.SoftReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description LoginRegister
 * @Date 2019-06-13
 * @Author sz
 */
public class DILoginRegister {
    private static final String TAG = DILoginRegister.class.getSimpleName();
    private static Class targetController;
    private static Map<String, SoftReference<Method>> sCachedAction = new HashMap<>();
    private static Map<Class, SoftReference<Method>> mModuleMethodMap = new HashMap<>();
    private static Map<Class, SoftReference<Object>> mInjectedObjs = new HashMap<>();
    private static Object module;

    public static void bind(@LoginController Class clazz) {
        targetController = clazz;
    }

    public static void initProvider(@Module Object module){
        DILoginRegister.module = module;
        Class clazz = module.getClass();
        for (Method method: clazz.getDeclaredMethods()){
            Provider provider = method.getAnnotation(Provider.class);
            if (provider != null){
                Class returnType = method.getReturnType();
                mModuleMethodMap.put(returnType, new SoftReference<>(method));
            }
        }

    }

    /**
     * 执行目标Action的Method
     *
     * @param mContext
     * @param action
     * @return
     */
    @Nullable
    public static ILoginController executeTargetAction(Context mContext, String action) {
        try {
            //根据action找到对应的方法
            Class clazz = targetController;
            if (clazz == null) {
                return null;
            }

            List<Constructor> constructors = getConstructorByAnnotation();
            Object obj = null;
            for (Constructor constructor: constructors){
                obj = setupConstructor(constructor);
                if (obj != null){
                    break;
                }
            }

            if (obj == null){
                Log.e(TAG, "constructor can not be initialized");
                return null;
            }

            Method actionMethod = getCachedMethod(action, clazz);
            if (actionMethod != null) {
                actionMethod.setAccessible(true);
                ILoginController iLoginController = (ILoginController) actionMethod.invoke(obj);
                return iLoginController;
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 构造对应Object
     *
     * @param constructor
     * @return
     */
    private static Object setupConstructor(Constructor constructor) {
        Class[] paramsClasses = constructor.getParameterTypes();
        Object[] paramsObj = new Object[paramsClasses.length];
        Object result = null;
        int index = 0;
        for (Class clazz: paramsClasses){
            if (mModuleMethodMap.get(clazz) != null){
                paramsObj[index] = getParamsInstance(clazz, mModuleMethodMap.get(clazz).get());
            }
        }

        try {
            result = constructor.newInstance(paramsObj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取参数的实例
     *
     * @param clazz
     * @param method
     * @param args
     * @return
     */
    private static Object getParamsInstance(Class clazz, Method method, Object... args) {
        if (mInjectedObjs == null){
            mInjectedObjs = new HashMap<>();
        } else {
            if (mInjectedObjs.get(clazz) != null && mInjectedObjs.get(clazz).get() !=null){
                Log.d(TAG, "Existing instance objects");
                return mInjectedObjs.get(clazz).get();
            }
        }

        Object object = null;
        if (method != null){
            try {
                object = method.invoke(module, args);
                mInjectedObjs.put(clazz, new SoftReference<>(object));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return object;
    }

    /**
     * 清空所有对象
     */
    public static void onDestory(){
        mInjectedObjs.clear();
        mInjectedObjs = null;
    }

    /**
     * 根据注解获取构造函数
     * @return
     */
    private static List<Constructor> getConstructorByAnnotation() {
        Constructor[] constructors =
                targetController.getDeclaredConstructors();
        List<Constructor> targetConstructors = new ArrayList<>();
        for (Constructor constructor: constructors){
            Annotation annotation = constructor.getAnnotation(Inject.class);
            if (annotation == null){
                continue;
            }
            if (annotation instanceof Inject){
                //当前constructor的注解就是Inject的
                targetConstructors.add(constructor);
            }
        }
        return targetConstructors;
    }

    /**
     * 在缓存中获取action和class对应的method
     *
     * @param action
     * @param clz
     * @return
     */
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
                if (action.equalsIgnoreCase(methodAction)
                        && method.getReturnType().isAssignableFrom(ILoginController.class)) {
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
