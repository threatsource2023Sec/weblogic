package weblogic.server.embed.internal;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;
import weblogic.kernel.KernelStatus;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.server.embed.Config;
import weblogic.server.embed.Deployer;
import weblogic.server.embed.EmbeddedServer;
import weblogic.server.embed.EmbeddedServerException;
import weblogic.server.embed.EmbeddedServerFactory;
import weblogic.t3.srvr.T3Srvr;

public final class EmbeddedServerImpl implements EmbeddedServer {
   static final boolean DEBUG = Boolean.getBoolean("weblogic.server.embed.debug");
   static final Logger LOGGER = EmbeddedServerProvider.get().getLogger();
   private final ServerRunner serverRunner;
   private final DomainConfig domainConfig;
   private final Deployer deployer;
   private final File domainHome;
   private EmbeddedServer.State state;

   public static EmbeddedServerImpl get() {
      return EmbeddedServerImpl.SingletonMaker.INSTANCE;
   }

   private void validateRuntime() {
      if (KernelStatus.isInitialized()) {
         throw new IllegalStateException("Server kernel is already initialized. This API can only be used in standalone mode.");
      }
   }

   private EmbeddedServerImpl() {
      this.validateRuntime();
      T3Srvr.initSubjectManager();
      String d = System.getProperty("weblogic.RootDirectory");
      this.domainHome = d == null ? createDomainDir() : new File(d);
      this.domainConfig = new DomainConfig(this.domainHome);
      this.deployer = new StandardDeployer();
      this.serverRunner = new ServerRunner(this, this.domainConfig);
      this.addShutdownHook();
      this.state = EmbeddedServer.State.NEW;
      EmbeddedServerProvider.get();
   }

   public Logger getLogger() {
      return LOGGER;
   }

   public Config getConfig() {
      return this.domainConfig;
   }

   public Deployer getDeployer() throws EmbeddedServerException {
      this.ensureStarted();
      this.assertServerRunning();
      return this.deployer;
   }

   public EmbeddedServer.State getState() {
      return this.state;
   }

   public void start() throws EmbeddedServerException {
      this.ensureStarted();
   }

   public void cleanupOnExit() {
      this.domainHome.deleteOnExit();
   }

