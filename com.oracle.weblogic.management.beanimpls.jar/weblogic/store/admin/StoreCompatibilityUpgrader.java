package weblogic.store.admin;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.management.InvalidAttributeValueException;
import weblogic.management.DistributedManagementException;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.FileStoreMBean;
import weblogic.management.configuration.JDBCStoreMBean;
import weblogic.management.configuration.JMSFileStoreMBean;
import weblogic.management.configuration.JMSJDBCStoreMBean;
import weblogic.management.configuration.JMSServerMBean;
import weblogic.management.configuration.JMSStoreMBean;
import weblogic.management.configuration.PersistentStoreMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.mbeans.custom.JMSFileStore;
import weblogic.management.mbeans.custom.JMSJDBCStore;
import weblogic.management.provider.AccessCallback;
import weblogic.management.provider.ConfigurationProcessor;
import weblogic.store.common.StoreDebug;
import weblogic.utils.ArrayUtils;

public class StoreCompatibilityUpgrader implements ConfigurationProcessor, PropertyChangeListener, ArrayUtils.DiffHandler, AccessCallback {
   private DomainMBean domainBean;

   public void shutdown() {
      this.domainBean.removePropertyChangeListener(this);
   }

   public void accessed(DomainMBean domain) {
      this.domainBean = domain;
      this.updateConfiguration(domain);
      domain.addPropertyChangeListener(this);
      JMSServerMBean[] jmsServers = domain.getJMSServers();

      for(int inc = 0; jmsServers != null && inc < jmsServers.length; ++inc) {
         jmsServers[inc].addPropertyChangeListener(new ServerListener(jmsServers[inc]));
      }

   }

   public void updateConfiguration(DomainMBean domain) {
      FileStoreMBean[] fileStores = domain.getFileStores();

      int inc;
      for(inc = 0; fileStores != null && inc < fileStores.length; ++inc) {
         createJMSFileStoreDelegate(domain, fileStores[inc]);
      }

      JDBCStoreMBean[] jdbcStores = domain.getJDBCStores();

      for(inc = 0; jdbcStores != null && inc < jdbcStores.length; ++inc) {
         createJMSJDBCStoreDelegate(domain, jdbcStores[inc]);
      }

      JMSServerMBean[] jmsServers = domain.getJMSServers();

      for(inc = 0; jmsServers != null && inc < jmsServers.length; ++inc) {
         setPersistentStoreAttribute(domain, jmsServers[inc], jmsServers[inc].getPersistentStore());
         setPagingDirectoryAttribute(domain, jmsServers[inc], jmsServers[inc].getPagingDirectory());
      }

   }

   private static void createJMSFileStoreDelegate(DomainMBean domain, FileStoreMBean bean) {
      if (domain.lookupJMSFileStore(bean.getName()) == null) {
         JMSFileStoreMBean compat = domain.createJMSFileStore(bean.getName());
         compat.setDelegatedBean(bean);
         if (StoreDebug.storeAdmin.isDebugEnabled()) {
            StoreDebug.storeAdmin.debug("Created a new JMSFileStoreMBean named " + bean.getName());
         }
      }

   }

   private static void createJMSJDBCStoreDelegate(DomainMBean domain, JDBCStoreMBean bean) {
      if (domain.lookupJMSJDBCStore(bean.getName()) == null) {
         JMSJDBCStoreMBean compat = domain.createJMSJDBCStore(bean.getName());
         compat.setDelegatedBean(bean);
         if (StoreDebug.storeAdmin.isDebugEnabled()) {
            StoreDebug.storeAdmin.debug("Created a new JMSJDBCStoreMBean named " + bean.getName());
         }
      }

   }

   private static void createFileStoreDelegate(DomainMBean domain, JMSFileStoreMBean compat) {
      if (domain.lookupFileStore(compat.getName()) == null) {
         FileStoreMBean bean = domain.createFileStore(compat.getName());
         JMSFileStore.copy(compat, bean);
         compat.setDelegatedBean(bean);
         if (StoreDebug.storeAdmin.isDebugEnabled()) {
            StoreDebug.storeAdmin.debug("Created a new FileStoreMBean named " + compat.getName());
         }
      }

   }

   private static void createJDBCStoreDelegate(DomainMBean domain, JMSJDBCStoreMBean compat) {
      if (domain.lookupJDBCStore(compat.getName()) == null) {
         JDBCStoreMBean bean = domain.createJDBCStore(compat.getName());
         JMSJDBCStore.copy(compat, bean);
         compat.setDelegatedBean(bean);
         if (StoreDebug.storeAdmin.isDebugEnabled()) {
            StoreDebug.storeAdmin.debug("Created a new JDBCStoreMBean named " + compat.getName());
         }
      }

   }

