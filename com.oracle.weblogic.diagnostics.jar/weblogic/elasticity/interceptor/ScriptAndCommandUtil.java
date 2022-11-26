package weblogic.elasticity.interceptor;

import com.oracle.core.interceptor.MethodInvocationContext;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import weblogic.elasticity.ScalingOperationStatus;
import weblogic.elasticity.util.ScriptPathValidator;
import weblogic.management.configuration.ScriptMBean;
import weblogic.management.workflow.FailurePlan;
import weblogic.management.workflow.WorkflowProgress;

public class ScriptAndCommandUtil {
   public static final String DYNAMIC_CLUSTER_NAME = "DYNAMIC_CLUSTER_NAME";
   public static final String DYNAMIC_CLUSTER_OPERATION_NAME = "DYNAMIC_CLUSTER_OPERATION_NAME";
   public static final String DYNAMIC_CLUSTER_MIN_SIZE = "DYNAMIC_CLUSTER_MIN_SIZE";
   public static final String DYNAMIC_CLUSTER_MAX_SIZE = "DYNAMIC_CLUSTER_MAX_SIZE";
   public static final String DYNAMIC_CLUSTER_SIZE = "DYNAMIC_CLUSTER_SIZE";
   public static final String DYNAMIC_CLUSTER_CANDIDATE_MEMBER_NAMES = "DYNAMIC_CLUSTER_CANDIDATE_MEMBER_NAMES";
   public static final String DYNAMIC_CLUSTER_ALLOWED_SCALING_SIZE = "DYNAMIC_CLUSTER_ALLOWED_SCALING_SIZE";
   public static final String DYNAMIC_CLUSTER_REQUESTED_SCALING_SIZE = "DYNAMIC_CLUSTER_REQUESTED_SCALING_SIZE";
   public static final String DYNAMIC_CLUSTER_SCALED_MEMBER_NAMES = "DYNAMIC_CLUSTER_SCALED_MEMBER_NAMES";
   public static final String WLS_SCRIPT_THIS_STEP_FAILED = "WLS_SCRIPT_THIS_STEP_FAILED";
   public static final String WLS_SCRIPT_OUTPUT_FILE = "WLS_SCRIPT_OUTPUT_FILE";
   public static final String WLS_SCRIPT_TEMP_DIR = "WLS_SCRIPT_TEMP_DIR";
   public static final String SHARED_ENV_FOR_SCRIPT = "SHARED_ENV_FOR_SCRIPT";
   public static final String FAILED_SCRIPT_NAMES = "FAILED_SCRIPT_NAMES";
   private static volatile Set _reservedScriptEnvNames;

   public static Set getReservedScriptEnvNames() {
      if (_reservedScriptEnvNames == null) {
         Class var0 = ScriptAndCommandUtil.class;
         synchronized(ScriptAndCommandUtil.class) {
            if (_reservedScriptEnvNames == null) {
               _reservedScriptEnvNames = new HashSet();
               String prefix = "WLS_SCRIPT_";
               _reservedScriptEnvNames.add(prefix + "DYNAMIC_CLUSTER_NAME");
               _reservedScriptEnvNames.add(prefix + "DYNAMIC_CLUSTER_OPERATION_NAME");
               _reservedScriptEnvNames.add(prefix + "DYNAMIC_CLUSTER_MIN_SIZE");
               _reservedScriptEnvNames.add(prefix + "DYNAMIC_CLUSTER_MAX_SIZE");
               _reservedScriptEnvNames.add(prefix + "DYNAMIC_CLUSTER_SIZE");
               _reservedScriptEnvNames.add(prefix + "DYNAMIC_CLUSTER_CANDIDATE_MEMBER_NAMES");
               _reservedScriptEnvNames.add(prefix + "DYNAMIC_CLUSTER_ALLOWED_SCALING_SIZE");
               _reservedScriptEnvNames.add(prefix + "DYNAMIC_CLUSTER_REQUESTED_SCALING_SIZE");
               _reservedScriptEnvNames.add(prefix + "DYNAMIC_CLUSTER_SCALED_MEMBER_NAMES");
               _reservedScriptEnvNames.add(prefix + "WLS_SCRIPT_THIS_STEP_FAILED");
               _reservedScriptEnvNames.add(prefix + "WLS_SCRIPT_OUTPUT_FILE");
               _reservedScriptEnvNames.add(prefix + "WLS_SCRIPT_TEMP_DIR");
            }
         }
      }

      return _reservedScriptEnvNames;
   }

