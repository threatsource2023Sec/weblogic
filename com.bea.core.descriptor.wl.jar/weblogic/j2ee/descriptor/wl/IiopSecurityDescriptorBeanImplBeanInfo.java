package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class IiopSecurityDescriptorBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = IiopSecurityDescriptorBean.class;

   public IiopSecurityDescriptorBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public IiopSecurityDescriptorBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.IiopSecurityDescriptorBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("exclude", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.IiopSecurityDescriptorBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ClientAuthentication")) {
         getterName = "getClientAuthentication";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setClientAuthentication";
         }

         currentResult = new PropertyDescriptor("ClientAuthentication", IiopSecurityDescriptorBean.class, getterName, setterName);
         descriptors.put("ClientAuthentication", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "supported");
         currentResult.setValue("legalValues", new Object[]{"none", "supported", "required"});
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Id")) {
         getterName = "getId";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setId";
         }

         currentResult = new PropertyDescriptor("Id", IiopSecurityDescriptorBean.class, getterName, setterName);
         descriptors.put("Id", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IdentityAssertion")) {
         getterName = "getIdentityAssertion";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIdentityAssertion";
         }

         currentResult = new PropertyDescriptor("IdentityAssertion", IiopSecurityDescriptorBean.class, getterName, setterName);
         descriptors.put("IdentityAssertion", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "supported");
         currentResult.setValue("legalValues", new Object[]{"none", "supported", "required"});
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TransportRequirements")) {
         getterName = "getTransportRequirements";
         setterName = null;
         currentResult = new PropertyDescriptor("TransportRequirements", IiopSecurityDescriptorBean.class, getterName, setterName);
         descriptors.put("TransportRequirements", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
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
