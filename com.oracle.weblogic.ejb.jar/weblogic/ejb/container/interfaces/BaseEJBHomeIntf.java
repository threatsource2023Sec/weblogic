package weblogic.ejb.container.interfaces;

import weblogic.ejb.spi.WLDeploymentException;

public interface BaseEJBHomeIntf extends weblogic.ejb.spi.BaseEJBHomeIntf {
   void setup(BeanInfo var1, BeanManager var2) throws WLDeploymentException;

   BeanInfo getBeanInfo();

   BeanManager getBeanManager();

   void undeploy();
}
