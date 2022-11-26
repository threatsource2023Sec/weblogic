package weblogic.ant.taskdefs.management;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.ConnectException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.naming.CommunicationException;
import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.BuildListener;
import org.apache.tools.ant.taskdefs.Java;
import weblogic.Home;
import weblogic.management.jmx.MBeanServerInvocationHandler;
import weblogic.management.mbeanservers.runtime.RuntimeServiceMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.rjvm.PeerGoneException;
import weblogic.rmi.extensions.RemoteRuntimeException;

public class WLServer extends Java implements Runnable, BuildListener {
   private static final String DEFAULT_POLICY_FILE = "weblogic.policy";
   private static final String WEBLOGIC_MAIN_CLASS = "weblogic.Server";
   private static final String DEFAULT_HOST = "localhost";
   private static final String DEFAULT_PROTOCOL = "t3";
   private static final int DEFAULT_PORT = 7001;
   private static final String LICENSE_FILE = "license.bea";
   private File beaHome;
   private File weblogicHome;
   private File configDir;
   private File outFile = null;
   private String configFile;
   private String serverName = "myserver";
   private String domainName;
   private boolean useBootProperties = false;
   private String managementUserName;
   private String managementPassword;
   private String pkPassword;
   private File securityPolicyFile;
   private String serverHost = "localhost";
   private String serverProtocol = "t3";
   private int serverPort = 7001;
   private boolean generateConfig = false;
   private boolean forceImplicitUpgrade = false;
   private String adminServerURL = null;
   private String action = "start";
   private long timeout = 0L;
   private double timeoutSecs = 0.0;
   private boolean productionModeEnabled = false;
   private boolean verbose = false;
   private boolean failOnError = false;
   private boolean execFailed = false;
   private boolean forceShutdown = false;
   private MBeanServerConnection connection;
   private ObjectName serverRuntimeObjectName;
   private String errorProperty;
   private boolean noExit = false;
   String mainClass = "weblogic.Server";
   private String adminProtocol = "t3s";
   private static final int MAX_SHUTDOWN = 3;
   private JMXConnector connector;
   private ServerRuntimeMBean serverRuntimeMBean;

   public void setMainClass(String s) throws BuildException {
      this.mainClass = s;
   }

   public void setNoExit(boolean noExit) {
      this.noExit = noExit;
   }

   public void setErrorProperty(String ep) {
      this.errorProperty = ep;
   }

   public void setPolicy(File securityPolicyFile) {
      this.securityPolicyFile = securityPolicyFile;
   }

   public void setDir(File configDir) {
      this.configDir = configDir;
      super.setDir(configDir);
   }

   public void setOutput(File output) {
      this.outFile = output;
   }

   public void setConfigFile(String configFile) {
      this.configFile = configFile;
   }

   public void setBEAHome(File beaHome) {
      this.beaHome = beaHome;
   }

   public void setAdminProtocol(String s) {
      this.adminProtocol = s;
   }

   public void setWebLogicHome(File weblogicHome) {
      this.weblogicHome = weblogicHome;
      if (this.beaHome == null) {
         File tmp;
         for(tmp = weblogicHome; tmp != null && !(new File(tmp, "license.bea")).exists(); tmp = tmp.getParentFile()) {
         }

         if (tmp != null) {
            this.beaHome = tmp;
         }
      }

   }

   public void setServerName(String serverName) {
      this.serverName = serverName;
   }

   public void setDomainName(String domain) {
      this.domainName = domain;
   }

   public void setAdminServerURL(String adminURL) {
      this.adminServerURL = adminURL;
   }

   public void setUseBootProperties(boolean useBootProperties) {
      this.useBootProperties = useBootProperties;
   }

   public void setUserName(String username) {
      this.managementUserName = username;
   }

   public void setPassword(String password) {
      this.managementPassword = password;
   }

   public void setPKPassword(String pkpassword) {
      this.pkPassword = pkpassword;
   }

   public void setTimeout(Long timeout) {
      this.timeout = timeout;
      this.timeoutSecs = timeout.doubleValue();
      this.timeoutSecs /= 1000.0;
   }

