package weblogic.rmi.cluster;

import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;

@Service
@Named
@RunLevel(10)
public final class RemoteBinderFactoryService extends AbstractServerService {
   @Inject
   @Named("RemoteNamingService")
   private ServerService dependencyOnRemoteNamingService;

   public void start() {
      ClusterableRemoteBinderFactory.initialize();
      MigratableRemoteBinderFactory.initialize();
   }
}
