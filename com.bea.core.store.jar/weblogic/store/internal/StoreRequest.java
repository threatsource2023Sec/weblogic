package weblogic.store.internal;

import weblogic.common.CompletionRequest;
import weblogic.store.PersistentStoreException;
import weblogic.store.io.PersistentStoreIO;

abstract class StoreRequest {
   static final int CREATE = 1;
   static final int READ = 2;
   static final int UPDATE = 3;
   static final int DELETE = 4;
   static final int DROP = 5;
   static final int REOPEN = 6;
   static final int CURSOR = 7;
   static final int POLL = 8;
   StoreRequest next;
   final PersistentHandleImpl handle;
   final PersistentStoreConnectionImpl connection;
   final int typeCode;
   final int flags;
   private CompletionRequest completionRequest;
   private Throwable error;
   private boolean ioFinished;
   private TransactionUnit unit;
   protected PersistentStoreException crashTestException;
   private final long flushGroup;
   private final long liveSequence;
   private boolean requestFailureFatal;

   StoreRequest() {
      this.typeCode = 0;
      this.liveSequence = -1L;
      this.handle = null;
      this.flushGroup = -1L;
      this.flags = -1;
      this.connection = null;
      this.requestFailureFatal = true;
   }

   StoreRequest(PersistentHandleImpl handle, PersistentStoreConnectionImpl connection, int flags) {
      this(handle, connection, flags, -1L, -1L, true);
   }

   StoreRequest(PersistentHandleImpl handle, PersistentStoreConnectionImpl connection, int flags, long flushGroup, long liveSequence) {
      this(handle, connection, flags, flushGroup, liveSequence, true);
   }

   StoreRequest(PersistentHandleImpl handle, PersistentStoreConnectionImpl connection, int flags, long flushGroup, long liveSequence, boolean requestFailureFatal) {
      this.handle = handle;
      this.connection = connection;
      this.typeCode = connection.getTypeCode();
      this.flags = flags;
      this.flushGroup = flushGroup;
      this.liveSequence = liveSequence;
      this.requestFailureFatal = requestFailureFatal;
   }

   long getFlushGroup() {
      return this.flushGroup;
   }

   long getLiveSequence() {
      return this.liveSequence;
   }

   boolean isRequestFailureFatal() {
      return this.requestFailureFatal;
   }

   final synchronized void setCompletionRequest(CompletionRequest cr) {
      this.completionRequest = cr;
   }

   final PersistentHandleImpl getHandle() {
      return this.handle;
   }

   final int getTypeCode() {
      return this.typeCode;
   }

   final void doTheIO(PersistentStoreIO ios) {
      if (!this.isIOFinished()) {
         if (this.error == null) {
            try {
               this.run(ios);
            } catch (PersistentStoreException var5) {
               this.error = var5;
            } catch (Throwable var6) {
               Throwable t = var6;

               try {
                  this.error = new PersistentStoreException(t);
               } catch (Throwable var4) {
                  this.error = var6;
               }
            }
         }

      }
   }

   abstract void run(PersistentStoreIO var1) throws PersistentStoreException;

   abstract boolean requiresFlush();

   Object getResult() {
      return Boolean.TRUE;
   }

   abstract void coalesce(StoreRequest var1);

   abstract int getType();

   final synchronized void handleResult() {
      if (this.error != null) {
         this.handleError(this.error);
      } else if (this.completionRequest != null) {
         this.completionRequest.setResult(this.getResult());
      }

   }

   final synchronized void handleError(Throwable error) {
      this.error = error;
      if (this.completionRequest != null) {
         this.completionRequest.setResult(error);
      }

   }

   final synchronized void setError(Exception error) {
      this.error = error;
   }

   final synchronized Throwable getError() {
      return this.error;
   }

   final synchronized void finishIO() {
      this.ioFinished = true;
   }

   final synchronized boolean isIOFinished() {
      return this.ioFinished;
   }

   final synchronized PersistentStoreException getCrashTestException() {
      return this.crashTestException;
   }

   final void setTransactionUnit(TransactionUnit unit) {
      this.unit = unit;
   }

   final TransactionUnit getTransactionUnit() {
      return this.unit;
   }

   public String toString() {
      return super.toString() + "[ handle " + this.handle + " connection " + this.connection + " ]";
   }
}
