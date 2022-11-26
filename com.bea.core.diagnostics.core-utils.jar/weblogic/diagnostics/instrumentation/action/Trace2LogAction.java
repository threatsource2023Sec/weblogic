package weblogic.diagnostics.instrumentation.action;

import weblogic.diagnostics.instrumentation.AbstractDiagnosticAction;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.StatelessDiagnosticAction;

public class Trace2LogAction extends AbstractDiagnosticAction implements StatelessDiagnosticAction {
   public Trace2LogAction() {
      this.setType(StatelessDiagnosticAction.class.getName());
   }

   public String[] getAttributeNames() {
      return null;
   }

   public void process(JoinPoint jp) {
      long invocationTime = System.currentTimeMillis();
   }
}
