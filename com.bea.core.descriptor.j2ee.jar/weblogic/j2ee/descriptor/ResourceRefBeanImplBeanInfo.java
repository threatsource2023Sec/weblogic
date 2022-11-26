package weblogic.j2ee.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class ResourceRefBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = ResourceRefBean.class;

   public ResourceRefBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ResourceRefBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.ResourceRefBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.ResourceRefBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("Descriptions")) {
         getterName = "getDescriptions";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDescriptions";
         }

         currentResult = new PropertyDescriptor("Descriptions", ResourceRefBean.class, getterName, setterName);
         descriptors.put("Descriptions", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Id")) {
         getterName = "getId";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setId";
         }

         currentResult = new PropertyDescriptor("Id", ResourceRefBean.class, getterName, setterName);
         descriptors.put("Id", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InjectionTargets")) {
         getterName = "getInjectionTargets";
         setterName = null;
         currentResult = new PropertyDescriptor("InjectionTargets", ResourceRefBean.class, getterName, setterName);
         descriptors.put("InjectionTargets", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyInjectionTarget");
         currentResult.setValue("creator", "createInjectionTarget");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LookupName")) {
         getterName = "getLookupName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLookupName";
         }

         currentResult = new PropertyDescriptor("LookupName", ResourceRefBean.class, getterName, setterName);
         descriptors.put("LookupName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MappedName")) {
         getterName = "getMappedName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMappedName";
         }

         currentResult = new PropertyDescriptor("MappedName", ResourceRefBean.class, getterName, setterName);
         descriptors.put("MappedName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ResAuth")) {
         getterName = "getResAuth";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setResAuth";
         }

         currentResult = new PropertyDescriptor("ResAuth", ResourceRefBean.class, getterName, setterName);
         descriptors.put("ResAuth", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "Container");
         currentResult.setValue("legalValues", new Object[]{"Application", "Container"});
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ResRefName")) {
         getterName = "getResRefName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setResRefName";
         }

         currentResult = new PropertyDescriptor("ResRefName", ResourceRefBean.class, getterName, setterName);
         descriptors.put("ResRefName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ResSharingScope")) {
         getterName = "getResSharingScope";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setResSharingScope";
         }

         currentResult = new PropertyDescriptor("ResSharingScope", ResourceRefBean.class, getterName, setterName);
         descriptors.put("ResSharingScope", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "Shareable");
         currentResult.setValue("legalValues", new Object[]{"Shareable", "Unshareable"});
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ResType")) {
         getterName = "getResType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setResType";
         }

         currentResult = new PropertyDescriptor("ResType", ResourceRefBean.class, getterName, setterName);
         descriptors.put("ResType", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ResourceRefBean.class.getMethod("createInjectionTarget");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "InjectionTargets");
      }

      mth = ResourceRefBean.class.getMethod("destroyInjectionTarget", InjectionTargetBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "InjectionTargets");
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ResourceRefBean.class.getMethod("addDescription", String.class);
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "Descriptions");
      }

      mth = ResourceRefBean.class.getMethod("removeDescription", String.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "Descriptions");
      }

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
