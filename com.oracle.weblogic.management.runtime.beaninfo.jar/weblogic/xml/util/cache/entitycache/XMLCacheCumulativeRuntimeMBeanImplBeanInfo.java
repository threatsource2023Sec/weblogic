package weblogic.xml.util.cache.entitycache;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.util.Map;
import weblogic.management.runtime.XMLCacheCumulativeRuntimeMBean;

public class XMLCacheCumulativeRuntimeMBeanImplBeanInfo extends XMLCacheMonitorRuntimeMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = XMLCacheCumulativeRuntimeMBean.class;

   public XMLCacheCumulativeRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public XMLCacheCumulativeRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.xml.util.cache.entitycache.XMLCacheCumulativeRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("deprecated", " ");
      beanDescriptor.setValue("exclude", Boolean.TRUE);
      beanDescriptor.setValue("internal", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.xml.util.cache.entitycache");
      String description = (new String("This interface is used to monitor the external XML entity cache.  In particular, the methods of this interface return information about the cumulative activity of the cache since WebLogic Server was started. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.XMLCacheCumulativeRuntimeMBean");
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
