package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class WebServiceMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WebServiceMBean.class;

   public WebServiceMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WebServiceMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.WebServiceMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.2.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>Encapsulates information about a Web Service configuration.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.WebServiceMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("CallbackQueue")) {
         getterName = "getCallbackQueue";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCallbackQueue";
         }

         currentResult = new PropertyDescriptor("CallbackQueue", WebServiceMBean.class, getterName, setterName);
         descriptors.put("CallbackQueue", currentResult);
         currentResult.setValue("description", "<p>For use only with the JAX-RPC stack. For JAX-WS, use WebServiceBufferingMBean instead. </p> ");
         setPropertyDescriptorDefault(currentResult, "weblogic.wsee.DefaultCallbackQueue");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CallbackQueueMDBRunAsPrincipalName")) {
         getterName = "getCallbackQueueMDBRunAsPrincipalName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCallbackQueueMDBRunAsPrincipalName";
         }

         currentResult = new PropertyDescriptor("CallbackQueueMDBRunAsPrincipalName", WebServiceMBean.class, getterName, setterName);
         descriptors.put("CallbackQueueMDBRunAsPrincipalName", currentResult);
         currentResult.setValue("description", "<p>For use only with the JAX-RPC stack. For JAX-WS, use WebServiceBufferingMBean instead. </p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JmsConnectionFactory")) {
         getterName = "getJmsConnectionFactory";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJmsConnectionFactory";
         }

         currentResult = new PropertyDescriptor("JmsConnectionFactory", WebServiceMBean.class, getterName, setterName);
         descriptors.put("JmsConnectionFactory", currentResult);
         currentResult.setValue("description", "<p>For use only with the JAX-RPC stack. For JAX-WS, use WebServiceBufferingMBean instead. </p> ");
         setPropertyDescriptorDefault(currentResult, "weblogic.jms.XAConnectionFactory");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessagingQueue")) {
         getterName = "getMessagingQueue";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMessagingQueue";
         }

         currentResult = new PropertyDescriptor("MessagingQueue", WebServiceMBean.class, getterName, setterName);
         descriptors.put("MessagingQueue", currentResult);
         currentResult.setValue("description", "<p>For use only with the JAX-RPC stack. For JAX-WS, use WebServiceBufferingMBean instead. </p> ");
         setPropertyDescriptorDefault(currentResult, "weblogic.wsee.DefaultQueue");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessagingQueueMDBRunAsPrincipalName")) {
         getterName = "getMessagingQueueMDBRunAsPrincipalName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMessagingQueueMDBRunAsPrincipalName";
         }

         currentResult = new PropertyDescriptor("MessagingQueueMDBRunAsPrincipalName", WebServiceMBean.class, getterName, setterName);
         descriptors.put("MessagingQueueMDBRunAsPrincipalName", currentResult);
         currentResult.setValue("description", "<p>For use only with the JAX-RPC stack. For JAX-WS, use WebServiceBufferingMBean instead. </p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WebServiceBuffering")) {
         getterName = "getWebServiceBuffering";
         setterName = null;
         currentResult = new PropertyDescriptor("WebServiceBuffering", WebServiceMBean.class, getterName, setterName);
         descriptors.put("WebServiceBuffering", currentResult);
         currentResult.setValue("description", "<p>Get buffering config for this server.</p> <p> NOTE: Not used by the JAX-RPC stack. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WebServicePersistence")) {
         getterName = "getWebServicePersistence";
         setterName = null;
         currentResult = new PropertyDescriptor("WebServicePersistence", WebServiceMBean.class, getterName, setterName);
         descriptors.put("WebServicePersistence", currentResult);
         currentResult.setValue("description", "<p>Get persistence config for this server.</p> <p> NOTE: Not used by the JAX-RPC stack. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WebServiceReliability")) {
         getterName = "getWebServiceReliability";
         setterName = null;
         currentResult = new PropertyDescriptor("WebServiceReliability", WebServiceMBean.class, getterName, setterName);
         descriptors.put("WebServiceReliability", currentResult);
         currentResult.setValue("description", "<p>Get reliability config for this server.</p> <p> NOTE: Not used by the JAX-RPC stack. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WebServiceResiliency")) {
         getterName = "getWebServiceResiliency";
         setterName = null;
         currentResult = new PropertyDescriptor("WebServiceResiliency", WebServiceMBean.class, getterName, setterName);
         descriptors.put("WebServiceResiliency", currentResult);
         currentResult.setValue("description", "<p>Get Resiliency config for this server.</p> ");
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
