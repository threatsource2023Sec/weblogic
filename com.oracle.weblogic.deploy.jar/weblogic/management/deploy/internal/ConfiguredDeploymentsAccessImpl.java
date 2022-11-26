package weblogic.management.deploy.internal;

import org.jvnet.hk2.annotations.Service;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.provider.internal.ConfiguredDeploymentsAccess;
import weblogic.server.ServiceFailureException;

@Service
public class ConfiguredDeploymentsAccessImpl implements ConfiguredDeploymentsAccess {
   public void remove(PartitionMBean partition) throws DeploymentException, ServiceFailureException {
      ConfiguredDeployments.getConfigureDeploymentsHandler().remove(partition);
   }

   public void remove(ResourceGroupMBean resourceGroup) throws DeploymentException, ServiceFailureException {
      ConfiguredDeployments.getConfigureDeploymentsHandler().remove(resourceGroup);
   }

   public void updateMultiVersionConfiguration(DomainMBean proposedDomain) {
      ConfiguredDeployments.getConfigureDeploymentsHandler().updateMultiVersionConfiguration(proposedDomain);
   }
}
