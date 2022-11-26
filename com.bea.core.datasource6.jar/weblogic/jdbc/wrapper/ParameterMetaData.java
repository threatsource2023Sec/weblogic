package weblogic.jdbc.wrapper;

import weblogic.jdbc.common.internal.ConnectionEnv;

public class ParameterMetaData extends JDBCWrapperImpl {
   protected java.sql.ParameterMetaData pmd = null;
   protected Connection conn = null;

   public void preInvocationHandler(String methodName, Object[] params) throws Exception {
      super.preInvocationHandler(methodName, params);
      this.conn.checkConnection();
   }

   public void init(java.sql.ParameterMetaData pmd, Connection conn) {
      this.pmd = pmd;
      this.conn = conn;
   }

   public static java.sql.ParameterMetaData makeParameterMetaData(java.sql.ParameterMetaData pmd, Connection conn) {
      if (conn != null && conn instanceof Connection) {
         ConnectionEnv cc = conn.getConnectionEnv();
         if (cc != null && !cc.isWrapTypes()) {
            return pmd;
         }
      }

      ParameterMetaData wrapperParameterMetaData = (ParameterMetaData)JDBCWrapperFactory.getWrapper(14, pmd, false);
      wrapperParameterMetaData.init(pmd, conn);
      return (java.sql.ParameterMetaData)wrapperParameterMetaData;
   }

   public ConnectionEnv getConnectionEnv() {
      return null;
   }
}
