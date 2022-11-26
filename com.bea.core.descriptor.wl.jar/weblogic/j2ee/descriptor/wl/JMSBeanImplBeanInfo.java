package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.descriptor.SettableBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class JMSBeanImplBeanInfo extends SettableBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = JMSBean.class;

   public JMSBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public JMSBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.JMSBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("The top of the JMS module bean tree. <p> JMS modules all have a JMSBean as their root bean (a bean with no parent).  The schema namespace that corresponds to this bean is \"http://xmlns.oracle.com/weblogic/weblogic-jms\" </p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.JMSBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ConnectionFactories")) {
         getterName = "getConnectionFactories";
         setterName = null;
         currentResult = new PropertyDescriptor("ConnectionFactories", JMSBean.class, getterName, setterName);
         descriptors.put("ConnectionFactories", currentResult);
         currentResult.setValue("description", "<p> Defines a set of connection configuration parameters that are used to create connections for JMS clients.</p> <p> Connection factories can configure properties of the connections returned to the JMS client, and also provide configurable options for default delivery, transaction, and message flow control parameters. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyConnectionFactory");
         currentResult.setValue("creator", "createConnectionFactory");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DestinationKeys")) {
         getterName = "getDestinationKeys";
         setterName = null;
         currentResult = new PropertyDescriptor("DestinationKeys", JMSBean.class, getterName, setterName);
         descriptors.put("DestinationKeys", currentResult);
         currentResult.setValue("description", "<p> Defines a unique sort order that destinations can apply to arriving messages.</p> <p> By default messages are sorted in FIFO (first-in, first-out) order, which sorts ascending based on each message's unique JMSMessageID. However, you can configure destination key to use a different sorting scheme for a destination, such as LIFO (last-in, first-out).</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyDestinationKey");
         currentResult.setValue("creator", "createDestinationKey");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DistributedQueues")) {
         getterName = "getDistributedQueues";
         setterName = null;
         currentResult = new PropertyDescriptor("DistributedQueues", JMSBean.class, getterName, setterName);
         descriptors.put("DistributedQueues", currentResult);
         currentResult.setValue("description", "<p> Defines a set of queues that are distributed on multiple JMS servers, but which are accessible as a single, logical queue to JMS clients.</p> <p> Distributed queues can help with message load balancing and distribution, and have many of the same properties as standalone queues.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyDistributedQueue");
         currentResult.setValue("creator", "createDistributedQueue");
         currentResult.setValue("deprecated", "10.3.4.0, since Weighted Distributed Destination has been deprecated and replaced by Uniform Distributed Destination ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DistributedTopics")) {
         getterName = "getDistributedTopics";
         setterName = null;
         currentResult = new PropertyDescriptor("DistributedTopics", JMSBean.class, getterName, setterName);
         descriptors.put("DistributedTopics", currentResult);
         currentResult.setValue("description", "Defines a set of topics that are distributed on multiple JMS servers, but which are accessible as a single, logical topic to JMS clients.</p> <p> Distributed topics can help with load balancing and distribution, and have many of the same properties as standalone topics.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createDistributedTopic");
         currentResult.setValue("destroyer", "destroyDistributedTopic");
         currentResult.setValue("deprecated", "10.3.4.0, since Weighted Distributed Destination has been deprecated and replaced by Uniform Distributed Destination ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ForeignServers")) {
         getterName = "getForeignServers";
         setterName = null;
         currentResult = new PropertyDescriptor("ForeignServers", JMSBean.class, getterName, setterName);
         descriptors.put("ForeignServers", currentResult);
         currentResult.setValue("description", "<p> Defines foreign messaging providers or remote WebLogic Server instances that are not part of the current domain.</p> <p> This is useful when integrating with another vendor's JMS product, or when referencing remote instances of WebLogic Server in another cluster or domain in the local WebLogic JNDI tree.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createForeignServer");
         currentResult.setValue("destroyer", "destroyForeignServer");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Notes")) {
         getterName = "getNotes";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNotes";
         }

         currentResult = new PropertyDescriptor("Notes", JMSBean.class, getterName, setterName);
         descriptors.put("Notes", currentResult);
         currentResult.setValue("description", "<p>Optional information that you can include to describe this JMS module.</p>  <p>JMS module saves this note in the JMS descriptor file as XML PCDATA. All left angle brackets (&lt;) are converted to the XML entity <code>&amp;lt;</code>. Carriage returns/line feeds are preserved.</p>  <dl> <dt>Note:</dt>  <dd> <p>If you create or edit a note from the Administration Console, the Administration Console does not preserve carriage returns/line feeds.</p> </dd> </dl> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Queues")) {
         getterName = "getQueues";
         setterName = null;
         currentResult = new PropertyDescriptor("Queues", JMSBean.class, getterName, setterName);
         descriptors.put("Queues", currentResult);
         currentResult.setValue("description", "<p> Defines a point-to-point destination type, which are used for asynchronous peer communications. A message delivered to a queue is distributed to only one consumer.</p> <p> Several aspects of a queue's behavior can be configured, including thresholds, logging, delivery overrides, and delivery failure options.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyQueue");
         currentResult.setValue("creator", "createQueue");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Quotas")) {
         getterName = "getQuotas";
         setterName = null;
         currentResult = new PropertyDescriptor("Quotas", JMSBean.class, getterName, setterName);
         descriptors.put("Quotas", currentResult);
         currentResult.setValue("description", "<p> Controls the allotment of system resources available to destinations.</p> <p> For example, the number of bytes a destination is allowed to store can be configured with a Quota resource. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyQuota");
         currentResult.setValue("creator", "createQuota");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SAFErrorHandlings")) {
         getterName = "getSAFErrorHandlings";
         setterName = null;
         currentResult = new PropertyDescriptor("SAFErrorHandlings", JMSBean.class, getterName, setterName);
         descriptors.put("SAFErrorHandlings", currentResult);
         currentResult.setValue("description", "<p> Defines the action to take when the SAF service fails to forward messages to remote destinations. </p> <p> Configuration options include an Error Handling Policy (Redirect, Log, Discard, or Always-Forward), a Log Format, and sending Retry parameters.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createSAFErrorHandling");
         currentResult.setValue("destroyer", "destroySAFErrorHandling");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SAFImportedDestinations")) {
         getterName = "getSAFImportedDestinations";
         setterName = null;
         currentResult = new PropertyDescriptor("SAFImportedDestinations", JMSBean.class, getterName, setterName);
         descriptors.put("SAFImportedDestinations", currentResult);
         currentResult.setValue("description", "<p> Defines a collection of imported store-and-forward (SAF) destinations. A SAF destination is a representation of a queue or topic in a remote server instance or cluster that is imported into the local cluster or server instance, so that the local server instance or cluster can send messages to the remote server instance or cluster.</p> <p> All JMS destinations are automatically exported by default, unless the Export SAF Destination parameter on a destination is explicitly disabled. Each collection of SAF imported destinations is associated with a remote SAF context resource, and, optionally, a SAF error handling resource.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createSAFImportedDestinations");
         currentResult.setValue("destroyer", "destroySAFImportedDestinations");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SAFRemoteContexts")) {
         getterName = "getSAFRemoteContexts";
         setterName = null;
         currentResult = new PropertyDescriptor("SAFRemoteContexts", JMSBean.class, getterName, setterName);
         descriptors.put("SAFRemoteContexts", currentResult);
         currentResult.setValue("description", "<p> Defines the URL of the remote server instance or cluster where a JMS destination is exported from. It also contains the security credentials to be authenticated and authorized in the remote cluster or server.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroySAFRemoteContext");
         currentResult.setValue("creator", "createSAFRemoteContext");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Templates")) {
         getterName = "getTemplates";
         setterName = null;
         currentResult = new PropertyDescriptor("Templates", JMSBean.class, getterName, setterName);
         descriptors.put("Templates", currentResult);
         currentResult.setValue("description", "<p> Defines a set of default configuration settings for multiple destinations.</p> <p> If a destination specifies a template and does not explicitly set the value of a parameter, then that parameter will take its value from the specified template. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyTemplate");
         currentResult.setValue("creator", "createTemplate");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Topics")) {
         getterName = "getTopics";
         setterName = null;
         currentResult = new PropertyDescriptor("Topics", JMSBean.class, getterName, setterName);
         descriptors.put("Topics", currentResult);
         currentResult.setValue("description", "<p> Defines a publish/subscribe destination type, which are used for asynchronous peer communications. A message delivered to a topic is distributed to all topic consumers.</p> <p> Several aspects of a topic's behavior can be configured, including thresholds, logging, delivery overrides, delivery failure, and multicasting parameters. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyTopic");
         currentResult.setValue("creator", "createTopic");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UniformDistributedQueues")) {
         getterName = "getUniformDistributedQueues";
         setterName = null;
         currentResult = new PropertyDescriptor("UniformDistributedQueues", JMSBean.class, getterName, setterName);
         descriptors.put("UniformDistributedQueues", currentResult);
         currentResult.setValue("description", "<p> Defines a uniformly configured distributed queue, whose members have a consistent configuration of all distributed queue parameters, particularly in regards to weighting, security, persistence, paging, and quotas. These uniform distributed queue members are created on JMS servers based on the targeting of the uniform distributed queue.</p> <p> Uniform distributed queues can help with message load balancing and distribution, and have many of the same properties as standalone queues, including thresholds, logging, delivery overrides, and delivery failure parameters.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyUniformDistributedQueue");
         currentResult.setValue("creator", "createUniformDistributedQueue");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UniformDistributedTopics")) {
         getterName = "getUniformDistributedTopics";
         setterName = null;
         currentResult = new PropertyDescriptor("UniformDistributedTopics", JMSBean.class, getterName, setterName);
         descriptors.put("UniformDistributedTopics", currentResult);
         currentResult.setValue("description", "<p> Defines a uniformly configured distributed topic, whose members have a consistent configuration of all uniform distributed queue parameters, particularly in regards to weighting, security, persistence, paging, and quotas. These uniform distributed topic members are created on JMS servers based on the targeting of the uniform distributed topic.</p> <p> Uniform distributed topics can help with message load balancing and distribution, and have many of the same properties as standalone topics, including thresholds, logging, delivery overrides, and delivery failure parameters.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createUniformDistributedTopic");
         currentResult.setValue("destroyer", "destroyUniformDistributedTopic");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Version")) {
         getterName = "getVersion";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setVersion";
         }

         currentResult = new PropertyDescriptor("Version", JMSBean.class, getterName, setterName);
         descriptors.put("Version", currentResult);
         currentResult.setValue("description", "The version of this file. ");
         setPropertyDescriptorDefault(currentResult, new Integer(1));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = JMSBean.class.getMethod("createQuota", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the quota bean to created ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates a quota bean and adds it to this JMS module  <p> Quota beans control the allotment of system resources available to destinations.  For example the number of bytes a destination is allowed to store can be configured with a QuotaBean </p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Quotas");
      }

      mth = JMSBean.class.getMethod("destroyQuota", QuotaBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("quota", "The quota bean to be removed ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Removes a quota bean from this JMS module  <p> Quota beans control the allotment of system resources available to destinations.  For example the number of bytes a destination is allowed to store can be configured with a QuotaBean </p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Quotas");
      }

      mth = JMSBean.class.getMethod("createTemplate", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the template to create ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates a template bean and adds it to this JMS module  <p> Template beans contain default values for destination attributes.  If a destination defines a template and does not explicitly set the value of an attribute, then that attribute will take its value from the defined template. </p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Templates");
      }

      mth = JMSBean.class.getMethod("destroyTemplate", TemplateBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("template", "The template to remove from this JMS module ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Removes a template bean from this JMS module  <p> Template beans contain default values for destination attributes.  If a destination defines a template and does not explicitly set the value of an attribute, then that attribute will take its value from the defined template. </p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Templates");
      }

      mth = JMSBean.class.getMethod("createDestinationKey", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the destination key to be added to this JMS module ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates a destination key bean and adds it to this JMS module  <p> Destination Key beans control the sorting criteria of JMS destinations </p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DestinationKeys");
      }

      mth = JMSBean.class.getMethod("destroyDestinationKey", DestinationKeyBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("destinationKey", "The destination key to be removed from this JMS module ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Removes a destination key bean from this JMS module  <p> Destination Key beans control the sorting criteria of JMS destinations </p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DestinationKeys");
      }

      mth = JMSBean.class.getMethod("createConnectionFactory", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the connection factory to add to this JMS module ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates a connection factory bean and adds it to this JMS module  <p> Connection factories are used to create connections for JMS clients.  Connection factories can configure properties of the connections returned to the JMS client. </p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ConnectionFactories");
      }

      mth = JMSBean.class.getMethod("destroyConnectionFactory", JMSConnectionFactoryBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("connectionFactory", "The connection factory bean to remove from this JMS module ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Removes a connection factory bean from this JMS module  <p> Connection factories are used to create connections for JMS clients.  Connection factories can configure properties of the connections returned to the JMS client. </p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ConnectionFactories");
      }

      mth = JMSBean.class.getMethod("createForeignServer", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the foreign server bean to add to this JMS module ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates a foreign server bean and adds it to this JMS module  <p> Foreign servers are used to configure messaging providers that are not part of the current domain.  This is useful when integrating with another vendors JMS product. </p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ForeignServers");
      }

      mth = JMSBean.class.getMethod("destroyForeignServer", ForeignServerBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("foreignServer", "The foreign server bean to remove from this JMS module ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Removes a foreign server bean from this JMS module  <p> Foreign servers are used to configure messaging providers that are not part of the current domain.  This is useful when integrating with another vendors JMS product. </p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ForeignServers");
      }

      mth = JMSBean.class.getMethod("createQueue", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the queue bean to be added to this JMS module ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates a queue bean and adds it to this JMS module  <p> Queues are used for asynchronous peer communications.  A message delivered to a queue will be distributed to one consumer.  Several aspects of a queues behavior can be configured with a queue bean. </p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Queues");
      }

      mth = JMSBean.class.getMethod("destroyQueue", QueueBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("queue", "The queue to be removed from this JMS module ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Removes a queue bean from this JMS module  <p> Queues are used for asynchronous peer communications.  A message delivered to a queue will be distributed to one consumer.  Several aspects of a queues behavior can be configured with a queue bean. </p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Queues");
      }

      mth = JMSBean.class.getMethod("createTopic", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the topic bean to add to this JMS module ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates a topic bean and adds it to this JMS module  <p> Topics are used for asynchronous peer communications.  A message delivered to a topic will be distributed to all topic consumers.  Several aspects of a topics behavior can be configured with a topic bean. </p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Topics");
      }

      mth = JMSBean.class.getMethod("destroyTopic", TopicBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("topic", "The topic to be removed from this JMS module ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Removes a topic bean from this JMS module  <p> Topics are used for asynchronous peer communications.  A message delivered to a topic will be distributed to all topic consumers.  Several aspects of a topics behavior can be configured with a topic bean. </p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Topics");
      }

      mth = JMSBean.class.getMethod("createDistributedQueue", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the distributed queue bean to add to this JMS module ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "10.3.4.0, since Weighted Distributed Destination has been deprecated and replaced by Uniform Distributed Destination ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates a distributed queue bean and adds it to this JMS module  <p> A distributed queue is a queue that is located on many JMS servers.  Distributed queues can help with load balancing and distribution, and have many of the same properties as normal queues.  Many aspects of a distributed queues behavior can be configured with a distributed queue bean. </p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DistributedQueues");
      }

      mth = JMSBean.class.getMethod("destroyDistributedQueue", DistributedQueueBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("distributedQueue", "The distributed queue bean to remove from this JMS module ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "10.3.4.0, since Weighted Distributed Destination has been deprecated and replaced by Uniform Distributed Destination ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Removes a distributed queue bean from this JMS module  <p> A distributed queue is a queue that is located on many JMS servers.  Distributed queues can help with load balancing and distribution, and have many of the same properties as normal queues.  Many aspects of a distributed queues behavior can be configured with a distributed queue bean. </p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DistributedQueues");
      }

      mth = JMSBean.class.getMethod("createDistributedTopic", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the distributed topic bean to add to this JMS module ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "10.3.4.0, since Weighted Distributed Destination has been deprecated and replaced by Uniform Distributed Destination ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates a distributed topic bean and adds it to this JMS module  <p> A distributed topic is a topic that is located on many JMS servers.  Distributed topics can help with load balancing and distribution, and have many of the same properties as normal topics.  Many aspects of a distributed topics behavior can be configured with a distributed topic bean. </p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DistributedTopics");
      }

      mth = JMSBean.class.getMethod("destroyDistributedTopic", DistributedTopicBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("distributedTopic", "The distributed topic bean to remove from this JMS module ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "10.3.4.0, since Weighted Distributed Destination has been deprecated and replaced by Uniform Distributed Destination ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Removes a distributed topic bean from this JMS module  <p> A distributed topic is a topic that is located on many JMS servers.  Distributed topics can help with load balancing and distribution, and have many of the same properties as normal topics.  Many aspects of a distributed topics behavior can be configured with a distributed topic bean. </p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DistributedTopics");
      }

      mth = JMSBean.class.getMethod("createUniformDistributedQueue", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the uniform distributed queue bean to add to this JMS module ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates a uniform distributed queue bean and adds it to this JMS module  <p> A uniform distributed queue is a distributed queue whose members are configured as part of the configuration of the distributed queue.  These members are, therefore, uniform and are created based on the targeting of the uniform distributed queue. </p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "UniformDistributedQueues");
      }

      mth = JMSBean.class.getMethod("destroyUniformDistributedQueue", UniformDistributedQueueBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("uniformDistributedQueue", "The uniform distributed queue bean to remove from this JMS module ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Removes a uniform distributed queue bean from this JMS module  <p> A uniform distributed queue is a distributed queue whose members are configured as part of the configuration of the distributed queue.  These members are, therefore, uniform and are created based on the targeting of the uniform distributed queue. </p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "UniformDistributedQueues");
      }

      mth = JMSBean.class.getMethod("createUniformDistributedTopic", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the uniform distributed topic bean to add to this JMS module ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates a uniform distributed topic bean and adds it to this JMS module  <p> A uniform distributed topic is a distributed topic whose members are configured as part of the configuration of the distributed topic.  These members are, therefore, uniform and are created based on the targeting of the uniform distributed topic. </p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "UniformDistributedTopics");
      }

      mth = JMSBean.class.getMethod("destroyUniformDistributedTopic", UniformDistributedTopicBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("uniformDistributedTopic", "The uniform distributed topic bean to remove from this JMS module ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Removes a uniform distributed topic bean from this JMS module  <p> A uniform distributed topic is a distributed topic whose members are configured as part of the configuration of the distributed topic.  These members are, therefore, uniform and are created based on the targeting of the uniform distributed topic. </p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "UniformDistributedTopics");
      }

      mth = JMSBean.class.getMethod("createSAFImportedDestinations", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the SAFImportedDestinations to be created. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Create a SAFImportedDestinationsBean in this JMSModule <p> SAFImportedDestinationsBean defines a set of SAFQueues and or SAFTopics imported in the local cluster from the remote Cluster or server </p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SAFImportedDestinations");
      }

      mth = JMSBean.class.getMethod("destroySAFImportedDestinations", SAFImportedDestinationsBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("safImportedDestinations", "SAFImportedDestinationsBean defined in this module ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Destroy a SAFImportedDestinationsBean in this JMS Module <p> SAFImportedDestinationsBean defines a set of SAFQueues and or SAFTopics imported in the local cluster from the remote Cluster or server </p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SAFImportedDestinations");
      }

      mth = JMSBean.class.getMethod("createSAFRemoteContext", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the SAFRemoteContextBean to be created. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Create SAFRemoteContextBean in this module <p> SAFRemoteContextBean defines the parameters to be used by the JMS Imported Destinations (SAFQueue or SAFTopic) to connect ot the remote Cluster. </p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SAFRemoteContexts");
      }

      mth = JMSBean.class.getMethod("destroySAFRemoteContext", SAFRemoteContextBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("safRemoteContext", "SAFRemoteContextBean in this module ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Destroy SAFRemoteContextBean in this module <p> SAFRemoteContextBean defines the parameters to be used by the JMS Imported Destinations (SAFQueue or SAFTopic) to connect ot the remote Cluster. </p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SAFRemoteContexts");
      }

      mth = JMSBean.class.getMethod("createSAFErrorHandling", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the SAFErrorHandlingBean to be created. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Create SAFErrorHandlingBean in this module <p> SAFErrorHandlingBeaa defines what has to be done for messages that cannot be forwarded by SAFAgents </P> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SAFErrorHandlings");
      }

      mth = JMSBean.class.getMethod("destroySAFErrorHandling", SAFErrorHandlingBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("safErrorHandling", "SAFErrorhandlingBean defined in this module ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Destroy SAFErrorHandlingBean defined in this module <p> SAFErrorHandlingBean defines what has to be done for messages that cannot be forwarded by SAFAgents </P> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SAFErrorHandlings");
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = JMSBean.class.getMethod("lookupQuota", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the quota to locate ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Locates the quota of a given name <p> Quota beans control the allotment of system resources available to destinations.  For example the number of bytes a destination is allowed to store can be configured with a QuotaBean </p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "Quotas");
      }

      mth = JMSBean.class.getMethod("lookupTemplate", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the template to find ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Locates a template bean with the given name <p> Template beans contain default values for destination attributes.  If a destination defines a template and does not explicitly set the value of an attribute, then that attribute will take its value from the defined template. </p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "Templates");
      }

      mth = JMSBean.class.getMethod("lookupDestinationKey", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the destination key to locate ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Locates a destination key bean with the given name  <p> Destination Key beans control the sorting criteria of JMS destinations </p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "DestinationKeys");
      }

      mth = JMSBean.class.getMethod("lookupConnectionFactory", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the connection factory to locate ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Locates a connection factory bean with the given name  <p> Connection factories are used to create connections for JMS clients.  Connection factories can configure properties of the connections returned to the JMS client. </p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "ConnectionFactories");
      }

      mth = JMSBean.class.getMethod("lookupForeignServer", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the foreign server to locate ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Locates a foreign server bean with the given name  <p> Foreign servers are used to configure messaging providers that are not part of the current domain.  This is useful when integrating with another vendors JMS product. </p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "ForeignServers");
      }

      mth = JMSBean.class.getMethod("lookupQueue", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the queue to lookup ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Locates a queue bean with the given name  <p> Queues are used for asynchronous peer communications.  A message delivered to a queue will be distributed to one consumer.  Several aspects of a queues behavior can be configured with a queue bean. </p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "Queues");
      }

      mth = JMSBean.class.getMethod("lookupTopic", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the topic to lookup ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Locates a topic bean with the given name ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "Topics");
      }

      mth = JMSBean.class.getMethod("lookupDistributedQueue", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the distributed queue to locate ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "10.3.4.0, since Weighted Distributed Destination has been deprecated and replaced by Uniform Distributed Destination ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Locates a distributed queue bean with the given name  <p> A distributed queue is a queue that is located on many JMS servers.  Distributed queues can help with load balancing and distribution, and have many of the same properties as normal queues.  Many aspects of a distributed queues behavior can be configured with a distributed queue bean. </p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "DistributedQueues");
      }

      mth = JMSBean.class.getMethod("lookupDistributedTopic", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the distributed topic to locate ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "10.3.4.0, since Weighted Distributed Destination has been deprecated and replaced by Uniform Distributed Destination ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Locates a distributed topic bean with the given name  <p> A distributed topic is a topic that is located on many JMS servers.  Distributed topics can help with load balancing and distribution, and have many of the same properties as normal topics.  Many aspects of a distributed topics behavior can be configured with a distributed topic bean. </p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "DistributedTopics");
      }

      mth = JMSBean.class.getMethod("lookupUniformDistributedQueue", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the uniform distributed queue to locate ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Locates a uniform distributed queue bean with the given name  <p> A uniform distributed queue is a distributed queue whose members are configured as part of the configuration of the distributed queue.  These members are, therefore, uniform and are created based on the targeting of the uniform distributed queue. </p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "UniformDistributedQueues");
      }

      mth = JMSBean.class.getMethod("lookupUniformDistributedTopic", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the uniform distributed topic bean to locate ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Locates a uniform distributed topic bean with the given name  <p> A uniform distributed topic is a distributed topic whose members are configured as part of the configuration of the distributed topic.  These members are, therefore, uniform and are created based on the targeting of the uniform distributed topic. </p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "UniformDistributedTopics");
      }

      mth = JMSBean.class.getMethod("lookupSAFImportedDestinations", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the SAF imported destinations to locate ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Locates a SAF Imported Destinations bean with the given name <p> SAFImportedDestinationsBean defines a set of SAFQueues and or SAFTopics imported in the local cluster from the remote Cluster or server </p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "SAFImportedDestinations");
      }

      mth = JMSBean.class.getMethod("lookupSAFRemoteContext", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the SAF Remote Context to locate ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Locates a SAF Remote Context bean with the given name <p> SAFRemoteContextBean defines the parameters to be used by the JMS Imported Destinations (SAFQueue or SAFTopic) to connect ot the remote Cluster. </p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "SAFRemoteContexts");
      }

      mth = JMSBean.class.getMethod("lookupSAFErrorHandling", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the SAF Error Handling to locate ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Locates a SAF Error Handling bean with the given name <p> SAFErrorHandlingBean defines what has to be done for messages that cannot be forwarded by SAFAgents </P> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "SAFErrorHandlings");
      }

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
