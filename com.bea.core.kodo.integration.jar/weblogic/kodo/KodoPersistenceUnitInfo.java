package weblogic.kodo;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import kodo.jdbc.conf.descriptor.JDBCConfigurationBeanParser;
import kodo.jdbc.conf.descriptor.PersistenceUnitConfigurationBean;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.conf.OpenJPAConfigurationImpl;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.MetaDataRepository;
import org.apache.openjpa.persistence.OpenJPAEntityManagerFactorySPI;
import org.apache.openjpa.persistence.OpenJPAPersistence;
import org.apache.openjpa.persistence.PersistenceProviderImpl;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.naming.EnvironmentException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.descriptor.DescriptorBean;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2ee.J2EELogger;
import weblogic.j2ee.descriptor.PersistenceUnitBean;
import weblogic.kodo.monitoring.KodoPersistenceUnitRuntimeMBeanFactory;
import weblogic.management.ManagementException;
import weblogic.management.runtime.PersistenceUnitRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.persistence.BasePersistenceUnitInfo;
import weblogic.persistence.spi.JPAIntegrationProvider.Type;
import weblogic.utils.classloaders.GenericClassLoader;

public class KodoPersistenceUnitInfo extends BasePersistenceUnitInfo {
   private static final String[] CONVERT = new String[]{"TransactionMode", "ConnectionFactory", "ConnectionFactoryName", "ConnectionFactoryMode", "ConnectionFactory2", "ConnectionFactory2Name", "ClassResolver"};
   private final PersistenceUnitConfigurationBean configDD;
   private final UpdateListener ul;

   public KodoPersistenceUnitInfo(PersistenceUnitBean dd, PersistenceUnitConfigurationBean configDD, GenericClassLoader cl, String persistenceArchiveId, URL rootUrl, URL jarParentUrl, String originalVersion, ApplicationContextInternal appCtx) throws EnvironmentException {
      super(Type.KODO, dd, persistenceArchiveId, rootUrl, cl, jarParentUrl, originalVersion, appCtx);
      this.configDD = configDD;
      this.ul = new UpdateListener(this);
   }

   public PersistenceUnitRuntimeMBean createRuntimeMBean(RuntimeMBean parentMBean) throws EnvironmentException {
      try {
         return KodoPersistenceUnitRuntimeMBeanFactory.getInstance().createKodoPersistenceUnitRuntimeMBean(this.getPersistenceUnitName(), parentMBean, this.getUnwrappedEntityManagerFactory());
      } catch (RuntimeException var4) {
         DebugLogger kodoRuntimeLogger = DebugLogger.getDebugLogger("DebugJpaRuntime");
         if (kodoRuntimeLogger.isDebugEnabled()) {
            kodoRuntimeLogger.debug("Problem creating KodoPersistenceUnitRuntimeMBean", var4);
         }

         return null;
      } catch (ManagementException var5) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Problem creating KodoPersistenceUnitRuntimeMBean", var5);
         }

