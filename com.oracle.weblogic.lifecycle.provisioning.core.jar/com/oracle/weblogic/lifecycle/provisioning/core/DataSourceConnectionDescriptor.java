package com.oracle.weblogic.lifecycle.provisioning.core;

import com.oracle.weblogic.lifecycle.provisioning.api.ConnectionDescriptor;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Objects;
import java.util.logging.Logger;
import javax.sql.DataSource;

public class DataSourceConnectionDescriptor extends ConnectionDescriptor {
   private static final long serialVersionUID = 1L;
   private DataSource dataSource;

   public DataSourceConnectionDescriptor() {
   }

   public DataSourceConnectionDescriptor(DataSource delegate) {
      Objects.requireNonNull(delegate);
      this.setDataSource(delegate);
   }

   public final DataSource getDataSource() {
      if (this.dataSource == null) {
         throw new IllegalStateException("this.dataSource == null");
      } else {
         return this.dataSource;
      }
   }

   public final void setDataSource(DataSource dataSource) {
      Objects.requireNonNull(dataSource);
      this.dataSource = dataSource;
   }

   public Connection getConnection() throws SQLException {
      DataSource dataSource = this.getDataSource();
      if (dataSource == null) {
         throw new SQLException(new IllegalStateException("this.getDataSource() == null"));
      } else {
         return dataSource.getConnection();
      }
   }

   public Connection getConnection(String user, String password) throws SQLException {
      DataSource dataSource = this.getDataSource();
      if (dataSource == null) {
         throw new SQLException(new IllegalStateException("this.getDataSource() == null"));
      } else {
         return dataSource.getConnection(user, password);
      }
   }

   public Logger getParentLogger() throws SQLFeatureNotSupportedException {
      DataSource dataSource = this.getDataSource();
      if (dataSource == null) {
         throw new SQLFeatureNotSupportedException(new IllegalStateException("this.getDataSource() == null"));
      } else {
         return dataSource.getParentLogger();
      }
   }

   public int getLoginTimeout() throws SQLException {
      DataSource dataSource = this.getDataSource();
      if (dataSource == null) {
         throw new SQLException(new IllegalStateException("this.getDataSource() == null"));
      } else {
         return dataSource.getLoginTimeout();
      }
   }

   public void setLoginTimeout(int loginTimeout) throws SQLException {
      DataSource dataSource = this.getDataSource();
      if (dataSource == null) {
         throw new SQLException(new IllegalStateException("this.getDataSource() == null"));
      } else {
         dataSource.setLoginTimeout(loginTimeout);
      }
   }

   public PrintWriter getLogWriter() throws SQLException {
      DataSource dataSource = this.getDataSource();
      if (dataSource == null) {
         throw new SQLException(new IllegalStateException("this.getDataSource() == null"));
      } else {
         return dataSource.getLogWriter();
      }
   }

   public void setLogWriter(PrintWriter logWriter) throws SQLException {
      DataSource dataSource = this.getDataSource();
      if (dataSource == null) {
         throw new SQLException(new IllegalStateException("this.getDataSource() == null"));
      } else {
         dataSource.setLogWriter(logWriter);
      }
   }

   public boolean isWrapperFor(Class c) throws SQLException {
      DataSource dataSource = this.getDataSource();
      if (dataSource == null) {
         throw new SQLException(new IllegalStateException("this.getDataSource() == null"));
      } else {
         return dataSource.isWrapperFor(c);
      }
   }

   public Object unwrap(Class c) throws SQLException {
      DataSource dataSource = this.getDataSource();
      if (dataSource == null) {
         throw new SQLException(new IllegalStateException("this.getDataSource() == null"));
      } else {
         return dataSource.unwrap(c);
      }
   }
}
