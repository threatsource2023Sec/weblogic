package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.descriptor.SettableBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class MulticastParamsBeanImplBeanInfo extends SettableBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = MulticastParamsBean.class;

   public MulticastParamsBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public MulticastParamsBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.MulticastParamsBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("<p>Topics with certain quality of service allowments can receive a signifigant performance boost by using multicast to receive messages rather than using a connection oriented protocol like TCP. These parameters can be configured with the bean returned.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.MulticastParamsBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("MulticastAddress")) {
         getterName = "getMulticastAddress";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMulticastAddress";
         }

         currentResult = new PropertyDescriptor("MulticastAddress", MulticastParamsBean.class, getterName, setterName);
         descriptors.put("MulticastAddress", currentResult);
         currentResult.setValue("description", "<p>The IP address that this topic uses to transmit messages to multicast consumers.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         setPropertyDescriptorDefault(currentResult, "");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MulticastPort")) {
         getterName = "getMulticastPort";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMulticastPort";
         }

         currentResult = new PropertyDescriptor("MulticastPort", MulticastParamsBean.class, getterName, setterName);
         descriptors.put("MulticastPort", currentResult);
         currentResult.setValue("description", "<p>The IP port that this topic uses to transmit messages to multicast consumers. </p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         setPropertyDescriptorDefault(currentResult, new Integer(6001));
         currentResult.setValue("legalMax", new Integer(65535));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MulticastTimeToLive")) {
         getterName = "getMulticastTimeToLive";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMulticastTimeToLive";
         }

         currentResult = new PropertyDescriptor("MulticastTimeToLive", MulticastParamsBean.class, getterName, setterName);
         descriptors.put("MulticastTimeToLive", currentResult);
         currentResult.setValue("description", "<p>The number of routers that a message traverses en route to a consumer. A value of 1 limits the message to one subnet (which prevents it from traversing any routers). </p> <p>This value is independent of the JMSExpirationTime value. </p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         setPropertyDescriptorDefault(currentResult, new Integer(1));
         currentResult.setValue("legalMax", new Integer(255));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TemplateBean")) {
         getterName = "getTemplateBean";
         setterName = null;
         currentResult = new PropertyDescriptor("TemplateBean", MulticastParamsBean.class, getterName, setterName);
         descriptors.put("TemplateBean", currentResult);
         currentResult.setValue("description", "<p>Finds the template bean for this destination.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("dynamic", Boolean.FALSE);
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
