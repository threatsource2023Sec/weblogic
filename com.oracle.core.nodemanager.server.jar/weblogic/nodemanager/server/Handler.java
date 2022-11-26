package weblogic.nodemanager.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import weblogic.admin.plugin.ChangeList;
import weblogic.admin.plugin.NMComponentTypeChangeList;
import weblogic.nodemanager.NodeManagerTextTextFormatter;
import weblogic.nodemanager.common.Command;
import weblogic.nodemanager.common.ConfigException;
import weblogic.nodemanager.common.DataFormat;
import weblogic.nodemanager.common.NMInputOutput;
import weblogic.nodemanager.common.NMProtocol;
import weblogic.nodemanager.plugin.ConfigurationPlugin;
import weblogic.nodemanager.plugin.InvocationPlugin;
import weblogic.nodemanager.plugin.MonitoringPlugin;
import weblogic.nodemanager.util.ScriptExecResult;
import weblogic.utils.StringUtils;
import weblogic.utils.progress.internal.AggregateProgressBeanImpl;

class Handler implements Runnable {
   private static final String DNAME_FILL = "__**DNAMEFILL**__";
   private static final String SNAME_FILL = "__**SNAMEFILL**__";
   private static final String EOL_TOKENS = "\n\r\f";
   private static final String[] UNKNOWN_PROGRESS;
   private final NMServer nmServer;
   private final Socket sock;
   private final NMInputOutput ioHandler;
   private DomainManager domainMgr;
   private ServerManagerI serverMgr;
   private String serverType;
   private byte[] nmUser;
   private boolean authorized;
   private Properties runtimeProperties;
   static final String ENCODING = "UTF-8";
   private boolean isCommandComplete;
   private static final Logger nmLog;
   private static final NodeManagerTextTextFormatter nmText;

   Handler(NMServer server, Socket sock) throws IOException {
      this.serverType = "WebLogic";
      this.isCommandComplete = false;
      this.nmServer = server;
      this.sock = sock;
      this.ioHandler = new NMInputOutput(sock.getInputStream(), sock.getOutputStream());
      this.authorized = !server.getConfig().isAuthenticationEnabled();
   }

   Handler(NMServer server, NMInputOutput nmIO) {
      this(server, nmIO, false);
   }

   Handler(NMServer server, NMInputOutput nmIO, boolean isAuthorized) {
      this.serverType = "WebLogic";
      this.isCommandComplete = false;
      this.nmServer = server;
      this.sock = null;
      this.ioHandler = nmIO;
      this.authorized = false;
      if (isAuthorized) {
         this.authorized = isAuthorized;
      } else {
         this.authorized = !server.getConfig().isAuthenticationEnabled();
      }

   }

   public void run() {
      while(true) {
         try {
            String line;
            if ((line = DataFormat.readCommand(this.ioHandler)) != null) {
               try {
                  if (this.handleCommand(line)) {
                     if (this.isCommandComplete) {
                        nmLog.fine("NM Command(s) processing completed");
                     }
                     continue;
                  }
               } catch (Throwable var13) {
                  String err = nmText.getUncaughtHandlerException(getMessage(var13));
                  this.logError(err, var13);
                  this.sendERR(err);
               }
            }
         } catch (IOException var14) {
            this.logAndSendSocketError(this.sock, var14);
         } finally {
            try {
               if (this.sock != null) {
                  this.sock.close();
                  if (this.isDebugEnabled()) {
                     this.debugSay("Closed connection from " + this.sock.getInetAddress());
                  }
               }
            } catch (IOException var12) {
               this.logError(nmText.getErrorClosingSocket(getMessage(var12)), var12);
            }

         }

         return;
      }
   }

   protected void runCommand() {
      try {
         this.handleCommand(DataFormat.readCommand(this.ioHandler));
      } catch (IOException var2) {
         this.logAndSendSocketError(this.sock, var2);
      }

   }

   private void logAndSendSocketError(Socket sock, IOException ioe) {
      if (this.isDebugEnabled()) {
         if (sock != null) {
            this.debugSay("Error reading from socket: " + sock + " ioe: " + ioe, ioe);
         } else {
            this.debugSay("Error reading from socket.ioe: " + ioe, ioe);
         }
      }

      try {
         this.sendERR("Error reading from socket: " + getMessage(ioe));
      } catch (IOException var4) {
      }

   }

