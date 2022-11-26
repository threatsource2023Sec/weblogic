package weblogic.servlet.internal.session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import javax.sql.DataSource;
import weblogic.cluster.replication.AsyncFlush;
import weblogic.cluster.replication.QueueManager;
import weblogic.cluster.replication.QueueManagerFactory;
import weblogic.cluster.replication.ResourceGroupKey;
import weblogic.cluster.replication.QueueManagerFactory.Locator;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.utils.collections.Stack;

public class AsyncJDBCPersistenceManager implements AsyncFlush {
   private QueueManager queue;
   private String updateQuery;
   private String insertQuery;
   private String deleteQuery;
   private String updateIdQuery;
   private Properties jdbcProps;
   private DataSource dataSource;
   private Stack pendingUpdates;
   private static final int QUERY_TIMEOUT = 30;
   protected static final DebugLogger DEBUG_SESSIONS = DebugLogger.getDebugLogger("DebugHttpSessions");

   public AsyncJDBCPersistenceManager(DataSource dataSource, String updateQuery, String updateIdQuery, String insertQuery, String deleteQuery, int flushInterval, int flushThreshold, int queueTimeout) {
      this.dataSource = dataSource;
      this.updateQuery = updateQuery;
      this.updateIdQuery = updateIdQuery;
      this.insertQuery = insertQuery;
      this.deleteQuery = deleteQuery;
      this.queue = this.createAsyncQueue(flushInterval, flushThreshold, queueTimeout);
      this.pendingUpdates = new Stack();
   }

   public Connection getConnection() throws SQLException {
      return JDBCSessionData.getConnection(this.dataSource, (Properties)null);
   }

   public void update(AsyncJDBCSessionData data) {
      this.queue.addToUpdates(data);
   }

   public void blockingFlush() {
      this.queue.flushOnce();
   }

   public synchronized void flushQueue(BlockingQueue updateSet, ResourceGroupKey resourceGroupKey) {
      Set set = new HashSet();
      updateSet.drainTo(set);
      if (this.pendingUpdates.size() > 0) {
         set.addAll(this.pendingUpdates);
         this.pendingUpdates.clear();
      }

      Connection con = null;

      try {
         con = this.getConnection();
      } catch (SQLException var14) {
         if (DEBUG_SESSIONS.isDebugEnabled()) {
            DEBUG_SESSIONS.debug("Database unavailable", var14);
         }

         this.pendingUpdates.addAll(set);
         return;
      }

      PreparedStatement stmt = null;

      try {
         this.createSessionsInDB(con, (PreparedStatement)stmt, set);
         this.updateSessionIdInDB(con, (PreparedStatement)stmt, set);
         this.updateSessionsInDB(con, (PreparedStatement)stmt, set);
         this.removeSessionsInDB(con, (PreparedStatement)stmt, set);
         if (DEBUG_SESSIONS.isDebugEnabled()) {
            DEBUG_SESSIONS.debug("Persisted " + set.size() + " sessions to the database");
         }
      } finally {
         this.closeStatement((PreparedStatement)stmt);
         if (con != null) {
            try {
               con.close();
            } catch (SQLException var13) {
            }
         }

      }

   }

   private void updateSessionIdInDB(Connection con, PreparedStatement stmt, Set set) {
      try {
         stmt = con.prepareStatement(this.updateIdQuery);
         Iterator updates = set.iterator();
         boolean execute = false;

         while(updates.hasNext()) {
            AsyncJDBCSessionData tmpData = (AsyncJDBCSessionData)updates.next();
            synchronized(tmpData.getInternalLock()) {
               if (tmpData.needToSyncNewSessionId() && tmpData.getState() == 2) {
                  execute = true;
                  tmpData.addUpdateIdStatements(stmt);
                  stmt.addBatch();
                  tmpData.setNeedToSyncNewSessionId(false);
               }
            }
         }

         if (execute) {
            stmt.executeBatch();
         }
      } catch (SQLException var14) {
         if (DEBUG_SESSIONS.isDebugEnabled()) {
            DEBUG_SESSIONS.debug("Failed while making bulk session id update. ", var14);
         }
      } finally {
         this.closeStatement(stmt);
      }

   }

   private void createSessionsInDB(Connection con, PreparedStatement stmt, Set set) {
      boolean execute = false;

      try {
         stmt = con.prepareStatement(this.insertQuery);
         Iterator updates = set.iterator();

         while(updates.hasNext()) {
            AsyncJDBCSessionData tmpData = (AsyncJDBCSessionData)updates.next();
            if (tmpData.getState() == 1) {
               execute = true;
               tmpData.addStatements(stmt);
               stmt.addBatch();
            }
         }

         if (execute) {
            stmt.executeBatch();
         }
      } catch (SQLException var10) {
         if (DEBUG_SESSIONS.isDebugEnabled()) {
            DEBUG_SESSIONS.debug("Failed while making bulk  insert. We will automatically attempt to fix this ", var10);
         }
      } finally {
         this.closeStatement(stmt);
      }

   }

   private void updateSessionsInDB(Connection con, PreparedStatement stmt, Set set) {
      boolean execute = false;

      try {
         stmt = con.prepareStatement(this.updateQuery);
         Iterator updates = set.iterator();

         while(updates.hasNext()) {
            AsyncJDBCSessionData tmpData = (AsyncJDBCSessionData)updates.next();
            synchronized(tmpData.getInternalLock()) {
               if (tmpData.needToSyncNewSessionId()) {
                  this.pendingUpdates.add(tmpData);
               } else if (tmpData.getState() == 2) {
                  execute = true;
                  tmpData.addStatements(stmt);
                  stmt.addBatch();
               }
            }
         }

         if (execute) {
            stmt.executeBatch();
         }
      } catch (SQLException var14) {
         if (DEBUG_SESSIONS.isDebugEnabled()) {
            DEBUG_SESSIONS.debug("Failed while making bulk  update. We will automatically attempt to fix this ", var14);
         }
      } finally {
         this.closeStatement(stmt);
      }

   }

   public void removeSessionsInDB(Connection con, PreparedStatement stmt, Set set) {
      boolean execute = false;

      try {
         stmt = con.prepareStatement(this.deleteQuery);
         setQueryTimeout(stmt, 30);
         Iterator updates = set.iterator();

         while(updates.hasNext()) {
            AsyncJDBCSessionData tmpData = (AsyncJDBCSessionData)updates.next();
            if (tmpData.getState() == 3) {
               execute = true;
               tmpData.addStatements(stmt);
               stmt.addBatch();
            }
         }

         if (execute) {
            stmt.executeBatch();
         }
      } catch (SQLException var10) {
         if (DEBUG_SESSIONS.isDebugEnabled()) {
            DEBUG_SESSIONS.debug("Failed to invalidate some  sessions. The server will perform auto recovery", var10);
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

   private void closeStatement(PreparedStatement stmt) {
      if (stmt != null) {
         try {
            stmt.close();
         } catch (SQLException var3) {
         }
      }

   }

   private QueueManager createAsyncQueue(int flushInterval, int flushThreshold, int queueTimeout) {
      QueueManagerFactory factory = Locator.locate();
      return factory.newAsyncQueue(this, false, flushInterval, flushThreshold, queueTimeout);
   }
}
