package weblogic.rjvm;

import java.io.IOException;
import java.rmi.UnmarshalException;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import weblogic.common.WLObjectInput;
import weblogic.common.internal.PeerInfo;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.kernel.KernelStatus;
import weblogic.rmi.cluster.PiggybackResponse;
import weblogic.rmi.extensions.RequestTimeoutException;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.internal.ObjectIO;
import weblogic.rmi.internal.RMIEnvironment;
import weblogic.rmi.spi.InboundResponse;
import weblogic.rmi.spi.MsgInput;
import weblogic.utils.StackTraceUtils;
import weblogic.workarea.WorkContextHelper;
import weblogic.workarea.WorkContextInput;

public class ResponseImpl implements Response, PeerGoneListener, InboundResponse {
   private static final DebugLogger debugMessaging = DebugLogger.getDebugLogger("DebugMessaging");
   private int id;
   private WLObjectInput msg;
   private Throwable t;
   private Object txContext;
   private final RuntimeMethodDescriptor md;
   private RJVM rjvm;
   private WLObjectInput msgThrowable = null;
   private int timeout;
   private final Lock lock = new ReentrantLock();
   private final Condition lockCondition;
   private boolean isThreadLocalContextRetrieved;
   private static final String JNDI_RESPONSE_READ_TIMEOUT = "weblogic.jndi.responseReadTimeout";
   private static final String JNDI_RESPONSE_READ_TIMEOUT_DEPRECATED = "weblogic.rmi.clientTimeout";

   public ResponseImpl(RJVM rjvm, int timeout, RuntimeMethodDescriptor md) {
      this.lockCondition = this.lock.newCondition();
      int envTimeout = this.getResponseReadTimeout();
      this.timeout = envTimeout != 0 ? envTimeout : timeout;
      this.md = md;
      this.rjvm = rjvm;
   }

   int getTimeout() {
      return this.timeout;
   }

   void setTimeout(int timeout) {
      this.timeout = timeout;
   }

   public final void setId(int id) {
      this.id = id;
   }

   public final int hashCode() {
      return this.id;
   }

   public final String toString() {
      return "weblogic.rjvm.ResponseImpl - id: '" + this.id + '\'';
   }

   public void notify(WLObjectInput msg) {
      this.lock.lock();

      try {
         this.msg = msg;
         this.lockCondition.signal();
      } finally {
         this.lock.unlock();
      }

   }

   public void notifyError(WLObjectInput msgThrowable) {
      this.lock.lock();

      try {
         this.msgThrowable = msgThrowable;
         this.lockCondition.signal();
      } finally {
         this.lock.unlock();
      }

   }

   public void notify(Throwable t) {
      this.lock.lock();

      try {
         this.t = t;
         this.lockCondition.signal();
      } finally {
         this.lock.unlock();
      }

   }

   private void waitForData() {
      String originalThreadName = null;

      try {
         if (KernelStatus.DEBUG && debugMessaging.isDebugEnabled()) {
            originalThreadName = Thread.currentThread().getName();
            Thread.currentThread().setName(originalThreadName + " [waiting for " + this.rjvm.getID().toString() + "]");
         }

         this.waitForDataInternal();
      } finally {
         if (originalThreadName != null) {
            Thread.currentThread().setName(originalThreadName);
         }

      }

   }

   private void waitForDataInternal() {
      boolean removePendingResponse = false;
      this.lock.lock();

      try {
         long timeToWait = (long)this.timeout;

         while(!this.isAvailable()) {
            try {
               boolean isTimedOut = false;
               long startTime = System.currentTimeMillis();
               if (timeToWait == 0L) {
                  this.lockCondition.await();
               } else {
                  this.lockCondition.await(timeToWait, TimeUnit.MILLISECONDS);
               }

               if (this.timeout > 0) {
                  long timeWaited = System.currentTimeMillis() - startTime;
                  if (timeWaited >= timeToWait) {
                     isTimedOut = true;
                  } else {
                     timeToWait -= timeWaited;
                  }
               }

               if (!this.isAvailable() && isTimedOut) {
                  this.t = new RequestTimeoutException("RJVM response from '" + this.rjvm + "' for '" + (this.md != null ? this.md.toString() : "null") + "' timed out after: " + this.timeout + "ms.");
                  RJVMLogger.logRequestTimeout(this.id, this.t.getMessage());
                  removePendingResponse = true;
                  break;
               }
            } catch (InterruptedException var12) {
            }
         }
      } finally {
         this.lock.unlock();
      }

      if (removePendingResponse) {
         RJVMLogger.logDebug("ResponseImpl removePendingResponse: " + this.id);
         this.rjvm.removePendingResponse(this.id);
      }

   }

   final void setTxContext(Object aTxContext) {
      this.txContext = aTxContext;
   }

