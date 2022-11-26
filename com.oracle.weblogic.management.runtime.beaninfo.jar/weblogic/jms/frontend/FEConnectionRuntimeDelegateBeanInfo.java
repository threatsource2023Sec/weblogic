package weblogic.jms.frontend;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.JMSConnectionRuntimeMBean;

public class FEConnectionRuntimeDelegateBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = JMSConnectionRuntimeMBean.class;

   public FEConnectionRuntimeDelegateBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public FEConnectionRuntimeDelegateBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.jms.frontend.FEConnectionRuntimeDelegate");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.jms.frontend");
      String description = (new String("<p>This class is used for monitoring a WebLogic JMS connection.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.JMSConnectionRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("ClientID")) {
         getterName = "getClientID";
         setterName = null;
         currentResult = new PropertyDescriptor("ClientID", JMSConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ClientID", currentResult);
         currentResult.setValue("description", "<p>The client ID for this connection.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ClientIDPolicy")) {
         getterName = "getClientIDPolicy";
         setterName = null;
         currentResult = new PropertyDescriptor("ClientIDPolicy", JMSConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ClientIDPolicy", currentResult);
         currentResult.setValue("description", "<p>The ClientIDPolicy on this connection or durable subscriber.</p> <p>Values are:</p> <ul> <li><code>weblogic.management.configuration.JMSConstants.CLIENT_ID_POLICY_RESTRICTED</code>: Only one connection that uses this policy exists in a cluster at any given time for a particular <code>ClientID</code>.</li> <li><code>weblogic.management.configuration.JMSConstants.CLIENT_ID_POLICY_UNRESTRICTED</code>: Connections created using this policy can specify any <code>ClientID</code>, even when other restricted or unrestricted connections already use the same <code>ClientID</code>.</li> </ul> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HostAddress")) {
         getterName = "getHostAddress";
         setterName = null;
         currentResult = new PropertyDescriptor("HostAddress", JMSConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("HostAddress", currentResult);
         currentResult.setValue("description", "<p>The host address of the client JVM as a string.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Sessions")) {
         getterName = "getSessions";
         setterName = null;
         currentResult = new PropertyDescriptor("Sessions", JMSConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Sessions", currentResult);
         currentResult.setValue("description", "<p>An array of sessions for this connection.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SessionsCurrentCount")) {
         getterName = "getSessionsCurrentCount";
         setterName = null;
         currentResult = new PropertyDescriptor("SessionsCurrentCount", JMSConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SessionsCurrentCount", currentResult);
         currentResult.setValue("description", "<p>The current number of sessions for this connection.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SessionsHighCount")) {
         getterName = "getSessionsHighCount";
         setterName = null;
         currentResult = new PropertyDescriptor("SessionsHighCount", JMSConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SessionsHighCount", currentResult);
         currentResult.setValue("description", "<p>The peak number of sessions for this connection since the last reset.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SessionsTotalCount")) {
         getterName = "getSessionsTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("SessionsTotalCount", JMSConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SessionsTotalCount", currentResult);
         currentResult.setValue("description", "<p>The number of sessions on this connection since the last reset.</p> ");
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
      Method mth = JMSConnectionRuntimeMBean.class.getMethod("destroy");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Destroys server side context for the connection.</p> ");
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