   public void propertyChange(PropertyChangeEvent event) {
      String name = event.getPropertyName();
      if (name.equals("JDBCStores") || name.equals("FileStores") || name.equals("JMSJDBCStores") || name.equals("JMSFileStores") || name.equals("JMSServers")) {
         ArrayUtils.computeDiff((Object[])((Object[])event.getOldValue()), (Object[])((Object[])event.getNewValue()), this);
      }

   }

   public void addObject(Object o) {
      if (o instanceof JMSFileStoreMBean) {
         createFileStoreDelegate(this.domainBean, (JMSFileStoreMBean)o);
      } else if (o instanceof JMSJDBCStoreMBean) {
         createJDBCStoreDelegate(this.domainBean, (JMSJDBCStoreMBean)o);
      } else if (o instanceof FileStoreMBean) {
         createJMSFileStoreDelegate(this.domainBean, (FileStoreMBean)o);
      } else if (o instanceof JDBCStoreMBean) {
         createJMSJDBCStoreDelegate(this.domainBean, (JDBCStoreMBean)o);
      } else if (o instanceof JMSServerMBean) {
         JMSServerMBean serverBean = (JMSServerMBean)o;
         serverBean.addPropertyChangeListener(new ServerListener(serverBean));
      }

   }

   public void removeObject(Object o) {
      if (o instanceof JMSFileStoreMBean) {
         FileStoreMBean deadBean = this.domainBean.lookupFileStore(((JMSFileStoreMBean)o).getName());
         if (deadBean != null) {
            this.domainBean.destroyFileStore(deadBean);
         }
      } else if (o instanceof JMSJDBCStoreMBean) {
         JDBCStoreMBean deadBean = this.domainBean.lookupJDBCStore(((JMSJDBCStoreMBean)o).getName());
         if (deadBean != null) {
            this.domainBean.destroyJDBCStore(deadBean);
         }
      } else if (o instanceof FileStoreMBean) {
         JMSFileStoreMBean deadBean = this.domainBean.lookupJMSFileStore(((FileStoreMBean)o).getName());
         if (deadBean != null) {
            this.domainBean.destroyJMSFileStore(deadBean);
         }
      } else if (o instanceof JDBCStoreMBean) {
         JMSJDBCStoreMBean deadBean = this.domainBean.lookupJMSJDBCStore(((JDBCStoreMBean)o).getName());
         if (deadBean != null) {
            this.domainBean.destroyJMSJDBCStore(deadBean);
         }
      }

   }

   private static void updateStoreAndTargets(DomainMBean domain, JMSServerMBean server, JMSStoreMBean oldStore, TargetMBean[] targets) {
      try {
         PersistentStoreMBean newStore = server.getPersistentStore();
         if (oldStore != null && !areTargetsNull(targets) && (newStore == null || !newStore.getName().equals(oldStore.getName()))) {
            Object setStore;
            if (oldStore instanceof JMSFileStoreMBean) {
               setStore = domain.lookupFileStore(oldStore.getName());
            } else {
               setStore = domain.lookupJDBCStore(oldStore.getName());
            }

            if (setStore != null) {
               if (areTargetsNull(((PersistentStoreMBean)setStore).getTargets())) {
                  ((PersistentStoreMBean)setStore).setTargets(server.getTargets());
               }

               server.setPersistentStore((PersistentStoreMBean)setStore);
               if (StoreDebug.storeAdmin.isDebugEnabled()) {
                  StoreDebug.storeAdmin.debug("JMSServerMBean " + server.getName() + ": Set PersistentStore to " + ((PersistentStoreMBean)setStore).getName());
               }
            }
         }
      } catch (InvalidAttributeValueException var6) {
         if (StoreDebug.storeAdmin.isDebugEnabled()) {
            StoreDebug.storeAdmin.debug("Error setting PersistentStore", var6);
         }
      } catch (DistributedManagementException var7) {
         if (StoreDebug.storeAdmin.isDebugEnabled()) {
            StoreDebug.storeAdmin.debug("Error setting PersistentStore", var7);
         }
      }

   }

