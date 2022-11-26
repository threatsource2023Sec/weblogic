package weblogic.diagnostics.instrumentation.action;

import weblogic.diagnostics.instrumentation.AbstractDiagnosticAction;
import weblogic.diagnostics.instrumentation.EventQueue;
import weblogic.diagnostics.instrumentation.InstrumentationDebug;
import weblogic.diagnostics.instrumentation.InstrumentationEvent;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.StatelessDiagnosticAction;

public final class TraceAction extends AbstractDiagnosticAction implements StatelessDiagnosticAction {
   public TraceAction() {
      this.setType("TraceAction");
   }

   public String[] getAttributeNames() {
      return null;
   }

   public void process(JoinPoint jp) {
      InstrumentationEvent event = this.createInstrumentationEvent(jp, false);
      if (InstrumentationDebug.DEBUG_ACTIONS.isDebugEnabled()) {
         InstrumentationDebug.DEBUG_ACTIONS.debug("TraceAction.process: event=" + event);
      }

      if (event != null) {
         EventQueue.getInstance().enqueue(event);
      }

   }
}
