package weblogic.cluster.singleton;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.runtime.MigrationDataRuntimeMBean;

public class MigrationDataRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = MigrationDataRuntimeMBean.class;

   public MigrationDataRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public MigrationDataRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.cluster.singleton.MigrationDataRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.cluster.singleton");
      String description = (new String("Runtime information about one past or ongoing migration. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.MigrationDataRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("ClusterMasterName")) {
         getterName = "getClusterMasterName";
         setterName = null;
         currentResult = new PropertyDescriptor("ClusterMasterName", MigrationDataRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ClusterMasterName", currentResult);
         currentResult.setValue("description", "Name of the server that acted as the cluster master for this migration. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ClusterName")) {
         getterName = "getClusterName";
         setterName = null;
         currentResult = new PropertyDescriptor("ClusterName", MigrationDataRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ClusterName", currentResult);
         currentResult.setValue("description", "Name of the server that acted as the cluster master for this migration. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MachineMigratedFrom")) {
         getterName = "getMachineMigratedFrom";
         setterName = null;
         currentResult = new PropertyDescriptor("MachineMigratedFrom", MigrationDataRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MachineMigratedFrom", currentResult);
         currentResult.setValue("description", "Machine from which the server was migrated from ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MachineMigratedTo")) {
         getterName = "getMachineMigratedTo";
         setterName = null;
         currentResult = new PropertyDescriptor("MachineMigratedTo", MigrationDataRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MachineMigratedTo", currentResult);
         currentResult.setValue("description", "Machine to which the server was migrated to or is in the process of being migrated to. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MachinesAttempted")) {
         getterName = "getMachinesAttempted";
         setterName = null;
         currentResult = new PropertyDescriptor("MachinesAttempted", MigrationDataRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MachinesAttempted", currentResult);
         currentResult.setValue("description", "Get all the machines attempted for migration. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MigrationEndTime")) {
         getterName = "getMigrationEndTime";
         setterName = null;
         currentResult = new PropertyDescriptor("MigrationEndTime", MigrationDataRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MigrationEndTime", currentResult);
         currentResult.setValue("description", "End time of migration ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MigrationStartTime")) {
         getterName = "getMigrationStartTime";
         setterName = null;
         currentResult = new PropertyDescriptor("MigrationStartTime", MigrationDataRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MigrationStartTime", currentResult);
         currentResult.setValue("description", "Start time of migration ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ServerName")) {
         getterName = "getServerName";
         setterName = null;
         currentResult = new PropertyDescriptor("ServerName", MigrationDataRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ServerName", currentResult);
         currentResult.setValue("description", "Name of the server migrated ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Status")) {
         getterName = "getStatus";
         setterName = null;
         currentResult = new PropertyDescriptor("Status", MigrationDataRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Status", currentResult);
         currentResult.setValue("description", "Name of the server migrated ");
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   protected void buildMethodDescriptors(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      this.fillinFinderMethodInfos(descriptors);
      if (!this.readOnly) {
         this.fillinCollectionMethodInfos(descriptors);
         this.fillinFactoryMethodInfos(descriptors);
      }

      this.fillinOperationMethodInfos(descriptors);
      super.buildMethodDescriptors(descriptors);
   }

   protected void buildEventSetDescriptors(Map descriptors) throws IntrospectionException {
   }
}
