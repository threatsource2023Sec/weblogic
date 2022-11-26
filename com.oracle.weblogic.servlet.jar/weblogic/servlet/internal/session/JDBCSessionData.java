package weblogic.servlet.internal.session;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.io.StreamCorruptedException;
import java.lang.annotation.Annotation;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import weblogic.common.internal.WLObjectInputStream;
import weblogic.common.internal.WLObjectOutputStream;
import weblogic.core.base.api.ClassLoaderService;
import weblogic.logging.Loggable;
import weblogic.protocol.ServerChannelManager;
import weblogic.rmi.utils.io.RemoteObjectReplacer;
import weblogic.server.GlobalServiceLocator;
import weblogic.servlet.internal.WebAppServletContext;
import weblogic.utils.StackTraceUtils;
import weblogic.utils.io.UnsyncByteArrayInputStream;
import weblogic.utils.io.UnsyncByteArrayOutputStream;

class JDBCSessionData extends SessionData implements HttpSession {
   private static final long serialVersionUID = 7715646887169061115L;
   private static final boolean PERSIST_APPVERSION = !Boolean.getBoolean("weblogic.servlet.session.PersistentBackCompatibility");
   private static final Driver driver = new weblogic.jdbc.pool.Driver();
   private final String logContext;
   private final String contextName;
   private final String fullCtxName;
   private final Properties jdbcProps;
   protected final JDBCSessionContext jdbcCtx;
   private final DataSource dataSource;
   protected transient long dbLAT;
   private transient long triggerLAT;
   private transient boolean isCacheStale = false;

   public JDBCSessionData(String id, SessionContext context, DataSource dataSource, Properties jdbcProps, boolean isNew) {
      super(id, context, isNew);
      this.dataSource = dataSource;
      this.jdbcProps = jdbcProps;
      this.logContext = context.getServletContext().getLogContext();
      this.jdbcCtx = (JDBCSessionContext)context;
      this.contextName = this.getContextName();
      this.fullCtxName = context.getServletContext().getFullCtxName();
   }

   static JDBCSessionData newSession(String sessionId, SessionContext context, DataSource dataSource, Properties jdbcProps) {
      JDBCSessionData ses = null;

      try {
         ses = new JDBCSessionData(sessionId, context, dataSource, jdbcProps, true);
         ses.dbCreate();
         context.getServletContext().getEventsManager().notifySessionLifetimeEvent(ses, true);
         return ses;
      } catch (SQLException var6) {
         HTTPSessionLogger.logUnexpectedError(context.getServletContext().getLogContext(), var6);
         return null;
      }
   }

   static JDBCSessionData getFromDB(String sessionId, SessionContext context, DataSource dataSource, Properties jdbcProps) {
      JDBCSessionData ses = null;

      try {
         ses = dbRefresh(sessionId, context, dataSource, jdbcProps);
         return ses;
      } catch (SessionNotFoundException var6) {
         return null;
      } catch (SQLException var7) {
         HTTPSessionLogger.logUnexpectedError(context.getServletContext().getLogContext(), var7);
         return null;
      }
   }

   static String[] getSessionIds(DataSource dataSource, Properties jdbcProps, WebAppServletContext context, String statement) {
      Connection conn = null;
      PreparedStatement stmt = null;
      ResultSet rs = null;
      ArrayList ids = new ArrayList();

      label51: {
         String[] var9;
         try {
            conn = getConnection(dataSource, jdbcProps);
            stmt = conn.prepareStatement(statement);
            stmt.setString(1, context.getName());
            stmt.setString(2, context.getFullCtxName());
            rs = stmt.executeQuery();

            while(true) {
               if (!rs.next()) {
                  break label51;
               }

               ids.add(rs.getString(1));
            }
         } catch (SQLException var13) {
            HTTPSessionLogger.logUnexpectedError(context.getLogContext(), var13);
            var9 = new String[0];
         } finally {
            closeDBResources(rs, stmt, conn);
         }

         return var9;
      }

      String[] sessionIds = new String[ids.size()];
      return (String[])((String[])ids.toArray(sessionIds));
   }

