package weblogic.socket;

import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.health.HealthMonitorService;
import weblogic.server.AbstractServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(10)
public final class SocketMuxerServerService extends AbstractServerService {
   public void start() throws ServiceFailureException {
      SocketMuxer.initOOMENotifier(HealthMonitorService.getOomeNotifier());
      SocketMuxer.initSocketMuxerOnServer();
   }
}
