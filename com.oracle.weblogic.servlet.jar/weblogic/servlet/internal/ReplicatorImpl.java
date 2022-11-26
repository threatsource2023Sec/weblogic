package weblogic.servlet.internal;

import java.rmi.RemoteException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import weblogic.cluster.ClusterServices;
import weblogic.cluster.ClusterServicesActivator.Locator;
import weblogic.cluster.replication.ROID;
import weblogic.jndi.Environment;
import weblogic.servlet.HTTPLogger;
import weblogic.servlet.internal.session.ReplicatedSessionContext;
import weblogic.servlet.internal.session.SessionContext;
import weblogic.servlet.spi.SubjectHandle;
import weblogic.servlet.spi.WebServerRegistry;
import weblogic.utils.collections.ConcurrentWeakHashMap;

public class ReplicatorImpl implements Replicator {
   private static final Object PLACEHOLDER = new Object();
   private static ROIDLookupImpl roidImpl = null;
   private final Map sessionIDToROIDPrimaryMap = new ConcurrentHashMap(101);
   private final Map sessionIDToROIDSecondaryMap = new ConcurrentHashMap(101);
   private final Map secondaryROIDMap = new ConcurrentWeakHashMap(101);
   private HttpServer server;

   public ReplicatorImpl(HttpServer httpServer) {
      if (roidImpl == null) {
         Class var2 = ReplicatorImpl.class;
         synchronized(ReplicatorImpl.class) {
            if (roidImpl == null) {
               try {
                  roidImpl = new ROIDLookupImpl();
                  ClusterServices service = Locator.locateClusterServices();
                  if (service != null && service.isReplicationTimeoutEnabled()) {
                     weblogic.rmi.extensions.server.ServerHelper.exportObject(roidImpl, service.getHeartbeatTimeoutMillis());
                  } else {
                     weblogic.rmi.extensions.server.ServerHelper.exportObject(roidImpl);
                  }
               } catch (RemoteException var5) {
                  throw new AssertionError("ROIDLookupImpl initial reference could not be exported.");
               }
            }
         }
      }

      this.server = httpServer;
   }

   public ROID getPrimary(String sessionID) {
      SessionIDToROIDMapHolder holder = (SessionIDToROIDMapHolder)this.sessionIDToROIDPrimaryMap.get(sessionID);
      return holder == null ? null : holder.getROID();
   }

   public ROID getSecondary(String sessionID) {
      SessionIDToROIDMapHolder holder = (SessionIDToROIDMapHolder)this.sessionIDToROIDSecondaryMap.get(sessionID);
      return holder == null ? null : holder.getROID();
   }

   public ROID[] getSecondaryIds() {
      ROID[] result = null;
      synchronized(this.secondaryROIDMap) {
         int size = this.secondaryROIDMap.size();
         if (size < 1) {
            return new ROID[0];
         } else {
            result = new ROID[size];
            this.secondaryROIDMap.keySet().toArray(result);
            return result;
         }
      }
   }

   public ROID lookupROID(final String sessionid, String url, final String cookieName, final String cookiePath, boolean forceRemoteLookup) throws RemoteException {
      if (!forceRemoteLookup) {
         WebAppServletContext[] webapps = this.server.getServletContextManager().getAllContexts();
         if (webapps == null) {
            return null;
         }

         for(int i = 0; i < webapps.length; ++i) {
            SessionContext ctx = webapps[i].getSessionContext();
            if (ctx.getPersistentStoreType() == "replicated" && ctx.getConfigMgr().getCookieName().equals(cookieName) && ctx.getConfigMgr().getCookiePath().equals(cookiePath)) {
               ReplicatedSessionContext context = (ReplicatedSessionContext)ctx;
               if (context.getROID(sessionid) != null) {
                  return null;
               }
            }
         }
      }

      final ROIDLookup localStub = getROIDLookup(url, this.server);
      if (localStub == null) {
         return null;
      } else {
         SubjectHandle subject = (SubjectHandle)AccessController.doPrivileged(new PrivilegedAction() {
            public SubjectHandle run() {
               return WebServerRegistry.getInstance().getSecurityProvider().getCurrentSubject();
            }
         });
         if (subject != null && subject.isAdmin()) {
            try {
               Object result = WebServerRegistry.getInstance().getSecurityProvider().getAnonymousSubject().run(new PrivilegedExceptionAction() {
                  public Object run() throws RemoteException {
                     return localStub.lookupROID(ReplicatorImpl.this.server.getName(), sessionid, cookieName, cookiePath);
                  }
               });
               return (ROID)result;
            } catch (PrivilegedActionException var10) {
               throw (RemoteException)var10.getException();
            }
         } else {
            return localStub.lookupROID(this.server.getName(), sessionid, cookieName, cookiePath);
         }
      }
   }

