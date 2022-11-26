package weblogic.servlet.cluster.wan;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.security.AccessController;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import javax.sql.DataSource;
import weblogic.rmi.extensions.server.RemoteDomainSecurityHelper;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.rmi.spi.HostID;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.subject.SubjectManager;
import weblogic.servlet.cluster.WANReplicationDetailsDebugLogger;
import weblogic.servlet.spi.WebServerRegistry;
import weblogic.utils.io.UnsyncByteArrayInputStream;

public class PersistenceServiceImpl implements PersistenceServiceInternal {
   private final String insertQuery;
   private final String updateQuery;
   private final String deleteQuery;
   private final String updateIdQuery;
   private final PersistenceServiceInternal localService;
   private static final int QUERY_TIMEOUT = 30;
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private final DataSource dataSource;

   public PersistenceServiceImpl(DataSource dataSource) {
      this.dataSource = dataSource;
      String tableName = WebServerRegistry.getInstance().getClusterMBean().getWANSessionPersistenceTableName();
      this.updateIdQuery = " UPDATE " + tableName + " set wl_id = ? where wl_id = ? AND wl_context_path = ? ";
      this.insertQuery = " INSERT INTO " + tableName + " (wl_id, wl_create_time, wl_context_path, wl_max_inactive_interval, wl_access_time, wl_version,  wl_session_attribute_key, wl_session_attribute_value, wl_internal_attribute)  values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
      this.updateQuery = " UPDATE " + tableName + " set wl_access_time  = ?, wl_session_attribute_value = ?, wl_version = ? where wl_id = ? AND wl_context_path = ? AND wl_session_attribute_key = ? AND wl_internal_attribute = ? AND (? > (select max(wl_version) from " + tableName + " where wl_id = ? AND wl_context_path = ? AND wl_session_attribute_key = ?))";
      this.deleteQuery = "DELETE FROM " + tableName + " WHERE WL_ID = ? AND WL_CONTEXT_PATH = ? ";
      this.localService = new LocalPersistenceServiceInternal(this);
   }

   public PersistenceServiceInternal getLocalService() {
      return this.localService;
   }

   public void localPersistState(BatchedSessionState state) throws ServiceUnavailableException {
      this.persistState(state, false);
   }

   public void persistState(BatchedSessionState state) throws ServiceUnavailableException {
      this.persistState(state, false);
   }

   public void persistState(BatchedSessionState state, boolean remote) throws ServiceUnavailableException {
      if (remote) {
         this.verifyCaller();
      }

      Update[] updates = state.getUpdates();
      int size = updates.length;
      if (size != 0) {
         Connection con = null;

         try {
            con = this.dataSource.getConnection();
         } catch (SQLException var14) {
            if (WANReplicationDetailsDebugLogger.isDebugEnabled()) {
               WANReplicationDetailsDebugLogger.debug("Database unavailable", var14);
            }

            throw new ServiceUnavailableException("Database unavailabe");
         }

         try {
            SessionDiff[] diffs = new SessionDiff[size];
            this.updateSessionId(con, size, updates, diffs, state);
            this.createSessionsInDB(con, size, updates, diffs, state);
            this.updateSession(con, size, diffs, updates, state);
            if (WANReplicationDetailsDebugLogger.isDebugEnabled()) {
               WANReplicationDetailsDebugLogger.debug("Persisted " + updates.length + " sessions to the database");
            }
         } finally {
            if (con != null) {
               try {
                  con.close();
               } catch (SQLException var13) {
               }
            }

         }

      }
   }

