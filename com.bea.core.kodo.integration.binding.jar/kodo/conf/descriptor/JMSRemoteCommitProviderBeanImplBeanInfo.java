package kodo.conf.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class JMSRemoteCommitProviderBeanImplBeanInfo extends RemoteCommitProviderBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = JMSRemoteCommitProviderBean.class;

   public JMSRemoteCommitProviderBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public JMSRemoteCommitProviderBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("kodo.conf.descriptor.JMSRemoteCommitProviderBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("configurable", Boolean.TRUE);
      beanDescriptor.setValue("package", "kodo.conf.descriptor");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "kodo.conf.descriptor.JMSRemoteCommitProviderBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ExceptionReconnectAttempts")) {
         getterName = "getExceptionReconnectAttempts";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setExceptionReconnectAttempts";
         }

         currentResult = new PropertyDescriptor("ExceptionReconnectAttempts", JMSRemoteCommitProviderBean.class, getterName, setterName);
         descriptors.put("ExceptionReconnectAttempts", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Topic")) {
         getterName = "getTopic";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTopic";
         }

         currentResult = new PropertyDescriptor("Topic", JMSRemoteCommitProviderBean.class, getterName, setterName);
         descriptors.put("Topic", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TopicConnectionFactory")) {
         getterName = "getTopicConnectionFactory";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTopicConnectionFactory";
         }

         currentResult = new PropertyDescriptor("TopicConnectionFactory", JMSRemoteCommitProviderBean.class, getterName, setterName);
         descriptors.put("TopicConnectionFactory", currentResult);
         currentResult.setValue("description", " ");
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
