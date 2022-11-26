package com.sun.faces.vendor;

import com.sun.faces.spi.InjectionProvider;
import com.sun.faces.spi.InjectionProviderException;
import com.sun.faces.util.FacesLogger;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class WebContainerInjectionProvider implements InjectionProvider {
   private static final Logger LOGGER;
   private static Map methodsPerClazz;

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
         } catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException var7) {
            throw new InjectionProviderException(var7.getMessage(), var7);
         } finally {
            method.setAccessible(accessible);
         }
      }

   }

   private static Method getAnnotatedMethod(Object managedBean, Class annotation) {
      for(Class clazz = managedBean.getClass(); !Object.class.equals(clazz); clazz = clazz.getSuperclass()) {
         ConcurrentHashMap methodsMap = (ConcurrentHashMap)methodsPerClazz.get(clazz);
         if (methodsMap == null) {
            ConcurrentHashMap newMethodsMap = new ConcurrentHashMap();
            methodsMap = (ConcurrentHashMap)methodsPerClazz.putIfAbsent(clazz, newMethodsMap);
            if (methodsMap == null) {
               methodsMap = newMethodsMap;
            }
         }

         MethodHolder methodHolder = (MethodHolder)methodsMap.get(annotation);
         if (methodHolder == null) {
            Method[] methods = clazz.getDeclaredMethods();
            Method method = getAnnotatedMethodForMethodArr(methods, annotation);
            MethodHolder newMethodHolder = new MethodHolder(method);
            methodHolder = (MethodHolder)methodsMap.putIfAbsent(annotation, newMethodHolder);
            if (methodHolder == null) {
               methodHolder = newMethodHolder;
            }
         }

         if (methodHolder.getMethod() != null) {
            return methodHolder.getMethod();
         }
      }

      return null;
   }

   private static Method getAnnotatedMethodForMethodArr(Method[] methods, Class annotation) {
      Method[] var2 = methods;
      int var3 = methods.length;
      int var4 = 0;

      Method method;
      while(true) {
         if (var4 >= var3) {
            return null;
         }

         method = var2[var4];
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
                  break;
               }

               boolean hasChecked = false;
               Class[] var8 = exceptions;
               int var9 = exceptions.length;

               for(int var10 = 0; var10 < var9; ++var10) {
                  Class excClass = var8[var10];
                  if (!RuntimeException.class.isAssignableFrom(excClass)) {
                     hasChecked = true;
                     break;
                  }
               }

               if (!hasChecked) {
                  break;
               }

               if (LOGGER.isLoggable(Level.WARNING)) {
                  LOGGER.log(Level.WARNING, "jsf.core.web.injection.method_no_checked_exceptions", new Object[]{method.toString(), annotation.getName()});
               }
            }
         }

         ++var4;
      }

      return method;
   }

   static {
      LOGGER = FacesLogger.APPLICATION.getLogger();
      methodsPerClazz = new ConcurrentHashMap();
   }

   private static class MethodHolder {
      private final Method method;

      public MethodHolder(Method method) {
         this.method = method;
      }

      public Method getMethod() {
         return this.method;
      }
   }
}