   private void updateSessionId(Connection con, int size, Update[] updates, SessionDiff[] diffs, BatchedSessionState state) {
      PreparedStatement stmt = null;
      boolean executeBatch = false;

      try {
         stmt = con.prepareStatement(this.updateIdQuery);

         for(int i = 0; i < size; ++i) {
            diffs[i] = updates[i].getChange();
            String oldId = (String)diffs[i].getInternalAttributes().get("weblogic.servlet.session.oldId");
            String newId = (String)diffs[i].getInternalAttributes().get("weblogic.servlet.session.newId");
            if (oldId != null && newId != null) {
               executeBatch = true;
               stmt.setString(1, newId);
               stmt.setString(2, oldId);
               stmt.setString(3, updates[i].getContextPath());
               stmt.addBatch();
            }
         }

         if (executeBatch) {
            stmt.executeBatch();
         }
      } catch (SQLException var14) {
         if (WANReplicationDetailsDebugLogger.isDebugEnabled()) {
            WANReplicationDetailsDebugLogger.debug("Failed while making bulk  update session id. We will automatically attempt to fix this ", var14);
         }

         this.makeIndividualUpdateIdCalls(con, state, diffs);
      } finally {
         this.closeStatement(stmt);
      }

   }

   private void createSessionsInDB(Connection con, int size, Update[] updates, SessionDiff[] diffs, BatchedSessionState state) {
      PreparedStatement stmt = null;

      try {
         stmt = con.prepareStatement(this.insertQuery);

         for(int i = 0; i < size; ++i) {
            try {
               if (diffs[i] == null) {
                  diffs[i] = updates[i].getChange();
               }

               HashMap attributes = diffs[i].getNewAttributes();
               this.insertAttributes(attributes, stmt, updates[i], false, diffs[i]);
               attributes = diffs[i].getNewInternalAttributes();
               this.insertAttributes(attributes, stmt, updates[i], true, diffs[i]);
            } catch (IOException var13) {
               if (WANReplicationDetailsDebugLogger.isDebugEnabled()) {
                  WANReplicationDetailsDebugLogger.debug("Failed to serialize new attributes for user session identified by " + updates[i].getSessionID(), var13);
               }
            }
         }

         stmt.executeBatch();
      } catch (SQLException var14) {
         if (WANReplicationDetailsDebugLogger.isDebugEnabled()) {
            WANReplicationDetailsDebugLogger.debug("Failed while making bulk  insert. We will automatically attempt to fix this ", var14);
         }

         this.makeIndividualInsertCalls(con, state, diffs);
      } finally {
         this.closeStatement(stmt);
      }

   }

   private void updateSession(Connection con, int size, SessionDiff[] diffs, Update[] updates, BatchedSessionState state) {
      PreparedStatement stmt = null;

      try {
         stmt = con.prepareStatement(this.updateQuery);
         setQueryTimeout(stmt, 30);

         for(int i = 0; i < size; ++i) {
            try {
               if (diffs[i] == null) {
                  diffs[i] = updates[i].getChange();
               }

               HashMap attributes = diffs[i].getUpdateAttributes();
               this.updateAttributes(attributes, stmt, updates[i], false, diffs[i]);
               attributes = diffs[i].getUpdateInternalAttributes();
               this.updateAttributes(attributes, stmt, updates[i], true, diffs[i]);
            } catch (IOException var13) {
               if (WANReplicationDetailsDebugLogger.isDebugEnabled()) {
                  WANReplicationDetailsDebugLogger.debug("Failed to serialize updated attributes for user session identified by " + updates[i].getSessionID(), var13);
               }
            }
         }

         stmt.executeBatch();
      } catch (SQLException var14) {
         if (WANReplicationDetailsDebugLogger.isDebugEnabled()) {
            WANReplicationDetailsDebugLogger.debug("Failed while making bulk  update. We will automatically attempt to fix this ", var14);
         }

         this.makeIndividualUpdateCalls(con, state, diffs);
      } finally {
         this.closeStatement(stmt);
      }

   }

   private void closeStatement(PreparedStatement stmt) {
      if (stmt != null) {
         try {
            stmt.close();
         } catch (SQLException var3) {
         }
      }

   }

