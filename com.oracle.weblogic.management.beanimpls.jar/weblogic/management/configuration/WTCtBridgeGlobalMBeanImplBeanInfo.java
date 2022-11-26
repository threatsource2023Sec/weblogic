package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class WTCtBridgeGlobalMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WTCtBridgeGlobalMBean.class;

   public WTCtBridgeGlobalMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WTCtBridgeGlobalMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.WTCtBridgeGlobalMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "7.0.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>This interface provides access to the WTC tBridge Global configuration attributes. The methods defined herein are applicable for tBridge configuration at the WLS domain level.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.WTCtBridgeGlobalMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("AllowNonStandardTypes")) {
         getterName = "getAllowNonStandardTypes";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAllowNonStandardTypes";
         }

         currentResult = new PropertyDescriptor("AllowNonStandardTypes", WTCtBridgeGlobalMBean.class, getterName, setterName);
         descriptors.put("AllowNonStandardTypes", currentResult);
         currentResult.setValue("description", "<p>Specifies whether non-standard data types are allowed to pass through this Tuxedo queuing bridge.</p>  <p>A value of <code>NO</code> means that non standard types are rejected and placed onto a specified error location; a value of <code>YES</code> means that non-standard types are placed on the target location as BLOBs with a tag indicating the original type.</p>  <p><i>Note:</i> Standard types are: ASCII text (TextMessage, STRING), or BLOB (BytesMessage, CARRAY).</p> ");
         setPropertyDescriptorDefault(currentResult, "NO");
         currentResult.setValue("legalValues", new Object[]{"Yes", "No"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultReplyDeliveryMode")) {
         getterName = "getDefaultReplyDeliveryMode";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultReplyDeliveryMode";
         }

         currentResult = new PropertyDescriptor("DefaultReplyDeliveryMode", WTCtBridgeGlobalMBean.class, getterName, setterName);
         descriptors.put("DefaultReplyDeliveryMode", currentResult);
         currentResult.setValue("description", "<p>The reply delivery mode to associate with a message when placing messages onto the target location.</p>  <p style=\"font-weight: bold\">Usage Notes:</p>  <ul> <li>Use when messages are being redirected to Tuxedo/Q from JMS and the <code>JMS_BEA_TuxGtway_Tuxedo_ReplyDeliveryMode</code> property is not set for a message. </li>  <li>If the <code>defaultReplyDeliveryMode</code> and <code>JMS_BEA_TuxGtway_Tuxedo_ReplyDeliveryMode</code> are not set, the default semantics defined for Tuxedo are enforced by the Tuxedo/Q subsystem. </li> </ul> ");
         setPropertyDescriptorDefault(currentResult, "DEFAULT");
         currentResult.setValue("legalValues", new Object[]{"PERSIST", "NONPERSIST", "DEFAULT"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DeliveryModeOverride")) {
         getterName = "getDeliveryModeOverride";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDeliveryModeOverride";
         }

         currentResult = new PropertyDescriptor("DeliveryModeOverride", WTCtBridgeGlobalMBean.class, getterName, setterName);
         descriptors.put("DeliveryModeOverride", currentResult);
         currentResult.setValue("description", "<p>The delivery mode to use when placing messages onto the target location.</p>  <p>If this value is not specified, the message is placed on the target location with the same delivery mode specified from the source location.</p>  <p><i>Note:</i> This value overrides any delivery mode associated with a message.</p> ");
         setPropertyDescriptorDefault(currentResult, "NONPERSIST");
         currentResult.setValue("legalValues", new Object[]{"PERSIST", "NONPERSIST"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JmsFactory")) {
         getterName = "getJmsFactory";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJmsFactory";
         }

         currentResult = new PropertyDescriptor("JmsFactory", WTCtBridgeGlobalMBean.class, getterName, setterName);
         descriptors.put("JmsFactory", currentResult);
         currentResult.setValue("description", "<p>The name of the JMS connection factory.</p>  <p><b>Example:</b> <code>weblogic.jms.ConnectionFactory</code></p> ");
         setPropertyDescriptorDefault(currentResult, "weblogic.jms.XAConnectionFactory");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JmsToTuxPriorityMap")) {
         getterName = "getJmsToTuxPriorityMap";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJmsToTuxPriorityMap";
         }

         currentResult = new PropertyDescriptor("JmsToTuxPriorityMap", WTCtBridgeGlobalMBean.class, getterName, setterName);
         descriptors.put("JmsToTuxPriorityMap", currentResult);
         currentResult.setValue("description", "<p>The mapping of priorities from JMS to Tuxedo. The default JMS To Tux Priority Map is: <code>0:1 | 1:12 | 2:23 | 3:34 | 4:45 |5:56 | 6:67 | 7:78 | 8:89 | 9:100</code></p>  <p><b>Examples:</b> <code>0:1 | 1:12 | 2:23 | 3:34 | 4:45 | 5:56 | 6:67 | 7:78 | 8:89 | 9:100 or 0:1-10|1:11-20|2:21-30|3:31-40|4:41-50|5:51-60|6:61-70|7:71-80|8:81-90|9:91-100</code></p>  <p><b>Note:</b> The are 10 possible JMS priorities(0=&gt;9) which can be paired to 100 possible Tuxedo priorities(1=&gt;100). A mapping consists of a \"|\" separated list of value-to-range pairs (jmsvalue:tuxrange) where pairs are separated by \":\" and ranges are separated by \"-\".</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JndiFactory")) {
         getterName = "getJndiFactory";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJndiFactory";
         }

         currentResult = new PropertyDescriptor("JndiFactory", WTCtBridgeGlobalMBean.class, getterName, setterName);
         descriptors.put("JndiFactory", currentResult);
         currentResult.setValue("description", "<p>The name of the JNDI lookup factory.</p>  <p><b>Example:</b> <code>weblogic.jndi.WLInitialContextFactory</code></p> ");
         setPropertyDescriptorDefault(currentResult, "weblogic.jndi.WLInitialContextFactory");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Retries")) {
         getterName = "getRetries";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRetries";
         }

         currentResult = new PropertyDescriptor("Retries", WTCtBridgeGlobalMBean.class, getterName, setterName);
         descriptors.put("Retries", currentResult);
         currentResult.setValue("description", "<p>The number of attempts to redirect a message before this Tuxedo queuing bridge places the message in the specified error location and logs an error.</p>  <p><b>Range of Values:</b> Between 0 and a positive 32-bit integer.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RetryDelay")) {
         getterName = "getRetryDelay";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRetryDelay";
         }

         currentResult = new PropertyDescriptor("RetryDelay", WTCtBridgeGlobalMBean.class, getterName, setterName);
         descriptors.put("RetryDelay", currentResult);
         currentResult.setValue("description", "<p>The minimum number of milliseconds this Tuxedo queuing bridge waits before redirecting a message after a failure.</p>  <p><i>Note:</i> During this waiting period, no other messages are redirected from the thread. Other threads may continue to redirect messages.</p>  <p><b>Range of Values:</b> Between <code>0</code> and a positive 32-bit integer.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(10));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Timeout")) {
         getterName = "getTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTimeout";
         }

         currentResult = new PropertyDescriptor("Timeout", WTCtBridgeGlobalMBean.class, getterName, setterName);
         descriptors.put("Timeout", currentResult);
         currentResult.setValue("description", "<p>The number of timeout seconds for an entire redirection when this Tuxedo queuing bridge places a message on the target location. A value of <code>0</code> specifies an infinite wait.</p>  <p><b>Range of Values:</b> Between <code>0</code> and a positive 32-bit integer.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(60));
         currentResult.setValue("secureValue", new Integer(60));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Transactional")) {
         getterName = "getTransactional";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTransactional";
         }

         currentResult = new PropertyDescriptor("Transactional", WTCtBridgeGlobalMBean.class, getterName, setterName);
         descriptors.put("Transactional", currentResult);
         currentResult.setValue("description", "<p>Specifies whether this Tuxedo queuing bridge should use transactions when retrieving messages from a source location and when placing messages on a target location.</p>  <p>A value of <code>YES</code> means that transactions are used for both operations; a value of <code>NO</code> means that transactions are not used for either operation.</p>  <p><i>Note:</i> Transactional is not supported in this release.</p> ");
         setPropertyDescriptorDefault(currentResult, "NO");
         currentResult.setValue("legalValues", new Object[]{"Yes", "No"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TuxErrorQueue")) {
         getterName = "getTuxErrorQueue";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTuxErrorQueue";
         }

         currentResult = new PropertyDescriptor("TuxErrorQueue", WTCtBridgeGlobalMBean.class, getterName, setterName);
         descriptors.put("TuxErrorQueue", currentResult);
         currentResult.setValue("description", "<p>The name of the Tuxedo queue used to store a message that cannot be redirected to a Tuxedo/Q source queue.</p>  <p>If not specified, all messages not redirected are lost. If the message cannot be placed into the <code>TuxErrorQueue</code>, an error is logged and the message is lost.</p>  <p><i>Note:</i> This queue is in the same queue space as the source queue.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TuxFactory")) {
         getterName = "getTuxFactory";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTuxFactory";
         }

         currentResult = new PropertyDescriptor("TuxFactory", WTCtBridgeGlobalMBean.class, getterName, setterName);
         descriptors.put("TuxFactory", currentResult);
         currentResult.setValue("description", "<p>The name of the Tuxedo connection factory.</p>  <p><b>Example:</b> <code>tuxedo.services.TuxedoConnection</code></p> ");
         setPropertyDescriptorDefault(currentResult, "tuxedo.services.TuxedoConnection");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TuxToJmsPriorityMap")) {
         getterName = "getTuxToJmsPriorityMap";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTuxToJmsPriorityMap";
         }

         currentResult = new PropertyDescriptor("TuxToJmsPriorityMap", WTCtBridgeGlobalMBean.class, getterName, setterName);
         descriptors.put("TuxToJmsPriorityMap", currentResult);
         currentResult.setValue("description", "<p>The mapping of priorities to map from Tuxedo to JMS. The default JMS To Tux Priority Map is: <code>1-10:0 | 11-20:1 | 21-30:2 | 31-40:3| 41-50:4| 51-60:5 | 61-70:6 | 71-80:7 | 81-90:8 | 91-100:9</code></p>  <p><b>Examples:</b> <code>1:0 | 12:1 | 23:2 | 34:3 | 45:4 | 56:5 | 67:6 | 78:7 | 89:8 | 100:9 or 20:0-1 | 40:2-3 | 60:4-5 | 80:6-7 | 100:8-9</code></p>  <p><i>Note:</i> The are 100 possible Tuxedo priorities(1=&gt;100) which can be paired to 10 possible JMS priorities(0=&gt;9). A mapping consists of a \"|\" separated list of value-to-range pairs (tuxvalue:jmsrange) where pairs are separated by \":\" and ranges are separated by \"-\".</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UserId")) {
         getterName = "getUserId";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUserId";
         }

         currentResult = new PropertyDescriptor("UserId", WTCtBridgeGlobalMBean.class, getterName, setterName);
         descriptors.put("UserId", currentResult);
         currentResult.setValue("description", "<p>The user identity for all messages handled by this Tuxedo queuing bridge for ACL checks when security is configured.</p>  <p>All messages assume this identity until the security/authentication contexts are passed between the subsystems. Until the security contexts are passed, there is no secure method to identify who generated a message received from the source location.</p>  <p><i>Note:</i> The <code>user</code> argument may be specified as either a user name or a user identification number (uid).</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WlsErrorDestination")) {
         getterName = "getWlsErrorDestination";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setWlsErrorDestination";
         }

         currentResult = new PropertyDescriptor("WlsErrorDestination", WTCtBridgeGlobalMBean.class, getterName, setterName);
         descriptors.put("WlsErrorDestination", currentResult);
         currentResult.setValue("description", "<p>The name of the location used to store WebLogic Server JMS messages when a message cannot be redirected.</p>  <p>If not specified, all messages not redirected are lost. If the message cannot be placed into <code>WlsErrorDestination</code> for any reason, an error is logged and the message is lost.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
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
