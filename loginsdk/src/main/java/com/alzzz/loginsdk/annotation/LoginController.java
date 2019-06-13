package com.alzzz.loginsdk.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description 没有真正实际的意义，只是用于标记
 *
 * @Date 2019-06-13
 * @Author sz
 */

@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER})
public @interface LoginController {
}