   private void makeIndividualUpdateIdCalls(Connection con, BatchedSessionState state, SessionDiff[] diffs) {
      Update[] updates = state.getUpdates();
      int size = updates.length;

      PreparedStatement stmt;
      try {
         stmt = con.prepareStatement(this.updateIdQuery);
      } catch (SQLException var11) {
         throw new AssertionError("Unexpected exception" + var11.toString());
      }

      try {
         for(int i = 0; i < size; ++i) {
            if (diffs[i] == null) {
               diffs[i] = updates[i].getChange();
            }

            this.makeIndividualUpdateIdCall(updates[i], diffs[i], stmt);
         }
      } finally {
         this.closeStatement(stmt);
      }

   }

   private void makeIndividualUpdateIdCall(Update update, SessionDiff diff, PreparedStatement stmt) {
      try {
         String oldId = (String)diff.getInternalAttributes().get("weblogic.servlet.session.oldId");
         String newId = (String)diff.getInternalAttributes().get("weblogic.servlet.session.newId");
         if (oldId != null && newId != null) {
            stmt.setString(1, newId);
            stmt.setString(2, oldId);
            stmt.setString(3, update.getContextPath());
         }

         stmt.execute();
      } catch (SQLException var6) {
         if (WANReplicationDetailsDebugLogger.isDebugEnabled()) {
            WANReplicationDetailsDebugLogger.debug("Failed while making individual update session id call for user with session id " + update.getSessionID(), var6);
         }
      }

   }

   private void makeIndividualUpdateCalls(Connection con, BatchedSessionState state, SessionDiff[] diffs) {
      Update[] updates = state.getUpdates();
      int size = updates.length;

      PreparedStatement stmt;
      try {
         stmt = con.prepareStatement(this.updateQuery);
      } catch (SQLException var11) {
         throw new AssertionError("Unexpected exception" + var11.toString());
      }

      try {
         for(int i = 0; i < size; ++i) {
            if (diffs[i] == null) {
               diffs[i] = updates[i].getChange();
            }

            this.makeIndividualUpdateCall(diffs[i].getUpdateAttributes(), updates[i], diffs[i], stmt, false);
            this.makeIndividualUpdateCall(diffs[i].getUpdateInternalAttributes(), updates[i], diffs[i], stmt, true);
         }
      } finally {
         this.closeStatement(stmt);
      }

   }

   private void makeIndividualUpdateCall(HashMap attributes, Update update, SessionDiff diff, PreparedStatement stmt, boolean isInternalAttribute) {
      Iterator iter = attributes.keySet().iterator();

      while(iter.hasNext()) {
         String key = (String)iter.next();

         try {
            stmt.setLong(1, update.getLastAccessTime());
            byte[] b = diff.getBytesForDB(attributes.get(key));
            stmt.setBytes(2, b);
            stmt.setInt(3, diff.getVersionCount());
            stmt.setString(4, update.getSessionID());
            stmt.setString(5, update.getContextPath());
            stmt.setString(6, key);
            stmt.setInt(7, isInternalAttribute ? 1 : 0);
            stmt.setInt(8, diff.getVersionCount());
            stmt.setString(9, update.getSessionID());
            stmt.setString(10, update.getContextPath());
            stmt.setString(11, key);
            setQueryTimeout(stmt, 30);
            stmt.execute();
         } catch (SQLException var9) {
            if (WANReplicationDetailsDebugLogger.isDebugEnabled()) {
               WANReplicationDetailsDebugLogger.debug("Failed while making individual update call for user with session id " + update.getSessionID(), var9);
            }
         } catch (IOException var10) {
            if (WANReplicationDetailsDebugLogger.isDebugEnabled()) {
               WANReplicationDetailsDebugLogger.debug("Failed while making individual update call for user with session id " + update.getSessionID(), var10);
            }
         }
      }

   }

