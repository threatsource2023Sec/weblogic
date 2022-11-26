package weblogic.jdbc.common.internal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;
import java.util.concurrent.Executor;

public final class ConnectionHolder {
   public Connection jconn;
   public ConnectionState state;
   public Method abort = null;
   public boolean hasAbortMethod = false;
   public Method pingDatabase = null;
   public boolean hasPingDatabaseMethod = false;
   public int pingDatabaseOk;
   public Method oracleProxyConnectionClose = null;
   public boolean hasOracleProxyConnectionCloseMethod = false;
   public Method oracleAttachServerConnection = null;
   public Method oracleDetachServerConnection = null;
   public Method oraclePhysicalConnectionWithin = null;
   public Method oracleBeginRequest = null;
   public Method oracleIsUsable = null;
   public Method getServerSessionInfo = null;
   public int proxySession;
   public Method oracleOpenProxySession = null;
   public boolean hasOracleOpenProxySession = false;
   public boolean hasSetProxyObject = false;
   public String proxyUserName;
   public String proxyUserPassword;
   public int proxyTypeUserName;
   public Method oracleGetTransactionState;
   public Object oracleTransactionStateTransactionStarted;
   public boolean abort41Supported;
   public Method abort41;
   private Executor abort41Executor = new Abort41Executor();
   public Method beginRequest43;
   public Method endRequest43;

   public boolean isAbortSupported() {
      return this.abort41Supported || this.hasAbortMethod;
   }

   public void invokeAbort(Connection conn) throws SQLException {
      Throwable abort41Throwable = null;
      Throwable abortThrowable = null;
      if (this.abort41Supported) {
         try {
            this.abort41.invoke(conn, this.abort41Executor);
            return;
         } catch (Throwable var6) {
            if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
               JdbcDebug.JDBCCONN.debug("JDBC 4.1 abort method invocation failed", var6);
            }

            this.abort41Supported = false;
            abort41Throwable = var6;
         }
      }

      if (this.hasAbortMethod) {
         try {
            this.abort.invoke(conn, (Object[])null);
            return;
         } catch (Throwable var5) {
            abortThrowable = var5;
            if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
               JdbcDebug.JDBCCONN.debug("Connection." + this.abort.getName() + "() invocation failed", var5);
            }

            if (var5 instanceof IllegalAccessException || var5 instanceof IllegalArgumentException || var5 instanceof InvocationTargetException) {
               throw new SQLException("Connection " + this.abort.getName() + "() invocation failed", var5);
            }
         }
      }

      if (abort41Throwable != null) {
         if (abort41Throwable instanceof Error) {
            throw (Error)abort41Throwable;
         } else if (abort41Throwable instanceof RuntimeException) {
            throw (RuntimeException)abort41Throwable;
         } else {
            throw new SQLException("JDBC 4.1 abort method invocation failed", abort41Throwable);
         }
      } else if (this.abort == null) {
         throw new UnsupportedOperationException("Invalid call to connection invokeAbort()");
      } else if (abortThrowable != null) {
         throw new UnsupportedOperationException("Connection " + this.abort.getName() + "() invocation failed", abortThrowable);
      } else {
         throw new UnsupportedOperationException("Connection " + this.abort.getName() + "() invocation failed");
      }
   }

   public boolean isBeginEndRequestSupported() {
      return this.beginRequest43 != null;
   }

   public void invokeBeginRequest(Connection conn) throws SQLException {
      if (this.beginRequest43 == null) {
         throw new UnsupportedOperationException("Connection.beginRequest() not supported");
      } else {
         try {
            this.beginRequest43.invoke(conn);
         } catch (Throwable var3) {
            if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
               JdbcDebug.JDBCCONN.debug("Connection.beginRequest() invocation failed", var3);
            }

            throw new SQLException("Connection.beginRequest() invocation failed", var3);
         }
      }
   }

   public void invokeEndRequest(Connection conn) throws SQLException {
      if (this.endRequest43 == null) {
         throw new UnsupportedOperationException("Connection.endRequest() not supported");
      } else {
         try {
            this.endRequest43.invoke(conn);
         } catch (Throwable var3) {
            if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
               JdbcDebug.JDBCCONN.debug("Connection.endRequest() invocation failed", var3);
            }

            throw new SQLException("Connection.endRequest() invocation failed", var3);
         }
      }
   }

   public boolean isOracleGetTransactionStateSupported() {
      return this.oracleGetTransactionState != null;
   }

   public boolean isOracleLocalTransactionStarted() {
      try {
         Object txstates = this.oracleGetTransactionState.invoke(this.jconn);
         return ((Set)txstates).contains(this.oracleTransactionStateTransactionStarted);
      } catch (Throwable var2) {
         if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
            JdbcDebug.JDBCCONN.debug(this.oracleGetTransactionState.toString() + " invocation failed", var2);
         }

         return false;
      }
   }

   private class Abort41Executor implements Executor {
      private Abort41Executor() {
      }

      public void execute(Runnable runnable) {
         runnable.run();
      }

      // $FF: synthetic method
      Abort41Executor(Object x1) {
         this();
      }
   }
}
