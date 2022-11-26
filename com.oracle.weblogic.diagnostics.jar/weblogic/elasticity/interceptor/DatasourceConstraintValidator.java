package weblogic.elasticity.interceptor;

import java.lang.annotation.Annotation;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.glassfish.hk2.api.ServiceLocator;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.provider.DomainAccess;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.ClusterRuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.management.workflow.command.AbstractCommand;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;

public class DatasourceConstraintValidator extends AbstractCommand {
   static final long serialVersionUID = 1234L;
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugDataSourceInterceptor");
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private String clusterName;
   private int scaleFactor;

   public DatasourceConstraintValidator() {
   }

   public DatasourceConstraintValidator(String clusterName, int scaleFactor) {
      this.clusterName = clusterName;
      this.scaleFactor = scaleFactor;
   }

   public boolean execute() throws Exception {
      if (this.scaleFactor > 0) {
         ServiceLocator serviceLocator = GlobalServiceLocator.getServiceLocator();
         DatasourceRegistry registry = (DatasourceRegistry)serviceLocator.getService(DatasourceRegistry.class, new Annotation[0]);
         RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
         DomainAccess domainAccess = ManagementService.getDomainAccess(kernelId);
         DomainMBean domainMBean = runtimeAccess.getDomain();
         List constraints = registry.findConstraints(domainMBean);
         if (constraints != null) {
            List infos = registry.findDatasourceInfos(domainMBean, this.clusterName);
            if (infos != null) {
               return this.checkConstraints(domainAccess, domainMBean, infos, constraints, this.clusterName, this.scaleFactor);
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private boolean checkConstraints(DomainAccess domainAccess, DomainMBean domainMBean, List infos, List constraints, String clusterName, int scaleFactor) {
      int[] output = new int[2];
      int errCnt = 0;
      Iterator var9 = constraints.iterator();

      while(var9.hasNext()) {
         DatasourceConstraint constraint = (DatasourceConstraint)var9.next();
         this.computePotentialConnections(domainAccess, domainMBean, infos, constraint, clusterName, scaleFactor, output);
         int total = output[0] + output[1];
         if (total > constraint.getQuota()) {
            ++errCnt;
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Constraint " + constraint + " violated: potentialConnections=" + total);
            }
         }
      }

      return errCnt == 0;
   }

   private void computePotentialConnections(DomainAccess domainAccess, DomainMBean domainMBean, List infos, DatasourceConstraint constraint, String clusterName, int scaleFactor, int[] output) {
      int potentialConnections = 0;
      int incrementalConnections = 0;
      Iterator var10 = infos.iterator();

      label57:
      while(true) {
         int maxCapacity;
         List targetList;
         do {
            DatasourceInfo info;
            String url;
            do {
               if (!var10.hasNext()) {
                  if (DEBUG.isDebugEnabled()) {
                     DEBUG.debug("checkConstraints: constraint=" + constraint + " potentialConnections=" + potentialConnections + " incrementalConnections=" + incrementalConnections);
                  }

                  output[0] = potentialConnections;
                  output[1] = incrementalConnections;
                  return;
               }

               info = (DatasourceInfo)var10.next();
               url = info.getUrl();
            } while(!constraint.matches(url));

            maxCapacity = info.getMaxCapacity();
            targetList = this.findTargets(info, domainMBean);
         } while(targetList == null);

         Iterator var15 = targetList.iterator();

         while(true) {
            while(true) {
               TargetMBean target;
               do {
                  if (!var15.hasNext()) {
                     continue label57;
                  }

                  target = (TargetMBean)var15.next();
                  if (target instanceof ServerMBean) {
                     potentialConnections += maxCapacity;
                  }
               } while(!(target instanceof ClusterMBean));

               ClusterMBean cluster = (ClusterMBean)target;
               if (cluster.getDynamicServers() != null && cluster.getDynamicServers().getServerTemplate() != null) {
                  potentialConnections += this.getAliveServerCount(domainAccess, cluster.getName()) * maxCapacity;
                  incrementalConnections += scaleFactor * maxCapacity;
               } else {
                  int serverCount = cluster.getServers() != null ? cluster.getServers().length : 0;
                  potentialConnections += serverCount * maxCapacity;
               }
            }
         }
      }
   }

   private List findTargets(DatasourceInfo info, DomainMBean domainMBean) {
      List targetList = null;
      if (info.getTargets() == null) {
         return targetList;
      } else {
         String[] var4 = info.getTargets();
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String targetName = var4[var6];
            TargetMBean target = domainMBean.lookupTarget(targetName);
            if (target != null) {
               if (targetList == null) {
                  targetList = new ArrayList();
               }

               targetList.add(target);
            }
         }

         return targetList;
      }
   }

   private int getAliveServerCount(DomainAccess domainAccess, String clusterName) {
      ServerRuntimeMBean[] srmbs = domainAccess.getDomainRuntimeService().getServerRuntimes();
      if (srmbs != null) {
         ServerRuntimeMBean[] var4 = srmbs;
         int var5 = srmbs.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            ServerRuntimeMBean server = var4[var6];
            ClusterRuntimeMBean cluster = server.getClusterRuntime();
            if (cluster != null && clusterName.equals(cluster.getName())) {
               return cluster.getAliveServerCount();
            }
         }
      }

      return 0;
   }
}
