package weblogic.nodemanager.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;
import weblogic.admin.plugin.NMComponentTypeChangeList;
import weblogic.admin.plugin.NMMachineChangeList;
import weblogic.nodemanager.NMException;
import weblogic.nodemanager.NodeManagerClientTextFormatter;
import weblogic.nodemanager.ScriptExecutionFailureException;
import weblogic.nodemanager.common.Command;
import weblogic.nodemanager.util.Platform;
import weblogic.utils.StringUtils;

public class ShellClient extends NMClient {
   private Process proc;
   private BufferedWriter out;
   private BufferedReader in;
   private boolean connected;
   private String shellCommand;
   private String script;
   private String scriptDir;
   private boolean hasEnv;
   private boolean writeOutput;
   private String scriptTimeout;
   private ErrDrainer errDrainer;
   int connectTimeout;
   private static boolean debug = false;
   private static final String SCRIPT_SPECIFIER = "-e ";
   private static final String SCRIPTDIR_SPECIFIER = "-l ";
   private static final String SCRIPT_TIMEOUT_SPECIFIER = "-t ";
   private static final String SCRIPT_ENV_SPECIFIER = "-i ";
   private static final String SCRIPT_OUTPUT_SPECIFIER = "-o ";
   private static final String RESPONSE_CODE_PREFIX = "<NodeManager> <ExitCode> ";
   private static final String OUTPUT_PREFIX = "<NodeManager> <Output> ";
   public static final String SHELL_COMMAND_PROP = "weblogic.nodemanager.ShellCommand";
   public static final String SHELL_COMMAND = "wlscontrol.sh -d %D -r %R -s %S %C";
   private static final NodeManagerClientTextFormatter nmText = NodeManagerClientTextFormatter.getInstance();
   private Vector removedOptions;

   public ShellClient() {
      this(System.getProperty("weblogic.nodemanager.ShellCommand", "wlscontrol.sh -d %D -r %R -s %S %C"));
   }

   public ShellClient(String cmd) {
      this.shellCommand = cmd;
   }

   public synchronized String getVersion() throws IOException {
      this.checkConnected(false);
      this.execCmd(Command.VERSION);

      String line;
      String version;
      for(version = null; (line = this.readLine()) != null; version = line) {
      }

      this.checkResponse();
      return version;
   }

   public synchronized String getState(int timeout) throws IOException {
      this.checkConnected(true);
      String result = null;
      NMCommandRunner ncr = new NMCommandRunner(Command.STAT);
      ncr.start();

      try {
         ncr.join((long)timeout);
         if (ncr.isAlive()) {
            throw new IOException(nmText.getOperationTimedOut(Command.STAT.toString()));
         } else {
            IOException ioe = ncr.getIOException();
            if (ioe != null) {
               throw ioe;
            } else {
               result = ncr.getResult();
               return result;
            }
         }
      } catch (InterruptedException var5) {
         throw new IOException(nmText.getOperationInterrupted(Command.STAT.toString()));
      }
   }

   public synchronized String getStates(int timeout) throws IOException {
      return this.getStates(timeout, ' ');
   }

   public synchronized String getStates(int timeout, char separator) throws IOException {
      if (this.domainName == null) {
         throw new IllegalStateException(nmText.getDomainNotSet());
      } else {
         String result = null;
         NMCommandRunner ncr = new NMCommandRunner(Command.GETSTATES, new String[]{String.valueOf(separator)});
         ncr.start();

         try {
            ncr.join((long)timeout);
            if (ncr.isAlive()) {
               throw new IOException(nmText.getOperationTimedOut(Command.GETSTATES.toString()));
            } else {
               IOException ioe = ncr.getIOException();
               if (ioe != null) {
                  throw ioe;
               } else {
                  result = ncr.getResult();
                  return result;
               }
            }
         } catch (InterruptedException var6) {
            throw new IOException(nmText.getOperationInterrupted(Command.GETSTATES.toString()));
         }
      }
   }

   public synchronized void getNMLog(Writer out) throws IOException {
      BufferedWriter br = new BufferedWriter(out);
      br.write(nmText.getCommandNotAvailable(Command.GETNMLOG.toString()));
      br.newLine();
      br.flush();
   }

