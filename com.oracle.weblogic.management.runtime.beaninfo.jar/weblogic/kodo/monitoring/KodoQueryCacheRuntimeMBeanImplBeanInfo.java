package weblogic.kodo.monitoring;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.KodoQueryCacheRuntimeMBean;

public class KodoQueryCacheRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = KodoQueryCacheRuntimeMBean.class;

   public KodoQueryCacheRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public KodoQueryCacheRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.kodo.monitoring.KodoQueryCacheRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "10.0.0.0");
      beanDescriptor.setValue("package", "weblogic.kodo.monitoring");
      String description = (new String("Base class for all runtime mbeans that provide status of running modules. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.KodoQueryCacheRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("CacheHitCount")) {
         getterName = "getCacheHitCount";
         setterName = null;
         currentResult = new PropertyDescriptor("CacheHitCount", KodoQueryCacheRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CacheHitCount", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CacheHitRatio")) {
         getterName = "getCacheHitRatio";
         setterName = null;
         currentResult = new PropertyDescriptor("CacheHitRatio", KodoQueryCacheRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CacheHitRatio", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CacheMissCount")) {
         getterName = "getCacheMissCount";
         setterName = null;
         currentResult = new PropertyDescriptor("CacheMissCount", KodoQueryCacheRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CacheMissCount", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Statistics")) {
         getterName = "getStatistics";
         setterName = null;
         currentResult = new PropertyDescriptor("Statistics", KodoQueryCacheRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Statistics", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalCurrentEntries")) {
         getterName = "getTotalCurrentEntries";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalCurrentEntries", KodoQueryCacheRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalCurrentEntries", currentResult);
         currentResult.setValue("description", " ");
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
      Method mth = KodoQueryCacheRuntimeMBean.class.getMethod("clear");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
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
