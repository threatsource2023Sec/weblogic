package weblogic.jdbc.rmi.internal;

import weblogic.jdbc.JDBCLogger;
import weblogic.jdbc.wrapper.JDBCWrapperFactory;

public class DatabaseMetaDataImpl extends RMISkelWrapperImpl {
   private java.sql.DatabaseMetaData t2_dbmd = null;
   private RmiDriverSettings rmiSettings = null;

   public void init(java.sql.DatabaseMetaData d, RmiDriverSettings settings) {
      this.t2_dbmd = d;
      this.rmiSettings = new RmiDriverSettings(settings);
   }

   public Object postInvocationHandler(String methodName, Object[] params, Object ret) throws Exception {
      if (ret == null) {
         super.postInvocationHandler(methodName, params, (Object)null);
         return null;
      } else {
         try {
            if (ret instanceof java.sql.ResultSet) {
               java.sql.ResultSet rs = (java.sql.ResultSet)ret;
               ResultSetImpl rmi_rs = (ResultSetImpl)JDBCWrapperFactory.getWrapper("weblogic.jdbc.rmi.internal.ResultSetImpl", rs, true);
               rmi_rs.init(rs, this.rmiSettings);
               ret = (java.sql.ResultSet)rmi_rs;
            }
         } catch (Exception var6) {
            JDBCLogger.logStackTrace(var6);
            throw var6;
         }

         super.postInvocationHandler(methodName, params, ret);
         return ret;
      }
   }
}
