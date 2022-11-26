package org.apache.openjpa.lib.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.openjpa.lib.util.concurrent.AbstractConcurrentEventManager;

public class JDBCEventConnectionDecorator extends AbstractConcurrentEventManager implements ConnectionDecorator {
   public Connection decorate(Connection conn) {
      return (Connection)(!this.hasListeners() ? conn : new EventConnection(conn));
   }

   private JDBCEvent fireEvent(Connection source, short type, JDBCEvent associatedEvent, Statement stmnt, String sql) {
      if (!this.hasListeners()) {
         return null;
      } else {
         JDBCEvent event = new JDBCEvent(source, type, associatedEvent, stmnt, sql);
         this.fireEvent(event);
         return event;
      }
   }

   protected void fireEvent(Object event, Object listener) {
      JDBCListener listen = (JDBCListener)listener;
      JDBCEvent ev = (JDBCEvent)event;
      switch (ev.getType()) {
         case 1:
            listen.beforePrepareStatement(ev);
            break;
         case 2:
            listen.afterPrepareStatement(ev);
            break;
         case 3:
            listen.beforeCreateStatement(ev);
            break;
         case 4:
            listen.afterCreateStatement(ev);
            break;
         case 5:
            listen.beforeExecuteStatement(ev);
            break;
         case 6:
            listen.afterExecuteStatement(ev);
            break;
         case 7:
            listen.beforeCommit(ev);
            break;
         case 8:
            listen.afterCommit(ev);
            break;
         case 9:
            listen.beforeRollback(ev);
            break;
         case 10:
            listen.afterRollback(ev);
            break;
         case 11:
            listen.afterConnect(ev);
            break;
         case 12:
            listen.beforeClose(ev);
      }

   }

   private class EventStatement extends DelegatingStatement {
      private final EventConnection _conn;

      public EventStatement(Statement stmnt, EventConnection conn) {
         super(stmnt, conn);
         this._conn = conn;
      }

      public int executeUpdate(String sql) throws SQLException {
         JDBCEvent before = JDBCEventConnectionDecorator.this.fireEvent(this._conn.getDelegate(), (short)5, (JDBCEvent)null, this.getDelegate(), sql);

         int var3;
         try {
            var3 = super.executeUpdate(sql);
         } finally {
            JDBCEventConnectionDecorator.this.fireEvent(this._conn.getDelegate(), (short)6, before, this.getDelegate(), sql);
         }

         return var3;
      }

      protected ResultSet executeQuery(String sql, boolean wrap) throws SQLException {
         JDBCEvent before = JDBCEventConnectionDecorator.this.fireEvent(this._conn.getDelegate(), (short)5, (JDBCEvent)null, this.getDelegate(), sql);

         ResultSet var4;
         try {
            var4 = super.executeQuery(sql, wrap);
         } finally {
            JDBCEventConnectionDecorator.this.fireEvent(this._conn.getDelegate(), (short)6, before, this.getDelegate(), sql);
         }

         return var4;
      }
   }

   private class EventPreparedStatement extends DelegatingPreparedStatement {
      private final EventConnection _conn;
      private final String _sql;

      public EventPreparedStatement(PreparedStatement ps, EventConnection conn, String sql) {
         super(ps, conn);
         this._conn = conn;
         this._sql = sql;
      }

      public int executeUpdate() throws SQLException {
         JDBCEvent before = JDBCEventConnectionDecorator.this.fireEvent(this._conn.getDelegate(), (short)5, (JDBCEvent)null, this.getDelegate(), this._sql);

         int var2;
         try {
            var2 = super.executeUpdate();
         } finally {
            JDBCEventConnectionDecorator.this.fireEvent(this._conn.getDelegate(), (short)6, before, this.getDelegate(), this._sql);
         }

         return var2;
      }

      protected ResultSet executeQuery(boolean wrap) throws SQLException {
         JDBCEvent before = JDBCEventConnectionDecorator.this.fireEvent(this._conn.getDelegate(), (short)5, (JDBCEvent)null, this.getDelegate(), this._sql);

         ResultSet var3;
         try {
            var3 = super.executeQuery(wrap);
         } finally {
            JDBCEventConnectionDecorator.this.fireEvent(this._conn.getDelegate(), (short)6, before, this.getDelegate(), this._sql);
         }

         return var3;
      }

