package weblogic.spring.monitoring.actions;

import weblogic.diagnostics.instrumentation.DiagnosticActionState;
import weblogic.spring.monitoring.SpringApplicationContextRuntimeMBeanImpl;
import weblogic.spring.monitoring.SpringRuntimeStatisticsHolder;

public class DefaultListableBeanFactoryGetBeanNamesForTypeAction extends BaseElapsedTimeAction {
   private static final long serialVersionUID = 1L;

   public DefaultListableBeanFactoryGetBeanNamesForTypeAction() {
      super("SpringDefaultListableBeanFactoryGetBeanNamesForTypeAction");
   }

   protected void updateRuntimeMBean(DiagnosticActionState actionState) {
      ElapsedTimeActionState state = (ElapsedTimeActionState)actionState;
      SpringApplicationContextRuntimeMBeanImpl runtimeMBean = SpringRuntimeStatisticsHolder.getGlobalSpringApplicationContextRuntimeMBeanImpl(state.getSpringBean());
      if (runtimeMBean != null) {
         runtimeMBean.addGetBeanNamesForTypeExecution(state.getSucceeded(), state.getElapsedTime());
      }

   }
}