   private void updateAttributes(HashMap attributes, PreparedStatement stmt, Update update, boolean isInternalAttribute, SessionDiff diff) throws SQLException, IOException {
      Iterator iter = attributes.keySet().iterator();

      while(iter.hasNext()) {
         String key = (String)iter.next();
         stmt.setLong(1, update.getLastAccessTime());
         byte[] b = diff.getBytesForDB(attributes.get(key));
         stmt.setBytes(2, b);
         stmt.setInt(3, diff.getVersionCount());
         stmt.setString(4, update.getSessionID());
         stmt.setString(5, update.getContextPath());
         stmt.setString(6, key);
         stmt.setInt(7, isInternalAttribute ? 1 : 0);
         stmt.setInt(8, diff.getVersionCount());
         stmt.setString(9, update.getSessionID());
         stmt.setString(10, update.getContextPath());
         stmt.setString(11, key);
         stmt.addBatch();
      }

   }

   public void localInvalidateSessions(Set set) throws RemoteException {
      this.invalidateSessions(set, false);
   }

   public void invalidateSessions(Set set) throws RemoteException {
      this.invalidateSessions(set, true);
   }

   private void invalidateSessions(Set set, boolean remote) throws RemoteException {
      if (remote) {
         this.verifyCaller();
      }

      Connection con = null;

      try {
         con = this.dataSource.getConnection();
      } catch (SQLException var16) {
         throw new ServiceUnavailableException("Database unavailabe");
      }

      PreparedStatement stmt = null;

      try {
         stmt = con.prepareStatement(this.deleteQuery);
         Iterator iterator = set.iterator();

         while(iterator.hasNext()) {
            Invalidate req = (Invalidate)iterator.next();
            stmt.setString(1, req.getSessionID());
            stmt.setString(2, req.getContextPath());
            stmt.addBatch();
         }

         stmt.executeBatch();
      } catch (SQLException var17) {
         if (WANReplicationDetailsDebugLogger.isDebugEnabled()) {
            WANReplicationDetailsDebugLogger.debug("Failed to invalidate some  sessions. The server will perform auto recovery", var17);
         }

         this.makeIndividualDeleteCalls(con, set);
      } finally {
         if (con != null) {
            try {
               con.close();
            } catch (SQLException var15) {
            }
         }

         this.closeStatement(stmt);
      }

   }

   private void insertAttributes(HashMap attributes, PreparedStatement stmt, Update update, boolean isInternalAttribute, SessionDiff diff) throws SQLException, IOException {
      Iterator iter = attributes.keySet().iterator();

      while(iter.hasNext()) {
         String key = (String)iter.next();
         stmt.setString(1, update.getSessionID());
         stmt.setLong(2, update.getCreationTime());
         stmt.setString(3, update.getContextPath());
         stmt.setInt(4, update.getMaxInactiveTime());
         stmt.setLong(5, update.getLastAccessTime());
         stmt.setInt(6, diff.getVersionCount());
         stmt.setString(7, key);
         byte[] b = diff.getBytesForDB(attributes.get(key));
         stmt.setBinaryStream(8, new UnsyncByteArrayInputStream(b), b.length);
         stmt.setInt(9, isInternalAttribute ? 1 : 0);
         stmt.addBatch();
      }

   }

   private void makeIndividualDeleteCalls(Connection con, Set set) {
      PreparedStatement stmt = null;

      try {
         stmt = con.prepareStatement(this.deleteQuery);
      } catch (SQLException var11) {
         throw new AssertionError("Unexpected exception" + var11.toString());
      }

      try {
         Iterator iterator = set.iterator();

         while(iterator.hasNext()) {
            Invalidate req = (Invalidate)iterator.next();

            try {
               stmt.setString(1, req.getSessionID());
               stmt.setString(2, req.getContextPath());
               stmt.execute();
            } catch (SQLException var12) {
               if (WANReplicationDetailsDebugLogger.isDebugEnabled()) {
                  WANReplicationDetailsDebugLogger.debug("Failed to invalidate  session " + req.getSessionID(), var12);
               }
            }
         }
      } finally {
         this.closeStatement(stmt);
      }

   }

