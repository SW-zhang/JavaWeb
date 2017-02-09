package com.framework.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.core.ResolvableType;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Locale;
import java.util.Map;

public class ContextHolder {

    private static final ContextHolder instance = new ContextHolder();

    private ContextHolder() {
    }

    private ServletContext servletContext;

    private ApplicationContext applicationContext;

    private ApplicationContext wrapedApplicationContext = new ApplicationContextWrapper();

    private static ThreadLocal<HttpServletRequest> servletRequestHolder = new ThreadLocal<HttpServletRequest>();

    public static ServletContext getServletContext() {
        return instance.servletContext;
    }

    public static ApplicationContext getApplicationContext() {
        return instance.wrapedApplicationContext;
    }

    public static ContextHolder getInstance() {
        return instance;
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public static void setHttpServletRequest(HttpServletRequest request) {
        servletRequestHolder.set(request);
    }

    public static void clearHttpServletRequest() {
        servletRequestHolder.set(null);
    }

    public static HttpServletRequest getCurrentRequest() {
        return servletRequestHolder.get();
    }

    public static <T> T getBean(Class<T> clazz) {
        return instance.applicationContext.getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> requiredType) {
        return instance.applicationContext.getBean(name, requiredType);
    }

    public static Object getBean(String name) {
        return instance.applicationContext.getBean(name);
    }

    /**
     * @see ApplicationContext
     */
    private class ApplicationContextWrapper implements ApplicationContext {

        /**
         * @see org.springframework.context.ApplicationEventPublisher#publishEvent(ApplicationEvent)
         */
        @Override
        public void publishEvent(ApplicationEvent event) {
            applicationContext.publishEvent(event);
        }

        @Override
        public void publishEvent(Object event) {

        }

        /**
         * @see org.springframework.beans.factory.HierarchicalBeanFactory#getParentBeanFactory()
         */
        @Override
        public BeanFactory getParentBeanFactory() {
            return applicationContext.getParentBeanFactory();
        }

        /**
         * @see org.springframework.beans.factory.HierarchicalBeanFactory#containsLocalBean(String)
         */
        @Override
        public boolean containsLocalBean(String name) {
            return applicationContext.containsLocalBean(name);
        }

        /**
         * @see org.springframework.context.MessageSource#getMessage(String, Object[], String, Locale)
         */
        @Override
        public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
            return applicationContext.getMessage(code, args, defaultMessage, locale);
        }

        /**
         * @see org.springframework.context.MessageSource#getMessage(String, Object[], Locale)
         */
        @Override
        public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
            return applicationContext.getMessage(code, args, locale);
        }

        /**
         * @see org.springframework.beans.factory.ListableBeanFactory#containsBeanDefinition(String)
         */
        @Override
        public boolean containsBeanDefinition(String beanName) {
            return applicationContext.containsBeanDefinition(beanName);
        }

