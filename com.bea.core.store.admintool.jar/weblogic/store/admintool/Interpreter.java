package weblogic.store.admintool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public final class Interpreter {
   private static final String CMD_INFILE = "-infile";
   private static final String CMD_OUTFILE = "-outfile";
   private static final String CMD_VERBOSE = "-verbose";
   private static final String CMD_PROMPT = "-prompt";
   private static final String PROMPT_SUFFIX = "-> ";
   static final String TAB = "  ";
   static final String COMMENT = "#";
   final String[] invocationArgs;
   final BufferedReader reader;
   final PrintWriter writer;
   final PrintStream interpErrOut;
   final String interpName;
   String prompt;
   String promptVal;
   String prevPromptVal;
   boolean promptChanged = false;
   boolean isStdIn;
   boolean isStdOut;
   private boolean isVerbose;
   private final List pendingOutputs;
   private boolean errorOccured;
   private boolean doQuit;
   private final HashMap showControl;

   public Interpreter(String name, String[] args) {
      this.invocationArgs = args;
      this.setVerbose(getParamArg("-verbose", args, false) != null);
      this.interpErrOut = System.err;
      this.interpName = name;
      this.prompt = this.setupPrompt("-prompt", args, name);
      this.reader = this.setupReader("-infile", args);
      this.writer = this.setupWriter("-outfile", args);
      this.pendingOutputs = new ArrayList();
      this.showControl = this.setupShowControl();
   }

   public synchronized String getNextCommand() {
      return this.getNextCommand(true);
   }

   public synchronized String getNextCommand(boolean printPrompt) {
      String inCmd = null;

      try {
         this.flush();

         do {
            if (!this.isStdIn && this.checkForError()) {
               this.fatal("Script execution stopped due to failure of command");
               break;
            }

            if (printPrompt) {
               this.writer.print(this.prompt);
            }

            this.writer.flush();
            inCmd = this.reader.readLine();
            if (inCmd == null) {
               if (this.isStdIn) {
                  this.interpError("Interrupted", Interpreter.MessageType.FATAL, (Throwable)null);
                  this.fatal("Interrupted");
               } else {
                  this.quit("End of script file");
               }
               break;
            }

            if (!this.isStdOut || !this.isStdIn) {
               this.writer.println(inCmd);
            }

            inCmd = inCmd.trim();
         } while(inCmd.length() == 0 || inCmd.startsWith("#"));
      } catch (Exception var7) {
         this.interpError("IO error in interpeter", Interpreter.MessageType.FATAL, var7);
         this.fatal("IO error in interpreter", var7);
         inCmd = null;
      } finally {
         this.flush();
      }

      return inCmd;
   }

   public synchronized boolean checkForError() {
      return this.errorOccured;
   }

   public synchronized void clearError() {
      this.errorOccured = false;
   }

   public synchronized boolean checkForQuit() {
      if (this.doQuit) {
         this.flush();

         try {
            this.reader.close();
            this.writer.close();
         } catch (Exception var2) {
         }
      }

      return this.doQuit;
   }

   public synchronized void flush() {
      if (this.pendingOutputs.size() == 0) {
         this.writer.flush();
      } else {
         Iterator it = this.pendingOutputs.iterator();

         while(it.hasNext()) {
            OutMessage currMsg = (OutMessage)it.next();
            if (currMsg.msg != null) {
               this.writer.println(currMsg.mt + currMsg.msg);
            }

            Throwable t = currMsg.ex;
            if (t != null) {
               if (t.getMessage() != null) {
                  this.writer.println(currMsg.mt.toString() + t.getMessage());
               }

               t = t.getCause();
            }

            if (this.isVerbose && currMsg.ex != null) {
               currMsg.ex.printStackTrace(this.writer);
            }
         }

         this.pendingOutputs.clear();
         this.writer.flush();
      }
   }

   public synchronized void showInfo(boolean x) {
      this.showControl.put(Interpreter.MessageType.INFO, x);
   }

   public synchronized void showWarn(boolean x) {
      this.showControl.put(Interpreter.MessageType.WARN, x);
   }

   public synchronized void showMessage(boolean x) {
      this.showControl.put(Interpreter.MessageType.MESSAGE, x);
   }

   public synchronized void fatal(String msg) {
      this.setOutput(msg, Interpreter.MessageType.FATAL, (Throwable)null);
   }

   public synchronized void fatal(String msg, Throwable t) {
      this.setOutput(msg, Interpreter.MessageType.FATAL, t);
   }

   public synchronized void error(String msg) {
      this.setOutput(msg, Interpreter.MessageType.ERROR, (Throwable)null);
   }

   public synchronized void error(String msg, Throwable t) {
      this.setOutput(msg, Interpreter.MessageType.ERROR, t);
   }

   public synchronized void warn(String msg) {
      this.setOutput(msg, Interpreter.MessageType.WARN, (Throwable)null);
   }

   public synchronized void warn(String msg, Throwable t) {
      this.setOutput(msg, Interpreter.MessageType.WARN, t);
   }

   public synchronized void info(String msg) {
      this.setOutput(msg, Interpreter.MessageType.INFO, (Throwable)null);
   }

   public synchronized void quit(String msg) {
      this.setOutput(msg, Interpreter.MessageType.EXIT, (Throwable)null);
   }

   public synchronized void message(String msg) {
      this.setOutput(msg, Interpreter.MessageType.MESSAGE, (Throwable)null);
   }

   public synchronized void setVerbose(boolean newVerbose) {
      this.isVerbose = newVerbose;
   }

   public synchronized boolean getVerbose() {
      return this.isVerbose;
   }

   public synchronized boolean confirm(String msg) {
      String answer;
      do {
         this.warn(msg + " (y/n)?");
         answer = this.getNextCommand(false).toLowerCase();
      } while(!answer.matches("[yn]|yes|no"));

      if (answer.matches("n|no")) {
         return false;
      } else {
         return true;
      }
   }

   public static String getParamArg(String cmd_prefix, String[] args, boolean wantNextParam) {
      if (args != null && cmd_prefix.startsWith("-")) {
         for(int i = 0; i < args.length; ++i) {
            if (cmd_prefix.equalsIgnoreCase(args[i])) {
               if (!wantNextParam) {
                  return args[i];
               }

               if (i < args.length - 1) {
                  if (args[i + 1].startsWith("-")) {
                     return null;
                  }

                  return args[i + 1];
               }

               return null;
            }
         }

         return null;
      } else {
         return null;
      }
   }

   private synchronized void setOutput(String msg, MessageType mt, Throwable ex) {
      if (mt.equals(Interpreter.MessageType.EXIT) || mt.equals(Interpreter.MessageType.FATAL)) {
         this.doQuit = true;
      }

      if (mt.equals(Interpreter.MessageType.ERROR) || mt.equals(Interpreter.MessageType.FATAL)) {
         this.errorOccured = true;
      }

      if ((Boolean)this.showControl.get(mt)) {
         this.pendingOutputs.add(new OutMessage(msg, mt, ex));
      }

   }

   private BufferedReader setupReader(String cmd_prefix, String[] args) {
      String readin = getParamArg(cmd_prefix, args, true);
      BufferedReader ret = null;
      if (readin != null) {
         try {
            ret = new BufferedReader(new FileReader(readin));
         } catch (FileNotFoundException var6) {
            this.interpError("Unable to locate/read input file " + readin, Interpreter.MessageType.WARN, var6);
         }
      }

      if (ret == null) {
         ret = new BufferedReader(new InputStreamReader(System.in));
         this.isStdIn = true;
      }

      return ret;
   }

   private PrintWriter setupWriter(String cmd_prefix, String[] args) {
      String writeout = getParamArg(cmd_prefix, args, true);
      PrintWriter ret = null;
      if (writeout != null) {
         try {
            ret = new PrintWriter(new BufferedWriter(new FileWriter(writeout)));
         } catch (IOException var6) {
            this.interpError("Unable to locate/open output file " + writeout, Interpreter.MessageType.WARN, var6);
         }
      }

      if (ret == null) {
         ret = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
         this.isStdOut = true;
      }

      return ret;
   }

   public String setupPrompt(String cmd_prefix, String[] args, String defaultPrompt) {
      String ret = getParamArg("-prompt", args, true);
      if (ret == null) {
         ret = defaultPrompt;
      }

      if (this.promptVal == null) {
         this.promptVal = ret;
      } else if (ret.equals(this.prevPromptVal)) {
         this.prevPromptVal = this.promptVal = ret;
         this.promptChanged = false;
      } else {
         this.prevPromptVal = this.promptVal;
         this.promptVal = ret;
         this.promptChanged = true;
      }

      ret = ret + "-> ";
      return ret;
   }

   public boolean isPromptChanged() {
      return this.promptChanged;
   }

   private HashMap setupShowControl() {
      HashMap ret = new HashMap();

      for(int i = 0; i < Interpreter.MessageType.ALL_TYPES.length; ++i) {
         ret.put(Interpreter.MessageType.ALL_TYPES[i], Boolean.TRUE);
      }

      return ret;
   }

   private void interpError(String errMsg, MessageType mt, Throwable t) {
      this.interpErrOut.println(mt.toString() + errMsg);
      if (t != null) {
         if (t.getMessage() != null) {
            this.interpErrOut.println(t.getMessage());
         }

         if (this.isVerbose) {
            t.printStackTrace(this.interpErrOut);
         }
      }

   }

   private static class MessageType {
      private String str;
      static final MessageType INFO = new MessageType("INFO: ");
      static final MessageType WARN = new MessageType("WARN: ");
      static final MessageType ERROR = new MessageType("ERROR: ");
      static final MessageType FATAL = new MessageType("FATAL: ");
      static final MessageType EXIT = new MessageType("EXIT: ");
      static final MessageType MESSAGE = new MessageType("");
      static final MessageType[] ALL_TYPES;

      private MessageType(String str) {
         this.str = str;
      }

      public String toString() {
         return this.str;
      }

      static {
         ALL_TYPES = new MessageType[]{INFO, WARN, ERROR, FATAL, EXIT, MESSAGE};
      }
   }

   private class OutMessage {
      String msg;
      MessageType mt;
      Throwable ex;

      OutMessage(String message, MessageType mt, Throwable ex) {
         this.msg = message;
         this.mt = mt;
         this.ex = ex;
      }
   }
}
