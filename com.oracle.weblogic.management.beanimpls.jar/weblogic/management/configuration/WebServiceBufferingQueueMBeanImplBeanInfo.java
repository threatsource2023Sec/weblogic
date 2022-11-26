package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class WebServiceBufferingQueueMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WebServiceBufferingQueueMBean.class;

   public WebServiceBufferingQueueMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WebServiceBufferingQueueMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.WebServiceBufferingQueueMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "10.3.3.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>Represents buffering queue configuration for web services (either for requests or responses).</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.WebServiceBufferingQueueMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ConnectionFactoryJndiName")) {
         getterName = "getConnectionFactoryJndiName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnectionFactoryJndiName";
         }

         currentResult = new PropertyDescriptor("ConnectionFactoryJndiName", WebServiceBufferingQueueMBean.class, getterName, setterName);
         descriptors.put("ConnectionFactoryJndiName", currentResult);
         currentResult.setValue("description", "The JNDI name of the connection factory to use when buffering messages onto this queue. Defaults to the 'default' JMS connection factory. ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", WebServiceBufferingQueueMBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "Get the name of this buffering queue. This name is the JNDI name of the queue to be used for buffering. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Enabled")) {
         getterName = "isEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEnabled";
         }

         currentResult = new PropertyDescriptor("Enabled", WebServiceBufferingQueueMBean.class, getterName, setterName);
         descriptors.put("Enabled", currentResult);
         currentResult.setValue("description", "A boolean flag indicating whether buffering is enabled (request buffering if this is the request queue, or response buffering if this is the response queue). Defaults to false. ");
         setPropertyDescriptorDefault(currentResult, false);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TransactionEnabled")) {
         getterName = "isTransactionEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTransactionEnabled";
         }

         currentResult = new PropertyDescriptor("TransactionEnabled", WebServiceBufferingQueueMBean.class, getterName, setterName);
         descriptors.put("TransactionEnabled", currentResult);
         currentResult.setValue("description", "A boolean flag indicating whether transactions should be used when buffering a message onto or consuming a message off of this queue. Defaults to false. ");
         setPropertyDescriptorDefault(currentResult, false);
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
