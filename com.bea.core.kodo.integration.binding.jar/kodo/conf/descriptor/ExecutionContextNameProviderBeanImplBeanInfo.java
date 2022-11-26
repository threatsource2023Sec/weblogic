package kodo.conf.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class ExecutionContextNameProviderBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = ExecutionContextNameProviderBean.class;

   public ExecutionContextNameProviderBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ExecutionContextNameProviderBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("kodo.conf.descriptor.ExecutionContextNameProviderBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("configurable", Boolean.TRUE);
      beanDescriptor.setValue("package", "kodo.conf.descriptor");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "kodo.conf.descriptor.ExecutionContextNameProviderBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("StackExecutionContextNameProvider")) {
         getterName = "getStackExecutionContextNameProvider";
         setterName = null;
         currentResult = new PropertyDescriptor("StackExecutionContextNameProvider", ExecutionContextNameProviderBean.class, getterName, (String)setterName);
         descriptors.put("StackExecutionContextNameProvider", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createStackExecutionContextNameProvider");
         currentResult.setValue("destroyer", "destroyStackExecutionContextNameProvider");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TransactionNameExecutionContextNameProvider")) {
         getterName = "getTransactionNameExecutionContextNameProvider";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionNameExecutionContextNameProvider", ExecutionContextNameProviderBean.class, getterName, (String)setterName);
         descriptors.put("TransactionNameExecutionContextNameProvider", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createTransactionNameExecutionContextNameProvider");
         currentResult.setValue("destroyer", "destroyTransactionNameExecutionContextNameProvider");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UserObjectExecutionContextNameProvider")) {
         getterName = "getUserObjectExecutionContextNameProvider";
         setterName = null;
         currentResult = new PropertyDescriptor("UserObjectExecutionContextNameProvider", ExecutionContextNameProviderBean.class, getterName, (String)setterName);
         descriptors.put("UserObjectExecutionContextNameProvider", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyUserObjectExecutionContextNameProvider");
         currentResult.setValue("creator", "createUserObjectExecutionContextNameProvider");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ExecutionContextNameProviderBean.class.getMethod("createStackExecutionContextNameProvider");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "StackExecutionContextNameProvider");
      }

      mth = ExecutionContextNameProviderBean.class.getMethod("destroyStackExecutionContextNameProvider");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "StackExecutionContextNameProvider");
      }

      mth = ExecutionContextNameProviderBean.class.getMethod("createTransactionNameExecutionContextNameProvider");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "TransactionNameExecutionContextNameProvider");
      }

      mth = ExecutionContextNameProviderBean.class.getMethod("destroyTransactionNameExecutionContextNameProvider");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "TransactionNameExecutionContextNameProvider");
      }

      mth = ExecutionContextNameProviderBean.class.getMethod("createUserObjectExecutionContextNameProvider");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "UserObjectExecutionContextNameProvider");
      }

      mth = ExecutionContextNameProviderBean.class.getMethod("destroyUserObjectExecutionContextNameProvider");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "UserObjectExecutionContextNameProvider");
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ExecutionContextNameProviderBean.class.getMethod("getExecutionContextNameProviderTypes");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = ExecutionContextNameProviderBean.class.getMethod("getExecutionContextNameProvider");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = ExecutionContextNameProviderBean.class.getMethod("createExecutionContextNameProvider", Class.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("type", "Class - Provider type to be created ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = ExecutionContextNameProviderBean.class.getMethod("destroyExecutionContextNameProvider");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
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
