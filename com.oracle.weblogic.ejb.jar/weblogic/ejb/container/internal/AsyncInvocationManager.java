package weblogic.ejb.container.internal;

import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;
import javax.ejb.NoSuchEJBException;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.interfaces.Invokable;
import weblogic.ejb.container.interfaces.SessionBeanInfo;
import weblogic.work.WorkAreaContextWrap;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public final class AsyncInvocationManager {
   private static final DebugLogger DEBUG_LOGGER;
   private final AtomicLong idGenerator = new AtomicLong(0L);
   private final SessionBeanInfo sbi;

   public AsyncInvocationManager(SessionBeanInfo sbi) {
      this.sbi = sbi;
   }

   public Future handleAsync(Invokable inv, BaseRemoteObject bro, InvocationWrapper wrap, Object[] args, int idx, RMIAsyncInvState asyncInvState) throws NoSuchObjectException {
      Object o = asyncInvState.retrieveRMIInvId();
      if (o == null) {
         return this.schedule(inv, bro, wrap, args, idx);
      } else {
         ResultHoldingFuture var9;
         try {
            wrap.setCancelRunning(asyncInvState.getMayInterruptIfRunningFlag(o));
            Future future = (Future)SessionRemoteMethodInvoker.invoke(inv, bro, wrap, args, idx);
            var9 = new ResultHoldingFuture(future.get());
            return var9;
         } catch (RemoteException var14) {
            if (wrap.getMethodDescriptor().getClientViewDescriptor().extendsRemote()) {
               var9 = new ResultHoldingFuture(var14);
               return var9;
            }

            var9 = new ResultHoldingFuture(EJBRuntimeUtils.wrapCauseInEJBException(var14));
         } catch (Throwable var15) {
            var9 = new ResultHoldingFuture(var15);
            return var9;
         } finally {
            asyncInvState.finishedExecuting(o);
         }

         return var9;
      }
   }

   public void handleOneway(Invokable inv, BaseRemoteObject bro, InvocationWrapper wrap, Object[] args, int idx, RMIAsyncInvState asyncInvState) throws NoSuchObjectException {
      Object o = asyncInvState.retrieveRMIInvId();
      if (o == null) {
         this.schedule(inv, bro, wrap, args, idx);
      } else {
         try {
            SessionRemoteMethodInvoker.invoke(inv, bro, wrap, args, idx);
         } catch (Throwable var12) {
            EJBLogger.logExceptionDuringAsyncInvocationExecution(" Method '" + wrap.getMethodDescriptor().getMethodInfo().getSignature() + "' on EJB '" + this.sbi.getDisplayName() + "'", var12);
         } finally {
            asyncInvState.finishedExecuting(o);
         }
      }

   }

   public AsyncExecutor scheduleAsync(Invokable inv, BaseLocalObject blo, InvocationWrapper wrap, Object[] args, int idx) {
      return this.doActualSchedule(inv, blo, wrap, args, idx);
   }

   public void scheduleOneway(Invokable inv, BaseLocalObject blo, InvocationWrapper wrap, Object[] args, int idx) {
      this.doActualSchedule(inv, blo, wrap, args, idx);
   }

   private AsyncExecutor schedule(Invokable inv, BaseRemoteObject bro, InvocationWrapper wrap, Object[] args, int idx) throws NoSuchObjectException {
      try {
         return this.doActualSchedule(inv, bro, wrap, args, idx);
      } catch (NoSuchEJBException var7) {
         if (!wrap.getMethodDescriptor().getClientViewDescriptor().extendsRemote()) {
            throw var7;
         } else {
            throw new NoSuchObjectException(var7.getMessage());
         }
      }
   }

   private AsyncExecutor doActualSchedule(Invokable inv, Object bo, InvocationWrapper wrap, Object[] args, int idx) {
      this.sbi.getBeanManager().ensureDeployed();
      long id = this.idGenerator.incrementAndGet();
      AsyncExecutor ae = new AsyncExecutor(id, inv, bo, wrap, args, idx);
      WorkManager wm = WorkManagerFactory.getInstance().find(this.sbi.getDispatchPolicy(), this.sbi.getDeploymentInfo().getApplicationId(), (String)null);
      wm.schedule(new WorkAreaContextWrap(ae));
      if (DEBUG_LOGGER.isDebugEnabled()) {
         this.debug("Scheduled invocation id : " + id);
      }

      return ae;
   }

   private void debug(String s) {
      DEBUG_LOGGER.debug("[AsyncInvocationManager] " + s);
   }

   static {
      DEBUG_LOGGER = EJBDebugService.invokeLogger;
   }
}
