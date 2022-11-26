package weblogic.diagnostics.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class WLDFImageNotificationBeanImplBeanInfo extends WLDFNotificationBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WLDFImageNotificationBean.class;

   public WLDFImageNotificationBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WLDFImageNotificationBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.diagnostics.descriptor.WLDFImageNotificationBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.0.0");
      beanDescriptor.setValue("package", "weblogic.diagnostics.descriptor");
      String description = (new String("<p>Use this interface to configure an image action, which will be sent when a diagnostic policy evaluates to <code>true</code>.</p> <p>Note: As of WebLogic Server 12.2.1, the terms <i>watch</i> and <i>notification</i> are replaced by <i>policy</i> and <i>action</i>, respectively. However, the definition of these terms has not changed.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.diagnostics.descriptor.WLDFImageNotificationBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ImageDirectory")) {
         getterName = "getImageDirectory";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setImageDirectory";
         }

         currentResult = new PropertyDescriptor("ImageDirectory", WLDFImageNotificationBean.class, getterName, setterName);
         descriptors.put("ImageDirectory", currentResult);
         currentResult.setValue("description", "<p>The directory where diagnostic images are stored. The default directory, relative to a server's root directory, is <code>./logs/diagnostic_images</code>.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("deprecated", "12.2.1.1.0 ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ImageLockout")) {
         getterName = "getImageLockout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setImageLockout";
         }

         currentResult = new PropertyDescriptor("ImageLockout", WLDFImageNotificationBean.class, getterName, setterName);
         descriptors.put("ImageLockout", currentResult);
         currentResult.setValue("description", "<p>The length of time, in minutes, during which no diagnostic images requests will be accepted; that is, the minimum amount of time between image capture requests.</p> ");
         currentResult.setValue("legalMin", new Integer(0));
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
