package weblogic.management.provider.beaninfo;

import weblogic.management.provider.internal.BeanInfoAccessImpl;

public class BeanInfoAccessFactory {
   public static BeanInfoAccess getBeanInfoAccess() {
      return BeanInfoAccessFactory.SINGLETON.instance;
   }

   public static BeanInfoRegistration getBeanInfoRegistration() {
      return BeanInfoAccessFactory.SINGLETON.instance;
   }

   public static BeanInfoAccess createInstance() {
      return new BeanInfoAccessImpl();
   }

   private static class SINGLETON {
      static BeanInfoAccessImpl instance = new BeanInfoAccessImpl();
   }
}
