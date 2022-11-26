package weblogic.store.xa.map;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import weblogic.common.CompletionRequest;
import weblogic.store.ObjectHandler;
import weblogic.store.PersistentHandle;
import weblogic.store.PersistentMap;
import weblogic.store.PersistentStoreConnection;
import weblogic.store.PersistentStoreException;
import weblogic.store.PersistentStoreRecord;
import weblogic.store.PersistentStoreTransaction;
import weblogic.store.gxa.GXAException;
import weblogic.store.gxa.GXAResource;
import weblogic.store.gxa.GXATransaction;
import weblogic.store.internal.LockManager;
import weblogic.store.internal.LockManagerImpl;
import weblogic.store.internal.PersistentStoreConnectionImpl;
import weblogic.store.internal.PersistentStoreTransactionImpl;
import weblogic.store.xa.internal.PersistentStoreXAImpl;
import weblogic.utils.collections.NumericKeyHashMap;

public final class PersistentMapXAImpl implements PersistentMap {
   private static final int NO_FLAGS = 0;
   private static final Object REMOVE_OBJECT = new Object();
   private PersistentStoreConnectionImpl keyConn;
   private PersistentStoreConnectionImpl valueConn;
   private final Map keyMap = Collections.synchronizedMap(new HashMap());
   private PersistentStoreXAImpl store;
   private GXAResource gxaResource;
   private long nextSeqNo = 1L;
   private LockManager lockManager = new LockManagerImpl();

   public PersistentMapXAImpl(PersistentStoreConnection keys, PersistentStoreConnection values) throws PersistentStoreException {
      this.keyConn = (PersistentStoreConnectionImpl)keys;
      this.keyConn.setObjectHandler(new ObjectHandlerImpl());
      this.valueConn = (PersistentStoreConnectionImpl)values;
      this.init();
   }

   public PersistentMapXAImpl(PersistentStoreConnection keys, PersistentStoreConnection values, ObjectHandler handler) throws PersistentStoreException {
      this.keyConn = (PersistentStoreConnectionImpl)keys;
      this.keyConn.setObjectHandler(new ObjectHandlerImpl(handler));
      this.valueConn = (PersistentStoreConnectionImpl)values;
      this.valueConn.setObjectHandler(handler);
      this.init();
   }

   private void init() throws PersistentStoreException {
      this.store = (PersistentStoreXAImpl)this.keyConn.getStore();
      if (this.store != this.valueConn.getStore()) {
         throw new PersistentStoreException("keyConn.store != valueConn.store");
      } else {
         this.gxaResource = this.store.getGXAResource();
         NumericKeyHashMap preparedEntries = new NumericKeyHashMap();
         NumericKeyHashMap committedEntries = new NumericKeyHashMap();
         PersistentStoreConnection.Cursor cursor = this.keyConn.createCursor(0);

         Entry nextEntry;
         for(PersistentStoreRecord rec = cursor.next(); rec != null; rec = cursor.next()) {
            nextEntry = (Entry)rec.getData();
            nextEntry.setHandle(rec.getHandle());
            long seqNo = nextEntry.getSeqNo();
            if (seqNo >= this.nextSeqNo) {
               this.nextSeqNo = seqNo + 1L;
            }

            Entry cur;
            if (nextEntry.getType() == 4) {
               cur = (Entry)committedEntries.remove(seqNo);
               if (cur != null) {
                  nextEntry.setNext(cur);

                  while(cur != null) {
                     cur.setGXid(nextEntry.getGXid());
                     cur = cur.getNext();
                  }
               }

               preparedEntries.put(seqNo, nextEntry);
            } else {
               cur = (Entry)preparedEntries.remove(seqNo);
               if (cur != null) {
                  nextEntry.setNext(cur);
                  nextEntry.setGXid(cur.getGXid());
                  preparedEntries.put(seqNo, nextEntry);
               } else {
                  Entry first = (Entry)committedEntries.remove(seqNo);
                  if (first != null) {
                     nextEntry.setNext(first);
                  }

                  committedEntries.put(seqNo, nextEntry);
               }
            }
         }

         Iterator i = committedEntries.values().iterator();

         Entry e;
         while(i.hasNext()) {
            for(e = (Entry)i.next(); e != null; e = nextEntry) {
               nextEntry = e.getNext();
               e.setNext((Entry)null);
               if (e.getType() != 2 && e.getType() != 1) {
                  throw new PersistentStoreException("failed recovery");
               }

               synchronized(this) {
                  this.keyMap.put(e.getKey(), e);
               }
            }
         }

         i = preparedEntries.values().iterator();

         while(i.hasNext()) {
            for(e = (Entry)i.next(); e != null; e = nextEntry) {
               nextEntry = e.getNext();
               e.setNext((Entry)null);
               e.setPMap(this);
               this.gxaResource.addRecoveredOperation(e);
            }
         }

      }
   }

   public boolean put(Object key, Object val) throws PersistentStoreException {
      GXATransaction gxaTran = this.gxaResource.enlist();
      if (gxaTran != null) {
         Entry txpe = new Entry(this, 2, key, val);
         this.lockManager.lock(gxaTran, key);

         try {
            this.gxaResource.addNewOperation(gxaTran, txpe);
         } catch (PersistentStoreException var13) {
            this.lockManager.unlock(gxaTran, key);
            throw var13;
         }

         Entry prevUpdateEntry = txpe.getPrevUpdateEntry();
         if (prevUpdateEntry != null) {
            return prevUpdateEntry.getValue() != null;
         } else {
            synchronized(this) {
               return this.keyMap.get(key) != null;
            }
         }
      } else {
         PersistentStoreTransactionImpl ptx = new PersistentStoreTransactionImpl(this.store);
         ptx.lock(this.lockManager, key);
         boolean success = false;

         boolean var7;
         try {
            boolean ret = this.apply(ptx, key, val);
            success = true;
            var7 = ret;
         } finally {
            if (success) {
               ptx.commit();
            } else {
               ptx.rollback();
            }

         }

         return var7;
      }
   }