   static String[] getWlCtxPaths(DataSource dataSource, Properties jdbcProps, WebAppServletContext context, String statement, String sid) {
      Connection conn = null;
      PreparedStatement stmt = null;
      ResultSet rs = null;
      ArrayList ctxPaths = new ArrayList();

      label51: {
         String[] var10;
         try {
            conn = getConnection(dataSource, jdbcProps);
            stmt = conn.prepareStatement(statement);
            stmt.setString(1, sid);
            rs = stmt.executeQuery();

            while(true) {
               if (!rs.next()) {
                  break label51;
               }

               ctxPaths.add(rs.getString(1));
            }
         } catch (SQLException var14) {
            HTTPSessionLogger.logUnexpectedError(context.getLogContext(), var14);
            var10 = new String[0];
         } finally {
            closeDBResources(rs, stmt, conn);
         }

         return var10;
      }

      String[] sessionIds = new String[ctxPaths.size()];
      return (String[])((String[])ctxPaths.toArray(sessionIds));
   }

   static Connection getConnection(DataSource dataSource, Properties jdbcProps) throws SQLException {
      Connection conn = null;

      try {
         if (dataSource != null) {
            conn = dataSource.getConnection();
         } else {
            conn = driver.connect("jdbc:weblogic:pool", jdbcProps);
         }

         conn.setAutoCommit(true);
         return conn;
      } catch (SQLException var6) {
         try {
            conn.close();
         } catch (Exception var5) {
         }

         throw var6;
      }
   }

   protected void dbCreate() throws SQLException {
      if (this.isDebugEnabled()) {
         Loggable l = HTTPSessionLogger.logPerformOperationLoggable("dbCreate()", this.id, this.getContextPath());
         DEBUG_SESSIONS.debug(l.getMessage());
      }

      Connection conn = null;
      PreparedStatement stmt = null;

      try {
         if (conn == null) {
            conn = getConnection(this.dataSource, this.jdbcProps);
         }

         stmt = conn.prepareStatement(this.jdbcCtx.getInsertQuery());
         this.addCreateStatement(stmt);
         if (stmt.executeUpdate() != 1) {
            throw new SQLException("Failed to insert record for session " + this.id + " in the database");
         }

         this.dbLAT = this.accessTime;
      } catch (SQLException var7) {
         this.setValid(false);
         throw var7;
      } finally {
         this.setModified(false);
         closeDBResources((ResultSet)null, stmt, conn);
      }

   }

   protected void addCreateStatement(PreparedStatement stmt) throws SQLException {
      int count = 0;
      ++count;
      stmt.setString(count, this.id);
      ++count;
      stmt.setString(count, PERSIST_APPVERSION ? this.fullCtxName : this.contextName);
      ++count;
      stmt.setString(count, this.isNew() ? "1" : "0");
      ++count;
      stmt.setLong(count, this.creationTime);

      try {
         this.initVersionAttrsIfNeeded();
         byte[] attrBytes = this.serializeAttributes();
         ++count;
         stmt.setBinaryStream(count, new ByteArrayInputStream(attrBytes), attrBytes.length);
      } catch (IOException var4) {
         this.setValid(false);
         throw new SQLException("Could not serialize attributes to create session " + this.id + ":\n" + StackTraceUtils.throwable2StackTrace(var4));
      }

      ++count;
      stmt.setString(count, this.isValid() ? "1" : "0");
      ++count;
      stmt.setLong(count, this.accessTime);
      ++count;
      stmt.setInt(count, this.maxInactiveInterval);
   }

