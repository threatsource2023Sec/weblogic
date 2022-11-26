package weblogic.transaction.internal;

import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.cluster.migration.MigrationException;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(10)
public final class TransactionRecoveryFailBackService extends AbstractServerService {
   @Inject
   @Named("MigrationManagerService")
   private ServerService dependencyOnMigrationService;
   @Inject
   @Named("ClusterServiceActivator")
   private ServerService dependencyOnClusterServiceActivator;

   public void start() throws ServiceFailureException {
      try {
         TransactionRecoveryService.failbackIfNeeded();
      } catch (MigrationException var2) {
         throw new ServiceFailureException("Error occurred while trying to fail back Transaction Recovery Service.", var2);
      }
   }

   public void stop() {
   }

   public void halt() {
   }
}
