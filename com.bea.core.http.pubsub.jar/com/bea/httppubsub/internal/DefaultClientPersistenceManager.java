package com.bea.httppubsub.internal;

import com.bea.httppubsub.AuthenticatedUser;
import com.bea.httppubsub.Client;
import com.bea.httppubsub.LocalClient;
import com.bea.httppubsub.PubSubContext;
import com.bea.httppubsub.PubSubServer;
import com.bea.httppubsub.PubSubServerException;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import weblogic.store.ObjectHandler;
import weblogic.store.PersistentHandle;
import weblogic.store.PersistentStore;
import weblogic.store.PersistentStoreConnection;
import weblogic.store.PersistentStoreException;
import weblogic.store.PersistentStoreRecord;
import weblogic.store.PersistentStoreTransaction;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.work.WorkManagerFactory;

public class DefaultClientPersistenceManager implements ClientPersistenceManager {
   private static final String CLIENT_CONNECTION_NAME = "com.bea.httppubsub.clientconn.";
   private static final String TIMEMANGER_PREFIX = "com.bea.httppubsub.clientmanager.timemanager.";
   private static final long MIN_SCAVANGE_INTERVAL = 60000L;
   private PersistentStore store = null;
   private PersistentStoreConnection clientConnection = null;
   private ConcurrentHashMap principalToRecord;
   private long offlineClientTimeout;
   private String clientConnectionName = null;
   private long scavangeInterval;
   private TimerManager tm = null;

   public DefaultClientPersistenceManager(PubSubServer server) throws PubSubServerException {
      try {
         this.store = DefaultPersistentStoreManager.getInstance().getDefaultStore();
         PubSubContext pubsubCtx = server.getContext();
         String contextPath = pubsubCtx.getServletContextPath();
         if (contextPath == null) {
            contextPath = "";
         }

         this.clientConnectionName = "com.bea.httppubsub.clientconn." + contextPath + "_" + server.getName();
         this.clientConnection = this.store.createConnection(this.clientConnectionName, new ClientObjectHandler());
         this.principalToRecord = new ConcurrentHashMap();
         this.tm = TimerManagerFactory.getTimerManagerFactory().getTimerManager("com.bea.httppubsub.clientmanager.timemanager." + server.getName(), WorkManagerFactory.getInstance().getSystem());
      } catch (PersistentStoreException var4) {
         throw new PubSubServerException("Can't get default store");
      }

      this.offlineClientTimeout = (long)server.getPersistentClientTimeout();
      this.scavangeInterval = this.offlineClientTimeout / 10L;
      if (this.scavangeInterval < 60000L) {
         this.scavangeInterval = 60000L;
      }

   }

   void setScavangeInterval(int interval) {
      this.scavangeInterval = (long)interval;
   }

   public void init() {
      this.loadClientRecords();
      this.startScavenger();
   }

   public void destroy() {
      this.stopScavenger();
   }

   public PersistedClientRecord fetchClientRecord(Client client) {
      String principalName = this.getPrincipalName(client);
      if (principalName != null && principalName.length() != 0) {
         long now = System.currentTimeMillis();
         PersistedClientRecord clientRecord = (PersistedClientRecord)this.principalToRecord.get(principalName);
         return clientRecord != null && !this.isTimeOut(clientRecord.getLastAccessTime(), now) ? clientRecord : null;
      } else {
         return null;
      }
   }

   public void storeClientRecord(Client client) {
      String principalName = this.getPrincipalName(client);
      if (principalName != null && principalName.length() != 0) {
         if (!this.isTimeOut(client.getLastAccessTime(), System.currentTimeMillis())) {
            PersistentStoreTransaction tx = null;

            try {
               tx = this.store.begin();
               PersistedClientRecord clientRecord = (PersistedClientRecord)this.principalToRecord.get(principalName);
               if (clientRecord == null) {
                  clientRecord = new PersistedClientRecord();
                  clientRecord.setPrincipalName(principalName);
                  clientRecord.setLastAccessTime(client.getLastAccessTime());
                  PersistentHandle handle = this.clientConnection.create(tx, clientRecord, 0);
                  clientRecord.setHandle(handle);
                  this.principalToRecord.put(principalName, clientRecord);
               } else {
                  synchronized(clientRecord) {
                     this.clientConnection.delete(tx, clientRecord.getHandle(), 0);
                     clientRecord.setLastAccessTime(client.getLastAccessTime());
                     PersistentHandle handle = this.clientConnection.create(tx, clientRecord, 0);
                     clientRecord.setHandle(handle);
                  }
               }

               tx.commit();
            } catch (PersistentStoreException var9) {
               this.rollbackStoreTranscation(tx);
            }

         }
      }
   }

