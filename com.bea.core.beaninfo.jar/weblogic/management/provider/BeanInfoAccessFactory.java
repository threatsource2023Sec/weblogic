package weblogic.management.provider;

public class BeanInfoAccessFactory {
   public static BeanInfoAccess getBeanInfoAccess() {
      return (BeanInfoAccess)weblogic.management.provider.beaninfo.BeanInfoAccessFactory.getBeanInfoAccess();
   }

   public static BeanInfoRegistration getBeanInfoRegistration() {
      return (BeanInfoRegistration)weblogic.management.provider.beaninfo.BeanInfoAccessFactory.getBeanInfoRegistration();
   }

   public static BeanInfoAccess createInstance() {
      return (BeanInfoAccess)weblogic.management.provider.beaninfo.BeanInfoAccessFactory.createInstance();
   }
}
