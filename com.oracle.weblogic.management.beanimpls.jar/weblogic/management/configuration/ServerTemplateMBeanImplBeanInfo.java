package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class ServerTemplateMBeanImplBeanInfo extends KernelMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ServerTemplateMBean.class;

   public ServerTemplateMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ServerTemplateMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.ServerTemplateMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>This class represents a WebLogic Server. A WebLogic Server is a Java process that is a container for Java EE applications.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.ServerTemplateMBean");
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

         currentResult = new PropertyDescriptor("AcceptBacklog", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("AcceptBacklog", currentResult);
         currentResult.setValue("description", "<p>The number of backlogged, new TCP connection requests that should be allowed for this server's regular and SSL ports.</p>  <p>Setting the backlog to <code>0</code> may prevent this server from accepting any incoming connection on some operating systems.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getListenPort"), BeanInfoHelper.encodeEntities("#getAcceptBacklog"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.NetworkAccessPointMBean#getAcceptBacklog")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(300));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AdminReconnectIntervalSeconds")) {
         getterName = "getAdminReconnectIntervalSeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAdminReconnectIntervalSeconds";
         }

         currentResult = new PropertyDescriptor("AdminReconnectIntervalSeconds", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("AdminReconnectIntervalSeconds", currentResult);
         currentResult.setValue("description", "<p>The number of seconds between reconnection attempts to the admin server. When the admin server fails the managed server will periodically try to connect back to it.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(10));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AdministrationPort")) {
         getterName = "getAdministrationPort";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAdministrationPort";
         }

         currentResult = new PropertyDescriptor("AdministrationPort", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("AdministrationPort", currentResult);
         currentResult.setValue("description", "<p>The secure administration port for the server. This port requires that you enable the domain's administration port and that SSL is configured and enabled.</p>  <p>By default, the server uses the administration port that is specified at the domain level. To override the domain-level administration port for the current server instance, set this server's administration port.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.DomainMBean#isAdministrationPortEnabled"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.DomainMBean#getAdministrationPort"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.KernelMBean#getSSL")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("secureValue", new Integer(7002));
         currentResult.setValue("secureValueDocOnly", Boolean.TRUE);
         currentResult.setValue("legalMax", new Integer(65535));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AutoKillIfFailed")) {
         getterName = "getAutoKillIfFailed";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAutoKillIfFailed";
         }

         currentResult = new PropertyDescriptor("AutoKillIfFailed", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("AutoKillIfFailed", currentResult);
         currentResult.setValue("description", "<p>This is the equivalent of setting the OverloadProtectionMBean failureAction to \"force-shutdown\" and the panicAction to \"system-exit\".</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("deprecated", "since 12.1.3.0 Use OverloadProtectionMBean instead ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AutoRestart")) {
         getterName = "getAutoRestart";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAutoRestart";
         }

         currentResult = new PropertyDescriptor("AutoRestart", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("AutoRestart", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the Node Manager can automatically restart this server if it crashes or otherwise goes down unexpectedly.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("BuzzAddress")) {
         getterName = "getBuzzAddress";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBuzzAddress";
         }

         currentResult = new PropertyDescriptor("BuzzAddress", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("BuzzAddress", currentResult);
         currentResult.setValue("description", "<p>Buzz endpoint address.</p> ");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("BuzzPort")) {
         getterName = "getBuzzPort";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBuzzPort";
         }

         currentResult = new PropertyDescriptor("BuzzPort", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("BuzzPort", currentResult);
         currentResult.setValue("description", "<p>Buzz endpoint port.</p>  <p>A value of <code>0</code> means system-allocated (dynamic) port.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("COM")) {
         getterName = "getCOM";
         setterName = null;
         currentResult = new PropertyDescriptor("COM", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("COM", currentResult);
         currentResult.setValue("description", "<p>Returns the server's COM configuration.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("deprecated", "12.2.1.2.0 ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CandidateMachines")) {
         getterName = "getCandidateMachines";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCandidateMachines";
         }

         currentResult = new PropertyDescriptor("CandidateMachines", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("CandidateMachines", currentResult);
         currentResult.setValue("description", "<p>Limits the list of candidate machines that the cluster specifies. (Requires you to enable this server for automatic migration and to configure the cluster with a set of candidate machines.)</p>  <p>If this server fails and if it is enabled for automatic migration, Node Manager automatically restarts it. By default, Node Manager restarts the server on any of the candidate machines that the cluster specifies (in order of preference that you configured in the cluster). To change the default, you use this server's list of candidate machines to create a subset of the cluster-wide candidates. You can also change the order of preference.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("ClusterMBean#getCandidateMachinesForMigratableServers")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Cluster")) {
         getterName = "getCluster";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCluster";
         }

         currentResult = new PropertyDescriptor("Cluster", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("Cluster", currentResult);
         currentResult.setValue("description", "<p>The cluster, or group of WebLogic Server instances, to which this server belongs.</p>  <p>If set, the server will listen for cluster multicast events.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getClusterWeight"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.ClusterMBean#getMulticastPort"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.ClusterMBean#getMulticastAddress")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("ClusterWeight")) {
         getterName = "getClusterWeight";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setClusterWeight";
         }

         currentResult = new PropertyDescriptor("ClusterWeight", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("ClusterWeight", currentResult);
         currentResult.setValue("description", "<p>The proportion of the load that this server will bear, relative to other servers in a cluster.</p>  <p>If all servers have the default weight or the same weight, each bears an equal proportion of the load. If one server has weight 50 and all other servers have weight 100, the 50-weight server will bear half as much load as any other server.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getCluster")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(100));
         currentResult.setValue("legalMax", new Integer(100));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CoherenceClusterSystemResource")) {
         getterName = "getCoherenceClusterSystemResource";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCoherenceClusterSystemResource";
         }

         currentResult = new PropertyDescriptor("CoherenceClusterSystemResource", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("CoherenceClusterSystemResource", currentResult);
         currentResult.setValue("description", "The system-level Coherence cluster resource associated with this server. ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CoherenceMemberConfig")) {
         getterName = "getCoherenceMemberConfig";
         setterName = null;
         currentResult = new PropertyDescriptor("CoherenceMemberConfig", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("CoherenceMemberConfig", currentResult);
         currentResult.setValue("description", "<p>Returns the Coherence Operational Config overrides associated with this Server.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConfigurationProperties")) {
         getterName = "getConfigurationProperties";
         setterName = null;
         currentResult = new PropertyDescriptor("ConfigurationProperties", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("ConfigurationProperties", currentResult);
         currentResult.setValue("description", "<p>Specifies the list of properties that are associated with this container object.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createConfigurationProperty");
         currentResult.setValue("destroyer", "destroyConfigurationProperty");
         currentResult.setValue("dynamic", Boolean.TRUE);
      }

      if (!descriptors.containsKey("ConsensusProcessIdentifier")) {
         getterName = "getConsensusProcessIdentifier";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConsensusProcessIdentifier";
         }

         currentResult = new PropertyDescriptor("ConsensusProcessIdentifier", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("ConsensusProcessIdentifier", currentResult);
         currentResult.setValue("description", "<p>Specifies the identifier to be used for consensus-based algorithms. Each server should have a unique identifier indexed from 0.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("legalMax", new Integer(65535));
         currentResult.setValue("legalMin", new Integer(-1));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CustomIdentityKeyStoreFileName")) {
         getterName = "getCustomIdentityKeyStoreFileName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCustomIdentityKeyStoreFileName";
         }

         currentResult = new PropertyDescriptor("CustomIdentityKeyStoreFileName", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("CustomIdentityKeyStoreFileName", currentResult);
         currentResult.setValue("description", "<p>The source of the identity keystore. For a JKS or PKCS12 keystore, the source is the path and file name. For an Oracle Key Store Service (KSS) keystore, the source is the KSS URI.</p>  <p>If using a JKS or PKCS12 keystore, the keystore path name must either be absolute or relative to where the server was booted.</p>  <p>If using a KSS keystore, the keystore URI must be of the form:</p>  <p><code>kss://system/<i>keystorename</i></code></p>  <p>where <code><i>keystorename</i></code> is the name of the keystore registered in KSS.</p>  <p> The value in this attribute is only used if <code>KeyStores</code> is <code>CUSTOM_IDENTITY_AND_JAVA_STANDARD_TRUST</code>, <code>CUSTOM_IDENTITY_AND_CUSTOM_TRUST</code> or <code>CUSTOM_IDENTITY_AND_COMMAND_LINE_TRUST</code>.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CustomIdentityKeyStorePassPhrase")) {
         getterName = "getCustomIdentityKeyStorePassPhrase";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCustomIdentityKeyStorePassPhrase";
         }

         currentResult = new PropertyDescriptor("CustomIdentityKeyStorePassPhrase", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("CustomIdentityKeyStorePassPhrase", currentResult);
         currentResult.setValue("description", "<p>The encrypted custom identity keystore's passphrase. If empty or null, then the keystore will be opened without a passphrase.</p>  <p>This attribute is only used if KeyStores is <code>CUSTOM_IDENTITY_AND_JAVA_STANDARD_TRUST</code>, <code>CUSTOM_IDENTITY_AND_CUSTOM_TRUST</code> or <code>CUSTOM_IDENTITY_AND_COMMAND_LINE_TRUST</code>.</p>  <p>When you get the value of this attribute, WebLogic Server does the following:</p> <ol><li>Retrieves the value of the <code>CustomIdentityKeyStorePassPhraseEncrypted</code> attribute.</li> <li>Decrypts the value and returns the unencrypted password as a String.</li> </ol>  <p>When you set the value of this attribute, WebLogic Server does the following:</p> <ol><li>Encrypts the value.</li> <li>Sets the value of the <code>CustomIdentityKeyStorePassPhraseEncrypted</code> attribute to the encrypted value.</li> </ol> <p>Using this attribute (<code>CustomIdentityKeyStorePassPhrase</code>) is a potential security risk because the String object (which contains the unencrypted password) remains in the JVM's memory until garbage collection removes it and the memory is reallocated. Depending on how memory is allocated in the JVM, a significant amount of time could pass before this unencrypted data is removed from memory.</p>  <p>Instead of using this attribute, use <code>CustomIdentityKeyStorePassPhraseEncrypted</code>.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getCustomIdentityKeyStorePassPhraseEncrypted()")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CustomIdentityKeyStorePassPhraseEncrypted")) {
         getterName = "getCustomIdentityKeyStorePassPhraseEncrypted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCustomIdentityKeyStorePassPhraseEncrypted";
         }

         currentResult = new PropertyDescriptor("CustomIdentityKeyStorePassPhraseEncrypted", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("CustomIdentityKeyStorePassPhraseEncrypted", currentResult);
         currentResult.setValue("description", "<p>Returns encrypted pass phrase defined when creating the keystore.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CustomIdentityKeyStoreType")) {
         getterName = "getCustomIdentityKeyStoreType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCustomIdentityKeyStoreType";
         }

         currentResult = new PropertyDescriptor("CustomIdentityKeyStoreType", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("CustomIdentityKeyStoreType", currentResult);
         currentResult.setValue("description", "<p>The type of the keystore. Generally, this is <code>JKS</code> or <code>PKCS12</code>. If using the Oracle Key Store Service, this is <code>KSS</code></p>  <p>If empty or null, then the JDK's default keystore type (specified in <code>java.security</code>) is used. The custom identity key store type is only used if <code>KeyStores</code> is <code>CUSTOM_IDENTITY_AND_JAVA_STANDARD_TRUST</code>, <code>CUSTOM_IDENTITY_AND_CUSTOM_TRUST</code> or <code>CUSTOM_IDENTITY_AND_COMMAND_LINE_TRUST</code>.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CustomTrustKeyStoreFileName")) {
         getterName = "getCustomTrustKeyStoreFileName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCustomTrustKeyStoreFileName";
         }

         currentResult = new PropertyDescriptor("CustomTrustKeyStoreFileName", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("CustomTrustKeyStoreFileName", currentResult);
         currentResult.setValue("description", "<p>The source of the custom trust keystore. For a JKS or PKCS12 keystore, the source is the path and file name. For an Oracle Key Store Service (KSS) keystore, the source is the KSS URI.</p>  <p>If using a JKS or PKCS12 keystore, the keystore path name must either be absolute or relative to where the server was booted.</p>  <p>If using a KSS keystore, the keystore URI must be of the form:</p>  <p><code>kss://system/<i>keystorename</i></code></p>  <p>where <code><i>keystorename</i></code> is the name of the keystore registered in KSS.</p>  <p> The value in this attribute is only used if <code>KeyStores</code> is  <code>CUSTOM_IDENTITY_AND_CUSTOM_TRUST</code>.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CustomTrustKeyStorePassPhrase")) {
         getterName = "getCustomTrustKeyStorePassPhrase";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCustomTrustKeyStorePassPhrase";
         }

         currentResult = new PropertyDescriptor("CustomTrustKeyStorePassPhrase", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("CustomTrustKeyStorePassPhrase", currentResult);
         currentResult.setValue("description", "<p>The custom trust keystore's passphrase. If empty or null, then the keystore will be opened without a passphrase.</p>  <p>This attribute is only used if KeyStores is CUSTOM_IDENTITY_AND_CUSTOM_TRUST.</p>  <p>When you get the value of this attribute, WebLogic Server does the following:</p> <ol><li>Retrieves the value of the <code>CustomTrustKeyStorePassPhraseEncrypted</code> attribute.</li> <li>Decrypts the value and returns the unencrypted password as a String.</li> </ol>  <p>When you set the value of this attribute, WebLogic Server does the following:</p> <ol><li>Encrypts the value.</li> <li>Sets the value of the <code>CustomTrustKeyStorePassPhraseEncrypted</code> attribute to the encrypted value.</li> </ol> <p>Using this attribute (<code>CustomTrustKeyStorePassPhrase</code>) is a potential security risk because the String object (which contains the unencrypted password) remains in the JVM's memory until garbage collection removes it and the memory is reallocated. Depending on how memory is allocated in the JVM, a significant amount of time could pass before this unencrypted data is removed from memory.</p>  <p>Instead of using this attribute, use <code>CustomTrustKeyStorePassPhraseEncrypted</code>.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getCustomTrustKeyStorePassPhraseEncrypted()")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CustomTrustKeyStorePassPhraseEncrypted")) {
         getterName = "getCustomTrustKeyStorePassPhraseEncrypted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCustomTrustKeyStorePassPhraseEncrypted";
         }

         currentResult = new PropertyDescriptor("CustomTrustKeyStorePassPhraseEncrypted", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("CustomTrustKeyStorePassPhraseEncrypted", currentResult);
         currentResult.setValue("description", "<p>The custom trust keystore's encrypted passphrase. If empty or null, then the keystore will be opened without a passphrase.</p>  <p>This attribute is only used if KeyStores is CUSTOM_IDENTITY_AND_CUSTOM_TRUST.</p>  <p>To set this attribute, use <code>weblogic.management.EncryptionHelper.encrypt()</code> to encrypt the value. Then set this attribute to the output of the encrypt() method. </p>  <p>To compare a password that a user enters with the encrypted value of this attribute, use the same WebLogic Server instance that you used to set and encrypt this attribute and use <code>weblogic.management.EncryptionHelper.encrypt()</code> to encrypt the user-supplied password. Then compare the encrypted values.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CustomTrustKeyStoreType")) {
         getterName = "getCustomTrustKeyStoreType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCustomTrustKeyStoreType";
         }

         currentResult = new PropertyDescriptor("CustomTrustKeyStoreType", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("CustomTrustKeyStoreType", currentResult);
         currentResult.setValue("description", "<p>The type of the keystore. Generally, this is <code>JKS</code> or <code>PKCS12</code>. If using the Oracle Key Store Service, this is <code>KSS</code></p>  <p>If empty or null, then the JDK's default keystore type (specified in <code>java.security</code>) is used. This keystore type is only used if KeyStores is CUSTOM_IDENTITY_AND_CUSTOM_TRUST.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DataSource")) {
         getterName = "getDataSource";
         setterName = null;
         currentResult = new PropertyDescriptor("DataSource", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("DataSource", currentResult);
         currentResult.setValue("description", "<p>The datasource configuration for the server.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultFileStore")) {
         getterName = "getDefaultFileStore";
         setterName = null;
         currentResult = new PropertyDescriptor("DefaultFileStore", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("DefaultFileStore", currentResult);
         currentResult.setValue("description", "<p>Controls the configuration of the default persistent store on this server. Each server has a default store, which is a file-based object repository used by various subsystems.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultIIOPPassword")) {
         getterName = "getDefaultIIOPPassword";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultIIOPPassword";
         }

         currentResult = new PropertyDescriptor("DefaultIIOPPassword", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("DefaultIIOPPassword", currentResult);
         currentResult.setValue("description", "<p>The password for the default IIOP user. (Requires you to enable IIOP.)</p> <p>As of 8.1 sp4, when you get the value of this attribute, WebLogic Server does the following:</p> <ol><li>Retrieves the value of the <code>DefaultIIOPPasswordEncrypted</code> attribute.</li> <li>Decrypts the value and returns the unencrypted password as a String.</li> </ol>  <p>Using this attribute (<code>DefaultIIOPPassword</code>) is a potential security risk because the String object (which contains the unencrypted password) remains in the JVM's memory until garbage collection removes it and the memory is reallocated. Depending on how memory is allocated in the JVM, a significant amount of time could pass before this unencrypted data is removed from memory.</p>  <p>Instead of using this attribute, use <code>DefaultIIOPPasswordEncrypted</code>.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#isIIOPEnabled"), BeanInfoHelper.encodeEntities("#getDefaultIIOPPasswordEncrypted()")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultIIOPPasswordEncrypted")) {
         getterName = "getDefaultIIOPPasswordEncrypted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultIIOPPasswordEncrypted";
         }

         currentResult = new PropertyDescriptor("DefaultIIOPPasswordEncrypted", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("DefaultIIOPPasswordEncrypted", currentResult);
         currentResult.setValue("description", "<p>The encrypted password for the default IIOP user.</p> <p>To set this attribute, use <code>weblogic.management.EncryptionHelper.encrypt()</code> to encrypt the value. Then set this attribute to the output of the encrypt() method.</p>  <p>To compare a password that a user enters with the encrypted value of this attribute, go to the same WebLogic Server instance that you used to set and encrypt this attribute and use <code>weblogic.management.EncryptionHelper.encrypt()</code> to encrypt the user-supplied password. Then compare the encrypted values.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultIIOPUser")) {
         getterName = "getDefaultIIOPUser";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultIIOPUser";
         }

         currentResult = new PropertyDescriptor("DefaultIIOPUser", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("DefaultIIOPUser", currentResult);
         currentResult.setValue("description", "<p>The user name of the default IIOP user. (Requires you to enable IIOP.)</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#isIIOPEnabled")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultTGIOPPassword")) {
         getterName = "getDefaultTGIOPPassword";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultTGIOPPassword";
         }

         currentResult = new PropertyDescriptor("DefaultTGIOPPassword", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("DefaultTGIOPPassword", currentResult);
         currentResult.setValue("description", "<p>The password for the default user associated with the Tuxedo GIOP (TGIOP) protocol. (Requires you to configure WebLogic Tuxedo Connector (WTC) for this server.)</p>  <p>As of 8.1 sp4, when you get the value of this attribute, WebLogic Server does the following:</p> <ol><li>Retrieves the value of the <code>DefaultTGIOPPasswordEncrypted</code> attribute.</li> <li>Decrypts the value and returns the unencrypted password as a String.</li> </ol> <p>When you set the value of this attribute, WebLogic Server does the following:</p> <ol><li>Encrypts the value.</li> <li>Sets the value of the <code>DefaultTGIOPPasswordEncrypted</code> attribute to the encrypted value.</li> </ol>  <p>Using this attribute (<code>DefaultTGIOPPassword</code>) is a potential security risk in because the String object (which contains the unencrypted password) remains the JVM's memory until garbage collection removes it and the memory is reallocated. Depending on how memory is allocated in the JVM, a significant amount of time could pass before this unencrypted data is removed from memory.</p>  <p>Instead of using this attribute, use <code>DefaultTGIOPPasswordEncrypted</code>.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getDefaultTGIOPPasswordEncrypted()")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultTGIOPPasswordEncrypted")) {
         getterName = "getDefaultTGIOPPasswordEncrypted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultTGIOPPasswordEncrypted";
         }

         currentResult = new PropertyDescriptor("DefaultTGIOPPasswordEncrypted", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("DefaultTGIOPPasswordEncrypted", currentResult);
         currentResult.setValue("description", "<p>The encrypted password for the default TGIOP user.</p>  <p>To set this attribute, use <code>weblogic.management.EncryptionHelper.encrypt()</code> to encrypt the value. Then set this attribute to the output of the encrypt() method.</p>  <p>To compare a password that a user enters with the encrypted value of this attribute, go to the same WebLogic Server instance that you used to set and encrypt this attribute and use <code>weblogic.management.EncryptionHelper.encrypt()</code> to encrypt the user-supplied password. Then compare the encrypted values.</p> ");
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultTGIOPUser")) {
         getterName = "getDefaultTGIOPUser";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultTGIOPUser";
         }

         currentResult = new PropertyDescriptor("DefaultTGIOPUser", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("DefaultTGIOPUser", currentResult);
         currentResult.setValue("description", "<p>The default user associated with the Tuxedo GIOP (TGIOP) protocol. (Requires you to configure WebLogic Tuxedo Connector (WTC) for this server.)</p> ");
         setPropertyDescriptorDefault(currentResult, "guest");
         currentResult.setValue("secureValueNull", Boolean.TRUE);
         currentResult.setValue("deprecated", "12.2.1.3.0 ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ExpectedToRun")) {
         getterName = "getExpectedToRun";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setExpectedToRun";
         }

         currentResult = new PropertyDescriptor("ExpectedToRun", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("ExpectedToRun", currentResult);
         currentResult.setValue("description", "<p>If this server expected to run if the domain is started.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#synchronousKill")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ExternalDNSName")) {
         getterName = "getExternalDNSName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setExternalDNSName";
         }

         currentResult = new PropertyDescriptor("ExternalDNSName", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("ExternalDNSName", currentResult);
         currentResult.setValue("description", "<p>The external IP address or DNS name for this server.</p>  <p>This address will be sent with HTTP session cookies and with dynamic server lists to HTTP proxies. It will also be used by external application clients to enable the propagation of RMI traffic through network address translating (NAT) firewalls.</p>  <p>You must specify an external DNS name for configurations in which a firewall is performing network address translation, unless clients are accessing WebLogic Server using t3 and the default channel. For example, define the external DNS name for configurations in which a firewall is performing network address translation, and clients are accessing WebLogic Server using HTTP via a proxy plug-in.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getListenAddress"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.NetworkAccessPointMBean#getExternalDNSName")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ExtraEjbcOptions")) {
         getterName = "getExtraEjbcOptions";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setExtraEjbcOptions";
         }

         currentResult = new PropertyDescriptor("ExtraEjbcOptions", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("ExtraEjbcOptions", currentResult);
         currentResult.setValue("description", "<p>The options passed to the EJB compiler during server-side generation.</p>  <p>Each EJB component can override the compiler options that you specify here. The following options are valid:</p>  <dl> <dt>-forcegeneration</dt> <dd>Forces generation of wrapper classes. Without this flag the classes may not be regenerated if it is determined to be unnecessary.</dd>  <dt>-disableHotCodeGen</dt> <dd>Generate ejb stub and skel as part of ejbc. Avoid HotCodeGen to have better performance. </dd>  <dt>-keepgenerated</dt> <dd>Keep the generated .java files.</dd>   <dt>-compiler javac</dt> <dd>Java compiler to exec.  If not specified, the -compilerclass option will be used.</dd>  <dt>-compilerclass com.sun.tools.javac.Main</dt> <dd>Specifies the compiler class to invoke.</dd>  <dt>-g</dt> <dd>Compile debugging info into class file.</dd>  <dt>-normi</dt> <dd>Passed through to Symantec's sj.</dd>  <dt>-classpath path</dt> <dd>Classpath to use.</dd>  <dt>-source source</dt> <dd>Source version.</dd>  <dt>-J<i>option</i></dt> <dd>Flags passed through to java runtime.</dd> </dl> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.EJBContainerMBean#getExtraEjbcOptions()")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ExtraRmicOptions")) {
         getterName = "getExtraRmicOptions";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setExtraRmicOptions";
         }

         currentResult = new PropertyDescriptor("ExtraRmicOptions", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("ExtraRmicOptions", currentResult);
         currentResult.setValue("description", "<p>The options passed to the RMIC compiler during server-side generation.</p>  <p>Each EJB component can override the compiler options that you specify here.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.EJBContainerMBean#getExtraRmicOptions()")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("9.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("FederationServices")) {
         getterName = "getFederationServices";
         setterName = null;
         currentResult = new PropertyDescriptor("FederationServices", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("FederationServices", currentResult);
         currentResult.setValue("description", "<p>Gets the Federation Services MBean</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.1.0.0");
      }

      if (!descriptors.containsKey("GracefulShutdownTimeout")) {
         getterName = "getGracefulShutdownTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setGracefulShutdownTimeout";
         }

         currentResult = new PropertyDescriptor("GracefulShutdownTimeout", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("GracefulShutdownTimeout", currentResult);
         currentResult.setValue("description", "<p>Number of seconds a graceful shutdown operation waits before forcing a shut down. A graceful shutdown gives WebLogic Server subsystems time to complete certain application processing currently in progress. If subsystems are unable to complete processing within the number of seconds that you specify here, then the server will force shutdown automatically.</p>  <p>A value of <code>0</code> means that the server will wait indefinitely for graceful shutdown to complete.</p>  <p>The graceful shutdown timeout applies only to graceful shutdown operations.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getServerLifeCycleTimeoutVal")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HealthCheckIntervalSeconds")) {
         getterName = "getHealthCheckIntervalSeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setHealthCheckIntervalSeconds";
         }

         currentResult = new PropertyDescriptor("HealthCheckIntervalSeconds", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("HealthCheckIntervalSeconds", currentResult);
         currentResult.setValue("description", "<p>The number of seconds that defines the frequency of this server's self-health monitoring. The server monitors the health of it's subsystems every HealthCheckIntervalSeconds and changes the Server's overall state if required. </p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(180));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HealthCheckStartDelaySeconds")) {
         getterName = "getHealthCheckStartDelaySeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setHealthCheckStartDelaySeconds";
         }

         currentResult = new PropertyDescriptor("HealthCheckStartDelaySeconds", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("HealthCheckStartDelaySeconds", currentResult);
         currentResult.setValue("description", "<p>The number of seconds the Node Manager should wait before starting to monitor the server.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(120));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HealthCheckTimeoutSeconds")) {
         getterName = "getHealthCheckTimeoutSeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setHealthCheckTimeoutSeconds";
         }

         currentResult = new PropertyDescriptor("HealthCheckTimeoutSeconds", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("HealthCheckTimeoutSeconds", currentResult);
         currentResult.setValue("description", "<p>The number of seconds the Node Manager should wait before timing out its health query to this server.</p>  <p>If the timeout is reached, Node Managed assumes the Managed Server has failed.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(60));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("deprecated", "9.0.0.0 Replaced by Server self-health monitoring that takes action without NodeManager intervention. NodeManager identifies if a running server was shutdown due to a restartable failure and restarts the server. ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HostsMigratableServices")) {
         getterName = "getHostsMigratableServices";
         setterName = null;
         currentResult = new PropertyDescriptor("HostsMigratableServices", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("HostsMigratableServices", currentResult);
         currentResult.setValue("description", "<p>Gets the hostsMigratableServices attribute of the ServerMBean object</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InterfaceAddress")) {
         getterName = "getInterfaceAddress";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setInterfaceAddress";
         }

         currentResult = new PropertyDescriptor("InterfaceAddress", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("InterfaceAddress", currentResult);
         currentResult.setValue("description", "<p>The IP address of the NIC that this server should use for multicast traffic.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getCluster"), BeanInfoHelper.encodeEntities("#setInterfaceAddress"), BeanInfoHelper.encodeEntities("ClusterMBean#getMulticastAddress")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JDBCLLRTableName")) {
         getterName = "getJDBCLLRTableName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJDBCLLRTableName";
         }

         currentResult = new PropertyDescriptor("JDBCLLRTableName", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("JDBCLLRTableName", currentResult);
         currentResult.setValue("description", "<p>The table name for this server's Logging Last Resource (LLR) database table(s). WebLogic Server creates the table(s) and then uses them during transaction processing for the LLR transaction optimization. This setting must be unique for each server. The default table name is <code>WL_LLR_<i>SERVERNAME</i></code>.</p>  <p>This setting only applies if this server hosts one or more LLR-enabled JDBC data sources.</p>  <p>The format for the tables that WebLogic Server creates is [[[catalog.]schema.]name.] Each \".\" in the table name is significant, and schema generally corresponds to username in many databases.</p>  <p><b>IMPORTANT:</b> If this value is changed but the LLR table already exists in the database, you must preserve the existing table's data. Consequently, when changing the table name, the existing database table must be renamed by a database administrator to match the new configured table name. Otherwise, transaction records may be lost, resulting in heuristic failures that aren't logged.</p>  <p><b>IMPORTANT:</b> Each server's table name must be unique. Multiple LLR-enabled data sources within the same server may share the same table, but multiple servers must not share the same table. If multiple same-named servers share a table, the behavior is undefined and it is likely that transactions will not recover properly after a crash, creating heuristic hazards.</p> ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("JDBCLogFileName")) {
         getterName = "getJDBCLogFileName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJDBCLogFileName";
         }

         currentResult = new PropertyDescriptor("JDBCLogFileName", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("JDBCLogFileName", currentResult);
         currentResult.setValue("description", "<p>The name of the JDBC log file. If the pathname is not absolute, it is assumed to be relative to the root directory of the machine on which this server is running. (Requires you to enable JDBC logging.)</p>  <p>If the log has no path element and is atomic (for example, <code>jdbc.log</code>), the file will be placed relative to the root directory in ./SERVER_NAME/ to avoid name space conflicts. This attribute is deprecated because the JDBC output now goes in the server log.</p> ");
         setPropertyDescriptorDefault(currentResult, "jdbc.log");
         currentResult.setValue("deprecated", "9.0.0.0 Driver level debugging is not supported ");
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("JDBCLoginTimeoutSeconds")) {
         getterName = "getJDBCLoginTimeoutSeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJDBCLoginTimeoutSeconds";
         }

         currentResult = new PropertyDescriptor("JDBCLoginTimeoutSeconds", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("JDBCLoginTimeoutSeconds", currentResult);
         currentResult.setValue("description", "The JDBC Login Timeout value. Specified value is passed into java.sql.DriverManager.setLoginTimeout(). Note that this DriverManager setting will impact *all* JDBC drivers loaded into this JVM. Feature is disabled by default. ");
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("legalMax", new Integer(300));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("JMSConnectionFactoryUnmappedResRefMode")) {
         getterName = "getJMSConnectionFactoryUnmappedResRefMode";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJMSConnectionFactoryUnmappedResRefMode";
         }

         currentResult = new PropertyDescriptor("JMSConnectionFactoryUnmappedResRefMode", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("JMSConnectionFactoryUnmappedResRefMode", currentResult);
         currentResult.setValue("description", "<p> Use this unmapped resource reference mode to specify the behavior of any resource reference to a Connection Factory when the resource reference does not directly specify a JNDI name by a lookup attribute, or by a mappedName attribute, or by a jndi-name in a descriptor file. If a JNDI name is specified, the resource reference either resolves to the specified object in the JNDI tree or generates a javax.naming.NameNotFoundException. </p>  <p> Following are the available unmapped resource reference modes: </p> <ul> <li> <code>FailSafe :</code> In this mode, an application JMS connection factory resource reference that does not specify a JNDI name resolves to an object bound to the JNDI tree with the same name as the resource reference name if one can be found in JNDI tree. Otherwise, it throws a javax.naming.NameNotFoundException. A resource reference without a JNDI name never returns a Java EE 7 Default Connection Factory in this mode. <p> This mode yields pre-12.2.1 WebLogic Server behavior. This mode is Java EE compliant, but the Java EE 7 specification disallows it from being the default mode. </p> </li> </ul> <p> </p> <ul> <li> <code>ReturnDefault :</code> This policy is a Java EE compliant mode and it is the default mode. It complies with Java EE 7 while doing a best effort to try delivering FailSafe behavior when it is expected. <p> (a) If the resource reference name matches the local JNDI name of a configured foreign JMS provider mapping, then WebLogic Server returns the remote JNDI entry from the mapping if it can be resolved, or it throws a javax.naming.NameNotFoundException. </p> <p> (b) If the resource reference does not match with a foreign JMS provider mapping, and if the resource reference name matches the name of an object that is already bound to the JNDI tree, WebLogic Server returns the JNDI tree object. </p> <p> (c) If neither (a) nor (b) apply, then WebLogic Server returns \"java:comp/DefaultJMSConnectionFactory\". In addition, it adds an 'Info' message in the server log indicating that a special behavior has occurred. This log message will occur at most once per application per server boot. </p> </li> </ul> ");
         setPropertyDescriptorDefault(currentResult, "ReturnDefault");
         currentResult.setValue("legalValues", new Object[]{"ReturnDefault", "FailSafe"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("JNDITransportableObjectFactoryList")) {
         getterName = "getJNDITransportableObjectFactoryList";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJNDITransportableObjectFactoryList";
         }

         currentResult = new PropertyDescriptor("JNDITransportableObjectFactoryList", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("JNDITransportableObjectFactoryList", currentResult);
         currentResult.setValue("description", "<p>List of factories that create transportable objects.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JTAMigratableTarget")) {
         getterName = "getJTAMigratableTarget";
         setterName = null;
         currentResult = new PropertyDescriptor("JTAMigratableTarget", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("JTAMigratableTarget", currentResult);
         currentResult.setValue("description", "<p>Returns the JTAMigratableTargetMBean that is used to deploy the JTA Recovery Service to (is the server's cluster is not null).</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JavaCompiler")) {
         getterName = "getJavaCompiler";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJavaCompiler";
         }

         currentResult = new PropertyDescriptor("JavaCompiler", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("JavaCompiler", currentResult);
         currentResult.setValue("description", "<p>The Java compiler to use for all applications hosted on this server that need to compile Java code.</p> ");
         setPropertyDescriptorDefault(currentResult, "javac");
         currentResult.setValue("secureValue", "javac");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JavaCompilerPostClassPath")) {
         getterName = "getJavaCompilerPostClassPath";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJavaCompilerPostClassPath";
         }

         currentResult = new PropertyDescriptor("JavaCompilerPostClassPath", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("JavaCompilerPostClassPath", currentResult);
         currentResult.setValue("description", "<p>The options to append to the Java compiler classpath when compiling Java code.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("secureValueNull", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JavaCompilerPreClassPath")) {
         getterName = "getJavaCompilerPreClassPath";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJavaCompilerPreClassPath";
         }

         currentResult = new PropertyDescriptor("JavaCompilerPreClassPath", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("JavaCompilerPreClassPath", currentResult);
         currentResult.setValue("description", "<p>The options to prepend to the Java compiler classpath when compiling Java code.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("secureValueNull", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JavaStandardTrustKeyStorePassPhrase")) {
         getterName = "getJavaStandardTrustKeyStorePassPhrase";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJavaStandardTrustKeyStorePassPhrase";
         }

         currentResult = new PropertyDescriptor("JavaStandardTrustKeyStorePassPhrase", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("JavaStandardTrustKeyStorePassPhrase", currentResult);
         currentResult.setValue("description", "<p>The password for the Java Standard Trust keystore. This password is defined when the keystore is created.</p> <p>If empty or null, then the keystore will be opened without a passphrase.</p>  <p>This attribute is only used if KeyStores is CUSTOM_IDENTITY_AND_JAVA_STANDARD_TRUST or DEMO_IDENTITY_AND_DEMO_TRUST.</p>  <p>When you get the value of this attribute, WebLogic Server does the following:</p> <ol><li>Retrieves the value of the <code>JavaStandardTrustKeyStorePassPhraseEncrypted</code> attribute.</li> <li>Decrypts the value and returns the unencrypted password as a String.</li> </ol>  <p>When you set the value of this attribute, WebLogic Server does the following:</p> <ol><li>Encrypts the value.</li> <li>Sets the value of the <code>JavaStandardTrustKeyStorePassPhraseEncrypted</code> attribute to the encrypted value.</li> </ol> <p>Using this attribute (<code>JavaStandardTrustKeyStorePassPhrase</code>) is a potential security risk because the String object (which contains the unencrypted password) remains in the JVM's memory until garbage collection removes it and the memory is reallocated. Depending on how memory is allocated in the JVM, a significant amount of time could pass before this unencrypted data is removed from memory.</p>  <p>Instead of using this attribute, use <code>JavaStandardTrustKeyStorePassPhraseEncrypted</code>.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getJavaStandardTrustKeyStorePassPhraseEncrypted()")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JavaStandardTrustKeyStorePassPhraseEncrypted")) {
         getterName = "getJavaStandardTrustKeyStorePassPhraseEncrypted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJavaStandardTrustKeyStorePassPhraseEncrypted";
         }

         currentResult = new PropertyDescriptor("JavaStandardTrustKeyStorePassPhraseEncrypted", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("JavaStandardTrustKeyStorePassPhraseEncrypted", currentResult);
         currentResult.setValue("description", "<p>The encrypted password for the Java Standard Trust keystore. This password is defined when the keystore is created.</p>  <p>To set this attribute,  use <code>weblogic.management.EncryptionHelper.encrypt()</code> to encrypt the value. Then set this attribute to the output of the encrypt() method.</p>  <p>To compare a password that a user enters with the encrypted value of this attribute, go to the same WebLogic Server instance that you used to set and encrypt this attribute and use <code>weblogic.management.EncryptionHelper.encrypt()</code> to encrypt the user-supplied password. Then compare the encrypted values.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("KernelDebug")) {
         getterName = "getKernelDebug";
         setterName = null;
         currentResult = new PropertyDescriptor("KernelDebug", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("KernelDebug", currentResult);
         currentResult.setValue("description", "<p>Get the debug flags for this kernel (will return a KernelDebugMBean if this is a KernelMBean) or the server (will return a ServerDebugMBean if this is a ServerMBean).</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
      }

      if (!descriptors.containsKey("KeyStores")) {
         getterName = "getKeyStores";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setKeyStores";
         }

         currentResult = new PropertyDescriptor("KeyStores", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("KeyStores", currentResult);
         currentResult.setValue("description", "<p>Which configuration rules should be used for finding the server's identity and trust keystores?</p> ");
         setPropertyDescriptorDefault(currentResult, "DemoIdentityAndDemoTrust");
         currentResult.setValue("legalValues", new Object[]{"DemoIdentityAndDemoTrust", "CustomIdentityAndJavaStandardTrust", "CustomIdentityAndCustomTrust", "CustomIdentityAndCommandLineTrust"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ListenAddress")) {
         getterName = "getListenAddress";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setListenAddress";
         }

         currentResult = new PropertyDescriptor("ListenAddress", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("ListenAddress", currentResult);
         currentResult.setValue("description", "<p>The IP address or DNS name this server uses to listen for incoming connections. For example, enter <code>12.34.5.67</code> or <code>mymachine</code>, respectively.</p>  <p>Note that the value you specify for the listen address is not the URL to the host machine and it does not include the communication protocol, listen port, or channel.</p>  <p>Servers can be reached through the following URL:</p>  <p><code>protocol://listen-address:listen-port</code></p>  <p>Any network channel that you configure for this server can override this listen address.</p>  <p>If a server's listen address is undefined, clients can reach the server through an IP address of the computer that hosts the server, a DNS name that resolves to the host, or the localhost string. The localhost string can be used only for requests from clients that are running on the same computer as the server.</p>  <p>If you want to limit the valid addresses for a server instance, specify one of the following:</p>  <ul> <li> <p>IP address. If you provide an IP address, clients can specify either the IP address or a DNS name that maps to the IP address. Clients that specify an IP address and attempt to connect through an SSL port must disable hostname verification.</p> </li>  <li> <p>DNS name. If you provide a DNS name, clients can specify either the DNS name or the corresponding IP address.</p> </li> </ul>  <p>Do not leave the listen address undefined on a computer that uses multiple IP address (a multihomed computer). On such a computer, the server will bind to all available IP addresses.</p>  <p>Note also that if you are using the demo certificates in a multi-server domain, Managed Server instances will fail to boot if you specify the fully-qualified DNS name. For information about this limitation and suggested workarounds, see \"Limitation on CertGen Usage\" in <i>Administering Security for Oracle WebLogic Server</i>.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getExternalDNSName"), BeanInfoHelper.encodeEntities("#getListenPort"), BeanInfoHelper.encodeEntities("#getInterfaceAddress"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.SSLMBean#getListenPort"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.KernelMBean#getSSL"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.NetworkAccessPointMBean#getListenAddress")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ListenDelaySecs")) {
         getterName = "getListenDelaySecs";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setListenDelaySecs";
         }

         currentResult = new PropertyDescriptor("ListenDelaySecs", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("ListenDelaySecs", currentResult);
         currentResult.setValue("description", "<p>Perpetuated for compatibility with 6.1 only.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ListenPort")) {
         getterName = "getListenPort";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setListenPort";
         }

         currentResult = new PropertyDescriptor("ListenPort", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("ListenPort", currentResult);
         currentResult.setValue("description", "<p>The default TCP port that this server uses to listen for regular (non-SSL) incoming connections.</p>  <p>Administrators must have the right privileges before binding to a port or else this operation will not be successful and it will render the console un-reachable.</p>  <p>If this port is disabled, the SSL port must be enabled. Additional ports can be configured using network channels. The cluster (multicast) port is configured separately.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#isListenPortEnabled"), BeanInfoHelper.encodeEntities("#getAdministrationPort"), BeanInfoHelper.encodeEntities("#getListenAddress"), BeanInfoHelper.encodeEntities("#getCluster"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.SSLMBean#getListenPort"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.ClusterMBean#getMulticastPort"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.NetworkAccessPointMBean#getListenPort")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(7001));
         currentResult.setValue("legalMax", new Integer(65535));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ListenThreadStartDelaySecs")) {
         getterName = "getListenThreadStartDelaySecs";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setListenThreadStartDelaySecs";
         }

         currentResult = new PropertyDescriptor("ListenThreadStartDelaySecs", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("ListenThreadStartDelaySecs", currentResult);
         currentResult.setValue("description", "<p>Returns the maximum time that the server will wait for server sockets to bind before starting a listen thread.</p>  <p>Properties to consider for removal</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(60));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ListenersBindEarly")) {
         getterName = "getListenersBindEarly";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setListenersBindEarly";
         }

         currentResult = new PropertyDescriptor("ListenersBindEarly", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("ListenersBindEarly", currentResult);
         currentResult.setValue("description", "<p>Determines whether the server should bind server sockets early.</p>  <p>Early binding detects port conflicts quickly and also gives user feedback on the default listen port as to the server state.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("LoginTimeout")) {
         getterName = "getLoginTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLoginTimeout";
         }

         currentResult = new PropertyDescriptor("LoginTimeout", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("LoginTimeout", currentResult);
         currentResult.setValue("description", "This does nothing. ");
         setPropertyDescriptorDefault(currentResult, new Integer(1000));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (!descriptors.containsKey("LoginTimeoutMillis")) {
         getterName = "getLoginTimeoutMillis";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLoginTimeoutMillis";
         }

         currentResult = new PropertyDescriptor("LoginTimeoutMillis", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("LoginTimeoutMillis", currentResult);
         currentResult.setValue("description", "<p>The login timeout for this server's default regular (non-SSL) listen port. This is the maximum amount of time allowed for a new connection to establish.</p>  <p>A value of <code>0</code> indicates there is no maximum.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.SSLMBean#getLoginTimeoutMillis"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.KernelMBean#getSSL"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.NetworkAccessPointMBean#getLoginTimeoutMillis")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(5000));
         currentResult.setValue("secureValue", new Integer(5000));
         currentResult.setValue("legalMax", new Integer(100000));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LowMemoryGCThreshold")) {
         getterName = "getLowMemoryGCThreshold";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLowMemoryGCThreshold";
         }

         currentResult = new PropertyDescriptor("LowMemoryGCThreshold", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("LowMemoryGCThreshold", currentResult);
         currentResult.setValue("description", "<p>The threshold level (in percent) that this server uses for logging low memory conditions and changing the server health state to <code>Warning</code>.</p>  <p>For example, if you specify 5, the server logs a low memory warning in the log file and changes the server health state to <code>Warning</code> after the average free memory reaches 5% of the initial free memory measured at the server's boot time.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(5));
         currentResult.setValue("secureValue", new Integer(5));
         currentResult.setValue("legalMax", new Integer(99));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("deprecated", "12.1.1.0.0 not used anywhere in runtime (core) code ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LowMemoryGranularityLevel")) {
         getterName = "getLowMemoryGranularityLevel";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLowMemoryGranularityLevel";
         }

         currentResult = new PropertyDescriptor("LowMemoryGranularityLevel", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("LowMemoryGranularityLevel", currentResult);
         currentResult.setValue("description", "<p>The granularity level (in percent) that this server uses for logging low memory conditions and changing the server health state to <tt>Warning</tt>.</p>  <p>For example, if you specify 5 and the average free memory drops by 5% or more over two measured intervals, the server logs a low memory warning in the log file and changes the server health state to <tt>Warning</tt>.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(5));
         currentResult.setValue("secureValue", new Integer(5));
         currentResult.setValue("legalMax", new Integer(100));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("deprecated", "12.1.1.0.0 not used anywhere in runtime (core) code ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LowMemorySampleSize")) {
         getterName = "getLowMemorySampleSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLowMemorySampleSize";
         }

         currentResult = new PropertyDescriptor("LowMemorySampleSize", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("LowMemorySampleSize", currentResult);
         currentResult.setValue("description", "<p>The number of times this server samples free memory during the time period specified by LowMemoryTimeInterval.</p>  <p>Increasing the sample size can improve the accuracy of the reading.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(10));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("deprecated", "12.1.1.0.0 not used anywhere in runtime (core) code ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LowMemoryTimeInterval")) {
         getterName = "getLowMemoryTimeInterval";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLowMemoryTimeInterval";
         }

         currentResult = new PropertyDescriptor("LowMemoryTimeInterval", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("LowMemoryTimeInterval", currentResult);
         currentResult.setValue("description", "<p>The amount of time (in seconds) that defines the interval over which this server determines average free memory values.</p>  <p>By default, the server obtains an average free memory value every 3600 seconds.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(3600));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(300));
         currentResult.setValue("deprecated", "12.1.1.0.0 not used anywhere in runtime (core) code ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Machine")) {
         getterName = "getMachine";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMachine";
         }

         currentResult = new PropertyDescriptor("Machine", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("Machine", currentResult);
         currentResult.setValue("description", "<p>The WebLogic Server host computer (machine) on which this server is meant to run.</p>  <p>If you want to use Node Manager to start this server, you must assign the server to a machine and you must configure the machine for Node Manager.</p>  <p>You cannot change this value if a server instance is already running.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("MaxConcurrentLongRunningRequests")) {
         getterName = "getMaxConcurrentLongRunningRequests";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxConcurrentLongRunningRequests";
         }

         currentResult = new PropertyDescriptor("MaxConcurrentLongRunningRequests", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("MaxConcurrentLongRunningRequests", currentResult);
         currentResult.setValue("description", "The maximum number of running long-running requests that can be submitted to all the Managed Executor Services or Managed Scheduled Executor Services in the server. ");
         setPropertyDescriptorDefault(currentResult, new Integer(100));
         currentResult.setValue("legalMax", new Integer(65534));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("MaxConcurrentNewThreads")) {
         getterName = "getMaxConcurrentNewThreads";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxConcurrentNewThreads";
         }

         currentResult = new PropertyDescriptor("MaxConcurrentNewThreads", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("MaxConcurrentNewThreads", currentResult);
         currentResult.setValue("description", "The maximum number of running threads that can be created by all the Managed Thread Factories in the server. ");
         setPropertyDescriptorDefault(currentResult, new Integer(100));
         currentResult.setValue("legalMax", new Integer(65534));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("9.2.3.0", (String)null, this.targetVersion) && !descriptors.containsKey("NMSocketCreateTimeoutInMillis")) {
         getterName = "getNMSocketCreateTimeoutInMillis";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNMSocketCreateTimeoutInMillis";
         }

         currentResult = new PropertyDescriptor("NMSocketCreateTimeoutInMillis", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("NMSocketCreateTimeoutInMillis", currentResult);
         currentResult.setValue("description", "Returns the timeout value to be used by NodeManagerRuntime when creating a a socket connection to the agent. Default set high as SSH agent may require a high connection establishment time. ");
         setPropertyDescriptorDefault(currentResult, new Integer(180000));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("deprecated", "12.2.1.2.0 ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.2.3.0");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>An alphanumeric name for this server instance. (Spaces are not valid.)</p>  <p>The name must be unique for all configuration objects in the domain. Within a domain, each server, machine, cluster, JDBC connection pool, virtual host, and any other resource type must be named uniquely and must not use the same name as the domain.</p>  <p>For more information on server naming conventions, see <a href=\"http://www.oracle.com/pls/topic/lookup?ctx=wls14110&amp;id=DOMCF237\" rel=\"noopener noreferrer\" target=\"_blank\">Domain and Server Name Restrictions</a> in Understanding Domain Configuration for Oracle WebLogic Server.</p>  <p>The server name is not used as part of the URL for applications that are deployed on the server. It is for your identification purposes only. The server name displays in the Administration Console, and if you use WebLogic Server command-line utilities or APIs, you use this name to identify the server.</p>  <p>After you have created a server, you cannot change its name. Instead, clone the server and provide a new name for the clone.</p> ");
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("7.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("NetworkAccessPoints")) {
         getterName = "getNetworkAccessPoints";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNetworkAccessPoints";
         }

         currentResult = new PropertyDescriptor("NetworkAccessPoints", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("NetworkAccessPoints", currentResult);
         currentResult.setValue("description", "<p>Network access points, or \"NAPs\", define additional ports and addresses that this server listens on. Additionally, if two servers both support the same channel for a given protocol, then new connections between them will use that channel.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyNetworkAccessPoint");
         currentResult.setValue("creator", "createNetworkAccessPoint");
         currentResult.setValue("setterDeprecated", "9.0.0.0 Use createNetworkAccessPoint() instead. ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "7.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("NumOfRetriesBeforeMSIMode")) {
         getterName = "getNumOfRetriesBeforeMSIMode";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNumOfRetriesBeforeMSIMode";
         }

         currentResult = new PropertyDescriptor("NumOfRetriesBeforeMSIMode", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("NumOfRetriesBeforeMSIMode", currentResult);
         currentResult.setValue("description", "Get the number of retries to contact admin server, before starting in Managed Server Independence Mode ");
         setPropertyDescriptorDefault(currentResult, new Integer(3));
         currentResult.setValue("legalMax", new Integer(65534));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("OverloadProtection")) {
         getterName = "getOverloadProtection";
         setterName = null;
         currentResult = new PropertyDescriptor("OverloadProtection", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("OverloadProtection", currentResult);
         currentResult.setValue("description", "get attributes related to server overload protection ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Parent")) {
         getterName = "getParent";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setParent";
         }

         currentResult = new PropertyDescriptor("Parent", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("Parent", currentResult);
         currentResult.setValue("description", "<p>Return the immediate parent for this MBean</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("PreferredSecondaryGroup")) {
         getterName = "getPreferredSecondaryGroup";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPreferredSecondaryGroup";
         }

         currentResult = new PropertyDescriptor("PreferredSecondaryGroup", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("PreferredSecondaryGroup", currentResult);
         currentResult.setValue("description", "<p>Defines secondary clustered instances considered for hosting replicas of the primary HTTP session states created on the server.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ReliableDeliveryPolicy")) {
         getterName = "getReliableDeliveryPolicy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setReliableDeliveryPolicy";
         }

         currentResult = new PropertyDescriptor("ReliableDeliveryPolicy", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("ReliableDeliveryPolicy", currentResult);
         currentResult.setValue("description", "<p>The reliable delivery policy for web services.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ReplicationGroup")) {
         getterName = "getReplicationGroup";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setReplicationGroup";
         }

         currentResult = new PropertyDescriptor("ReplicationGroup", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("ReplicationGroup", currentResult);
         currentResult.setValue("description", "<p>Defines preferred clustered instances considered for hosting replicas of the primary HTTP session states created on the server.</p> ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.4.0", (String)null, this.targetVersion) && !descriptors.containsKey("ReplicationPorts")) {
         getterName = "getReplicationPorts";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setReplicationPorts";
         }

         currentResult = new PropertyDescriptor("ReplicationPorts", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("ReplicationPorts", currentResult);
         currentResult.setValue("description", "When WLS is running on Exalogic machines, cluster replication traffic could go over multiple replication channels. However multiple replication channels need not be configured on each clustered server instance. Only one replication channel with explicit IP-Address needs to be configured for each server and replicationPorts range can be specified for each server. For eg. range 7001-7010 will create 10 replication channels with ports 7001 to 7010 for the given server. These channels inherits all the properties of the configured replication channel except the listen port.Names of these channels will be derived from the configured replication channel with suffic {x} added where x could be 1,2.. as per the number of ports specified. Public ports are same as the listen port for these additional channels. ");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.4.0");
      }

      if (!descriptors.containsKey("ResolveDNSName")) {
         getterName = "getResolveDNSName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setResolveDNSName";
         }

         currentResult = new PropertyDescriptor("ResolveDNSName", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("ResolveDNSName", currentResult);
         currentResult.setValue("description", "<p>Resolve the listen address to use for all T3 communication</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getListenAddress()"), BeanInfoHelper.encodeEntities("#getExternalDNSName()"), BeanInfoHelper.encodeEntities("NetworkAccessPointMBean#getResolveDNSName()")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RestartDelaySeconds")) {
         getterName = "getRestartDelaySeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRestartDelaySeconds";
         }

         currentResult = new PropertyDescriptor("RestartDelaySeconds", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("RestartDelaySeconds", currentResult);
         currentResult.setValue("description", "<p>The number of seconds the Node Manager should wait before restarting this server.</p>  <p>After killing a server process, the system might need several seconds to release the TCP port(s) the server was using. If Node Manager attempts to restart the Managed Server while its ports are still active, the startup attempt fails.</p>  <p>If AutoMigration is enabled and RestartDelaySeconds is 0, the RestartDelaySeconds is automatically set to the lease time. This prevents the server from failing to restart after migration when the previous lease is still valid.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RestartIntervalSeconds")) {
         getterName = "getRestartIntervalSeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRestartIntervalSeconds";
         }

         currentResult = new PropertyDescriptor("RestartIntervalSeconds", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("RestartIntervalSeconds", currentResult);
         currentResult.setValue("description", "<p>The number of seconds during which this server can be restarted, up to the number of times specified in RestartMax.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getRestartMax")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(3600));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(300));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RestartMax")) {
         getterName = "getRestartMax";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRestartMax";
         }

         currentResult = new PropertyDescriptor("RestartMax", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("RestartMax", currentResult);
         currentResult.setValue("description", "<p>The number of times that the Node Manager can restart this server within the interval specified in RestartIntervalSeconds.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(2));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("RetryIntervalBeforeMSIMode")) {
         getterName = "getRetryIntervalBeforeMSIMode";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRetryIntervalBeforeMSIMode";
         }

         currentResult = new PropertyDescriptor("RetryIntervalBeforeMSIMode", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("RetryIntervalBeforeMSIMode", currentResult);
         currentResult.setValue("description", "Get the number of seconds between the retries to contact admin server, before starting in Managed Server Independence Mode ");
         setPropertyDescriptorDefault(currentResult, new Integer(5));
         currentResult.setValue("legalMax", new Integer(65534));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("ServerDebug")) {
         getterName = "getServerDebug";
         setterName = null;
         currentResult = new PropertyDescriptor("ServerDebug", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("ServerDebug", currentResult);
         currentResult.setValue("description", "<p>The debug setting for this server.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ServerDiagnosticConfig")) {
         getterName = "getServerDiagnosticConfig";
         setterName = null;
         currentResult = new PropertyDescriptor("ServerDiagnosticConfig", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("ServerDiagnosticConfig", currentResult);
         currentResult.setValue("description", "The diagnostic configuration for the servers ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (!descriptors.containsKey("ServerLifeCycleTimeoutVal")) {
         getterName = "getServerLifeCycleTimeoutVal";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setServerLifeCycleTimeoutVal";
         }

         currentResult = new PropertyDescriptor("ServerLifeCycleTimeoutVal", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("ServerLifeCycleTimeoutVal", currentResult);
         currentResult.setValue("description", "<p>Number of seconds a force shutdown operation waits before timing out and killing itself. If the operation does not complete within the configured timeout seconds, the server will shutdown automatically if the state of the server at that time was <code>SHUTTING_DOWN</code>.</p>  <p>A value of <code>0</code> means that the server will wait indefinitely for life cycle operation to complete.</p> ");
         currentResult.setValue("restProductionModeDefault", new Integer(120));
         setPropertyDescriptorDefault(currentResult, new Integer(30));
         currentResult.setValue("secureValue", new Integer(120));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ServerStart")) {
         getterName = "getServerStart";
         setterName = null;
         currentResult = new PropertyDescriptor("ServerStart", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("ServerStart", currentResult);
         currentResult.setValue("description", "<p>Returns the ServerStartMBean that can be used to start up this server remotely.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ServerVersion")) {
         getterName = "getServerVersion";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setServerVersion";
         }

         currentResult = new PropertyDescriptor("ServerVersion", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("ServerVersion", currentResult);
         currentResult.setValue("description", "<p>The release identifier for the server. Since this is a configured attribute it is only as accurate as the configuration. The form of the version is major.minor.servicepack.rollingpatch. Not all parts of the version are required. i.e. \"7\" is acceptable.</p> ");
         setPropertyDescriptorDefault(currentResult, "unknown");
         currentResult.setValue("deprecated", "9.0.0.0 ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("9.5.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("SingleSignOnServices")) {
         getterName = "getSingleSignOnServices";
         setterName = null;
         currentResult = new PropertyDescriptor("SingleSignOnServices", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("SingleSignOnServices", currentResult);
         currentResult.setValue("description", "<p>Gets the Single Sign-On Services MBean</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.5.0.0");
      }

      if (!descriptors.containsKey("SitConfigPollingInterval")) {
         getterName = "getSitConfigPollingInterval";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSitConfigPollingInterval";
         }

         currentResult = new PropertyDescriptor("SitConfigPollingInterval", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("SitConfigPollingInterval", currentResult);
         currentResult.setValue("description", "Returns the current polling interval for finding the situational config file in the filesystem ");
         setPropertyDescriptorDefault(currentResult, new Integer(5));
         currentResult.setValue("legalMax", new Integer(65534));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("StagingDirectoryName")) {
         getterName = "getStagingDirectoryName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStagingDirectoryName";
         }

         currentResult = new PropertyDescriptor("StagingDirectoryName", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("StagingDirectoryName", currentResult);
         currentResult.setValue("description", "<p>The directory path on the Managed Server where all staged (prepared) applications are placed.</p>  <p>If an absolute directory name is not specified, the path is relative to the root directory \"/\". Once configured, you cannot change the staging directory name. Remove all applications from the server prior to changing this attribute. The default staging directory is \"stage\", relative to the server root.</p> ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("7.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("StagingMode")) {
         getterName = "getStagingMode";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStagingMode";
         }

         currentResult = new PropertyDescriptor("StagingMode", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("StagingMode", currentResult);
         currentResult.setValue("description", "<p>The mode that specifies whether an application's files are copied from a source on the Administration Server to the Managed Server's staging area during application preparation.</p>  <p>During application preparation, the application's files are copied from the source on the Administration Server to the Managed Server's staging area. If you specify <code>nostage</code> or <code>external_stage</code>, the copy will not occur. This is useful when the staging area is a shared directory, already containing the application files, or if this is a single server domain. The administrator must ensure that the Managed Server's staging directory is set appropriately. Deployment errors will result if the application is not available during the preparation or activation of the application. Each application can override the staging mode specified here.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.ApplicationMBean#getStagingMode()")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalValues", new Object[]{ServerMBean.DEFAULT_STAGE, "stage", "nostage", "external_stage"});
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "7.0.0.0");
      }

      if (!descriptors.containsKey("StartupMode")) {
         getterName = "getStartupMode";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStartupMode";
         }

         currentResult = new PropertyDescriptor("StartupMode", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("StartupMode", currentResult);
         currentResult.setValue("description", "<p>The state in which this server should be started. If you specify <code>STANDBY</code>, you must also enable the domain-wide administration port.</p>  <p>In the <code>RUNNING</code> state, a server offers its services to clients and can operate as a full member of a cluster. In the <code>ADMIN</code> state, the server is up and running, but available only for administration operations, allowing you to perform server and application-level administration tasks without risk to running applications. In the <code>STANDBY</code> state, a server instance does not process any request; its regular Listen Port is closed. The Administration Port is open. It only accepts life cycle commands that transition the server instance to either the <code>RUNNING</code> or the <code>SHUTDOWN</code> state. Other Administration requests are not accepted. A <code>STANDBY</code> server's only purpose is to resume into the <code>RUNNING</code> state quickly; it saves server startup time.</p> ");
         setPropertyDescriptorDefault(currentResult, "RUNNING");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("StartupTimeout")) {
         getterName = "getStartupTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStartupTimeout";
         }

         currentResult = new PropertyDescriptor("StartupTimeout", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("StartupTimeout", currentResult);
         currentResult.setValue("description", "<p>Timeout value for server start and resume operations. If the server fails to start in the timeout period, it will force shutdown.</p>  <p>A value of <code>0</code> means that the server will wait indefinitely for the operation to complete.</p> ");
         currentResult.setValue("restProductionModeDefault", new Integer(0));
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("StdoutFormat")) {
         getterName = "getStdoutFormat";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStdoutFormat";
         }

         currentResult = new PropertyDescriptor("StdoutFormat", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("StdoutFormat", currentResult);
         currentResult.setValue("description", "<p>The output format to use when logging to the console.</p> ");
         setPropertyDescriptorDefault(currentResult, "standard");
         currentResult.setValue("legalValues", new Object[]{"standard", "noid"});
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("StdoutSeverityLevel")) {
         getterName = "getStdoutSeverityLevel";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStdoutSeverityLevel";
         }

         currentResult = new PropertyDescriptor("StdoutSeverityLevel", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("StdoutSeverityLevel", currentResult);
         currentResult.setValue("description", "<p>The minimum severity of a message that the server sends to standard out. (Requires you to enable sending messages to standard out.)</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#isStdoutEnabled")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(32));
         currentResult.setValue("secureValue", new Integer(32));
         currentResult.setValue("legalValues", new Object[]{new Integer(256), new Integer(128), new Integer(64), new Integer(16), new Integer(8), new Integer(32), new Integer(4), new Integer(2), new Integer(1), new Integer(0)});
         currentResult.setValue("deprecated", "9.0.0.0 Replaced by LogMBean.StdoutSeverity.  For backward compatibility the changes to this attribute will be  propagated to the LogMBean. ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (!descriptors.containsKey("SystemPasswordEncrypted")) {
         getterName = "getSystemPasswordEncrypted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSystemPasswordEncrypted";
         }

         currentResult = new PropertyDescriptor("SystemPasswordEncrypted", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("SystemPasswordEncrypted", currentResult);
         currentResult.setValue("description", "<p>The password required to access administrative functions on this server.</p>  <p>To set this attribute,  use <code>weblogic.management.EncryptionHelper.encrypt()</code> to encrypt the value. Then set this attribute to the output of the encrypt() method.</p>  <p>To compare a password that a user enters with the encrypted value of this attribute, go to the same WebLogic Server instance that you used to set and encrypt this attribute and use <code>weblogic.management.EncryptionHelper.encrypt()</code> to encrypt the user-supplied password. Then compare the encrypted values.</p> ");
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Tags")) {
         getterName = "getTags";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTags";
         }

         currentResult = new PropertyDescriptor("Tags", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("Tags", currentResult);
         currentResult.setValue("description", "<p>Return all tags on this Configuration MBean</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("TransactionLogFilePrefix")) {
         getterName = "getTransactionLogFilePrefix";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTransactionLogFilePrefix";
         }

         currentResult = new PropertyDescriptor("TransactionLogFilePrefix", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("TransactionLogFilePrefix", currentResult);
         currentResult.setValue("description", "<p>The path prefix for the server's JTA transaction log files. If the pathname is not absolute, the path is assumed to be relative to the server's root directory.</p>  <p>For a clustered server, if you plan to be able to migrate the Transaction Recovery Service from this server if it fails to another server (backup server) in the same cluster, you must store transaction log files on persistent storage, such as a Storage Area Network (SAN) device or a dual-ported disk, available to both servers.</p>  <p>Do not use an NFS file system to store transaction log files. Because of the caching scheme in NFS, transaction log files on disk may not always be current. Using transaction log files stored on an NFS device for recovery may cause data corruption. </p> ");
         setPropertyDescriptorDefault(currentResult, "./");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TransactionLogFileWritePolicy")) {
         getterName = "getTransactionLogFileWritePolicy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTransactionLogFileWritePolicy";
         }

         currentResult = new PropertyDescriptor("TransactionLogFileWritePolicy", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("TransactionLogFileWritePolicy", currentResult);
         currentResult.setValue("description", "<p>The policy that determines how transaction log file entries are written to disk. This policy can affect transaction performance. (Note: To be transactionally safe, the Direct-Write policy may require additional OS or environment changes on some Windows systems.)</p>  <p>WebLogic Server supports the following policies:</p>  <ul> <li> <p>Cache-Flush. Flushes operating system and on-disk caches after each write.</p> </li>  <li> <p>Direct-Write. Tells the operating system to write directly to disk with each write. Direct-Write performs better than Cache-Flush.</p> </li> </ul>  <p>If Direct-Write is not supported on the host platform, the policy becomes Cache-Flush and a log message is printed.</p>  <p><b>Note</b>: On Windows, the \"Direct-Write\" policy may leave transaction data in the on-disk cache without writing it to disk immediately. This is not transactionally safe because a power failure can cause loss of on-disk cache data. For transactionally safe writes using \"Direct-Write\" on Windows, either disable all write caching for the disk (enabled by default), or use a disk with a battery-backed cache.</p>  <p>The on-disk cache for a hard-drive on Windows can be disabled through system administration: Control-Panel -&gt; System -&gt; Hardware-tab -&gt; Device-Manager-button -&gt; Disk-Drives -&gt; name-of-drive -&gt; Policies-tab -&gt; \"Enable write caching on the disk\" check-box. Some file systems do not allow this value to be changed. For example, a RAID system that has a reliable cache.</p> ");
         setPropertyDescriptorDefault(currentResult, "Direct-Write");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("legalValues", new Object[]{"Cache-Flush", "Direct-Write"});
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TransactionLogJDBCStore")) {
         getterName = "getTransactionLogJDBCStore";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionLogJDBCStore", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("TransactionLogJDBCStore", currentResult);
         currentResult.setValue("description", "<p>The JDBC TLOG store used for transaction logging. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.4.0", (String)null, this.targetVersion) && !descriptors.containsKey("TransactionPrimaryChannelName")) {
         getterName = "getTransactionPrimaryChannelName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTransactionPrimaryChannelName";
         }

         currentResult = new PropertyDescriptor("TransactionPrimaryChannelName", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("TransactionPrimaryChannelName", currentResult);
         currentResult.setValue("description", "<p>Returns the name of the server network channel to derive the default URL used for internal JTA communication with this server.</p> ");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.4.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.4.0", (String)null, this.targetVersion) && !descriptors.containsKey("TransactionPublicChannelName")) {
         getterName = "getTransactionPublicChannelName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTransactionPublicChannelName";
         }

         currentResult = new PropertyDescriptor("TransactionPublicChannelName", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("TransactionPublicChannelName", currentResult);
         currentResult.setValue("description", "<p>Returns the name of the server network channel to derive the public URL used for internal JTA communication with this server.</p> ");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.4.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.4.0", (String)null, this.targetVersion) && !descriptors.containsKey("TransactionPublicSecureChannelName")) {
         getterName = "getTransactionPublicSecureChannelName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTransactionPublicSecureChannelName";
         }

         currentResult = new PropertyDescriptor("TransactionPublicSecureChannelName", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("TransactionPublicSecureChannelName", currentResult);
         currentResult.setValue("description", "<p>Returns the name of the server network channel to derive the public secure URL used for internal JTA communication with this server.</p> ");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.4.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.4.0", (String)null, this.targetVersion) && !descriptors.containsKey("TransactionSecureChannelName")) {
         getterName = "getTransactionSecureChannelName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTransactionSecureChannelName";
         }

         currentResult = new PropertyDescriptor("TransactionSecureChannelName", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("TransactionSecureChannelName", currentResult);
         currentResult.setValue("description", "<p>Returns the name of the server network channel to derive the secure URL used for internal JTA communication with this server.</p> ");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.4.0");
      }

      if (!descriptors.containsKey("TunnelingClientPingSecs")) {
         getterName = "getTunnelingClientPingSecs";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTunnelingClientPingSecs";
         }

         currentResult = new PropertyDescriptor("TunnelingClientPingSecs", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("TunnelingClientPingSecs", currentResult);
         currentResult.setValue("description", "<p>The interval (in seconds) at which to ping a tunneled client to see if it is still alive.</p>  <p>If you create network channels for this server, each channel can override this setting.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.NetworkAccessPointMBean#getTunnelingClientPingSecs")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(45));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TunnelingClientTimeoutSecs")) {
         getterName = "getTunnelingClientTimeoutSecs";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTunnelingClientTimeoutSecs";
         }

         currentResult = new PropertyDescriptor("TunnelingClientTimeoutSecs", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("TunnelingClientTimeoutSecs", currentResult);
         currentResult.setValue("description", "<p>The amount of time (in seconds) after which a missing tunneled client is considered dead.</p>  <p>If you create network channels for this server, each channel can override this setting.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.NetworkAccessPointMBean#getTunnelingClientTimeoutSecs")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(40));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UploadDirectoryName")) {
         getterName = "getUploadDirectoryName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUploadDirectoryName";
         }

         currentResult = new PropertyDescriptor("UploadDirectoryName", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("UploadDirectoryName", currentResult);
         currentResult.setValue("description", "<p>The directory path on the Administration Server where all uploaded applications are placed.</p>  <p>If an absolute directory name is not specified, the path is relative to the root directory \"/\". The default staging directory is \"stage\", relative to the server root. On the Managed Server this returns null, and is not configurable.</p> ");
         currentResult.setValue("secureValue", "An absolute directory that is outside the root directory of any  WebLogic Server instance or application, and that resides on a physical disk  that is separate from the WebLogic Server host's system disk.");
         currentResult.setValue("secureValueDocOnly", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("VerboseEJBDeploymentEnabled")) {
         getterName = "getVerboseEJBDeploymentEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setVerboseEJBDeploymentEnabled";
         }

         currentResult = new PropertyDescriptor("VerboseEJBDeploymentEnabled", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("VerboseEJBDeploymentEnabled", currentResult);
         currentResult.setValue("description", "<p>Whether or not verbose deployment of EJBs is enabled.</p> ");
         setPropertyDescriptorDefault(currentResult, "false");
         currentResult.setValue("deprecated", "Deprecated as of 10.3.3.0 in favor of {@link ServerDebugMBean#getDebugEjbDeployment()} ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.2.0", (String)null, this.targetVersion) && !descriptors.containsKey("VirtualMachineName")) {
         getterName = "getVirtualMachineName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setVirtualMachineName";
         }

         currentResult = new PropertyDescriptor("VirtualMachineName", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("VirtualMachineName", currentResult);
         currentResult.setValue("description", "When WLS is running on JRVE, this specifies the name of the virtual machine running this server ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("deprecated", "12.1.3.0 ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.2.0");
      }

      if (!descriptors.containsKey("WebServer")) {
         getterName = "getWebServer";
         setterName = null;
         currentResult = new PropertyDescriptor("WebServer", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("WebServer", currentResult);
         currentResult.setValue("description", "<p>Returns the web server for this server. A server has exactly one WebServer. A server may also have one or more VirtualHosts. A VirtualHost is a subclass of WebServer.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("9.2.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("WebService")) {
         getterName = "getWebService";
         setterName = null;
         currentResult = new PropertyDescriptor("WebService", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("WebService", currentResult);
         currentResult.setValue("description", "<p>Gets Web service configuration for this server</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.2.0.0");
      }

      if (!descriptors.containsKey("XMLEntityCache")) {
         getterName = "getXMLEntityCache";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setXMLEntityCache";
         }

         currentResult = new PropertyDescriptor("XMLEntityCache", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("XMLEntityCache", currentResult);
         currentResult.setValue("description", "<p>The server's XML entity cache, which is used to configure the behavior of JAXP (Java API for XML Parsing).</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("XMLEntityCacheMBean")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("XMLRegistry")) {
         getterName = "getXMLRegistry";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setXMLRegistry";
         }

         currentResult = new PropertyDescriptor("XMLRegistry", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("XMLRegistry", currentResult);
         currentResult.setValue("description", "<p>The server's XML registry, which is used to configure the behavior of JAXP (Java API for XML Parsing).</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("XMLRegistryMBean")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AdministrationPortEnabled")) {
         getterName = "isAdministrationPortEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAdministrationPortEnabled";
         }

         currentResult = new PropertyDescriptor("AdministrationPortEnabled", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("AdministrationPortEnabled", currentResult);
         currentResult.setValue("description", "<p>Indicates whether or not administration port is enabled for the server. This field is derived from the DomainMBean and has no setter here All the server (7.0 and later) in s single domain should either have an administration port or not The administration port uses SSL, so SSL must be configured and enabled properly for it to be active.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getAdministrationPort"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.DomainMBean#isAdministrationPortEnabled"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.DomainMBean#getAdministrationPort"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.KernelMBean#getSSL")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AutoMigrationEnabled")) {
         getterName = "isAutoMigrationEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAutoMigrationEnabled";
         }

         currentResult = new PropertyDescriptor("AutoMigrationEnabled", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("AutoMigrationEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether Node Manager can automatically restart this server and its services on another machine if the server fails.</p> ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("BuzzEnabled")) {
         getterName = "isBuzzEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBuzzEnabled";
         }

         currentResult = new PropertyDescriptor("BuzzEnabled", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("BuzzEnabled", currentResult);
         currentResult.setValue("description", "<p>Enables Buzz for sending/receiving messages with peers. Enabling this attribute increases efficiency during I/O in environments with high network throughput and should be used only when configuring a WebLogic domain for Oracle Exalogic.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("COMEnabled")) {
         getterName = "isCOMEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCOMEnabled";
         }

         currentResult = new PropertyDescriptor("COMEnabled", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("COMEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether COM support is enabled on the regular (non-SSL) port. COM is not supported on the SSL port. (The remaining fields on this page are relevant only if you check this box.)</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getListenPort")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(false));
         currentResult.setValue("deprecated", "12.2.1.2.0 ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ClasspathServletDisabled")) {
         getterName = "isClasspathServletDisabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setClasspathServletDisabled";
         }

         currentResult = new PropertyDescriptor("ClasspathServletDisabled", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("ClasspathServletDisabled", currentResult);
         currentResult.setValue("description", "<p>The ClasspathServlet will serve any class file in the classpath and is registered by default in every Web application (including management). It does not need to be turned on for many applications though, and represents a security hole if unchecked.</p>  <p>WLS components such as JDBC and JMS rely on the ClasspathServlet. If the servlet can not be disabled, then set the ClassPathServletSecureModeEnabled to true.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#isClasspathServletSecureModeEnabled()")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(true));
         currentResult.setValue("secureValueDocOnly", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ClasspathServletSecureModeEnabled")) {
         getterName = "isClasspathServletSecureModeEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setClasspathServletSecureModeEnabled";
         }

         currentResult = new PropertyDescriptor("ClasspathServletSecureModeEnabled", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("ClasspathServletSecureModeEnabled", currentResult);
         currentResult.setValue("description", "<p>If secure mode is enabled, the ClasspathServlet will serve only class files from wellknown packages required for JDBC and JMS functionality. If secure mode is disabled, it represents a security hole if unchecked as the ClassPatchServlet will serve any class in the classpath and is registered by default in every Web application (including management).</p>  <p> If set to true, the ClasspathServlet will not be disabled by the DefaultInternalServletsDisabled value of true. </p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(true));
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.4.0", (String)null, this.targetVersion) && !descriptors.containsKey("CleanupOrphanedSessionsEnabled")) {
         getterName = "isCleanupOrphanedSessionsEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCleanupOrphanedSessionsEnabled";
         }

         currentResult = new PropertyDescriptor("CleanupOrphanedSessionsEnabled", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("CleanupOrphanedSessionsEnabled", currentResult);
         currentResult.setValue("description", "Indicates if replication service should cleanup orphaned http and ejb sessions. Orphaned sessions are http and ejb session instances that are not referenced by the replication service. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.4.0");
      }

      if (!descriptors.containsKey("ClientCertProxyEnabled")) {
         getterName = "isClientCertProxyEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setClientCertProxyEnabled";
         }

         currentResult = new PropertyDescriptor("ClientCertProxyEnabled", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("ClientCertProxyEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the <code>HttpClusterServlet</code> proxies the client certificate in a special header.</p>  <p>By default (or if you specify <code>false</code>), the <code>weblogic.xml</code> deployment descriptor for each web application that is deployed on this server determines whether the web application trusts certificates sent from the proxy server plugin. By default (or if the deployment descriptor specifies <code>false</code>), users cannot log in to the web application from a proxy server plugin.</p>  <p>A value of <code>true</code> causes proxy-server plugins to pass identity certifications from clients to all web applications that are deployed on this server instance. A proxy-server plugin encodes each identify certification in the <code>WL-Proxy-Client-Cert</code> header and passes the header to WebLogic Server instances. A WebLogic Server instance takes the certificate information from the header, trusting that it came from a secure source, and uses that information to authenticate the user.</p>  <p>If you specify <code>true</code>, use a <code>weblogic.security.net.ConnectionFilter</code> to ensure that this WebLogic Server instance accepts connections only from the machine on which the proxy-server plugin is running. Specifying <code>true</code> without using a connection filter creates a security vulnerability because the <code>WL-Proxy-Client-Cert</code> header can be spoofed.</p>  <p>A cluster can also specify whether the <code>HttpClusterServlet</code> proxies the client certificate in a special header. The cluster-level setting overrides the setting in individual servers that are part of the cluster.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.security.net.ConnectionFilter"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.WebAppContainerMBean#isClientCertProxyEnabled()"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.ClusterMBean#isClientCertProxyEnabled()")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConsoleInputEnabled")) {
         getterName = "isConsoleInputEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConsoleInputEnabled";
         }

         currentResult = new PropertyDescriptor("ConsoleInputEnabled", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("ConsoleInputEnabled", currentResult);
         currentResult.setValue("description", "<p>True if commands can be typed at console. REMOVE?</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultInternalServletsDisabled")) {
         getterName = "isDefaultInternalServletsDisabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultInternalServletsDisabled";
         }

         currentResult = new PropertyDescriptor("DefaultInternalServletsDisabled", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("DefaultInternalServletsDisabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether all default servlets in the servlet engine are disabled.</p>  <p>This includes: weblogic.servlet.ClasspathServlet weblogic.servlet.utils.iiop.GetIORServlet weblogic.rjvm.http.TunnelSendServlet weblogic.rjvm.http.TunnelRecvServlet weblogic.rjvm.http.TunnelLoginServlet weblogic.rjvm.http.TunnelCloseServlet If set to true, this property overrides the ClasspathServletDisabled property.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(true));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DynamicallyCreated")) {
         getterName = "isDynamicallyCreated";
         setterName = null;
         currentResult = new PropertyDescriptor("DynamicallyCreated", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("DynamicallyCreated", currentResult);
         currentResult.setValue("description", "<p>Return whether the MBean was created dynamically or is persisted to config.xml</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("transient", Boolean.TRUE);
      }

      if (!descriptors.containsKey("HttpTraceSupportEnabled")) {
         getterName = "isHttpTraceSupportEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setHttpTraceSupportEnabled";
         }

         currentResult = new PropertyDescriptor("HttpTraceSupportEnabled", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("HttpTraceSupportEnabled", currentResult);
         currentResult.setValue("description", "<p>Returns the HttpTraceSupportEnabled value</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.WebAppContainerMBean#isHttpTraceSupportEnabled()"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.ClusterMBean#isHttpTraceSupportEnabled()")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HttpdEnabled")) {
         getterName = "isHttpdEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setHttpdEnabled";
         }

         currentResult = new PropertyDescriptor("HttpdEnabled", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("HttpdEnabled", currentResult);
         currentResult.setValue("description", "<p>Whether or not HTTP support is enabled on the regular port or SSL port.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getListenPort"), BeanInfoHelper.encodeEntities("#isTunnelingEnabled"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.SSLMBean#getListenPort"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.KernelMBean#getSSL")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IIOPEnabled")) {
         getterName = "isIIOPEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIIOPEnabled";
         }

         currentResult = new PropertyDescriptor("IIOPEnabled", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("IIOPEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether this server has IIOP support enabled for both the regular (non-SSL) and SSL ports.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getListenPort"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.SSLMBean#getListenPort"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.KernelMBean#getSSL")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IgnoreSessionsDuringShutdown")) {
         getterName = "isIgnoreSessionsDuringShutdown";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIgnoreSessionsDuringShutdown";
         }

         currentResult = new PropertyDescriptor("IgnoreSessionsDuringShutdown", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("IgnoreSessionsDuringShutdown", currentResult);
         currentResult.setValue("description", "<p>Indicates whether a graceful shutdown operation drops all HTTP sessions immediately.</p> <p>If this is set to <code>false</code>, a graceful shutdown operation waits for HTTP sessions to complete or timeout.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JDBCLoggingEnabled")) {
         getterName = "isJDBCLoggingEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJDBCLoggingEnabled";
         }

         currentResult = new PropertyDescriptor("JDBCLoggingEnabled", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("JDBCLoggingEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether this server maintains a JDBC log file.</p> ");
         currentResult.setValue("deprecated", "9.0.0.0 Driver level debugging is not supported ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JMSDefaultConnectionFactoriesEnabled")) {
         getterName = "isJMSDefaultConnectionFactoriesEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJMSDefaultConnectionFactoriesEnabled";
         }

         currentResult = new PropertyDescriptor("JMSDefaultConnectionFactoriesEnabled", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("JMSDefaultConnectionFactoriesEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether this server uses JMS default connection factories.</p>  <p>WebLogic Server provides the following JMS default connection factories:</p>  <ul> <li> <code>weblogic.jms.ConnectionFactory</code> </li>  <li> <code>weblogic.jms.XAConnectionFactory</code> An XA factory is required for JMS applications to use JTA user-transactions, but is not required for transacted sessions. All other preconfigured attributes for the default connection factories are set to the same default values as a user-defined connection factory. If the preconfigured settings of the default factories are appropriate for your application, you do not need to configure any additional factories for your application. </li> </ul> <p> <b>Note:</b> When using the default connection factories, you have no control over targeting the WebLogic Server instances where the connection factory may be deployed. However, you can disable the default connection factories on a per-server basis. To deploy a connection factory on independent servers, on specific servers within a cluster, or on an entire cluster, you need to configure a connection factory and specify the appropriate server targets. The default Java EE 7 JMS Connection Factory, java:comp/DefaultJMSConnectionFactory, will be available even when the WebLogic JMS default connection factories are disabled.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (BeanInfoHelper.isVersionCompliant("7.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ListenPortEnabled")) {
         getterName = "isListenPortEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setListenPortEnabled";
         }

         currentResult = new PropertyDescriptor("ListenPortEnabled", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("ListenPortEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether this server can be reached through the default plain-text (non-SSL) listen port.</p>  <p>If you disable this listen port, you must enable the default SSL listen port.</p>  <p>You can define additional listen ports for this server by configuring network channels.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getListenPort"), BeanInfoHelper.encodeEntities("#isAdministrationPortEnabled"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.KernelMBean#getSSL"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.SSLMBean#isListenPortEnabled"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.NetworkAccessPointMBean#isEnabled")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("secureValue", new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "7.0.0.0");
      }

      if (!descriptors.containsKey("MSIFileReplicationEnabled")) {
         getterName = "isMSIFileReplicationEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMSIFileReplicationEnabled";
         }

         currentResult = new PropertyDescriptor("MSIFileReplicationEnabled", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("MSIFileReplicationEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the Administration Server replicates its configuration files to this Managed Server.</p>  <p>With file replication enabled, the Administration Server copies its configuration file and <code>SerializedSystemIni.dat</code> into the Managed Server's root directory every 5 minutes. This option does not replicate a boot identity file.</p>  <p>Regardless of the name of the configuration file that you used to start the Administration Server, the replicated file is always named <code>msi-config.xml</code>. For example, if you specified <code>-Dweblogic.ConfigFile=MyConfig.xml</code> when you started the Administration Server, if you have enabled file replication, the Administration Server copies <code>MyConfig.xml</code> and names the copy <code>msi-config.xml</code>.</p>  <p>Depending on your backup schemes and the frequency with which you update your domain's configuration, this option might not be worth the performance cost of copying potentially large files across a network.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(false));
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ManagedServerIndependenceEnabled")) {
         getterName = "isManagedServerIndependenceEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setManagedServerIndependenceEnabled";
         }

         currentResult = new PropertyDescriptor("ManagedServerIndependenceEnabled", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("ManagedServerIndependenceEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether this Managed Server can be started when the Administration Server is unavailable.</p>  <p>In such a case, the Managed Server retrieves its configuration by reading a configuration file and other files directly.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("secureValue", new Boolean(true));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessageIdPrefixEnabled")) {
         getterName = "isMessageIdPrefixEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMessageIdPrefixEnabled";
         }

         currentResult = new PropertyDescriptor("MessageIdPrefixEnabled", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("MessageIdPrefixEnabled", currentResult);
         currentResult.setValue("description", "<p>Indicates whether message IDs in logged messages will include a prefix. Message ids are 6 digit numeric strings that can be optionally presented in a log entry with a prefix. The prefix used by server messages is \"BEA-\".</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.4.0", (String)null, this.targetVersion) && !descriptors.containsKey("SessionReplicationOnShutdownEnabled")) {
         getterName = "isSessionReplicationOnShutdownEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSessionReplicationOnShutdownEnabled";
         }

         currentResult = new PropertyDescriptor("SessionReplicationOnShutdownEnabled", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("SessionReplicationOnShutdownEnabled", currentResult);
         currentResult.setValue("description", "Indicates if session replication on server shutdown is enabled. During server shutdown, any active http and ejb stateful session states will be replicated to a secondary server (if available) in the cluster. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.4.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.3.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("SitConfigRequired")) {
         getterName = "isSitConfigRequired";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSitConfigRequired";
         }

         currentResult = new PropertyDescriptor("SitConfigRequired", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("SitConfigRequired", currentResult);
         currentResult.setValue("description", "Returns whether situational config files are required and WebLogic Server should fail to boot if situational config files are not present. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.3.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("StdoutDebugEnabled")) {
         getterName = "isStdoutDebugEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStdoutDebugEnabled";
         }

         currentResult = new PropertyDescriptor("StdoutDebugEnabled", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("StdoutDebugEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the server sends messages of the <code>DEBUG</code> severity to standard out in addition to the log file. (Requires you to enable sending messages to standard out.)</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(false));
         currentResult.setValue("deprecated", "9.0.0.0 replaced by LogMBean.StdoutSeverity For backward compatibility the changes to this attribute will be propagated to the LogMBean. ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("StdoutEnabled")) {
         getterName = "isStdoutEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStdoutEnabled";
         }

         currentResult = new PropertyDescriptor("StdoutEnabled", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("StdoutEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the server sends messages to standard out in addition to the log file.</p>  <p>Other settings configure the minimum severity of a message that the server sends to standard out.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#isStdoutDebugEnabled"), BeanInfoHelper.encodeEntities("#getStdoutSeverityLevel")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("deprecated", "9.0.0.0 replaced by LogMBean.StdoutSeverity, for backward compatibility the changes to this attribute will be propagated to the LogMBean. ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("StdoutLogStack")) {
         getterName = "isStdoutLogStack";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStdoutLogStack";
         }

         currentResult = new PropertyDescriptor("StdoutLogStack", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("StdoutLogStack", currentResult);
         currentResult.setValue("description", "<p>Specifies whether to dump stack traces to the console when included in logged message.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("deprecated", "9.0.0.0 ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (!descriptors.containsKey("TGIOPEnabled")) {
         getterName = "isTGIOPEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTGIOPEnabled";
         }

         currentResult = new PropertyDescriptor("TGIOPEnabled", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("TGIOPEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether this server supports Tuxedo GIOP (TGIOP) requests. (Requires you to configure WebLogic Tuxedo Connector (WTC) for this server.)</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("secureValue", new Boolean(true));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TunnelingEnabled")) {
         getterName = "isTunnelingEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTunnelingEnabled";
         }

         currentResult = new PropertyDescriptor("TunnelingEnabled", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("TunnelingEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether tunneling for the T3, T3S, HTTP, HTTPS, IIOP, and IIOPS protocols should be enabled for this server.</p>  <p>If you create network channels for this server, each channel can override this setting.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#isHttpdEnabled"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.NetworkAccessPointMBean#isTunnelingEnabled")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UseFusionForLLR")) {
         getterName = "isUseFusionForLLR";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUseFusionForLLR";
         }

         currentResult = new PropertyDescriptor("UseFusionForLLR", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("UseFusionForLLR", currentResult);
         currentResult.setValue("description", "<p>Enables the use of the <code>ADM_DDL </code> store procedure for LLR. The default value is <code>false</code> (not enabled). </p> <p>When enabled, a  <code>WLS_</code> prefix and <code>_DYD</code> suffix is is automatically added to the LLR table name at runtime so the LLR table name in server configuration is not consistent with the actual table name in database.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WeblogicPluginEnabled")) {
         getterName = "isWeblogicPluginEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setWeblogicPluginEnabled";
         }

         currentResult = new PropertyDescriptor("WeblogicPluginEnabled", ServerTemplateMBean.class, getterName, setterName);
         descriptors.put("WeblogicPluginEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether this server uses the proprietary <code>WL-Proxy-Client-IP</code> header.</p>  <p>Set this attribute to <code>true</code> to specify that this server instance uses the proprietary <code>WL-Proxy-Client-IP</code> header, which is recommended if the server instance will receive requests from a proxy plug-in. If the server instance is a member of a cluster that will receive proxied requests, enable the WebLogic plug-in at the cluster level. For servers that are members of a cluster, the setting at the cluster level overrides the server's setting. When the WebLogic plug-in is enabled at the cluster level, a call to <code>getRemoteAddr</code> will return the address of the browser client from the proprietary <code>WL-Proxy-Client-IP</code> header instead of the Web server.</p>  <p>Set this attrbute to <code>false</code> to disable the <code>weblogic-plugin-enabled</code> parameter, <code>weblogic-plugin-enabled=false</code>, in the <code>config.xml</code> file.</p>  <p>Note: If you are using Oracle HTTP Server, the WebLogic Proxy Plug-In, or Oracle Traffic Director to distribute client requests to a Managed Server or a cluster, Oracle recommends setting this attribute to <code>true</code>.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.WebAppContainerMBean#isWeblogicPluginEnabled()"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.ClusterMBean#isWeblogicPluginEnabled()")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ServerTemplateMBean.class.getMethod("createConfigurationProperty", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "of ConfigurationProperty ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Create ConfigurationProperty object on the container</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ConfigurationProperties");
      }

      mth = ServerTemplateMBean.class.getMethod("destroyConfigurationProperty", ConfigurationPropertyMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("wsc", "ConfigurationProperty mbean ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Destroy ConfigurationProperty object on the container</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ConfigurationProperties");
      }

      if (BeanInfoHelper.isVersionCompliant("7.0.0.0", (String)null, this.targetVersion)) {
         mth = ServerTemplateMBean.class.getMethod("createNetworkAccessPoint", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "7.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Factory to create a new NetworkAccessPoint instance for this Server.</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "NetworkAccessPoints");
            currentResult.setValue("since", "7.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("7.0.0.0", (String)null, this.targetVersion)) {
         mth = ServerTemplateMBean.class.getMethod("destroyNetworkAccessPoint", NetworkAccessPointMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("accessPoint", "to be destroyed ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "7.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Destroys a NetworkAccessPoint object.</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "NetworkAccessPoints");
            currentResult.setValue("since", "7.0.0.0");
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
         mth = ServerTemplateMBean.class.getMethod("addTag", String.class);
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

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ServerTemplateMBean.class.getMethod("removeTag", String.class);
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

      if (BeanInfoHelper.isVersionCompliant("7.0.0.0", (String)null, this.targetVersion)) {
         mth = ServerTemplateMBean.class.getMethod("addNetworkAccessPoint", NetworkAccessPointMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("networkAccessPoint", "The feature to be added to the NetworkAccessPoint attribute ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "7.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getNetworkAccessPoints")};
            currentResult.setValue("see", seeObjectArray);
            currentResult.setValue("role", "collection");
            currentResult.setValue("property", "NetworkAccessPoints");
            currentResult.setValue("since", "7.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("7.0.0.0", (String)null, this.targetVersion)) {
         mth = ServerTemplateMBean.class.getMethod("removeNetworkAccessPoint", NetworkAccessPointMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("networkAccessPoint", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "7.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getNetworkAccessPoints")};
            currentResult.setValue("see", seeObjectArray);
            currentResult.setValue("role", "collection");
            currentResult.setValue("property", "NetworkAccessPoints");
            currentResult.setValue("since", "7.0.0.0");
         }
      }

   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ServerTemplateMBean.class.getMethod("lookupConfigurationProperty", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "name of ConfigurationProperty ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Look up ConfigurationProperty object</p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "ConfigurationProperties");
      }

      if (BeanInfoHelper.isVersionCompliant("7.0.0.0", (String)null, this.targetVersion)) {
         mth = ServerTemplateMBean.class.getMethod("lookupNetworkAccessPoint", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the key of the network access point. ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "7.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Look up an NetworkAccessPoint by name</p> ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "NetworkAccessPoints");
            currentResult.setValue("since", "7.0.0.0");
         }
      }

   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ServerTemplateMBean.class.getMethod("freezeCurrentValue", String.class);
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

      mth = ServerTemplateMBean.class.getMethod("restoreDefaultValue", String.class);
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

      mth = ServerTemplateMBean.class.getMethod("synchronousStart");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         currentResult.setValue("deprecated", "9.0.0.0 Use {@link weblogic.management.runtime.ServerLifeCycleRuntimeMBean#start()} instead. ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Start this server. This is a blocking call. Returns String containing NodeManger log for starting the server.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = ServerTemplateMBean.class.getMethod("synchronousKill");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         currentResult.setValue("deprecated", "9.0.0.0 Use {@link weblogic.management.runtime.ServerRuntimeMBean#forceShutdown()} instead ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Kill this server. This is a blocking call. Returns String containing NodeManger log for killing the server.</p> ");
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
