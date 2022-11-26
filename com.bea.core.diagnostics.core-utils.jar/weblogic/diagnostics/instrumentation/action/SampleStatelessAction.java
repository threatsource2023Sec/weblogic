package weblogic.diagnostics.instrumentation.action;

import weblogic.diagnostics.instrumentation.AbstractDiagnosticAction;
import weblogic.diagnostics.instrumentation.EventQueue;
import weblogic.diagnostics.instrumentation.InstrumentationEvent;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.StatelessDiagnosticAction;

public final class SampleStatelessAction extends AbstractDiagnosticAction implements StatelessDiagnosticAction {
   public SampleStatelessAction() {
      this.setType("SampleStatelessAction");
   }

   public String[] getAttributeNames() {
      return null;
   }

   public void process(JoinPoint jp) {
      System.out.println("TRACE [SampleStatelessAction] " + jp);
      InstrumentationEvent event = this.createInstrumentationEvent(jp, false);
      if (event != null) {
         EventQueue.getInstance().enqueue(event);
      }

   }
}
