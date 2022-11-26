package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class EmbeddedLDAPMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = EmbeddedLDAPMBean.class;

   public EmbeddedLDAPMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public EmbeddedLDAPMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.EmbeddedLDAPMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "7.0.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>The MBean that defines the configuration properties for the embedded LDAP server for the WebLogic domain.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.EmbeddedLDAPMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("BackupCopies")) {
         getterName = "getBackupCopies";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBackupCopies";
         }

         currentResult = new PropertyDescriptor("BackupCopies", EmbeddedLDAPMBean.class, getterName, setterName);
         descriptors.put("BackupCopies", currentResult);
         currentResult.setValue("description", "<p>The maximum number of backup copies that should be made for the embedded LDAP server.</p>  <p>This value limits the number of zip files in the ldap/backup directory.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(7));
         currentResult.setValue("legalMax", new Integer(65534));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BackupHour")) {
         getterName = "getBackupHour";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBackupHour";
         }

         currentResult = new PropertyDescriptor("BackupHour", EmbeddedLDAPMBean.class, getterName, setterName);
         descriptors.put("BackupHour", currentResult);
         currentResult.setValue("description", "<p>The hour at which the embedded LDAP server should be backed up.</p>  <p>The Backup Hour value is used in conjunction with the Backup Minute value to determine the time at which the embedded LDAP server data files are backed up. At the specified time, WebLogic Server suspends writes to the embedded LDAP server, backs up the data files into a zip files in the ldap/backup directory, and then resumes writes.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(23));
         currentResult.setValue("legalMax", new Integer(23));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BackupMinute")) {
         getterName = "getBackupMinute";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBackupMinute";
         }

         currentResult = new PropertyDescriptor("BackupMinute", EmbeddedLDAPMBean.class, getterName, setterName);
         descriptors.put("BackupMinute", currentResult);
         currentResult.setValue("description", "<p>The minute at which the embedded LDAP server should be backed up.</p>  <p>The Backup Minute value is used in conjunction with the Back Up Hour value to determine the time at which the embedded LDAP server data files are backed up</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(5));
         currentResult.setValue("legalMax", new Integer(59));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CacheSize")) {
         getterName = "getCacheSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCacheSize";
         }

         currentResult = new PropertyDescriptor("CacheSize", EmbeddedLDAPMBean.class, getterName, setterName);
         descriptors.put("CacheSize", currentResult);
         currentResult.setValue("description", "<p>The size of the cache (in kilobytes) that is used with the embedded LDAP server.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(32));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CacheTTL")) {
         getterName = "getCacheTTL";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCacheTTL";
         }

         currentResult = new PropertyDescriptor("CacheTTL", EmbeddedLDAPMBean.class, getterName, setterName);
         descriptors.put("CacheTTL", currentResult);
         currentResult.setValue("description", "<p>The time-to-live of the cache (in seconds) that is used with the embedded LDAP server.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(60));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Credential")) {
         getterName = "getCredential";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCredential";
         }

         currentResult = new PropertyDescriptor("Credential", EmbeddedLDAPMBean.class, getterName, setterName);
         descriptors.put("Credential", currentResult);
         currentResult.setValue("description", "<p>The credential (usually a password) used to connect to the embedded LDAP server.</p>  <p>If this credential has not been set, WebLogic Server generates a password at startup, initializes the attribute, and saves the configuration to the config.xml file. If you want to connect to the embedded LDAP server using an external LDAP browser and the embedded LDAP administrator account (cn=Admin), change this attribute from the generated value.</p> <p>As of 8.1 sp4, when you get the value of this attribute, WebLogic Server does the following:</p> <ol><li>Retrieves the value of the <code>CredentialEncrypted</code> attribute.</li> <li>Decrypts the value and returns the unencrypted password as a String.</li> </ol>  <p>When you set the value of this attribute, WebLogic Server does the following:</p> <ol><li>Encrypts the value.</li> <li>Sets the value of the <code>CredentialEncrypted</code> attribute to the encrypted value.</li> </ol> <p>Using the <code>Credential</code> attribute is a potential security risk because the String object (which contains the unencrypted password) remains in the JVM's memory until garbage collection removes it and the memory is reallocated. Depending on how memory is allocated in the JVM, a significant amount of time could pass before this unencrypted data is removed from memory.</p>  <p>Instead of using this attribute, use <code>CredentialEncrypted()</code>.</p> ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getCredentialEncrypted()")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CredentialEncrypted")) {
         getterName = "getCredentialEncrypted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCredentialEncrypted";
         }

         currentResult = new PropertyDescriptor("CredentialEncrypted", EmbeddedLDAPMBean.class, getterName, setterName);
         descriptors.put("CredentialEncrypted", currentResult);
         currentResult.setValue("description", "<p>The credential (usually password) used to connect to the embedded LDAP server.</p>  <p>If this credential has not been set, WebLogic Server generates a password at startup, initializes the attribute, and saves the configuration to the config.xml file. If you want to connect to the embedded LDAP server using an external LDAP browser and the embedded LDAP administrator account (cn=Admin), change this attribute from the generated value.</p> <p>To set this attribute, use <code>weblogic.management.EncryptionHelper.encrypt()</code> to encrypt the value. Then set this attribute to the output of the encrypt() method.</p>  <p>To compare a password that a user enters with the encrypted value of this attribute, go to the same WebLogic Server instance that you used to set and encrypt this attribute and use <code>weblogic.management.EncryptionHelper.encrypt()</code> to encrypt the user-supplied password. Then compare the encrypted values.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Timeout")) {
         getterName = "getTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTimeout";
         }

         currentResult = new PropertyDescriptor("Timeout", EmbeddedLDAPMBean.class, getterName, setterName);
         descriptors.put("Timeout", currentResult);
         currentResult.setValue("description", "<p>Specifies the maximum number of seconds to wait for results from the embedded LDAP server before timing out. If this option is set to 0, there is no maximum time limit.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AnonymousBindAllowed")) {
         getterName = "isAnonymousBindAllowed";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAnonymousBindAllowed";
         }

         currentResult = new PropertyDescriptor("AnonymousBindAllowed", EmbeddedLDAPMBean.class, getterName, setterName);
         descriptors.put("AnonymousBindAllowed", currentResult);
         currentResult.setValue("description", "Specifies whether the embedded LDAP server should allow anonymous connections. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CacheEnabled")) {
         getterName = "isCacheEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCacheEnabled";
         }

         currentResult = new PropertyDescriptor("CacheEnabled", EmbeddedLDAPMBean.class, getterName, setterName);
         descriptors.put("CacheEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether a cache is used with the embedded LDAP server.</p>  <p>This cache is used when a managed server is reading or writing to the master embedded LDAP server that is running on the Administration server.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("KeepAliveEnabled")) {
         getterName = "isKeepAliveEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setKeepAliveEnabled";
         }

         currentResult = new PropertyDescriptor("KeepAliveEnabled", EmbeddedLDAPMBean.class, getterName, setterName);
         descriptors.put("KeepAliveEnabled", currentResult);
         currentResult.setValue("description", "Whether keep alive is enabled in the socket connection ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MasterFirst")) {
         getterName = "isMasterFirst";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMasterFirst";
         }

         currentResult = new PropertyDescriptor("MasterFirst", EmbeddedLDAPMBean.class, getterName, setterName);
         descriptors.put("MasterFirst", currentResult);
         currentResult.setValue("description", "<p>Specifies whether a Managed Server should always connect to the master LDAP server (contained in the Administration Server), instead of connecting to the local replicated LDAP server (contained in the Managed Server).</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RefreshReplicaAtStartup")) {
         getterName = "isRefreshReplicaAtStartup";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRefreshReplicaAtStartup";
         }

         currentResult = new PropertyDescriptor("RefreshReplicaAtStartup", EmbeddedLDAPMBean.class, getterName, setterName);
         descriptors.put("RefreshReplicaAtStartup", currentResult);
         currentResult.setValue("description", "<p>Specifies whether a Managed Server should refresh all replicated data at boot time. (This is useful if you have made a large amount of changes when the Managed Server was not active, and you want to download the entire replica instead of having the Administration Server push each change to the Managed Server.)</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
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
