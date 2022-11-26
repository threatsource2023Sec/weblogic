package weblogic.management.utils;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.commo.AbstractCommoConfigurationBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class LDAPServerMBeanImplBeanInfo extends AbstractCommoConfigurationBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = LDAPServerMBean.class;

   public LDAPServerMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public LDAPServerMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.utils.LDAPServerMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("abstract", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.management.utils");
      String description = (new String("The LDAPServerMBean interface defines methods used to get/set the configuration attributes that are required to communicate with an external LDAP server. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.utils.LDAPServerMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("CacheSize")) {
         getterName = "getCacheSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCacheSize";
         }

         currentResult = new PropertyDescriptor("CacheSize", LDAPServerMBean.class, getterName, setterName);
         descriptors.put("CacheSize", currentResult);
         currentResult.setValue("description", "Returns the size of the cache in K. ");
         setPropertyDescriptorDefault(currentResult, new Integer(32));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CacheTTL")) {
         getterName = "getCacheTTL";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCacheTTL";
         }

         currentResult = new PropertyDescriptor("CacheTTL", LDAPServerMBean.class, getterName, setterName);
         descriptors.put("CacheTTL", currentResult);
         currentResult.setValue("description", "Returns the time-to-live (TTL) of the cache in seconds. ");
         setPropertyDescriptorDefault(currentResult, new Integer(60));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectTimeout")) {
         getterName = "getConnectTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnectTimeout";
         }

         currentResult = new PropertyDescriptor("ConnectTimeout", LDAPServerMBean.class, getterName, setterName);
         descriptors.put("ConnectTimeout", currentResult);
         currentResult.setValue("description", "Returns the maximum number of seconds to wait for the LDAP connection to be established. ");
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionPoolSize")) {
         getterName = "getConnectionPoolSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnectionPoolSize";
         }

         currentResult = new PropertyDescriptor("ConnectionPoolSize", LDAPServerMBean.class, getterName, setterName);
         descriptors.put("ConnectionPoolSize", currentResult);
         currentResult.setValue("description", "The LDAP connection pool size. Default is 6. ");
         setPropertyDescriptorDefault(currentResult, new Integer(6));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionRetryLimit")) {
         getterName = "getConnectionRetryLimit";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnectionRetryLimit";
         }

         currentResult = new PropertyDescriptor("ConnectionRetryLimit", LDAPServerMBean.class, getterName, setterName);
         descriptors.put("ConnectionRetryLimit", currentResult);
         currentResult.setValue("description", "Specifies the number of times to attempt to connect to the LDAP server if the initial connection failed. ");
         setPropertyDescriptorDefault(currentResult, new Integer(1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Credential")) {
         getterName = "getCredential";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCredential";
         }

         currentResult = new PropertyDescriptor("Credential", LDAPServerMBean.class, getterName, setterName);
         descriptors.put("Credential", currentResult);
         currentResult.setValue("description", "Returns the credential (generally a password) used to authenticate the LDAP user that is defined in the Principal attribute. ");
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

         currentResult = new PropertyDescriptor("CredentialEncrypted", LDAPServerMBean.class, getterName, setterName);
         descriptors.put("CredentialEncrypted", currentResult);
         currentResult.setValue("description", "Returns the credential (generally a password) used to authenticate the LDAP user that is defined in the Principal attribute. ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Host")) {
         getterName = "getHost";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setHost";
         }

         currentResult = new PropertyDescriptor("Host", LDAPServerMBean.class, getterName, setterName);
         descriptors.put("Host", currentResult);
         currentResult.setValue("description", "Returns the host name or IP address of the LDAP server. ");
         setPropertyDescriptorDefault(currentResult, "localhost");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ParallelConnectDelay")) {
         getterName = "getParallelConnectDelay";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setParallelConnectDelay";
         }

         currentResult = new PropertyDescriptor("ParallelConnectDelay", LDAPServerMBean.class, getterName, setterName);
         descriptors.put("ParallelConnectDelay", currentResult);
         currentResult.setValue("description", "<p>Returns the number of seconds to delay when making concurrent attempts to connect to multiple servers.</p>  <p>If set to 0, connection attempts are serialized. An attempt is made to connect to the first server in the list. The next entry in the list is tried only if the attempt to connect to the current host fails. This might cause your application to block for unacceptably long time if a host is down. If set to greater than 0, another connection setup thread is started after this number of delay seconds has passed.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Port")) {
         getterName = "getPort";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPort";
         }

         currentResult = new PropertyDescriptor("Port", LDAPServerMBean.class, getterName, setterName);
         descriptors.put("Port", currentResult);
         currentResult.setValue("description", "Returns the port number on which the LDAP server is listening. ");
         setPropertyDescriptorDefault(currentResult, new Integer(389));
         currentResult.setValue("legalMax", new Integer(65534));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Principal")) {
         getterName = "getPrincipal";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPrincipal";
         }

         currentResult = new PropertyDescriptor("Principal", LDAPServerMBean.class, getterName, setterName);
         descriptors.put("Principal", currentResult);
         currentResult.setValue("description", "Returns the Distinguished Name (DN) of the LDAP user that is used by WebLogic Server to connect to the LDAP server. ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ResultsTimeLimit")) {
         getterName = "getResultsTimeLimit";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setResultsTimeLimit";
         }

         currentResult = new PropertyDescriptor("ResultsTimeLimit", LDAPServerMBean.class, getterName, setterName);
         descriptors.put("ResultsTimeLimit", currentResult);
         currentResult.setValue("description", "Returns the maximum number of milliseconds to wait for results before timing out. If set to 0, there is no maximum time limit. ");
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BindAnonymouslyOnReferrals")) {
         getterName = "isBindAnonymouslyOnReferrals";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBindAnonymouslyOnReferrals";
         }

         currentResult = new PropertyDescriptor("BindAnonymouslyOnReferrals", LDAPServerMBean.class, getterName, setterName);
         descriptors.put("BindAnonymouslyOnReferrals", currentResult);
         currentResult.setValue("description", "Returns whether to anonymously bind when following referrals within the LDAP directory. If set to false, then the current Principal and Credential will be used. ");
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

         currentResult = new PropertyDescriptor("CacheEnabled", LDAPServerMBean.class, getterName, setterName);
         descriptors.put("CacheEnabled", currentResult);
         currentResult.setValue("description", "Returns whether to cache LDAP requests with the LDAP server. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FollowReferrals")) {
         getterName = "isFollowReferrals";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFollowReferrals";
         }

         currentResult = new PropertyDescriptor("FollowReferrals", LDAPServerMBean.class, getterName, setterName);
         descriptors.put("FollowReferrals", currentResult);
         currentResult.setValue("description", "Returns whether referrals will automatically be followed within the LDAP Directory. If set to false, then a Referral exception will be thrown when referrals are encountered during LDAP requests. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SSLEnabled")) {
         getterName = "isSSLEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSSLEnabled";
         }

         currentResult = new PropertyDescriptor("SSLEnabled", LDAPServerMBean.class, getterName, setterName);
         descriptors.put("SSLEnabled", currentResult);
         currentResult.setValue("description", "Returns whether SSL will be used to connect to the LDAP server. ");
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
