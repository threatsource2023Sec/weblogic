package weblogic.servlet.internal.session;

import java.lang.reflect.Constructor;
import weblogic.application.ApplicationContextInternal;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.i18n.logging.Loggable;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.ClusterMBean;
import weblogic.servlet.internal.HttpServer;
import weblogic.servlet.internal.Replicator;
import weblogic.servlet.internal.ReplicatorImpl;
import weblogic.servlet.internal.WebAppServletContext;
import weblogic.servlet.spi.WebServerRegistry;

public class SessionContextFactoryImpl implements SessionContextFactory {
   protected static final DebugLogger DEBUG_SESSIONS = DebugLogger.getDebugLogger("DebugHttpSessions");
   private static final ClusterMBean cluster = WebServerRegistry.getInstance().getClusterMBean();

   public final synchronized SessionContext createSessionContext(WebAppServletContext sci) throws DeploymentException {
      SessionConfigManager scm = sci.getSessionConfigManager();
      this.validate(sci, scm);
      String pst = scm.getPersistentStoreType();
      SessionContext sessionContext = this.createSessionContext(sci, scm, pst);
      if (sessionContext == null) {
         Loggable l = HTTPSessionLogger.logUnknownPeristentTypeLoggable(pst, sci.getAppDisplayName());
         l.log();
         throw new DeploymentException(l.getMessage());
      } else {
         return sessionContext;
      }
   }

   public Replicator createReplicator(HttpServer server) {
      return new ReplicatorImpl(server);
   }

   private SessionContext createSessionContext(WebAppServletContext sci, SessionConfigManager scm, String pst) throws DeploymentException {
      SessionContext sc = this.createCoherenceSessionContext(sci, scm, pst);
      if (sc == null) {
         sc = this.createMemorySessionContext(sci, scm, pst);
      }

      if (sc == null) {
         sc = this.createFileSessionContext(sci, scm, pst);
      }

      if (sc == null) {
         sc = this.createCookieSessionContext(sci, scm, pst);
      }

      if (sc == null) {
         sc = this.createPersistentSessionContext(sci, scm, pst);
      }

      if (sc == null) {
         sc = this.createJdbcSessionContext(sci, scm, pst);
      }

      if (sc != null) {
         this.logSessionContextType(sc.getPersistentStoreType(), sci);
      }

      return sc;
   }

   private void validate(WebAppServletContext sci, SessionConfigManager scm) throws DeploymentException {
      if (sci != null && sci.getVersionId() != null && sci.getContextManager() != null) {
         WebAppServletContext ctx = sci.getContextManager().getContext(sci.isAdminMode());
         if (ctx != null && ctx.getVersionId() != null && !scm.isSamePersistentStore(ctx.getSessionContext().getConfigMgr())) {
            Loggable l = HTTPSessionLogger.logIncompatiblePersistentStoreLoggable(sci.getAppDisplayName());
            l.log();
            throw new DeploymentException(l.getMessage());
         }
      }

   }

   private SessionContext createPersistentSessionContext(WebAppServletContext sci, SessionConfigManager scm, String pst) throws DeploymentException {
      if (!"replicated".equals(pst) && !"async-replicated".equals(pst) && !"replicated_if_clustered".equals(pst) && !"async-replicated-if-clustered".equals(pst)) {
         return null;
      } else {
         boolean async = "async-replicated".equals(pst) || "async-replicated-if-clustered".equals(pst);
         boolean ifClustered = "replicated_if_clustered".equals(pst) || "async-replicated-if-clustered".equals(pst);
         if (ifClustered && cluster == null) {
            return new MemorySessionContext(sci, scm);
         } else {
            SessionContext sessionContext = null;

            try {
               this.checkClusterAvailability(sci);
               TargetValidator.validateTargetting(sci.getWebAppModule(), pst);
               if (!cluster.getPersistSessionsOnShutdown() && !"wan".equals(cluster.getClusterType())) {
                  if ("man".equals(cluster.getClusterType())) {
                     sessionContext = async ? new MANAsyncReplicatedSessionContext(sci, scm) : new MANReplicatedSessionContext(sci, scm);
                  } else {
                     sessionContext = async ? new AsyncReplicatedSessionContext(sci, scm) : new ReplicatedSessionContext(sci, scm);
                  }
               } else {
                  this.checkClusterConfiguration(sci);
                  sessionContext = async ? new WANAsyncSessionContext(sci, scm) : new WANSessionContext(sci, scm);
               }
            } catch (RuntimeException var8) {
               HTTPSessionLogger.logSessionNotAllowed(var8.getMessage());
               sessionContext = new MemorySessionContext(sci, scm);
            }

            return (SessionContext)sessionContext;
         }
      }
   }

