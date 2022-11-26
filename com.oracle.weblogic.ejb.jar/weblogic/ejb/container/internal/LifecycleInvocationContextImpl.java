package weblogic.ejb.container.internal;

import java.util.HashMap;
import java.util.Map;

public class LifecycleInvocationContextImpl implements InvocationContext {
   private Map contextData = null;

   public Map getContextData() {
      if (this.contextData == null) {
         this.contextData = new HashMap();
      }

      return this.contextData;
   }
}
