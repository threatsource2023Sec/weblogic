package weblogic.cluster;

import org.jvnet.hk2.annotations.Service;

@Service(
   name = "RemoteManSite"
)
public final class MANRemoteClusterMemberManager extends AbstractRemoteClusterMemberManager {
   private MANRemoteClusterMemberManager() {
      this.crossDomainSecurityNeeded = true;
   }
}