   protected void addFirstUpdateStatement(PreparedStatement stmt) throws SQLException, IOException {
      byte[] attrBytes = this.serializeAttributes();
      stmt.setBinaryStream(1, new ByteArrayInputStream(attrBytes), attrBytes.length);
      stmt.setString(2, this.isNew() ? "1" : "0");
      stmt.setString(3, this.isValid() ? "1" : "0");
      stmt.setLong(4, this.accessTime);
      stmt.setInt(5, this.maxInactiveInterval);
   }

   protected void addTimeSensitiveUpdateStatement(PreparedStatement stmt) throws SQLException {
      stmt.setString(6, this.id);
      stmt.setString(7, this.contextName);
      stmt.setString(8, this.fullCtxName);
      stmt.setLong(9, this.dbLAT);
      stmt.setLong(10, this.triggerLAT);
   }

   protected void dbUpdate() throws SQLException {
      if (this.isDebugEnabled()) {
         Loggable l = HTTPSessionLogger.logPerformOperationLoggable("dbUpdate() lat=" + this.accessTime + " ", this.id, this.getContextPath());
         DEBUG_SESSIONS.debug(l.getMessage());
      }

      if (this.needToSyncNewSessionId) {
         this.updateId();
         this.setNeedToSyncNewSessionId(false);
      }

      Connection conn = null;
      PreparedStatement stmt = null;

      try {
         int count = false;
         if (this.isModified()) {
            conn = getConnection(this.dataSource, this.jdbcProps);
            stmt = conn.prepareStatement(this.jdbcCtx.getUpdateQuery());

            try {
               this.addFirstUpdateStatement(stmt);
               int i = false;
               int i;
               synchronized(this.getInternalLock()) {
                  this.addTimeSensitiveUpdateStatement(stmt);
                  i = stmt.executeUpdate();
                  if (i > 0) {
                     this.dbLAT = this.accessTime;
                     this.triggerLAT = 0L;
                  }
               }

               if (i == 0) {
                  if (!this.isValid()) {
                     throw new SQLException("Failed to update database record for session " + this.id + " for contextName = " + this.contextName);
                  }

                  try {
                     this.dbCreate();
                  } catch (SQLException var13) {
                     HTTPSessionLogger.logJDBCSessionConcurrentModification(this.id + " ctx:" + this.contextName + " dblat:" + this.dbLAT + " triggerLAT:" + this.triggerLAT, var13);
                     throw var13;
                  }
               }
            } catch (IOException var15) {
               throw new SQLException("Could not serialize attributes to update session " + this.id + ":\n" + StackTraceUtils.throwable2StackTrace(var15));
            }
         } else {
            this.jdbcCtx.updateLAT(this, this.contextName, this.fullCtxName);
         }
      } catch (SQLException var16) {
         this.setValid(false);
         throw var16;
      } finally {
         this.setModified(false);
         closeDBResources((ResultSet)null, stmt, conn);
      }

   }

   protected void updateId() throws SQLException {
      if (this.isDebugEnabled()) {
         Loggable l = HTTPSessionLogger.logPerformOperationLoggable("updateId() oldId=" + this.oldId + " ", this.id, this.getContextPath());
         DEBUG_SESSIONS.debug(l.getMessage());
      }

      Connection conn = null;
      PreparedStatement stmt = null;

      try {
         conn = getConnection(this.dataSource, this.jdbcProps);
         stmt = conn.prepareStatement(this.jdbcCtx.getUpdateIdQuery());
         this.addUpdateIdStatement(stmt);
         stmt.executeUpdate();
      } catch (SQLException var7) {
         this.setValid(false);
         throw var7;
      } finally {
         this.setModified(false);
         closeDBResources((ResultSet)null, stmt, conn);
      }

   }

   protected void addUpdateIdStatement(PreparedStatement stmt) throws SQLException {
      stmt.setString(1, this.id);
      stmt.setString(2, this.oldId);
      stmt.setString(3, this.contextName);
      stmt.setString(4, this.fullCtxName);
   }

   protected static String getJDBCContextName(SessionContext context) {
      return context.getConfigMgr().isSessionSharingEnabled() ? context.getServletContext().getApplicationId() : context.getServletContext().getName();
   }

