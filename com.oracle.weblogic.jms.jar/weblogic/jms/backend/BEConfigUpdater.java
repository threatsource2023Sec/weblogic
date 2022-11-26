package weblogic.jms.backend;

import javax.management.InvalidAttributeValueException;
import weblogic.jms.JMSLogger;
import weblogic.management.ManagementException;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.FileStoreMBean;
import weblogic.management.configuration.JDBCStoreMBean;
import weblogic.management.configuration.JMSFileStoreMBean;
import weblogic.management.configuration.JMSJDBCStoreMBean;
import weblogic.management.configuration.JMSServerMBean;
import weblogic.management.configuration.JMSStoreMBean;
import weblogic.management.configuration.JMSTemplateMBean;
import weblogic.management.configuration.PersistentStoreMBean;
import weblogic.management.provider.ConfigurationProcessor;
import weblogic.management.provider.UpdateException;

public class BEConfigUpdater implements ConfigurationProcessor {
   public void updateConfiguration(DomainMBean root) throws UpdateException {
      try {
         JMSServerMBean[] servers = root.getJMSServers();

         int inc;
         for(inc = 0; servers != null && inc < servers.length; ++inc) {
            this.updateJMSServer(root, servers[inc]);
         }

         JMSStoreMBean[] stores = root.getJMSStores();

         for(inc = 0; stores != null && inc < stores.length; ++inc) {
            if (stores[inc] instanceof JMSFileStoreMBean) {
               this.updateFileStore(root, (JMSFileStoreMBean)stores[inc]);
            } else {
               if (!(stores[inc] instanceof JMSJDBCStoreMBean)) {
                  throw new AssertionError("Unknown JMSStoreMBean type");
               }

               this.updateJDBCStore(root, (JMSJDBCStoreMBean)stores[inc]);
            }
         }

      } catch (InvalidAttributeValueException var5) {
         throw new UpdateException(var5);
      } catch (ManagementException var6) {
         throw new UpdateException(var6);
      }
   }

   private void updateJMSServer(DomainMBean root, JMSServerMBean server) throws InvalidAttributeValueException, ManagementException {
      JMSStoreMBean store = server.getStore();
      JMSFileStoreMBean oldBean;
      if (store != null) {
         server.setStore((JMSStoreMBean)null);
         Object newStore;
         if (store instanceof JMSFileStoreMBean) {
            oldBean = (JMSFileStoreMBean)store;
            newStore = this.updateFileStore(root, oldBean);
         } else {
            if (!(store instanceof JMSJDBCStoreMBean)) {
               throw new AssertionError("Unknown JMSStoreMBean type");
            }

            JMSJDBCStoreMBean oldBean = (JMSJDBCStoreMBean)store;
            newStore = this.updateJDBCStore(root, oldBean);
         }

         ((PersistentStoreMBean)newStore).setTargets(server.getTargets());
         server.setPersistentStore((PersistentStoreMBean)newStore);
      } else if (server.getPersistentStore() == null) {
         server.setStoreEnabled(false);
      }

      JMSStoreMBean pagingStore = server.getPagingStore();
      if (pagingStore != null) {
         server.setPagingStore((JMSStoreMBean)null);
         if (pagingStore instanceof JMSFileStoreMBean) {
            JMSLogger.logReplacingJMSPagingStore(server.getName());
            oldBean = (JMSFileStoreMBean)pagingStore;
            server.setPagingDirectory(oldBean.getDirectory());
            root.destroyJMSFileStore(oldBean);
         } else {
            if (!(pagingStore instanceof JMSJDBCStoreMBean)) {
               throw new AssertionError("Unknown JMSStoreMBean type");
            }

            JMSLogger.logReplacingJMSJDBCPagingStore(server.getName());
         }
      }

      JMSTemplateMBean temporaryTemplate = server.getTemporaryTemplate();
      if (temporaryTemplate != null) {
         server.setHostingTemporaryDestinations(true);
         server.setTemporaryTemplateResource("interop-jms");
         server.setTemporaryTemplateName(temporaryTemplate.getName());
      } else if (server.getTemporaryTemplateResource() == null && server.getTemporaryTemplateName() == null) {
         server.setHostingTemporaryDestinations(false);
      }

      if (!server.isSet("AllowsPersistentDowngrade")) {
         server.setAllowsPersistentDowngrade(true);
      }

   }

   private FileStoreMBean updateFileStore(DomainMBean root, JMSFileStoreMBean oldBean) throws InvalidAttributeValueException, ManagementException {
      JMSLogger.logReplacingJMSFileStoreMBean(oldBean.getName());
      FileStoreMBean newBean = root.createFileStore(oldBean.getName());
      if (oldBean.getDirectory() != null) {
         newBean.setDirectory(oldBean.getDirectory());
      }

      if (oldBean.getSynchronousWritePolicy() != null) {
         newBean.setSynchronousWritePolicy(oldBean.getSynchronousWritePolicy());
      } else {
         newBean.setSynchronousWritePolicy("Direct-Write");
      }

      root.destroyJMSFileStore(oldBean);
      return newBean;
   }

   private JDBCStoreMBean updateJDBCStore(DomainMBean root, JMSJDBCStoreMBean oldBean) throws InvalidAttributeValueException {
      JMSLogger.logReplacingJMSJDBCStoreMBean(oldBean.getName());
      JDBCStoreMBean newBean = root.createJDBCStore(oldBean.getName());
      return newBean;
   }
}
