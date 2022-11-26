package weblogic.diagnostics.metrics;

import com.oracle.weblogic.diagnostics.expressions.AdminServer;
import com.oracle.weblogic.diagnostics.expressions.ExpressionBean;
import com.oracle.weblogic.diagnostics.expressions.ExpressionBeanRuntimeException;
import com.oracle.weblogic.diagnostics.expressions.TrackedValueSource;
import com.oracle.weblogic.diagnostics.expressions.WLDFI18n;
import com.oracle.weblogic.diagnostics.watch.MetricRuleType;
import com.oracle.weblogic.diagnostics.watch.beans.jmx.AbstractJMXMetricSource;
import com.oracle.weblogic.diagnostics.watch.beans.jmx.JMXTrackedItemCollection;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.inject.Singleton;
import javax.management.MBeanServer;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.QueryExp;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.i18n.DiagnosticsTextWatchTextFormatter;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean;
import weblogic.management.provider.DomainAccess;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.DomainRuntimeMBean;
import weblogic.management.runtime.ServerLifeCycleRuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.rjvm.PeerGoneException;
import weblogic.rmi.extensions.RemoteRuntimeException;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

@WLDFI18n(
   resourceBundle = "weblogic.diagnostics.metrics.MetricsSourcesBundle",
   value = "domain.runtime.desc",
   displayName = "domain.runtime.displayname"
)
@ExpressionBean(
   name = "domainRuntime",
   prefix = "wls"
)
@Service
@Singleton
@AdminServer
@MetricRuleType
public class WLSDomainRuntimeMetricSource extends AbstractJMXMetricSource {
   private static final DiagnosticsTextWatchTextFormatter txtFormatter = DiagnosticsTextWatchTextFormatter.getInstance();
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticsExpressionMetrics");
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private MBeanServer domainRuntimeMBeanServer;
   private DomainRuntimeMBean domainRuntime;
   private DomainMBean domainConfig;
   private DomainRuntimeServiceMBean domainRuntimeService;

   public WLSDomainRuntimeMetricSource() {
      RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(KERNEL_ID);
      if (runtimeAccess.isAdminServer()) {
         DomainAccess domainAccess = ManagementService.getDomainAccess(KERNEL_ID);
         this.domainRuntime = domainAccess.getDomainRuntime();
         this.domainRuntimeService = domainAccess.getDomainRuntimeService();
         this.domainConfig = runtimeAccess.getDomain();
      } else {
         throw new ExpressionBeanRuntimeException(txtFormatter.getCannotUseDomainBeanOnManagedServer());
      }
   }

   @WLDFI18n("domain.runtime.domainquery.desc")
   public Iterable query(@WLDFI18n(name = "targets",value = "targets.string.desc") String targets, @WLDFI18n(name = "onPattern",value = "onPattern.desc") String onPattern, @WLDFI18n(name = "expression",value = "expression.desc") String expression) {
      String[] targetsArray = targets.split(",");
      return this.queryTargets(targetsArray, onPattern, expression);
   }

   @WLDFI18n("domain.runtime.targetquery.desc")
   public Iterable queryTargets(@WLDFI18n(name = "targets",value = "targets.array.desc") String[] targets, @WLDFI18n(name = "onPattern",value = "onPattern.desc") String onPattern, @WLDFI18n(name = "expression",value = "expression.desc") String expression) {
      TrackedValueSource collector = this.findOrCreateDomainMetricsCollector(targets, onPattern, expression);
      return collector;
   }

   @WLDFI18n("domain.runtime.namedesc")
   public String getName() {
      return "domainRuntime";
   }

   @WLDFI18n("domain.runtime.domain.desc")
   public DomainRuntimeMBean getDomain() {
      return (DomainRuntimeMBean)BeantreeInterceptor.createProxy(this.domainRuntime, DomainRuntimeMBean.class);
   }

   @WLDFI18n("domain.runtime.serverRuntimes.desc")
   public ServerRuntimeMBean[] getServerRuntimes() {
      ServerRuntimeMBean[] serverRuntimes = this.domainRuntimeService.getServerRuntimes();
      return (ServerRuntimeMBean[])BeantreeInterceptor.createProxyArray(serverRuntimes, ServerRuntimeMBean.class);
   }

