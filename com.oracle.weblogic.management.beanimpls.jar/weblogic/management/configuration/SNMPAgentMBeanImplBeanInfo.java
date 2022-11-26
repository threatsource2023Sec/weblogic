package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class SNMPAgentMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = SNMPAgentMBean.class;

   public SNMPAgentMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public SNMPAgentMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.SNMPAgentMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("dynamic", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>This singleton MBean represents an SNMP agent that is scoped to a WebLogic Server domain.</p> <p>This MBean is provided to support domains that were created with WebLogic Server release 9.2 and earlier. For new domains, create an instance of {@link SNMPAgentDeploymentMBean} and target it to the domain's Administration Server.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.SNMPAgentMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (BeanInfoHelper.isVersionCompliant("10.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("AuthenticationProtocol")) {
         getterName = "getAuthenticationProtocol";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAuthenticationProtocol";
         }

         currentResult = new PropertyDescriptor("AuthenticationProtocol", SNMPAgentMBean.class, getterName, setterName);
         descriptors.put("AuthenticationProtocol", currentResult);
         currentResult.setValue("description", "<p>The protocol that this SNMP agent uses to ensure that only authorized users can request or receive information about your WebLogic Server domain. Applicable only with SNMPv3.</p> <p>The protocol also ensures message integrity and prevents masquerading and reordered, delayed, or replayed messages.</p> <p>To use this protocol when receiving requests from SNMP managers, you must configure credential mapping in the WebLogic Server security realm. To use this protocol when sending responses or notifications, you must configure the security level of your trap destinations.</p> <p>If you do not choose an authentication protocol, then the SNMP agent does not authenticate incoming SNMPv3 requests; anyone can use SNMPv3 to retrieve information about your WebLogic Server domain.</p> ");
         setPropertyDescriptorDefault(currentResult, "MD5");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("legalValues", new Object[]{"noAuth", "MD5", "SHA"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.0.0.0");
      }

      if (!descriptors.containsKey("CommunityPrefix")) {
         getterName = "getCommunityPrefix";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCommunityPrefix";
         }

         currentResult = new PropertyDescriptor("CommunityPrefix", SNMPAgentMBean.class, getterName, setterName);
         descriptors.put("CommunityPrefix", currentResult);
         currentResult.setValue("description", "<p>The password (community name) that you want this SNMP agent to use to secure SNMPv1 or v2 communication with SNMP managers. Requires you to enable community based access for this agent.</p>  <p>SNMPv3 does not use community names. Instead, it encrypts user names and passwords in its PDUs. </p>  <p>When you use SNMPv1 or v2, there are two community names that are needed when the WebLogic SNMP agent and SNMP managers interact:</p>  <ul> <li> <p>The name that you specify in this community prefix. All SNMP managers must send this name when connecting to this SNMP agent.</p> </li>  <li> <p>The community name that the SNMP manager defines. The SNMP agent must send this name when connecting to the manager. (You supply this community name when you configure a trap destination.)</p> </li> </ul>  <p>In addition to using the community prefix as a password, an SNMP agent on an Administration Server uses the prefix to qualify requests from SNMP managers. Because the Administration Server can access data for all WebLogic Server instances in a domain, a request that specifies only an attribute name is potentially ambiguous. For example, the attribute <code>serverUptime</code> exists for each WebLogic Server instance in a domain. To clarify requests that you send to SNMP agents on Administration Servers, use the community prefix as follows:</p>  <ul> <li> <p>To request the value of an attribute on a specific Managed Server, when you send a request from an SNMP manager, append the name of the server instance to the community prefix: <code><i>community_prefix@server_name</i></code>.</p> </li>  <li> <p>To request the value of an attribute for all server instances in a domain, send a community name with the following form: <code><i>community_prefix</i></code></p> </li> </ul>  <p>To secure access to the values of the WebLogic attributes when using the SNMPv1 or v2 protocols, it is recommended that you set community prefix to a value other than <code>public</code> or <code>private</code>.</p>  <p>You cannot specify a null (empty) value for the community prefix when SNMP agent is enabled. If you do not want this agent to receive SNMPv1 or v2 requests, disable community based access.</p> ");
         currentResult.setValue("secureValue", "A unique value, should not be public or private");
         currentResult.setValue("secureValueDocOnly", Boolean.TRUE);
         currentResult.setValue("deprecated", "12.2.1.4 There is no replacement for this attribute as SNMPv1 and v2 support will be removed. ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DebugLevel")) {
         getterName = "getDebugLevel";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDebugLevel";
         }

         currentResult = new PropertyDescriptor("DebugLevel", SNMPAgentMBean.class, getterName, setterName);
         descriptors.put("DebugLevel", currentResult);
         currentResult.setValue("description", "<p>The minimum severity of debug messages that this SNMP agent generates.</p>  <p>The SNMP agent writes all debug messages to standard out; they are not written to the WebLogic Server log files. Debug messages provide a detailed description of the SNMP agent's actions. For example, the agent outputs a noncritical message each time it generates a notification.</p>  <p>Valid values are:</p>  <ul> <li><code>0</code>  <p>No debug messages.</p> </li>  <li><code>1</code>  <p>Fatal messages only.</p> </li>  <li><code>2</code>  <p>Critical and fatal messages.</p> </li>  <li><code>3</code>  <p>Non-critical, critical, and, fatal messages.</p> </li> </ul> ");
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("secureValue", new Integer(0));
         currentResult.setValue("legalValues", new Object[]{new Integer(0), new Integer(1), new Integer(2), new Integer(3)});
         currentResult.setValue("deprecated", "10.0.0.0 Use the ServerDebugMBean.DebugSNMPToolkit attribute to configure the SNMP Toolkit debug ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("10.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("InformRetryInterval")) {
         getterName = "getInformRetryInterval";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setInformRetryInterval";
         }

         currentResult = new PropertyDescriptor("InformRetryInterval", SNMPAgentMBean.class, getterName, setterName);
         descriptors.put("InformRetryInterval", currentResult);
         currentResult.setValue("description", "<p>The number of milliseconds that this SNMP agent will wait for a response to an INFORM notification.</p> <p>If the agent does not receive a response within the specified interval, it will resend the notification.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(10000));
         currentResult.setValue("legalMax", new Integer(30000));
         currentResult.setValue("legalMin", new Integer(3000));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("ListenAddress")) {
         getterName = "getListenAddress";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setListenAddress";
         }

         currentResult = new PropertyDescriptor("ListenAddress", SNMPAgentMBean.class, getterName, setterName);
         descriptors.put("ListenAddress", currentResult);
         currentResult.setValue("description", "<p>The listen address on which you want this SNMP agent to listen for incoming requests from SNMP managers that use the UDP protocol.</p>  <p>If not specified the Server's ListenAddress is used as the ListenAddress for the SNMPAgent.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.1.0");
      }

      String[] seeObjectArray;
      if (BeanInfoHelper.isVersionCompliant("10.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("LocalizedKeyCacheInvalidationInterval")) {
         getterName = "getLocalizedKeyCacheInvalidationInterval";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLocalizedKeyCacheInvalidationInterval";
         }

         currentResult = new PropertyDescriptor("LocalizedKeyCacheInvalidationInterval", SNMPAgentMBean.class, getterName, setterName);
         descriptors.put("LocalizedKeyCacheInvalidationInterval", currentResult);
         currentResult.setValue("description", "<p>The number of milliseconds after which WebLogic Server invalidates its cache of SNMP security keys. Setting a high value creates a risk that users whose credentials have been removed can still access SNMP data.</p> <p>An SNMP security key is an encrypted version of an SNMP agent's engine ID and an authentication password or privacy password. WebLogic Server generates one security key for each entry that you create in the SNMP credential map. When a WebLogic Server SNMP agent receives an SNMPv3 request, it compares the key that is in the request with its WebLogic Server keys. If it finds a match, it processes the request. The SNMP agent also encodes these keys in its responses and notifications. (You configure which keys are encoded when you create a trap destination.)</p> <p>Instead of regenerating the keys for each SNMPv3 communication, WebLogic Server caches the keys. To make sure that the cache contains the latest set of SNMP credentials, WebLogic Server periodically invalidates the cache. After the cache is invalidated, the next time an SNMP agent requests credentials, WebLogic Server regenerates the cache.</p> <p>Note that making a change to the credential map does not automatically update the cache. Instead, the cache is updated only after it has been invalidated.</p> <p>For example, if you update a privacy password in an existing entry in the SNMP credential map, the SNMP agent is not aware of the new password until the key cache is invalidated and regenerated. An SNMP user with the old security password can still access WebLogic Server data until the cache is invalidated.</p> <p>You can invalidate a key immediately instead of waiting for this invalidation interval to expire. </p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.runtime.SNMPAgentRuntimeMBean#invalidateLocalizedKeyCache(String)"), BeanInfoHelper.encodeEntities("SNMPTrapDestinationMBean")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Long(3600000L));
         currentResult.setValue("legalMax", new Long(86400000L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("MasterAgentXPort")) {
         getterName = "getMasterAgentXPort";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMasterAgentXPort";
         }

         currentResult = new PropertyDescriptor("MasterAgentXPort", SNMPAgentMBean.class, getterName, setterName);
         descriptors.put("MasterAgentXPort", currentResult);
         currentResult.setValue("description", "<p>The port that this SNMP agent uses to communicate with its subagents.</p> <p>The agent uses subagents to provide access to custom MBeans (MBeans that you create and register) and to other software components. WebLogic Server SNMP agents do not enable users to register their own subagents.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(705));
         currentResult.setValue("legalMax", new Integer(65535));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("MaxInformRetryCount")) {
         getterName = "getMaxInformRetryCount";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxInformRetryCount";
         }

         currentResult = new PropertyDescriptor("MaxInformRetryCount", SNMPAgentMBean.class, getterName, setterName);
         descriptors.put("MaxInformRetryCount", currentResult);
         currentResult.setValue("description", "<p>The maximum number of times that this SNMP agent will resend INFORM notifications for which it has not received a response.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(1));
         currentResult.setValue("legalMax", new Integer(3));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.0.0.0");
      }

      if (!descriptors.containsKey("MibDataRefreshInterval")) {
         getterName = "getMibDataRefreshInterval";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMibDataRefreshInterval";
         }

         currentResult = new PropertyDescriptor("MibDataRefreshInterval", SNMPAgentMBean.class, getterName, setterName);
         descriptors.put("MibDataRefreshInterval", currentResult);
         currentResult.setValue("description", "<p>The minimum number of seconds that this SNMP agent caches OIDs before checking if new ones have been added to the Management Information Base (MIB).</p>  <p>A MIB is a database of all objects that can be managed through SNMP. When you create a new WebLogic Server resource, the SNMP agent assigns a unique OID to the resource and adds it to the MIB. For example, when you create a new server, the SNMP agent adds an OID to the MIB.</p>  <p>This attribute is not used by the SNMP Agent anymore. The SNMP Agent retrieves internal notifications about MBean registrations in the WLS MBeanServers.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(120));
         currentResult.setValue("legalMax", new Integer(65535));
         currentResult.setValue("legalMin", new Integer(30));
         currentResult.setValue("deprecated", "10.0.0.0 There is no replacement for this attribute. ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", SNMPAgentMBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>The user-specified name of this MBean instance.</p>  <p>This name is included as one of the key properties in the MBean's <code>javax.management.ObjectName</code>:</p>  <p><code>Name=<i>user-specified-name</i></code></p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("key", Boolean.TRUE);
      }

      if (BeanInfoHelper.isVersionCompliant("10.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("PrivacyProtocol")) {
         getterName = "getPrivacyProtocol";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPrivacyProtocol";
         }

         currentResult = new PropertyDescriptor("PrivacyProtocol", SNMPAgentMBean.class, getterName, setterName);
         descriptors.put("PrivacyProtocol", currentResult);
         currentResult.setValue("description", "<p>The protocol that this SNMP agent uses to encrypt and unencrypt messages. Applicable only with SNMPv3. Requires you to also use an authentication protocol.</p> <p>To use this protocol when sending responses or notifications, you must also configure the security level of your trap destinations.</p> <p>If you do not choose a privacy protocol, then communication between this agent and managers can be viewed (but not altered) by unauthorized users.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("SNMPTrapDestinationMBean#getSecurityLevel()")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, "AES_128");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("legalValues", new Object[]{"noPriv", "DES", "AES_128"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.0.0.0");
      }

      if (!descriptors.containsKey("SNMPAttributeChanges")) {
         getterName = "getSNMPAttributeChanges";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSNMPAttributeChanges";
         }

         currentResult = new PropertyDescriptor("SNMPAttributeChanges", SNMPAgentMBean.class, getterName, setterName);
         descriptors.put("SNMPAttributeChanges", currentResult);
         currentResult.setValue("description", "<p>The <code>SNMPAttributeChangeMBeans</code> which describe the MBean type and Attribute name for which attribute change notification should be sent when an attribute change is observed.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.SNMPAttributeChangeMBean")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroySNMPAttributeChange");
         currentResult.setValue("creator", "createSNMPAttributeChange");
         currentResult.setValue("creator", "createSNMPAttributeChange");
         currentResult.setValue("setterDeprecated", "9.0.0.0 Instead, use standard JMX design patterns using <code>javax.management.MBeanServerConnection</code> interface. ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SNMPCounterMonitors")) {
         getterName = "getSNMPCounterMonitors";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSNMPCounterMonitors";
         }

         currentResult = new PropertyDescriptor("SNMPCounterMonitors", SNMPAgentMBean.class, getterName, setterName);
         descriptors.put("SNMPCounterMonitors", currentResult);
         currentResult.setValue("description", "<p>The <code>SNMPCounterMonitorMBeans</code> which describe the criteria for generating notifications based on JMX CounterMonitor.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.SNMPCounterMonitorMBean")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createSNMPCounterMonitor");
         currentResult.setValue("creator", "createSNMPCounterMonitor");
         currentResult.setValue("destroyer", "destroySNMPCounterMonitor");
         currentResult.setValue("setterDeprecated", "9.0.0.0 Instead, use standard JMX design patterns using <code>javax.management.MBeanServerConnection</code> interface. ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("10.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("SNMPEngineId")) {
         getterName = "getSNMPEngineId";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSNMPEngineId";
         }

         currentResult = new PropertyDescriptor("SNMPEngineId", SNMPAgentMBean.class, getterName, setterName);
         descriptors.put("SNMPEngineId", currentResult);
         currentResult.setValue("description", "<p>An identifier for this SNMP agent that is unique amongst all other SNMP agents in the current WebLogic Server domain.</p> <p>If you use SNMPv3 to send messages to this SNMP agent, you must specify the SNMP engine ID when you configure the SNMP manager.</p> <p>For an SNMP agent on an Administration Server, the default value is the name of the WebLogic Server domain. For an agent on a Managed Server, the default is the name of the server.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.0.0.0");
      }

      if (!descriptors.containsKey("SNMPGaugeMonitors")) {
         getterName = "getSNMPGaugeMonitors";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSNMPGaugeMonitors";
         }

         currentResult = new PropertyDescriptor("SNMPGaugeMonitors", SNMPAgentMBean.class, getterName, setterName);
         descriptors.put("SNMPGaugeMonitors", currentResult);
         currentResult.setValue("description", "<p>The <code>SNMPGaugeMonitorMBeans</code> which describe the criteria for generating notifications based on JMX GaugeMonitor.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.SNMPGaugeMonitorMBean")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroySNMPGaugeMonitor");
         currentResult.setValue("creator", "createSNMPGaugeMonitor");
         currentResult.setValue("creator", "createSNMPGaugeMonitor");
         currentResult.setValue("setterDeprecated", "9.0.0.0 Instead, use standard JMX design patterns using <code>javax.management.MBeanServerConnection</code> interface. ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SNMPLogFilters")) {
         getterName = "getSNMPLogFilters";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSNMPLogFilters";
         }

         currentResult = new PropertyDescriptor("SNMPLogFilters", SNMPAgentMBean.class, getterName, setterName);
         descriptors.put("SNMPLogFilters", currentResult);
         currentResult.setValue("description", "<p>The <code>SNMPLogFilterMBeans</code> which describe filters for generating notifications based on server log messages.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.SNMPLogFilterMBean")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroySNMPLogFilter");
         currentResult.setValue("creator", "createSNMPLogFilter");
         currentResult.setValue("creator", "createSNMPLogFilter");
         currentResult.setValue("setterDeprecated", "9.0.0.0 Instead, use standard JMX design patterns using <code>javax.management.MBeanServerConnection</code> interface. ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SNMPPort")) {
         getterName = "getSNMPPort";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSNMPPort";
         }

         currentResult = new PropertyDescriptor("SNMPPort", SNMPAgentMBean.class, getterName, setterName);
         descriptors.put("SNMPPort", currentResult);
         currentResult.setValue("description", "<p>The port on which you want this SNMP agent to listen for incoming requests from SNMP managers that use the UDP protocol.</p>  <p>SNMP managers can use this port to ping the SNMP agent and request the status of specific attributes.</p>  <p>If you target this SNMP agent to multiple server instances, and if two or more servers are running on the same computer, WebLogic Server will automatically increment this UDP port value by 1 for each agent. WebLogic Server never assigns port 162 because it is the default port that an agent uses to send notifications. In addition, if any port is already in use, WebLogic Server skips the port and assigns the next available port.</p> <p>For example, if you use the default value of this attribute and then target this agent to ManagedServer1 and ManagedServer2, and if both servers are running on the same computer, then the agent on ManagedServer1 will listen on UDP port 161 and the agent on ManagedServer2 will listen on UDP port 163.</p> <p>The incremented port number is not persisted in the domain's configuration; when WebLogic Server increments port numbers, it does so in the order in which servers are started on the same computer.</p> <p>If WebLogic Server re-assigns the UDP port for an SNMP agent, look in the agent's SNMPAgentRuntimeMBean to see the agent's runtime UDP port.</p> <p>SNMP agents can also communicate through the host server's TCP listen port (7001 by default) or through a TCP port that is configured by a custom network channel.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.runtime.SNMPAgentRuntimeMBean"), BeanInfoHelper.encodeEntities("ServerMBean#getListenPort()"), BeanInfoHelper.encodeEntities("NetworkAccessPointMBean")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(161));
         currentResult.setValue("legalMax", new Integer(65535));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SNMPProxies")) {
         getterName = "getSNMPProxies";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSNMPProxies";
         }

         currentResult = new PropertyDescriptor("SNMPProxies", SNMPAgentMBean.class, getterName, setterName);
         descriptors.put("SNMPProxies", currentResult);
         currentResult.setValue("description", "<p>The SNMP agents for which this SNMP agent is a proxy. <code>SNMPProxyMBeans</code> describe settings for SNMP agents to be proxied by this SNMP agent.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.SNMPProxyMBean")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createSNMPProxy");
         currentResult.setValue("creator", "createSNMPProxy");
         currentResult.setValue("destroyer", "destroySNMPProxy");
         currentResult.setValue("setterDeprecated", "9.0.0.0 Instead, use standard JMX design patterns using <code>javax.management.MBeanServerConnection</code> interface. ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SNMPStringMonitors")) {
         getterName = "getSNMPStringMonitors";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSNMPStringMonitors";
         }

         currentResult = new PropertyDescriptor("SNMPStringMonitors", SNMPAgentMBean.class, getterName, setterName);
         descriptors.put("SNMPStringMonitors", currentResult);
         currentResult.setValue("description", "<p>The <code>SNMPStringMonitorMBeans</code> which describe the criteria for generating notifications based on JMX StringMonitor.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.SNMPStringMonitorMBean")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroySNMPStringMonitor");
         currentResult.setValue("creator", "createSNMPStringMonitor");
         currentResult.setValue("creator", "createSNMPStringMonitor");
         currentResult.setValue("setterDeprecated", "9.0.0.0 Instead, use standard JMX design patterns using <code>javax.management.MBeanServerConnection</code> interface. ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("SNMPTrapDestinations")) {
         getterName = "getSNMPTrapDestinations";
         setterName = null;
         currentResult = new PropertyDescriptor("SNMPTrapDestinations", SNMPAgentMBean.class, getterName, setterName);
         descriptors.put("SNMPTrapDestinations", currentResult);
         currentResult.setValue("description", "<p>WebLogic Server uses a trap destination to specify the SNMP management station and the community name used by the SNMP agent to send notifications. Select which trap destination(s) should be used in this WebLogic Server domain from the list of available trap destinations.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroySNMPTrapDestination");
         currentResult.setValue("creator", "createSNMPTrapDestination");
         currentResult.setValue("creator", "createSNMPTrapDestination");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (!descriptors.containsKey("SNMPTrapVersion")) {
         getterName = "getSNMPTrapVersion";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSNMPTrapVersion";
         }

         currentResult = new PropertyDescriptor("SNMPTrapVersion", SNMPAgentMBean.class, getterName, setterName);
         descriptors.put("SNMPTrapVersion", currentResult);
         currentResult.setValue("description", "<p>The SNMP notification version that this SNMP agent generates.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("SNMPAgentMBean#isCommunityBasedAccessEnabled")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(3));
         currentResult.setValue("legalValues", new Object[]{new Integer(1), new Integer(2), new Integer(3)});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ServerStatusCheckIntervalFactor")) {
         getterName = "getServerStatusCheckIntervalFactor";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setServerStatusCheckIntervalFactor";
         }

         currentResult = new PropertyDescriptor("ServerStatusCheckIntervalFactor", SNMPAgentMBean.class, getterName, setterName);
         descriptors.put("ServerStatusCheckIntervalFactor", currentResult);
         currentResult.setValue("description", "<p>The multiplier used to calculate the interval at which this SNMP agent checks for newly started or shut down server instances.</p>  <p>You can enable the SNMP agent to automatically generate serverStartup and serverShutdown notifications when servers start or shut down. See {@link SNMPAgentMBean#isSendAutomaticTrapsEnabled}.</p>  <p>This status check value is multiplied by the MIB Data Refresh Interval to determine the interval:</p>  <p><code>interval = n * MibDataRefreshInterval</code></p>  <p>The SNMP Agent uses internal notifications to update itself when a server is restarted so there is no need to poll the server for their status.</p>  <p>For the most frequent interval, specify <code>1</code> as the multiplier value.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(1));
         currentResult.setValue("legalMax", new Integer(65535));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("deprecated", "10.0.0.0 There is no replacement for this attribute. ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Tags")) {
         getterName = "getTags";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTags";
         }

         currentResult = new PropertyDescriptor("Tags", SNMPAgentMBean.class, getterName, setterName);
         descriptors.put("Tags", currentResult);
         currentResult.setValue("description", "<p>Return all tags on this Configuration MBean</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("TargetedTrapDestinations")) {
         getterName = "getTargetedTrapDestinations";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTargetedTrapDestinations";
         }

         currentResult = new PropertyDescriptor("TargetedTrapDestinations", SNMPAgentMBean.class, getterName, setterName);
         descriptors.put("TargetedTrapDestinations", currentResult);
         currentResult.setValue("description", "<p>WebLogic Server uses a trap destination to specify the SNMP management station and the community name used by the SNMP agent to send trap notifications.</p> <p>This attribute contains the collection of trap destinations that have been configured for this SNMP agent.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("adder", "addTargetedTrapDestination");
         currentResult.setValue("remover", "removeTargetedTrapDestination");
         currentResult.setValue("deprecated", "9.0.0.0 Use the getSNMPTrapDestinations() method instead. ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("CommunityBasedAccessEnabled")) {
         getterName = "isCommunityBasedAccessEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCommunityBasedAccessEnabled";
         }

         currentResult = new PropertyDescriptor("CommunityBasedAccessEnabled", SNMPAgentMBean.class, getterName, setterName);
         descriptors.put("CommunityBasedAccessEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether this SNMP agent supports SNMPv1 and v2.</p> <p>SNMPv1 and v2 use community strings for authentication. If you disable community strings for this SNMP agent, the agent will process only SNMPv3 requests. If an SNMP manager sends a v1 or v2 message, the agent discards the message and returns an error code to the manager.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(false));
         currentResult.setValue("deprecated", "12.2.1.4 There is no replacement for this attribute as SNMPv1 and v2 support will be removed. ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.0.0.0");
      }

      if (!descriptors.containsKey("DynamicallyCreated")) {
         getterName = "isDynamicallyCreated";
         setterName = null;
         currentResult = new PropertyDescriptor("DynamicallyCreated", SNMPAgentMBean.class, getterName, setterName);
         descriptors.put("DynamicallyCreated", currentResult);
         currentResult.setValue("description", "<p>Return whether the MBean was created dynamically or is persisted to config.xml</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
      }

      if (!descriptors.containsKey("Enabled")) {
         getterName = "isEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEnabled";
         }

         currentResult = new PropertyDescriptor("Enabled", SNMPAgentMBean.class, getterName, setterName);
         descriptors.put("Enabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether this SNMP agent is enabled.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("10.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("InformEnabled")) {
         getterName = "isInformEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setInformEnabled";
         }

         currentResult = new PropertyDescriptor("InformEnabled", SNMPAgentMBean.class, getterName, setterName);
         descriptors.put("InformEnabled", currentResult);
         currentResult.setValue("description", "<p>Configures this SNMP agent to send notifications as an INFORM instead of a TRAP. Requires you to specify the agent's SNMPTrapVersion as SNMPv2 or SNMPv3.</p> <p>When an agent sends an INFORM notification, it waits for a confirmation response from the SNMP manager. If it does not receive a response, it resends the INFORM notification.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getSNMPTrapVersion()")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("SNMPAccessForUserMBeansEnabled")) {
         getterName = "isSNMPAccessForUserMBeansEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSNMPAccessForUserMBeansEnabled";
         }

         currentResult = new PropertyDescriptor("SNMPAccessForUserMBeansEnabled", SNMPAgentMBean.class, getterName, setterName);
         descriptors.put("SNMPAccessForUserMBeansEnabled", currentResult);
         currentResult.setValue("description", "<p>Configures this SNMP agent to provide read-only access to MBean types that you have created and registered (custom MBeans).</p> <p>If you enable this access, when you register a custom MBean in a WebLogic Server MBeanServer, this SNMP agent dynamically updates a runtime MIB module that WebLogic Server maintains for custom MBeans. </p> <p>For each custom MBean type, WebLogic Server adds a table to the MIB module. For each instance of the custom MBean, it adds a table row. While WebLogic Server does not persist the MIB as a file or other data structure, the OIDs in the MIB remain constant across server sessions.</p> <p>The MIB module for custom MBeans is managed by a subAgent. Its master agent is this WebLogic Server SNMP agent and it communicates with the master agent through the AgentX port.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getMasterAgentXPort()")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.0.0.0");
      }

      if (!descriptors.containsKey("SendAutomaticTrapsEnabled")) {
         getterName = "isSendAutomaticTrapsEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSendAutomaticTrapsEnabled";
         }

         currentResult = new PropertyDescriptor("SendAutomaticTrapsEnabled", SNMPAgentMBean.class, getterName, setterName);
         descriptors.put("SendAutomaticTrapsEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether this SNMP agent sends automatically generated notifications to SNMP managers.</p>  <p>The SNMP agent generates automatic notifications when any of the following events occur:</p>  <ul> <li><p>The WebLogic Server instance that is hosting the SNMP agent starts.</p> <p>This type of notification (coldStart) has no variable bindings.</p></li> <li> <p>A server instance starts or stops.</p> <p>An SNMP agent on a Managed Server generates these notifications only when its host Managed Server starts or stops. An SNMP agent on an Administration Server generates these notifications when any server in the domain starts or stops.</p> <p>These notification types (serverStart and serverShutdown) contain variable bindings to identify the server that started or stopped and the time at which the notification was generated.</p> </li> </ul> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("SNMPAgentMBean#getServerStatusCheckIntervalFactor")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      MethodDescriptor currentResult;
      Method mth;
      ParameterDescriptor[] parameterDescriptors;
      String methodKey;
      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = SNMPAgentMBean.class.getMethod("createSNMPTrapDestination", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Factory method to create SNMPTrapDestination objects</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "SNMPTrapDestinations");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = SNMPAgentMBean.class.getMethod("createSNMPTrapDestination", String.class, SNMPTrapDestinationMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null), createParameterDescriptor("toClone", "is the SNMPTrapDestinationMBean that is being moved from the DomainMBean ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Factory method to clone an SNMPTrapDestination object</p> ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "SNMPTrapDestinations");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = SNMPAgentMBean.class.getMethod("destroySNMPTrapDestination", SNMPTrapDestinationMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("trapdestination", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Removes a SNMPTrapDestination from this SNMPAgent</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "SNMPTrapDestinations");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = SNMPAgentMBean.class.getMethod("createSNMPProxy", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Factory method to create SNMPProxy objects</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "SNMPProxies");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = SNMPAgentMBean.class.getMethod("createSNMPProxy", String.class, SNMPProxyMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null), createParameterDescriptor("toClone", "is the SNMPProxyMBean that is being moved from the DomainMBean ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Factory method to clone an SNMPProxy object</p> ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "SNMPProxies");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = SNMPAgentMBean.class.getMethod("destroySNMPProxy", SNMPProxyMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("snmpProxy", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Removes a SNMPProxy from this domain</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "SNMPProxies");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = SNMPAgentMBean.class.getMethod("createSNMPGaugeMonitor", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Factory method to create SNMPGaugeMonitor objects</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "SNMPGaugeMonitors");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = SNMPAgentMBean.class.getMethod("createSNMPGaugeMonitor", String.class, SNMPGaugeMonitorMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null), createParameterDescriptor("toClone", "is the SNMPGaugeMonitorMBean that is being moved from the DomainMBean ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Factory method to clone an SNMPGaugeMonitor object</p> ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "SNMPGaugeMonitors");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = SNMPAgentMBean.class.getMethod("destroySNMPGaugeMonitor", SNMPGaugeMonitorMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("gaugemonitor", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Removes a SNMPGaugeMonitor from this domain</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "SNMPGaugeMonitors");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = SNMPAgentMBean.class.getMethod("createSNMPStringMonitor", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Factory method to create SNMPStringMonitor objects</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "SNMPStringMonitors");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = SNMPAgentMBean.class.getMethod("createSNMPStringMonitor", String.class, SNMPStringMonitorMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null), createParameterDescriptor("toClone", "is the SNMPStringMonitorMBean that is being moved from the DomainMBean ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Factory method to clone an SNMPStringMonitor object</p> ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "SNMPStringMonitors");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = SNMPAgentMBean.class.getMethod("destroySNMPStringMonitor", SNMPStringMonitorMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("stringmonitor", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Removes a SNMPStringMonitor from this domain</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "SNMPStringMonitors");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = SNMPAgentMBean.class.getMethod("createSNMPCounterMonitor", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Factory method to create SNMPCounterMonitor objects</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "SNMPCounterMonitors");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = SNMPAgentMBean.class.getMethod("createSNMPCounterMonitor", String.class, SNMPCounterMonitorMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null), createParameterDescriptor("toClone", "is the SNMPCounterMonitorMBean that is being moved from the DomainMBean ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Factory method to clone an SNMPCounterMonitor object</p> ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "SNMPCounterMonitors");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = SNMPAgentMBean.class.getMethod("destroySNMPCounterMonitor", SNMPCounterMonitorMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("countermonitor", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Removes a SNMPCounterMonitor from this domain</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "SNMPCounterMonitors");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = SNMPAgentMBean.class.getMethod("createSNMPLogFilter", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Factory method to create SNMPLogFilter objects</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "SNMPLogFilters");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = SNMPAgentMBean.class.getMethod("createSNMPLogFilter", String.class, SNMPLogFilterMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null), createParameterDescriptor("toClone", "is the SNMPLogFilterMBean that is being moved from the DomainMBean ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Factory method to clone an SNMPLogFilter object</p> ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "SNMPLogFilters");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = SNMPAgentMBean.class.getMethod("destroySNMPLogFilter", SNMPLogFilterMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("logfilter", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Removes a SNMPLogFilter from this domain</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "SNMPLogFilters");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = SNMPAgentMBean.class.getMethod("createSNMPAttributeChange", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Factory method to create SNMPAttributeChange objects</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "SNMPAttributeChanges");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = SNMPAgentMBean.class.getMethod("createSNMPAttributeChange", String.class, SNMPAttributeChangeMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null), createParameterDescriptor("toClone", "is the SNMPAttributeChangeMBean that is being moved from the DomainMBean ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Factory method to clone an SNMPAttributeChange object</p> ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "SNMPAttributeChanges");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = SNMPAgentMBean.class.getMethod("destroySNMPAttributeChange", SNMPAttributeChangeMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("attrchange", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Removes a SNMPAttributeChange from this domain</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "SNMPAttributeChanges");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      MethodDescriptor currentResult;
      Method mth;
      ParameterDescriptor[] parameterDescriptors;
      String methodKey;
      String[] seeObjectArray;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = SNMPAgentMBean.class.getMethod("addTag", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("tag", "tag to be added to the MBean ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("IllegalArgumentException if the tag contains illegal punctuation")};
            currentResult.setValue("throws", seeObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Add a tag to this Configuration MBean.  Adds a tag to the current set of tags on the Configuration MBean.  Tags may contain white spaces.</p> ");
            currentResult.setValue("role", "collection");
            currentResult.setValue("property", "Tags");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      mth = SNMPAgentMBean.class.getMethod("addTargetedTrapDestination", SNMPTrapDestinationMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("trapdestination", "The feature to be added to the TargetedTrapDestination attribute ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Adds a single trap destination to this SNMP agent's list of targeted trap destinations.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getTargetedTrapDestinations")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "TargetedTrapDestinations");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = SNMPAgentMBean.class.getMethod("removeTag", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("tag", "tag to be removed from the MBean ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("IllegalArgumentException if the tag contains illegal punctuation")};
            currentResult.setValue("throws", seeObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Remove a tag from this Configuration MBean</p> ");
            currentResult.setValue("role", "collection");
            currentResult.setValue("property", "Tags");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      mth = SNMPAgentMBean.class.getMethod("removeTargetedTrapDestination", SNMPTrapDestinationMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("trapdestination", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "TargetedTrapDestinations");
      }

      mth = SNMPAgentMBean.class.getMethod("addSNMPTrapDestination", SNMPTrapDestinationMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("trapdestination", "The feature to be added to the SNMPTrapDestination attribute ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 Instead, use standard JMX design patterns using <code>javax.management.MBeanServerConnection</code> interface. ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Adds an SNMPTrapDestination to the SNMPAgentMBean object</p> ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "SNMPTrapDestinations");
      }

      mth = SNMPAgentMBean.class.getMethod("addSNMPProxy", SNMPProxyMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("snmpProxy", "The feature to be added to the SNMPProxy attribute ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 Instead, use standard JMX design patterns using <code>javax.management.MBeanServerConnection</code> interface. ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Adds a feature to the SNMPProxy attribute of the SNMPAgentMBean object</p> ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "SNMPProxies");
      }

      mth = SNMPAgentMBean.class.getMethod("removeSNMPProxy", SNMPProxyMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("snmpProxy", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 Instead, use standard JMX design patterns using <code>javax.management.MBeanServerConnection</code> interface. ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "SNMPProxies");
      }

      mth = SNMPAgentMBean.class.getMethod("addSNMPGaugeMonitor", SNMPGaugeMonitorMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("gaugemonitor", "The feature to be added to the SNMPGaugeMonitor attribute ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 Instead, use standard JMX design patterns using <code>javax.management.MBeanServerConnection</code> interface. ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Adds a feature to the SNMPGaugeMonitor attribute of the SNMPAgentMBean object</p> ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "SNMPGaugeMonitors");
      }

      mth = SNMPAgentMBean.class.getMethod("removeSNMPGaugeMonitor", SNMPGaugeMonitorMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("gaugemonitor", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 Instead, use standard JMX design patterns using <code>javax.management.MBeanServerConnection</code> interface. ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "SNMPGaugeMonitors");
      }

      mth = SNMPAgentMBean.class.getMethod("addSNMPStringMonitor", SNMPStringMonitorMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("stringmonitor", "The feature to be added to the SNMPStringMonitor attribute ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 Instead, use standard JMX design patterns using <code>javax.management.MBeanServerConnection</code> interface. ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Adds a single <code>SNMPStringMonitorMBeans</code> to this SNMP agent's collection.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getSNMPStringMonitors()")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "SNMPStringMonitors");
      }

      mth = SNMPAgentMBean.class.getMethod("removeSNMPStringMonitor", SNMPStringMonitorMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("stringmonitor", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 Instead, use standard JMX design patterns using <code>javax.management.MBeanServerConnection</code> interface. ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "SNMPStringMonitors");
      }

      mth = SNMPAgentMBean.class.getMethod("addSNMPCounterMonitor", SNMPCounterMonitorMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("countermonitor", "The feature to be added to the SNMPCounterMonitor attribute ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 Instead, use standard JMX design patterns using <code>javax.management.MBeanServerConnection</code> interface. ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Adds a feature to the SNMPCounterMonitor attribute of the SNMPAgentMBean object</p> ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "SNMPCounterMonitors");
      }

      mth = SNMPAgentMBean.class.getMethod("removeSNMPCounterMonitor", SNMPCounterMonitorMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("countermonitor", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 Instead, use standard JMX design patterns using <code>javax.management.MBeanServerConnection</code> interface. ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "SNMPCounterMonitors");
      }

      mth = SNMPAgentMBean.class.getMethod("addSNMPLogFilter", SNMPLogFilterMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("logfilter", "The feature to be added to the SNMPLogFilter attribute ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 Instead, use standard JMX design patterns using <code>javax.management.MBeanServerConnection</code> interface. ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Adds a feature to the SNMPLogFilter attribute of the SNMPAgentMBean object</p> ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "SNMPLogFilters");
      }

      mth = SNMPAgentMBean.class.getMethod("removeSNMPLogFilter", SNMPLogFilterMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("logfilter", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 Instead, use standard JMX design patterns using <code>javax.management.MBeanServerConnection</code> interface. ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "SNMPLogFilters");
      }

      mth = SNMPAgentMBean.class.getMethod("addSNMPAttributeChange", SNMPAttributeChangeMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("attrchange", "The feature to be added to the SNMPAttributeChange attribute ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 Instead, use standard JMX design patterns using <code>javax.management.MBeanServerConnection</code> interface. ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Adds a feature to the SNMPAttributeChange attribute of the SNMPAgentMBean object</p> ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "SNMPAttributeChanges");
      }

      mth = SNMPAgentMBean.class.getMethod("removeSNMPAttributeChange", SNMPAttributeChangeMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("attrchange", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 Instead, use standard JMX design patterns using <code>javax.management.MBeanServerConnection</code> interface. ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "SNMPAttributeChanges");
      }

   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      MethodDescriptor currentResult;
      Method mth;
      ParameterDescriptor[] parameterDescriptors;
      String methodKey;
      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = SNMPAgentMBean.class.getMethod("lookupSNMPTrapDestination", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "SNMPTrapDestinations");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = SNMPAgentMBean.class.getMethod("lookupSNMPProxy", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "SNMPProxies");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = SNMPAgentMBean.class.getMethod("lookupSNMPGaugeMonitor", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "SNMPGaugeMonitors");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = SNMPAgentMBean.class.getMethod("lookupSNMPStringMonitor", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "SNMPStringMonitors");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = SNMPAgentMBean.class.getMethod("lookupSNMPCounterMonitor", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "SNMPCounterMonitors");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = SNMPAgentMBean.class.getMethod("lookupSNMPLogFilter", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "SNMPLogFilters");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = SNMPAgentMBean.class.getMethod("lookupSNMPAttributeChange", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "SNMPAttributeChanges");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = SNMPAgentMBean.class.getMethod("freezeCurrentValue", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("attributeName", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>If the specified attribute has not been set explicitly, and if the attribute has a default value, this operation forces the MBean to persist the default value.</p>  <p>Unless you use this operation, the default value is not saved and is subject to change if you update to a newer release of WebLogic Server. Invoking this operation isolates this MBean from the effects of such changes.</p>  <p>Note: To insure that you are freezing the default value, invoke the <code>restoreDefaultValue</code> operation before you invoke this.</p>  <p>This operation has no effect if you invoke it on an attribute that does not provide a default value or on an attribute for which some other value has been set.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = SNMPAgentMBean.class.getMethod("restoreDefaultValue", String.class);
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
