package weblogic.security.service.internal;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.security.legacy.ConfigHelperFactory;
import com.bea.common.security.legacy.InternalServicesConfigHelper;
import java.io.File;
import java.util.Properties;
import weblogic.ldap.EmbeddedLDAP;
import weblogic.management.DomainDir;
import weblogic.management.bootstrap.BootStrap;
import weblogic.management.configuration.EmbeddedLDAPMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.security.RDBMSSecurityStoreMBean;
import weblogic.management.security.RealmMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.utils.StringUtils;

public class WLSInternalServicesConfigHelper {
   private static final String STRING_DELIMITER = ",";
   private static final String PROPERTY_DELIMITER = "=";
   private static final String USERNAME = "Username";
   private static final String PASSWORD = "Password";
   private static final String JNDI_USERNAME = "java.naming.security.principal";
   private static final String JNDI_PASSWORD = "java.naming.security.credentials";
   private static final String CONNECTION_URL = "ConnectionURL";
   private static final String DRIVER_NAME = "DriverName";
   private static final String JMS_TOPIC = "JMSTopic";
   private static final String JMS_TOPIC_CONNECTION_FACTORY = "JMSTopicConnectionFactory";
   private static final String JMS_EXCEPTION_RECONNECT_ATTEMPTS = "JMSExceptionReconnectAttempts";

   private WLSInternalServicesConfigHelper() {
   }

   public static String getLDAPSSLSocketFactoryLookupServiceName(ClassLoader cssImplLoader, RealmMBean realmMBean) {
      return ConfigHelperFactory.getInstance(cssImplLoader).getInternalServicesConfigHelper(realmMBean).getLDAPSSLSocketFactoryLookupServiceName();
   }

   public static String getJAXPFactoryServiceName(ClassLoader cssImplLoader, RealmMBean realmMBean) {
      return ConfigHelperFactory.getInstance(cssImplLoader).getInternalServicesConfigHelper(realmMBean).getJAXPFactoryServiceName();
   }

   public static String getNamedSQLConnectionLookupServiceName(ClassLoader cssImplLoader, RealmMBean realmMBean) {
      return ConfigHelperFactory.getInstance(cssImplLoader).getInternalServicesConfigHelper(realmMBean).getNamedSQLConnectionLookupServiceName();
   }

   public static String getSAMLKeyServiceName(ClassLoader cssImplLoader, RealmMBean realmMBean) {
      return ConfigHelperFactory.getInstance(cssImplLoader).getInternalServicesConfigHelper(realmMBean).getSAMLKeyServiceName();
   }

   public static String getStoreServiceName(ClassLoader cssImplLoader, RealmMBean realmMBean) {
      return ConfigHelperFactory.getInstance(cssImplLoader).getInternalServicesConfigHelper(realmMBean).getStoreServiceName();
   }