   private boolean handleCommand(String line) throws IOException {
      int i = line.indexOf(32);
      String s = i != -1 ? line.substring(0, i) : line;
      if (this.isDebugEnabled()) {
         this.debugSay("---> received command line:[[" + ("PASS".equalsIgnoreCase(s) ? "PASS ******" : line) + "]]");
      }

      Command cmd;
      try {
         cmd = Command.parse(s);
      } catch (IllegalArgumentException var10) {
         cmd = null;
      }

      if (cmd == null) {
         this.sendERR(nmText.getInvalidCommand(s));
         return true;
      } else {
         if (cmd == Command.VERSION) {
            this.handleVersion();
         } else if (cmd == Command.NMPROTOCOL) {
            this.handleNMProtocolNegotiation(line);
         } else if (cmd == Command.DOMAIN) {
            this.handleDomain(line);
         } else if (cmd == Command.SERVER_TYPE) {
            this.handleServerType(line);
         } else if (cmd == Command.SERVER) {
            this.handleServer(line);
         } else if (cmd == Command.COHERENCESERVER) {
            this.serverType = "Coherence";

            try {
               this.handleServer(line);
            } finally {
               this.serverType = "WebLogic";
            }
         } else if (cmd == Command.USER) {
            this.handleUser(line);
         } else if (cmd == Command.PASS) {
            this.handlePass(line);
         } else if (cmd == Command.START) {
            this.handleStart(false);
         } else if (cmd == Command.STARTP) {
            this.handleStart(true);
         } else if (cmd == Command.KILL) {
            this.handleKill();
         } else if (cmd == Command.STAT) {
            this.handleStat();
         } else if (cmd == Command.GETLOG) {
            this.handleGetLog();
         } else if (cmd == Command.GETNMLOG) {
            this.handleGetNMLog();
         } else if (cmd == Command.GETSTATES) {
            this.handleGetStates(line);
         } else if (cmd == Command.EXECSCRIPT) {
            this.handleExecScript(line);
         } else if (cmd == Command.SCRIPTCALL) {
            this.handleScriptCall(line);
         } else if (cmd == Command.HELLO) {
            this.sendGreeting();
         } else if (cmd == Command.QUIT) {
            this.handleQuit(cmd.toString());
         } else if (cmd == Command.CHGCRED) {
            this.handleChgCred(line);
         } else if (cmd == Command.UPDATEPROPS) {
            this.handleUpdateProps();
         } else if (cmd == Command.CHANGELIST) {
            this.handleChangeList(line);
         } else if (cmd == Command.VALIDATE_CHANGELIST) {
            this.handleValidateChangeList(line);
         } else if (cmd == Command.COMMIT_CHANGELIST) {
            this.handleCommitChangeList(line);
         } else if (cmd == Command.ROLLBACK_CHANGELIST) {
            this.handleRollbackChangeList(line);
         } else if (cmd == Command.DIAGNOSTICS) {
            this.handleDiagnostics(line);
         } else if (cmd == Command.INVOCATION) {
            this.handleInvocation(line);
         } else if (cmd == Command.PROPERTIES) {
            this.handleProperties(line);
         } else if (cmd == Command.PUTFILE) {
            this.handlPutFile(line);
         } else if (cmd == Command.SOFTRESTART) {
            this.handleSoftRestart();
         } else if (cmd == Command.SYNC_CHANGELIST) {
            this.handleSyncChangeList(line);
         } else if (cmd == Command.REMOVE) {
            this.handleRemove();
         } else if (cmd == Command.INITSTATE) {
            this.handleInitState();
         } else if (cmd == Command.GETCHANGELIST) {
            this.handleGetChangeList(line);
         } else if (cmd == Command.GETFILE) {
            this.handleGetFile(line);
         } else if (cmd == Command.RESTARTNM) {
            this.handleRestartNM(line);
         } else if (cmd == Command.PRINTTHREADDUMP) {
            this.handlePrintThreadDump();
         } else if (cmd == Command.DONE) {
            this.isCommandComplete = true;
         } else if (cmd == Command.PROGRESS) {
            this.handleGetProgress();
         } else {
            this.sendERR(nmText.getInvalidCommand(cmd.toString()));
         }

         return true;
      }
   }

   public boolean isCommandComplete() {
      return this.isCommandComplete;
   }

   private void handleGetStates(String line) throws IOException {
      if (this.checkDomain() && this.checkAuthorized()) {
         String separator = " ";
         int i = line.indexOf(32);
         if (i++ != -1 && i < line.length()) {
            separator = line.substring(i);
         }

         Map states = this.domainMgr.getAllStates();
         StringBuffer sb = new StringBuffer();
         int loc = 0;
         Iterator var7 = states.entrySet().iterator();

         while(var7.hasNext()) {
            Map.Entry entry = (Map.Entry)var7.next();
            ++loc;
            String name = (String)entry.getKey();
            String state = (String)entry.getValue();
            state = state != null ? state : "UNKNOWN";
            sb.append(name);
            sb.append('=');
            sb.append(state);
            if (loc < states.size()) {
               sb.append(separator);
            }
         }

         String result = sb.toString();
         this.sendOK(result);
         if (this.isDebugEnabled()) {
            this.debugSay("Sent statuses: [" + result + "]");
         }

      }
   }

   private void handleVersion() throws IOException {
      if (this.checkDomain() && this.checkAuthorized()) {
         this.sendOK("14.1.1.0.0");
      }
   }

   private void handleNMProtocolNegotiation(String line) throws IOException {
      String protocolVersion = null;
      int i = line.indexOf(32);
      if (i++ != -1 && i < line.length()) {
         protocolVersion = line.substring(i);
      }

      if (protocolVersion == null) {
         this.sendERR(nmText.getInvalidCommandSyntax(Command.NMPROTOCOL.toString()));
      } else {
         try {
            NMProtocol protocol = NMProtocol.valueOf(protocolVersion);
            this.ioHandler.setNMProtocolVersion(protocol);
         } catch (IllegalArgumentException var6) {
            NMProtocol protocol = NMProtocol.getLatestVersion();
            this.ioHandler.setNMProtocolVersion(protocol);
            protocolVersion = protocol.name();
         }

         this.sendOK(protocolVersion);
      }
   }

   private void handleDomain(String line) throws IOException {
      String name = null;
      String path = null;
      int i = line.indexOf(32);
      if (i++ != -1 && i < line.length()) {
         int j = line.indexOf(32, i);
         if (j == -1) {
            name = line.substring(i);
         } else if (j < line.length() - 1) {
            name = line.substring(i, j);
            path = line.substring(j + 1);
         }
      }

      if (name == null) {
         this.sendERR(nmText.getInvalidCommandSyntax(Command.DOMAIN.toString()));
      } else {
         String err;
         try {
            this.domainMgr = this.nmServer.getDomainManager(name, path);
            this.sendOK(nmText.getSetDomainMsg(name));
         } catch (ConfigException var7) {
            err = nmText.getDomainError(name, getMessage(var7));
            this.logError(err, var7);
            this.sendERR(err);
         } catch (IOException var8) {
            err = nmText.getDomainIOError(name, getMessage(var8));
            this.logError(err, var8);
            this.sendERR(err);
         } catch (Throwable var9) {
            this.sendUnexpectedError(Command.DOMAIN, var9);
         }

      }
   }

