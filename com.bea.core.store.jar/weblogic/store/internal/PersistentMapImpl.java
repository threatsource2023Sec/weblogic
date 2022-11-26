package weblogic.store.internal;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.naming.InitialContext;
import weblogic.common.CompletionListener;
import weblogic.common.CompletionRequest;
import weblogic.store.DefaultObjectHandler;
import weblogic.store.ObjectHandler;
import weblogic.store.PersistentHandle;
import weblogic.store.PersistentMapAsyncTX;
import weblogic.store.PersistentStore;
import weblogic.store.PersistentStoreConnection;
import weblogic.store.PersistentStoreException;
import weblogic.store.PersistentStoreRecord;
import weblogic.store.PersistentStoreTransaction;
import weblogic.store.StoreWritePolicy;
import weblogic.store.io.PersistentStoreIO;
import weblogic.store.io.file.FileStoreIO;

public final class PersistentMapImpl implements PersistentMapAsyncTX {
   private static final int NO_FLAGS = 0;
   private static final Object REMOVE_OBJECT = new Object();
   private PersistentStoreConnectionImpl keyConn;
   private PersistentStoreConnectionImpl valueConn;
   private final Map keyMap = Collections.synchronizedMap(new HashMap());
   private PersistentStoreImpl store;
   private LockManager lockManager = new LockManagerImpl();
   private boolean deleteBad;
   private boolean instrBad;

   PersistentMapImpl(PersistentStoreConnection keys, PersistentStoreConnection values) throws PersistentStoreException {
      this.keyConn = (PersistentStoreConnectionImpl)keys;
      this.keyConn.setObjectHandler(new PersistentMapObjectHandler());
      this.valueConn = (PersistentStoreConnectionImpl)values;
      this.init();
   }

   PersistentMapImpl(PersistentStoreConnection keys, PersistentStoreConnection values, ObjectHandler handler) throws PersistentStoreException {
      this.keyConn = (PersistentStoreConnectionImpl)keys;
      this.keyConn.setObjectHandler(new PersistentMapObjectHandler(handler));
      this.valueConn = (PersistentStoreConnectionImpl)values;
      this.valueConn.setObjectHandler(handler);
      this.init();
   }

   private void init() throws PersistentStoreException {
      ArrayList badHandles = new ArrayList();
      this.store = (PersistentStoreImpl)this.keyConn.getStore();
      if (this.store != this.valueConn.getStore()) {
         throw new PersistentStoreException("keyConn.store != valueConn.store");
      } else {
         this.deleteBad = this.isSet("weblogic.store.DeleteBadPMapKeys");
         this.instrBad = this.isSet("weblogic.store.DeleteBadInstrTest");
         PersistentStoreConnection.Cursor cursor = this.keyConn.createCursor(0);

         PersistentStoreTransaction tran;
         for(PersistentStoreRecord rec = cursor.next(); rec != null; rec = cursor.next()) {
            Entry entry = (Entry)rec.getData();
            entry.handle = rec.getHandle();
            if (this.deleteBad && entry.valueHandle != null && !this.valueConn.isHandleReadable(entry.valueHandle)) {
               badHandles.add(entry.handle);
            } else {
               if (this.instrBad && ((String)entry.key).contains("CORRUPTME") && this.valueConn.isHandleReadable(entry.valueHandle)) {
                  tran = this.store.begin();
                  this.valueConn.delete(tran, entry.valueHandle, 0);
                  tran.commit();
               }

               this.keyMap.put(entry.key, entry);
            }
         }

         Iterator var6 = badHandles.iterator();

         while(var6.hasNext()) {
            PersistentHandle ph = (PersistentHandle)var6.next();
            tran = this.store.begin();
            this.keyConn.delete(tran, ph, 0);
            tran.commit();
         }

      }
   }

