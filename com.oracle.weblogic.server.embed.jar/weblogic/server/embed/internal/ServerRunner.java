package weblogic.server.embed.internal;

import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import weblogic.version;
import weblogic.kernel.KernelStatus;
import weblogic.server.embed.EmbeddedServer;
import weblogic.server.embed.EmbeddedServerException;
import weblogic.t3.srvr.ServerRuntime;

class ServerRunner implements Runnable {
   static final Logger LOGGER;
   private static final Object waitLock;
   private static boolean beginServerStartMonitoring;
   private final EmbeddedServerImpl server;
   private final DomainConfig config;
   private volatile Throwable serverStartFailure;

   public ServerRunner(EmbeddedServerImpl server, DomainConfig config) {
      this.server = server;
      this.config = config;
   }

   public void run() {
      try {
         this.getWLSMainMethod().invoke((Object)null, new String[0]);
      } catch (Throwable var2) {
         this.serverStartFailure = var2;
      }

      if (this.serverStartFailure == null) {
         this.serverStartFailure = new EmbeddedServerException("Server main thread exited");
      }

      notifyStarterThread();
   }

   private Method getWLSMainMethod() {
      try {
         return Class.forName("weblogic.Server").getDeclaredMethod("main", String[].class);
      } catch (Exception var2) {
         throw new AssertionError(var2);
      }
   }

   public void startServer() throws EmbeddedServerException {
      KernelStatus.setIsEmbedded(true);

      Thread serverRunnerThread;
      try {
         serverRunnerThread = new Thread(this, "EmbeddedServer - Starting");
         serverRunnerThread.setDaemon(true);
         serverRunnerThread.start();
      } catch (Throwable var3) {
         this.server.setState(EmbeddedServer.State.FAILED);
         KernelStatus.setIsEmbedded(false);
         throw new EmbeddedServerException("Error starting server", var3);
      }

      LOGGER.info("Embedded " + version.getVersions());
      LOGGER.info("Embedded domain home " + this.config.getDomainHome());
      LOGGER.info("Server starting. Waiting for RUNNING Status");
      if (!waitForServerStart()) {
         this.server.setState(EmbeddedServer.State.FAILED);
         throw new EmbeddedServerException("Server startup failed. Refer to the  server logs for details at " + this.config.getDomainHome());
      } else if (this.serverStartFailure != null) {
         LOGGER.log(Level.SEVERE, "Error starting Server", this.serverStartFailure);
         throw new EmbeddedServerException("Error starting Server", this.serverStartFailure);
      } else {
         serverRunnerThread.setName("EmbeddedServer - Started");
         LOGGER.info("Server started.");
      }
   }

   public static boolean waitForServerStart() {
      synchronized(waitLock) {
         if (EmbeddedServerImpl.DEBUG) {
            EmbeddedServerImpl.LOGGER.info("Waiting for server to reach RUNNING status");
         }

         while(true) {
            boolean var10000;
            try {
               ServerRuntime sr;
               do {
                  do {
                     waitLock.wait(100L);
                  } while(!beginServerStartMonitoring);

                  sr = ServerRuntime.theOne();
               } while(sr == null);

               int state = sr.getStateVal();
               if (state == 2) {
                  var10000 = true;
                  return var10000;
               }

               if (state != 8) {
                  continue;
               }

               var10000 = false;
            } catch (InterruptedException var4) {
               return false;
            }

            return var10000;
         }
      }
   }

   public static void notifyStarterThread() {
      synchronized(waitLock) {
         waitLock.notifyAll();
      }
   }

   public static void monitorServerStart() {
      beginServerStartMonitoring = true;
   }

   static {
      LOGGER = EmbeddedServerImpl.LOGGER;
      waitLock = new Object();
      beginServerStartMonitoring = false;
   }
}
