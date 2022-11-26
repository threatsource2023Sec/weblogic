package weblogic.t3.srvr;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import javax.inject.Named;
import org.glassfish.hk2.api.Rank;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.kernel.Kernel;
import weblogic.kernel.T3Srvr2Logger;
import weblogic.kernel.T3SrvrLogger;
import weblogic.management.bootstrap.BootStrap;
import weblogic.management.configuration.ConfigurationException;
import weblogic.net.http.Handler;
import weblogic.security.utils.SSLSetup;
import weblogic.server.AbstractServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(5)
@Rank(100)
public class PreConfigBootService extends AbstractServerService {
   public void start() throws ServiceFailureException {
      try {
         boolean nodeMgrSvcEnabled = Boolean.getBoolean("weblogic.nodemanager.ServiceEnabled");
         String stdErrLog;
         if (!nodeMgrSvcEnabled) {
            stdErrLog = System.getProperty("weblogic.Stdout");
            if (stdErrLog != null) {
               try {
                  System.setOut(getPrintStream(stdErrLog));
               } catch (Exception var5) {
                  T3Srvr2Logger.logErrorRedirectingStream("stdout", var5);
               }
            }
         }

         stdErrLog = System.getProperty("weblogic.Stderr");
         if (stdErrLog != null) {
            try {
               System.setErr(getPrintStream(stdErrLog));
            } catch (Exception var4) {
               T3Srvr2Logger.logErrorRedirectingStream("stderr", var4);
            }
         }

         T3SrvrLogger.logServerStarting(System.getProperty("java.vm.name"), System.getProperty("java.vm.version"), System.getProperty("java.vm.vendor"));
         String home = BootStrap.getWebLogicHome();
         if (home == null) {
            throw new ConfigurationException("Property weblogic.home must be set to run WebLogic Server.  This should be set to the location of your WebLogic Server install (i.e. -Dweblogic.home=/bea/wlserver[version])");
         } else {
            SSLSetup.initForServer();
            Handler.init();
            Kernel.setIsServer(true);
         }
      } catch (ConfigurationException var6) {
         T3SrvrLogger.logConfigFailure(var6.getMessage());
         throw new ServiceFailureException(var6);
      }
   }

   private static PrintStream getPrintStream(String filename) throws IOException {
      File f = new File(filename);
      if (!f.exists()) {
         File dir = f.getParentFile();
         if (dir != null && !dir.exists()) {
            dir.mkdirs();
         }
      }

      return new PrintStream(new FileOutputStream(filename, true));
   }

   public void stop() {
      this.shutdown();
   }

   public void halt() {
      this.shutdown();
   }

   public void shutdown() {
   }
}
