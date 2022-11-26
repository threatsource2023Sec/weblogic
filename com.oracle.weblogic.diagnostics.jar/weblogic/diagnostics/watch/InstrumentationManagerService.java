package weblogic.diagnostics.watch;

import org.jvnet.hk2.annotations.Contract;
import weblogic.diagnostics.instrumentation.InstrumentationEventListener;

@Contract
public interface InstrumentationManagerService {
   void removeInstrumentationEventListener(InstrumentationEventListener var1);

   void addInstrumentationEventListener(InstrumentationEventListener var1);
}
