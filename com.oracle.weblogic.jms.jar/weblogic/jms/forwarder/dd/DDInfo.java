package weblogic.jms.forwarder.dd;

import weblogic.jms.forwarder.DestinationName;

public interface DDInfo {
   DestinationName getDestinationName();

   int getType();

   String getApplicationName();

   String getModuleName();

   int getLoadBalancingPolicy();

   int getForwardingPolicy();

   int getForwardDelay();

   String getPathServiceJNDIName();

   String getSAFExportPolicy();

   String getUnitOfOrderRouting();
}
