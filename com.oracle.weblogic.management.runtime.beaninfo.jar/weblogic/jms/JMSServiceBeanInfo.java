package weblogic.jms;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.runtime.JMSRuntimeMBean;

public class JMSServiceBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = JMSRuntimeMBean.class;

   public JMSServiceBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public JMSServiceBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.jms.JMSService");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.jms");
      String description = (new String("This class is used for monitoring a WebLogic JMS service. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.JMSRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("Connections")) {
         getterName = "getConnections";
         setterName = null;
         currentResult = new PropertyDescriptor("Connections", JMSRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Connections", currentResult);
         currentResult.setValue("description", "<p>The connections to this WebLogic server.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionsCurrentCount")) {
         getterName = "getConnectionsCurrentCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ConnectionsCurrentCount", JMSRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConnectionsCurrentCount", currentResult);
         currentResult.setValue("description", "<p>The current number of connections to WebLogic Server server.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionsHighCount")) {
         getterName = "getConnectionsHighCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ConnectionsHighCount", JMSRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConnectionsHighCount", currentResult);
         currentResult.setValue("description", "<p>The highest number of connections to this WebLogic Server since the last reset.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionsTotalCount")) {
         getterName = "getConnectionsTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ConnectionsTotalCount", JMSRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConnectionsTotalCount", currentResult);
         currentResult.setValue("description", "<p>The total number of connections made to this WebLogic Server since the last reset.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HealthState")) {
         getterName = "getHealthState";
         setterName = null;
         currentResult = new PropertyDescriptor("HealthState", JMSRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("HealthState", currentResult);
         currentResult.setValue("description", "<p>The health state of this JMS service.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JMSPooledConnections")) {
         getterName = "getJMSPooledConnections";
         setterName = null;
         currentResult = new PropertyDescriptor("JMSPooledConnections", JMSRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("JMSPooledConnections", currentResult);
         currentResult.setValue("description", "<p>The JMS pooled connections to this WebLogic server.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JMSServers")) {
         getterName = "getJMSServers";
         setterName = null;
         currentResult = new PropertyDescriptor("JMSServers", JMSRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("JMSServers", currentResult);
         currentResult.setValue("description", "<p>The JMS servers that are currently deployed on this WebLogic Server instance.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JMSServersCurrentCount")) {
         getterName = "getJMSServersCurrentCount";
         setterName = null;
         currentResult = new PropertyDescriptor("JMSServersCurrentCount", JMSRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("JMSServersCurrentCount", currentResult);
         currentResult.setValue("description", "<p>The current number of JMS servers that are deployed on this WebLogic Server instance.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JMSServersHighCount")) {
         getterName = "getJMSServersHighCount";
         setterName = null;
         currentResult = new PropertyDescriptor("JMSServersHighCount", JMSRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("JMSServersHighCount", currentResult);
         currentResult.setValue("description", "<p>The highest number of JMS servers that were deployed on this WebLogic Server instance since this server was started.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JMSServersTotalCount")) {
         getterName = "getJMSServersTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("JMSServersTotalCount", JMSRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("JMSServersTotalCount", currentResult);
         currentResult.setValue("description", "<p>The total number of JMS servers that were deployed on this WebLogic Server instance since this server was started.</p> ");
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
