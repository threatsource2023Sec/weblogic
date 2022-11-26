package weblogic.management.runtime;

public interface ONSClientRuntimeMBean extends RuntimeMBean {
   ONSDaemonRuntimeMBean[] getONSDaemonRuntimes();
}
