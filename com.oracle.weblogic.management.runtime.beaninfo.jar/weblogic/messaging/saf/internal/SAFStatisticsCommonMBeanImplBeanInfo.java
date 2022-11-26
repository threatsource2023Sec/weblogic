package weblogic.messaging.saf.internal;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.runtime.SAFStatisticsCommonMBean;

public class SAFStatisticsCommonMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = SAFStatisticsCommonMBean.class;

   public SAFStatisticsCommonMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public SAFStatisticsCommonMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.messaging.saf.internal.SAFStatisticsCommonMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.0.0");
      beanDescriptor.setValue("package", "weblogic.messaging.saf.internal");
      String description = (new String("This class is has all the common statistics stuff for a SAFAgentRuntimeMBean or a SAFRemoteEndpoitsRuntimeMBean. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.SAFStatisticsCommonMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("BytesCurrentCount")) {
         getterName = "getBytesCurrentCount";
         setterName = null;
         currentResult = new PropertyDescriptor("BytesCurrentCount", SAFStatisticsCommonMBean.class, getterName, (String)setterName);
         descriptors.put("BytesCurrentCount", currentResult);
         currentResult.setValue("description", "<p>Returns the current number of bytes. This number does not include the pending bytes.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BytesHighCount")) {
         getterName = "getBytesHighCount";
         setterName = null;
         currentResult = new PropertyDescriptor("BytesHighCount", SAFStatisticsCommonMBean.class, getterName, (String)setterName);
         descriptors.put("BytesHighCount", currentResult);
         currentResult.setValue("description", "<p>Returns the peak number of bytes since the last reset.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BytesPendingCount")) {
         getterName = "getBytesPendingCount";
         setterName = null;
         currentResult = new PropertyDescriptor("BytesPendingCount", SAFStatisticsCommonMBean.class, getterName, (String)setterName);
         descriptors.put("BytesPendingCount", currentResult);
         currentResult.setValue("description", "<p>Returns the number of pending bytes. Pending bytes are over and above the current number of bytes.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BytesReceivedCount")) {
         getterName = "getBytesReceivedCount";
         setterName = null;
         currentResult = new PropertyDescriptor("BytesReceivedCount", SAFStatisticsCommonMBean.class, getterName, (String)setterName);
         descriptors.put("BytesReceivedCount", currentResult);
         currentResult.setValue("description", "<p>The number of bytes received since the last reset.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BytesThresholdTime")) {
         getterName = "getBytesThresholdTime";
         setterName = null;
         currentResult = new PropertyDescriptor("BytesThresholdTime", SAFStatisticsCommonMBean.class, getterName, (String)setterName);
         descriptors.put("BytesThresholdTime", currentResult);
         currentResult.setValue("description", "<p>Returns the amount of time in the threshold condition since the last reset.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FailedMessagesTotal")) {
         getterName = "getFailedMessagesTotal";
         setterName = null;
         currentResult = new PropertyDescriptor("FailedMessagesTotal", SAFStatisticsCommonMBean.class, getterName, (String)setterName);
         descriptors.put("FailedMessagesTotal", currentResult);
         currentResult.setValue("description", "<p>Returns the total number of messages that have failed to be forwarded since the last reset.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessagesCurrentCount")) {
         getterName = "getMessagesCurrentCount";
         setterName = null;
         currentResult = new PropertyDescriptor("MessagesCurrentCount", SAFStatisticsCommonMBean.class, getterName, (String)setterName);
         descriptors.put("MessagesCurrentCount", currentResult);
         currentResult.setValue("description", "<p>Returns the current number of messages. This number includes the pending messages.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessagesHighCount")) {
         getterName = "getMessagesHighCount";
         setterName = null;
         currentResult = new PropertyDescriptor("MessagesHighCount", SAFStatisticsCommonMBean.class, getterName, (String)setterName);
         descriptors.put("MessagesHighCount", currentResult);
         currentResult.setValue("description", "<p>Returns the peak number of messages since the last reset.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessagesPendingCount")) {
         getterName = "getMessagesPendingCount";
         setterName = null;
         currentResult = new PropertyDescriptor("MessagesPendingCount", SAFStatisticsCommonMBean.class, getterName, (String)setterName);
         descriptors.put("MessagesPendingCount", currentResult);
         currentResult.setValue("description", "<p>Returns the number of pending messages. Pending messages are over and above the current number of messages. A pending message is one that has either been sent in a transaction and not committed, or been forwarded but has not been acknowledged.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessagesReceivedCount")) {
         getterName = "getMessagesReceivedCount";
         setterName = null;
         currentResult = new PropertyDescriptor("MessagesReceivedCount", SAFStatisticsCommonMBean.class, getterName, (String)setterName);
         descriptors.put("MessagesReceivedCount", currentResult);
         currentResult.setValue("description", "<p>The number of messages received since the last reset.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessagesThresholdTime")) {
         getterName = "getMessagesThresholdTime";
         setterName = null;
         currentResult = new PropertyDescriptor("MessagesThresholdTime", SAFStatisticsCommonMBean.class, getterName, (String)setterName);
         descriptors.put("MessagesThresholdTime", currentResult);
         currentResult.setValue("description", "<p>Returns the amount of time in the threshold condition since the last reset.</p> ");
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
