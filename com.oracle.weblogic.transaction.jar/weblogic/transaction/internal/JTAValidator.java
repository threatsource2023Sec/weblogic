package weblogic.transaction.internal;

import java.security.AccessController;
import java.util.Arrays;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceParamsBean;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JDBCSystemResourceMBean;
import weblogic.management.configuration.JTAClusterMBean;
import weblogic.management.configuration.JTAMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class JTAValidator {
   static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public static void validateJdbcTlogDataSourceGlobal(JDBCSystemResourceMBean dataSourceSystemMBean) {
      DomainMBean domainMBean = null;
      String dataSourceName = dataSourceSystemMBean.getName();
      if (dataSourceSystemMBean.getParentBean() instanceof DomainMBean) {
         domainMBean = (DomainMBean)dataSourceSystemMBean.getParentBean();
      }

      if (domainMBean == null) {
         throw new IllegalArgumentException("DataSource " + dataSourceName + " is not a system level datasource");
      } else {
         JDBCSystemResourceMBean[] resources = domainMBean.getJDBCSystemResources();
         boolean jdbcSystemResourceFound = false;
         String partitionName = null;

         for(int j = 0; j < resources.length; ++j) {
            JDBCSystemResourceMBean jdbcSystemResourceMBean = resources[j];
            String jdbcSystemResourceName = jdbcSystemResourceMBean.getName();
            partitionName = jdbcSystemResourceMBean.getPartitionName();
            if (dataSourceName.equals(jdbcSystemResourceName) && partitionName == null) {
               jdbcSystemResourceFound = true;
               break;
            }
         }

         if (!jdbcSystemResourceFound) {
            throw new IllegalArgumentException("DataSource " + dataSourceName + " is not a system level datasource");
         }
      }
   }

   public static void validateDeterminersGlobal(JTAMBean jtaMBean, String[] determiners) {
      DomainMBean domainMBean;
      if (jtaMBean instanceof JTAClusterMBean) {
         ClusterMBean clusterMBean = (ClusterMBean)jtaMBean.getParentBean();
         domainMBean = (DomainMBean)clusterMBean.getParentBean();
      } else {
         domainMBean = (DomainMBean)jtaMBean.getParentBean();
      }

      if (domainMBean != null) {
         int size = determiners.length;
         if (size != 0) {
            for(int determinerInd = 0; determinerInd < size; ++determinerInd) {
               String determinerName = determiners[determinerInd];
               if (determinerName != null && determinerName.length() != 0) {
                  try {
                     if (!isParallelXAEnabled(jtaMBean)) {
                        throw new IllegalArgumentException("determiner resource " + Arrays.toString(determiners) + " can not be configured if parallel-xa-enabled is false");
                     }

                     isClusterParallelXAEnabled(domainMBean, determiners);
                     isDataSourceGlobal(determinerName, domainMBean);
                  } catch (IllegalArgumentException var7) {
                     throw var7;
                  }
               }
            }

         }
      }
   }

   public static void isClusterParallelXAEnabled(DomainMBean domainMBean, String[] determiners) {
      ClusterMBean[] clusterMBeans = domainMBean.getClusters();
      if (clusterMBeans.length != 0) {
         for(int i = 0; i < clusterMBeans.length; ++i) {
            JTAClusterMBean jtaClusterMBean = clusterMBeans[i].getJTACluster();
            boolean parallelXAEnabled = jtaClusterMBean.getParallelXAEnabled();
            int size = determiners.length;
            if (size == 0) {
               return;
            }

            for(int determinerInd = 0; determinerInd < size; ++determinerInd) {
               String determinerName = determiners[determinerInd];
               if (determinerName != null && determinerName.length() != 0 && !isParallelXAEnabled(jtaClusterMBean)) {
                  throw new IllegalArgumentException("determiner resource " + Arrays.toString(determiners) + " can not be configured if parallel-xa-enabled is false Cluster:" + clusterMBeans[i].getName());
               }
            }
         }

      }
   }

   public static void isDataSourceGlobal(String determinerName, DomainMBean domainMBean) {
      JTAPartitionService jtaPartitionService = JTAPartitionService.getOrCreateJTAPartitionService();
      String currentPartitionName = jtaPartitionService.getPartitionName(true);
      JDBCSystemResourceMBean[] resources = domainMBean.getJDBCSystemResources();

      for(int j = 0; j < resources.length; ++j) {
         JDBCSystemResourceMBean jdbcSystemResourceMBean = resources[j];
         String partitionName = jdbcSystemResourceMBean.getPartitionName();
         String dataSourceName = jdbcSystemResourceMBean.getName() + "_" + domainMBean.getName();
         if (determinerName.equals(dataSourceName)) {
            JDBCDataSourceBean dataSourceBean = jdbcSystemResourceMBean.getJDBCResource();
            if (dataSourceBean != null) {
               JDBCDataSourceParamsBean params = dataSourceBean.getJDBCDataSourceParams();
               if (params != null) {
                  String protocol = params.getGlobalTransactionsProtocol();
                  if (!"TwoPhaseCommit".equals(protocol)) {
                     throw new IllegalArgumentException("Determiner DataSource \"" + determinerName + "\" protocol " + protocol + ", must be " + "TwoPhaseCommit");
                  }

                  if (partitionName == null) {
                     return;
                  }

                  throw new IllegalArgumentException("Determiner DataSource \"" + determinerName + "\" must be global. determiner partition name(" + partitionName + ") on the running partition " + currentPartitionName);
               }
            }
         }
      }

   }

   public static void validateParallelXA(JTAMBean jtaMBean, boolean parallelXAEnabled) {
      if (!parallelXAEnabled) {
         String[] determiners = jtaMBean.getDeterminers();
         if (determiners != null) {
            if (determiners.length != 0) {
               throw new IllegalArgumentException("parallel-xa-enabled should be enabled if you use determiner resource " + Arrays.toString(determiners));
            } else if (jtaMBean instanceof JTAClusterMBean) {
               ClusterMBean clusterMBean = (ClusterMBean)jtaMBean.getParentBean();
               if (clusterMBean != null) {
                  DomainMBean domainMBean = (DomainMBean)clusterMBean.getParentBean();
                  if (domainMBean != null) {
                     JTAMBean aJtaMBean = domainMBean.getJTA();
                     if (aJtaMBean != null) {
                        String[] aDeterminers = aJtaMBean.getDeterminers();
                        if (aDeterminers != null) {
                           if (aDeterminers.length != 0) {
                              throw new IllegalArgumentException("parallel-xa-enabled should be enabled if you use JTA determiner resource " + Arrays.toString(aDeterminers));
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public static boolean isParallelXAEnabled(JTAMBean jtaMBean) {
      return jtaMBean.getParallelXAEnabled();
   }
}
