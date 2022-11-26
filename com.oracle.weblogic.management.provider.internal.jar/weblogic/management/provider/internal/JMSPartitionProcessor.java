package weblogic.management.provider.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.management.InvalidAttributeValueException;
import org.jvnet.hk2.annotations.Service;
import weblogic.deployment.jms.JMSPartitionDeploymentHelper;
import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.management.ManagementException;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JMSServerMBean;
import weblogic.management.configuration.JMSSystemResourceMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.PersistentStoreMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.configuration.VirtualHostMBean;
import weblogic.management.configuration.VirtualTargetMBean;

@Service
public class JMSPartitionProcessor extends AbstractComponentPartitionProcessor implements ComponentPartitionSystemResourceProcessor {
   public boolean isTargetableVirtually(ConfigurationMBean bean) {
      return false;
   }

   public void processJMSServer(DomainMBean domain, PartitionMBean partition, ResourceGroupMBean resourceGroup, JMSServerMBean bean, JMSServerMBean clone) throws InvalidAttributeValueException, ManagementException {
      PersistentStoreMBean delegateStoreMBean = bean.getPersistentStore();
      if (delegateStoreMBean != null) {
         PersistentStoreMBean clonedStoreMBean = (PersistentStoreMBean)PartitionProcessor.clone(domain, partition, resourceGroup, delegateStoreMBean);
         clone.setPersistentStore(clonedStoreMBean);
      }

   }

   public void processJMSSystemResource(DomainMBean domain, PartitionMBean partition, ResourceGroupMBean resourceGroup, JMSSystemResourceMBean bean, JMSSystemResourceMBean clone) throws InvalidAttributeValueException, ManagementException {
      TargetMBean[] effectiveTargets = clone.getTargets();
      List deploymentTargets = new ArrayList();
      TargetMBean[] var8 = effectiveTargets;
      int var9 = effectiveTargets.length;

      for(int var10 = 0; var10 < var9; ++var10) {
         TargetMBean t = var8[var10];
         if (t instanceof VirtualHostMBean) {
            deploymentTargets.addAll(Arrays.asList(((VirtualHostMBean)t).getTargets()));
         } else if (t instanceof VirtualTargetMBean) {
            deploymentTargets.addAll(Arrays.asList(((VirtualTargetMBean)t).getTargets()));
         } else {
            deploymentTargets.add(t);
         }
      }

      clone.setTargets((TargetMBean[])deploymentTargets.toArray(new TargetMBean[deploymentTargets.size()]));
      JMSPartitionDeploymentHelper.processPartition(partition, bean, clone);
   }

   public boolean handles(Class beanClass) {
      return JMSSystemResourceMBean.class.isAssignableFrom(beanClass);
   }

   public Class descriptorBeanType() {
      return JMSBean.class;
   }

   public void processResource(PartitionMBean partition, ResourceGroupMBean resourceGroup, JMSSystemResourceMBean bean, JMSSystemResourceMBean clone, JMSBean resource) {
      JMSPartitionDeploymentHelper.processResource(partition, bean, clone, resource);
   }
}
