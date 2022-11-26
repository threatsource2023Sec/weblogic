package weblogic.diagnostics.smartrule;

import com.oracle.weblogic.diagnostics.expressions.AdminServer;
import com.oracle.weblogic.diagnostics.expressions.Function;
import com.oracle.weblogic.diagnostics.expressions.FunctionProvider;
import com.oracle.weblogic.diagnostics.expressions.ManagedServer;
import com.oracle.weblogic.diagnostics.expressions.WLDFI18n;
import com.oracle.weblogic.diagnostics.watch.MetricRuleType;
import java.text.ParseException;
import javax.inject.Singleton;
import org.jvnet.hk2.annotations.Service;

@FunctionProvider(
   prefix = "wls",
   category = "Server"
)
@Singleton
@Service
@WLDFI18n(
   resourceBundle = "weblogic.diagnostics.smartrule.SmartRuleFunctionProvider"
)
@AdminServer
@ManagedServer
@MetricRuleType
public class ServerSmartRuleFunctionProvider extends BaseSmartRuleFunctionProvider {
   public static final String PREFIX = "wls";

   private static boolean evaluateExpression(String attribute, String period, String duration, String oper, float attributeThreshold) throws ParseException {
      StringBuffer buf = new StringBuffer();
      buf.append("wls:extract(\"").append(attribute).append("\", \"").append(period).append("\", \"").append(duration).append("\").tableAverages().stream().anyMatch(x->x ").append(oper).append(" ").append(attributeThreshold).append(")");
      String expr = buf.toString();
      return evaluateExpression(expr);
   }

   private static boolean evaluateExpression(String instancePattern, String attribute, String period, String duration, String oper, float attributeThreshold) throws ParseException {
      StringBuffer buf = new StringBuffer();
      buf.append("wls:extract(wls.runtime.query(\"").append(instancePattern).append("\", \"").append(attribute).append("\"), \"").append(period).append("\", \"").append(duration).append("\").tableAverages().stream().anyMatch(x->x ").append(oper).append(" ").append(attributeThreshold).append(")");
      String expr = buf.toString();
      return evaluateExpression(expr);
   }

   @Function(
      smartRule = true
   )
   @WLDFI18n(
      value = "server.generic.desc",
      displayName = "server.generic.displayName",
      fullDescription = "server.generic.full"
   )
   public static boolean ServerGenericMetricRule(@WLDFI18n(name = "instancePattern",displayName = "generic.params.instance.displayName",value = "generic.params.instancePattern") String instancePattern, @WLDFI18n(name = "attribute",displayName = "generic.params.attribute.displayName",value = "generic.params.attribute") String attribute, @WLDFI18n(name = "operation",displayName = "generic.params.operation.displayName",value = "generic.params.operation",defaultValue = ">=") String operation, @WLDFI18n(name = "thresholdValue",displayName = "generic.params.thresholdValue.displayName",value = "generic.params.thresholdValue") float thresholdValue, @WLDFI18n(name = "period",displayName = "params.period.displayName",value = "params.period",defaultValue = "30 seconds") String period, @WLDFI18n(name = "duration",displayName = "params.duration.displayName",value = "params.duration",defaultValue = "10 minutes") String duration) throws ParseException {
      validateOperation(operation);
      return evaluateExpression(instancePattern, attribute, period, duration, operation, thresholdValue);
   }

   @Function(
      smartRule = true
   )
   @WLDFI18n(
      value = "server.lowProcessCpuLoadAverage.desc",
      displayName = "server.lowProcessCpuLoadAverage.displayName",
      fullDescription = "server.lowProcessCpuLoadAverage.full"
   )
   public static boolean ServerLowProcessCpuLoadAverage(@WLDFI18n(name = "period",displayName = "params.period.displayName",value = "params.period",defaultValue = "30 seconds") String period, @WLDFI18n(name = "duration",displayName = "params.duration.displayName",value = "params.duration",defaultValue = "10 minutes") String duration, @WLDFI18n(name = "processCpuLoadLimit",displayName = "procCpuLoadLimit.displayName",value = "procCpuLoadLimit.desc") float processCpuLoadLimit) throws ParseException {
      return evaluateExpression("wls.platform.query('java.lang:type=OperatingSystem,*', 'ProcessCpuLoad')", period, duration, "<", processCpuLoadLimit);
   }

