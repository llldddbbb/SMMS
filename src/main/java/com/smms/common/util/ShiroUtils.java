package com.smms.common.util;

import com.smms.common.exception.MyException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.util.StringUtils;


/**
 * Shiro工具类
 */
public class ShiroUtils {

    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static void setSessionAttribute(Object key, Object value) {
        getSession().setAttribute(key, value);
    }

    public static Object getSessionAttribute(Object key) {
        return getSession().getAttribute(key);
    }

    public static String getKaptcha(String key) {
        Object kaptcha = getSessionAttribute(key);
        if (StringUtils.isEmpty(kaptcha)) {
            throw new MyException("验证码已失效");
        }
        //一次请求就清空一次验证码
        getSession().removeAttribute(key);
        return kaptcha.toString();
    }
}
