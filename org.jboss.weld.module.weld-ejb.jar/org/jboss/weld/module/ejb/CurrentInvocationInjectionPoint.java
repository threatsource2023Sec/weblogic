package org.jboss.weld.module.ejb;

import org.jboss.weld.bootstrap.api.Service;
import org.jboss.weld.injection.ThreadLocalStack;

class CurrentInvocationInjectionPoint extends ThreadLocalStack implements Service {
   public void cleanup() {
   }
}
