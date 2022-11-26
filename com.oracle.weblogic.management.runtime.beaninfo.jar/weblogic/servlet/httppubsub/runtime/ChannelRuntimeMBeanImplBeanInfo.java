package weblogic.servlet.httppubsub.runtime;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.runtime.ChannelRuntimeMBean;

public class ChannelRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ChannelRuntimeMBean.class;

   public ChannelRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ChannelRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.servlet.httppubsub.runtime.ChannelRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.servlet.httppubsub.runtime");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.ChannelRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         currentResult = new PropertyDescriptor("Name", ChannelRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "Get the qualified name for this channel ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PublishedMessageCount")) {
         getterName = "getPublishedMessageCount";
         setterName = null;
         currentResult = new PropertyDescriptor("PublishedMessageCount", ChannelRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PublishedMessageCount", currentResult);
         currentResult.setValue("description", "Get the number of published messages to this Channel ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SubChannels")) {
         getterName = "getSubChannels";
         setterName = null;
         currentResult = new PropertyDescriptor("SubChannels", ChannelRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SubChannels", currentResult);
         currentResult.setValue("description", "Get the sub channel runtimes if any ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SubscriberCount")) {
         getterName = "getSubscriberCount";
         setterName = null;
         currentResult = new PropertyDescriptor("SubscriberCount", ChannelRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SubscriberCount", currentResult);
         currentResult.setValue("description", "Get the number of subscribers for this channel currently ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Subscribers")) {
         getterName = "getSubscribers";
         setterName = null;
         currentResult = new PropertyDescriptor("Subscribers", ChannelRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Subscribers", currentResult);
         currentResult.setValue("description", "Get the subscribers for this channel ");
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
