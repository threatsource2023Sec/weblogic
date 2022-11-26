package weblogic.management.runtime;

public interface JMSSessionPoolRuntimeMBean extends RuntimeMBean {
   JMSServerRuntimeMBean getJMSServer();

   JMSConsumerRuntimeMBean[] getConnectionConsumers();

   long getConnectionConsumersCurrentCount();

   long getConnectionConsumersHighCount();

   long getConnectionConsumersTotalCount();
}
