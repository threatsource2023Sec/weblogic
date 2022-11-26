package weblogic.nodemanager.client;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.StringTokenizer;
import weblogic.admin.plugin.ChangeList;
import weblogic.admin.plugin.NMComponentTypeChangeList;
import weblogic.admin.plugin.NMMachineChangeList;
import weblogic.nodemanager.NMConnectException;
import weblogic.nodemanager.NMException;
import weblogic.nodemanager.NodeManagerClientTextFormatter;
import weblogic.nodemanager.ScriptExecutionFailureException;
import weblogic.nodemanager.common.Command;
import weblogic.nodemanager.common.DataFormat;
import weblogic.nodemanager.common.NMInputOutput;
import weblogic.nodemanager.common.NMProtocol;
import weblogic.nodemanager.util.Protocol;
import weblogic.utils.PlatformConstants;
import weblogic.utils.StringUtils;
import weblogic.utils.net.SocketResetException;

abstract class NMServerClient extends NMClient {
   private Socket sock;
   private NMInputOutput ioHandler;
   private boolean connected;
   private static final NodeManagerClientTextFormatter nmText = NodeManagerClientTextFormatter.getInstance();

   public synchronized String getState(int timeout) throws IOException {
      this.checkConnected(timeout);
      this.sendServer();
      this.sendCmd(Command.STAT);
      return this.checkResponse();
   }

   public synchronized String getStates(int timeout) throws IOException {
      this.checkConnected(timeout);
      this.sendCmd(Command.GETSTATES);
      return this.checkResponse();
   }

   public synchronized String getStates(int timeout, char separator) throws IOException {
      this.checkConnected(timeout);
      this.sendCmd(Command.GETSTATES, new String[]{String.valueOf(separator)});
      return this.checkResponse();
   }

   public synchronized String getVersion() throws IOException {
      this.checkConnected();
      this.sendCmd(Command.VERSION);
      return this.checkResponse();
   }

   public synchronized void getNMLog(Writer out) throws IOException {
      this.checkConnected();
      this.sendCmd(Command.GETNMLOG);

      try {
         DataFormat.copy(this.ioHandler, out);
      } catch (IOException var3) {
         this.rethrowAsNMConnectExeptionIfNecessary(var3);
      }

      this.checkResponse();
   }

   public synchronized void getLog(Writer out) throws IOException {
      this.checkConnected();
      this.sendServer();
      this.sendCmd(Command.GETLOG);

      try {
         DataFormat.copy(this.ioHandler, out);
      } catch (IOException var3) {
         this.rethrowAsNMConnectExeptionIfNecessary(var3);
      }

      this.checkResponse();
   }

   public synchronized void start() throws IOException {
      this.start((Properties)null);
   }

   public synchronized void start(Properties props) throws IOException {
      this.checkConnected();
      this.sendServer();
      this.sendRuntimeProperties();
      if (props != null) {
         this.sendCmd(Command.STARTP);

         try {
            DataFormat.writeProperties(this.ioHandler, props);
         } catch (IOException var3) {
            this.rethrowAsNMConnectExeptionIfNecessary(var3);
         }
      } else {
         this.sendCmd(Command.START);
      }

      this.checkResponse();
   }

   public synchronized void kill() throws IOException {
      this.checkConnected();
      this.sendServer();
      this.sendRuntimeProperties();
      this.sendCmd(Command.KILL);
      this.checkResponse();
   }

   public synchronized void printThreadDump() throws IOException {
      this.checkConnected();
      this.sendServer();
      this.sendRuntimeProperties();
      this.sendCmd(Command.PRINTTHREADDUMP);
      this.checkResponse();
   }

   public synchronized String getProgress() throws IOException {
      this.checkConnected();
      this.sendServer();
      this.sendRuntimeProperties();
      this.sendCmd(Command.PROGRESS);
      String[] result = DataFormat.readMultiLineCommand(this.ioHandler);
      return StringUtils.join(result, PlatformConstants.EOL);
   }

