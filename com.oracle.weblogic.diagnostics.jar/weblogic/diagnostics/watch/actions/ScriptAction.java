package weblogic.diagnostics.watch.actions;

import com.oracle.weblogic.diagnostics.expressions.AdminServer;
import com.oracle.weblogic.diagnostics.expressions.ManagedServer;
import com.oracle.weblogic.diagnostics.watch.actions.ActionAdapter;
import com.oracle.weblogic.diagnostics.watch.actions.ActionConfigBean;
import com.oracle.weblogic.diagnostics.watch.actions.ActionContext;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import org.glassfish.hk2.api.PerLookup;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.watch.i18n.DiagnosticsWatchLogger;
import weblogic.elasticity.util.ElasticityUtils;
import weblogic.elasticity.util.ScriptPathValidator;

@Service(
   name = "ScriptAction"
)
@PerLookup
@AdminServer
@ManagedServer
public class ScriptAction extends ActionAdapter {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticsScriptAction");
   public static final String ACTION_NAME = "ScriptAction";
   public static final String EXIT_VALUE_KEY = "exit-value";
   public static final String EXCEPTION_KEY = "exception";
   private volatile Process proc;
   private volatile int exitCode = -1;
   private ScriptActionConfig currentConfig;

   public ScriptAction() {
      super("ScriptAction");
   }

   public void execute(ActionContext context) {
      ActionConfigBean actionConfig = context.getActionConfig();
      if (actionConfig != null && actionConfig instanceof ScriptActionConfig) {
         this.currentConfig = (ScriptActionConfig)actionConfig;
         ProcessBuilder pb = new ProcessBuilder(new String[0]);
         ElasticityUtils.initScriptProcessEnvironment(pb);
         if (this.currentConfig.getWorkingDirectory() != null) {
            pb.directory(new File(this.currentConfig.getWorkingDirectory()));
         }

         String pathToScript = ScriptPathValidator.buildScriptPath(this.currentConfig.getPathToScript());
         if (pathToScript != null && !pathToScript.isEmpty() && (new File(pathToScript)).exists()) {
            List commands = new LinkedList();
            commands.add(pathToScript);
            if (this.currentConfig.getParameters() != null) {
               commands.addAll(this.currentConfig.getParameters());
            }

            pb.command(commands);
            if (this.currentConfig.getEnvironment() != null) {
               pb.environment().putAll(this.currentConfig.getEnvironment());
            }

            HashMap procInfo = new HashMap();
            context.addWatchData("ScriptAction", procInfo);

            try {
               String tmpdirPath = System.getProperty("java.io.tmpdir", ".");
               File outdir = new File(tmpdirPath);
               String prefix = "ScriptAction-" + this.currentConfig.getName() + "-";
               File stdout = File.createTempFile(prefix, ".out", outdir);
               File stderr = File.createTempFile(prefix, ".err", outdir);
               pb.redirectOutput(stdout);
               pb.redirectError(stderr);
               DiagnosticsWatchLogger.logScriptActionExecutionStart(this.currentConfig.getName(), stdout.getAbsolutePath(), stderr.getAbsolutePath());
               this.proc = pb.start();
               this.exitCode = this.proc.waitFor();
               DiagnosticsWatchLogger.logScriptActionExecutionComplete(this.currentConfig.getName(), pathToScript, this.exitCode);
            } catch (InterruptedException var12) {
               procInfo.put("exception", var12);
               DiagnosticsWatchLogger.logScriptActionInterrupted(this.currentConfig.getName(), var12.getMessage());
            } catch (Exception var13) {
               procInfo.put("exception", var13);
               DiagnosticsWatchLogger.logScriptActionFailed(this.currentConfig.getName(), var13.getMessage());
            }

            procInfo.put("exit-value", this.exitCode);
         } else {
            DiagnosticsWatchLogger.logScriptActionPathEmptyOrDoesNotExist(this.currentConfig.getName(), pathToScript);
         }
      } else {
         DiagnosticsWatchLogger.logInvalidScriptActionConfigType(actionConfig == null ? "null" : actionConfig.getClass().getName(), ScriptActionConfig.class.getName());
      }
   }

   public void cancel() {
      DiagnosticsWatchLogger.logScriptActionCancelled(this.currentConfig != null ? this.currentConfig.getName() : "");

      try {
         this.proc.destroy();
         this.proc.waitFor();
      } catch (Exception var2) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Exception occurred canceling the script action", var2);
         }
      }

   }
}
