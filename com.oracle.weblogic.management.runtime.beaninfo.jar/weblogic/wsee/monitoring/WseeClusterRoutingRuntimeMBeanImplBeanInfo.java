package weblogic.wsee.monitoring;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.runtime.WseeClusterRoutingRuntimeMBean;

public class WseeClusterRoutingRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WseeClusterRoutingRuntimeMBean.class;

   public WseeClusterRoutingRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WseeClusterRoutingRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.wsee.monitoring.WseeClusterRoutingRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "10.3.3.0");
      beanDescriptor.setValue("package", "weblogic.wsee.monitoring");
      String description = (new String("<p>Encapsulates runtime information about a particular Web Service cluster routing instance (whether it be a front-end router or an in-place router).</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.WseeClusterRoutingRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("LastRoutingFailure")) {
         getterName = "getLastRoutingFailure";
         setterName = null;
         currentResult = new PropertyDescriptor("LastRoutingFailure", WseeClusterRoutingRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LastRoutingFailure", currentResult);
         currentResult.setValue("description", "The exception that caused the last routing failure, or null if no failures have occurred. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LastRoutingFailureTime")) {
         getterName = "getLastRoutingFailureTime";
         setterName = null;
         currentResult = new PropertyDescriptor("LastRoutingFailureTime", WseeClusterRoutingRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LastRoutingFailureTime", currentResult);
         currentResult.setValue("description", "The time (in milliseconds since epoch) of the last routing failure (or 0 if no failures have occurred). ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RequestCount")) {
         getterName = "getRequestCount";
         setterName = null;
         currentResult = new PropertyDescriptor("RequestCount", WseeClusterRoutingRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RequestCount", currentResult);
         currentResult.setValue("description", "The number of requests (messages with no RelatesTo header) that have come through this front-end since the server started. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ResponseCount")) {
         getterName = "getResponseCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ResponseCount", WseeClusterRoutingRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ResponseCount", currentResult);
         currentResult.setValue("description", "The number of responses (messages with a RelatesTo header) that have come through this front-end since the server started. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RoutedRequestCount")) {
         getterName = "getRoutedRequestCount";
         setterName = null;
         currentResult = new PropertyDescriptor("RoutedRequestCount", WseeClusterRoutingRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RoutedRequestCount", currentResult);
         currentResult.setValue("description", "The number of requests that were routed to a specific server instance via routing information in the request (not just load balanced) since the server started. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RoutedResponseCount")) {
         getterName = "getRoutedResponseCount";
         setterName = null;
         currentResult = new PropertyDescriptor("RoutedResponseCount", WseeClusterRoutingRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RoutedResponseCount", currentResult);
         currentResult.setValue("description", "The number of responses that were routed to a specific server instance via routing information in the response (not just load balanced) since the server started. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RoutingFailureCount")) {
         getterName = "getRoutingFailureCount";
         setterName = null;
         currentResult = new PropertyDescriptor("RoutingFailureCount", WseeClusterRoutingRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RoutingFailureCount", currentResult);
         currentResult.setValue("description", "The number of times a message failed to be routed, since the server started. ");
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
