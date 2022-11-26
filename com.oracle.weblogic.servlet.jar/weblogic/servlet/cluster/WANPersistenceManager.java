package weblogic.servlet.cluster;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.rmi.RemoteException;
import java.security.AccessController;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.jvnet.hk2.annotations.Service;
import weblogic.cluster.replication.WANPersistenceServiceControl;
import weblogic.jndi.Environment;
import weblogic.management.ManagementException;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JDBCSystemResourceMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.ServiceFailureException;
import weblogic.servlet.cluster.wan.BatchedSessionState;
import weblogic.servlet.cluster.wan.Invalidate;
import weblogic.servlet.cluster.wan.PersistenceService;
import weblogic.servlet.cluster.wan.PersistenceServiceImpl;
import weblogic.servlet.cluster.wan.PersistenceServiceInternal;
import weblogic.servlet.cluster.wan.ServiceUnavailableException;
import weblogic.servlet.cluster.wan.SessionDiff;
import weblogic.servlet.cluster.wan.Update;
import weblogic.servlet.internal.session.HTTPSessionLogger;
import weblogic.servlet.internal.session.WANSessionData;
import weblogic.servlet.spi.WebServerRegistry;
import weblogic.timers.NakedTimerListener;
import weblogic.timers.StopTimerListener;
import weblogic.timers.Timer;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.AssertionError;
import weblogic.utils.io.UnsyncByteArrayInputStream;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

@Service
@Singleton
@Named
public final class WANPersistenceManager implements PersistenceService, WANPersistenceServiceControl {
   private static final int DEFAULT_MAX_CONCURRENCY = 5;
   private String selectQuery;
   private static String isSessionValidQuery;
   private long timeAtLastInvalidateFlush;
   private final int UPDATE_SIZE;
   private final int INVALIDATE_SIZE;
   private final String backupClusterAddress;
   private final String dataSourceName;
   private final DataSource dataSource;
   private final int sessionFlushInterval;
   private final ArrayList pendingUpdates = new ArrayList();
   private final ArrayList pendingInvalidates = new ArrayList();
   private final HashSet updateSet;
   private final HashSet invalidateSet;
   private final WorkManager workManager;
   private final PersistenceServiceInternal localService;
   private final Timer updateTimer;
   private final Timer invalidateTimer;
   private long timeAtLastUpdateFlush = 0L;
   private int updateIndex = 0;
   private int invalidateIndex = 0;
   private LinkLivenessChecker linkChecker;
   private final TimerManager sessionUpdateFlushTimerManager;
   private final TimerManager sessionInvalidateFlushTimerManager;
   private final boolean isServiceAvailable;
   private final WANReplicationRuntime runtime;
   private final boolean updateOnlyOnShutdown;
   private String srvrState;

   public void startService() throws ServiceFailureException {
      this.start();
   }

   public void stopService() {
      this.stop();
   }

   private void initQueryStrings(String wanSessionPersistenceTableName) {
      this.selectQuery = " SELECT WL_SESSION_ATTRIBUTE_KEY, WL_SESSION_ATTRIBUTE_VALUE FROM " + wanSessionPersistenceTableName + " WHERE WL_ID = ? AND WL_CONTEXT_PATH = ? AND WL_INTERNAL_ATTRIBUTE = ?";
      isSessionValidQuery = "SELECT WL_CREATE_TIME, WL_ACCESS_TIME, WL_MAX_INACTIVE_INTERVAL, WL_VERSION FROM " + wanSessionPersistenceTableName + " WHERE WL_ID = ? AND WL_CONTEXT_PATH = ? AND WL_VERSION = (SELECT MAX(WL_VERSION) from " + wanSessionPersistenceTableName + " WHERE WL_ID = ? AND WL_CONTEXT_PATH = ?)";
   }