   private void handleQuit(String command) throws IOException {
      if (this.checkDomain() && this.checkAuthorized()) {
         if (this.nmServer.getConfig().getQuitEnabled()) {
            this.sendOK(nmText.getQuitMsg());
            this.nmServer.exit();
         } else {
            this.sendERR(nmText.getDisabledCommand(command));
         }

      }
   }

   private void handleRestartNM(String line) throws IOException {
      if (this.checkDomain() && this.checkAuthorized()) {
         String updateStr = null;
         String allStr = null;
         int i = line.indexOf(32);
         if (i++ != -1 && i < line.length()) {
            int j = line.indexOf(32, i);
            if (j != -1 && j < line.length() - 1) {
               updateStr = line.substring(i, j);
               allStr = line.substring(j + 1);
            }
         }

         if (updateStr != null && allStr != null) {
            boolean update = Boolean.valueOf(updateStr);
            boolean restartAll = Boolean.valueOf(allStr);
            if (restartAll) {
               try {
                  this.domainMgr.shutdownAllForRestart();
               } catch (IOException var10) {
                  String err = nmText.getRestartAllFailed(this.domainMgr.getDomainName(), getMessage(var10));
                  this.logError(err, var10);
                  this.sendERR(err);
                  return;
               }
            }

            try {
               this.sendOK(nmText.getRestartMsg());
            } catch (IOException var9) {
            }

            this.nmServer.restart(update);
         } else {
            this.sendERR(nmText.getInvalidCommandSyntax(Command.RESTARTNM.toString()));
         }
      }
   }

   private void handleChgCred(String line) throws IOException {
      if (this.checkDomain() && this.checkAuthorized()) {
         String newUser = null;
         String newPass = null;
         int i = line.indexOf(32);
         if (i++ != -1 && i < line.length()) {
            int j = line.indexOf(32, i);
            if (j != -1 && j < line.length() - 1) {
               newUser = line.substring(i, j);
               newPass = line.substring(j + 1);
            }
         }

         if (newUser != null && newPass != null) {
            try {
               this.domainMgr.resetCredentials(newUser, newPass);
               this.sendOK(nmText.getDomainCredChg(this.domainMgr.getDomainName()));
            } catch (IOException var7) {
               String err = nmText.getDomainCredChgFailed(this.domainMgr.getDomainName(), getMessage(var7));
               this.logError(err, var7);
               this.sendERR(err);
            } catch (Throwable var8) {
               this.sendUnexpectedError(Command.CHGCRED, var8);
            }

         } else {
            this.sendERR(nmText.getInvalidCommandSyntax(Command.CHGCRED.toString()));
         }
      }
   }

   private void handleUpdateProps() throws IOException {
      if (this.isDebugEnabled()) {
         this.debugSay("Updating server '" + this.serverMgr.getServerName() + "' startup properties");
      }

      if (this.checkServer()) {
         Properties props = null;
         props = new Properties();
         DataFormat.readProperties(this.ioHandler, props);

         try {
            this.serverMgr.saveStartupConfig(props);
            this.sendOK(nmText.getSrvrPropsUpdate(this.serverMgr.getServerName()));
         } catch (Throwable var4) {
            String err = nmText.getErrorWritingConfig(this.serverMgr.getServerName(), getMessage(var4));
            this.logError(err, var4);
            this.sendERR(err);
         }

      }
   }

   private void handleServerType(String line) throws IOException {
      if (this.checkDomain() && this.checkAuthorized()) {
         String type = null;
         int i = line.indexOf(32);
         if (i++ != -1 && i < line.length()) {
            type = line.substring(i);
         }

         if (type == null) {
            this.sendERR(nmText.getInvalidCommandSyntax(Command.SERVER_TYPE.toString()));
         } else {
            this.serverType = type;
            this.sendOK(nmText.getSrvrTypeMsg(type));
         }
      }
   }

   private void handleServer(String line) throws IOException {
      this.serverMgr = null;
      if (this.checkDomain() && this.checkAuthorized()) {
         String name = null;
         int i = line.indexOf(32);
         if (i++ != -1 && i < line.length()) {
            name = line.substring(i);
         }

         if (name == null) {
            this.sendERR(nmText.getInvalidCommandSyntax(Command.SERVER.toString()));
         } else {
            String err;
            try {
               this.serverMgr = this.domainMgr.getServerManager(name, this.serverType);
               this.sendOK(nmText.getSrvrMsg(name));
            } catch (ConfigException var6) {
               err = nmText.getServerDirError(name, this.serverType, getMessage(var6));
               this.logError(err, var6);
               this.sendERR(err);
            } catch (IOException var7) {
               err = nmText.getServerDirIOError(name, this.serverType, getMessage(var7));
               this.logError(err, var7);
               this.sendERR(err);
            } catch (Throwable var8) {
               this.sendUnexpectedError(Command.SERVER, var8);
            }

         }
      }
   }

   private void handleUser(String line) throws IOException {
      if (this.checkDomain()) {
         String user = null;
         int i = line.indexOf(32);
         if (i++ != -1 && i < line.length()) {
            user = line.substring(i);
         }

         if (user == null) {
            this.sendERR(nmText.getInvalidCommandSyntax(Command.USER.toString()));
         } else {
            this.nmUser = user.getBytes("UTF-8");
            this.sendOK(nmText.getNMUserMsg(user));
         }
      }
   }

