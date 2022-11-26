package weblogic.management.runtime;

public interface WseeClientPortRuntimeMBean extends WseeBasePortRuntimeMBean {
   int getPoolCapacity();

   int getPoolFreeCount();

   int getPoolTakenCount();

   int getPoolTotalPooledClientTakeCount();

   int getPoolTotalConversationalClientTakeCount();

   int getPoolTotalSimpleClientCreateCount();

   WseeClientOperationRuntimeMBean[] getOperations();

   WseePortConfigurationRuntimeMBean getWseePortConfigurationRuntimeMBean();
}
