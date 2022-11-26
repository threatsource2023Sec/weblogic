package weblogic.jdbc.wrapper;

import java.sql.SQLException;
import weblogic.jdbc.common.internal.JdbcDebug;
import weblogic.utils.StackTraceUtils;
import weblogic.utils.wrapper.WrapperImpl;

public class AbstractWrapperImpl extends WrapperImpl {
   protected boolean JDBCSQLDebug;

   public AbstractWrapperImpl() {
      this.JDBCSQLDebug = JdbcDebug.JDBCSQL.isDebugEnabled();
   }

   public void preInvocationHandler(String methodName, Object[] params) throws SQLException {
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

   }

   public Object postInvocationHandler(String methodName, Object[] params, Object ret) throws SQLException {
      if (this.JDBCSQLDebug) {
         String s = methodName + " returns";
         if (ret != null) {
            s = s + " " + ret;
         }

         this.trace(s);
      }

      return ret;
   }

   public Object invocationExceptionHandler(String methodName, Object[] params, Throwable t) throws SQLException {
      if (this.JDBCSQLDebug) {
         String s = methodName + "(";
         if (params != null && params.length > 0) {
            for(int i = 0; i < params.length - 1; ++i) {
               s = s + params[i] + ", ";
            }

            s = s + params[params.length - 1] + ") throws ";
         } else {
            s = s + "unknown) throws: ";
         }

         s = s + StackTraceUtils.throwable2StackTrace(t);
         this.trace(s);
      }

      if (t instanceof SQLException) {
         throw (SQLException)t;
      } else if (t instanceof RuntimeException) {
         throw (RuntimeException)t;
      } else if (t instanceof Error) {
         throw (Error)t;
      } else {
         throw new SQLException(t);
      }
   }

   public void trace(String s) {
      JdbcDebug.JDBCSQL.debug("[" + this + "] " + s);
   }
}
