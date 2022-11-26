package weblogic.diagnostics.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class WLDFLogActionBeanImplBeanInfo extends WLDFNotificationBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WLDFLogActionBean.class;

   public WLDFLogActionBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WLDFLogActionBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.diagnostics.descriptor.WLDFLogActionBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.diagnostics.descriptor");
      String description = (new String("<p> Configures an action that can record a customized log record using the WebLogic Server <code>NonCatalogLogger</code>. The custom log message set via {@link #setMessage(String)} can be either a literal String or a compound EL expression. </p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.diagnostics.descriptor.WLDFLogActionBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("Message")) {
         getterName = "getMessage";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMessage";
         }

         currentResult = new PropertyDescriptor("Message", WLDFLogActionBean.class, getterName, setterName);
         descriptors.put("Message", currentResult);
         currentResult.setValue("description", "<p>Returns the customized log message for this action.</p> ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setMessage(String)")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Severity")) {
         getterName = "getSeverity";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSeverity";
         }

         currentResult = new PropertyDescriptor("Severity", WLDFLogActionBean.class, getterName, setterName);
         descriptors.put("Severity", currentResult);
         currentResult.setValue("description", "<p>The severity of the log message recorded by this action.</p>  default weblogic.i18n.logging.Severities.NOTICE_TEXT legalValues weblogic.i18n.logging.Severities.INFO_TEXT, weblogic.i18n.logging.Severities.WARNING_TEXT, weblogic.i18n.logging.Severities.ERROR_TEXT, weblogic.i18n.logging.Severities.NOTICE_TEXT, weblogic.i18n.logging.Severities.CRITICAL_TEXT, weblogic.i18n.logging.Severities.ALERT_TEXT, weblogic.i18n.logging.Severities.EMERGENCY_TEXT ");
         setPropertyDescriptorDefault(currentResult, "Notice");
         currentResult.setValue("legalValues", new Object[]{"Info", "Warning", "Error", "Notice", "Critical", "Alert", "Emergency"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SubsystemName")) {
         getterName = "getSubsystemName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSubsystemName";
         }

         currentResult = new PropertyDescriptor("SubsystemName", WLDFLogActionBean.class, getterName, setterName);
         descriptors.put("SubsystemName", currentResult);
         currentResult.setValue("description", "<p>Returns the customized log message for this action.</p> ");
         currentResult.setValue("legalNull", Boolean.TRUE);
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
