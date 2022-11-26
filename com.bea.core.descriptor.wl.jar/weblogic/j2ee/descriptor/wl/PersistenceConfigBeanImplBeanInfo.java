package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class PersistenceConfigBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = PersistenceConfigBean.class;

   public PersistenceConfigBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public PersistenceConfigBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.PersistenceConfigBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("configurable", Boolean.TRUE);
      beanDescriptor.setValue("dynamic", Boolean.TRUE);
      beanDescriptor.setValue("exclude", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("An XML persistenceConfigType. This is configuration used by the persistence subsystem for web services as related to the service in which this descriptor resides. (@http://www.bea.com/ns/weblogic/weblogic-webservices). This is a complex type. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.PersistenceConfigBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("DefaultLogicalStoreName")) {
         getterName = "getDefaultLogicalStoreName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultLogicalStoreName";
         }

         currentResult = new PropertyDescriptor("DefaultLogicalStoreName", PersistenceConfigBean.class, getterName, setterName);
         descriptors.put("DefaultLogicalStoreName", currentResult);
         currentResult.setValue("description", "Name of the logical store to use, by default, for persistence within this service. ");
         setPropertyDescriptorDefault(currentResult, "WseeStore");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Customized")) {
         getterName = "isCustomized";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCustomized";
         }

         currentResult = new PropertyDescriptor("Customized", PersistenceConfigBean.class, getterName, setterName);
         descriptors.put("Customized", currentResult);
         currentResult.setValue("description", "A boolean flag indicating whether the config described by this bean has been customized and should be considered active for use at runtime. Defaults to true. If false, none of the values on this bean will be used, and the server-wide defaults for these values will take effect. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
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
