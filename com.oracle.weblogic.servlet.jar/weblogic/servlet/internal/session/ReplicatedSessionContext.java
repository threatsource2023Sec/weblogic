package weblogic.servlet.internal.session;

import java.rmi.RemoteException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import weblogic.cluster.ClusterServices;
import weblogic.cluster.replication.NotFoundException;
import weblogic.cluster.replication.ROID;
import weblogic.cluster.replication.ReplicationServices;
import weblogic.cluster.replication.ReplicationServicesFactory.Locator;
import weblogic.cluster.replication.ReplicationServicesFactory.ServiceType;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.invocation.PartitionTable;
import weblogic.invocation.PartitionTableEntry;
import weblogic.logging.Loggable;
import weblogic.protocol.LocalServerIdentity;
import weblogic.protocol.ServerIdentity;
import weblogic.protocol.URLManager;
import weblogic.rmi.extensions.RemoteSystemException;
import weblogic.rmi.extensions.RequestTimeoutException;
import weblogic.rmi.spi.HostID;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.servlet.internal.HttpServer;
import weblogic.servlet.internal.MembershipControllerImpl;
import weblogic.servlet.internal.ServletRequestImpl;
import weblogic.servlet.internal.ServletResponseImpl;
import weblogic.servlet.internal.WebAppServletContext;
import weblogic.servlet.spi.WebServerRegistry;
import weblogic.timers.NakedTimerListener;
import weblogic.timers.Timer;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.work.WorkManagerFactory;

public class ReplicatedSessionContext extends SessionContext implements ReplicatableSessionContext {
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final ReplicationServices repserv;
   private final HttpServer httpServer = this.getServletContext().getServer();
   private LATUpdateTrigger latTrigger;
   private static HashMap httpServerToQueryMap;
   private static ClusterServices clusterServices;
   private static final ServerIdentity currentClusterMember;

   public ReplicatedSessionContext(WebAppServletContext sci, SessionConfigManager scm) {
      super(sci, scm);
   }

   protected final ServerIdentity getCurrentClusterMember() {
      return currentClusterMember;
   }

   public void startTimers() {
      super.startTimers();
      this.latTrigger = new LATUpdateTrigger();
   }

   public String getPersistentStoreType() {
      return "replicated";
   }

   ROID getSecondarySession(String sessionId) {
      return (ROID)this.httpServer.getReplicator().getSecondary(sessionId);
   }

   void removeSecondarySession(String sessionId) {
      this.httpServer.getReplicator().removeSecondary(sessionId, this.getServletContext().getContextPath());
   }

   protected void beforeSync(SessionData session) {
      ReplicatedSessionData rs = (ReplicatedSessionData)session;
      if (DEBUG_SESSIONS.isDebugEnabled()) {
         DEBUG_SESSIONS.debug("synchronized on " + rs.getROID() + " and session is inUse: " + rs.sessionInUse() + " and active request count is: " + rs.getActiveRequestCount());
      }

   }

   protected void afterSync(SessionData session) {
      ReplicatedSessionData rs = (ReplicatedSessionData)session;
      if (rs.getActiveRequestCount() < 0) {
         throw new AssertionError("Reference count value set below 0, value is" + rs.getActiveRequestCount());
      }
   }

   protected boolean needToPassivate() {
      return true;
   }

   public ROID getROID(String sessionID) {
      ROID roid = (ROID)this.getOpenSession(sessionID);
      if (roid == null) {
         roid = this.getSecondarySession(sessionID);
      }

      return roid;
   }

   public void updateSecondaryLAT(ROID roid, long lat) {
      ReplicatedSessionData session = this.getSecondarySession(roid);
      if (session != null && session.getLAT() < lat) {
         if (DEBUG_SESSIONS.isDebugEnabled()) {
            DEBUG_SESSIONS.debug("updating LAT for " + roid + " lat=" + lat + " prev=" + session.getLAT());
         }

         session.setLastAccessedTime(lat);
      }

   }

   void killSecondary(ReplicatedSessionData rs) {
      rs.unregisterSecondary();
      this.removeSecondarySession(rs.id);
   }

   boolean hasSecondarySessionExpired(ReplicatedSessionData session, long currentTime) {
      long expiration = Long.MAX_VALUE;
      int timeout = session.getMaxInactiveInterval() * 2;
      expiration = currentTime - (long)timeout * 1000L;
      return timeout >= 0 && session.getLAT() < expiration;
   }

   public void destroy(boolean serverShutdown) {
      if (DEBUG_SESSIONS.isDebugEnabled()) {
         DEBUG_SESSIONS.debug("ReplicatedSessionContext@" + this.hashCode() + " destroy(serverShutdown=" + serverShutdown + "), servletContext=" + this.getServletContext());
      }

      if (this.latTrigger != null) {
         this.latTrigger.stop();
      }

      super.destroy(serverShutdown);
      if (httpServerToQueryMap.containsKey(this.httpServer)) {
         httpServerToQueryMap.remove(this.httpServer);
      }

      if (!serverShutdown) {
         if (!this.configMgr.isSaveSessionsOnRedeployEnabled()) {
            boolean partialInvalidate = false;
            ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
            String partitionName = null;
            if (cic != null) {
               partitionName = cic.getPartitionName();
            }

            if (partitionName != null) {
               PartitionTableEntry partitionTable = PartitionTable.getInstance().lookupByName(partitionName);
               if (this.httpServer.isPartitionShutdownOnCurrentServer(partitionTable)) {
                  if (DEBUG_SESSIONS.isDebugEnabled()) {
                     DEBUG_SESSIONS.debug("ReplicatedSessionContext@" + this.hashCode() + " destroy(): need to check replicationOnShutdown for " + partitionName);
                  }

                  if (!partitionName.equals("DOMAIN") && clusterServices.isSessionReplicationOnShutdownEnabled(partitionName)) {
                     List list = this.getROIDs();
                     if (DEBUG_SESSIONS.isDebugEnabled()) {
                        DEBUG_SESSIONS.debug("ReplicatedSessionContext@" + this.hashCode() + " destroy(): do ensureFullStateReplicated for " + list.size() + " roids for partition " + partitionName);
                     }

                     this.getReplicationServices().ensureFullStateReplicated(list);
                     partialInvalidate = true;
                  }
               }
            }

            String[] ids = this.getIdsInternal();
            if (ids != null && ids.length > 0) {
               SessionCleanupAction action = new SessionCleanupAction(ids, partialInvalidate);
               Throwable excp = (Throwable)SecurityServiceManager.runAs(kernelId, SubjectUtils.getAnonymousSubject(), action);
               if (excp != null) {
                  HTTPSessionLogger.logUnexpectedErrorCleaningUpSessions(this.getServletContext().getLogContext(), excp);
               }
            }

            this.cleanupLocalSecondarySessions();
         }
      }
   }