         throw new EnvironmentException(var5);
      }
   }

   protected void processProperties() {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Creating properties:" + this.rootUrl + ":" + this.dd + ":" + this.configDD);
      }

      String prop;
      if (this.configDD != null) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Parsing persistence-configuration.xml properties...");
         }

         prop = null;
         Properties config = (new JDBCConfigurationBeanParser()).load(this.configDD);
         Iterator it = config.keySet().iterator();

         label85:
         while(true) {
            while(true) {
               if (!it.hasNext()) {
                  break label85;
               }

               prop = (String)it.next();

               for(int i = 0; i < CONVERT.length; ++i) {
                  if (prop.endsWith(CONVERT[i])) {
                     (new StringBuilder()).append("openjpa").append(prop.substring("kodo".length())).toString();
                     break;
                  }

                  this.properties.setProperty(prop, config.getProperty(prop));
               }
            }
         }
      }

      if (DEBUG.isDebugEnabled()) {
         Iterator var6 = this.properties.entrySet().iterator();

         while(var6.hasNext()) {
            Map.Entry entry = (Map.Entry)var6.next();
            DEBUG.debug("Configuration property:" + entry.getKey() + "," + entry.getValue());
         }

         DEBUG.debug("Done parsing -configuration.");
      }

      super.processProperties();
      if (this.isKodoPersistenceUnit()) {
         if (this.properties.containsKey("kodo.Id")) {
            prop = "kodo.Id";
         } else {
            prop = "openjpa.Id";
         }

         if (this.properties.containsKey(prop)) {
            J2EELogger.logPersistenceUnitIdPropertySpecified(this.getPersistenceUnitId(), prop);
         }

         this.properties.setProperty(prop, this.getPersistenceUnitId());
         boolean isFiltered = false;

         try {
            Class appClass = Class.forName(PersistenceProviderImpl.class.getName(), true, this.getClassLoader());
            isFiltered = appClass != PersistenceProviderImpl.class;
         } catch (ClassNotFoundException var5) {
         }

         if (isFiltered) {
            J2EELogger.logOpenJPAPersistenceUnitUsesApplicationJars(this.getPersistenceUnitId());
         } else {
            String logKey;
            if (this.properties.containsKey("kodo.Log")) {
               logKey = "kodo.Log";
            } else {
               logKey = "openjpa.Log";
            }

            if (this.properties.containsKey(logKey)) {
               J2EELogger.logPersistenceUnitLogConfigurationSpecified(this.getPersistenceUnitId(), logKey);
            }

            this.properties.setProperty(logKey, WebLogicLogFactory.class.getName());
         }
      }

   }

   public PersistenceUnitConfigurationBean getConfigDD() {
      return this.configDD;
   }

   public void dataSourceUpdated(boolean updatedJTA, boolean updatedNonJTA) {
      EntityManagerFactory emf = this.getUnwrappedEntityManagerFactory();
      if (emf != null && emf instanceof OpenJPAEntityManagerFactorySPI) {
         OpenJPAConfiguration conf = ((OpenJPAEntityManagerFactorySPI)emf).getConfiguration();
         if (conf == null) {
            return;
         }

         if (updatedJTA) {
            conf.setConnectionFactory(this.jtaDataSource);
            conf.setConnectionFactoryMode("managed");
         }

         if (updatedNonJTA) {
            if (!updatedJTA) {
               conf.setConnectionFactory(this.nonJtaDataSource);
            } else {
               conf.setConnectionFactory2(this.nonJtaDataSource);
            }
         }
      }

   }

   public void registerUpdateListeners() {
      ((DescriptorBean)this.dd).addBeanUpdateListener(this.ul);
      if (this.configDD != null) {
         ((DescriptorBean)this.configDD).addBeanUpdateListener(this.ul);
      }

   }

   public void unregisterUpdateListeners() {
      ((DescriptorBean)this.dd).removeBeanUpdateListener(this.ul);
      if (this.configDD != null) {
         ((DescriptorBean)this.configDD).removeBeanUpdateListener(this.ul);
      }

   }

   private static final class UpdateListener implements BeanUpdateListener {
      private final KodoPersistenceUnitInfo puii;

      UpdateListener(KodoPersistenceUnitInfo puii) {
         this.puii = puii;
      }

      public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
         if (BasePersistenceUnitInfo.DEBUG.isDebugEnabled()) {
            BasePersistenceUnitInfo.DEBUG.debug("prepareUpdate: " + event);
         }

         BeanUpdateEvent.PropertyUpdate[] list = event.getUpdateList();
         PersistenceUnitConfigurationBean pucb = (PersistenceUnitConfigurationBean)event.getProposedBean();
         int i = 0;

         while(i < list.length) {
            BeanUpdateEvent.PropertyUpdate prop = list[i];
            switch (prop.getUpdateType()) {
               case 1:
                  String propertyName = prop.getPropertyName();
                  if (BasePersistenceUnitInfo.DEBUG.isDebugEnabled()) {
                     BasePersistenceUnitInfo.DEBUG.debug("Preparing property of type: " + propertyName);
                  }

                  if (propertyName.equals("LockTimeout")) {
                     if (BasePersistenceUnitInfo.DEBUG.isDebugEnabled()) {
                        BasePersistenceUnitInfo.DEBUG.debug("Preparing persistence property: " + propertyName);
                     }

                     if (pucb.getLockTimeout() < -1) {
                        throw new BeanUpdateRejectedException("Value for newLockTimout is < -1, it should be >= -1.");
                     }
                  } else if (propertyName.equals("DataCacheTimeout")) {
                     if (BasePersistenceUnitInfo.DEBUG.isDebugEnabled()) {
                        BasePersistenceUnitInfo.DEBUG.debug("Preparing persistence property: " + propertyName);
                     }

                     if (pucb.getDataCacheTimeout() < -1) {
                        throw new BeanUpdateRejectedException("Value for dataCacheTimout is < -1, it should be >= -1.");
                     }
                  } else {
                     if (!propertyName.equals("FetchBatchSize")) {
                        throw new AssertionError("Unexpected propertyName: " + propertyName);
                     }

                     if (BasePersistenceUnitInfo.DEBUG.isDebugEnabled()) {
                        BasePersistenceUnitInfo.DEBUG.debug("Preparing persistence property: " + propertyName);
                     }

                     if (pucb.getFetchBatchSize() < -1) {
                        throw new BeanUpdateRejectedException("Value for fetchBatchSize is < -1, it should be >= -1.");
                     }
                  }
               default:
                  ++i;
                  break;
               case 2:
               case 3:
                  throw new AssertionError("Unexpected BeanUpdateEvent: " + event);
            }
         }

      }

      public void activateUpdate(BeanUpdateEvent event) {
         if (BasePersistenceUnitInfo.DEBUG.isDebugEnabled()) {
            BasePersistenceUnitInfo.DEBUG.debug("activateUpdate: " + event);
         }

         BeanUpdateEvent.PropertyUpdate[] list = event.getUpdateList();
         PersistenceUnitConfigurationBean pucb = (PersistenceUnitConfigurationBean)event.getProposedBean();
         int i = 0;

         while(i < list.length) {
            BeanUpdateEvent.PropertyUpdate prop = list[i];
            switch (prop.getUpdateType()) {
               case 1:
                  String propertyName = prop.getPropertyName();
                  if (BasePersistenceUnitInfo.DEBUG.isDebugEnabled()) {
                     BasePersistenceUnitInfo.DEBUG.debug("Changing property of type: " + propertyName);
                  }

                  int newFetchBatchSize;
                  if (propertyName.equals("LockTimeout")) {
                     if (BasePersistenceUnitInfo.DEBUG.isDebugEnabled()) {
                        BasePersistenceUnitInfo.DEBUG.debug("Changing persistence property: " + propertyName);
                     }

                     newFetchBatchSize = pucb.getLockTimeout();
                     this.updateLockTimeout(newFetchBatchSize);
                  } else if (propertyName.equals("DataCacheTimeout")) {
                     if (BasePersistenceUnitInfo.DEBUG.isDebugEnabled()) {
                        BasePersistenceUnitInfo.DEBUG.debug("Changing persistence property: " + propertyName);
                     }

                     String pun = pucb.getName();
                     int newDataCacheTimeout = pucb.getDataCacheTimeout();
                     this.updateDataCacheTimeout(newDataCacheTimeout);
                  } else {
                     if (!propertyName.equals("FetchBatchSize")) {
                        throw new AssertionError("Unexpected propertyName: " + propertyName);
                     }

                     if (BasePersistenceUnitInfo.DEBUG.isDebugEnabled()) {
                        BasePersistenceUnitInfo.DEBUG.debug("Changing persistence property: " + propertyName);
                     }

                     newFetchBatchSize = pucb.getFetchBatchSize();
                     this.updateFetchBatchSize(newFetchBatchSize);
                  }
               default:
                  ++i;
                  break;
               case 2:
               case 3:
                  throw new AssertionError("Unexpected BeanUpdateEvent: " + event);
            }
         }

      }

      public void rollbackUpdate(BeanUpdateEvent event) {
         if (BasePersistenceUnitInfo.DEBUG.isDebugEnabled()) {
            BasePersistenceUnitInfo.DEBUG.debug("rollbackUpdate: " + event);
         }

      }

      private void updateLockTimeout(int lockTimeout) {
         EntityManagerFactory emf = this.puii.getUnwrappedEntityManagerFactory();
         OpenJPAConfigurationImpl configuration = this.getConfiguration(emf);
         configuration.lockTimeout.set(lockTimeout);
      }

      private OpenJPAConfigurationImpl getConfiguration(EntityManagerFactory emf) {
         return (OpenJPAConfigurationImpl)((OpenJPAEntityManagerFactorySPI)OpenJPAPersistence.cast(emf)).getConfiguration();
      }

      private void updateDataCacheTimeout(int timeout) {
         EntityManagerFactory emf = this.puii.getUnwrappedEntityManagerFactory();
         OpenJPAConfigurationImpl conf = this.getConfiguration(emf);
         int oldValue = conf.dataCacheTimeout.get();
         conf.dataCacheTimeout.set(timeout);
         MetaDataRepository repo = conf.getMetaDataRepositoryInstance();
         if (repo != null) {
            ClassMetaData[] metas = repo.getMetaDatas();
            if (metas != null) {
               List candidateList = new ArrayList();
               ClassMetaData[] var8 = metas;
               int var9 = metas.length;

               for(int var10 = 0; var10 < var9; ++var10) {
                  ClassMetaData meta = var8[var10];
                  if (meta.getDataCacheTimeout() == oldValue) {
                     candidateList.add(meta);
                  }
               }

               Iterator var12 = candidateList.iterator();

               while(var12.hasNext()) {
                  ClassMetaData md = (ClassMetaData)var12.next();
                  md.setDataCacheTimeout(timeout);
               }

            }
         }
      }

      private void updateFetchBatchSize(int fetchBatchSize) {
         EntityManagerFactory emf = this.puii.getUnwrappedEntityManagerFactory();
         this.getConfiguration(emf).fetchBatchSize.set(fetchBatchSize);
      }
   }
}
