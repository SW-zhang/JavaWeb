package com.framework.security;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 登陆用户信息 随session存在而存在
 * 当用户登陆成功之后需要将本对象放入session中  key 为 SESSION_KEY
 */
public class SecurityContext implements Serializable {

    public static final String SESSION_KEY = "__com.framework.security.SecurityContext";

    public SecurityContext() {
    }

    private Map<String, Object> userObject;

    public void put(String key, Object value) {
        if (userObject == null) {
            userObject = new HashMap<String, Object>();
        }
        userObject.put(key, value);
    }

    public Object remove(String key) {
        if (userObject == null)
            return null;
        return userObject.remove(key);
    }

    public Object get(String key) {
        if (userObject == null) {
            return null;
        }
        return userObject.get(key);
    }

}
