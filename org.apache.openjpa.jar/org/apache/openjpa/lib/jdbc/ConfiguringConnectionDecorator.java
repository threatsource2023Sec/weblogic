package org.apache.openjpa.lib.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ConfiguringConnectionDecorator implements ConnectionDecorator {
   private int _isolation = -1;
   private int _queryTimeout = -1;
   private Boolean _autoCommit = null;

   public int getQueryTimeout() {
      return this._queryTimeout;
   }

   public void setQueryTimeout(int timeout) {
      this._queryTimeout = timeout;
   }

   public int getTransactionIsolation() {
      return this._isolation;
   }

   public void setTransactionIsolation(int isolation) {
      this._isolation = isolation;
   }

   public Boolean getAutoCommit() {
      return this._autoCommit;
   }

   public void setAutoCommit(Boolean autoCommit) {
      this._autoCommit = autoCommit;
   }

   public Connection decorate(Connection conn) throws SQLException {
      if (this._isolation == 0 || this._queryTimeout != -1 || this._autoCommit != null) {
         conn = new ConfiguringConnection((Connection)conn);
      }

      if (this._isolation != -1 && this._isolation != 0) {
         ((Connection)conn).setTransactionIsolation(this._isolation);
      }

      return (Connection)conn;
   }

   private class ConfiguringConnection extends DelegatingConnection {
      private boolean _curAutoCommit = false;

      public ConfiguringConnection(Connection conn) throws SQLException {
         super(conn);
         if (ConfiguringConnectionDecorator.this._autoCommit != null) {
            this._curAutoCommit = this.getAutoCommit();
            if (this._curAutoCommit != ConfiguringConnectionDecorator.this._autoCommit) {
               this.setAutoCommit(ConfiguringConnectionDecorator.this._autoCommit);
            }
         }

      }

      public void setAutoCommit(boolean auto) throws SQLException {
         if (ConfiguringConnectionDecorator.this._isolation != 0) {
            super.setAutoCommit(auto);
            this._curAutoCommit = auto;
         }

      }

      public void commit() throws SQLException {
         if (ConfiguringConnectionDecorator.this._isolation != 0) {
            super.commit();
         }

         if (ConfiguringConnectionDecorator.this._autoCommit != null && ConfiguringConnectionDecorator.this._autoCommit != this._curAutoCommit) {
            this.setAutoCommit(ConfiguringConnectionDecorator.this._autoCommit);
         }

      }

      public void rollback() throws SQLException {
         if (ConfiguringConnectionDecorator.this._isolation != 0) {
            super.rollback();
         }

         if (ConfiguringConnectionDecorator.this._autoCommit != null && ConfiguringConnectionDecorator.this._autoCommit != this._curAutoCommit) {
            this.setAutoCommit(ConfiguringConnectionDecorator.this._autoCommit);
         }

      }

      protected PreparedStatement prepareStatement(String sql, boolean wrap) throws SQLException {
         PreparedStatement stmnt = super.prepareStatement(sql, wrap);
         if (ConfiguringConnectionDecorator.this._queryTimeout != -1) {
            stmnt.setQueryTimeout(ConfiguringConnectionDecorator.this._queryTimeout);
         }

         return stmnt;
      }

      protected PreparedStatement prepareStatement(String sql, int rsType, int rsConcur, boolean wrap) throws SQLException {
         PreparedStatement stmnt = super.prepareStatement(sql, rsType, rsConcur, wrap);
         if (ConfiguringConnectionDecorator.this._queryTimeout != -1) {
            stmnt.setQueryTimeout(ConfiguringConnectionDecorator.this._queryTimeout);
         }

         return stmnt;
      }

      protected Statement createStatement(boolean wrap) throws SQLException {
         Statement stmnt = super.createStatement(wrap);
         if (ConfiguringConnectionDecorator.this._queryTimeout != -1) {
            stmnt.setQueryTimeout(ConfiguringConnectionDecorator.this._queryTimeout);
         }

         return stmnt;
      }

      protected Statement createStatement(int rsType, int rsConcur, boolean wrap) throws SQLException {
         Statement stmnt = super.createStatement(rsType, rsConcur, wrap);
         if (ConfiguringConnectionDecorator.this._queryTimeout != -1) {
            stmnt.setQueryTimeout(ConfiguringConnectionDecorator.this._queryTimeout);
         }

         return stmnt;
      }
   }
}
