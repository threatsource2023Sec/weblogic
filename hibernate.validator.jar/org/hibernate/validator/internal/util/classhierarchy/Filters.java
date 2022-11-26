package org.hibernate.validator.internal.util.classhierarchy;

public class Filters {
   private static final Filter PROXY_FILTER = new WeldProxyFilter();
   private static final Filter INTERFACES_FILTER = new InterfacesFilter();

   private Filters() {
   }

   public static Filter excludeInterfaces() {
      return INTERFACES_FILTER;
   }

   public static Filter excludeProxies() {
      return PROXY_FILTER;
   }

   private static class WeldProxyFilter implements Filter {
      private static final String WELD_PROXY_INTERFACE_NAME = "org.jboss.weld.bean.proxy.ProxyObject";

      private WeldProxyFilter() {
      }

      public boolean accepts(Class clazz) {
         return !this.isWeldProxy(clazz);
      }

      private boolean isWeldProxy(Class clazz) {
         Class[] var2 = clazz.getInterfaces();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Class implementedInterface = var2[var4];
            if (implementedInterface.getName().equals("org.jboss.weld.bean.proxy.ProxyObject")) {
               return true;
            }
         }

         return false;
      }

      // $FF: synthetic method
      WeldProxyFilter(Object x0) {
         this();
      }
   }

   private static class InterfacesFilter implements Filter {
      private InterfacesFilter() {
      }

      public boolean accepts(Class clazz) {
         return !clazz.isInterface();
      }

      // $FF: synthetic method
      InterfacesFilter(Object x0) {
         this();
      }
   }
}
