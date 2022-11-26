package weblogic.jdbc.wrapper;

import java.sql.SQLException;
import weblogic.jdbc.common.internal.ConnectionEnv;

public class Blob extends DataType {
   public static java.sql.Blob makeBlob(java.sql.Blob blob, java.sql.Connection conn) {
      if (blob == null) {
         return null;
      } else {
         if (conn != null && conn instanceof Connection) {
            ConnectionEnv cc = ((Connection)conn).getConnectionEnv();
            if (cc != null && !cc.isWrapTypes()) {
               return blob;
            }
         }

         Blob wrapperBlob = (Blob)JDBCWrapperFactory.getWrapper(13, blob, false);
         wrapperBlob.init(conn);
         return (java.sql.Blob)wrapperBlob;
      }
   }

   public long position(java.sql.Blob pattern, long start) throws SQLException {
      long ret = -1L;
      String methodName = "position";
      Object[] params = new Object[]{pattern, start};

      try {
         this.preInvocationHandler(methodName, params);
         if (pattern instanceof JDBCWrapperImpl) {
            ret = ((java.sql.Blob)this.getVendorObj()).position((java.sql.Blob)((JDBCWrapperImpl)pattern).getVendorObj(), start);
         } else {
            ret = ((java.sql.Blob)this.getVendorObj()).position(pattern, start);
         }

         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var9) {
         this.invocationExceptionHandler(methodName, params, var9);
      }

      return ret;
   }
}
