package weblogic.jdbc.common.internal;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.JDBCServiceRuntimeMBean;

public class ServiceRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = JDBCServiceRuntimeMBean.class;

   public ServiceRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ServiceRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.jdbc.common.internal.ServiceRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.0.0");
      beanDescriptor.setValue("package", "weblogic.jdbc.common.internal");
      String description = (new String("<p>This class is used for monitoring a WebLogic JDBC service. It maps to a JDBCResource JMO.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.JDBCServiceRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("HealthState")) {
         getterName = "getHealthState";
         setterName = null;
         currentResult = new PropertyDescriptor("HealthState", JDBCServiceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("HealthState", currentResult);
         currentResult.setValue("description", "<p>The health state of the JDBC subsystem.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JDBCDataSourceRuntimeMBeans")) {
         getterName = "getJDBCDataSourceRuntimeMBeans";
         setterName = null;
         currentResult = new PropertyDescriptor("JDBCDataSourceRuntimeMBeans", JDBCServiceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("JDBCDataSourceRuntimeMBeans", currentResult);
         currentResult.setValue("description", "The list of JDBCDataSourceRuntimeMBeans created in this domain. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JDBCDriverRuntimeMBeans")) {
         getterName = "getJDBCDriverRuntimeMBeans";
         setterName = null;
         currentResult = new PropertyDescriptor("JDBCDriverRuntimeMBeans", JDBCServiceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("JDBCDriverRuntimeMBeans", currentResult);
         currentResult.setValue("description", "The list of JDBCDriverRuntimeMBeans created in this domain. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JDBCMultiDataSourceRuntimeMBeans")) {
         getterName = "getJDBCMultiDataSourceRuntimeMBeans";
         setterName = null;
         currentResult = new PropertyDescriptor("JDBCMultiDataSourceRuntimeMBeans", JDBCServiceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("JDBCMultiDataSourceRuntimeMBeans", currentResult);
         currentResult.setValue("description", "The list of JDBCMultiDataSourceRuntimeMBeans created in this domain. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      MethodDescriptor currentResult;
      Method mth;
      ParameterDescriptor[] parameterDescriptors;
      String methodKey;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion)) {
         mth = JDBCServiceRuntimeMBean.class.getMethod("lookupJDBCDataSourceRuntimeMBean", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The data source name ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.3.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Returns the JDBCDataSourceRuntimeMBean registered under the specified name. ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "JDBCDataSourceRuntimeMBeans");
            currentResult.setValue("since", "12.2.1.3.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion)) {
         mth = JDBCServiceRuntimeMBean.class.getMethod("lookupJDBCDataSourceRuntimeMBean", String.class, String.class, String.class, String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The data source name "), createParameterDescriptor("appName", "The application name "), createParameterDescriptor("moduleName", "The module name "), createParameterDescriptor("componentName", "the component name ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.3.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Returns the JDBCDataSourceRuntimeMBean registered under the specified name, application name, module name and/or component name. ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "JDBCDataSourceRuntimeMBeans");
            currentResult.setValue("since", "12.2.1.3.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion)) {
         mth = JDBCServiceRuntimeMBean.class.getMethod("lookupJDBCMultiDataSourceRuntimeMBean", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The data source name ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.3.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Returns the JDBCMultiDataSourceRuntimeMBean registered under the specified name. ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "JDBCMultiDataSourceRuntimeMBeans");
            currentResult.setValue("since", "12.2.1.3.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion)) {
         mth = JDBCServiceRuntimeMBean.class.getMethod("lookupJDBCMultiDataSourceRuntimeMBean", String.class, String.class, String.class, String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The data source name "), createParameterDescriptor("appName", "The application name "), createParameterDescriptor("moduleName", "The module name "), createParameterDescriptor("componentName", "the component name ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.3.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Returns the JDBCMultiDataSourceRuntimeMBean registered under the specified name, application name, module name and/or component name. ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "JDBCMultiDataSourceRuntimeMBeans");
            currentResult.setValue("since", "12.2.1.3.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion)) {
         mth = JDBCServiceRuntimeMBean.class.getMethod("lookupJDBCDriverRuntimeMBean", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The data source name ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.3.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Returns the JDBCDriverRuntimeMBean registered under the specified name. ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "JDBCDriverRuntimeMBeans");
            currentResult.setValue("since", "12.2.1.3.0");
         }
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
