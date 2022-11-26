package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class WTCExportMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WTCExportMBean.class;

   public WTCExportMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WTCExportMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.WTCExportMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "7.0.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("This interface provides access to the WTC export configuration attributes.  The methods defined herein are applicable for WTC configuration at the WLS domain level. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.WTCExportMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("EJBName")) {
         getterName = "getEJBName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEJBName";
         }

         currentResult = new PropertyDescriptor("EJBName", WTCExportMBean.class, getterName, setterName);
         descriptors.put("EJBName", currentResult);
         currentResult.setValue("description", "<p>The complete name of the EJB home interface to use when invoking a service.</p>  <p>If not specified, the default interface used is <code>tuxedo.services.<i>servicename</i>Home</code>. For example: If the service being invoked is TOUPPER and EJBName attribute is not specified, the home interface looked up in JNDI would be <code>tuxedo.services.TOUPPERHome</code>.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LocalAccessPoint")) {
         getterName = "getLocalAccessPoint";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLocalAccessPoint";
         }

         currentResult = new PropertyDescriptor("LocalAccessPoint", WTCExportMBean.class, getterName, setterName);
         descriptors.put("LocalAccessPoint", currentResult);
         currentResult.setValue("description", "<p>The name of the local access point that exports this service.</p> ");
         setPropertyDescriptorDefault(currentResult, "myLAP");
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

         currentResult = new PropertyDescriptor("RemoteName", WTCExportMBean.class, getterName, setterName);
         descriptors.put("RemoteName", currentResult);
         currentResult.setValue("description", "<p>The remote name of this service. </p>  <p>If this value is not specified, the ResourceName value is used.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ResourceName")) {
         getterName = "getResourceName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setResourceName";
         }

         currentResult = new PropertyDescriptor("ResourceName", WTCExportMBean.class, getterName, setterName);
         descriptors.put("ResourceName", currentResult);
         currentResult.setValue("description", "<p>The name used to identify an exported service.</p>  <p>The combination of ResourceName and LocalAccessPoint must be unique within defined Exports. This allows you to define unique configurations having the same ResourceName.</p> ");
         setPropertyDescriptorDefault(currentResult, "myExport");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TargetClass")) {
         getterName = "getTargetClass";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTargetClass";
         }

         currentResult = new PropertyDescriptor("TargetClass", WTCExportMBean.class, getterName, setterName);
         descriptors.put("TargetClass", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TargetJar")) {
         getterName = "getTargetJar";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTargetJar";
         }

         currentResult = new PropertyDescriptor("TargetJar", WTCExportMBean.class, getterName, setterName);
         descriptors.put("TargetJar", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
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
