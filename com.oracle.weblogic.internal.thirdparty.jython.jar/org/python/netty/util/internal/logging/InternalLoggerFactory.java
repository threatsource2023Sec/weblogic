package org.python.netty.util.internal.logging;

public abstract class InternalLoggerFactory {
   private static volatile InternalLoggerFactory defaultFactory;

   private static InternalLoggerFactory newDefaultFactory(String name) {
      Object f;
      try {
         f = new Slf4JLoggerFactory(true);
         ((InternalLoggerFactory)f).newInstance(name).debug("Using SLF4J as the default logging framework");
      } catch (Throwable var5) {
         try {
            f = Log4JLoggerFactory.INSTANCE;
            ((InternalLoggerFactory)f).newInstance(name).debug("Using Log4J as the default logging framework");
         } catch (Throwable var4) {
            f = JdkLoggerFactory.INSTANCE;
            ((InternalLoggerFactory)f).newInstance(name).debug("Using java.util.logging as the default logging framework");
         }
      }

      return (InternalLoggerFactory)f;
   }

   public static InternalLoggerFactory getDefaultFactory() {
      if (defaultFactory == null) {
         defaultFactory = newDefaultFactory(InternalLoggerFactory.class.getName());
      }

      return defaultFactory;
   }

   public static void setDefaultFactory(InternalLoggerFactory defaultFactory) {
      if (defaultFactory == null) {
         throw new NullPointerException("defaultFactory");
      } else {
         InternalLoggerFactory.defaultFactory = defaultFactory;
      }
   }

   public static InternalLogger getInstance(Class clazz) {
      return getInstance(clazz.getName());
   }

   public static InternalLogger getInstance(String name) {
      return getDefaultFactory().newInstance(name);
   }

   protected abstract InternalLogger newInstance(String var1);
}
