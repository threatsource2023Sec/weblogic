package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class WTCPasswordMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WTCPasswordMBean.class;

   public WTCPasswordMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WTCPasswordMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.WTCPasswordMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "7.0.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("This interface provides access to the WTC password configuration attributes. The methods defined herein are applicable for WTC configuration at the WLS domain level. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.WTCPasswordMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("LocalAccessPoint")) {
         getterName = "getLocalAccessPoint";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLocalAccessPoint";
         }

         currentResult = new PropertyDescriptor("LocalAccessPoint", WTCPasswordMBean.class, getterName, setterName);
         descriptors.put("LocalAccessPoint", currentResult);
         currentResult.setValue("description", "<p>The name of the local access point to which this password applies.</p> ");
         setPropertyDescriptorDefault(currentResult, "myLAP");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LocalPassword")) {
         getterName = "getLocalPassword";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLocalPassword";
         }

         currentResult = new PropertyDescriptor("LocalPassword", WTCPasswordMBean.class, getterName, setterName);
         descriptors.put("LocalPassword", currentResult);
         currentResult.setValue("description", "<p>The local password used to authenticate connections between the local access point and the remote access point.</p>  <p><i>Note:</i> This password is used to authenticate connections between the local Tuxedo access point identified by LocalAccessPoint and the remote Tuxedo access point identified by RemoteAccessPoint.</p> ");
         setPropertyDescriptorDefault(currentResult, "myLPWD");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LocalPasswordIV")) {
         getterName = "getLocalPasswordIV";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLocalPasswordIV";
         }

         currentResult = new PropertyDescriptor("LocalPasswordIV", WTCPasswordMBean.class, getterName, setterName);
         descriptors.put("LocalPasswordIV", currentResult);
         currentResult.setValue("description", "<p>The initialization vector used to encrypt the local password.</p> ");
         setPropertyDescriptorDefault(currentResult, "myLPWDIV");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RemoteAccessPoint")) {
         getterName = "getRemoteAccessPoint";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRemoteAccessPoint";
         }

         currentResult = new PropertyDescriptor("RemoteAccessPoint", WTCPasswordMBean.class, getterName, setterName);
         descriptors.put("RemoteAccessPoint", currentResult);
         currentResult.setValue("description", "<p>The name of the remote access point to which this password applies.</p> ");
         setPropertyDescriptorDefault(currentResult, "myRAP");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RemotePassword")) {
         getterName = "getRemotePassword";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRemotePassword";
         }

         currentResult = new PropertyDescriptor("RemotePassword", WTCPasswordMBean.class, getterName, setterName);
         descriptors.put("RemotePassword", currentResult);
         currentResult.setValue("description", "<p>The remote password used to authenticate connections between the local access point and remote access point.</p>  <p><i>Note:</i> This password is used to authenticate connections between the local Tuxedo access point identified by LocalAccessPoint and the remote Tuxedo access point identified by RemoteAccessPoint.</p> ");
         setPropertyDescriptorDefault(currentResult, "myRPWD");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RemotePasswordIV")) {
         getterName = "getRemotePasswordIV";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRemotePasswordIV";
         }

         currentResult = new PropertyDescriptor("RemotePasswordIV", WTCPasswordMBean.class, getterName, setterName);
         descriptors.put("RemotePasswordIV", currentResult);
         currentResult.setValue("description", "<p>The initialization vector used to encrypt the remote password.</p> ");
         setPropertyDescriptorDefault(currentResult, "myRPWDIV");
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