   public ROID lookupROID(String sessionid, String url, String cookieName, String cookiePath) throws RemoteException {
      return this.lookupROID(sessionid, url, cookieName, cookiePath, false);
   }

   public void putPrimary(String sessionID, ROID roid, String contextName) {
      this.removeSecondary(sessionID, contextName);
      SessionIDToROIDMapHolder holder = (SessionIDToROIDMapHolder)this.sessionIDToROIDPrimaryMap.get(sessionID);
      if (holder == null) {
         holder = new SessionIDToROIDMapHolder(roid);
         this.sessionIDToROIDPrimaryMap.put(sessionID, holder);
      }

      holder.add(contextName);
   }

   public void putSecondary(String sessionID, ROID roid, String contextName) {
      this.removePrimary(sessionID, contextName);
      SessionIDToROIDMapHolder holder = (SessionIDToROIDMapHolder)this.sessionIDToROIDSecondaryMap.get(sessionID);
      if (holder == null) {
         holder = new SessionIDToROIDMapHolder(roid);
         this.sessionIDToROIDSecondaryMap.put(sessionID, holder);
      }

      holder.add(contextName);
      this.secondaryROIDMap.put(roid, PLACEHOLDER);
   }

   public void removePrimary(String sessionID, String contextName) {
      SessionIDToROIDMapHolder holder = (SessionIDToROIDMapHolder)this.sessionIDToROIDPrimaryMap.get(sessionID);
      if (holder != null && holder.remove(contextName)) {
         this.sessionIDToROIDPrimaryMap.remove(sessionID);
      }

   }

   public void removeSecondary(String sessionID, String contextName) {
      SessionIDToROIDMapHolder holder = (SessionIDToROIDMapHolder)this.sessionIDToROIDSecondaryMap.get(sessionID);
      if (holder != null && holder.remove(contextName)) {
         this.sessionIDToROIDSecondaryMap.remove(sessionID);
         this.secondaryROIDMap.remove(holder.getROID());
      }

   }

   public void updateROIDLastAccessTimes(String url, ROID[] roids, long[] lats, String contextPath) {
      try {
         ROIDLookup localStub = getROIDLookup(url, this.server);
         if (localStub == null) {
            return;
         }

         localStub.updateLastAccessTimes(this.server.getName(), roids, lats, System.currentTimeMillis(), contextPath);
      } catch (RemoteException var6) {
         HTTPLogger.logFailedToPerformBatchedLATUpdate(this.server.getName(), (String)null, url, 0, 0, var6);
      }

   }

   private static ROIDLookup getROIDLookup(String url, HttpServer server) {
      try {
         Environment env = new Environment();
         env.setProviderUrl(url);
         env.setProviderChannel(server.getReplicationChannel());
         return (ROIDLookup)env.getInitialReference(ROIDLookupImpl.class);
      } catch (Throwable var3) {
         return null;
      }
   }

   public boolean isAvailableInOtherCtx(final String sessionid, String url, final String contextPath, final String cookieName, final String cookiePath) throws RemoteException {
      final ROIDLookup localStub = getROIDLookup(url, this.server);
      if (localStub == null) {
         return false;
      } else {
         SubjectHandle subject = (SubjectHandle)AccessController.doPrivileged(new PrivilegedAction() {
            public SubjectHandle run() {
               return WebServerRegistry.getInstance().getSecurityProvider().getCurrentSubject();
            }
         });
         if (subject != null && subject.isAdmin()) {
            try {
               Object result = WebServerRegistry.getInstance().getSecurityProvider().getAnonymousSubject().run(new PrivilegedExceptionAction() {
                  public Object run() throws RemoteException {
                     return localStub.isAvailableInOtherCtx(ReplicatorImpl.this.server.getName(), sessionid, contextPath, cookieName, cookiePath);
                  }
               });
               return (Boolean)result;
            } catch (PrivilegedActionException var9) {
               throw (RemoteException)var9.getException();
            }
         } else {
            return localStub.isAvailableInOtherCtx(this.server.getName(), sessionid, contextPath, cookieName, cookiePath);
         }
      }
   }

   private static class SessionIDToROIDMapHolder {
      private final HashSet set;
      private final ROID id;

      private SessionIDToROIDMapHolder(ROID id) {
         this.set = new HashSet();
         this.id = id;
      }

      private synchronized void add(String contextName) {
         this.set.add(contextName);
      }

      private synchronized boolean remove(String contextname) {
         this.set.remove(contextname);
         return this.set.size() == 0;
      }

      private ROID getROID() {
         return this.id;
      }

      // $FF: synthetic method
      SessionIDToROIDMapHolder(ROID x0, Object x1) {
         this(x0);
      }
   }
}
