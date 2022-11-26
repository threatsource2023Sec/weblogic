package weblogic.store.internal;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import weblogic.common.CompletionRequest;
import weblogic.store.ByteBufferObjectHandler;
import weblogic.store.DefaultObjectHandler;
import weblogic.store.ObjectHandler;
import weblogic.store.OperationStatistics;
import weblogic.store.PersistentHandle;
import weblogic.store.PersistentStore;
import weblogic.store.PersistentStoreConnection;
import weblogic.store.PersistentStoreException;
import weblogic.store.PersistentStoreRecord;
import weblogic.store.PersistentStoreRuntimeException;
import weblogic.store.PersistentStoreTransaction;
import weblogic.store.StoreLogger;
import weblogic.store.StoreWritePolicy;
import weblogic.store.TestStoreException;
import weblogic.store.common.StoreDebug;
import weblogic.store.io.IORecord;
import weblogic.store.io.PersistentStoreIO;
import weblogic.utils.Debug;

public final class PersistentStoreConnectionImpl implements PersistentStoreConnection {
   private static final int DYNAMC_CURSOR_BATCH_SIZE = Integer.getInteger("weblogic.store.CursorBatchSize", 512);
   private static final boolean TRACK_CONCURRENT_MODIFICATIONS = Debug.getCategory("weblogic.store.TrackConcurrentModifications").isEnabled();
   private final PersistentStoreImpl.ConnectionKey key;
   private final String name;
   private final PersistentStoreImpl store;
   private final int typeCode;
   private final OperationStatisticsImpl statistics;
   private volatile int modCount;
   private ObjectHandler handler;
   private boolean isHandlerStoreExceptionTest;
   private Throwable lastModification;
   volatile boolean onlineDeserializationPossible;

   PersistentStoreConnectionImpl(PersistentStoreImpl.ConnectionKey key, PersistentStoreImpl store, int typeCode) {
      this.key = key;
      this.name = key.getName();
      this.store = store;
      this.typeCode = typeCode;
      this.statistics = new OperationStatisticsImpl(this.name, store.getStatisticsImpl());
      this.setObjectHandler(DefaultObjectHandler.THE_ONE);
   }

   public PersistentHandle create(PersistentStoreTransaction tx, Object data, int flags) {
      return this.createInternal(tx, (PersistentHandle)null, data, flags, -1L, -1L);
   }

   public void create(PersistentStoreTransaction tx, PersistentHandle ph, Object data, int flags) {
      this.createInternal(tx, ph, data, flags, -1L, -1L);
   }

   public PersistentHandle create(PersistentStoreTransaction tx, Object data, int flags, long flushGroup, long liveSequence) {
      return this.createInternal(tx, (PersistentHandle)null, data, flags, flushGroup, liveSequence);
   }

   private PersistentHandle createInternal(PersistentStoreTransaction tx, PersistentHandle ph, Object data, int flags) {
      return this.createInternal(tx, ph, data, flags, -1L, -1L);
   }

   private PersistentHandle createInternal(PersistentStoreTransaction tx, PersistentHandle ph, Object data, int flags, long flushGroup, long liveSequence) {
      PersistentStoreTransactionImpl ptx = (PersistentStoreTransactionImpl)tx;
      ByteBuffer[] body = this.serialize(data);
      PersistentHandleImpl handle;
      if (ph == null) {
         handle = this.store.allocateHandle(this.typeCode);

         assert handle.getTypeCode() == this.typeCode;
      } else {
         handle = (PersistentHandleImpl)ph;
         handle.setTypeCode(this.typeCode);
         this.store.ensureHandleAllocated(handle);
         handle.setDeleted(false);
      }

      CreateRequest req = new CreateRequest(handle, this, body, flags, flushGroup, liveSequence);
      if (this.isHandlerStoreExceptionTest && data instanceof TestStoreException) {
         PersistentStoreException error = ((TestStoreException)data).getTestException();
         if (error != null) {
            req.setCrashTestException(error);
         }
      }

      StoreRequest pending = ptx.put(handle, req);
      if (pending != null) {
         if (pending.getType() != 4) {
            throw new PersistentStoreRuntimeException(StoreLogger.logStoreRecordAlreadyExistsLoggable((long)handle.getStoreHandle(), this.name, this.store.getName()));
         }

         pending.getHandle().setDeleted(false);
      }

      if (StoreDebug.storeIOLogical.isDebugEnabled()) {
         StoreDebug.storeIOLogical.debug("create, tcd=" + this.typeCode + ", hdl=" + handle + ", ptx=" + tx + ", cls=" + data.getClass().getName() + ", obj=[" + data + "]");
      }

      this.mod();
      return handle;
   }

