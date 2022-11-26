package weblogic.t3.srvr;

import java.lang.annotation.Annotation;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.provider.RuntimeAccess;
import weblogic.server.AbstractServerService;
import weblogic.server.GlobalServiceLocator;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(10)
public class ListenerService extends AbstractServerService {
   @Inject
   private RuntimeAccess runtimeAccess;

   public void start() throws ServiceFailureException {
      if (this.runtimeAccess.getServer().getListenersBindEarly()) {
         ChannelListenerService cls = (ChannelListenerService)GlobalServiceLocator.getServiceLocator().getService(ChannelListenerService.class, new Annotation[0]);
         cls.createAndBindServerSockets();
      }

   }
}
