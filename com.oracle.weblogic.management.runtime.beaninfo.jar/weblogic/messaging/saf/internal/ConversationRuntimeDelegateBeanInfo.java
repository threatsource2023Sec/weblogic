package weblogic.messaging.saf.internal;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.SAFConversationRuntimeMBean;

public class ConversationRuntimeDelegateBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = SAFConversationRuntimeMBean.class;

   public ConversationRuntimeDelegateBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ConversationRuntimeDelegateBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.messaging.saf.internal.ConversationRuntimeDelegate");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.0.0");
      beanDescriptor.setValue("package", "weblogic.messaging.saf.internal");
      String description = (new String("<p>This class is used for monitoring a WebLogic SAF conversation.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.SAFConversationRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("ConversationName")) {
         getterName = "getConversationName";
         setterName = null;
         currentResult = new PropertyDescriptor("ConversationName", SAFConversationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConversationName", currentResult);
         currentResult.setValue("description", "<p>The name of the conversation.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DestinationURL")) {
         getterName = "getDestinationURL";
         setterName = null;
         currentResult = new PropertyDescriptor("DestinationURL", SAFConversationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DestinationURL", currentResult);
         currentResult.setValue("description", "<p>The URL of the destination.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("QOS")) {
         getterName = "getQOS";
         setterName = null;
         currentResult = new PropertyDescriptor("QOS", SAFConversationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("QOS", currentResult);
         currentResult.setValue("description", "<p>The quality of service (QOS) for the SAF conversation.</p>  <p>The quality-of-service values are:</p>  <ul> <li> <code>weblogic.management.runtime.SAFConstants.QOS_EXACTLY_ONCE</code></li>  <li> <code>weblogic.management.runtime.SAFConstants.QOS_ATLEAST_ONCE</code></li>  <li> <code>weblogic.management.runtime.SAFConstants.QOS_ATMOST_ONCE</code></li> </ul> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
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
      Method mth = SAFConversationRuntimeMBean.class.getMethod("destroy");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Destroys the conversation and purges all the messages.</p> ");
         currentResult.setValue("role", "operation");
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
