package weblogic.jdbc.wrapper;

import weblogic.jdbc.common.internal.ConnectionEnv;

public class Array extends DataType {
   public static java.sql.Array makeArray(java.sql.Array array, java.sql.Connection conn) {
      if (array == null) {
         return null;
      } else {
         if (conn != null && conn instanceof Connection) {
            ConnectionEnv cc = ((Connection)conn).getConnectionEnv();
            if (cc != null && !cc.isWrapTypes()) {
               return array;
            }
         }

         Array wrapperArray = (Array)JDBCWrapperFactory.getWrapper(9, array, false);
         wrapperArray.init(conn);
         return (java.sql.Array)wrapperArray;
      }
   }
}