   private SessionContext createJdbcSessionContext(WebAppServletContext sci, SessionConfigManager scm, String pst) throws DeploymentException {
      if ("jdbc".equals(pst)) {
         return new JDBCSessionContext(sci, scm);
      } else {
         return "async-jdbc".equals(pst) ? new AsyncJDBCSessionContext(sci, scm) : null;
      }
   }

   private SessionContext createMemorySessionContext(WebAppServletContext sci, SessionConfigManager scm, String pst) throws DeploymentException {
      return "memory".equals(pst) ? new MemorySessionContext(sci, scm) : null;
   }

   private SessionContext createFileSessionContext(WebAppServletContext sci, SessionConfigManager scm, String pst) throws DeploymentException {
      return "file".equals(pst) ? new FileSessionContext(sci, scm) : null;
   }

   private SessionContext createCookieSessionContext(WebAppServletContext sci, SessionConfigManager scm, String pst) throws DeploymentException {
      return "cookie".equals(pst) ? new CookieSessionContext(sci, scm) : null;
   }

   private SessionContext createCoherenceSessionContext(WebAppServletContext sci, SessionConfigManager scm, String pst) throws DeploymentException {
      boolean contextParamEnabled = "true".equalsIgnoreCase(sci.getInitParameter("coherence-web-sessions-enabled"));
      ApplicationContextInternal appCtx = sci.getApplicationContext();
      boolean appParamEnabled = appCtx != null && "true".equalsIgnoreCase(appCtx.getApplicationParameter("coherence-web-sessions-enabled"));
      if (!contextParamEnabled && !appParamEnabled && !"coherence-web".equals(pst)) {
         return null;
      } else {
         try {
            Class cwsc = sci.getServletClassLoader().loadClass("weblogic.servlet.internal.session.CoherenceWebSessionContextImpl");
            Class[] argClasses = new Class[]{sci.getClass(), scm.getClass()};
            Constructor c = cwsc.getConstructor(argClasses);
            Object[] args = new Object[]{sci, scm};
            return (SessionContext)c.newInstance(args);
         } catch (ClassNotFoundException var11) {
            throw new DeploymentException("This web-application was configured to use Coherence*Web sessions, but either the Coherence*Web session SPI library is not found in the web-application classloader or coherence.jar was not found in the WebLogic system classloader.", var11);
         } catch (Exception var12) {
            throw new DeploymentException("An error occurred instantiating Coherence*Web sessions for this web application", var12);
         }
      }
   }

   protected void logSessionContextType(String pst, WebAppServletContext sci) {
      if (DEBUG_SESSIONS.isDebugEnabled()) {
         String appName = sci.getAppDisplayName();
         Loggable l = HTTPSessionLogger.logCreatingSessionContextOfTypeLoggable(pst, appName);
         DEBUG_SESSIONS.debug(l.getMessage());
      }

   }

   private void checkClusterAvailability(WebAppServletContext sci) throws DeploymentException {
      if (cluster == null) {
         String appName = sci.getAppDisplayName();
         Loggable l = HTTPSessionLogger.logClusteringRequiredForReplicationLoggable(appName);
         l.log();
         throw new DeploymentException(l.getMessage());
      }
   }

   private void checkClusterConfiguration(WebAppServletContext sci) throws DeploymentException {
      if (null == cluster.getDataSourceForSessionPersistence()) {
         String appName = sci.getAppDisplayName();
         Loggable l = HTTPSessionLogger.logInsufficientConfigurationLoggable(appName);
         l.log();
         throw new DeploymentException(l.getMessage());
      }
   }
}