   public static ScriptExecutorState createScriptExecutorState(ScriptMBean scriptMBean, MethodInvocationContext invCtx) {
      ScriptExecutorState state = createScriptExecutorState(scriptMBean);
      state.setIsScaleUp("scaleUp".equalsIgnoreCase(invCtx.getMethodInvocation().getMethod().getName()));
      state.setDynamicClusterName((String)invCtx.getMethodInvocation().getArguments()[0]);
      state.getEnvironment().putAll(extractSharedData(invCtx));
      return state;
   }

   private static ScriptExecutorState createScriptExecutorState(ScriptMBean scriptMBean) {
      ScriptExecutorState state = new ScriptExecutorState();
      state.setScriptMBeanName(scriptMBean.getName());
      state.setArguments(scriptMBean.getArguments());
      state.setEnvironment(scriptMBean.getEnvironment());
      state.setIgnoreFailures(scriptMBean.isIgnoreFailures());
      state.setNumberOfRetriesAllowed(scriptMBean.getNumberOfRetriesAllowed());
      state.setPathToErrorHandlerScript(scriptMBean.getPathToErrorHandlerScript());
      state.setPathToScript(ScriptPathValidator.buildScriptPath(scriptMBean.getPathToScript()));
      state.setRetryDelayInMillis(scriptMBean.getRetryDelayInMillis());
      state.setWorkingDirectory(scriptMBean.getWorkingDirectory());
      state.setTimeoutInSeconds(scriptMBean.getTimeoutInSeconds());
      return state;
   }

   public static Map extractScriptOutputDataFromEnv(Map env) {
      Map map = new HashMap();
      Iterator var2 = env.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry e = (Map.Entry)var2.next();
         if (((String)e.getKey()).startsWith("WLS_SCRIPT_")) {
            switch ((String)e.getKey()) {
               case "WLS_SCRIPT_DYNAMIC_CLUSTER_NAME":
               case "WLS_SCRIPT_DYNAMIC_CLUSTER_OPERATION_NAME":
               case "WLS_SCRIPT_DYNAMIC_CLUSTER_MIN_SIZE":
               case "WLS_SCRIPT_DYNAMIC_CLUSTER_MAX_SIZE":
               case "WLS_SCRIPT_DYNAMIC_CLUSTER_SIZE":
               case "WLS_SCRIPT_DYNAMIC_CLUSTER_CANDIDATE_MEMBER_NAMES":
               case "WLS_SCRIPT_DYNAMIC_CLUSTER_ALLOWED_SCALING_SIZE":
               case "WLS_SCRIPT_DYNAMIC_CLUSTER_REQUESTED_SCALING_SIZE":
               case "WLS_SCRIPT_DYNAMIC_CLUSTER_SCALED_MEMBER_NAMES":
                  break;
               default:
                  map.put(((String)e.getKey()).substring("WLS_SCRIPT_".length()), e.getValue());
            }
         }
      }

