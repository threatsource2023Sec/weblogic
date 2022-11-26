package weblogic.cluster;

import org.jvnet.hk2.annotations.Service;

@Service(
   name = "LocalSite"
)
public final class RemoteClusterMemberManagerImpl extends AbstractRemoteClusterMemberManager {
   private RemoteClusterMemberManagerImpl() {
      this.crossDomainSecurityNeeded = false;
   }
}
