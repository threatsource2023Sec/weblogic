package org.jboss.weld.bootstrap.api.helpers;

import org.jboss.weld.bootstrap.api.BootstrapService;

public abstract class AbstractBootstrapService implements BootstrapService {
   public void cleanup() {
      this.cleanupAfterBoot();
   }
}
