package weblogic.diagnostics.smartrule;

import com.oracle.weblogic.diagnostics.expressions.AdminServer;
import com.oracle.weblogic.diagnostics.expressions.Function;
import com.oracle.weblogic.diagnostics.expressions.FunctionProvider;
import com.oracle.weblogic.diagnostics.expressions.FunctionProviderRuntimeException;
import com.oracle.weblogic.diagnostics.expressions.WLDFI18n;
import com.oracle.weblogic.diagnostics.watch.MetricRuleType;
import java.text.ParseException;
import javax.inject.Singleton;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.l18n.DiagnosticsServicesTextTextFormatter;

@FunctionProvider(
   prefix = "wls",
   category = "Cluster"
)
@Singleton
@Service
@WLDFI18n(
   resourceBundle = "weblogic.diagnostics.smartrule.SmartRuleFunctionProvider"
)
@AdminServer
@MetricRuleType
public class SmartRuleFunctionProvider extends BaseSmartRuleFunctionProvider {
   private static final DiagnosticsServicesTextTextFormatter txtFormatter = DiagnosticsServicesTextTextFormatter.getInstance();
   public static final String PREFIX = "wls";

   private static boolean evaluateExpression(String clusterName, String instanceExpr, String attribute, String period, String duration, float attributeThreshold, float percentServersLimit, String oper) throws ParseException {
      String targetCluster = clusterName == null ? null : clusterName.trim();
      if (targetCluster != null && !targetCluster.isEmpty()) {
         StringBuffer buf = new StringBuffer();
         buf.append("wls.clusterRuntime.clusters.get(\"").append(clusterName).append("\").extract(\"").append(instanceExpr).append("\", \"").append(attribute).append("\", \"").append(period).append("\", \"").append(duration).append("\").tableAverages().percentMatch(x->x ").append(oper).append(" ").append(attributeThreshold).append(") >= ").append((double)percentServersLimit / 100.0);
         String expr = buf.toString();
         return evaluateExpression(expr);
      } else {
         throw new FunctionProviderRuntimeException(txtFormatter.getSmartRuleClusterNameEmptyOrNullText());
      }
   }

   @Function(
      smartRule = true
   )
   @WLDFI18n(
      value = "cluster.generic.desc",
      displayName = "cluster.generic.displayName",
      fullDescription = "cluster.generic.full"
   )
   public static boolean ClusterGenericMetricRule(@WLDFI18n(name = "clusterName",displayName = "params.clusterName.displayName",value = "params.clusterName") String clusterName, @WLDFI18n(name = "instancePattern",displayName = "generic.params.instance.displayName",value = "generic.params.instancePattern") String instancePattern, @WLDFI18n(name = "attribute",displayName = "generic.params.attribute.displayName",value = "generic.params.attribute") String attribute, @WLDFI18n(name = "operation",displayName = "generic.params.operation.displayName",value = "generic.params.operation",defaultValue = ">=") String operation, @WLDFI18n(name = "thresholdValue",displayName = "generic.params.thresholdValue.displayName",value = "generic.params.thresholdValue") float thresholdValue, @WLDFI18n(name = "percentServersLimit",displayName = "params.percentServersLimit.displayName",value = "params.percentServersLimit") float percentServersLimit, @WLDFI18n(name = "period",displayName = "params.period.displayName",value = "params.period",defaultValue = "30 seconds") String period, @WLDFI18n(name = "duration",displayName = "params.duration.displayName",value = "params.duration",defaultValue = "10 minutes") String duration) throws ParseException {
      validateOperation(operation);
      return evaluateExpression(clusterName, instancePattern, attribute, period, duration, thresholdValue, percentServersLimit, operation);
   }

