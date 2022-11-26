package weblogic.diagnostics.instrumentation.gathering;

import weblogic.diagnostics.instrumentation.ValueRenderer;
import weblogic.jdbc.common.internal.ConnectionEnv;
import weblogic.jdbc.wrapper.Connection;

public class JDBCConnectionRenderer implements ValueRenderer {
   public Object render(Object inputObject) {
      if (inputObject != null && inputObject instanceof Connection) {
         Connection conn = (Connection)inputObject;
         ConnectionEnv env = conn.getConnectionEnv();
         return env == null ? new JDBCEventInfoImpl((String)null, conn.getPoolName()) : new JDBCEventInfoImpl((String)null, conn.getPoolName(), env.isInfected());
      } else {
         return null;
      }
   }
}
