package weblogic.spring.monitoring.actions;

import weblogic.diagnostics.instrumentation.DiagnosticActionState;
import weblogic.spring.monitoring.SpringRuntimeStatisticsHolder;
import weblogic.spring.monitoring.SpringTransactionManagerRuntimeMBeanImpl;

public class AbstractPlatformTransactionManagerSuspendAction extends BaseElapsedTimeAction {
   private static final long serialVersionUID = 1L;

   public AbstractPlatformTransactionManagerSuspendAction() {
      super("SpringAbstractPlatformTransactionManagerSuspendAction");
   }

   protected void updateRuntimeMBean(DiagnosticActionState actionState) {
      ElapsedTimeActionState state = (ElapsedTimeActionState)actionState;
      SpringTransactionManagerRuntimeMBeanImpl runtimeMBean = SpringRuntimeStatisticsHolder.getGlobalSpringTransactionManagerRuntimeMBeanImpl(state.getSpringBean());
      if (runtimeMBean != null) {
         runtimeMBean.addSuspend(state.getSucceeded());
      }

   }
}