   private WANPersistenceManager() {
      AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      ClusterMBean cluster = ManagementService.getRuntimeAccess(kernelId).getServer().getCluster();
      this.initQueryStrings(cluster.getWANSessionPersistenceTableName());
      this.UPDATE_SIZE = cluster.getSessionFlushThreshold() == -1 ? 0 : cluster.getSessionFlushThreshold();
      this.INVALIDATE_SIZE = this.UPDATE_SIZE / 10 == 0 ? 1 : this.UPDATE_SIZE / 10;
      this.sessionFlushInterval = cluster.getSessionFlushInterval() == -1 ? 0 : cluster.getSessionFlushInterval() * 1000;
      this.updateOnlyOnShutdown = this.UPDATE_SIZE + this.sessionFlushInterval == 0 || cluster.getPersistSessionsOnShutdown() && cluster.getClusterType() != "wan";
      JDBCSystemResourceMBean tmp = cluster.getDataSourceForSessionPersistence();
      if (tmp != null) {
         String[] jndiNames = tmp.getJDBCResource().getJDBCDataSourceParams().getJNDINames();
         if (jndiNames != null && jndiNames.length > 0) {
            this.dataSourceName = jndiNames[0];
         } else {
            this.dataSourceName = null;
         }
      } else {
         this.dataSourceName = null;
      }

      this.backupClusterAddress = cluster.getRemoteClusterAddress();
      this.dataSource = this.lookupDataSource();
      this.workManager = WorkManagerFactory.getInstance().findOrCreate("WAN_ASYNC_SESSION_FLUSH_WM", -1, getMaxThreadsConstraint(tmp));
      this.localService = this.dataSource != null ? new PersistenceServiceImpl(this.dataSource) : null;
      this.sessionUpdateFlushTimerManager = this.dataSource != null ? TimerManagerFactory.getTimerManagerFactory().getTimerManager("sessionUpdateFlushTimerManager") : null;
      this.sessionInvalidateFlushTimerManager = this.dataSource != null ? TimerManagerFactory.getTimerManagerFactory().getTimerManager("sessionInvalidateFlushTimerManager") : null;
      this.isServiceAvailable = this.dataSource != null;
      this.startLivenessLinkChecker(cluster);
      this.runtime = this.getWANReplicationRuntime(ManagementService.getRuntimeAccess(kernelId).getServer().getName());
      this.updateSet = createUpdateSet(this.UPDATE_SIZE);
      this.invalidateSet = createInvalidationSet(this.INVALIDATE_SIZE);
      this.updateTimer = this.scheduleSessionUpdateTimer();
      this.invalidateTimer = this.scheduleInvalidationTimer();
      this.srvrState = this.isServiceAvailable ? "RUNNING" : "";
   }

   private static int getMaxThreadsConstraint(JDBCSystemResourceMBean tmp) {
      return tmp == null ? 5 : tmp.getJDBCResource().getJDBCConnectionPoolParams().getMaxCapacity();
   }

   private Timer scheduleSessionUpdateTimer() {
      if (!this.isServiceAvailable) {
         return null;
      } else {
         if (WANReplicationDetailsDebugLogger.isDebugEnabled()) {
            WANReplicationDetailsDebugLogger.debug("Session Flush Interval " + this.sessionFlushInterval + "ms and threshold is " + this.UPDATE_SIZE);
         }

         return this.sessionUpdateFlushTimerManager.schedule(new SessionUpdateFlushTrigger(this, this.sessionFlushInterval), (long)this.sessionFlushInterval, (long)this.sessionFlushInterval);
      }
   }

   private Timer scheduleInvalidationTimer() {
      if (!this.isServiceAvailable) {
         return null;
      } else {
         int invalidationInterval = this.sessionFlushInterval / 2;
         if (WANReplicationDetailsDebugLogger.isDebugEnabled()) {
            WANReplicationDetailsDebugLogger.debug("Session Invalidation Interval " + invalidationInterval + "ms  and threshold is " + this.INVALIDATE_SIZE);
         }

         return this.sessionInvalidateFlushTimerManager.schedule(new SessionInvalidateFlushTrigger(this), (long)invalidationInterval, (long)invalidationInterval);
      }
   }

