package weblogic.nodemanager.client;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Writer;
import java.util.Map;
import java.util.Properties;
import weblogic.admin.plugin.NMComponentTypeChangeList;
import weblogic.admin.plugin.NMMachineChangeList;
import weblogic.nodemanager.NodeManagerClientTextFormatter;
import weblogic.nodemanager.ScriptExecutionFailureException;

public abstract class NMClient {
   protected String host;
   protected int port;
   protected String nmDir;
   protected String domainName;
   protected String domainDir;
   protected String serverName;
   protected String serverType;
   protected Properties runtimeProperties;
   protected int connectionCreationTimeout = 0;
   protected static final String ENCODING = "UTF-8";
   protected byte[] nmUser;
   protected byte[] nmPass;
   protected boolean verbose;
   protected PrintStream stdout;
   public static final String PLAIN = "plain";
   public static final String SSL = "ssl";
   public static final String SSH = "ssh";
   public static final String RSH = "rsh";
   public static final String SHELL = "shell";
   private static final NodeManagerClientTextFormatter nmText = NodeManagerClientTextFormatter.getInstance();

   NMClient() {
      this.stdout = System.out;
      this.serverType = "WebLogic";
   }

   public static NMClient getInstance(String type) {
      if ("plain".equalsIgnoreCase(type)) {
         return new PlainClient();
      } else if ("ssl".equalsIgnoreCase(type)) {
         return SSLClient.getSSLClient();
      } else if ("shell".equalsIgnoreCase(type)) {
         return new ShellClient();
      } else if ("ssh".equalsIgnoreCase(type)) {
         return new SSHClient();
      } else if ("rsh".equalsIgnoreCase(type)) {
         return new RSHClient();
      } else {
         throw new IllegalArgumentException(nmText.getUnknownClient(type));
      }
   }

   protected void checkNullOrEmpty(String str, String msg) throws IllegalArgumentException {
      if (str == null || str.length() == 0) {
         throw new IllegalArgumentException(nmText.getNullOrEmpty(msg));
      }
   }

   public synchronized void setHost(String host) {
      this.checkNullOrEmpty(host, nmText.getInvalidHostName());
      this.checkNotConnected();
      this.host = host;
   }

   public synchronized void setPort(int port) {
      if (port <= 0) {
         throw new IllegalArgumentException(nmText.getInvalidPort(Integer.toString(port)));
      } else {
         this.checkNotConnected();
         this.port = port;
      }
   }

   public synchronized void setDomainName(String name) {
      this.checkNullOrEmpty(name, nmText.getInvalidDomain());
      this.checkNotConnected();
      this.domainName = name;
   }

   public synchronized void setDomainDir(String dir) {
      this.checkNullOrEmpty(dir, nmText.getInvalidDomainDir());
      this.checkNotConnected();
      this.domainDir = dir;
   }

   public synchronized void setServerName(String name) {
      this.checkNullOrEmpty(name, nmText.getInvalidServerName());
      this.serverName = name;
   }

   public synchronized void setServerType(String serverType) {
      this.serverType = serverType;
   }

   public synchronized void setNMDir(String dir) {
      this.checkNullOrEmpty(dir, nmText.getInvalidNMHome());
      this.checkNotConnected();
      this.nmDir = dir;
   }

   public synchronized void setNMUser(String user) {
      this.checkNullOrEmpty(user, nmText.getInvalidUser());
      this.checkNotConnected();

      try {
         this.nmUser = user.getBytes("UTF-8");
      } catch (IOException var3) {
         this.nmUser = null;
      }

   }

   public synchronized void setNMPass(String pass) {
      this.checkNullOrEmpty(pass, nmText.getInvalidPwd());
      this.checkNotConnected();

      try {
         this.nmPass = pass.getBytes("UTF-8");
      } catch (IOException var3) {
         this.nmPass = null;
      }

   }

   public synchronized void setConnectionCreationTimeout(int timeout) {
      this.connectionCreationTimeout = timeout;
   }

   public synchronized int getConnectionCreationTimeout() {
      return this.connectionCreationTimeout;
   }

   public void execScript(String name, long timeout) throws IOException, ScriptExecutionFailureException {
      this.checkNullOrEmpty(name, nmText.getInvalidScriptName());
      this.executeScript(name, timeout);
   }

   public synchronized void setVerbose(boolean verbose) {
      this.verbose = verbose;
   }

   public synchronized void setRuntimeProperties(Properties runtimeProperties) {
      this.runtimeProperties = runtimeProperties;
   }

   public String getServerName() {
      return this.serverName;
   }

   public String getServerType() {
      return this.serverType;
   }

   public abstract String getStates(int var1) throws IOException;

   public abstract String getStates(int var1, char var2) throws IOException;

   public abstract String getState(int var1) throws IOException;

   public abstract String getVersion() throws IOException;

   public abstract void getNMLog(Writer var1) throws IOException;

   public abstract void getLog(Writer var1) throws IOException;

   public abstract void start() throws IOException;

   public abstract void start(Properties var1) throws IOException;

   public abstract void kill() throws IOException;

   public abstract void printThreadDump() throws IOException;

   public abstract String getProgress() throws IOException;

   public abstract void done();

   public abstract void quit() throws IOException;

   protected abstract void checkNotConnected() throws IllegalStateException;

   public abstract void changeList(NMMachineChangeList var1) throws IOException;

   public abstract void syncChangeList(NMMachineChangeList var1) throws IOException;

   public abstract void validateChangeList(String var1) throws IOException;

   public abstract void commitChangeList(String var1) throws IOException;

   public abstract void rollbackChangeList(String var1) throws IOException;

   public abstract void softRestart() throws IOException;

   public abstract void invocationRequest(String var1, String[] var2, Writer var3) throws IOException;

   public abstract void invocationRequest(String var1, String[] var2, OutputStream var3) throws IOException;

   /** @deprecated */
   @Deprecated
   public abstract void diagnosticRequest(String var1, String[] var2, Writer var3) throws IOException;

   public abstract void executeScript(String var1, long var2) throws IOException, ScriptExecutionFailureException;

   public abstract int executeScript(String var1, String var2, Map var3, Writer var4, long var5) throws IOException, ScriptExecutionFailureException;

   public abstract void updateServerProps(Properties var1) throws IOException;

   public abstract void chgCred(String var1, String var2) throws IOException;

   public void setOutputStream(PrintStream out) {
      this.stdout = out;
   }

   public abstract void remove() throws IOException;

   /** @deprecated */
   @Deprecated
   public abstract void initState(int var1) throws IOException;

   public abstract void initState() throws IOException;

   public abstract NMComponentTypeChangeList getChangeListForAllFiles(String var1, String var2) throws IOException;

   public abstract byte[] getFile(String var1, String var2, String var3) throws IOException;

   public abstract void restart(long var1, boolean var3) throws IOException;

   public abstract void restartAsync(boolean var1) throws IOException;

   public abstract void restartAll(long var1, boolean var3) throws IOException;

   public abstract void restartAllAsync(boolean var1) throws IOException;

   public String getHostAndPort() {
      return this.host + ":" + this.port;
   }
}