   public static void addToConfig(ServiceEngineConfig serviceEngineConfig, ClassLoader cssImplLoader, String cssLifecycleImplLoaderName, String wlsLifecycleImplLoaderName, RealmMBean realmMBean, AuthenticatedSubject kernelId) {
      ConfigHelperFactory helperFactory = ConfigHelperFactory.getInstance(cssImplLoader);
      InternalServicesConfigHelper internalConfig = helperFactory.getInternalServicesConfigHelper(realmMBean);
      InternalServicesConfigHelper.JAXPFactoryServiceConfigCustomizer jaxpCustomizer = internalConfig.getJAXPFactoryServiceCustomizer();
      jaxpCustomizer.setDocumentBuilderFactoryClassName("weblogic.xml.jaxp.WebLogicDocumentBuilderFactory");
      jaxpCustomizer.setTransformerFactoryClassName("weblogic.xml.jaxp.WebLogicTransformerFactory");
      boolean isLDAP = configStoreService(realmMBean, internalConfig);
      InternalServicesConfigHelper.BootStrapServiceConfigCustomizer bootStrapCustomizer = internalConfig.getBootStrapServiceCustomizer();
      Properties bootStrapProperties = new Properties();
      String bootstrapVersioningDir = getBootstrapVersioningDir(isLDAP, kernelId);
      bootStrapProperties.setProperty("envrootdir", BootStrap.getWebLogicHome());
      bootStrapProperties.setProperty("securityrootdir", DomainDir.getSecurityDir());
      bootStrapProperties.setProperty("bootstrapversioningdir", bootstrapVersioningDir);
      bootStrapProperties.setProperty("tmpfilerootdir", ManagementService.getRuntimeAccess(kernelId).getDomain().getRootDirectory());
      bootStrapCustomizer.setBootStrapProperties(bootStrapProperties);
      InternalServicesConfigHelper.NamedSQLConnectionLookupServiceConfigCustomizer sqlCustomizer = internalConfig.getNamedSQLConnectionLookupServiceCustomizer();
      InternalServicesConfigHelper.LDAPSSLSocketFactoryLookupServiceConfigCustomizer ldapSSLCustomizer = internalConfig.getLDAPSSLSocketFactoryLookupServiceCustomizer();
      InternalServicesConfigHelper.SAMLKeyServiceConfigCustomizer samlCustomizer = internalConfig.getSAMLKeyServiceCustomizer();
      sqlCustomizer.removeService();
      samlCustomizer.removeService();
      ldapSSLCustomizer.removeService();
      internalConfig.addToConfig(serviceEngineConfig, cssLifecycleImplLoaderName);
      LDAPSSLSocketFactoryLookupServiceConfigHelper.addToConfig(serviceEngineConfig, cssImplLoader, wlsLifecycleImplLoaderName, realmMBean, kernelId);
      NamedSQLConnectionLookupServiceConfigHelper.addToConfig(serviceEngineConfig, cssImplLoader, wlsLifecycleImplLoaderName, realmMBean, kernelId);
      SAMLKeyServiceConfigHelper.addToConfig(serviceEngineConfig, cssImplLoader, wlsLifecycleImplLoaderName, realmMBean, kernelId);
   }

   private static boolean configStoreService(RealmMBean realmMBean, InternalServicesConfigHelper internalConfig) {
      InternalServicesConfigHelper.StoreServiceConfigCustomizer storeCustomizer = internalConfig.getStoreServiceCustomizer();
      Properties storeProperties = new Properties();
      Properties connectionProperties = new Properties();
      Properties notificationProperties = new Properties();
      RDBMSSecurityStoreMBean securityStore = realmMBean.getRDBMSSecurityStore();
      if (securityStore == null) {
         setEmbeddedLDAPProperties(realmMBean, storeProperties, connectionProperties, notificationProperties);
      } else {
         setRDBMSStoreProperties(securityStore, storeProperties, connectionProperties, notificationProperties);
      }

      storeCustomizer.setStoreProperties(storeProperties);
      storeCustomizer.setConnectionProperties(connectionProperties);
      storeCustomizer.setNotificationProperties(notificationProperties);
      return securityStore == null;
   }

   private static void setEmbeddedLDAPProperties(RealmMBean realmMBean, Properties storeProperties, Properties connectionProperties, Properties notificationProperties) {
      StringBuilder url = new StringBuilder();
      url.append("ldap");
      if (EmbeddedLDAP.getEmbeddedLDAPUseSSL()) {
         url.append('s');
      }

      url.append("://");
      url.append(EmbeddedLDAP.getEmbeddedLDAPHost());
      url.append(':');
      url.append(EmbeddedLDAP.getEmbeddedLDAPPort());
      storeProperties.setProperty("ConnectionURL", url.toString());
      storeProperties.setProperty("Username", "cn=admin");
      EmbeddedLDAP embLDAP = EmbeddedLDAP.getEmbeddedLDAP();
      EmbeddedLDAPMBean embMBean = embLDAP.getEmbeddedLDAPMBean();
      String cred = embMBean.getCredential();
      storeProperties.setProperty("Password", cred);
      storeProperties.setProperty("StoreServicePropertiesConfiguratorClassname", "com.bea.common.ldap.properties.LDAPStoreServicePropertiesConfigurator");
      storeProperties.setProperty("StoreServiceRemoteCommitProviderClassname", "com.bea.common.ldap.notification.LDAPRemoteCommitProvider");
      connectionProperties.put("embedded", "true");
      notificationProperties.put("baseDN", "dc=" + EmbeddedLDAP.getEmbeddedLDAPDomain());
      notificationProperties.put("realmName", realmMBean.getName());
   }

