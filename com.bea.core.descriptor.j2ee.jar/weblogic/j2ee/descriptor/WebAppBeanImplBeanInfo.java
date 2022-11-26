package weblogic.j2ee.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class WebAppBeanImplBeanInfo extends WebAppBaseBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WebAppBean.class;

   public WebAppBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WebAppBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.WebAppBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("exclude", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.WebAppBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("AbsoluteOrderings")) {
         getterName = "getAbsoluteOrderings";
         setterName = null;
         currentResult = new PropertyDescriptor("AbsoluteOrderings", WebAppBean.class, getterName, setterName);
         descriptors.put("AbsoluteOrderings", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createAbsoluteOrdering");
         currentResult.setValue("destroyer", "destroyAbsoluteOrdering");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultContextPaths")) {
         getterName = "getDefaultContextPaths";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultContextPaths";
         }

         currentResult = new PropertyDescriptor("DefaultContextPaths", WebAppBean.class, getterName, setterName);
         descriptors.put("DefaultContextPaths", currentResult);
         currentResult.setValue("description", ">    * @mergeRule ignoreTarget >    * @since 12.3.1.0.0 > ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("DenyUncoveredHttpMethods")) {
         getterName = "getDenyUncoveredHttpMethods";
         setterName = null;
         currentResult = new PropertyDescriptor("DenyUncoveredHttpMethods", WebAppBean.class, getterName, setterName);
         descriptors.put("DenyUncoveredHttpMethods", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createDenyUncoveredHttpMethods");
         currentResult.setValue("destroyer", "destroyDenyUncoveredHttpMethods");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("JavaEEModuleName")) {
         getterName = "getJavaEEModuleName";
         setterName = null;
         currentResult = new PropertyDescriptor("JavaEEModuleName", WebAppBean.class, getterName, setterName);
         descriptors.put("JavaEEModuleName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
      }

      if (!descriptors.containsKey("ModuleNames")) {
         getterName = "getModuleNames";
         setterName = null;
         currentResult = new PropertyDescriptor("ModuleNames", WebAppBean.class, getterName, setterName);
         descriptors.put("ModuleNames", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RequestCharacterEncodings")) {
         getterName = "getRequestCharacterEncodings";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRequestCharacterEncodings";
         }

         currentResult = new PropertyDescriptor("RequestCharacterEncodings", WebAppBean.class, getterName, setterName);
         descriptors.put("RequestCharacterEncodings", currentResult);
         currentResult.setValue("description", ">    * @since 12.3.1.0.0 > ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ResponseCharacterEncodings")) {
         getterName = "getResponseCharacterEncodings";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setResponseCharacterEncodings";
         }

         currentResult = new PropertyDescriptor("ResponseCharacterEncodings", WebAppBean.class, getterName, setterName);
         descriptors.put("ResponseCharacterEncodings", currentResult);
         currentResult.setValue("description", ">    * @since 12.3.1.0.0 > ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Version")) {
         getterName = "getVersion";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setVersion";
         }

         currentResult = new PropertyDescriptor("Version", WebAppBean.class, getterName, setterName);
         descriptors.put("Version", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "4.0");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      MethodDescriptor currentResult;
      Method mth;
      String methodKey;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = WebAppBean.class.getMethod("createDenyUncoveredHttpMethods");
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "DenyUncoveredHttpMethods");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = WebAppBean.class.getMethod("destroyDenyUncoveredHttpMethods", EmptyBean.class);
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "DenyUncoveredHttpMethods");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      mth = WebAppBean.class.getMethod("createAbsoluteOrdering");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "AbsoluteOrderings");
      }

      mth = WebAppBean.class.getMethod("destroyAbsoluteOrdering", AbsoluteOrderingBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "AbsoluteOrderings");
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = WebAppBean.class.getMethod("addDefaultContextPath", String.class);
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "DefaultContextPaths");
      }

      mth = WebAppBean.class.getMethod("removeDefaultContextPath", String.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "DefaultContextPaths");
      }

      mth = WebAppBean.class.getMethod("addRequestCharacterEncoding", String.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "RequestCharacterEncodings");
      }

      mth = WebAppBean.class.getMethod("removeRequestCharacterEncoding", String.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "RequestCharacterEncodings");
      }

      mth = WebAppBean.class.getMethod("addResponseCharacterEncoding", String.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "ResponseCharacterEncodings");
      }

      mth = WebAppBean.class.getMethod("removeResponseCharacterEncoding", String.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "ResponseCharacterEncodings");
      }

   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         Method mth = WebAppBean.class.getMethod("isDenyUncoveredHttpMethods");
         String methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            MethodDescriptor currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.0.0");
         }
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
