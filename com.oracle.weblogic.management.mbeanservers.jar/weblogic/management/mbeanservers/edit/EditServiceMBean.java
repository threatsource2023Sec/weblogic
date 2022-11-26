package weblogic.management.mbeanservers.edit;

import weblogic.management.configuration.DomainMBean;
import weblogic.management.mbeanservers.Service;

public interface EditServiceMBean extends Service {
   String MBEANSERVER_JNDI_NAME = "weblogic.management.mbeanservers.edit";
   String OBJECT_NAME = "com.bea:Name=EditService,Type=" + EditServiceMBean.class.getName();

   DomainMBean getDomainConfiguration();

   ConfigurationManagerMBean getConfigurationManager();

   PortablePartitionManagerMBean getPortablePartitionManager();

   AppDeploymentConfigurationManagerMBean getAppDeploymentConfigurationManager();

   RecordingManagerMBean getRecordingManager();
}
