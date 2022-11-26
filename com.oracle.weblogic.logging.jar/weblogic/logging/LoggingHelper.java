package weblogic.logging;

import java.security.AccessController;
import java.util.logging.Logger;
import weblogic.i18n.logging.LoggingTextFormatter;
import weblogic.kernel.Kernel;
import weblogic.kernel.KernelLogManager;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public final class LoggingHelper {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static Logger domainLogger = null;

   public static Logger getServerLogger() {
      return Kernel.isServer() ? KernelLogManager.getLogger() : null;
   }

   public static Logger getClientLogger() {
      return !Kernel.isServer() ? KernelLogManager.getLogger() : null;
   }

   public static Logger getDomainLogger() throws LoggerNotAvailableException {
      if (!ManagementService.getRuntimeAccess(kernelId).isAdminServer()) {
         LoggingTextFormatter fmt = new LoggingTextFormatter();
         throw new LoggerNotAvailableException(fmt.getDomainLoggerDoesNotExistMsg());
      } else {
         return domainLogger;
      }
   }

   /** @deprecated */
   @Deprecated
   public static void setDomainLogger(Logger dl) {
   }

   static void initDomainLogger(Logger dl) {
      if (domainLogger == null) {
         domainLogger = dl;
      }

   }

   /** @deprecated */
   @Deprecated
   public static void addServerLoggingHandler(Logger logger, boolean useParentHandlers) {
      JDKLoggerFactory.addServerLoggingHandler(logger, useParentHandlers);
   }
}
