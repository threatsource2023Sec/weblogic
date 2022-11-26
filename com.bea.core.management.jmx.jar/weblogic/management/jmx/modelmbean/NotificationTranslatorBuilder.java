package weblogic.management.jmx.modelmbean;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class NotificationTranslatorBuilder {
   private static final Class[] CONSTRUCTOR_SIGNATURE = new Class[]{WLSModelMBeanContext.class, Object.class, NotificationGenerator.class};

   public static final void instantiateNotificationTranslator(WLSModelMBeanContext context, Object managedResource, NotificationGenerator generator) {
      instantiateNotificationTranslator(context.getNotificationFactoryClassName(), context, managedResource, generator);
   }

   public static final void instantiateNotificationTranslator(String className, WLSModelMBeanContext context, Object managedResource, NotificationGenerator generator) {
      Class clazz = null;

      try {
         clazz = Class.forName(className);
      } catch (ClassNotFoundException var11) {
         throw new AssertionError(var11);
      }

      Constructor constructor = null;

      try {
         constructor = clazz.getConstructor(CONSTRUCTOR_SIGNATURE);
      } catch (NoSuchMethodException var10) {
         throw new AssertionError(var10);
      }

      try {
         constructor.newInstance(context, managedResource, generator);
      } catch (InstantiationException var7) {
         throw new AssertionError(var7);
      } catch (IllegalAccessException var8) {
         throw new AssertionError(var8);
      } catch (InvocationTargetException var9) {
         throw new AssertionError(var9.getTargetException());
      }
   }
}
