package weblogic.management.patching.extensions;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import weblogic.management.configuration.MachineMBean;
import weblogic.management.patching.commands.CommandException;
import weblogic.management.patching.commands.MachineBasedUtils;
import weblogic.management.patching.commands.PatchingLogger;
import weblogic.management.patching.commands.PatchingMessageTextFormatter;
import weblogic.management.patching.commands.ServerUtils;
import weblogic.management.workflow.command.SharedState;
import weblogic.nodemanager.ScriptExecutionFailureException;
import weblogic.nodemanager.mbean.NodeManagerRuntime;

public class ScriptExecutorExtension extends ExtensionRevert {
   public static final long DEFAULT_SCRIPT_EXEC_TIMEOUT = 2L;
   private static final String IS_REVERT = "isRevert";
   private static final long serialVersionUID = -8689608152455248763L;
   private String isRevertValue = "false";
   @SharedState
   private transient String machineName;
   @SharedState
   private transient String scriptName;
   @SharedState
   private transient String EXTENSION_SCRIPT_DIR;
   @SharedState
   private transient long scriptTimeoutInMin = 2L;
   @SharedState
   private transient Properties scriptEnvProps;
   private Map scriptEnv;

   public boolean execute() throws Exception {
      boolean result = this.executeScript(this.isRevertValue);
      return result;
   }

   public boolean revert() throws Exception {
      this.isRevertValue = "true";
      boolean result = this.executeScript(this.isRevertValue);
      return result;
   }

   public boolean executeScript(String value) throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.getAdminServerName();
      PatchingLogger.logExecutingStep(workflowId, className, logTarget);
      if (this.scriptEnvProps != null) {
         this.scriptEnv = new HashMap();
         Iterator var5 = this.scriptEnvProps.entrySet().iterator();

         while(var5.hasNext()) {
            Map.Entry entry = (Map.Entry)var5.next();
            this.scriptEnv.put(entry.getKey().toString(), entry.getValue().toString());
         }
      }

      this.scriptEnv.put("isRevert", value);
      ByteArrayOutputStream output = new ByteArrayOutputStream();
      OutputStreamWriter writer = new OutputStreamWriter(output);
      MachineMBean machine = (new MachineBasedUtils()).getMachineMBean(this.machineName);
      NodeManagerRuntime nmr = NodeManagerRuntime.getInstance(machine);

      boolean var9;
      try {
         nmr.execScript(this.scriptName, this.EXTENSION_SCRIPT_DIR, this.scriptEnv, writer, this.scriptTimeoutInMin * 60L * 1000L);
         var9 = true;
      } catch (ScriptExecutionFailureException var24) {
         PatchingLogger.logFailedStepNoError(workflowId, className, logTarget);

         try {
            writer.flush();
         } catch (IOException var23) {
         }

         String fulloutput = output.toString();
         int index = fulloutput.lastIndexOf("\n", fulloutput.lastIndexOf("\n"));
         String lastTwoLines = fulloutput;
         if (index > 0 && index < fulloutput.length() - 2) {
            lastTwoLines = fulloutput.substring(index);
         }

         CommandException ce = new CommandException(PatchingMessageTextFormatter.getInstance().getScriptExecutorExtensionFailure(lastTwoLines));
         ce.initCause(var24);
         throw ce;
      } catch (IOException var25) {
         CommandException ce = new CommandException(PatchingMessageTextFormatter.getInstance().getNodeManagerError(var25.getClass() + ":" + var25.getMessage()));
         ce.initCause(var25);
         throw ce;
      } finally {
         try {
            if (writer != null) {
               writer.flush();
               writer.close();
            }

            if (output != null) {
               output.close();
            }
         } catch (IOException var22) {
         }

         PatchingLogger.logScriptOutput(this.scriptName, output.toString());
      }

      return var9;
   }

   private String getAdminServerName() {
      ServerUtils serverUtils = new ServerUtils();
      String adminServerName = serverUtils.getServerMBean().getName();
      return adminServerName;
   }
}
