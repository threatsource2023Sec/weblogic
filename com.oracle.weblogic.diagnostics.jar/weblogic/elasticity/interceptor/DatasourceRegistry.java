package weblogic.elasticity.interceptor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.PatternSyntaxException;
import javax.enterprise.deploy.model.DDBeanRoot;
import javax.inject.Singleton;
import org.jvnet.hk2.annotations.Service;
import weblogic.deploy.api.model.WebLogicDDBeanRoot;
import weblogic.deploy.api.model.WebLogicDeployableObject;
import weblogic.deploy.api.tools.ManagedSessionHelper;
import weblogic.deploy.api.tools.SessionHelper;
import weblogic.descriptor.DescriptorBean;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.DatasourceInterceptorMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.InterceptorMBean;
import weblogic.management.configuration.JDBCSystemResourceMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.configuration.VirtualHostMBean;
import weblogic.management.configuration.VirtualTargetMBean;

@Service
@Singleton
public class DatasourceRegistry {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugDataSourceInterceptor");

   List findConstraints(DomainMBean domainMBean) {
      return this.gatherConstraints(domainMBean);
   }

   private List gatherConstraints(DomainMBean domainMBean) {
      InterceptorMBean[] interceptors = domainMBean.getInterceptors().getInterceptors();
      if (interceptors == null) {
         return null;
      } else {
         List constraints = null;
         InterceptorMBean[] var4 = interceptors;
         int var5 = interceptors.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            InterceptorMBean interceptor = var4[var6];
            if (interceptor instanceof DatasourceInterceptorMBean) {
               DatasourceInterceptorMBean dsIn = (DatasourceInterceptorMBean)interceptor;
               int quota = dsIn.getConnectionQuota();
               String urlPattern = dsIn.getConnectionUrlsPattern();
               if (urlPattern != null) {
                  try {
                     DatasourceConstraint c = new DatasourceConstraint(dsIn.getName(), urlPattern, quota);
                     if (constraints == null) {
                        constraints = new ArrayList();
                     }

                     constraints.add(c);
                  } catch (PatternSyntaxException var12) {
                     if (DEBUG.isDebugEnabled()) {
                        DEBUG.debug("Invalid pattern: " + urlPattern);
                     }
                  }
               }
            }
         }

         return constraints;
      }
   }

   public List findConfiguredDatasourceInfos(DomainMBean domainMBean) {
      return this.gatherDatasourceInfos(domainMBean, (String)null, true);
   }

   List findDatasourceInfos(DomainMBean domainMBean, String clusterName) {
      return this.gatherDatasourceInfos(domainMBean, clusterName, false);
   }

   private List gatherDatasourceInfos(DomainMBean domainMBean, String clusterName, boolean ignoreTargetChecks) {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Gathering datasource infos");
      }

      List infos = new ArrayList();
      this.gatherGlobalDatasourceSysresourceInfos(domainMBean, infos, ignoreTargetChecks);
      this.gatherGlobalAppScopedDatasourceInfos(domainMBean, infos, ignoreTargetChecks);
      this.gatherPartitionScopedInfos(domainMBean, infos, ignoreTargetChecks);
      return infos;
   }

   private void gatherPartitionScopedInfos(DomainMBean domainMBean, List infos, boolean ignoreTargetChecks) {
   }

   private void gatherInfosInResourceGroup(ResourceGroupMBean resourceGroup, List infos, boolean ignoreTargetChecks) {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Gathering datasource infos in resource group " + resourceGroup.getName());
      }

      TargetMBean[] targets = this.expandTargetsList(resourceGroup.getTargets());
      if (ignoreTargetChecks || targets != null && targets.length != 0) {
         this.gatherDatasourceSysresourceInfos(resourceGroup.getJDBCSystemResources(), infos, targets, ignoreTargetChecks);
         this.gatherAppScopedDatasourceInfos(resourceGroup.getAppDeployments(), infos, targets, ignoreTargetChecks);
      }
   }

   private void gatherGlobalDatasourceSysresourceInfos(DomainMBean domainMBean, List infos, boolean ignoreTargetChecks) {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Gathering global datasource system resource infos");
      }

      this.gatherDatasourceSysresourceInfos(domainMBean.getJDBCSystemResources(), infos, (TargetMBean[])null, ignoreTargetChecks);
   }

   private void gatherDatasourceSysresourceInfos(JDBCSystemResourceMBean[] jdbcResources, List infos, TargetMBean[] targets, boolean ignoreTargets) {
      if (jdbcResources != null && jdbcResources.length != 0) {
         JDBCSystemResourceMBean[] var5 = jdbcResources;
         int var6 = jdbcResources.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            JDBCSystemResourceMBean res = var5[var7];
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Gathering datasource info for system resource " + res.getName());
            }

            TargetMBean[] _targets = targets;
            if (targets == null) {
               _targets = this.expandTargetsList(res.getTargets());
            }

            String[] targetNames = this.getTargetNames(_targets);
            if (ignoreTargets || targetNames != null) {
               this.gatherDatasourceInfo(res.getJDBCResource(), targetNames, infos);
            }
         }

      }
   }

   private void gatherGlobalAppScopedDatasourceInfos(DomainMBean domainMBean, List infos, boolean ignoreTargetChecks) {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Gathering global app scoped datasource infos");
      }

      this.gatherAppScopedDatasourceInfos(domainMBean.getAppDeployments(), infos, (TargetMBean[])null, ignoreTargetChecks);
   }

   private void gatherAppScopedDatasourceInfos(AppDeploymentMBean[] appDeployments, List infos, TargetMBean[] targets, boolean ignoreTargetChecks) {
      if (!(appDeployments == null | appDeployments.length == 0)) {
         AppDeploymentMBean[] var5 = appDeployments;
         int var6 = appDeployments.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            AppDeploymentMBean app = var5[var7];
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Gathering datasource info for app " + app.getApplicationIdentifier());
            }

            List appInfos = this.getAppScopedDatasourceInfos(app, targets, ignoreTargetChecks);
            if (appInfos != null) {
               infos.addAll(appInfos);
            }
         }

      }
   }

   private List getAppScopedDatasourceInfos(AppDeploymentMBean app, TargetMBean[] targets, boolean ignoreTargetChecks) {
      List infos = null;
      if (targets == null) {
         targets = this.expandTargetsList(app.getTargets());
      }

      String[] targetNames = this.getTargetNames(targets);
      if (!ignoreTargetChecks && targetNames == null) {
         return null;
      } else {
         ManagedSessionHelper managedSessionHelper = null;

         try {
            managedSessionHelper = new ManagedSessionHelper(app);
            SessionHelper helper = managedSessionHelper.getHelper();
            WebLogicDeployableObject dObj = helper.getDeployableObject();
            DDBeanRoot[] beanRoots = dObj.getDDBeanRoots();
            if (beanRoots != null) {
               DDBeanRoot[] var10 = beanRoots;
               int var11 = beanRoots.length;

               for(int var12 = 0; var12 < var11; ++var12) {
                  DDBeanRoot root = var10[var12];
                  if (root instanceof WebLogicDDBeanRoot) {
                     DescriptorBean dBean = ((WebLogicDDBeanRoot)root).getDescriptorBean();
                     if (dBean instanceof JDBCDataSourceBean) {
                        if (infos == null) {
                           infos = new ArrayList();
                        }

                        this.gatherDatasourceInfo((JDBCDataSourceBean)dBean, targetNames, infos);
                     }
                  }
               }
            }
         } catch (Exception var18) {
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Exception handling application " + app.getName(), var18);
            }
         } finally {
            if (managedSessionHelper != null) {
               managedSessionHelper.close();
            }

         }

         return infos;
      }
   }

   private void gatherDatasourceInfo(JDBCDataSourceBean datasourceBean, String[] targetNames, List infos) {
      if (datasourceBean != null) {
         String name = datasourceBean.getName();
         int maxCapacity = datasourceBean.getJDBCConnectionPoolParams().getMaxCapacity();
         String url = datasourceBean.getJDBCDriverParams().getUrl();
         if (url != null) {
            DatasourceInfo info = new DatasourceInfo(name, url, maxCapacity, targetNames);
            infos.add(info);
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Found datasource: " + info);
            }
         }

      }
   }

   private String[] getTargetNames(TargetMBean[] targets) {
      if (targets != null && targets.length != 0) {
         String[] targetNames = new String[targets.length];

         for(int i = 0; i < targets.length; ++i) {
            targetNames[i] = targets[i].getName();
         }

         return targetNames;
      } else {
         return null;
      }
   }

   private TargetMBean[] expandTargetsList(TargetMBean[] targets) {
      if (targets == null) {
         return null;
      } else {
         Set out = new HashSet();
         TargetMBean[] var3 = targets;
         int var4 = targets.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            TargetMBean t = var3[var5];
            TargetMBean[] var8;
            int var9;
            int var10;
            TargetMBean vtTarget;
            if (t instanceof VirtualHostMBean) {
               VirtualHostMBean vh = (VirtualHostMBean)t;
               if (vh.getTargets() != null) {
                  var8 = vh.getTargets();
                  var9 = var8.length;

                  for(var10 = 0; var10 < var9; ++var10) {
                     vtTarget = var8[var10];
                     out.add(vtTarget);
                  }
               }
            } else if (t instanceof VirtualTargetMBean) {
               VirtualTargetMBean vt = (VirtualTargetMBean)t;
               if (vt.getTargets() != null) {
                  var8 = vt.getTargets();
                  var9 = var8.length;

                  for(var10 = 0; var10 < var9; ++var10) {
                     vtTarget = var8[var10];
                     out.add(vtTarget);
                  }
               }
            } else {
               out.add(t);
            }
         }

         return (TargetMBean[])((TargetMBean[])out.toArray(new TargetMBean[0]));
      }
   }
}
