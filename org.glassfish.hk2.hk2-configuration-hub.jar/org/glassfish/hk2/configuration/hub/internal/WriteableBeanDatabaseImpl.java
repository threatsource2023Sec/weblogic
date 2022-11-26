package org.glassfish.hk2.configuration.hub.internal;

import java.io.PrintStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.api.TwoPhaseResource;
import org.glassfish.hk2.api.TwoPhaseTransactionData;
import org.glassfish.hk2.configuration.hub.api.Change;
import org.glassfish.hk2.configuration.hub.api.Instance;
import org.glassfish.hk2.configuration.hub.api.Type;
import org.glassfish.hk2.configuration.hub.api.WriteableBeanDatabase;
import org.glassfish.hk2.configuration.hub.api.WriteableType;

public class WriteableBeanDatabaseImpl implements WriteableBeanDatabase {
   private final long baseRevision;
   private final HashMap types = new HashMap();
   private final HubImpl hub;
   private final TwoPhaseResourceImpl resource = new TwoPhaseResourceImpl();
   private final LinkedList changes = new LinkedList();
   private final LinkedList removedTypes = new LinkedList();
   private boolean committed = false;
   private Object commitMessage = null;

   WriteableBeanDatabaseImpl(HubImpl hub, BeanDatabaseImpl currentDatabase) {
      this.hub = hub;
      this.baseRevision = currentDatabase.getRevision();
      Iterator var3 = currentDatabase.getAllTypes().iterator();

      while(var3.hasNext()) {
         Type type = (Type)var3.next();
         this.types.put(type.getName(), new WriteableTypeImpl(this, (TypeImpl)type));
      }

   }

   public synchronized Set getAllTypes() {
      return Collections.unmodifiableSet(new HashSet(this.types.values()));
   }

   public Set getAllWriteableTypes() {
      return Collections.unmodifiableSet(new HashSet(this.types.values()));
   }

   public synchronized Type getType(String type) {
      return (Type)this.types.get(type);
   }

   public synchronized Instance getInstance(String type, String instanceKey) {
      Type t = this.getType(type);
      return t == null ? null : t.getInstance(instanceKey);
   }

   private void checkState() {
      if (this.committed) {
         throw new IllegalStateException("This database has already been committed");
      }
   }

   public synchronized WriteableType addType(String typeName) {
      if (typeName == null) {
         throw new IllegalArgumentException();
      } else {
         this.checkState();
         WriteableTypeImpl wti = new WriteableTypeImpl(this, typeName);
         this.changes.add(new ChangeImpl(Change.ChangeCategory.ADD_TYPE, wti, (String)null, (Instance)null, (Instance)null, (List)null));
         this.types.put(typeName, wti);
         return wti;
      }
   }

   public synchronized Type removeType(String typeName) {
      if (typeName == null) {
         throw new IllegalArgumentException();
      } else {
         this.checkState();
         WriteableTypeImpl retVal = (WriteableTypeImpl)this.types.remove(typeName);
         if (retVal == null) {
            return null;
         } else {
            Map instances = retVal.getInstances();
            Iterator var4 = (new HashSet(instances.keySet())).iterator();

            while(var4.hasNext()) {
               String key = (String)var4.next();
               retVal.removeInstance(key);
            }

            this.changes.add(new ChangeImpl(Change.ChangeCategory.REMOVE_TYPE, retVal, (String)null, (Instance)null, (Instance)null, (List)null));
            this.removedTypes.add(retVal);
            return retVal;
         }
      }
   }

   public synchronized WriteableType getWriteableType(String typeName) {
      this.checkState();
      return (WriteableType)this.types.get(typeName);
   }

   public synchronized WriteableType findOrAddWriteableType(String typeName) {
      if (typeName == null) {
         throw new IllegalArgumentException();
      } else {
         this.checkState();
         WriteableTypeImpl wti = (WriteableTypeImpl)this.types.get(typeName);
         return (WriteableType)(wti == null ? this.addType(typeName) : wti);
      }
   }

   public void commit() {
      Object defaultCommit;
      synchronized(this) {
         defaultCommit = this.commitMessage;
      }

      this.commit(defaultCommit);
   }

