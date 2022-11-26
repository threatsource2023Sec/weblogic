package weblogic.jdbc.wrapper;

import weblogic.jdbc.common.internal.ConnectionEnv;
import weblogic.jdbc.common.internal.JDBCConnectionPool;
import weblogic.jdbc.extensions.DriverInterceptor;

public class ResultSetMetaData extends JDBCWrapperImpl {
   protected Connection conn = null;
   protected java.sql.ResultSetMetaData rsmd = null;

   public Object postInvocationHandler(String methodName, Object[] params, Object ret) throws Exception {
      if (this.conn != null) {
         ConnectionEnv cc = this.conn.getConnectionEnv();
         if (cc != null) {
            cc.setNotInUse();
            JDBCConnectionPool cp = cc.getConnectionPool();
            if (cp != null) {
               DriverInterceptor cb = cp.getDriverInterceptor();
               if (cb != null) {
                  cb.postInvokeCallback(this.vendorObj, methodName, params, ret);
               }
            }
         }
      }

      if (this.JDBCSQLDebug) {
         String s = methodName + " returns";
         if (ret != null) {
            s = s + " " + ret;
         }

         this.trace(s);
      }

      return ret;
   }

   public void preInvocationHandler(String methodName, Object[] params) throws Exception {
      if (this.JDBCSQLDebug) {
         String s = methodName + "(";
         if (params != null && params.length > 0) {
            for(int i = 0; i < params.length - 1; ++i) {
               s = s + params[i] + ", ";
            }

            s = s + params[params.length - 1] + ")";
         } else {
            s = s + ")";
         }

         this.trace(s);
      }

      if (this.conn != null) {
         ConnectionEnv cc = this.conn.getConnectionEnv();
         if (cc != null) {
            DriverInterceptor cb = cc.getConnectionPool().getDriverInterceptor();
            if (cb != null) {
               cb.preInvokeCallback(this.vendorObj, methodName, params);
            }

            cc.setInUse();
         }

         this.conn.checkConnection(cc);
      }

   }

   public ConnectionEnv getConnectionEnv() {
      return this.conn == null ? null : this.conn.getConnectionEnv();
   }

   public void init(java.sql.ResultSetMetaData rsmd, Connection conn) {
      this.rsmd = rsmd;
      this.conn = conn;
   }

   public static java.sql.ResultSetMetaData makeResultSetMetaData(java.sql.ResultSetMetaData rsmd, Connection conn) {
      if (rsmd == null) {
         return null;
      } else {
         if (conn != null && conn instanceof Connection) {
            ConnectionEnv cc = conn.getConnectionEnv();
            if (cc != null && !cc.isWrapTypes()) {
               return rsmd;
            }
         }

         ResultSetMetaData wrapperResultSetMetaData = (ResultSetMetaData)JDBCWrapperFactory.getWrapper(7, rsmd, false);
         wrapperResultSetMetaData.init(rsmd, conn);
         return (java.sql.ResultSetMetaData)wrapperResultSetMetaData;
      }
   }
}
