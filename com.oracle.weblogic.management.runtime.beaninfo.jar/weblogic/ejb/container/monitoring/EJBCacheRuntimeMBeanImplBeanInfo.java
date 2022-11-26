package weblogic.ejb.container.monitoring;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.EJBCacheRuntimeMBean;

public class EJBCacheRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = EJBCacheRuntimeMBean.class;

   public EJBCacheRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public EJBCacheRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.ejb.container.monitoring.EJBCacheRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.ejb.container.monitoring");
      String description = (new String("This interface contains accessor methods for all cache runtime information collected for an EJB.  Note that the sum of the cacheHitCount and cacheMissCount may not add up to the cacheAccessCount in a running server because these metrics are retrieved using multiple calls and the counts could change between the calls. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.EJBCacheRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("ActivationCount")) {
         getterName = "getActivationCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ActivationCount", EJBCacheRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ActivationCount", currentResult);
         currentResult.setValue("description", "<p>Provides a count of the total number of beans from this EJB Home that have been activated.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CacheAccessCount")) {
         getterName = "getCacheAccessCount";
         setterName = null;
         currentResult = new PropertyDescriptor("CacheAccessCount", EJBCacheRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CacheAccessCount", currentResult);
         currentResult.setValue("description", "<p>Provides a count of the total number of attempts to access a bean from the cache.</p>  <p> The sum of the Cache Hit Count and Cache Miss Count may not add up to the cacheAccessCount in a running server because these metrics are retrieved using multiple calls and the counts could change between the calls.</p>* ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CacheHitCount")) {
         getterName = "getCacheHitCount";
         setterName = null;
         currentResult = new PropertyDescriptor("CacheHitCount", EJBCacheRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CacheHitCount", currentResult);
         currentResult.setValue("description", "<p>Provides a count of the total number of times an attempt to access a bean from the cache succeeded.</p>  <p> The sum of the Cache Hit Count and Cache Miss Count may not add up to the cacheAccessCount in a running server because these metrics are retrieved using multiple calls and the counts could change between the calls.</p> ");
         currentResult.setValue("deprecated", "28-Aug-2002.  The cache hit count can be calculated by  subtracting the cache miss count from the cache access count. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CacheMissCount")) {
         getterName = "getCacheMissCount";
         setterName = null;
         currentResult = new PropertyDescriptor("CacheMissCount", EJBCacheRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CacheMissCount", currentResult);
         currentResult.setValue("description", "<p>Provides a count of the total number of times an attempt to access a bean from the cache failed.</p>  <p> The sum of the Cache Hit Count and Cache Miss Count may not add up to the cacheAccessCount in a running server because these metrics are retrieved using multiple calls and the counts could change between the calls.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CachedBeansCurrentCount")) {
         getterName = "getCachedBeansCurrentCount";
         setterName = null;
         currentResult = new PropertyDescriptor("CachedBeansCurrentCount", EJBCacheRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CachedBeansCurrentCount", currentResult);
         currentResult.setValue("description", "<p>Provides a count of the total number of beans from this EJB Home currently in the EJB cache.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PassivationCount")) {
         getterName = "getPassivationCount";
         setterName = null;
         currentResult = new PropertyDescriptor("PassivationCount", EJBCacheRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PassivationCount", currentResult);
         currentResult.setValue("description", "<p>Provides a count of the total number of beans from this EJB Home that have been passivated.</p> ");
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
      Method mth = EJBCacheRuntimeMBean.class.getMethod("reInitializeCacheAndPools");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Instructs the cache to initialize itself all of its associated pools to their configured initial sizes.</p> ");
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
