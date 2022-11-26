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

public class EjbLocalRefBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = EjbLocalRefBean.class;

   public EjbLocalRefBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public EjbLocalRefBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.EjbLocalRefBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.EjbLocalRefBean");
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

         currentResult = new PropertyDescriptor("Descriptions", EjbLocalRefBean.class, getterName, setterName);
         descriptors.put("Descriptions", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EjbLink")) {
         getterName = "getEjbLink";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEjbLink";
         }

         currentResult = new PropertyDescriptor("EjbLink", EjbLocalRefBean.class, getterName, setterName);
         descriptors.put("EjbLink", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EjbRefName")) {
         getterName = "getEjbRefName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEjbRefName";
         }

         currentResult = new PropertyDescriptor("EjbRefName", EjbLocalRefBean.class, getterName, setterName);
         descriptors.put("EjbRefName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EjbRefType")) {
         getterName = "getEjbRefType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEjbRefType";
         }

         currentResult = new PropertyDescriptor("EjbRefType", EjbLocalRefBean.class, getterName, setterName);
         descriptors.put("EjbRefType", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("legalValues", new Object[]{"Entity", "Session"});
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Id")) {
         getterName = "getId";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setId";
         }

         currentResult = new PropertyDescriptor("Id", EjbLocalRefBean.class, getterName, setterName);
         descriptors.put("Id", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InjectionTargets")) {
         getterName = "getInjectionTargets";
         setterName = null;
         currentResult = new PropertyDescriptor("InjectionTargets", EjbLocalRefBean.class, getterName, setterName);
         descriptors.put("InjectionTargets", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyInjectionTarget");
         currentResult.setValue("creator", "createInjectionTarget");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Local")) {
         getterName = "getLocal";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLocal";
         }

         currentResult = new PropertyDescriptor("Local", EjbLocalRefBean.class, getterName, setterName);
         descriptors.put("Local", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LocalHome")) {
         getterName = "getLocalHome";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLocalHome";
         }

         currentResult = new PropertyDescriptor("LocalHome", EjbLocalRefBean.class, getterName, setterName);
         descriptors.put("LocalHome", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LookupName")) {
         getterName = "getLookupName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLookupName";
         }

         currentResult = new PropertyDescriptor("LookupName", EjbLocalRefBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("MappedName", EjbLocalRefBean.class, getterName, setterName);
         descriptors.put("MappedName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = EjbLocalRefBean.class.getMethod("createInjectionTarget");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "InjectionTargets");
      }

      mth = EjbLocalRefBean.class.getMethod("destroyInjectionTarget", InjectionTargetBean.class);
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
      Method mth = EjbLocalRefBean.class.getMethod("addDescription", String.class);
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "Descriptions");
      }

      mth = EjbLocalRefBean.class.getMethod("removeDescription", String.class);
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