      return map;
   }

   public static Path createTemporaryDirForScript(Map sharedMap, String prefix) throws IOException {
      Path tempDirPath = Files.createTempDirectory("script-temp-dir-" + prefix);
      sharedMap.put("WLS_SCRIPT_TEMP_DIR", tempDirPath.normalize().toString());
      return tempDirPath;
   }

   public static Path createTemporaryFileForScript(Map sharedMap, Path tempDirPath) throws IOException {
      Path tempFilePath = Files.createTempFile(tempDirPath, "script-interceptor-", "-shared-data");
      sharedMap.put("WLS_SCRIPT_OUTPUT_FILE", tempFilePath.normalize().toString());
      return tempFilePath;
   }

   public static String getTemporaryDirNameForScript(Map sharedMap) {
      return (String)sharedMap.get("WLS_SCRIPT_TEMP_DIR");
   }

   public static String getTemporaryFileNameForScript(Map sharedMap) {
      return (String)sharedMap.get("WLS_SCRIPT_OUTPUT_FILE");
   }

   public static Map extractSharedData(MethodInvocationContext ctx) {
      Map map = new HashMap();
      if (ctx != null) {
         Map sharedData = ctx.getSharedInterceptorDataMap();
         if (sharedData != null) {
            Iterator var3 = sharedData.entrySet().iterator();

            while(var3.hasNext()) {
               Map.Entry e = (Map.Entry)var3.next();
               String key = "WLS_SCRIPT_" + e.getKey().toString();
               map.put(key, valueToString(e.getValue()));
            }
         }
      }

      return map;
   }

   public static Map toMap(ScalingOperationStatus scalingOperationStatus, String keyPrefix) {
      Map map = new HashMap();
      if (scalingOperationStatus != null) {
         map.put(keyPrefix + "DYNAMIC_CLUSTER_NAME", scalingOperationStatus.getClusterName());
         map.put(keyPrefix + "DYNAMIC_CLUSTER_OPERATION_NAME", scalingOperationStatus.isScaleUp() ? "scaleUp" : "scaleDown");
         map.put(keyPrefix + "DYNAMIC_CLUSTER_MIN_SIZE", "" + scalingOperationStatus.getClusterMinSize());
         map.put(keyPrefix + "DYNAMIC_CLUSTER_MAX_SIZE", "" + scalingOperationStatus.getClusterMaxSize());
         map.put(keyPrefix + "DYNAMIC_CLUSTER_SIZE", "" + scalingOperationStatus.getClusterSize());
         map.put(keyPrefix + "DYNAMIC_CLUSTER_CANDIDATE_MEMBER_NAMES", "" + valueToString(scalingOperationStatus.getCandidateMemberNames()));
         map.put(keyPrefix + "DYNAMIC_CLUSTER_ALLOWED_SCALING_SIZE", "" + valueToString(scalingOperationStatus.getAllowedScalingSize()));
         map.put(keyPrefix + "DYNAMIC_CLUSTER_REQUESTED_SCALING_SIZE", "" + scalingOperationStatus.getRequestedScalingSize());
         map.put(keyPrefix + "DYNAMIC_CLUSTER_SCALED_MEMBER_NAMES", "" + scalingOperationStatus.getScaledMemberNames());
      }

      return map;
   }

   public static Map extractSharedData(WorkflowProgress progress) {
      Map map = new HashMap();
      if (progress != null) {
         Map sharedData = (Map)progress.getSharedState("InterceptorSharedDataConstants_workflow_shared_data_map_key");
         Iterator var3 = sharedData.entrySet().iterator();

         while(var3.hasNext()) {
            Map.Entry e = (Map.Entry)var3.next();
            String key = "WLS_SCRIPT_" + e.getKey().toString();
            map.put(key, valueToString(e.getValue()));
         }
      }

      return map;
   }

   public static FailurePlan createFailurePlan(ScriptExecutorState state) {
      FailurePlan fp = new FailurePlan();
      fp.setRetryDelayInMillis(state.getRetryDelayInMillis());
      fp.setNumberOfRetriesAllowed(state.getNumberOfRetriesAllowed());
      fp.setShouldIgnore(state.isIgnoreFailures());
      fp.setShouldRetry(!state.isIgnoreFailures());
      fp.setShouldRevert(state.getPathToErrorHandlerScript() != null);
      return fp;
   }

   public static String valueToString(Object value) {
      StringBuilder sb = new StringBuilder();
      Class valueClazz = value.getClass();
      if (value instanceof Map) {
         sb.append("{");
         String prefix = "";

         for(Iterator var4 = ((Map)value).entrySet().iterator(); var4.hasNext(); prefix = ", ") {
            Map.Entry entry = (Map.Entry)var4.next();
            String k = entry.getKey().toString();
            String v = valueToString(entry.getValue());
            sb.append(prefix).append(k).append(" : ").append(v);
         }

         sb.append("}");
      } else if (!valueClazz.isArray() && !(value instanceof Collection)) {
         sb.append(value.toString());
      } else {
         Collection collection = valueClazz.isArray() ? Arrays.asList((Object[])((Object[])value)) : (Collection)value;
         sb.append("[");
         String prefix = "";

         for(Iterator var10 = ((Collection)collection).iterator(); var10.hasNext(); prefix = ", ") {
            Object o = var10.next();
            sb.append(prefix).append(valueToString(o));
         }

         sb.append("]");
      }

      return sb.toString();
   }

   public static void main(String[] args) {
   }
}