   private static void setRDBMSStoreProperties(RDBMSSecurityStoreMBean storeMBean, Properties storeProperties, Properties connectionProperties, Properties notificationProperties) {
      String driverName = storeMBean.getDriverName();
      String connectionURL = storeMBean.getConnectionURL();
      String username = storeMBean.getUsername();
      String password = storeMBean.getPassword();
      String jndiUsername = storeMBean.getJNDIUsername();
      String jndiPassword = storeMBean.getJNDIPassword();
      storeProperties.setProperty("DriverName", driverName);
      storeProperties.setProperty("ConnectionURL", connectionURL);
      storeProperties.setProperty("Username", username);
      storeProperties.setProperty("Password", password);
      String propertyString = storeMBean.getConnectionProperties();
      addToProperties(connectionProperties, propertyString);
      String jmsTopic = storeMBean.getJMSTopic();
      if (jmsTopic != null) {
         storeProperties.setProperty("JMSTopic", jmsTopic);
         String jmsTopicConnectionFactory = storeMBean.getJMSTopicConnectionFactory();
         if (jmsTopicConnectionFactory != null) {
            storeProperties.setProperty("JMSTopicConnectionFactory", jmsTopicConnectionFactory);
         }

         int jmsExceptionReconnectAttempts = storeMBean.getJMSExceptionReconnectAttempts();
         storeProperties.setProperty("JMSExceptionReconnectAttempts", Integer.toString(jmsExceptionReconnectAttempts));
         String notificationPropertyString = storeMBean.getNotificationProperties();
         propertyString = StringUtils.isEmptyString(notificationPropertyString) ? "" : notificationPropertyString;
         if (!StringUtils.isEmptyString(jndiUsername)) {
            if (propertyString.length() > 0) {
               propertyString = propertyString + ",";
            }

            propertyString = propertyString + "java.naming.security.principal=" + jndiUsername;
         }

         if (!StringUtils.isEmptyString(jndiPassword)) {
            if (propertyString.length() > 0) {
               propertyString = propertyString + ",";
            }

            propertyString = propertyString + "java.naming.security.credentials=" + jndiPassword;
         }

         addToProperties(notificationProperties, propertyString);
      }

   }

   private static void addToProperties(Properties properties, String propertiesString) {
      if (!StringUtils.isEmptyString(propertiesString)) {
         String[] subStrings;
         if (propertiesString.indexOf(",") < 0) {
            subStrings = new String[]{propertiesString};
         } else {
            subStrings = propertiesString.split(",");
         }

         for(int i = 0; i < subStrings.length; ++i) {
            String aString = subStrings[i].trim();
            if (aString.length() > 0) {
               int index = aString.indexOf("=");
               String key = aString.substring(0, index).trim();
               String value = aString.substring(index + 1, aString.length()).trim();
               properties.put(key, value);
            }
         }

      }
   }

   private static String getBootstrapVersioningDir(boolean isLDAP, AuthenticatedSubject kernelId) {
      String serverName = ManagementService.getPropertyService(kernelId).getServerName();
      if (isLDAP) {
         return DomainDir.getLDAPDataDirForServer(serverName);
      } else {
         String dataDirForServer = DomainDir.getDataDirForServer(serverName);
         String securityDataDirNameForServer = dataDirForServer + File.separator + "security";
         File securityDataDirForServer = new File(securityDataDirNameForServer);
         securityDataDirForServer.mkdirs();
         return securityDataDirNameForServer;
      }
   }
}
