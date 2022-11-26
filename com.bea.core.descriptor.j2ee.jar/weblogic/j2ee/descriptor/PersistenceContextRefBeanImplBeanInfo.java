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

public class PersistenceContextRefBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = PersistenceContextRefBean.class;

   public PersistenceContextRefBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public PersistenceContextRefBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.PersistenceContextRefBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.PersistenceContextRefBean");
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
         currentResult = new PropertyDescriptor("Descriptions", PersistenceContextRefBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("Id", PersistenceContextRefBean.class, getterName, setterName);
         descriptors.put("Id", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InjectionTargets")) {
         getterName = "getInjectionTargets";
         setterName = null;
         currentResult = new PropertyDescriptor("InjectionTargets", PersistenceContextRefBean.class, getterName, setterName);
         descriptors.put("InjectionTargets", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyInjectionTarget");
         currentResult.setValue("creator", "createInjectionTarget");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MappedName")) {
         getterName = "getMappedName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMappedName";
         }

         currentResult = new PropertyDescriptor("MappedName", PersistenceContextRefBean.class, getterName, setterName);
         descriptors.put("MappedName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PersistenceContextRefName")) {
         getterName = "getPersistenceContextRefName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPersistenceContextRefName";
         }

         currentResult = new PropertyDescriptor("PersistenceContextRefName", PersistenceContextRefBean.class, getterName, setterName);
         descriptors.put("PersistenceContextRefName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PersistenceContextType")) {
         getterName = "getPersistenceContextType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPersistenceContextType";
         }

         currentResult = new PropertyDescriptor("PersistenceContextType", PersistenceContextRefBean.class, getterName, setterName);
         descriptors.put("PersistenceContextType", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "Transaction");
         currentResult.setValue("legalValues", new Object[]{"Transaction", "Extended"});
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PersistenceProperties")) {
         getterName = "getPersistenceProperties";
         setterName = null;
         currentResult = new PropertyDescriptor("PersistenceProperties", PersistenceContextRefBean.class, getterName, setterName);
         descriptors.put("PersistenceProperties", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyPersistenceProperty");
         currentResult.setValue("creator", "createPersistenceProperty");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PersistenceUnitName")) {
         getterName = "getPersistenceUnitName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPersistenceUnitName";
         }

         currentResult = new PropertyDescriptor("PersistenceUnitName", PersistenceContextRefBean.class, getterName, setterName);
         descriptors.put("PersistenceUnitName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SynchronizationType")) {
         getterName = "getSynchronizationType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSynchronizationType";
         }

         currentResult = new PropertyDescriptor("SynchronizationType", PersistenceContextRefBean.class, getterName, setterName);
         descriptors.put("SynchronizationType", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "Synchronized");
         currentResult.setValue("legalValues", new Object[]{"Synchronized", "Unsynchronized"});
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = PersistenceContextRefBean.class.getMethod("createPersistenceProperty");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "PersistenceProperties");
      }

      mth = PersistenceContextRefBean.class.getMethod("destroyPersistenceProperty", JavaEEPropertyBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "PersistenceProperties");
      }

      mth = PersistenceContextRefBean.class.getMethod("createInjectionTarget");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "InjectionTargets");
      }

      mth = PersistenceContextRefBean.class.getMethod("destroyInjectionTarget", InjectionTargetBean.class);
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
      Method mth = PersistenceContextRefBean.class.getMethod("addDescription", String.class);
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "Descriptions");
      }

      mth = PersistenceContextRefBean.class.getMethod("removeDescription", String.class);
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
