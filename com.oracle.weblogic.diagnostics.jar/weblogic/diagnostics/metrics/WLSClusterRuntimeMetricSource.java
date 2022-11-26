package weblogic.diagnostics.metrics;

import com.oracle.weblogic.diagnostics.expressions.AdminServer;
import com.oracle.weblogic.diagnostics.expressions.DiagnosticsFunctionProvider;
import com.oracle.weblogic.diagnostics.expressions.ExpressionBean;
import com.oracle.weblogic.diagnostics.expressions.ExpressionBeanRuntimeException;
import com.oracle.weblogic.diagnostics.expressions.TrackedValueSource;
import com.oracle.weblogic.diagnostics.expressions.WLDFI18n;
import com.oracle.weblogic.diagnostics.watch.MetricRuleType;
import com.oracle.weblogic.diagnostics.watch.beans.jmx.AbstractJMXMetricSource;
import java.lang.ref.WeakReference;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.management.MBeanServer;
import javax.management.MBeanServerConnection;
import org.jvnet.hk2.annotations.Service;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.descriptor.DescriptorBean;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.l18n.DiagnosticsServicesTextTextFormatter;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

@WLDFI18n(
   resourceBundle = "weblogic.diagnostics.metrics.MetricsSourcesBundle",
   value = "cluster.runtime.desc",
   displayName = "cluster.runtime.displayname"
)
@ExpressionBean(
   name = "clusterRuntime",
   prefix = "wls"
)
@Service
@Singleton
@AdminServer
@MetricRuleType
public class WLSClusterRuntimeMetricSource extends AbstractJMXMetricSource implements BeanUpdateListener {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final DiagnosticsServicesTextTextFormatter txtFormatter = DiagnosticsServicesTextTextFormatter.getInstance();
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticsExpressionMetrics");
   private static final Collection EMPTY_COLLECTION = new ArrayList();
   private final RuntimeAccess runtimeAccess;
   private MBeanServer domainRuntimeMBeanServer;
   private ClusterWrapperMap clustersMap = null;
   @Inject
   private WLSDomainRuntimeMetricSource domainRuntimeSource;
   @Inject
   private DiagnosticsFunctionProvider diagFunctionProvider;

   public WLSClusterRuntimeMetricSource() {
      this.runtimeAccess = ManagementService.getRuntimeAccess(KERNEL_ID);
      DomainMBean domain = this.runtimeAccess.getDomain();
      domain.addBeanUpdateListener(this);
   }

   protected MBeanServerConnection lookupMBeanServerReference() {
      if (this.domainRuntimeMBeanServer == null) {
         RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(KERNEL_ID);
         if (runtimeAccess.isAdminServer()) {
            this.domainRuntimeMBeanServer = ManagementService.getDomainRuntimeMBeanServer(KERNEL_ID);
         }
      }

      return this.domainRuntimeMBeanServer;
   }

   @WLDFI18n("cluster.runtime.namedesc")
   public String getName() {
      return "clusterRuntime";
   }

   @WLDFI18n("clusterRuntime.getClusters.desc")
   public synchronized Map getClusters() {
      if (this.clustersMap == null) {
         ClusterWrapperMap map = new ClusterWrapperMap();
         DomainMBean domainMBean = this.runtimeAccess.getDomain();
         ClusterMBean[] var3 = domainMBean.getClusters();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            ClusterMBean clusterMBean = var3[var5];
            String clusterName = clusterMBean.getName();
            ClusterWrapper wrapper = new ClusterWrapper(clusterMBean);
            map.put(clusterName, wrapper);
         }

         this.clustersMap = map;
      }

      return this.clustersMap;
   }

   public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
   }

   public void activateUpdate(BeanUpdateEvent event) throws BeanUpdateFailedException {
      DescriptorBean sourceBean = event.getSourceBean();
      if (sourceBean instanceof DomainMBean) {
         DomainMBean domainMBean = (DomainMBean)sourceBean;
         ClusterMBean[] clusters = domainMBean.getClusters();
         this.updateClustersMap(clusters);
      }

   }

   private synchronized void updateClustersMap(ClusterMBean[] clusters) {
      if (this.clustersMap != null) {
         ClusterMBean[] var2 = clusters;
         int var3 = clusters.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            ClusterMBean c = var2[var4];
            String clusterName = c.getName();
            if (!this.clustersMap.containsKey(clusterName)) {
               ClusterWrapper wrapper = new ClusterWrapper(c);
               this.clustersMap.put(clusterName, wrapper);
            }
         }

         List removeList = new ArrayList();
         Iterator var11 = this.clustersMap.keySet().iterator();

         String clusterName;
         while(var11.hasNext()) {
            clusterName = (String)var11.next();
            boolean found = false;
            ClusterMBean[] var14 = clusters;
            int var15 = clusters.length;

            for(int var8 = 0; var8 < var15; ++var8) {
               ClusterMBean c = var14[var8];
               if (clusterName.equals(c.getName())) {
                  found = true;
                  break;
               }
            }

            if (!found) {
               removeList.add(clusterName);
            }
         }

         var11 = removeList.iterator();

         while(var11.hasNext()) {
            clusterName = (String)var11.next();
            this.clustersMap.remove(clusterName);
         }
      }

   }

   public void rollbackUpdate(BeanUpdateEvent event) {
   }

   public class ClusterWrapper {
      private final WeakReference ref;

      ClusterWrapper(ClusterMBean clusterMBean) {
         this.ref = new WeakReference(clusterMBean);
      }

      private ClusterMBean getClusterMBean() {
         return (ClusterMBean)this.ref.get();
      }

      public Iterable extract(String expression, String attribute, String schedule, String duration) {
         ClusterMBean clusterMBean = this.getClusterMBean();
         if (clusterMBean == null) {
            return WLSClusterRuntimeMetricSource.EMPTY_COLLECTION;
         } else {
            String[] targets = new String[]{clusterMBean.getName()};
            TrackedValueSource spec = (TrackedValueSource)WLSClusterRuntimeMetricSource.this.domainRuntimeSource.queryTargets(targets, expression, attribute);
            Iterable it = DiagnosticsFunctionProvider.extract(spec, schedule, duration);
            if (WLSClusterRuntimeMetricSource.debugLogger.isDebugEnabled()) {
               WLSClusterRuntimeMetricSource.debugLogger.debug("extract: it=" + it);
            }

            return it;
         }
      }

      public String toString() {
         ClusterMBean clusterMBean = this.getClusterMBean();
         if (clusterMBean != null) {
            StringBuffer buf = new StringBuffer();
            buf.append(clusterMBean.getName()).append(":").append(clusterMBean.getServerNames());
            return buf.toString();
         } else {
            return "?Removed?";
         }
      }
   }

   public static class ClusterWrapperMap extends HashMap {
      public ClusterWrapper get(Object key) {
         ClusterWrapper value = (ClusterWrapper)super.get(key);
         if (value == null) {
            throw new ExpressionBeanRuntimeException(WLSClusterRuntimeMetricSource.txtFormatter.getClusterBeanNotFoundText(key != null ? key.toString() : ""));
         } else {
            return value;
         }
      }
   }
}
