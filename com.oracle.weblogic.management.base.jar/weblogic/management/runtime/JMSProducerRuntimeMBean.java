package weblogic.management.runtime;

public interface JMSProducerRuntimeMBean extends RuntimeMBean {
   long getMessagesPendingCount();

   long getMessagesSentCount();

   long getBytesPendingCount();

   long getBytesSentCount();
}