   protected static JDBCSessionData dbRefresh(String sessionID, SessionContext sesContext, DataSource dataSource, Properties properties) throws SQLException, SessionNotFoundException {
      Connection conn = null;
      PreparedStatement stmt = null;
      ResultSet rs = null;
      JDBCSessionData ses = null;
      JDBCSessionContext jdbcCtx = (JDBCSessionContext)sesContext;
      String contextName = sesContext.getServletContext().getName();
      if (DEBUG_SESSIONS.isDebugEnabled()) {
         DEBUG_SESSIONS.debug("Looking in database for " + sessionID + " with contextPath: " + contextName);
      }

      try {
         conn = getConnection(dataSource, properties);
         stmt = conn.prepareStatement(jdbcCtx.getSelectQuery());
         stmt.setString(1, sessionID);
         stmt.setString(2, contextName);
         stmt.setString(3, sesContext.getServletContext().getFullCtxName());
         rs = stmt.executeQuery();
         String isNewStr = null;
         if (!rs.next()) {
            throw new SessionNotFoundException();
         } else {
            isNewStr = rs.getString(1);
            if (rs.wasNull()) {
               throw new SQLException("Failed to read session " + sessionID + " from db");
            } else {
               ses = jdbcCtx.createNewData(sessionID, sesContext, dataSource, properties, false);
               ses.isNew = isNewStr.equals("1");
               ses.creationTime = rs.getLong(2);
               InputStream is = rs.getBinaryStream(3);
               ByteArrayOutputStream baos = new ByteArrayOutputStream();
               int i = false;
               byte[] attrBytes = null;

               byte[] attrBytes;
               try {
                  int i;
                  while((i = is.read()) != -1) {
                     baos.write(i);
                  }

                  attrBytes = baos.toByteArray();
               } catch (Exception var25) {
                  throw new SQLException("JDBCSessionData dbRefresh() fails getting session data: " + var25.getMessage());
               }

               ses.setValid(rs.getString(4).equals("1"));
               ses.accessTime = rs.getLong(5);
               ses.maxInactiveInterval = rs.getInt(6);
               ses.dbLAT = ses.accessTime;

               try {
                  ses.deSerializeAttributes(attrBytes, ses);
                  if (sesContext.isDebugEnabled()) {
                     Loggable l = HTTPSessionLogger.logPerformOperationLoggable("dbRefresh() gets version=" + ses.getInternalAttribute("weblogic.versionId"), sessionID, contextName);
                     DEBUG_SESSIONS.debug(l.getMessage());
                  }
               } catch (IOException var26) {
                  boolean isValid = ses.isValid();
                  ses.remove();
                  long interval = System.currentTimeMillis() - ses.getLAT();
                  if (isValid && (ses.maxInactiveInterval < 0 || interval <= (long)(ses.maxInactiveInterval * 1000))) {
                     throw new SQLException("Could not deserialize attributes after reading session " + sessionID + ":\n" + StackTraceUtils.throwable2StackTrace(var26));
                  }

                  Object var19 = null;
                  return (JDBCSessionData)var19;
               }

               JDBCSessionData var31 = ses;
               return var31;
            }
         }
      } catch (SQLException var27) {
         if (ses != null) {
            ses.setValid(false);
         }

         throw var27;
      } finally {
         if (ses != null) {
            ses.setModified(false);
         }

         closeDBResources(rs, stmt, conn);
      }
   }

   boolean isCacheStale() {
      if (!this.isCacheStale) {
         this.isCacheStale = this.checkCacheStale();
      }

      return this.isCacheStale;
   }

