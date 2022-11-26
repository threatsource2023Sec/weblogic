package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.descriptor.SettableBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class MessageLoggingParamsBeanImplBeanInfo extends SettableBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = MessageLoggingParamsBean.class;

   public MessageLoggingParamsBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public MessageLoggingParamsBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.MessageLoggingParamsBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("<p>These parameters control what should happen to messages logging. They allow the adminstrator to control logging.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.MessageLoggingParamsBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("MessageLoggingFormat")) {
         getterName = "getMessageLoggingFormat";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMessageLoggingFormat";
         }

         currentResult = new PropertyDescriptor("MessageLoggingFormat", MessageLoggingParamsBean.class, getterName, setterName);
         descriptors.put("MessageLoggingFormat", currentResult);
         currentResult.setValue("description", "<p>Defines which information about the message is logged.</p> <p>Valid values are:</p> <ul> <li> <b>%header%</b> - All JMS header fields are logged.</li> <li> <b>%properties%</b> - All user-defined properties are logged.</li> <li> <b>JMSDeliveryTime</b> - This WebLogic JMS-specific extended header field is logged.</li> <li> <b>JMSRedeliveryLimit</b> - This WebLogic JMS-specific extended header field is logged.</li> <li> <b><i>foo</i> </b> - Any valid JMS header field or user-defined property is logged.</li> </ul>   <p>When specifying multiple values, enter them as a comma-separated list. The <code>%header%</code> and <code>%properies% </code> values are <i>not</i> case sensitive. For example, you could use <code>\"%header%,%properties%\"</code> for all the JMS header fields and user properties. However, the enumeration of individual JMS header fields and user-defined properties are case sensitive. To enumerate only individual JMS header fields you could use <code>\"%header, name, address, city, state, zip\"</code>.</p>  <p><b>Note:</b> The <code>JMSMessageID</code> field is always logged and cannot be turned off. Therefore, if the Message Logging Format is not defined (i.e., null) or is defined as an empty string, then the output to the log file contains only the <code>JMSMessageID</code> of the message. Gets the <code>message-logging-format</code> element.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TemplateBean")) {
         getterName = "getTemplateBean";
         setterName = null;
         currentResult = new PropertyDescriptor("TemplateBean", MessageLoggingParamsBean.class, getterName, setterName);
         descriptors.put("TemplateBean", currentResult);
         currentResult.setValue("description", "<p>Finds the template bean for this destination.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessageLoggingEnabled")) {
         getterName = "isMessageLoggingEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMessageLoggingEnabled";
         }

         currentResult = new PropertyDescriptor("MessageLoggingEnabled", MessageLoggingParamsBean.class, getterName, setterName);
         descriptors.put("MessageLoggingEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the module logs information about the message life cycle.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
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