   private void sendRuntimeProperties() throws IOException {
      if (this.runtimeProperties != null && !this.runtimeProperties.isEmpty()) {
         this.sendCmd(Command.PROPERTIES);

         try {
            DataFormat.writeProperties(this.ioHandler, this.runtimeProperties);
         } catch (IOException var2) {
            this.rethrowAsNMConnectExeptionIfNecessary(var2);
         }

         this.checkResponse();
      }

   }

   public synchronized void chgCred(String newUser, String newPass) throws IOException {
      this.checkConnected();
      this.sendCmd(Command.CHGCRED, new String[]{newUser, newPass});
      this.checkResponse();
   }

   private void sendHello() throws IOException {
      this.sendCmd(Command.HELLO);
   }

   public synchronized void done() {
      try {
         if (this.ioHandler != null && this.ioHandler.getNMProtocol().ordinal() >= NMProtocol.v2_6.ordinal()) {
            this.sendCmd(Command.DONE);
         }

         this.disconnect();
      } catch (IOException var2) {
      }

   }

   public synchronized void quit() throws IOException {
      this.checkConnected();
      this.sendCmd(Command.QUIT);
      this.checkResponse();
   }

   protected abstract Socket createSocket(String var1, int var2, int var3) throws IOException;

   protected void checkNotConnected() throws IllegalStateException {
      if (this.connected) {
         throw new IllegalStateException(nmText.getAlreadyConnected());
      }
   }

   public synchronized void executeScript(String name, long timeout) throws IOException, ScriptExecutionFailureException {
      if (name != null && !name.equals("")) {
         this.checkConnected();
         this.sendCmd(Command.EXECSCRIPT, new String[]{name, String.valueOf(timeout)});
         this.checkScriptResponse(name);
      } else {
         throw new IOException(nmText.getInvalidPath(name));
      }
   }

   public int executeScript(String name, String dir, Map env, Writer writer, long timeout) throws IOException, ScriptExecutionFailureException {
      this.checkNullOrEmpty(name, nmText.getInvalidScriptName());
      this.checkConnected();
      String hasEnv = env != null && !env.isEmpty() ? Boolean.TRUE.toString() : Boolean.FALSE.toString();
      String writeOutput = writer == null ? Boolean.FALSE.toString() : Boolean.TRUE.toString();
      this.sendCmd(Command.SCRIPTCALL, new String[]{name, "dirPath=" + (dir != null ? dir : ""), hasEnv, writeOutput, String.valueOf(timeout)});
      if (env != null && !env.isEmpty()) {
         DataFormat.writePropMap(this.ioHandler, env);
      }

      int rc = this.checkScriptResponse(name, writer);
      return rc;
   }

   public synchronized void updateServerProps(Properties props) throws IOException {
      this.checkConnected();
      this.sendServer();
      this.sendCmd(Command.UPDATEPROPS);

      try {
         DataFormat.writeProperties(this.ioHandler, props);
      } catch (IOException var3) {
         this.rethrowAsNMConnectExeptionIfNecessary(var3);
      }

      this.checkResponse();
   }

   public synchronized void changeList(NMMachineChangeList changes) throws IOException {
      String transaction = changes.getTransactionID();
      Iterator var3 = changes.getChanges().entrySet().iterator();

      while(var3.hasNext()) {
         Map.Entry entry = (Map.Entry)var3.next();
         this.changeList((String)entry.getKey(), transaction, (NMComponentTypeChangeList)entry.getValue());
      }

   }

   private void changeList(String serverType, String transaction, NMComponentTypeChangeList changeList) throws IOException {
      this.changeListCommand(Command.CHANGELIST, serverType, transaction, changeList);
   }

   private void syncChangeList(String serverType, String transaction, NMComponentTypeChangeList changeList) throws IOException {
      this.changeListCommand(Command.SYNC_CHANGELIST, serverType, transaction, changeList);
      this.commitChangeList(transaction);
   }

