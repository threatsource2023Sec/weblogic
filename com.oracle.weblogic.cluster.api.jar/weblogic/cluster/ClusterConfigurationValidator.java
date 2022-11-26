package weblogic.cluster;

import org.jvnet.hk2.annotations.Contract;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.ServerTemplateMBean;

@Contract
public interface ClusterConfigurationValidator {
   void validateMulticastAddress(String var1) throws IllegalArgumentException;

   void canSetCluster(ServerMBean var1, ClusterMBean var2) throws IllegalArgumentException;

   void validateUnicastCluster(ServerTemplateMBean var1, ClusterMBean var2) throws IllegalArgumentException;
}
