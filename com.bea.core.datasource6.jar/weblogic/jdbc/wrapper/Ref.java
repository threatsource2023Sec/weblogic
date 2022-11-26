package weblogic.jdbc.wrapper;

import weblogic.jdbc.common.internal.ConnectionEnv;

public class Ref extends DataType {
   public static java.sql.Ref makeRef(java.sql.Ref ref, java.sql.Connection conn) {
      if (ref == null) {
         return null;
      } else {
         if (conn != null && conn instanceof Connection) {
            ConnectionEnv cc = ((Connection)conn).getConnectionEnv();
            if (cc != null && !cc.isWrapTypes()) {
               return ref;
            }
         }

         Ref wrapperRef = (Ref)JDBCWrapperFactory.getWrapper(11, ref, false);
         wrapperRef.init(conn);
         return (java.sql.Ref)wrapperRef;
      }
   }
}
