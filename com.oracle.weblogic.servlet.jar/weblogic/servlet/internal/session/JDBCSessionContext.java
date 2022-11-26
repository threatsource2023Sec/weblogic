package weblogic.servlet.internal.session;

import java.lang.ref.WeakReference;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.WeakHashMap;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.sql.DataSource;
import weblogic.cache.utils.BubblingCache;
import weblogic.servlet.internal.NakedTimerListenerBase;
import weblogic.servlet.internal.ServletRequestImpl;
import weblogic.servlet.internal.ServletResponseImpl;
import weblogic.servlet.internal.WebAppServletContext;
import weblogic.timers.Timer;

public class JDBCSessionContext extends SessionContext {
   protected final String selectQuery;
   protected final String selectLATQuery;
   protected final String selectIDSQuery;
   protected final String updateQuery;
   protected final String updateIdQuery;
   protected final String updateLATQuery;
   protected final String deleteQuery;
   protected final String insertQuery;
   protected final String countQuery;
   protected final String selectCtxPathQuery;
   protected final String tableName;
   protected final String wlMaxInactiveInternal;
   protected final Properties properties;
   protected final Map cache;
   protected final DataSource dataSource;
   protected static final String dataSourceKey = "dataSourceProp";
   private LastAccessTimeTrigger latTrigger;
   private static boolean tryImplicitConversionFromCharToSmallInt = Boolean.getBoolean("weblogic.servlet.sessions.jdbc.tryImplicitConversionFromCharToSmallInt");

   public JDBCSessionContext(WebAppServletContext sci, SessionConfigManager scm) {
      super(sci, scm);
      this.wlMaxInactiveInternal = this.configMgr.getMaxInactiveIntervalColumnName();
      this.tableName = this.configMgr.getPersistentStoreTable();
      this.insertQuery = this.createInsertQuery();
      this.selectIDSQuery = this.createSelectIDSQuery();
      this.updateQuery = this.createUpdateQuery();
      this.updateIdQuery = this.createUpdateIdQuery();
      this.updateLATQuery = this.createUpdateLATQuery();
      this.selectQuery = this.createSelectQuery();
      this.selectLATQuery = this.createSelectLATQuery();
      this.deleteQuery = this.createDeleteQuery();
      this.countQuery = this.createCountQuery();
      this.selectCtxPathQuery = this.createSelectCtxPathQuery();
      this.cache = (Map)(this.configMgr.getCacheSize() <= 0 ? Collections.EMPTY_MAP : new JDBCSessionCache(this.configMgr.getCacheSize()));
      this.properties = new Properties();
      String dataSourceJNDIName;
      if ((dataSourceJNDIName = this.configMgr.getPersistentDataSourceJNDIName()) != null) {
         this.dataSource = this.lookupDataSource(dataSourceJNDIName);
      } else {
         this.properties.put("connectionPoolID", this.configMgr.getPersistentStorePool());
         this.dataSource = null;
      }

   }

   private DataSource lookupDataSource(String dataSourceJNDIName) {
      Context ctx = null;

      Object var4;
      try {
         ctx = new InitialContext();
         DataSource var3 = (DataSource)ctx.lookup(dataSourceJNDIName);
         return var3;
      } catch (NamingException var14) {
         HTTPSessionLogger.logUnexpectedError(this.getServletContext().getLogContext(), var14);
         var4 = null;
      } finally {
         if (ctx != null) {
            try {
               ctx.close();
            } catch (NamingException var13) {
            }
         }

      }

      return (DataSource)var4;
   }

   public void startTimers() {
      super.startTimers();
      this.latTrigger = new LastAccessTimeTrigger(this.getServletContext(), this.dataSource, this.properties, 10, this.isDebugEnabled());
   }

   String getSelectQuery() {
      return this.selectQuery;
   }

   String getSelectLATQuery() {
      return this.selectLATQuery;
   }

   String getUpdateQuery() {
      return this.updateQuery;
   }

   String getUpdateIdQuery() {
      return this.updateIdQuery;
   }