   private void handlePass(String line) throws IOException {
      if (this.checkDomain()) {
         if (this.nmUser == null) {
            this.sendERR(nmText.getPassError());
         } else {
            String pass = null;
            int i = line.indexOf(32);
            if (i++ != -1 && i < line.length()) {
               pass = line.substring(i);
            }

            if (pass == null) {
               this.sendERR(nmText.getInvalidCommandSyntax(Command.PASS.toString()));
            } else if (!this.domainMgr.isAuthorized(new String(this.nmUser, "UTF-8"), pass)) {
               this.sendERR(nmText.getAuthError(this.domainMgr.getDomainName(), new String(this.nmUser, "UTF-8")));
            } else {
               this.authorized = true;
               this.sendOK(nmText.getPassMsg());
            }
         }
      }
   }

   private void handleGetLog() throws IOException {
      if (this.checkServer()) {
         if (!"WebLogic".equals(this.serverType) && !"Coherence".equals(this.serverType)) {
            DataFormat.writeEOS(this.ioHandler);
            this.sendERR(nmText.IllegalServerTypeForNMCommand(Command.GETLOG.getName(), this.serverType, "WebLogic, Coherence"));
         } else {
            BufferedInputStream bis = null;

            try {
               try {
                  File f = this.serverMgr.getServerDir().getOutFile();

                  try {
                     bis = new BufferedInputStream(new FileInputStream(f));
                  } catch (FileNotFoundException var10) {
                     DataFormat.writeEOS(this.ioHandler);
                     if (this.isDebugEnabled()) {
                        this.debugSay(Command.GETLOG + ": Detailed exception: " + var10, var10);
                     }

                     this.sendERR(nmText.getOutputLogNotFound(this.serverMgr.getServerName(), this.serverType, f.toString()));
                     return;
                  }

                  try {
                     this.ioHandler.copy(bis);
                  } catch (Throwable var11) {
                     String err = nmText.getUnexpectedCommandFailure(Command.GETLOG.getName(), getMessage(var11));
                     this.logError(err, var11);
                     DataFormat.writeEOS(this.ioHandler);
                     return;
                  }

                  this.sendOK(nmText.getServerLogFile());
                  if (this.isDebugEnabled()) {
                     this.debugSay("Sent server '" + this.serverMgr.getServerName() + "' output log file");
                     return;
                  }
               } catch (Throwable var12) {
                  this.sendUnexpectedError(Command.GETLOG, var12);
               }

            } finally {
               this.close(bis);
            }
         }
      }
   }

   private void handleGetNMLog() throws IOException {
      if (this.checkDomain() && this.checkAuthorized()) {
         long currentLogSize = -1L;
         File f = null;

         BufferedInputStream bis;
         try {
            f = new File(this.nmServer.getConfig().getLogFile());
            bis = new BufferedInputStream(new FileInputStream(f));
            currentLogSize = f.length();
         } catch (FileNotFoundException var12) {
            DataFormat.writeEOS(this.ioHandler);
            if (this.isDebugEnabled()) {
               this.debugSay(Command.GETNMLOG + ": Detailed exception: " + var12, var12);
            }

            this.sendERR("Node manager log file not found");
            return;
         }

         try {
            if (currentLogSize > 0L) {
               this.ioHandler.copy(bis, currentLogSize);
            } else {
               this.ioHandler.copy(bis);
            }
         } catch (IOException var10) {
            this.logError(nmText.getUnexpectedCommandFailure(Command.GETNMLOG.getName(), getMessage(var10)), var10);
            DataFormat.writeEOS(this.ioHandler);
         } finally {
            this.close(bis);
         }

         this.sendOK(nmText.getNMLogFile());
         if (this.isDebugEnabled()) {
            this.debugSay("Sent NodeManager log file");
         }

      }
   }

   private void handleStat() throws IOException {
      if (this.checkServer()) {
         try {
            String state = this.serverMgr.getState();
            state = state != null ? state : "UNKNOWN";
            this.sendOK(state);
            if (this.isDebugEnabled()) {
               this.debugSay("Sent status on server '" + this.serverMgr.getServerName() + "' : " + state);
            }
         } catch (Throwable var2) {
            this.sendUnexpectedError(Command.STAT, var2);
         }

      }
   }

   private void handleExecScript(String line) throws IOException {
      if (this.checkDomain() && this.checkAuthorized()) {
         String path = null;
         String timeout = null;
         int i = line.indexOf(32);
         if (i++ != -1 && i < line.length()) {
            int j = line.indexOf(32, i);
            if (j == -1) {
               path = line.substring(i);
            } else if (j < line.length() - 1) {
               path = line.substring(i, j);
               timeout = line.substring(j + 1);
            }
         }

         if (path == null) {
            this.sendERR(nmText.getInvalidCommandSyntax(Command.EXECSCRIPT.toString()));
         } else {
            long timeoutValue = 0L;

            try {
               if (timeout != null) {
                  timeoutValue = Long.parseLong(timeout);
               }

               this.commonScriptExec(path, (String)null, false, timeoutValue, (Map)null);
            } catch (Throwable var9) {
               String err = nmText.getScriptError(getMessage(var9));
               this.logError(err, var9);
               this.sendERR(err + ": " + getMessage(var9));
            }

         }
      }
   }