   public synchronized void getLog(Writer out) throws IOException {
      this.checkConnected(true);
      this.execCmd(Command.GETLOG);
      this.copyTo(new BufferedWriter(out), false);
      this.checkResponse();
   }

   public synchronized void start() throws IOException {
      this.checkConnected(true);
      this.execCmd(Command.START);
      this.checkResponse();
   }

   public synchronized void start(Properties props) throws IOException {
      this.checkConnected(true);
      this.execCmd(Command.STARTP);
      Iterator it = props.keySet().iterator();

      while(it.hasNext()) {
         String name = (String)it.next();
         String value = props.getProperty(name);
         this.writeLine(name + "=" + value);
      }

      this.endOutput();
      this.checkResponse();
   }

   public synchronized void kill() throws IOException {
      this.checkConnected(true);
      this.execCmd(Command.KILL);
      this.checkResponse();
   }

   public synchronized void printThreadDump() throws IOException {
      this.checkConnected(true);
      this.execCmd(Command.PRINTTHREADDUMP);
      this.checkResponse();
   }

   public synchronized String getProgress() throws IOException {
      this.checkConnected(true);
      this.execCmd(Command.PROGRESS);

      String retVal;
      String line;
      for(retVal = ""; (line = this.readLine()) != null; retVal = retVal.concat(line)) {
      }

      return retVal;
   }

   public synchronized void quit() throws IOException {
      this.stdout.println(nmText.getCommandNotAvailable(Command.QUIT.toString()));
      this.stdout.flush();
   }

   protected void checkNotConnected() {
      assert !this.connected;

   }

   public void executeScript(String name, long timeout) throws IOException, ScriptExecutionFailureException {
      this.checkNullOrEmpty(name, nmText.getInvalidScriptName());
      this.script = name;
      this.execCmd(Command.EXECSCRIPT);
      this.checkScriptResponse(name, (Writer)null);
   }

   public int executeScript(String name, String dir, Map env, Writer writer, long timeout) throws IOException, ScriptExecutionFailureException {
      this.checkNullOrEmpty(name, nmText.getInvalidScriptName());
      if (dir == null) {
         dir = "";
      }

      this.hasEnv = env != null && !env.isEmpty();
      this.writeOutput = writer != null;
      this.script = name;
      this.scriptDir = dir;
      this.scriptTimeout = (new Long(timeout)).toString();
      this.execCmd(Command.SCRIPTCALL);
      if (this.hasEnv) {
         Iterator var7 = env.entrySet().iterator();

         while(var7.hasNext()) {
            Map.Entry entry = (Map.Entry)var7.next();
            this.writeLine((String)entry.getKey() + "=" + (String)entry.getValue());
         }
      }

      int rc = this.checkScriptResponse(name, writer);
      return rc;
   }

   public void updateServerProps(Properties props) throws IOException {
   }

   public void chgCred(String newUser, String newPass) throws IOException {
   }

   public void changeList(NMMachineChangeList changes) {
      throw new UnsupportedOperationException();
   }

   public void syncChangeList(NMMachineChangeList changes) {
      throw new UnsupportedOperationException();
   }

   public void validateChangeList(String transaction) {
      throw new UnsupportedOperationException();
   }

   public void commitChangeList(String transaction) {
      throw new UnsupportedOperationException();
   }

   public void rollbackChangeList(String transaction) {
      throw new UnsupportedOperationException();
   }

   public void diagnosticRequest(String type, String[] command, Writer out) throws IOException {
      throw new UnsupportedOperationException();
   }

   public void invocationRequest(String type, String[] command, Writer out) throws IOException {
      throw new UnsupportedOperationException();
   }

   public void invocationRequest(String type, String[] command, OutputStream out) throws IOException {
      throw new UnsupportedOperationException();
   }

   public void softRestart() throws IOException {
      throw new UnsupportedOperationException();
   }

   public void remove() throws IOException {
      throw new UnsupportedOperationException();
   }

   public void initState(int timeout) throws IOException {
   }

