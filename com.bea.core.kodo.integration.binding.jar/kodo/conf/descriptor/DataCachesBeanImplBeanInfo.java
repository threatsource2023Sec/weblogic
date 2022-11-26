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

public class DataCachesBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = DataCachesBean.class;

   public DataCachesBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public DataCachesBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("kodo.conf.descriptor.DataCachesBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("configurable", Boolean.TRUE);
      beanDescriptor.setValue("package", "kodo.conf.descriptor");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "kodo.conf.descriptor.DataCachesBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("CustomDataCache")) {
         getterName = "getCustomDataCache";
         setterName = null;
         currentResult = new PropertyDescriptor("CustomDataCache", DataCachesBean.class, getterName, (String)setterName);
         descriptors.put("CustomDataCache", currentResult);
         currentResult.setValue("description", "Return custom datacache implementations to use. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyCustomDataCache");
         currentResult.setValue("creator", "createCustomDataCache");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultDataCache")) {
         getterName = "getDefaultDataCache";
         setterName = null;
         currentResult = new PropertyDescriptor("DefaultDataCache", DataCachesBean.class, getterName, (String)setterName);
         descriptors.put("DefaultDataCache", currentResult);
         currentResult.setValue("description", "Return default datacache implementations configured. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyDefaultDataCache");
         currentResult.setValue("creator", "createDefaultDataCache");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("GemFireDataCache")) {
         getterName = "getGemFireDataCache";
         setterName = null;
         currentResult = new PropertyDescriptor("GemFireDataCache", DataCachesBean.class, getterName, (String)setterName);
         descriptors.put("GemFireDataCache", currentResult);
         currentResult.setValue("description", "Return GemFire datacache implementations configured. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createGemFireDataCache");
         currentResult.setValue("destroyer", "destroyGemFireDataCache");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("KodoConcurrentDataCache")) {
         getterName = "getKodoConcurrentDataCache";
         setterName = null;
         currentResult = new PropertyDescriptor("KodoConcurrentDataCache", DataCachesBean.class, getterName, (String)setterName);
         descriptors.put("KodoConcurrentDataCache", currentResult);
         currentResult.setValue("description", "Return Kodo high-concurrency datacache implementations configured. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyKodoConcurrentDataCache");
         currentResult.setValue("creator", "createKodoConcurrentDataCache");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LRUDataCache")) {
         getterName = "getLRUDataCache";
         setterName = null;
         currentResult = new PropertyDescriptor("LRUDataCache", DataCachesBean.class, getterName, (String)setterName);
         descriptors.put("LRUDataCache", currentResult);
         currentResult.setValue("description", "Return LRU strategy datacache implementations configured. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createLRUDataCache");
         currentResult.setValue("destroyer", "destroyLRUDataCache");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TangosolDataCache")) {
         getterName = "getTangosolDataCache";
         setterName = null;
         currentResult = new PropertyDescriptor("TangosolDataCache", DataCachesBean.class, getterName, (String)setterName);
         descriptors.put("TangosolDataCache", currentResult);
         currentResult.setValue("description", "Return Tangosol datacache implementations configured. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createTangosolDataCache");
         currentResult.setValue("destroyer", "destroyTangosolDataCache");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = DataCachesBean.class.getMethod("createDefaultDataCache", String.class);
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DefaultDataCache");
      }

      mth = DataCachesBean.class.getMethod("destroyDefaultDataCache", DefaultDataCacheBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DefaultDataCache");
      }

      mth = DataCachesBean.class.getMethod("createKodoConcurrentDataCache", String.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "KodoConcurrentDataCache");
      }

      mth = DataCachesBean.class.getMethod("destroyKodoConcurrentDataCache", KodoConcurrentDataCacheBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "KodoConcurrentDataCache");
      }

      mth = DataCachesBean.class.getMethod("createGemFireDataCache", String.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "GemFireDataCache");
      }

      mth = DataCachesBean.class.getMethod("destroyGemFireDataCache", GemFireDataCacheBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "GemFireDataCache");
      }

      mth = DataCachesBean.class.getMethod("createLRUDataCache", String.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "LRUDataCache");
      }

      mth = DataCachesBean.class.getMethod("destroyLRUDataCache", LRUDataCacheBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "LRUDataCache");
      }

      mth = DataCachesBean.class.getMethod("createTangosolDataCache", String.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "TangosolDataCache");
      }

      mth = DataCachesBean.class.getMethod("destroyTangosolDataCache", TangosolDataCacheBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "TangosolDataCache");
      }

      mth = DataCachesBean.class.getMethod("createCustomDataCache", String.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CustomDataCache");
      }

      mth = DataCachesBean.class.getMethod("destroyCustomDataCache", CustomDataCacheBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CustomDataCache");
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = DataCachesBean.class.getMethod("getDataCacheTypes");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = DataCachesBean.class.getMethod("getDataCaches");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = DataCachesBean.class.getMethod("createDataCache", Class.class, String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("type", "Class type of the data cache "), createParameterDescriptor("name", "String name property of the data cache ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = DataCachesBean.class.getMethod("lookupDataCache", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "String name property of the data cache ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = DataCachesBean.class.getMethod("destroyDataCache", DataCacheBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("destroy", "DataCacheBean ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
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
