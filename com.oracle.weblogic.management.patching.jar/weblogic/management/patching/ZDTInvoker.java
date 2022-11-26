package weblogic.management.patching;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import weblogic.management.configuration.MachineMBean;
import weblogic.management.patching.agent.PlatformUtils;
import weblogic.management.patching.agent.ZdtAgent;
import weblogic.management.patching.agent.ZdtAgentInputOutput;
import weblogic.management.patching.agent.ZdtAgentLogMessage;
import weblogic.management.patching.agent.ZdtAgentRequest;
import weblogic.management.patching.agent.ZdtUpdateApplicationAgent;
import weblogic.management.patching.agent.ZdtUpdateOracleHomeAgent;
import weblogic.management.patching.commands.CommandException;
import weblogic.management.patching.commands.PatchingLogger;
import weblogic.management.patching.commands.PatchingMessageTextFormatter;
import weblogic.management.patching.commands.ServerUtils;
import weblogic.management.workflow.CommandFailedNoTraceException;
import weblogic.nodemanager.ScriptExecutionFailureException;
import weblogic.nodemanager.mbean.NodeManagerRuntime;

public class ZDTInvoker {
   private static final String PATCHING_SCRIPT_DIR = "patching";
   private static final String SERVER_TYPE = "ZdtAgent";
   private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
   public static final String USE_NM = "useNM";
   public static final String UPDATE_ORACLE_HOME = "UpdateOracleHome";
   public static final String UPDATE_APPLICATION = "UpdateApplication";
   public static final boolean javaInvoker = new Boolean(System.getProperty("weblogic.management.patching.javaInvoker", "true"));

   public static boolean nmInvoke(String machineName, String pluginName, Map scriptEnv, long timeoutMillis, MessageCallback callback) throws CommandException {
      boolean result = false;
      MachineMBean machine = TopologyInspector.getMachineMBean(machineName);
      NodeManagerRuntime nmr = NodeManagerRuntime.getInstance(machine);
      ByteArrayOutputStream response;
      if (javaInvoker) {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("Invoking ZDT agent " + pluginName + " on node manager " + machineName);
         }

         ZdtAgentRequest request = new ZdtAgentRequest(scriptEnv);
         response = new ByteArrayOutputStream();

         try {
            nmr.invocationRequest(pluginName, "ZdtAgent", request.generateCommand(), response);
         } catch (IOException var26) {
            CommandException ce = new CommandException(PatchingMessageTextFormatter.getInstance().getNodeManagerError(var26.getClass() + ":" + var26.getMessage()));
            ce.initCause(var26);
            throw ce;
         }

         ZdtAgentInputOutput zdtAgentIO = new ZdtAgentInputOutput();
         zdtAgentIO.readResponse(response.toString());
         List logMessages = zdtAgentIO.getMessages();
         Iterator var13 = logMessages.iterator();

         while(var13.hasNext()) {
            ZdtAgentLogMessage msg = (ZdtAgentLogMessage)var13.next();
            Level level = msg.getLevel();
            StringBuilder sb = new StringBuilder();
            sb.append("[").append(sdf.format(new Date(msg.getTimeStampMillis()))).append("] ");
            sb.append("[").append(msg.getMsgContents()).append("]");
            if (level.equals(Level.SEVERE)) {
               PatchingLogger.logNMError(machineName, sb.toString());
            } else if (level.equals(Level.WARNING)) {
               PatchingLogger.logNMWarning(machineName, sb.toString());
            } else if (level.equals(Level.INFO)) {
               PatchingLogger.logNMInfo(machineName, sb.toString());
            } else if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("DEBUG message from NM [" + machineName + "] [" + sb.toString());
            }
         }

         result = zdtAgentIO.isSuccess();
         if (!result) {
            throw new CommandException(PatchingMessageTextFormatter.getInstance().getFailedZDTAgentExecution(pluginName, machineName, zdtAgentIO.getCause()));
         }

         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("Successfully completed ZDT agent " + pluginName + " invocation on node manager " + machineName);
         }
      } else {
         String scriptName = pluginName + PlatformUtils.getScriptExtension();
         response = new ByteArrayOutputStream();
         OutputStreamWriter writer = new OutputStreamWriter(response);

         try {
            if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("Calling node manager " + machineName + " to run " + scriptName);
            }

            int returnCode = nmr.execScript(scriptName, "patching", scriptEnv, writer, timeoutMillis);
            result = returnCode == 0;
         } catch (ScriptExecutionFailureException var27) {
            String fulloutput = response.toString();
            int index = fulloutput.lastIndexOf("\n", fulloutput.lastIndexOf("\n"));
            String lastTwoLines = fulloutput;
            if (index > 0 && index < fulloutput.length() - 2) {
               lastTwoLines = fulloutput.substring(index);
            }

            PatchingLogger.logPathVerificationError(machineName, lastTwoLines, var27);
            throw new CommandFailedNoTraceException(callback.getErrorMessage(lastTwoLines));
         } catch (IOException var28) {
            CommandException ce = new CommandException(PatchingMessageTextFormatter.getInstance().getNodeManagerError(var28.getClass() + ":" + var28.getMessage()));
            ce.initCause(var28);
            throw ce;
         } finally {
            try {
               writer.flush();
            } catch (IOException var25) {
            }

            PatchingLogger.logScriptOutput(scriptName, response.toString());
         }

         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("Successfully executed script " + scriptName + " on node manager " + machineName);
         }
      }

      return result;
   }

   public static boolean localInvoke(String agentName, Map scriptEnv) throws CommandException {
      boolean result = false;
      ZdtAgentLocalOutputHandler zdtOutput = null;

      try {
         ZdtAgentRequest request = new ZdtAgentRequest(scriptEnv);
         String domainDir = (new ServerUtils()).getServerMBean().getRootDirectory();
         request.setDomainDirectory(domainDir);
         zdtOutput = new ZdtAgentLocalOutputHandler(request);
         ZdtAgent zdtAgent = null;
         if (agentName.equals("UpdateOracleHome")) {
            zdtAgent = new ZdtUpdateOracleHomeAgent(request, zdtOutput);
         } else {
            if (!agentName.equals("UpdateApplication")) {
               throw new CommandException("Invalid ZDT agent: " + agentName);
            }

            zdtAgent = new ZdtUpdateApplicationAgent(request, zdtOutput, new File(domainDir));
         }

         ((ZdtAgent)zdtAgent).execRequest();
         result = true;
         return result;
      } catch (Exception var7) {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("Caught error executing local zdtAgent request", var7);
         }

         throw new CommandException(var7);
      }
   }

   public static boolean invokeAgent(boolean useNM, String machineName, String agentName, Map scriptEnv, long timeoutMillis, MessageCallback callback) throws CommandException {
      boolean ret = false;
      if (useNM) {
         ret = nmInvoke(machineName, agentName, scriptEnv, timeoutMillis, callback);
      } else {
         ret = localInvoke(agentName, scriptEnv);
      }

      return ret;
   }
}
