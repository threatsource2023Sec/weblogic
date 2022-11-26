package weblogic.messaging.saf.common;

import weblogic.diagnostics.debug.DebugLogger;

public final class SAFDebug {
   public static final DebugLogger SAFAdmin = DebugLogger.getDebugLogger("DebugSAFAdmin");
   public static final DebugLogger SAFManager = DebugLogger.getDebugLogger("DebugSAFManager");
   public static final DebugLogger SAFSendingAgent = DebugLogger.getDebugLogger("DebugSAFSendingAgent");
   public static final DebugLogger SAFReceivingAgent = DebugLogger.getDebugLogger("DebugSAFReceivingAgent");
   public static final DebugLogger SAFStore = DebugLogger.getDebugLogger("DebugSAFStore");
   public static final DebugLogger SAFVerbose = DebugLogger.getDebugLogger("DebugSAFVerbose");
}
