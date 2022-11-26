package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class MessageDestinationDescriptorBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = MessageDestinationDescriptorBean.class;

   public MessageDestinationDescriptorBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public MessageDestinationDescriptorBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.MessageDestinationDescriptorBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.MessageDestinationDescriptorBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("DestinationJNDIName")) {
         getterName = "getDestinationJNDIName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDestinationJNDIName";
         }

         currentResult = new PropertyDescriptor("DestinationJNDIName", MessageDestinationDescriptorBean.class, getterName, setterName);
         descriptors.put("DestinationJNDIName", currentResult);
         currentResult.setValue("description", "Specifies the JNDI name used to associate a message-driven bean with an actual JMS Queue or Topic deployed in the WebLogic Server JNDI tree. ");
         currentResult.setValue("dependency", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DestinationResourceLink")) {
         getterName = "getDestinationResourceLink";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDestinationResourceLink";
         }

         currentResult = new PropertyDescriptor("DestinationResourceLink", MessageDestinationDescriptorBean.class, getterName, setterName);
         descriptors.put("DestinationResourceLink", currentResult);
         currentResult.setValue("description", "Maps to a resource within a JMS module defined in ejb-jar.xml to an actual JMS Module Reference in WebLogic Server. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Id")) {
         getterName = "getId";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setId";
         }

         currentResult = new PropertyDescriptor("Id", MessageDestinationDescriptorBean.class, getterName, setterName);
         descriptors.put("Id", currentResult);
         currentResult.setValue("description", "The bean Id. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InitialContextFactory")) {
         getterName = "getInitialContextFactory";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setInitialContextFactory";
         }

         currentResult = new PropertyDescriptor("InitialContextFactory", MessageDestinationDescriptorBean.class, getterName, setterName);
         descriptors.put("InitialContextFactory", currentResult);
         currentResult.setValue("description", "Specifies the initial context factory used by the JMS provider to create initial context. ");
         setPropertyDescriptorDefault(currentResult, "weblogic.jndi.WLInitialContextFactory");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessageDestinationName")) {
         getterName = "getMessageDestinationName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMessageDestinationName";
         }

         currentResult = new PropertyDescriptor("MessageDestinationName", MessageDestinationDescriptorBean.class, getterName, setterName);
         descriptors.put("MessageDestinationName", currentResult);
         currentResult.setValue("description", "Specifies the name of a message destination reference. This is the reference that the EJB provider places within the ejb-jar.xml deployment file. ");
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ProviderUrl")) {
         getterName = "getProviderUrl";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setProviderUrl";
         }

         currentResult = new PropertyDescriptor("ProviderUrl", MessageDestinationDescriptorBean.class, getterName, setterName);
         descriptors.put("ProviderUrl", currentResult);
         currentResult.setValue("description", "Specifies the URL to be used by the InitialContext. ");
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
