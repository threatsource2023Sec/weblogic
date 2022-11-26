package weblogic.logging;

import com.bea.logging.LoggingSupplementalAttribute;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.AccessController;
import java.util.Properties;
import org.jvnet.hk2.annotations.Service;
import weblogic.core.base.api.FastThreadLocalMarker;
import weblogic.diagnostics.context.Correlation;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.kernel.AuditableThreadLocal;
import weblogic.kernel.AuditableThreadLocalFactory;
import weblogic.management.provider.ManagementService;
import weblogic.security.Security;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.transaction.TxHelperService;
import weblogic.utils.LocatorUtilities;

@Service
public final class LogEntryInitializer implements FastThreadLocalMarker {
   private static final String UNKNOWN = "Unknown";
   private static String currentMachineName = null;
   private static String currentServerName = null;
   private static boolean serverInitialized = false;
   private static final AuditableThreadLocal threadLocal = AuditableThreadLocalFactory.createThreadLocal();
   private static final boolean DEBUG = false;

   public static void initializeLogEntry(LogEntry rec) {
      if (threadLocal.get() == null) {
         threadLocal.set(new Boolean(true));

         try {
            String user = getCurrentUserId();
            rec.setUserId(user);
            rec.setTransactionId(getCurrentTransactionId());
            rec.setMachineName(getCurrentMachineName());
            rec.setServerName(getCurrentServerName());
            setDiagnosticContextIds(rec);
            Object severityValue = rec.getSupplementalAttributes().get(LoggingSupplementalAttribute.SUPP_ATTR_SEVERITY_VALUE.getAttributeName());
            if (severityValue == null) {
               rec.getSupplementalAttributes().put(LoggingSupplementalAttribute.SUPP_ATTR_SEVERITY_VALUE.getAttributeName(), rec.getSeverity());
            }

            ComponentInvocationContextManager compCtxMgr = ComponentInvocationContextManager.getInstance();
            if (!rec.isExcludePartition() && compCtxMgr != null) {
               ComponentInvocationContext compCtx = compCtxMgr.getCurrentComponentInvocationContext();
               if (compCtx != null) {
                  String pid = compCtx.getPartitionId();
                  if (pid != null && !pid.isEmpty()) {
                     rec.getSupplementalAttributes().put(LoggingSupplementalAttribute.SUPP_ATTR_PARTITION_ID.getAttributeName(), pid);
                  }

                  String pname = compCtx.getPartitionName();
                  if (pname != null && !pname.isEmpty()) {
                     rec.getSupplementalAttributes().put(LoggingSupplementalAttribute.SUPP_ATTR_PARTITION_NAME.getAttributeName(), pname);
                  }
               }
            }
         } catch (Throwable var7) {
         }

         threadLocal.set((Object)null);
      }
   }

   public static String getCurrentMachineName() {
      if (currentMachineName == null) {
         try {
            currentMachineName = InetAddress.getLocalHost().getHostName();
         } catch (UnknownHostException var1) {
            currentMachineName = "Unknown";
         }
      }

      return currentMachineName;
   }

   public static String getCurrentServerName() {
      if (!serverInitialized) {
         return "";
      } else {
         if (currentServerName == null) {
            currentServerName = ManagementService.getRuntimeAccess(LogEntryInitializer.KernelIdInitializer.KERNEL_ID).getServerName();
         }

         return currentServerName;
      }
   }

   public static String getCurrentTransactionId() {
      if (!serverInitialized) {
         return "";
      } else {
         TxHelperService txHelper = (TxHelperService)LocatorUtilities.getService(TxHelperService.class);
         String txId = txHelper.getTransactionId();
         if (txId == null) {
            txId = "";
         }

         return txId;
      }
   }

   public static String getCurrentUserId() {
      if (!serverInitialized) {
         return "";
      } else {
         String userId = SubjectUtils.getUsername(Security.getCurrentSubject());
         if (userId == null) {
            userId = "";
         }

         return userId;
      }
   }

   static boolean isServerInitialized() {
      return serverInitialized;
   }

   static void setServerInitialized(boolean value) {
      serverInitialized = value;
   }

   private static void setDiagnosticContextIds(LogEntry rec) {
      if (!serverInitialized) {
         rec.setDiagnosticContextId("");
      } else {
         Correlation ctx = (Correlation)LocatorUtilities.getService(Correlation.class);
         if (ctx == null) {
            rec.setDiagnosticContextId("");
            Properties supAttrs = rec.getSupplementalAttributes();
            supAttrs.setProperty(LoggingSupplementalAttribute.SUPP_ATTR_RID.getAttributeName(), "");
         } else {
            String id = ctx.getECID();
            rec.setDiagnosticContextId(id == null ? "" : id);
            String rid = ctx.getRID();
            Properties supAttrs = rec.getSupplementalAttributes();
            supAttrs.setProperty(LoggingSupplementalAttribute.SUPP_ATTR_RID.getAttributeName(), rid == null ? "" : rid);
         }
      }
   }

   public String getFastThreadLocalClassName() {
      return this.getClass().getCanonicalName();
   }

   private static class KernelIdInitializer {
      private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   }
}
