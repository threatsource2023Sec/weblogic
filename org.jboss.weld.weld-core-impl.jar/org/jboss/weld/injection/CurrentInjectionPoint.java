package org.jboss.weld.injection;

import org.jboss.weld.bootstrap.api.Service;

public class CurrentInjectionPoint extends ThreadLocalStack implements Service {
   public void cleanup() {
   }
}
