package weblogic.store.admin;

import weblogic.management.ManagementException;
import weblogic.management.runtime.PersistentStoreConnectionRuntimeMBean;
import weblogic.management.runtime.PersistentStoreRuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.store.OperationStatistics;
import weblogic.store.PersistentStoreConnection;
import weblogic.store.common.PartitionNameUtils;

public class PersistentStoreConnectionRuntimeMBeanImpl extends RuntimeMBeanDelegate implements PersistentStoreConnectionRuntimeMBean {
   private final OperationStatistics statistics;

   public PersistentStoreConnectionRuntimeMBeanImpl(PersistentStoreConnection conn, PersistentStoreRuntimeMBean parent) throws ManagementException {
      super(PartitionNameUtils.stripDecoratedPartitionNamesFromCombinedName(".", conn.getName()), parent, true, "Connections");
      this.statistics = conn.getStatistics();
   }

   public long getCreateCount() {
      return this.statistics.getCreateCount();
   }

   public long getReadCount() {
      return this.statistics.getReadCount();
   }

   public long getUpdateCount() {
      return this.statistics.getUpdateCount();
   }

   public long getDeleteCount() {
      return this.statistics.getDeleteCount();
   }

   public long getObjectCount() {
      return this.statistics.getObjectCount();
   }
}