   boolean checkCacheStale() {
      if (this.isDebugEnabled()) {
         Loggable l = HTTPSessionLogger.logPerformOperationLoggable("isCacheStale()", this.id, this.getContextPath());
         DEBUG_SESSIONS.debug(l.getMessage());
      }

      Connection conn = null;
      PreparedStatement stmt = null;
      ResultSet rs = null;
      long lat = this.dbLAT;
      if (this.triggerLAT > this.dbLAT) {
         lat = this.triggerLAT;
      }

      boolean var7;
      try {
         conn = getConnection(this.dataSource, this.jdbcProps);
         stmt = conn.prepareStatement(this.jdbcCtx.getSelectLATQuery());
         stmt.setString(1, this.id);
         stmt.setString(2, this.contextName);
         stmt.setString(3, this.fullCtxName);
         synchronized(this.getInternalLock()) {
            rs = stmt.executeQuery();
            if (rs.next()) {
               this.dbLAT = Math.max(this.dbLAT, rs.getLong(1));
               return lat < this.dbLAT;
            }

            var7 = false;
         }
      } catch (SQLException var14) {
         HTTPSessionLogger.logUnexpectedError(this.logContext, var14);
         var7 = true;
         return var7;
      } finally {
         closeDBResources(rs, stmt, conn);
      }

      return var7;
   }

   protected void dbRemove() {
      if (this.isDebugEnabled()) {
         Loggable l = HTTPSessionLogger.logPerformOperationLoggable("dbRemove()", this.id, this.getContextPath());
         DEBUG_SESSIONS.debug(l.getMessage());
      }

      Connection conn = null;
      PreparedStatement stmt = null;

      try {
         conn = getConnection(this.dataSource, this.jdbcProps);
         stmt = conn.prepareStatement(this.jdbcCtx.getDeleteQuery());
         this.addRemoveStatement(stmt);
         stmt.executeUpdate();
      } catch (SQLException var7) {
         HTTPSessionLogger.logUnexpectedError(this.logContext, var7);
      } finally {
         closeDBResources((ResultSet)null, stmt, conn);
      }

   }

   protected void addRemoveStatement(PreparedStatement stmt) throws SQLException {
      if (this.needToSyncNewSessionId) {
         stmt.setString(1, this.oldId);
      } else {
         stmt.setString(1, this.id);
      }

      stmt.setString(2, this.contextName);
      stmt.setString(3, this.fullCtxName);
   }

   static int getTotalSessionsCount(DataSource dataSource, Properties jdbcProps, WebAppServletContext context, String statement) {
      Connection conn = null;
      PreparedStatement stmt = null;
      ResultSet rs = null;

      try {
         conn = getConnection(dataSource, jdbcProps);
         stmt = conn.prepareStatement(statement);
         stmt.setString(1, context.getName());
         stmt.setString(2, context.getFullCtxName());
         rs = stmt.executeQuery();
         int count = 0;
         if (rs.next()) {
            count = rs.getInt(1);
         }

         int var8 = count;
         return var8;
      } catch (SQLException var12) {
         HTTPSessionLogger.logUnexpectedError(context.getLogContext(), var12);
      } finally {
         closeDBResources(rs, stmt, conn);
      }

      return 0;
   }

   private byte[] serializeAttributes() throws IOException {
      byte[] serBytes = null;
      UnsyncByteArrayOutputStream baos = new UnsyncByteArrayOutputStream();
      WLObjectOutputStream oos = new WLObjectOutputStream(baos);
      oos.setReplacer(RemoteObjectReplacer.getReplacer());
      oos.setServerChannel(ServerChannelManager.findDefaultLocalServerChannel());
      oos.writeObject(convertToHashtable(this.attributes));
      oos.writeObject(convertToHashtable(this.internalAttributes));
      oos.flush();
      byte[] serBytes = baos.toRawBytes();
      return serBytes;
   }

