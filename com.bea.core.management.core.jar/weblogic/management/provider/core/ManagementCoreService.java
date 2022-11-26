package weblogic.management.provider.core;

import weblogic.management.provider.beaninfo.BeanInfoAccess;
import weblogic.management.provider.beaninfo.BeanInfoRegistration;

public class ManagementCoreService {
   private static BeanInfoAccess beanInfoAccess;

   public static void initializeBeanInfo(BeanInfoAccess beanInfo) {
      if (beanInfoAccess != null) {
         throw new AssertionError("The beaninfo access can only be initialized once");
      } else {
         beanInfoAccess = beanInfo;
      }
   }

   public static BeanInfoAccess getBeanInfoAccess() {
      return beanInfoAccess;
   }

   public static BeanInfoRegistration getBeanInfoRegistration() {
      return (BeanInfoRegistration)beanInfoAccess;
   }
}
