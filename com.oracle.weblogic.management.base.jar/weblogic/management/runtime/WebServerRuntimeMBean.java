package weblogic.management.runtime;

public interface WebServerRuntimeMBean extends RuntimeMBean {
   String getWebServerName();

   boolean isDefaultWebServer();

   LogRuntimeMBean getLogRuntime();
}
