package weblogic.management.patching.commands;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import weblogic.management.configuration.MachineMBean;
import weblogic.management.workflow.command.AbstractCommand;
import weblogic.management.workflow.command.SharedState;
import weblogic.nodemanager.ScriptExecutionFailureException;
import weblogic.nodemanager.mbean.NodeManagerRuntime;

public class ExecScriptCommand extends AbstractCommand {
   private static final long serialVersionUID = -5179085566956822829L;
   static final long DEFAULT_SCRIPT_EXEC_TIMEOUT = 0L;
   static final String PATCHING_SCRIPT_DIR = "patching";
   @SharedState
   private String machineName;
   @SharedState
   private String scriptName;
   @SharedState
   private long timeoutMillis = 0L;
   @SharedState
   private Properties scriptEnvProps;
   private Map scriptEnv;
   private Writer writer;

   public boolean execute() throws Exception {
      if (this.scriptEnvProps != null) {
         this.scriptEnv = new HashMap();
         Iterator var1 = this.scriptEnvProps.entrySet().iterator();

         while(var1.hasNext()) {
            Map.Entry entry = (Map.Entry)var1.next();
            this.scriptEnv.put(entry.getKey().toString(), entry.getValue().toString());
         }
      }

      ByteArrayOutputStream output = new ByteArrayOutputStream();
      OutputStreamWriter writer = new OutputStreamWriter(output);
      MachineMBean machine = (new MachineBasedUtils()).getMachineMBean(this.machineName);
      NodeManagerRuntime nmr = NodeManagerRuntime.getInstance(machine);

      boolean var5;
      try {
         nmr.execScript(this.scriptName, "patching", this.scriptEnv, writer, this.timeoutMillis);
         var5 = true;
      } catch (ScriptExecutionFailureException var20) {
         try {
            writer.flush();
         } catch (IOException var19) {
         }

         String fulloutput = output.toString();
         int index = fulloutput.lastIndexOf("\n", fulloutput.lastIndexOf("\n"));
         String lastTwoLines = fulloutput;
         if (index > 0 && index < fulloutput.length() - 2) {
            lastTwoLines = fulloutput.substring(index);
         }

         CommandException ce = new CommandException(PatchingMessageTextFormatter.getInstance().getUpdateOracleHomeFailure(lastTwoLines));
         ce.initCause(var20);
         throw ce;
      } catch (IOException var21) {
         CommandException ce = new CommandException(PatchingMessageTextFormatter.getInstance().getNodeManagerError(var21.getClass() + ":" + var21.getMessage()));
         ce.initCause(var21);
         throw ce;
      } finally {
         try {
            writer.flush();
         } catch (IOException var18) {
         }

         PatchingLogger.logScriptOutput(this.scriptName, output.toString());
      }

      return var5;
   }
}
