package weblogic.connector.work;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.resource.spi.work.ExecutionContext;
import javax.resource.spi.work.RetryableWorkRejectedException;
import javax.resource.spi.work.WorkCompletedException;
import javax.resource.spi.work.WorkEvent;
import javax.resource.spi.work.WorkException;
import weblogic.connector.common.Debug;
import weblogic.connector.exception.RAException;
import weblogic.connector.security.layer.TransactionContextImpl;
import weblogic.connector.security.layer.WorkImpl;
import weblogic.connector.security.layer.WorkListenerImpl;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.utils.StackTraceUtils;
import weblogic.workarea.WorkContextHelper;
import weblogic.workarea.spi.WorkContextMapInterceptor;

class WorkRequest implements Runnable {
   protected WorkImpl work;
   private volatile int status;
   private long startTimeout;
   private long creationTime;
   private ExecutionContext ec;
   private WorkListenerImpl listener;
   private volatile boolean notifyOnWorkStart;
   private volatile boolean notifyOnWorkCompletion;
   private WorkException exception;
   private WorkContextMapInterceptor contexts;
   private WorkContextManager ctxManager;
   private boolean needProcessWorkContexts;
   static final long serialVersionUID = -8012252343747514130L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.connector.work.WorkRequest");
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_After_Work;
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_Around_Work;
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_Before_Work;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;
   static final JoinPoint _WLDF$INST_JPFLD_1;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_1;

   WorkRequest(WorkImpl work, long startTimeout, ExecutionContext ec, WorkListenerImpl listener, WorkContextManager ctxManager) throws WorkException {
      this.work = work;
      this.startTimeout = startTimeout;
      this.ec = ec;
      this.listener = listener;
      this.creationTime = System.currentTimeMillis();
      this.contexts = WorkContextHelper.getWorkContextHelper().getInterceptor().copyThreadContexts(2);
      this.ctxManager = ctxManager;
      this.needProcessWorkContexts = work.supportWorkContextProvider();
      if (Debug.isWorkEnabled()) {
         Debug.work("WorkRequest.<init>.@" + Integer.toHexString(this.hashCode()) + " work = " + work);
      }

   }

   public void run() {
      if (Debug.isWorkEnabled()) {
         Debug.work("WorkRequest.run(); start to run work = " + this.work);
      }

      long currentTime = System.currentTimeMillis();
      if (this.startTimeout != Long.MAX_VALUE && currentTime > this.creationTime + this.startTimeout) {
         this.sendWorkRejectedEvent();
      } else {
         Throwable error = null;
         List processedContexts = new ArrayList();
         List cleanupErrors = null;

         try {
            WorkContextHelper.getWorkContextHelper().getInterceptor().restoreThreadContexts(this.contexts);
            this.sendWorkStartedEvent();
            List contexts = null;
            TransactionContextImpl txContextImpl = null;
            if (this.needProcessWorkContexts) {
               contexts = this.work.getWorkContexts();
               if (contexts != null && !((List)contexts).isEmpty()) {
                  contexts = this.ctxManager.getValidator().removeNullElements((List)contexts);
               } else {
                  this.needProcessWorkContexts = false;
               }
            } else if (this.ec != null) {
               if (this.ec.getXid() == null) {
                  if (Debug.isXAworkEnabled()) {
                     Debug.xaWork("WorkRequest.run found null Xid; don't need to process transaction context");
                  }
               } else {
                  if (Debug.isXAworkEnabled()) {
                     Debug.xaWork("WorkRequest.run wrap ExecutionContext using TransactionContext");
                  }

                  if (contexts == null) {
                     contexts = new ArrayList();
                  }

                  txContextImpl = TransactionContextProcessor.createWrapper(this.ec, this.work.getSubjectStack(), this.work.getKernelId());
                  ((List)contexts).add(txContextImpl);
                  this.needProcessWorkContexts = true;
               }
            }

            if (this.needProcessWorkContexts) {
               this.ctxManager.validate((List)contexts, this.work.getRuntimeMetadata());
               this.ctxManager.setupContext((List)contexts, this.work.getRuntimeMetadata(), processedContexts);
            }

            this.work.getRuntimeMetadata().setThreadNameAsNeeded();
            this.work.run();
         } catch (Throwable var11) {
            if (Debug.isWorkEnabled()) {
               Debug.work("WorkRequest.run(); got exception " + var11, var11);
            }

            error = var11;
         } finally {
            if (!processedContexts.isEmpty()) {
               cleanupErrors = this.ctxManager.cleanupContext(processedContexts, error != null, this.work.getRuntimeMetadata());
            }

            this.sendWorkCompletedEvent(error, cleanupErrors);
            this.work.getRuntimeMetadata().restoreThreadNameAsNeeded();
         }
      }

   }

