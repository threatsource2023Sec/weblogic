package weblogic.spring.monitoring.actions;

import weblogic.diagnostics.instrumentation.DiagnosticActionState;
import weblogic.spring.monitoring.SpringApplicationContextRuntimeMBeanImpl;
import weblogic.spring.monitoring.SpringRuntimeStatisticsHolder;

public class AbstractBeanFactoryCreateBeanAction extends BaseElapsedTimeAction {
   private static final long serialVersionUID = 1L;

   public AbstractBeanFactoryCreateBeanAction() {
      super("SpringAbstractBeanFactoryCreateBeanAction");
   }

   public DiagnosticActionState createState() {
      return new CreateBeanElapsedTimeActionState();
   }

   protected void setArguments(Object[] arguments, DiagnosticActionState actionState) {
      super.setArguments(arguments, actionState);
      if (arguments != null && arguments.length >= 3) {
         CreateBeanElapsedTimeActionState state = (CreateBeanElapsedTimeActionState)actionState;
         state.setAbstractBeanDefinition(arguments[2]);
      }
   }

   protected void updateRuntimeMBean(DiagnosticActionState actionState) {
      CreateBeanElapsedTimeActionState state = (CreateBeanElapsedTimeActionState)actionState;
      SpringApplicationContextRuntimeMBeanImpl runtimeMBean = SpringRuntimeStatisticsHolder.getGlobalSpringApplicationContextRuntimeMBeanImpl(state.getSpringBean());
      if (runtimeMBean != null) {
         if (state.isSingleton()) {
            runtimeMBean.addSingletonBeanCreation(state.getElapsedTime());
         } else if (state.isPrototype()) {
            runtimeMBean.addPrototypeBeanCreation(state.getElapsedTime());
         } else {
            String scopeName = state.getScopeName();
            if (scopeName != null) {
               runtimeMBean.addCustomScopeBeanCreation(scopeName, state.getElapsedTime());
            }
         }
      }

   }
}