   private void handleScriptCall(String line) throws IOException {
      if (this.checkDomain() && this.checkAuthorized()) {
         String path = null;
         String env = null;
         String output = null;
         String timeout = null;
         String dir = null;
         StringTokenizer st = new StringTokenizer(line, " ");

         try {
            String commandName = st.nextToken();
            path = st.nextToken();
            dir = st.nextToken();
            env = st.nextToken();
            output = st.nextToken();
            timeout = st.nextToken();
         } catch (NoSuchElementException var14) {
         }

         if (path != null && dir != null && env != null && output != null && timeout != null) {
            if (dir.equals("dirPath=")) {
               dir = null;
            } else {
               dir = dir.substring("dirPath=".length());
            }

            boolean hasEnv = Boolean.parseBoolean(env);
            boolean writeOutput = Boolean.parseBoolean(output);

            try {
               long timeoutValue = Long.parseLong(timeout);
               Map passedEnv = null;
               if (hasEnv) {
                  passedEnv = new HashMap();
                  DataFormat.readPropMap(this.ioHandler, passedEnv);
               }

               this.commonScriptExec(path, dir, writeOutput, timeoutValue, passedEnv);
            } catch (Throwable var13) {
               String err = nmText.getScriptError(getMessage(var13));
               this.logError(err, var13);
               this.sendERR(err + ": " + getMessage(var13));
            }

         } else {
            this.sendERR(nmText.getInvalidCommandSyntax(Command.SCRIPTCALL.toString()));
         }
      }
   }

   private void commonScriptExec(String path, String dir, boolean writeOutput, long timeoutValue, Map passedEnv) throws IOException {
      ScriptExecResult res = this.domainMgr.executeScript(path, dir, passedEnv, timeoutValue);
      if (res.getScriptResponseCode() != 0) {
         this.sendScriptERR(res.getScriptResponseCode());
      } else {
         this.sendOK(res.getScriptResponseCode() + " " + nmText.getScriptMsg(path));
      }

      if (writeOutput) {
         this.ioHandler.copy(res.getScriptOutput());
      } else {
         nmLog.info(nmText.getScriptMsg(path));
         BufferedReader br = new BufferedReader(new InputStreamReader(res.getScriptOutput()));

         String out;
         while((out = br.readLine()) != null) {
            nmLog.info(out);
         }
      }

   }

   private void handleStart(boolean saveProps) throws IOException {
      if (this.checkServer()) {
         try {
            Properties props = null;
            if (saveProps) {
               props = new Properties();
               DataFormat.readProperties(this.ioHandler, props);
            }

            boolean started = this.serverMgr.start(props, this.runtimeProperties);
            if (started) {
               this.sendOK(nmText.getServerStartedMsg(this.serverMgr.getServerName(), this.serverType));
            } else {
               String err = nmText.getServerAlreadyRunningOrStarting(this.serverMgr.getServerName(), this.serverType);
               this.sendERR(err);
            }
         } catch (Throwable var5) {
            String err = nmText.getServerStartError(this.serverMgr.getServerName(), this.serverType, getMessage(var5));
            this.logError(err, var5);
            this.sendERR(err);
         }

      }
   }

   private void handleKill() throws IOException {
      if (this.checkServer()) {
         String err;
         try {
            if (this.isDebugEnabled()) {
               this.debugSay("Killing server " + this.serverMgr.getServerName());
            }

            boolean killed = this.serverMgr.kill(this.runtimeProperties);
            if (killed) {
               this.sendOK(nmText.getServerKilled(this.serverMgr.getServerName(), this.serverType));
            } else {
               err = nmText.getServerStopped(this.serverMgr.getServerName(), this.serverType);
               this.sendERR(err);
            }
         } catch (Throwable var3) {
            err = nmText.getServerStopError(this.serverMgr.getServerName(), this.serverType, getMessage(var3));
            this.logError(err, var3);
            this.sendERR(err);
         }

      }
   }

   private void handlePrintThreadDump() throws IOException {
      if (this.checkServer()) {
         try {
            if (this.isDebugEnabled()) {
               this.debugSay("Print thread dump of server " + this.serverMgr.getServerName());
            }

            this.serverMgr.printThreadDump(this.runtimeProperties);
            this.sendOK(nmText.getServerPrintThreadDump(this.serverMgr.getServerName()));
         } catch (Throwable var3) {
            String err = nmText.getUnexpectedCommandFailure(Command.PRINTTHREADDUMP.getName(), getMessage(var3));
            this.logError(err, var3);
            this.sendERR(err);
         }

      }
   }

   private static void updateProgressFill(String[] lines, String fillString, String replacement) {
      for(int lcv = 0; lcv < lines.length; ++lcv) {
         String line = lines[lcv];
         if (line.contains(fillString)) {
            lines[lcv] = line.replace(fillString, replacement);
            return;
         }
      }

   }

   private static String[] getUnknownProgress(String domainName, String serverName, String serverDisposition) {
      String[] unknownProgress = new String[UNKNOWN_PROGRESS.length];
      System.arraycopy(UNKNOWN_PROGRESS, 0, unknownProgress, 0, UNKNOWN_PROGRESS.length);
      updateProgressFill(unknownProgress, "__**DNAMEFILL**__", domainName);
      updateProgressFill(unknownProgress, "__**SNAMEFILL**__", serverName);
      updateProgressFill(unknownProgress, "__**FILL**__", serverDisposition);
      return unknownProgress;
   }

   private void handleGetProgress() throws IOException {
      if (this.checkServer()) {
         try {
            if (this.isDebugEnabled()) {
               this.debugSay("getProgress of server " + this.serverMgr.getServerName());
            }

            String result = this.serverMgr.getProgress(this.runtimeProperties, this.nmServer.getConfig().getProgressTrackerInitialDataTimeout());
            String[] resultAsLines = StringUtils.splitCompletely(result, "\n\r\f");
            updateProgressFill(resultAsLines, "__**FILL**__", this.getServerState());
            DataFormat.writeMultiLineCommand(this.ioHandler, resultAsLines);
         } catch (Throwable var4) {
            String err = nmText.getUnexpectedCommandFailure(Command.PROGRESS.getName(), getMessage(var4));
            this.logError(err, var4);
            String[] resultAsLines = getUnknownProgress(this.serverMgr.getDomainManager().getDomainName(), this.serverMgr.getServerName(), this.getServerState());
            DataFormat.writeMultiLineCommand(this.ioHandler, resultAsLines);
         }

      }
   }

