package weblogic.management.runtime;

public interface WseeClusterFrontEndRuntimeMBean extends RuntimeMBean {
   WseeClusterRoutingRuntimeMBean getClusterRouting();
}
