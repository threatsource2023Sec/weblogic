package org.apache.openjpa.event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.kernel.Broker;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.lib.util.Closeable;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.util.concurrent.AbstractConcurrentEventManager;
import org.apache.openjpa.util.UserException;

public class RemoteCommitEventManager extends AbstractConcurrentEventManager implements EndTransactionListener, Closeable {
   private static final Localizer _loc = Localizer.forPackage(RemoteCommitEventManager.class);
   private final RemoteCommitProvider _provider;
   private boolean _transmitPersIds = false;

   public RemoteCommitEventManager(OpenJPAConfiguration conf) {
      this._provider = conf.newRemoteCommitProviderInstance();
      if (this._provider != null) {
         this._provider.setRemoteCommitEventManager(this);
      }

   }

   public boolean areRemoteEventsEnabled() {
      return this._provider != null;
   }

   public RemoteCommitProvider getRemoteCommitProvider() {
      return this._provider;
   }

   public boolean getTransmitPersistedObjectIds() {
      return this._transmitPersIds;
   }

   public void setTransmitPersistedObjectIds(boolean transmit) {
      this._transmitPersIds = transmit;
   }

   public void addInternalListener(RemoteCommitListener listen) {
      if (this._provider == null) {
         throw new UserException(_loc.get("no-provider"));
      } else {
         ((List)this._listeners).add(0, listen);
      }
   }

   public void addListener(RemoteCommitListener listen) {
      if (this._provider == null) {
         throw new UserException(_loc.get("no-provider"));
      } else {
         super.addListener(listen);
      }
   }

   public void close() {
      if (this._provider != null) {
         this._provider.close();
         Collection listeners = this.getListeners();
         Iterator itr = listeners.iterator();

         while(itr.hasNext()) {
            ((RemoteCommitListener)itr.next()).close();
         }
      }

   }

   protected void fireEvent(Object event, Object listener) {
      RemoteCommitListener listen = (RemoteCommitListener)listener;
      RemoteCommitEvent ev = (RemoteCommitEvent)event;
      listen.afterCommit(ev);
   }

   public void fireLocalStaleNotification(Object oid) {
      RemoteCommitEvent ev = new RemoteCommitEvent(3, (Collection)null, (Collection)null, Collections.singleton(oid), (Collection)null);
      this.fireEvent(ev);
   }

   public void afterCommit(TransactionEvent event) {
      if (this._provider != null) {
         RemoteCommitEvent rce = this.createRemoteCommitEvent(event);
         if (rce != null) {
            this._provider.broadcast(rce);
         }
      }

   }

   private RemoteCommitEvent createRemoteCommitEvent(TransactionEvent event) {
      Broker broker = (Broker)event.getSource();
      Collection persIds = null;
      Collection addClassNames = null;
      Collection updates = null;
      Collection deletes = null;
      int payload;
      if (broker.isTrackChangesByType()) {
         payload = 2;
         addClassNames = toClassNames(event.getPersistedTypes());
         updates = toClassNames(event.getUpdatedTypes());
         deletes = toClassNames(event.getDeletedTypes());
         if (addClassNames == null && updates == null && deletes == null) {
            return null;
         } else {
            return new RemoteCommitEvent(payload, persIds, (Collection)addClassNames, (Collection)updates, (Collection)deletes);
         }
      } else {
         Collection trans = event.getTransactionalObjects();
         if (trans.isEmpty()) {
            return null;
         } else {
            payload = this._transmitPersIds ? 1 : 0;
            Iterator itr = trans.iterator();

            while(true) {
               Object obj;
               OpenJPAStateManager sm;
               do {
                  do {
                     do {
                        do {
                           if (!itr.hasNext()) {
                              if (addClassNames == null && updates == null && deletes == null) {
                                 return null;
                              }

                              return new RemoteCommitEvent(payload, persIds, (Collection)addClassNames, (Collection)updates, (Collection)deletes);
                           }

                           obj = itr.next();
                           sm = broker.getStateManager(obj);
                        } while(sm == null);
                     } while(!sm.isPersistent());
                  } while(!sm.isDirty());
               } while(sm.isNew() && sm.isDeleted());

               Object oid = sm.fetchObjectId();
               if (sm.isNew()) {
                  if (this._transmitPersIds) {
                     if (persIds == null) {
                        persIds = new ArrayList();
                     }

                     persIds.add(oid);
                  }

                  if (addClassNames == null) {
                     addClassNames = new HashSet();
                  }

                  ((Collection)addClassNames).add(obj.getClass().getName());
               } else if (sm.isDeleted()) {
                  if (deletes == null) {
                     deletes = new ArrayList();
                  }

                  ((Collection)deletes).add(oid);
               } else {
                  if (updates == null) {
                     updates = new ArrayList();
                  }

                  ((Collection)updates).add(oid);
               }
            }
         }
      }
   }

   private static Collection toClassNames(Collection clss) {
      if (clss.isEmpty()) {
         return null;
      } else {
         List names = new ArrayList(clss);

         for(int i = 0; i < names.size(); ++i) {
            names.set(i, ((Class)names.get(i)).getName());
         }

         return names;
      }
   }

   public void beforeCommit(TransactionEvent event) {
   }

   public void afterRollback(TransactionEvent event) {
   }

   public void afterCommitComplete(TransactionEvent event) {
   }

   public void afterRollbackComplete(TransactionEvent event) {
   }

   public void afterStateTransitions(TransactionEvent event) {
   }
}
