package weblogic.ejb.container.interfaces;

import javax.ejb.EJBHome;
import javax.ejb.EJBObject;
import weblogic.ejb.spi.WLDeploymentException;

public interface BaseEJBRemoteHomeIntf extends BaseEJBHomeIntf {
   void activate() throws WLDeploymentException;

   EJBObject allocateEO(Object var1);

   EJBObject allocateEO();

   EJBHome getCBVHomeStub();

   Object getReferenceToBind();

   void unprepare();
}