   @Function(
      smartRule = true
   )
   @WLDFI18n(
      value = "cluster.lowHeapFree.desc",
      displayName = "cluster.lowheapFree.displayName",
      fullDescription = "cluster.lowHeapFree.full"
   )
   public static boolean ClusterLowHeapFreePercent(@WLDFI18n(name = "clusterName",displayName = "params.clusterName.displayName",value = "params.clusterName") String clusterName, @WLDFI18n(name = "period",displayName = "params.period.displayName",value = "params.period",defaultValue = "30 seconds") String period, @WLDFI18n(name = "duration",displayName = "params.duration.displayName",value = "params.duration",defaultValue = "10 minutes") String duration, @WLDFI18n(name = "percentFreeLimit",displayName = "percentFreeLimit.displayName",value = "percentFreeLimit.desc") float percentFreeLimit, @WLDFI18n(name = "percentServersLimit",displayName = "params.percentServersLimit.displayName",value = "params.percentServersLimit") float percentServersLimit) throws ParseException {
      return evaluateExpression(clusterName, "com.bea:Type=JVMRuntime,*", "HeapFreePercent", period, duration, percentFreeLimit, percentServersLimit, "<");
   }

   @Function(
      smartRule = true
   )
   @WLDFI18n(
      value = "cluster.highHeapFree.desc",
      displayName = "cluster.highheapFree.displayName",
      fullDescription = "cluster.highHeapFree.full"
   )
   public static boolean ClusterHighHeapFreePercent(@WLDFI18n(name = "clusterName",displayName = "params.clusterName.displayName",value = "params.clusterName") String clusterName, @WLDFI18n(name = "period",displayName = "params.period.displayName",value = "params.period",defaultValue = "30 seconds") String period, @WLDFI18n(name = "duration",displayName = "params.duration.displayName",value = "params.duration",defaultValue = "10 minutes") String duration, @WLDFI18n(name = "percentFreeLimit",displayName = "percentFreeLimit.displayName",value = "percentFreeLimit.desc") float percentFreeLimit, @WLDFI18n(name = "percentServersLimit",displayName = "params.percentServersLimit.displayName",value = "params.percentServersLimit") float percentServersLimit) throws ParseException {
      return evaluateExpression(clusterName, "com.bea:Type=JVMRuntime,*", "HeapFreePercent", period, duration, percentFreeLimit, percentServersLimit, ">=");
   }

   @Function(
      smartRule = true
   )
   @WLDFI18n(
      value = "cluster.lowSystemLoadAverage.desc",
      displayName = "cluster.lowSystemLoadAverage.displayName",
      fullDescription = "cluster.lowSystemLoadAverage.full"
   )
   public static boolean ClusterLowSystemLoadAverage(@WLDFI18n(name = "clusterName",displayName = "params.clusterName.displayName",value = "params.clusterName") String clusterName, @WLDFI18n(name = "period",displayName = "params.period.displayName",value = "params.period",defaultValue = "30 seconds") String period, @WLDFI18n(name = "duration",displayName = "params.duration.displayName",value = "params.duration",defaultValue = "10 minutes") String duration, @WLDFI18n(name = "loadLimit",displayName = "loadLimit.displayName",value = "loadLimit.desc") float loadLimit, @WLDFI18n(name = "percentServersLimit",displayName = "params.percentServersLimit.displayName",value = "params.percentServersLimit") float percentServersLimit) throws ParseException {
      return evaluateExpression(clusterName, "java.lang:type=OperatingSystem,*", "SystemLoadAverage", period, duration, loadLimit, percentServersLimit, "<");
   }

   @Function(
      smartRule = true
   )
   @WLDFI18n(
      value = "cluster.highSystemLoadAverage.desc",
      displayName = "cluster.highSystemLoadAverage.displayName",
      fullDescription = "cluster.highSystemLoadAverage.full"
   )
   public static boolean ClusterHighSystemLoadAverage(@WLDFI18n(name = "clusterName",displayName = "params.clusterName.displayName",value = "params.clusterName") String clusterName, @WLDFI18n(name = "period",displayName = "params.period.displayName",value = "params.period",defaultValue = "30 seconds") String period, @WLDFI18n(name = "duration",displayName = "params.duration.displayName",value = "params.duration",defaultValue = "10 minutes") String duration, @WLDFI18n(name = "loadLimit",displayName = "loadLimit.displayName",value = "loadLimit.desc") float loadLimit, @WLDFI18n(name = "percentServersLimit",displayName = "params.percentServersLimit.displayName",value = "params.percentServersLimit") float percentServersLimit) throws ParseException {
      return evaluateExpression(clusterName, "java.lang:type=OperatingSystem,*", "SystemLoadAverage", period, duration, loadLimit, percentServersLimit, ">=");
   }