   private void changeListCommand(Command changeCommand, String serverType, String transaction, NMComponentTypeChangeList changeList) throws IOException {
      this.checkConnected();
      this.serverType = serverType;
      this.sendServerType();
      this.sendCmd(changeCommand, new String[]{transaction});
      ChangeList changeList1 = changeList.getComponentTypeChanges();

      try {
         DataFormat.writeChangeList(this.ioHandler, changeList1, changeList.getComponentNames());
      } catch (IOException var11) {
         this.rethrowAsNMConnectExeptionIfNecessary(var11);
      }

      this.checkResponse();

      try {
         String[] filesToGet = (String[])((String[])this.ioHandler.readObject());
         if (filesToGet != null && filesToGet.length > 0) {
            String[] var7 = filesToGet;
            int var8 = filesToGet.length;

            for(int var9 = 0; var9 < var8; ++var9) {
               String fileName = var7[var9];
               if (fileName != null && changeList1.getChanges() != null) {
                  this.sendFile(fileName, transaction);
               }
            }
         }

      } catch (ClassNotFoundException var12) {
         throw new IOException(var12);
      }
   }

   private void sendFile(String fileName, String transaction) throws IOException {
      File file = new File(fileName);
      if (file.canRead()) {
         this.sendCmd(Command.PUTFILE, new String[]{transaction, fileName});
         InputStream in = null;

         try {
            in = new BufferedInputStream(new FileInputStream(file));

            try {
               DataFormat.copy(in, this.ioHandler);
            } catch (IOException var13) {
               this.rethrowAsNMConnectExeptionIfNecessary(var13);
            }

            this.checkResponse();
         } finally {
            if (in != null) {
               try {
                  in.close();
               } catch (IOException var12) {
               }
            }

         }

      } else {
         throw new IOException(nmText.getFileToSendNotReadable(file.getAbsolutePath().toString()));
      }
   }

   public synchronized void syncChangeList(NMMachineChangeList changes) throws IOException {
      String transaction = changes.getTransactionID();
      if (transaction == null) {
         throw new IllegalArgumentException(nmText.getTxIDRequired(changes.toString()));
      } else {
         Iterator var3 = changes.getChanges().entrySet().iterator();

         while(var3.hasNext()) {
            Map.Entry entry = (Map.Entry)var3.next();
            this.syncChangeList((String)entry.getKey(), transaction, (NMComponentTypeChangeList)entry.getValue());
         }

      }
   }

   public synchronized void validateChangeList(String transaction) throws IOException {
      this.checkConnected();
      this.sendCmd(Command.VALIDATE_CHANGELIST, new String[]{transaction});
      this.checkResponse();
   }

   public synchronized void commitChangeList(String transaction) throws IOException {
      this.checkConnected();
      this.sendCmd(Command.COMMIT_CHANGELIST, new String[]{transaction});
      this.checkResponse();
   }

   public synchronized void rollbackChangeList(String transaction) throws IOException {
      this.checkConnected();
      this.sendCmd(Command.ROLLBACK_CHANGELIST, new String[]{transaction});
      this.checkResponse();
   }

   public synchronized void invocationRequest(String type, String[] command, Writer writer) throws IOException {
      this.checkConnected();
      this.sendServerType();
      this.sendCmd(Command.INVOCATION, new String[]{type});

      try {
         DataFormat.writeMultiLineCommand(this.ioHandler, command);
         DataFormat.copy(this.ioHandler, writer);
      } catch (IOException var5) {
         this.rethrowAsNMConnectExeptionIfNecessary(var5);
      }

      this.checkResponse();
   }

   public synchronized void invocationRequest(String type, String[] command, OutputStream out) throws IOException {
      this.checkConnected();
      this.sendServerType();
      this.sendCmd(Command.INVOCATION, new String[]{type});

      try {
         DataFormat.writeMultiLineCommand(this.ioHandler, command);
         DataFormat.copy(this.ioHandler, out);
      } catch (IOException var5) {
         this.rethrowAsNMConnectExeptionIfNecessary(var5);
      }

      this.checkResponse();
   }

