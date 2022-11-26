package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class WebServiceResiliencyMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WebServiceResiliencyMBean.class;

   public WebServiceResiliencyMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WebServiceResiliencyMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.WebServiceResiliencyMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>Represents resilience configuration for web services.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.WebServiceResiliencyMBean");
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

         currentResult = new PropertyDescriptor("RetryCount", WebServiceResiliencyMBean.class, getterName, setterName);
         descriptors.put("RetryCount", currentResult);
         currentResult.setValue("description", "The number of times a wsdl get request can be retried before it is abandoned. Default value  is zero. ");
         setPropertyDescriptorDefault(currentResult, new Integer(0));
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

         currentResult = new PropertyDescriptor("RetryDelay", WebServiceResiliencyMBean.class, getterName, setterName);
         descriptors.put("RetryDelay", currentResult);
         currentResult.setValue("description", "The amount time between retries . Note, this value is only applicable when RetryCount > 0. Defaults to \"5000\" (5000 milliseconds). ");
         setPropertyDescriptorDefault(currentResult, "5000");
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