   public void setTimeoutSeconds(Long timeoutSeconds) {
      this.timeout = timeoutSeconds * 1000L;
      this.timeoutSecs = timeoutSeconds.doubleValue();
   }

   public void setProductionModeEnabled(boolean productionMode) {
      this.productionModeEnabled = productionMode;
   }

   public void setHost(String host) {
      this.serverHost = host;
   }

   public void setProtocol(String protocol) {
      this.serverProtocol = protocol;
   }

   public void setPort(int port) throws BuildException {
      this.serverPort = port;
   }

   public void setGenerateConfig(boolean generateConfig) {
      this.generateConfig = generateConfig;
   }

   public void setForceImplicitUpgrade(boolean forceUpgrade) {
      this.forceImplicitUpgrade = forceUpgrade;
   }

   public void setAction(String action) {
      this.action = action;
   }

   public void setVerbose(boolean verbose) {
      this.verbose = verbose;
   }

   public void setFailonerror(boolean failOnError) {
      this.failOnError = failOnError;
      super.setFailonerror(failOnError);
   }

   public void setForceShutdown(boolean forceShutdown) {
      this.forceShutdown = forceShutdown;
   }

   public void execute() throws BuildException {
      ClassLoader currentClassloader = Thread.currentThread().getContextClassLoader();
      Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());

