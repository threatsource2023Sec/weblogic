package com.bea.common.security.internal.service;

import com.bea.common.engine.ServiceConfigurationException;
import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.internal.utils.Delegator;
import com.bea.common.security.servicecfg.StoreServiceConfig;
import com.bea.common.security.storeservice.util.StoreServiceDelegate;
import com.bea.common.security.utils.CSSPlatformProxy;
import com.bea.common.store.service.RemoteCommitListener;
import com.bea.common.store.service.StoreInitializationException;
import com.bea.common.store.service.StoreNotFoundException;
import com.bea.common.store.service.StoreService;
import com.bea.common.store.service.config.StoreServicePropertiesConfigurator;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import org.apache.openjpa.conf.OpenJPAVersion;

public class StoreServiceImpl implements ServiceLifecycleSpi, StoreService {
   public static final String STORE_SERVICE_PROPERTIES_CONFIGURATOR = "StoreServicePropertiesConfiguratorClassname";
   public static final String STORE_SERVICE_REMOTE_COMMIT_PROVIDER = "StoreServiceRemoteCommitProviderClassname";
   private LoggerSpi logger;
   private StoreServiceDelegate delegate;
   private StoreServicePropertiesConfigurator sspc;
   private boolean isMySQLUsed = false;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      LoggerService loggerService = (LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME);
      this.logger = loggerService.getLogger("com.bea.common.security.service.StoreService");
      boolean debug = this.logger != null && this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".init";
      if (debug) {
         this.logger.debug(method);
      }

