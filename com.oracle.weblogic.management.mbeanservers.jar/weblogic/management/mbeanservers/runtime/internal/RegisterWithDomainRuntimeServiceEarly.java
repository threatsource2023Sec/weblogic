package weblogic.management.mbeanservers.runtime.internal;

import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(15)
public final class RegisterWithDomainRuntimeServiceEarly extends RegisterWithDomainRuntimeService {
   @Inject
   @Named("EnableListenersIfAdminChannelAbsentService")
   private ServerService dependencyOnEnableListenersIfAdminChannelAbsentService;

   public void start() throws ServiceFailureException {
      if (this.doEarly()) {
         super.start();
      }

   }

   public void stop() throws ServiceFailureException {
      if (this.doEarly()) {
         super.stop();
      }

   }
}
