package weblogic.management.provider.internal;

import org.jvnet.hk2.annotations.Contract;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.server.ServiceFailureException;

@Contract
public interface ConfiguredDeploymentsAccess {
   void remove(PartitionMBean var1) throws DeploymentException, ServiceFailureException;

   void remove(ResourceGroupMBean var1) throws DeploymentException, ServiceFailureException;

   void updateMultiVersionConfiguration(DomainMBean var1);
}
