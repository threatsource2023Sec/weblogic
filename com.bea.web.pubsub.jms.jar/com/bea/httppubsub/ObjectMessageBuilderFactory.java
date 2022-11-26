package com.bea.httppubsub;

public final class ObjectMessageBuilderFactory {
   private static final String BUILDER_CLASS_NAME = "com.bea.httppubsub.jms.internal.ObjectMessageBuilderImpl";
   private static final ObjectMessageBuilder builder;

   private ObjectMessageBuilderFactory() {
   }

   public static ObjectMessageBuilder getObjectMessageBuilder() {
      return builder;
   }

   static {
      try {
         ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
         Class builderClass = classLoader.loadClass("com.bea.httppubsub.jms.internal.ObjectMessageBuilderImpl");
         builder = (ObjectMessageBuilder)builderClass.newInstance();
      } catch (Exception var2) {
         throw new RuntimeException("Cannot instantiate ObjectMessageBuilder.", var2);
      }
   }
}
