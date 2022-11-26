package weblogic.cluster.messaging.internal.server;

import java.security.AccessController;
import weblogic.cluster.messaging.internal.PropertyService;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class PropertyServiceImpl implements PropertyService {
   private static final int HEARTBEAT_TIMEOUT_MILLIS = 15000;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public static PropertyServiceImpl getInstance() {
      return PropertyServiceImpl.Factory.THE_ONE;
   }

   public int getDiscoveryPeriodMillis() {
      return ManagementService.getRuntimeAccess(kernelId).getServer().getCluster().getUnicastDiscoveryPeriodMillis();
   }

   public int getHeartbeatTimeoutMillis() {
      return 15000;
   }

   private static final class Factory {
      static final PropertyServiceImpl THE_ONE = new PropertyServiceImpl();
   }
}
