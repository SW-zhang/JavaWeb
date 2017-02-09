package com.framework.listener;

import com.framework.context.ContextHolder;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.CachedIntrospectionResults;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContextEvent;
import java.beans.Introspector;

public class ContextLoaderListener extends org.springframework.web.context.ContextLoaderListener {
    private static final Logger log = LoggerFactory.getLogger(ContextLoaderListener.class);

    public ContextLoaderListener() {
    }

    public ContextLoaderListener(WebApplicationContext context) {
        super(context);
    }

    /**
     * Initialize the root web application context.
     */
    @Override
    public void contextInitialized(ServletContextEvent event) {

        log.info("Context Initializing... Startup Spring Context:");
        CachedIntrospectionResults.acceptClassLoader(Thread.currentThread().getContextClassLoader());
        super.contextInitialized(event);

        log.info("Spring Context Initialized. Initializing Application:");
        ContextHolder.getInstance().setServletContext(event.getServletContext());
        ContextHolder.getInstance().setApplicationContext(getCurrentWebApplicationContext());

        initialize();

        log.info("Application Initializing: Done.");

    }

    /**
     * Close the root web application context.
     */
    @Override
    public void contextDestroyed(ServletContextEvent event) {
        super.contextDestroyed(event);
        log.info("Context Destroying...");

        CachedIntrospectionResults.clearClassLoader(Thread.currentThread().getContextClassLoader());
        Introspector.flushCaches();

        destroy();

        log.info("Context Destroying: Done.");
        // Release Commons Logging Cache, (fix commons-logging classLoader memory leak)
        LogFactory.release(Thread.currentThread().getContextClassLoader());
        System.gc();
    }

    protected void initialize() {
    }

    protected void destroy() {
    }
}
