package weblogic.cluster.singleton;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.runtime.ServiceMigrationDataRuntimeMBean;

public class ServiceMigrationDataRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ServiceMigrationDataRuntimeMBean.class;

   public ServiceMigrationDataRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ServiceMigrationDataRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.cluster.singleton.ServiceMigrationDataRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.cluster.singleton");
      String description = (new String("Runtime information about one past or ongoing migration. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.ServiceMigrationDataRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("ClusterName")) {
         getterName = "getClusterName";
         setterName = null;
         currentResult = new PropertyDescriptor("ClusterName", ServiceMigrationDataRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ClusterName", currentResult);
         currentResult.setValue("description", "Name of the cluster ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CoordinatorName")) {
         getterName = "getCoordinatorName";
         setterName = null;
         currentResult = new PropertyDescriptor("CoordinatorName", ServiceMigrationDataRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CoordinatorName", currentResult);
         currentResult.setValue("description", "Name of the server that acted as the coordinator for this migration. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DestinationsAttempted")) {
         getterName = "getDestinationsAttempted";
         setterName = null;
         currentResult = new PropertyDescriptor("DestinationsAttempted", ServiceMigrationDataRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DestinationsAttempted", currentResult);
         currentResult.setValue("description", "Get all the destinations attempted for migration. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MigratedFrom")) {
         getterName = "getMigratedFrom";
         setterName = null;
         currentResult = new PropertyDescriptor("MigratedFrom", ServiceMigrationDataRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MigratedFrom", currentResult);
         currentResult.setValue("description", "Where this object was migrated from ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MigratedTo")) {
         getterName = "getMigratedTo";
         setterName = null;
         currentResult = new PropertyDescriptor("MigratedTo", ServiceMigrationDataRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MigratedTo", currentResult);
         currentResult.setValue("description", "Where this object was migrated to ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MigrationEndTime")) {
         getterName = "getMigrationEndTime";
         setterName = null;
         currentResult = new PropertyDescriptor("MigrationEndTime", ServiceMigrationDataRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MigrationEndTime", currentResult);
         currentResult.setValue("description", "End time of migration ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MigrationStartTime")) {
         getterName = "getMigrationStartTime";
         setterName = null;
         currentResult = new PropertyDescriptor("MigrationStartTime", ServiceMigrationDataRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MigrationStartTime", currentResult);
         currentResult.setValue("description", "Start time of migration ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ServerName")) {
         getterName = "getServerName";
         setterName = null;
         currentResult = new PropertyDescriptor("ServerName", ServiceMigrationDataRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ServerName", currentResult);
         currentResult.setValue("description", "Name of the object migrated ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Status")) {
         getterName = "getStatus";
         setterName = null;
         currentResult = new PropertyDescriptor("Status", ServiceMigrationDataRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Status", currentResult);
         currentResult.setValue("description", "Status of the migration (SUCCESSFUL, IN_PROGRESS, or FAILED) ");
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
