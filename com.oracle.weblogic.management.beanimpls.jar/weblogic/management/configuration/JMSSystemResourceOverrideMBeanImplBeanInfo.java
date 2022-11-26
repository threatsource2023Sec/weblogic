package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class JMSSystemResourceOverrideMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = JMSSystemResourceOverrideMBean.class;

   public JMSSystemResourceOverrideMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public JMSSystemResourceOverrideMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.JMSSystemResourceOverrideMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("For each configuration entity that supports per-partition-override via a MBean, we have two MBeans, the original configure MBean and the corresponding override MBean, that eventually determine the effective settings of the configured resource.  We mark an attribute of an override MBean \"dynamic true\" even though the original configure MBean masks it \"dynamic false\". This is to make sure that the overriding settings take effect on partition restart. Otherwise a server restart is required.  <p>Defines partition-specific overrides for the most commonly overridden attributes in a JMS system resource descriptor file. It overrides some of the settings in a same-named JMSSystemResource MBean instance in the same multi-tenant scope to which this override MBean belongs. </p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.JMSSystemResourceOverrideMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      if (!descriptors.containsKey("ForeignServers")) {
         String getterName = "getForeignServers";
         String setterName = null;
         currentResult = new PropertyDescriptor("ForeignServers", JMSSystemResourceOverrideMBean.class, getterName, (String)setterName);
         descriptors.put("ForeignServers", currentResult);
         currentResult.setValue("description", "The foreign JMS servers in this JMS system resource. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createForeignServer");
         currentResult.setValue("destroyer", "destroyForeignServer");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = JMSSystemResourceOverrideMBean.class.getMethod("createForeignServer", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the specific foreign server to create ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates a foreign server in this JMS system resource. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ForeignServers");
      }

      mth = JMSSystemResourceOverrideMBean.class.getMethod("destroyForeignServer", ForeignServerOverrideMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("foreignServer", "The specific foreign server to remove ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Removes a foreign server from this JMS system resource. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ForeignServers");
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = JMSSystemResourceOverrideMBean.class.getMethod("lookupForeignServer", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the foreign server to find ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Finds a foreign server with the given name. ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "ForeignServers");
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
