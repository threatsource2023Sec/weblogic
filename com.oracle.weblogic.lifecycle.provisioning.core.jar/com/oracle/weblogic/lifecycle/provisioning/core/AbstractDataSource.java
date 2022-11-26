package com.oracle.weblogic.lifecycle.provisioning.core;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;
import javax.sql.DataSource;

public abstract class AbstractDataSource implements DataSource {
   protected AbstractDataSource() {
   }

   public int getLoginTimeout() {
      return 0;
   }

   public void setLoginTimeout(int timeout) throws SQLException {
      throw new SQLFeatureNotSupportedException();
   }

   public PrintWriter getLogWriter() {
      return null;
   }

   public void setLogWriter(PrintWriter writer) throws SQLException {
      throw new SQLFeatureNotSupportedException();
   }

   public Logger getParentLogger() throws SQLFeatureNotSupportedException {
      throw new SQLFeatureNotSupportedException();
   }

   public boolean isWrapperFor(Class cls) {
      return cls != null && cls.isInstance(this);
   }

   public Object unwrap(Class cls) throws SQLException {
      if (cls != null && this.isWrapperFor(cls)) {
         if (!cls.isInstance(this)) {
            throw new SQLException("!cls.isInstance(this): " + cls + ", " + this);
         } else {
            return cls.cast(this);
         }
      } else {
         throw new SQLException("Not a wrapper for " + cls);
      }
   }
}