   String getUpdateLATQuery() {
      return this.updateLATQuery;
   }

   String getDeleteQuery() {
      return this.deleteQuery;
   }

   String getInsertQuery() {
      return this.insertQuery;
   }

   String getCountQuery() {
      return this.countQuery;
   }

   String getSelectCtxPathQuery() {
      return this.selectCtxPathQuery;
   }

   public HttpSession getNewSession(String sessionid, ServletRequestImpl req, ServletResponseImpl res) {
      JDBCSessionData sess = JDBCSessionData.newSession(sessionid, this, this.dataSource, this.properties);
      if (sess == null) {
         return null;
      } else {
         sess.incrementActiveRequestCount();
         if (this.cache != Collections.EMPTY_MAP) {
            this.cache.put(sess.id, sess);
         }

         this.incrementOpenSessionsCount();
         return sess;
      }
   }

   public String getPersistentStoreType() {
      return "jdbc";
   }

   protected void invalidateOrphanedSessions() {
      Set ids = this.getServletContext().getServer().getSessionLogin().getAllIds();
      if (!ids.isEmpty()) {
         String[] internalIds = this.getIdsInternal();

         int i;
         for(i = 0; i < internalIds.length; ++i) {
            ids.remove(internalIds[i]);
         }

         if (i != 0) {
            String id = null;
            Iterator iter = ids.iterator();

            while(iter.hasNext()) {
               id = (String)iter.next();
               this.getServletContext().getServer().getSessionLogin().unregister(id, this.getServletContext().getContextPath());
            }

         }
      }
   }

   public SessionData getSessionInternal(String sessionid, ServletRequestImpl req, ServletResponseImpl res) {
      sessionid = SessionData.getID(sessionid);
      synchronized(sessionid.intern()) {
         JDBCSessionData sess = (JDBCSessionData)this.cache.get(sessionid);
         if (sess != null) {
            synchronized(sess) {
               if (!sess.sessionInUse() && sess.isCacheStale()) {
                  this.cache.remove(sessionid);
                  sess = null;
               }
            }
         }

         if (sess != null) {
            if (req != null && res != null) {
               if (!sess.isValidForceCheck()) {
                  this.cache.remove(sessionid);
                  return null;
               }

               sess.incrementActiveRequestCount();
               if (!sess.isValidForceCheck()) {
                  this.cache.remove(sessionid);
                  return null;
               }
            }

            return sess;
         } else {
            sess = this.getSessionDataFromDB(sessionid, this.properties);
            if (sess == null) {
               return null;
            } else {
               synchronized(sess) {
                  sess.transientAttributes = (Hashtable)this.transientData.get(sess.id);
                  if (req != null && res != null) {
                     if (!sess.isValidForceCheck()) {
                        Object var10000 = null;
                        return (SessionData)var10000;
                     } else {
                        sess.incrementActiveRequestCount();
                        sess.updateVersionIfNeeded(this);
                        ((JDBCSessionContext)sess.getContext()).cacheSession(sess);
                        sess.notifyActivated(new HttpSessionEvent(sess));
                        sess.reinitRuntimeMBean();
                     }
                  }

                  return sess;
               }
            }
         }
      }
   }

   public JDBCSessionData getSessionDataFromDB(String sessionid, Properties properties) {
      return JDBCSessionData.getFromDB(sessionid, this, this.dataSource, properties);
   }

   protected JDBCSessionData createNewData(String sessionID, SessionContext sesContext, DataSource dataSource, Properties properties, boolean isNew) {
      return new JDBCSessionData(sessionID, sesContext, dataSource, properties, isNew);
   }

   public String[] getIdsInternal() {
      return JDBCSessionData.getSessionIds(this.dataSource, this.properties, this.getServletContext(), this.selectIDSQuery);
   }

