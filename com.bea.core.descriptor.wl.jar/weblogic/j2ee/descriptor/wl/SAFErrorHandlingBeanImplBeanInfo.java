package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class SAFErrorHandlingBeanImplBeanInfo extends NamedEntityBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = SAFErrorHandlingBean.class;

   public SAFErrorHandlingBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public SAFErrorHandlingBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.SAFErrorHandlingBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("Store-and-Forward (SAF) Error Handling defines the action to be taken when the delivery of a JMS message fails to be forwarded to a remote destination. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.SAFErrorHandlingBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("LogFormat")) {
         getterName = "getLogFormat";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLogFormat";
         }

         currentResult = new PropertyDescriptor("LogFormat", SAFErrorHandlingBean.class, getterName, setterName);
         descriptors.put("LogFormat", currentResult);
         currentResult.setValue("description", "<p>Specifies how information is logged when <code>Message Handling Policy</code> is set to <code>Log</code>.</p>  <p>Any change to this parameter affects only incoming messages; stored messages are not affected.</p> <p>The valid values are:</p> <ul> <li> <b>%header%</b> - All JMS header fields are logged.</li> <li> <b>%properties%</b> - All user-defined properties are logged.</li> <li> <b>JMSDeliveryTime</b> - This WebLogic JMS-specific extended header field is logged.</li> <li> <b>JMSRedeliveryLimit</b> - This WebLogic JMS-specific extended header field is logged.</li> <li> <b><i>foo</i> </b> - Any valid JMS header field or user-defined property is logged.</li> </ul>  <p>When specifying multiple values, enter them as a comma-separated list. The <code>%header%</code> and <code>%properties% </code> values are <i>not</i> case sensitive. For example, you could use <code>\"%header%,%properties%\"</code> for all the JMS header fields and user properties. However, the enumeration of individual JMS header fields and user-defined properties are case sensitive. To enumerate only individual JMS header fields you could use <code>\"%header, name, address, city, state, zip\"</code>.</p>  <p><b>Note:</b> The <code>JMSMessageID</code> field is always logged and cannot be turned off. Therefore, if the Log Format is not defined (i.e., null) or is defined as an empty string, then the output to the log file contains only the <code>JMSMessageID</code> of the message. </p> ");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Policy")) {
         getterName = "getPolicy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPolicy";
         }

         currentResult = new PropertyDescriptor("Policy", SAFErrorHandlingBean.class, getterName, setterName);
         descriptors.put("Policy", currentResult);
         currentResult.setValue("description", "<p>The error handling policy for this SAF error handling resource.</p> <ul> <li> Discard - By default, expired messages are simply removed from the system. The removal is not logged and the message is not redirected to another location.</li> <li> Log - Removes expired messages and writes an entry to the server log file indicating that the messages were removed from the system. You define the actual information that will be logged in the Log Format field.</li> <li> Redirect - Moves expired messages from their current location into the Error Destination defined for imported SAF destinations.</li> <li> Always-Forward - Ignores the SAF Default Time-to-Live value set on the imported destination and the expiration time set on the message, and so forwards the message to a remote destination even after it has expired.</li> </ul>  <p>Any change to this parameter affects only incoming messages; stored messages are not affected.</p> ");
         setPropertyDescriptorDefault(currentResult, "Discard");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("legalValues", new Object[]{"Discard", "Log", "Redirect", "Always-Forward"});
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SAFErrorDestination")) {
         getterName = "getSAFErrorDestination";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSAFErrorDestination";
         }

         currentResult = new PropertyDescriptor("SAFErrorDestination", SAFErrorHandlingBean.class, getterName, setterName);
         descriptors.put("SAFErrorDestination", currentResult);
         currentResult.setValue("description", "<p>Specifies the error destination when <code>Policy</code> is set to <code>Redirect</code>.</p>  <p>Any change to this parameter affects only incoming messages; stored messages are not affected.</p> ");
         currentResult.setValue("relationship", "reference");
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
