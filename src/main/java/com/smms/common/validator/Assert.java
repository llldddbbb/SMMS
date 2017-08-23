package com.smms.common.validator;

import com.alibaba.druid.util.StringUtils;
import com.smms.common.exception.MyException;

/**
 * 数据校验
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-23 15:50
 */
public abstract class Assert {

    public static void isBlank(String str, String message) {
        if (StringUtils.isEmpty(str)) {
            throw new MyException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new MyException(message);
        }
    }
}
