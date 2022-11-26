package weblogic.jdbc.wrapper;

import java.io.Reader;
import java.sql.SQLException;
import weblogic.jdbc.common.internal.ConnectionEnv;
import weblogic.utils.StackTraceUtils;

public class Clob extends DataType {
   protected java.sql.Clob clob;

   public static java.sql.Clob makeClob(java.sql.Clob clob, java.sql.Connection conn) {
      if (clob == null) {
         return null;
      } else {
         if (conn != null && conn instanceof Connection) {
            ConnectionEnv cc = ((Connection)conn).getConnectionEnv();
            if (cc != null && !cc.isWrapTypes()) {
               return clob;
            }
         }

         Clob wrapperClob = (Clob)JDBCWrapperFactory.getWrapper(12, clob, false);
         wrapperClob.init(conn);
         wrapperClob.clob = clob;
         return (java.sql.Clob)wrapperClob;
      }
   }

   public long position(java.sql.Clob searchstr, long start) throws SQLException {
      long ret = -1L;
      String methodName = "position";
      Object[] params = new Object[]{searchstr, start};

      try {
         this.preInvocationHandler(methodName, params);
         if (searchstr instanceof JDBCWrapperImpl) {
            ret = ((java.sql.Clob)this.getVendorObj()).position((java.sql.Clob)((JDBCWrapperImpl)searchstr).getVendorObj(), start);
         } else {
            ret = ((java.sql.Clob)this.getVendorObj()).position(searchstr, start);
         }

         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var9) {
         this.invocationExceptionHandler(methodName, params, var9);
      }

      return ret;
   }

   public Reader getCharacterStream() throws SQLException {
      String methodName = "getCharacterStream";
      Object[] params = new Object[0];
      Reader ret = null;

      try {
         super.preInvocationHandler(methodName, params);
         ret = ((java.sql.Clob)this.getVendorObj()).getCharacterStream();
         super.postInvocationHandler(methodName, params, ret);
      } catch (Exception var7) {
         Exception e = var7;

         try {
            this.invocationExceptionHandler(methodName, params, e);
         } catch (Exception var6) {
            throw new SQLException(StackTraceUtils.throwable2StackTrace(var6));
         }
      }

      return ret;
   }
}