   private List getROIDs() {
      List list = new ArrayList();
      String[] var3 = this.getIdsInternal();
      int i = var3.length;

      ROID roid;
      for(int var5 = 0; var5 < i; ++var5) {
         String id = var3[var5];
         roid = (ROID)this.getOpenSession(id);
         if (roid == null) {
            if (DEBUG_SESSIONS_CONCISE.isDebugEnabled()) {
               DEBUG_SESSIONS_CONCISE.debug("ReplicatedSessionContext@" + this.hashCode() + " getROIDs(): getOpenSession() returned roid null for id=" + id);
            }
         } else {
            if (DEBUG_SESSIONS_CONCISE.isDebugEnabled()) {
               DEBUG_SESSIONS_CONCISE.debug("ReplicatedSessionContext@" + this.hashCode() + " getROIDs(): add " + roid + ", id=" + id);
            }

            list.add(roid);
         }
      }

      if (DEBUG_SESSIONS.isDebugEnabled()) {
         DEBUG_SESSIONS.debug("ReplicatedSessionContext@" + this.hashCode() + " getROIDs(): " + list.size() + " open sessions " + list);
      }

      ROID[] secondaries = (ROID[])((ROID[])this.httpServer.getReplicator().getSecondaryIds());
      if (DEBUG_SESSIONS.isDebugEnabled()) {
         DEBUG_SESSIONS.debug("ReplicatedSessionContext@" + this.hashCode() + " getROIDs(): " + (secondaries == null ? "no secondaries" : secondaries.length + " secondaries") + " from httpServer@" + this.httpServer.hashCode());
      }

      if (secondaries != null) {
         for(i = 0; i < secondaries.length; ++i) {
            roid = secondaries[i];
            if (roid != null) {
               ReplicatedSessionData data = this.getSecondarySession(roid);
               if (data != null) {
                  if (DEBUG_SESSIONS_CONCISE.isDebugEnabled()) {
                     DEBUG_SESSIONS_CONCISE.debug("ReplicatedSessionContext@" + this.hashCode() + " getROIDs(): add secondary " + roid);
                  }

                  list.add(roid);
               }
            }
         }
      }

      if (DEBUG_SESSIONS.isDebugEnabled()) {
         DEBUG_SESSIONS.debug("ReplicatedSessionContext@" + this.hashCode() + " getROIDs(): retrurn " + list.size() + " roids ");
      }

      return list;
   }

   private void cleanupLocalSecondarySessions() {
      ROID[] secondaryROIDs = (ROID[])((ROID[])this.httpServer.getReplicator().getSecondaryIds());
      if (secondaryROIDs != null && secondaryROIDs.length > 0) {
         Throwable th = null;

         for(int i = 0; i < secondaryROIDs.length; ++i) {
            try {
               ReplicatedSessionData session = null;
               if (secondaryROIDs[i] == null) {
                  break;
               }

               session = this.getSecondarySession(secondaryROIDs[i]);
               if (session != null) {
                  this.killSecondary(session);
               }
            } catch (Throwable var5) {
               th = var5;
            }
         }

         if (th != null) {
            HTTPSessionLogger.logUnexpectedErrorCleaningUpSessions(this.getServletContext().getLogContext(), th);
         }
      }

   }

   void unregisterExpiredSessions(ArrayList expired) {
      if (!expired.isEmpty()) {
         ROID[] roids = new ROID[expired.size()];

         for(int i = 0; i < roids.length; ++i) {
            roids[i] = ((ReplicatedSessionData)expired.get(i)).getROID();
         }

         this.getReplicationServices().unregister(roids, this.getServletContext().getContextPath());
      }
   }

   public HttpSession getNewSession(String id, ServletRequestImpl req, ServletResponseImpl res) {
      this.checkSessionCount();
      ReplicatedSessionData rs = this.createReplicatedSessionData(id, true);
      return rs;
   }

   public ReplicatedSessionData createReplicatedSessionData(String sessionID, boolean isNew) {
      ReplicatedSessionData repData = this.instantiateReplicatedSessionData(sessionID, isNew);
      repData.initialize();
      return repData;
   }

   protected ReplicatedSessionData instantiateReplicatedSessionData(String sessionID, boolean isNew) {
      return new ReplicatedSessionData(sessionID, this, isNew);
   }

