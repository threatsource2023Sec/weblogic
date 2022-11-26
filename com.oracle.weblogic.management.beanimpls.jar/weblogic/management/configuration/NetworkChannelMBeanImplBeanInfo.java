package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class NetworkChannelMBeanImplBeanInfo extends DeploymentMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = NetworkChannelMBean.class;

   public NetworkChannelMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public NetworkChannelMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.NetworkChannelMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("deprecated", "7.0.0.0 use {@link NetworkAccessPointMBean} ");
      String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("ServerMBean#getListenPort"), BeanInfoHelper.encodeEntities("ServerMBean#getAdministrationPort"), BeanInfoHelper.encodeEntities("SSLMBean#getListenPort"), BeanInfoHelper.encodeEntities("ServerMBean#getSSL"), BeanInfoHelper.encodeEntities("NetworkAccessPointMBean"), BeanInfoHelper.encodeEntities("ServerMBean#getNetworkAccessPoints")};
      beanDescriptor.setValue("see", seeObjectArray);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("This MBean defines a network channel.   A network channel is used to configure additional ports for a server beyond its default listen ports.   Network channels do not support IIOP. <p> A network channel can be targeted at multiple clusters and servers. Targeting a channel at a cluster targets it at every server that is a member of that cluster. A server can support multiple channels.</p> A server can fine-tune its network channel settings by using a NetworkAccessPointMBean.  The NetworkAccessPointMBean also servers to set the listen address and external DNS name that a server uses for a particular channel. <p> A server serves up to three default listen ports: ServerMBean ListenPort, ServerMBean AdministrationPort, and SSLMBean ListenPort. The default listen ports form implicit channel(s) of weight 50.</p> <p> A network channel also defines the creation of server-to-server connections. If a server is initiating a new connection to another server, the highest weighted common (same named) channel that supports the desired protocol is used to determine which port to contact.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.NetworkChannelMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      String[] seeObjectArray;
      if (!descriptors.containsKey("AcceptBacklog")) {
         getterName = "getAcceptBacklog";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAcceptBacklog";
         }

         currentResult = new PropertyDescriptor("AcceptBacklog", NetworkChannelMBean.class, getterName, setterName);
         descriptors.put("AcceptBacklog", currentResult);
         currentResult.setValue("description", "<p>Allowed backlog of connection requests on the listen port(s). Individual servers may override this value using a NetworkAccessPointMBean. Setting the backlog to 0 may prevent accepting any incoming connection on some of the OS.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.NetworkAccessPointMBean#getAcceptBacklog"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.ServerMBean#getAcceptBacklog")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(50));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ChannelWeight")) {
         getterName = "getChannelWeight";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setChannelWeight";
         }

         currentResult = new PropertyDescriptor("ChannelWeight", NetworkChannelMBean.class, getterName, setterName);
         descriptors.put("ChannelWeight", currentResult);
         currentResult.setValue("description", "<p>A weight to give this channel when creating server-to-server connections.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.NetworkChannelMBean")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(50));
         currentResult.setValue("legalMax", new Integer(100));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ClusterAddress")) {
         getterName = "getClusterAddress";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setClusterAddress";
         }

         currentResult = new PropertyDescriptor("ClusterAddress", NetworkChannelMBean.class, getterName, setterName);
         descriptors.put("ClusterAddress", currentResult);
         currentResult.setValue("description", "<p>This channel's cluster address. If this is not set, the cluster address from the cluster configuration is used in its place.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.ClusterMBean#getClusterAddress")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CompleteCOMMessageTimeout")) {
         getterName = "getCompleteCOMMessageTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCompleteCOMMessageTimeout";
         }

         currentResult = new PropertyDescriptor("CompleteCOMMessageTimeout", NetworkChannelMBean.class, getterName, setterName);
         descriptors.put("CompleteCOMMessageTimeout", currentResult);
         currentResult.setValue("description", "<p>The maximum number of seconds spent waiting for a complete COM message to be received. This attribute helps guard against denial of service attacks in which a caller indicates that they will be sending a message of a certain size which they never finish sending. Individual servers may override this value using a NetworkAccessPointMBean.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.KernelMBean#getCompleteCOMMessageTimeout"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.NetworkAccessPointMBean#getCompleteCOMMessageTimeout")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(60));
         currentResult.setValue("secureValue", new Integer(60));
         currentResult.setValue("legalMax", new Integer(480));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("deprecated", "12.2.1.2.0 ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CompleteHTTPMessageTimeout")) {
         getterName = "getCompleteHTTPMessageTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCompleteHTTPMessageTimeout";
         }

         currentResult = new PropertyDescriptor("CompleteHTTPMessageTimeout", NetworkChannelMBean.class, getterName, setterName);
         descriptors.put("CompleteHTTPMessageTimeout", currentResult);
         currentResult.setValue("description", "<p>The maximum number of seconds spent waiting for a complete HTTP message to be received. This attribute helps guard against denial of service attacks in which a caller indicates that they will be sending a message of a certain size which they never finish sending. Individual servers may override this value using a NetworkAccessPointMBean.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.KernelMBean#getCompleteHTTPMessageTimeout"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.NetworkAccessPointMBean#getCompleteHTTPMessageTimeout")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(60));
         currentResult.setValue("secureValue", new Integer(60));
         currentResult.setValue("legalMax", new Integer(480));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CompleteT3MessageTimeout")) {
         getterName = "getCompleteT3MessageTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCompleteT3MessageTimeout";
         }

         currentResult = new PropertyDescriptor("CompleteT3MessageTimeout", NetworkChannelMBean.class, getterName, setterName);
         descriptors.put("CompleteT3MessageTimeout", currentResult);
         currentResult.setValue("description", "<p>The maximum number of seconds spent waiting for a complete T3 message to be received. This attribute helps guard against denial of service attacks in which a caller indicates that they will be sending a message of a certain size which they never finish sending. Individual servers may override this value using a NetworkAccessPointMBean.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.KernelMBean#getCompleteT3MessageTimeout"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.NetworkAccessPointMBean#getCompleteT3MessageTimeout")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(60));
         currentResult.setValue("secureValue", new Integer(60));
         currentResult.setValue("legalMax", new Integer(480));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultIIOPPasswordEncrypted")) {
         getterName = "getDefaultIIOPPasswordEncrypted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultIIOPPasswordEncrypted";
         }

         currentResult = new PropertyDescriptor("DefaultIIOPPasswordEncrypted", NetworkChannelMBean.class, getterName, setterName);
         descriptors.put("DefaultIIOPPasswordEncrypted", currentResult);
         currentResult.setValue("description", "<p>The encrypted password for the default IIOP user.</p>  <p>To set this attribute, use <code>weblogic.management.EncryptionHelper.encrypt()</code> to encrypt the value. Then set this attribute to the output of the encrypt() method.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.EncryptionHelper")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Description")) {
         getterName = "getDescription";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDescription";
         }

         currentResult = new PropertyDescriptor("Description", NetworkChannelMBean.class, getterName, setterName);
         descriptors.put("Description", currentResult);
         currentResult.setValue("description", "<p>Optional short description of this channel for console display purposes. For long descriptions, use the \"Notes\" field.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ListenPort")) {
         getterName = "getListenPort";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setListenPort";
         }

         currentResult = new PropertyDescriptor("ListenPort", NetworkChannelMBean.class, getterName, setterName);
         descriptors.put("ListenPort", currentResult);
         currentResult.setValue("description", "<p>The plaintext (non-SSL) listen port for the channel. Individual servers may override this value, but may not enable the port if disabled here and may not disable the port if enabled here. Individual servers may override this value using a NetworkAccessPointMBean.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#isListenPortEnabled"), BeanInfoHelper.encodeEntities("#getSSLListenPort"), BeanInfoHelper.encodeEntities("NetworkAccessPointMBean#getListenPort"), BeanInfoHelper.encodeEntities("NetworkAccessPointMBean#getListenAddress"), BeanInfoHelper.encodeEntities("NetworkAccessPointMBean#getExternalDNSName"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.ServerMBean#getListenPort")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(8001));
         currentResult.setValue("legalMax", new Integer(65535));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LoginTimeoutMillis")) {
         getterName = "getLoginTimeoutMillis";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLoginTimeoutMillis";
         }

         currentResult = new PropertyDescriptor("LoginTimeoutMillis", NetworkChannelMBean.class, getterName, setterName);
         descriptors.put("LoginTimeoutMillis", currentResult);
         currentResult.setValue("description", "<p>The login timeout for the server, in milliseconds. This value must be equal to or greater than 0. Individual servers may override this value using a NetworkAccessPointMBean.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.NetworkAccessPointMBean#getLoginTimeoutMillis"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.ServerMBean#getLoginTimeoutMillis")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(5000));
         currentResult.setValue("secureValue", new Integer(5000));
         currentResult.setValue("legalMax", new Integer(100000));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LoginTimeoutMillisSSL")) {
         getterName = "getLoginTimeoutMillisSSL";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLoginTimeoutMillisSSL";
         }

         currentResult = new PropertyDescriptor("LoginTimeoutMillisSSL", NetworkChannelMBean.class, getterName, setterName);
         descriptors.put("LoginTimeoutMillisSSL", currentResult);
         currentResult.setValue("description", "<p>Duration allowed for an SSL login sequence. If the duration is exceeded, the login is timed out. 0 to disable. Individual servers may override this value using a NetworkAccessPointMBean.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.NetworkAccessPointMBean#getLoginTimeoutMillisSSL"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.SSLMBean#getLoginTimeoutMillis")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(25000));
         currentResult.setValue("secureValue", new Integer(25000));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxCOMMessageSize")) {
         getterName = "getMaxCOMMessageSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxCOMMessageSize";
         }

         currentResult = new PropertyDescriptor("MaxCOMMessageSize", NetworkChannelMBean.class, getterName, setterName);
         descriptors.put("MaxCOMMessageSize", currentResult);
         currentResult.setValue("description", "<p>The maximum COM message size allowable in a message header. This attribute attempts to prevent a denial of service attack whereby a caller attempts to force the server to allocate more memory than is available thereby keeping the server from responding quickly to other requests. Individual servers may override this value using a NetworkAccessPointMBean.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.NetworkAccessPointMBean#getMaxMessageSize"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.KernelMBean#getMaxCOMMessageSize")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(10000000));
         currentResult.setValue("secureValue", new Integer(10000000));
         currentResult.setValue("legalMax", new Integer(2000000000));
         currentResult.setValue("legalMin", new Integer(4096));
         currentResult.setValue("deprecated", "12.2.1.2.0 ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxHTTPMessageSize")) {
         getterName = "getMaxHTTPMessageSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxHTTPMessageSize";
         }

         currentResult = new PropertyDescriptor("MaxHTTPMessageSize", NetworkChannelMBean.class, getterName, setterName);
         descriptors.put("MaxHTTPMessageSize", currentResult);
         currentResult.setValue("description", "<p>The maximum HTTP message size allowable in a message header. This attribute attempts to prevent a denial of service attack whereby a caller attempts to force the server to allocate more memory than is available thereby keeping the server from responding quickly to other requests. Individual servers may override this value using a NetworkAccessPointMBean.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.NetworkAccessPointMBean#getMaxMessageSize"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.KernelMBean#getMaxHTTPMessageSize")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(10000000));
         currentResult.setValue("secureValue", new Integer(10000000));
         currentResult.setValue("legalMax", new Integer(2000000000));
         currentResult.setValue("legalMin", new Integer(4096));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxT3MessageSize")) {
         getterName = "getMaxT3MessageSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxT3MessageSize";
         }

         currentResult = new PropertyDescriptor("MaxT3MessageSize", NetworkChannelMBean.class, getterName, setterName);
         descriptors.put("MaxT3MessageSize", currentResult);
         currentResult.setValue("description", "<p>The maximum T3 message size allowable in a message header. This attribute attempts to prevent a denial of service attack whereby a caller attempts to force the server to allocate more memory than is available thereby keeping the server from responding quickly to other requests. Individual servers may override this value using a NetworkAccessPointMBean.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.NetworkAccessPointMBean#getMaxMessageSize"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.KernelMBean#getMaxT3MessageSize")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(10000000));
         currentResult.setValue("secureValue", new Integer(10000000));
         currentResult.setValue("legalMax", new Integer(2000000000));
         currentResult.setValue("legalMin", new Integer(4096));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", NetworkChannelMBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>The name of the channel. The name must not start with \".WL\".</p> ");
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SSLListenPort")) {
         getterName = "getSSLListenPort";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSSLListenPort";
         }

         currentResult = new PropertyDescriptor("SSLListenPort", NetworkChannelMBean.class, getterName, setterName);
         descriptors.put("SSLListenPort", currentResult);
         currentResult.setValue("description", "<p>The SSL listen port for the channel. Individual server's may override this value, but may not enable the port if disabled here and may not disable the port if enabled here. SSL must be configured and enabled for this port to work. Individual servers may override this value using a NetworkAccessPointMBean.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getSSLListenPort"), BeanInfoHelper.encodeEntities("#isSSLListenPortEnabled"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.SSLMBean#getListenPort"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.KernelMBean#getSSL"), BeanInfoHelper.encodeEntities("NetworkAccessPointMBean#getSSLListenPort"), BeanInfoHelper.encodeEntities("NetworkAccessPointMBean#getListenAddress")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(8002));
         currentResult.setValue("legalMax", new Integer(65535));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TunnelingClientPingSecs")) {
         getterName = "getTunnelingClientPingSecs";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTunnelingClientPingSecs";
         }

         currentResult = new PropertyDescriptor("TunnelingClientPingSecs", NetworkChannelMBean.class, getterName, setterName);
         descriptors.put("TunnelingClientPingSecs", currentResult);
         currentResult.setValue("description", "<p>Interval (in seconds) at which to ping an http-tunneled client to see if its still alive. Individual servers may override this value using a NetworkAccessPointMBean.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.ServerMBean#getTunnelingClientPingSecs"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.NetworkAccessPointMBean#getTunnelingClientPingSecs")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(45));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TunnelingClientTimeoutSecs")) {
         getterName = "getTunnelingClientTimeoutSecs";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTunnelingClientTimeoutSecs";
         }

         currentResult = new PropertyDescriptor("TunnelingClientTimeoutSecs", NetworkChannelMBean.class, getterName, setterName);
         descriptors.put("TunnelingClientTimeoutSecs", currentResult);
         currentResult.setValue("description", "<p>Duration (in seconds) after which a missing http-tunneled client is considered dead. Individual servers may override this value using a NetworkAccessPointMBean.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.ServerMBean#getTunnelingClientTimeoutSecs"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.NetworkAccessPointMBean#getTunnelingClientTimeoutSecs")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(40));
         currentResult.setValue("secureValue", new Integer(40));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BoundOutgoingEnabled")) {
         getterName = "isBoundOutgoingEnabled";
         setterName = null;
         currentResult = new PropertyDescriptor("BoundOutgoingEnabled", NetworkChannelMBean.class, getterName, setterName);
         descriptors.put("BoundOutgoingEnabled", currentResult);
         currentResult.setValue("description", "<p>Bind new outgoing server side T3 or T3S connections to the server channel's listen address. Other protocols ignore this field. This field is ignored for connections initiated via URLs, it takes effect if and only if the connection was initiated by accessing a remote reference (such as an EJB or RMI stub.)</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("COMEnabled")) {
         getterName = "isCOMEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCOMEnabled";
         }

         currentResult = new PropertyDescriptor("COMEnabled", NetworkChannelMBean.class, getterName, setterName);
         descriptors.put("COMEnabled", currentResult);
         currentResult.setValue("description", "<p>Indicates whether plaintext (non-SSL) COM traffic is enabled.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.ServerMBean#isCOMEnabled")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(false));
         currentResult.setValue("deprecated", "12.2.1.2.0 ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HTTPEnabled")) {
         getterName = "isHTTPEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setHTTPEnabled";
         }

         currentResult = new PropertyDescriptor("HTTPEnabled", NetworkChannelMBean.class, getterName, setterName);
         descriptors.put("HTTPEnabled", currentResult);
         currentResult.setValue("description", "<p>Whether or not plaintext (non-SSL) HTTP traffic is enabled.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.ServerMBean#isHttpdEnabled"), BeanInfoHelper.encodeEntities("#isHTTPSEnabled"), BeanInfoHelper.encodeEntities("#isTunnelingEnabled")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HTTPSEnabled")) {
         getterName = "isHTTPSEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setHTTPSEnabled";
         }

         currentResult = new PropertyDescriptor("HTTPSEnabled", NetworkChannelMBean.class, getterName, setterName);
         descriptors.put("HTTPSEnabled", currentResult);
         currentResult.setValue("description", "<p>Whether or not secure (SSL) HTTP traffic is enabled.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.ServerMBean#isHttpdEnabled"), BeanInfoHelper.encodeEntities("#isHTTPEnabled"), BeanInfoHelper.encodeEntities("#isTunnelingEnabled")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ListenPortEnabled")) {
         getterName = "isListenPortEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setListenPortEnabled";
         }

         currentResult = new PropertyDescriptor("ListenPortEnabled", NetworkChannelMBean.class, getterName, setterName);
         descriptors.put("ListenPortEnabled", currentResult);
         currentResult.setValue("description", "<p>Whether or not plaintext port is enabled for the channel.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getListenPort"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.ServerMBean#isListenPortEnabled")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OutgoingEnabled")) {
         getterName = "isOutgoingEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOutgoingEnabled";
         }

         currentResult = new PropertyDescriptor("OutgoingEnabled", NetworkChannelMBean.class, getterName, setterName);
         descriptors.put("OutgoingEnabled", currentResult);
         currentResult.setValue("description", "<p>Whether or not new server-to-server connections may consider this channel when initiating.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.NetworkChannelMBean")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SSLListenPortEnabled")) {
         getterName = "isSSLListenPortEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSSLListenPortEnabled";
         }

         currentResult = new PropertyDescriptor("SSLListenPortEnabled", NetworkChannelMBean.class, getterName, setterName);
         descriptors.put("SSLListenPortEnabled", currentResult);
         currentResult.setValue("description", "<p>Whether or not SSL port is enabled for the channel. SSL must be configured and enabled in addition to this setting for the SSL port to work.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.SSLMBean#isListenPortEnabled"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.KernelMBean#getSSL")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("T3Enabled")) {
         getterName = "isT3Enabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setT3Enabled";
         }

         currentResult = new PropertyDescriptor("T3Enabled", NetworkChannelMBean.class, getterName, setterName);
         descriptors.put("T3Enabled", currentResult);
         currentResult.setValue("description", "<p>Whether or not plaintext (non-SSL) T3 traffic is enabled. Note that it is not possible to disable T3 traffic on the default channel(s).</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.NetworkChannelMBean"), BeanInfoHelper.encodeEntities("#isT3SEnabled")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("T3SEnabled")) {
         getterName = "isT3SEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setT3SEnabled";
         }

         currentResult = new PropertyDescriptor("T3SEnabled", NetworkChannelMBean.class, getterName, setterName);
         descriptors.put("T3SEnabled", currentResult);
         currentResult.setValue("description", "<p>Whether or not secure T3 traffic is enabled. Note that it is not possible to disable T3 traffic on the default channel(s).</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.NetworkChannelMBean"), BeanInfoHelper.encodeEntities("#isT3Enabled")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TunnelingEnabled")) {
         getterName = "isTunnelingEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTunnelingEnabled";
         }

         currentResult = new PropertyDescriptor("TunnelingEnabled", NetworkChannelMBean.class, getterName, setterName);
         descriptors.put("TunnelingEnabled", currentResult);
         currentResult.setValue("description", "<p>Enables tunneling via http.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.ServerMBean#isTunnelingEnabled"), BeanInfoHelper.encodeEntities("#isHTTPEnabled"), BeanInfoHelper.encodeEntities("#isHTTPSEnabled")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(false));
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
