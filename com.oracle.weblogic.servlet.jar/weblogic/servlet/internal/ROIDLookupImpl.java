package weblogic.servlet.internal;

import java.rmi.RemoteException;
import weblogic.cluster.replication.ROID;
import weblogic.cluster.replication.ReplicationServices;
import weblogic.cluster.replication.ReplicationServicesFactory.Locator;
import weblogic.cluster.replication.ReplicationServicesFactory.ServiceType;
import weblogic.rmi.spi.HostID;
import weblogic.servlet.internal.session.HTTPSessionLogger;
import weblogic.servlet.internal.session.ReplicatedSessionContext;
import weblogic.servlet.internal.session.SessionContext;
import weblogic.servlet.internal.session.SessionData;
import weblogic.servlet.spi.HttpServerManager;
import weblogic.servlet.spi.WebServerRegistry;

public final class ROIDLookupImpl implements ROIDLookup {
   public ROID lookupROID(String httpServerName, String sessionID, String cookieName, String cookiePath) throws RemoteException {
      return this.lookupROIDInternal(getHttpServerByName(httpServerName), sessionID, cookieName, cookiePath);
   }

   private static HttpServer getHttpServerByName(String httpServerName) {
      HttpServerManager httpSrvManager = WebServerRegistry.getInstance().getHttpServerManager();
      if (httpServerName == null) {
         return httpSrvManager.defaultHttpServer();
      } else {
         HttpServer httpSrvr = httpSrvManager.getHttpServer(httpServerName);
         httpSrvr = httpSrvr == null ? httpSrvManager.defaultHttpServer() : httpSrvr;
         return httpSrvr;
      }
   }

   private HttpServer getDefaultServer() {
      HttpServerManager httpSrvManager = WebServerRegistry.getInstance().getHttpServerManager();
      return httpSrvManager.defaultHttpServer();
   }

   public ROID lookupROID(String sessionID, String cookieName, String cookiePath) throws RemoteException {
      return this.lookupROIDInternal(getHttpServerByName((String)null), sessionID, cookieName, cookiePath);
   }

   private ROID lookupROIDInternal(HttpServer httpSrvr, String sessionID, String cookieName, String cookiePath) throws RemoteException {
      WebAppServletContext[] webapps = httpSrvr.getServletContextManager().getAllContexts();
      if (webapps == null) {
         return null;
      } else {
         for(int i = 0; i < webapps.length; ++i) {
            ROID roid = this.getROID(sessionID, cookieName, cookiePath, webapps[i]);
            if (roid != null) {
               return roid;
            }
         }

         return null;
      }
   }

   private ROID getROID(String sessionID, String cookieName, String cookiePath, WebAppServletContext webapp) {
      SessionContext ctx = webapp.getSessionContext();
      if (ctx instanceof ReplicatedSessionContext && ctx.getConfigMgr().getCookieName().equals(cookieName) && ctx.getConfigMgr().getCookiePath().equals(cookiePath)) {
         ReplicatedSessionContext context = (ReplicatedSessionContext)ctx;
         ROID roid = context.getROID(sessionID);
         if (roid != null) {
            return roid;
         }
      }

      return null;
   }

   public void updateLastAccessTimes(String httpServerName, ROID[] roid, long[] lat, long timeOnPrimary, String contextPath) throws RemoteException {
      this.updateLastAccessTimesInternal(getHttpServerByName(httpServerName), roid, lat, timeOnPrimary, contextPath);
   }

   public void updateLastAccessTimes(ROID[] roid, long[] lat, long timeOnPrimary, String contextPath) throws RemoteException {
      this.updateLastAccessTimesInternal(getHttpServerByName((String)null), roid, lat, timeOnPrimary, contextPath);
   }