   private static void setPersistentStoreAttribute(DomainMBean domain, JMSServerMBean server, PersistentStoreMBean newStore) {
      try {
         JMSStoreMBean oldStore = server.getStore();
         if (newStore == null && oldStore != null) {
            server.setStore((JMSStoreMBean)null);
            if (StoreDebug.storeAdmin.isDebugEnabled()) {
               StoreDebug.storeAdmin.debug("JMSServerMBean " + server.getName() + ": Set Store to null");
            }
         } else if (newStore != null && (oldStore == null || !oldStore.getName().equals(newStore.getName()))) {
            Object setStore;
            if (newStore instanceof FileStoreMBean) {
               setStore = domain.lookupJMSFileStore(newStore.getName());
            } else {
               setStore = domain.lookupJMSJDBCStore(newStore.getName());
            }

            if (setStore != null) {
               server.setStore((JMSStoreMBean)setStore);
               if (StoreDebug.storeAdmin.isDebugEnabled()) {
                  StoreDebug.storeAdmin.debug("JMSServerMBean " + server.getName() + ": Set Store to " + ((JMSStoreMBean)setStore).getName());
               }
            }
         }
      } catch (InvalidAttributeValueException var5) {
         if (StoreDebug.storeAdmin.isDebugEnabled()) {
            StoreDebug.storeAdmin.debug("Error setting Store", var5);
         }
      }

   }

   private static void setPagingDirectoryAttribute(DomainMBean domain, JMSServerMBean server, String directory) {
      try {
         JMSStoreMBean pagingStore = server.getPagingStore();
         if (isStringEmpty(directory) && pagingStore != null) {
            server.setPagingStore((JMSStoreMBean)null);
            if (pagingStore instanceof JMSFileStoreMBean) {
               domain.destroyJMSFileStore((JMSFileStoreMBean)pagingStore);
            }

            if (StoreDebug.storeAdmin.isDebugEnabled()) {
               StoreDebug.storeAdmin.debug("JMSServer " + server.getName() + " set PagingDirectory to null and deleted paging store");
            }
         } else if (!isStringEmpty(directory) && pagingStore == null) {
            JMSFileStoreMBean fStore = domain.createJMSFileStore(server.getName() + "PagingStore");
            fStore.setSynchronousWritePolicy("Disabled");
            fStore.setDelegatedJMSServer(server);
            server.setPagingStore(fStore);
            if (StoreDebug.storeAdmin.isDebugEnabled()) {
               StoreDebug.storeAdmin.debug("JMSServer " + server.getName() + " created a JMSFileStore and set it to the PagingStore attribute");
            }
         }
      } catch (InvalidAttributeValueException var5) {
         if (StoreDebug.storeAdmin.isDebugEnabled()) {
            StoreDebug.storeAdmin.debug("Error setting PagingStore attribute", var5);
         }
      } catch (DistributedManagementException var6) {
         if (StoreDebug.storeAdmin.isDebugEnabled()) {
            StoreDebug.storeAdmin.debug("Error setting PagingStore attribute", var6);
         }
      }

   }

   private static void setPagingStoreAttribute(DomainMBean domain, JMSServerMBean server, JMSStoreMBean pagingStore) {
      try {
         String pagingDirectory = server.getPagingDirectory();
         if (pagingStore == null && !isStringEmpty(pagingDirectory)) {
            server.setPagingDirectory((String)null);
            if (StoreDebug.storeAdmin.isDebugEnabled()) {
               StoreDebug.storeAdmin.debug("JMSServer " + server.getName() + " setting PagingDirectory to null");
            }
         } else if (pagingStore != null && pagingStore instanceof JMSFileStoreMBean && isStringEmpty(pagingDirectory)) {
            JMSFileStoreMBean pagingStoreBean = (JMSFileStoreMBean)pagingStore;

            try {
               if (pagingStoreBean.getDirectory() != null) {
                  server.setPagingDirectory(pagingStoreBean.getDirectory());
               }
            } catch (InvalidAttributeValueException var6) {
            }

            pagingStoreBean.setDelegatedJMSServer(server);
            if (StoreDebug.storeAdmin.isDebugEnabled()) {
               StoreDebug.storeAdmin.debug("JMSServer " + server.getName() + " setting PagingDirectory from PagingStore parameters");
            }
         }
      } catch (InvalidAttributeValueException var7) {
         if (StoreDebug.storeAdmin.isDebugEnabled()) {
            StoreDebug.storeAdmin.debug("Error setting PagingDirectory attribute", var7);
         }
      }

   }

   private static boolean isStringEmpty(String str) {
      return str == null || str.length() == 0;
   }

   private static boolean areTargetsNull(TargetMBean[] targets) {
      return targets == null || targets.length == 0;
   }

   private final class ServerListener implements PropertyChangeListener {
      private JMSServerMBean server;

      ServerListener(JMSServerMBean server) {
         this.server = server;
      }

      public void propertyChange(PropertyChangeEvent event) {
         String name = event.getPropertyName();
         if (name.equals("Targets")) {
            StoreCompatibilityUpgrader.updateStoreAndTargets(StoreCompatibilityUpgrader.this.domainBean, this.server, this.server.getStore(), (TargetMBean[])((TargetMBean[])event.getNewValue()));
         }

      }
   }
}