   public void initState() throws IOException {
   }

   public NMComponentTypeChangeList getChangeListForAllFiles(String compType, String compName) throws IOException {
      throw new UnsupportedOperationException();
   }

   public byte[] getFile(String compType, String compName, String relativePath) throws IOException {
      throw new UnsupportedOperationException();
   }

   public void restartAll(long restartTimeoutMillis, boolean update) throws IOException {
      throw new UnsupportedOperationException();
   }

   public void restart(long restartTimeoutMillis, boolean update) throws IOException {
      throw new UnsupportedOperationException();
   }

   public void restartAsync(boolean update) throws IOException {
      throw new UnsupportedOperationException();
   }

   public void restartAllAsync(boolean update) throws IOException {
      throw new UnsupportedOperationException();
   }

   public void setShellCommand(String cmd) {
      this.shellCommand = cmd;
   }

   private void checkConnected(boolean serverNeeded) {
      if (!this.connected) {
         if (this.domainName == null) {
            throw new IllegalStateException(nmText.getDomainNotSet());
         }

         if (serverNeeded && this.serverName == null) {
            throw new IllegalStateException(nmText.getServerNotSet());
         }

         this.connected = true;
      }

   }

   private void execCmd(Command cmd) throws IOException {
      this.execCmd(cmd, (String[])null);
   }

   private void execCmd(Command cmd, String[] params) throws IOException {
      String[] cmdArray = this.getCommandLine(cmd, this.shellCommand);
      if (params != null && params.length > 0) {
         int aLen = cmdArray.length;
         int bLen = params.length;
         String[] newCmdArray = new String[aLen + bLen];
         System.arraycopy(cmdArray, 0, newCmdArray, 0, aLen);
         System.arraycopy(params, 0, newCmdArray, aLen, bLen);
         cmdArray = newCmdArray;
      }

      if (this.verbose) {
         this.stdout.println("DEBUG: ShellClient: Executing shell command: " + StringUtils.join(cmdArray, " "));
      }

      this.proc = Runtime.getRuntime().exec(cmdArray);
      this.errDrainer = new ErrDrainer(this.proc.getErrorStream());
      this.errDrainer.start();
   }

   protected String[] getCommandLine(Command cmd, String template) {
      ArrayList cmdLine = new ArrayList();
      this.removedOptions = new Vector();
      String[] args = template.split("\\s");

      for(int i = 0; i < args.length; ++i) {
         cmdLine.add(this.parse(cmd, args[i]));
      }

      String[] ret = (String[])((String[])cmdLine.toArray(new String[cmdLine.size()]));
      if (this.removedOptions.size() > 0) {
         StringBuffer removalPattern = new StringBuffer();
         removalPattern.append("-[");

         for(int indx = 0; indx < this.removedOptions.size(); ++indx) {
            removalPattern.append(this.removedOptions.get(indx));
         }

         removalPattern.append("]");
         String pattern = removalPattern.toString();

         for(int indx = 0; indx < ret.length; ++indx) {
            if (ret[indx].matches(pattern)) {
               ret[indx] = "";
            }
         }
      }

      return ret;
   }

