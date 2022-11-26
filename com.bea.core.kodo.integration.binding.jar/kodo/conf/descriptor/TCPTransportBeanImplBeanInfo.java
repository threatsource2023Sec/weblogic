package kodo.conf.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class TCPTransportBeanImplBeanInfo extends PersistenceServerBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = TCPTransportBean.class;

   public TCPTransportBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public TCPTransportBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("kodo.conf.descriptor.TCPTransportBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "kodo.conf.descriptor");
      String description = (new String("Transport which uses TCP. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "kodo.conf.descriptor.TCPTransportBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("Host")) {
         getterName = "getHost";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setHost";
         }

         currentResult = new PropertyDescriptor("Host", TCPTransportBean.class, getterName, setterName);
         descriptors.put("Host", currentResult);
         currentResult.setValue("description", "Host to find server. ");
         setPropertyDescriptorDefault(currentResult, "localhost");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Port")) {
         getterName = "getPort";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPort";
         }

         currentResult = new PropertyDescriptor("Port", TCPTransportBean.class, getterName, setterName);
         descriptors.put("Port", currentResult);
         currentResult.setValue("description", "Port to find server. ");
         setPropertyDescriptorDefault(currentResult, new Integer(5637));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SoTimeout")) {
         getterName = "getSoTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSoTimeout";
         }

         currentResult = new PropertyDescriptor("SoTimeout", TCPTransportBean.class, getterName, setterName);
         descriptors.put("SoTimeout", currentResult);
         currentResult.setValue("description", "Socket timeout. ");
         setPropertyDescriptorDefault(currentResult, new Integer(0));
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
