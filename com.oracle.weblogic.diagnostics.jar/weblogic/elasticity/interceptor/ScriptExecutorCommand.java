package weblogic.elasticity.interceptor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.elasticity.ScalingOperationStatus;
import weblogic.elasticity.i18n.ElasticityLogger;
import weblogic.elasticity.util.ElasticityUtils;
import weblogic.i18n.logging.Loggable;
import weblogic.management.workflow.CommandFailedNoTraceException;
import weblogic.management.workflow.command.CommandInterface;
import weblogic.management.workflow.command.CommandRevertInterface;
import weblogic.management.workflow.command.SharedState;
import weblogic.management.workflow.command.WorkflowContext;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.FileUtils;

public class ScriptExecutorCommand implements CommandInterface, CommandRevertInterface {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("ScriptExecutorCommand");
   private static final long SECOND = 1000L;
   private final ScriptExecutorState scriptExecutorState;
   private transient volatile WorkflowContext context;
   private final Map outputProperties = new HashMap();
   @SharedState(
      name = "InterceptorSharedDataConstants_workflow_shared_data_map_key"
   )
   private transient Map sharedMap;
   private ArrayList list;

   public ScriptExecutorCommand(ScriptExecutorState state) {
      this.scriptExecutorState = state;
   }

   public void initialize(WorkflowContext context) {
      this.context = context;
      this.list = (ArrayList)this.sharedMap.get("echo.command.list");
      if (this.list == null) {
         this.list = new ArrayList();
         this.sharedMap.put("echo.command.list", this.list);
      }

   }

   public boolean execute() throws Exception {
      Map inputState = (Map)this.context.getSharedState("InterceptorSharedDataConstants_workflow_shared_data_map_key");
      boolean result = this.executeScript(true, this.scriptExecutorState, inputState);
      return result;
   }

   public boolean revert() throws Exception {
      Map inputState = (Map)this.context.getSharedState("InterceptorSharedDataConstants_workflow_shared_data_map_key");
      return this.executeScript(false, this.scriptExecutorState, inputState);
   }

   public static Properties getEnv() {
      Properties props = new Properties();
      Iterator var1 = System.getenv().entrySet().iterator();

      while(var1.hasNext()) {
         Map.Entry e = (Map.Entry)var1.next();
         props.put(e.getKey(), e.getValue());
      }

      return props;
   }

   private boolean executeScript(boolean isMainScript, final ScriptExecutorState state, Map inputState) throws Exception {
      final String commandName = isMainScript ? state.getPathToScript() : state.getPathToErrorHandlerScript();
      Integer exitCode = null;
      File stdout = null;
      File stderr = null;
      Timer timer = null;
      final String workflowId = this.context.getWorkflowId();
      Exception theException = null;

      try {
         ProcessBuilder pb = this.preInvoke(isMainScript, state);
         String tmpdirPath = System.getProperty("java.io.tmpdir", ".");
         File outdir = new File(tmpdirPath);
         String prefix = "ScriptInterceptor-" + state.getScriptMBeanName() + "-" + System.currentTimeMillis();
         stdout = File.createTempFile(prefix, ".out", outdir);
         stderr = File.createTempFile(prefix, ".err", outdir);
         pb.redirectOutput(stdout);
         pb.redirectError(stderr);
         reportScriptExecution(workflowId, state);
         final Process proc = pb.start();
         final int timeout = state.getTimeoutInSeconds();
         final AtomicInteger timeoutSecs = new AtomicInteger(timeout);
         if (state.getTimeoutInSeconds() <= 0) {
            timeoutSecs.set(Integer.MAX_VALUE);
         }

         TimerManager timerManager = TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager();
         timer = timerManager.scheduleAtFixedRate(new TimerListener() {
            public void timerExpired(Timer timer) {
               boolean destroy = false;
               if (ScriptExecutorCommand.this.context.isCancel()) {
                  ElasticityLogger.logAbortScriptExceution(workflowId, state.getPathToScript());
                  destroy = true;
               } else if (timeoutSecs.decrementAndGet() <= 0) {
                  ElasticityLogger.logScriptInterceptorTimeout(workflowId, commandName, state.getScriptMBeanName(), state.getDynamicClusterName(), timeout);
                  destroy = true;
               }

               if (destroy) {
                  proc.destroy();
               }

            }
         }, 1000L, 1000L);
         exitCode = proc.waitFor();
         reportScriptCompletionStatus(workflowId, state, exitCode);
      } catch (Exception var22) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("ScriptInterceptor encountered exception", var22);
         }

