package com.bea.common.security.storeservice.util;

import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.store.service.RemoteCommitListener;
import com.bea.common.store.service.RemoteCommitProvider;
import com.bea.common.store.service.StoreInitializationException;
import com.bea.common.store.service.StoreNotFoundException;
import com.bea.common.store.service.config.StoreServicePropertiesConfigurator;
import com.bea.security.utils.DigestUtils;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import javax.jdo.JDOFatalException;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import kodo.jdo.KodoJDOHelper;
import kodo.jdo.KodoPersistenceManagerFactory;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.event.RemoteCommitEvent;
import org.apache.openjpa.kernel.Bootstrap;
import org.apache.openjpa.kernel.BrokerFactory;
import org.apache.openjpa.lib.conf.ConfigurationProvider;
import org.apache.openjpa.lib.conf.MapConfigurationProvider;
import org.apache.openjpa.meta.ClassMetaData;

public class StoreServiceDelegate {
   public static final String STORE_SERVICE_PROPERTIES_CONFIGURATOR = "StoreServicePropertiesConfiguratorClassname";
   public static final String STORE_SERVICE_REMOTE_COMMIT_PROVIDER = "StoreServiceRemoteCommitProviderClassname";
   private static final Map loggerMap = Collections.synchronizedMap(new HashMap());
   private String loggerKey = System.currentTimeMillis() + ":" + Math.random();
   private LoggerSpi logger;
   private PersistenceManagerFactory pmf;
   private RemoteCommitProvider rcp;
   private String storeId;
   private ClassLoader contextLoader = new StoreServiceClassLoader(ClassLoader.getSystemClassLoader(), this.getClass().getClassLoader());

   static LoggerSpi getLogger(String key) {
      return (LoggerSpi)loggerMap.get(key);
   }

   public StoreServiceDelegate(LoggerService loggerService, Properties jdoProperties) {
      this.logger = loggerService.getLogger("com.bea.common.security.service.StoreService");
      boolean debug = false;
      if (this.logger != null) {
         loggerMap.put(this.loggerKey, this.logger);
         debug = this.logger.isDebugEnabled();
      }

      String method = this.getClass().getName() + ".init";
      if (debug) {
         this.logger.debug(method);
      }

      this.initJDO(jdoProperties);
   }

   public PersistenceManagerFactory getPersistenceManagerFactory() {
      return this.pmf;
   }

   private void initJDO(Properties jdoProperties) throws StoreInitializationException {
      String logPrefix = "StoreServiceImpl.initJDO - ";
      jdoProperties.setProperty("kodo.LicenseKey", "6E6F-70CD-CD7A-F574-7400");
      jdoProperties.setProperty("javax.jdo.PersistenceManagerFactoryClass", "kodo.jdo.PersistenceManagerFactoryImpl");
      jdoProperties.setProperty("kodo.MetaDataFactory", "jdo(Resources=com/bea/common/security/store/data/package.jdo)");
      jdoProperties.setProperty("kodo.Log", "com.bea.common.security.storeservice.util.LogFactoryImpl(Key=" + this.loggerKey + ")");
      String connectionURL = jdoProperties.getProperty("kodo.ConnectionURL").toLowerCase();
      String storeType = "ldap";

      try {
         if (connectionURL.startsWith("jdbc")) {
            storeType = "rdbms";
         }

         this.storeId = storeType + "_" + DigestUtils.digestSHA1(connectionURL);
      } catch (Exception var11) {
         this.logger.error("Failed to generate storeId, storeId == null.", var11);
         this.storeId = null;
      }

      Thread t = Thread.currentThread();
      ClassLoader savedContextClassLoader = t.getContextClassLoader();

      try {
         t.setContextClassLoader(this.contextLoader);
         if (storeType == "rdbms") {
            this.pmf = StoreServiceDelegate.StoreSerivceHelper.getPersistenceManagerFactory(jdoProperties, this.getClass().getClassLoader());
         } else {
            this.pmf = JDOHelper.getPersistenceManagerFactory(jdoProperties, this.getClass().getClassLoader());
         }
      } finally {
         t.setContextClassLoader(savedContextClassLoader);
      }

      this.logger.info(logPrefix + "StoreService is initialized with Id = " + this.storeId);
   }

   public void initRemoteCommitProvider(Properties storeProperties, Properties notificationProperties, StoreServicePropertiesConfigurator sspc, boolean isActive) throws StoreInitializationException {
      try {
         String rcpClassname = storeProperties != null ? storeProperties.getProperty("StoreServiceRemoteCommitProviderClassname") : null;
         this.rcp = (RemoteCommitProvider)(rcpClassname != null ? (RemoteCommitProvider)Class.forName(rcpClassname).newInstance() : new DefaultRemoteCommitProvider(isActive));
         this.rcp.initialize(storeProperties, notificationProperties, sspc);
      } catch (ClassNotFoundException var6) {
         throw new StoreInitializationException("TBDI8N: Error loading store service plug-in class: " + var6.getMessage());
      } catch (IllegalAccessException var7) {
         throw new StoreInitializationException("TBDI8N: Error loading store service plug-in class: " + var7.getMessage());
      } catch (InstantiationException var8) {
         throw new StoreInitializationException("TBDI8N: Error instantiating store service plug-in class: " + var8.getMessage());
      }
   }

