package kodo.conf.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class ClusterRemoteCommitProviderBeanImplBeanInfo extends RemoteCommitProviderBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ClusterRemoteCommitProviderBean.class;

   public ClusterRemoteCommitProviderBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ClusterRemoteCommitProviderBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("kodo.conf.descriptor.ClusterRemoteCommitProviderBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("configurable", Boolean.TRUE);
      beanDescriptor.setValue("package", "kodo.conf.descriptor");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "kodo.conf.descriptor.ClusterRemoteCommitProviderBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("BufferSize")) {
         getterName = "getBufferSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBufferSize";
         }

         currentResult = new PropertyDescriptor("BufferSize", ClusterRemoteCommitProviderBean.class, getterName, setterName);
         descriptors.put("BufferSize", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(10));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CacheTopics")) {
         getterName = "getCacheTopics";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCacheTopics";
         }

         currentResult = new PropertyDescriptor("CacheTopics", ClusterRemoteCommitProviderBean.class, getterName, setterName);
         descriptors.put("CacheTopics", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RecoverAction")) {
         getterName = "getRecoverAction";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRecoverAction";
         }

         currentResult = new PropertyDescriptor("RecoverAction", ClusterRemoteCommitProviderBean.class, getterName, setterName);
         descriptors.put("RecoverAction", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "none");
         currentResult.setValue("legalValues", new Object[]{"none", "clear"});
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
