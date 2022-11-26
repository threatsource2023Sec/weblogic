package weblogic.management.runtime;

public interface WebsocketApplicationRuntimeMBean extends WebsocketBaseRuntimeMBean {
   WebsocketEndpointRuntimeMBean[] getEndpointMBeans();
}
