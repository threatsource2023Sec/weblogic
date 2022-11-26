package weblogic.diagnostics.instrumentation;

import weblogic.diagnostics.accessor.DataRecord;

public interface InstrumentationEventListener {
   void handleInstrumentationEvent(DataRecord var1);
}