   public final Object getTxContext() {
      this.waitForData();
      return this.txContext;
   }

   public final boolean hasTxContext() {
      return this.txContext != null;
   }

   public Object getContext(int id) throws IOException {
      this.waitForData();
      MsgAbbrevInputStream mais = (MsgAbbrevInputStream)((MsgAbbrevInputStream)(this.msgThrowable != null ? this.msgThrowable : this.msg));
      return mais.getContext(id);
   }

   public void retrieveThreadLocalContext() throws IOException {
      this.retrieveThreadLocalContext(true);
   }

   public void retrieveThreadLocalContext(boolean forceReset) throws IOException {
      if (!this.isThreadLocalContextRetrieved) {
         this.waitForData();
         MsgAbbrevInputStream mais = (MsgAbbrevInputStream)((MsgAbbrevInputStream)(this.msgThrowable != null ? this.msgThrowable : this.msg));
         if (mais != null) {
            if (mais.getMessageHeader().getFlag(16)) {
               mais.readExtendedContexts();
               this.isThreadLocalContextRetrieved = true;
            }

            if (forceReset && mais.getPeerInfo() != null && mais.getPeerInfo().getMajor() >= PeerInfo.VERSION_DIABLO.getMajor() && !mais.hasContext(5)) {
               WorkContextHelper.getWorkContextHelper().getInterceptor().receiveResponse((WorkContextInput)null);
            }

         }
      }
   }

   public final Throwable getThrowable() {
      this.waitForData();
      if (this.msgThrowable != null && this.t == null) {
         try {
            this.t = (Throwable)this.msgThrowable.readObject();
         } catch (IOException var12) {
            this.t = new UnmarshalException("Problem deserializing error response", var12);
         } catch (ClassNotFoundException var13) {
            this.t = new UnmarshalException("Problem finding error class", var13);
         } finally {
            try {
               this.msgThrowable.close();
            } catch (IOException var11) {
            }

         }
      }

      if (this.t instanceof PeerGoneException && RMIEnvironment.getEnvironment().printExceptionStackTrace()) {
         this.t.fillInStackTrace();
      }

      return this.t;
   }

   public final WLObjectInput getMsg() {
      this.waitForData();
      return this.msg;
   }

   public final boolean isAvailable() {
      return this.msg != null || this.msgThrowable != null || this.t != null;
   }

   public void peerGone(PeerGoneEvent e) {
      this.notify((Throwable)e.getReason());
   }

   public final MsgInput getMsgInput() {
      return (MsgInput)this.getMsg();
   }

   public Object unmarshalReturn() throws Throwable {
      this.retrieveThreadLocalContext();
      Throwable throwable = this.getThrowable();
      if (throwable != null) {
         if (RMIEnvironment.getEnvironment().printExceptionStackTrace()) {
            throw StackTraceUtils.getThrowableWithCause(throwable);
         } else {
            throw StackTraceUtils.getThrowableWithCauseAndNoStack(throwable);
         }
      } else {
         Class returnType = this.md.getReturnType();
         short returnTypeCode = this.md.getReturnTypeAbbrev();

         try {
            return ObjectIO.readObject(this.getMsgInput(), returnType, returnTypeCode);
         } catch (IOException var5) {
            throw new UnmarshalException("failed to unmarshal return type: " + returnType, var5);
         } catch (ClassNotFoundException var6) {
            throw new UnmarshalException("failed to load return type: " + returnType, var6);
         }
      }
   }

   public final PiggybackResponse getReplicaInfo() throws IOException {
      try {
         return (PiggybackResponse)this.getMsgInput().readObject(Object.class);
      } catch (ClassNotFoundException var2) {
         throw new AssertionError(var2);
      }
   }

   public final Object getActivatedPinnedRef() throws IOException {
      try {
         return this.getMsgInput().readObject(Object.class);
      } catch (ClassNotFoundException var2) {
         throw new AssertionError(var2);
      }
   }

   public final void close() {
      if (this.msg != null) {
         try {
            this.msg.close();
         } catch (IOException var2) {
         }
      }

   }

   private int getResponseReadTimeout() {
      Hashtable ht = RMIEnvironment.getEnvironment().getFromThreadLocalMap();
      Object o;
      if (ht == null) {
         o = RMIEnvironment.getEnvironment().threadEnvironmentGet();
         if (o == null) {
            return 0;
         }

         ht = RMIEnvironment.getEnvironment().getProperties(o);
         if (ht == null) {
            return 0;
         }
      }

      o = ht.get("weblogic.jndi.responseReadTimeout");
      if (o == null) {
         o = ht.get("weblogic.rmi.clientTimeout");
      }

      long result;
      if (o == null) {
         result = 0L;
      } else if (o instanceof String) {
         result = Long.parseLong((String)o);
      } else {
         result = (Long)o;
      }

      return (int)result;
   }
}