   public synchronized void diagnosticRequest(String type, String[] command, Writer writer) throws IOException {
      this.checkConnected();
      this.sendServerType();
      this.sendCmd(Command.DIAGNOSTICS, new String[]{type});

      try {
         DataFormat.writeMultiLineCommand(this.ioHandler, command);
         DataFormat.copy(this.ioHandler, writer);
      } catch (IOException var5) {
         this.rethrowAsNMConnectExeptionIfNecessary(var5);
      }

      this.checkResponse();
   }

   public synchronized void softRestart() throws IOException {
      this.checkConnected();
      this.sendServer();
      this.sendRuntimeProperties();
      this.sendCmd(Command.SOFTRESTART);
      this.checkResponse();
   }

   public synchronized void remove() throws IOException {
      this.checkConnected();
      this.sendServer();
      this.sendRuntimeProperties();
      this.sendCmd(Command.REMOVE);
      this.checkResponse();
   }

   public synchronized void initState(int timeout) throws IOException {
      this.checkConnected(timeout);
      this.sendServer();
      this.sendRuntimeProperties();
      this.sendCmd(Command.INITSTATE);
      this.checkResponse();
   }

   public synchronized void initState() throws IOException {
      this.checkConnected();
      this.sendServer();
      this.sendRuntimeProperties();
      this.sendCmd(Command.INITSTATE);
      this.checkResponse();
   }

   public NMComponentTypeChangeList getChangeListForAllFiles(String compType, String compName) throws IOException {
      this.checkConnected();
      this.serverType = compType;
      this.sendServerType();
      this.sendCmd(Command.GETCHANGELIST, new String[]{compName});
      this.checkResponse();
      NMComponentTypeChangeList nmChangeList = new NMComponentTypeChangeList(new String[0], new ChangeList());
      DataFormat.readNMComponentTypeChangeList(this.ioHandler, nmChangeList);
      return nmChangeList;
   }

   public byte[] getFile(String compType, String compName, String relativePath) throws IOException {
      this.checkConnected();
      this.serverType = compType;
      this.sendServerType();
      this.sendCmd(Command.GETFILE, new String[]{compName, relativePath});
      this.checkResponse();
      ByteArrayOutputStream baos = new ByteArrayOutputStream();

      try {
         DataFormat.copy(this.ioHandler, baos);
      } catch (IOException var6) {
         this.rethrowAsNMConnectExeptionIfNecessary(var6);
      }

      return baos.toByteArray();
   }

   public void restartAll(long restartTimeoutMillis, boolean update) throws IOException {
      this.restart(restartTimeoutMillis, update, true);
   }

   public void restartAllAsync(boolean update) throws IOException {
      this.restartAsync(update, true);
   }

   public void restart(long restartTimeoutMillis, boolean update) throws IOException {
      this.restart(restartTimeoutMillis, update, false);
   }

   public void restartAsync(boolean update) throws IOException {
      this.restartAsync(update, false);
   }

   public void restart(long restartTimeoutMillis, boolean update, boolean restartAll) throws IOException {
      this.checkConnected();
      this.sendCmd(Command.RESTARTNM, new String[]{String.valueOf(update), String.valueOf(restartAll)});
      this.checkResponse();
      this.connected = false;

      try {
         if (this.sock != null) {
            this.sock.close();
         }
      } catch (Exception var14) {
      }

      long restartBegin = System.currentTimeMillis();
      long remainingWaitTime = 0L;
      long sleepTime = 2000L;
      boolean blockingCall = restartTimeoutMillis <= 0L;

      while(blockingCall || (remainingWaitTime = restartTimeoutMillis - (System.currentTimeMillis() - restartBegin)) > 0L) {
         if (sleepTime > remainingWaitTime && remainingWaitTime > 0L) {
            sleepTime = remainingWaitTime;
         }

         try {
            Thread.sleep(sleepTime);
         } catch (InterruptedException var13) {
         }

         if (!blockingCall) {
            remainingWaitTime -= sleepTime;
            if (remainingWaitTime <= 0L) {
               remainingWaitTime = 1L;
            }
         }

         try {
            this.checkConnected((new Long(remainingWaitTime)).intValue());
            return;
         } catch (NMConnectException var15) {
            if (sleepTime > 500L) {
               sleepTime /= 2L;
            }
         }
      }

      throw new NMException(nmText.restartNMTimeout(Long.toString(restartTimeoutMillis)));
   }

