package weblogic.management.runtime;

public interface WseeClientConfigurationRuntimeMBean extends RuntimeMBean {
   String getServiceReferenceName();

   WseePortConfigurationRuntimeMBean[] getPorts();
}