   private static HashSet createUpdateSet(int defaultSize) {
      return defaultSize == 0 ? new HashSet(5) : new HashSet(defaultSize);
   }

   private static HashSet createInvalidationSet(int defaultSize) {
      return defaultSize == 0 ? new HashSet(5) : new HashSet(defaultSize);
   }

   private void startLivenessLinkChecker(ClusterMBean cluster) {
      if (this.backupClusterAddress != null) {
         boolean requireValidSubject = false;
         if (cluster != null) {
            requireValidSubject = ((DomainMBean)cluster.getParent()).getSecurityConfiguration().isCrossDomainSecurityEnabled();
         }

         this.linkChecker = new LinkLivenessChecker(this.backupClusterAddress, cluster, requireValidSubject);
         this.linkChecker.resume();
      }
   }

   private WANReplicationRuntime getWANReplicationRuntime(String name) {
      if (this.dataSource == null) {
         return null;
      } else {
         try {
            return new WANReplicationRuntime(name);
         } catch (ManagementException var3) {
            throw new AssertionError("Unexpected exception", var3);
         }
      }
   }

   private DataSource lookupDataSource() {
      if (this.dataSourceName == null) {
         return null;
      } else {
         Context ctx = null;

         Object var3;
         try {
            ctx = new InitialContext();
            DataSource var2 = (DataSource)ctx.lookup(this.dataSourceName);
            return var2;
         } catch (NamingException var13) {
            HTTPSessionLogger.logWANSessionConfigurationError();
            var3 = null;
         } finally {
            if (ctx != null) {
               try {
                  ctx.close();
               } catch (NamingException var12) {
               }
            }

         }

         return (DataSource)var3;
      }
   }

   private PersistenceServiceInternal getPersistenceServiceInternal() {
      return this.backupClusterAddress == null ? ((PersistenceServiceImpl)this.localService).getLocalService() : this.linkChecker.getRemotePersistenceService();
   }

   public void update(String sessionID, long creationTime, String contextName, int maxInactiveInterval, long lastAccessTime, SessionDiff diff) {
      if (!this.updateOnlyOnShutdown) {
         boolean flushSessions = false;
         Set clone = null;
         synchronized(this) {
            this.updateSet.add(new Update(sessionID, contextName, creationTime, maxInactiveInterval, lastAccessTime, diff));
            ++this.updateIndex;
            flushSessions = this.updateIndex == this.UPDATE_SIZE;
            if (flushSessions) {
               clone = (HashSet)this.updateSet.clone();
               this.updateSet.clear();
               this.updateIndex = 0;
               this.timeAtLastUpdateFlush = System.currentTimeMillis();
            }
         }

         if (flushSessions) {
            this.workManager.schedule(new FlushWork(clone, this));
         }

      }
   }

   public void flushUponShutdown(String sessionID, long creationTime, String contextName, int maxInactiveInterval, long lastAccessTime, SessionDiff diff) {
      this.updateSet.add(new Update(sessionID, contextName, creationTime, maxInactiveInterval, lastAccessTime, diff));
      PersistenceServiceInternal service = this.getPersistenceServiceInternal();
      if (service != null) {
         this.flush(service, this.updateSet);
      }
   }

