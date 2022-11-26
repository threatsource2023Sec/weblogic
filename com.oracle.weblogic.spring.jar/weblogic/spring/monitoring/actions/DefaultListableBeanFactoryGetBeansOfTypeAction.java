package weblogic.spring.monitoring.actions;

import weblogic.diagnostics.instrumentation.DiagnosticActionState;
import weblogic.spring.monitoring.SpringApplicationContextRuntimeMBeanImpl;
import weblogic.spring.monitoring.SpringRuntimeStatisticsHolder;

public class DefaultListableBeanFactoryGetBeansOfTypeAction extends BaseElapsedTimeAction {
   private static final long serialVersionUID = 1L;

   public DefaultListableBeanFactoryGetBeansOfTypeAction() {
      super("SpringDefaultListableBeanFactoryGetBeansOfTypeAction");
   }

   protected void updateRuntimeMBean(DiagnosticActionState actionState) {
      ElapsedTimeActionState state = (ElapsedTimeActionState)actionState;
      SpringApplicationContextRuntimeMBeanImpl runtimeMBean = SpringRuntimeStatisticsHolder.getGlobalSpringApplicationContextRuntimeMBeanImpl(state.getSpringBean());
      if (runtimeMBean != null) {
         runtimeMBean.addGetBeansOfTypeExecution(state.getSucceeded(), state.getElapsedTime());
      }

   }
}
