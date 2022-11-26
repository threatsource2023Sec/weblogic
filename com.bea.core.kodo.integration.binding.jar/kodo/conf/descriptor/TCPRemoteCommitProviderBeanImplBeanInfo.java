package kodo.conf.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class TCPRemoteCommitProviderBeanImplBeanInfo extends RemoteCommitProviderBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = TCPRemoteCommitProviderBean.class;

   public TCPRemoteCommitProviderBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public TCPRemoteCommitProviderBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("kodo.conf.descriptor.TCPRemoteCommitProviderBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("configurable", Boolean.TRUE);
      beanDescriptor.setValue("package", "kodo.conf.descriptor");
      String description = (new String("Uses TCP sockets to transmit remote commit events. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "kodo.conf.descriptor.TCPRemoteCommitProviderBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("Addresses")) {
         getterName = "getAddresses";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAddresses";
         }

         currentResult = new PropertyDescriptor("Addresses", TCPRemoteCommitProviderBean.class, getterName, setterName);
         descriptors.put("Addresses", currentResult);
         currentResult.setValue("description", "Addresses to seek other TCP providers. ");
         setPropertyDescriptorDefault(currentResult, "[]");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxActive")) {
         getterName = "getMaxActive";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxActive";
         }

         currentResult = new PropertyDescriptor("MaxActive", TCPRemoteCommitProviderBean.class, getterName, setterName);
         descriptors.put("MaxActive", currentResult);
         currentResult.setValue("description", "Max active in pool. ");
         setPropertyDescriptorDefault(currentResult, new Integer(2));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxIdle")) {
         getterName = "getMaxIdle";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxIdle";
         }

         currentResult = new PropertyDescriptor("MaxIdle", TCPRemoteCommitProviderBean.class, getterName, setterName);
         descriptors.put("MaxIdle", currentResult);
         currentResult.setValue("description", "Maximum idle threads. ");
         setPropertyDescriptorDefault(currentResult, new Integer(2));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NumBroadcastThreads")) {
         getterName = "getNumBroadcastThreads";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNumBroadcastThreads";
         }

         currentResult = new PropertyDescriptor("NumBroadcastThreads", TCPRemoteCommitProviderBean.class, getterName, setterName);
         descriptors.put("NumBroadcastThreads", currentResult);
         currentResult.setValue("description", "Number of broadcast threads. ");
         setPropertyDescriptorDefault(currentResult, new Integer(2));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Port")) {
         getterName = "getPort";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPort";
         }

         currentResult = new PropertyDescriptor("Port", TCPRemoteCommitProviderBean.class, getterName, setterName);
         descriptors.put("Port", currentResult);
         currentResult.setValue("description", "The port to listen on. ");
         setPropertyDescriptorDefault(currentResult, new Integer(5636));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RecoveryTimeMillis")) {
         getterName = "getRecoveryTimeMillis";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRecoveryTimeMillis";
         }

         currentResult = new PropertyDescriptor("RecoveryTimeMillis", TCPRemoteCommitProviderBean.class, getterName, setterName);
         descriptors.put("RecoveryTimeMillis", currentResult);
         currentResult.setValue("description", "Number of time to recover. ");
         setPropertyDescriptorDefault(currentResult, new Integer(15000));
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
