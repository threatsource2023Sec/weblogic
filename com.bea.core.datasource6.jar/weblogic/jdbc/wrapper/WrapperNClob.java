package weblogic.jdbc.wrapper;

import java.sql.NClob;
import weblogic.jdbc.common.internal.ConnectionEnv;

public class WrapperNClob extends Clob {
   public static NClob makeNClob(NClob nclob, java.sql.Connection conn) {
      if (nclob == null) {
         return null;
      } else {
         if (conn != null && conn instanceof Connection) {
            ConnectionEnv cc = ((Connection)conn).getConnectionEnv();
            if (cc != null && !cc.isWrapTypes()) {
               return nclob;
            }
         }

         WrapperNClob wrapperNClob = (WrapperNClob)JDBCWrapperFactory.getWrapper(15, nclob, false);
         wrapperNClob.init(conn);
         return (NClob)wrapperNClob;
      }
   }
}