   @Function(
      smartRule = true
   )
   @WLDFI18n(
      value = "cluster.highProcessCpuLoadAverage.desc",
      displayName = "cluster.highProcessCpuLoadAverage.displayName",
      fullDescription = "cluster.highProcessCpuLoadAverage.full"
   )
   public static boolean ClusterHighProcessCpuLoadAverage(@WLDFI18n(name = "clusterName",displayName = "params.clusterName.displayName",value = "params.clusterName") String clusterName, @WLDFI18n(name = "period",displayName = "params.period.displayName",value = "params.period",defaultValue = "30 seconds") String period, @WLDFI18n(name = "duration",displayName = "params.duration.displayName",value = "params.duration",defaultValue = "10 minutes") String duration, @WLDFI18n(name = "procCpuLoadLimit",displayName = "procCpuLoadLimit.displayName",value = "procCpuLoadLimit.desc") float procCpuLoadLimit, @WLDFI18n(name = "percentServersLimit",displayName = "params.percentServersLimit.displayName",value = "params.percentServersLimit") float percentServersLimit) throws ParseException {
      return evaluateExpression(clusterName, "java.lang:type=OperatingSystem,*", "ProcessCpuLoad", period, duration, procCpuLoadLimit, percentServersLimit, ">=");
   }

   @Function(
      smartRule = true
   )
   @WLDFI18n(
      value = "cluster.lowProcessCpuLoadAverage.desc",
      displayName = "cluster.lowProcessCpuLoadAverage.displayName",
      fullDescription = "cluster.lowProcessCpuLoadAverage.full"
   )
   public static boolean ClusterLowProcessCpuLoadAverage(@WLDFI18n(name = "clusterName",displayName = "params.clusterName.displayName",value = "params.clusterName") String clusterName, @WLDFI18n(name = "period",displayName = "params.period.displayName",value = "params.period",defaultValue = "30 seconds") String period, @WLDFI18n(name = "duration",displayName = "params.duration.displayName",value = "params.duration",defaultValue = "10 minutes") String duration, @WLDFI18n(name = "procCpuLoadLimit",displayName = "procCpuLoadLimit.displayName",value = "procCpuLoadLimit.desc") float procCpuLoadLimit, @WLDFI18n(name = "percentServersLimit",displayName = "params.percentServersLimit.displayName",value = "params.percentServersLimit") float percentServersLimit) throws ParseException {
      return evaluateExpression(clusterName, "java.lang:type=OperatingSystem,*", "ProcessCpuLoad", period, duration, procCpuLoadLimit, percentServersLimit, "<");
   }

   @Function(
      smartRule = true
   )
   @WLDFI18n(
      value = "cluster.highSystemCpuLoadAverage.desc",
      displayName = "cluster.highSystemCpuLoadAverage.displayName",
      fullDescription = "cluster.highSystemCpuLoadAverage.full"
   )
   public static boolean ClusterHighSystemCpuLoadAverage(@WLDFI18n(name = "clusterName",displayName = "params.clusterName.displayName",value = "params.clusterName") String clusterName, @WLDFI18n(name = "period",displayName = "params.period.displayName",value = "params.period",defaultValue = "30 seconds") String period, @WLDFI18n(name = "duration",displayName = "params.duration.displayName",value = "params.duration",defaultValue = "10 minutes") String duration, @WLDFI18n(name = "systemCpuLoadLimit",displayName = "sysCpuloadLimit.displayName",value = "sysCpuloadLimit.desc") float systemCpuLoadLimit, @WLDFI18n(name = "percentServersLimit",displayName = "params.percentServersLimit.displayName",value = "params.percentServersLimit") float percentServersLimit) throws ParseException {
      return evaluateExpression(clusterName, "java.lang:type=OperatingSystem,*", "SystemCpuLoad", period, duration, systemCpuLoadLimit, percentServersLimit, ">=");
   }