   public boolean fetchState(String sessionID, String contextPath, WANSessionData data) {
      Connection con = null;

      try {
         con = this.dataSource.getConnection();
      } catch (SQLException var22) {
         if (WANReplicationDetailsDebugLogger.isDebugEnabled()) {
            WANReplicationDetailsDebugLogger.debug("Failed to obtain database connection", var22);
         }

         return false;
      }

      PreparedStatement ps = null;
      ResultSet rs = null;
      contextPath = "".equals(contextPath) ? "/" : contextPath;

      label231: {
         boolean var8;
         try {
            ps = con.prepareStatement(isSessionValidQuery);
            rs = isSessionValid(ps, sessionID, contextPath);
            if (!rs.next()) {
               if (WANReplicationDetailsDebugLogger.isDebugEnabled()) {
                  WANReplicationDetailsDebugLogger.debug("Session is invalid");
               }

               boolean var27 = false;
               return var27;
            }

            long creationTime = rs.getLong(1);
            long lastAccessTime = rs.getLong(2);
            int maxInactiveInterval = rs.getInt(3);
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastAccessTime <= (long)(maxInactiveInterval * 1000)) {
               data.setLastAccessedTime(lastAccessTime);
               data.setMaxInactiveInterval(maxInactiveInterval);
               data.setCreationTime(creationTime);
               SessionDiff diff = new SessionDiff();
               int version = rs.getInt(4);
               close(ps, rs, (Connection)null);
               if (version > diff.getVersionCount()) {
                  diff.setVersionCounter(version);
               }

               this.populateInternalAttributes(con, sessionID, contextPath, diff);
               this.populateAttributes(con, sessionID, contextPath, diff);
               data.applySessionDiff(diff);
               break label231;
            }

            if (WANReplicationDetailsDebugLogger.isDebugEnabled()) {
               WANReplicationDetailsDebugLogger.debug("Session expired");
               WANReplicationDetailsDebugLogger.debug("CURRENT TIME " + currentTime);
               WANReplicationDetailsDebugLogger.debug("Last Access " + lastAccessTime);
               WANReplicationDetailsDebugLogger.debug("MAX INACTIVE " + maxInactiveInterval);
               WANReplicationDetailsDebugLogger.debug("RESULT " + (currentTime - lastAccessTime > (long)(maxInactiveInterval * 1000)));
            }

            boolean var14 = false;
            return var14;
         } catch (SQLException var23) {
            if (WANReplicationDetailsDebugLogger.isDebugEnabled()) {
               WANReplicationDetailsDebugLogger.debug("Failed to updateIndex the database", var23);
            }

            var8 = false;
         } catch (IOException var24) {
            if (WANReplicationDetailsDebugLogger.isDebugEnabled()) {
               WANReplicationDetailsDebugLogger.debug("Failed while deserializing the attribute for user session with id: " + sessionID, var24);
            }

            var8 = false;
            return var8;
         } catch (ClassNotFoundException var25) {
            if (WANReplicationDetailsDebugLogger.isDebugEnabled()) {
               WANReplicationDetailsDebugLogger.debug("Failed while deserializing the session attributes for user session  with id: " + sessionID, var25);
            }

            var8 = false;
            return var8;
         } finally {
            close(ps, rs, con);
         }

         return var8;
      }

