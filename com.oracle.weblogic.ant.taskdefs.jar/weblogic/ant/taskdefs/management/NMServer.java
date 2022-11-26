package weblogic.ant.taskdefs.management;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.BuildListener;
import org.apache.tools.ant.taskdefs.Execute;
import org.apache.tools.ant.taskdefs.ExecuteStreamHandler;
import org.apache.tools.ant.taskdefs.Java;
import org.apache.tools.ant.taskdefs.PumpStreamHandler;
import org.apache.tools.ant.taskdefs.condition.Os;
import org.apache.tools.ant.types.CommandlineJava;
import org.apache.tools.ant.types.Environment;
import weblogic.nodemanager.client.NMClient;

public class NMServer extends Java implements Runnable, BuildListener {
   private String home = null;
   private String config = null;
   private File domainDir = null;
   private boolean verbose = false;
   private boolean debug = false;
   private String listenPort = "5555";
   private String listenAddress = "localhost";
   private boolean noExit = false;
   private String action = "start";
   private String userName;
   private String password;
   private String domainName = "mydomain";
   private boolean quitEnabled = true;
   private boolean acceptDemoTrust = false;
   private Process process = null;
   private static final String NM_MAIN_CLASS = "weblogic.nodemanager.server.NMServer";
   private boolean authenticationEnabled = true;

   public void setAcceptDemoTrust(boolean acceptDemoTrust) {
      this.acceptDemoTrust = acceptDemoTrust;
   }

   public void setQuitEnabled(boolean quitEnabled) {
      this.quitEnabled = quitEnabled;
   }

   public void setDomainName(String domainName) {
      this.domainName = domainName;
   }

   public void setNoExit(boolean noExit) {
      this.noExit = noExit;
   }

   public void setListenPort(String listenPort) {
      this.listenPort = listenPort;
   }

   public void setListenAddress(String listenAddress) {
      this.listenAddress = listenAddress;
   }

