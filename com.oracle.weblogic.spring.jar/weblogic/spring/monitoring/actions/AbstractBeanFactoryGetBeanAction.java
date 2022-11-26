package weblogic.spring.monitoring.actions;

import weblogic.diagnostics.instrumentation.DiagnosticActionState;
import weblogic.spring.monitoring.SpringApplicationContextRuntimeMBeanImpl;
import weblogic.spring.monitoring.SpringRuntimeStatisticsHolder;

public class AbstractBeanFactoryGetBeanAction extends BaseElapsedTimeAction {
   private static final long serialVersionUID = 1L;

   public AbstractBeanFactoryGetBeanAction() {
      super("SpringAbstractBeanFactoryGetBeanAction");
   }

   protected void updateRuntimeMBean(DiagnosticActionState actionState) {
      ElapsedTimeActionState state = (ElapsedTimeActionState)actionState;
      SpringApplicationContextRuntimeMBeanImpl runtimeMBean = SpringRuntimeStatisticsHolder.getGlobalSpringApplicationContextRuntimeMBeanImpl(state.getSpringBean());
      if (runtimeMBean != null) {
         runtimeMBean.addGetBeanExecution(state.getSucceeded(), state.getElapsedTime());
      }

   }
}