   private String getServerState() {
      String state = null;

      try {
         state = this.serverMgr.getState();
      } catch (Throwable var3) {
      }

      return state == null ? "UNKNOWN" : state;
   }

   private void handleSyncChangeList(String line) throws IOException {
      if (this.checkDomain() && this.checkAuthorized()) {
         int i = line.indexOf(32);
         if (i++ != -1 && i < line.length()) {
            String trans = line.substring(i);

            try {
               ChangeList cl = new ChangeList();
               ArrayList componentNames = new ArrayList();
               DataFormat.readChangeList(this.ioHandler, cl, componentNames);
               String[] filesToGet = this.getConfigurationManager().syncChangeList(trans, cl, componentNames);
               this.sendOK(nmText.getChangeListMsg(trans));
               this.ioHandler.writeObject(filesToGet);
            } catch (Throwable var7) {
               this.sendUnexpectedError(Command.SYNC_CHANGELIST, var7);
            }

         } else {
            this.sendERR(nmText.getInvalidCommandSyntax(Command.SYNC_CHANGELIST.toString()));
         }
      }
   }

   private void handleChangeList(String line) throws IOException {
      if (this.checkDomain() && this.checkAuthorized()) {
         int i = line.indexOf(32);
         if (i++ != -1 && i < line.length()) {
            String trans = line.substring(i);

            try {
               ChangeList cl = new ChangeList();
               ArrayList componentNames = new ArrayList();
               DataFormat.readChangeList(this.ioHandler, cl, componentNames);
               String[] filesToGet = this.getConfigurationManager().changeList(trans, cl, componentNames);
               this.sendOK(nmText.getChangeListMsg(trans));
               this.ioHandler.writeObject(filesToGet);
            } catch (Throwable var7) {
               this.sendUnexpectedError(Command.CHANGELIST, var7);
            }

         } else {
            this.sendERR(nmText.getInvalidCommandSyntax(Command.CHANGELIST.toString()));
         }
      }
   }

   private void handlPutFile(String line) throws IOException {
      if (this.checkDomain() && this.checkAuthorized()) {
         int i = line.indexOf(32);
         if (i++ != -1 && i < line.length()) {
            int j = line.indexOf(32, i);
            if (j != -1 && j + 1 != line.length()) {
               String trans = line.substring(i, j);
               String fileName = line.substring(j + 1);
               BufferedOutputStream out = null;
               boolean deleteFileFromError = false;
               File file = null;

               label117: {
                  try {
                     String parent;
                     try {
                        File stagingDir = this.getConfigurationManager().getStagingDir(trans);
                        file = new File(stagingDir, fileName);
                        parent = file.getParent();
                        if (parent != null) {
                           File dir = new File(parent);
                           dir.mkdirs();
                        }

                        out = new BufferedOutputStream(new FileOutputStream(file));
                        this.ioHandler.copy(out);
                        break label117;
                     } catch (Throwable var14) {
                        parent = nmText.getPutFileErrorMsg(file == null ? fileName : file.getAbsolutePath(), getMessage(var14));
                        this.logError(parent, var14);
                        this.sendERR(parent);
                     }
                  } finally {
                     this.close(out);
                     if (file != null && deleteFileFromError) {
                        file.delete();
                     }

                  }

                  return;
               }

               this.sendOK(nmText.getPutFileMsg(trans, fileName));
            } else {
               this.sendERR(nmText.getInvalidCommandSyntax(Command.PUTFILE.toString()));
            }
         } else {
            this.sendERR(nmText.getInvalidCommandSyntax(Command.PUTFILE.toString()));
         }
      }
   }

   private void handleGetChangeList(String line) throws IOException {
      if (this.checkDomain() && this.checkAuthorized()) {
         int i = line.indexOf(32);
         String compName = i == -1 ? null : line.substring(i + 1);

         try {
            NMComponentTypeChangeList changeList = this.getConfigurationManager().getChangeList(compName);
            if (changeList == null) {
               ChangeList changes = new ChangeList();
               changeList = new NMComponentTypeChangeList(new String[]{compName}, changes);
            }

            this.sendOK(nmText.msgForGetChangeList(this.serverType));
            DataFormat.writeNMComponentTypeChangeList(this.ioHandler, changeList);
         } catch (Throwable var6) {
            this.sendUnexpectedError(Command.GETCHANGELIST, var6);
         }

      }
   }

   private void handleGetFile(String line) throws IOException {
      if (this.checkDomain() && this.checkAuthorized()) {
         int i1 = line.indexOf(" ");
         int i2 = i1 == -1 ? -1 : line.indexOf(" ", i1 + 1);
         String compName = null;
         String filePath = null;
         if (i1 != -1 && i2 != -1) {
            compName = line.substring(i1 + 1, i2);
            filePath = line.substring(i2 + 1);
         }

         if (compName != null && filePath != null) {
            FileInputStream fis = null;

            try {
               fis = new FileInputStream(this.getConfigurationManager().getFile(compName, filePath));
               this.sendOK(nmText.msgForGetFile("[" + compName + "] " + filePath));
               this.ioHandler.copy(fis);
            } catch (Throwable var11) {
               var11.printStackTrace();
               this.sendUnexpectedError(Command.GETFILE, var11);
            } finally {
               if (fis != null) {
                  fis.close();
               }

            }

         } else {
            this.sendERR(nmText.getInvalidCommandSyntax(Command.GETFILE.toString()));
         }
      }
   }

