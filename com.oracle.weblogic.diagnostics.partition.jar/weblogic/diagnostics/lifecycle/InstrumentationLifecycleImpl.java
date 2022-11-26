package weblogic.diagnostics.lifecycle;

import weblogic.diagnostics.instrumentation.InstrumentationManager;

public class InstrumentationLifecycleImpl implements DiagnosticComponentLifecycle {
   private static InstrumentationLifecycleImpl singleton = new InstrumentationLifecycleImpl();

   public static final DiagnosticComponentLifecycle getInstance() {
      return singleton;
   }

   public int getStatus() {
      return 4;
   }

   public void initialize() throws DiagnosticComponentLifecycleException {
      InstrumentationManager.getInstrumentationManager().initializeInstrumentationParameters();
   }

   public void enable() throws DiagnosticComponentLifecycleException {
   }

   public void disable() throws DiagnosticComponentLifecycleException {
   }
}
