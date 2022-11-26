package weblogic.management.logging;

import java.rmi.RemoteException;
import java.security.AccessController;
import java.util.List;
import java.util.logging.Logger;
import javax.naming.Context;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.snmp.agent.SNMPTrapException;
import weblogic.diagnostics.snmp.agent.SNMPTrapSender;
import weblogic.diagnostics.snmp.agent.SNMPTrapUtil;
import weblogic.i18n.logging.LoggingTextFormatter;
import weblogic.jndi.Environment;
import weblogic.logging.LogEntry;
import weblogic.logging.LoggingHelper;
import weblogic.logging.WLLevel;
import weblogic.logging.WLLogRecord;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.snmp.server.ALSBTrapUtil;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public final class DomainLogHandlerImpl implements DomainLogHandler {
   private static final DebugLogger DEBUG_LOGGER = DebugLogger.getDebugLogger("DebugDomainLogHandler");
   private static DomainLogHandler singleton = null;
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static RuntimeAccess runtimeAccess;
   private static LoggingTextFormatter loggingTextFmt;
   private Logger domainLogger;

   public static synchronized DomainLogHandler getInstance() throws DomainLogHandlerException {
      if (!runtimeAccess.isAdminServer()) {
         throw new DomainLogHandlerException(loggingTextFmt.getDomainLoggerDoesNotExistMsg());
      } else {
         if (singleton == null) {
            try {
               singleton = new DomainLogHandlerImpl();
            } catch (Exception var1) {
               throw new DomainLogHandlerException(loggingTextFmt.getDomainLoggerDoesNotExistMsg(), var1);
            }
         }

         return singleton;
      }
   }

   private DomainLogHandlerImpl() throws Exception {
      Environment env = new Environment();
      env.setReplicateBindings(false);
      env.setCreateIntermediateContexts(true);
      env.setProperty("weblogic.jndi.createUnderSharable", "true");
      Context initialCtx = env.getInitialContext();
      initialCtx.bind("weblogic.logging.DomainLogHandler", this);
      this.domainLogger = LoggingHelper.getDomainLogger();
   }

   public Logger getDomainLogger() {
      return this.domainLogger;
   }

   public void publishLogEntries(LogEntry[] entries) {
      if (this.domainLogger != null && entries != null) {
         WLLogRecord logRecord = null;

         for(int i = 0; i < entries.length; ++i) {
            LogEntry le = entries[i];
            logRecord = new WLLogRecord(WLLevel.getLevel(le.getSeverity()), le.getLogMessage(), false);
            logRecord.setId(le.getId());
            logRecord.setLoggerName(le.getSubsystem());
            logRecord.setMillis(le.getTimestamp());
            logRecord.setParameters(new Object[0]);
            logRecord.setThrowableWrapper(le.getThrowableWrapper());
            logRecord.setMachineName(le.getMachineName());
            logRecord.setServerName(le.getServerName());
            logRecord.setThreadName(le.getThreadName());
            logRecord.setUserId(le.getUserId());
            logRecord.setTransactionId(le.getTransactionId());
            logRecord.setDiagnosticContextId(le.getDiagnosticContextId());
            logRecord.getSupplementalAttributes().putAll(le.getSupplementalAttributes());
            if (DEBUG_LOGGER.isDebugEnabled()) {
               DEBUG_LOGGER.debug("Logging message to domain log " + logRecord.getLogMessage() + ", supplemental attrs=" + logRecord.getSupplementalAttributes());
            }

            this.domainLogger.log(logRecord);
         }

      }
   }

   public void sendTrap(String trapName, List varBindings) throws RemoteException {
      try {
         SNMPTrapSender ts = SNMPTrapUtil.getInstance().getSNMPTrapSender();
         if (ts != null) {
            ts.sendTrap(trapName, varBindings);
         }

      } catch (SNMPTrapException var4) {
         throw new RemoteException(var4.getMessage());
      }
   }

   public void sendALAlertTrap(String trapType, String severity, String domainName, String serverName, String alertId, String ruleId, String ruleName, String ruleCondition, String timeStamp, String annotation, String serviceName, String servicePath) throws RemoteException {
      try {
         ALSBTrapUtil.sendALSBAlert(trapType, severity, domainName, serverName, alertId, ruleId, ruleName, ruleCondition, timeStamp, annotation, serviceName, servicePath);
      } catch (Exception var14) {
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("Error sending trap to admin server", var14);
         }

         throw new RemoteException(var14.getMessage());
      }
   }

   public void ping() {
   }

   static {
      runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
      loggingTextFmt = LoggingTextFormatter.getInstance();
   }
}
