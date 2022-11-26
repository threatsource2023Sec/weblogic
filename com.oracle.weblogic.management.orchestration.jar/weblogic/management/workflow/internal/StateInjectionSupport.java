package weblogic.management.workflow.internal;

import java.lang.annotation.Annotation;
import java.util.NoSuchElementException;
import weblogic.management.workflow.StateInjectionException;
import weblogic.management.workflow.command.SharedState;
import weblogic.management.workflow.command.WorkflowContext;

public class StateInjectionSupport extends SimpleInjectionSupport {
   private final WorkflowContext ctx;

   public StateInjectionSupport(WorkflowContext ctx) {
      this.ctx = ctx;
   }

   static String getName(SharedState state, String fieldName) {
      String result = state.name();
      if (result == null || result.isEmpty()) {
         result = fieldName;
      }

      return result;
   }

   public void inject(Object target) throws StateInjectionException {
      super.inject(target, SharedState.class);
   }

   protected Object getValue(Object target, Class targetClazz, Annotation annotation, String fieldName) throws NoSuchElementException {
      SharedState state = (SharedState)annotation;
      String name = getName(state, fieldName);
      return this.ctx.getSharedState(name);
   }
}
