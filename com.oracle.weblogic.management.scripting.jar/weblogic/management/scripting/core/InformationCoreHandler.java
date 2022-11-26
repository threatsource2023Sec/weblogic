package weblogic.management.scripting.core;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.Date;
import java.util.Locale;
import java.util.MissingResourceException;
import weblogic.management.scripting.ScriptException;
import weblogic.management.scripting.WLCoreScriptContext;
import weblogic.management.scripting.core.utils.WLSTCoreUtil;

public class InformationCoreHandler implements Serializable {
   private WLCoreScriptContext ctx;
   private transient FileOutputStream fos = null;
   private transient BufferedOutputStream bos = null;
   private String recordFile = null;
   private transient DataOutputStream dos = null;

   public InformationCoreHandler() {
   }

   public InformationCoreHandler(WLCoreScriptContext ctx) {
      this.ctx = ctx;
   }

   public void help(String methodName) throws ScriptException {
      if (methodName.equals("default1")) {
         this.printDefaultHelp();
      } else {
         this.printHelp(methodName);
      }

   }

   private void printDefaultHelp() throws ScriptException {
      try {
         this.ctx.scriptCoreCmdHelp.printDefaultHelp();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   private void printHelp(String method) throws ScriptException {
      this.ctx.scriptCoreCmdHelp.printHelp(method);
   }

   public void addHelpCommandGroup(String groupName, String resourceBundleName) throws ScriptException {
      try {
         this.ctx.scriptCoreCmdHelp.addHelpCommandGroup(groupName, resourceBundleName);
      } catch (MissingResourceException var4) {
         this.ctx.throwWLSTException(this.ctx.getWLSTMsgFormatter().getErrorGettingResourceBundle(), var4);
      }

   }

   public void addHelpCommand(String commandName, String groupName, String offlineStr, String onlineStr) throws ScriptException {
      boolean offline = false;
      boolean online = false;
      if (offlineStr.toLowerCase(Locale.US).equals("true")) {
         offline = true;
      }

      if (onlineStr.toLowerCase(Locale.US).equals("true")) {
         online = true;
      }

      this.ctx.scriptCoreCmdHelp.addHelpCommand(commandName, groupName, offline, online);
   }

   public void redirect(String outputFile, String toStdOut) throws ScriptException {
      if (this.ctx.redirecting) {
         this.ctx.println(this.ctx.getWLSTMsgFormatter().getAlreadyRedirecting(outputFile, this.ctx.outputFile));
      } else if (outputFile == null) {
         this.ctx.println(this.ctx.getWLSTMsgFormatter().getOutputFileIsNull());
      } else {
         try {
            File f = new File(outputFile);
            boolean bool = this.ctx.getBoolean(toStdOut);
            this.ctx.logToStandardOut = bool;
            PrintStream printStream = new PrintStream(f);
            this.ctx.setSTDOutputMedium(printStream);
            this.ctx.redirecting = true;
            this.ctx.outputFile = outputFile;
         } catch (FileNotFoundException var6) {
            this.ctx.throwWLSTException(this.ctx.getWLSTMsgFormatter().getRedirectFileNotFound(outputFile), var6);
         }

      }
   }

   public void stopRedirect() throws ScriptException {
      try {
         if (this.ctx.redirecting) {
            this.ctx.println(this.ctx.getWLSTMsgFormatter().getStopRedirect(this.ctx.outputFile));
            this.ctx.redirecting = false;
            ((OutputStream)this.ctx.stdOutputMedium).flush();
            ((OutputStream)this.ctx.stdOutputMedium).close();
            this.ctx.stdOutputMedium = null;
            this.ctx.outputFile = null;
            this.ctx.logToStandardOut = true;
         } else {
            this.ctx.println(this.ctx.getWLSTMsgFormatter().getNotRedirecting());
         }
      } catch (IOException var2) {
         this.ctx.throwWLSTException(this.ctx.getWLSTMsgFormatter().getIOExceptionStoppingRedirect(), var2);
      }

   }

   public void startRecording(String filePath, String recordAll) throws ScriptException {
      try {
         if (this.ctx.recording) {
            String msg = this.ctx.getWLSTMsgFormatter().getAlreadyRecording(this.recordFile);
            this.ctx.throwWLSTException(msg);
            return;
         }

         File file = new File(filePath);
         if (file.exists()) {
            if (!file.isFile()) {
               this.ctx.throwWLSTException(this.ctx.getWLSTMsgFormatter().getOutputFileIsDir(filePath));
               return;
            }

            file.delete();
            file.createNewFile();
         } else {
            file.createNewFile();
         }

         this.fos = new FileOutputStream(file);
         this.bos = new BufferedOutputStream(this.fos);
         this.dos = new DataOutputStream(this.bos);
         this.ctx.println(this.ctx.getWLSTMsgFormatter().getStartedRecording(file.getAbsolutePath()));
         this.recordFile = filePath;
         this.dos.writeBytes("# Started recording all user actions at " + new Date() + "\n");
         this.ctx.recording = true;
         this.ctx.getWLSTInterpreter().setRecordingInProgress(true);
         this.ctx.getWLSTInterpreter().setRecordAll(recordAll.equals("true"));
      } catch (Throwable var4) {
         this.ctx.throwWLSTException(this.ctx.getWLSTMsgFormatter().getErrorWhileRecording(), var4);
      }

   }

   public void stopRecording() throws ScriptException {
      try {
         if (!this.ctx.recording) {
            this.ctx.throwWLSTException(this.ctx.getWLSTMsgFormatter().getNotRecording());
            return;
         }

         this.ctx.getWLSTInterpreter().setRecordingInProgress(false);
         this.ctx.getWLSTInterpreter().setRecordAll(false);
         this.ctx.println(this.ctx.getWLSTMsgFormatter().getStoppedRecording(this.recordFile));
         this.dos.writeBytes("\n# Stopped recording at " + new Date() + "\n");
         this.ctx.recording = false;
         this.dos.flush();
         this.dos.close();
         this.bos.flush();
         this.bos.close();
         this.fos.flush();
         this.fos.close();
         DataOutputStream dos = null;
         FileOutputStream fos = null;
         Object var3 = null;
      } catch (Throwable var4) {
         this.ctx.throwWLSTException(this.ctx.getWLSTMsgFormatter().getErrorWhileStoppingRecording(), var4);
      }

   }

   public void writeCommand(String command) throws ScriptException {
      try {
         if (command.length() == 0) {
            return;
         }

         this.dos.writeBytes(command);
         this.dos.writeBytes("\n");
         this.dos.flush();
      } catch (Throwable var3) {
         this.ctx.throwWLSTException(this.ctx.getWLSTMsgFormatter().getErrorWritingCommand(command), var3);
      }

   }

   public void write(String s) {
      try {
         this.writeCommand(s);
      } catch (Throwable var3) {
         this.ctx.errorMsg = this.ctx.getWLSTMsgFormatter().getErrorWritingCommand(s);
         this.ctx.println(this.ctx.errorMsg);
      }

   }

   public void writeIniFile(String filePath) throws ScriptException {
      try {
         File file = new File(filePath);
         if (file.exists()) {
            if (!file.isFile()) {
               this.ctx.throwWLSTException(this.ctx.getWLSTMsgFormatter().getOutputFileIsDir(filePath));
               return;
            }

            file.delete();
            file.createNewFile();
         } else {
            file.createNewFile();
         }

         WLSTCoreUtil.writeWLSTAsModule(file.getAbsolutePath());
         this.ctx.println(this.ctx.getWLSTMsgFormatter().getWroteIniFile(filePath));
      } catch (Throwable var3) {
         this.ctx.throwWLSTException(this.ctx.getWLSTMsgFormatter().getErrorWritingIni(), var3);
      }

   }
}
