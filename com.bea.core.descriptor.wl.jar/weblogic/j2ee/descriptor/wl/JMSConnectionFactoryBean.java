package weblogic.j2ee.descriptor.wl;

public interface JMSConnectionFactoryBean extends TargetableBean {
   String getName();

   void setName(String var1);

   String getJNDIName();

   void setJNDIName(String var1) throws IllegalArgumentException;

   String getLocalJNDIName();

   void setLocalJNDIName(String var1) throws IllegalArgumentException;

   DefaultDeliveryParamsBean getDefaultDeliveryParams();

   ClientParamsBean getClientParams();

   TransactionParamsBean getTransactionParams();

   FlowControlParamsBean getFlowControlParams();

   LoadBalancingParamsBean getLoadBalancingParams();

   SecurityParamsBean getSecurityParams();
}
