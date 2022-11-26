package weblogic.connector.work;

import java.io.Serializable;
import java.security.AccessController;
import java.util.Iterator;
import java.util.Map;
import javax.resource.ResourceException;
import javax.resource.spi.ResourceAdapter;
import javax.resource.spi.ResourceAdapterAssociation;
import javax.resource.spi.work.ExecutionContext;
import javax.resource.spi.work.RetryableWorkRejectedException;
import javax.resource.spi.work.Work;
import javax.resource.spi.work.WorkEvent;
import javax.resource.spi.work.WorkException;
import javax.resource.spi.work.WorkListener;
import javax.resource.spi.work.WorkRejectedException;
import weblogic.connector.ConnectorLogger;
import weblogic.connector.common.Debug;
import weblogic.connector.security.SubjectStack;
import weblogic.connector.security.layer.ExecutionContextImpl;
import weblogic.connector.security.layer.WorkImpl;
import weblogic.connector.security.layer.WorkListenerImpl;
import weblogic.connector.utils.PartitionUtils;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.j2ee.descriptor.wl.ConnectorWorkManagerBean;
import weblogic.kernel.KernelStatus;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.StackTraceUtils;

public class WorkManager implements javax.resource.spi.work.WorkManager, Serializable {
   private static final long serialVersionUID = 3997838374571468077L;
   private String appId;
   private String moduleName;
   private String partitionName;
   private boolean acceptingDoWorkCalls;
   private boolean suspended;
   private transient weblogic.work.WorkManager workManager;
   private transient LongRunningWorkManager lrWorkManager;
   private transient WorkContextManager ctxManager;
   private transient SubjectStack subjectStack;
   private transient ClassLoader rarClassLoader;
   private AuthenticatedSubject kernelId;
   private ResourceAdapter ra;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.connector.work.WorkManager");
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_After_Work;
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_Around_Work;
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_Before_Work;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;
   static final JoinPoint _WLDF$INST_JPFLD_1;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_1;
   static final JoinPoint _WLDF$INST_JPFLD_2;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_2;
   static final JoinPoint _WLDF$INST_JPFLD_3;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_3;
   static final JoinPoint _WLDF$INST_JPFLD_4;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_4;
   static final JoinPoint _WLDF$INST_JPFLD_5;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_5;

   private WorkManager(String appId, String moduleName, String partitionName, SubjectStack subjectStack, WorkContextProcessorFactory workContextProcessorFactory, ClassLoader rarClassLoader, weblogic.work.WorkManager wm, ConnectorWorkManagerBean connWMBean, ResourceAdapter ra) {
      this.appId = appId;
      this.moduleName = moduleName;
      this.partitionName = partitionName;
      this.subjectStack = subjectStack;
      this.rarClassLoader = rarClassLoader;
      this.acceptingDoWorkCalls = true;
      this.ra = ra;
      this.suspended = false;
      this.workManager = wm;
      this.ctxManager = new WorkContextManager();
      Iterator var10 = workContextProcessorFactory.getWorkContextProcessors().iterator();

      while(var10.hasNext()) {
         WorkContextProcessor processor = (WorkContextProcessor)var10.next();
         this.ctxManager.registerContext(processor);
      }

      this.lrWorkManager = new LongRunningWorkManager(this.ctxManager);
      Debug.deployment("====>WorkManager: connWMBean:" + connWMBean);
      this.lrWorkManager.setMaxConcurrentRequests(connWMBean.getMaxConcurrentLongRunningRequests());
      this.kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   }

   public static WorkManager create(String appId, String moduleName, String partitionName, SubjectStack subjectStack, WorkContextProcessorFactory workContextProcessorFactory, ClassLoader rarClassLoader, weblogic.work.WorkManager wm, ConnectorWorkManagerBean connWMBean, ResourceAdapter ra) {
      return new WorkManager(appId, moduleName, partitionName, subjectStack, workContextProcessorFactory, rarClassLoader, wm, connWMBean, ra);
   }

   public boolean isSuspended() {
      return this.suspended;
   }