   private void sendWorkCompletedEvent(Throwable t, List cleanupErrors) {
      LocalHolder var8;
      if ((var8 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         if (var8.argsCapture) {
            var8.args = InstrumentationSupport.toSensitive(3);
         }

         if (var8.monitorHolder[1] != null) {
            var8.monitorIndex = 1;
            InstrumentationSupport.createDynamicJoinPoint(var8);
            InstrumentationSupport.preProcess(var8);
         }

         if (var8.monitorHolder[2] != null) {
            var8.monitorIndex = 2;
            InstrumentationSupport.createDynamicJoinPoint(var8);
            InstrumentationSupport.process(var8);
         }

         var8.resetPostBegin();
      }

      try {
         if (Debug.isWorkEventsEnabled()) {
            Debug.workEvent("WorkRequest.sendWorkCompletedEvent(); listener = " + this.listener + " for work = " + this.work);
         }

         if (cleanupErrors != null && !cleanupErrors.isEmpty()) {
            Object ex;
            if (cleanupErrors.size() == 1) {
               ex = (Throwable)cleanupErrors.get(0);
            } else {
               RAException rae = new RAException();
               Iterator var5 = cleanupErrors.iterator();

               while(var5.hasNext()) {
                  Throwable th = (Throwable)var5.next();
                  rae.addError(th);
               }

               ex = rae;
            }

            if (t == null) {
               t = ex;
            } else {
               ((Throwable)ex).initCause((Throwable)t);
               t = ex;
            }
         }

         if (t != null) {
            if (Debug.isWorkEventsEnabled()) {
               Debug.workEvent("WorkRequest.sendWorkCompletedEvent() creating WorkCompletedException\n" + StackTraceUtils.throwable2StackTrace((Throwable)t));
            }

            if (t instanceof WorkCompletedException) {
               this.exception = (WorkCompletedException)t;
            } else {
               Debug.workEvent("WorkRequest.sendWorkCompletedEvent() set errorCode: WorkException.UNDEFINED:0");
               this.exception = new WorkCompletedException();
               this.exception.setErrorCode("0");
               this.exception.initCause((Throwable)t);
            }
         }

         if (this.listener != null) {
            WorkEvent event = new WorkEvent(this.work.getOriginalWork(), 4, this.work.getOriginalWork(), this.exception);
            this.listener.workCompleted(event);
         }

         this.status = 4;
         synchronized(this) {
            if (this.notifyOnWorkCompletion) {
               this.notifyAll();
            }
         }
      } catch (Throwable var12) {
         if (var8 != null) {
            var8.th = var12;
            if (var8.monitorHolder[1] != null) {
               var8.monitorIndex = 1;
               InstrumentationSupport.postProcess(var8);
            }

            if (var8.monitorHolder[0] != null) {
               var8.monitorIndex = 0;
               InstrumentationSupport.process(var8);
            }
         }

         throw var12;
      }

      if (var8 != null) {
         if (var8.monitorHolder[1] != null) {
            var8.monitorIndex = 1;
            InstrumentationSupport.postProcess(var8);
         }

         if (var8.monitorHolder[0] != null) {
            var8.monitorIndex = 0;
            InstrumentationSupport.process(var8);
         }
      }

   }

   private void sendWorkStartedEvent() {
      LocalHolder var3;
      if ((var3 = LocalHolder.getInstance(_WLDF$INST_JPFLD_1, _WLDF$INST_JPFLD_JPMONS_1)) != null) {
         if (var3.argsCapture) {
            var3.args = InstrumentationSupport.toSensitive(1);
         }

         if (var3.monitorHolder[1] != null) {
            var3.monitorIndex = 1;
            InstrumentationSupport.createDynamicJoinPoint(var3);
            InstrumentationSupport.preProcess(var3);
         }

         if (var3.monitorHolder[2] != null) {
            var3.monitorIndex = 2;
            InstrumentationSupport.createDynamicJoinPoint(var3);
            InstrumentationSupport.process(var3);
         }

         var3.resetPostBegin();
      }

      try {
         if (Debug.isWorkEventsEnabled()) {
            Debug.workEvent("WorkRequest.sendWorkStartedEvent(); listener = " + this.listener);
         }

         if (this.listener != null) {
            WorkEvent workEvent = new WorkEvent(this.work.getOriginalWork(), 3, this.work.getOriginalWork(), (WorkException)null);
            this.listener.workStarted(workEvent);
         }

         this.status = 3;
         synchronized(this) {
            if (this.notifyOnWorkStart) {
               this.notifyAll();
            }
         }
      } catch (Throwable var7) {
         if (var3 != null) {
            var3.th = var7;
            if (var3.monitorHolder[1] != null) {
               var3.monitorIndex = 1;
               InstrumentationSupport.postProcess(var3);
            }

            if (var3.monitorHolder[0] != null) {
               var3.monitorIndex = 0;
               InstrumentationSupport.process(var3);
            }
         }

         throw var7;
      }

      if (var3 != null) {
         if (var3.monitorHolder[1] != null) {
            var3.monitorIndex = 1;
            InstrumentationSupport.postProcess(var3);
         }

         if (var3.monitorHolder[0] != null) {
            var3.monitorIndex = 0;
            InstrumentationSupport.process(var3);
         }
      }

   }

   void sendWorkRejectedEvent() {
      if (Debug.isWorkEventsEnabled()) {
         Debug.workEvent("WorkRequest.sendWorkRejectedEvent(); listener = " + this.listener + " for work = " + this.work);
      }

      String exMsg = Debug.getExceptionStartTimeout();
      this.exception = new RetryableWorkRejectedException(exMsg, "1");
      if (Debug.isWorkEventsEnabled()) {
         Debug.workEvent("WorkRequest.sendWorkRejectedEvent(); set errorCide:  WorkException.START_TIMED_OUT1");
      }

      if (this.listener != null) {
         WorkEvent event = new WorkEvent(this.work.getOriginalWork(), 2, this.work.getOriginalWork(), this.exception);
         this.listener.workRejected(event);
      }

      this.status = 2;
      synchronized(this) {
         this.notifyAll();
      }
   }

   void blockTillCompletion() {
      if (Debug.isWorkEnabled()) {
         Debug.work("WorkRequest.blockTillCompletion(); waiting for end of work = " + this.work);
      }

      try {
         if (this.status != 4 && this.status != 2) {
            synchronized(this) {
               while(this.status != 4 && this.status != 2) {
                  this.notifyOnWorkCompletion = true;

                  try {
                     this.wait();
                  } catch (InterruptedException var8) {
                  }
               }

            }
         }
      } finally {
         if (Debug.isWorkEnabled()) {
            Debug.work("WorkRequest.blockTillCompletion(); end. work = " + this.work);
         }

      }
   }

   void blockTillStart() {
      if (Debug.isWorkEnabled()) {
         Debug.work("WorkRequest.blockTillStart(); waiting for start of work = " + this.work);
      }

      try {
         if (this.status != 3 && this.status != 4 && this.status != 2) {
            synchronized(this) {
               while(this.status != 3 && this.status != 4 && this.status != 2) {
                  this.notifyOnWorkStart = true;

                  try {
                     this.wait();
                  } catch (InterruptedException var8) {
                  }
               }

               return;
            }
         }
      } finally {
         if (Debug.isWorkEnabled()) {
            Debug.work("WorkRequest.blockTillStart(); end. work = " + this.work);
         }

      }

   }

   WorkException getException() {
      return this.exception;
   }

   WorkImpl getWork() {
      return this.work;
   }

   static {
      _WLDF$INST_FLD_Connector_After_Work = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_After_Work");
      _WLDF$INST_FLD_Connector_Around_Work = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_Around_Work");
      _WLDF$INST_FLD_Connector_Before_Work = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_Before_Work");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "WorkRequest.java", "weblogic.connector.work.WorkRequest", "sendWorkCompletedEvent", "(Ljava/lang/Throwable;Ljava/util/List;)V", 146, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Connector_After_Work, _WLDF$INST_FLD_Connector_Around_Work, _WLDF$INST_FLD_Connector_Before_Work};
      _WLDF$INST_JPFLD_1 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "WorkRequest.java", "weblogic.connector.work.WorkRequest", "sendWorkStartedEvent", "()V", 203, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_1 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Connector_After_Work, _WLDF$INST_FLD_Connector_Around_Work, _WLDF$INST_FLD_Connector_Before_Work};
   }
}
