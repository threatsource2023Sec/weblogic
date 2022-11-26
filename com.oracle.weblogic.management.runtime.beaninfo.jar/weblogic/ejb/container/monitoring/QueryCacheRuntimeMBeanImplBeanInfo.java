package weblogic.ejb.container.monitoring;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.runtime.QueryCacheRuntimeMBean;

public class QueryCacheRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = QueryCacheRuntimeMBean.class;

   public QueryCacheRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public QueryCacheRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.ejb.container.monitoring.QueryCacheRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.ejb.container.monitoring");
      String description = (new String("<p>This interface contains accessor methods for all query cache runtime information collected for an EJB. The cache miss counts need some explaining. A query-cache miss can occur due to one of the following reasons:</p>  <ul> <li>The query result was not found in the query-cache</li> <li>The query result has timed out</li> <li>A bean which satisfies the query wasnot found in the entity cache</li> <li>A query with relationship-caching turned on did not find the related-beans query result</li> <li>A query which loads multiple EJBs could not load one or more of them</li> </ul>  <p>To better aid tuning, there are separate counters provided for each of the last four of the above causes. The fifth counter is a total cache miss counter. This counter takes into account all five causes of a cache miss.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.QueryCacheRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("CacheAccessCount")) {
         getterName = "getCacheAccessCount";
         setterName = null;
         currentResult = new PropertyDescriptor("CacheAccessCount", QueryCacheRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CacheAccessCount", currentResult);
         currentResult.setValue("description", "<p>Provides a count of the number of accesses of the query cache for this EJB.</p>  <p>Returns the number of accesses of the query cache for this EJB.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CacheHitCount")) {
         getterName = "getCacheHitCount";
         setterName = null;
         currentResult = new PropertyDescriptor("CacheHitCount", QueryCacheRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CacheHitCount", currentResult);
         currentResult.setValue("description", "<p>Provides a count of the number of cache hits of the query cache for this EJB.</p>  <p>Returns the number of cache hits of the query cache for this EJB.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CacheMissByBeanEvictionCount")) {
         getterName = "getCacheMissByBeanEvictionCount";
         setterName = null;
         currentResult = new PropertyDescriptor("CacheMissByBeanEvictionCount", QueryCacheRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CacheMissByBeanEvictionCount", currentResult);
         currentResult.setValue("description", "<p>Provides a count of the number of cache misses of the query cache for this EJB because corresponding beans were not found in the entity cache.</p>  <p>Returns the number of times a cache miss occurred for this EJB because corresponding beans were not found in the entity cache.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CacheMissByDependentQueryMissCount")) {
         getterName = "getCacheMissByDependentQueryMissCount";
         setterName = null;
         currentResult = new PropertyDescriptor("CacheMissByDependentQueryMissCount", QueryCacheRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CacheMissByDependentQueryMissCount", currentResult);
         currentResult.setValue("description", "<p>Provides a count of the number of cache misses of the query cache for this EJB because a dependent query was not found in another EJB's query cache.</p>  <p>Returns the number of times a cache miss occurred for this EJB because a dependent query was not found in another EJB's query cache.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CacheMissByRelatedQueryMissCount")) {
         getterName = "getCacheMissByRelatedQueryMissCount";
         setterName = null;
         currentResult = new PropertyDescriptor("CacheMissByRelatedQueryMissCount", QueryCacheRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CacheMissByRelatedQueryMissCount", currentResult);
         currentResult.setValue("description", "<p>Provides a count of the number of cache misses of the query cache for this EJB because a related query was not found in another EJB's query cache.</p>  <p>Returns the number of times a cache miss occurred for this EJB because a related query was not found in another EJB's query cache.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CacheMissByTimeoutCount")) {
         getterName = "getCacheMissByTimeoutCount";
         setterName = null;
         currentResult = new PropertyDescriptor("CacheMissByTimeoutCount", QueryCacheRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CacheMissByTimeoutCount", currentResult);
         currentResult.setValue("description", "<p>Provides a count of the number of cache misses of the query cache for this EJB due to query results timing out.</p>  <p>Returns the number of cache misses due to query result timeout for this EJB.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalCacheMissCount")) {
         getterName = "getTotalCacheMissCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalCacheMissCount", QueryCacheRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalCacheMissCount", currentResult);
         currentResult.setValue("description", "<p>Provides a count of the total number of cache misses of the query cache for this EJB.</p>  <p>Returns the total number of cache misses of the query cache for this EJB.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalCachedQueriesCount")) {
         getterName = "getTotalCachedQueriesCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalCachedQueriesCount", QueryCacheRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalCachedQueriesCount", currentResult);
         currentResult.setValue("description", "<p>Provides a count of the total number of query results for this EJB currently in the query cache.</p>  <p>Returns the total number of query results for this EJB currently in the EJB cache.</p> ");
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
