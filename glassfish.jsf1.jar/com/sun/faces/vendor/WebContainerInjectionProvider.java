package com.sun.faces.vendor;

import com.sun.faces.spi.InjectionProvider;
import com.sun.faces.spi.InjectionProviderException;
import com.sun.faces.util.FacesLogger;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class WebContainerInjectionProvider implements InjectionProvider {
   private static final Logger LOGGER;

   public void inject(Object managedBean) throws InjectionProviderException {
   }

   public void invokePreDestroy(Object managedBean) throws InjectionProviderException {
      if (managedBean != null) {
         invokeAnnotatedMethod(getAnnotatedMethod(managedBean, PreDestroy.class), managedBean);
      }

   }

   public void invokePostConstruct(Object managedBean) throws InjectionProviderException {
      if (managedBean != null) {
         invokeAnnotatedMethod(getAnnotatedMethod(managedBean, PostConstruct.class), managedBean);
      }

   }

   private static void invokeAnnotatedMethod(Method method, Object managedBean) throws InjectionProviderException {
      if (method != null) {
         boolean accessible = method.isAccessible();
         method.setAccessible(true);

         try {
            method.invoke(managedBean);
         } catch (Exception var7) {
            throw new InjectionProviderException(var7.getMessage(), var7);
         } finally {
            method.setAccessible(accessible);
         }
      }

   }

   private static Method getAnnotatedMethod(Object managedBean, Class annotation) {
      for(Class clazz = managedBean.getClass(); !Object.class.equals(clazz); clazz = clazz.getSuperclass()) {
         Method[] methods = clazz.getDeclaredMethods();
         Method[] arr$ = methods;
         int len$ = methods.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            Method method = arr$[i$];
            if (method.isAnnotationPresent(annotation)) {
               if (Modifier.isStatic(method.getModifiers())) {
                  if (LOGGER.isLoggable(Level.WARNING)) {
                     LOGGER.log(Level.WARNING, "jsf.core.web.injection.method_not_static", new Object[]{method.toString(), annotation.getName()});
                  }
               } else if (!Void.TYPE.equals(method.getReturnType())) {
                  if (LOGGER.isLoggable(Level.WARNING)) {
                     LOGGER.log(Level.WARNING, "jsf.core.web.injection.method_return_not_void", new Object[]{method.toString(), annotation.getName()});
                  }
               } else if (method.getParameterTypes().length != 0) {
                  if (LOGGER.isLoggable(Level.WARNING)) {
                     LOGGER.log(Level.WARNING, "jsf.core.web.injection.method_no_params", new Object[]{method.toString(), annotation.getName()});
                  }
               } else {
                  Class[] exceptions = method.getExceptionTypes();
                  if (method.getExceptionTypes().length == 0) {
                     return method;
                  }

                  boolean hasChecked = false;
                  Class[] arr$ = exceptions;
                  int len$ = exceptions.length;

                  for(int i$ = 0; i$ < len$; ++i$) {
                     Class excClass = arr$[i$];
                     if (!RuntimeException.class.isAssignableFrom(excClass)) {
                        hasChecked = true;
                        break;
                     }
                  }

                  if (!hasChecked) {
                     return method;
                  }

                  if (LOGGER.isLoggable(Level.WARNING)) {
                     LOGGER.log(Level.WARNING, "jsf.core.web.injection.method_no_checked_exceptions", new Object[]{method.toString(), annotation.getName()});
                  }
               }
            }
         }
      }

      return null;
   }

   static {
      LOGGER = FacesLogger.APPLICATION.getLogger();
   }
}
