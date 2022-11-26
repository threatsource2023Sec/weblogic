package weblogic.wsee.mc.mbean;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.WseeMcRuntimeMBean;

public class WseeMcRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WseeMcRuntimeMBean.class;

   public WseeMcRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WseeMcRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.wsee.mc.mbean.WseeMcRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "10.3.3.0");
      beanDescriptor.setValue("package", "weblogic.wsee.mc.mbean");
      String description = (new String("<p>Describes the state of MakeConnection pending message lists</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.WseeMcRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      if (!descriptors.containsKey("AnonymousEndpointIds")) {
         String getterName = "getAnonymousEndpointIds";
         String setterName = null;
         currentResult = new PropertyDescriptor("AnonymousEndpointIds", WseeMcRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AnonymousEndpointIds", currentResult);
         currentResult.setValue("description", "Get the list of MakeConnection anonymous endpoint Ids for which the MC Receiver is awaiting MakeConnection incoming messages ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "No default REST mapping for List<String>");
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
      Method mth = WseeMcRuntimeMBean.class.getMethod("getAnonymousEndpointInfo", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("anonymousId", "the id if the anonymous endpoint to get info on ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "No default REST mapping for CompositeData");
         String[] throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ManagementException if the id is unknown")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Given an anonyomus endpoint id get info about the endpoint. The info is a CompositeData item containing the following items: <p> AnonymousEndpointId - the MakeConnection unique identifier for this anonymous endpoint </p> <p> PendingmMessageCount - the number of messages stored for this endpoint </p> <p> ReceivedMcMessageCount - the number of MakeConnection messages received for this endpoint </p> <p> EmptyResponseCount - the number of responses to received MakeConnection messages that did not return a stored message </p> <p> NonEmptyResponseCount - the number of responses to received MakeConnection messages that returned a stored message </p <p> OldestPendingMessageTime - the oldest timestamp of messages stored for this endpoint </p> <p> NewestPendingMessageTime - the newest timestamp of messages stored for this endpoint </p ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "No default REST mapping for CompositeData");
      }

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
