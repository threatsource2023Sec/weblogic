package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class ApplicationEntityCacheBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = ApplicationEntityCacheBean.class;

   public ApplicationEntityCacheBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ApplicationEntityCacheBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.ApplicationEntityCacheBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.ApplicationEntityCacheBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("CachingStrategy")) {
         getterName = "getCachingStrategy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCachingStrategy";
         }

         currentResult = new PropertyDescriptor("CachingStrategy", ApplicationEntityCacheBean.class, getterName, setterName);
         descriptors.put("CachingStrategy", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "MultiVersion");
         currentResult.setValue("legalValues", new Object[]{"Exclusive", "MultiVersion"});
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EntityCacheName")) {
         getterName = "getEntityCacheName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEntityCacheName";
         }

         currentResult = new PropertyDescriptor("EntityCacheName", ApplicationEntityCacheBean.class, getterName, setterName);
         descriptors.put("EntityCacheName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxBeansInCache")) {
         getterName = "getMaxBeansInCache";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxBeansInCache";
         }

         currentResult = new PropertyDescriptor("MaxBeansInCache", ApplicationEntityCacheBean.class, getterName, setterName);
         descriptors.put("MaxBeansInCache", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(1000));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxCacheSize")) {
         getterName = "getMaxCacheSize";
         setterName = null;
         currentResult = new PropertyDescriptor("MaxCacheSize", ApplicationEntityCacheBean.class, getterName, setterName);
         descriptors.put("MaxCacheSize", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createMaxCacheSize");
         currentResult.setValue("destroyer", "destroyMaxCacheSize");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxQueriesInCache")) {
         getterName = "getMaxQueriesInCache";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxQueriesInCache";
         }

         currentResult = new PropertyDescriptor("MaxQueriesInCache", ApplicationEntityCacheBean.class, getterName, setterName);
         descriptors.put("MaxQueriesInCache", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(100));
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ApplicationEntityCacheBean.class.getMethod("createMaxCacheSize");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "MaxCacheSize");
      }

      mth = ApplicationEntityCacheBean.class.getMethod("destroyMaxCacheSize", MaxCacheSizeBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "MaxCacheSize");
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
