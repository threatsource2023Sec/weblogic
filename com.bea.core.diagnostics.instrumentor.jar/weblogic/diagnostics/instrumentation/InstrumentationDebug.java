package weblogic.diagnostics.instrumentation;

import weblogic.diagnostics.debug.DebugLogger;

public final class InstrumentationDebug {
   public static final DebugLogger DEBUG_LOGGER = DebugLogger.getDebugLogger("DebugDiagnosticInstrumentation");
   public static final DebugLogger DEBUG_RESULT = DebugLogger.getDebugLogger("DebugDiagnosticInstrumentationResult");
   public static final DebugLogger DEBUG_WEAVING = DebugLogger.getDebugLogger("DebugDiagnosticInstrumentationWeaving");
   public static final DebugLogger DEBUG_WEAVING_MATCHES = DebugLogger.getDebugLogger("DebugDiagnosticInstrumentationWeavingMatches");
   public static final DebugLogger DEBUG_ACTIONS = DebugLogger.getDebugLogger("DebugDiagnosticInstrumentationActions");
   public static final DebugLogger DEBUG_EVENTS = DebugLogger.getDebugLogger("DebugDiagnosticInstrumentationEvents");
   public static final DebugLogger DEBUG_CONFIG = DebugLogger.getDebugLogger("DebugDiagnosticInstrumentationConfig");
   public static final DebugLogger DEBUG_CLASSINFO = DebugLogger.getDebugLogger("DebugDiagnosticInstrumentationClassInfo");
}
