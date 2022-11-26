package weblogic.jdbc.module;

import javax.management.InvalidAttributeValueException;
import org.jvnet.hk2.annotations.Service;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;
import weblogic.management.ManagementException;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JDBCSystemResourceMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.provider.internal.AbstractComponentPartitionProcessor;
import weblogic.management.provider.internal.ComponentPartitionSystemResourceProcessor;

@Service
public class JDBCPartitionProcessor extends AbstractComponentPartitionProcessor implements ComponentPartitionSystemResourceProcessor {
   public boolean isTargetableVirtually(ConfigurationMBean bean) {
      return bean instanceof JDBCSystemResourceMBean;
   }

   public void processJDBCSystemResource(DomainMBean domain, PartitionMBean partition, ResourceGroupMBean resourceGroup, JDBCSystemResourceMBean bean, JDBCSystemResourceMBean clone) throws InvalidAttributeValueException, ManagementException {
      JDBCPartitionDeploymentHelper.processPartition(partition, bean, clone);
   }

   public void processResource(PartitionMBean partition, ResourceGroupMBean resourceGroup, JDBCSystemResourceMBean bean, JDBCSystemResourceMBean clone, JDBCDataSourceBean resource) {
      JDBCPartitionDeploymentHelper.processJDBCResource(partition, bean, clone, resource);
   }

   public Class descriptorBeanType() {
      return JDBCDataSourceBean.class;
   }

   public boolean handles(Class beanClass) {
      return JDBCSystemResourceMBean.class.isAssignableFrom(beanClass);
   }
}