   @Function(
      smartRule = true
   )
   @WLDFI18n(
      value = "server.highProcessCpuLoadAverage.desc",
      displayName = "server.highProcessCpuLoadAverage.displayName",
      fullDescription = "server.highProcessCpuLoadAverage.full"
   )
   public static boolean ServerHighProcessCpuLoadAverage(@WLDFI18n(name = "period",displayName = "params.period.displayName",value = "params.period",defaultValue = "30 seconds") String period, @WLDFI18n(name = "duration",displayName = "params.duration.displayName",value = "params.duration",defaultValue = "10 minutes") String duration, @WLDFI18n(name = "processCpuLoadLimit",displayName = "procCpuLoadLimit.displayName",value = "procCpuLoadLimit.desc") float processCpuLoadLimit) throws ParseException {
      return evaluateExpression("wls.platform.query('java.lang:type=OperatingSystem,*', 'ProcessCpuLoad')", period, duration, ">=", processCpuLoadLimit);
   }

   @Function(
      smartRule = true
   )
   @WLDFI18n(
      value = "server.lowSystemCpuLoadAverage.desc",
      displayName = "server.lowSystemCpuLoadAverage.displayName",
      fullDescription = "server.lowSystemCpuLoadAverage.full"
   )
   public static boolean ServerLowSystemCpuLoadAverage(@WLDFI18n(name = "period",displayName = "params.period.displayName",value = "params.period",defaultValue = "30 seconds") String period, @WLDFI18n(name = "duration",displayName = "params.duration.displayName",value = "params.duration",defaultValue = "10 minutes") String duration, @WLDFI18n(name = "systemCpuLoadLimit",displayName = "sysCpuloadLimit.displayName",value = "sysCpuloadLimit.desc") float systemCpuLoadLimit) throws ParseException {
      return evaluateExpression("wls.platform.query('java.lang:type=OperatingSystem,*', 'SystemCpuLoad')", period, duration, "<", systemCpuLoadLimit);
   }

   @Function(
      smartRule = true
   )
   @WLDFI18n(
      value = "server.highSystemCpuLoadAverage.desc",
      displayName = "server.highSystemCpuLoadAverage.displayName",
      fullDescription = "server.highSystemCpuLoadAverage.full"
   )
   public static boolean ServerHighSystemCpuLoadAverage(@WLDFI18n(name = "period",displayName = "params.period.displayName",value = "params.period",defaultValue = "30 seconds") String period, @WLDFI18n(name = "duration",displayName = "params.duration.displayName",value = "params.duration",defaultValue = "10 minutes") String duration, @WLDFI18n(name = "systemCpuLoadLimit",displayName = "sysCpuloadLimit.displayName",value = "sysCpuloadLimit.desc") float systemCpuLoadLimit) throws ParseException {
      return evaluateExpression("wls.platform.query('java.lang:type=OperatingSystem,*', 'SystemCpuLoad')", period, duration, ">=", systemCpuLoadLimit);
   }

   @Function(
      smartRule = true
   )
   @WLDFI18n(
      value = "server.lowHeapFree.desc",
      displayName = "server.lowheapFree.displayName",
      fullDescription = "server.lowHeapFree.full"
   )
   public static boolean ServerLowHeapFreePercent(@WLDFI18n(name = "period",displayName = "params.period.displayName",value = "params.period",defaultValue = "30 seconds") String period, @WLDFI18n(name = "duration",displayName = "params.duration.displayName",value = "params.duration",defaultValue = "10 minutes") String duration, @WLDFI18n(name = "percentFreeLimit",displayName = "percentFreeLimit.displayName",value = "percentFreeLimit.desc") float percentFreeLimit) throws ParseException {
      return evaluateExpression("wls.runtime.serverRuntime.JVMRuntime.heapFreePercent", period, duration, "<", percentFreeLimit);
   }

   @Function(
      smartRule = true
   )
   @WLDFI18n(
      value = "server.lowSystemLoadAverage.desc",
      displayName = "server.lowSystemLoadAverage.displayName",
      fullDescription = "server.lowSystemLoadAverage.full"
   )
   public static boolean ServerLowSystemLoadAverage(@WLDFI18n(name = "period",displayName = "params.period.displayName",value = "params.period",defaultValue = "30 seconds") String period, @WLDFI18n(name = "duration",displayName = "params.duration.displayName",value = "params.duration",defaultValue = "10 minutes") String duration, @WLDFI18n(name = "loadLimit",displayName = "loadLimit.displayName",value = "loadLimit.desc") float loadLimit) throws ParseException {
      return evaluateExpression("wls.platform.query('java.lang:type=OperatingSystem,*', 'SystemLoadAverage')", period, duration, "<", loadLimit);
   }

