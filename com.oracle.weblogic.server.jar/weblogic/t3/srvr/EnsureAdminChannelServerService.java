package weblogic.t3.srvr;

import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.protocol.configuration.ChannelHelper;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerLifecycleException;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(10)
public class EnsureAdminChannelServerService extends AbstractServerService {
   @Inject
   private WebLogicServer t3Srvr;

   public void start() throws ServiceFailureException {
      try {
         if ("STANDBY".equalsIgnoreCase(this.t3Srvr.getStartupMode())) {
            ensureAdminChannel();
         }

      } catch (ServerLifecycleException var2) {
         throw new ServiceFailureException(var2);
      }
   }

   private static void ensureAdminChannel() throws ServerLifecycleException {
      if (!isAdminChannelEnabled()) {
         T3SrvrTextTextFormatter fmt = new T3SrvrTextTextFormatter();
         throw new ServerLifecycleException(fmt.getStartupWithoutAdminChannel());
      }
   }

   private static boolean isAdminChannelEnabled() {
      return ChannelHelper.isLocalAdminChannelEnabled();
   }
}