      public int[] executeBatch() throws SQLException {
         JDBCEvent before = JDBCEventConnectionDecorator.this.fireEvent(this._conn.getDelegate(), (short)5, (JDBCEvent)null, this.getDelegate(), this._sql);

         int[] var2;
         try {
            var2 = super.executeBatch();
         } finally {
            JDBCEventConnectionDecorator.this.fireEvent(this._conn.getDelegate(), (short)6, before, this.getDelegate(), this._sql);
         }

         return var2;
      }
   }

   private class EventConnection extends DelegatingConnection {
      public EventConnection(Connection conn) {
         super(conn);
         JDBCEventConnectionDecorator.this.fireEvent(this.getDelegate(), (short)11, (JDBCEvent)null, (Statement)null, (String)null);
      }

      public void commit() throws SQLException {
         JDBCEvent before = JDBCEventConnectionDecorator.this.fireEvent(this.getDelegate(), (short)7, (JDBCEvent)null, (Statement)null, (String)null);

         try {
            super.commit();
         } finally {
            JDBCEventConnectionDecorator.this.fireEvent(this.getDelegate(), (short)8, before, (Statement)null, (String)null);
         }

      }

      public void rollback() throws SQLException {
         JDBCEvent before = JDBCEventConnectionDecorator.this.fireEvent(this.getDelegate(), (short)9, (JDBCEvent)null, (Statement)null, (String)null);

         try {
            super.rollback();
         } finally {
            JDBCEventConnectionDecorator.this.fireEvent(this.getDelegate(), (short)10, before, (Statement)null, (String)null);
         }

      }

      protected Statement createStatement(boolean wrap) throws SQLException {
         JDBCEvent before = JDBCEventConnectionDecorator.this.fireEvent(this.getDelegate(), (short)3, (JDBCEvent)null, (Statement)null, (String)null);
         Statement stmnt = null;

         try {
            stmnt = JDBCEventConnectionDecorator.this.new EventStatement(super.createStatement(false), this);
         } finally {
            JDBCEventConnectionDecorator.this.fireEvent(this.getDelegate(), (short)4, before, stmnt, (String)null);
         }

         return stmnt;
      }

      protected Statement createStatement(int rsType, int rsConcur, boolean wrap) throws SQLException {
         JDBCEvent before = JDBCEventConnectionDecorator.this.fireEvent(this.getDelegate(), (short)3, (JDBCEvent)null, (Statement)null, (String)null);
         Statement stmnt = null;

         try {
            stmnt = JDBCEventConnectionDecorator.this.new EventStatement(super.createStatement(rsType, rsConcur, false), this);
         } finally {
            JDBCEventConnectionDecorator.this.fireEvent(this.getDelegate(), (short)4, before, stmnt, (String)null);
         }

         return stmnt;
      }

      protected PreparedStatement prepareStatement(String sql, boolean wrap) throws SQLException {
         JDBCEvent before = JDBCEventConnectionDecorator.this.fireEvent(this.getDelegate(), (short)1, (JDBCEvent)null, (Statement)null, sql);
         PreparedStatement stmnt = null;

         try {
            stmnt = JDBCEventConnectionDecorator.this.new EventPreparedStatement(super.prepareStatement(sql, false), this, sql);
         } finally {
            JDBCEventConnectionDecorator.this.fireEvent(this.getDelegate(), (short)2, before, stmnt, sql);
         }

         return stmnt;
      }

      protected PreparedStatement prepareStatement(String sql, int rsType, int rsConcur, boolean wrap) throws SQLException {
         JDBCEvent before = JDBCEventConnectionDecorator.this.fireEvent(this.getDelegate(), (short)1, (JDBCEvent)null, (Statement)null, sql);
         PreparedStatement stmnt = null;

         try {
            stmnt = JDBCEventConnectionDecorator.this.new EventPreparedStatement(super.prepareStatement(sql, rsType, rsConcur, false), this, sql);
         } finally {
            JDBCEventConnectionDecorator.this.fireEvent(this.getDelegate(), (short)2, before, stmnt, sql);
         }

         return stmnt;
      }

      public void close() throws SQLException {
         try {
            JDBCEventConnectionDecorator.this.fireEvent(this.getDelegate(), (short)12, (JDBCEvent)null, (Statement)null, (String)null);
         } finally {
            super.close();
         }

      }
   }
}