   public void initResourceAdapter(ResourceAdapter ra) {
      if (ra == null) {
         throw new IllegalStateException("ResourceAdapter bean must not be null");
      } else if (this.ra != null) {
         throw new IllegalStateException("This WorkManager instance already associates with valid ResourceAdapter bean");
      } else {
         this.ra = ra;
      }
   }

   public void doWork(Runnable runnable) throws WorkException {
      LocalHolder var2;
      if ((var2 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         if (var2.argsCapture) {
            var2.args = InstrumentationSupport.toSensitive(2);
         }

         if (var2.monitorHolder[1] != null) {
            var2.monitorIndex = 1;
            InstrumentationSupport.createDynamicJoinPoint(var2);
            InstrumentationSupport.preProcess(var2);
         }

         if (var2.monitorHolder[2] != null) {
            var2.monitorIndex = 2;
            InstrumentationSupport.createDynamicJoinPoint(var2);
            InstrumentationSupport.process(var2);
         }

         var2.resetPostBegin();
      }

      try {
         this.doWork((Work)(new ProxyWork(runnable)), Long.MAX_VALUE, (ExecutionContext)null, (WorkListener)null);
      } catch (Throwable var4) {
         if (var2 != null) {
            var2.th = var4;
            if (var2.monitorHolder[1] != null) {
               var2.monitorIndex = 1;
               InstrumentationSupport.postProcess(var2);
            }

            if (var2.monitorHolder[0] != null) {
               var2.monitorIndex = 0;
               InstrumentationSupport.process(var2);
            }
         }

         throw var4;
      }

      if (var2 != null) {
         if (var2.monitorHolder[1] != null) {
            var2.monitorIndex = 1;
            InstrumentationSupport.postProcess(var2);
         }

         if (var2.monitorHolder[0] != null) {
            var2.monitorIndex = 0;
            InstrumentationSupport.process(var2);
         }
      }

   }

   public void doWork(Work work) throws WorkException {
      LocalHolder var2;
      if ((var2 = LocalHolder.getInstance(_WLDF$INST_JPFLD_1, _WLDF$INST_JPFLD_JPMONS_1)) != null) {
         if (var2.argsCapture) {
            var2.args = InstrumentationSupport.toSensitive(2);
         }

         if (var2.monitorHolder[1] != null) {
            var2.monitorIndex = 1;
            InstrumentationSupport.createDynamicJoinPoint(var2);
            InstrumentationSupport.preProcess(var2);
         }

         if (var2.monitorHolder[2] != null) {
            var2.monitorIndex = 2;
            InstrumentationSupport.createDynamicJoinPoint(var2);
            InstrumentationSupport.process(var2);
         }

         var2.resetPostBegin();
      }

      try {
         this.doWork((Work)work, Long.MAX_VALUE, (ExecutionContext)null, (WorkListener)null);
      } catch (Throwable var4) {
         if (var2 != null) {
            var2.th = var4;
            if (var2.monitorHolder[1] != null) {
               var2.monitorIndex = 1;
               InstrumentationSupport.postProcess(var2);
            }

            if (var2.monitorHolder[0] != null) {
               var2.monitorIndex = 0;
               InstrumentationSupport.process(var2);
            }
         }

         throw var4;
      }

      if (var2 != null) {
         if (var2.monitorHolder[1] != null) {
            var2.monitorIndex = 1;
            InstrumentationSupport.postProcess(var2);
         }

         if (var2.monitorHolder[0] != null) {
            var2.monitorIndex = 0;
            InstrumentationSupport.process(var2);
         }
      }

   }

   public void doWork(Runnable runnable, long startTimeout, ExecutionContext execContext, WorkListener workListener) throws WorkException {
      this.doWork((Work)(new ProxyWork(runnable)), startTimeout, execContext, workListener);
   }

   public void doWork(Work originalWork, long startTimeout, ExecutionContext execContext, WorkListener originalWorkListener) throws WorkException {
      this.checkPartition(this.partitionName);
      if (Debug.isWorkEnabled()) {
         Debug.work("WorkManager.doWork enter( " + (originalWork == null ? "<null work>" : originalWork.getClass()) + ", " + startTimeout + ", " + execContext + ", " + (originalWorkListener == null ? "<null listener>" : originalWorkListener.getClass()) + " )");
      }

      if (originalWork != null) {
         WorkImpl work = new WorkImpl(this.appId, this.moduleName, originalWork, this.subjectStack, this.kernelId, this.rarClassLoader, this.ctxManager);
         if (execContext != null) {
            execContext = new ExecutionContextImpl((ExecutionContext)execContext, this.subjectStack, this.kernelId);
         }

         WorkListenerImpl workListener = null;
         if (originalWorkListener != null) {
            workListener = new WorkListenerImpl(originalWorkListener, this.subjectStack, this.kernelId);
         }

         if (Debug.isWorkEnabled()) {
            Debug.work("WorkManager.doWork( " + work + ", " + startTimeout + ", " + execContext + ", " + workListener + " )");
         }

         if (!this.acceptingDoWorkCalls && KernelStatus.isServer()) {
            String exMsg = Debug.getExceptionDoWorkNotAccepted();
            rejectWork(work, workListener, new WorkRejectedException(exMsg), 2);
         }

         this.checkDuplicateExecContextAndProvider(work, (ExecutionContext)execContext, workListener);
         this.checkSuspended(work, workListener);
         this.sendWorkAcceptedEvent(work, workListener);
         WorkRequest request = this.createRequestAndSchedule(work, startTimeout, (ExecutionContext)execContext, workListener, this.ctxManager);
         request.blockTillCompletion();
         WorkException exception = request.getException();
         this.reportException(".doWork()", exception);
      } else {
         String exMsg = Debug.getExceptionWorkIsNull();
         throw new IllegalArgumentException(exMsg);
      }
   }

   public void scheduleWork(Runnable runnable) throws WorkException {
      LocalHolder var2;
      if ((var2 = LocalHolder.getInstance(_WLDF$INST_JPFLD_2, _WLDF$INST_JPFLD_JPMONS_2)) != null) {
         if (var2.argsCapture) {
            var2.args = InstrumentationSupport.toSensitive(2);
         }

         if (var2.monitorHolder[1] != null) {
            var2.monitorIndex = 1;
            InstrumentationSupport.createDynamicJoinPoint(var2);
            InstrumentationSupport.preProcess(var2);
         }

         if (var2.monitorHolder[2] != null) {
            var2.monitorIndex = 2;
            InstrumentationSupport.createDynamicJoinPoint(var2);
            InstrumentationSupport.process(var2);
         }

         var2.resetPostBegin();
      }

      try {
         this.scheduleWork((Work)(new ProxyWork(runnable)), Long.MAX_VALUE, (ExecutionContext)null, (WorkListener)null);
      } catch (Throwable var4) {
         if (var2 != null) {
            var2.th = var4;
            if (var2.monitorHolder[1] != null) {
               var2.monitorIndex = 1;
               InstrumentationSupport.postProcess(var2);
            }

            if (var2.monitorHolder[0] != null) {
               var2.monitorIndex = 0;
               InstrumentationSupport.process(var2);
            }
         }

         throw var4;
      }

      if (var2 != null) {
         if (var2.monitorHolder[1] != null) {
            var2.monitorIndex = 1;
            InstrumentationSupport.postProcess(var2);
         }

         if (var2.monitorHolder[0] != null) {
            var2.monitorIndex = 0;
            InstrumentationSupport.process(var2);
         }
      }

   }

   public void scheduleWork(Work work) throws WorkException {
      LocalHolder var2;
      if ((var2 = LocalHolder.getInstance(_WLDF$INST_JPFLD_3, _WLDF$INST_JPFLD_JPMONS_3)) != null) {
         if (var2.argsCapture) {
            var2.args = InstrumentationSupport.toSensitive(2);
         }

         if (var2.monitorHolder[1] != null) {
            var2.monitorIndex = 1;
            InstrumentationSupport.createDynamicJoinPoint(var2);
            InstrumentationSupport.preProcess(var2);
         }

         if (var2.monitorHolder[2] != null) {
            var2.monitorIndex = 2;
            InstrumentationSupport.createDynamicJoinPoint(var2);
            InstrumentationSupport.process(var2);
         }

         var2.resetPostBegin();
      }

      try {
         this.scheduleWork((Work)work, Long.MAX_VALUE, (ExecutionContext)null, (WorkListener)null);
      } catch (Throwable var4) {
         if (var2 != null) {
            var2.th = var4;
            if (var2.monitorHolder[1] != null) {
               var2.monitorIndex = 1;
               InstrumentationSupport.postProcess(var2);
            }

            if (var2.monitorHolder[0] != null) {
               var2.monitorIndex = 0;
               InstrumentationSupport.process(var2);
            }
         }

         throw var4;
      }

      if (var2 != null) {
         if (var2.monitorHolder[1] != null) {
            var2.monitorIndex = 1;
            InstrumentationSupport.postProcess(var2);
         }

         if (var2.monitorHolder[0] != null) {
            var2.monitorIndex = 0;
            InstrumentationSupport.process(var2);
         }
      }

   }

   public void scheduleWork(Runnable runnable, long startTimeout, ExecutionContext execContext, WorkListener workListener) throws WorkException {
      this.scheduleWork((Work)(new ProxyWork(runnable)), startTimeout, execContext, workListener);
   }

   public void scheduleWork(Work originalWork, long startTimeout, ExecutionContext execContext, WorkListener originalWorkListener) throws WorkException {
      this.checkPartition(this.partitionName);
      if (Debug.isWorkEnabled()) {
         Debug.work("WorkManager.scheduleWork enter( " + (originalWork == null ? "<null work>" : originalWork.getClass()) + ", " + startTimeout + ", " + (execContext == null ? "<null ExecutionContext>" : execContext.getClass()) + ", " + (originalWorkListener == null ? "<null listener>" : originalWorkListener.getClass()) + " )");
      }

      if (originalWork != null) {
         WorkImpl work = new WorkImpl(this.appId, this.moduleName, originalWork, this.subjectStack, this.kernelId, this.rarClassLoader, this.ctxManager);
         if (execContext != null) {
            execContext = new ExecutionContextImpl((ExecutionContext)execContext, this.subjectStack, this.kernelId);
         }

         WorkListenerImpl workListener = null;
         if (originalWorkListener != null) {
            workListener = new WorkListenerImpl(originalWorkListener, this.subjectStack, this.kernelId);
         }

         if (Debug.isWorkEnabled()) {
            Debug.work("WorkManager.scheduleWork( " + work + ", " + startTimeout + ", " + execContext + ", " + workListener + " )");
         }

         this.checkSuspended(work, workListener);
         this.checkDuplicateExecContextAndProvider(work, (ExecutionContext)execContext, workListener);
         this.sendWorkAcceptedEvent(work, workListener);
         this.createRequestAndSchedule(work, startTimeout, (ExecutionContext)execContext, workListener, this.ctxManager);
      } else {
         String exMsg = Debug.getExceptionWorkIsNull();
         throw new IllegalArgumentException(exMsg);
      }
   }

   public long startWork(Runnable runnable) throws WorkException {
      LocalHolder var2;
      if ((var2 = LocalHolder.getInstance(_WLDF$INST_JPFLD_4, _WLDF$INST_JPFLD_JPMONS_4)) != null) {
         if (var2.argsCapture) {
            var2.args = InstrumentationSupport.toSensitive(2);
         }

         if (var2.monitorHolder[1] != null) {
            var2.monitorIndex = 1;
            InstrumentationSupport.createDynamicJoinPoint(var2);
            InstrumentationSupport.preProcess(var2);
         }

         if (var2.monitorHolder[2] != null) {
            var2.monitorIndex = 2;
            InstrumentationSupport.createDynamicJoinPoint(var2);
            InstrumentationSupport.process(var2);
         }

         var2.resetPostBegin();
      }

      long var10000;
      try {
         var10000 = this.startWork((Work)(new ProxyWork(runnable)), Long.MAX_VALUE, (ExecutionContext)null, (WorkListener)null);
      } catch (Throwable var4) {
         if (var2 != null) {
            var2.th = var4;
            var2.ret = InstrumentationSupport.convertToObject(0L);
            if (var2.monitorHolder[1] != null) {
               var2.monitorIndex = 1;
               InstrumentationSupport.createDynamicJoinPoint(var2);
               InstrumentationSupport.postProcess(var2);
            }

            if (var2.monitorHolder[0] != null) {
               var2.monitorIndex = 0;
               InstrumentationSupport.createDynamicJoinPoint(var2);
               InstrumentationSupport.process(var2);
            }
         }

         throw var4;
      }

      if (var2 != null) {
         var2.ret = InstrumentationSupport.convertToObject(var10000);
         if (var2.monitorHolder[1] != null) {
            var2.monitorIndex = 1;
            InstrumentationSupport.createDynamicJoinPoint(var2);
            InstrumentationSupport.postProcess(var2);
         }

         if (var2.monitorHolder[0] != null) {
            var2.monitorIndex = 0;
            InstrumentationSupport.createDynamicJoinPoint(var2);
            InstrumentationSupport.process(var2);
         }
      }

      return var10000;
   }

   public long startWork(Work work) throws WorkException {
      LocalHolder var2;
      if ((var2 = LocalHolder.getInstance(_WLDF$INST_JPFLD_5, _WLDF$INST_JPFLD_JPMONS_5)) != null) {
         if (var2.argsCapture) {
            var2.args = InstrumentationSupport.toSensitive(2);
         }

         if (var2.monitorHolder[1] != null) {
            var2.monitorIndex = 1;
            InstrumentationSupport.createDynamicJoinPoint(var2);
            InstrumentationSupport.preProcess(var2);
         }

         if (var2.monitorHolder[2] != null) {
            var2.monitorIndex = 2;
            InstrumentationSupport.createDynamicJoinPoint(var2);
            InstrumentationSupport.process(var2);
         }

         var2.resetPostBegin();
      }

      long var10000;
      try {
         var10000 = this.startWork((Work)work, Long.MAX_VALUE, (ExecutionContext)null, (WorkListener)null);
      } catch (Throwable var4) {
         if (var2 != null) {
            var2.th = var4;
            var2.ret = InstrumentationSupport.convertToObject(0L);
            if (var2.monitorHolder[1] != null) {
               var2.monitorIndex = 1;
               InstrumentationSupport.createDynamicJoinPoint(var2);
               InstrumentationSupport.postProcess(var2);
            }

            if (var2.monitorHolder[0] != null) {
               var2.monitorIndex = 0;
               InstrumentationSupport.createDynamicJoinPoint(var2);
               InstrumentationSupport.process(var2);
            }
         }

         throw var4;
      }

      if (var2 != null) {
         var2.ret = InstrumentationSupport.convertToObject(var10000);
         if (var2.monitorHolder[1] != null) {
            var2.monitorIndex = 1;
            InstrumentationSupport.createDynamicJoinPoint(var2);
            InstrumentationSupport.postProcess(var2);
         }

         if (var2.monitorHolder[0] != null) {
            var2.monitorIndex = 0;
            InstrumentationSupport.createDynamicJoinPoint(var2);
            InstrumentationSupport.process(var2);
         }
      }

      return var10000;
   }

   public long startWork(Runnable runnable, long startTimeout, ExecutionContext execContext, WorkListener workListener) throws WorkException {
      return this.startWork((Work)(new ProxyWork(runnable)), startTimeout, execContext, workListener);
   }

   public long startWork(Work originalWork, long startTimeout, ExecutionContext execContext, WorkListener originalWorkListener) throws WorkException {
      this.checkPartition(this.partitionName);
      long startTimeMsec = System.currentTimeMillis();
      if (Debug.isWorkEnabled()) {
         Debug.work("WorkManager.startWork enter( " + (originalWork == null ? "<null work>" : originalWork.getClass()) + ", " + startTimeout + ", " + (execContext == null ? "<null ExecutionContext>" : execContext.getClass()) + ", " + (originalWorkListener == null ? "<null listener>" : originalWorkListener.getClass()) + " )");
      }

      if (originalWork != null) {
         WorkImpl work = new WorkImpl(this.appId, this.moduleName, originalWork, this.subjectStack, this.kernelId, this.rarClassLoader, this.ctxManager);
         if (execContext != null) {
            execContext = new ExecutionContextImpl((ExecutionContext)execContext, this.subjectStack, this.kernelId);
         }

         WorkListenerImpl workListener = null;
         if (originalWorkListener != null) {
            workListener = new WorkListenerImpl(originalWorkListener, this.subjectStack, this.kernelId);
         }

         if (Debug.isWorkEnabled()) {
            Debug.work("WorkManager.startWork( " + work + ", " + startTimeout + ", " + execContext + ", " + workListener + " )");
         }

         this.checkSuspended(work, workListener);
         this.checkDuplicateExecContextAndProvider(work, (ExecutionContext)execContext, workListener);
         this.sendWorkAcceptedEvent(work, workListener);
         WorkRequest request = this.createRequestAndSchedule(work, startTimeout, (ExecutionContext)execContext, workListener, this.ctxManager);
         request.blockTillStart();
         WorkException exception = request.getException();
         this.reportException("WorkManager.startWork()", exception);
         return System.currentTimeMillis() - startTimeMsec;
      } else {
         String exMsg = Debug.getExceptionWorkIsNull();
         throw new IllegalArgumentException(exMsg);
      }
   }

   private void checkSuspended(WorkImpl work, WorkListenerImpl workListener) throws WorkException {
      if (this.suspended) {
         String exMsg = Debug.getExceptionWorkManagerSuspended();
         rejectWork(work, workListener, new RetryableWorkRejectedException(exMsg, "-1"), 2);
      }

   }

   private void sendWorkAcceptedEvent(WorkImpl work, WorkListenerImpl workListener) {
      if (Debug.isWorkEventsEnabled()) {
         Debug.workEvent("WorkManager.sendWorkAcceptedEvent( " + work + ", " + workListener + " )");
      }

      if (workListener != null) {
         WorkEvent workEvent = new WorkEvent(work.getSourceObj(), 1, (Work)((Work)work.getSourceObj()), (WorkException)null);
         workListener.workAccepted(workEvent);
      }

   }

   private void reportException(String aMethod, WorkException exception) throws WorkException {
      if (exception != null) {
         if (Debug.isWorkEnabled()) {
            Debug.work(aMethod + " threw exception: " + StackTraceUtils.throwable2StackTrace(exception));
         }

         throw exception;
      }
   }

   public void acceptDoWorkCalls() {
      this.acceptingDoWorkCalls = true;
   }

   public void suspend() {
      this.suspended = true;
      if (Debug.isWorkEnabled()) {
         Debug.work("WorkManager.suspend()");
      }

   }

   public void resume() {
      this.suspended = false;
      if (Debug.isWorkEnabled()) {
         Debug.work("WorkManager.resume()");
      }

   }

   static void rejectWork(WorkImpl work, WorkListenerImpl workListener, WorkException we, int eventType) throws WorkException {
      if (Debug.isWorkEventsEnabled()) {
         Debug.workEvent("WorkManager.rejectWork( " + workListener + ", " + we + ", " + eventType + " )");
      }

      if (workListener != null) {
         WorkEvent workEvent = new WorkEvent(work.getSourceObj(), eventType, (Work)((Work)work.getSourceObj()), we);
         workListener.workRejected(workEvent);
      }

      throw we;
   }

   public WorkContextManager getWorkContextManager() {
      return this.ctxManager;
   }

   private WorkRequest createRequestAndSchedule(WorkImpl work, long startTimeout, ExecutionContext execContext, WorkListenerImpl workListener, WorkContextManager ctxManager) throws WorkException {
      if (this.lrWorkManager.isLongRunningWork(work)) {
         synchronized(this.lrWorkManager) {
            work.getRuntimeMetadata().setLongRunning(true);
            if (this.lrWorkManager.allowNewWork()) {
               this.processResourceAdapterAssociationIfNeeded(work.getOriginalWork());
               WorkRequest request = new LongRunningWorkRequest(work, startTimeout, execContext, workListener, ctxManager, this.lrWorkManager);
               this.lrWorkManager.schedule((LongRunningWorkRequest)request);
               return request;
            } else {
               this.lrWorkManager.increaseRejecteCound();
               ConnectorLogger.logWorkRejectedDueToExceedLimit(this.lrWorkManager.getMaxConcurrentRequests());
               String exMsg = ConnectorLogger.logWorkRejectedDueToExceedLimitLoggable(this.lrWorkManager.getMaxConcurrentRequests()).getMessage();
               rejectWork(work, workListener, new WorkRejectedException(exMsg), 2);
               return null;
            }
         }
      } else {
         work.getRuntimeMetadata().setLongRunning(false);
         this.processResourceAdapterAssociationIfNeeded(work.getOriginalWork());
         WorkRequest request = new WorkRequest(work, startTimeout, execContext, workListener, ctxManager);
         this.workManager.schedule(request);
         return request;
      }
   }

   private void processResourceAdapterAssociationIfNeeded(Work work) throws WorkException {
      if (work instanceof ResourceAdapterAssociation) {
         ResourceAdapterAssociation raa = (ResourceAdapterAssociation)work;

         try {
            raa.setResourceAdapter(this.ra);
         } catch (ResourceException var4) {
            throw new WorkException("Failed to set ResourceAdapter on work instance", var4);
         }
      }

   }

   public LongRunningWorkManager getLongRunningWorkManager() {
      return this.lrWorkManager;
   }

   private void checkDuplicateExecContextAndProvider(WorkImpl workImpl, ExecutionContext execContext, WorkListenerImpl workListener) throws WorkException {
      if (execContext != null && workImpl.supportWorkContextProvider()) {
         String exMsg = "Connector 1.6 SPEC 11.5: adpter must not submit a work that implements WorkContextProvider along with a valid ExecutionContext to a Connector WorkManager";
         rejectWork(workImpl, workListener, new WorkRejectedException(exMsg), 2);
      }

   }

   SubjectStack getSubjectStack() {
      return this.subjectStack;
   }

   private void checkPartition(String partitionName) throws WorkException {
      try {
         PartitionUtils.checkPartition(partitionName);
      } catch (IllegalStateException var3) {
         throw new WorkException(var3.getMessage());
      }
   }

   static {
      _WLDF$INST_FLD_Connector_After_Work = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_After_Work");
      _WLDF$INST_FLD_Connector_Around_Work = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_Around_Work");
      _WLDF$INST_FLD_Connector_Before_Work = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_Before_Work");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "WorkManager.java", "weblogic.connector.work.WorkManager", "doWork", "(Ljava/lang/Runnable;)V", 164, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Connector_After_Work, _WLDF$INST_FLD_Connector_Around_Work, _WLDF$INST_FLD_Connector_Before_Work};
      _WLDF$INST_JPFLD_1 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "WorkManager.java", "weblogic.connector.work.WorkManager", "doWork", "(Ljavax/resource/spi/work/Work;)V", 177, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_1 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Connector_After_Work, _WLDF$INST_FLD_Connector_Around_Work, _WLDF$INST_FLD_Connector_Before_Work};
      _WLDF$INST_JPFLD_2 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "WorkManager.java", "weblogic.connector.work.WorkManager", "scheduleWork", "(Ljava/lang/Runnable;)V", 288, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_2 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Connector_After_Work, _WLDF$INST_FLD_Connector_Around_Work, _WLDF$INST_FLD_Connector_Before_Work};
      _WLDF$INST_JPFLD_3 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "WorkManager.java", "weblogic.connector.work.WorkManager", "scheduleWork", "(Ljavax/resource/spi/work/Work;)V", 299, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_3 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Connector_After_Work, _WLDF$INST_FLD_Connector_Around_Work, _WLDF$INST_FLD_Connector_Before_Work};
      _WLDF$INST_JPFLD_4 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "WorkManager.java", "weblogic.connector.work.WorkManager", "startWork", "(Ljava/lang/Runnable;)J", 397, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_4 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Connector_After_Work, _WLDF$INST_FLD_Connector_Around_Work, _WLDF$INST_FLD_Connector_Before_Work};
      _WLDF$INST_JPFLD_5 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "WorkManager.java", "weblogic.connector.work.WorkManager", "startWork", "(Ljavax/resource/spi/work/Work;)J", 411, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_5 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Connector_After_Work, _WLDF$INST_FLD_Connector_Around_Work, _WLDF$INST_FLD_Connector_Before_Work};
   }
}
