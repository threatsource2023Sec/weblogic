package weblogic.servlet.internal;

import java.util.Map;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Optional;
import org.jvnet.hk2.annotations.Service;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;
import weblogic.server.ShutdownParametersBean;
import weblogic.servlet.internal.session.GracefulShutdownHelper;
import weblogic.servlet.spi.HttpServerManager;
import weblogic.servlet.spi.WebServerRegistry;

@Service
@Named
@RunLevel(20)
public class WebAppShutdownService extends AbstractServerService {
   @Inject
   @Named("ClientInitiatedTxShutdownService")
   private ServerService depdendencyOnShutdownService;
   @Inject
   @Named("DeploymentPostAdminServerService")
   private ServerService dependencyOnDeploymentPostAdminServerService;
   @Inject
   @Optional
   @Named("BuzzService")
   private ServerService dependencyOnBuzzService;
   private static boolean isSuspending;
   private static boolean isSuspended;
   private static boolean ignoreSessions;

   public static void ignoreSessionsDuringShutdown() {
      ignoreSessions = true;
   }

   public static boolean isSuspending() {
      return isSuspending;
   }

   public static boolean isSuspended() {
      return isSuspended;
   }

   static void setSuspending(boolean b) {
      isSuspending = b;
   }

   static void setSuspended(boolean b) {
      isSuspended = b;
   }

   static void setIgnoreSessions(boolean b) {
      ignoreSessions = b;
   }

   public void start() throws ServiceFailureException {
      if (isSuspended) {
         HttpServerManager httpSrvManager = WebServerRegistry.getInstance().getHttpServerManager();
         httpSrvManager.startWebServers();
      }

      setSuspending(false);
      setSuspended(false);
      setIgnoreSessions(false);
   }

   public void stop(Map directives) throws ServiceFailureException {
      setSuspending(true);
      Boolean ignoreSessions = null != directives ? (Boolean)directives.get("ignore.sessions") : null;
      if (null != ignoreSessions) {
         setIgnoreSessions(ignoreSessions);
      }

      super.stop(directives);
   }

   public void stop() throws ServiceFailureException {
      setSuspending(true);
      if (!ignoreSessions) {
         if (ShutdownParametersBean.getInstance().waitForAllSessions()) {
            GracefulShutdownHelper.waitForPendingSessions(true);
         } else {
            GracefulShutdownHelper.waitForPendingSessions(false);
         }
      }

      this.halt();
   }

   public void halt() {
      HttpServerManager httpSrvManager = WebServerRegistry.getInstance().getHttpServerManager();
      httpSrvManager.stopWebServers();
      setSuspended(true);
   }
}
