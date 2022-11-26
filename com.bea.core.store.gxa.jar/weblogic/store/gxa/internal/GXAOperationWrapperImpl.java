package weblogic.store.gxa.internal;

import weblogic.diagnostics.debug.DebugLogger;
import weblogic.store.gxa.GXAOperation;
import weblogic.store.gxa.GXAOperationWrapper;
import weblogic.store.gxa.GXATraceLogger;
import weblogic.store.gxa.GXid;

final class GXAOperationWrapperImpl implements GXATraceLogger, GXAOperationWrapper {
   static final DebugLogger storeXA = DebugLogger.getDebugLogger("DebugStoreXA");
   static final DebugLogger storeXAVerbose = DebugLogger.getDebugLogger("DebugStoreXAVerbose");
   private static final String FN_INIT = "init";
   private static final String FN_PREPARE1PC = "prepare-1pc";
   private static final String FN_PREPARE2PC = "prepare-2pc";
   private static final String FN_COMMIT = "commit";
   private static final String FN_ROLLBACK = "rollback";
   private static final String FN_REMOVE = "removeFromTran";
   private String currentFn = "";
   private GXAOperationWrapperImpl next;
   private GXAOperationWrapperImpl prev;
   private GXAResourceImpl gxaResource;
   private GXAAbstractTransaction gxaTran;
   private GXidImpl gXid;
   private GXAOperation operation;
   private boolean alreadyRemoved;

   GXAOperationWrapperImpl(GXAResourceImpl gxaResource, GXAAbstractTransaction gxaTran, GXAOperation operation) {
      this.operation = operation;
      this.gxaResource = gxaResource;
      this.gxaTran = gxaTran;
      this.gXid = gxaTran.getGXidImpl();
   }

   GXAOperation getOperation() {
      return this.operation;
   }

   GXAOperationWrapperImpl getNext() {
      synchronized(this.gxaTran) {
         return this.next;
      }
   }

   GXAOperationWrapperImpl getPrev() {
      synchronized(this.gxaTran) {
         return this.prev;
      }
   }

   void setNext(GXAOperationWrapperImpl next) {
      synchronized(this.gxaTran) {
         this.next = next;
      }
   }

   void setPrev(GXAOperationWrapperImpl prev) {
      synchronized(this.gxaTran) {
         this.prev = prev;
      }
   }

   public boolean removeFromTransaction() {
      synchronized(this.gxaTran) {
         if (this.alreadyRemoved) {
            throw new IllegalStateException("prog err, already removed");
         } else {
            this.alreadyRemoved = true;
            if (this.gxaTran.getStatus() != 1) {
               return false;
            } else {
               if (this.gxaResource.isTracingEnabled()) {
                  this.gxaResource.trace(this.fnString("removeFromTran"), (GXid)this.gXid, (String)this.operation.toString());
               }

               this.gxaTran.removeOperation(this);
               return true;
            }
         }
      }
   }

   public void logXATrace(String msg, Throwable t) {
      this.trace(msg, t);
   }

   void init() {
      this.currentFn = "init";
      if (storeXAVerbose.isDebugEnabled()) {
         this.traceIn("");
      }

      this.operation.onInitialize(this, this.gxaTran, this);
      if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
         this.trace("");
      }

   }

   boolean onPrepare(int pass, boolean onePhase) {
      this.currentFn = onePhase ? "prepare-1pc" : "prepare-2pc";
      if (storeXAVerbose.isDebugEnabled()) {
         this.traceIn(passString(pass));
      }

      boolean ret = this.operation.onPrepare(pass, onePhase);
      if (storeXAVerbose.isDebugEnabled()) {
         this.traceOut(passString(pass) + " ret=" + ret);
      }

      return ret;
   }

   void onCommit(int pass) {
      this.currentFn = "commit";
      if (storeXAVerbose.isDebugEnabled()) {
         this.traceIn(passString(pass));
      }

      this.operation.onCommit(pass);
      if (storeXAVerbose.isDebugEnabled()) {
         this.traceOut(passString(pass));
      }

   }

   void onRollback(int pass) {
      this.currentFn = "rollback";
      if (storeXAVerbose.isDebugEnabled()) {
         this.traceIn(passString(pass));
      }

      this.operation.onRollback(pass);
      if (storeXAVerbose.isDebugEnabled()) {
         this.traceOut(passString(pass));
      }

   }

   private static String passString(int pass) {
      return "pass=" + pass;
   }

   private String fnString(String fn) {
      return "OP-" + this.operation.getDebugPrefix() + "-" + fn + ":";
   }

   private String fnString() {
      return this.fnString(this.currentFn);
   }

   private void trace(String msg) {
      this.trace(msg, (Throwable)null);
   }

   private void trace(String msg, Throwable t) {
      this.gxaResource.trace(this.fnString(), this.gXid, this.operation.toString() + " " + msg, t);
   }

   private void traceIn(String msg) {
      this.gxaResource.traceIn(this.fnString(), (GXid)this.gXid, (String)(this.operation.toString() + " " + msg));
   }

   private void traceOut(String msg) {
      this.gxaResource.traceOut(this.fnString(), (GXid)this.gXid, (String)(this.operation.toString() + " " + msg));
   }
}