   private void handleValidateChangeList(String line) throws IOException {
      if (this.checkDomain() && this.checkAuthorized()) {
         int i = line.indexOf(32);
         if (i++ != -1 && i < line.length()) {
            String trans = line.substring(i);

            try {
               Iterator var4 = this.getConfigurationManagers().iterator();

               while(var4.hasNext()) {
                  ConfigurationManager cm = (ConfigurationManager)var4.next();
                  cm.validateChangeList(trans);
               }

               this.sendOK(nmText.getValidateChangeListMsg(trans));
            } catch (ConfigurationPlugin.ValidationException var6) {
               String err = nmText.getValidateChangeListErrorMsg(trans, getMessage(var6));
               this.logError(err, var6);
               this.sendERR(err);
            } catch (Throwable var7) {
               this.sendUnexpectedError(Command.VALIDATE_CHANGELIST, var7);
            }

         } else {
            this.sendERR(nmText.getInvalidCommandSyntax(Command.VALIDATE_CHANGELIST.toString()));
         }
      }
   }

   private void handleCommitChangeList(String line) throws IOException {
      if (this.checkDomain() && this.checkAuthorized()) {
         int i = line.indexOf(32);
         if (i++ != -1 && i < line.length()) {
            String trans = line.substring(i);

            try {
               Iterator var4 = this.getConfigurationManagers().iterator();

               while(var4.hasNext()) {
                  ConfigurationManager cm = (ConfigurationManager)var4.next();
                  cm.commitChangeList(trans);
               }

               this.sendOK(nmText.getCommitChangeListMsg(trans));
            } catch (Throwable var6) {
               this.sendUnexpectedError(Command.COMMIT_CHANGELIST, var6);
            }

         } else {
            this.sendERR(nmText.getInvalidCommandSyntax(Command.COMMIT_CHANGELIST.toString()));
         }
      }
   }

   private void handleRollbackChangeList(String line) throws IOException {
      if (this.checkDomain() && this.checkAuthorized()) {
         int i = line.indexOf(32);
         if (i++ != -1 && i < line.length()) {
            String trans = line.substring(i);

            try {
               Iterator var4 = this.getConfigurationManagers().iterator();

               while(var4.hasNext()) {
                  ConfigurationManager cm = (ConfigurationManager)var4.next();
                  cm.rollbackChangeList(trans);
               }

               this.sendOK(nmText.getRollbackChangeListMsg(trans));
            } catch (Throwable var6) {
               this.sendUnexpectedError(Command.ROLLBACK_CHANGELIST, var6);
            }

         } else {
            this.sendERR(nmText.getInvalidCommandSyntax(Command.ROLLBACK_CHANGELIST.toString()));
         }
      }
   }

   private void handleDiagnostics(String line) throws IOException {
      if (this.checkDomain() && this.checkAuthorized()) {
         int i = line.indexOf(32);
         if (i++ != -1 && i < line.length()) {
            String type = line.substring(i);
            Writer writer = null;

            try {
               String[] command = DataFormat.readMultiLineCommand(this.ioHandler);
               writer = new OutputStreamWriter(this.ioHandler.getOutputStream());
               this.getMonitoringPlugin().diagnosticsRequest(type, command, writer);
               this.close(writer);
               this.sendOK(nmText.getDiagnosticsMsg(type));
            } catch (Throwable var9) {
               this.sendUnexpectedError(Command.DIAGNOSTICS, var9);
            } finally {
               this.close(writer);
            }

         } else {
            this.sendERR(nmText.getInvalidCommandSyntax(Command.DIAGNOSTICS.toString()));
         }
      }
   }

   private void handleInvocation(String line) throws IOException {
      if (this.checkDomain() && this.checkAuthorized()) {
         int i = line.indexOf(32);
         if (i++ != -1 && i < line.length()) {
            String type = line.substring(i);
            OutputStream out = null;

            try {
               try {
                  String[] command = DataFormat.readMultiLineCommand(this.ioHandler);
                  out = this.ioHandler.getOutputStream();

                  InvocationPlugin plugin;
                  try {
                     plugin = this.getInvocationPlugin(type);
                  } catch (Throwable var13) {
                     String err = nmText.getUnexpectedCommandFailure(Command.INVOCATION.getName(), getMessage(var13));
                     this.sendERR(err);
                     return;
                  }

                  plugin.invocationRequest(command, out);
                  this.close(out);
                  this.sendOK(nmText.getInvocationMsg(type));
               } catch (Throwable var14) {
                  this.sendUnexpectedError(Command.INVOCATION, var14);
               }

            } finally {
               this.close(out);
            }
         } else {
            this.sendERR(nmText.getInvalidCommandSyntax(Command.INVOCATION.toString()));
         }
      }
   }

   private void handleProperties(String line) throws IOException {
      Properties props = new Properties();
      DataFormat.readProperties(this.ioHandler, props);
      if (!props.isEmpty()) {
         this.runtimeProperties = props;
      }

      this.sendOK(nmText.getPropertiesMsg());
   }

   private void handleSoftRestart() throws IOException {
      if (this.checkServer()) {
         String err;
         try {
            if (this.isDebugEnabled()) {
               this.debugSay("softRestarting server " + this.serverMgr.getServerName());
            }

            boolean restarted = this.serverMgr.softRestart(this.runtimeProperties);
            if (restarted) {
               this.sendOK(nmText.getServerSoftRestart(this.serverMgr.getServerName(), this.serverType));
            } else {
               err = nmText.getServerStopped(this.serverMgr.getServerName(), this.serverType);
               this.sendERR(err);
            }
         } catch (UnsupportedOperationException var3) {
            err = nmText.softRestartUnsupported(this.serverMgr.getServerName(), this.serverType);
            this.logError(err, var3);
            this.sendERR(err);
         } catch (Throwable var4) {
            err = nmText.getSoftRestartError(this.serverMgr.getServerName(), this.serverType, getMessage(var4));
            this.logError(err, var4);
            this.sendERR(err);
         }

      }
   }

