package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class WTCImportMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WTCImportMBean.class;

   public WTCImportMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WTCImportMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.WTCImportMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "7.0.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>This interface provides access to the WTC import configuration attributes.  The methods defined herein are applicable for WTC configuration at the WLS domain level.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.WTCImportMBean");
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

         currentResult = new PropertyDescriptor("LocalAccessPoint", WTCImportMBean.class, getterName, setterName);
         descriptors.put("LocalAccessPoint", currentResult);
         currentResult.setValue("description", "<p>The name of the local access point that offers this service.</p> ");
         setPropertyDescriptorDefault(currentResult, "myLAP");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RemoteAccessPointList")) {
         getterName = "getRemoteAccessPointList";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRemoteAccessPointList";
         }

         currentResult = new PropertyDescriptor("RemoteAccessPointList", WTCImportMBean.class, getterName, setterName);
         descriptors.put("RemoteAccessPointList", currentResult);
         currentResult.setValue("description", "<p>The comma-separated failover list that identifies the remote domain access points through which resources are imported.</p>  <p>For example: <code>TDOM1,TDOM2,TDOM3</code></p> ");
         setPropertyDescriptorDefault(currentResult, "myRAP");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RemoteName")) {
         getterName = "getRemoteName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRemoteName";
         }

         currentResult = new PropertyDescriptor("RemoteName", WTCImportMBean.class, getterName, setterName);
         descriptors.put("RemoteName", currentResult);
         currentResult.setValue("description", "<p>The remote name of this service.</p>  <p><i>Note:</i> If not specified, the ResourceName value is used.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ResourceName")) {
         getterName = "getResourceName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setResourceName";
         }

         currentResult = new PropertyDescriptor("ResourceName", WTCImportMBean.class, getterName, setterName);
         descriptors.put("ResourceName", currentResult);
         currentResult.setValue("description", "<p>The name used to identify this imported service.</p>  <p><i>Note:</i> This name must be unique within defined Imports. This allows you to define unique configurations having the same Remote Name.</p> ");
         setPropertyDescriptorDefault(currentResult, "myImport");
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
