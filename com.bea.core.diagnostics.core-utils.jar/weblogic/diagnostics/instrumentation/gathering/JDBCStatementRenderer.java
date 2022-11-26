package weblogic.diagnostics.instrumentation.gathering;

import weblogic.diagnostics.instrumentation.ValueRenderer;
import weblogic.jdbc.common.internal.ConnectionEnv;
import weblogic.jdbc.wrapper.Statement;

public class JDBCStatementRenderer implements ValueRenderer {
   public Object render(Object inputObject) {
      if (inputObject != null && inputObject instanceof Statement) {
         Statement statement = (Statement)inputObject;
         ConnectionEnv conn = statement.getConnectionEnv();
         if (statement.sql == null && conn == null) {
            return null;
         } else {
            return conn == null ? new JDBCEventInfoImpl(statement.sql, (String)null) : new JDBCEventInfoImpl(statement.sql, conn.getPoolName(), conn.isInfected());
         }
      } else {
         return null;
      }
   }
}