      try {
         String fileName;
         if (!this.action.equals("reboot") && !this.action.equals("shutdown") && !this.action.equals("connect") && !this.action.equals("start")) {
            fileName = "Invalid action specified: " + this.action;
            if (this.failOnError) {
               throw new BuildException(fileName);
            }

            this.log(fileName, 0);
         }

         if (this.action.equals("reboot") || this.action.equals("shutdown")) {
            this.killServer();
         }

         if (this.action.equals("connect")) {
            this.getServerRuntimeMBean();
            if (this.serverRuntimeMBean != null) {
               return;
            }

            fileName = "Unable to connect to " + this.serverProtocol + "://" + this.serverHost + ":" + this.serverPort;
            if (this.failOnError) {
               throw new BuildException(fileName);
            }

            if (this.errorProperty != null) {
               this.getProject().setProperty(this.errorProperty, fileName);
            }
         }

         if (this.action.equals("start") || this.action.equals("reboot")) {
            if (this.noExit) {
               this.setSpawn(true);
            }

            if (this.weblogicHome == null) {
               this.setWebLogicHome(Home.getFile());
            }

            this.log("weblogicHome is set to " + this.weblogicHome.getAbsolutePath(), 4);
            if (this.weblogicHome == null) {
               throw new BuildException("weblogichome not set");
            }

            if (this.beaHome != null && !this.beaHome.isDirectory()) {
               throw new BuildException("BEA home " + this.beaHome.getPath() + " not valid");
            }

            if (!this.generateConfig && this.adminServerURL == null) {
               fileName = this.configFile == null ? "config.xml" : this.configFile;
               File cf = new File(this.configDir, fileName);
               if (!cf.exists()) {
                  cf = new File(this.configDir, "config/" + fileName);
                  if (!cf.exists()) {
                     throw new BuildException("Server config file not found.");
                  }
               }
            }

            if (this.securityPolicyFile == null) {
               this.securityPolicyFile = this.getSecurityPolicyFile();
            }

            Thread t = new Thread(this, "Execute-WLS");
            t.setDaemon(true);
            t.start();
            Thread.currentThread();
            Thread.yield();
            this.getProject().addBuildListener(this);
            List states = new ArrayList();
            states.add("RUNNING");

            try {
               Thread.currentThread();
               Thread.sleep(2000L);
            } catch (InterruptedException var59) {
            }

            ThreadDumpAbleThreadFactory thFactory = new ThreadDumpAbleThreadFactory();
            ExecutorService waitExecutor = Executors.newSingleThreadExecutor(thFactory);
            WaitForServerState waitForServerState = new WaitForServerState(states, this.timeout);
            Future waitResult = waitExecutor.submit(waitForServerState);
            String message = "";
            boolean serverIsReady = false;

            try {
               serverIsReady = (Boolean)waitResult.get();
            } catch (InterruptedException var57) {
               var57.printStackTrace();
               message = var57.getMessage();
            } catch (ExecutionException var58) {
               var58.printStackTrace();
               message = var58.getMessage();
            }

            if (!serverIsReady) {
               if (!waitExecutor.isTerminated()) {
                  message = (this.execFailed ? "Error in server execution (" + this.serverName + ")" : "Server " + this.serverName + " did not reach RUNNING state after " + this.timeoutSecs + " seconds") + "\n=============Begin of ThreadDump" + this.getThreadDump(thFactory.getTargetThread()) + "\n=============End of ThreadDump";
               }

               if (this.failOnError) {
                  throw new BuildException(message);
               }

               this.log(message, 0);
            }
         }
      } finally {
         this.serverRuntimeObjectName = null;
         this.connection = null;
         this.serverRuntimeMBean = null;
         if (this.connector != null) {
            try {
               this.connector.close();
            } catch (IOException var55) {
            } finally {
               this.connector = null;
            }
         }

      }

   }

   private void executeServer() {
      if (this.serverName != null) {
         String taskname = this.getClass().getName();
         String pkgname = this.getClass().getPackage().getName();
         taskname = taskname.substring(pkgname.length() + 1, taskname.length());
         this.setTaskName(taskname + " " + this.serverName);
      }

      this.setFork(true);
      this.setClassname(this.mainClass);
      this.setProperty("weblogic.Domain", this.domainName);
      if (this.configFile != null) {
         this.setProperty("weblogic.ConfigFile", this.configFile);
      }

      this.setProperty("weblogic.Name", this.serverName);
      this.setProperty("platform.home", this.weblogicHome);
      Path newWeblogicHome = Paths.get(this.weblogicHome.getPath());
      if (!newWeblogicHome.endsWith("server")) {
         this.setProperty("wls.home", newWeblogicHome.resolve("server"));
         this.setProperty("weblogic.home", newWeblogicHome.resolve("server"));
      } else {
         this.setProperty("wls.home", newWeblogicHome);
         this.setProperty("weblogic.home", newWeblogicHome);
      }

      if (this.beaHome != null) {
         this.setProperty("bea.home", this.beaHome);
      }

      this.setProperty("java.security.policy=", this.securityPolicyFile);
      if (!this.useBootProperties) {
         this.setProperty("weblogic.management.username", this.managementUserName);
         this.setProperty("weblogic.management.password", this.managementPassword);
      }

      this.setProperty("weblogic.pkpassword", this.pkPassword);
      if (this.generateConfig) {
         this.setProperty("weblogic.management.GenerateDefaultConfig", Boolean.toString(this.generateConfig));
      }

      this.setProperty("weblogic.management.server", this.adminServerURL);
      if (this.productionModeEnabled) {
         this.setProperty("weblogic.ProductionModeEnabled", Boolean.toString(this.productionModeEnabled));
      }

      if (this.forceImplicitUpgrade) {
         this.setProperty("weblogic.ForceImplicitUpgradeIfNeeded", Boolean.toString(this.forceImplicitUpgrade));
      }

      if (this.serverHost != "localhost") {
         this.setProperty("weblogic.ListenAddress", this.serverHost);
      }

      if (this.serverPort != 7001) {
         this.setProperty("weblogic.ListenPort", this.serverPort + "");
      }

      if (this.outFile != null) {
         if (this.noExit) {
            this.getProject().log("Ignoring output redirection as it is incompatibile with noExit");
         } else {
            super.setOutput(this.outFile);
         }
      }

      try {
         if (this.executeJava() != 0) {
            this.execFailed = true;
         }

      } catch (BuildException var3) {
         throw var3;
      }
   }

   protected void setProperty(String property, Object value) {
      if (value != null) {
         this.createJvmarg().setValue("-D" + property + "=" + value.toString());
      }

   }

   protected void setProperty(String property, boolean bool) {
      if (bool) {
         this.createJvmarg().setValue("-D" + property);
      }

   }

   private File getSecurityPolicyFile() {
      File policyFile = new File(this.weblogicHome, "lib/weblogic.policy");
      if (!policyFile.exists() && this.configDir != null) {
         policyFile = new File(this.configDir, "weblogic.policy");
      }

      return !policyFile.exists() ? null : policyFile;
   }

   private void killServer() {
      int maxTry = 3;

      try {
         while(maxTry >= 0 && this.serverRuntimeMBean == null) {
            this.getServerRuntimeMBean();
            --maxTry;
         }

         if (this.serverRuntimeMBean != null) {
            maxTry = 3;
            Throwable shutdownErr = null;

            while(maxTry >= 0) {
               try {
                  if (this.forceShutdown) {
                     this.serverRuntimeMBean.forceShutdown();
                  } else {
                     this.serverRuntimeMBean.shutdown();
                  }
                  break;
               } catch (Throwable var51) {
                  --maxTry;
                  shutdownErr = var51;
               }
            }

            if (shutdownErr != null) {
               if (this.failOnError) {
                  throw new BuildException("cannot shutdown server: " + this.serverName, shutdownErr);
               }

               this.log("cannot shutdown server: " + this.serverName + " with below exception");
               shutdownErr.printStackTrace();
            }
         } else {
            if (this.failOnError) {
               throw new BuildException("cannot get ServerRuntimeMBean of " + this.serverName + " with " + maxTry + " times, it is not alive or cannot be shutdown.");
            }

            this.log("cannot get ServerRuntimeMBean of " + this.serverName + " with " + maxTry + " times, it is not alive or cannot be shutdown.");
         }
      } catch (SecurityException var52) {
         throw new BuildException(var52);
      } catch (Exception var53) {
         if (!(var53 instanceof CommunicationException) && !(var53 instanceof ConnectException) && !(var53 instanceof RemoteRuntimeException) && !(var53 instanceof PeerGoneException) && !(var53 instanceof SocketException)) {
            var53.printStackTrace();
         }
      } finally {
         this.connection = null;
         this.serverRuntimeMBean = null;
         if (this.connector != null) {
            try {
               this.connector.close();
            } catch (IOException var49) {
            } finally {
               this.connector = null;
            }
         }

      }

   }

   private void initConnection(String protocol, boolean sslEnabled, String hostname, String portString, String username, String password, long timeout) throws IOException, MalformedURLException, NumberFormatException {
      if (this.connector == null) {
         Integer portInteger = Integer.valueOf(portString);
         int port = portInteger;
         String jndiroot = "/jndi/";
         String mserver = "weblogic.management.mbeanservers.runtime";
         JMXServiceURL serviceURL = new JMXServiceURL(protocol, hostname, port, jndiroot + mserver);
         Hashtable h = new Hashtable();
         h.put("java.naming.security.principal", username);
         h.put("java.naming.security.credentials", password);
         h.put("jmx.remote.protocol.provider.pkgs", "weblogic.management.remote");
         if (timeout > 0L) {
            h.put("jmx.remote.x.request.waiting.timeout", new Long(timeout));
         }

         this.connector = JMXConnectorFactory.connect(serviceURL, h);
      }
   }

   private void getServerRuntimeMBean() {
      if (this.serverRuntimeMBean == null) {
         long jmxtimeout = -1L;
         String s = System.getProperty("wlserver.jmxtimeout");
         if (s != null) {
            jmxtimeout = (long)Integer.parseInt(s);
         }

         try {
            try {
               if (this.connector == null) {
                  this.initConnection(this.serverProtocol, false, this.serverHost, String.valueOf(this.serverPort), this.managementUserName, this.managementPassword, jmxtimeout);
               }

               RuntimeServiceMBean runtimeService = (RuntimeServiceMBean)MBeanServerInvocationHandler.newProxyInstance(this.connector.getMBeanServerConnection(), new ObjectName(RuntimeServiceMBean.OBJECT_NAME));
               this.serverRuntimeMBean = runtimeService.getServerRuntime();
            } catch (SecurityException var13) {
               throw new BuildException(var13);
            } catch (MalformedObjectNameException var14) {
               throw new BuildException(var14);
            } catch (MalformedURLException var15) {
               throw new BuildException(var15);
            } catch (IOException var16) {
               this.serverRuntimeMBean = null;
            } catch (RuntimeException var17) {
               throw new BuildException(var17);
            } catch (Throwable var18) {
               this.getProject().log("Error with getServerState()", var18, 3);
               this.serverRuntimeMBean = null;
            }

         } finally {
            ;
         }
      }
   }

   private String getServerState() {
      this.getServerRuntimeMBean();
      if (this.serverRuntimeMBean == null) {
         return "unknown";
      } else {
         try {
            return this.serverRuntimeMBean.getState();
         } catch (Throwable var2) {
            return "unknown";
         }
      }
   }

   public void run() {
      this.executeServer();
   }

   public void buildStarted(BuildEvent event) {
   }

   public void buildFinished(BuildEvent event) {
      if (this.noExit) {
         this.log("Server will not be killed due to noExit flag " + this.serverName);
      } else {
         try {
            this.log("Killing WLS Server Instance " + this.serverName);
            this.killServer();
         } catch (Exception var3) {
            this.log("Exception occurred while shutting down the server.");
            var3.printStackTrace();
         }

      }
   }

   public String getThreadDump(Thread watchedThread) {
      StackTraceElement[] stacks = watchedThread.getStackTrace();
      Throwable t = new Throwable();
      t.setStackTrace(stacks);
      StringWriter writer = new StringWriter();
      t.printStackTrace(new PrintWriter(writer));
      String s = writer.toString();
      s = s.substring(s.indexOf(10));
      return s;
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

   private class ThreadDumpAbleThreadFactory implements ThreadFactory {
      Thread theThread = null;

      public ThreadDumpAbleThreadFactory() {
      }

      public Thread newThread(Runnable r) {
         this.theThread = new Thread(r);
         return this.theThread;
      }

      public Thread getTargetThread() {
         return this.theThread;
      }
   }

   private class WaitForServerState extends Thread implements Callable {
      private List stateNames;
      private long timeout;

      public WaitForServerState(List stateNames, long timeout) {
         this.stateNames = stateNames;
         this.timeout = timeout;
      }

      public Boolean call() {
         if (WLServer.this.verbose) {
            System.err.println("waiting for " + WLServer.this.serverName + " to transition to " + this.stateNames + ", within " + this.timeout + " ms");
         }

         try {
            long waitingfor = 0L;
            String oldstate = "<not-initialized>";
            String newstate = "";
            if (WLServer.this.verbose) {
               System.err.println("querying " + WLServer.this.serverName + " for state every 500ms, timeout " + this.timeout + " ms");
            }

            while(waitingfor < this.timeout || this.timeout == 0L) {
               newstate = WLServer.this.getServerState();
               if (this.stateNames.contains(newstate.toUpperCase())) {
                  break;
               }

               try {
                  Thread.sleep(500L);
                  waitingfor += 500L;
                  if (!oldstate.equals(newstate)) {
                     if (WLServer.this.verbose) {
                        System.err.println("update: " + WLServer.this.serverName + " state transition from " + oldstate + " to " + newstate + ", after " + waitingfor + " ms");
                     }

                     oldstate = newstate;
                  }
               } catch (InterruptedException var6) {
               }
            }

            if (this.timeout > 0L && waitingfor >= this.timeout) {
               if (WLServer.this.verbose) {
                  System.err.println("timeout: " + WLServer.this.serverName + " failed to transition to state " + this.stateNames + ", after " + waitingfor + " ms");
               }

               return false;
            } else {
               if (WLServer.this.verbose) {
                  System.err.println("ok: " + WLServer.this.serverName + " transitioned to state " + this.stateNames + ", after " + waitingfor + " ms");
               }

               return true;
            }
         } catch (Exception var7) {
            Throwable e = var7.getCause() != null ? var7.getCause() : var7;
            if (!(e instanceof CommunicationException) && !(e instanceof ConnectException) && !(e instanceof RemoteRuntimeException) && !(e instanceof SocketException) && !(e instanceof PeerGoneException)) {
               ((Throwable)e).printStackTrace(System.out);
               return false;
            } else {
               return true;
            }
         }
      }
   }
}
