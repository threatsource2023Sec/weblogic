package weblogic.j2ee.descriptor.wl;

import weblogic.descriptor.SettableBean;

public interface GroupParamsBean extends SettableBean {
   String getSubDeploymentName();

   void setSubDeploymentName(String var1) throws IllegalArgumentException;

   DestinationBean getErrorDestination();

   void setErrorDestination(DestinationBean var1) throws IllegalArgumentException;
}
