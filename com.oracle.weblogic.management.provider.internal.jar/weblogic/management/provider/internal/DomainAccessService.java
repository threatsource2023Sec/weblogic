package weblogic.management.provider.internal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.provider.DomainAccess;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.server.AbstractServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(
   value = 10,
   mode = 0
)
public class DomainAccessService extends AbstractServerService {
   @Inject
   private RuntimeAccess runtimeAccess;
   @Inject
   private Provider domainAccess;

   public void start() throws ServiceFailureException {
      if (this.runtimeAccess.isAdminServer()) {
         ManagementService.initializeDomain((DomainAccess)this.domainAccess.get());
         ((DomainAccessImpl)this.domainAccess.get()).initializeDomainRuntimeMBean();
      }
   }
}