   private String parse(Command cmd, String s) {
      StringBuffer sb = new StringBuffer();
      int i = 0;

      while(true) {
         while(true) {
            while(i < s.length()) {
               int c = s.charAt(i++);
               if (c == '%') {
                  c = s.charAt(i++);
                  switch (c) {
                     case 'C':
                     case 'c':
                        if (this.script != null) {
                           sb.append("-e ");
                           if (this.script.contains(" ") || this.script.contains(";") || this.script.contains("$") || this.script.contains("`") || this.script.contains("\\")) {
                              this.script = "\"" + this.script + "\"";
                           }

                           sb.append(this.script + " ");
                           if (this.scriptDir != null) {
                              sb.append("-l ");
                              sb.append(this.scriptDir + " ");
                           }

                           if (this.hasEnv) {
                              sb.append("-i ");
                           }

                           if (this.scriptTimeout != null) {
                              sb.append("-t ");
                              sb.append(this.scriptTimeout + " ");
                           }

                           if (this.writeOutput) {
                              sb.append("-o ");
                           }
                        }

                        sb.append(cmd.getName());
                        break;
                     case 'D':
                     case 'd':
                        sb.append(this.domainName);
                        break;
                     case 'E':
                     case 'F':
                     case 'G':
                     case 'I':
                     case 'J':
                     case 'K':
                     case 'L':
                     case 'M':
                     case 'O':
                     case 'Q':
                     case 'T':
                     case 'U':
                     case 'V':
                     case 'W':
                     case 'X':
                     case 'Y':
                     case 'Z':
                     case '[':
                     case '\\':
                     case ']':
                     case '^':
                     case '_':
                     case '`':
                     case 'a':
                     case 'b':
                     case 'e':
                     case 'f':
                     case 'g':
                     case 'i':
                     case 'j':
                     case 'k':
                     case 'l':
                     case 'm':
                     case 'o':
                     case 'q':
                     default:
                        sb.append('%');
                        sb.append((char)c);
                        break;
                     case 'H':
                     case 'h':
                        sb.append(this.host);
                        break;
                     case 'N':
                     case 'n':
                        sb.append(Platform.isUnix() ? "\\'" : "\"");
                        sb.append(this.nmDir != null ? this.nmDir : System.getProperty("user.dir"));
                        sb.append(Platform.isUnix() ? "\\'" : "\"");
                        break;
                     case 'P':
                     case 'p':
                        sb.append(Integer.toString(this.port));
                        break;
                     case 'R':
                     case 'r':
                        if (this.domainDir != null) {
                           sb.append(this.domainDir);
                        } else {
                           this.removedOptions.add(new String("Rr"));
                        }
                        break;
                     case 'S':
                     case 's':
                        if (this.serverName != null) {
                           sb.append(Platform.isUnix() ? "\\'" : "\"");
                           sb.append(this.serverName);
                           sb.append(Platform.isUnix() ? "\\'" : "\"");
                        } else {
                           this.removedOptions.add(new String("Ss"));
                        }
                  }
               } else {
                  sb.append((char)c);
               }
            }

            String shellCmd = sb.toString();
            return shellCmd;
         }
      }
   }

   public void done() {
   }

   private int checkScriptResponse(String name, Writer writer) throws IOException {
      String error = null;
      this.endOutput();
      boolean parsedRC = false;
      int scriptRC = 0;

      while(true) {
         String line;
         int rc;
         while((line = this.readLine()) != null) {
            if (line.startsWith("<NodeManager> <ExitCode> ")) {
               try {
                  scriptRC = Integer.parseInt(line.substring("<NodeManager> <ExitCode> ".length()));
                  parsedRC = true;
               } catch (NumberFormatException var12) {
                  throw new IOException(nmText.getUnexpectedResponse(line));
               }
            } else if (line.startsWith("<NodeManager> <Output> ")) {
               try {
                  rc = Integer.parseInt(line.substring("<NodeManager> <Output> ".length()));
                  char[] cbuf = new char[rc];

                  int nread;
                  for(int pos = 0; pos < rc; pos += nread) {
                     nread = this.getReader().read(cbuf, pos, rc - pos);
                  }

                  if (writer != null) {
                     writer.write(new String(cbuf));
                  }
               } catch (NumberFormatException var13) {
                  throw new IOException(nmText.getUnexpectedResponse(line));
               }
            }
         }

         rc = 0;

         try {
            rc = this.proc.waitFor();
            this.errDrainer.join();
         } catch (InterruptedException var11) {
            error = nmText.getIOInterrupted();
         }

         String lastLineErr = this.errDrainer.getLastLine();
         if (lastLineErr != null) {
            error = lastLineErr;
         }

         this.proc.destroy();
         this.proc = null;
         this.out = null;
         this.in = null;
         this.connected = false;
         if (parsedRC) {
            if (scriptRC != 0) {
               throw new ScriptExecutionFailureException(name, rc);
            }

            return scriptRC;
         }

         if (error == null) {
            error = nmText.getUnknownSHError(rc);
         }

         throw new NMException(error);
      }
   }

