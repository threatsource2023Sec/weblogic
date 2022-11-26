package weblogic.work;

import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.spi.ComponentRequest;

public abstract class ComponentWorkAdapter extends WorkAdapter implements ComponentRequest {
   protected ComponentInvocationContext componentInvocationContext;

   void returnForReuse() {
      super.returnForReuse();
      this.componentInvocationContext = null;
   }

   public ComponentInvocationContext getComponentInvocationContext() {
      return this.componentInvocationContext;
   }
}
