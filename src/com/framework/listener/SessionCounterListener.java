package com.framework.listener;

import com.framework.security.SecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.io.Serializable;

public class SessionCounterListener implements HttpSessionListener, HttpSessionAttributeListener, ServletContextListener {

    public static final String KEY_COUNTER = "__com.framework.listener.SessionCounterListener.Counter";

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected final boolean isDebugEnabled = logger.isDebugEnabled();

    protected final Counter counter = new Counter();

    private ServletContext servletContext;

    /**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    @Override
    public void sessionCreated(HttpSessionEvent event) {
        synchronized (counter) {
            counter.alive++;
        }
    }

    /**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        synchronized (counter) {
            counter.alive--;
            if (event.getSession().getAttribute(SecurityContext.SESSION_KEY) != null) {
                counter.online--;
            }
        }
    }

    /**
     * @see HttpSessionAttributeListener#attributeAdded(HttpSessionBindingEvent)
     */
    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        if (SecurityContext.SESSION_KEY.equals(event.getName())) {
            synchronized (counter) {
                counter.online++;
            }
        }
    }

    /**
     * @see HttpSessionAttributeListener#attributeRemoved(HttpSessionBindingEvent)
     */
    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
    }

    /**
     * @see HttpSessionAttributeListener#attributeReplaced(HttpSessionBindingEvent)
     */
    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {

    }

    /**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    @Override
    public void contextDestroyed(ServletContextEvent event) {

    }

    /**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    @Override
    public void contextInitialized(ServletContextEvent event) {
        servletContext = event.getServletContext();
        servletContext.setAttribute(KEY_COUNTER, counter);
    }

    public class Counter implements Serializable {
        public long alive;
        public long online;
    }

}