   @Function(
      smartRule = true
   )
   @WLDFI18n(
      value = "cluster.lowSystemCpuLoadAverage.desc",
      displayName = "cluster.lowSystemCpuLoadAverage.displayName",
      fullDescription = "cluster.lowSystemCpuLoadAverage.full"
   )
   public static boolean ClusterLowSystemCpuLoadAverage(@WLDFI18n(name = "clusterName",displayName = "params.clusterName.displayName",value = "params.clusterName") String clusterName, @WLDFI18n(name = "period",displayName = "params.period.displayName",value = "params.period",defaultValue = "30 seconds") String period, @WLDFI18n(name = "duration",displayName = "params.duration.displayName",value = "params.duration",defaultValue = "10 minutes") String duration, @WLDFI18n(name = "systemCpuLoadLimit",displayName = "sysCpuloadLimit.displayName",value = "sysCpuloadLimit.desc") float systemCpuLoadLimit, @WLDFI18n(name = "percentServersLimit",displayName = "params.percentServersLimit.displayName",value = "params.percentServersLimit") float percentServersLimit) throws ParseException {
      return evaluateExpression(clusterName, "java.lang:type=OperatingSystem,*", "SystemCpuLoad", period, duration, systemCpuLoadLimit, percentServersLimit, "<");
   }

   @Function(
      smartRule = true
   )
   @WLDFI18n(
      value = "cluster.highStuckThreads.desc",
      displayName = "cluster.highStuckThreads.displayName",
      fullDescription = "cluster.highStuckThreads.full"
   )
   public static boolean ClusterHighStuckThreads(@WLDFI18n(name = "clusterName",displayName = "params.clusterName.displayName",value = "params.clusterName") String clusterName, @WLDFI18n(name = "period",displayName = "params.period.displayName",value = "params.period",defaultValue = "30 seconds") String period, @WLDFI18n(name = "duration",displayName = "params.duration.displayName",value = "params.duration",defaultValue = "10 minutes") String duration, @WLDFI18n(name = "stuckThreadsLimit",displayName = "stuckThreadsLimit.displayName",value = "stuckThreadsLimit.desc") float stuckThreadsLimit, @WLDFI18n(name = "percentServersLimit",displayName = "params.percentServersLimit.displayName",value = "params.percentServersLimit") float percentServersLimit) throws ParseException {
      return evaluateExpression(clusterName, "com.bea:Type=ThreadPoolRuntime,*", "StuckThreadCount", period, duration, stuckThreadsLimit, percentServersLimit, ">=");
   }

   @Function(
      smartRule = true
   )
   @WLDFI18n(
      value = "cluster.highIdleThreads.desc",
      displayName = "cluster.highIdleThreads.displayName",
      fullDescription = "cluster.highIdleThreads.full"
   )
   public static boolean ClusterHighIdleThreads(@WLDFI18n(name = "clusterName",displayName = "params.clusterName.displayName",value = "params.clusterName") String clusterName, @WLDFI18n(name = "period",displayName = "params.period.displayName",value = "params.period",defaultValue = "30 seconds") String period, @WLDFI18n(name = "duration",displayName = "params.duration.displayName",value = "params.duration",defaultValue = "10 minutes") String duration, @WLDFI18n(name = "idleThreadsLimit",displayName = "idleThreadLimit.displayName",value = "idleThreadLimit.desc") float idleThreadsLimit, @WLDFI18n(name = "percentServersLimit",displayName = "params.percentServersLimit.displayName",value = "params.percentServersLimit") float percentServersLimit) throws ParseException {
      return evaluateExpression(clusterName, "com.bea:Type=ThreadPoolRuntime,*", "ExecuteThreadIdleCount", period, duration, idleThreadsLimit, percentServersLimit, ">=");
   }

