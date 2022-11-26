package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class WSReliableDeliveryPolicyMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WSReliableDeliveryPolicyMBean.class;

   public WSReliableDeliveryPolicyMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WSReliableDeliveryPolicyMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.WSReliableDeliveryPolicyMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("This class represents the reliable messaging delivery policy for the current WebLogic Server as both a sender and a receiver of a reliable SOAP message to and from a Web service running on a different WebLogic Server. <p> Reliable messaging is a framework for applications running in WebLogic Server to asynchronously and reliably invoke a Web service running in a different WebLogic Server.  Reliable messaging works only between WebLogic Servers.  This class encapsulates the default reliable messaging parameters for an application running in this WebLogic Server instance that both sends and receives a SOAP message reliably. These parameters include the number of times to retry sending the message, the time to wait between retries, the persistent store for the reliable message, and so on. </p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.WSReliableDeliveryPolicyMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("DefaultRetryCount")) {
         getterName = "getDefaultRetryCount";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultRetryCount";
         }

         currentResult = new PropertyDescriptor("DefaultRetryCount", WSReliableDeliveryPolicyMBean.class, getterName, setterName);
         descriptors.put("DefaultRetryCount", currentResult);
         currentResult.setValue("description", "<p>The default maximum number of times that the sender runtime should attempt to redeliver a message that the receiver WebLogic Server has not yet acknowledged.</p>  <p>The default maximum number of times that the sender should attempt to redeliver a message that the receiver WebLogic Web service has not yet acknowledged.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(10));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultRetryInterval")) {
         getterName = "getDefaultRetryInterval";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultRetryInterval";
         }

         currentResult = new PropertyDescriptor("DefaultRetryInterval", WSReliableDeliveryPolicyMBean.class, getterName, setterName);
         descriptors.put("DefaultRetryInterval", currentResult);
         currentResult.setValue("description", "<p>The default minimum number of seconds that the sender runtime should wait between retries if the receiver does not send an acknowledgement of receiving the message, or if the sender runtime detects a communications error while attempting to send a message.</p>  <p>The default minimum number of seconds that the sender should wait between retries if the receiver does not send an acknowledgement of receiving the message, or if the sender detects a communications error while attempting to send a message.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(6));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultTimeToLive")) {
         getterName = "getDefaultTimeToLive";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultTimeToLive";
         }

         currentResult = new PropertyDescriptor("DefaultTimeToLive", WSReliableDeliveryPolicyMBean.class, getterName, setterName);
         descriptors.put("DefaultTimeToLive", currentResult);
         currentResult.setValue("description", "<p>The default number of seconds that the receiver of the reliable message should persist the history of the reliable message in its store.</p>  <p>The default minimum number of seconds that the receiver of the reliably sent message should persist the message in its storage.</p>  <p>If the DefaultTimeToLive number of message have passed since the message was first sent, the sender should not resent a message with the same message id.</p>  <p>If a sender cannot send a message successfully before the DefaultTimeToLive has passed, the sender should report a delivery failure. The receiver, after recovering from a crash, will not dispatch saved messages that have expired.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(360));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JMSServer")) {
         getterName = "getJMSServer";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJMSServer";
         }

         currentResult = new PropertyDescriptor("JMSServer", WSReliableDeliveryPolicyMBean.class, getterName, setterName);
         descriptors.put("JMSServer", currentResult);
         currentResult.setValue("description", "<p>The JMS server used by WebLogic Server, in its role as a sender, to persist the reliable messages that it sends, or the JMS server used by the receiver WebLogic Server to persist the history of a reliable message sent by a sender.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("Store")) {
         getterName = "getStore";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStore";
         }

         currentResult = new PropertyDescriptor("Store", WSReliableDeliveryPolicyMBean.class, getterName, setterName);
         descriptors.put("Store", currentResult);
         currentResult.setValue("description", "<p>The persistent JMS store used by WebLogic Server, in its role as a sender, to persist the reliable messages that it sends, or the persistent JMS store used by the receiver WebLogic Server to persist the history of a reliable message sent by a sender.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("deprecated", "9.0.0.0 use the JMSServer attribute instead ");
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
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