   private ReplicatedSessionData lookupRO(String incomingid, String rsidID, ServerIdentity remoteServer, ROID roid) {
      ROID id = null;

      try {
         if (remoteServer == null) {
            return null;
         } else {
            ReplicatedSessionData data = (ReplicatedSessionData)this.getReplicationServices().registerLocally(remoteServer, roid, this.getServletContext().getContextPath());
            id = (ROID)this.getOpenSession(rsidID);
            if (data != null) {
               if (this.isDebugEnabled()) {
                  Loggable l = HTTPSessionLogger.logRetrievedROIDFromSecondaryLoggable(id.toString(), remoteServer.toString(), "", rsidID);
                  DEBUG_SESSIONS.debug(l.getMessage());
               }

               if (DEBUG_SESSIONS.isDebugEnabled()) {
                  DEBUG_SESSIONS.debug("looked up the roid = " + id + " from host " + remoteServer.toString());
               }
            }

            return data;
         }
      } catch (RemoteException var8) {
         String[] serverNames = this.getPrimaryAndSecondaryServerNames(incomingid);
         HTTPSessionLogger.logErrorGettingReplicatedSession(incomingid, serverNames[0], serverNames[1], var8);
         return null;
      }
   }

   private SessionData getPrimarySessionForTrigger(String sessionid) throws NotFoundException {
      if (sessionid == null) {
         return null;
      } else {
         ROID roid = (ROID)this.getOpenSession(sessionid);
         return roid == null ? null : (ReplicatedSessionData)this.getReplicationServices().invalidationLookup(roid, this.getServletContext().getContextPath());
      }
   }

   public SessionData getSecondarySessionInternal(String sessionid) {
      if (sessionid == null) {
         return null;
      } else {
         ROID roid = this.getSecondarySession(SessionData.getID(sessionid));
         return roid == null ? null : this.getSecondarySession(roid);
      }
   }

   public boolean isPrimaryOrSecondary(String incomingId) {
      MembershipControllerImpl msi = (MembershipControllerImpl)WebServerRegistry.getInstance().getClusterProvider();
      RSID rsid = new RSID(incomingId, msi.getClusterMembers());
      if (rsid.id == null) {
         return true;
      } else {
         ServerIdentity currentServer = this.getCurrentClusterMember();
         this.DEBUGSAY("current server: " + currentServer);
         this.DEBUGSAY("rsid.getSecondary(): " + rsid.getSecondary());
         this.DEBUGSAY("rsid.getPrimary(): " + rsid.getPrimary());
         if (rsid.getPrimary() == null && rsid.getSecondary() == null) {
            return true;
         } else {
            return currentServer.equals(rsid.getPrimary()) || currentServer.equals(rsid.getSecondary());
         }
      }
   }

   public SessionData getSessionInternalForAuthentication(String incomingid, ServletRequestImpl req, ServletResponseImpl res) {
      return this.getSessionInternal(incomingid, req, res, true);
   }

   public SessionData getSessionInternal(String incomingid, ServletRequestImpl req, ServletResponseImpl res) {
      return this.getSessionInternal(incomingid, req, res, false);
   }

   private SessionData getSessionInternal(String incomingid, ServletRequestImpl req, ServletResponseImpl res, boolean forAuth) {
      try {
         if (req == null) {
            return this.getPrimarySessionForTrigger(SessionData.getID(incomingid));
         }

         return this.lookupSession(incomingid, req, res, forAuth);
      } catch (NotFoundException var7) {
         if (DEBUG_SESSIONS_CONCISE.isDebugEnabled()) {
            HTTPSessionLogger.logSessionNotFound(this.getServletContext().getLogContext(), incomingid, var7);
         }
      } catch (RuntimeException var8) {
         String[] serverNames = this.getPrimaryAndSecondaryServerNames(incomingid);
         HTTPSessionLogger.logErrorGettingReplicatedSession(incomingid, serverNames[0], serverNames[1], var8);
      }

      return null;
   }

   public boolean hasSession(String incomingid) {
      if (this.getSessionInternal(incomingid, (ServletRequestImpl)null, (ServletResponseImpl)null) != null) {
         return true;
      } else {
         return this.lookupSession(incomingid, (ServletRequestImpl)null, (ServletResponseImpl)null, false) != null;
      }
   }

   public void detectSessionCompatiblity(HttpSession session, HttpServletRequest req) {
      String incomingId = req.getRequestedSessionId();
      MembershipControllerImpl msi = (MembershipControllerImpl)WebServerRegistry.getInstance().getClusterProvider();
      Enumeration enu = session.getAttributeNames();

      while(enu.hasMoreElements()) {
         session.getAttribute((String)enu.nextElement());
      }

   }

