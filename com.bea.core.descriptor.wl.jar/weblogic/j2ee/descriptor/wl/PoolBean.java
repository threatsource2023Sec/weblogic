package weblogic.j2ee.descriptor.wl;

public interface PoolBean {
   int getMaxBeansInFreePool();

   void setMaxBeansInFreePool(int var1);

   int getInitialBeansInFreePool();

   void setInitialBeansInFreePool(int var1);

   int getIdleTimeoutSeconds();

   void setIdleTimeoutSeconds(int var1);

   String getId();

   void setId(String var1);
}
