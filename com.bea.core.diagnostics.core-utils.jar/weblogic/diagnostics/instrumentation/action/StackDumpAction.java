package weblogic.diagnostics.instrumentation.action;

import weblogic.diagnostics.instrumentation.AbstractDiagnosticAction;
import weblogic.diagnostics.instrumentation.EventQueue;
import weblogic.diagnostics.instrumentation.InstrumentationEvent;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.StatelessDiagnosticAction;
import weblogic.diagnostics.type.StackTraceUtility;

public final class StackDumpAction extends AbstractDiagnosticAction implements StatelessDiagnosticAction {
   private static final String INST_PKG = "weblogic.diagnostics.instrumentation";

   public StackDumpAction() {
      this.setType("StackDumpAction");
   }

   public String[] getAttributeNames() {
      return null;
   }

   public void process(JoinPoint jp) {
      InstrumentationEvent event = this.createInstrumentationEvent(jp, false);
      if (event != null) {
         Exception dump = new Exception();
         int framesToRemove = StackTraceUtility.getMatchingFrames(dump, "weblogic.diagnostics.instrumentation");
         String modifiedDump = StackTraceUtility.removeFrames(dump, framesToRemove);
         event.setPayload(modifiedDump);
         EventQueue.getInstance().enqueue(event);
      }

   }
}