   private void updateLastAccessTimesInternal(HttpServer httpSrvr, ROID[] roid, long[] lat, long timeOnPrimary, String contextPath) throws RemoteException {
      if (roid != null && roid.length >= 1) {
         long diff = System.currentTimeMillis() - timeOnPrimary;
         WebAppServletContext servletContext = httpSrvr.getServletContextManager().getContextForContextPath(contextPath);
         if (servletContext != null) {
            try {
               ReplicatedSessionContext sessionContext = (ReplicatedSessionContext)servletContext.getSessionContext();

               for(int i = 0; i < roid.length; ++i) {
                  sessionContext.updateSecondaryLAT(roid[i], lat[i] + diff);
               }

            } catch (ClassCastException var12) {
               HTTPSessionLogger.logPersistentStoreTypeNotReplicated(contextPath, "updateLastAccessTimes");
            }
         }
      }
   }

   public void unregister(ROID id, Object[] internalIDs) throws RemoteException {
      ReplicationServices service = Locator.locate().getReplicationService(ServiceType.SYNC);

      for(int i = 0; i < internalIDs.length; ++i) {
         service.unregister(id, internalIDs[i]);
      }

   }

   public boolean roidExists(String httpServerName, String sessionID) {
      HttpServer httpSrvr = getHttpServerByName(httpServerName);
      if (httpSrvr != null) {
         WebAppServletContext[] webapps = httpSrvr.getServletContextManager().getAllContexts();
         if (webapps == null) {
            return false;
         }

         for(int i = 0; i < webapps.length; ++i) {
            SessionContext ctx = webapps[i].getSessionContext();
            if (ctx instanceof ReplicatedSessionContext) {
               ReplicatedSessionContext context = (ReplicatedSessionContext)ctx;
               ROID roid = context.getROID(sessionID);
               if (roid != null) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   private static ROID findROID(String httpServerName, String sessionID) {
      HttpServer httpSrvr = getHttpServerByName(httpServerName);
      if (httpSrvr != null) {
         WebAppServletContext[] webapps = httpSrvr.getServletContextManager().getAllContexts();
         if (webapps == null) {
            return null;
         }

         for(int i = 0; i < webapps.length; ++i) {
            SessionContext ctx = webapps[i].getSessionContext();
            if (ctx instanceof ReplicatedSessionContext) {
               ReplicatedSessionContext context = (ReplicatedSessionContext)ctx;
               ROID roid = context.getROID(sessionID);
               if (roid != null) {
                  return roid;
               }
            }
         }
      }

      return null;
   }

   public HostID[] getPrimarySecondaryHosts(String httpServerName, String sessionID) {
      ROID roid = findROID(httpServerName, sessionID);
      if (roid != null) {
         ReplicationServices service = Locator.locate().getReplicationService(ServiceType.SYNC);
         return service.getPrimarySecondaryHosts(roid);
      } else {
         return null;
      }
   }

   public boolean isAvailableInOtherCtx(String httpServerName, String sessionID, String contextPath, String cookieName, String cookiePath) throws RemoteException {
      HttpServer httpServer = getHttpServerByName(httpServerName);
      WebAppServletContext[] webapps = httpServer.getServletContextManager().getAllContexts();
      if (webapps != null && webapps.length != 0) {
         for(int i = 0; i < webapps.length; ++i) {
            SessionContext ctx = webapps[i].getSessionContext();
            if (ctx instanceof ReplicatedSessionContext && ctx.getConfigMgr().getCookieName().equals(cookieName) && ctx.getConfigMgr().getCookiePath().equals(cookiePath) && !ctx.getServletContext().getContextPath().equals(contextPath)) {
               ReplicatedSessionContext context = (ReplicatedSessionContext)ctx;
               if (context.getSessionInternal(SessionData.getID(sessionID), (ServletRequestImpl)null, (ServletResponseImpl)null) != null || context.getSecondarySessionInternal(SessionData.getID(sessionID)) != null) {
                  return true;
               }
            }
         }

         return false;
      } else {
         return false;
      }
   }
}