   private void makeIndividualInsertCall(HashMap attributes, Update update, SessionDiff diff, PreparedStatement stmt, boolean isInternalAttribute) {
      Iterator iter = attributes.keySet().iterator();

      while(iter.hasNext()) {
         String key = (String)iter.next();

         try {
            stmt.setString(1, update.getSessionID());
            stmt.setLong(2, update.getCreationTime());
            stmt.setString(3, update.getContextPath());
            stmt.setInt(4, update.getMaxInactiveTime());
            stmt.setLong(5, update.getLastAccessTime());
            stmt.setInt(6, diff.getVersionCount());
            stmt.setString(7, key);
            byte[] b = diff.getBytesForDB(attributes.get(key));
            stmt.setBinaryStream(8, new UnsyncByteArrayInputStream(b), b.length);
            stmt.setInt(9, isInternalAttribute ? 1 : 0);
            stmt.execute();
         } catch (SQLException var9) {
            if (WANReplicationDetailsDebugLogger.isDebugEnabled()) {
               WANReplicationDetailsDebugLogger.debug("Failed during an individual insert calls to the database. This is normal as the remote  call is idempotent", var9);
            }
         } catch (IOException var10) {
            if (WANReplicationDetailsDebugLogger.isDebugEnabled()) {
               WANReplicationDetailsDebugLogger.debug("Failed during an individual insert calls to the database. This is normal as the remote  call is idempotent", var10);
            }
         }
      }

   }

   private void makeIndividualInsertCalls(Connection con, BatchedSessionState state, SessionDiff[] diffs) {
      Update[] updates = state.getUpdates();
      int size = updates.length;

      PreparedStatement stmt;
      try {
         stmt = con.prepareStatement(this.insertQuery);
      } catch (SQLException var11) {
         throw new AssertionError("Unexpected exception" + var11.toString());
      }

      try {
         for(int i = 0; i < size; ++i) {
            if (diffs[i] == null) {
               diffs[i] = updates[i].getChange();
            }

            this.makeIndividualInsertCall(diffs[i].getNewAttributes(), updates[i], diffs[i], stmt, false);
            this.makeIndividualInsertCall(diffs[i].getNewInternalAttributes(), updates[i], diffs[i], stmt, true);
         }
      } finally {
         this.closeStatement(stmt);
      }

   }

   private static void setQueryTimeout(PreparedStatement stmt, int timeout) {
      try {
         stmt.setQueryTimeout(timeout);
      } catch (SQLException var3) {
      }

   }

   private void verifyCaller() {
      try {
         HostID id = ServerHelper.getClientEndPoint().getHostID();
         AuthenticatedSubject subject = (AuthenticatedSubject)SubjectManager.getSubjectManager().getCurrentSubject(KERNEL_ID);
         if (subject == null) {
            throw new SecurityException("Null user is not permitted to perform WAN session replication operations");
         } else {
            int result = RemoteDomainSecurityHelper.acceptRemoteDomainCall(id, subject);
            if (result == 1) {
               throw new SecurityException("user " + subject.getName() + " is not permitted to perform WAN session replication operations");
            }
         }
      } catch (ServerNotActiveException var4) {
         throw new SecurityException("operation not permitted");
      }
   }

   private final class LocalPersistenceServiceInternal implements PersistenceServiceInternal {
      PersistenceServiceImpl persistenceService;

      private LocalPersistenceServiceInternal(PersistenceServiceImpl persistenceService) {
         this.persistenceService = persistenceService;
      }

      public void invalidateSessions(Set set) throws RemoteException {
         this.persistenceService.localInvalidateSessions(set);
      }

      public void persistState(BatchedSessionState state) throws ServiceUnavailableException, RemoteException {
         this.persistenceService.localPersistState(state);
      }

      // $FF: synthetic method
      LocalPersistenceServiceInternal(PersistenceServiceImpl x1, Object x2) {
         this(x1);
      }
   }
}