      if (config != null && config instanceof StoreServiceConfig) {
         StoreServiceConfig myconfig = (StoreServiceConfig)config;
         if (myconfig.getStoreProperties() != null && !myconfig.getStoreProperties().isEmpty()) {
            Properties jdoProperties = this.initJDOProperties(myconfig.getStoreProperties(), myconfig.getConnectionProperties(), myconfig.getNotificationProperties());
            this.delegate = new StoreServiceDelegate(loggerService, jdoProperties);
            boolean isActive = true;
            if (this.sspc instanceof DefaultStoreServicePropertiesConfigurator) {
               isActive = ((DefaultStoreServicePropertiesConfigurator)this.sspc).isNotificationActive();
            }

            this.delegate.initRemoteCommitProvider(myconfig.getStoreProperties(), myconfig.getNotificationProperties(), this.sspc, isActive);
            return Delegator.getProxy((String)"com.bea.common.store.service.StoreService", this);
         } else {
            if (debug) {
               this.logger.debug(ServiceLogger.getStoreServicePropertiesIsNull());
               this.logger.debug(ServiceLogger.getStoreServiceNotInitialized());
            }

            return Delegator.getProxy((String)"com.bea.common.store.service.StoreService", this);
         }
      } else {
         throw new ServiceConfigurationException(ServiceLogger.getExpectedConfigurationNotSupplied(method, "StoreServiceConfig"));
      }
   }

   private Properties initJDOProperties(Properties storeProperties, Properties connectionProperties, Properties notificationProperties) throws StoreInitializationException {
      try {
         String sspcClassname = storeProperties != null ? storeProperties.getProperty("StoreServicePropertiesConfiguratorClassname") : null;
         this.sspc = (StoreServicePropertiesConfigurator)(sspcClassname != null ? (StoreServicePropertiesConfigurator)Class.forName(sspcClassname).newInstance() : new DefaultStoreServicePropertiesConfigurator());
         String driverName = storeProperties != null ? storeProperties.getProperty("DriverName") : null;
         this.isMySQLUsed = driverName != null && driverName.toUpperCase().contains("MYSQL");
         return this.sspc.convertStoreProperties(storeProperties, connectionProperties, notificationProperties);
      } catch (ClassNotFoundException var6) {
         throw new StoreInitializationException("TBDI8N: Error loading store service plug-in class: " + var6.getMessage());
      } catch (IllegalAccessException var7) {
         throw new StoreInitializationException("TBDI8N: Error loading store service plug-in class: " + var7.getMessage());
      } catch (InstantiationException var8) {
         throw new StoreInitializationException("TBDI8N: Error instantiating store service plug-in class: " + var8.getMessage());
      }
   }

   public void shutdown() {
      if (this.delegate != null) {
         this.delegate.shutdown();
      }
   }

   public PersistenceManager getPersistenceManager() throws StoreInitializationException, StoreNotFoundException {
      if (this.delegate == null) {
         throw new StoreInitializationException(ServiceLogger.getStoreServiceNotInitialized());
      } else {
         return this.delegate.getPersistenceManager();
      }
   }

   public PersistenceManagerFactory getPersistenceManagerFactory() throws StoreInitializationException, StoreNotFoundException {
      return this.delegate.getPersistenceManagerFactory();
   }

   public void addRemoteCommitListener(Class pc, RemoteCommitListener listener) {
      if (this.delegate == null) {
         throw new StoreInitializationException(ServiceLogger.getStoreServiceNotInitialized());
      } else {
         this.delegate.addRemoteCommitListener(pc, listener);
      }
   }

   public synchronized String getStoreId() {
      if (this.delegate == null) {
         throw new StoreInitializationException(ServiceLogger.getStoreServiceNotInitialized());
      } else {
         return this.delegate.getStoreId();
      }
   }

   public boolean isMySQLUsed() {
      return this.isMySQLUsed;
   }

   private static class DefaultStoreServicePropertiesConfigurator implements StoreServicePropertiesConfigurator {
      public static final String USERNAME = "Username";
      public static final String PASSWORD = "Password";
      public static final String CONNECTION_URL = "ConnectionURL";
      public static final String DRIVER_NAME = "DriverName";
      public static final String MAX_ACTIVE = "MaxActive";
      public static final String MAX_IDLE = "MaxIdle";
      public static final String MAX_TOTAL = "MaxTotal";
      public static final String MAX_WAIT = "MaxWait";
      public static final String MIN_EVICTABLE_TIME_MILLIS = "MinEvictableTimeMillis";
      public static final String TEST_ON_BORROW = "TestOnBorrow";
      public static final String TEST_ON_RETURN = "TestOnReturn";
      public static final String TEST_WHILE_IDLE = "TestWhileIdle";
      public static final String TIME_BETWEEN_EVICTION_RUNS_MILLIS = "TimeBetweenEvictionRunsMillis";
      public static final String VALIDATION_SQL = "ValidationSQL";
      public static final String VALIDATION_TIMEOUT = "ValidationTimeout";
      public static final String WARNING_ACTION = "WarningAction";
      public static final String WHEN_EXHAUSTED_ACTION = "WhenExhaustedAction";
      public static final String QUERY_TIMEOUT = "QueryTimeout";
      public static final String TOPIC = "JMSTopic";
      public static final String TOPIC_CONNECTION_FACTORY = "JMSTopicConnectionFactory";
      public static final String EXCEPTION_RECONNECT_ATTEMPTS = "JMSExceptionReconnectAttempts";
      private boolean isActive = false;

      public DefaultStoreServicePropertiesConfigurator() {
      }

      public boolean isNotificationActive() {
         return this.isActive;
      }

      public Properties convertStoreProperties(Properties storeProperties, Properties connectionProperties, Properties notificationProperties) throws StoreInitializationException {
         Properties jdoProperties = new Properties();
         if (storeProperties == null) {
            throw new StoreInitializationException(ServiceLogger.getStoreServicePropertiesIsNull());
         } else {
            String property = storeProperties.getProperty("Username");
            if (property == null) {
               throw new StoreInitializationException(ServiceLogger.getStoreServicePropertiesHasNullField("Username"));
            } else {
               jdoProperties.setProperty("kodo.ConnectionUserName", property);
               property = storeProperties.getProperty("Password");
               if (property != null) {
                  jdoProperties.setProperty("kodo.ConnectionPassword", property);
               }

               property = storeProperties.getProperty("ConnectionURL");
               if (property == null) {
                  throw new StoreInitializationException(ServiceLogger.getStoreServicePropertiesHasNullField("ConnectionURL"));
               } else {
                  jdoProperties.setProperty("kodo.ConnectionURL", property);
                  property = storeProperties.getProperty("DriverName");
                  if (property == null) {
                     throw new StoreInitializationException(ServiceLogger.getStoreServicePropertiesHasNullField("DriverName"));
                  } else {
                     jdoProperties.setProperty("kodo.ConnectionDriverName", property);
                     StringBuilder cfp = new StringBuilder();
                     property = storeProperties.getProperty("MaxActive");
                     if (property != null) {
                        if (cfp.length() > 0) {
                           cfp.append(", ");
                        }

                        cfp.append("MaxActive");
                        cfp.append('=');
                        cfp.append(property);
                     }

                     property = storeProperties.getProperty("MaxIdle");
                     if (property != null) {
                        if (cfp.length() > 0) {
                           cfp.append(", ");
                        }

                        cfp.append("MaxIdle");
                        cfp.append('=');
                        cfp.append(property);
                     }

                     property = storeProperties.getProperty("MaxTotal");
                     if (property != null) {
                        if (cfp.length() > 0) {
                           cfp.append(", ");
                        }

                        cfp.append("MaxTotal");
                        cfp.append('=');
                        cfp.append(property);
                     }

                     property = storeProperties.getProperty("MaxWait");
                     if (property != null) {
                        if (cfp.length() > 0) {
                           cfp.append(", ");
                        }

                        cfp.append("MaxWait");
                        cfp.append('=');
                        cfp.append(property);
                     }

                     property = storeProperties.getProperty("MinEvictableTimeMillis");
                     if (property != null) {
                        if (cfp.length() > 0) {
                           cfp.append(", ");
                        }

                        cfp.append("MinEvictableTimeMillis");
                        cfp.append('=');
                        cfp.append(property);
                     }

                     property = storeProperties.getProperty("TestOnBorrow");
                     if (property != null) {
                        if (cfp.length() > 0) {
                           cfp.append(", ");
                        }

                        cfp.append("TestOnBorrow");
                        cfp.append('=');
                        cfp.append(property);
                     }

                     property = storeProperties.getProperty("TestOnReturn");
                     if (property != null) {
                        if (cfp.length() > 0) {
                           cfp.append(", ");
                        }

                        cfp.append("TestOnReturn");
                        cfp.append('=');
                        cfp.append(property);
                     }

                     property = storeProperties.getProperty("TestWhileIdle");
                     if (property != null) {
                        if (cfp.length() > 0) {
                           cfp.append(", ");
                        }

                        cfp.append("TestWhileIdle");
                        cfp.append('=');
                        cfp.append(property);
                     }

                     property = storeProperties.getProperty("TimeBetweenEvictionRunsMillis");
                     if (property != null) {
                        if (cfp.length() > 0) {
                           cfp.append(", ");
                        }

                        cfp.append("TimeBetweenEvictionRunsMillis");
                        cfp.append('=');
                        cfp.append(property);
                     }

                     property = storeProperties.getProperty("ValidationSQL");
                     if (property != null) {
                        if (cfp.length() > 0) {
                           cfp.append(", ");
                        }

                        cfp.append("ValidationSQL");
                        cfp.append('=');
                        cfp.append(property);
                     }

                     property = storeProperties.getProperty("ValidationTimeout");
                     if (property != null) {
                        if (cfp.length() > 0) {
                           cfp.append(", ");
                        }

                        cfp.append("ValidationTimeout");
                        cfp.append('=');
                        cfp.append(property);
                     }

                     property = storeProperties.getProperty("WarningAction");
                     if (property != null) {
                        if (cfp.length() > 0) {
                           cfp.append(", ");
                        }

                        cfp.append("WarningAction");
                        cfp.append('=');
                        cfp.append(property);
                     }

                     property = storeProperties.getProperty("WhenExhaustedAction");
                     if (property != null) {
                        if (cfp.length() > 0) {
                           cfp.append(", ");
                        }

                        cfp.append("WhenExhaustedAction");
                        cfp.append('=');
                        cfp.append(property);
                     }

                     property = storeProperties.getProperty("QueryTimeout");
                     if (property != null) {
                        if (cfp.length() > 0) {
                           cfp.append(", ");
                        }

                        cfp.append("QueryTimeout");
                        cfp.append('=');
                        cfp.append(property);
                     }

                     if (cfp.length() > 0) {
                        jdoProperties.setProperty("kodo.ConnectionFactoryProperties", cfp.toString());
                     }

                     StringBuilder jms;
                     Iterator it;
                     Map.Entry e;
                     if (connectionProperties != null) {
                        jms = new StringBuilder();
                        it = connectionProperties.entrySet().iterator();

                        while(it.hasNext()) {
                           e = (Map.Entry)it.next();
                           jms.append(e.getKey().toString());
                           jms.append('=');
                           jms.append(e.getValue().toString());
                           if (it.hasNext()) {
                              jms.append(", ");
                           }
                        }

                        jdoProperties.setProperty("kodo.ConnectionProperties", jms.toString());
                     }

                     property = storeProperties.getProperty("JMSTopic");
                     if (property != null) {
                        jms = new StringBuilder();
                        if (CSSPlatformProxy.getInstance().isOnWLS()) {
                           jms.append("weblogic.security.jms.WebLogicJMSRemoteCommitProvider(");
                        } else {
                           jms.append("com.bea.common.security.jms.RobustJMSRemoteCommitProvider(");
                        }

                        jms.append("Topic");
                        jms.append('=');
                        jms.append(property);
                        jms.append(", ");
                        jms.append("TransmitPersistedObjectIds=true");
                        property = storeProperties.getProperty("JMSTopicConnectionFactory");
                        if (property != null) {
                           jms.append(", ");
                           jms.append("TopicConnectionFactory");
                           jms.append('=');
                           jms.append(property);
                        }

                        property = storeProperties.getProperty("JMSExceptionReconnectAttempts");
                        if (property != null) {
                           jms.append(", ");
                           jms.append("ExceptionReconnectAttempts");
                           jms.append('=');
                           jms.append(property);
                        }

                        if (notificationProperties != null) {
                           it = notificationProperties.entrySet().iterator();

                           while(it.hasNext()) {
                              e = (Map.Entry)it.next();
                              String keyString = e.getKey().toString().trim();
                              String valueString = e.getValue().toString().trim();
                              if (keyString.length() > 0 && valueString.length() > 0) {
                                 jms.append(", ");
                                 jms.append(keyString);
                                 jms.append('=');
                                 jms.append(valueString);
                              }
                           }
                        }

                        jms.append(')');
                        jdoProperties.setProperty("kodo.RemoteCommitProvider", jms.toString());
                        if (OpenJPAVersion.MAJOR_RELEASE >= 1 && OpenJPAVersion.MINOR_RELEASE >= 1) {
                           StringBuilder metaCache = new StringBuilder();
                           metaCache.append("default(Id=org.apache.openjpa.conf.MetaDataCacheMaintenance,");
                           metaCache.append("InputResource=com/bea/common/security/store/data/openjpa-rdbms-metadata.ser, ConsumeSerializationErrors=true,");
                           metaCache.append("ValidationPolicy=org.apache.openjpa.conf.OpenJPAVersionAndConfigurationTypeValidationPolicy)");
                           jdoProperties.setProperty("openjpa.CacheMarshallers", metaCache.toString());
                        }

                        this.isActive = true;
                     }

                     return jdoProperties;
                  }
               }
            }
         }
      }
   }
}
