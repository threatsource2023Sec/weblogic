package org.jboss.weld.contexts.unbound;

import javax.inject.Singleton;
import org.jboss.weld.context.SingletonContext;
import org.jboss.weld.contexts.AbstractSharedContext;

public class SingletonContextImpl extends AbstractSharedContext implements SingletonContext {
   public SingletonContextImpl(String contextId) {
      super(contextId);
   }

   public Class getScope() {
      return Singleton.class;
   }
}
