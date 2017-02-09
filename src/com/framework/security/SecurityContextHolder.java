package com.framework.security;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * 当前线程共享对象
 */
public class SecurityContextHolder {

    private static final ThreadLocal<SecurityContext> holder = new ThreadLocal<SecurityContext>();

    public static SecurityContext getSecurityContext() {
        return holder.get();
    }

    public static void setSecurityContext(SecurityContext context) {
        if (context != null) {
            holder.set(context);
        } else {
            holder.remove();
        }
    }

    public static void remove() {
        holder.remove();
    }

    /**
     * KEY: cstNo, VALUE:HttpSession
     */
    public static Map<String, HttpSession> sessionRegister = new WeakHashMap<String, HttpSession>();
    public static Map<String, String> sessionCstNoMap = new HashMap<String, String>();
}