   private void deSerializeAttributes(byte[] serBytes, JDBCSessionData sess) throws IOException {
      if (serBytes != null && serBytes.length >= 2) {
         try {
            UnsyncByteArrayInputStream bais = new UnsyncByteArrayInputStream(serBytes);
            if (serBytes[0] == -84 && serBytes[1] == -19) {
               SessionMigrationObjectInputStream ois = new SessionMigrationObjectInputStream(bais);
               sess.attributes = convertToConcurrentHashMap(ois.readObject());

               try {
                  sess.internalAttributes = convertToConcurrentHashMap(ois.readObject());
               } catch (StreamCorruptedException var7) {
                  this.internalAttributes = null;
                  if (this.isDebugEnabled()) {
                     DEBUG_SESSIONS.debug("Ignoring the StreamCorruptedException " + var7.getMessage());
                  }
               }
            } else {
               WLObjectInputStream wlis = new WLObjectInputStream(bais);
               wlis.setReplacer(RemoteObjectReplacer.getReplacer());
               sess.attributes = convertToConcurrentHashMap(wlis.readObject());

               try {
                  sess.internalAttributes = convertToConcurrentHashMap(wlis.readObject());
               } catch (StreamCorruptedException var6) {
                  this.internalAttributes = null;
                  if (this.isDebugEnabled()) {
                     DEBUG_SESSIONS.debug("Ignoring the StreamCorruptedException " + var6.getMessage());
                  }
               }
            }

         } catch (ClassNotFoundException var8) {
            throw new IOException("Exception deserializing attributes:" + StackTraceUtils.throwable2StackTrace(var8));
         }
      }
   }

   void setTriggerLAT(long l) {
      this.triggerLAT = l;
   }

   void remove() {
      this.dbRemove();
      super.remove();
   }

   void syncSession() {
      if (this.isValid()) {
         try {
            super.syncSession();
            this.dbUpdate();
         } catch (SQLException var5) {
            HTTPSessionLogger.logUnexpectedError(this.logContext, var5);
         } finally {
            this.postInvalidate();
         }

      }
   }

   public void setAttribute(String name, Object value) throws IllegalStateException, IllegalArgumentException {
      synchronized(this.getInternalLock()) {
         super.setAttribute(name, value);
         this.setModified(true);
      }
   }

   protected void removeAttribute(String name, boolean isChange) throws IllegalStateException {
      super.removeAttribute(name, isChange);
      this.setModified(true);
   }

   public void setInternalAttribute(String name, Object value) throws IllegalStateException, IllegalArgumentException {
      synchronized(this.getInternalLock()) {
         super.setInternalAttribute(name, value);
         this.setModified(true);
      }
   }

   public void removeInternalAttribute(String name) throws IllegalStateException {
      super.removeInternalAttribute(name);
      this.setModified(true);
   }

   protected void logTransientAttributeError(String name) {
      HTTPSessionLogger.logTransientJDBCAttributeError(this.logContext, name, this.getId());
   }

   private static void closeDBResources(ResultSet rs, PreparedStatement stmt, Connection conn) {
      try {
         if (rs != null) {
            rs.close();
         }
      } catch (Exception var6) {
      }

      try {
         if (stmt != null) {
            stmt.close();
         }
      } catch (Exception var5) {
      }

      try {
         if (conn != null) {
            conn.close();
         }
      } catch (Exception var4) {
      }

   }

   private static class SessionMigrationObjectInputStream extends ObjectInputStream {
      public SessionMigrationObjectInputStream() throws IOException, SecurityException {
      }

      public SessionMigrationObjectInputStream(UnsyncByteArrayInputStream in) throws IOException, StreamCorruptedException {
         super(in);
      }

      private ClassLoaderService getClassLoaderService() {
         ClassLoaderService cls = (ClassLoaderService)GlobalServiceLocator.getServiceLocator().getService(ClassLoaderService.class, "Application", new Annotation[0]);
         if (cls == null) {
            throw new RuntimeException("Implementation of weblogic.common.internal.ClassLoaderService with name of Application not found on classpath");
         } else {
            return cls;
         }
      }

      public Class resolveClass(ObjectStreamClass descriptor) throws ClassNotFoundException {
         return this.getClassLoaderService().loadClass(descriptor.getName(), (String)null);
      }
   }
}
