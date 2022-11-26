package weblogic.diagnostics.instrumentation.action;

import weblogic.diagnostics.instrumentation.AbstractDiagnosticAction;
import weblogic.diagnostics.instrumentation.EventQueue;
import weblogic.diagnostics.instrumentation.InstrumentationEvent;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.StatelessDiagnosticAction;

public final class DisplayArgumentsAction extends AbstractDiagnosticAction implements StatelessDiagnosticAction {
   public DisplayArgumentsAction() {
      this.setType("DisplayArgumentsAction");
   }

   public String[] getAttributeNames() {
      return null;
   }

   public boolean requiresArgumentsCapture() {
      return true;
   }

   public void process(JoinPoint jp) {
      InstrumentationEvent event = this.createInstrumentationEvent(jp, true);
      if (event != null) {
         EventQueue.getInstance().enqueue(event);
      }

   }
}
