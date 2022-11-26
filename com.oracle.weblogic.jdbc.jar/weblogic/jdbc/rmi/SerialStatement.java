package weblogic.jdbc.rmi;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import weblogic.jdbc.JDBCLogger;
import weblogic.jdbc.wrapper.JDBCWrapperFactory;

public class SerialStatement extends RMIStubWrapperImpl implements RmiStatement, Serializable {
   private static final long serialVersionUID = -1944649289013384795L;
   private Statement rmi_stmt = null;
   private transient SerialConnection parent_conn = null;
   protected transient Set rsets = Collections.synchronizedSet(new HashSet());
   private transient boolean isClosed = false;

   public Object postInvocationHandler(String methodName, Object[] params, Object ret) throws Exception {
      if (ret == null) {
         super.postInvocationHandler(methodName, params, (Object)null);
         return null;
      } else {
         try {
            if (ret instanceof ResultSet) {
               ret = SerialResultSet.makeSerialResultSet((ResultSet)ret, this);
            }
         } catch (Exception var5) {
            JDBCLogger.logStackTrace(var5);
            throw var5;
         }

         super.postInvocationHandler(methodName, params, ret);
         return ret;
      }
   }

   public void preInvocationHandler(String methodName, Object[] params) throws Exception {
      super.preInvocationHandler(methodName, params);
      if (!methodName.equals("isClosed")) {
         this.checkClosed();
      }

   }

   public void init(Statement s, SerialConnection c) {
      this.rmi_stmt = s;
      this.parent_conn = c;
      this.isClosed = false;
   }

   public static Statement makeSerialStatement(Statement s, SerialConnection conn) {
      if (s == null) {
         return null;
      } else {
         SerialStatement serial_stmt = (SerialStatement)JDBCWrapperFactory.getWrapper("weblogic.jdbc.rmi.SerialStatement", s, false);
         serial_stmt.init(s, conn);
         return (Statement)serial_stmt;
      }
   }

   void addResultSet(SerialResultSet rs) {
      this.rsets.add(rs);
   }

   void removeResultSet(SerialResultSet rs) {
      this.rsets.remove(rs);
   }

   public int getRmiFetchSize() throws SQLException {
      try {
         return ((RmiStatement)this.rmi_stmt).getRmiFetchSize();
      } catch (Exception var2) {
         if (var2 instanceof SQLException) {
            throw (SQLException)var2;
         } else {
            throw new SQLException(var2.toString());
         }
      }
   }

   public void setRmiFetchSize(int new_size) throws SQLException {
      try {
         ((RmiStatement)this.rmi_stmt).setRmiFetchSize(new_size);
      } catch (Exception var3) {
         if (var3 instanceof SQLException) {
            throw (SQLException)var3;
         } else {
            throw new SQLException(var3.toString());
         }
      }
   }

   public void close() throws SQLException {
      this.close(true);
   }

   void close(boolean notify_parent) throws SQLException {
      if (!this.isClosed) {
         this.isClosed = true;

         try {
            this.closeAndClearAllResultSets();
            if (notify_parent) {
               this.parent_conn.removeStatement(this);
            }

            if (this.rmi_stmt instanceof SerialStatement) {
               ((SerialStatement)this.rmi_stmt).close(notify_parent);
            } else {
               this.rmi_stmt.close();
            }

         } catch (Exception var3) {
            if (var3 instanceof SQLException) {
               throw (SQLException)var3;
            } else {
               throw new SQLException(var3.toString());
            }
         }
      }
   }

   private void checkClosed() throws SQLException {
      if (this.isClosed) {
         throw new SQLException("Statement is closed.");
      }
   }

   public Connection getConnection() throws SQLException {
      String methodName = "getConnection";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         this.postInvocationHandler(methodName, params, this.parent_conn);
      } catch (Exception var4) {
         this.invocationExceptionHandler(methodName, params, var4);
      }

      return (Connection)this.parent_conn;
   }

   private void closeAndClearAllResultSets() {
      synchronized(this.rsets) {
         Iterator it = this.rsets.iterator();

         while(it.hasNext()) {
            SerialResultSet rset = (SerialResultSet)it.next();

            try {
               rset.close(false);
               it.remove();
            } catch (SQLException var6) {
            }
         }

      }
   }

   public boolean isClosed() throws SQLException {
      String methodName = "isClosed";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         super.postInvocationHandler(methodName, params, new Boolean(this.isClosed));
      } catch (Exception var4) {
         this.invocationExceptionHandler(methodName, params, var4);
      }

      return this.isClosed;
   }
}
