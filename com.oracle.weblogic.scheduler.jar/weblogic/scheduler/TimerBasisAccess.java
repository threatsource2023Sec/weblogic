package weblogic.scheduler;

import java.security.AccessController;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import weblogic.management.configuration.JDBCSystemResourceMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.timers.RuntimeDomainSelector;

public final class TimerBasisAccess {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final ConcurrentHashMap timerBasisMap = new ConcurrentHashMap();

   static void reset() {
      timerBasisMap.clear();
   }

   static Map getTimerBasisMap() {
      return timerBasisMap;
   }

   public static TimerBasis getTimerBasis() throws TimerException {
      String domainID = RuntimeDomainSelector.getDomain();
      return getTimerBasis(domainID);
   }

   public static TimerBasis getTimerBasis(boolean forGlobalRG) throws TimerException {
      return forGlobalRG ? getTimerBasis("weblogic.timers.defaultDomain-RG") : getTimerBasis(RuntimeDomainSelector.getDomain());
   }

   static TimerBasis getTimerBasis(String domainID) throws TimerException {
      TimerBasis timerBasis = (TimerBasis)timerBasisMap.get(domainID);
      if (timerBasis != null) {
         return timerBasis;
      } else {
         TimerBasis newTimerBasis = createTimerBasis(domainID);
         timerBasis = (TimerBasis)timerBasisMap.putIfAbsent(domainID, newTimerBasis);
         return timerBasis == null ? newTimerBasis : timerBasis;
      }
   }

   static void removeTimerBasis(String domainID) {
      timerBasisMap.remove(domainID);
   }

   private static TimerBasis createTimerBasis(String domainID) throws TimerException {
      JDBCSystemResourceMBean dataSource = null;
      String tableName = null;
      String clusterName = null;
      RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
      boolean forGlobalRG = "weblogic.timers.defaultDomain-RG".equals(domainID);
      if (!forGlobalRG && !RuntimeDomainSelector.isDefaultDomain(domainID)) {
         PartitionMBean partitionMBean = findPartitionMBean(runtimeAccess, domainID);
         if (partitionMBean == null) {
            throw new TimerException("Unable to create TimerAccess for domain " + domainID);
         }

         dataSource = partitionMBean.getDataSourceForJobScheduler();
         tableName = partitionMBean.getJobSchedulerTableName();
         clusterName = partitionMBean.getPartitionID();
      } else {
         ServerMBean serverMBean = runtimeAccess.getServer();

         assert serverMBean.getCluster() != null;

         dataSource = serverMBean.getCluster().getDataSourceForJobScheduler();
         tableName = serverMBean.getCluster().getJobSchedulerTableName();
         clusterName = forGlobalRG ? "0" : serverMBean.getCluster().getName();
      }

      assert dataSource != null;

      try {
         TimerBasis basis = new DBTimerBasisImpl(dataSource, tableName, runtimeAccess.getDomainName(), clusterName, runtimeAccess.getServerName(), domainID);
         return basis;
      } catch (SQLException var7) {
         throw new TimerException("Unable to access data source '" + dataSource.getName() + "'", var7);
      }
   }

   private static PartitionMBean findPartitionMBean(RuntimeAccess runtimeAccess, String domainId) {
      return null;
   }
}