   public String lookupAppVersionIdForSession(String sid, ServletRequestImpl req, ServletResponseImpl res) {
      if (sid == null) {
         return null;
      } else {
         sid = SessionData.getID(sid);
         SessionData sess = (SessionData)this.cache.get(sid);
         if (sess != null) {
            return sess.getVersionId();
         } else {
            String currCtxPath = this.getServletContext().getName();
            String[] fullCtxPaths = JDBCSessionData.getWlCtxPaths(this.dataSource, this.properties, this.getServletContext(), this.getSelectCtxPathQuery(), sid);
            String[] var7 = fullCtxPaths;
            int var8 = fullCtxPaths.length;

            for(int var9 = 0; var9 < var8; ++var9) {
               String ctxPath = var7[var9];
               if (ctxPath.startsWith(currCtxPath)) {
                  int poundPos = ctxPath.indexOf(35);
                  if (poundPos != -1) {
                     return ctxPath.substring(poundPos + 1);
                  }
               }
            }

            return null;
         }
      }
   }

   void unregisterExpiredSessions(ArrayList expired) {
   }

   public int getCurrOpenSessionsCount() {
      return JDBCSessionData.getTotalSessionsCount(this.dataSource, this.properties, this.getServletContext(), this.countQuery);
   }

   boolean invalidateSession(SessionData data, boolean trigger) {
      if (data == null) {
         return false;
      } else {
         this.cache.remove(data.id);

         try {
            this.transientData.remove(data.id);
            data.remove();
            this.decrementOpenSessionsCount();
            return true;
         } catch (Exception var4) {
            HTTPSessionLogger.logUnableToRemoveSession(data.id, var4);
            return false;
         }
      }
   }

   void registerIdChangedSession(SessionData session) {
      super.registerIdChangedSession(session);
      if (this.cache != Collections.EMPTY_MAP && this.cache.remove(session.oldId) != null) {
         this.cache.put(session.id, session);
      }

      if (session.transientAttributes != null && this.transientData.remove(session.oldId) != null) {
         this.transientData.put(session.id, session.transientAttributes);
      }

   }

   protected void afterSync(SessionData session) {
      if (this.cache != Collections.EMPTY_MAP) {
         this.cache.put(session.id, session);
      }

      if (session.transientAttributes != null) {
         this.transientData.put(session.id, session.transientAttributes);
      }

   }

   protected boolean needToPassivate() {
      return true;
   }

   public void destroy(boolean serverState) {
      super.destroy(serverState);
      if (this.latTrigger != null) {
         this.latTrigger.stop();
      }

   }

   public int getNonPersistedSessionCount() {
      return 0;
   }

   void updateLAT(JDBCSessionData sess, String contextName, String ctxNamePlusVid) {
      this.latTrigger.addLATUpdateQuery(new UpdateQueryObject(sess, contextName, ctxNamePlusVid, this.tableName));
   }

   private String createInsertQuery() {
      StringBuilder sb = new StringBuilder();
      sb.append("insert into ");
      sb.append(this.tableName);
      sb.append(" (wl_id, wl_context_path, wl_is_new, wl_create_time, ");
      sb.append("wl_session_values, wl_is_valid, wl_access_time, ");
      sb.append(this.wlMaxInactiveInternal);
      sb.append(" ) values (?, ?, ?, ?, ?, ?, ?, ?)");
      return sb.toString();
   }

   protected String createSelectIDSQuery() {
      StringBuilder sb = new StringBuilder();
      sb.append("select wl_id from ");
      sb.append(this.tableName);
      sb.append(" where ( wl_context_path = ? or wl_context_path = ? )");
      return sb.toString();
   }

   private String createUpdateQuery() {
      StringBuilder sb = new StringBuilder();
      sb.append("update ");
      sb.append(this.tableName);
      sb.append(" set wl_session_values = ?, ");
      sb.append("wl_is_new = ?, wl_is_valid = ?, wl_access_time = ?, ");
      sb.append(this.wlMaxInactiveInternal);
      sb.append(" = ?");
      sb.append(" where wl_id = ? ");
      sb.append(" and ( wl_context_path = ? or wl_context_path = ? )");
      sb.append(" and ( wl_access_time = ? or wl_access_time = ? )");
      return sb.toString();
   }