   public void read(PersistentStoreTransaction tx, PersistentHandle handle, CompletionRequest cr) {
      this.read(tx, handle, cr, true);
   }

   public void read(PersistentStoreTransaction tx, PersistentHandle handle, CompletionRequest cr, boolean isReadFailureFatal) {
      PersistentHandleImpl h = PersistentHandleImpl.check(this, handle);
      h.setTypeCode(this.typeCode);
      ReadRequest req = new ReadRequest(h, this, this.handler, isReadFailureFatal);
      if (tx instanceof PersistentStoreTransactionImpl) {
         StoreRequest pending = ((PersistentStoreTransactionImpl)tx).get(handle);
         if (pending != null) {
            pending.coalesce(req);
         }
      }

      req.setCompletionRequest(cr);
      if (StoreDebug.storeIOLogical.isDebugEnabled()) {
         StoreDebug.storeIOLogical.debug("read, tcd=" + this.typeCode + ", hdl=" + handle + ", ptx=" + tx);
      }

      this.store.schedule(req);
   }

   public void update(PersistentStoreTransaction tx, PersistentHandle handle, Object data, int flags) {
      this.update(tx, handle, data, flags, -1L, -1L);
   }

   public void update(PersistentStoreTransaction tx, PersistentHandle handle, Object data, int flags, long flushGroup, long liveSequence) {
      PersistentHandleImpl h = PersistentHandleImpl.check(this, handle);
      h.setTypeCode(this.typeCode);
      ByteBuffer[] bufs = this.serialize(data);
      UpdateRequest req = new UpdateRequest(h, this, bufs, flags, flushGroup, liveSequence);
      PersistentStoreTransactionImpl ptx = (PersistentStoreTransactionImpl)tx;
      if (StoreDebug.storeIOLogical.isDebugEnabled()) {
         StoreDebug.storeIOLogical.debug("update, tcd=" + this.typeCode + ", hdl=" + handle + ", ptx=" + tx + ", cls=" + data.getClass().getName() + ", obj=[" + data + "]");
      }

      StoreRequest pending = ptx.get(handle);
      if (pending != null) {
         pending.coalesce(req);
      } else {
         ptx.put(handle, req);
      }

      this.mod();
   }

   public void delete(PersistentStoreTransaction tx, PersistentHandle handle, int flags) {
      this.delete(tx, handle, flags, -1L, -1L);
   }

   public void delete(PersistentStoreTransaction tx, PersistentHandle handle, int flags, long flushGroup, long liveSequence) {
      PersistentHandleImpl h = PersistentHandleImpl.check(this, handle);
      h.setTypeCode(this.typeCode);
      DeleteRequest req = new DeleteRequest(h, this, flags, flushGroup, liveSequence);
      PersistentStoreTransactionImpl ptx = (PersistentStoreTransactionImpl)tx;
      StoreRequest pending = ptx.put(handle, req);
      if (pending != null) {
         pending.coalesce(req);
      }

      if (StoreDebug.storeIOLogical.isDebugEnabled()) {
         StoreDebug.storeIOLogical.debug("delete, tcd=" + this.typeCode + ", hdl=" + handle + ", ptx=" + tx);
      }

      h.setDeleted(true);
      this.mod();
   }

   public boolean isHandleReadable(PersistentHandle handle) {
      PersistentHandleImpl hi = (PersistentHandleImpl)handle;
      PersistentHandleImpl hcopy = new PersistentHandleImpl(hi.getTypeCode() == 0 ? this.typeCode : hi.getTypeCode(), hi.getStoreHandle());
      return hcopy.getTypeCode() == this.typeCode && !hcopy.isDeleted() && this.store.isHandleReadable(hcopy);
   }

   public synchronized void close() {
   }

   public void delete() throws PersistentStoreException {
      this.close();
      this.store.delete(this);
   }

   public String getName() {
      return this.name;
   }

   public PersistentStoreConnection.Cursor createCursor(int flags) throws PersistentStoreException {
      return (PersistentStoreConnection.Cursor)((!this.store.supportsFastReads() || (flags & 64) != 0) && (flags & 32) != 32 ? new CursorImpl(this.store.createCursor(this.typeCode, flags)) : new DynamicCursorImpl(flags));
   }

   PersistentStoreImpl.ConnectionKey getKey() {
      return this.key;
   }

   private void mod() {
      ++this.modCount;
      if (TRACK_CONCURRENT_MODIFICATIONS) {
         this.lastModification = new Throwable();
      }

   }