      this.runtime.incrementNumberOfSessionsRetrievedFromTheDatabase();
      return true;
   }

   private void populateAttributes(Connection con, String sessionID, String contextPath, SessionDiff diff) throws SQLException, IOException, ClassNotFoundException {
      PreparedStatement ps = null;
      ResultSet rs = null;

      try {
         ps = con.prepareStatement(this.selectQuery);
         ps.setString(1, sessionID);
         ps.setString(2, contextPath);
         ps.setInt(3, 0);
         rs = ps.executeQuery();
         if (rs.next()) {
            populateAttributes(rs, diff);
         }
      } finally {
         close(ps, rs, (Connection)null);
      }

   }

   private void populateInternalAttributes(Connection con, String sessionID, String contextPath, SessionDiff diff) throws SQLException, IOException, ClassNotFoundException {
      PreparedStatement ps = null;
      ResultSet rs = null;

      try {
         ps = con.prepareStatement(this.selectQuery);
         ps.setString(1, sessionID);
         ps.setString(2, contextPath);
         ps.setInt(3, 1);
         rs = ps.executeQuery();
         if (rs.next()) {
            populateInternalAttributes(rs, diff);
         }
      } finally {
         close(ps, rs, (Connection)null);
      }

   }

   private static ResultSet isSessionValid(PreparedStatement ps, String sessionID, String contextPath) throws SQLException {
      ps.setString(1, sessionID);
      ps.setString(2, contextPath);
      ps.setString(3, sessionID);
      ps.setString(4, contextPath);
      return ps.executeQuery();
   }

   private static void close(PreparedStatement ps, ResultSet rs, Connection con) {
      try {
         if (ps != null) {
            ps.close();
         }
      } catch (SQLException var6) {
      }

      try {
         if (rs != null) {
            rs.close();
         }
      } catch (SQLException var5) {
      }

      try {
         if (con != null) {
            con.close();
         }
      } catch (SQLException var4) {
      }

   }

   private static void populateAttributes(ResultSet rs, SessionDiff diff) throws SQLException, IOException, ClassNotFoundException {
      do {
         String key = rs.getString(1);
         byte[] b = rs.getBytes(2);
         Object value = deserialize(b);
         diff.setAttribute(key, value, false, false);
      } while(rs.next());

   }

   private static void populateInternalAttributes(ResultSet rs, SessionDiff diff) throws SQLException, IOException, ClassNotFoundException {
      do {
         String key = rs.getString(1);
         byte[] b = rs.getBytes(2);
         Object value = deserialize(b);
         diff.setAttribute(key, value, false, true);
      } while(rs.next());

   }

   private static Object deserialize(byte[] b) throws IOException, ClassNotFoundException {
      UnsyncByteArrayInputStream bais = new UnsyncByteArrayInputStream(b);
      ObjectInputStream in = WebServerRegistry.getInstance().getContainerSupportProvider().getObjectInputStream(bais);
      return in.readObject();
   }

   public void invalidate(String sessionID, String contextPath) {
      boolean flushInvalidates = false;
      Set clone = null;
      synchronized(this) {
         this.invalidateSet.add(new Invalidate(sessionID, contextPath));
         ++this.invalidateIndex;
         flushInvalidates = this.invalidateIndex == this.INVALIDATE_SIZE;
         if (flushInvalidates) {
            clone = (HashSet)this.invalidateSet.clone();
            this.timeAtLastInvalidateFlush = System.currentTimeMillis();
            this.invalidateIndex = 0;
            this.invalidateSet.clear();
         }
      }

      PersistenceServiceInternal service = this.getPersistenceServiceInternal();
      if (flushInvalidates && service != null) {
         this.flushInvalidateRequests(service, clone);
      }

   }

   public final boolean isServiceAvailable() {
      return this.isServiceAvailable;
   }

   public void start() {
      if (this.localService != null) {
         try {
            Environment env = new Environment();
            env.setCreateIntermediateContexts(true);
            Context ctx = env.getInitialContext();
            ctx.rebind("weblogic/servlet/wan/persistenceservice", this.localService);
         } catch (NamingException var3) {
            throw new AssertionError("Unexpected exception" + var3.toString());
         }
      }
   }

   public void stop() {
      PersistenceServiceInternal service = this.getPersistenceServiceInternal();
      if (service != null) {
         Set clone = null;
         synchronized(this) {
            clone = (HashSet)this.updateSet.clone();
         }

         this.flush(service, clone);

         try {
            Environment env = new Environment();
            Context ctx = env.getInitialContext();
            ctx.unbind("weblogic/servlet/wan/persistenceservice");
         } catch (NamingException var5) {
            throw new AssertionError("Unexpected exception" + var5.toString());
         }

         this.invalidateTimer.cancel();
         this.updateTimer.cancel();
         this.srvrState = "SHUTDOWN";
      }
   }

   public void halt() {
      this.stop();
   }

   private void flushInvalidateRequests(PersistenceServiceInternal service, Set set) {
      if (set.size() != 0) {
         try {
            service.invalidateSessions(set);
            if (this.getPendingInvalidatesSize() > 0) {
               this.firePendingInvalidates();
            }

            if (WANReplicationDetailsDebugLogger.isDebugEnabled()) {
               WANReplicationDetailsDebugLogger.debug("Invalidating " + this.invalidateSet.size() + " sessions in the database");
            }
         } catch (RemoteException var4) {
            this.addPendingInvalidates(new PendingInvalidateRequest(set, this));
         }

      }
   }

   private void flush() {
      PersistenceServiceInternal service = this.getPersistenceServiceInternal();
      if (service != null) {
         Set clone = null;
         synchronized(this) {
            clone = (HashSet)this.updateSet.clone();
         }

         this.flush(service, clone);
      }
   }

   private void flushInvalidation() {
      PersistenceServiceInternal service = this.getPersistenceServiceInternal();
      if (service != null) {
         Set clone = null;
         synchronized(this) {
            clone = (HashSet)this.invalidateSet.clone();
         }

         this.flushInvalidateRequests(service, clone);
      }
   }

   private void flush(PersistenceServiceInternal service, Set set) {
      int size = set.size();
      Update[] updates = new Update[size];
      set.toArray(updates);
      BatchedSessionState state = new BatchedSessionState(updates);

      try {
         if (service != null) {
            service.persistState(state);
            if (this.getPendingUpdatesSize() > 0) {
               this.firePendingUpdates();
               this.runtime.setRemoteClusterReachable(true);
            }

            this.incrementUpdateCount();
         } else {
            this.addPendingUpdates(new PendingUpdateRequest(state, this));
         }

         if (WANReplicationDetailsDebugLogger.isDebugEnabled() && this.backupClusterAddress != null) {
            WANReplicationDetailsDebugLogger.debug("Flushed " + size + " sessions to the remote cluster");
         }
      } catch (ServiceUnavailableException var7) {
         this.runtime.setRemoteClusterReachable(false);
         this.addPendingUpdates(new PendingUpdateRequest(state, this));
      } catch (RemoteException var8) {
         this.runtime.setRemoteClusterReachable(false);
         this.addPendingUpdates(new PendingUpdateRequest(state, this));
      }

   }

   private long getTimeAtLastUpdateFlush() {
      return this.timeAtLastUpdateFlush;
   }

   private long getTimeAtLastInvalidateFlush() {
      return this.timeAtLastInvalidateFlush;
   }

   private void addPendingUpdates(PendingUpdateRequest request) {
      synchronized(this) {
         this.pendingUpdates.add(request);
      }
   }

   private int getPendingUpdatesSize() {
      synchronized(this) {
         return this.pendingUpdates.size();
      }
   }

   private void firePendingUpdates() {
      ArrayList pending = null;
      synchronized(this) {
         pending = (ArrayList)this.pendingUpdates.clone();
         this.pendingUpdates.clear();
      }

      for(int i = 0; i < pending.size(); ++i) {
         PendingUpdateRequest request = (PendingUpdateRequest)pending.get(i);
         this.workManager.schedule(request);
      }

   }

   private int getPendingInvalidatesSize() {
      synchronized(this) {
         return this.pendingInvalidates.size();
      }
   }

   private void firePendingInvalidates() {
      ArrayList pending = null;
      synchronized(this) {
         pending = (ArrayList)this.pendingInvalidates.clone();
         this.pendingInvalidates.clear();
      }

      for(int i = 0; i < pending.size(); ++i) {
         PendingInvalidateRequest req = (PendingInvalidateRequest)pending.get(i);
         this.workManager.schedule(req);
      }

   }

   private void incrementUpdateCount() {
      this.runtime.incrementNumberOfSessionsFlushedToTheDatabase();
   }

   private LinkLivenessChecker getLinkChecker() {
      return this.linkChecker;
   }

   private void addPendingInvalidates(PendingInvalidateRequest pendingInvalidateRequest) {
      synchronized(this) {
         this.pendingInvalidates.add(pendingInvalidateRequest);
      }
   }

   private static final class SessionInvalidateFlushTrigger implements NakedTimerListener, StopTimerListener {
      private final WANPersistenceManager manager;

      private SessionInvalidateFlushTrigger(WANPersistenceManager manager) {
         this.manager = manager;
      }

      public void timerExpired(Timer timer) {
         this.manager.flushInvalidation();
      }

      public void timerStopped(Timer timer) {
         this.manager.flushInvalidation();
      }

      // $FF: synthetic method
      SessionInvalidateFlushTrigger(WANPersistenceManager x0, Object x1) {
         this(x0);
      }
   }

   private static final class FlushWork implements Runnable {
      private final WANPersistenceManager manager;
      private final Set set;

      private FlushWork(Set set, WANPersistenceManager manager) {
         this.set = set;
         this.manager = manager;
      }

      public void run() {
         PersistenceServiceInternal service = this.manager.getPersistenceServiceInternal();
         if (service == null && WANReplicationDetailsDebugLogger.isDebugEnabled()) {
            WANReplicationDetailsDebugLogger.debug("Couldn't reach remote cluster  persistence service");
         }

         this.manager.flush(service, this.set);
      }

      // $FF: synthetic method
      FlushWork(Set x0, WANPersistenceManager x1, Object x2) {
         this(x0, x1);
      }
   }

   private static final class SessionUpdateFlushTrigger implements NakedTimerListener {
      private final WANPersistenceManager manager;
      private final int flushPeriod;

      private SessionUpdateFlushTrigger(WANPersistenceManager manager, int flushPeriod) {
         this.manager = manager;
         this.flushPeriod = flushPeriod;
      }

      public void timerExpired(Timer timer) {
         if (System.currentTimeMillis() - this.manager.getTimeAtLastUpdateFlush() > (long)this.flushPeriod) {
            this.manager.flush();
         }

      }

      // $FF: synthetic method
      SessionUpdateFlushTrigger(WANPersistenceManager x0, int x1, Object x2) {
         this(x0, x1);
      }
   }

   private static final class PendingInvalidateRequest implements Runnable {
      private final WANPersistenceManager manager;
      private final Set set;

      private PendingInvalidateRequest(Set set, WANPersistenceManager manager) {
         this.set = set;
         this.manager = manager;
      }

      public void run() {
         try {
            PersistenceServiceInternal service = this.manager.getPersistenceServiceInternal();
            if (service != null) {
               service.invalidateSessions(this.set);
               if (this.manager.getLinkChecker() != null) {
                  this.manager.getLinkChecker().stop();
               }
            } else {
               this.manager.addPendingInvalidates(this);
            }
         } catch (RemoteException var2) {
            this.manager.addPendingInvalidates(this);
            if (this.manager.getLinkChecker() != null) {
               this.manager.getLinkChecker().resume();
            }
         }

      }

      // $FF: synthetic method
      PendingInvalidateRequest(Set x0, WANPersistenceManager x1, Object x2) {
         this(x0, x1);
      }
   }

   private static final class PendingUpdateRequest implements Runnable {
      private final WANPersistenceManager manager;
      private final BatchedSessionState state;

      private PendingUpdateRequest(BatchedSessionState state, WANPersistenceManager manager) {
         this.state = state;
         this.manager = manager;
      }

      public void run() {
         try {
            PersistenceServiceInternal service = this.manager.getPersistenceServiceInternal();
            if (service != null) {
               service.persistState(this.state);
               this.manager.incrementUpdateCount();
               if (this.manager.getLinkChecker() != null) {
                  this.manager.getLinkChecker().stop();
               }
            } else {
               this.manager.addPendingUpdates(this);
            }
         } catch (ServiceUnavailableException var2) {
            this.manager.addPendingUpdates(this);
            if (this.manager.getLinkChecker() != null) {
               this.manager.getLinkChecker().resume();
            }
         } catch (RemoteException var3) {
            this.manager.addPendingUpdates(this);
            if (this.manager.getLinkChecker() != null) {
               this.manager.getLinkChecker().resume();
            }
         }

      }

      // $FF: synthetic method
      PendingUpdateRequest(BatchedSessionState x0, WANPersistenceManager x1, Object x2) {
         this(x0, x1);
      }
   }
}
