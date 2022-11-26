package weblogic.management.runtime;

public interface WebsocketEndpointRuntimeMBean extends WebsocketBaseRuntimeMBean {
   String getEndpointPath();

   String getEndpointClassName();
}
