package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;
import weblogic.management.DistributedManagementException;

public interface WebDeploymentMBean extends DeploymentMBean {
   /** @deprecated */
   @Deprecated
   WebServerMBean[] getWebServers();

   /** @deprecated */
   @Deprecated
   void setWebServers(WebServerMBean[] var1) throws InvalidAttributeValueException, DistributedManagementException;

   /** @deprecated */
   @Deprecated
   boolean addWebServer(WebServerMBean var1) throws InvalidAttributeValueException, DistributedManagementException;

   /** @deprecated */
   @Deprecated
   boolean removeWebServer(WebServerMBean var1) throws DistributedManagementException;

   VirtualHostMBean[] getVirtualHosts();

   void setVirtualHosts(VirtualHostMBean[] var1) throws InvalidAttributeValueException, DistributedManagementException;

   boolean addVirtualHost(VirtualHostMBean var1) throws InvalidAttributeValueException, DistributedManagementException;

   boolean removeVirtualHost(VirtualHostMBean var1) throws DistributedManagementException;

   VirtualHostMBean[] getDeployedVirtualHosts();

   void setDeployedVirtualHosts(VirtualHostMBean[] var1);
}
