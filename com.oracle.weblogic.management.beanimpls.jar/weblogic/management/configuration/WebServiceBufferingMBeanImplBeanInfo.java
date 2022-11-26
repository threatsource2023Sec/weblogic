package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class WebServiceBufferingMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WebServiceBufferingMBean.class;

   public WebServiceBufferingMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WebServiceBufferingMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.WebServiceBufferingMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "10.3.3.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>Represents buffering configuration for web services.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.WebServiceBufferingMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("RetryCount")) {
         getterName = "getRetryCount";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRetryCount";
         }

         currentResult = new PropertyDescriptor("RetryCount", WebServiceBufferingMBean.class, getterName, setterName);
         descriptors.put("RetryCount", currentResult);
         currentResult.setValue("description", "The number of times a buffered request or response can be retried before it is abandoned (and moved to any error queue defined for the buffer queue) ");
         setPropertyDescriptorDefault(currentResult, new Integer(3));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RetryDelay")) {
         getterName = "getRetryDelay";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRetryDelay";
         }

         currentResult = new PropertyDescriptor("RetryDelay", WebServiceBufferingMBean.class, getterName, setterName);
         descriptors.put("RetryDelay", currentResult);
         currentResult.setValue("description", "The amount time between retries of a buffered request and response. Note, this value is only applicable when RetryCount > 0. String value in \"Duration\" format. Defaults to \"P0DT30S\" (30 seconds). ");
         setPropertyDescriptorDefault(currentResult, "P0DT30S");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WebServiceRequestBufferingQueue")) {
         getterName = "getWebServiceRequestBufferingQueue";
         setterName = null;
         currentResult = new PropertyDescriptor("WebServiceRequestBufferingQueue", WebServiceBufferingMBean.class, getterName, setterName);
         descriptors.put("WebServiceRequestBufferingQueue", currentResult);
         currentResult.setValue("description", "Configuration for the request buffering queue ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WebServiceResponseBufferingQueue")) {
         getterName = "getWebServiceResponseBufferingQueue";
         setterName = null;
         currentResult = new PropertyDescriptor("WebServiceResponseBufferingQueue", WebServiceBufferingMBean.class, getterName, setterName);
         descriptors.put("WebServiceResponseBufferingQueue", currentResult);
         currentResult.setValue("description", "Configuration for the response buffering queue ");
         currentResult.setValue("relationship", "containment");
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