   private void handleRemove() throws IOException {
      if (this.checkServer()) {
         if (this.isDebugEnabled()) {
            this.debugSay("removing server " + this.serverMgr.getServerName());
         }

         try {
            this.serverMgr.remove();
            this.domainMgr.removeServerManager(this.serverMgr.getServerName(), this.serverType);
            this.sendOK(nmText.getRemoveInstance(this.serverMgr.getServerName(), this.serverType));
         } catch (Throwable var3) {
            String err = nmText.getRemoveError(this.serverMgr.getServerName(), this.serverType, getMessage(var3));
            this.logError(err, var3);
            this.sendERR(err);
         }

      }
   }

   private void handleInitState() throws IOException {
      if (this.checkServer()) {
         if (this.isDebugEnabled()) {
            this.debugSay("initstate for server " + this.serverMgr.getServerName());
         }

         try {
            this.serverMgr.initState();
            this.sendOK(nmText.getInitState(this.serverMgr.getServerName(), this.serverType));
         } catch (IOException var3) {
            String err = nmText.getInitStateError(this.serverMgr.getServerName(), this.serverType, getMessage(var3));
            this.logError(err, var3);
            this.sendERR(err);
         }

      }
   }

   private Collection getConfigurationManagers() throws IOException {
      return this.getPluginManager().getConfigurationManagers();
   }

   private ConfigurationManager getConfigurationManager() throws IOException {
      return this.getPluginManager().getConfigurationManager(this.serverType);
   }

   private MonitoringPlugin getMonitoringPlugin() throws IOException {
      return this.getPluginManager().getMonitoringPlugin(this.serverType);
   }

   private InvocationPlugin getInvocationPlugin(String type) throws IOException {
      return this.getPluginManager().getInvocationPlugin(type, this.serverType);
   }

   private NMPluginManager getPluginManager() {
      return this.domainMgr.getPluginManager();
   }

   private boolean checkDomain() throws IOException {
      if (this.domainMgr == null) {
         this.sendERR(nmText.getDomainNull());
         return false;
      } else {
         return true;
      }
   }

   private boolean checkServer() throws IOException {
      if (this.serverMgr == null) {
         this.sendERR(nmText.getServerNull());
         return false;
      } else {
         return true;
      }
   }

   private boolean checkAuthorized() throws IOException {
      if (!this.authorized) {
         this.sendERR(nmText.getAuthNull());
         return false;
      } else {
         return true;
      }
   }

   private void sendGreeting() throws IOException {
      String greeting = nmText.getGreeting("Node Manager");
      this.sendOK(greeting);
      if (this.isDebugEnabled()) {
         this.debugSay("Sent Greeting : " + greeting);
      }

   }

   private void sendOK(String msg) throws IOException {
      if (this.isDebugEnabled()) {
         this.debugSay("Sending OK message : " + msg);
      }

      DataFormat.writeOK(this.ioHandler, msg);
      if (this.isDebugEnabled()) {
         this.debugSay("Sent OK message : " + msg);
      }

   }

   private void sendERR(String msg) throws IOException {
      if (this.isDebugEnabled()) {
         this.debugSay("Sending ERROR message : " + msg);
      }

      DataFormat.writeERR(this.ioHandler, msg);
      if (this.isDebugEnabled()) {
         this.debugSay("Sent ERROR message : " + msg);
      }

   }

   private void sendScriptERR(int exitCode) throws IOException {
      if (this.isDebugEnabled()) {
         this.debugSay("Sending ERROR for script exit code : " + exitCode);
      }

      DataFormat.writeERR(this.ioHandler, String.valueOf(exitCode));
      if (this.isDebugEnabled()) {
         this.debugSay("Sent ERROR for script exit code : " + exitCode);
      }

   }

   private void sendUnexpectedError(Command command, Throwable th) throws IOException {
      String err = nmText.getUnexpectedCommandFailure(command.getName(), getMessage(th));
      this.logError(err, th);
      this.sendERR(err);
   }

   private void logError(String err, Throwable th) {
      nmLog.log(Level.WARNING, err);
      this.debugSay("Detailed exception: " + th, th);
   }

   private void close(Closeable out) {
      try {
         if (out != null) {
            out.close();
         }
      } catch (Throwable var3) {
      }

   }

   static String getMessage(Throwable ex) {
      String msg = ex.getMessage();
      return msg != null && !msg.trim().isEmpty() ? msg : nmText.exceptionWithoutDetailedMessage(ex.getClass().getName());
   }

   private boolean isDebugEnabled() {
      return Level.ALL.equals(nmLog.getLevel());
   }

   private void debugSay(String msg) {
      this.debugSay(msg, (Throwable)null);
   }

   private void debugSay(String msg, Throwable th) {
      StringBuffer sb = new StringBuffer();
      sb.append("<").append(Thread.currentThread()).append("> ");
      sb.append(msg);
      nmLog.log(Level.FINEST, sb.toString(), th);
   }

   static {
      UNKNOWN_PROGRESS = new String[]{AggregateProgressBeanImpl.FIRST_LINE_PROGRESS_TRACKER, AggregateProgressBeanImpl.SECOND_LINE_PROGRESS_TRACKER, AggregateProgressBeanImpl.THIRD_LINE_PROGRESS_TRACKER, "  <domain-name>__**DNAMEFILL**__</domain-name>", "  <server-name>__**SNAMEFILL**__</server-name>", "  <aggregate-state>UNKNOWN</aggregate-state>", "  <server-disposition>__**FILL**__</server-disposition>", AggregateProgressBeanImpl.LAST_LINE_PROGRESS_TRACKER};
      nmLog = Logger.getLogger("weblogic.nodemanager");
      nmText = NodeManagerTextTextFormatter.getInstance();
   }
}
