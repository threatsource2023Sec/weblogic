package weblogic.time.server;

import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;
import weblogic.time.common.internal.InternalScheduledTrigger;

@Service
@Named("TimerService2")
@RunLevel(10)
public final class TimerService extends AbstractServerService {
   @Inject
   @Named("ApplicationShutdownService")
   private ServerService dependencyOnApplicationShutdownService;

   public void halt() throws ServiceFailureException {
      InternalScheduledTrigger.cancelAppTriggers(true);
   }

   public void stop() throws ServiceFailureException {
      this.halt();
   }
}