   public boolean put(Object key, Object value) throws PersistentStoreException {
      PersistentStoreTransactionImpl ptx = new PersistentStoreTransactionImpl(this.store);
      ptx.lock(this.lockManager, key);
      boolean success = false;

      boolean var6;
      try {
         boolean ret = this.apply(ptx, key, value);
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

   public void put(final Object key, final Object value, final CompletionRequest cr) {
      final PersistentStoreTransactionImpl ptx = new PersistentStoreTransactionImpl(this.store);
      ptx.lock(this.lockManager, key, new LockManager.Listener() {
         public void onLock() {
            Boolean retValue = Boolean.FALSE;

            try {
               if (PersistentMapImpl.this.apply(ptx, key, value)) {
                  retValue = Boolean.TRUE;
               }
            } finally {
               PersistentMapImpl.this.commitSetValueNeedThread(ptx, cr, retValue);
            }

         }
      });
   }

   public final boolean put(Object key, Object value, PersistentStoreTransaction ptx) throws PersistentStoreException {
      PersistentMapTransactionImpl tx = PersistentMapTransactionImpl.check(ptx);
      tx.lock(this.lockManager, key);
      return tx.put(key, value) != null;
   }

   public final void put(final Object key, final Object value, PersistentStoreTransaction ptx, final CompletionRequest cr) {
      final PersistentMapTransactionImpl tx;
      try {
         tx = PersistentMapTransactionImpl.check(ptx);
      } catch (PersistentStoreException var7) {
         cr.setResult(var7);
         return;
      }

      tx.lock(this.lockManager, key, new LockManager.Listener() {
         public void onLock() {
            Object old = tx.put(key, value);
            boolean replace = old != null;
            if (!replace) {
               synchronized(PersistentMapImpl.this.keyMap) {
                  replace = PersistentMapImpl.this.keyMap.containsKey(key);
               }
            }

            cr.setResult(replace);
         }
      });
   }

   public Object get(Object key) throws PersistentStoreException {
      CompletionRequest cr = new CompletionRequest();
      PersistentStoreTransaction ptx = this.store.begin();
      synchronized(this.keyMap) {
         Entry entry = (Entry)this.keyMap.get(key);
         if (entry == null) {
            return null;
         }

         this.valueConn.read(ptx, entry.valueHandle, cr);
      }

      try {
         return ((PersistentStoreRecord)cr.getResult()).getData();
      } catch (PersistentStoreException var15) {
         Object var20;
         try {
            var20 = this.getForUpdate(key, ptx);
         } finally {
            ptx.commit();
         }

         return var20;
      } catch (RuntimeException var16) {
         throw var16;
      } catch (Error var17) {
         throw var17;
      } catch (Throwable var18) {
         throw new AssertionError(var18);
      }
   }

   public void get(Object key, CompletionRequest userCompletionRequest) {
      PersistentStoreTransaction ptx = this.store.begin();
      this.getInternal(key, ptx, userCompletionRequest, false);
   }

   private static void setResultInSameThread(CompletionRequest userCompletionRequest, Object value) {
      boolean oldValue = userCompletionRequest.runListenersInSetResult(true);

      try {
         userCompletionRequest.setResult(value);
      } finally {
         if (!oldValue) {
            userCompletionRequest.runListenersInSetResult(false);
         }

      }

   }

   private void getInternal(Object key, PersistentStoreTransaction ptx, CompletionRequest userCompletionRequest, boolean withKeyLock) {
      synchronized(this.keyMap) {
         try {
            Entry entry = (Entry)this.keyMap.get(key);
            if (entry != null && entry.valueHandle != null) {
               if (withKeyLock) {
                  this.valueConn.read(ptx, entry.valueHandle, new ReadCompletionWithKeyLock(userCompletionRequest));
               } else {
                  this.valueConn.read(ptx, entry.valueHandle, new ReadCompletionWithoutKeyLock(key, ptx, userCompletionRequest));
               }
            } else {
               userCompletionRequest.setResult((Object)null);
            }
         } catch (RuntimeException var8) {
            setResultInSameThread(userCompletionRequest, var8);
            throw var8;
         } catch (Error var9) {
            setResultInSameThread(userCompletionRequest, var9);
            throw var9;
         }

      }
   }

   public final Object get(Object key, PersistentStoreTransaction ptx) throws PersistentStoreException {
      PersistentMapTransactionImpl tx = PersistentMapTransactionImpl.check(ptx);
      Object val = tx.get(key);
      if (val != null) {
         return val;
      } else {
         CompletionRequest cr = new CompletionRequest();
         this.getInternal(key, ptx, cr, false);
         return waitForCompletion(cr);
      }
   }

   public final void get(Object key, PersistentStoreTransaction ptx, CompletionRequest cr) {
      PersistentMapTransactionImpl tx;
      try {
         tx = PersistentMapTransactionImpl.check(ptx);
         Object val = tx.get(key);
         if (val != null) {
            cr.setResult(val);
            return;
         }
      } catch (PersistentStoreException var6) {
         cr.setResult(var6);
         return;
      }

      this.getInternal(key, tx, cr, false);
   }

   public final Object getForUpdate(Object key, PersistentStoreTransaction tx) throws PersistentStoreException {
      CompletionRequest cr = new CompletionRequest();
      PersistentTransactionImpl ptx = checkPersistentTransactionImpl(tx);
      ptx.lock(this.lockManager, key);
      this.getInternal(key, ptx, cr, true);
      return waitForCompletion(cr);
   }

   public final void getForUpdate(final Object key, PersistentStoreTransaction tx, final CompletionRequest cr) {
      final PersistentMapTransactionImpl ptx;
      try {
         ptx = PersistentMapTransactionImpl.check(tx);
      } catch (PersistentStoreException var6) {
         cr.setResult(var6);
         return;
      } catch (RuntimeException var7) {
         cr.setResult(var7);
         throw var7;
      } catch (Error var8) {
         cr.setResult(var8);
         throw var8;
      } catch (Throwable var9) {
         cr.setResult(var9);
         throw new AssertionError(var9);
      }

      ptx.lock(this.lockManager, key, new LockManager.Listener() {
         public void onLock() {
            PersistentMapImpl.this.getInternal(key, ptx, cr, true);
         }
      });
   }

   public boolean remove(Object key) throws PersistentStoreException {
      PersistentStoreTransactionImpl ptx = new PersistentStoreTransactionImpl(this.store);
      ptx.lock(this.lockManager, key);
      boolean success = false;

      boolean var5;
      try {
         boolean ret = this.apply(ptx, key, REMOVE_OBJECT);
         success = true;
         var5 = ret;
      } finally {
         if (success) {
            ptx.commit();
         } else {
            ptx.rollback();
         }

      }

      return var5;
   }

   public void remove(final Object key, final CompletionRequest userCompReq) {
      final PersistentStoreTransactionImpl ptx = new PersistentStoreTransactionImpl(this.store);
      ptx.lock(this.lockManager, key, new LockManager.Listener() {
         public void onLock() {
            Object retValue = PersistentMapImpl.this.apply(ptx, key, PersistentMapImpl.REMOVE_OBJECT);
            PersistentMapImpl.this.commitSetValueNeedThread(ptx, userCompReq, retValue);
         }
      });
   }

   public final boolean remove(Object key, PersistentStoreTransaction ptx) throws PersistentStoreException {
      PersistentMapTransactionImpl tx = PersistentMapTransactionImpl.check(ptx);
      tx.lock(this.lockManager, key);
      return tx.put(key, REMOVE_OBJECT) != null;
   }

   public final void remove(final Object key, PersistentStoreTransaction ptx, final CompletionRequest cr) {
      final PersistentMapTransactionImpl tx;
      try {
         tx = PersistentMapTransactionImpl.check(ptx);
      } catch (PersistentStoreException var6) {
         cr.setResult(var6);
         return;
      }

      tx.lock(this.lockManager, key, new LockManager.Listener() {
         public void onLock() {
            if (tx.put(key, PersistentMapImpl.REMOVE_OBJECT) != null) {
               cr.setResult(Boolean.TRUE);
            } else {
               cr.setResult(Boolean.FALSE);
            }

         }
      });
   }

   public void remove(Object key, Object value, CompletionRequest userCompletionRequest) {
      if (value == null) {
         this.remove(key, userCompletionRequest);
      } else {
         PersistentMapTransactionImpl ptx = new PersistentMapTransactionImpl(this);
         ptx.lock(this.lockManager, key, new RemoveMatchCompletion(key, value, ptx, userCompletionRequest));
      }
   }

   public final PersistentStoreTransaction begin() {
      return new PersistentMapTransactionImpl(this);
   }

   public final void commit(final PersistentMapTransactionImpl tx, final CompletionRequest userCompletionRequest) {
      PersistentStoreTransaction ptx = this.store.begin();
      Iterator entries = tx.entrySet().iterator();

      while(entries.hasNext()) {
         Map.Entry entry = (Map.Entry)entries.next();
         this.apply(ptx, entry.getKey(), entry.getValue());
      }

      ptx.commit(new CompReqListener() {
         public void onCompletion(CompletionRequest completionRequest, Object result) {
            tx.unlockAll();
            PersistentMapImpl.setResultInSameThread(userCompletionRequest, result);
         }

         public void onException(CompletionRequest completionRequest, Throwable reason) {
            tx.unlockAll();
            PersistentMapImpl.setResultInSameThread(userCompletionRequest, reason);
         }
      });
   }

   private boolean apply(PersistentStoreTransaction ptx, Object key, Object val) {
      synchronized(this.keyMap) {
         Entry entry = val == REMOVE_OBJECT ? (Entry)this.keyMap.remove(key) : (Entry)this.keyMap.get(key);
         if (entry == null) {
            if (val != REMOVE_OBJECT) {
               PersistentHandle valueHandle = this.valueConn.create(ptx, val, 0);
               Entry newEntry = new Entry(key, valueHandle);
               PersistentHandle handle = this.keyConn.create(ptx, newEntry, 0);
               newEntry.handle = handle;
               this.keyMap.put(key, newEntry);
            }
         } else if (val == REMOVE_OBJECT) {
            this.keyConn.delete(ptx, entry.handle, 0);
            this.valueConn.delete(ptx, entry.valueHandle, 0);
         } else {
            this.valueConn.update(ptx, entry.valueHandle, val, 0);
         }

         return entry != null;
      }
   }

   public final void rollback(PersistentMapTransactionImpl tx, CompletionRequest cr) {
      tx.unlockAll();
   }

   public void putIfAbsent(Object key, Object val, CompletionRequest cr) {
      PersistentStoreTransactionImpl tx = new PersistentStoreTransactionImpl(this.store);
      tx.lock(this.lockManager, key, this.putIfAbsentLockListener(key, val, true, tx, cr));
   }

   private static PersistentTransactionImpl checkPersistentTransactionImpl(PersistentStoreTransaction ptx) throws PersistentStoreException {
      if (ptx instanceof PersistentTransactionImpl) {
         return (PersistentTransactionImpl)ptx;
      } else {
         throw new PersistentStoreException("Transaction invalid");
      }
   }

   public void putIfAbsent(Object key, Object val, PersistentStoreTransaction ptx, CompletionRequest cr) {
      PersistentTransactionImpl tx;
      try {
         tx = checkPersistentTransactionImpl(ptx);
      } catch (PersistentStoreException var7) {
         cr.setResult(var7);
         return;
      }

      tx.lock(this.lockManager, key, this.putIfAbsentLockListener(key, val, false, tx, cr));
   }

   private LockManager.Listener putIfAbsentLockListener(final Object key, final Object val, final boolean commitInternalPtx, final PersistentTransactionImpl ptx, final CompletionRequest cr) {
      return new LockManager.Listener() {
         public void onLock() {
            Entry entry;
            synchronized(PersistentMapImpl.this.keyMap) {
               entry = (Entry)PersistentMapImpl.this.keyMap.get(key);
            }

            if (entry != null) {
               CompReqListener readListener = new CompReqListener() {
                  public void onException(CompletionRequest completionRequest, Throwable reason) {
                     try {
                        if (commitInternalPtx) {
                           ptx.unlockAll();
                        }
                     } finally {
                        PersistentMapImpl.setResultInSameThread(cr, PersistentMapImpl.convertThrowable(reason));
                     }

                  }

                  public void onCompletion(CompletionRequest completionRequest, Object result) {
                     try {
                        if (commitInternalPtx) {
                           ptx.unlockAll();
                        }

                        result = ((PersistentStoreRecord)result).getData();
                     } catch (PersistentStoreException var4) {
                        result = var4;
                     }

                     PersistentMapImpl.setResultInSameThread(cr, result);
                  }
               };
               PersistentMapImpl.this.valueConn.read((PersistentStoreTransaction)null, entry.valueHandle, readListener);
            } else {
               if (ptx instanceof PersistentStoreTransactionImpl) {
                  PersistentMapImpl.this.apply(ptx, key, val);
               } else if (ptx instanceof PersistentMapTransactionImpl) {
                  ((PersistentMapTransactionImpl)ptx).put(key, val);
               }

               if (commitInternalPtx) {
                  PersistentMapImpl.this.commitSetValueNeedThread(ptx, cr, val);
               } else {
                  cr.setResult(val);
               }

            }
         }
      };
   }

   private static Throwable convertThrowable(Throwable reason) {
      if (reason instanceof PersistentStoreException) {
         return reason;
      } else if (reason instanceof Error) {
         return reason;
      } else {
         return (Throwable)(reason instanceof RuntimeException ? reason : new PersistentStoreException(reason));
      }
   }

   public final int size() {
      return this.keyMap.size();
   }

   public final boolean isEmpty() {
      return this.size() == 0;
   }

   public final boolean containsKey(Object key) {
      return this.keyMap.containsKey(key);
   }

   public void delete() throws PersistentStoreException {
      synchronized(this.keyMap) {
         try {
            this.keyConn.delete();
         } finally {
            this.valueConn.delete();
         }

         this.keyMap.clear();
      }
   }

   public Set keySet() {
      return Collections.unmodifiableSet(this.keyMap.keySet());
   }

   private static final Object waitForCompletion(CompletionRequest cr) throws PersistentStoreException {
      try {
         return cr.getResult();
      } catch (PersistentStoreException var2) {
         throw var2;
      } catch (RuntimeException var3) {
         throw var3;
      } catch (Error var4) {
         throw var4;
      } catch (Throwable var5) {
         throw new AssertionError(var5);
      }
   }

   private void commitSetValueNeedThread(PersistentStoreTransaction ptx, CompletionRequest completionRequest, Object retVal) {
      ptx.commit(new SetResultCompReqListener(completionRequest, retVal, true, false));
   }

   private void commitSetValueHaveThread(PersistentStoreTransaction ptx, CompletionRequest completionRequest, Object retVal, boolean replaceRetValWithException) {
      ptx.commit(new SetResultCompReqListener(completionRequest, retVal, replaceRetValWithException, true));
   }

   private boolean isSet(String sysProp) {
      try {
         String val = System.getProperty(sysProp);
         if (val == null) {
            val = (String)this.store.getConfigValue(sysProp);
         }

         return val != null && val.equalsIgnoreCase("true");
      } catch (Throwable var3) {
         return false;
      }
   }

   public static void main(String[] args) throws Exception {
      InitialContext ctx = new InitialContext();
      PersistentStoreIO ios = new FileStoreIO("MyStoreName", "storedir");
      PersistentStore store = new PersistentStoreImpl("MyStoreName", ios);
      HashMap config = new HashMap();
      config.put("SynchronousWritePolicy", StoreWritePolicy.DIRECT_WRITE);
      store.open(config);
      ctx.bind("persistentmap", store.createPersistentMap("persistentMap"));
      System.err.println("\n\n*** Bound PersistentMapTX ***\n\n");
   }

   private static final class SetResultCompReqListener extends CompletionRequest implements CompletionListener {
      CompletionRequest userCompReq;
      Object userResult;
      boolean replaceRetValWithException;
      boolean haveThread;

      SetResultCompReqListener(CompletionRequest userCR, Object userRes, boolean replaceValWithException, boolean hasThread) {
         this.addListener(this);
         this.userCompReq = userCR;
         this.userResult = userRes;
         this.replaceRetValWithException = replaceValWithException;
         this.haveThread = hasThread;
      }

      public void onCompletion(CompletionRequest completionRequest, Object result) {
         boolean oldValue;
         if (this.haveThread) {
            oldValue = this.userCompReq.runListenersInSetResult(true);
         } else {
            oldValue = true;
         }

         try {
            this.userCompReq.setResult(this.userResult);
         } finally {
            if (this.haveThread && !oldValue) {
               this.userCompReq.runListenersInSetResult(false);
            }

         }

      }

      public void onException(CompletionRequest completionRequest, Throwable reason) {
         boolean oldValue;
         if (this.haveThread) {
            oldValue = this.userCompReq.runListenersInSetResult(true);
         } else {
            oldValue = true;
         }

         try {
            if (this.replaceRetValWithException) {
               this.userCompReq.setResult(reason);
            } else {
               this.userCompReq.setResult(this.userResult);
            }
         } finally {
            if (this.haveThread && !oldValue) {
               this.userCompReq.runListenersInSetResult(false);
            }

         }

      }
   }

   private final class PersistentMapObjectHandler implements ObjectHandler {
      private static final int MAGIC = 1111834912;
      private final ObjectHandler delegate;

      PersistentMapObjectHandler() {
         this.delegate = DefaultObjectHandler.THE_ONE;
      }

      PersistentMapObjectHandler(ObjectHandler delegate) {
         this.delegate = delegate;
      }

      public void writeObject(ObjectOutput out, Object obj) throws IOException {
         if (!(obj instanceof Entry)) {
            throw new IOException("Cannot serialize: " + obj.getClass());
         } else {
            Entry entry = (Entry)obj;
            out.writeInt(1111834912);
            this.delegate.writeObject(out, entry.key);
            PersistentHandle h = entry.valueHandle;
            PersistentHandle.write(out, h);
         }
      }

      public Object readObject(ObjectInput in) throws ClassNotFoundException, IOException {
         int magic = in.readInt();
         if (1111834912 != magic) {
            throw new ClassNotFoundException("PersistentMap attempting to deserialize unknown object");
         } else {
            Object key = this.delegate.readObject(in);
            PersistentHandle valueHandle = PersistentHandle.read(in);
            return new Entry(key, valueHandle);
         }
      }
   }

   private static final class Entry {
      private final Object key;
      private PersistentHandle handle;
      private final PersistentHandle valueHandle;

      public Entry(Object key, PersistentHandle valueHandle) {
         this.key = key;
         this.valueHandle = valueHandle;
      }
   }

   private final class RemoveMatchCompletion extends CompReqListener implements LockManager.Listener {
      final Object key;
      final Object value;
      final PersistentMapTransactionImpl ptx;
      final CompletionRequest userCompletionRequest;

      RemoveMatchCompletion(Object key, Object value, PersistentMapTransactionImpl ptx, CompletionRequest userCompletionRequest) {
         this.key = key;
         this.value = value;
         this.ptx = ptx;
         this.userCompletionRequest = userCompletionRequest;
      }

      public void onLock() {
         PersistentMapImpl.this.getForUpdate(this.key, this.ptx, this);
      }

      public void onException(CompletionRequest completionRequest, Throwable reportReason) {
         PersistentMapImpl.this.commitSetValueHaveThread(this.ptx, this.userCompletionRequest, reportReason, false);
      }

      public void onCompletion(CompletionRequest completionRequest, Object result) {
         if (result != null && this.value.equals(result)) {
            this.ptx.put(this.key, PersistentMapImpl.REMOVE_OBJECT);
            PersistentMapImpl.this.commitSetValueHaveThread(this.ptx, this.userCompletionRequest, Boolean.TRUE, true);
         } else {
            PersistentMapImpl.this.commitSetValueHaveThread(this.ptx, this.userCompletionRequest, Boolean.FALSE, true);
         }
      }
   }

   private final class ReadCompletionWithoutKeyLock extends ReadCompletionWithKeyLock {
      private Object key;
      private PersistentStoreTransaction ptx;

      private ReadCompletionWithoutKeyLock(Object key, PersistentStoreTransaction ptx, CompletionRequest completionRequest) {
         super(completionRequest, null);
         this.key = key;
         this.ptx = ptx;
      }

      public void onException(CompletionRequest request, Throwable reason) {
         try {
            PersistentTransactionImpl ptxi = PersistentMapImpl.checkPersistentTransactionImpl(this.ptx);
            ptxi.lock(PersistentMapImpl.this.lockManager, this.key);
            PersistentMapImpl.this.getInternal(this.key, ptxi, this.userCompletionRequest, true);
         } catch (PersistentStoreException var4) {
            PersistentMapImpl.setResultInSameThread(this.userCompletionRequest, reason);
         }

      }

      // $FF: synthetic method
      ReadCompletionWithoutKeyLock(Object x1, PersistentStoreTransaction x2, CompletionRequest x3, Object x4) {
         this(x1, x2, x3);
      }
   }

   private class ReadCompletionWithKeyLock extends CompReqListener {
      protected CompletionRequest userCompletionRequest;

      private ReadCompletionWithKeyLock(CompletionRequest completionRequest) {
         this.userCompletionRequest = completionRequest;
      }

      public void onCompletion(CompletionRequest request, Object result) {
         try {
            PersistentMapImpl.setResultInSameThread(this.userCompletionRequest, ((PersistentStoreRecord)result).getData());
         } catch (PersistentStoreException var5) {
            PersistentMapImpl.setResultInSameThread(this.userCompletionRequest, var5);
         } catch (RuntimeException var6) {
            PersistentMapImpl.setResultInSameThread(this.userCompletionRequest, var6);
            throw var6;
         } catch (Error var7) {
            PersistentMapImpl.setResultInSameThread(this.userCompletionRequest, var7);
            throw var7;
         } catch (Throwable var8) {
            AssertionError ae = new AssertionError(var8);
            PersistentMapImpl.setResultInSameThread(this.userCompletionRequest, ae);
            throw ae;
         }

      }

      public void onException(CompletionRequest request, Throwable reason) {
         PersistentMapImpl.setResultInSameThread(this.userCompletionRequest, reason);
      }

      // $FF: synthetic method
      ReadCompletionWithKeyLock(CompletionRequest x1, Object x2) {
         this(x1);
      }
   }

   private abstract static class CompReqListener extends CompletionRequest implements CompletionListener {
      CompReqListener() {
         this.addListener(this);
      }
   }
}
