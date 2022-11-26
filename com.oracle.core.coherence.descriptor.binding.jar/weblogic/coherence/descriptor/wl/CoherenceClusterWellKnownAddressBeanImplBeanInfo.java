package weblogic.coherence.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.descriptor.SettableBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class CoherenceClusterWellKnownAddressBeanImplBeanInfo extends SettableBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = CoherenceClusterWellKnownAddressBean.class;

   public CoherenceClusterWellKnownAddressBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public CoherenceClusterWellKnownAddressBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.coherence.descriptor.wl.CoherenceClusterWellKnownAddressBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("configurable", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.coherence.descriptor.wl");
      String description = (new String("A unique name that identifies a well known address of a Coherence cluster member. Other members can use this address to enroll into the cluster. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.coherence.descriptor.wl.CoherenceClusterWellKnownAddressBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ListenAddress")) {
         getterName = "getListenAddress";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setListenAddress";
         }

         currentResult = new PropertyDescriptor("ListenAddress", CoherenceClusterWellKnownAddressBean.class, getterName, setterName);
         descriptors.put("ListenAddress", currentResult);
         currentResult.setValue("description", "<p>The IP address. </p> ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ListenPort")) {
         getterName = "getListenPort";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setListenPort";
         }

         currentResult = new PropertyDescriptor("ListenPort", CoherenceClusterWellKnownAddressBean.class, getterName, setterName);
         descriptors.put("ListenPort", currentResult);
         currentResult.setValue("description", "<p>The listen port. </p> ");
         currentResult.setValue("legalMax", new Integer(65535));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("deprecated", "12.2.1.0.0 Well known address does not need port any more ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", CoherenceClusterWellKnownAddressBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "A unique name that identifies this well known address of a Coherence cluster member. ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("key", Boolean.TRUE);
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
