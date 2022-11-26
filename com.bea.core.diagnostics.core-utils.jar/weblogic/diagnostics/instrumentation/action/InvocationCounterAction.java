package weblogic.diagnostics.instrumentation.action;

import weblogic.diagnostics.instrumentation.AbstractDiagnosticAction;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.StatelessDiagnosticAction;

public class InvocationCounterAction extends AbstractDiagnosticAction implements StatelessDiagnosticAction {
   public InvocationCounterAction() {
      this.setType(StatelessDiagnosticAction.class.getName());
   }

   public String[] getAttributeNames() {
      return null;
   }

   public void process(JoinPoint jp) {
      long invocationTime = System.currentTimeMillis();
   }
}
