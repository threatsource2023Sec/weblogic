package org.jboss.weld.contexts.unbound;

import javax.enterprise.context.ApplicationScoped;
import org.jboss.weld.context.ApplicationContext;
import org.jboss.weld.contexts.AbstractSharedContext;

public class ApplicationContextImpl extends AbstractSharedContext implements ApplicationContext {
   public ApplicationContextImpl(String contextId) {
      super(contextId);
   }

   public Class getScope() {
      return ApplicationScoped.class;
   }
}
