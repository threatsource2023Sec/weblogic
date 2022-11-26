package com.oracle.weblogic.lifecycle.provisioning.core;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DriverManagerDataSource extends AbstractDataSource {
   private final String url;
   private final Properties properties;

   public DriverManagerDataSource(String url) {
      this(url, (String)null, (String)null);
   }

   public DriverManagerDataSource(String url, String user) {
      this(url, user, (String)null);
   }

   public DriverManagerDataSource(String url, Properties properties) {
      if (url == null) {
         throw new IllegalArgumentException("url", new NullPointerException("url == null"));
      } else {
         this.url = url;
         this.properties = properties;
      }
   }

   public DriverManagerDataSource(String url, String user, String password) {
      if (url == null) {
         throw new IllegalArgumentException("url", new NullPointerException("url == null"));
      } else {
         Properties properties = this.createDefaultProperties();
         if (properties == null) {
            throw new IllegalStateException("createDefaultProperties() == null");
         } else {
            if (user != null) {
               properties.setProperty("user", user);
            }

            if (password != null) {
               properties.setProperty("password", password);
            }

            this.url = url;
            this.properties = properties;
         }
      }
   }

   protected Properties createDefaultProperties() {
      return new Properties();
   }

   public final int getLoginTimeout() {
      return DriverManager.getLoginTimeout();
   }

   public final void setLoginTimeout(int timeout) throws SQLException {
      throw new SQLFeatureNotSupportedException();
   }

   public final PrintWriter getLogWriter() {
      return DriverManager.getLogWriter();
   }

   public final void setLogWriter(PrintWriter writer) throws SQLException {
      throw new SQLFeatureNotSupportedException();
   }

   public final Connection getConnection() throws SQLException {
      String className = DriverManagerDataSource.class.getName();
      String methodName = "getConnection";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "getConnection");
      }

      Connection connection;
      if (this.properties != null) {
         if (logger != null && logger.isLoggable(Level.FINER)) {
            logger.logp(Level.FINER, className, "getConnection", "User: {0}", this.properties.getProperty("user"));
            logger.logp(Level.FINER, className, "getConnection", "Password: {0}", this.properties.getProperty("password"));
            logger.logp(Level.FINER, className, "getConnection", "URL: {0}", this.url);
         }

         connection = DriverManager.getConnection(this.url, this.properties);
      } else {
         connection = DriverManager.getConnection(this.url);
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "getConnection", connection);
      }

      return connection;
   }

   public final Connection getConnection(String user, String password) throws SQLException {
      Properties properties;
      if (this.properties == null) {
         properties = this.createDefaultProperties();
         if (properties == null) {
            throw new IllegalStateException("createDefaultProperties() == null");
         }
      } else {
         properties = new Properties(this.properties);
      }

      if (user != null) {
         properties.put("user", user);
      }

      if (password != null) {
         properties.put("password", password);
      }

      return DriverManager.getConnection(this.url, properties);
   }
}
