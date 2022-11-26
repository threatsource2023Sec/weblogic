package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceConfigurationException;
import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.security.internal.service.ServiceLogger;
import com.bea.common.security.legacy.InternalServicesConfigHelper;
import com.bea.common.security.legacy.ServiceConfigCustomizer;
import com.bea.common.security.servicecfg.NamedSQLConnectionPoolConfig;
import java.util.Properties;
import java.util.Vector;
import javax.net.ssl.SSLContext;
import weblogic.management.security.RealmMBean;

public class InternalServicesConfigHelperImpl extends BaseServicesConfigImpl implements InternalServicesConfigHelper {
   private LDAPSSLSocketFactoryLookupServiceConfigCustomizerImpl ldapSSLSocketFactoryLookupServiceCustomizer = new LDAPSSLSocketFactoryLookupServiceConfigCustomizerImpl(this.getLDAPSSLSocketFactoryLookupServiceName());
   private JAXPFactoryServiceConfigCustomizerImpl jaxpFactoryServiceCustomizer = new JAXPFactoryServiceConfigCustomizerImpl(this.getJAXPFactoryServiceName());
   private NamedSQLConnectionLookupServiceConfigCustomizerImpl namedSQLConnectionLookupServiceCustomizer = new NamedSQLConnectionLookupServiceConfigCustomizerImpl(this.getNamedSQLConnectionLookupServiceName());
   private SAMLKeyServiceConfigCustomizerImpl samlKeyServiceCustomizer = new SAMLKeyServiceConfigCustomizerImpl(this.getSAMLKeyServiceName());
   private StoreServiceConfigCustomizerImpl storeServiceCustomizer = new StoreServiceConfigCustomizerImpl(this.getStoreServiceName());
   private BootStrapServiceConfigCustomizerImpl bootStrapServiceCustomizer = new BootStrapServiceConfigCustomizerImpl(this.getBootStrapServiceName());
   private ServiceConfigCustomizerImpl sessionServiceCustomizer = new ServiceConfigCustomizerImpl(this.getSessionServiceName());

   public String getLDAPSSLSocketFactoryLookupServiceName() {
      return LDAPSSLSocketFactoryLookupServiceConfigHelper.getServiceName(this.getRealmMBean());
   }

   public String getJAXPFactoryServiceName() {
      return JAXPFactoryServiceConfigHelper.getServiceName(this.getRealmMBean());
   }

   public String getNamedSQLConnectionLookupServiceName() {
      return NamedSQLConnectionLookupServiceConfigHelper.getServiceName(this.getRealmMBean());
   }

   public String getSAMLKeyServiceName() {
      return SAMLKeyServiceConfigHelper.getServiceName(this.getRealmMBean());
   }

   public String getStoreServiceName() {
      return StoreServiceConfigHelper.getServiceName(this.getRealmMBean());
   }

   public String getBootStrapServiceName() {
      return BootStrapServiceConfigHelper.getServiceName(this.getRealmMBean());
   }

   public String getSessionServiceName() {
      return SessionServiceConfigHelper.getServiceName(this.getRealmMBean());
   }

   public InternalServicesConfigHelperImpl(RealmMBean realmMBean) {
      super(realmMBean);
   }

   public InternalServicesConfigHelper.LDAPSSLSocketFactoryLookupServiceConfigCustomizer getLDAPSSLSocketFactoryLookupServiceCustomizer() {
      return this.ldapSSLSocketFactoryLookupServiceCustomizer;
   }

   public InternalServicesConfigHelper.JAXPFactoryServiceConfigCustomizer getJAXPFactoryServiceCustomizer() {
      return this.jaxpFactoryServiceCustomizer;
   }

   public InternalServicesConfigHelper.NamedSQLConnectionLookupServiceConfigCustomizer getNamedSQLConnectionLookupServiceCustomizer() {
      return this.namedSQLConnectionLookupServiceCustomizer;
   }

   public InternalServicesConfigHelper.SAMLKeyServiceConfigCustomizer getSAMLKeyServiceCustomizer() {
      return this.samlKeyServiceCustomizer;
   }

   public InternalServicesConfigHelper.StoreServiceConfigCustomizer getStoreServiceCustomizer() {
      return this.storeServiceCustomizer;
   }

   public InternalServicesConfigHelper.BootStrapServiceConfigCustomizer getBootStrapServiceCustomizer() {
      return this.bootStrapServiceCustomizer;
   }

