package weblogic.servlet.internal.session;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import javax.sql.DataSource;
import weblogic.utils.StackTraceUtils;

public class AsyncJDBCSessionData extends JDBCSessionData {
   private static final long serialVersionUID = 7506555655775346941L;
   public static final int SHOULD_BATCH = 0;
   public static final int CREATE = 1;
   public static final int UPDATE = 2;
   public static final int REMOVE = 3;
   private AsyncJDBCSessionContext jdbcCtx;
   private boolean blockingFlushCall = false;
   private boolean shouldBatch = false;
   private boolean shouldCreate = false;
   private int state;

   public AsyncJDBCSessionData(String id, SessionContext context, DataSource dataSource, Properties jdbcProps, boolean isNew) {
      super(id, context, dataSource, jdbcProps, isNew);
      this.jdbcCtx = (AsyncJDBCSessionContext)context;
      this.state = 0;
      this.shouldBatch = true;
   }

   static JDBCSessionData newSession(String sessionId, SessionContext context, DataSource dataSource, Properties jdbcProps) {
      AsyncJDBCSessionData ses = new AsyncJDBCSessionData(sessionId, context, dataSource, jdbcProps, true);
      ses.shouldCreate = true;
      context.getServletContext().getEventsManager().notifySessionLifetimeEvent(ses, true);
      return ses;
   }

   public String changeSessionId(String newId) {
      synchronized(this.getInternalLock()) {
         return super.changeSessionId(newId);
      }
   }

   void syncSession() {
      try {
         super.syncSession();
         if (this.blockingFlushCall) {
            this.jdbcCtx.getPersistenceManager().blockingFlush();
            this.blockingFlushCall = false;
         }
      } finally {
         this.postInvalidate();
      }

   }

   public int getState() {
      return this.state;
   }

   protected void dbCreate() {
      synchronized(this.getInternalLock()) {
         this.state = 1;
         this.jdbcCtx.getPersistenceManager().update(this);
         this.shouldBatch = false;
      }
   }

   protected void dbUpdate() {
      synchronized(this.getInternalLock()) {
         if (this.shouldBatch) {
            if (this.shouldCreate) {
               this.shouldCreate = false;
               this.state = 1;
            } else {
               this.state = 2;
            }

            this.jdbcCtx.getPersistenceManager().update(this);
            this.shouldBatch = false;
         }

      }
   }

   protected void dbRemove() {
      synchronized(this.getInternalLock()) {
         this.state = 3;
         if (this.shouldBatch) {
            this.shouldBatch = false;
            this.jdbcCtx.getPersistenceManager().update(this);
         }

      }
   }

   private void commit() {
      this.state = 0;
      this.shouldBatch = true;
   }

   public void addStatements(PreparedStatement stmt) throws SQLException {
      synchronized(this.getInternalLock()) {
         if (this.state == 0) {
            throw new AssertionError("This should never happen, and state is " + this.state);
         } else {
            if (this.state == 1) {
               this.addCreateStatement(stmt);
            } else if (this.state == 2) {
               try {
                  this.addFirstUpdateStatement(stmt);
               } catch (IOException var5) {
                  throw new SQLException("Could not serialize attributes to update session " + this.id + ":\n" + StackTraceUtils.throwable2StackTrace(var5));
               }

               this.addTimeSensitiveUpdateStatement(stmt);
            } else if (this.state == 3) {
               this.addRemoveStatement(stmt);
            }

            if (this.dbLAT < this.accessTime) {
               this.dbLAT = this.accessTime;
            }

            this.commit();
         }
      }
   }

   public void addUpdateIdStatements(PreparedStatement stmt) throws SQLException {
      synchronized(this.getInternalLock()) {
         this.addUpdateIdStatement(stmt);
      }
   }

   public void removeInternalAttribute(String name) throws IllegalStateException {
      super.removeInternalAttribute(name);
      if (name.equals("weblogic.authuser")) {
         this.blockingFlushCall = true;
      }

   }
}