   private void checkResponse() throws IOException {
      String error = null;
      this.endOutput();

      while(this.readLine() != null) {
      }

      int rc;
      try {
         rc = this.proc.waitFor();
         this.errDrainer.join();
      } catch (InterruptedException var4) {
         throw new IOException(nmText.getIOInterrupted());
      }

      if (rc != 0 || (error = this.errDrainer.getLastLine()) != null) {
         if (rc != 0) {
            error = this.errDrainer.getLastLine();
         }

         if (error == null) {
            error = nmText.getUnknownSHError(rc);
         }
      }

      this.proc.destroy();
      this.proc = null;
      this.out = null;
      this.in = null;
      this.connected = false;
      if (rc == -100) {
         throw new AssertionError("Script is reporting a usage error: -100");
      } else if (error != null) {
         throw new NMException(error);
      }
   }

   private void endOutput() throws IOException {
      if (this.out != null) {
         this.out.flush();
         this.out.close();
         this.out = null;
      }

   }

   private static void p(String msg) {
      if (debug) {
         System.out.println("ShellClient => " + msg);
      }

   }

   private BufferedReader getReader() {
      if (this.in == null) {
         this.in = new BufferedReader(new InputStreamReader(this.proc.getInputStream()));
      }

      return this.in;
   }

   private String readLine() throws IOException {
      String line = this.getReader().readLine();
      if (this.verbose && line != null) {
         this.stdout.println("DEBUG: ShellClient: STDOUT: " + line);
      }

      return line;
   }

   private void writeLine(String line) throws IOException {
      BufferedWriter bw = this.getWriter();
      bw.write(line);
      bw.newLine();
      if (this.verbose) {
         this.stdout.println("DEBUG: ShellClient: STDIN: " + line);
      }

   }

   private BufferedWriter getWriter() {
      if (this.out == null) {
         this.out = new BufferedWriter(new OutputStreamWriter(this.proc.getOutputStream()));
      }

      return this.out;
   }

   private void copyTo(BufferedWriter out, boolean flush) throws IOException {
      String line;
      while((line = this.readLine()) != null) {
         out.write(line);
         out.newLine();
         if (flush) {
            out.flush();
         }
      }

      out.flush();
   }

   public String toString() {
      return "ShellClient(" + System.identityHashCode(this) + ")";
   }

   private class NMCommandRunner extends Thread {
      private Command cmd;
      private String[] params;
      private String result;
      private IOException ioe;

      NMCommandRunner(Command cmd) {
         this(cmd, (String[])null);
      }

      NMCommandRunner(Command cmd, String[] params) {
         this.cmd = cmd;
         this.params = params;
      }

      public void run() {
         try {
            ShellClient.this.execCmd(this.cmd, this.params);

            String line;
            while((line = ShellClient.this.readLine()) != null) {
               this.result = line;
            }

            ShellClient.this.checkResponse();
         } catch (IOException var2) {
            this.ioe = var2;
         } catch (Throwable var3) {
            this.ioe = (IOException)(new IOException("Exception: " + var3.getMessage())).initCause(var3);
         }

      }

      String getResult() {
         return this.result;
      }

      IOException getIOException() {
         return this.ioe;
      }

      void killProcess() {
         ShellClient.this.proc.destroy();
      }

      boolean stillRunning() {
         try {
            int exitCode = ShellClient.this.proc.exitValue();
            return false;
         } catch (IllegalThreadStateException var2) {
            return true;
         }
      }
   }

   private class ErrDrainer extends Thread {
      private BufferedReader in;
      private String lastLine;

      ErrDrainer(InputStream is) {
         this.in = new BufferedReader(new InputStreamReader(is));
      }

      public void run() {
         while(true) {
            try {
               String line;
               if ((line = this.in.readLine()) != null) {
                  if (ShellClient.this.verbose) {
                     ShellClient.this.stdout.println("DEBUG: ShellClient: STDERR: " + line);
                  }

                  this.lastLine = line;
                  continue;
               }
            } catch (IOException var2) {
            }

            return;
         }
      }

      String getLastLine() {
         return this.lastLine;
      }
   }
}
