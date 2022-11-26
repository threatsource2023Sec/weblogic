package weblogic.management.descriptors.weblogic;

public interface PoolMBean extends WeblogicBeanDescriptorMBean {
   int getMaxBeansInFreePool();

   void setMaxBeansInFreePool(int var1);

   int getInitialBeansInFreePool();

   void setInitialBeansInFreePool(int var1);
}
