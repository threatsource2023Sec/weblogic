package weblogic.spring.monitoring.actions;

import weblogic.diagnostics.instrumentation.AbstractDiagnosticAction;
import weblogic.diagnostics.instrumentation.AroundDiagnosticAction;
import weblogic.diagnostics.instrumentation.DiagnosticActionState;
import weblogic.diagnostics.instrumentation.DynamicJoinPoint;
import weblogic.diagnostics.instrumentation.JoinPoint;

public abstract class BaseElapsedTimeAction extends AbstractDiagnosticAction implements AroundDiagnosticAction {
   protected BaseElapsedTimeAction() {
      this.setType("SpringBaseElapsedTimeAction");
   }

   protected BaseElapsedTimeAction(String actionType) {
      this.setType(actionType);
   }

   public String[] getAttributeNames() {
      return null;
   }

   public DiagnosticActionState createState() {
      return new ElapsedTimeActionState();
   }

   public boolean requiresArgumentsCapture() {
      return true;
   }

   public void preProcess(JoinPoint jp, DiagnosticActionState actionState) {
      this.setArguments(((DynamicJoinPoint)jp).getArguments(), actionState);
   }

   public void postProcess(JoinPoint jp, DiagnosticActionState actionState) {
      this.postProcessUpdateState(actionState);
      this.updateRuntimeMBean(actionState);
   }

   protected void setArguments(Object[] arguments, DiagnosticActionState actionState) {
      ElapsedTimeActionState state = (ElapsedTimeActionState)actionState;
      if (arguments != null && arguments.length != 0) {
         state.setSpringBean(arguments[0]);
      }
   }

   protected void preProcessUpdateState(DiagnosticActionState actionState) {
      ((ElapsedTimeActionState)actionState).startTimer();
   }

   protected void postProcessUpdateState(DiagnosticActionState actionState) {
      ((ElapsedTimeActionState)actionState).stopTimer();
   }

   protected abstract void updateRuntimeMBean(DiagnosticActionState var1);
}
