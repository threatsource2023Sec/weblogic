package weblogic.management.runtime;

public interface ConnectorInboundRuntimeMBean extends RuntimeMBean {
   String getMsgListenerType();

   String getActivationSpecClass();

   MessageDrivenEJBRuntimeMBean[] getMDBRuntimes();

   String getState();
}
