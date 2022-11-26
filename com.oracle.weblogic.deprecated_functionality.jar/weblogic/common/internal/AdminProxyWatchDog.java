package weblogic.common.internal;

import java.security.AccessController;
import weblogic.common.CommonLogger;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.ServerLifecycleException;
import weblogic.time.common.internal.TimeEventGenerator;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManagerFactory;

class AdminProxyWatchDog implements TimerListener, Manufacturable {
   Timer timeRegistration;
   AuthenticatedSubject subject;
   int intervalSecs;
   long lastEcho = TimeEventGenerator.getCurrentSecs();
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   AdminProxyWatchDog(AuthenticatedSubject subject, int intervalSecs) {
      this.intervalSecs = intervalSecs;
      this.subject = subject;
   }

   public void initialize() {
      this.timeRegistration = TimerManagerFactory.getTimerManagerFactory().getTimerManager("AdminProxyWatchDog").scheduleAtFixedRate(this, 0L, (long)(this.intervalSecs * 1000));
      CommonLogger.logEnabled((long)this.intervalSecs);
   }

   void disable() {
      if (this.timeRegistration != null) {
         this.timeRegistration.cancel();
         this.timeRegistration = null;
      }

      CommonLogger.logDisabled();
   }

   public void destroy() {
      if (this.timeRegistration != null) {
         CommonLogger.logLost();
         this.disable();

         try {
            ManagementService.getRuntimeAccess(kernelId).getServerRuntime().forceShutdown();
         } catch (ServerLifecycleException var2) {
            CommonLogger.logErrorWhileServerShutdown(var2);
         }

         this.subject = null;
      }

   }

   public void timerExpired(Timer timer) {
      int diff = TimeEventGenerator.deltaSecs(this.lastEcho);
      CommonLogger.logTick(diff);
      if (diff > this.intervalSecs) {
         CommonLogger.logNoEcho(diff);

         try {
            ManagementService.getRuntimeAccess(kernelId).getServerRuntime().forceShutdown();
         } catch (ServerLifecycleException var4) {
            CommonLogger.logErrorWhileServerShutdown(var4);
         }
      }

   }

   public void echoReceived() {
      CommonLogger.logEcho();
      this.lastEcho = TimeEventGenerator.getCurrentSecs();
   }
}
