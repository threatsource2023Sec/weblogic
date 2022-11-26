package weblogic.connector.utils;

import java.security.AccessController;
import weblogic.diagnostics.accessor.runtime.AccessRuntimeMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.rjvm.LocalRJVM;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class RuntimeAccessUtils {
   private static final RuntimeAccess runtimeAccess;

   public static String getRJVMID() {
      return String.valueOf(LocalRJVM.getLocalRJVM().getID().getTransientIdentity().getIdentityAsLong());
   }

   public static String getDomainAndServerName() {
      return String.format("%s/%s", runtimeAccess.getDomainName(), runtimeAccess.getServerName());
   }

   public static AccessRuntimeMBean getWLDFAccessRuntimeMBean() {
      ServerRuntimeMBean serverRuntime = runtimeAccess.getServerRuntime();
      if (PartitionUtils.isDomainScope()) {
         return serverRuntime.getWLDFRuntime().getWLDFAccessRuntime();
      } else {
         PartitionRuntimeMBean partitionRuntime = serverRuntime.lookupPartitionRuntime(PartitionUtils.getPartitionName());
         return partitionRuntime.getWLDFPartitionRuntime().getWLDFPartitionAccessRuntime();
      }
   }

   public static DomainMBean getDomainMBean() {
      return runtimeAccess.getDomain();
   }

   static {
      AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
   }
}