   public void commit(Object commitMessage) {
      synchronized(this) {
         this.checkState();
         this.committed = true;
      }

      this.hub.setCurrentDatabase(this, commitMessage, this.changes);
      Iterator var2 = this.removedTypes.iterator();

      while(var2.hasNext()) {
         WriteableTypeImpl removedType = (WriteableTypeImpl)var2.next();
         removedType.getHelper().dispose();
      }

      this.removedTypes.clear();
   }

   long getBaseRevision() {
      return this.baseRevision;
   }

   synchronized void addChange(Change change) {
      this.changes.add(change);
   }

   public void dumpDatabase() {
      this.dumpDatabase(System.err);
   }

   public synchronized void dumpDatabase(PrintStream output) {
      Utilities.dumpDatabase(this, output);
   }

   public String dumpDatabaseAsString() {
      return Utilities.dumpDatabaseAsString(this);
   }

   public TwoPhaseResource getTwoPhaseResource() {
      return this.resource;
   }

   public synchronized Object getCommitMessage() {
      return this.commitMessage;
   }

   public synchronized void setCommitMessage(Object commitMessage) {
      this.commitMessage = commitMessage;
   }

   private String getChanges() {
      int lcv = 1;
      StringBuffer sb = new StringBuffer("\n");

      for(Iterator var3 = this.changes.iterator(); var3.hasNext(); ++lcv) {
         Change change = (Change)var3.next();
         sb.append(lcv + ". " + change + "\n");
      }

      return sb.toString();
   }

   public String toString() {
      return "WriteableBeanDatabaseImpl(" + this.baseRevision + ",changes=" + this.getChanges() + "," + System.identityHashCode(this) + ")";
   }

   private class TwoPhaseResourceImpl implements TwoPhaseResource {
      private LinkedList completedListeners;

      private TwoPhaseResourceImpl() {
      }

      public void prepareDynamicConfiguration(TwoPhaseTransactionData dynamicConfiguration) throws MultiException {
         Object defaultCommit;
         synchronized(WriteableBeanDatabaseImpl.this) {
            WriteableBeanDatabaseImpl.this.checkState();
            WriteableBeanDatabaseImpl.this.committed = true;
            defaultCommit = WriteableBeanDatabaseImpl.this.commitMessage;
         }

         this.completedListeners = WriteableBeanDatabaseImpl.this.hub.prepareCurrentDatabase(WriteableBeanDatabaseImpl.this, defaultCommit, WriteableBeanDatabaseImpl.this.changes);
      }

      public void activateDynamicConfiguration(TwoPhaseTransactionData dynamicConfiguration) {
         LinkedList completedListeners = this.completedListeners;
         this.completedListeners = null;
         Object defaultCommit;
         synchronized(WriteableBeanDatabaseImpl.this) {
            defaultCommit = WriteableBeanDatabaseImpl.this.commitMessage;
         }

         WriteableBeanDatabaseImpl.this.hub.activateCurrentDatabase(WriteableBeanDatabaseImpl.this, defaultCommit, WriteableBeanDatabaseImpl.this.changes, completedListeners);
         Iterator var4 = WriteableBeanDatabaseImpl.this.removedTypes.iterator();

         while(var4.hasNext()) {
            WriteableTypeImpl removedType = (WriteableTypeImpl)var4.next();
            removedType.getHelper().dispose();
         }

         WriteableBeanDatabaseImpl.this.removedTypes.clear();
      }

      public void rollbackDynamicConfiguration(TwoPhaseTransactionData dynamicConfiguration) {
         LinkedList completedListeners = this.completedListeners;
         this.completedListeners = null;
         Object defaultCommit;
         synchronized(WriteableBeanDatabaseImpl.this) {
            defaultCommit = WriteableBeanDatabaseImpl.this.commitMessage;
         }

         WriteableBeanDatabaseImpl.this.hub.rollbackCurrentDatabase(WriteableBeanDatabaseImpl.this, defaultCommit, WriteableBeanDatabaseImpl.this.changes, completedListeners);
         Iterator var4 = WriteableBeanDatabaseImpl.this.removedTypes.iterator();

         while(var4.hasNext()) {
            WriteableTypeImpl removedType = (WriteableTypeImpl)var4.next();
            removedType.getHelper().dispose();
         }

         WriteableBeanDatabaseImpl.this.removedTypes.clear();
      }

      // $FF: synthetic method
      TwoPhaseResourceImpl(Object x1) {
         this();
      }
   }
}
