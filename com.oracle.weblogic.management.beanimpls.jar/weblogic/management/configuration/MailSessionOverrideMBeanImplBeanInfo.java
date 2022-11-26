package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class MailSessionOverrideMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = MailSessionOverrideMBean.class;

   public MailSessionOverrideMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public MailSessionOverrideMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.MailSessionOverrideMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("dynamic", Boolean.TRUE);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("Defines partition-specific mail session attribute overrides of a mail session referenced by a partition resource group definition. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.MailSessionOverrideMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("Properties")) {
         getterName = "getProperties";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setProperties";
         }

         currentResult = new PropertyDescriptor("Properties", MailSessionOverrideMBean.class, getterName, setterName);
         descriptors.put("Properties", currentResult);
         currentResult.setValue("description", "The configuration options and user authentication data that this mail session uses to interact with a mail server. ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      String[] roleObjectArrayGet;
      if (!descriptors.containsKey("SessionPassword")) {
         getterName = "getSessionPassword";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSessionPassword";
         }

         currentResult = new PropertyDescriptor("SessionPassword", MailSessionOverrideMBean.class, getterName, setterName);
         descriptors.put("SessionPassword", currentResult);
         currentResult.setValue("description", "The decrypted JavaMail Session password attribute, for use only temporarily in-memory; the value returned by this attribute should not be held in memory long term. ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         roleObjectArrayGet = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowedGet", roleObjectArrayGet);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SessionPasswordEncrypted")) {
         getterName = "getSessionPasswordEncrypted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSessionPasswordEncrypted";
         }

         currentResult = new PropertyDescriptor("SessionPasswordEncrypted", MailSessionOverrideMBean.class, getterName, setterName);
         descriptors.put("SessionPasswordEncrypted", currentResult);
         currentResult.setValue("description", "The encrypted JavaMail Session password attribute, for use only temporarily in-memory; the value returned by this attribute should not be held in memory long term. ");
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         roleObjectArrayGet = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowedGet", roleObjectArrayGet);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SessionUsername")) {
         getterName = "getSessionUsername";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSessionUsername";
         }

         currentResult = new PropertyDescriptor("SessionUsername", MailSessionOverrideMBean.class, getterName, setterName);
         descriptors.put("SessionUsername", currentResult);
         currentResult.setValue("description", "The username to be used to create an authenticated JavaMail Session, using a JavaMail <code>Authenticator</code> instance. ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         roleObjectArrayGet = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowedGet", roleObjectArrayGet);
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
