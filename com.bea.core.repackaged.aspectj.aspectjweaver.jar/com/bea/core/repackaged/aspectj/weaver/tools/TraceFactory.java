package com.bea.core.repackaged.aspectj.weaver.tools;

import com.bea.core.repackaged.aspectj.util.LangUtil;

public abstract class TraceFactory {
   public static final String DEBUG_PROPERTY = "com.bea.core.repackaged.aspectj.tracing.debug";
   public static final String FACTORY_PROPERTY = "com.bea.core.repackaged.aspectj.tracing.factory";
   public static final String DEFAULT_FACTORY_NAME = "default";
   protected static boolean debug = getBoolean("com.bea.core.repackaged.aspectj.tracing.debug", false);
   private static TraceFactory instance;

   public Trace getTrace(Class clazz) {
      return instance.getTrace(clazz);
   }

   public static TraceFactory getTraceFactory() {
      return instance;
   }

   protected static boolean getBoolean(String name, boolean def) {
      String defaultValue = String.valueOf(def);
      String value = System.getProperty(name, defaultValue);
      return Boolean.valueOf(value);
   }

   static {
      String factoryName = System.getProperty("com.bea.core.repackaged.aspectj.tracing.factory");
      Class factoryClass;
      if (factoryName != null) {
         try {
            if (factoryName.equals("default")) {
               instance = new DefaultTraceFactory();
            } else {
               factoryClass = Class.forName(factoryName);
               instance = (TraceFactory)factoryClass.newInstance();
            }
         } catch (Throwable var3) {
            if (debug) {
               var3.printStackTrace();
            }
         }
      }

      if (instance == null) {
         try {
            if (LangUtil.is15VMOrGreater()) {
               factoryClass = Class.forName("com.bea.core.repackaged.aspectj.weaver.tools.Jdk14TraceFactory");
               instance = (TraceFactory)factoryClass.newInstance();
            } else {
               factoryClass = Class.forName("com.bea.core.repackaged.aspectj.weaver.tools.CommonsTraceFactory");
               instance = (TraceFactory)factoryClass.newInstance();
            }
         } catch (Throwable var2) {
            if (debug) {
               var2.printStackTrace();
            }
         }
      }

      if (instance == null) {
         instance = new DefaultTraceFactory();
      }

      if (debug) {
         System.err.println("TraceFactory.instance=" + instance);
      }

   }
}
