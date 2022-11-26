package weblogic.utils.cmm.serverservice;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(5)
public class CMMServerService extends AbstractServerService {
   @Inject
   @Named("BootService")
   private ServerService dependency1;
   @Inject
   @Named("PlatformServerService")
   private ServerService dependency2;
   @Inject
   private Provider cmmJmxVerificationServiceProvider;

   public void start() throws ServiceFailureException {
      this.cmmJmxVerificationServiceProvider.get();
   }
}