   private SessionData lookupSession(String incomingid, ServletRequestImpl req, ServletResponseImpl res, boolean forAuth) {
      try {
         MembershipControllerImpl msi = (MembershipControllerImpl)WebServerRegistry.getInstance().getClusterProvider();
         RSID rsid = new RSID(incomingid, msi.getClusterMembers());
         if (rsid.id == null) {
            return null;
         }

         ServerIdentity currentServer = this.getCurrentClusterMember();
         String primaryURL = null;
         String secondaryURL = null;
         this.DEBUGSAY("currentServer: " + currentServer + ",\nprimary: " + rsid.getPrimary() + ",\nsecondary: " + rsid.getSecondary());
         if (rsid.getPrimary() != null && !currentServer.equals(rsid.getPrimary())) {
            primaryURL = URLManager.findURL(rsid.getPrimary(), this.httpServer.getReplicationChannel());
         }

         if (rsid.getSecondary() != null && !currentServer.equals(rsid.getSecondary())) {
            secondaryURL = URLManager.findURL(rsid.getSecondary(), this.httpServer.getReplicationChannel());
         }

         this.DEBUGSAY("primaryURL: " + primaryURL + ", secondaryURL: " + secondaryURL);
         boolean updateLAT = false;
         String origin = null;
         ROID roid = (ROID)this.getOpenSession(rsid.id);
         boolean notPrimaryOrSecondary = !currentServer.equals(rsid.getSecondary()) && !currentServer.equals(rsid.getPrimary());
         if (notPrimaryOrSecondary && roid != null && this.getPersistentStoreType() != "async-replication-across-cluster" && !forAuth) {
            HTTPSessionLogger.logSessionAccessFromNonPrimaryNonSecondary(rsid.id, currentServer.toString(), rsid.getPrimary() != null ? rsid.getPrimary().toString() : "NONE", rsid.getSecondary() != null ? rsid.getSecondary().toString() : "NONE", req != null ? req.getRequestURL().toString() : "NONE");
         }

         ROID localSecondaryROID = null;
         if (roid == null) {
            roid = this.getSecondarySession(rsid.id);
            localSecondaryROID = roid;
            if (roid != null && rsid.getPrimary() == null && rsid.getSecondary() == null) {
               notPrimaryOrSecondary = false;
               updateLAT = true;
            }
         }

         if (roid == null || notPrimaryOrSecondary) {
            ROID tempROID = null;
            ReplicatedSessionData data = null;
            RemoteException roidLookupFailure = null;
            if (primaryURL != null) {
               this.DEBUGSAY("### Primary Server =  " + primaryURL);

               try {
                  tempROID = this.fetchROID(rsid, primaryURL, notPrimaryOrSecondary);
               } catch (RemoteException var33) {
                  this.DEBUGSAY("Exception looking up roid from primary" + var33.getMessage());
                  if (var33 instanceof RemoteSystemException) {
                     this.DEBUGSAY("Requested timed out, trying to fetch session from primary server");
                  }

                  roidLookupFailure = var33;
               }

               if (tempROID != null) {
                  roid = tempROID;
                  origin = "primary-server: " + primaryURL;
                  data = this.lookupRO(incomingid, rsid.id, rsid.getPrimary(), tempROID);
                  if (data != null) {
                     updateLAT = true;
                  } else {
                     tempROID = null;
                  }
               }
            }

            if (tempROID == null && secondaryURL != null) {
               this.DEBUGSAY("### Secondary Server =  " + secondaryURL);

               try {
                  tempROID = this.fetchROID(rsid, secondaryURL, notPrimaryOrSecondary);
               } catch (RemoteException var32) {
                  if (var32 instanceof RequestTimeoutException) {
                     this.DEBUGSAY("Request timed out, trying to fetch session from secondary ");
                  }

                  roidLookupFailure = var32;
               }

               if (tempROID != null) {
                  roid = tempROID;
                  origin = "secondary-server: " + secondaryURL;
                  data = this.lookupRO(incomingid, rsid.id, rsid.getSecondary(), tempROID);
                  if (data != null) {
                     updateLAT = true;
                  } else {
                     tempROID = null;
                  }
               }
            }

            ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
            String partitionName = null;
            if (cic != null) {
               partitionName = cic.getPartitionName();
            }

            boolean isSessionStateQueryProtocolEnabled = partitionName != null ? clusterServices.isSessionStateQueryProtocolEnabled(partitionName) : clusterServices.isSessionStateQueryProtocolEnabled();

            for(int maxLoopAttempts = msi.getClusterMembers().size() - 1; isSessionStateQueryProtocolEnabled && tempROID == null && maxLoopAttempts > 0; --maxLoopAttempts) {
               Map idToQueryObjectMap = getIdToQueryObjectMap(this.httpServer);
               SessionStateQueryObject queryHttpSessionROID;
               synchronized(idToQueryObjectMap) {
                  queryHttpSessionROID = (SessionStateQueryObject)idToQueryObjectMap.get(rsid.id);
                  if (queryHttpSessionROID == null) {
                     queryHttpSessionROID = new SessionStateQueryObject(incomingid, rsid, this.getConfigMgr().getSessionTimeoutSecs(), this, this.httpServer);
                     idToQueryObjectMap.put(rsid.id, queryHttpSessionROID);
                  }
               }

               synchronized(queryHttpSessionROID) {
                  roid = (ROID)this.getOpenSession(rsid.id);
                  if (roid == null) {
                     if (!queryHttpSessionROID.haveQueried()) {
                        queryHttpSessionROID.execute();
                        roid = (ROID)this.getOpenSession(rsid.id);
                     } else if (queryHttpSessionROID.response != null) {
                        try {
                           roid = this.fetchROID(rsid, queryHttpSessionROID.ownerURL, true);
                        } catch (RemoteException var29) {
                           this.DEBUGSAY("Exception looking up roid from owner: " + queryHttpSessionROID.ownerURL);
                           if (var29 instanceof RequestTimeoutException) {
                              this.DEBUGSAY("Request timed out, trying to fetch session from " + queryHttpSessionROID.ownerURL);
                           }

                           roidLookupFailure = var29;
                        }

                        if (roid != null) {
                           data = this.lookupRO(incomingid, rsid.id, (ServerIdentity)queryHttpSessionROID.owner, roid);
                           if (data != null) {
                              updateLAT = true;
                           }
                        }
                     }
                  }
               }

               if (roid != null) {
                  synchronized(idToQueryObjectMap) {
                     if (idToQueryObjectMap.containsKey(rsid.id)) {
                        idToQueryObjectMap.remove(rsid.id);
                     }
                  }
               }

               if (roid != null || queryHttpSessionROID.response == null) {
                  if (localSecondaryROID != null && roid == null) {
                     roid = localSecondaryROID;
                  }
                  break;
               }
            }

            if (roid != null) {
               roidLookupFailure = null;
            }

            if (roidLookupFailure != null) {
               this.DEBUGSAY("Error looking up ROID from primary and/or secondary");
               HTTPSessionLogger.logErrorGettingReplicatedSession(incomingid, rsid.getPrimaryServerName(), rsid.getSecondaryServerName(), roidLookupFailure);
            }
         }

         if (roid == null) {
            return null;
         }

         if (origin == null) {
            origin = "current-server";
         }

         ReplicatedSessionData session = (ReplicatedSessionData)this.getReplicationServices().lookup(roid, this.getServletContext().getContextPath());
         if (session == null) {
            return null;
         }

         if (!session.id.equals(rsid.id)) {
            this.DEBUGSAY("Incoming sessionid: " + incomingid + "\nrsid: " + rsid + "\nrsid.id: " + rsid.id + "\nroid: " + roid + "\nprimary: " + rsid.getPrimary() + "\nsecondary: " + rsid.getSecondary() + "\ncurrentServer: " + currentServer.hashCode() + "\ncurrent context path: " + this.getServletContext().getContextPath() + "\norigin: " + origin + "\nsession.id: " + session.id + "\nsession.oldId " + session.oldId + "\nsession.ROID: " + session.getROID() + "\nsession.ContextPath: " + session.getContextPath() + "\nsession.isValid: " + session.isValid());
            if (!rsid.id.equals(session.oldId)) {
               throw new AssertionError("Lookup returned replicated session object with a different sessionid");
            }

            this.DEBUGSAY("found rsid.id equals with session's old id but different with session's id");
         }

         if (!session.isValid()) {
            return null;
         }

         session.setSessionContext(this);
         session.updateVersionIfNeeded(this);
         if (updateLAT) {
            this.DEBUGSAY("Secondary took over as the primary now for session: " + incomingid);
            session.setLastAccessedTime(System.currentTimeMillis());
            session.notifyActivated(new HttpSessionEvent(session));
         }

         if (!session.isValidForceCheck()) {
            return null;
         }

         if (req != null) {
            this.DEBUGSAY("incrementing active request count for " + session.getROID());
            session.incrementActiveRequestCount();
            this.DEBUGSAY("active request count is " + session.getActiveRequestCount() + " for " + session.getROID());
         }

         if (!session.isValidForceCheck()) {
            return null;
         }

         session.reinitializeSecondary();
         return session;
      } catch (NotFoundException var34) {
         if (DEBUG_SESSIONS_CONCISE.isDebugEnabled()) {
            HTTPSessionLogger.logSessionNotFound(this.getServletContext().getLogContext(), incomingid, var34);
         }
      } catch (RuntimeException var35) {
         String[] serverNames = this.getPrimaryAndSecondaryServerNames(incomingid);
         HTTPSessionLogger.logErrorGettingReplicatedSession(incomingid, serverNames[0], serverNames[1], var35);
      }

      return null;
   }

