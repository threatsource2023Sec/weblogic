package com.oracle.weblogic.lifecycle.provisioning.core;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class JNDIDataSource extends AbstractDataSource {
   private String jndiName;
   private Context context;

   public JNDIDataSource() {
   }

   public JNDIDataSource(Context context, String jndiName) {
      this();
      this.setContext(context);
      this.setJNDIName(jndiName);
   }

   public String getJNDIName() {
      return this.jndiName;
   }

   public void setJNDIName(String jndiName) {
      Objects.requireNonNull(jndiName);
      this.jndiName = jndiName;
   }

   public Context getContext() {
      return this.context;
   }

   public void setContext(Context context) {
      this.context = context;
   }

   public Connection getConnection() throws SQLException {
      return this.getDataSource().getConnection();
   }

   public Connection getConnection(String user, String password) throws SQLException {
      return this.getDataSource().getConnection(user, password);
   }

   protected DataSource getDataSource() throws SQLException {
      String className = JNDIDataSource.class.getName();
      String methodName = "getDataSource";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "getDataSource");
      }

      Context context = this.getContext();
      if (context == null) {
         try {
            context = new InitialContext();
         } catch (NamingException var12) {
            throw new SQLException(var12);
         }
      }

      String jndiName = this.getJNDIName();
      if (jndiName == null) {
         throw new SQLException(new IllegalStateException("this.getJNDIName() == null"));
      } else {
         DataSource dataSource = null;
         if (logger != null && logger.isLoggable(Level.FINE)) {
            logger.logp(Level.FINE, className, "getDataSource", "Looking up a javax.sql.DataSource indexed under the name \"{0}\"", jndiName);
         }

         Object dataSourceObject = null;

         try {
            synchronized(context) {
               dataSourceObject = ((Context)context).lookup(jndiName);
            }
         } catch (NamingException var11) {
            throw new SQLException(var11);
         }

         if (dataSourceObject == null) {
            throw new SQLException("Could not obtain data source indexed under JNDI name " + jndiName);
         } else if (!(dataSourceObject instanceof DataSource)) {
            throw new SQLException("The object indexed under " + jndiName + " was not a DataSource");
         } else {
            dataSource = (DataSource)dataSourceObject;
            if (logger != null && logger.isLoggable(Level.FINER)) {
               logger.exiting(className, "getDataSource", dataSource);
            }

            return dataSource;
         }
      }
   }
}
