package weblogic.jdbc.wrapper;

import weblogic.jdbc.common.internal.ConnectionEnv;

public class DataType extends JDBCWrapperImpl {
   private java.sql.Connection conn;

   public void init(java.sql.Connection conn) {
      this.conn = conn;
   }

   public ConnectionEnv getConnectionEnv() {
      return this.conn == null ? null : ((Connection)this.conn).getConnectionEnv();
   }

   public void preInvocationHandler(String methodName, Object[] params) throws Exception {
      super.preInvocationHandler(methodName, params);
      if (this.conn != null) {
         ((Connection)this.conn).checkConnection();
      }

   }
}
