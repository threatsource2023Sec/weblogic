package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class Http2ConfigMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = Http2ConfigMBean.class;

   public Http2ConfigMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public Http2ConfigMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.Http2ConfigMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.3.1.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p> The local setting of an HTTP/2 connection on a web server. </p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.Http2ConfigMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      String[] seeObjectArray;
      if (!descriptors.containsKey("HeaderTableSize")) {
         getterName = "getHeaderTableSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setHeaderTableSize";
         }

         currentResult = new PropertyDescriptor("HeaderTableSize", Http2ConfigMBean.class, getterName, setterName);
         descriptors.put("HeaderTableSize", currentResult);
         currentResult.setValue("description", "<p> The server's maximum size of the header compression table used to decode header blocks, in octets. </p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setHeaderTableSize(int)")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(4096));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InitialWindowSize")) {
         getterName = "getInitialWindowSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setInitialWindowSize";
         }

         currentResult = new PropertyDescriptor("InitialWindowSize", Http2ConfigMBean.class, getterName, setterName);
         descriptors.put("InitialWindowSize", currentResult);
         currentResult.setValue("description", "<p> The server's initial window size (in octets) for stream-level flow control. This setting affects the window size of all streams. </p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setInitialWindowSize(int)")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(65535));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxConcurrentStreams")) {
         getterName = "getMaxConcurrentStreams";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxConcurrentStreams";
         }

         currentResult = new PropertyDescriptor("MaxConcurrentStreams", Http2ConfigMBean.class, getterName, setterName);
         descriptors.put("MaxConcurrentStreams", currentResult);
         currentResult.setValue("description", "<p> The maximum number of concurrent streams that the server will allow. This limit is directional: it applies to the number of streams that the server permits the receiver to create. </p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setMaxConcurrentStreams(int)")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(300));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxFrameSize")) {
         getterName = "getMaxFrameSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxFrameSize";
         }

         currentResult = new PropertyDescriptor("MaxFrameSize", Http2ConfigMBean.class, getterName, setterName);
         descriptors.put("MaxFrameSize", currentResult);
         currentResult.setValue("description", "<p> The size of the largest frame payload that the server is willing to receive, in octets. </p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setMaxFrameSize(int)")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(16384));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxHeaderListSize")) {
         getterName = "getMaxHeaderListSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxHeaderListSize";
         }

         currentResult = new PropertyDescriptor("MaxHeaderListSize", Http2ConfigMBean.class, getterName, setterName);
         descriptors.put("MaxHeaderListSize", currentResult);
         currentResult.setValue("description", "<p> The maximum size of header list that the server is prepared to accept, in octets. The value is based on the uncompressed size of header fields, including the length of the name and value in octets plus an overhead of 32 octets for each header field. </p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setMaxHeaderListSize(int)")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(Integer.MAX_VALUE));
         currentResult.setValue("configurable", Boolean.TRUE);
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
