package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class JMSConnectionFactoryBeanImplBeanInfo extends TargetableBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = JMSConnectionFactoryBean.class;

   public JMSConnectionFactoryBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public JMSConnectionFactoryBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.JMSConnectionFactoryBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("Connection factories are used to create connections for JMS clients.  Connection factories can configure properties of the connections returned to the JMS client. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.JMSConnectionFactoryBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ClientParams")) {
         getterName = "getClientParams";
         setterName = null;
         currentResult = new PropertyDescriptor("ClientParams", JMSConnectionFactoryBean.class, getterName, setterName);
         descriptors.put("ClientParams", currentResult);
         currentResult.setValue("description", "The client parameters for this connection factory.  <p> Several parameters that govern JMS server behavior with regard to a client are configured using a client parameters bean. For example, the client Id when using a  connection factory. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultDeliveryParams")) {
         getterName = "getDefaultDeliveryParams";
         setterName = null;
         currentResult = new PropertyDescriptor("DefaultDeliveryParams", JMSConnectionFactoryBean.class, getterName, setterName);
         descriptors.put("DefaultDeliveryParams", currentResult);
         currentResult.setValue("description", "The default delivery parameters for this connection factory.  <p> If a client does not specify certain parameters then the values that those parameters will take can be controlled with a default delivery parameters bean. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FlowControlParams")) {
         getterName = "getFlowControlParams";
         setterName = null;
         currentResult = new PropertyDescriptor("FlowControlParams", JMSConnectionFactoryBean.class, getterName, setterName);
         descriptors.put("FlowControlParams", currentResult);
         currentResult.setValue("description", "The flow control parameters for this connection factory.  <p> Many clients producing messages can cause the server to fall behind in processing messages.  The flow control parameters can help by slowing down production of messages.  Using flow control can help the overall throughput of the system. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JNDIName")) {
         getterName = "getJNDIName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJNDIName";
         }

         currentResult = new PropertyDescriptor("JNDIName", JMSConnectionFactoryBean.class, getterName, setterName);
         descriptors.put("JNDIName", currentResult);
         currentResult.setValue("description", "<p>The global JNDI name used to look up a connection factory within a clustered JNDI namespace.</p>  <p>In a clustered environment, this name is propagated to the entire cluster. If you want the JNDI name to be bound only on the local server, and not propagated to the rest of the cluster, then use the <code>Local JNDI Name</code> setting.</p>  <p>If not specified, then the connection factory is not bound into a clustered JNDI namespace.</p> ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getLocalJNDIName")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LoadBalancingParams")) {
         getterName = "getLoadBalancingParams";
         setterName = null;
         currentResult = new PropertyDescriptor("LoadBalancingParams", JMSConnectionFactoryBean.class, getterName, setterName);
         descriptors.put("LoadBalancingParams", currentResult);
         currentResult.setValue("description", "The load balancing parameters for this connection factory.  <p> Multiple clients may use this connection factory.  The load balancing parameters allow those client to choose how they wish to distribute the work to the configured servers. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LocalJNDIName")) {
         getterName = "getLocalJNDIName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLocalJNDIName";
         }

         currentResult = new PropertyDescriptor("LocalJNDIName", JMSConnectionFactoryBean.class, getterName, setterName);
         descriptors.put("LocalJNDIName", currentResult);
         currentResult.setValue("description", "<p>The local JNDI name used to look up the connection factory within the JNDI namespace of the server where the connection factory is targeted. In a clustered environment, this name is bound only on the local server instance and is not propagated to the rest of the cluster.</p>  <p>If not specified, then the connection factory is not bound into the local JNDI namespace.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", JMSConnectionFactoryBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SecurityParams")) {
         getterName = "getSecurityParams";
         setterName = null;
         currentResult = new PropertyDescriptor("SecurityParams", JMSConnectionFactoryBean.class, getterName, setterName);
         descriptors.put("SecurityParams", currentResult);
         currentResult.setValue("description", "The security parameters for this connection factory.  <p> Some clients may wish to customize the security information associated with them.  They can use the security parameters bean to do so. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TransactionParams")) {
         getterName = "getTransactionParams";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionParams", JMSConnectionFactoryBean.class, getterName, setterName);
         descriptors.put("TransactionParams", currentResult);
         currentResult.setValue("description", "The transactional parameters for this connection factory.  <p> The transaction parameters bean controls how transactions are handled from clients who connect using this factory. </p> ");
         currentResult.setValue("relationship", "containment");
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