        /**
         * @see org.springframework.context.MessageSource#getMessage(MessageSourceResolvable, Locale)
         */
        @Override
        public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
            return applicationContext.getMessage(resolvable, locale);
        }

        /**
         * @see org.springframework.beans.factory.ListableBeanFactory#getBeanDefinitionCount()
         */
        @Override
        public int getBeanDefinitionCount() {
            return applicationContext.getBeanDefinitionCount();
        }

        /**
         * @see ApplicationContext#getParent()
         */
        @Override
        public ApplicationContext getParent() {
            return applicationContext.getParent();
        }

        /**
         * @see org.springframework.beans.factory.ListableBeanFactory#getBeanDefinitionNames()
         */
        @Override
        public String[] getBeanDefinitionNames() {
            return applicationContext.getBeanDefinitionNames();
        }

        @Override
        public String[] getBeanNamesForType(ResolvableType type) {
            return new String[0];
        }

        /**
         * @see ApplicationContext#getAutowireCapableBeanFactory()
         */
        @Override
        public AutowireCapableBeanFactory getAutowireCapableBeanFactory() throws IllegalStateException {
            return applicationContext.getAutowireCapableBeanFactory();
        }

        /**
         * @see org.springframework.beans.factory.ListableBeanFactory#getBeanNamesForType(Class)
         */
        @SuppressWarnings("rawtypes")
        @Override
        public String[] getBeanNamesForType(Class type) {
            return applicationContext.getBeanNamesForType(type);
        }

        /**
         * @see org.springframework.beans.factory.ListableBeanFactory#getBeanNamesForType(Class, boolean, boolean)
         */
        @SuppressWarnings("rawtypes")
        @Override
        public String[] getBeanNamesForType(Class type, boolean includeNonSingletons, boolean allowEagerInit) {
            return applicationContext.getBeanNamesForType(type, includeNonSingletons, allowEagerInit);
        }

        /**
         * @see BeanFactory#getBean(String)
         */
        @Override
        public Object getBean(String name) throws BeansException {
            return applicationContext.getBean(name);
        }

        /**
         * @see BeanFactory#getBean(String, Class)
         */
        @Override
        public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
            return applicationContext.getBean(name, requiredType);
        }

        /**
         * @see org.springframework.beans.factory.ListableBeanFactory#getBeansOfType(Class)
         */
        @Override
        public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
            return applicationContext.getBeansOfType(type);
        }

        /**
         * @see BeanFactory#getBean(Class)
         */
        @Override
        public <T> T getBean(Class<T> requiredType) throws BeansException {
            return applicationContext.getBean(requiredType);
        }

        /**
         * @see BeanFactory#getBean(String, Object[])
         */
        @Override
        public Object getBean(String name, Object... args) throws BeansException {
            return applicationContext.getBean(name, args);
        }

        @Override
        public <T> T getBean(Class<T> requiredType, Object... args) throws BeansException {
            return null;
        }

        /**
         * @see org.springframework.beans.factory.ListableBeanFactory#getBeansOfType(Class, boolean, boolean)
         */
        @Override
        public <T> Map<String, T> getBeansOfType(Class<T> type, boolean includeNonSingletons, boolean allowEagerInit) throws BeansException {
            return applicationContext.getBeansOfType(type, includeNonSingletons, allowEagerInit);
        }

        @Override
        public String[] getBeanNamesForAnnotation(Class<? extends Annotation> annotationType) {
            return new String[0];
        }

        /**
         * @see BeanFactory#containsBean(String)
         */
        @Override
        public boolean containsBean(String name) {
            return applicationContext.containsBean(name);
        }

        /**
         * @see org.springframework.beans.factory.ListableBeanFactory#getBeansWithAnnotation(Class)
         */
        @Override
        public Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) throws BeansException {
            return applicationContext.getBeansWithAnnotation(annotationType);
        }

        /**
         * @see org.springframework.beans.factory.ListableBeanFactory#findAnnotationOnBean(String, Class)
         */
        @Override
        public <A extends Annotation> A findAnnotationOnBean(String beanName, Class<A> annotationType) {
            return applicationContext.findAnnotationOnBean(beanName, annotationType);
        }

        /**
         * @see BeanFactory#getAliases(String)
         */
        @Override
        public String[] getAliases(String name) {
            return applicationContext.getAliases(name);
        }

        /**
         * @see org.springframework.core.io.ResourceLoader#getClassLoader()
         */
        @Override
        public ClassLoader getClassLoader() {
            return applicationContext.getClassLoader();
        }

        /**
         * @see ApplicationContext#getId()
         */
        @Override
        public String getId() {
            return applicationContext.getId();
        }

        @Override
        public String getApplicationName() {
            return null;
        }

        /**
         * @see ApplicationContext#getDisplayName()
         */
        @Override
        public String getDisplayName() {
            return applicationContext.getDisplayName();
        }

        /**
         * @see org.springframework.core.io.ResourceLoader#getResource(String)
         */
        @Override
        public Resource getResource(String arg0) {
            return applicationContext.getResource(arg0);
        }

        /**
         * @see org.springframework.core.io.support.ResourcePatternResolver#getResources(String)
         */
        @Override
        public Resource[] getResources(String arg0) throws IOException {
            return applicationContext.getResources(arg0);
        }

        /**
         * @see ApplicationContext#getStartupDate()
         */
        @Override
        public long getStartupDate() {
            return applicationContext.getStartupDate();
        }

        /**
         * @see BeanFactory#isSingleton(String)
         */
        @Override
        public boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
            return applicationContext.isSingleton(name);
        }

        /**
         * @see BeanFactory#isPrototype(String)
         */
        @Override
        public boolean isPrototype(String name) throws NoSuchBeanDefinitionException {
            return applicationContext.isPrototype(name);
        }

        @Override
        public boolean isTypeMatch(String name, ResolvableType typeToMatch) throws NoSuchBeanDefinitionException {
            return false;
        }

        /**
         * @see BeanFactory#isTypeMatch(String, Class)
         */
        @SuppressWarnings("rawtypes")
        @Override
        public boolean isTypeMatch(String name, Class targetType) throws NoSuchBeanDefinitionException {
            return applicationContext.isTypeMatch(name, targetType);
        }

        /**
         * @see BeanFactory#getType(String)
         */
        @Override
        public Class<?> getType(String name) throws NoSuchBeanDefinitionException {
            return applicationContext.getType(name);
        }

        @Override
        public Environment getEnvironment() {
            return null;
        }
    }
}
