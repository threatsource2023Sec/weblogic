package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class KernelMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = KernelMBean.class;

   public KernelMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public KernelMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.KernelMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("This bean represents the configuration of the core message passing kernel on both WebLogic clients and servers.  <p> {@link ServerMBean ServerMBean} extends this bean to represent the configuration of a server.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.KernelMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("AdministrationProtocol")) {
         getterName = "getAdministrationProtocol";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAdministrationProtocol";
         }

         currentResult = new PropertyDescriptor("AdministrationProtocol", KernelMBean.class, getterName, setterName);
         descriptors.put("AdministrationProtocol", currentResult);
         currentResult.setValue("description", "<p>Returns the protocol to be used for administrative connections when none is specified.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalValues", new Object[]{"t3s", "https", "iiops", "t3", "http", "iiop"});
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      String[] seeObjectArray;
      if (BeanInfoHelper.isVersionCompliant("7.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("CompleteCOMMessageTimeout")) {
         getterName = "getCompleteCOMMessageTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCompleteCOMMessageTimeout";
         }

         currentResult = new PropertyDescriptor("CompleteCOMMessageTimeout", KernelMBean.class, getterName, setterName);
         descriptors.put("CompleteCOMMessageTimeout", currentResult);
         currentResult.setValue("description", "<p>The maximum number of seconds spent waiting for a complete COM message to be received. This setting does not apply to any network channels that you have configured for this server.</p>  <p>This timeout helps guard against a denial of service attack in which a caller indicates that they will be sending a message of a certain size which they never finish sending.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("NetworkAccessPointMBean#getCompleteMessageTimeout")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("legalMax", new Integer(480));
         currentResult.setValue("legalMin", new Integer(-1));
         currentResult.setValue("deprecated", "8.1.0.0 use {@link NetworkAccessPointMBean#getCompleteMessageTimeout()} ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "7.0.0.0");
      }

      if (!descriptors.containsKey("CompleteHTTPMessageTimeout")) {
         getterName = "getCompleteHTTPMessageTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCompleteHTTPMessageTimeout";
         }

         currentResult = new PropertyDescriptor("CompleteHTTPMessageTimeout", KernelMBean.class, getterName, setterName);
         descriptors.put("CompleteHTTPMessageTimeout", currentResult);
         currentResult.setValue("description", "<p>The maximum number of seconds spent waiting for a complete HTTP message to be received. If you configure network channels for this server, each channel can override this HTTP message timeout.</p>  <p>This timeout helps guard against a denial of service attack in which a caller indicates that it will be sending a message of a certain size which it never finishes sending.</p>  <p>A value of -1 indicates that this value should be obtained from network channels configured for this server.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("NetworkAccessPointMBean#getCompleteMessageTimeout")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("legalMax", new Integer(480));
         currentResult.setValue("legalMin", new Integer(-1));
         currentResult.setValue("deprecated", "8.1.0.0 use {@link NetworkAccessPointMBean#getCompleteMessageTimeout()} ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CompleteIIOPMessageTimeout")) {
         getterName = "getCompleteIIOPMessageTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCompleteIIOPMessageTimeout";
         }

         currentResult = new PropertyDescriptor("CompleteIIOPMessageTimeout", KernelMBean.class, getterName, setterName);
         descriptors.put("CompleteIIOPMessageTimeout", currentResult);
         currentResult.setValue("description", "<p>The maximum number of seconds spent waiting for a complete IIOP message to be received. This timeout helps guard against denial of service attacks in which a caller indicates that they will be sending a message of a certain size which they never finish sending.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("legalMax", new Integer(480));
         currentResult.setValue("legalMin", new Integer(-1));
         currentResult.setValue("deprecated", "8.1.0.0 use {@link NetworkAccessPointMBean#getCompleteMessageTimeout()} ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CompleteMessageTimeout")) {
         getterName = "getCompleteMessageTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCompleteMessageTimeout";
         }

         currentResult = new PropertyDescriptor("CompleteMessageTimeout", KernelMBean.class, getterName, setterName);
         descriptors.put("CompleteMessageTimeout", currentResult);
         currentResult.setValue("description", "<p>The maximum number of seconds that this server waits for a complete message to be received. If you configure network channels for this server, each channel can override this message timeout.</p>  <p>This timeout helps guard against a denial of service attack in which a caller indicates that it will be sending a message of a certain size which it never finishes sending.</p>  <p>CompleteMessageTimeout affects the HTTP Response, such that if WebLogic Server discovers sockets inactive for longer than the CompleteMessageTimeout, the server will close these sockets.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("NetworkAccessPointMBean#getCompleteMessageTimeout")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(60));
         currentResult.setValue("legalMax", new Integer(480));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CompleteT3MessageTimeout")) {
         getterName = "getCompleteT3MessageTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCompleteT3MessageTimeout";
         }

         currentResult = new PropertyDescriptor("CompleteT3MessageTimeout", KernelMBean.class, getterName, setterName);
         descriptors.put("CompleteT3MessageTimeout", currentResult);
         currentResult.setValue("description", "<p>The maximum number of seconds spent waiting for a complete T3 message to be received. If you configure network channels for this server, each channel can override this T3 message timeout.</p>  <p>This timeout helps guard against a denial of service attack in which a caller indicates that it will be sending a message of a certain size which it never finishes sending.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("NetworkAccessPointMBean#getCompleteMessageTimeout")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("legalMax", new Integer(480));
         currentResult.setValue("legalMin", new Integer(-1));
         currentResult.setValue("deprecated", "8.1.0.0 use {@link NetworkAccessPointMBean#getCompleteMessageTimeout()} ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.1.2.0", (String)null, this.targetVersion) && !descriptors.containsKey("CompleteWriteTimeout")) {
         getterName = "getCompleteWriteTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCompleteWriteTimeout";
         }

         currentResult = new PropertyDescriptor("CompleteWriteTimeout", KernelMBean.class, getterName, setterName);
         descriptors.put("CompleteWriteTimeout", currentResult);
         currentResult.setValue("description", "<p>The maximum number of seconds that this server waits for a complete HTTP response to be sent.</p>  <p>A value of <code>0</code> disables the complete write timeout. The default value is derived from the CompleteHTTPMessageTimeout. If it is <code>-1</code>, then the CompleteMessageTimeout value is used for the default value.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.1.2.0");
      }

      if (!descriptors.containsKey("ConnectTimeout")) {
         getterName = "getConnectTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnectTimeout";
         }

         currentResult = new PropertyDescriptor("ConnectTimeout", KernelMBean.class, getterName, setterName);
         descriptors.put("ConnectTimeout", currentResult);
         currentResult.setValue("description", "<p>The amount of time that this server should wait to establish an outbound socket connection before timing out. A value of <code>0</code> disables server connect timeout.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("NetworkAccessPointMBean#getConnectTimeout")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("legalMax", new Integer(240));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DGCIdlePeriodsUntilTimeout")) {
         getterName = "getDGCIdlePeriodsUntilTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDGCIdlePeriodsUntilTimeout";
         }

         currentResult = new PropertyDescriptor("DGCIdlePeriodsUntilTimeout", KernelMBean.class, getterName, setterName);
         descriptors.put("DGCIdlePeriodsUntilTimeout", currentResult);
         currentResult.setValue("description", "<p>The number of idle periods allowed before the object is collected.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(5));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultProtocol")) {
         getterName = "getDefaultProtocol";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultProtocol";
         }

         currentResult = new PropertyDescriptor("DefaultProtocol", KernelMBean.class, getterName, setterName);
         descriptors.put("DefaultProtocol", currentResult);
         currentResult.setValue("description", "<p>The protocol to use for connections when none is specified.</p> ");
         setPropertyDescriptorDefault(currentResult, "t3");
         currentResult.setValue("legalValues", new Object[]{"t3", "t3s", "http", "https", "iiop", "iiops"});
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultSecureProtocol")) {
         getterName = "getDefaultSecureProtocol";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultSecureProtocol";
         }

         currentResult = new PropertyDescriptor("DefaultSecureProtocol", KernelMBean.class, getterName, setterName);
         descriptors.put("DefaultSecureProtocol", currentResult);
         currentResult.setValue("description", "<p>The protocol to use for secure connections when none is specified.</p> ");
         setPropertyDescriptorDefault(currentResult, "t3s");
         currentResult.setValue("legalValues", new Object[]{"t3s", "https", "iiops"});
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ExecuteQueues")) {
         getterName = "getExecuteQueues";
         setterName = null;
         currentResult = new PropertyDescriptor("ExecuteQueues", KernelMBean.class, getterName, setterName);
         descriptors.put("ExecuteQueues", currentResult);
         currentResult.setValue("description", "<p>Returns the execute queues configured for this server.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createExecuteQueue");
         currentResult.setValue("destroyer", "destroyExecuteQueue");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IIOP")) {
         getterName = "getIIOP";
         setterName = null;
         currentResult = new PropertyDescriptor("IIOP", KernelMBean.class, getterName, setterName);
         descriptors.put("IIOP", currentResult);
         currentResult.setValue("description", "<p>Returns the kernel's IIOP configuration. An IIOP MBean is always linked to a particular kernel and cannot be changed. Individual attributes on the IIOP MBean may be changed, as documented, but the MBean itself may not.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IIOPTxMechanism")) {
         getterName = "getIIOPTxMechanism";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIIOPTxMechanism";
         }

         currentResult = new PropertyDescriptor("IIOPTxMechanism", KernelMBean.class, getterName, setterName);
         descriptors.put("IIOPTxMechanism", currentResult);
         currentResult.setValue("description", "<p>Configures IIOP propagate transactions using either WebLogic-specific JTA or the OMG-specified OTS.</p> <p> It is not possible to use both because it affects the way transactions are negotiated.</p> ");
         setPropertyDescriptorDefault(currentResult, "ots");
         currentResult.setValue("legalValues", new Object[]{"ots", "jta"});
         currentResult.setValue("deprecated", "8.1.0.0 use {@link IIOPMBean#getTxMechanism()} ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IdleConnectionTimeout")) {
         getterName = "getIdleConnectionTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIdleConnectionTimeout";
         }

         currentResult = new PropertyDescriptor("IdleConnectionTimeout", KernelMBean.class, getterName, setterName);
         descriptors.put("IdleConnectionTimeout", currentResult);
         currentResult.setValue("description", "<p>The maximum number of seconds that a connection is allowed to be idle before it is closed by the server. The T3 and T3S protocols ignore this attribute. If you configure network channels for this server, each channel can override this idle connection message timeout.</p>  <p>This timeout helps guard against server deadlock through too many open connections.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("NetworkAccessPointMBean#getIdleConnectionTimeout")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(65));
         currentResult.setValue("secureValue", new Integer(65));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IdleIIOPConnectionTimeout")) {
         getterName = "getIdleIIOPConnectionTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIdleIIOPConnectionTimeout";
         }

         currentResult = new PropertyDescriptor("IdleIIOPConnectionTimeout", KernelMBean.class, getterName, setterName);
         descriptors.put("IdleIIOPConnectionTimeout", currentResult);
         currentResult.setValue("description", "<p>The maximum number of seconds an IIOP connection is allowed to be idle before it is closed by the server. This timeout helps guard against server deadlock through too many open connections.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("legalMin", new Integer(-1));
         currentResult.setValue("deprecated", "8.1.0.0 use {@link NetworkAccessPointMBean#getIdleConnectionTimeout()} ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IdlePeriodsUntilTimeout")) {
         getterName = "getIdlePeriodsUntilTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIdlePeriodsUntilTimeout";
         }

         currentResult = new PropertyDescriptor("IdlePeriodsUntilTimeout", KernelMBean.class, getterName, setterName);
         descriptors.put("IdlePeriodsUntilTimeout", currentResult);
         currentResult.setValue("description", "<p>The number of idle periods until peer is considered unreachable.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(4));
         currentResult.setValue("legalMax", new Integer(65534));
         currentResult.setValue("legalMin", new Integer(4));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JMSThreadPoolSize")) {
         getterName = "getJMSThreadPoolSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJMSThreadPoolSize";
         }

         currentResult = new PropertyDescriptor("JMSThreadPoolSize", KernelMBean.class, getterName, setterName);
         descriptors.put("JMSThreadPoolSize", currentResult);
         currentResult.setValue("description", "<p>The size of the JMS execute thread pool.</p>  <p><b>Note</b>: Incoming RMI calls execute in the JMS execute queue/thread pool, if one exists; otherwise, they execute in the default execute queue.</p>  <p>Additional executes (work that cannot be completed in the initial RMI thread) are executed in the default execute queue.</p>  <p>The difference in setting up a JMS-specific thread pool is that JMS will not be starved by other execute threads and vice versa.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(15));
         currentResult.setValue("legalMax", new Integer(65534));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("KernelDebug")) {
         getterName = "getKernelDebug";
         setterName = null;
         currentResult = new PropertyDescriptor("KernelDebug", KernelMBean.class, getterName, setterName);
         descriptors.put("KernelDebug", currentResult);
         currentResult.setValue("description", "<p>Get the debug flags for this kernel (will return a KernelDebugMBean if this is a KernelMBean) or the server (will return a ServerDebugMBean if this is a ServerMBean).</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Log")) {
         getterName = "getLog";
         setterName = null;
         currentResult = new PropertyDescriptor("Log", KernelMBean.class, getterName, setterName);
         descriptors.put("Log", currentResult);
         currentResult.setValue("description", "<p>Returns the log settings for this kernel. A Log MBean is always linked to a particular kernel and cannot be changed. Individual attributes on the Log MBean may be changed, as documented, but the MBean itself may not.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("7.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("MaxCOMMessageSize")) {
         getterName = "getMaxCOMMessageSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxCOMMessageSize";
         }

         currentResult = new PropertyDescriptor("MaxCOMMessageSize", KernelMBean.class, getterName, setterName);
         descriptors.put("MaxCOMMessageSize", currentResult);
         currentResult.setValue("description", "<p>The maximum number of bytes allowed in messages that are received over the COM protocol. If you configure custom network channels for this server, each channel can override this maximum message size.</p>  <p>This maximum message size helps guard against a denial of service attack in which a caller attempts to force the server to allocate more memory than is available thereby keeping the server from responding quickly to other requests.</p>  <p>A value of -1 causes the COM protocol to use the maximums that are specified elsewhere along the order of precedence.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("KernelMBean#getMaxMessageSize"), BeanInfoHelper.encodeEntities("NetworkAccessPointMBean#getMaxMessageSize")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("deprecated", "8.1.0.0 use {@link NetworkAccessPointMBean#getMaxMessageSize()} ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "7.0.0.0");
      }

      if (!descriptors.containsKey("MaxHTTPMessageSize")) {
         getterName = "getMaxHTTPMessageSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxHTTPMessageSize";
         }

         currentResult = new PropertyDescriptor("MaxHTTPMessageSize", KernelMBean.class, getterName, setterName);
         descriptors.put("MaxHTTPMessageSize", currentResult);
         currentResult.setValue("description", "<p>The maximum number of bytes allowed in messages that are received over the HTTP protocol. If you configure custom network channels for this server, each channel can override this maximum message size.</p>  <p>This maximum message size helps guard against a denial of service attack in which a caller attempts to force the server to allocate more memory than is available thereby keeping the server from responding quickly to other requests.</p>  <p>A value of -1 causes the HTTP protocol to use the maximums that are specified elsewhere along the order of precedence.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("KernelMBean#getMaxMessageSize"), BeanInfoHelper.encodeEntities("NetworkAccessPointMBean#getMaxMessageSize")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("deprecated", "8.1.0.0 use {@link NetworkAccessPointMBean#getMaxMessageSize()} ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxIIOPMessageSize")) {
         getterName = "getMaxIIOPMessageSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxIIOPMessageSize";
         }

         currentResult = new PropertyDescriptor("MaxIIOPMessageSize", KernelMBean.class, getterName, setterName);
         descriptors.put("MaxIIOPMessageSize", currentResult);
         currentResult.setValue("description", "<p>The maximum number of bytes allowed in messages that are received over the IIOP protocol. If you configure custom network channels for this server, each channel can override this maximum message size.</p>  <p>This maximum message size helps guard against a denial of service attack in which a caller attempts to force the server to allocate more memory than is available thereby keeping the server from responding quickly to other requests.</p>  <p>A value of -1 causes the IIOP protocol to use the maximums that are specified elsewhere along the order of precedence.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("KernelMBean#getMaxMessageSize"), BeanInfoHelper.encodeEntities("NetworkAccessPointMBean#getMaxMessageSize")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("deprecated", "8.1.0.0 use {@link NetworkAccessPointMBean#getMaxMessageSize()} ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxMessageSize")) {
         getterName = "getMaxMessageSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxMessageSize";
         }

         currentResult = new PropertyDescriptor("MaxMessageSize", KernelMBean.class, getterName, setterName);
         descriptors.put("MaxMessageSize", currentResult);
         currentResult.setValue("description", "<p>The maximum number of bytes allowed in messages that are received over all supported protocols, unless overridden by a protocol-specific setting or a custom channel setting.</p>  <p>The order of precedence for setting message size maximums is as follows:</p>  <ol> <li> <p>A channel-wide maximum in a custom network channel.</p> </li>  <li> <p>A protocol-specific setting in the default network channel.</p> <p>See</p> <ul> <li>{@link #getMaxCOMMessageSize() getMaxCOMMessageSize}</li> <li>{@link #getMaxHTTPMessageSize() getMaxHTTPMessageSize}</li> <li>{@link #getMaxIIOPMessageSize() getMaxIIOPessageSize}</li> <li>{@link #getMaxT3MessageSize() getMaxT3MessageSize}</li> </ul> </li>  <li> <p>The message maximum in this attribute.</p> </li> </ol>  <p>This maximum message size helps guard against a denial of service attack in which a caller attempts to force the server to allocate more memory than is available thereby keeping the server from responding quickly to other requests.</p> <p>A client can set this value using the <code>-Dweblogic.MaxMessageSize</code> property.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.NetworkAccessPointMBean")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(10000000));
         currentResult.setValue("secureValue", new Integer(10000000));
         currentResult.setValue("legalMax", new Integer(2000000000));
         currentResult.setValue("legalMin", new Integer(4096));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxOpenSockCount")) {
         getterName = "getMaxOpenSockCount";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxOpenSockCount";
         }

         currentResult = new PropertyDescriptor("MaxOpenSockCount", KernelMBean.class, getterName, setterName);
         descriptors.put("MaxOpenSockCount", currentResult);
         currentResult.setValue("description", "<p>The maximum number of open sockets allowed in server at a given point of time.</p>  <p>When the maximum threshold is reached, the server stops accepting new requests until the number of sockets drops below the threshold.</p>  <p>A value less than <code>0</code> indicates an unlimited size.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(-1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxT3MessageSize")) {
         getterName = "getMaxT3MessageSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxT3MessageSize";
         }

         currentResult = new PropertyDescriptor("MaxT3MessageSize", KernelMBean.class, getterName, setterName);
         descriptors.put("MaxT3MessageSize", currentResult);
         currentResult.setValue("description", "<p>The maximum number of bytes allowed in messages that are received over the T3 protocol. If you configure custom network channels for this server, each channel can override this maximum message size.</p>  <p>This maximum message size helps guard against a denial of service attack in which a caller attempts to force the server to allocate more memory than is available thereby keeping the server from responding quickly to other requests.</p>  <p>A value of -1 causes the T3 protocol to use the maximums that are specified elsewhere along the order of precedence.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("KernelMBean#getMaxMessageSize"), BeanInfoHelper.encodeEntities("NetworkAccessPointMBean#getMaxMessageSize")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("deprecated", "8.1.0.0 use {@link NetworkAccessPointMBean#getMaxMessageSize()} ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessagingBridgeThreadPoolSize")) {
         getterName = "getMessagingBridgeThreadPoolSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMessagingBridgeThreadPoolSize";
         }

         currentResult = new PropertyDescriptor("MessagingBridgeThreadPoolSize", KernelMBean.class, getterName, setterName);
         descriptors.put("MessagingBridgeThreadPoolSize", currentResult);
         currentResult.setValue("description", "<p>Returns the size of the messaging bridge execute thread pool.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(5));
         currentResult.setValue("legalMax", new Integer(65534));
         currentResult.setValue("legalMin", new Integer(-1));
         currentResult.setValue("deprecated", "9.0.0.0 replaced by a Work Manager named weblogic.jms.MessagingBridge ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MuxerClass")) {
         getterName = "getMuxerClass";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMuxerClass";
         }

         currentResult = new PropertyDescriptor("MuxerClass", KernelMBean.class, getterName, setterName);
         descriptors.put("MuxerClass", currentResult);
         currentResult.setValue("description", "<p>Non-blocking IO is enabled by default on the server side.</p> <p>The default value is <code>weblogic.socket.NIOSocketMuxer</code>. </p> <p>The Certicom SSL implementation is not supported with NIOSocketMuxer. If you need to secure internet communication, Oracle recommends enabling JSSE (Java Secure Socket Extension).</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", KernelMBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>The user-specified name of this MBean instance.</p>  <p>This name is included as one of the key properties in the MBean's <code>javax.management.ObjectName</code>:</p>  <p><code>Name=<i>user-specified-name</i></code></p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("key", Boolean.TRUE);
      }

      if (!descriptors.containsKey("PeriodLength")) {
         getterName = "getPeriodLength";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPeriodLength";
         }

         currentResult = new PropertyDescriptor("PeriodLength", KernelMBean.class, getterName, setterName);
         descriptors.put("PeriodLength", currentResult);
         currentResult.setValue("description", "<p>The time interval in milliseconds of the heartbeat period. A value of 0 indicates that heartbeats are turned off.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(60000));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PrintStackTraceInProduction")) {
         getterName = "getPrintStackTraceInProduction";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPrintStackTraceInProduction";
         }

         currentResult = new PropertyDescriptor("PrintStackTraceInProduction", KernelMBean.class, getterName, setterName);
         descriptors.put("PrintStackTraceInProduction", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the server message log includes exception stack trace that are raised in remote systems.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SSL")) {
         getterName = "getSSL";
         setterName = null;
         currentResult = new PropertyDescriptor("SSL", KernelMBean.class, getterName, setterName);
         descriptors.put("SSL", currentResult);
         currentResult.setValue("description", "<p>Returns the kernel's SSL configuration. An SSL MBean is always linked to a particular kernel and cannot be changed. Individual attributes on the SSL MBean may be changed, as documented, but the MBean itself may not.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SelfTuningThreadPoolSizeMax")) {
         getterName = "getSelfTuningThreadPoolSizeMax";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSelfTuningThreadPoolSizeMax";
         }

         currentResult = new PropertyDescriptor("SelfTuningThreadPoolSizeMax", KernelMBean.class, getterName, setterName);
         descriptors.put("SelfTuningThreadPoolSizeMax", currentResult);
         currentResult.setValue("description", "<p>Sets the maximum thread pool size of the self-tuning thread pool.</p> <p> The self-tuning thread pool starts with the default size of 1. It grows and shrinks automatically as required. Setting this attribute changes the default maximum pool size. The active thread count will never increase beyond this value. This value defines the maximum number of threads permitted in the server. Note that the server will add threads only if it improves throughput. Measurements are taken every two seconds and the decision to increase or decrease the thread count is based on the current throughput measurement versus past values. </p> <p> This attribute is used only when {@link #setUse81StyleExecuteQueues} is turned off which is the default. </p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(400));
         currentResult.setValue("legalMax", new Integer(65534));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SelfTuningThreadPoolSizeMin")) {
         getterName = "getSelfTuningThreadPoolSizeMin";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSelfTuningThreadPoolSizeMin";
         }

         currentResult = new PropertyDescriptor("SelfTuningThreadPoolSizeMin", KernelMBean.class, getterName, setterName);
         descriptors.put("SelfTuningThreadPoolSizeMin", currentResult);
         currentResult.setValue("description", "<p>Get the minimum thread pool size of the self-tuning thread pool.</p> <p> The self-tuning thread pool starts with the default size of 1. It grows and shrinks automatically as required. Setting this attribute changes the default minimum pool size. The thread count will never shrink below this value. It can add threads to improve throughput but will never decrease below the set minimum. </p> <p> This attribute is used only when {@link #setUse81StyleExecuteQueues} is turned off which is the default. </p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(1));
         currentResult.setValue("legalMax", new Integer(65534));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SocketReaders")) {
         getterName = "getSocketReaders";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSocketReaders";
         }

         currentResult = new PropertyDescriptor("SocketReaders", KernelMBean.class, getterName, setterName);
         descriptors.put("SocketReaders", currentResult);
         currentResult.setValue("description", "<p>The number of socket reader threads.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("legalMax", new Integer(65534));
         currentResult.setValue("legalMin", new Integer(-1));
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("StdoutFormat")) {
         getterName = "getStdoutFormat";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStdoutFormat";
         }

         currentResult = new PropertyDescriptor("StdoutFormat", KernelMBean.class, getterName, setterName);
         descriptors.put("StdoutFormat", currentResult);
         currentResult.setValue("description", "<p>The output format to use when logging to the console.</p> ");
         setPropertyDescriptorDefault(currentResult, "standard");
         currentResult.setValue("legalValues", new Object[]{"standard", "noid"});
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("StdoutSeverityLevel")) {
         getterName = "getStdoutSeverityLevel";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStdoutSeverityLevel";
         }

         currentResult = new PropertyDescriptor("StdoutSeverityLevel", KernelMBean.class, getterName, setterName);
         descriptors.put("StdoutSeverityLevel", currentResult);
         currentResult.setValue("description", "<p>The minimum severity of a message that the server sends to standard out. (Requires you to enable sending messages to standard out.)</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#isStdoutEnabled")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(32));
         currentResult.setValue("secureValue", new Integer(32));
         currentResult.setValue("legalValues", new Object[]{new Integer(256), new Integer(128), new Integer(64), new Integer(16), new Integer(8), new Integer(32), new Integer(4), new Integer(2), new Integer(1), new Integer(0)});
         currentResult.setValue("deprecated", "9.0.0.0 Replaced by LogMBean.StdoutSeverity.  For backward compatibility the changes to this attribute will be  propagated to the LogMBean. ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (!descriptors.containsKey("StuckThreadMaxTime")) {
         getterName = "getStuckThreadMaxTime";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStuckThreadMaxTime";
         }

         currentResult = new PropertyDescriptor("StuckThreadMaxTime", KernelMBean.class, getterName, setterName);
         descriptors.put("StuckThreadMaxTime", currentResult);
         currentResult.setValue("description", "<p>The number of seconds that a thread must be continually working before this server considers the thread stuck.</p>  <p>For example, if you set this to 600 seconds, WebLogic Server considers a thread to be \"stuck\" after 600 seconds of continuous use.</p>  <p>In WebLogic Server 9.x and later, it is recommended that you use the ServerFailureTriggerMBean in the OverloadProtectionMBean. The ServerFailureTriggerMBean transitions the server to a FAILED state after the specified number of stuck threads are detected. The OverloadProtectionMBean has options to suspend or shutdown a failed server. </p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(600));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("deprecated", "9.0.0.0 replaced by ServerFailureTriggerMBean.getMaxStuckThreadTime() ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("StuckThreadTimerInterval")) {
         getterName = "getStuckThreadTimerInterval";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStuckThreadTimerInterval";
         }

         currentResult = new PropertyDescriptor("StuckThreadTimerInterval", KernelMBean.class, getterName, setterName);
         descriptors.put("StuckThreadTimerInterval", currentResult);
         currentResult.setValue("description", "<p>The number of seconds after which WebLogic Server periodically scans threads to see if they have been continually working for the configured maximum length of time.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getStuckThreadMaxTime")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(60));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Tags")) {
         getterName = "getTags";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTags";
         }

         currentResult = new PropertyDescriptor("Tags", KernelMBean.class, getterName, setterName);
         descriptors.put("Tags", currentResult);
         currentResult.setValue("description", "<p>Return all tags on this Configuration MBean</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("ThreadPoolPercentSocketReaders")) {
         getterName = "getThreadPoolPercentSocketReaders";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setThreadPoolPercentSocketReaders";
         }

         currentResult = new PropertyDescriptor("ThreadPoolPercentSocketReaders", KernelMBean.class, getterName, setterName);
         descriptors.put("ThreadPoolPercentSocketReaders", currentResult);
         currentResult.setValue("description", "<p>The percentage of execute threads from the default queue that can be used as socket readers.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(33));
         currentResult.setValue("legalMax", new Integer(99));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TimedOutRefIsolationTime")) {
         getterName = "getTimedOutRefIsolationTime";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTimedOutRefIsolationTime";
         }

         currentResult = new PropertyDescriptor("TimedOutRefIsolationTime", KernelMBean.class, getterName, setterName);
         descriptors.put("TimedOutRefIsolationTime", currentResult);
         currentResult.setValue("description", "<p>The amount of time in milliseconds a reference should not be used after a request timed out. The clusterable reference avoids using this remote reference for the period specified.</p> ");
         setPropertyDescriptorDefault(currentResult, new Long(0L));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Use81StyleExecuteQueues")) {
         getterName = "getUse81StyleExecuteQueues";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUse81StyleExecuteQueues";
         }

         currentResult = new PropertyDescriptor("Use81StyleExecuteQueues", KernelMBean.class, getterName, setterName);
         descriptors.put("Use81StyleExecuteQueues", currentResult);
         currentResult.setValue("description", "<p>Backward compatibility mode to switch to 8.1 execute queues instead of WorkManagers. Each of the WorkManagers is converted to an individual execute queue. Setting this attribute requires a server restart. </p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getUse81StyleExecuteQueues")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.4.0", (String)null, this.targetVersion) && !descriptors.containsKey("AddWorkManagerThreadsByCpuCount")) {
         getterName = "isAddWorkManagerThreadsByCpuCount";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAddWorkManagerThreadsByCpuCount";
         }

         currentResult = new PropertyDescriptor("AddWorkManagerThreadsByCpuCount", KernelMBean.class, getterName, setterName);
         descriptors.put("AddWorkManagerThreadsByCpuCount", currentResult);
         currentResult.setValue("description", "Enables increased efficiency of the self-tuning thread pool by aligning it with the Exalogic processor architecture threading capabilities. Use only when configuring a WebLogic domain for Oracle Exalogic. <ul> <li>Enabling this attribute increases efficiency during I/O in environments with high network throughput.</li> <li>Disabling this attribute allows the thread pool to self-tune to match the demand of the workload at the expense of longer ramp up time.</li> </ul> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.4.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.1.3.0", (String)null, this.targetVersion) && !descriptors.containsKey("AllowShrinkingPriorityRequestQueue")) {
         getterName = "isAllowShrinkingPriorityRequestQueue";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAllowShrinkingPriorityRequestQueue";
         }

         currentResult = new PropertyDescriptor("AllowShrinkingPriorityRequestQueue", KernelMBean.class, getterName, setterName);
         descriptors.put("AllowShrinkingPriorityRequestQueue", currentResult);
         currentResult.setValue("description", "<p>Specifies whether self-tuning should allow shrinking of its priority based queue for pending requests after it has grown in size due to busy workload, and whether it should try to purge work requests that have already been picked up for processing due to minimum threads constraints before growing the queue. Setting this to true would help reduce memory footprint after the busy work period has ended, at the expense of the overhead of growing the request queue again at the next period of busy workload. It would also help to reduce the amount of memory allocated for the request queue, at the expense of the overhead required in purging already processed work requests from the queue.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.1.3.0");
      }

      if (!descriptors.containsKey("DynamicallyCreated")) {
         getterName = "isDynamicallyCreated";
         setterName = null;
         currentResult = new PropertyDescriptor("DynamicallyCreated", KernelMBean.class, getterName, setterName);
         descriptors.put("DynamicallyCreated", currentResult);
         currentResult.setValue("description", "<p>Return whether the MBean was created dynamically or is persisted to config.xml</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("transient", Boolean.TRUE);
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("EagerThreadLocalCleanup")) {
         getterName = "isEagerThreadLocalCleanup";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEagerThreadLocalCleanup";
         }

         currentResult = new PropertyDescriptor("EagerThreadLocalCleanup", KernelMBean.class, getterName, setterName);
         descriptors.put("EagerThreadLocalCleanup", currentResult);
         currentResult.setValue("description", "Specifies whether to clean up all ThreadLocals storage from self-tuning thread pool threads after they have finished processing each work request. By default, the self-tuning thread pool only cleans up ThreadLocal storage in its threads after an application is undeployed to avoid ClassLoader memory leak. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("GatheredWritesEnabled")) {
         getterName = "isGatheredWritesEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setGatheredWritesEnabled";
         }

         currentResult = new PropertyDescriptor("GatheredWritesEnabled", KernelMBean.class, getterName, setterName);
         descriptors.put("GatheredWritesEnabled", currentResult);
         currentResult.setValue("description", "Enables gathered writes over NIO socket channels. Enabling this attribute increases efficiency during I/O in environments with high network throughput and should be used only when configuring a WebLogic domain for Oracle Exalogic. ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InstrumentStackTraceEnabled")) {
         getterName = "isInstrumentStackTraceEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setInstrumentStackTraceEnabled";
         }

         currentResult = new PropertyDescriptor("InstrumentStackTraceEnabled", KernelMBean.class, getterName, setterName);
         descriptors.put("InstrumentStackTraceEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the server returns stack traces for RMI calls that generate exceptions.</p>  <p>With RMI stack tracking enabled, if a client issues an RMI call to a server subsystem or to a module running within the server, and if the subsystem or module generates an exception that includes a stack trace, the server will return the exception as well as the stack trace. With it disabled, the server will return the exception without the stack trace details.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("secureValue", new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion) && !descriptors.containsKey("IsolatePartitionThreadLocals")) {
         getterName = "isIsolatePartitionThreadLocals";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIsolatePartitionThreadLocals";
         }

         currentResult = new PropertyDescriptor("IsolatePartitionThreadLocals", KernelMBean.class, getterName, setterName);
         descriptors.put("IsolatePartitionThreadLocals", currentResult);
         currentResult.setValue("description", "Specified whether the server needs to ensure that threadlocals storage from self-tuning thread pool threads must be cleaned up before the threads are used for processing work requests from a different partition. This helps ensure that no threadlocals from a different partition can be retrieved from work requests while being processed by a self-tuning thread pool thread.  The different between this attribute and the eagerThreadLocalCleanup attribute is that threadlocals storage will not be cleared if the previous work request processed by the self-tuning thread pool thread was for the same partition, whereas with eagerThreadLocalCleanup set, the threadlocals storage will also be cleared in this case. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.3.0");
         currentResult.setValue("excludeFromRest", "${excludeFromRest}");
      }

      if (!descriptors.containsKey("LogRemoteExceptionsEnabled")) {
         getterName = "isLogRemoteExceptionsEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLogRemoteExceptionsEnabled";
         }

         currentResult = new PropertyDescriptor("LogRemoteExceptionsEnabled", KernelMBean.class, getterName, setterName);
         descriptors.put("LogRemoteExceptionsEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the server message log includes exceptions that are raised in remote systems.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(true));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NativeIOEnabled")) {
         getterName = "isNativeIOEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNativeIOEnabled";
         }

         currentResult = new PropertyDescriptor("NativeIOEnabled", KernelMBean.class, getterName, setterName);
         descriptors.put("NativeIOEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether native I/O is enabled for the server.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getSocketReaderTimeoutMaxMillis")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OutboundEnabled")) {
         getterName = "isOutboundEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOutboundEnabled";
         }

         currentResult = new PropertyDescriptor("OutboundEnabled", KernelMBean.class, getterName, setterName);
         descriptors.put("OutboundEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether new server-to-server connections may consider the default server channel when initiating a connection. This is only relevant if the connection needs to be bound to the default listen address. This will only work for binary protocols that support both outbound and inbound traffic.</p>  <p>When this feature is not enabled, connections are initiated using a local address selected by the underlying hardware. For the default channel this is usually what is wanted for IP-routing to be effective. Note that since the default is false, other outbound channels will be considered in preference to the default channel.</p>  <p>Default administration channels, created when the domain-wide administration port is turned on, are always considered and bound when initiating an administrative connection. To allow IP-routing for administration traffic create custom admin with {@link NetworkAccessPointMBean#isOutboundEnabled isOutboundEnabled} set to false instead of enabling the domain-wide ADMIN port.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("NetworkAccessPointMBean#isOutboundEnabled")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OutboundPrivateKeyEnabled")) {
         getterName = "isOutboundPrivateKeyEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOutboundPrivateKeyEnabled";
         }

         currentResult = new PropertyDescriptor("OutboundPrivateKeyEnabled", KernelMBean.class, getterName, setterName);
         descriptors.put("OutboundPrivateKeyEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the SSL identity specified by {@link SSLMBean#getServerPrivateKeyAlias() getSSLMBean#ServerPrivateKeyAlias} for this server should be used for outbound SSL connections on the default server channel. In normal circumstances the outbound identity is determined by the caller's environment.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#isOutboundEnabled")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ReverseDNSAllowed")) {
         getterName = "isReverseDNSAllowed";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setReverseDNSAllowed";
         }

         currentResult = new PropertyDescriptor("ReverseDNSAllowed", KernelMBean.class, getterName, setterName);
         descriptors.put("ReverseDNSAllowed", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the kernel is allowed to perform reverse DNS lookups.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ScatteredReadsEnabled")) {
         getterName = "isScatteredReadsEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setScatteredReadsEnabled";
         }

         currentResult = new PropertyDescriptor("ScatteredReadsEnabled", KernelMBean.class, getterName, setterName);
         descriptors.put("ScatteredReadsEnabled", currentResult);
         currentResult.setValue("description", "Enables scattered reads over NIO socket channels. Enabling this attribute increases efficiency during I/O in environments with high network throughput and should be used only when configuring a WebLogic domain for Oracle Exalogic. ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SocketBufferSizeAsChunkSize")) {
         getterName = "isSocketBufferSizeAsChunkSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSocketBufferSizeAsChunkSize";
         }

         currentResult = new PropertyDescriptor("SocketBufferSizeAsChunkSize", KernelMBean.class, getterName, setterName);
         descriptors.put("SocketBufferSizeAsChunkSize", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the server's buffer size for sending or receiving data through a raw socket should be set to 4KB. </p> <p>Otherwise, the server does not impose a limit to the buffer size and defers to the operating system. This option is useful only on some operating systems for improving performance. It should be disabled in most environments.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("StdoutDebugEnabled")) {
         getterName = "isStdoutDebugEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStdoutDebugEnabled";
         }

         currentResult = new PropertyDescriptor("StdoutDebugEnabled", KernelMBean.class, getterName, setterName);
         descriptors.put("StdoutDebugEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the server sends messages of the <code>DEBUG</code> severity to standard out in addition to the log file. (Requires you to enable sending messages to standard out.)</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(false));
         currentResult.setValue("deprecated", "9.0.0.0 replaced by LogMBean.StdoutSeverity For backward compatibility the changes to this attribute will be propagated to the LogMBean. ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("StdoutEnabled")) {
         getterName = "isStdoutEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStdoutEnabled";
         }

         currentResult = new PropertyDescriptor("StdoutEnabled", KernelMBean.class, getterName, setterName);
         descriptors.put("StdoutEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the server sends messages to standard out in addition to the log file.</p>  <p>Other settings configure the minimum severity of a message that the server sends to standard out.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#isStdoutDebugEnabled"), BeanInfoHelper.encodeEntities("#getStdoutSeverityLevel")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("deprecated", "9.0.0.0 replaced by LogMBean.StdoutSeverity, for backward compatibility the changes to this attribute will be propagated to the LogMBean. ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("StdoutLogStack")) {
         getterName = "isStdoutLogStack";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStdoutLogStack";
         }

         currentResult = new PropertyDescriptor("StdoutLogStack", KernelMBean.class, getterName, setterName);
         descriptors.put("StdoutLogStack", currentResult);
         currentResult.setValue("description", "<p>Specifies whether to dump stack traces to the console when included in logged message.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("deprecated", "9.0.0.0 ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.6.0", (String)null, this.targetVersion) && !descriptors.containsKey("UseConcurrentQueueForRequestManager")) {
         getterName = "isUseConcurrentQueueForRequestManager";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUseConcurrentQueueForRequestManager";
         }

         currentResult = new PropertyDescriptor("UseConcurrentQueueForRequestManager", KernelMBean.class, getterName, setterName);
         descriptors.put("UseConcurrentQueueForRequestManager", currentResult);
         currentResult.setValue("description", "<p>Reduces lock contention by using concurrent buffer queue to park incoming requests. Enabling this attribute increases throughput as requests are scheduled without acquiring any locks. </p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.6.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.1.2.0", (String)null, this.targetVersion) && !descriptors.containsKey("UseDetailedThreadName")) {
         getterName = "isUseDetailedThreadName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUseDetailedThreadName";
         }

         currentResult = new PropertyDescriptor("UseDetailedThreadName", KernelMBean.class, getterName, setterName);
         descriptors.put("UseDetailedThreadName", currentResult);
         currentResult.setValue("description", "Specifies whether to includes detailed information in self-tuning thread pool thread names. Setting this flag as true would include additional information, such as the name of the Work Manager for which the execute thread is executing the task, but at the expense of overhead in keeping the thread name updated with such information. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.1.2.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.1.3.0", (String)null, this.targetVersion) && !descriptors.containsKey("UseEnhancedIncrementAdvisor")) {
         getterName = "isUseEnhancedIncrementAdvisor";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUseEnhancedIncrementAdvisor";
         }

         currentResult = new PropertyDescriptor("UseEnhancedIncrementAdvisor", KernelMBean.class, getterName, setterName);
         descriptors.put("UseEnhancedIncrementAdvisor", currentResult);
         currentResult.setValue("description", "<p>Specifies whether self-tuning should use the enhanced IncrementAdvisor implementation that uses a different algorithm to adjust the size of the self-tuning thread pool.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.1.3.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.1.2.0", (String)null, this.targetVersion) && !descriptors.containsKey("UseEnhancedPriorityQueueForRequestManager")) {
         getterName = "isUseEnhancedPriorityQueueForRequestManager";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUseEnhancedPriorityQueueForRequestManager";
         }

         currentResult = new PropertyDescriptor("UseEnhancedPriorityQueueForRequestManager", KernelMBean.class, getterName, setterName);
         descriptors.put("UseEnhancedPriorityQueueForRequestManager", currentResult);
         currentResult.setValue("description", "<p>Reduces lock contention by using the enhanced ConcurrentCalendarQueue as the priority based queue for pending requests that are waiting for a thread. </p> <p>This attribute should be enabled only in an Oracle Exalogic environment.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.1.2.0");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = KernelMBean.class.getMethod("createExecuteQueue", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "of the new queue ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Create a new execute queue.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ExecuteQueues");
      }

      mth = KernelMBean.class.getMethod("destroyExecuteQueue", ExecuteQueueMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("queue", "to destroy ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Destroy execute queue.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ExecuteQueues");
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      MethodDescriptor currentResult;
      Method mth;
      ParameterDescriptor[] parameterDescriptors;
      String methodKey;
      String[] throwsObjectArray;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = KernelMBean.class.getMethod("addTag", String.class);
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
         mth = KernelMBean.class.getMethod("removeTag", String.class);
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
      Method mth = KernelMBean.class.getMethod("lookupExecuteQueue", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "ExecuteQueues");
      }

   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = KernelMBean.class.getMethod("freezeCurrentValue", String.class);
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

      mth = KernelMBean.class.getMethod("restoreDefaultValue", String.class);
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
