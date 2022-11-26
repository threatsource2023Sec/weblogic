package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class CdiContainerMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = CdiContainerMBean.class;

   public CdiContainerMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public CdiContainerMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.CdiContainerMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>This MBean is used to specify domain-wide defaults for the CDI container</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.CdiContainerMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("Policy")) {
         getterName = "getPolicy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPolicy";
         }

         currentResult = new PropertyDescriptor("Policy", CdiContainerMBean.class, getterName, setterName);
         descriptors.put("Policy", currentResult);
         currentResult.setValue("description", "<p>Indicates the CDI policy.  CDI can be \"Enabled\" (the default) or \"Disabled\". If CDI is disabled then no CDI processing will occur including scanning of pojos and initialization of Weld and CDI will not be available to the application via the CDI apis.  </p> ");
         setPropertyDescriptorDefault(currentResult, "Enabled");
         currentResult.setValue("legalValues", new Object[]{"Enabled", "Disabled"});
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.1.0");
      }

      if (!descriptors.containsKey("ImplicitBeanDiscoveryEnabled")) {
         getterName = "isImplicitBeanDiscoveryEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setImplicitBeanDiscoveryEnabled";
         }

         currentResult = new PropertyDescriptor("ImplicitBeanDiscoveryEnabled", CdiContainerMBean.class, getterName, setterName);
         descriptors.put("ImplicitBeanDiscoveryEnabled", currentResult);
         currentResult.setValue("description", "<p>This flag may be used to limit implicit Bean discovery</p> ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setImplicitBeanDiscoveryEnabled(boolean)")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
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
