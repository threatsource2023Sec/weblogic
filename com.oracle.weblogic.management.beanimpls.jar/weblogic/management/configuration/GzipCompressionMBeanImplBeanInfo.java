package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class GzipCompressionMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = GzipCompressionMBean.class;

   public GzipCompressionMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public GzipCompressionMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.GzipCompressionMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p> This MBean is used to specify domain-wide default values for GZIP compression support. In general, these properties can be overridden for a specific Web application (in the weblogic.xml file). </p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.GzipCompressionMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      String[] seeObjectArray;
      if (!descriptors.containsKey("GzipCompressionContentType")) {
         getterName = "getGzipCompressionContentType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setGzipCompressionContentType";
         }

         currentResult = new PropertyDescriptor("GzipCompressionContentType", GzipCompressionMBean.class, getterName, setterName);
         descriptors.put("GzipCompressionContentType", currentResult);
         currentResult.setValue("description", "<p> Returns the type of content to be included in compression. </p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setGzipCompressionContentType(String[])")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("default", new String[]{"text/html", "text/xml", "text/plain"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("GzipCompressionMinContentLength")) {
         getterName = "getGzipCompressionMinContentLength";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setGzipCompressionMinContentLength";
         }

         currentResult = new PropertyDescriptor("GzipCompressionMinContentLength", GzipCompressionMBean.class, getterName, setterName);
         descriptors.put("GzipCompressionMinContentLength", currentResult);
         currentResult.setValue("description", "<p> Returns the minimum content length to trigger GZIP compression. This allows you to bypass small-sized resources where compression does not yield a great return and uses unnecessary CPU. </p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setGzipCompressionMinContentLength(long)")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Long(2048L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("GzipCompressionEnabled")) {
         getterName = "isGzipCompressionEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setGzipCompressionEnabled";
         }

         currentResult = new PropertyDescriptor("GzipCompressionEnabled", GzipCompressionMBean.class, getterName, setterName);
         descriptors.put("GzipCompressionEnabled", currentResult);
         currentResult.setValue("description", "<p> This global property determines whether or not the container should provide GZIP compression. </p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
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
