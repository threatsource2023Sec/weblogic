package org.jboss.weld.event;

import org.jboss.weld.bootstrap.api.Service;
import org.jboss.weld.injection.ThreadLocalStack;

public class CurrentEventMetadata extends ThreadLocalStack implements Service {
   public void cleanup() {
   }
}
