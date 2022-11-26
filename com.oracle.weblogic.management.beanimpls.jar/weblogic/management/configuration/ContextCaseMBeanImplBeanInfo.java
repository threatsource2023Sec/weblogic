package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class ContextCaseMBeanImplBeanInfo extends DeploymentMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ContextCaseMBean.class;

   public ContextCaseMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ContextCaseMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.ContextCaseMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("This mbean defines the mapping between the current context (security principal, group etc) and the request class to use. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.ContextCaseMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (BeanInfoHelper.isVersionCompliant("12.1.3.0", (String)null, this.targetVersion) && !descriptors.containsKey("FairShareRequestClass")) {
         getterName = "getFairShareRequestClass";
         setterName = null;
         currentResult = new PropertyDescriptor("FairShareRequestClass", ContextCaseMBean.class, getterName, setterName);
         descriptors.put("FairShareRequestClass", currentResult);
         currentResult.setValue("description", "Get the fair share request class ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createFairShareRequestClass");
         currentResult.setValue("destroyer", "destroyFairShareRequestClass");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.1.3.0");
      }

      if (!descriptors.containsKey("GroupName")) {
         getterName = "getGroupName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setGroupName";
         }

         currentResult = new PropertyDescriptor("GroupName", ContextCaseMBean.class, getterName, setterName);
         descriptors.put("GroupName", currentResult);
         currentResult.setValue("description", "<p>The name of the user group whose requests are to be processed by the request class with the name specified in RequestClassName.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RequestClassName")) {
         getterName = "getRequestClassName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRequestClassName";
         }

         currentResult = new PropertyDescriptor("RequestClassName", ContextCaseMBean.class, getterName, setterName);
         descriptors.put("RequestClassName", currentResult);
         currentResult.setValue("description", "<p>The name of the request class to be used for processing requests for the specified user and/or group.</p> ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.1.3.0", (String)null, this.targetVersion) && !descriptors.containsKey("ResponseTimeRequestClass")) {
         getterName = "getResponseTimeRequestClass";
         setterName = null;
         currentResult = new PropertyDescriptor("ResponseTimeRequestClass", ContextCaseMBean.class, getterName, setterName);
         descriptors.put("ResponseTimeRequestClass", currentResult);
         currentResult.setValue("description", "Get the response time request class ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createResponseTimeRequestClass");
         currentResult.setValue("destroyer", "destroyResponseTimeRequestClass");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.1.3.0");
      }

      if (!descriptors.containsKey("UserName")) {
         getterName = "getUserName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUserName";
         }

         currentResult = new PropertyDescriptor("UserName", ContextCaseMBean.class, getterName, setterName);
         descriptors.put("UserName", currentResult);
         currentResult.setValue("description", "<p>The name of the user whose requests are to be processed by the request class with the name specified in RequestClassName.</p> ");
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      MethodDescriptor currentResult;
      Method mth;
      ParameterDescriptor[] parameterDescriptors;
      String methodKey;
      if (BeanInfoHelper.isVersionCompliant("12.1.3.0", (String)null, this.targetVersion)) {
         mth = ContextCaseMBean.class.getMethod("createResponseTimeRequestClass", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.1.3.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "This is the factory method for ResponseTimeRequestClasss ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "ResponseTimeRequestClass");
            currentResult.setValue("since", "12.1.3.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.1.3.0", (String)null, this.targetVersion)) {
         mth = ContextCaseMBean.class.getMethod("destroyResponseTimeRequestClass", ResponseTimeRequestClassMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("bean", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.1.3.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Destroys and removes a ResponseTimeRequestClass ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "ResponseTimeRequestClass");
            currentResult.setValue("since", "12.1.3.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.1.3.0", (String)null, this.targetVersion)) {
         mth = ContextCaseMBean.class.getMethod("createFairShareRequestClass", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.1.3.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "This is the factory method for FairShareRequestClasses ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "FairShareRequestClass");
            currentResult.setValue("since", "12.1.3.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.1.3.0", (String)null, this.targetVersion)) {
         mth = ContextCaseMBean.class.getMethod("destroyFairShareRequestClass", FairShareRequestClassMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("bean", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.1.3.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Destroys and removes a FairShareRequestClass ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "FairShareRequestClass");
            currentResult.setValue("since", "12.1.3.0");
         }
      }

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
