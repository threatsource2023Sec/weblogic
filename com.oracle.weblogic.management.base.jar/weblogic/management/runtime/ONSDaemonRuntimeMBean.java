package weblogic.management.runtime;

public interface ONSDaemonRuntimeMBean extends RuntimeMBean {
   String getHost();

   int getPort();

   String getStatus();

   void ping();
}
