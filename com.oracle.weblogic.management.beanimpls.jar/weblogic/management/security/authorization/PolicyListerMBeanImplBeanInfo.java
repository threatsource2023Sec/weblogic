package weblogic.management.security.authorization;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.utils.PropertiesListerMBeanImplBeanInfo;

public class PolicyListerMBeanImplBeanInfo extends PropertiesListerMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = PolicyListerMBean.class;

   public PolicyListerMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public PolicyListerMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.security.authorization.PolicyListerMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("abstract", Boolean.TRUE);
      beanDescriptor.setValue("VisibleToPartitions", "ALWAYS");
      beanDescriptor.setValue("owner", "RealmAdministrator");
      beanDescriptor.setValue("package", "weblogic.management.security.authorization");
      String description = (new String("<p>Provides a set of methods for listing data about policies. An Authorization-provider MBean can optionally extend this MBean. The WebLogic Server Administration Console detects when an Authorization provider extends this MBean and automatically provides a GUI for using these methods.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.security.authorization.PolicyListerMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = PolicyListerMBean.class.getMethod("listAllPolicies", Integer.TYPE);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("maximumToReturn", "- The maximum number of entires to return. Use 0 to return all policy definitions. ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Obtain an unsorted list of policy definitions.</p>  <p>This method returns a cursor that you can pass to the methods from <code>weblogic.management.utils.PropertiesListerMBean</code> (which this MBean extends) to iterate through the returned list.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = PolicyListerMBean.class.getMethod("listPoliciesByResourceType", String.class, Integer.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("resourceType", "- The name of the resource type specified by a <code>weblogic.security.spi.Resource</code> object. "), createParameterDescriptor("maximumToReturn", "- The maximum number of entires to return. Use 0 to return all policy definitions. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Obtain an list of policy definitions by resource type.</p>  <p>This method returns a cursor that you can pass to the methods from <code>weblogic.management.utils.PropertiesListerMBean</code> (which this MBean extends) to iterate through the returned list.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = PolicyListerMBean.class.getMethod("listPoliciesByApplication", String.class, Integer.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("applicationName", "- The name of the application. "), createParameterDescriptor("maximumToReturn", "- The maximum number of entires to return. Use 0 to return all policy definitions. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Obtain an list of policy definitions by application name.</p>  <p>This method returns a cursor that you can pass to the methods from <code>weblogic.management.utils.PropertiesListerMBean</code> (which this MBean extends) to iterate through the returned list.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = PolicyListerMBean.class.getMethod("listPoliciesByComponent", String.class, String.class, String.class, Integer.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("componentName", "- The name of the component. "), createParameterDescriptor("componentType", "- The component type. "), createParameterDescriptor("applicationName", "- The name of the application. "), createParameterDescriptor("maximumToReturn", "- The maximum number of entires to return. Use 0 to return all policy definitions. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Obtain an list of policy definitions for a specific Java EE component.</p>  <p>This method returns a cursor that you can pass to the methods from <code>weblogic.management.utils.PropertiesListerMBean</code> (which this MBean extends) to iterate through the returned list.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = PolicyListerMBean.class.getMethod("listChildPolicies", String.class, Integer.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("resourceId", "- a security resource identifier. "), createParameterDescriptor("maximumToReturn", "- The maximum number of entires to return. Use 0 to return all policy definitions. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Obtain a list of policy definitions for the children of a resource.</p>  <p>This method returns a cursor that you can pass to the methods from <code>weblogic.management.utils.PropertiesListerMBean</code> (which this MBean extends) to iterate through the returned list.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = PolicyListerMBean.class.getMethod("listRepeatingActionsPolicies", String.class, Integer.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("resourceId", "- a security resource identifier. "), createParameterDescriptor("maximumToReturn", "- The maximum number of entires to return. Use 0 to return all policy definitions. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Obtain a list of policy definitions for the actions that are repeating on a resource.</p>  <p>This method returns a cursor that you can pass to the methods from <code>weblogic.management.utils.PropertiesListerMBean</code> (which this MBean extends) to iterate through the returned list.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = PolicyListerMBean.class.getMethod("getPolicy", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("resourceId", "- a security resource identifier. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Obtain a policy definition for a resource. A null is returned when no policy is found. The <code>Properties</code> object is the same as those retuned from the <code>PropertiesListerMBean</code>.</p> ");
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
