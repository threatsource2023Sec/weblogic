package weblogic.management.rest.lib.bean.utils;

import weblogic.management.rest.lib.utils.ActivationTaskWrapper;

public class BeanActivationTaskWrapper extends BaseWrapper implements ActivationTaskWrapper {
   public BeanActivationTaskWrapper(InvocationContext ic) throws Exception {
      super(ic);
   }

   public void waitForTaskCompletion() throws Exception {
      this.act("waitForTaskCompletion");
   }

   public Exception getError() throws Exception {
      return (Exception)this.getProperty("taskError");
   }
}
