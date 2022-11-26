package weblogic.server;

import org.glassfish.hk2.extras.interception.Intercepted;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.configuration.PartitionMBean;

@Service
@Intercepted
public class PartitionInterceptorService {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConfigurationEdit");

   @PartitionInterceptorServiceAPI
   public void onPrePartitionCreate(PartitionMBean partitionMBean) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Intercepting pre-create of partition " + partitionMBean.getName());
      }

   }

   @PartitionInterceptorServiceAPI
   public void onPostPartitionCreate(PartitionMBean partitionMBean) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Intercepting post-create of partition " + partitionMBean.getName());
      }

   }

   @PartitionInterceptorServiceAPI
   public void onPreDeletePartition(PartitionMBean partitionMBean) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Intercepting pre-delete of partition " + partitionMBean.getName());
      }

   }

   @PartitionInterceptorServiceAPI
   public void onPostDeletePartition(PartitionMBean partitionMBean) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Intercepting post-delete of partition " + partitionMBean.getName());
      }

   }

   @PartitionInterceptorServiceAPI
   public void onRollbackCreatePartition(PartitionMBean partitionMBean) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Intercepting rollback of create partition ");
      }

   }

   @PartitionInterceptorServiceAPI
   public void onRollbackDeletePartition(PartitionMBean partitionMBean) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Intercepting rollback of delete partition ");
      }

   }
}
