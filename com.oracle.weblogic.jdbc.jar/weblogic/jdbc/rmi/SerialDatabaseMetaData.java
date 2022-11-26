package weblogic.jdbc.rmi;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import weblogic.jdbc.JDBCLogger;

public class SerialDatabaseMetaData extends RMIStubWrapperImpl implements Serializable {
   private static final long serialVersionUID = -7342158409258074989L;
   private DatabaseMetaData rmi_dbmd = null;
   private transient Connection parent_conn = null;

   public Object postInvocationHandler(String methodName, Object[] params, Object ret) throws Exception {
      super.postInvocationHandler(methodName, params, ret);
      if (ret == null) {
         return null;
      } else {
         try {
            if (ret instanceof ResultSet) {
               return SerialResultSet.makeSerialResultSet((ResultSet)ret, (SerialStatement)null);
            }
         } catch (Exception var5) {
            JDBCLogger.logStackTrace(var5);
         }

         return ret;
      }
   }

   public void init(DatabaseMetaData d, Connection c) {
      this.rmi_dbmd = d;
      this.parent_conn = c;
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

      return this.parent_conn;
   }
}
