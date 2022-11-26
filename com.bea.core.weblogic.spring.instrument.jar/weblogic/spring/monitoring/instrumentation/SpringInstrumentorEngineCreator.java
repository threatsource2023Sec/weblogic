package weblogic.spring.monitoring.instrumentation;

import weblogic.diagnostics.instrumentation.engine.InstrumentorEngine;

public interface SpringInstrumentorEngineCreator {
   InstrumentorEngine createSpringInstrumentorEngine();

   InstrumentorEngine createSpringInstrumentorEngine(boolean var1);
}
