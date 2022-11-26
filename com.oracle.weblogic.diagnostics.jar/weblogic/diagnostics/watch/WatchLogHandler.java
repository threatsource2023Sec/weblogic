package weblogic.diagnostics.watch;

import java.util.logging.Handler;
import java.util.logging.LogRecord;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.utils.LogEventRulesEvaluator;
import weblogic.logging.WLErrorManager;
import weblogic.logging.WLLevel;
import weblogic.logging.WLLogger;

public class WatchLogHandler extends Handler {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticWatch");
   private LogEventRulesEvaluator watchManager;
   private int logRuleType;

   WatchLogHandler(LogEventRulesEvaluator watchMgr, int severity) {
      this(watchMgr, severity, 1);
   }

   WatchLogHandler(LogEventRulesEvaluator watchMgr, int severity, int logRuleType) {
      this.logRuleType = 1;
      this.logRuleType = logRuleType;
      this.watchManager = watchMgr;
      this.setErrorManager(new WLErrorManager(this));
      this.setLevel(WLLevel.getLevel(severity));
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Set Watch log handler with severity " + severity);
      }

   }

   public void close() {
   }

   public void flush() {
   }

   public void publish(LogRecord rec) {
      if (this.isLoggable(rec)) {
         this.watchManager.evaluateLogEventRules(WLLogger.normalizeLogRecord(rec), this.logRuleType);
      }
   }
}
