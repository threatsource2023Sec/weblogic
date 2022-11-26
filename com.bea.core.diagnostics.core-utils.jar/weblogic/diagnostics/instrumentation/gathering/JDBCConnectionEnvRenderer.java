package weblogic.diagnostics.instrumentation.gathering;

import weblogic.diagnostics.instrumentation.ValueRenderer;
import weblogic.jdbc.common.internal.ConnectionEnv;

public class JDBCConnectionEnvRenderer implements ValueRenderer {
   public Object render(Object inputObject) {
      if (inputObject != null && inputObject instanceof ConnectionEnv) {
         ConnectionEnv connEnv = (ConnectionEnv)inputObject;
         return new JDBCEventInfoImpl((String)null, connEnv.getPoolName(), connEnv.isInfected());
      } else {
         return null;
      }
   }
}
