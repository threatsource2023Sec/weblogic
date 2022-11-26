package weblogic.management.deploy.internal;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Properties;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.LibDeploymentRuntimeMBean;

public class LibDeploymentRuntimeImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = LibDeploymentRuntimeMBean.class;

   public LibDeploymentRuntimeImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public LibDeploymentRuntimeImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.deploy.internal.LibDeploymentRuntimeImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.deploy.internal");
      String description = (new String("<p>This MBean provides deployment operations for a library..</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.LibDeploymentRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("ImplementationVersion")) {
         getterName = "getImplementationVersion";
         setterName = null;
         currentResult = new PropertyDescriptor("ImplementationVersion", LibDeploymentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ImplementationVersion", currentResult);
         currentResult.setValue("description", "<p>The library's implementation version, null if none is set</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LibraryIdentifier")) {
         getterName = "getLibraryIdentifier";
         setterName = null;
         currentResult = new PropertyDescriptor("LibraryIdentifier", LibDeploymentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LibraryIdentifier", currentResult);
         currentResult.setValue("description", "<p>The library's identifier.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LibraryName")) {
         getterName = "getLibraryName";
         setterName = null;
         currentResult = new PropertyDescriptor("LibraryName", LibDeploymentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LibraryName", currentResult);
         currentResult.setValue("description", "<p>The library's name.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("PartitionName")) {
         getterName = "getPartitionName";
         setterName = null;
         currentResult = new PropertyDescriptor("PartitionName", LibDeploymentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PartitionName", currentResult);
         currentResult.setValue("description", "<p>The name of the partition the library is in.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
         currentResult.setValue("excludeFromRest", "${excludeFromRest}");
      }

      if (!descriptors.containsKey("SpecificationVersion")) {
         getterName = "getSpecificationVersion";
         setterName = null;
         currentResult = new PropertyDescriptor("SpecificationVersion", LibDeploymentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SpecificationVersion", currentResult);
         currentResult.setValue("description", "<p>The library's specification version, null if none is set</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
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
      Method mth = LibDeploymentRuntimeMBean.class.getMethod("undeploy");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Undeploy the library using the default options and configured targets. This is a synchronous operation that returns when the redeploy operation has completed. The default options are adminMode boolean false timeout: no timeout</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = LibDeploymentRuntimeMBean.class.getMethod("undeploy", String[].class, Properties.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("targets", "The targets on which to undeploy the library. This would be server names, cluster names, or module names in a similar format to weblogic.Deployer (i.e. module1@server1). If null, the library will be undeployed on all configured targets. "), createParameterDescriptor("deploymentOptions", "Allows for overriding the deployment options. If null, default options will be used. The values should all be of type The keys,units and default values for options are adminMode boolean false timeout milliseconds 0 (no timeout) ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Undeploy the library in the background for the targets specified with the options specified.  This is an asynchronous operation that returns immediately.  The returned {@link DeploymentProgressObjectMBean} can be used to determine when the operation is completed.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = LibDeploymentRuntimeMBean.class.getMethod("redeploy");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Redeploy the library using the default options and configured targets. This is a synchronous operation that returns when the redeploy operation has completed. The default options are adminMode: false, timeout: no timeout</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = LibDeploymentRuntimeMBean.class.getMethod("redeploy", String[].class, Properties.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("targets", "The targets on which to redeploy the library. This would be server names, cluster names, or module names in a similar format to weblogic.Deployer (i.e. module1@server1). If null, the library will be redeployed on all configured targets. "), createParameterDescriptor("deploymentOptions", "Allows for overriding the deployment options. If null, default options will be used. The keys,units and default values for options are adminMode boolean false, timeout milliseconds 0 (no timeout) ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Redeploy the library in the background for the targets specified with the options specified.  This is an synchronous operation that returns immediately.  The returned {@link DeploymentProgressObjectMBean} can be used to determine when the operation is completed.</p> ");
         currentResult.setValue("role", "operation");
      }

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