   @Function(
      smartRule = true
   )
   @WLDFI18n(
      value = "server.highHeapFree.desc",
      displayName = "server.highHeapFree.displayName",
      fullDescription = "server.highHeapFree.full"
   )
   public static boolean ServerHighHeapFreePercent(@WLDFI18n(name = "period",displayName = "params.period.displayName",value = "params.period",defaultValue = "30 seconds") String period, @WLDFI18n(name = "duration",displayName = "params.duration.displayName",value = "params.duration",defaultValue = "10 minutes") String duration, @WLDFI18n(name = "percentFreeLimit",displayName = "percentFreeLimit.displayName",value = "percentFreeLimit.desc") float percentFreeLimit) throws ParseException {
      return evaluateExpression("wls.runtime.serverRuntime.JVMRuntime.heapFreePercent", period, duration, ">=", percentFreeLimit);
   }

   @Function(
      smartRule = true
   )
   @WLDFI18n(
      value = "server.highSystemLoadAverage.desc",
      displayName = "server.highSystemLoadAverage.displayName",
      fullDescription = "server.highSystemLoadAverage.full"
   )
   public static boolean ServerHighSystemLoadAverage(@WLDFI18n(name = "period",displayName = "params.period.displayName",value = "params.period",defaultValue = "30 seconds") String period, @WLDFI18n(name = "duration",displayName = "params.duration.displayName",value = "params.duration",defaultValue = "10 minutes") String duration, @WLDFI18n(name = "loadLimit",displayName = "loadLimit.displayName",value = "loadLimit.desc") float loadLimit) throws ParseException {
      return evaluateExpression("wls.platform.query('java.lang:type=OperatingSystem,*', 'SystemLoadAverage')", period, duration, ">=", loadLimit);
   }

   @Function(
      smartRule = true
   )
   @WLDFI18n(
      value = "server.highStuckThreads.desc",
      displayName = "server.highStuckThreads.displayName",
      fullDescription = "server.highStuckThreads.full"
   )
   public static boolean ServerHighStuckThreads(@WLDFI18n(name = "period",displayName = "params.period.displayName",value = "params.period",defaultValue = "30 seconds") String period, @WLDFI18n(name = "duration",displayName = "params.duration.displayName",value = "params.duration",defaultValue = "10 minutes") String duration, @WLDFI18n(name = "stuckThreadsLimit",displayName = "stuckThreadsLimit.displayName",value = "stuckThreadsLimit.desc") float stuckThreadsLimit) throws ParseException {
      return evaluateExpression("wls.runtime.serverRuntime.threadPoolRuntime.stuckThreadCount", period, duration, ">=", stuckThreadsLimit);
   }

   @Function(
      smartRule = true
   )
   @WLDFI18n(
      value = "server.highIdleThreads.desc",
      displayName = "server.highIdleThreads.displayName",
      fullDescription = "server.highIdleThreads.full"
   )
   public static boolean ServerHighIdleThreads(@WLDFI18n(name = "period",displayName = "params.period.displayName",value = "params.period",defaultValue = "30 seconds") String period, @WLDFI18n(name = "duration",displayName = "params.duration.displayName",value = "params.duration",defaultValue = "10 minutes") String duration, @WLDFI18n(name = "idleThreadsLimit",displayName = "idleThreadLimit.displayName",value = "idleThreadLimit.desc") float idleThreadsLimit) throws ParseException {
      return evaluateExpression("wls.runtime.serverRuntime.threadPoolRuntime.executeThreadIdleCount", period, duration, ">=", idleThreadsLimit);
   }

   @Function(
      smartRule = true
   )
   @WLDFI18n(
      value = "server.lowIdleThreads.desc",
      displayName = "server.lowIdleThreads.displayName",
      fullDescription = "server.lowIdleThreads.full"
   )
   public static boolean ServerLowIdleThreads(@WLDFI18n(name = "period",displayName = "params.period.displayName",value = "params.period",defaultValue = "30 seconds") String period, @WLDFI18n(name = "duration",displayName = "params.duration.displayName",value = "params.duration",defaultValue = "10 minutes") String duration, @WLDFI18n(name = "idleThreadsLimit",displayName = "idleThreadLimit.displayName",value = "idleThreadLimit.desc") float idleThreadsLimit) throws ParseException {
      return evaluateExpression("wls.runtime.serverRuntime.threadPoolRuntime.executeThreadIdleCount", period, duration, "<", idleThreadsLimit);
   }

   @Function(
      smartRule = true
   )
   @WLDFI18n(
      value = "server.highPendingUserRequests.desc",
      displayName = "server.highPendingUserRequests.displayName",
      fullDescription = "server.highPendingUserRequests.full"
   )
   public static boolean ServerHighPendingUserRequests(@WLDFI18n(name = "period",displayName = "params.period.displayName",value = "params.period",defaultValue = "30 seconds") String period, @WLDFI18n(name = "duration",displayName = "params.duration.displayName",value = "params.duration",defaultValue = "10 minutes") String duration, @WLDFI18n(name = "pendingRequestsLimit",displayName = "pendingRequestsLimit.displayName",value = "pendingRequestsLimit.desc") float pendingRequestsLimit) throws ParseException {
      return evaluateExpression("wls.runtime.serverRuntime.threadPoolRuntime.pendingUserRequestCount", period, duration, ">=", pendingRequestsLimit);
   }

