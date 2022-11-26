package weblogic.t3.srvr;

import java.lang.annotation.Annotation;
import weblogic.management.partition.admin.PartitionManagerService;
import weblogic.management.partition.admin.ResourceGroupLifecycleOperations;
import weblogic.management.partition.admin.ResourceGroupLifecycleOperations.RGState;
import weblogic.server.GlobalServiceLocator;

public class RGGracefulRequest extends GracefulRequest {
   private PartitionManagerService partitionManagerService = (PartitionManagerService)GlobalServiceLocator.getServiceLocator().getService(PartitionManagerService.class, new Annotation[0]);

   RGGracefulRequest(ResourceGroupLifecycleOperations.RGOperation operation, String partitionName, String resourceGroupName, boolean ignoreSessions, boolean waitForAllSessions, int timeout) {
      super(operation, partitionName, resourceGroupName, ignoreSessions, waitForAllSessions, timeout);
   }

   protected boolean isNextSuccessStateShutdown() {
      return ((ResourceGroupLifecycleOperations.RGOperation)this.operation).nextSuccessRGState == RGState.SHUTDOWN;
   }

   protected boolean isNextSuccessStateSuspend() {
      return ((ResourceGroupLifecycleOperations.RGOperation)this.operation).nextSuccessRGState == RGState.ADMIN;
   }

   protected void execSuspend() {
      this.partitionManagerService.suspendResourceGroup(this.partitionName, this.resourceGroupName, this.timeout, this.ignoreSessions);
   }

   protected void execShutdown() {
      this.partitionManagerService.shutdownResourceGroup(this.partitionName, this.resourceGroupName, this.timeout, this.ignoreSessions, this.waitForAllSessions);
   }
}
