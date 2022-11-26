package weblogic.transaction.internal;

import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class PeerSiteRecoveryJDBCWrappers {
   static DataSource testDataSource;

   DataSource getDataSource(String lookupString) throws PeerRecoveryException {
      try {
         return (DataSource)(testDataSource != null ? testDataSource : new JDBCDataSource((javax.sql.DataSource)(new InitialContext()).lookup(lookupString)));
      } catch (NamingException var3) {
         throw new PeerRecoveryException(var3);
      }
   }

   Date getDate(Object obj) throws PeerRecoveryException {
      if (obj instanceof Date) {
         return (Date)obj;
      } else if (obj == null) {
         return null;
      } else if (obj instanceof java.util.Date) {
         return new Date(((java.util.Date)obj).getTime());
      } else {
         try {
            java.util.Date d = DateFormat.getDateInstance().parse(obj.toString());
            return new Date(d.getTime());
         } catch (Exception var3) {
            throw new PeerRecoveryException(var3);
         }
      }
   }

   public class PeerRecoveryException extends Exception {
      public PeerRecoveryException(Exception ex) {
         super(ex);
      }
   }

   class JDBCResultSet implements ResultSet {
      java.sql.ResultSet resultSet;

      public JDBCResultSet(java.sql.ResultSet resultSet) {
         this.resultSet = resultSet;
      }

      public boolean next() throws PeerRecoveryException {
         try {
            return this.resultSet.next();
         } catch (SQLException var2) {
            throw PeerSiteRecoveryJDBCWrappers.this.new PeerRecoveryException(var2);
         }
      }

      public String getString(String server) throws PeerRecoveryException {
         try {
            return this.resultSet.getString(server);
         } catch (SQLException var3) {
            throw PeerSiteRecoveryJDBCWrappers.this.new PeerRecoveryException(var3);
         }
      }

      public Object getObject(String timeout) throws PeerRecoveryException {
         try {
            return this.resultSet.getObject(timeout);
         } catch (SQLException var3) {
            throw PeerSiteRecoveryJDBCWrappers.this.new PeerRecoveryException(var3);
         }
      }

      public void beforeFirst() throws PeerRecoveryException {
         try {
            this.resultSet.beforeFirst();
         } catch (SQLException var2) {
            throw PeerSiteRecoveryJDBCWrappers.this.new PeerRecoveryException(var2);
         }
      }

      public Object getObject(int i) throws PeerRecoveryException {
         try {
            return this.resultSet.getObject(i);
         } catch (SQLException var3) {
            throw PeerSiteRecoveryJDBCWrappers.this.new PeerRecoveryException(var3);
         }
      }
   }

   class JDBCPreparedStatement implements PreparedStatement {
      java.sql.PreparedStatement preparedStatement;

      public JDBCPreparedStatement(java.sql.PreparedStatement preparedStatement) {
         this.preparedStatement = preparedStatement;
      }

      public void execute() throws PeerRecoveryException {
         try {
            this.preparedStatement.execute();
         } catch (SQLException var2) {
            throw PeerSiteRecoveryJDBCWrappers.this.new PeerRecoveryException(var2);
         }
      }

      public ResultSet executeQuery() throws PeerRecoveryException {
         try {
            return PeerSiteRecoveryJDBCWrappers.this.new JDBCResultSet(this.preparedStatement.executeQuery());
         } catch (SQLException var2) {
            throw PeerSiteRecoveryJDBCWrappers.this.new PeerRecoveryException(var2);
         }
      }

      public void setString(int i, String recoverySiteName) throws PeerRecoveryException {
         try {
            this.preparedStatement.setString(i, recoverySiteName);
         } catch (SQLException var4) {
            throw PeerSiteRecoveryJDBCWrappers.this.new PeerRecoveryException(var4);
         }
      }

      public void setDate(int i, java.util.Date currentDate) throws PeerRecoveryException {
         try {
            this.preparedStatement.setDate(i, (Date)currentDate);
         } catch (SQLException var4) {
            throw PeerSiteRecoveryJDBCWrappers.this.new PeerRecoveryException(var4);
         }
      }
   }

   class JDBCConnection implements Connection {
      java.sql.Connection connection;

      JDBCConnection(java.sql.Connection connection) {
         this.connection = connection;
      }

      public void commit() throws PeerRecoveryException {
         try {
            this.connection.commit();
         } catch (SQLException var2) {
            throw PeerSiteRecoveryJDBCWrappers.this.new PeerRecoveryException(var2);
         }
      }

      public void rollback() throws PeerRecoveryException {
         try {
            this.connection.rollback();
         } catch (SQLException var2) {
            throw PeerSiteRecoveryJDBCWrappers.this.new PeerRecoveryException(var2);
         }
      }

      public PreparedStatement prepareStatementScrollInsensitive(String selectAllForClusterDomainSiteSql) throws PeerRecoveryException {
         try {
            return PeerSiteRecoveryJDBCWrappers.this.new JDBCPreparedStatement(this.connection.prepareStatement(selectAllForClusterDomainSiteSql, 1004, 1007));
         } catch (SQLException var3) {
            throw PeerSiteRecoveryJDBCWrappers.this.new PeerRecoveryException(var3);
         }
      }

      public PreparedStatement prepareStatement(String selectAllForClusterDomainSiteSql) throws PeerRecoveryException {
         try {
            return PeerSiteRecoveryJDBCWrappers.this.new JDBCPreparedStatement(this.connection.prepareStatement(selectAllForClusterDomainSiteSql));
         } catch (SQLException var3) {
            throw PeerSiteRecoveryJDBCWrappers.this.new PeerRecoveryException(var3);
         }
      }

      public void setAutoCommit(boolean b) throws PeerRecoveryException {
         try {
            this.connection.setAutoCommit(b);
         } catch (SQLException var3) {
            throw PeerSiteRecoveryJDBCWrappers.this.new PeerRecoveryException(var3);
         }
      }
   }

   class JDBCDataSource implements DataSource {
      javax.sql.DataSource datasource;

      JDBCDataSource(javax.sql.DataSource datasource) {
         this.datasource = datasource;
      }

      public Connection getConnection() throws PeerRecoveryException {
         try {
            return PeerSiteRecoveryJDBCWrappers.this.new JDBCConnection(this.datasource.getConnection());
         } catch (SQLException var2) {
            throw PeerSiteRecoveryJDBCWrappers.this.new PeerRecoveryException(var2);
         }
      }
   }

   interface ResultSet {
      boolean next() throws PeerRecoveryException;

      String getString(String var1) throws PeerRecoveryException;

      Object getObject(String var1) throws PeerRecoveryException;

      void beforeFirst() throws PeerRecoveryException;

      Object getObject(int var1) throws PeerRecoveryException;
   }

   interface PreparedStatement {
      void execute() throws PeerRecoveryException;

      ResultSet executeQuery() throws PeerRecoveryException;

      void setString(int var1, String var2) throws PeerRecoveryException;

      void setDate(int var1, java.util.Date var2) throws PeerRecoveryException;
   }

   interface Connection {
      void commit() throws PeerRecoveryException;

      void rollback() throws PeerRecoveryException;

      PreparedStatement prepareStatementScrollInsensitive(String var1) throws PeerRecoveryException;

      PreparedStatement prepareStatement(String var1) throws PeerRecoveryException;

      void setAutoCommit(boolean var1) throws PeerRecoveryException;
   }

   interface DataSource {
      Connection getConnection() throws PeerRecoveryException;
   }
}
