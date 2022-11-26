package weblogic.ejb.container.interfaces;

import weblogic.ejb.container.InternalException;
import weblogic.ejb.spi.WLDeploymentException;

public interface PoolIntf {
   Object getBean() throws InternalException;

   Object getBean(long var1) throws InternalException;

   void releaseBean(Object var1);

   void destroyBean(Object var1);

   void setMaxBeansInFreePool(int var1);

   void updateMaxBeansInFreePool(int var1);

   void updateIdleTimeoutSeconds(int var1);

   void setInitialBeansInFreePool(int var1);

   void cleanup();

   void createInitialBeans() throws WLDeploymentException;

   void reset();

   void reInitializePool();
}
