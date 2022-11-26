package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class DistributedDestinationMemberBeanImplBeanInfo extends NamedEntityBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = DistributedDestinationMemberBean.class;

   public DistributedDestinationMemberBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public DistributedDestinationMemberBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.DistributedDestinationMemberBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("deprecated", "10.3.4.0, since Weighted Distributed Destination has been deprecated and replaced by Uniform Distributed Destination ");
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("<p>This bean defines common properties of distributed destination members. Distributed destination members can be members of queues or topics.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.DistributedDestinationMemberBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("PhysicalDestinationName")) {
         getterName = "getPhysicalDestinationName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPhysicalDestinationName";
         }

         currentResult = new PropertyDescriptor("PhysicalDestinationName", DistributedDestinationMemberBean.class, getterName, setterName);
         descriptors.put("PhysicalDestinationName", currentResult);
         currentResult.setValue("description", "This attribute is only used by the JMS interop module. <p> The name of the destination that is to be a member of the distributed destination. </p>  <p>Note that this is the real name of the destination (e.g. the name as retrieved by getName()), not the destination-name element of the destination (e.g. the name as retrieved by getJMSCreateDestinationIdentifier()). </p> <p> This field is only used by the JMS interop module.  All other modules may not set this field.  In all other modules the name of the DistributedDestinationMemberBean itself is the name of the queue to be used by this destination </p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("deprecated", "9.0.0.0 Replaced by {@link weblogic.j2ee.descriptor.wl.DistributedDestinationMemberBean#getName() getName} ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Weight")) {
         getterName = "getWeight";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setWeight";
         }

         currentResult = new PropertyDescriptor("Weight", DistributedDestinationMemberBean.class, getterName, setterName);
         descriptors.put("Weight", currentResult);
         currentResult.setValue("description", "<p> The weight of a distributed destination member is a measure of its ability to handle message load, compared to the other member destinations in the same distributed set. </p> <!-- Gets the weight element --> ");
         setPropertyDescriptorDefault(currentResult, new Integer(1));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("configurable", Boolean.TRUE);
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
