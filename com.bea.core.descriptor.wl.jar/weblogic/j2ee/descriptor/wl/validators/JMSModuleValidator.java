package weblogic.j2ee.descriptor.wl.validators;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import weblogic.j2ee.descriptor.wl.DestinationBean;
import weblogic.j2ee.descriptor.wl.DestinationKeyBean;
import weblogic.j2ee.descriptor.wl.JMSBean;

public class JMSModuleValidator {
   private static final Class cls;

   private JMSModuleValidator() {
   }

   public static void validateTimeToDeliverOverride(DestinationBean dbean) {
      try {
         Method method = cls.getMethod("validateTimeToDeliverOverride", DestinationBean.class);
         method.invoke((Object)null, dbean);
      } catch (Exception var2) {
         handleWithException(var2);
      }

   }

   public static void validateTimeToDeliverOverride(String ttlOverride) {
      try {
         Method method = cls.getMethod("validateTimeToDeliverOverride", String.class);
         method.invoke((Object)null, ttlOverride);
      } catch (Exception var2) {
         handleWithException(var2);
      }

   }

   public static void validateTopicSubscriptionMessagesLimit(long messagesLimit) {
      try {
         Method method = cls.getMethod("validateTopicSubscriptionMessagesLimit", Long.class);
         method.invoke((Object)null, messagesLimit);
      } catch (Exception var3) {
         handleWithException(var3);
      }

   }

   public static void validateDestinationKeyProperty(DestinationKeyBean dkb) throws IllegalArgumentException {
      try {
         Method method = cls.getMethod("validateDestinationKeyProperty", DestinationKeyBean.class);
         method.invoke((Object)null, dkb);
      } catch (Exception var2) {
         handleWithException(var2);
      }

   }

   public static void validateJMSModule(JMSBean module) throws IllegalArgumentException {
      try {
         Method method = cls.getMethod("validateJMSModule", JMSBean.class);
         method.invoke((Object)null, module);
      } catch (Exception var2) {
         handleWithException(var2);
      }

   }

   public static void validateCFName(String name) throws IllegalArgumentException {
      try {
         Method method = cls.getMethod("validateCFName", String.class);
         method.invoke((Object)null, name);
      } catch (Exception var2) {
         handleWithException(var2);
      }

   }

   public static void validateForeignServerInitialContextFactory(String initialContextFactory) throws IllegalArgumentException {
      try {
         Method method = cls.getMethod("validateForeignServerInitialContextFactory", String.class);
         method.invoke((Object)null, initialContextFactory);
      } catch (Exception var2) {
         handleWithException(var2);
      }

   }

   public static void validateMDBInitialContextFactory(String initialContextFactory) throws IllegalArgumentException {
      try {
         Method method = cls.getMethod("validateMDBInitialContextFactory", String.class);
         method.invoke((Object)null, initialContextFactory);
      } catch (Exception var2) {
         handleWithException(var2);
      }

   }

   public static void validateCFJNDIName(String jndiName) throws IllegalArgumentException {
      try {
         Method method = cls.getMethod("validateCFJNDIName", String.class);
         method.invoke((Object)null, jndiName);
      } catch (Exception var2) {
         handleWithException(var2);
      }

   }

   public static void validateMulticastAddress(String address) throws IllegalArgumentException {
      try {
         Method method = cls.getMethod("validateMulticastAddress", String.class);
         method.invoke((Object)null, address);
      } catch (Exception var2) {
         handleWithException(var2);
      }

   }

   public static void validateEntityName(String name) throws IllegalArgumentException {
      try {
         Method method = cls.getMethod("validateEntityName", String.class);
         method.invoke((Object)null, name);
      } catch (Exception var2) {
         handleWithException(var2);
      }

   }

   private static void handleWithException(Exception ex) {
      if (ex instanceof InvocationTargetException) {
         Throwable cause = ex.getCause();
         if (cause instanceof IllegalArgumentException) {
            throw (IllegalArgumentException)cause;
         } else {
            throw new RuntimeException(cause);
         }
      } else {
         throw new RuntimeException(ex);
      }
   }

   static {
      String className = "weblogic.jms.module.validators.JMSModuleValidator";

      try {
         cls = Class.forName(className);
      } catch (ClassNotFoundException var2) {
         throw new RuntimeException("Cannot load class [" + className + "].", var2);
      }
   }
}
