package weblogic.management.mbeanservers.runtime.internal;

import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(20)
public final class RegisterWithDomainRuntimeServiceLate extends RegisterWithDomainRuntimeService {
   public void start() throws ServiceFailureException {
      if (!this.doEarly()) {
         super.start();
      }

   }

   public void stop() throws ServiceFailureException {
      if (!this.doEarly()) {
         super.stop();
      }

   }
}
