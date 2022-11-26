package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class BasicDeploymentMBeanImplBeanInfo extends TargetInfoMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = BasicDeploymentMBean.class;

   public BasicDeploymentMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public BasicDeploymentMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.BasicDeploymentMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>This bean represents a file or archive that is deployed to a set of targets in the domain.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.BasicDeploymentMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("DeploymentOrder")) {
         getterName = "getDeploymentOrder";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDeploymentOrder";
         }

         currentResult = new PropertyDescriptor("DeploymentOrder", BasicDeploymentMBean.class, getterName, setterName);
         descriptors.put("DeploymentOrder", currentResult);
         currentResult.setValue("description", "<p>An integer value that indicates when this unit is deployed, relative to other deployable units on a server, during startup.</p>  <p>Units with lower values are deployed before those with higher values.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(100));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DeploymentPrincipalName")) {
         getterName = "getDeploymentPrincipalName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDeploymentPrincipalName";
         }

         currentResult = new PropertyDescriptor("DeploymentPrincipalName", BasicDeploymentMBean.class, getterName, setterName);
         descriptors.put("DeploymentPrincipalName", currentResult);
         currentResult.setValue("description", "<p>A string value that indicates the principal that should be used when deploying the file or archive during startup and shutdown. This principal will be used to set the current subject when calling out into application code for interfaces such as ApplicationLifecycleListener. If no principal name is specified, then the anonymous principal will be used.</p> ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("PartitionName")) {
         getterName = "getPartitionName";
         setterName = null;
         currentResult = new PropertyDescriptor("PartitionName", BasicDeploymentMBean.class, getterName, setterName);
         descriptors.put("PartitionName", currentResult);
         currentResult.setValue("description", "<p>The partition name for this deployment if this deployment is configured in a partition; otherwise, <code>null</code>.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("SourcePath")) {
         getterName = "getSourcePath";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSourcePath";
         }

         currentResult = new PropertyDescriptor("SourcePath", BasicDeploymentMBean.class, getterName, setterName);
         descriptors.put("SourcePath", currentResult);
         currentResult.setValue("description", "<p>The path to the source of the deployment unit on admin server.</p> ");
         currentResult.setValue("setterDeprecated", "9.0.0.0 There is no replacement for this method. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SubDeployments")) {
         getterName = "getSubDeployments";
         setterName = null;
         currentResult = new PropertyDescriptor("SubDeployments", BasicDeploymentMBean.class, getterName, setterName);
         descriptors.put("SubDeployments", currentResult);
         currentResult.setValue("description", "<p>Targeting for subcomponents that differs from targeting for the component.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroySubDeployment");
         currentResult.setValue("creator", "createSubDeployment");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = BasicDeploymentMBean.class.getMethod("createSubDeployment", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Create a new subdeployment</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SubDeployments");
      }

      mth = BasicDeploymentMBean.class.getMethod("destroySubDeployment", SubDeploymentMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("subDeployment", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Remove subDeployment</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SubDeployments");
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = BasicDeploymentMBean.class.getMethod("lookupSubDeployment", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Look up a subdeployment</p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "SubDeployments");
      }

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