   public void setUserName(String username) {
      this.userName = username;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public void setAction(String action) {
      this.action = action;
   }

   public void setHome(String home) {
      this.home = home;
   }

   public void setAuthenticationEnabled(boolean authenticationEnabled) {
      this.authenticationEnabled = authenticationEnabled;
   }

   public void setConfig(String config) {
      this.config = config;
   }

   public void setDir(File domainDir) {
      this.domainDir = domainDir;
      super.setDir(domainDir);
   }

   public void setVerbose(boolean verbose) {
      this.verbose = verbose;
   }

   public void setDebug(boolean debug) {
      this.debug = debug;
   }

   public void execute() throws BuildException {
      if (this.action.equals("start")) {
         this.startAction();
      } else if (this.action.equals("shutdown")) {
         this.shutdownAction();
      } else {
         System.err.println("Unknown action " + this.action);
         throw new BuildException("Unknown action " + this.action);
      }
   }

   private void startAction() throws BuildException {
      this.getProject().addBuildListener(this);
      this.setClassname("weblogic.nodemanager.server.NMServer");
      String taskname = this.getClass().getName();
      String pkgname = this.getClass().getPackage().getName();
      taskname = taskname.substring(pkgname.length() + 1, taskname.length());
      this.setTaskName(taskname);
      this.setFork(true);
      this.setSpawn(true);
      if (this.domainDir != null && !this.domainDir.exists()) {
         throw new BuildException(this.domainDir + " doesn't exist.");
      } else {
         this.checkSuppliedCredentials();
         CommandlineJava cmdl = new CommandlineJava();
         cmdl.setClassname("weblogic.nodemanager.server.NMServer");
         this.setNMProperty(cmdl, "ListenPort", this.listenPort);
         this.setNMProperty(cmdl, "ListenAddress", this.listenAddress);
         this.setNMProperty(cmdl, "QuitEnabled", Boolean.toString(this.quitEnabled));
         if (this.home != null) {
            this.setCMDLArg(cmdl, "NodeManagerHome", this.home);
         }

         if (this.config != null) {
            this.setCMDLArg(cmdl, "PropertiesFile", this.config);
         }

         if (!this.authenticationEnabled) {
            this.setCMDLArg(cmdl, "AuthenticationEnabled", "false");
         }

         if (this.verbose) {
            cmdl.createArgument().setValue("-v");
         }

         if (this.debug) {
            cmdl.createArgument().setValue("-d");
         }

         this.startProcess(cmdl);
         if (this.waitForNMStart()) {
            System.out.println("Nodemanager is started");
         } else {
            System.out.println("Nodemanager is not started. It may still be booting or has failed. Check nodemanager log for details.");
         }

      }
   }

   private boolean waitForNMStart() {
      String trustKeyStore = System.getProperty("weblogic.security.TrustKeyStore");
      boolean resetTrust = false;
      if (this.acceptDemoTrust && (trustKeyStore == null || !trustKeyStore.equals("DemoTrust"))) {
         System.setProperty("weblogic.security.TrustKeyStore", "DemoTrust");
         resetTrust = true;
      }

      NMClient client = NMClient.getInstance("ssl");
      client.setDomainName(this.domainName);
      client.setPort(Integer.parseInt(this.listenPort));
      client.setHost(this.listenAddress);
      client.setVerbose(true);
      client.setNMUser(this.userName);
      client.setNMPass(this.password);
      boolean started = false;

      for(int i = 0; !started && i < 20; ++i) {
         try {
            String state = client.getVersion();
            client.done();
            started = true;
         } catch (IOException var9) {
            System.out.println("Nodemanager is not started. Waiting...");

            try {
               Thread.sleep(5000L);
            } catch (InterruptedException var8) {
            }
         }
      }

      if (resetTrust) {
         if (trustKeyStore == null) {
            System.clearProperty("weblogic.security.TrustKeyStore");
         } else {
            System.setProperty("weblogic.security.TrustKeyStore", trustKeyStore);
         }
      }

      return started;
   }

   private void startProcess(CommandlineJava cmdl) {
      try {
         System.out.println("Starting CommandLine " + cmdl.describeJavaCommand());
         this.process = Execute.launch(this.getProject(), cmdl.getCommandline(), (new Environment()).getVariables(), this.domainDir, true);
         if (Os.isFamily("windows")) {
            try {
               Thread.sleep(5000L);
            } catch (InterruptedException var4) {
               this.getProject().log("interruption in the sleep after having spawned a process", 3);
            }
         }

         OutputStream dummyOut = new OutputStream() {
            public void write(int b) {
            }
         };
         ExecuteStreamHandler streamHandler = new PumpStreamHandler(dummyOut);
         streamHandler.setProcessErrorStream(this.process.getErrorStream());
         streamHandler.setProcessOutputStream(this.process.getInputStream());
         streamHandler.start();
         this.process.getOutputStream().close();
         this.getProject().log("spawned process " + this.process.toString(), 3);
      } catch (IOException var5) {
         System.err.println("Unable to start a process for " + cmdl);
         var5.printStackTrace();
      }

   }

   private void shutdownAction() throws BuildException {
      String trustKeyStore = System.getProperty("weblogic.security.TrustKeyStore");
      boolean resetTrust = false;
      if (this.acceptDemoTrust && (trustKeyStore == null || !trustKeyStore.equals("DemoTrust"))) {
         System.setProperty("weblogic.security.TrustKeyStore", "DemoTrust");
         resetTrust = true;
      }

      NMClient client = NMClient.getInstance("ssl");
      client.setDomainName(this.domainName);
      client.setPort(Integer.parseInt(this.listenPort));
      client.setHost(this.listenAddress);
      client.setVerbose(true);
      this.checkSuppliedCredentials();
      client.setNMUser(this.userName);
      client.setNMPass(this.password);

      try {
         System.out.println("Shutting down the node manager");
         client.quit();
         client.done();
      } catch (IOException var5) {
         throw new BuildException(var5);
      }

      if (resetTrust) {
         if (trustKeyStore == null) {
            System.clearProperty("weblogic.security.TrustKeyStore");
         } else {
            System.setProperty("weblogic.security.TrustKeyStore", trustKeyStore);
         }
      }

   }

   private void checkSuppliedCredentials() throws BuildException {
      if (this.userName == null || this.password == null) {
         throw new BuildException("NodeManager username and password must be supplied");
      }
   }

   private void setNMProperty(CommandlineJava cmdl, String property, Object value) {
      if (value != null) {
         String nmArgument = "-Dweblogic.nodemanager." + property + "=" + value;
         System.out.println("Adding NM argument" + nmArgument);
         cmdl.createVmArgument().setValue(nmArgument);
      }

   }

   private void setCMDLArg(CommandlineJava cmdl, String property, Object value) {
      if (value != null) {
         cmdl.createVmArgument().setValue("-D" + property + "=" + value.toString());
      }

   }

   public void run() {
   }

   public void buildStarted(BuildEvent event) {
   }

   public void buildFinished(BuildEvent event) {
      if (!this.noExit) {
         System.out.println("Killing NodeManager Instance");
         this.shutdownAction();
      } else {
         System.out.println("Leaving the NodeManager running");
      }

   }

   public void targetStarted(BuildEvent event) {
   }

   public void targetFinished(BuildEvent event) {
   }

   public void taskStarted(BuildEvent event) {
   }

   public void taskFinished(BuildEvent event) {
   }

   public void messageLogged(BuildEvent event) {
   }
}
