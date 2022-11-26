package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class TemplateBeanImplBeanInfo extends NamedEntityBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = TemplateBean.class;

   public TemplateBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public TemplateBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.TemplateBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("Template beans contain default values for destination attributes. If a destination defines a template and does not explicitly set the value of an attribute, then that attribute will take its value from the defined template. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.TemplateBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("AttachSender")) {
         getterName = "getAttachSender";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAttachSender";
         }

         currentResult = new PropertyDescriptor("AttachSender", TemplateBean.class, getterName, setterName);
         descriptors.put("AttachSender", currentResult);
         currentResult.setValue("description", "<p>Specifies whether messages landing on destinations that use this template should attach the credential of the sending user.</p>  <ul> <li><b>Supports</b> The JMSXUserID property is set with the security principal of the sending user if requested. The sender can request its identity to be attached to its messages by using a ConnectionFactory with <code>AttachJMSXUserID=\"true\"</code>.</li>  <li><b>Never</b> The JMSXUserID property is never be set with the security principal of the sending user.</li>  <li><b>Always</b> The JMSXUserID property is always set with the security principal of the sending user.</li> </ul>  <p>This property is dynamically configurable. A dynamic change will only affect messages received after the update has been made.</p> ");
         setPropertyDescriptorDefault(currentResult, "supports");
         currentResult.setValue("legalValues", new Object[]{"supports", "never", "always"});
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DeliveryFailureParams")) {
         getterName = "getDeliveryFailureParams";
         setterName = null;
         currentResult = new PropertyDescriptor("DeliveryFailureParams", TemplateBean.class, getterName, setterName);
         descriptors.put("DeliveryFailureParams", currentResult);
         currentResult.setValue("description", "<p>The parameters that control what should happen to messages when failures occur.</p>  <p>These parameters enable the Administrator to control error destinations, logging, and other actions that might be taken when a message cannot be delivered or when other failures are detected.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DeliveryParamsOverrides")) {
         getterName = "getDeliveryParamsOverrides";
         setterName = null;
         currentResult = new PropertyDescriptor("DeliveryParamsOverrides", TemplateBean.class, getterName, setterName);
         descriptors.put("DeliveryParamsOverrides", currentResult);
         currentResult.setValue("description", "<p>Delivery parameter settings that override delivery parameters set by the JMS client.</p>  <p>These overrides, if set, will cause those specific parameters set in the JMS client to be ignored and replaced by the value set here.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DestinationKeys")) {
         getterName = "getDestinationKeys";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDestinationKeys";
         }

         currentResult = new PropertyDescriptor("DestinationKeys", TemplateBean.class, getterName, setterName);
         descriptors.put("DestinationKeys", currentResult);
         currentResult.setValue("description", "<p>The list of potential destination keys for sorting messages that arrive on destinations that use this JMS template.</p>  <p>The keys are ordered from most significant to least significant. If more than one key is specified, a key based on the JMSMessageID can only be the last key in the list.</p>  <p><b>Note:</b> If JMSMessageID is not defined in the key, it is implicitly assumed to be the last key and is set as \"Ascending\" (FIFO) for the sort order.</p> ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("GroupParams")) {
         getterName = "getGroupParams";
         setterName = null;
         currentResult = new PropertyDescriptor("GroupParams", TemplateBean.class, getterName, setterName);
         descriptors.put("GroupParams", currentResult);
         currentResult.setValue("description", "<p>The group parameters for this template. When using templates, group parameters enable you to customize parameters based on the group to which the destination belongs.</p>  <p>Templates are a convenient way to associate common parameters amongst a large number of destinations. However, some attributes of particular destinations may need to be configured based on where the destination is targeted. For example, the error destination of a destination and the destination itself must be targeted to the same JMS server.</p>  <p>Group parameters allow the template user to customize parameters based on the group the destination is a member of. The value that will be used for a destination will come from the group parameter if the subdeployment attribute has the same name as the subdeployment the destination is a member of.</p>  <p>For example, imagine two queues Q1 and Q2 in subdeployments G1 and G2 respectively. Both Q1 and Q2 specify template X1 as their template. X1 has group parameters with sub-deployment attributes G1 and G2, where the error destination value for G1 is E1 and the quota value for G2 is E2. Neither Q1 nor Q2 specify their error-destination variable explicitly, so they will get their error destinations from the template. Since Q1 is a member of G1, and the template value of G1 is E1, Q1 will get the error destination E1. Likewise, Q2's error destination will eventually resolve to error destination E2. If a third destination Q3 is added in with sub-deployment G3 and the template does not have a group parameter for G3 then Q3 will get the default value for its error destination.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyGroupParams");
         currentResult.setValue("creator", "createGroupParams");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IncompleteWorkExpirationTime")) {
         getterName = "getIncompleteWorkExpirationTime";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIncompleteWorkExpirationTime";
         }

         currentResult = new PropertyDescriptor("IncompleteWorkExpirationTime", TemplateBean.class, getterName, setterName);
         descriptors.put("IncompleteWorkExpirationTime", currentResult);
         currentResult.setValue("description", "<p>Specifies the maximum amount of time, in milliseconds, before undelivered messages in an incomplete UOW are expired. Such messages will then follow the template's expiration policy defined for undeliverable messages.</p>  <p><b>Note:</b> A template's error destination for UOW messages cannot be configured to use the <b>Single Message Delivery</b> value.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaximumMessageSize")) {
         getterName = "getMaximumMessageSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaximumMessageSize";
         }

         currentResult = new PropertyDescriptor("MaximumMessageSize", TemplateBean.class, getterName, setterName);
         descriptors.put("MaximumMessageSize", currentResult);
         currentResult.setValue("description", "<p>The maximum size of a message that will be accepted from producers on this destination. The message size includes the message body, any user-defined properties, and the user-defined JMS header fields: <code>JMSCorrelationID</code> and <code>JMSType</code>.</p>  <p>Producers sending messages that exceed the configured maximum message size for the destination receive a <code>ResourceAllocationException</code> .</p>  <p>The maximum message size is only enforced for the initial production of a message. Messages that are redirected to an error destination or forwarded to a member of a distributed destination are not checked for size. For instance, if a destination and its corresponding error destination are configured with a maximum message size of 128K bytes and 64K bytes, respectively, a message of 96K bytes could be redirected to the error destination (even though it exceeds the 64K byte maximum), but a producer could not directly send the 96K byte message to the error destination.</p>  <p>Maximum Message Size is dynamically configurable, but only incoming messages are affected; stored messages are not affected.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessageLoggingParams")) {
         getterName = "getMessageLoggingParams";
         setterName = null;
         currentResult = new PropertyDescriptor("MessageLoggingParams", TemplateBean.class, getterName, setterName);
         descriptors.put("MessageLoggingParams", currentResult);
         currentResult.setValue("description", "<p>Message logging parameters control message logging actions when a message life cycle change is detected.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessagingPerformancePreference")) {
         getterName = "getMessagingPerformancePreference";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMessagingPerformancePreference";
         }

         currentResult = new PropertyDescriptor("MessagingPerformancePreference", TemplateBean.class, getterName, setterName);
         descriptors.put("MessagingPerformancePreference", currentResult);
         currentResult.setValue("description", "<p>Controls how long destinations that use this template are willing to wait to create full batches of available messages (if at all) for delivery to consumers. At the minimum value, batching is disabled; at the default value, less-than-full batches will not wait and are delivered immediately with currently available messages; tuning higher than the default value controls the maximum wait time for additional messages before less-than-full batches are delivered to consumers.</p>  <p>The maximum message count of a full batch is controlled by the JMS connection factory's MessagesMaximum setting.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(25));
         currentResult.setValue("legalMax", new Integer(100));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Multicast")) {
         getterName = "getMulticast";
         setterName = null;
         currentResult = new PropertyDescriptor("Multicast", TemplateBean.class, getterName, setterName);
         descriptors.put("Multicast", currentResult);
         currentResult.setValue("description", "<p>The parameters for receiving messages using multicast rather than a connection-oriented protocol such as TCP.</p>  <p>Topics with certain quality of service tolerance can receive a significant performance boost by using multicast to receive messages rather than using a connection oriented protocol like TCP.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Quota")) {
         getterName = "getQuota";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setQuota";
         }

         currentResult = new PropertyDescriptor("Quota", TemplateBean.class, getterName, setterName);
         descriptors.put("Quota", currentResult);
         currentResult.setValue("description", "<p>A Quota controls the allotment of system resources available to destinations. For example the number of bytes a destination is allowed to store can be configured with a QuotaBean.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SafExportPolicy")) {
         getterName = "getSafExportPolicy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSafExportPolicy";
         }

         currentResult = new PropertyDescriptor("SafExportPolicy", TemplateBean.class, getterName, setterName);
         descriptors.put("SafExportPolicy", currentResult);
         currentResult.setValue("description", "<p>Specifies whether a user can send messages to destinations that use this template using Store-and-Forward.</p>  <ul> <li><b>All</b> All users can send messages to destinations using Store-and-Forward.</li>  <li><b>None</b> Remote users cannot send messages to destinations using Store-and-Forward. </li> </ul>  <p>This property is dynamically configurable. A dynamic change will only affect messages sent after the update has been made.</p> ");
         setPropertyDescriptorDefault(currentResult, "All");
         currentResult.setValue("legalValues", new Object[]{"All", "None"});
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Thresholds")) {
         getterName = "getThresholds";
         setterName = null;
         currentResult = new PropertyDescriptor("Thresholds", TemplateBean.class, getterName, setterName);
         descriptors.put("Thresholds", currentResult);
         currentResult.setValue("description", "<p>A threshold is a point that must be exceeded in order to produce a given effect. These action points may cause logging, flow control, or other actions, as defined by the specific points whose values have been exceeded.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TopicSubscriptionParams")) {
         getterName = "getTopicSubscriptionParams";
         setterName = null;
         currentResult = new PropertyDescriptor("TopicSubscriptionParams", TemplateBean.class, getterName, setterName);
         descriptors.put("TopicSubscriptionParams", currentResult);
         currentResult.setValue("description", "<p>Gets the subscription parameters for this topic.</p>  <p><code>TopicSubscriptionParamsBean</code> specifies topic subscription parameters.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UnitOfWorkHandlingPolicy")) {
         getterName = "getUnitOfWorkHandlingPolicy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUnitOfWorkHandlingPolicy";
         }

         currentResult = new PropertyDescriptor("UnitOfWorkHandlingPolicy", TemplateBean.class, getterName, setterName);
         descriptors.put("UnitOfWorkHandlingPolicy", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the Unit-of-Work (UOW) feature is enabled for destinations that use this template. A UOW is a set of messages that are processed as a single unit.</p> <ul> <li> <b>Pass-Through</b> By default, destinations that use this template do not treat messages as part of a UOW.</li> <li> <b>Single Message Delivery</b> This value should only be selected if UOW consumers are receiving messages on destinations that use this template. When selected, UOW messages are formed into a list and are consumed as an <code>ObjectMessage</code> containing the list.</li> </ul> ");
         setPropertyDescriptorDefault(currentResult, "PassThrough");
         currentResult.setValue("legalValues", new Object[]{"PassThrough", "SingleMessageDelivery"});
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConsumptionPausedAtStartup")) {
         getterName = "isConsumptionPausedAtStartup";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConsumptionPausedAtStartup";
         }

         currentResult = new PropertyDescriptor("ConsumptionPausedAtStartup", TemplateBean.class, getterName, setterName);
         descriptors.put("ConsumptionPausedAtStartup", currentResult);
         currentResult.setValue("description", "<p>Specifies whether message consumption is paused on destinations that use this template at server startup.</p>  <ul> <li><b>false</b> Paused message consumption at server startup is disabled on this template.</li>  <li><b>true</b> Paused message consumption at server startup is enabled on this template.</li> </ul> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultUnitOfOrder")) {
         getterName = "isDefaultUnitOfOrder";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultUnitOfOrder";
         }

         currentResult = new PropertyDescriptor("DefaultUnitOfOrder", TemplateBean.class, getterName, setterName);
         descriptors.put("DefaultUnitOfOrder", currentResult);
         currentResult.setValue("description", "<p>Specifies whether WebLogic Server creates a system-generated unit-of-order name based on the domain, JMS server, and destination name. Any message arriving at this destination that does not already belong to a unit-of-order is assigned this default name.</p>  <p>This field is recommended for advanced use. Generally, it is  recommended to set a message unit-of-order using application calls or connection factory configuration. When relying on a destination default unit order to enforce ordering with adistributed destination, the application must be aware that unit-of-order routing doesn't apply. Instead the application must specifically ensure that any particular set of messages that must be processed in order are all sent to the same specific member destination even in the event of process failures or service migration. </p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InsertionPausedAtStartup")) {
         getterName = "isInsertionPausedAtStartup";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setInsertionPausedAtStartup";
         }

         currentResult = new PropertyDescriptor("InsertionPausedAtStartup", TemplateBean.class, getterName, setterName);
         descriptors.put("InsertionPausedAtStartup", currentResult);
         currentResult.setValue("description", "<p>Specifies whether new message insertion is paused on destinations that use this template at server startup.</p>  <ul> <li><b>false</b> Paused message insertion at server startup is disabled on this template.</li>  <li><b>true</b> Paused message insertion at server startup is enabled on this template.</li> </ul> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ProductionPausedAtStartup")) {
         getterName = "isProductionPausedAtStartup";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setProductionPausedAtStartup";
         }

         currentResult = new PropertyDescriptor("ProductionPausedAtStartup", TemplateBean.class, getterName, setterName);
         descriptors.put("ProductionPausedAtStartup", currentResult);
         currentResult.setValue("description", "<p>Specifies whether new message production on destinations that use this template is paused on at server startup.</p>  <ul> <li><b>false</b> Paused message production at server startup is disabled on this template.</li>  <li><b>true</b> Paused message production at server startup is enabled on this template.</li> </ul> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = TemplateBean.class.getMethod("createGroupParams", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("subDeploymentName", "The name of the sub-deployment for which these parameters apply ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Creates a group parameter and adds it to this template.</p>  <p>Templates are a convenient way to associate common parameters amongst a large number of destinations. However, some attributes of particular destinations may need to be configured based on where the destination is targeted. For example, the error destination of a destination and the destination itself must be targeted to the same JMS server.</p>  <p>Group parameters allow the template user to customize parameters based on the group the destination is a member of. The value that will be used for a destination will come from the group parameter if the sub-deployment attribute has the same name as the sub-deployment the destination is a member of.</p> ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getGroupParams")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "GroupParams");
      }

      mth = TemplateBean.class.getMethod("destroyGroupParams", GroupParamsBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("quota", "The quota to be removed from this template ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Removes a group parameter from this template.</p>  <p>Templates are a convenient way to associate common parameters amongst a large number of destinations. However, some attributes of particular destinations may need to be configured based on where the destination is targeted. For example, the quota of a destination and the destination itself must be targeted to the same JMS server.</p>  <p>Group parameters allow the template user to customize parameters based on the group the destination is a member of. The value that will be used for a destination will come from the group parameter if the group parameter has the same name as the name of the group the destination is a member of. If no group parameter matches the group of the destination then that destination will get the default value for the attribute.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "GroupParams");
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = TemplateBean.class.getMethod("addDestinationKey", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("destinationKey", "The string to be added as a key.  Will be added to the end    of the list of keys ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Adds a destination key to the end of the list of keys used for sorting destinations.</p> ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "DestinationKeys");
      }

      mth = TemplateBean.class.getMethod("removeDestinationKey", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("destinationKey", "The string to be removed as a key used for sorting ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Removes a destination key from the list of keys used for sorting destinations.</p> ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "DestinationKeys");
      }

   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = TemplateBean.class.getMethod("lookupGroupParams", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("subDeploymentName", "The name of the sub-deployment key for which to find the associated GroupParamsBean ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Finds the group params bean associated with the given sub-deployment-name ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "GroupParams");
      }

   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = TemplateBean.class.getMethod("findErrorDestination", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("subDeploymentName", "The subDeployment for which to find the quota ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Finds the name of the error destination to use when a destination comes from a specific group</p> ");
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