   private static AuthenticatedSubject getKernelId() {
      return (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   }

   private static ServerRuntimeMBean getServerRuntime() {
      return ManagementService.getRuntimeAccess(getKernelId()).getServerRuntime();
   }

   public void suspend() throws EmbeddedServerException {
      this.assertServerRunning();
      AuthenticatedSubject kernelId = getKernelId();
      SecurityServiceManager.runAs(kernelId, kernelId, new PrivilegedAction() {
         public Object run() {
            try {
               EmbeddedServerImpl.getServerRuntime().suspend();
            } catch (Throwable var2) {
               EmbeddedServerImpl.LOGGER.severe(var2.getMessage());
            }

            return null;
         }
      });
      this.setState(EmbeddedServer.State.SUSPENDED);
   }

   public void shutdown() throws EmbeddedServerException {
      if (get().getState() != EmbeddedServer.State.STARTED && get().getState() != EmbeddedServer.State.SUSPENDED) {
         throw new EmbeddedServerException("Embedded server can only shutdown from STARTED or SUSPENDED state");
      } else {
         AuthenticatedSubject kernelId = getKernelId();
         SecurityServiceManager.runAs(kernelId, kernelId, new PrivilegedAction() {
            public Object run() {
               try {
                  EmbeddedServerImpl.getServerRuntime().shutdown();
               } catch (Throwable var2) {
                  EmbeddedServerImpl.LOGGER.severe(var2.getMessage());
               }

               return null;
            }
         });
         this.setState(EmbeddedServer.State.CONFIGURED);
      }
   }

   public void resume() throws EmbeddedServerException {
      if (get().getState() != EmbeddedServer.State.SUSPENDED) {
         throw new EmbeddedServerException("Embedded server can only resume from SUSPENDED state");
      } else {
         AuthenticatedSubject kernelId = getKernelId();
         SecurityServiceManager.runAs(kernelId, kernelId, new PrivilegedAction() {
            public Object run() {
               try {
                  EmbeddedServerImpl.getServerRuntime().resume();
               } catch (Throwable var2) {
                  EmbeddedServerImpl.LOGGER.severe(var2.getMessage());
               }

               return null;
            }
         });
         this.setState(EmbeddedServer.State.STARTED);
      }
   }

   public HttpURLConnection getHttpURLConnection(URL u) throws IOException, EmbeddedServerException {
      throw new UnsupportedOperationException("Unimplemented");
   }

   public synchronized void ensureStarted() throws EmbeddedServerException {
      if (this.state != EmbeddedServer.State.STARTED) {
         if (this.state == EmbeddedServer.State.SUSPENDED) {
            this.resume();
         } else {
            this.assertServerHealthy();
            this.domainConfig.ensureInitialized();
            this.domainConfig.saveConfig();
            if (DEBUG) {
               LOGGER.info("Really starting server...");
            }

            this.serverRunner.startServer();
            this.setState(EmbeddedServer.State.STARTED);
         }

      }
   }

   private static File createDomainDir() {
      String tempDir = System.getProperty("java.io.tmpdir");
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss");
      String dateString = formatter.format(new Date());
      File dir = new File(tempDir, "domain-" + dateString);
      dir.mkdirs();
      return dir;
   }

   public static int getServerState() {
      return getServerRuntime().getStateVal();
   }

   public boolean isStarted() {
      return this.state == EmbeddedServer.State.STARTED;
   }

   public boolean isFailed() {
      return this.state == EmbeddedServer.State.FAILED;
   }

   public void assertServerRunning() throws EmbeddedServerException {
      if (get().getState() != EmbeddedServer.State.STARTED) {
         throw new EmbeddedServerException("Embedded server not running yet");
      }
   }

   public void assertServerHealthy() throws EmbeddedServerException {
      if (get().getState() == EmbeddedServer.State.FAILED) {
         throw new EmbeddedServerException("Embedded server in FAILED state");
      }
   }

   public static void main(String[] xargs) throws Exception {
      EmbeddedServerImpl server = (EmbeddedServerImpl)EmbeddedServerFactory.getEmbeddedServer();
      server.start();
      Deployer deployer = server.getDeployer();
      server.getLogger().info("Start started!!!");
      System.out.println("Welcome to the embedded server interactive console");
      System.out.println();
      Console console = System.console();

      while(true) {
         System.out.println();
         System.out.print("Embedded WLS > ");
         String cmd = console.readLine();
         String[] args = cmd.split(" ");
         cmd = args[0].trim();
         long t0 = System.currentTimeMillis();

         try {
            if (cmd.equals("deploy")) {
               if (args[1].equalsIgnoreCase("lib")) {
                  deployer.deployLib(args[2], new File(args[3]));
               } else {
                  deployer.deployApp(args[2], new File(args[3]));
               }
            } else if (cmd.equals("undeploy")) {
               if (args[1].equalsIgnoreCase("lib")) {
                  deployer.undeployLib(args[2]);
               } else {
                  deployer.undeployApp(args[2]);
               }

               System.out.println("Undeploy operation successful");
            } else if (cmd.equals("redeploy")) {
               if (args[1].equalsIgnoreCase("lib")) {
                  deployer.redeployLib(args[2]);
               } else {
                  deployer.redeployApp(args[2]);
               }
            } else if (cmd.equals("exit")) {
               System.out.println("Shutdown initialized");
               System.exit(0);
            } else {
               System.out.println("Error: unrecognized command " + cmd);
               System.out.println("Supported commands: ");
               System.out.println("   deploy   [app|lib] <name> <path>");
               System.out.println("   undeploy [all|lib] <name>");
               System.out.println("   redeploy [app|lib] <name>");
               System.out.println("   exit");
            }

            System.out.println();
            System.out.println("[Command '" + cmd + "' took: " + (System.currentTimeMillis() - t0) + "ms.]");
         } catch (Throwable var9) {
            var9.printStackTrace();
         }
      }
   }

   public void setState(EmbeddedServer.State newState) throws EmbeddedServerException {
      if (!this.isValidTransition(newState)) {
         throw new EmbeddedServerException("Invalid state transition " + this.state + " -> " + newState);
      } else {
         this.state = newState;
      }
   }

   private boolean isValidTransition(EmbeddedServer.State newState) {
      if (this.state == newState) {
         return true;
      } else if (this.state == EmbeddedServer.State.NEW) {
         return newState == EmbeddedServer.State.CONFIGURED;
      } else if (this.state == EmbeddedServer.State.CONFIGURED) {
         return newState == EmbeddedServer.State.STARTED || newState == EmbeddedServer.State.FAILED;
      } else if (this.state == EmbeddedServer.State.STARTED) {
         return newState == EmbeddedServer.State.SUSPENDED || newState == EmbeddedServer.State.CONFIGURED;
      } else if (this.state != EmbeddedServer.State.SUSPENDED) {
         return false;
      } else {
         return newState == EmbeddedServer.State.STARTED || newState == EmbeddedServer.State.CONFIGURED;
      }
   }

   private void addShutdownHook() {
      Runtime.getRuntime().addShutdownHook(new Thread() {
         public void run() {
            EmbeddedServerImpl.LOGGER.info("Embedded Server shutting down...");
         }
      });
   }

   // $FF: synthetic method
   EmbeddedServerImpl(Object x0) {
      this();
   }

   private static final class SingletonMaker {
      private static final EmbeddedServerImpl INSTANCE = new EmbeddedServerImpl();
   }
}