   public ByteBuffer[] serialize(Object o) {
      if (this.handler instanceof ByteBufferObjectHandler) {
         return new ByteBuffer[]{(ByteBuffer)o};
      } else {
         try {
            PersistentStoreOutputStreamImpl psos = new PersistentStoreOutputStreamImpl();
            this.handler.writeObject(psos, o);
            return psos.getBuffers();
         } catch (IOException var4) {
            throw new PersistentStoreRuntimeException(StoreLogger.logCreateFailedLoggable(), var4);
         }
      }
   }

   boolean deserializationDeferred() throws PersistentStoreException {
      if (!this.onlineDeserializationPossible) {
         return true;
      } else if (this.store == null) {
         return false;
      } else {
         StoreWritePolicy swp = (StoreWritePolicy)this.store.getConfigValue("SynchronousWritePolicy");
         if (swp == null) {
            return false;
         } else {
            return !swp.mappedRead();
         }
      }
   }

   public void setObjectHandler(ObjectHandler handler) {
      this.isHandlerStoreExceptionTest = handler instanceof TestStoreException;
      this.handler = handler;
   }

   public PersistentStore getStore() {
      return this.store;
   }

   PersistentStoreImpl getStoreImpl() {
      return this.store;
   }

   int getTypeCode() {
      return this.typeCode;
   }

   public OperationStatistics getStatistics() {
      return this.statistics;
   }

   OperationStatisticsImpl getStatisticsImpl() {
      return this.statistics;
   }

   public String toString() {
      return "[ name=" + this.name + " store=" + this.store.getName() + " typeCode=" + this.typeCode + " ]";
   }

   private final class DynamicCursorImpl implements PersistentStoreConnection.Cursor {
      private ArrayList recList;
      private int flags;
      private PersistentStoreIO.Cursor cursor;
      private boolean cursordone;

      public DynamicCursorImpl(int flags) {
         this.flags = flags;
      }

      public PersistentStoreRecord next() throws PersistentStoreException {
         if (this.cursordone) {
            return null;
         } else {
            if (this.recList == null || this.recList.isEmpty()) {
               CursorRequest cursorReq = new CursorRequest(PersistentStoreConnectionImpl.this, PersistentStoreConnectionImpl.DYNAMC_CURSOR_BATCH_SIZE, this.cursor, PersistentStoreConnectionImpl.this.handler, this.flags);
               CompletionRequest cr = new CompletionRequest();
               cursorReq.setCompletionRequest(cr);
               PersistentStoreConnectionImpl.this.store.schedule(cursorReq);

               try {
                  this.recList = (ArrayList)cr.getResult();
                  this.cursor = cursorReq.getCursor();
               } catch (PersistentStoreException var4) {
                  throw var4;
               } catch (RuntimeException var5) {
                  throw var5;
               } catch (Error var6) {
                  throw var6;
               } catch (Throwable var7) {
                  throw new AssertionError(var7);
               }
            }

            PersistentStoreRecord ret = (PersistentStoreRecord)this.recList.remove(0);
            if (ret == null) {
               this.cursordone = true;
            }

            return ret;
         }
      }
   }

   private final class CursorImpl implements PersistentStoreConnection.Cursor {
      private final PersistentStoreIO.Cursor cursor;
      private final int expectedModCount;

      public CursorImpl(PersistentStoreIO.Cursor cursor) {
         this.cursor = cursor;
         this.expectedModCount = PersistentStoreConnectionImpl.this.modCount;
      }

      public PersistentStoreRecord next() throws PersistentStoreException {
         if (this.expectedModCount != PersistentStoreConnectionImpl.this.modCount) {
            ConcurrentModificationException cme = new ConcurrentModificationException();
            if (PersistentStoreConnectionImpl.TRACK_CONCURRENT_MODIFICATIONS && PersistentStoreConnectionImpl.this.lastModification != null) {
               cme.initCause(PersistentStoreConnectionImpl.this.lastModification);
            }

            throw cme;
         } else {
            IORecord next = this.cursor.next();
            if (next == null) {
               return null;
            } else {
               PersistentStoreRecordImpl psr = new PersistentStoreRecordImpl(next, PersistentStoreConnectionImpl.this.handler, PersistentStoreConnectionImpl.this, false);
               if (psr != null && StoreDebug.storeIOLogicalBoot.isDebugEnabled()) {
                  Object data = psr.getData();
                  if (data != null) {
                     StoreDebug.storeIOLogicalBoot.debug("boot, tcd=" + next.getTypeCode() + ", hdl=" + next.getHandle() + ", cls=" + data.getClass().getName() + ", obj=[" + data + "]");
                  } else {
                     StoreDebug.storeIOLogicalBoot.debug("boot, tcd=" + next.getTypeCode() + ", hdl=" + next.getHandle() + ", obj=[" + data + "]");
                  }
               }

               return psr;
            }
         }
      }
   }
}
