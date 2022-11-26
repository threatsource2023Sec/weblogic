package weblogic.jms.safclient;

import java.io.File;
import javax.jms.JMSException;
import javax.naming.Context;
import org.w3c.dom.Document;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.extensions.ClientSAF;
import weblogic.jms.safclient.admin.ConfigurationUtils;
import weblogic.jms.safclient.admin.PersistentStoreBean;
import weblogic.jms.safclient.agent.AgentManager;
import weblogic.jms.safclient.jndi.ContextImpl;
import weblogic.jms.safclient.store.StoreUtils;
import weblogic.store.xa.PersistentStoreXA;
import weblogic.transaction.TransactionHelper;

public final class ClientSAFDelegate {
   private Object lock = new Object();
   private boolean closed = true;
   private AgentManager agentManager;
   private ClientSAF userObj;
   private ContextImpl context;
   private File rootDirectory;

   ClientSAFDelegate(ClientSAF paramUserObj) {
      this.userObj = paramUserObj;
   }

   void discover(Document configuration, File rootDirectory, String discoveryFilePath, long cutoffTime) throws JMSException {
      TransactionHelper.pushTransactionHelper(ClientSAFManager.getTxHelper());
      boolean var14 = false;

      try {
         var14 = true;
         this.initStore(configuration, rootDirectory);
         this.context = new ContextImpl(this.userObj, configuration, this);
         MessageMigrator.discover(rootDirectory, StoreUtils.getStore(rootDirectory), this.context, discoveryFilePath, cutoffTime);
         var14 = false;
      } finally {
         if (var14) {
            TransactionHelper.popTransactionHelper();
            PersistentStoreXA pStore = StoreUtils.getStore(rootDirectory);
            if (pStore != null) {
               try {
                  pStore.close();
               } catch (Throwable var15) {
                  if (JMSDebug.JMSCommon.isDebugEnabled()) {
                     JMSDebug.JMSCommon.debug("Failed to close the store:" + var15.getMessage());
                  }
               }

               StoreUtils.removeStore(rootDirectory);
            }

         }
      }

      TransactionHelper.popTransactionHelper();
      PersistentStoreXA pStore = StoreUtils.getStore(rootDirectory);
      if (pStore != null) {
         try {
            pStore.close();
         } catch (Throwable var17) {
            if (JMSDebug.JMSCommon.isDebugEnabled()) {
               JMSDebug.JMSCommon.debug("Failed to close the store:" + var17.getMessage());
            }
         }

         StoreUtils.removeStore(rootDirectory);
      }

   }

   void open(Document configuration, File rootDirectory, char[] password) throws JMSException {
      synchronized(this.lock) {
         if (!this.closed) {
            return;
         }

         this.closed = false;
      }

      this.rootDirectory = rootDirectory;
      TransactionHelper.pushTransactionHelper(ClientSAFManager.getTxHelper());
      boolean openSuccessful = false;

      try {
         this.initStore(configuration, rootDirectory);
         this.context = new ContextImpl(this.userObj, configuration, this);
         MessageMigrator.migrateMessagesIfNecessary(rootDirectory, StoreUtils.getStore(rootDirectory), this.context);
         this.agentManager = new AgentManager(configuration, this.context, password, rootDirectory);
         openSuccessful = true;
      } finally {
         TransactionHelper.popTransactionHelper();
         if (!openSuccessful) {
            this.close();
         }

      }

   }

   private void initStore(Document configuration, File rootDir) throws JMSException {
      PersistentStoreBean store = ConfigurationUtils.getPersistentStore(configuration);
      File storeDirectory = new File(store.getStoreDirectory());
      if (!storeDirectory.isAbsolute()) {
         storeDirectory = new File(rootDir, store.getStoreDirectory());
      }

      if (storeDirectory.exists() && !storeDirectory.isDirectory()) {
         throw new JMSException("Store directory " + storeDirectory.getAbsolutePath() + " must be a directory");
      } else {
         StoreUtils.initStores(rootDir, storeDirectory, store.getSynchronousWritePolicy());
      }
   }

   boolean close() {
      synchronized(this.lock) {
         if (this.closed) {
            return false;
         } else {
            this.closed = true;
            if (this.context != null) {
               try {
                  this.context.shutdown(new JMSException("The client SAF system is being closed normally"));
               } catch (Throwable var6) {
                  System.out.println(var6.getMessage());
                  var6.printStackTrace();
               }
            }

            if (this.agentManager != null) {
               try {
                  this.agentManager.shutdown();
               } catch (Throwable var5) {
                  System.out.println(var5.getMessage());
                  var5.printStackTrace();
               }
            }

            PersistentStoreXA pStore = StoreUtils.getStore(this.rootDirectory);
            if (pStore != null) {
               try {
                  pStore.close();
               } catch (Throwable var7) {
                  if (JMSDebug.JMSCommon.isDebugEnabled()) {
                     JMSDebug.JMSCommon.debug("Failed to close the store:" + var7.getMessage());
                  }
               }

               StoreUtils.removeStore(this.rootDirectory);
            }

            return true;
         }
      }
   }

   ClientSAF getUserObj() {
      return this.userObj;
   }

   public boolean isOpened() {
      synchronized(this.lock) {
         return !this.closed;
      }
   }

   public Context getContext() {
      return this.context;
   }

   public AgentManager getAgentManager() {
      return this.agentManager;
   }

   public TransactionHelper getTransactionHelper() {
      return ClientSAFManager.getTxHelper();
   }
}
