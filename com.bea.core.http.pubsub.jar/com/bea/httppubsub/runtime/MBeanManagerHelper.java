package com.bea.httppubsub.runtime;

import com.bea.httppubsub.PubSubLogger;
import com.bea.httppubsub.descriptor.WeblogicPubsubBean;
import com.bea.httppubsub.util.ConfigUtils;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.servlet.ServletContext;

public class MBeanManagerHelper {
   private static ConcurrentMap managers = new ConcurrentHashMap();
   private static final String MBEAN_MANAGER_FACTORY_PROPERTY = "com.bea.httppubsub.runtime.MBeanManagerFactory";
   private static MBeanManagerFactory factory;

   private MBeanManagerHelper() {
   }

   public static MBeanManager getMBeanManager(String pubSubServerName) {
      return (MBeanManager)managers.get(pubSubServerName);
   }

   public static void destroyMBeanManager(String pubSubServerName) {
      managers.remove(pubSubServerName);
   }

   public static MBeanManager createMBeanManager(WeblogicPubsubBean bean, ServletContext servletContext) {
      MBeanManager manager = factory.createMBeanManager(bean, servletContext);
      String pubSubServerName = ConfigUtils.getPubSubServerName(bean, servletContext);
      managers.put(pubSubServerName, manager);
      return manager;
   }

   static {
      String factoryClassName = System.getProperty("com.bea.httppubsub.runtime.MBeanManagerFactory");
      if (factoryClassName == null) {
         factoryClassName = "weblogic.servlet.httppubsub.runtime.MBeanManagerFactoryImpl";
      }

      try {
         ClassLoader loader = Thread.currentThread().getContextClassLoader();
         Class factoryClass;
         if (loader != null) {
            factoryClass = loader.loadClass(factoryClassName);
         } else {
            factoryClass = Class.forName(factoryClassName);
         }

         factory = (MBeanManagerFactory)factoryClass.newInstance();
      } catch (Exception var3) {
         PubSubLogger.logCannotInitMBeanManagerFactory(factoryClassName, var3);
         throw new Error(PubSubLogger.logCannotInitMBeanManagerFactoryLoggable(factoryClassName, var3).getMessage(), var3);
      }
   }
}
