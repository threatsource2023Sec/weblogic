package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class EntityMappingBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = EntityMappingBean.class;

   public EntityMappingBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public EntityMappingBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.EntityMappingBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("exclude", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.EntityMappingBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("CacheTimeoutInterval")) {
         getterName = "getCacheTimeoutInterval";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCacheTimeoutInterval";
         }

         currentResult = new PropertyDescriptor("CacheTimeoutInterval", EntityMappingBean.class, getterName, setterName);
         descriptors.put("CacheTimeoutInterval", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EntityMappingName")) {
         getterName = "getEntityMappingName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEntityMappingName";
         }

         currentResult = new PropertyDescriptor("EntityMappingName", EntityMappingBean.class, getterName, setterName);
         descriptors.put("EntityMappingName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EntityUri")) {
         getterName = "getEntityUri";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEntityUri";
         }

         currentResult = new PropertyDescriptor("EntityUri", EntityMappingBean.class, getterName, setterName);
         descriptors.put("EntityUri", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PublicId")) {
         getterName = "getPublicId";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPublicId";
         }

         currentResult = new PropertyDescriptor("PublicId", EntityMappingBean.class, getterName, setterName);
         descriptors.put("PublicId", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SystemId")) {
         getterName = "getSystemId";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSystemId";
         }

         currentResult = new PropertyDescriptor("SystemId", EntityMappingBean.class, getterName, setterName);
         descriptors.put("SystemId", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WhenToCache")) {
         getterName = "getWhenToCache";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setWhenToCache";
         }

         currentResult = new PropertyDescriptor("WhenToCache", EntityMappingBean.class, getterName, setterName);
         descriptors.put("WhenToCache", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
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
