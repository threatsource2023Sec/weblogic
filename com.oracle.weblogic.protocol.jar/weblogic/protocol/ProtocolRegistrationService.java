package weblogic.protocol;

import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;
import weblogic.utils.jars.ManifestManager;

@Service
@Named
@RunLevel(5)
public class ProtocolRegistrationService extends AbstractServerService {
   @Inject
   @Named("RJVMService")
   private ServerService dependencyOnRJVMService;

   public void start() throws ServiceFailureException {
      ManifestManager.getServices(AbstractProtocolService.class);
   }
}
