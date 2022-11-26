package weblogic.t3.srvr;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.DomainPartitionRuntimeMBean;

public class DomainPartitionRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = DomainPartitionRuntimeMBean.class;

   public DomainPartitionRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public DomainPartitionRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.t3.srvr.DomainPartitionRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("VisibleToPartitions", "ALWAYS");
      beanDescriptor.setValue("package", "weblogic.t3.srvr");
      String description = (new String("<p>This class is used for domain level partition handling. </p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.DomainPartitionRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("AppRuntimeStateRuntime")) {
         getterName = "getAppRuntimeStateRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("AppRuntimeStateRuntime", DomainPartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AppRuntimeStateRuntime", currentResult);
         currentResult.setValue("description", "<p>Provides access to the service interface used to manage app runtime state in this partition.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DeployerRuntime")) {
         getterName = "getDeployerRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("DeployerRuntime", DomainPartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DeployerRuntime", currentResult);
         currentResult.setValue("description", "<p>Provides access to the service interface to the interface that is used to deploy new customer applications or modules into this partition.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DeploymentManager")) {
         getterName = "getDeploymentManager";
         setterName = null;
         currentResult = new PropertyDescriptor("DeploymentManager", DomainPartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DeploymentManager", currentResult);
         currentResult.setValue("description", "<p>Provides access to the service interface to the interface that is used to deploy new customer applications or modules into this partition.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EditSessionConfigurationManager")) {
         getterName = "getEditSessionConfigurationManager";
         setterName = null;
         currentResult = new PropertyDescriptor("EditSessionConfigurationManager", DomainPartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("EditSessionConfigurationManager", currentResult);
         currentResult.setValue("description", "<p>Provides access to the service interface used to manage named edit sessions in this partition.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PartitionID")) {
         getterName = "getPartitionID";
         setterName = null;
         currentResult = new PropertyDescriptor("PartitionID", DomainPartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PartitionID", currentResult);
         currentResult.setValue("description", "<p>Returns partition ID value for corresponding partition.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "");
      }

      if (!descriptors.containsKey("PartitionLifeCycleRuntime")) {
         getterName = "getPartitionLifeCycleRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("PartitionLifeCycleRuntime", DomainPartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PartitionLifeCycleRuntime", currentResult);
         currentResult.setValue("description", "Returns the partition life cycle run-time MBean for this partition. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("PartitionUserFileSystemManager")) {
         getterName = "getPartitionUserFileSystemManager";
         setterName = null;
         currentResult = new PropertyDescriptor("PartitionUserFileSystemManager", DomainPartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PartitionUserFileSystemManager", currentResult);
         currentResult.setValue("description", "<p>Provides access to to the service interface that is used to manipulate user file system content for this partition.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.1.0");
         currentResult.setValue("excludeFromRest", "");
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
