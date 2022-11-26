package weblogic.management.scripting;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Properties;
import org.python.util.InteractiveInterpreter;
import weblogic.management.scripting.core.ExceptionCoreHandler;
import weblogic.management.scripting.core.InformationCoreHandler;
import weblogic.management.scripting.core.NodeManagerCoreService;
import weblogic.management.scripting.core.utils.ScriptCommandsCoreHelp;
import weblogic.management.scripting.core.utils.WLSTCoreUtil;
import weblogic.management.scripting.utils.WLSTInterpreter;
import weblogic.management.scripting.utils.WLSTMsgTextFormatter;
import weblogic.utils.StringUtils;
import weblogic.utils.TypeConversionUtils;

public class WLCoreScriptContext extends WLSTUtils {
   private static final WLSTMsgTextFormatter txtFmt = new WLSTMsgTextFormatter();
   private WLSTInterpreter wlstInterpreter;

   public WLCoreScriptContext() {
      this.nmService = new NodeManagerCoreService(this);
      this.infoHandler = new InformationCoreHandler(this);
      this.scriptCoreCmdHelp = new ScriptCommandsCoreHelp(this);
      this.exceptionHandler = new ExceptionCoreHandler(this);
   }

   public WLSTInterpreter getWLSTInterpreter() {
      return this.wlstInterpreter;
   }

   public void setWLSTInterpreter(WLSTInterpreter interp) {
      this.wlstInterpreter = interp;
   }

   public void debug(String val) throws ScriptException {
      this.commandType = "debug";
      if (val == null) {
         this.debug = !this.debug;
      } else {
         this.debug = this.getBoolean(val);
      }

      if (this.debug) {
         this.println(txtFmt.getDebugOn());
      } else {
         this.println(txtFmt.getDebugOff());
      }

   }

   public boolean isDebug() {
      return this.debug;
   }

   public WLSTMsgTextFormatter getWLSTMsgFormatter() {
      return txtFmt;
   }

   public NodeManagerCoreService getNodeManagerService() {
      return this.nmService;
   }

   public void help(String cmd) throws Throwable {
      this.commandType = "help";
      this.infoHandler.help(cmd);
   }

   public void loadProperties(String fileName, InteractiveInterpreter interp) throws Throwable {
      this.commandType = "loadProperties";
      WLSTCoreUtil.setProperties(fileName, interp);
   }

   public Properties makePropertiesObject(String value) {
      Properties p = new Properties();
      this.commandType = "makePropertiesObject";
      TypeConversionUtils.stringToDictionary(value, p, ";");
      return p;
   }

   public String[] makeArrayObject(String value) {
      this.commandType = "makeArrayObject";
      return StringUtils.splitCompletely(value, ",");
   }

   public void redirect(String outputFile, String toStdOut) throws ScriptException {
      this.commandType = "redirect";
      this.infoHandler.redirect(outputFile, toStdOut);
   }

   public void stopRedirect() throws ScriptException {
      this.commandType = "stopRedirect";
      this.infoHandler.stopRedirect();
   }

   public void startRecording(String filePath, String recordAll) throws Throwable {
      this.commandType = "startRecording";
      this.infoHandler.startRecording(filePath, recordAll);
   }

   public void stopRecording() throws Throwable {
      this.commandType = "stopRecording";
      this.infoHandler.stopRecording();
   }

   public void writeIniFile(String filePath) throws Throwable {
      this.commandType = "writeInifile";
      this.infoHandler.writeIniFile(filePath);
   }

   public void addHelpCommandGroup(String groupName, String resourceBundleName) throws Throwable {
      this.commandType = "addHelpCommandGroup";
      this.infoHandler.addHelpCommandGroup(groupName, resourceBundleName);
   }

   public void addHelpCommand(String commandName, String groupName, String offline, String online) throws Throwable {
      this.commandType = "addHelpCommand";
      this.infoHandler.addHelpCommand(commandName, groupName, offline, online);
   }

   public InformationCoreHandler getInfoHandler() {
      return this.infoHandler;
   }

   public String getCommandType() {
      return this.commandType;
   }

   public void exit(String defaultAnswer, int exitcode) throws Throwable {
      this.commandType = "exit";
      this.println(txtFmt.getExitingWLST());
      System.exit(exitcode);
   }

   public Throwable dumpStack() {
      return this.dumpStack(true);
   }

   public Throwable dumpStack(boolean includeStack) {
      this.commandType = "dumpStack";
      Throwable th = this.stackTrace;
      if (this.stackTrace == null) {
         this.println(txtFmt.getNoStackAvailable());
         return null;
      } else {
         this.println(txtFmt.getExceptionOccurredAt(this.timeAtError));
         if (this.stdOutputMedium != null) {
            if (this.stdOutputMedium instanceof PrintWriter) {
               this.stackTrace.printStackTrace((PrintWriter)this.stdOutputMedium);
            } else if (this.stdOutputMedium instanceof PrintStream) {
               this.stackTrace.printStackTrace((PrintStream)this.stdOutputMedium);
            }
         } else if (includeStack) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            this.stackTrace.printStackTrace(new PrintStream(bos));
            this.println(bos.toString());
         } else {
            this.println(this.stackTrace.toString());
         }

         this.stackTrace = null;
         return th;
      }
   }

   public boolean isStandalone() {
      return this.wlstInterpreter.isStandalone();
   }
}
