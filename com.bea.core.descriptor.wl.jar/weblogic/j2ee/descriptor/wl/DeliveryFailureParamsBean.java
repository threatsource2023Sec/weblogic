package weblogic.j2ee.descriptor.wl;

import weblogic.descriptor.SettableBean;

public interface DeliveryFailureParamsBean extends SettableBean {
   DestinationBean getErrorDestination();

   void setErrorDestination(DestinationBean var1) throws IllegalArgumentException;

   int getRedeliveryLimit();

   void setRedeliveryLimit(int var1) throws IllegalArgumentException;

   String getExpirationPolicy();

   void setExpirationPolicy(String var1) throws IllegalArgumentException;

   String getExpirationLoggingPolicy();

   void setExpirationLoggingPolicy(String var1) throws IllegalArgumentException;

   TemplateBean getTemplateBean();

   String findSubDeploymentName();
}
