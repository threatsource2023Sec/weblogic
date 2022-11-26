package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class TransportRequirementsBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = TransportRequirementsBean.class;

   public TransportRequirementsBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public TransportRequirementsBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.TransportRequirementsBeanImpl");
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
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.TransportRequirementsBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ClientCertAuthentication")) {
         getterName = "getClientCertAuthentication";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setClientCertAuthentication";
         }

         currentResult = new PropertyDescriptor("ClientCertAuthentication", TransportRequirementsBean.class, getterName, setterName);
         descriptors.put("ClientCertAuthentication", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "supported");
         currentResult.setValue("legalValues", new Object[]{"none", "supported", "required"});
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Confidentiality")) {
         getterName = "getConfidentiality";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConfidentiality";
         }

         currentResult = new PropertyDescriptor("Confidentiality", TransportRequirementsBean.class, getterName, setterName);
         descriptors.put("Confidentiality", currentResult);
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

         currentResult = new PropertyDescriptor("Id", TransportRequirementsBean.class, getterName, setterName);
         descriptors.put("Id", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Integrity")) {
         getterName = "getIntegrity";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIntegrity";
         }

         currentResult = new PropertyDescriptor("Integrity", TransportRequirementsBean.class, getterName, setterName);
         descriptors.put("Integrity", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "supported");
         currentResult.setValue("legalValues", new Object[]{"none", "supported", "required"});
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