   private String createUpdateIdQuery() {
      StringBuilder sb = new StringBuilder();
      sb.append("update ");
      sb.append(this.tableName);
      sb.append(" set wl_id = ? ");
      sb.append(" where wl_id = ? ");
      sb.append(" and ( wl_context_path = ? or wl_context_path = ? )");
      return sb.toString();
   }

   protected String createSelectQuery() {
      StringBuilder sb = new StringBuilder();
      sb.append("select wl_is_new, ");
      sb.append(" wl_create_time,");
      sb.append("wl_session_values, wl_is_valid, wl_access_time, ");
      sb.append(this.wlMaxInactiveInternal);
      sb.append(" from ");
      sb.append(this.tableName);
      sb.append(" where wl_id = ?");
      sb.append(" and ( wl_context_path = ? or wl_context_path = ? )");
      return sb.toString();
   }

   protected String createSelectLATQuery() {
      StringBuilder sb = new StringBuilder();
      sb.append("select wl_access_time from ");
      sb.append(this.tableName);
      sb.append(" where wl_id = ?");
      sb.append(" and ( wl_context_path = ? or wl_context_path = ? )");
      return sb.toString();
   }

   private String createDeleteQuery() {
      StringBuilder sb = new StringBuilder();
      sb.append("delete from ");
      sb.append(this.tableName);
      sb.append(" where wl_id = ?");
      sb.append("and ( wl_context_path = ? or wl_context_path = ? )");
      return sb.toString();
   }

   protected String createCountQuery() {
      StringBuilder sb = new StringBuilder();
      sb.append("select count(*) from ");
      sb.append(this.tableName);
      sb.append(" where ( wl_context_path = ? or wl_context_path = ? )");
      return sb.toString();
   }

   protected String createSelectCtxPathQuery() {
      StringBuilder sb = new StringBuilder();
      sb.append("select wl_context_path from ").append(this.tableName);
      sb.append(" where wl_id = ? ");
      return sb.toString();
   }

   public String createUpdateLATQuery() {
      StringBuilder sb = new StringBuilder();
      sb.append("update ");
      sb.append(this.tableName);
      sb.append(" set wl_is_valid = ?");
      sb.append(", wl_access_time = ?");
      sb.append(" where wl_id = ?");
      sb.append(" and ( wl_context_path = ? or wl_context_path = ? )");
      sb.append(" and wl_access_time < ?");
      return sb.toString();
   }

   private void cacheSession(JDBCSessionData session) {
      if (this.cache != Collections.EMPTY_MAP) {
         this.cache.put(session.id, session);
      }

   }

   protected class JDBCSessionCache extends BubblingCache {
      private static final long serialVersionUID = 6276338313903142442L;
      private final Map overflows = new WeakHashMap();

      public JDBCSessionCache(int size) {
         super(size);
      }

      public synchronized Object get(Object key) {
         Object value = super.get(key);
         if (value == null) {
            WeakReference ref = (WeakReference)this.overflows.get(key);
            if (ref != null) {
               value = ref.get();
               if (value == null) {
                  this.overflows.remove(key);
               }
            }
         }

         return value;
      }

      public synchronized Object put(Object key, Object value) {
         JDBCSessionData old = (JDBCSessionData)super.put(key, value);
         if (old != null && old != value) {
            this.overflows.put(old.id, new WeakReference(old));
         }

         return old;
      }

      public synchronized Object remove(Object key) {
         this.overflows.remove(key);
         return super.remove(key);
      }
   }

   private static class UpdateQueryObject {
      private final JDBCSessionData session;
      private final String contextName;
      private final String ctxNamePlusVid;
      private final String tableName;
      private final int hashCode;

      public UpdateQueryObject(JDBCSessionData sess, String contextName, String ctxNamePlusVid, String tableName) {
         this.session = sess;
         this.contextName = contextName;
         this.ctxNamePlusVid = ctxNamePlusVid;
         this.hashCode = sess.getInternalId().hashCode() ^ contextName.hashCode();
         this.tableName = tableName;
      }

