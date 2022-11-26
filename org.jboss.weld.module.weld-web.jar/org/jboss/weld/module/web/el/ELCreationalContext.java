package org.jboss.weld.module.web.el;

import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.spi.Contextual;
import org.jboss.weld.contexts.CreationalContextImpl;

class ELCreationalContext extends CreationalContextImpl {
   private static final long serialVersionUID = -8337917208165841779L;
   private final Map expressionLocalDependentInstances = new HashMap();

   public ELCreationalContext(Contextual contextual) {
      super(contextual);
   }

   public void registerDependentInstanceForExpression(String name, Object value) {
      this.expressionLocalDependentInstances.put(name, value);
   }

   public Object getDependentInstanceForExpression(String name) {
      return this.expressionLocalDependentInstances.get(name);
   }
}
