package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class WTCRemoteTuxDomMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WTCRemoteTuxDomMBean.class;

   public WTCRemoteTuxDomMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WTCRemoteTuxDomMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.WTCRemoteTuxDomMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "7.0.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>This interface provides access to the WTC remote Tuxedo Domain configuration attributes. The methods defined herein are applicable for WTC configuration at the WLS domain level.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.WTCRemoteTuxDomMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("AccessPoint")) {
         getterName = "getAccessPoint";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAccessPoint";
         }

         currentResult = new PropertyDescriptor("AccessPoint", WTCRemoteTuxDomMBean.class, getterName, setterName);
         descriptors.put("AccessPoint", currentResult);
         currentResult.setValue("description", "<p>The unique name used to identify this remote Tuxedo access point. This name should be unique for all local and remote Tuxedo access points defined within a WTC Service. This allows you to define unique configurations having the same Access Point ID.</p> ");
         setPropertyDescriptorDefault(currentResult, "myRAP");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AccessPointId")) {
         getterName = "getAccessPointId";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAccessPointId";
         }

         currentResult = new PropertyDescriptor("AccessPointId", WTCRemoteTuxDomMBean.class, getterName, setterName);
         descriptors.put("AccessPointId", currentResult);
         currentResult.setValue("description", "<p>The connection principal name used to identify this remote domain access point when attempting to establish a session connection to local Tuxedo access points. This value must be globally unique among all interconnected Tuxedo and WTC Domains.</p> ");
         setPropertyDescriptorDefault(currentResult, "myRAPId");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AclPolicy")) {
         getterName = "getAclPolicy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAclPolicy";
         }

         currentResult = new PropertyDescriptor("AclPolicy", WTCRemoteTuxDomMBean.class, getterName, setterName);
         descriptors.put("AclPolicy", currentResult);
         currentResult.setValue("description", "<p>The inbound access control list (ACL) policy toward requests from a remote Tuxedo access point.</p>  <p>The allowed values are:</p>  <ul> <li>LOCAL: The local Tuxedo access point modifies the identity of service requests received from a given remote Tuxedo access point to the principal name specified in the local principal name for a given remote Tuxedo access point.</li>  <li>GLOBAL: The local Tuxedo access point passes the service request with no change in identity.</li> </ul>  <p><i>Note:</i> If Interoperate is set to Yes, AclPolicy is ignored.</p> ");
         setPropertyDescriptorDefault(currentResult, "LOCAL");
         currentResult.setValue("legalValues", new Object[]{"GLOBAL", "LOCAL"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AllowAnonymous")) {
         getterName = "getAllowAnonymous";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAllowAnonymous";
         }

         currentResult = new PropertyDescriptor("AllowAnonymous", WTCRemoteTuxDomMBean.class, getterName, setterName);
         descriptors.put("AllowAnonymous", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the anonymous user is allowed to access remote Tuxedo services.</p>  <p><i>Note:</i> If the anonymous user is allowed to access Tuxedo, the default AppKey will be used for <code>TpUsrFile</code> and <code>LDAP</code> AppKey plug-ins. Interaction with the <code>Custom</code> AppKey plug-in depends on the design of the Custom AppKey generator.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AppKey")) {
         getterName = "getAppKey";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAppKey";
         }

         currentResult = new PropertyDescriptor("AppKey", WTCRemoteTuxDomMBean.class, getterName, setterName);
         descriptors.put("AppKey", currentResult);
         currentResult.setValue("description", "<p>Specifies the type of AppKey plug-in used.</p>  <p>The allowed values are:</p>  <ul> <li>TpUsrFile: <code>TpUsrFile</code> is the default plug-in. It uses an imported Tuxedo TPUSR file to provide user security information. Previous releases of WebLogic Tuxedo Connector support this option.</li>  <li>LDAP: The <code>LDAP</code> plug-in utilizes an embedded LDAP server to provide user security information. The user record must define the Tuxedo UID and GID information in the description field. This functionality is not supported in previous releases of WebLogic Tuxedo Connector. </li>  <li>Custom: The <code>Custom</code> plug-in provides the ability to write your own AppKey generator class to provide the security information required by Tuxedo. This functionality is not supported in previous releases of WebLogic Tuxedo Connector. </li> </ul> ");
         setPropertyDescriptorDefault(currentResult, "TpUsrFile");
         currentResult.setValue("legalValues", new Object[]{"TpUsrFile", "LDAP", "Custom"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CmpLimit")) {
         getterName = "getCmpLimit";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCmpLimit";
         }

         currentResult = new PropertyDescriptor("CmpLimit", WTCRemoteTuxDomMBean.class, getterName, setterName);
         descriptors.put("CmpLimit", currentResult);
         currentResult.setValue("description", "<p>The compression threshold this remote Tuxedo access point uses when sending data to a local Tuxedo access point. Application buffers larger than this size are compressed.</p>  <p><b>Range of Values:</b> Between <code>0</code> and a positive 32-bit integer.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnPrincipalName")) {
         getterName = "getConnPrincipalName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnPrincipalName";
         }

         currentResult = new PropertyDescriptor("ConnPrincipalName", WTCRemoteTuxDomMBean.class, getterName, setterName);
         descriptors.put("ConnPrincipalName", currentResult);
         currentResult.setValue("description", "<p>The principal name used to verify the identity of this remote Tuxedo access point when it establishes a session connection with a local Tuxedo access point. If not specified, the connection principal name defaults to the AccessPointID for this access point.</p>  <p><i>Note:</i> This parameter only applies to domains of type TDOMAIN that are running Oracle  Tuxedo 7.1 or later software.</p>  <p><b>Note:</b> ConnPrincipalName is not supported in this release.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionPolicy")) {
         getterName = "getConnectionPolicy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnectionPolicy";
         }

         currentResult = new PropertyDescriptor("ConnectionPolicy", WTCRemoteTuxDomMBean.class, getterName, setterName);
         descriptors.put("ConnectionPolicy", currentResult);
         currentResult.setValue("description", "<p>The conditions under which this remote Tuxedo access point establishes a session connection with a local Tuxedo access point.</p>  <p>The allowed values are:</p>  <ul> <li>ON_DEMAND: A connection is attempted only when requested by either a client request to a remote service or an administrative connect command.</li>  <li>ON_STARTUP: A domain gateway attempts to establish a connection with its remote Tuxedo access points at gateway server initialization time. Remote services (services advertised in JNDI by the domain gateway for this local Tuxedo access point) are advertised only if a connection is successfully established to that remote Tuxedo access point. If there is no active connection to a remote Tuxedo access point, then the remote services are suspended. By default, this connection policy retries failed connections every 60 seconds. Use the MaxRetry and RetryInterval attributes to specify application specific values.</li>  <li>INCOMING_ONLY: A domain gateway does not attempt an initial connection to remote Tuxedo access points at startup and remote services are initially suspended. The domain gateway is available for incoming connections from remote Tuxedo access points and remote services are advertised when the domain gateway for this local Tuxedo access point receives an incoming connection. Connection retry processing is not allowed.</li>  <li>LOCAL: Specifies that the remote domain connection policy is explicitly defaulted to the local domain ConnectionPolicy attribute value.</li> </ul> ");
         setPropertyDescriptorDefault(currentResult, "LOCAL");
         currentResult.setValue("legalValues", new Object[]{"ON_DEMAND", "ON_STARTUP", "INCOMING_ONLY", "LOCAL"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CredentialPolicy")) {
         getterName = "getCredentialPolicy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCredentialPolicy";
         }

         currentResult = new PropertyDescriptor("CredentialPolicy", WTCRemoteTuxDomMBean.class, getterName, setterName);
         descriptors.put("CredentialPolicy", currentResult);
         currentResult.setValue("description", "<p>The outbound access control list (ACL) policy toward requests to a remote Tuxedo access point.</p>  <p>The allowed values are:</p>  <ul> <li>LOCAL: The remote Tuxedo access point controls the identity of service requests received from the local Tuxedo access point to the principal name specified in the local principal name for this remote Tuxedo access point.</li>  <li>GLOBAL: The remote Tuxedo access point passes the service request with no change.</li> </ul>  <p><i>Note:</i>If Interoperate is set to Yes, CredentialPolicy is ignored.</p> ");
         setPropertyDescriptorDefault(currentResult, "LOCAL");
         currentResult.setValue("legalValues", new Object[]{"GLOBAL", "LOCAL"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CustomAppKeyClass")) {
         getterName = "getCustomAppKeyClass";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCustomAppKeyClass";
         }

         currentResult = new PropertyDescriptor("CustomAppKeyClass", WTCRemoteTuxDomMBean.class, getterName, setterName);
         descriptors.put("CustomAppKeyClass", currentResult);
         currentResult.setValue("description", "<p>The full pathname to the custom <code>AppKey</code> generator class. (This class is only relevant if you specify <code>Custom</code> as the AppKey Generator.)</p>  <p><i>Note:</i> This class is loaded at runtime if the <code>Custom</code> AppKey generator plug-in is selected.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CustomAppKeyClassParam")) {
         getterName = "getCustomAppKeyClassParam";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCustomAppKeyClassParam";
         }

         currentResult = new PropertyDescriptor("CustomAppKeyClassParam", WTCRemoteTuxDomMBean.class, getterName, setterName);
         descriptors.put("CustomAppKeyClassParam", currentResult);
         currentResult.setValue("description", "<p>The optional parameters to be used by the custom <code>AppKey</code> class at the class initialization time. This class is only relevant if you specify <code>Custom</code> as the AppKey Generator.)</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultAppKey")) {
         getterName = "getDefaultAppKey";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultAppKey";
         }

         currentResult = new PropertyDescriptor("DefaultAppKey", WTCRemoteTuxDomMBean.class, getterName, setterName);
         descriptors.put("DefaultAppKey", currentResult);
         currentResult.setValue("description", "<p>The default <code>AppKey</code> value to be used by the anonymous user and other users who are not defined in the user database if the plug-in allows them to access Tuxedo.</p>  <p><i>Note:</i> The <code>TpUsrFile</code> and <code>LDAP</code> plug-ins do not allow users that are not defined in user database to access Tuxedo unless Allow Anonymous is enabled.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FederationName")) {
         getterName = "getFederationName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFederationName";
         }

         currentResult = new PropertyDescriptor("FederationName", WTCRemoteTuxDomMBean.class, getterName, setterName);
         descriptors.put("FederationName", currentResult);
         currentResult.setValue("description", "<p>The context at which this remote Tuxedo access point federates to a foreign name service. If omitted, the default federation point is <code>tuxedo.domains</code>.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FederationURL")) {
         getterName = "getFederationURL";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFederationURL";
         }

         currentResult = new PropertyDescriptor("FederationURL", WTCRemoteTuxDomMBean.class, getterName, setterName);
         descriptors.put("FederationURL", currentResult);
         currentResult.setValue("description", "<p>The URL for a foreign name service that is federated into JNDI.</p>  <p><i>Note:</i> The Weblogic Tuxedo Connector can federate to non-CORBA service providers.</p>  <p><i>Note:</i> If this value is not specified, the WebLogic Tuxedo Connector:</p>  <ul> <li>Assumes there is a CosNaming server in the foreign domain.</li>  <li>Federates to the CosNaming server using TGIOP. </li> </ul> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("KeepAlive")) {
         getterName = "getKeepAlive";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setKeepAlive";
         }

         currentResult = new PropertyDescriptor("KeepAlive", WTCRemoteTuxDomMBean.class, getterName, setterName);
         descriptors.put("KeepAlive", currentResult);
         currentResult.setValue("description", "<p>Indicates whether this remote Tuxedo access point is configured with Application Level Keep Alive.</p> <ul> <li> Greater than 0: Application Level Keep Alive is enabled and the value indicates the amount of idle time, in milliseconds rounded to the nearest second, allowed before starting the wait timer.</li> <li> 0: Application Level Keep Alive is not enabled for the access point.</li> <li> -1: Application Level Keep Alive is enabled and indicates that the value from the local Tuxedo access point is used to indicate the amount of idle time, in milliseconds rounded to the nearest second, allowed before starting the wait timer.</li> </ul> ");
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(-1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("KeepAliveWait")) {
         getterName = "getKeepAliveWait";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setKeepAliveWait";
         }

         currentResult = new PropertyDescriptor("KeepAliveWait", WTCRemoteTuxDomMBean.class, getterName, setterName);
         descriptors.put("KeepAliveWait", currentResult);
         currentResult.setValue("description", "<p>Indicates whether this remote Tuxedo access point requires the acknowledgement of Application Level Keep Alive. If the value is:</p> <ul> <li> Greater than 0: Application Level Keep Alive is enabled and the value indicates the amount of idle time, in milliseconds rounded to the nearest second, allowed before WebLogic Tuxedo Connector marks the connection as failed if the heartbeat is not acknowledged.</li> <li> 0: Application Level Keep Alive is not enabled for the access point but the access point acknowledges if a heartbeat is received.</li> </ul> ");
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LocalAccessPoint")) {
         getterName = "getLocalAccessPoint";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLocalAccessPoint";
         }

         currentResult = new PropertyDescriptor("LocalAccessPoint", WTCRemoteTuxDomMBean.class, getterName, setterName);
         descriptors.put("LocalAccessPoint", currentResult);
         currentResult.setValue("description", "<p>The local domain name from which this remote Tuxedo domain is reached.</p> ");
         setPropertyDescriptorDefault(currentResult, "myLAP");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxEncryptBits")) {
         getterName = "getMaxEncryptBits";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxEncryptBits";
         }

         currentResult = new PropertyDescriptor("MaxEncryptBits", WTCRemoteTuxDomMBean.class, getterName, setterName);
         descriptors.put("MaxEncryptBits", currentResult);
         currentResult.setValue("description", "<p>The maximum encryption key length (in bits) this remote Tuxedo access point uses when establishing a session connection. A value of <code>0</code> indicates no encryption is used.</p>  <p style=\"font-weight: bold\">Value Restrictions:</p>  <ul> <li>The value of the MaxEncryptBits attribute must be greater than or equal to the value of the MinEncrypBits attribute. </li>  <li>A MaxEncryptBits of 40 can be used only with domains running Tuxedo 7.1 or higher. </li> </ul> ");
         setPropertyDescriptorDefault(currentResult, "128");
         currentResult.setValue("legalValues", new Object[]{"0", "40", "56", "128"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxRetries")) {
         getterName = "getMaxRetries";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxRetries";
         }

         currentResult = new PropertyDescriptor("MaxRetries", WTCRemoteTuxDomMBean.class, getterName, setterName);
         descriptors.put("MaxRetries", currentResult);
         currentResult.setValue("description", "<p>The maximum number of times that this remote Tuxedo access point tries to establish a session connections to local Tuxedo access points. Use only when the ConnectionPolicy value is set to <code>ON_STARTUP</code>.</p>  <p><i>Note:</i> When the ConnectionPolicy value is <code>LOCAL</code>, the MaxRetries value from the local Tuxedo access point is used. When the ConnectionPolicy value is <code>ON_STARTUP</code>, a value of <code>-1</code> indicates 9223372036854775807.</p>  <p><b>Range of Values:</b> Between <code>-1</code> and a positive 64-bit integer.</p>  <ul> <li>Use <code>0</code> to disable the retry mechanism.</li>  <li>Use the maximum value to try until a connection is established.</li> </ul> ");
         setPropertyDescriptorDefault(currentResult, new Long(-1L));
         currentResult.setValue("legalMax", new Long(Long.MAX_VALUE));
         currentResult.setValue("legalMin", new Long(-1L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MinEncryptBits")) {
         getterName = "getMinEncryptBits";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMinEncryptBits";
         }

         currentResult = new PropertyDescriptor("MinEncryptBits", WTCRemoteTuxDomMBean.class, getterName, setterName);
         descriptors.put("MinEncryptBits", currentResult);
         currentResult.setValue("description", "<p>The minimum encryption key length (in bits) this remote Tuxedo access point uses when establishing a session connection. A value of <code>0</code> indicates no encryption is used.</p>  <p style=\"font-weight: bold\">Value Restrictions:</p>  <ul> <li>The MinEncrypBits value must be less than or equal to the MaxEncrypBits value. </li>  <li>A MinEncrypBits value of 40 can be used only with domains running Tuxedo 7.1 or higher. </li> </ul> ");
         setPropertyDescriptorDefault(currentResult, "0");
         currentResult.setValue("secureValue", "40");
         currentResult.setValue("legalValues", new Object[]{"0", "40", "56", "128"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NWAddr")) {
         getterName = "getNWAddr";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNWAddr";
         }

         currentResult = new PropertyDescriptor("NWAddr", WTCRemoteTuxDomMBean.class, getterName, setterName);
         descriptors.put("NWAddr", currentResult);
         currentResult.setValue("description", "<p>The network address and port number of this remote Tuxedo access point.</p> Specify the address in one of the following formats: <ul> <li>TCP/IP address in the format <code>//hostname:port_number</code> or <code>//#.#.#.#:port_number</code>.</li> <li>SDP address in the format <code>sdp://hostname:port_number</code> or <code>sdp://#.#.#.#:port_number</code>.</li> </ul>  <i>Notes:</i> <ul> <li>If the hostname is used, the access point finds an address for hostname using the local name resolution facilities (usually DNS). If dotted decimal format is used, each # should be a number from 0 to 255. This dotted decimal number represents the IP address of the local machine. The port_number is the TCP/SDP port number at which the access point listens for incoming requests.</li>  <li>If SDP format address is specified, the transport protocol for this access point is SDP instead of TCP. This feature is only available when WTC and Tuxedo domain gateway are both deployed on Oracle Exalogic platform. Requires Tuxedo 11gR1PS2 and higher.</li> </ul> ");
         setPropertyDescriptorDefault(currentResult, "//localhost:8902");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RetryInterval")) {
         getterName = "getRetryInterval";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRetryInterval";
         }

         currentResult = new PropertyDescriptor("RetryInterval", WTCRemoteTuxDomMBean.class, getterName, setterName);
         descriptors.put("RetryInterval", currentResult);
         currentResult.setValue("description", "<p>The number of seconds that this remote Tuxedo access point waits between automatic connection attempts to local Tuxedo access points. Use this only when the ConnectionPolicy value is set to <code>ON_STARTUP</code>.</p>  <p><b>Range of Values:</b> Between <code>-1</code> and a positive 32-bit integer.</p>  <p><i>Note:</i> When the ConnectionPolicy value is <code>LOCAL</code>, the RetryInterval value from the local Tuxedo access point is used. When the ConnectionPolicy value is <code>ON_STARTUP</code>, a value of <code>-1</code> indicates 60.</p> ");
         setPropertyDescriptorDefault(currentResult, new Long(-1L));
         currentResult.setValue("legalMax", new Long(2147483647L));
         currentResult.setValue("legalMin", new Long(-1L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SSLProtocolVersion")) {
         getterName = "getSSLProtocolVersion";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSSLProtocolVersion";
         }

         currentResult = new PropertyDescriptor("SSLProtocolVersion", WTCRemoteTuxDomMBean.class, getterName, setterName);
         descriptors.put("SSLProtocolVersion", currentResult);
         currentResult.setValue("description", "<p>The protocol version of the SSL connection this remote access point uses when establishing a SSL connection with specific local access point. </p>  <p style=\"font-weight: bold\">Value Restrictions:</p>  <ul> <li> TLSv1.0 </li> <li> TLSv1.1 </li> <li> TLSv1.2 </li> </ul> ");
         setPropertyDescriptorDefault(currentResult, "TLSv1.2");
         currentResult.setValue("legalValues", new Object[]{"TLSv1.0", "TLSv1.1", "TLSv1.2"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TpUsrFile")) {
         getterName = "getTpUsrFile";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTpUsrFile";
         }

         currentResult = new PropertyDescriptor("TpUsrFile", WTCRemoteTuxDomMBean.class, getterName, setterName);
         descriptors.put("TpUsrFile", currentResult);
         currentResult.setValue("description", "<p>The full path to the user password file containing UID/GID information. (This field is only relevant if you specify <code>TpUsrFile</code> as the AppKey Generator.)</p>  <p><i>Note:</i> This file is generated by the Tuxedo <code>tpusradd</code> utility on the remote Tuxedo domain specified by the remote Tuxedo access point. A copy of this file must be available in your WebLogic Tuxedo Connector environment to provide correct authorization, authentication, and auditing.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TuxedoGidKw")) {
         getterName = "getTuxedoGidKw";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTuxedoGidKw";
         }

         currentResult = new PropertyDescriptor("TuxedoGidKw", WTCRemoteTuxDomMBean.class, getterName, setterName);
         descriptors.put("TuxedoGidKw", currentResult);
         currentResult.setValue("description", "<p>The keyword for Tuxedo GID (Group ID) used in the <code>WlsUser</code> when using the Tuxedo migration utility <code>tpmigldap</code>. This field is only relevant if you specify <code>LDAP</code> as the AppKey Generator.)</p>  <p><i>Note:</i> The keyword is used to find Tuxedo GID in the user record in the embedded LDAP database.</p> ");
         setPropertyDescriptorDefault(currentResult, "TUXEDO_GID");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TuxedoUidKw")) {
         getterName = "getTuxedoUidKw";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTuxedoUidKw";
         }

         currentResult = new PropertyDescriptor("TuxedoUidKw", WTCRemoteTuxDomMBean.class, getterName, setterName);
         descriptors.put("TuxedoUidKw", currentResult);
         currentResult.setValue("description", "<p>The keyword for Tuxedo UID (User ID) used in the <code>WlsUser</code> when using the Tuxedo migration utility <code>tpmigldap</code>. This keyword is only relevant if you specify <code>LDAP</code> as the AppKey Generator.)</p>  <p><i>Note:</i> The keyword is used to find Tuxedo UID in the user record in the embedded LDAP database.</p> ");
         setPropertyDescriptorDefault(currentResult, "TUXEDO_UID");
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
