package org.glassfish.hk2.configuration.hub.internal;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import javax.inject.Inject;
import org.glassfish.hk2.api.DescriptorVisibility;
import org.glassfish.hk2.api.IterableProvider;
import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.api.Visibility;
import org.glassfish.hk2.configuration.hub.api.BeanDatabase;
import org.glassfish.hk2.configuration.hub.api.BeanDatabaseUpdateListener;
import org.glassfish.hk2.configuration.hub.api.CommitFailedException;
import org.glassfish.hk2.configuration.hub.api.Hub;
import org.glassfish.hk2.configuration.hub.api.PrepareFailedException;
import org.glassfish.hk2.configuration.hub.api.RollbackFailedException;
import org.glassfish.hk2.configuration.hub.api.WriteableBeanDatabase;
import org.jvnet.hk2.annotations.ContractsProvided;
import org.jvnet.hk2.annotations.Service;

@Service
@ContractsProvided({Hub.class})
@Visibility(DescriptorVisibility.LOCAL)
public class HubImpl implements Hub {
   private static final AtomicLong revisionCounter = new AtomicLong(1L);
   private final Object lock = new Object();
   private BeanDatabaseImpl currentDatabase;
   @Inject
   private IterableProvider listeners;
   private int inTransaction;

   public HubImpl() {
      this.currentDatabase = new BeanDatabaseImpl(revisionCounter.getAndIncrement());
      this.inTransaction = 0;
   }

   public BeanDatabase getCurrentDatabase() {
      synchronized(this.lock) {
         return this.currentDatabase;
      }
   }

   public WriteableBeanDatabase getWriteableDatabaseCopy() {
      synchronized(this.lock) {
         return new WriteableBeanDatabaseImpl(this, this.currentDatabase);
      }
   }

   LinkedList prepareCurrentDatabase(WriteableBeanDatabaseImpl writeableDatabase, Object commitMessage, List changes) {
      synchronized(this.lock) {
         if (this.inTransaction > 0) {
            throw new IllegalStateException("This Hub is already in a transaction");
         } else {
            long currentRevision = this.currentDatabase.getRevision();
            long writeRevision = writeableDatabase.getBaseRevision();
            if (currentRevision != writeRevision) {
               throw new IllegalStateException("commit was called on a WriteableDatabase but the current database has changed after that copy was made");
            } else {
               LinkedList completedListeners = new LinkedList();
               Iterator var10 = this.listeners.iterator();

               while(var10.hasNext()) {
                  BeanDatabaseUpdateListener listener = (BeanDatabaseUpdateListener)var10.next();

                  try {
                     listener.prepareDatabaseChange(this.currentDatabase, writeableDatabase, commitMessage, changes);
                     completedListeners.add(listener);
                  } catch (Throwable var19) {
                     MultiException throwMe = new MultiException(new PrepareFailedException(var19));
                     Iterator var14 = completedListeners.iterator();

                     while(var14.hasNext()) {
                        BeanDatabaseUpdateListener completedListener = (BeanDatabaseUpdateListener)var14.next();

                        try {
                           completedListener.rollbackDatabaseChange(this.currentDatabase, writeableDatabase, commitMessage, changes);
                        } catch (Throwable var18) {
                           throwMe.addError(new RollbackFailedException(var18));
                        }
                     }

                     throw throwMe;
                  }
               }

               ++this.inTransaction;
               return completedListeners;
            }
         }
      }
   }

   void activateCurrentDatabase(WriteableBeanDatabaseImpl writeableDatabase, Object commitMessage, List changes, LinkedList completedListeners) {
      synchronized(this.lock) {
         --this.inTransaction;
         if (this.inTransaction < 0) {
            this.inTransaction = 0;
         }

         List completed = completedListeners;
         completedListeners = null;
         if (completed == null) {
            completed = Collections.emptyList();
         }

         BeanDatabaseImpl oldDatabase = this.currentDatabase;
         this.currentDatabase = new BeanDatabaseImpl(revisionCounter.getAndIncrement(), writeableDatabase);
         MultiException commitError = null;
         Iterator var9 = ((List)completed).iterator();

         while(var9.hasNext()) {
            BeanDatabaseUpdateListener completedListener = (BeanDatabaseUpdateListener)var9.next();

            try {
               completedListener.commitDatabaseChange(oldDatabase, this.currentDatabase, commitMessage, changes);
            } catch (Throwable var13) {
               if (commitError == null) {
                  commitError = new MultiException(new CommitFailedException(var13));
               } else {
                  commitError.addError(new CommitFailedException(var13));
               }
            }
         }

         if (commitError != null) {
            throw commitError;
         }
      }
   }

   void rollbackCurrentDatabase(WriteableBeanDatabaseImpl writeableDatabase, Object commitMessage, List changes, LinkedList completedListeners) {
      synchronized(this.lock) {
         --this.inTransaction;
         if (this.inTransaction < 0) {
            this.inTransaction = 0;
         }

         List completed = completedListeners;
         completedListeners = null;
         if (completed == null) {
            completed = Collections.emptyList();
         }

         MultiException rollbackError = null;
         Iterator var8 = ((List)completed).iterator();

         while(var8.hasNext()) {
            BeanDatabaseUpdateListener completedListener = (BeanDatabaseUpdateListener)var8.next();

            try {
               completedListener.rollbackDatabaseChange(this.currentDatabase, writeableDatabase, commitMessage, changes);
            } catch (Throwable var12) {
               if (rollbackError == null) {
                  rollbackError = new MultiException(new RollbackFailedException(var12));
               } else {
                  rollbackError.addError(new RollbackFailedException(var12));
               }
            }
         }

         if (rollbackError != null) {
            throw rollbackError;
         }
      }
   }

   void setCurrentDatabase(WriteableBeanDatabaseImpl writeableDatabase, Object commitMessage, List changes) {
      LinkedList completedListeners = this.prepareCurrentDatabase(writeableDatabase, commitMessage, changes);
      this.activateCurrentDatabase(writeableDatabase, commitMessage, changes, completedListeners);
   }
}
