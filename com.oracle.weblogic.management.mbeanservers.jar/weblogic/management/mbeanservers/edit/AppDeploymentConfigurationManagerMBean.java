package weblogic.management.mbeanservers.edit;

import javax.management.ObjectName;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.mbeanservers.Service;

public interface AppDeploymentConfigurationManagerMBean extends Service {
   String OBJECT_NAME = "com.bea:Name=AppDeploymentConfigurationManager,Type=" + AppDeploymentConfigurationManagerMBean.class.getName();

   AppDeploymentConfigurationMBean loadDeploymentConfiguration(String var1);

   AppDeploymentConfigurationMBean loadDeploymentConfiguration(String var1, boolean var2);

   AppDeploymentConfigurationMBean loadDeploymentConfiguration(String var1, String var2);

   AppDeploymentConfigurationMBean loadDeploymentConfiguration(String var1, String var2, boolean var3);

   AppDeploymentConfigurationMBean loadDeploymentConfigurationForPartition(String var1, String var2, boolean var3, String var4);

   AppDeploymentConfigurationMBean loadDeploymentConfiguration(String var1, String var2, boolean var3, String var4, String var5);

   AppDeploymentConfigurationMBean loadDeploymentConfigurationForResourceGroupTemplate(String var1, String var2, boolean var3, String var4);

   AppDeploymentConfigurationMBean loadDeploymentConfiguration(String var1, String var2, boolean var3, String var4);

   AppDeploymentConfigurationMBean loadDeploymentConfiguration(ObjectName var1, String var2);

   AppDeploymentConfigurationMBean editDeploymentConfiguration(String var1, String var2, String var3);

   AppDeploymentConfigurationMBean editDeploymentConfiguration(String var1, String var2, String var3, boolean var4);

   AppDeploymentConfigurationMBean editDeploymentConfiguration(String var1, String var2);

   AppDeploymentConfigurationMBean editDeploymentConfiguration(String var1, String var2, boolean var3);

   AppDeploymentConfigurationMBean editDeploymentConfiguration(String var1);

   AppDeploymentConfigurationMBean editDeploymentConfiguration(String var1, boolean var2);

   AppDeploymentConfigurationMBean editDeploymentConfigurationForPartition(String var1, String var2, boolean var3, String var4);

   AppDeploymentConfigurationMBean editDeploymentConfiguration(String var1, String var2, boolean var3, String var4, String var5);

   AppDeploymentConfigurationMBean editDeploymentConfigurationForResourceGroupTemplate(String var1, String var2, boolean var3, String var4);

   AppDeploymentConfigurationMBean editDeploymentConfiguration(String var1, String var2, boolean var3, String var4);

   AppDeploymentConfigurationMBean editDeploymentConfiguration(ObjectName var1, String var2);

   AppDeploymentConfigurationMBean[] getDeploymentConfigurations();

   void releaseDeploymentConfiguration(String var1);

   AppDeploymentMBean[] getUnactivatedDeployments();

   AppDeploymentMBean[] getUnactivatedDeployments(String var1);
}
