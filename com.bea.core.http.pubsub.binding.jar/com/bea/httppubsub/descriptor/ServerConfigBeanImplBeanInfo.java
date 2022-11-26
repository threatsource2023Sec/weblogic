package com.bea.httppubsub.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class ServerConfigBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = ServerConfigBean.class;

   public ServerConfigBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ServerConfigBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("com.bea.httppubsub.descriptor.ServerConfigBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "10.3.0.0");
      beanDescriptor.setValue("package", "com.bea.httppubsub.descriptor");
      String description = (new String("Server bean interface. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "com.bea.httppubsub.descriptor.ServerConfigBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ClientTimeoutSecs")) {
         getterName = "getClientTimeoutSecs";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setClientTimeoutSecs";
         }

         currentResult = new PropertyDescriptor("ClientTimeoutSecs", ServerConfigBean.class, getterName, setterName);
         descriptors.put("ClientTimeoutSecs", currentResult);
         currentResult.setValue("description", "Client timeout value in second unit. Client will get timeout and disconnected by server if it doesn't send back connect/reconnect message during this period. ");
         setPropertyDescriptorDefault(currentResult, new Integer(60));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionIdleTimeoutSecs")) {
         getterName = "getConnectionIdleTimeoutSecs";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnectionIdleTimeoutSecs";
         }

         currentResult = new PropertyDescriptor("ConnectionIdleTimeoutSecs", ServerConfigBean.class, getterName, setterName);
         descriptors.put("ConnectionIdleTimeoutSecs", currentResult);
         currentResult.setValue("description", "Connection idle timeout value in second unit. If client's connection with reconnect message doesn't receive any message during this period, it will receive a reconnect response with advice to suggest it to re- handshake. ");
         setPropertyDescriptorDefault(currentResult, new Integer(40));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CookiePath")) {
         getterName = "getCookiePath";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCookiePath";
         }

         currentResult = new PropertyDescriptor("CookiePath", ServerConfigBean.class, getterName, setterName);
         descriptors.put("CookiePath", currentResult);
         currentResult.setValue("description", "Define cookie path, for examples: /foo, /foo/bar ");
         setPropertyDescriptorDefault(currentResult, "/");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IntervalMillisecs")) {
         getterName = "getIntervalMillisecs";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIntervalMillisecs";
         }

         currentResult = new PropertyDescriptor("IntervalMillisecs", ServerConfigBean.class, getterName, setterName);
         descriptors.put("IntervalMillisecs", currentResult);
         currentResult.setValue("description", "An integer representing the period in milliseconds for a client to delay subsequent requests to the /meta/connect channel. A negative period indicates that the message should not be retried. ");
         setPropertyDescriptorDefault(currentResult, new Integer(500));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MultiFrameIntervalMillisecs")) {
         getterName = "getMultiFrameIntervalMillisecs";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMultiFrameIntervalMillisecs";
         }

         currentResult = new PropertyDescriptor("MultiFrameIntervalMillisecs", ServerConfigBean.class, getterName, setterName);
         descriptors.put("MultiFrameIntervalMillisecs", currentResult);
         currentResult.setValue("description", "An integer representing the period in milliseconds for a client to delay subsequent requests to the /meta/connect channel when multi frame is detected. Traditional polling will be used when multi frame from same client is detected by server. The values defines interval between traditional pollings. ");
         setPropertyDescriptorDefault(currentResult, new Integer(3000));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", ServerConfigBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "Configure pubsub server's name. If it is not explicitly configured, server name will be set automatically with servlet path as its value when startup. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PersistentClientTimeoutSecs")) {
         getterName = "getPersistentClientTimeoutSecs";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPersistentClientTimeoutSecs";
         }

         currentResult = new PropertyDescriptor("PersistentClientTimeoutSecs", ServerConfigBean.class, getterName, setterName);
         descriptors.put("PersistentClientTimeoutSecs", currentResult);
         currentResult.setValue("description", "An integer representing the period in seconds for a client going offline and re-connect afterwards. If client reconnect during this period, client could recieve all messages it may interested during this period, otherwise messages will be lost. ");
         setPropertyDescriptorDefault(currentResult, new Integer(600));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportedTransport")) {
         getterName = "getSupportedTransport";
         setterName = null;
         currentResult = new PropertyDescriptor("SupportedTransport", ServerConfigBean.class, getterName, setterName);
         descriptors.put("SupportedTransport", currentResult);
         currentResult.setValue("description", "Return transport types server is configured to support. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createSupportedTransport");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WorkManager")) {
         getterName = "getWorkManager";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setWorkManager";
         }

         currentResult = new PropertyDescriptor("WorkManager", ServerConfigBean.class, getterName, setterName);
         descriptors.put("WorkManager", currentResult);
         currentResult.setValue("description", "Set the WorkManager name that will be used to deliver event messages to subscribed clients. If it is not explicitly configured, the server will use server name to find or create the work manager. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PublishWithoutConnectAllowed")) {
         getterName = "isPublishWithoutConnectAllowed";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPublishWithoutConnectAllowed";
         }

         currentResult = new PropertyDescriptor("PublishWithoutConnectAllowed", ServerConfigBean.class, getterName, setterName);
         descriptors.put("PublishWithoutConnectAllowed", currentResult);
         currentResult.setValue("description", "Decide if allow publish message to server without handshake or connect. When it's set to true, client can publish messages directly. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ServerConfigBean.class.getMethod("createSupportedTransport");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SupportedTransport");
      }

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