         theException = new CommandFailedNoTraceException(var22.getMessage(), var22);
         throw theException;
      } finally {
         if (timer != null) {
            timer.cancel();
         }

         this.postInvoke(exitCode, commandName, isMainScript, state, stderr, theException);
         if (stdout != null) {
            stdout.delete();
         }

         if (stderr != null) {
            stdout.delete();
         }

      }

      return exitCode == 0;
   }

   private static void reportScriptExecution(String workflowId, ScriptExecutorState state) {
      if (state.isPreProcessor()) {
         if (state.isScaleUp()) {
            ElasticityLogger.executingScaleUpPreProcessorScript(workflowId, state.getPathToScript(), state.getScriptMBeanName(), state.getDynamicClusterName());
         } else {
            ElasticityLogger.executingScaleDownPreProcessorScript(workflowId, state.getPathToScript(), state.getScriptMBeanName(), state.getDynamicClusterName());
         }
      } else if (state.isScaleUp()) {
         ElasticityLogger.executingScaleUpPostProcessorScript(workflowId, state.getPathToScript(), state.getScriptMBeanName(), state.getDynamicClusterName());
      } else {
         ElasticityLogger.executingScaleDownPostProcessorScript(workflowId, state.getPathToScript(), state.getScriptMBeanName(), state.getDynamicClusterName());
      }

   }

   private static void reportScriptCompletionStatus(String workflowId, ScriptExecutorState state, Integer exitCode) {
      if (state.isPreProcessor()) {
         if (state.isScaleUp()) {
            ElasticityLogger.reportPreProcessorScaleUpScriptCompletionStatus(workflowId, state.getPathToScript(), state.getScriptMBeanName(), state.getDynamicClusterName(), exitCode);
         } else {
            ElasticityLogger.reportPreProcessorScaleDownScriptCompletionStatus(workflowId, state.getPathToScript(), state.getScriptMBeanName(), state.getDynamicClusterName(), exitCode);
         }
      } else if (state.isScaleUp()) {
         ElasticityLogger.reportPostProcessorScaleUpScriptCompletionStatus(workflowId, state.getPathToScript(), state.getScriptMBeanName(), state.getDynamicClusterName(), exitCode);
      } else {
         ElasticityLogger.reportPostProcessorScaleDownScriptCompletionStatus(workflowId, state.getPathToScript(), state.getScriptMBeanName(), state.getDynamicClusterName(), exitCode);
      }

   }

   private ProcessBuilder preInvoke(boolean isMainScript, ScriptExecutorState state) throws IOException {
      ProcessBuilder pb = new ProcessBuilder(new String[0]);
      if (state.getWorkingDirectory() != null) {
         pb.directory(new File(state.getWorkingDirectory()));
      }

      String[] commandAndArgs = new String[1 + state.getArguments().length];
      commandAndArgs[0] = isMainScript ? state.getPathToScript() : state.getPathToErrorHandlerScript();
      if (state.getArguments().length > 0) {
         System.arraycopy(state.getArguments(), 0, commandAndArgs, 1, state.getArguments().length);
      }

      pb.command(commandAndArgs);
      ElasticityUtils.initScriptProcessEnvironment(pb);
      if (state.getEnvironment() != null) {
         Iterator var5 = state.getEnvironment().entrySet().iterator();

         while(var5.hasNext()) {
            Map.Entry e = (Map.Entry)var5.next();
            pb.environment().put(e.getKey().toString(), e.getValue().toString());
         }
      }

      ScalingOperationStatus scalingOperationStatus = (ScalingOperationStatus)this.sharedMap.get("InterceptorSharedDataConstants_elasticity_scaling_operation_status_key");
      if (scalingOperationStatus != null) {
         pb.environment().putAll(ScriptAndCommandUtil.toMap(scalingOperationStatus, "WLS_SCRIPT_"));
      }

      HashMap sharedEnvForScript = (HashMap)this.sharedMap.get("SHARED_ENV_FOR_SCRIPT");
      if (sharedEnvForScript != null) {
         pb.environment().putAll(sharedEnvForScript);
      }

      pb.environment().put("WLS_SCRIPT_TEMP_DIR", ScriptAndCommandUtil.getTemporaryDirNameForScript(this.sharedMap));
      pb.environment().put("WLS_SCRIPT_OUTPUT_FILE", ScriptAndCommandUtil.getTemporaryFileNameForScript(this.sharedMap));
      if (!isMainScript) {
         Set failedScriptNames = (Set)this.sharedMap.get("FAILED_SCRIPT_NAMES");
         boolean didThisScriptFail = failedScriptNames != null && failedScriptNames.contains(this.scriptExecutorState.getScriptMBeanName());
         pb.environment().put("WLS_SCRIPT_THIS_STEP_FAILED", "" + didThisScriptFail);
      }

      return pb;
   }

   private void postInvoke(Integer exitCode, String commandName, boolean isMainScript, ScriptExecutorState state, File stderr, Exception originalException) throws IOException, CommandFailedNoTraceException {
      String tempFileName = (String)this.sharedMap.get("WLS_SCRIPT_OUTPUT_FILE");
      Set failedScriptNames = (Set)this.sharedMap.get("FAILED_SCRIPT_NAMES");
      boolean var50 = false;

      try {
         var50 = true;
         if (exitCode != null && exitCode == 0) {
            this.populateSharedEnvFromScriptOutputFile(tempFileName);
            if (isMainScript) {
               if (failedScriptNames != null) {
                  failedScriptNames.remove(this.scriptExecutorState.getScriptMBeanName());
                  var50 = false;
               } else {
                  var50 = false;
               }
            } else {
               var50 = false;
            }
         } else {
            if (isMainScript && failedScriptNames == null) {
               Set failedScriptNames = new HashSet();
               failedScriptNames.add(this.scriptExecutorState.getScriptMBeanName());
            }

            if (DEBUG.isDebugEnabled()) {
               String stderrOutput = stderr == null ? "" : new String(FileUtils.readBytes(stderr));
               DEBUG.debug("Script error output:\n" + stderrOutput);
            }

            if (originalException == null) {
               Loggable loggable = ElasticityLogger.logScriptInterceptorFailedLoggable(this.context.getWorkflowId(), commandName, state.getScriptMBeanName(), state.getDynamicClusterName(), exitCode == null ? -1 : exitCode);
               throw new CommandFailedNoTraceException(loggable.getMessageText());
            }

            var50 = false;
         }
      } finally {
         if (var50) {
            try {
               PrintWriter pw = new PrintWriter(new FileWriter(tempFileName));
               Throwable var16 = null;

               try {
                  pw.println("#Script Interceptor Input / Output File");
               } catch (Throwable var52) {
                  var16 = var52;
                  throw var52;
               } finally {
                  if (pw != null) {
                     if (var16 != null) {
                        try {
                           pw.close();
                        } catch (Throwable var51) {
                           var16.addSuppressed(var51);
                        }
                     } else {
                        pw.close();
                     }
                  }

               }
            } catch (Exception var56) {
            }

         }
      }

      try {
         PrintWriter pw = new PrintWriter(new FileWriter(tempFileName));
         Throwable var10 = null;

         try {
            pw.println("#Script Interceptor Input / Output File");
         } catch (Throwable var54) {
            var10 = var54;
            throw var54;
         } finally {
            if (pw != null) {
               if (var10 != null) {
                  try {
                     pw.close();
                  } catch (Throwable var53) {
                     var10.addSuppressed(var53);
                  }
               } else {
                  pw.close();
               }
            }

         }
      } catch (Exception var58) {
      }

   }

   private void populateSharedEnvFromScriptOutputFile(String tempFileName) {
      try {
         BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(tempFileName)));
         Throwable var3 = null;

         try {
            HashMap sharedEnvForScript = (HashMap)this.sharedMap.get("SHARED_ENV_FOR_SCRIPT");

            for(String line = br.readLine(); line != null; line = br.readLine()) {
               line = line.trim();
               if (!line.startsWith("#")) {
                  int index = line.indexOf(61);
                  if (index > 0) {
                     if (sharedEnvForScript == null) {
                        sharedEnvForScript = new HashMap();
                        this.sharedMap.put("SHARED_ENV_FOR_SCRIPT", sharedEnvForScript);
                     }

                     sharedEnvForScript.put(line.substring(0, index), line.substring(index + 1));
                  }
               }
            }
         } catch (Throwable var15) {
            var3 = var15;
            throw var15;
         } finally {
            if (br != null) {
               if (var3 != null) {
                  try {
                     br.close();
                  } catch (Throwable var14) {
                     var3.addSuppressed(var14);
                  }
               } else {
                  br.close();
               }
            }

         }
      } catch (IOException var17) {
      }

   }

   private void populateScriptEnvFromSharedScriptEnv() {
      HashMap sharedEnvForScript = (HashMap)this.sharedMap.get("SHARED_ENV_FOR_SCRIPT");
      if (sharedEnvForScript != null) {
         Properties props = new Properties();
         props.putAll(sharedEnvForScript);
      }

   }
}