   protected ROID fetchROID(RSID rsid, String ownerURL, boolean notPrimaryOrSecondary) throws RemoteException {
      return (ROID)this.httpServer.getReplicator().lookupROID(rsid.id, ownerURL, this.getConfigMgr().getCookieName(), this.getConfigMgr().getCookiePath(), notPrimaryOrSecondary);
   }

   public Object getOpenSession(String sessionId) {
      return this.httpServer.getReplicator().getPrimary(sessionId);
   }

   public void removeSession(String sessionId, ROID roid) {
      super.removeSession(sessionId);
      this.httpServer.getReplicator().removePrimary(sessionId, this.getServletContext().getContextPath());
   }

   public int getCurrOpenSessionsCount() {
      return this.getOpenSessions().size();
   }

   boolean invalidateSession(SessionData data, boolean trigger) {
      String sessionID = data.id;
      ReplicatedSessionData rsd = (ReplicatedSessionData)data;
      rsd.remove(trigger);
      this.removeSession(sessionID, rsd.getROID());
      this.decrementOpenSessionsCount();
      return true;
   }

   boolean partialInvalidateSession(SessionData data) {
      String sessionID = data.id;
      ReplicatedSessionData rsd = (ReplicatedSessionData)data;
      this.getTimer().unregisterLAT(rsd.getROID());
      this.getReplicationServices().unregister(rsd.getROID(), rsd.contextName);
      HttpServer.SessionLogin sessionLogin = this.servletContext.getServer().getSessionLogin();
      if (this.configMgr.isSessionSharingEnabled()) {
         sessionLogin.unregister(sessionID);
      } else {
         sessionLogin.unregister(sessionID, this.servletContext.getContextPath());
      }

      data.unregisterRuntimeMBean();
      this.removeSession(sessionID, rsd.getROID());
      return true;
   }

   private ReplicatedSessionData getSecondarySession(ROID id) {
      if (id == null) {
         return null;
      } else {
         try {
            return (ReplicatedSessionData)this.getReplicationServices().invalidationLookup(id, this.getServletContext().getContextPath());
         } catch (NotFoundException var3) {
            return null;
         }
      }
   }

   protected void invalidateOrphanedSessions() {
      long lastInvalidation = System.currentTimeMillis();
      ROID[] secondaryROIDs = (ROID[])((ROID[])this.httpServer.getReplicator().getSecondaryIds());
      int total = secondaryROIDs.length;
      int invalidated = 0;

      for(int i = 0; i < total; ++i) {
         ROID roid = secondaryROIDs[i];

         try {
            ReplicatedSessionData rs = this.getSecondarySession(roid);
            if (rs != null && !rs.sessionInUse() && this.hasSecondarySessionExpired(rs, lastInvalidation)) {
               this.killSecondary(rs);
               if (this.isDebugEnabled()) {
                  Loggable l = HTTPSessionLogger.logTimerInvalidatedSessionLoggable("secondary:" + roid, this.getServletContext().getContextPath());
                  DEBUG_SESSIONS.debug(l.getMessage());
               }

               this.DEBUGSAY("Invalidated secondary = " + roid + " path=" + this.getServletContext().getContextPath());
               ++invalidated;
            }
         } catch (ThreadDeath var10) {
            throw var10;
         } catch (Throwable var11) {
            HTTPSessionLogger.logUnexpectedTimeoutError(var11);
         }
      }

      if (DEBUG_SESSIONS.isDebugEnabled() && total > 0) {
         DEBUG_SESSIONS.debug("Total secondary sessions invalidated = " + invalidated + " out of total secondary sessions = " + total + " in " + this.getServletContext().getContextPath());
      }

      cleanupSessionStateQueryObjects();
   }