   public Object get(Object key) throws PersistentStoreException {
      return this.localGet(key, true);
   }

   private Object localGet(Object key, boolean doRead) throws PersistentStoreException {
      GXATransaction gxaTran = this.gxaResource.enlist();
      if (gxaTran != null) {
         Entry gfu = new Entry(this, 5, key, (PersistentHandle)null);
         this.lockManager.lock(gxaTran, key);

         try {
            this.gxaResource.addNewOperation(gxaTran, gfu);
         } catch (PersistentStoreException var22) {
            this.lockManager.unlock(gxaTran, key);
            throw var22;
         }

         Entry removeOrPut = gfu.getPrevUpdateEntry();
         if (removeOrPut != null) {
            return removeOrPut.getValue();
         }
      }

      CompletionRequest cr = new CompletionRequest();
      PersistentStoreTransactionImpl ptx = new PersistentStoreTransactionImpl(this.store);
      if (gxaTran == null) {
         this.lockManager.lock(ptx, key);
      }

      Object var6;
      try {
         synchronized(this) {
            Entry entry = (Entry)this.keyMap.get(key);
            Boolean var8;
            if (entry == null) {
               var8 = null;
               return var8;
            }

            if (!doRead) {
               var8 = Boolean.TRUE;
               return var8;
            }

            this.valueConn.read(ptx, entry.getValueHandle(), cr);
         }

         try {
            var6 = ((PersistentStoreRecord)cr.getResult()).getData();
         } catch (PersistentStoreException var18) {
            throw var18;
         } catch (Error var19) {
            throw var19;
         } catch (RuntimeException var20) {
            throw var20;
         } catch (Throwable var21) {
            throw new AssertionError(var21);
         }
      } finally {
         if (gxaTran == null) {
            this.lockManager.unlock(ptx, key);
         }

      }

      return var6;
   }

   public boolean remove(Object key) throws PersistentStoreException {
      GXATransaction gxaTran = this.gxaResource.enlist();
      if (gxaTran != null) {
         Entry re = new Entry(this, 3, key, (PersistentHandle)null);
         this.lockManager.lock(gxaTran, key);

         try {
            this.gxaResource.addNewOperation(gxaTran, re);
         } catch (GXAException var12) {
            this.lockManager.unlock(gxaTran, key);
            throw var12;
         }

         Entry removeOrPut = re.getPrevUpdateEntry();
         if (removeOrPut != null) {
            return removeOrPut.getValue() != null;
         } else {
            synchronized(this) {
               return this.keyMap.get(key) != null;
            }
         }
      } else {
         PersistentStoreTransactionImpl ptx = new PersistentStoreTransactionImpl(this.store);
         ptx.lock(this.lockManager, key);
         boolean success = false;

         boolean var6;
         try {
            boolean ret = this.apply(ptx, key, REMOVE_OBJECT);
            success = true;
            var6 = ret;
         } finally {
            if (success) {
               ptx.commit();
            } else {
               ptx.rollback();
            }

         }

         return var6;
      }
   }

   private boolean apply(PersistentStoreTransaction ptx, Object key, Object val) {
      synchronized(this) {
         Entry entry = val == REMOVE_OBJECT ? (Entry)this.keyMap.remove(key) : (Entry)this.keyMap.get(key);
         if (entry == null) {
            if (val != REMOVE_OBJECT) {
               PersistentHandle valueHandle = this.valueConn.create(ptx, val, 0);
               Entry newEntry = new Entry(this, 1, key, valueHandle);
               PersistentHandle handle = this.keyConn.create(ptx, newEntry, 0);
               newEntry.setHandle(handle);
               this.keyMap.put(key, newEntry);
            }
         } else if (val == REMOVE_OBJECT) {
            this.keyConn.delete(ptx, entry.getHandle(), 0);
            this.valueConn.delete(ptx, entry.getValueHandle(), 0);
         } else {
            this.valueConn.update(ptx, entry.getValueHandle(), val, 0);
         }

         return entry != null;
      }
   }

   void obtainLock(Object owner, Object key) {
      this.lockManager.lock(owner, key);
   }

   void releaseLock(Object owner, Object key) {
      this.lockManager.unlock(owner, key);
   }

   String getName() {
      return this.store.getName();
   }

   Map getKeyMap() {
      return this.keyMap;
   }

   PersistentStoreConnection getKeyConnection() {
      return this.keyConn;
   }

   PersistentStoreConnection getValueConnection() {
      return this.valueConn;
   }

   GXAResource getGXAResource() {
      return this.gxaResource;
   }

   synchronized long nextSequenceNo() {
      return (long)(this.nextSeqNo++);
   }

   public boolean containsKey(Object key) throws PersistentStoreException {
      return this.localGet(key, false) != null;
   }

   public void delete() throws PersistentStoreException {
   }

   public int size() throws PersistentStoreException {
      GXATransaction gxaTran = this.gxaResource.enlist();
      synchronized(this) {
         return gxaTran == null ? this.keyMap.size() : Entry.localizeSize(this, gxaTran);
      }
   }

   public boolean isEmpty() throws PersistentStoreException {
      return this.size() == 0;
   }

   public Set keySet() throws PersistentStoreException {
      GXATransaction gxaTran = this.gxaResource.enlist();
      synchronized(this) {
         HashSet hs = new HashSet(this.keyMap.keySet());
         if (gxaTran != null) {
            Entry.localizeKeySet(this, gxaTran, hs);
         }

         return hs;
      }
   }
}