   public ServiceConfigCustomizer getSessionServiceCustomizer() {
      return this.sessionServiceCustomizer;
   }

   public void addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName) {
      RealmMBean realm = this.getRealmMBean();
      if (!this.ldapSSLSocketFactoryLookupServiceCustomizer.isServiceRemoved()) {
         LDAPSSLSocketFactoryLookupServiceConfigHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, realm, this.ldapSSLSocketFactoryLookupServiceCustomizer.getServiceName(), this.ldapSSLSocketFactoryLookupServiceCustomizer.getSSLContext());
      }

      if (!this.jaxpFactoryServiceCustomizer.isServiceRemoved()) {
         JAXPFactoryServiceConfigHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, realm, this.jaxpFactoryServiceCustomizer.getServiceName(), this.jaxpFactoryServiceCustomizer.getDocumentBuilderFactoryClassName(), this.jaxpFactoryServiceCustomizer.getTransformerFactoryClassName());
      }

      if (!this.namedSQLConnectionLookupServiceCustomizer.isServiceRemoved()) {
         NamedSQLConnectionLookupServiceConfigHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, realm, this.namedSQLConnectionLookupServiceCustomizer.getServiceName(), this.namedSQLConnectionLookupServiceCustomizer.getNamedSQLConnectionPoolConfigs());
      }

      if (!this.samlKeyServiceCustomizer.isServiceRemoved()) {
         SAMLKeyServiceConfigHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, realm, this.samlKeyServiceCustomizer.getServiceName(), this.samlKeyServiceCustomizer.getKeyStoreFile(), this.samlKeyServiceCustomizer.getKeyStoreType(), this.samlKeyServiceCustomizer.getKeyStorePassPhrase(), this.samlKeyServiceCustomizer.getStoreValidationPollInterval(), this.samlKeyServiceCustomizer.getDefaultKeyAlias(), this.samlKeyServiceCustomizer.getDefaultKeyPassphrase());
      }

      if (!this.storeServiceCustomizer.isServiceRemoved()) {
         StoreServiceConfigHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, realm, this.storeServiceCustomizer.getServiceName(), this.storeServiceCustomizer.getStoreProperties(), this.storeServiceCustomizer.getConnectionProperties(), this.storeServiceCustomizer.getNotificationProperties());
      }

      if (!this.bootStrapServiceCustomizer.isServiceRemoved()) {
         BootStrapServiceConfigHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, realm, this.bootStrapServiceCustomizer.getServiceName(), this.bootStrapServiceCustomizer.getBootStrapProperties());
      }

      if (!this.sessionServiceCustomizer.isServiceRemoved()) {
         SessionServiceConfigHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, realm);
      }

   }

   private static class BootStrapServiceConfigCustomizerImpl extends ServiceConfigCustomizerImpl implements InternalServicesConfigHelper.BootStrapServiceConfigCustomizer {
      private Properties bootStrapProperties;

      private BootStrapServiceConfigCustomizerImpl(String serviceName) {
         super(serviceName);
      }

      public Properties getBootStrapProperties() {
         return this.bootStrapProperties;
      }

      public void setBootStrapProperties(Properties bootStrapProperties) {
         this.bootStrapProperties = bootStrapProperties;
      }

      // $FF: synthetic method
      BootStrapServiceConfigCustomizerImpl(String x0, Object x1) {
         this(x0);
      }
   }

   private static class StoreServiceConfigCustomizerImpl extends ServiceConfigCustomizerImpl implements InternalServicesConfigHelper.StoreServiceConfigCustomizer {
      private Properties storeProperties;
      private Properties connectionProperties;
      private Properties notificationProperties;
      private RealmMBean realmMBean;

      private StoreServiceConfigCustomizerImpl(String serviceName) {
         super(serviceName);
      }

      public Properties getStoreProperties() {
         return this.storeProperties;
      }

      public void setStoreProperties(Properties storeProperties) {
         this.storeProperties = storeProperties;
      }

      public Properties getConnectionProperties() {
         return this.connectionProperties;
      }

      public void setConnectionProperties(Properties connectionProperties) {
         this.connectionProperties = connectionProperties;
      }

      public Properties getNotificationProperties() {
         return this.notificationProperties;
      }

      public void setNotificationProperties(Properties notificationProperties) {
         this.notificationProperties = notificationProperties;
      }

      public RealmMBean getRealmMBean() {
         return this.realmMBean;
      }

      public void setRealmMBean(RealmMBean realmMBean) {
         this.realmMBean = realmMBean;
      }

      // $FF: synthetic method
      StoreServiceConfigCustomizerImpl(String x0, Object x1) {
         this(x0);
      }
   }

   private static class SAMLKeyServiceConfigCustomizerImpl extends ServiceConfigCustomizerImpl implements InternalServicesConfigHelper.SAMLKeyServiceConfigCustomizer {
      private String keyStoreFile;
      private String keyStoreType;
      private char[] keyStorePassphrase;
      private int storeValidationPollInterval;
      private String defaultKeyAlias;
      private char[] defaultKeyPassphrase;

      private SAMLKeyServiceConfigCustomizerImpl(String serviceName) {
         super(serviceName);
         this.keyStoreType = "JKS";
         this.storeValidationPollInterval = 15000;
      }

      public void setKeyStoreFile(String keyStoreFile) {
         this.keyStoreFile = keyStoreFile;
      }

      private String getKeyStoreFile() {
         return this.keyStoreFile;
      }

      public void setKeyStoreType(String keyStoreType) {
         this.keyStoreType = keyStoreType;
      }

      private String getKeyStoreType() {
         return this.keyStoreType;
      }

      public void setKeyStorePassPhrase(char[] keyStorePassphrase) {
         if (keyStorePassphrase != null) {
            this.keyStorePassphrase = new char[keyStorePassphrase.length];
            System.arraycopy(keyStorePassphrase, 0, this.keyStorePassphrase, 0, keyStorePassphrase.length);
         }

      }

      private char[] getKeyStorePassPhrase() {
         return this.keyStorePassphrase;
      }

      public void setStoreValidationPollInterval(int storeValidationPollInterval) {
         this.storeValidationPollInterval = storeValidationPollInterval;
      }

      private int getStoreValidationPollInterval() {
         return this.storeValidationPollInterval;
      }

      public void setDefaultKeyAlias(String defaultKeyAlias) {
         this.defaultKeyAlias = defaultKeyAlias;
      }

      private String getDefaultKeyAlias() {
         return this.defaultKeyAlias;
      }

      public void setDefaultKeyPassphrase(char[] defaultKeyPassphrase) {
         if (defaultKeyPassphrase != null) {
            this.defaultKeyPassphrase = new char[defaultKeyPassphrase.length];
            System.arraycopy(defaultKeyPassphrase, 0, this.defaultKeyPassphrase, 0, defaultKeyPassphrase.length);
         }

      }

      private char[] getDefaultKeyPassphrase() {
         return this.defaultKeyPassphrase;
      }

      // $FF: synthetic method
      SAMLKeyServiceConfigCustomizerImpl(String x0, Object x1) {
         this(x0);
      }
   }

   private static class NamedSQLConnectionLookupServiceConfigCustomizerImpl extends ServiceConfigCustomizerImpl implements InternalServicesConfigHelper.NamedSQLConnectionLookupServiceConfigCustomizer {
      private Vector pools;

      private NamedSQLConnectionLookupServiceConfigCustomizerImpl(String serviceName) {
         super(serviceName);
         this.pools = new Vector();
      }

      public synchronized void addNamedSQLConnectionPool(String poolName, String jdbcDriverClassName, int connectionPoolCapacity, int connectionPoolTimeout, String jdbcConnectionURL, Properties jdbcConnectionProperties, String databaseUserLogin, String databaseUserPassword) throws ServiceConfigurationException {
         this.judgeNullConfigFields(jdbcDriverClassName, jdbcConnectionURL, jdbcConnectionProperties);
         this.pools.add(NamedSQLConnectionLookupServiceConfigHelper.getNamedSQLConnectionPoolConfig(poolName, jdbcDriverClassName, connectionPoolCapacity, connectionPoolTimeout, jdbcConnectionURL, jdbcConnectionProperties, databaseUserLogin, databaseUserPassword));
      }

      public synchronized void addNamedSQLConnectionPool(String poolName, String jdbcDriverClassName, int connectionPoolCapacity, int connectionPoolTimeout, boolean automaticFailoverEnabled, int primaryRetryInterval, String jdbcConnectionURL, Properties jdbcConnectionProperties, String databaseUserLogin, String databaseUserPassword, String backupJDBCConnectionURL, Properties backupJDBCConnectionProperties, String backupDatabaseUserLogin, String backupDatabaseUserPassword) throws ServiceConfigurationException {
         this.judgeNullConfigFields(jdbcDriverClassName, jdbcConnectionURL, jdbcConnectionProperties);
         this.pools.add(NamedSQLConnectionLookupServiceConfigHelper.getNamedSQLConnectionPoolConfig(poolName, jdbcDriverClassName, connectionPoolCapacity, connectionPoolTimeout, automaticFailoverEnabled, primaryRetryInterval, jdbcConnectionURL, jdbcConnectionProperties, databaseUserLogin, databaseUserPassword, backupJDBCConnectionURL, backupJDBCConnectionProperties, backupDatabaseUserLogin, backupDatabaseUserPassword));
      }

      private synchronized NamedSQLConnectionPoolConfig[] getNamedSQLConnectionPoolConfigs() {
         int size = this.pools.size();
         if (size == 0) {
            return null;
         } else {
            NamedSQLConnectionPoolConfig[] poolArray = new NamedSQLConnectionLookupServiceConfigHelper.NamedSQLConnectionPoolConfigImpl[size];

            for(int i = 0; i < size; ++i) {
               poolArray[i] = (NamedSQLConnectionPoolConfig)this.pools.get(i);
            }

            return poolArray;
         }
      }

      private void judgeNullConfigFields(String jdbcDriverClassName, String jdbcConnectionURL, Properties jdbcConnectionProperties) throws ServiceConfigurationException {
         if (jdbcConnectionProperties == null) {
            throw new ServiceConfigurationException(ServiceLogger.getConfigurationMissingRequiredInfo("NamedSQLConnectionLookupService", "config", "JDBCConnectionProperties"));
         } else if (jdbcDriverClassName == null) {
            throw new ServiceConfigurationException(ServiceLogger.getConfigurationMissingRequiredInfo("NamedSQLConnectionLookupService", "config", "JDBCDriverClassName"));
         } else if (jdbcConnectionURL == null) {
            throw new ServiceConfigurationException(ServiceLogger.getConfigurationMissingRequiredInfo("NamedSQLConnectionLookupService", "config", "JDBCConnectionURL"));
         }
      }

      // $FF: synthetic method
      NamedSQLConnectionLookupServiceConfigCustomizerImpl(String x0, Object x1) {
         this(x0);
      }
   }

   private static class JAXPFactoryServiceConfigCustomizerImpl extends ServiceConfigCustomizerImpl implements InternalServicesConfigHelper.JAXPFactoryServiceConfigCustomizer {
      private String documentBuilderFactoryClassName;
      private String transformerFactoryClassName;

      private JAXPFactoryServiceConfigCustomizerImpl(String serviceName) {
         super(serviceName);
      }

      public void setDocumentBuilderFactoryClassName(String documentBuilderFactoryClassName) {
         this.documentBuilderFactoryClassName = documentBuilderFactoryClassName;
      }

      private String getDocumentBuilderFactoryClassName() {
         return this.documentBuilderFactoryClassName;
      }

      public void setTransformerFactoryClassName(String transformerFactoryClassName) {
         this.transformerFactoryClassName = transformerFactoryClassName;
      }

      private String getTransformerFactoryClassName() {
         return this.transformerFactoryClassName;
      }

      // $FF: synthetic method
      JAXPFactoryServiceConfigCustomizerImpl(String x0, Object x1) {
         this(x0);
      }
   }

   private static class LDAPSSLSocketFactoryLookupServiceConfigCustomizerImpl extends ServiceConfigCustomizerImpl implements InternalServicesConfigHelper.LDAPSSLSocketFactoryLookupServiceConfigCustomizer {
      private SSLContext sslContext;

      private LDAPSSLSocketFactoryLookupServiceConfigCustomizerImpl(String serviceName) {
         super(serviceName);
      }

      public void setSSLContext(SSLContext sslContext) {
         this.sslContext = sslContext;
      }

      private SSLContext getSSLContext() {
         return this.sslContext;
      }

      // $FF: synthetic method
      LDAPSSLSocketFactoryLookupServiceConfigCustomizerImpl(String x0, Object x1) {
         this(x0);
      }
   }
}
