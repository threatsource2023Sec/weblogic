package weblogic.apache.org.apache.log.output.db;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import weblogic.apache.org.apache.log.LogEvent;
import weblogic.apache.org.apache.log.output.AbstractTarget;

public abstract class AbstractJDBCTarget extends AbstractTarget {
   private DataSource m_dataSource;
   private Connection m_connection;

   protected AbstractJDBCTarget(DataSource dataSource) {
      this.m_dataSource = dataSource;
   }

   protected synchronized void doProcessEvent(LogEvent event) throws Exception {
      this.checkConnection();
      if (this.isOpen()) {
         this.output(event);
      }

   }

   protected abstract void output(LogEvent var1);

   protected synchronized void open() {
      if (!this.isOpen()) {
         super.open();
         this.openConnection();
      }

   }

   protected synchronized void openConnection() {
      try {
         this.m_connection = this.m_dataSource.getConnection();
      } catch (Throwable var2) {
         this.getErrorHandler().error("Unable to open connection", var2, (LogEvent)null);
      }

   }

   protected final synchronized Connection getConnection() {
      return this.m_connection;
   }

   protected final synchronized void checkConnection() {
      if (this.isStale()) {
         this.closeConnection();
         this.openConnection();
      }

   }

   protected synchronized boolean isStale() {
      if (null == this.m_connection) {
         return true;
      } else {
         try {
            return this.m_connection.isClosed();
         } catch (SQLException var2) {
            return true;
         }
      }
   }

   public synchronized void close() {
      if (this.isOpen()) {
         this.closeConnection();
         super.close();
      }

   }

   protected synchronized void closeConnection() {
      if (null != this.m_connection) {
         try {
            this.m_connection.close();
         } catch (SQLException var2) {
            this.getErrorHandler().error("Error shutting down JDBC connection", var2, (LogEvent)null);
         }

         this.m_connection = null;
      }

   }
}
