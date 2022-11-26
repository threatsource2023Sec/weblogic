package org.glassfish.hk2.runlevel;

import org.glassfish.hk2.runlevel.internal.AsyncRunLevelContext;
import org.glassfish.hk2.runlevel.internal.RunLevelControllerImpl;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class RunLevelServiceModule extends AbstractBinder {
   protected void configure() {
      this.addActiveDescriptor(RunLevelContext.class);
      this.addActiveDescriptor(AsyncRunLevelContext.class);
      this.addActiveDescriptor(RunLevelControllerImpl.class);
   }
}
