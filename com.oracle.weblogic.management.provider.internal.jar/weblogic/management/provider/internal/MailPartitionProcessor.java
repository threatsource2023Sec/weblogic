package weblogic.management.provider.internal;

import javax.management.InvalidAttributeValueException;
import org.jvnet.hk2.annotations.Service;
import weblogic.deployment.mail.MailSessionPartitionDeploymentHelper;
import weblogic.management.ManagementException;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.MailSessionMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ResourceGroupMBean;

@Service
public class MailPartitionProcessor extends AbstractComponentPartitionProcessor {
   public boolean isTargetableVirtually(ConfigurationMBean bean) {
      return false;
   }

   public void processMailSession(DomainMBean domain, PartitionMBean partition, ResourceGroupMBean resourceGroup, MailSessionMBean bean, MailSessionMBean clone) throws InvalidAttributeValueException, ManagementException {
      MailSessionPartitionDeploymentHelper.processPartition(partition, bean, clone);
   }
}