   private void restartAsync(boolean update, boolean restartAll) throws IOException {
      this.checkConnected();
      this.sendCmd(Command.RESTARTNM, new String[]{String.valueOf(update), String.valueOf(restartAll)});
      this.checkResponse();
      this.connected = false;
   }

   private void checkConnected(int timeout) throws IOException {
      if (!this.connected) {
         this.connect(timeout);
         this.connected = true;
      }

   }

   private void checkConnected() throws IOException {
      this.checkConnected(this.getConnectionCreationTimeout());
   }

   private void connect(int timeout) throws IOException {
      if (this.domainName == null) {
         throw new IllegalStateException(nmText.getDomainNotSet());
      } else if (this.nmUser != null && this.nmPass == null) {
         throw new IllegalStateException(nmText.getNoPassword());
      } else if (this.nmPass != null && this.nmUser == null) {
         throw new IllegalStateException(nmText.getNoUser());
      } else {
         try {
            this.sock = this.createSocket(this.host, this.port, timeout);
         } catch (IOException var5) {
            InetAddress address;
            if (this.host == null) {
               address = InetAddress.getLocalHost();
            } else {
               address = InetAddress.getByName(this.host);
            }

            NMConnectException nmce = new NMConnectException(var5.getMessage() + ". " + nmText.getNoConnect(address.toString(), Integer.toString(this.port)), this.host, this.port);
            nmce.setStackTrace(var5.getStackTrace());
            throw nmce;
         }

         this.ioHandler = new NMInputOutput(this.sock.getInputStream(), this.sock.getOutputStream());
         NMProtocol protocol = this.negotiateProtocol();
         this.ioHandler.setNMProtocolVersion(protocol);
         this.sendHello();
         this.checkResponse();
         if (this.domainDir != null) {
            this.sendCmd(Command.DOMAIN, new String[]{this.domainName, this.domainDir});
         } else {
            this.sendCmd(Command.DOMAIN, new String[]{this.domainName});
         }

         this.checkResponse();
         if (this.nmUser != null && this.nmPass != null) {
            this.sendCmd(Command.USER, new String[]{new String(this.nmUser, "UTF-8")});
            this.checkResponse();
            this.sendCmd(Command.PASS, new String[]{new String(this.nmPass, "UTF-8")});
            this.checkResponse();
         }

      }
   }

   private NMProtocol negotiateProtocol() throws IOException {
      NMProtocol[] protocols = NMProtocol.values();
      NMProtocol protocol = NMProtocol.getLatestVersion();
      this.sendCmd(Command.NMPROTOCOL, new String[]{protocol.name()});
      String protocolResp = null;

      try {
         protocolResp = this.checkResponse();
      } catch (IOException var6) {
         protocol = NMProtocol.v2;
      }

      if (protocolResp != null) {
         try {
            protocol = NMProtocol.valueOf(protocolResp);
         } catch (IllegalArgumentException var5) {
            throw new IOException(nmText.getUnexpectedResponse(protocolResp));
         }
      }

      return protocol;
   }

   private void sendServer() throws IOException {
      this.sendServerType();
      if (this.serverName == null) {
         throw new IllegalStateException(nmText.getServerNotSet());
      } else {
         this.sendCmd(Command.SERVER, new String[]{this.serverName});
         this.checkResponse();
      }
   }

