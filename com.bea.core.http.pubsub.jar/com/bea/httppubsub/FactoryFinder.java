package com.bea.httppubsub;

import java.util.HashMap;
import java.util.Map;

public final class FactoryFinder {
   public static final String PUBSUBSERVER_FACTORY = "com.bea.httppubsub.PubSubServerFactory";
   public static final String PUBSUBCONFIG_FACTORY = "com.bea.httppubsub.PubSubConfigFactory";
   private static final String DEFAULT_PUBSUBSERVER_FACTORY = "com.bea.httppubsub.internal.PubSubServerFactoryImpl";
   private static final String DEFAULT_PUBSUBCONFIG_FACTORY = "com.bea.httppubsub.internal.PubSubConfigFactoryImpl";
   private static Map factories = new HashMap();

   private FactoryFinder() {
   }

   public static Object getFactory(String factoryName) throws PubSubServerException {
      if (factoryName == null) {
         throw new NullPointerException("The parameter factoryName is null");
      } else if (!validateFactoryName(factoryName)) {
         throw new IllegalArgumentException("The parameter factoryName is invalid");
      } else {
         return factories.get(factoryName);
      }
   }

   public static void setFactory(String factoryName, String implName) throws PubSubServerException {
      if (factoryName != null && implName != null) {
         if (!validateFactoryName(factoryName)) {
            throw new IllegalArgumentException("The parameter factoryName is invalid");
         } else {
            ClassLoader cl = Thread.currentThread().getContextClassLoader();

            try {
               Class clz = cl.loadClass(implName);
               factories.put(factoryName, clz.newInstance());
            } catch (ClassNotFoundException var6) {
               try {
                  Class clz = FactoryFinder.class.getClassLoader().loadClass(implName);
                  factories.put(factoryName, clz.newInstance());
               } catch (Exception var5) {
                  throw new PubSubServerException("FactoryFinder can't load factory " + factoryName + "'s implementation class " + implName);
               }
            } catch (Exception var7) {
               throw new PubSubServerException("FactoryFinder can't load factory " + factoryName + "'s implementation class " + implName);
            }

         }
      } else {
         throw new NullPointerException("The parameter factoryName or implName is null");
      }
   }

   private static boolean validateFactoryName(String factoryname) {
      return "com.bea.httppubsub.PubSubServerFactory".equals(factoryname) || "com.bea.httppubsub.PubSubConfigFactory".equals(factoryname);
   }

   static {
      try {
         setFactory("com.bea.httppubsub.PubSubServerFactory", "com.bea.httppubsub.internal.PubSubServerFactoryImpl");
         setFactory("com.bea.httppubsub.PubSubConfigFactory", "com.bea.httppubsub.internal.PubSubConfigFactoryImpl");
      } catch (Exception var1) {
         throw new RuntimeException(var1);
      }
   }
}
