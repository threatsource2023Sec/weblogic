package weblogic.management.mbeanservers.edit.internal;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.mbeanservers.edit.AppDeploymentConfigurationModuleMBean;

public class AppDeploymentConfigurationModuleMBeanImplBeanInfo extends DescriptorMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = AppDeploymentConfigurationModuleMBean.class;

   public AppDeploymentConfigurationModuleMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public AppDeploymentConfigurationModuleMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.mbeanservers.edit.internal.AppDeploymentConfigurationModuleMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.1.2");
      beanDescriptor.setValue("VisibleToPartitions", "ALWAYS");
      beanDescriptor.setValue("owner", "Context");
      beanDescriptor.setValue("exclude", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.management.mbeanservers.edit.internal");
      String description = (new String("This interface represents the deployment configuration.for the module-level configuration for an application ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.mbeanservers.edit.AppDeploymentConfigurationModuleMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("Descriptors")) {
         getterName = "getDescriptors";
         setterName = null;
         currentResult = new PropertyDescriptor("Descriptors", AppDeploymentConfigurationModuleMBean.class, getterName, (String)setterName);
         descriptors.put("Descriptors", currentResult);
         currentResult.setValue("description", "Return the descriptors for the module ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         currentResult = new PropertyDescriptor("Name", AppDeploymentConfigurationModuleMBean.class, getterName, (String)setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "Return the name of the module ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ParentAttribute")) {
         getterName = "getParentAttribute";
         setterName = null;
         currentResult = new PropertyDescriptor("ParentAttribute", AppDeploymentConfigurationModuleMBean.class, getterName, (String)setterName);
         descriptors.put("ParentAttribute", currentResult);
         currentResult.setValue("description", "<p>The name of the attribute of the parent that refers to this bean</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ParentService")) {
         getterName = "getParentService";
         setterName = null;
         currentResult = new PropertyDescriptor("ParentService", AppDeploymentConfigurationModuleMBean.class, getterName, (String)setterName);
         descriptors.put("ParentService", currentResult);
         currentResult.setValue("description", "<p>The MBean that created the current MBean instance.</p>  <p>In the data model for WebLogic Server MBeans, an MBean that creates another MBean is called a <i>parent</i>. MBeans at the top of the hierarchy have no parents.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "No default REST mapping for weblogic.management.provider.Service");
      }

      if (!descriptors.containsKey("Path")) {
         getterName = "getPath";
         setterName = null;
         currentResult = new PropertyDescriptor("Path", AppDeploymentConfigurationModuleMBean.class, getterName, (String)setterName);
         descriptors.put("Path", currentResult);
         currentResult.setValue("description", "<p>Returns the path to the bean relative to the reoot of the heirarchy of services</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Type")) {
         getterName = "getType";
         setterName = null;
         currentResult = new PropertyDescriptor("Type", AppDeploymentConfigurationModuleMBean.class, getterName, (String)setterName);
         descriptors.put("Type", currentResult);
         currentResult.setValue("description", "Return the type of the module ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
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
