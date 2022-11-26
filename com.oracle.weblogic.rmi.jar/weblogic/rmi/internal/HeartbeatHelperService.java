package weblogic.rmi.internal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.Context;
import javax.naming.NamingException;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.jndi.Environment;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(10)
public final class HeartbeatHelperService extends AbstractServerService {
   @Inject
   @Named("RemoteNamingService")
   private ServerService dependencyOnRemoteNamingService;

   public void start() throws ServiceFailureException {
      try {
         Environment env = new Environment();
         env.setReplicateBindings(false);
         env.setProperty("weblogic.jndi.createUnderSharable", "true");
         Context ctx = env.getInitialContext();
         ctx.addToEnvironment("weblogic.jndi.createIntermediateContexts", "true");
         ctx.bind("weblogic/rmi/extensions/server/HeartbeatHelper", HeartbeatHelperImpl.getHeartbeatHelper());
      } catch (NamingException var3) {
         throw new ServiceFailureException(var3);
      }
   }
}
