package weblogic.jdbc.wrapper;

import java.sql.SQLXML;
import weblogic.jdbc.common.internal.ConnectionEnv;

public class WrapperSQLXML extends DataType {
   public static SQLXML makeSQLXML(SQLXML sqlxml, java.sql.Connection conn) {
      if (sqlxml == null) {
         return null;
      } else {
         if (conn != null && conn instanceof Connection) {
            ConnectionEnv cc = ((Connection)conn).getConnectionEnv();
            if (cc != null && !cc.isWrapTypes()) {
               return sqlxml;
            }
         }

         WrapperSQLXML wrapperSQLXML = (WrapperSQLXML)JDBCWrapperFactory.getWrapper(16, sqlxml, false);
         wrapperSQLXML.init(conn);
         return (SQLXML)wrapperSQLXML;
      }
   }
}