      public JDBCSessionData getSession() {
         return this.session;
      }

      public int hashCode() {
         return this.hashCode;
      }

      public boolean equals(Object object) {
         if (this == object) {
            return true;
         } else if (!(object instanceof UpdateQueryObject)) {
            return false;
         } else {
            UpdateQueryObject other = (UpdateQueryObject)object;
            return this.session.getInternalId().equals(other.session.getInternalId()) && this.contextName.equals(other.contextName);
         }
      }

      public void prepareLATQuery(PreparedStatement pstmt, long lat) throws SQLException {
         pstmt.setString(1, this.session.isValid() ? "1" : "0");
         pstmt.setLong(2, lat);
         pstmt.setString(3, this.session.getInternalId());
         pstmt.setString(4, this.contextName);
         pstmt.setString(5, this.ctxNamePlusVid);
         pstmt.setLong(6, lat);
      }
   }

   private static class LastAccessTimeTrigger extends NakedTimerListenerBase {
      private Timer timer;
      private final Properties jdbcProps;
      private final HashSet set;
      private final int triggerInterval;
      private final boolean debug;
      private final DataSource dataSource;
      private final JDBCSessionContext jdbcCtx;

      private LastAccessTimeTrigger(WebAppServletContext ctx, DataSource dataSource, Properties jdbcProps, int interval, boolean dbg) {
         super("JDBCLastAccessTimeTrigger", ctx);
         this.jdbcProps = jdbcProps;
         this.dataSource = dataSource;
         this.set = new HashSet();
         this.triggerInterval = interval * 1000;
         this.debug = dbg;
         this.jdbcCtx = (JDBCSessionContext)ctx.getSessionContext();
         this.start();
      }

      private void start() {
         this.timer = this.timerManager.schedule(this, 0L, (long)this.triggerInterval);
      }

      private void stop() {
         this.timer.cancel();
         this.timerManager.stop();
      }

      public Connection getConnection() throws SQLException {
         return this.dataSource != null ? this.dataSource.getConnection() : JDBCSessionData.getConnection(this.dataSource, this.jdbcProps);
      }

      public void timerExpired(Timer timer) {
         Connection con = null;
         PreparedStatement stmt = null;
         HashSet persistSet = new HashSet();
         if (this.debug) {
            SessionContext.DEBUG_SESSIONS.debug("LAT trigger started at " + new Date() + " size " + this.set.size());
         }

         try {
            try {
               synchronized(this.set) {
                  if (this.set.size() == 0) {
                     return;
                  }

                  persistSet.addAll(this.set);
                  this.set.clear();
               }

               con = this.getConnection();
               stmt = con.prepareStatement(this.jdbcCtx.getUpdateLATQuery());
               Iterator i = persistSet.iterator();

               while(i.hasNext()) {
                  UpdateQueryObject object = (UpdateQueryObject)i.next();
                  JDBCSessionData sess = object.getSession();
                  synchronized(sess) {
                     if (!sess.isCacheStale()) {
                        long lat = sess.getLAT();
                        object.prepareLATQuery(stmt, lat);
                        stmt.addBatch();
                        object.getSession().setTriggerLAT(lat);
                     }
                  }
               }

               stmt.executeBatch();
               persistSet.clear();
            } catch (SQLException var30) {
               var30.printStackTrace();
            }

         } finally {
            try {
               if (con != null) {
                  con.close();
               }
            } catch (SQLException var27) {
            }

            try {
               if (stmt != null) {
                  stmt.close();
               }
            } catch (SQLException var26) {
            }

         }
      }

      private void addLATUpdateQuery(UpdateQueryObject query) {
         synchronized(this.set) {
            this.set.add(query);
         }
      }

      // $FF: synthetic method
      LastAccessTimeTrigger(WebAppServletContext x0, DataSource x1, Properties x2, int x3, boolean x4, Object x5) {
         this(x0, x1, x2, x3, x4);
      }
   }
}
