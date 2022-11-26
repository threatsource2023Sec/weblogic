package weblogic.management.runtime;

public interface WseeClientRuntimeMBean extends RuntimeMBean {
   WseeClientPortRuntimeMBean getPort();

   String getClientID();
}
