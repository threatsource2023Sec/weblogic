package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class CdiDescriptorBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = CdiDescriptorBean.class;

   public CdiDescriptorBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public CdiDescriptorBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.CdiDescriptorBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.CdiDescriptorBean");
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

         currentResult = new PropertyDescriptor("Policy", CdiDescriptorBean.class, getterName, setterName);
         descriptors.put("Policy", currentResult);
         currentResult.setValue("description", "<p>Indicates the CDI policy.  CDI can be \"Enabled\" (the default) or \"Disabled\". If CDI is disabled then no CDI processing will occur including scanning of pojos and initialization of Weld and CDI will not be available to the application via the CDI apis.  This was added in 12.2.1.1.0</p> ");
         setPropertyDescriptorDefault(currentResult, "Enabled");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.1.0");
      }

      if (!descriptors.containsKey("ImplicitBeanDiscoveryEnabled")) {
         getterName = "isImplicitBeanDiscoveryEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setImplicitBeanDiscoveryEnabled";
         }

         currentResult = new PropertyDescriptor("ImplicitBeanDiscoveryEnabled", CdiDescriptorBean.class, getterName, setterName);
         descriptors.put("ImplicitBeanDiscoveryEnabled", currentResult);
         currentResult.setValue("description", "<p>This flag may be used to limit implicit Bean discovery</p> ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setImplicitBeanDiscoveryEnabled(boolean)")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
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
