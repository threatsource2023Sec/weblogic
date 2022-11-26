package weblogic.management.patching;

import java.util.Objects;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Optional;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.provider.RuntimeAccess;
import weblogic.server.AbstractServerService;

@Service
@RunLevel(20)
@Named
public class RolloutServiceStarter extends AbstractServerService {
   @Inject
   @Optional
   private RolloutService rolloutService;
   @Inject
   private RuntimeAccess runtimeAccess;

   public void start() {
      if (this.runtimeAccess.isAdminServer()) {
         Objects.requireNonNull(this.rolloutService);
      }

   }
}