   public int getNonPersistedSessionCount() {
      Hashtable sessions = this.getOpenSessions();
      if (sessions.size() < 1) {
         return 0;
      } else {
         int count = 0;
         Enumeration e = sessions.elements();

         while(e.hasMoreElements()) {
            ReplicatedSessionData session = (ReplicatedSessionData)e.nextElement();
            ROID curROID = session.getROID();

            try {
               if (!this.getReplicationServices().hasSecondaryServer(curROID)) {
                  ++count;
               }
            } catch (NotFoundException var7) {
               if (this.isDebugEnabled()) {
                  DEBUG_SESSIONS.debug("Caught NotFoundException for " + curROID + var7.getMessage() + ".  So we will not include it in nonPersistedSessionCount");
               }
            }
         }

         return count;
      }
   }

   protected ReplicationServices getReplicationServices() {
      return repserv;
   }

   public String lookupAppVersionIdForSession(String sid, ServletRequestImpl req, ServletResponseImpl res) {
      HttpSession sess = req.getSession(false);
      return sess != null && sess instanceof ReplicatedSessionData ? ((ReplicatedSessionData)sess).getVersionId() : null;
   }

   private static Map getIdToQueryObjectMap(HttpServer httpServer) {
      Map result = null;
      synchronized(httpServerToQueryMap) {
         result = (Map)httpServerToQueryMap.get(httpServer);
         if (result == null) {
            result = new HashMap();
            httpServerToQueryMap.put(httpServer, result);
         }

         return (Map)result;
      }
   }

   private static void cleanupSessionStateQueryObjects() {
      Collection values = httpServerToQueryMap.values();
      Iterator var1 = values.iterator();

      while(var1.hasNext()) {
         Map m = (Map)var1.next();
         Set s = m.entrySet();
         Iterator itr = s.iterator();

         while(itr.hasNext()) {
            Map.Entry entry = (Map.Entry)itr.next();
            if (((SessionStateQueryObject)entry.getValue()).hasExpired()) {
               itr.remove();
            }
         }
      }

   }

   LATUpdateTrigger getTimer() {
      return this.latTrigger;
   }

   private void DEBUGSAY(String msg) {
      if (DEBUG_SESSIONS_CONCISE.isDebugEnabled()) {
         DEBUG_SESSIONS_CONCISE.debug("ReplicatedSessionContext@" + this.hashCode() + " " + msg);
      }

   }

   private String[] getPrimaryAndSecondaryServerNames(String sessionid) {
      String[] serverNames = new String[]{null, null};
      MembershipControllerImpl msi = (MembershipControllerImpl)WebServerRegistry.getInstance().getClusterProvider();
      RSID rsid = new RSID(sessionid, msi.getClusterMembers());
      if (rsid.id != null) {
         serverNames[0] = rsid.getPrimaryServerName();
         serverNames[1] = rsid.getSecondaryServerName();
      }

      return serverNames;
   }

   static {
      repserv = Locator.locate().getReplicationService(ServiceType.SYNC);
      httpServerToQueryMap = new HashMap();
      clusterServices = weblogic.cluster.ClusterServices.Locator.locate();
      currentClusterMember = LocalServerIdentity.getIdentity();
   }

   protected static class SessionStateQueryObject {
      private String incomingid;
      private RSID rsid;
      private ReplicatedSessionContext replicatedSessionContext;
      private HttpServer httpServer;
      private boolean haveQueried = false;
      private long expirationTime;
      private HttpQuerySessionResponseMessage response = null;
      private String ownerURL = null;
      private HostID primary = null;
      private HostID secondary = null;
      private HostID owner = null;

      public SessionStateQueryObject(String incomingid, RSID rsid, int maxInactiveTimeInterval, ReplicatedSessionContext replicatedSessionContext, HttpServer httpServer) {
         this.incomingid = incomingid;
         this.rsid = rsid;
         this.replicatedSessionContext = replicatedSessionContext;
         this.httpServer = httpServer;
         this.expirationTime = System.currentTimeMillis() + (long)maxInactiveTimeInterval * 1000L;
      }