   @WLDFI18n("domain.runtime.lookupServerRuntime.desc")
   public ServerRuntimeMBean lookupServerRuntime(@WLDFI18n(value = "domain.runtime.lookupServerRuntime.serverName",name = "serverName") String serverName) {
      ServerRuntimeMBean proxy = null;
      ServerRuntimeMBean serverRuntime = this.domainRuntimeService.lookupServerRuntime(serverName);
      if (serverRuntime != null) {
         proxy = (ServerRuntimeMBean)BeantreeInterceptor.createProxy(serverRuntime, ServerRuntimeMBean.class);
      }

      return proxy;
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

   protected List getRunningServers() {
      List activeServers = new ArrayList();
      ServerLifeCycleRuntimeMBean[] lcRuntimes = this.domainRuntime.getServerLifeCycleRuntimes();
      ServerLifeCycleRuntimeMBean[] var3 = lcRuntimes;
      int var4 = lcRuntimes.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         ServerLifeCycleRuntimeMBean slcr = var3[var5];

         try {
            if (slcr.getStateVal() == 2) {
               activeServers.add(slcr.getName());
            }
         } catch (Throwable var8) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Caught unexpected exception getting state for server " + slcr.getName() + ", ignoring", var8);
            }
         }
      }

      return activeServers;
   }

   private TrackedValueSource findOrCreateDomainMetricsCollector(String[] targets, String onPattern, String expression) {
      String key = this.createMetricKey(targets, onPattern, expression);
      TrackedValueSource collector = this.findSpec(key);
      if (collector == null) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Creating new collector for " + key);
         }

         collector = new DomainMetricsCollectionImpl(targets, onPattern, expression, key);
         this.getLocator().inject(collector);
         this.getLocator().postConstruct(collector);
         this.addSpec((TrackedValueSource)collector);
      } else if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Found collector for key " + key);
      }

      return (TrackedValueSource)collector;
   }

   private String createMetricKey(String[] targets, String onPattern, String expression) {
      String targetKey = Arrays.toString(targets) + "//" + onPattern + "//" + expression;
      return targetKey;
   }

   private class DomainMetricsCollectionImpl extends JMXTrackedItemCollection {
      private final String[] targets;

      public DomainMetricsCollectionImpl(String[] targets, String onPattern, String attributePattern, String key) {
         super(WLSDomainRuntimeMetricSource.this, onPattern, attributePattern, key);
         this.targets = targets;
      }

      protected Set getMatchingInstances(String namePattern) {
         Set matchingInstances = new HashSet();
         List serversForTargets = this.getServersForTargets();
         Iterator var4 = serversForTargets.iterator();

         while(var4.hasNext()) {
            String server = (String)var4.next();
            String[] parts = namePattern.split(":");
            if (parts.length < 2) {
               throw new IllegalArgumentException(namePattern);
            }

            String domain = parts[0];
            String propsList = parts[1];
            ObjectName objectName = null;

            try {
               propsList = "Location=" + server + "," + propsList;
               objectName = new ObjectName(domain + ":" + propsList);
               Set names = WLSDomainRuntimeMetricSource.this.lookupMBeanServerReference().queryNames(objectName, (QueryExp)null);
               matchingInstances.addAll(names);
            } catch (Throwable var11) {
               if (!this.isIgnorable(var11)) {
                  throw new ExpressionBeanRuntimeException(var11);
               }

               if (WLSDomainRuntimeMetricSource.debugLogger.isDebugEnabled()) {
                  WLSDomainRuntimeMetricSource.debugLogger.debug("Caught PeerGoneException on queryNames(" + objectName + ") for server " + server + ", ignoring", var11);
               }
            }
         }

         return matchingInstances;
      }

      protected boolean isCommunicationsException(Throwable t) {
         return t instanceof PeerGoneException || t instanceof RemoteRuntimeException || super.isCommunicationsException(t);
      }

      private List getServersForTargets() {
         List targetNames = Arrays.asList(this.targets);
         List foundTargets = new ArrayList();
         List activeServers = WLSDomainRuntimeMetricSource.this.getRunningServers();
         Map clustersMap = this.getClustersMap();
         Iterator var5 = targetNames.iterator();

         while(var5.hasNext()) {
            String target = (String)var5.next();
            if (activeServers.contains(target)) {
               foundTargets.add(target);
            } else if (clustersMap.get(target) != null) {
               Set clusterServers = (Set)clustersMap.get(target);
               foundTargets.addAll(clusterServers);
            }
         }

         return foundTargets;
      }

      private Map getClustersMap() {
         Map clusterMap = new HashMap();
         ClusterMBean[] clusterBeans = WLSDomainRuntimeMetricSource.this.domainConfig.getClusters();
         ClusterMBean[] var3 = clusterBeans;
         int var4 = clusterBeans.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            ClusterMBean cluster = var3[var5];
            clusterMap.put(cluster.getName(), cluster.getServerNames());
         }

         return clusterMap;
      }
   }
}