   @Function(
      smartRule = true
   )
   @WLDFI18n(
      value = "cluster.lowIdleThreads.desc",
      displayName = "cluster.lowIdleThreads.displayName",
      fullDescription = "cluster.lowIdleThreads.full"
   )
   public static boolean ClusterLowIdleThreads(@WLDFI18n(name = "clusterName",displayName = "params.clusterName.displayName",value = "params.clusterName") String clusterName, @WLDFI18n(name = "period",displayName = "params.period.displayName",value = "params.period",defaultValue = "30 seconds") String period, @WLDFI18n(name = "duration",displayName = "params.duration.displayName",value = "params.duration",defaultValue = "10 minutes") String duration, @WLDFI18n(name = "idleThreadsLimit",displayName = "idleThreadLimit.displayName",value = "idleThreadLimit.desc") float idleThreadsLimit, @WLDFI18n(name = "percentServersLimit",displayName = "params.percentServersLimit.displayName",value = "params.percentServersLimit") float percentServersLimit) throws ParseException {
      return evaluateExpression(clusterName, "com.bea:Type=ThreadPoolRuntime,*", "ExecuteThreadIdleCount", period, duration, idleThreadsLimit, percentServersLimit, "<");
   }

   @Function(
      smartRule = true
   )
   @WLDFI18n(
      value = "cluster.highPendingUserRequests.desc",
      displayName = "cluster.highPendingUserRequests.displayName",
      fullDescription = "cluster.highPendingUserRequests.full"
   )
   public static boolean ClusterHighPendingUserRequests(@WLDFI18n(name = "clusterName",displayName = "params.clusterName.displayName",value = "params.clusterName") String clusterName, @WLDFI18n(name = "period",displayName = "params.period.displayName",value = "params.period",defaultValue = "30 seconds") String period, @WLDFI18n(name = "duration",displayName = "params.duration.displayName",value = "params.duration",defaultValue = "10 minutes") String duration, @WLDFI18n(name = "pendingRequestsLimit",displayName = "pendingRequestsLimit.displayName",value = "pendingRequestsLimit.desc") float pendingRequestsLimit, @WLDFI18n(name = "percentServersLimit",displayName = "params.percentServersLimit.displayName",value = "params.percentServersLimit") float percentServersLimit) throws ParseException {
      return evaluateExpression(clusterName, "com.bea:Type=ThreadPoolRuntime,*", "PendingUserRequestCount", period, duration, pendingRequestsLimit, percentServersLimit, ">=");
   }

   @Function(
      smartRule = true
   )
   @WLDFI18n(
      value = "cluster.lowPendingUserRequests.desc",
      displayName = "cluster.lowPendingUserRequests.displayName",
      fullDescription = "cluster.lowPendingUserRequests.full"
   )
   public static boolean ClusterLowPendingUserRequests(@WLDFI18n(name = "clusterName",displayName = "params.clusterName.displayName",value = "params.clusterName") String clusterName, @WLDFI18n(name = "period",displayName = "params.period.displayName",value = "params.period",defaultValue = "30 seconds") String period, @WLDFI18n(name = "duration",displayName = "params.duration.displayName",value = "params.duration",defaultValue = "10 minutes") String duration, @WLDFI18n(name = "pendingRequestsLimit",displayName = "pendingRequestsLimit.displayName",value = "pendingRequestsLimit.desc") float pendingRequestsLimit, @WLDFI18n(name = "percentServersLimit",displayName = "params.percentServersLimit.displayName",value = "params.percentServersLimit") float percentServersLimit) throws ParseException {
      return evaluateExpression(clusterName, "com.bea:Type=ThreadPoolRuntime,*", "PendingUserRequestCount", period, duration, pendingRequestsLimit, percentServersLimit, "<");
   }

   @Function(
      smartRule = true
   )
   @WLDFI18n(
      value = "cluster.highQueueLength.desc",
      displayName = "cluster.highQueueLength.displayName",
      fullDescription = "cluster.highQueueLength.full"
   )
   public static boolean ClusterHighQueueLength(@WLDFI18n(name = "clusterName",displayName = "params.clusterName.displayName",value = "params.clusterName") String clusterName, @WLDFI18n(name = "period",displayName = "params.period.displayName",value = "params.period",defaultValue = "30 seconds") String period, @WLDFI18n(name = "duration",displayName = "params.duration.displayName",value = "params.duration",defaultValue = "10 minutes") String duration, @WLDFI18n(name = "queueLengthLimit",displayName = "queueLengthLimit.displayName",value = "queueLengthLimit.desc") float queueLengthLimit, @WLDFI18n(name = "percentServersLimit",displayName = "params.percentServersLimit.displayName",value = "params.percentServersLimit") float percentServersLimit) throws ParseException {
      return evaluateExpression(clusterName, "com.bea:Type=ThreadPoolRuntime,*", "QueueLength", period, duration, queueLengthLimit, percentServersLimit, ">=");
   }

