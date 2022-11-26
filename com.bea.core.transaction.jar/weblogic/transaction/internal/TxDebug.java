package weblogic.transaction.internal;

import weblogic.diagnostics.debug.DebugLogger;
import weblogic.utils.StackTraceUtils;

public final class TxDebug {
   public static final boolean isDebugConditional = new Boolean(System.getProperty("weblogic.transaction.is.debug.conditional", "false"));
   public static final boolean isIsDebugConditionalSetRollbackOnly = new Boolean(System.getProperty("weblogic.transaction.is.debug.conditional.setRollbackOnly", "false"));
   public static final boolean isIsDebugConditionalResourceCommitRollbackException = new Boolean(System.getProperty("weblogic.transaction.is.debug.conditional.resourceCommitRollbackException", "false"));
   public static final boolean isIsDebugConditionalResourcePrepareException = new Boolean(System.getProperty("weblogic.transaction.is.debug.conditional.resourcePrepareException", "false"));
   private static final String debugEnabledClasses = System.getProperty("weblogic.transaction.debug.enabled.classes");
   public static final TxDebug JTA2PC = new TxDebug(DebugLogger.getDebugLogger("DebugJTA2PC"), "JTA2PC");
   public static final TxDebug JTA2PCDetail = new TxDebug(DebugLogger.getDebugLogger("DebugJTA2PCDetail"), "JTA2PCDetail");
   public static final TxDebug JTA2PCStackTrace = new TxDebug(DebugLogger.getDebugLogger("DebugJTA2PCStackTrace"), "JTA2PCStackTrace");
   public static final TxDebug JTAGateway = new TxDebug(DebugLogger.getDebugLogger("DebugJTAGateway"), "JTAGateway");
   public static final TxDebug JTAGatewayDetail = new TxDebug(DebugLogger.getDebugLogger("DebugJTAGatewayStackTrace"), "JTAGatewayDetail");
   public static final TxDebug JTAHealth = new TxDebug(DebugLogger.getDebugLogger("DebugJTAHealth"), "JTAHealth");
   public static final TxDebug JTALifecycle = new TxDebug(DebugLogger.getDebugLogger("DebugJTALifecycle"), "JTALifecycle");
   public static final TxDebug JTALLR = new TxDebug(DebugLogger.getDebugLogger("DebugJTALLR"), "JTALLR");
   public static final TxDebug JTAMigration = new TxDebug(DebugLogger.getDebugLogger("DebugJTAMigration"), "JTAMigration");
   public static final TxDebug JTANaming = new TxDebug(DebugLogger.getDebugLogger("DebugJTANaming"), "JTANaming");
   public static final TxDebug JTANamingStackTrace = new TxDebug(DebugLogger.getDebugLogger("DebugJTANamingStackTrace"), "JTANamingStackTrace");
   public static final TxDebug JTANonXA = new TxDebug(DebugLogger.getDebugLogger("DebugJTANonXA"), "JTANonXA");
   public static final TxDebug JTAPropagate = new TxDebug(DebugLogger.getDebugLogger("DebugJTAPropagate"), "JTAPropagate");
   public static final TxDebug JTARecovery = new TxDebug(DebugLogger.getDebugLogger("DebugJTARecovery"), "JTARecovery");
   public static final TxDebug JTAResourceHealth = new TxDebug(DebugLogger.getDebugLogger("DebugJTAResourceHealth"), "JTAResourceHealth");
   public static final TxDebug JTATLOG = new TxDebug(DebugLogger.getDebugLogger("DebugJTATLOG"), "JTATLOG");
   public static final TxDebug JTAXA = new TxDebug(DebugLogger.getDebugLogger("DebugJTAXA"), "JTAXA");
   public static final TxDebug JTAXAStackTrace = new TxDebug(DebugLogger.getDebugLogger("DebugJTAXAStackTrace"), "JTAXAStackTrace");
   public static final TxDebug JTACDI = new TxDebug(DebugLogger.getDebugLogger("DebugJTACDI"), "JTACDI");
   public static final TxDebug JTAPeerSiteRecovery = new TxDebug(DebugLogger.getDebugLogger("DebugJTAPeerSiteRecovery"), "JTAPeerSiteRecovery");
   public static final TxDebug JTAJDBC = new TxDebug(DebugLogger.getDebugLogger("DebugJTAJDBC"), "JTAJDBC");
   DebugLogger TxDebug;
   String loggerName;

   private static String getXID(TransactionImpl tx) {
      return tx != null ? tx.getXID() + ": " + tx.getName() + ": " : "";
   }

   public static void debugStack(TxDebug logger, String msg) {
      logger.debug(PlatformHelper.getPlatformHelper().throwable2StackTrace(new Exception("DEBUG: " + msg)));
   }

   public static void txdebug(TxDebug logger, TransactionImpl tx, String msg) {
      logger.debug(tx, getXID(tx) + msg, (Throwable)null);
   }

   public static void txdebug(TxDebug logger, TransactionImpl tx, String msg, Throwable t) {
      logger.debug(tx, getXID(tx) + msg, t);
   }

   public static void txdebugStack(TxDebug logger, TransactionImpl tx, String msg) {
      logger.debug(tx, PlatformHelper.getPlatformHelper().throwable2StackTrace(new Exception("DEBUG: " + getXID(tx) + msg)), (Throwable)null);
   }

   private TxDebug(DebugLogger debugLogger, String loggerName) {
      this.TxDebug = debugLogger;
      this.loggerName = loggerName;
   }

   public void setDebugEnabled(boolean enabled) {
   }

   public boolean isDebugEnabled() {
      return this.TxDebug.isDebugEnabled();
   }

   public void debug(String s) {
      if (!isDebugConditional) {
         this.TxDebug.debug(s);
      }

   }

   public void debug(String msg, Throwable t) {
      if (!isDebugConditional) {
         this.TxDebug.debug(msg, t);
      }

   }

   public void debug(TransactionImpl tx, String s) {
      this.debug(tx, s, (Throwable)null);
   }

   public void debug(TransactionImpl tx, String s, Throwable t) {
      if (!isDebugConditional) {
         if (t == null) {
            this.TxDebug.debug(s);
         } else {
            this.TxDebug.debug(s, t);
         }
      } else {
         this.addMessage(tx, s, t);
      }

   }

   void addMessage(TransactionImpl tx, String s, Throwable e) {
      if (tx != null) {
         tx.addDebugMessage(System.currentTimeMillis() + " " + Thread.currentThread() + " " + this.loggerName + " " + s + (e == null ? "" : " Exception:" + StackTraceUtils.throwable2StackTrace(e)));
      }

   }
}