   @Function(
      smartRule = true
   )
   @WLDFI18n(
      value = "server.lowPendingUserRequests.desc",
      displayName = "server.lowPendingUserRequests.displayName",
      fullDescription = "server.lowPendingUserRequests.full"
   )
   public static boolean ServerLowPendingUserRequests(@WLDFI18n(name = "period",displayName = "params.period.displayName",value = "params.period",defaultValue = "30 seconds") String period, @WLDFI18n(name = "duration",displayName = "params.duration.displayName",value = "params.duration",defaultValue = "10 minutes") String duration, @WLDFI18n(name = "pendingRequestsLimit",displayName = "pendingRequestsLimit.displayName",value = "pendingRequestsLimit.desc") float pendingRequestsLimit) throws ParseException {
      return evaluateExpression("wls.runtime.serverRuntime.threadPoolRuntime.pendingUserRequestCount", period, duration, "<", pendingRequestsLimit);
   }

   @Function(
      smartRule = true
   )
   @WLDFI18n(
      value = "server.highQueueLength.desc",
      displayName = "server.highQueueLength.displayName",
      fullDescription = "server.highQueueLength.full"
   )
   public static boolean ServerHighQueueLength(@WLDFI18n(name = "period",displayName = "params.period.displayName",value = "params.period",defaultValue = "30 seconds") String period, @WLDFI18n(name = "duration",displayName = "params.duration.displayName",value = "params.duration",defaultValue = "10 minutes") String duration, @WLDFI18n(name = "queueLengthLimit",displayName = "queueLengthLimit.displayName",value = "queueLengthLimit.desc") float queueLengthLimit) throws ParseException {
      return evaluateExpression("wls.runtime.serverRuntime.threadPoolRuntime.queueLength", period, duration, ">=", queueLengthLimit);
   }

   @Function(
      smartRule = true
   )
   @WLDFI18n(
      value = "server.lowQueueLength.desc",
      displayName = "server.lowQueueLength.displayName",
      fullDescription = "server.lowQueueLength.full"
   )
   public static boolean ServerLowQueueLength(@WLDFI18n(name = "period",displayName = "params.period.displayName",value = "params.period",defaultValue = "30 seconds") String period, @WLDFI18n(name = "duration",displayName = "params.duration.displayName",value = "params.duration",defaultValue = "10 minutes") String duration, @WLDFI18n(name = "queueLengthLimit",displayName = "queueLengthLimit.displayName",value = "queueLengthLimit.desc") float queueLengthLimit) throws ParseException {
      return evaluateExpression("wls.runtime.serverRuntime.threadPoolRuntime.queueLength", period, duration, "<", queueLengthLimit);
   }

   @Function(
      smartRule = true
   )
   @WLDFI18n(
      value = "server.highThroughput.desc",
      displayName = "server.highThroughput.displayName",
      fullDescription = "server.highThroughput.full"
   )
   public static boolean ServerHighThroughput(@WLDFI18n(name = "period",displayName = "params.period.displayName",value = "params.period",defaultValue = "30 seconds") String period, @WLDFI18n(name = "duration",displayName = "params.duration.displayName",value = "params.duration",defaultValue = "10 minutes") String duration, @WLDFI18n(name = "throughputLimit",displayName = "throughputLimit.displayName",value = "throughputLimit.desc") float throughputLimit) throws ParseException {
      return evaluateExpression("wls.runtime.serverRuntime.threadPoolRuntime.throughput", period, duration, ">=", throughputLimit);
   }

   @Function(
      smartRule = true
   )
   @WLDFI18n(
      value = "server.lowThroughput.desc",
      displayName = "server.lowThroughput.displayName",
      fullDescription = "server.lowThroughput.full"
   )
   public static boolean ServerLowThroughput(@WLDFI18n(name = "period",displayName = "params.period.displayName",value = "params.period",defaultValue = "30 seconds") String period, @WLDFI18n(name = "duration",displayName = "params.duration.displayName",value = "params.duration",defaultValue = "10 minutes") String duration, @WLDFI18n(name = "throughputLimit",displayName = "throughputLimit.displayName",value = "throughputLimit.desc") float throughputLimit) throws ParseException {
      return evaluateExpression("wls.runtime.serverRuntime.threadPoolRuntime.throughput", period, duration, "<", throughputLimit);
   }
}