      ROID execute() {
         ROID roid = null;

         try {
            HttpQuerySessionRequestMessage request = new HttpQuerySessionRequestMessage(this.rsid.id, this.httpServer.getName());
            this.response = (HttpQuerySessionResponseMessage)ReplicatedSessionContext.repserv.sendQuerySessionRequest(request, ReplicatedSessionContext.clusterServices.getSessionStateQueryRequestTimeout());
            if (this.response == null) {
               return roid;
            } else {
               this.primary = this.response.getPrimary();
               this.secondary = this.response.getSecondary();
               ReplicatedSessionData data;
               ROID var4;
               if (this.primary != null) {
                  try {
                     this.ownerURL = URLManager.findURL((ServerIdentity)this.primary, this.httpServer.getReplicationChannel());
                     roid = this.replicatedSessionContext.fetchROID(this.rsid, this.ownerURL, true);
                     data = this.replicatedSessionContext.lookupRO(this.incomingid, this.rsid.id, (ServerIdentity)this.primary, roid);
                     if (data != null) {
                        if (SessionContext.DEBUG_SESSIONS.isDebugEnabled()) {
                           SessionContext.DEBUG_SESSIONS.debug("looked up the roid = " + roid + " from primary: " + this.primary);
                        }

                        this.owner = this.primary;
                        var4 = roid;
                        return var4;
                     }

                     if (SessionContext.DEBUG_SESSIONS.isDebugEnabled()) {
                        SessionContext.DEBUG_SESSIONS.debug("Unable to find session with roid = " + roid + " from primary: " + this.primary + " and rsid: " + this.rsid + " and url " + this.ownerURL);
                     }
                  } catch (RemoteException var10) {
                     this.replicatedSessionContext.DEBUGSAY("Exception looking up roid from primary: " + this.primary);
                     if (var10 instanceof RequestTimeoutException) {
                        this.replicatedSessionContext.DEBUGSAY("Request timed out, trying to fetch session from " + this.primary);
                     }
                  }
               }

               if (this.secondary == null) {
                  return roid;
               } else {
                  try {
                     this.ownerURL = URLManager.findURL((ServerIdentity)this.secondary, this.httpServer.getReplicationChannel());
                     roid = this.replicatedSessionContext.fetchROID(this.rsid, this.ownerURL, true);
                     data = this.replicatedSessionContext.lookupRO(this.incomingid, this.rsid.id, (ServerIdentity)this.secondary, roid);
                     if (data == null) {
                        if (SessionContext.DEBUG_SESSIONS.isDebugEnabled()) {
                           SessionContext.DEBUG_SESSIONS.debug("Unable to find session with roid = " + roid + " from secondary: " + this.secondary + " and rsid: " + this.rsid + " and url " + this.ownerURL);
                        }

                        return roid;
                     } else {
                        if (SessionContext.DEBUG_SESSIONS.isDebugEnabled()) {
                           SessionContext.DEBUG_SESSIONS.debug("looked up the roid = " + roid + " from secondary: " + this.secondary);
                        }

                        this.owner = this.secondary;
                        var4 = roid;
                        return var4;
                     }
                  } catch (RemoteException var9) {
                     this.replicatedSessionContext.DEBUGSAY("Exception looking up roid from secondary: " + this.secondary);
                     if (var9 instanceof RequestTimeoutException) {
                        this.replicatedSessionContext.DEBUGSAY("Request timed out, trying to fetch session from " + this.secondary);
                     }

                     return roid;
                  }
               }
            }
         } finally {
            this.haveQueried = true;
         }
      }

      boolean haveQueried() {
         return this.haveQueried;
      }

      boolean hasExpired() {
         return System.currentTimeMillis() >= this.expirationTime;
      }

      public String toString() {
         return "IncomingID: " + this.incomingid + ", RSID: " + this.rsid + ", haveQueried: " + this.haveQueried;
      }
   }

   private class SecondarySessionCleanupAction implements PrivilegedAction {
      private final ROID[] ids;

      SecondarySessionCleanupAction(ROID[] ids) {
         this.ids = ids;
      }

      public Object run() {
         try {
            for(int i = 0; i < this.ids.length; ++i) {
               ReplicatedSessionData session = null;
               if (this.ids[i] == null) {
                  break;
               }

               session = ReplicatedSessionContext.this.getSecondarySession(this.ids[i]);
               if (session != null) {
                  ReplicatedSessionContext.this.killSecondary(session);
               }
            }

            return null;
         } catch (Throwable var3) {
            return var3;
         }
      }
   }

   private class SessionCleanupAction implements PrivilegedAction {
      private final String[] ids;
      private final boolean partialInvalidate;

      SessionCleanupAction(String[] ids, boolean partialInvalidate) {
         this.ids = ids;
         this.partialInvalidate = partialInvalidate;
      }

      public Object run() {
         try {
            for(int i = 0; i < this.ids.length; ++i) {
               SessionData session = null;
               if (this.ids[i] == null) {
                  break;
               }

               try {
                  session = ReplicatedSessionContext.this.getPrimarySessionForTrigger(this.ids[i]);
               } catch (NotFoundException var4) {
               }

               if (session != null) {
                  if (this.partialInvalidate) {
                     ReplicatedSessionContext.this.partialInvalidateSession(session);
                  } else {
                     ReplicatedSessionContext.this.invalidateSession(session, false);
                  }
               }
            }

            return null;
         } catch (Throwable var5) {
            return var5;
         }
      }
   }

   class LATUpdateTrigger implements NakedTimerListener {
      private static final int DEFAULT_INTERVAL = 120;
      private static final int MAX_INTERVAL = 900;
      private static final int MIN_INTERVAL = 60;
      private final Hashtable allBatchJobs;
      private final TimerManager timerManager;
      private Timer timer;
      private boolean stopRequested;
      private int triggerInterval;
      private int latestBatchJobInterval;

      private LATUpdateTrigger() {
         this.allBatchJobs = new Hashtable();
         this.timerManager = TimerManagerFactory.getTimerManagerFactory().getTimerManager("LATUpdateTrigger-ContextPath='" + ReplicatedSessionContext.this.getServletContext().getContextPath() + (ReplicatedSessionContext.this.getServletContext().getVersionId() != null ? "'-Version#" + ReplicatedSessionContext.this.getServletContext().getVersionId() : ""), WorkManagerFactory.getInstance().getSystem());
         this.stopRequested = false;
         this.triggerInterval = 120;
         this.latestBatchJobInterval = 120;
         this.latestBatchJobInterval = 120;
         this.computeBatchInterval(-1);
         this.start();
      }

      private void start() {
         this.stopRequested = false;
         this.triggerInterval = this.latestBatchJobInterval;
         this.timer = this.timerManager.schedule(this, 0L, (long)(this.triggerInterval * 1000));
      }

      private void stop() {
         this.stopRequested = true;
         this.timer.cancel();
         this.timerManager.stop();
      }

