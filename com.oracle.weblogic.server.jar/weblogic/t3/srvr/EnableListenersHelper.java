package weblogic.t3.srvr;

import java.lang.annotation.Annotation;
import java.security.AccessController;
import weblogic.kernel.T3SrvrLogger;
import weblogic.logging.LoggingMemoryOptimizer;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.server.ServiceFailureException;

final class EnableListenersHelper {
   private static final boolean DEBUG = false;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private boolean started;

   private EnableListenersHelper() {
   }

   static EnableListenersHelper getInstance() {
      return EnableListenersHelper.Factory.THE_ONE;
   }

   synchronized void start() throws ServiceFailureException {
      if (!this.started) {
         this.started = true;
         ChannelListenerService cls = (ChannelListenerService)GlobalServiceLocator.getServiceLocator().getService(ChannelListenerService.class, new Annotation[0]);
         if (!ManagementService.getRuntimeAccess(kernelId).getServer().getListenersBindEarly()) {
            cls.createAndBindServerSockets();
         }

         cls.enableServerSockets();
         SetUIDRendezvous.finish();
         logStartedMessage();
         LoggingMemoryOptimizer.clearCache();
      }
   }

   void stop() {
      this.halt();
   }

   synchronized void halt() {
      if (this.started) {
         this.started = false;
         ChannelListenerService cls = (ChannelListenerService)GlobalServiceLocator.getServiceLocator().getService(ChannelListenerService.class, new Annotation[0]);
         cls.closeServerSockets();
      }
   }

   private static void logStartedMessage() {
      RuntimeAccess ra = ManagementService.getRuntimeAccess(kernelId);
      String server = ra.getServerName();
      String domain = ra.getDomainName();
      boolean prod = ra.getDomain().isProductionModeEnabled();
      if (ra.getDomain().getSecurityConfiguration().getSecureMode().isSecureModeEnabled()) {
         T3SrvrLogger.logSecureModeEnabled(server);
      }

      if (ra.isAdminServer()) {
         if (prod) {
            T3SrvrLogger.logStartedAdminServerProduction(server, domain);
         } else {
            T3SrvrLogger.logStartedAdminServerDevelopment(server, domain);
         }
      } else if (!ra.isAdminServerAvailable()) {
         if (prod) {
            T3SrvrLogger.logStartedIndependentManagedServerProdMode(server, domain);
         } else {
            T3SrvrLogger.logStartedIndependentManagedServerDevMode(server, domain);
         }
      } else if (prod) {
         T3SrvrLogger.logStartedManagedServerProduction(server, domain);
      } else {
         T3SrvrLogger.logStartedManagedServerDevelopment(server, domain);
      }

   }

   private static void debug(String str) {
      System.out.println("<LISTENER_DEBUG>" + str);
   }

   // $FF: synthetic method
   EnableListenersHelper(Object x0) {
      this();
   }

   private static final class Factory {
      static final EnableListenersHelper THE_ONE = new EnableListenersHelper();
   }
}
