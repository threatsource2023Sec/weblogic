package weblogic.management.mbeanservers.internal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import weblogic.utils.codegen.ImplementationFactory;
import weblogic.utils.codegen.RoleInfoImplementationFactory;

public class BeanInfoFactory implements RoleInfoImplementationFactory {
   private static final Map interfaceMap = new HashMap(29);
   private static final ArrayList roleInfoList;
   private static final BeanInfoFactory SINGLETON;

   public static final ImplementationFactory getInstance() {
      return SINGLETON;
   }

   public String getImplementationClassName(String interfaceName) {
      return (String)interfaceMap.get(interfaceName);
   }

   public String[] getInterfaces() {
      Set keySet = interfaceMap.keySet();
      return (String[])((String[])keySet.toArray(new String[keySet.size()]));
   }

   public String[] getInterfacesWithRoleInfo() {
      return (String[])((String[])roleInfoList.toArray(new String[roleInfoList.size()]));
   }

   public String getRoleInfoImplementationFactoryTimestamp() {
      try {
         InputStream is = this.getClass().getResourceAsStream("BeanInfoFactory.tstamp");
         return (new BufferedReader(new InputStreamReader(is))).readLine();
      } catch (IOException var2) {
         throw new RuntimeException(var2);
      }
   }

   static {
      interfaceMap.put("weblogic.management.mbeanservers.MBeanTypeService", "weblogic.management.mbeanservers.internal.MBeanTypeServiceImplBeanInfo");
      interfaceMap.put("weblogic.management.mbeanservers.Service", "weblogic.management.mbeanservers.internal.ServiceImplBeanInfo");
      interfaceMap.put("weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean", "weblogic.management.mbeanservers.domainruntime.internal.DomainRuntimeServiceMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.mbeanservers.domainruntime.MBeanServerConnectionManagerMBean", "weblogic.management.mbeanservers.domainruntime.internal.MBeanServerConnectionManagerBeanInfo");
      interfaceMap.put("weblogic.management.mbeanservers.edit.ActivationTaskMBean", "weblogic.management.mbeanservers.edit.internal.ActivationTaskMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.mbeanservers.edit.AppDeploymentConfigurationMBean", "weblogic.management.mbeanservers.edit.internal.AppDeploymentConfigurationMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.mbeanservers.edit.AppDeploymentConfigurationManagerMBean", "weblogic.management.mbeanservers.edit.internal.AppDeploymentConfigurationManagerMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.mbeanservers.edit.AppDeploymentConfigurationModuleMBean", "weblogic.management.mbeanservers.edit.internal.AppDeploymentConfigurationModuleMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.mbeanservers.edit.ApplicationDescriptorMBean", "weblogic.management.mbeanservers.edit.internal.ApplicationDescriptorMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.mbeanservers.edit.Change", "weblogic.management.mbeanservers.edit.internal.ChangeImplBeanInfo");
      interfaceMap.put("weblogic.management.mbeanservers.edit.ConfigurationManagerMBean", "weblogic.management.mbeanservers.edit.internal.ConfigurationManagerMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.mbeanservers.edit.ConnectorDescriptorMBean", "weblogic.management.mbeanservers.edit.internal.ConnectorDescriptorMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.mbeanservers.edit.DatasourceDescriptorMBean", "weblogic.management.mbeanservers.edit.internal.DatasourceDescriptorMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.mbeanservers.edit.DescriptorMBean", "weblogic.management.mbeanservers.edit.internal.DescriptorMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.mbeanservers.edit.EJBDescriptorMBean", "weblogic.management.mbeanservers.edit.internal.EJBDescriptorMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.mbeanservers.edit.EditServiceMBean", "weblogic.management.mbeanservers.edit.internal.EditServiceMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.mbeanservers.edit.EditSessionServiceMBean", "weblogic.management.mbeanservers.edit.internal.EditSessionServiceMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.mbeanservers.edit.GarDescriptorMBean", "weblogic.management.mbeanservers.edit.internal.GarDescriptorMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.mbeanservers.edit.JMSDescriptorMBean", "weblogic.management.mbeanservers.edit.internal.JMSDescriptorMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.mbeanservers.edit.PersistenceDescriptorMBean", "weblogic.management.mbeanservers.edit.internal.PersistenceDescriptorMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.mbeanservers.edit.PortablePartitionManagerMBean", "weblogic.management.mbeanservers.edit.internal.PortablePartitionManagerMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.mbeanservers.edit.RecordingManagerMBean", "weblogic.management.mbeanservers.edit.internal.RecordingManagerMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.mbeanservers.edit.ServerStatus", "weblogic.management.mbeanservers.edit.internal.ServerStatusImplBeanInfo");
      interfaceMap.put("weblogic.management.mbeanservers.edit.WLDFDescriptorMBean", "weblogic.management.mbeanservers.edit.internal.WLDFDescriptorMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.mbeanservers.edit.WebAppDescriptorMBean", "weblogic.management.mbeanservers.edit.internal.WebAppDescriptorMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.mbeanservers.edit.WebservicesDescriptorMBean", "weblogic.management.mbeanservers.edit.internal.WebservicesDescriptorMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.mbeanservers.edit.internal.ImportExportPartitionTaskMBean", "weblogic.management.mbeanservers.edit.internal.ImportExportPartitionTaskMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.mbeanservers.edit.internal.ResourceGroupMigrationTaskMBean", "weblogic.management.mbeanservers.edit.internal.ResourceGroupMigrationTaskMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.mbeanservers.runtime.RuntimeServiceMBean", "weblogic.management.mbeanservers.runtime.internal.RuntimeServiceMBeanImplBeanInfo");
      roleInfoList = new ArrayList(22);
      roleInfoList.add("weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean");
      roleInfoList.add("weblogic.management.mbeanservers.edit.ActivationTaskMBean");
      roleInfoList.add("weblogic.management.mbeanservers.edit.AppDeploymentConfigurationMBean");
      roleInfoList.add("weblogic.management.mbeanservers.edit.AppDeploymentConfigurationManagerMBean");
      roleInfoList.add("weblogic.management.mbeanservers.edit.AppDeploymentConfigurationModuleMBean");
      roleInfoList.add("weblogic.management.mbeanservers.edit.ApplicationDescriptorMBean");
      roleInfoList.add("weblogic.management.mbeanservers.edit.ConfigurationManagerMBean");
      roleInfoList.add("weblogic.management.mbeanservers.edit.ConnectorDescriptorMBean");
      roleInfoList.add("weblogic.management.mbeanservers.edit.DatasourceDescriptorMBean");
      roleInfoList.add("weblogic.management.mbeanservers.edit.DescriptorMBean");
      roleInfoList.add("weblogic.management.mbeanservers.edit.EJBDescriptorMBean");
      roleInfoList.add("weblogic.management.mbeanservers.edit.EditServiceMBean");
      roleInfoList.add("weblogic.management.mbeanservers.edit.EditSessionServiceMBean");
      roleInfoList.add("weblogic.management.mbeanservers.edit.GarDescriptorMBean");
      roleInfoList.add("weblogic.management.mbeanservers.edit.JMSDescriptorMBean");
      roleInfoList.add("weblogic.management.mbeanservers.edit.PersistenceDescriptorMBean");
      roleInfoList.add("weblogic.management.mbeanservers.edit.RecordingManagerMBean");
      roleInfoList.add("weblogic.management.mbeanservers.edit.WLDFDescriptorMBean");
      roleInfoList.add("weblogic.management.mbeanservers.edit.WebAppDescriptorMBean");
      roleInfoList.add("weblogic.management.mbeanservers.edit.WebservicesDescriptorMBean");
      roleInfoList.add("weblogic.management.mbeanservers.edit.internal.ResourceGroupMigrationTaskMBean");
      roleInfoList.add("weblogic.management.mbeanservers.runtime.RuntimeServiceMBean");
      SINGLETON = new BeanInfoFactory();
   }
}
