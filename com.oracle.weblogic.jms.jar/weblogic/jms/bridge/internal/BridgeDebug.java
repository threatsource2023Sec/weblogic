package weblogic.jms.bridge.internal;

import weblogic.diagnostics.debug.DebugLogger;

public final class BridgeDebug {
   public static final DebugLogger MessagingBridgeStartup = DebugLogger.getDebugLogger("DebugMessagingBridgeStartup");
   public static final DebugLogger MessagingBridgeRuntime = DebugLogger.getDebugLogger("DebugMessagingBridgeRuntime");
   public static final DebugLogger MessagingBridgeRuntimeVerbose = DebugLogger.getDebugLogger("DebugMessagingBridgeRuntimeVerbose");
}
