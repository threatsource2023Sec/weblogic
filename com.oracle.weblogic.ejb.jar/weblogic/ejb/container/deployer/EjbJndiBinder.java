package weblogic.ejb.container.deployer;

import weblogic.ejb.spi.WLDeploymentException;

public interface EjbJndiBinder {
   void bindToJNDI() throws WLDeploymentException;

   void unbindFromJNDI();

   boolean isNameBound(String var1);
}
