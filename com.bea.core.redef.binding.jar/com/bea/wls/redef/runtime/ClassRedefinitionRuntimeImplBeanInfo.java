package com.bea.wls.redef.runtime;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class ClassRedefinitionRuntimeImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ClassRedefinitionRuntimeMBean.class;

   public ClassRedefinitionRuntimeImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ClassRedefinitionRuntimeImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("com.bea.wls.redef.runtime.ClassRedefinitionRuntimeImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "10.3.0.0");
      beanDescriptor.setValue("package", "com.bea.wls.redef.runtime");
      String description = (new String("<p> This interface provides functionality to monitor the class redefinition process. It also provides methods to explicitly initiate class redefinition. </p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "com.bea.wls.redef.runtime.ClassRedefinitionRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("ClassRedefinitionCount")) {
         getterName = "getClassRedefinitionCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ClassRedefinitionCount", ClassRedefinitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ClassRedefinitionCount", currentResult);
         currentResult.setValue("description", "<p>Number of class redefinitions executed so far.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ClassRedefinitionTasks")) {
         getterName = "getClassRedefinitionTasks";
         setterName = null;
         currentResult = new PropertyDescriptor("ClassRedefinitionTasks", ClassRedefinitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ClassRedefinitionTasks", currentResult);
         currentResult.setValue("description", "<p>Return the array of class redefinition tasks.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FailedClassRedefinitionCount")) {
         getterName = "getFailedClassRedefinitionCount";
         setterName = null;
         currentResult = new PropertyDescriptor("FailedClassRedefinitionCount", ClassRedefinitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("FailedClassRedefinitionCount", currentResult);
         currentResult.setValue("description", "<p>Number of failed redefinition operations since the application was deployed.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ProcessedClassesCount")) {
         getterName = "getProcessedClassesCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ProcessedClassesCount", ClassRedefinitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ProcessedClassesCount", currentResult);
         currentResult.setValue("description", "<p>Number of classes processed since the application was deployed.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalClassRedefinitionTime")) {
         getterName = "getTotalClassRedefinitionTime";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalClassRedefinitionTime", ClassRedefinitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalClassRedefinitionTime", currentResult);
         currentResult.setValue("description", "<p>Total time spent processing classes, in nano seconds since the application was deployed. </p> ");
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
      Method mth = ClassRedefinitionRuntimeMBean.class.getMethod("redefineClasses");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Initiate a class redefinition cycle, which will identify classes which may have changed and then attempt to redefine them. Class redefinition will be performed asynchronously. The progress of the class redefinition process can be monitored with the returned task. </p> ");
         currentResult.setValue("role", "operation");
      }

      mth = ClassRedefinitionRuntimeMBean.class.getMethod("redefineClasses", String.class, String[].class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("moduleName", "Module from which the classes are to be redefined. "), createParameterDescriptor("classNames", "Classes to be redefined. ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Initiate a class redefinition cycle, to redefine only the specified classes from given module. Class redefinition will be performed asynchronously. The progress of the class redefinition process can be monitored with the returned task. If specified module name is null, any classes within the application (scoped to any modules as well as globally scoped classes) which are included in the list will be redefined. </p> ");
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
