package weblogic.diagnostics.snmp.server;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.SNMPAgentRuntimeMBean;

public class SNMPAgentRuntimeBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = SNMPAgentRuntimeMBean.class;

   public SNMPAgentRuntimeBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public SNMPAgentRuntimeBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.diagnostics.snmp.server.SNMPAgentRuntime");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.0.0");
      String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.SNMPAgentMBean"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.SNMPAgentDeploymentMBean")};
      beanDescriptor.setValue("see", seeObjectArray);
      beanDescriptor.setValue("package", "weblogic.diagnostics.snmp.server");
      String description = (new String("<p>Runtime information for an SNMP agent that is running in the current WebLogic Server domain.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.SNMPAgentRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (BeanInfoHelper.isVersionCompliant("10.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("AttributeChangeTrapCount")) {
         getterName = "getAttributeChangeTrapCount";
         setterName = null;
         currentResult = new PropertyDescriptor("AttributeChangeTrapCount", SNMPAgentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AttributeChangeTrapCount", currentResult);
         currentResult.setValue("description", "<p>The number of attribute change notifications that this SNMP agent has sent to all trap destinations since the agent's host server was started.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("CounterMonitorTrapCount")) {
         getterName = "getCounterMonitorTrapCount";
         setterName = null;
         currentResult = new PropertyDescriptor("CounterMonitorTrapCount", SNMPAgentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CounterMonitorTrapCount", currentResult);
         currentResult.setValue("description", "<p>The number of counter monitor notifications that this SNMP agent has sent to all trap destinations since the agent's host server was started.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("FailedAuthenticationCount")) {
         getterName = "getFailedAuthenticationCount";
         setterName = null;
         currentResult = new PropertyDescriptor("FailedAuthenticationCount", SNMPAgentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("FailedAuthenticationCount", currentResult);
         currentResult.setValue("description", "<p>The number of requests that this agent has rejected because of incorrect user credentials.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("FailedAuthorizationCount")) {
         getterName = "getFailedAuthorizationCount";
         setterName = null;
         currentResult = new PropertyDescriptor("FailedAuthorizationCount", SNMPAgentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("FailedAuthorizationCount", currentResult);
         currentResult.setValue("description", "<p>The number of requests that this agent has rejected because an authenticated user does not have sufficient privileges to view the requested information. You use the WebLogic Server security realm to assign privileges to users.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("FailedEncryptionCount")) {
         getterName = "getFailedEncryptionCount";
         setterName = null;
         currentResult = new PropertyDescriptor("FailedEncryptionCount", SNMPAgentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("FailedEncryptionCount", currentResult);
         currentResult.setValue("description", "<p>The number of requests that this agent has rejected because of incorrect privacy (encryption) credentials</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("GaugeMonitorTrapCount")) {
         getterName = "getGaugeMonitorTrapCount";
         setterName = null;
         currentResult = new PropertyDescriptor("GaugeMonitorTrapCount", SNMPAgentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("GaugeMonitorTrapCount", currentResult);
         currentResult.setValue("description", "<p>The number of gauge monitor notifications that this SNMP agent has sent to all trap destinations since the agent's host server was started.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("LogMessageTrapCount")) {
         getterName = "getLogMessageTrapCount";
         setterName = null;
         currentResult = new PropertyDescriptor("LogMessageTrapCount", SNMPAgentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LogMessageTrapCount", currentResult);
         currentResult.setValue("description", "<p>The number of log message notifications that this SNMP agent has sent to all trap destinations since the agent's host server was started.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("MasterAgentXPort")) {
         getterName = "getMasterAgentXPort";
         setterName = null;
         currentResult = new PropertyDescriptor("MasterAgentXPort", SNMPAgentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MasterAgentXPort", currentResult);
         currentResult.setValue("description", "<p>The port that this SNMP agent uses to communicate with subagents.</p> <p>The agent uses subagents to provide access to custom MBeans (MBeans that you create and register) and to other Oracle software components. WebLogic Server SNMP agents do not enable users to register their own subagents.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("MonitorTrapCount")) {
         getterName = "getMonitorTrapCount";
         setterName = null;
         currentResult = new PropertyDescriptor("MonitorTrapCount", SNMPAgentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MonitorTrapCount", currentResult);
         currentResult.setValue("description", "<p>The total number of all notifications that this SNMP agent has sent to all trap destinations since the agent's host server was started.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("SNMPAgentName")) {
         getterName = "getSNMPAgentName";
         setterName = null;
         currentResult = new PropertyDescriptor("SNMPAgentName", SNMPAgentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SNMPAgentName", currentResult);
         currentResult.setValue("description", "<p>Gets the name of the SNMPAgent MBean configuration that is currently active. Returns null if no SNMPAgent configuration is currently active on this Server.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ServerStartTrapCount")) {
         getterName = "getServerStartTrapCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ServerStartTrapCount", SNMPAgentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ServerStartTrapCount", currentResult);
         currentResult.setValue("description", "<p>The number of serverStart notifications that this SNMP agent has sent to all trap destinations since the agent's host server was started.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ServerStopTrapCount")) {
         getterName = "getServerStopTrapCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ServerStopTrapCount", SNMPAgentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ServerStopTrapCount", currentResult);
         currentResult.setValue("description", "<p>The number of serverShutdown notifications that this SNMP agent has sent to all trap destinations since the agent's host server was started.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("StringMonitorTrapCount")) {
         getterName = "getStringMonitorTrapCount";
         setterName = null;
         currentResult = new PropertyDescriptor("StringMonitorTrapCount", SNMPAgentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("StringMonitorTrapCount", currentResult);
         currentResult.setValue("description", "<p>The number of string monitor notifications that this SNMP agent has sent to all trap destinations since the agent's host server was started.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("UDPListenPort")) {
         getterName = "getUDPListenPort";
         setterName = null;
         currentResult = new PropertyDescriptor("UDPListenPort", SNMPAgentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("UDPListenPort", currentResult);
         currentResult.setValue("description", "<p>The UDP port on which this SNMP agent is listening for incoming requests from SNMP managers.</p> <p>SNMP agents can also communicate through the host server's TCP listen port (7001 by default) or through a TCP port that is configured by a custom network channel.</p> ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.SNMPAgentMBean#getSNMPPort()")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Running")) {
         getterName = "isRunning";
         setterName = null;
         currentResult = new PropertyDescriptor("Running", SNMPAgentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Running", currentResult);
         currentResult.setValue("description", "<p>Indicates whether this SNMP agent is running.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.0.0.0");
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
      MethodDescriptor currentResult;
      Method mth;
      if (BeanInfoHelper.isVersionCompliant("10.0.0.0", (String)null, this.targetVersion)) {
         mth = SNMPAgentRuntimeMBean.class.getMethod("outputCustomMBeansMIBModule");
         String methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            String[] throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ManagementException")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "10.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Returns WebLogic Server's MIB module for custom MBeans as a <code>java.lang.String</code>. You can save the <code>String</code> to a file and then load the file into a MIB browser.</p>  <p>When you register custom MBeans in the WebLogic Server Runtime MBean Server, WebLogic Server adds entries to a runtime MIB module that it maintains for custom MBeans. For each custom MBean type, WebLogic Server adds a table to the MIB module. For each instance of the custom MBean, it adds a table row. While WebLogic Server does not persist the MIB module as a file or other data structure, the OIDs in the module remain constant across server sessions.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "10.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("10.0.0.0", (String)null, this.targetVersion)) {
         mth = SNMPAgentRuntimeMBean.class.getMethod("invalidateLocalizedKeyCache", String.class);
         ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("username", "Name of the user ")};
         String methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "10.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Immediately invalidates the cached security keys for the specified WebLogic Server user.</p>  <p>An SNMP security key is an encrypted version of an SNMP agent's engine ID and an authentication password or privacy password. WebLogic Server generates one security key for each entry that you create in the SNMP credential map. When a WebLogic Server SNMP agent receives an SNMPv3 request, it compares the key that is in the request with its WebLogic Server keys. If it finds a match, it processes the request. The SNMP agent also encodes these keys in its responses and notifications. (You configure which keys are encoded when you create a trap destination.)</p>  <p>Instead of regenerating the keys for each SNMPv3 communication, WebLogic Server caches the keys. To make sure that the cache contains the latest set of SNMP credentials, WebLogic Server periodically invalidates the cache. After the cache is invalidated, the next time an SNMP agent requests credentials, WebLogic Server regenerates the cache.</p>  <p>Note that making a change to the credential map does not automatically update the cache. Instead, the cache is updated only after it has been invalidated.</p>  <p>Instead of waiting for WebLogic Server to invalidate the cached entry for a key, you can invalidate it immediately.</p> ");
            String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.SNMPAgentMBean#getLocalizedKeyCacheInvalidationInterval()")};
            currentResult.setValue("see", seeObjectArray);
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "10.0.0.0");
         }
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
