package weblogic.j2ee.dd.xml;

public abstract class JCAConnectionFactoryProvider {
   public static JCAConnectionFactoryProvider provider = null;

   public static boolean isAdapterConnectionFactoryClass(String className) {
      return provider != null ? provider.isAdapterConnectionFactory(className) : false;
   }

   public static void set(JCAConnectionFactoryProvider impl) {
      provider = impl;
   }

   public abstract boolean isAdapterConnectionFactory(String var1);
}
