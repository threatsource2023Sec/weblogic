package weblogic.diagnostics.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class WLDFSMTPNotificationBeanImplBeanInfo extends WLDFNotificationBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WLDFSMTPNotificationBean.class;

   public WLDFSMTPNotificationBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WLDFSMTPNotificationBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.diagnostics.descriptor.WLDFSMTPNotificationBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.0.0");
      beanDescriptor.setValue("package", "weblogic.diagnostics.descriptor");
      String description = (new String("<p>Use this interface to define a SMTP action, which is sent when a diagnostic policy evaluates to <code>true</code>.</p> <p>Note: As of WebLogic Server 12.2.1, the terms <i>watch</i> and <i>notification</i> are replaced by <i>policy</i> and <i>action</i>, respectively. However, the definition of these terms has not changed.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.diagnostics.descriptor.WLDFSMTPNotificationBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("Body")) {
         getterName = "getBody";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBody";
         }

         currentResult = new PropertyDescriptor("Body", WLDFSMTPNotificationBean.class, getterName, setterName);
         descriptors.put("Body", currentResult);
         currentResult.setValue("description", "<p>The body for the mail message. If the body is not specified, a body is created from the action information.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MailSessionJNDIName")) {
         getterName = "getMailSessionJNDIName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMailSessionJNDIName";
         }

         currentResult = new PropertyDescriptor("MailSessionJNDIName", WLDFSMTPNotificationBean.class, getterName, setterName);
         descriptors.put("MailSessionJNDIName", currentResult);
         currentResult.setValue("description", "<p>The JNDI name of the mail session. This name must match the attribute in the corresponding MailSessionMBean.</p> ");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Recipients")) {
         getterName = "getRecipients";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRecipients";
         }

         currentResult = new PropertyDescriptor("Recipients", WLDFSMTPNotificationBean.class, getterName, setterName);
         descriptors.put("Recipients", currentResult);
         currentResult.setValue("description", "<p>The address of the recipient or recipients of the SMTP action mail. The address uses the syntax defined in RFC822. Typical address syntax is of the form <code><i>user</i>@<i>host</i>.<i>domain</i></code> or <code><i>Personal Name</i></code>. An address can include multiple recipients, separated by commas or spaces.</p>  <p>For more information, refer to the javax.mail.internet.InternetAddress.parse method.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Subject")) {
         getterName = "getSubject";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSubject";
         }

         currentResult = new PropertyDescriptor("Subject", WLDFSMTPNotificationBean.class, getterName, setterName);
         descriptors.put("Subject", currentResult);
         currentResult.setValue("description", "<p>The subject for the mail message. If the subject is not specified, a subject is created from the action information.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = WLDFSMTPNotificationBean.class.getMethod("addRecipient", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("recipient", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Adds a recipient to the list of e-mail addresses that will receive this action.</p> ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "Recipients");
      }

      mth = WLDFSMTPNotificationBean.class.getMethod("removeRecipient", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("recipient", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Removes a recipient from the list of e-mail addresses that will receive this action.</p> ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "Recipients");
      }

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
