package weblogic.spring.monitoring.actions;

import weblogic.diagnostics.instrumentation.AbstractDiagnosticAction;
import weblogic.diagnostics.instrumentation.AroundDiagnosticAction;
import weblogic.diagnostics.instrumentation.DiagnosticActionState;
import weblogic.diagnostics.instrumentation.DynamicJoinPoint;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.spring.monitoring.SpringApplicationContextRuntimeMBeanImpl;
import weblogic.spring.monitoring.SpringRuntimeStatisticsHolder;

public class AbstractBeanFactoryRegisterScopeAction extends AbstractDiagnosticAction implements AroundDiagnosticAction {
   private static final long serialVersionUID = 1L;

   public AbstractBeanFactoryRegisterScopeAction() {
      this.setType("SpringAbstractBeanFactoryRegisterScopeAction");
   }

   public String[] getAttributeNames() {
      return null;
   }

   public DiagnosticActionState createState() {
      return new ScopeArgActionState();
   }

   public boolean requiresArgumentsCapture() {
      return true;
   }

   public void preProcess(JoinPoint jp, DiagnosticActionState actionState) {
      this.setArguments(((DynamicJoinPoint)jp).getArguments(), actionState);
   }

   public void postProcess(JoinPoint jp, DiagnosticActionState actionState) {
      this.updateRuntimeMBean(actionState);
   }

   private void setArguments(Object[] arguments, DiagnosticActionState actionState) {
      ScopeArgActionState state = (ScopeArgActionState)actionState;
      if (arguments != null && arguments.length != 0) {
         state.setSpringBean(arguments[0]);
         if (arguments.length >= 1) {
            state.setScopeName((String)arguments[1]);
         }

      }
   }

   private void updateRuntimeMBean(DiagnosticActionState actionState) {
      ScopeArgActionState state = (ScopeArgActionState)actionState;
      if (state.getSucceeded()) {
         SpringApplicationContextRuntimeMBeanImpl runtimeMBean = SpringRuntimeStatisticsHolder.getGlobalSpringApplicationContextRuntimeMBeanImpl(state.getSpringBean());
         if (runtimeMBean != null) {
            runtimeMBean.addCustomScope(state.getScopeName());
         }
      }

   }
}
