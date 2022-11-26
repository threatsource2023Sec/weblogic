package weblogic.application.utils;

import weblogic.application.ApplicationContextInternal;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.JMSServerMBean;
import weblogic.management.configuration.SAFAgentMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.SubDeploymentMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.configuration.VirtualHostMBean;
import weblogic.management.configuration.VirtualTargetMBean;

public final class TargetUtils {
   public static TargetMBean findLocalServerTarget(TargetMBean[] targets) {
      if (targets == null) {
         return null;
      } else {
         ServerMBean server = ManagementUtils.getServerMBean();
         return findLocalTarget(targets, server);
      }
   }

   public static TargetMBean findLocalTarget(TargetMBean[] targets, ServerMBean server) {
      if (targets == null) {
         return null;
      } else {
         String serverName = server.getName();

         for(int i = 0; i < targets.length; ++i) {
            if (targets[i] instanceof ClusterMBean) {
               if (server.getCluster() != null && server.getCluster().getName().equals(targets[i].getName())) {
                  return targets[i];
               }
            } else if (targets[i] instanceof VirtualHostMBean) {
               VirtualHostMBean vh = (VirtualHostMBean)targets[i];
               if (isDeployedLocally(vh.getTargets())) {
                  return vh;
               }
            } else if (targets[i] instanceof VirtualTargetMBean) {
               VirtualTargetMBean vt = (VirtualTargetMBean)targets[i];
               if (isDeployedLocally(vt.getTargets())) {
                  return vt;
               }
            } else if (targets[i] instanceof JMSServerMBean) {
               JMSServerMBean jms = (JMSServerMBean)targets[i];
               if (isDeployedLocally(jms.getTargets())) {
                  return jms;
               }
            } else {
               if (targets[i] instanceof SAFAgentMBean) {
                  SAFAgentMBean safAgent = (SAFAgentMBean)targets[i];
                  return findLocalTarget(safAgent.getTargets(), server);
               }

               if (targets[i].getName().equals(serverName)) {
                  return targets[i];
               }
            }
         }

         return null;
      }
   }

   public static boolean isDeployedLocally(TargetMBean[] targets) {
      if (!ManagementUtils.isRuntimeAccessAvailable()) {
         return true;
      } else {
         ServerMBean server = ManagementUtils.getServerMBean();
         return findLocalTarget(targets, server) != null;
      }
   }

   private static SubDeploymentMBean findSubDeployment(SubDeploymentMBean[] sub, ApplicationContextInternal appCtx, String uri) {
      if (sub == null) {
         return null;
      } else {
         for(int i = 0; i < sub.length; ++i) {
            String moduleId = appCtx != null ? EarUtils.toModuleId(appCtx, sub[i].getName()) : sub[i].getName();
            if (uri.equals(moduleId)) {
               return sub[i];
            }
         }

         return null;
      }
   }

   public static TargetMBean[] findModuleTargets(BasicDeploymentMBean proposed, BasicDeploymentMBean current, String moduleId) {
      return findModuleTargets(proposed, current, (ApplicationContextInternal)null, moduleId);
   }

   public static TargetMBean[] findModuleTargets(BasicDeploymentMBean proposed, BasicDeploymentMBean current, ApplicationContextInternal appCtx, String moduleId) {
      BasicDeploymentMBean mbean = proposed != null ? proposed : current;
      SubDeploymentMBean sub = findSubDeployment(mbean.getSubDeployments(), appCtx, moduleId);
      return sub != null && sub.getTargets() != null && sub.getTargets().length != 0 ? sub.getTargets() : mbean.getTargets();
   }

   public static boolean isModuleDeployedHere(BasicDeploymentMBean mbean, String uri) {
      SubDeploymentMBean sub = findSubDeployment(mbean.getSubDeployments(), (ApplicationContextInternal)null, uri);
      return sub != null && sub.getTargets() != null && sub.getTargets().length != 0 ? isDeployedLocally(sub.getTargets()) : isDeployedLocally(mbean.getTargets());
   }

   public static TargetMBean findLocalTarget(BasicDeploymentMBean mbean, ServerMBean server) {
      TargetMBean[] ts = mbean.getTargets();
      TargetMBean t = null;
      if (ts != null) {
         t = findLocalTarget(ts, server);
      }

      if (t == null) {
         SubDeploymentMBean[] sub = mbean.getSubDeployments();
         if (t != null) {
            for(int i = 0; i < sub.length; ++i) {
               SubDeploymentMBean bean = sub[i];
               t = findLocalTarget(bean.getTargets(), server);
               if (t != null) {
                  break;
               }
            }
         }
      }

      if (t == null) {
         t = server;
      }

      return (TargetMBean)t;
   }
}
