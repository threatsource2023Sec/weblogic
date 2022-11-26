package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class JMSSessionPoolMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = JMSSessionPoolMBean.class;

   public JMSSessionPoolMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public JMSSessionPoolMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.JMSSessionPoolMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("deprecated", "9.0.0.0 Replaced with message-dirven beans.  This functionality will be removed in a future release. ");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("This class represents a JMS session pool, a server-managed pool of server sessions that enables an application to process messages concurrently. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.JMSSessionPoolMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("AcknowledgeMode")) {
         getterName = "getAcknowledgeMode";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAcknowledgeMode";
         }

         currentResult = new PropertyDescriptor("AcknowledgeMode", JMSSessionPoolMBean.class, getterName, setterName);
         descriptors.put("AcknowledgeMode", currentResult);
         currentResult.setValue("description", "<p>The acknowledge mode used by non-transacted sessions within this JMS session pool.</p>  <p>For transacted sessions, messages are acknowledged automatically when the session is committed and this value is ignored.</p> ");
         setPropertyDescriptorDefault(currentResult, "Auto");
         currentResult.setValue("legalValues", new Object[]{"Auto", "Client", "Dups-Ok", "None"});
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionConsumers")) {
         getterName = "getConnectionConsumers";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnectionConsumers";
         }

         currentResult = new PropertyDescriptor("ConnectionConsumers", JMSSessionPoolMBean.class, getterName, setterName);
         descriptors.put("ConnectionConsumers", currentResult);
         currentResult.setValue("description", "<p>The connection consumers for this JMS session pool. JMS connection consumers are queues (Point-To-Point) or topics (Pub/Sub) that retrieve server sessions and process messages. Once you have defined a session pool, you can configure one or more connection consumers for each session pool. This method is provided for backward compatibility.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("remover", "removeConnectionConsumer");
         currentResult.setValue("adder", "addConnectionConsumer");
         currentResult.setValue("deprecated", "9.0.0.0 Replaced with message-driven beans. ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionFactory")) {
         getterName = "getConnectionFactory";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnectionFactory";
         }

         currentResult = new PropertyDescriptor("ConnectionFactory", JMSSessionPoolMBean.class, getterName, setterName);
         descriptors.put("ConnectionFactory", currentResult);
         currentResult.setValue("description", "<p>The JNDI name of the connection factory for this JMS session pool. Connection factories are used to create connections with predefined attributes.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JMSConnectionConsumers")) {
         getterName = "getJMSConnectionConsumers";
         setterName = null;
         currentResult = new PropertyDescriptor("JMSConnectionConsumers", JMSSessionPoolMBean.class, getterName, setterName);
         descriptors.put("JMSConnectionConsumers", currentResult);
         currentResult.setValue("description", "<p>The connection consumers for this JMS session pool. JMS connection consumers are queues (Point-To-Point) or topics (Pub/Sub) that retrieve server sessions and process messages. Once you have defined a session pool, you can configure one or more connection consumers for each session pool.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createJMSConnectionConsumer");
         currentResult.setValue("creator", "createJMSConnectionConsumer");
         currentResult.setValue("destroyer", "destroyJMSConnectionConsumer");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ListenerClass")) {
         getterName = "getListenerClass";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setListenerClass";
         }

         currentResult = new PropertyDescriptor("ListenerClass", JMSSessionPoolMBean.class, getterName, setterName);
         descriptors.put("ListenerClass", currentResult);
         currentResult.setValue("description", "<p>The name of the server-side listener class for this JMS session pool, which is used to receive and process messages concurrently.</p> ");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", JMSSessionPoolMBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>The user-specified name of this MBean instance.</p>  <p>This name is included as one of the key properties in the MBean's <code>javax.management.ObjectName</code>:</p>  <p><code>Name=<i>user-specified-name</i></code></p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("key", Boolean.TRUE);
      }

      if (!descriptors.containsKey("SessionsMaximum")) {
         getterName = "getSessionsMaximum";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSessionsMaximum";
         }

         currentResult = new PropertyDescriptor("SessionsMaximum", JMSSessionPoolMBean.class, getterName, setterName);
         descriptors.put("SessionsMaximum", currentResult);
         currentResult.setValue("description", "<p>The maximum number of sessions allowed for this JMS session pool. A value of <code>-1</code> indicates that there is no maximum.</p>  <p>This attribute is dynamically configurable; however, it does not take effect until the session pool is restarted.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(-1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Tags")) {
         getterName = "getTags";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTags";
         }

         currentResult = new PropertyDescriptor("Tags", JMSSessionPoolMBean.class, getterName, setterName);
         descriptors.put("Tags", currentResult);
         currentResult.setValue("description", "<p>Return all tags on this Configuration MBean</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("DynamicallyCreated")) {
         getterName = "isDynamicallyCreated";
         setterName = null;
         currentResult = new PropertyDescriptor("DynamicallyCreated", JMSSessionPoolMBean.class, getterName, setterName);
         descriptors.put("DynamicallyCreated", currentResult);
         currentResult.setValue("description", "<p>Return whether the MBean was created dynamically or is persisted to config.xml</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("transient", Boolean.TRUE);
      }

      if (!descriptors.containsKey("Transacted")) {
         getterName = "isTransacted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTransacted";
         }

         currentResult = new PropertyDescriptor("Transacted", JMSSessionPoolMBean.class, getterName, setterName);
         descriptors.put("Transacted", currentResult);
         currentResult.setValue("description", "<p>Indicates whether this JMS session pool creates transacted sessions.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = JMSSessionPoolMBean.class.getMethod("createJMSConnectionConsumer", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the connection consumer to create ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>The connection consumers for this JMS session pool. JMS connection consumers are queues (Point-To-Point) or topics (Pub/Sub) that retrieve server sessions and process messages. Once you have defined a session pool, you can configure one or more connection consumers for each session pool.</p>  <p>Create a connection consumer for the session pool.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "JMSConnectionConsumers");
      }

      mth = JMSSessionPoolMBean.class.getMethod("destroyJMSConnectionConsumer", JMSConnectionConsumerMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("connectionConsumer", "a reference to  JMSConnectionConsumerMBean ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Remove a connection consumer from the session pool.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "JMSConnectionConsumers");
      }

      mth = JMSSessionPoolMBean.class.getMethod("createJMSConnectionConsumer", String.class, JMSConnectionConsumerMBean.class);
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "JMSConnectionConsumers");
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      MethodDescriptor currentResult;
      Method mth;
      ParameterDescriptor[] parameterDescriptors;
      String methodKey;
      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = JMSSessionPoolMBean.class.getMethod("addConnectionConsumer", JMSConnectionConsumerMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("connectionConsumer", "the connection consumer to add ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 Replaced with message-driven beans. ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Add a connection consumer to the session pool. This method is provided for backward compatibility.</p> ");
            currentResult.setValue("transient", Boolean.TRUE);
            currentResult.setValue("role", "collection");
            currentResult.setValue("property", "ConnectionConsumers");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = JMSSessionPoolMBean.class.getMethod("removeConnectionConsumer", JMSConnectionConsumerMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("connectionConsumer", "a reference to  JMSConnectionConsumerMBean ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 Replaced with message-driven beans. ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Remove a connection consumer from the session pool. This method is provided for backward compatibility.</p> ");
            currentResult.setValue("transient", Boolean.TRUE);
            currentResult.setValue("role", "collection");
            currentResult.setValue("property", "ConnectionConsumers");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      String[] throwsObjectArray;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = JMSSessionPoolMBean.class.getMethod("addTag", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("tag", "tag to be added to the MBean ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("IllegalArgumentException if the tag contains illegal punctuation")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Add a tag to this Configuration MBean.  Adds a tag to the current set of tags on the Configuration MBean.  Tags may contain white spaces.</p> ");
            currentResult.setValue("role", "collection");
            currentResult.setValue("property", "Tags");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = JMSSessionPoolMBean.class.getMethod("removeTag", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("tag", "tag to be removed from the MBean ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("IllegalArgumentException if the tag contains illegal punctuation")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Remove a tag from this Configuration MBean</p> ");
            currentResult.setValue("role", "collection");
            currentResult.setValue("property", "Tags");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = JMSSessionPoolMBean.class.getMethod("lookupJMSConnectionConsumer", String.class);
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Get a connection consumer from the session pool by name. ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "JMSConnectionConsumers");
      }

   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = JMSSessionPoolMBean.class.getMethod("destroyJMSConnectionConsumer", String.class, JMSConnectionConsumerMBean.class);
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = JMSSessionPoolMBean.class.getMethod("freezeCurrentValue", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("attributeName", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>If the specified attribute has not been set explicitly, and if the attribute has a default value, this operation forces the MBean to persist the default value.</p>  <p>Unless you use this operation, the default value is not saved and is subject to change if you update to a newer release of WebLogic Server. Invoking this operation isolates this MBean from the effects of such changes.</p>  <p>Note: To insure that you are freezing the default value, invoke the <code>restoreDefaultValue</code> operation before you invoke this.</p>  <p>This operation has no effect if you invoke it on an attribute that does not provide a default value or on an attribute for which some other value has been set.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = JMSSessionPoolMBean.class.getMethod("restoreDefaultValue", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("attributeName", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey) && !this.readOnly) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>If the specified attribute has a default value, this operation removes any value that has been set explicitly and causes the attribute to use the default value.</p>  <p>Default values are subject to change if you update to a newer release of WebLogic Server. To prevent the value from changing if you update to a newer release, invoke the <code>freezeCurrentValue</code> operation.</p>  <p>This operation has no effect if you invoke it on an attribute that does not provide a default value or on an attribute that is already using the default.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("impact", "action");
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