   @Function(
      smartRule = true
   )
   @WLDFI18n(
      value = "cluster.lowQueueLength.desc",
      displayName = "cluster.lowQueueLength.displayName",
      fullDescription = "cluster.lowQueueLength.full"
   )
   public static boolean ClusterLowQueueLength(@WLDFI18n(name = "clusterName",displayName = "params.clusterName.displayName",value = "params.clusterName") String clusterName, @WLDFI18n(name = "period",displayName = "params.period.displayName",value = "params.period",defaultValue = "30 seconds") String period, @WLDFI18n(name = "duration",displayName = "params.duration.displayName",value = "params.duration",defaultValue = "10 minutes") String duration, @WLDFI18n(name = "queueLengthLimit",displayName = "queueLengthLimit.displayName",value = "queueLengthLimit.desc") float queueLengthLimit, @WLDFI18n(name = "percentServersLimit",displayName = "params.percentServersLimit.displayName",value = "params.percentServersLimit") float percentServersLimit) throws ParseException {
      return evaluateExpression(clusterName, "com.bea:Type=ThreadPoolRuntime,*", "QueueLength", period, duration, queueLengthLimit, percentServersLimit, "<");
   }

   @Function(
      smartRule = true
   )
   @WLDFI18n(
      value = "cluster.highThroughput.desc",
      displayName = "cluster.highThroughput.displayName",
      fullDescription = "cluster.highThroughput.full"
   )
   public static boolean ClusterHighThroughput(@WLDFI18n(name = "clusterName",displayName = "params.clusterName.displayName",value = "params.clusterName") String clusterName, @WLDFI18n(name = "period",displayName = "params.period.displayName",value = "params.period",defaultValue = "30 seconds") String period, @WLDFI18n(name = "duration",displayName = "params.duration.displayName",value = "params.duration",defaultValue = "10 minutes") String duration, @WLDFI18n(name = "throughputLimit",displayName = "throughputLimit.displayName",value = "throughputLimit.desc") float throughputLimit, @WLDFI18n(name = "percentServersLimit",displayName = "params.percentServersLimit.displayName",value = "params.percentServersLimit") float percentServersLimit) throws ParseException {
      return evaluateExpression(clusterName, "com.bea:Type=ThreadPoolRuntime,*", "Throughput", period, duration, throughputLimit, percentServersLimit, ">=");
   }

   @Function(
      smartRule = true
   )
   @WLDFI18n(
      value = "cluster.lowThroughput.desc",
      displayName = "cluster.lowThroughput.displayName",
      fullDescription = "cluster.lowThroughput.full"
   )
   public static boolean ClusterLowThroughput(@WLDFI18n(name = "clusterName",displayName = "params.clusterName.displayName",value = "params.clusterName") String clusterName, @WLDFI18n(name = "period",displayName = "params.period.displayName",value = "params.period",defaultValue = "30 seconds") String period, @WLDFI18n(name = "duration",displayName = "params.duration.displayName",value = "params.duration",defaultValue = "10 minutes") String duration, @WLDFI18n(name = "throughputLimit",displayName = "throughputLimit.displayName",value = "throughputLimit.desc") float throughputLimit, @WLDFI18n(name = "percentServersLimit",displayName = "params.percentServersLimit.displayName",value = "params.percentServersLimit") float percentServersLimit) throws ParseException {
      return evaluateExpression(clusterName, "com.bea:Type=ThreadPoolRuntime,*", "Throughput", period, duration, throughputLimit, percentServersLimit, "<");
   }
}