      private void bounce() {
         this.timer.cancel();
         this.start();
      }

      void registerLAT(ROID roid, long accessTime, int maxInactiveInterval) {
         ServerIdentity secondary = this.getSecondary(roid);
         if (secondary != null) {
            Hashtable job = (Hashtable)this.allBatchJobs.get(secondary);
            if (job == null) {
               synchronized(this.allBatchJobs) {
                  job = (Hashtable)this.allBatchJobs.get(secondary);
                  if (job == null) {
                     job = new Hashtable();
                     this.allBatchJobs.put(secondary, job);
                  }
               }
            }

            job.put(roid, new Long((long)maxInactiveInterval));
            this.computeBatchInterval(maxInactiveInterval);
         }
      }

      private ServerIdentity getSecondary(ROID roid) {
         try {
            return (ServerIdentity)ReplicatedSessionContext.this.getReplicationServices().getSecondaryInfo(roid);
         } catch (NotFoundException var3) {
            return null;
         }
      }

      void unregisterLAT(ROID roid) {
         ServerIdentity secondary = this.getSecondary(roid);
         if (secondary != null) {
            Hashtable job = (Hashtable)this.allBatchJobs.get(secondary);
            if (job != null) {
               job.remove(roid);
            }
         }
      }

      private void computeBatchInterval(int mii) {
         if (mii > 0 && mii / 3 < this.latestBatchJobInterval) {
            this.latestBatchJobInterval = mii / 3;
         }

         int invSecs = ReplicatedSessionContext.this.configMgr.getInvalidationIntervalSecs();
         if (invSecs / 2 < this.latestBatchJobInterval) {
            this.latestBatchJobInterval = invSecs / 2;
         }

         if (this.latestBatchJobInterval < 60) {
            this.latestBatchJobInterval = 60;
         } else if (this.latestBatchJobInterval > 900) {
            this.latestBatchJobInterval = 900;
         }

      }

      public void timerExpired(Timer timer) {
         if (ReplicatedSessionContext.this.getServletContext().isStarted()) {
            ComponentInvocationContextManager cicManager = ComponentInvocationContextManager.getInstance();
            ComponentInvocationContext cic = ReplicatedSessionContext.this.getServletContext().getComponentInvocationContext();
            ManagedInvocationContext mic = cicManager.setCurrentComponentInvocationContext(cic);
            Throwable var5 = null;

            try {
               final int numJobs = this.allBatchJobs.size();
               if (numJobs >= 1) {
                  SecurityServiceManager.runAs(ReplicatedSessionContext.kernelId, SubjectUtils.getAnonymousSubject(), new PrivilegedAction() {
                     public Object run() {
                        try {
                           LATUpdateTrigger.this.runTrigger(numJobs);
                        } catch (Throwable var2) {
                           HTTPSessionLogger.logLATUpdateError(ReplicatedSessionContext.this.getServletContext().getContextPath(), var2);
                        }

                        return null;
                     }
                  });
                  return;
               }

               this.latestBatchJobInterval = 120;
            } catch (Throwable var16) {
               var5 = var16;
               throw var16;
            } finally {
               if (mic != null) {
                  if (var5 != null) {
                     try {
                        mic.close();
                     } catch (Throwable var15) {
                        var5.addSuppressed(var15);
                     }
                  } else {
                     mic.close();
                  }
               }

            }

         }
      }

      private void runTrigger(int numJobs) {
         try {
            if (SessionContext.DEBUG_SESSIONS.isDebugEnabled()) {
               SessionContext.DEBUG_SESSIONS.debug("LATUpdateTrigger: total jobs: " + numJobs + " current interval=" + this.latestBatchJobInterval);
            }

            Hashtable currentAllJobs = null;
            synchronized(this.allBatchJobs) {
               currentAllJobs = (Hashtable)this.allBatchJobs.clone();
               this.allBatchJobs.clear();
            }

            numJobs = currentAllJobs.size();
            if (numJobs < 1) {
               return;
            }

            Iterator iter = currentAllJobs.values().iterator();

            while(iter.hasNext()) {
               this.sendBatchJob((Hashtable)iter.next());
            }
         } finally {
            if (!this.stopRequested && this.triggerInterval != this.latestBatchJobInterval) {
               this.bounce();
            }

         }

      }

      private void sendBatchJob(Hashtable job) {
         int size = job.size();
         if (size >= 1) {
            ROID[] roids = new ROID[size];
            long[] lats = new long[size];
            String url = null;
            Enumeration e = job.keys();

            for(int i = 0; e.hasMoreElements() && i < size; ++i) {
               ROID roid = (ROID)e.nextElement();
               if (url == null) {
                  try {
                     ServerIdentity jvmid = (ServerIdentity)ReplicatedSessionContext.this.getReplicationServices().getSecondaryInfo(roid);
                     if (jvmid != null) {
                        url = URLManager.findURL(jvmid, ReplicatedSessionContext.this.httpServer.getReplicationChannel());
                     }
                  } catch (NotFoundException var10) {
                  }
               }

               roids[i] = roid;
               lats[i] = (Long)job.get(roid);
            }

            if (SessionContext.DEBUG_SESSIONS.isDebugEnabled()) {
               SessionContext.DEBUG_SESSIONS.debug("LATUpdateTrigger: sending a batch of size = " + size + " to secondary url : " + url);
            }

            if (url != null) {
               ReplicatedSessionContext.this.httpServer.getReplicator().updateROIDLastAccessTimes(url, roids, lats, ReplicatedSessionContext.this.getServletContext().getContextPath());
            }

            job.clear();
         }
      }

      // $FF: synthetic method
      LATUpdateTrigger(Object x1) {
         this();
      }
   }
}