   private String getPrincipalName(Client client) {
      if (client != null && !(client instanceof LocalClient)) {
         AuthenticatedUser authUser = client.getAuthenticatedUser();
         if (authUser == null) {
            return null;
         } else {
            Principal principal = authUser.getUserPrincipal();
            return principal == null ? null : principal.getName();
         }
      } else {
         return null;
      }
   }

   private void loadClientRecords() {
      if (this.store != null && this.clientConnection != null) {
         PersistentStoreTransaction tx = null;

         try {
            tx = this.store.begin();
            PersistentStoreConnection.Cursor cursor = this.clientConnection.createCursor(0);
            long now = System.currentTimeMillis();
            List staleHandlers = new ArrayList();

            PersistentStoreRecord record;
            while((record = cursor.next()) != null) {
               PersistentHandle handle = record.getHandle();
               PersistedClientRecord clientRecord = (PersistedClientRecord)record.getData();
               if (this.isTimeOut(clientRecord.getLastAccessTime(), now)) {
                  staleHandlers.add(handle);
               } else {
                  clientRecord.setHandle(handle);
                  this.principalToRecord.put(clientRecord.getPrincipalName(), clientRecord);
               }
            }

            if (staleHandlers.size() != 0) {
               Iterator var10 = staleHandlers.iterator();

               while(var10.hasNext()) {
                  PersistentHandle handle = (PersistentHandle)var10.next();
                  this.clientConnection.delete(tx, handle, 1);
               }
            }

            tx.commit();
         } catch (PersistentStoreException var9) {
            var9.printStackTrace();
            this.rollbackStoreTranscation(tx);
         }

      }
   }

   protected void setScavangeInterval(long interval) {
      this.scavangeInterval = interval;
   }

   private void startScavenger() {
      TimerListener tunscav = new TimerListener() {
         public void timerExpired(Timer timer) {
            if (DefaultClientPersistenceManager.this.clientConnection == null) {
               timer.cancel();
            } else {
               long now = System.currentTimeMillis();
               PersistentStoreTransaction tx = null;

               try {
                  tx = DefaultClientPersistenceManager.this.store.begin();
                  List handleToBeDeleted = new ArrayList();
                  Iterator it = DefaultClientPersistenceManager.this.principalToRecord.values().iterator();

                  PersistedClientRecord handle;
                  while(it.hasNext()) {
                     handle = (PersistedClientRecord)it.next();
                     if (DefaultClientPersistenceManager.this.isTimeOut(handle.getLastAccessTime(), now) && DefaultClientPersistenceManager.this.principalToRecord.remove(handle.getPrincipalName(), handle)) {
                        handleToBeDeleted.add(handle);
                     }
                  }

                  it = handleToBeDeleted.iterator();

                  while(it.hasNext()) {
                     handle = (PersistedClientRecord)it.next();
                     synchronized(handle) {
                        DefaultClientPersistenceManager.this.clientConnection.delete(tx, handle.getHandle(), 1);
                     }
                  }

                  tx.commit();
               } catch (PersistentStoreException var11) {
                  DefaultClientPersistenceManager.this.rollbackStoreTranscation(tx);
               }

            }
         }
      };
      this.tm.scheduleAtFixedRate(tunscav, this.scavangeInterval, this.scavangeInterval);
   }

   private void stopScavenger() {
      if (this.tm != null && !this.tm.isStopped()) {
         this.tm.stop();
      }

   }

   private boolean isTimeOut(long lat, long now) {
      return lat + this.offlineClientTimeout <= now;
   }

   private void rollbackStoreTranscation(PersistentStoreTransaction tx) {
      if (tx != null) {
         try {
            tx.rollback();
         } catch (Exception var3) {
         }
      }

   }

   private void closeStore() {
      this.closeStoreConnection(this.clientConnection);
      this.closeStore(this.store);
   }

   private void closeStoreConnection(PersistentStoreConnection conn) {
      if (conn != null) {
         conn.close();
      }

   }

   private void closeStore(PersistentStore store) {
      if (store != null) {
         try {
            store.close();
         } catch (PersistentStoreException var3) {
         }
      }

   }

   private class ClientObjectHandler implements ObjectHandler {
      private ClientObjectHandler() {
      }

      public Object readObject(ObjectInput in) throws ClassNotFoundException, IOException {
         PersistedClientRecord cr = new PersistedClientRecord();
         cr.readExternal(in);
         return cr;
      }

      public void writeObject(ObjectOutput out, Object obj) throws IOException {
         if (obj instanceof PersistedClientRecord) {
            ((PersistedClientRecord)obj).writeExternal(out);
         }

      }

      // $FF: synthetic method
      ClientObjectHandler(Object x1) {
         this();
      }
   }
}
