package weblogic.spring.monitoring.actions;

import weblogic.diagnostics.instrumentation.DiagnosticActionState;
import weblogic.spring.monitoring.SpringApplicationContextRuntimeMBeanImpl;
import weblogic.spring.monitoring.SpringRuntimeStatisticsHolder;
import weblogic.spring.monitoring.utils.AbstractApplicationContextDelegator;

public class AbstractApplicationContextRefreshAction extends BaseElapsedTimeAction {
   private static final long serialVersionUID = 1L;

   public AbstractApplicationContextRefreshAction() {
      super("SpringAbstractApplicationContextRefreshAction");
   }

   protected void updateRuntimeMBean(DiagnosticActionState actionState) {
      ElapsedTimeActionState state = (ElapsedTimeActionState)actionState;
      SpringApplicationContextRuntimeMBeanImpl runtimeMBean = SpringRuntimeStatisticsHolder.getGlobalSpringApplicationContextRuntimeMBeanImpl(this.getBeanFactory(state.getSpringBean()));
      if (runtimeMBean != null) {
         runtimeMBean.addRefresh(state.getSucceeded(), state.getElapsedTime());
      }

   }

   private Object getBeanFactory(Object applicationContext) {
      if (applicationContext == null) {
         return null;
      } else {
         AbstractApplicationContextDelegator delegator = new AbstractApplicationContextDelegator(applicationContext);
         return delegator.getBeanFactory();
      }
   }
}
