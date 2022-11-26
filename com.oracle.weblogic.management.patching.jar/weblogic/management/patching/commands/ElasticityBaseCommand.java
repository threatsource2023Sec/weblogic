package weblogic.management.patching.commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import weblogic.diagnostics.descriptor.WLDFScaleDownActionBean;
import weblogic.diagnostics.descriptor.WLDFScaleUpActionBean;
import weblogic.diagnostics.descriptor.WLDFWatchNotificationBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.WLDFSystemResourceMBean;
import weblogic.management.patching.ElasticitySyncCounter;
import weblogic.management.patching.PatchingDebugLogger;
import weblogic.management.patching.TopologyInspector;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.management.runtime.WLDFSystemResourceControlRuntimeMBean;
import weblogic.management.workflow.command.AbstractCommand;
import weblogic.management.workflow.command.SharedState;

public abstract class ElasticityBaseCommand extends AbstractCommand {
   private static final long serialVersionUID = 3351525262376654053L;
   @SharedState
   public transient String clusterName;
   @SharedState
   public transient Map elasticityMBeanMap;

   public boolean enableElasticity() throws Exception {
      boolean result = true;
      ServerRuntimeMBean serverRuntimeMBean = TopologyInspector.getServerRuntimeMBean();
      synchronized(ElasticitySyncCounter.getInstance()) {
         ElasticitySyncCounter.getInstance().decrement(this.clusterName);
         if (ElasticitySyncCounter.getInstance().getCounterValue(this.clusterName) == 0 && this.elasticityMBeanMap.containsKey(this.clusterName)) {
            List mbeanNamesList = (List)this.elasticityMBeanMap.get(this.clusterName);
            Iterator var5 = mbeanNamesList.iterator();

            while(var5.hasNext()) {
               String mbeanName = (String)var5.next();
               WLDFSystemResourceControlRuntimeMBean controlRuntime = serverRuntimeMBean.getWLDFRuntime().getWLDFControlRuntime().lookupSystemResourceControl(mbeanName);
               if (!controlRuntime.isEnabled()) {
                  controlRuntime.setEnabled(true);
                  if (PatchingDebugLogger.isDebugEnabled()) {
                     PatchingDebugLogger.debug("Enabling elasticity for mbean with name" + mbeanName);
                  }
               } else if (PatchingDebugLogger.isDebugEnabled()) {
                  PatchingDebugLogger.debug("Elasticity already enabled for mbean with name" + mbeanName);
               }
            }
         }

         return result;
      }
   }

   public boolean disableElasticity() throws Exception {
      boolean result = true;
      ServerRuntimeMBean serverRuntimeMBean = TopologyInspector.getServerRuntimeMBean();
      ArrayList mbeanNamesList = new ArrayList();
      List wldfSystemResourceMBeanList = this.getWLDFSystemResourcesForCluster(this.clusterName);
      Iterator iterator = wldfSystemResourceMBeanList.iterator();
      synchronized(ElasticitySyncCounter.getInstance()) {
         boolean isElasticityEnabled = this.getElasticityStatus(this.clusterName);
         if (isElasticityEnabled) {
            ElasticitySyncCounter.getInstance().setCounterValue(1, this.clusterName);

            while(iterator.hasNext()) {
               WLDFSystemResourceMBean wldfSystemResourceMBean = (WLDFSystemResourceMBean)iterator.next();
               WLDFSystemResourceControlRuntimeMBean controlRuntime = serverRuntimeMBean.getWLDFRuntime().getWLDFControlRuntime().lookupSystemResourceControl(wldfSystemResourceMBean.getName());
               if (controlRuntime.isEnabled()) {
                  controlRuntime.setEnabled(false);
                  mbeanNamesList.add(wldfSystemResourceMBean.getName());
                  if (PatchingDebugLogger.isDebugEnabled()) {
                     PatchingDebugLogger.debug("Disabling elasticity for mbean with name" + wldfSystemResourceMBean.getName());
                  }
               } else if (PatchingDebugLogger.isDebugEnabled()) {
                  PatchingDebugLogger.debug("Elasticity already disabled for mbean with name" + wldfSystemResourceMBean.getName());
               }
            }

            this.elasticityMBeanMap.put(this.clusterName, mbeanNamesList);
         } else {
            ElasticitySyncCounter.getInstance().increment(this.clusterName);
         }

         return result;
      }
   }

   private List getWLDFSystemResourcesForCluster(String clusterName) {
      List wldfSystemResourceMBeansList = new ArrayList();
      DomainMBean domainMBean = TopologyInspector.getDomainMBean();
      WLDFSystemResourceMBean[] wldfSystemResourceMBeans = domainMBean.getWLDFSystemResources();
      WLDFSystemResourceMBean[] var5 = wldfSystemResourceMBeans;
      int var6 = wldfSystemResourceMBeans.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         WLDFSystemResourceMBean wldfSystemResourceMBean = var5[var7];
         WLDFWatchNotificationBean wldfWatchNotificationBean = wldfSystemResourceMBean.getWLDFResource().getWatchNotification();
         WLDFScaleUpActionBean[] wldfScaleUpActionBeans = wldfWatchNotificationBean.getScaleUpActions();
         WLDFScaleUpActionBean[] var11 = wldfScaleUpActionBeans;
         int var12 = wldfScaleUpActionBeans.length;

         int var13;
         for(var13 = 0; var13 < var12; ++var13) {
            WLDFScaleUpActionBean wldfScaleUpActionBean = var11[var13];
            if (wldfScaleUpActionBean.getClusterName().equals(clusterName)) {
               wldfSystemResourceMBeansList.add(wldfSystemResourceMBean);
            }
         }

         WLDFScaleDownActionBean[] wldfScaleDownActionBeans = wldfWatchNotificationBean.getScaleDownActions();
         WLDFScaleDownActionBean[] var17 = wldfScaleDownActionBeans;
         var13 = wldfScaleDownActionBeans.length;

         for(int var18 = 0; var18 < var13; ++var18) {
            WLDFScaleDownActionBean wldfScaleDownActionBean = var17[var18];
            if (wldfScaleDownActionBean.getClusterName().equals(clusterName)) {
               wldfSystemResourceMBeansList.add(wldfSystemResourceMBean);
            }
         }
      }

      return wldfSystemResourceMBeansList;
   }

   public boolean getElasticityStatus(String clusterName) {
      boolean enabled = true;
      ServerRuntimeMBean serverRuntimeMBean = TopologyInspector.getServerRuntimeMBean();
      List wldfSystemResourceMBeanList = this.getWLDFSystemResourcesForCluster(clusterName);
      Iterator iterator = wldfSystemResourceMBeanList.iterator();

      while(iterator.hasNext()) {
         WLDFSystemResourceMBean wldfSystemResourceMBean = (WLDFSystemResourceMBean)iterator.next();
         WLDFSystemResourceControlRuntimeMBean controlRuntime = serverRuntimeMBean.getWLDFRuntime().getWLDFControlRuntime().lookupSystemResourceControl(wldfSystemResourceMBean.getName());
         if (!controlRuntime.isEnabled()) {
            enabled = false;
            break;
         }
      }

      return enabled;
   }
}
