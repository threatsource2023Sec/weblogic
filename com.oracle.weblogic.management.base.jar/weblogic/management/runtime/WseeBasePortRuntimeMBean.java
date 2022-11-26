package weblogic.management.runtime;

public interface WseeBasePortRuntimeMBean extends RuntimeMBean {
   String getPortName();

   String getTransportProtocolType();

   WseeHandlerRuntimeMBean[] getHandlers();

   WseePortPolicyRuntimeMBean getPortPolicy();

   long getStartTime();

   int getPolicyFaults();

   /** @deprecated */
   @Deprecated
   int getTotalFaults();

   int getTotalSecurityFaults();

   WseeClusterRoutingRuntimeMBean getClusterRouting();

   WseeWsrmRuntimeMBean getWsrm();

   WseeMcRuntimeMBean getMc();

   WseeBaseOperationRuntimeMBean[] getBaseOperations();

   WseeAggregatableBaseOperationRuntimeMBean getAggregatedBaseOperations();
}
