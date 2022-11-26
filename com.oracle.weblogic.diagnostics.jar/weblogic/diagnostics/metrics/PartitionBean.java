package weblogic.diagnostics.metrics;

import com.oracle.weblogic.diagnostics.expressions.ExpressionBean;
import com.oracle.weblogic.diagnostics.expressions.ExpressionBeanRuntimeException;
import com.oracle.weblogic.diagnostics.expressions.Partition;
import com.oracle.weblogic.diagnostics.expressions.WLDFI18n;
import com.oracle.weblogic.diagnostics.watch.MetricRuleType;
import com.oracle.weblogic.diagnostics.watch.beans.jmx.AbstractJMXMetricSource;
import com.oracle.weblogic.diagnostics.watch.beans.jmx.JMXExpressionBeanRuntimeException;
import java.security.AccessController;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import javax.management.MBeanServerConnection;
import org.glassfish.hk2.api.PerLookup;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

@WLDFI18n(
   value = "partition.bean.short",
   fullDescription = "partition.bean.full",
   displayName = "partition.bean.displayName",
   resourceBundle = "weblogic.diagnostics.metrics.MetricsSourcesBundle"
)
@Partition
@MetricRuleType
@ExpressionBean(
   name = "partition",
   prefix = "wls"
)
@Service
@PerLookup
public class PartitionBean extends AbstractJMXMetricSource {
   public static final String PARTITION_BEAN_NAME = "partition";
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticsExpressionMetrics");
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private ComponentInvocationContextManager cicManager;
   private String name;
   private String id;
   private PartitionRuntimeMBean runtime;
   private PartitionMBean config;

   public PartitionBean(String partitionName) {
      this.name = partitionName;
   }

   public PartitionBean() {
   }

   @WLDFI18n("partition.name")
   public String getName() {
      if (this.name == null) {
         this.name = this.getCICManager().getCurrentComponentInvocationContext().getPartitionName();
      }

      return this.name;
   }

   @WLDFI18n("partition.id")
   public String getID() {
      if (this.id == null) {
         RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(KERNEL_ID);
         PartitionMBean partition = runtimeAccess.getDomain().lookupPartition(this.getName());
         if (partition != null) {
            this.id = partition.getPartitionID();
         } else {
            this.id = "0";
         }
      }

      return this.id;
   }

   @WLDFI18n(
      value = "partition.runtime.short",
      fullDescription = "partition.runtime.full"
   )
   public PartitionRuntimeMBean getRuntime() {
      if (this.runtime == null) {
         RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(KERNEL_ID);
         PartitionRuntimeMBean actualPartitionRuntime = runtimeAccess.getServerRuntime().lookupPartitionRuntime(this.getName());
         this.runtime = (PartitionRuntimeMBean)BeantreeInterceptor.createProxy(actualPartitionRuntime, PartitionRuntimeMBean.class);
      }

      return this.runtime;
   }

   @WLDFI18n(
      value = "partition.config.short",
      fullDescription = "partition.config.full"
   )
   public PartitionMBean getConfig() {
      if (this.config == null) {
         RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(KERNEL_ID);
         PartitionMBean actualPartitionConfig = runtimeAccess.getDomain().lookupPartition(this.getName());
         this.config = (PartitionMBean)BeantreeInterceptor.createProxy(actualPartitionConfig, PartitionMBean.class);
      }

      return this.config;
   }

   @WLDFI18n("partition.query.short")
   public Iterable query(@WLDFI18n(name = "onPattern",value = "partition.query.onPattern") final String onPattern, @WLDFI18n(name = "attributePattern",value = "partition.query.attributePattern") final String attributePattern) throws JMXExpressionBeanRuntimeException {
      String partitionName = this.getName();
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Running partition query for partition " + partitionName + ": name pattern=" + onPattern + ", attrExp=" + attributePattern);
      }

      this.getCICManager();
      Iterable result = null;
      ComponentInvocationContext cic = this.cicManager.createComponentInvocationContext(partitionName);

      try {
         result = (Iterable)ComponentInvocationContextManager.runAs(KERNEL_ID, cic, new Callable() {
            public Iterable call() throws Exception {
               return PartitionBean.super.query(onPattern, attributePattern);
            }
         });
         return result;
      } catch (ExecutionException var7) {
         throw new ExpressionBeanRuntimeException(var7);
      }
   }

   protected MBeanServerConnection lookupMBeanServerReference() {
      return ManagementService.getRuntimeMBeanServer(KERNEL_ID);
   }

   private ComponentInvocationContextManager getCICManager() {
      if (this.cicManager == null) {
         this.cicManager = ComponentInvocationContextManager.getInstance(KERNEL_ID);
      }

      return this.cicManager;
   }
}
