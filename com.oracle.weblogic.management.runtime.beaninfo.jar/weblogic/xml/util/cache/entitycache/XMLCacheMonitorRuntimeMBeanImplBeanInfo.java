package weblogic.xml.util.cache.entitycache;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.util.Map;
import weblogic.cache.management.CacheMonitorRuntimeMBeanImplBeanInfo;
import weblogic.management.runtime.XMLCacheMonitorRuntimeMBean;

public class XMLCacheMonitorRuntimeMBeanImplBeanInfo extends CacheMonitorRuntimeMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = XMLCacheMonitorRuntimeMBean.class;

   public XMLCacheMonitorRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public XMLCacheMonitorRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.xml.util.cache.entitycache.XMLCacheMonitorRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("deprecated", " ");
      beanDescriptor.setValue("exclude", Boolean.TRUE);
      beanDescriptor.setValue("internal", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.xml.util.cache.entitycache");
      String description = (new String("This interface is used to monitor the external XML entity cache.  In particular, you can use the methods of the interface to return information about the current state of the cache. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.XMLCacheMonitorRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
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
      super.buildMethodDescriptors(descriptors);
   }

   protected void buildEventSetDescriptors(Map descriptors) throws IntrospectionException {
   }
}
