package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class XMLEntityCacheMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = XMLEntityCacheMBean.class;

   public XMLEntityCacheMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public XMLEntityCacheMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.XMLEntityCacheMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("Configure the behavior of JAXP (Java API for XML Parsing) in the server. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.XMLEntityCacheMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("CacheDiskSize")) {
         getterName = "getCacheDiskSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCacheDiskSize";
         }

         currentResult = new PropertyDescriptor("CacheDiskSize", XMLEntityCacheMBean.class, getterName, setterName);
         descriptors.put("CacheDiskSize", currentResult);
         currentResult.setValue("description", "<p>The disk size, in MB, of the persistent disk cache. The default value is 5 MB.</p>  <p>Return the disk size in MBytes of the cache.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(5));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CacheLocation")) {
         getterName = "getCacheLocation";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCacheLocation";
         }

         currentResult = new PropertyDescriptor("CacheLocation", XMLEntityCacheMBean.class, getterName, setterName);
         descriptors.put("CacheLocation", currentResult);
         currentResult.setValue("description", "<p>Provides the path name for the persistent cache files.</p> ");
         setPropertyDescriptorDefault(currentResult, "xmlcache");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CacheMemorySize")) {
         getterName = "getCacheMemorySize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCacheMemorySize";
         }

         currentResult = new PropertyDescriptor("CacheMemorySize", XMLEntityCacheMBean.class, getterName, setterName);
         descriptors.put("CacheMemorySize", currentResult);
         currentResult.setValue("description", "<p>The memory size, in KB, of the cache. The default value is 500 KB.</p>  <p>Return the memory size in KBytes of the cache.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(500));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CacheTimeoutInterval")) {
         getterName = "getCacheTimeoutInterval";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCacheTimeoutInterval";
         }

         currentResult = new PropertyDescriptor("CacheTimeoutInterval", XMLEntityCacheMBean.class, getterName, setterName);
         descriptors.put("CacheTimeoutInterval", currentResult);
         currentResult.setValue("description", "<p>The default timeout interval, in seconds, for the cache. The default value is 120 seconds.</p>  <p>Return the default timeout interval in seconds for the cache.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(120));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxSize")) {
         getterName = "getMaxSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxSize";
         }

         currentResult = new PropertyDescriptor("MaxSize", XMLEntityCacheMBean.class, getterName, setterName);
         descriptors.put("MaxSize", currentResult);
         currentResult.setValue("description", "<p>Provides the maximum number of entries that can be stored in the cache at any given time.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
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
