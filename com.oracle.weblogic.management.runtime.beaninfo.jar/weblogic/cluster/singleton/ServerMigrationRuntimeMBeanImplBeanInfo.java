package weblogic.cluster.singleton;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.runtime.ServerMigrationRuntimeMBean;

public class ServerMigrationRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ServerMigrationRuntimeMBean.class;

   public ServerMigrationRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ServerMigrationRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.cluster.singleton.ServerMigrationRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.cluster.singleton");
      String description = (new String("ServerMigrationRuntimeMBean provides runtime monitoring information about the past migrations performed by this server as the cluster master. If this server was never the cluster master then no information would be available. This RuntimeMBean would be hosted on all cluster members and can be queried for the location of the cluster master which is just another peer in the cluster. JMX clients can make another call to the server hosting the cluster master functionality to get the migration history. <p> Please note that the migration history is not persisted and is lost when a server is shutdown. </p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.ServerMigrationRuntimeMBean");
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
         currentResult = new PropertyDescriptor("ClusterMasterName", ServerMigrationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ClusterMasterName", currentResult);
         currentResult.setValue("description", "Returns the server name who is the cluster master. Migration history is only available from the cluster master. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MigrationData")) {
         getterName = "getMigrationData";
         setterName = null;
         currentResult = new PropertyDescriptor("MigrationData", ServerMigrationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MigrationData", currentResult);
         currentResult.setValue("description", "Returns the migrations performed by this server as the cluster master. Returns <code>null</code> if there is no history available. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ClusterMaster")) {
         getterName = "isClusterMaster";
         setterName = null;
         currentResult = new PropertyDescriptor("ClusterMaster", ServerMigrationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ClusterMaster", currentResult);
         currentResult.setValue("description", "Is the current server the cluster master? ");
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