   private void sendServerType() throws IOException {
      if (this.serverType == null) {
         throw new IllegalStateException(nmText.getServerTypeNotSet());
      } else {
         this.sendCmd(Command.SERVER_TYPE, new String[]{this.serverType.toString()});
         this.checkResponse();
      }
   }

   private synchronized void disconnect() throws IOException {
      if (this.connected) {
         this.sock.close();
         this.connected = false;
      }

   }

   private int checkScriptResponse(String script) throws IOException {
      return this.checkScriptResponse(script, (Writer)null);
   }

   private int checkScriptResponse(String script, Writer writer) throws IOException {
      String line = null;
      line = this.getResponseString();
      if (line == null) {
         throw new IOException(nmText.getEndOfStream());
      } else {
         String msg;
         if ((msg = DataFormat.parseERR(line)) != null) {
            int exitCode = false;
            int exitCode;
            if ((exitCode = DataFormat.parseScriptERR(line)) != 0) {
               if (writer != null) {
                  this.ioHandler.copy(writer);
               }

               throw new ScriptExecutionFailureException(script, exitCode);
            } else {
               throw new NMException(nmText.errorFromServer(msg));
            }
         } else if ((msg = DataFormat.parseOK(line)) != null) {
            StringTokenizer st = new StringTokenizer(msg, " ");
            String rc = null;
            String successMsg = null;

            try {
               rc = st.nextToken();
               successMsg = st.nextToken();
            } catch (NoSuchElementException var11) {
            }

            int exitCode = false;

            int exitCode;
            try {
               exitCode = Integer.parseInt(rc);
            } catch (NumberFormatException var10) {
               throw new IOException(nmText.getUnexpectedResponse(line));
            }

            if (writer != null) {
               this.ioHandler.copy(writer);
            }

            return exitCode;
         } else {
            throw new IOException(nmText.getUnexpectedResponse(line));
         }
      }
   }

   private String checkResponse() throws IOException {
      String line = this.getResponseString();
      if (line == null) {
         throw new IOException(nmText.getEndOfStream());
      } else {
         String msg;
         if ((msg = DataFormat.parseERR(line)) != null) {
            throw new NMException(nmText.errorFromServer(msg));
         } else if ((msg = DataFormat.parseOK(line)) != null) {
            return msg;
         } else {
            throw new IOException(nmText.getUnexpectedResponse(line));
         }
      }
   }

   private String getResponseString() throws IOException {
      String line = null;

      try {
         line = DataFormat.readResponse(this.ioHandler);
      } catch (Protocol.TLSAlertException var3) {
         throw new IOException(nmText.getProtocolTLSAlertException(), var3);
      } catch (Protocol.ProtocolException var4) {
         throw new IOException(nmText.getUnknownProtocolException(), var4);
      } catch (IOException var5) {
         this.rethrowAsNMConnectExeptionIfNecessary(var5);
      }

      return line;
   }

   private void sendCmd(Command cmd) throws IOException {
      try {
         DataFormat.writeCommand(this.ioHandler, cmd, (String[])null);
      } catch (IOException var3) {
         this.rethrowAsNMConnectExeptionIfNecessary(var3);
      }

   }

   private void sendCmd(Command cmd, String[] args) throws IOException {
      try {
         DataFormat.writeCommand(this.ioHandler, cmd, args);
      } catch (IOException var4) {
         this.rethrowAsNMConnectExeptionIfNecessary(var4);
      }

   }

   private void rethrowAsNMConnectExeptionIfNecessary(IOException e) throws NMConnectException, IOException {
      if (SocketResetException.isResetException(e)) {
         NMConnectException nme = new NMConnectException(nmText.errorTalkWithServer(e.getMessage(), this.host, Integer.toString(this.port)), this.host, this.port);
         nme.initCause(e);
         throw nme;
      } else {
         throw e;
      }
   }

   public String toString() {
      return "NMServerClient(" + this.serverName + "," + System.identityHashCode(this) + ")";
   }
}