   public void shutdown() {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".shutdown" : null;
      if (debug) {
         this.logger.debug(method);
      }

      if (this.rcp != null) {
         this.rcp.shutdown();
      }

      if (this.pmf != null) {
         this.pmf.close();
      }

      loggerMap.remove(this.loggerKey);
   }

   public PersistenceManager getPersistenceManager() throws StoreInitializationException, StoreNotFoundException {
      Thread t = Thread.currentThread();
      ClassLoader tcl = t.getContextClassLoader();

      PersistenceManager var3;
      try {
         t.setContextClassLoader(this.contextLoader);
         var3 = this.pmf.getPersistenceManager();
      } catch (JDOFatalException var7) {
         throw new StoreInitializationException(var7);
      } finally {
         t.setContextClassLoader(tcl);
      }

      return var3;
   }

   public synchronized String getStoreId() {
      return this.storeId;
   }

   public void addRemoteCommitListener(Class pc, RemoteCommitListener listener) {
      this.rcp.addRemoteCommitListener(this.pmf, pc, listener);
   }

   private static class StoreServiceClassLoader extends ClassLoader {
      private ClassLoader delegate;

      StoreServiceClassLoader(ClassLoader parent, ClassLoader delegate) {
         super(parent);
         if (delegate == null) {
            throw new IllegalArgumentException("Delegate ClassLoader cannot be null.");
         } else {
            this.delegate = delegate;
         }
      }

      public URL getResource(String name) {
         return this.delegate.getResource(name);
      }

      public InputStream getResourceAsStream(String name) {
         return this.delegate.getResourceAsStream(name);
      }

      public Class loadClass(String name) throws ClassNotFoundException {
         return this.delegate.loadClass(name);
      }
   }

   private static class StoreSerivceHelper {
      protected static PersistenceManagerFactory getPersistenceManagerFactory(Properties jdoProperties, ClassLoader cl) {
         ConfigurationProvider conf = new MapConfigurationProvider(jdoProperties);
         BrokerFactory bf = Bootstrap.getBrokerFactory(conf, cl);
         return KodoJDOHelper.toPersistenceManagerFactory(bf);
      }
   }

   private static class DefaultRemoteCommitProvider implements RemoteCommitProvider {
      private boolean isActive = true;

      public DefaultRemoteCommitProvider(boolean isActive) {
         this.isActive = isActive;
      }

      public void initialize(Properties storeProperties, Properties notificationProperties, StoreServicePropertiesConfigurator sspc) {
      }

      public void shutdown() {
      }

      public void addRemoteCommitListener(PersistenceManagerFactory pmf, Class pc, final RemoteCommitListener listener) {
         if (this.isActive) {
            OpenJPAConfiguration config = ((KodoPersistenceManagerFactory)pmf).getConfiguration();
            ClassMetaData cmd = config.getMetaDataRepositoryInstance().getMetaData(pc, pc.getClassLoader(), true);
            final Class oidc = cmd.getObjectIdType();
            config.getRemoteCommitEventManager().addListener(new org.apache.openjpa.event.RemoteCommitListener() {
               public void afterCommit(RemoteCommitEvent event) {
                  final Collection deletedObjectIds = new ArrayList();
                  final Collection addedObjectIds = new ArrayList();
                  final Collection updatedObjectIds = new ArrayList();
                  Collection edoi = event.getDeletedObjectIds();
                  if (edoi != null) {
                     Iterator itxx = edoi.iterator();

                     while(itxx.hasNext()) {
                        Object oid = itxx.next();
                        if (oidc.isAssignableFrom(oid.getClass())) {
                           deletedObjectIds.add(oid);
                        }
                     }
                  }

                  Collection eaoi = event.getPersistedObjectIds();
                  if (edoi != null) {
                     Iterator it = eaoi.iterator();

                     while(it.hasNext()) {
                        Object oidx = it.next();
                        if (oidc.isAssignableFrom(oidx.getClass())) {
                           addedObjectIds.add(oidx);
                        }
                     }
                  }

                  Collection euoi = event.getUpdatedObjectIds();
                  if (edoi != null) {
                     Iterator itx = euoi.iterator();

                     while(itx.hasNext()) {
                        Object oidxx = itx.next();
                        if (oidc.isAssignableFrom(oidxx.getClass())) {
                           updatedObjectIds.add(oidxx);
                        }
                     }
                  }

                  if (!deletedObjectIds.isEmpty() || !addedObjectIds.isEmpty() || !updatedObjectIds.isEmpty()) {
                     listener.afterCommit(new com.bea.common.store.service.RemoteCommitEvent() {
                        public Collection getDeletedObjectIds() {
                           return deletedObjectIds;
                        }

                        public Collection getAddedObjectIds() {
                           return addedObjectIds;
                        }

                        public Collection getUpdatedObjectIds() {
                           return updatedObjectIds;
                        }
                     });
                  }

               }

               public void close() {
               }
            });
         }
      }
   }
}
