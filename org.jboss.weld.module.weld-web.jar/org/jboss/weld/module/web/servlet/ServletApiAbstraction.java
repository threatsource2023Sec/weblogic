package org.jboss.weld.module.web.servlet;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.servlet.ServletRequest;
import org.jboss.weld.bootstrap.api.Service;
import org.jboss.weld.module.web.logging.ServletLogger;
import org.jboss.weld.resources.spi.ResourceLoader;
import org.jboss.weld.util.ApiAbstraction;
import org.jboss.weld.util.reflection.Reflections;

public class ServletApiAbstraction extends ApiAbstraction implements Service {
   public static final String SERVLET_CONTEXT_CLASS_NAME = "javax.servlet.ServletContext";
   private static final String ASYNC_LISTENER_CONTEXT_CLASS_NAME = "javax.servlet.AsyncListener";
   private static final String SERVLET_REQUEST_CLASS_NAME = "javax.servlet.ServletRequest";
   private static final String IS_ASYNC_STARTED_METHOD_NAME = "isAsyncStarted";
   private final boolean asyncSupported;
   private final Method isAsyncStartedMethod;

   public ServletApiAbstraction(ResourceLoader resourceLoader) {
      super(resourceLoader);
      this.asyncSupported = Reflections.isClassLoadable("javax.servlet.AsyncListener", resourceLoader);
      Method isAsyncStartedMethodLocal = null;
      Class servletRequestClass = Reflections.loadClass("javax.servlet.ServletRequest", resourceLoader);
      if (servletRequestClass != null) {
         try {
            isAsyncStartedMethodLocal = servletRequestClass.getMethod("isAsyncStarted");
         } catch (NoSuchMethodException var5) {
            ServletLogger.LOG.servlet2Environment();
         }
      }

      this.isAsyncStartedMethod = isAsyncStartedMethodLocal;
   }

   public boolean isAsyncSupported() {
      return this.asyncSupported;
   }

   public boolean isAsyncStarted(ServletRequest request) {
      if (this.isAsyncStartedMethod != null) {
         try {
            return (Boolean)this.isAsyncStartedMethod.invoke(request);
         } catch (IllegalAccessException var3) {
            ServletLogger.LOG.error(var3);
            return false;
         } catch (InvocationTargetException var4) {
            ServletLogger.LOG.error(var4);
            return false;
         }
      } else {
         return false;
      }
   }

   public void cleanup() {
   }
}
